package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

import de.dvdgeisler.iot.dirigera.client.api.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.List;
import java.util.Optional;

@Component
public class Cryptographer extends ByteToMessageCodec<ByteBuf> {
    private final static Logger log = LoggerFactory.getLogger(Cryptographer.class);

    private final static int HMAC_LENGTH = 16;

    private final Sodium sodium;

    private byte[] writeKey;
    private byte[] readKey;

    private long requestCounter;
    private long responseCounter;

    public Cryptographer(final Sodium sodium) {
        this.sodium = sodium;
        this.requestCounter = 0;
        this.responseCounter = 0;
        this.writeKey = null;
        this.readKey = null;
    }

    public synchronized void enable(final byte[] writeKey, final byte[] readKey) {
        this.requestCounter = 0;
        this.responseCounter = 0;
        this.writeKey = writeKey;
        this.readKey = readKey;
    }

    public boolean isEnabled() {
        return this.readKey != null && this.writeKey != null;
    }

    public TcpClient tcpSetup(final TcpClient tcpClient) {
        return tcpClient.doOnConnected(this::tcpSetup);
    }

    public void tcpSetup(final Connection connection) {
        final ChannelPipeline pipeline;

        pipeline = connection.channel().pipeline();

        if (pipeline.get("homekit.encryption") != null) {
            log.info("Encryption channel handler already set");
            return;
        }

        Optional.of(pipeline)
                .map(p -> p.get("homekit.encryption"))
                .ifPresentOrElse((channelHandler) -> {
                    log.info("Encryption channel handler already set");
                }, () -> {
                    log.info("Add encryption channel handler to homekit connection");
                    pipeline.addFirst("homekit.encryption", this);
                });
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        byte[] chunk, AAD, nonce;

        if(!this.isEnabled()) {
            ByteUtils.move(in, out);
            return;
        }

        log.debug("encode: \"{}\"", in.toString(StandardCharsets.UTF_8));

        for (int bytes = in.readableBytes(); bytes > 0; bytes = in.readableBytes()) {
            chunk = new byte[Math.min(bytes, 0x400)];
            in.readBytes(chunk);

            AAD = ByteUtils.UInt16toLE((short) chunk.length);
            nonce = ByteUtils.UInt53toLE(this.requestCounter);

            chunk = this.encryptAndSeal(chunk, AAD, nonce, this.writeKey);
            chunk = ByteUtils.concat(AAD, chunk);
            out.writeBytes(chunk);

            this.requestCounter++;
        }

    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
        ByteBuf result;

        if(!this.isEnabled()) {
            result = ctx.alloc().buffer();
            ByteUtils.move(in, result);
            out.add(result);
            return;
        }

        byte[] AAD, nonce, cipherText, hmac, decrypted;
        int length;

        while (in.readableBytes() >0) {
            if(!this.isPackageComplete(in))
                break;

            AAD = new byte[2];
            in.readBytes(AAD);

            length = ByteUtils.LEtoUInt16(AAD);
            nonce = ByteUtils.UInt53toLE(this.responseCounter);

            cipherText = new byte[length];
            in.readBytes(cipherText);

            hmac = new byte[HMAC_LENGTH];
            in.readBytes(hmac);

            decrypted = this.verifyAndDecrypt(cipherText, hmac, AAD, nonce, this.readKey);

            result = ctx.alloc().buffer(decrypted.length);
            result.writeBytes(decrypted);
            out.add(result);

            log.debug("decoded: \"{}\"", result.toString(StandardCharsets.UTF_8));

            this.responseCounter++;
        }
    }

    private boolean isPackageComplete(final ByteBuf buf) {
        return buf.getShortLE(0) <= buf.readableBytes() - HMAC_LENGTH;
    }

    private byte[] verifyAndDecrypt(final byte[] cipherText, final byte[] hmac, final byte[] AAD, final byte[] nonce, final byte[] key) throws SignatureException {
        final int match;
        final byte[] computedHMac, decrypted;

        computedHMac = this.computePoly1305(cipherText, AAD, nonce, key);
        match = this.sodium.sodium_compare(hmac, computedHMac, HMAC_LENGTH);

        if(match != 0)
            throw new SignatureException("Computed MAC dies not match with response");

        decrypted = new byte[cipherText.length];
        this.sodium.crypto_stream_chacha20_xor_ic(decrypted, cipherText, cipherText.length, nonce, 1, key);

        return decrypted;
    }

    private byte[] encryptAndSeal(byte[] plainText, byte[] AAD, byte[] nonce, byte[] key) {
        final byte[] cipherText;
        final byte[] hmac;

        cipherText = new byte[plainText.length];
        this.sodium.crypto_stream_chacha20_xor_ic(cipherText, plainText, plainText.length, nonce, 1, key);
        hmac = this.computePoly1305(cipherText, AAD, nonce, key);

        return ByteUtils.concat(cipherText, hmac);
    }


    private byte[] computePoly1305(final byte[] cipherText, final byte[] AAD, final byte[] nonce, final byte[] key) {
        final byte[] msg;
        final byte[] polyKey;
        final byte[] hMac;

        msg = ByteUtils.concat(
                AAD,
                getPadding(AAD, 16),
                cipherText,
                getPadding(cipherText, 16),
                ByteUtils.UInt53toLE(AAD.length),
                ByteUtils.UInt53toLE(cipherText.length));
        polyKey = new byte[32];
        this.sodium.crypto_stream_chacha20(polyKey, 32, nonce, key);
        hMac = new byte[HMAC_LENGTH];
        this.sodium.crypto_onetimeauth(hMac, msg, msg.length, polyKey);

        return hMac;
    }

    private byte[] getPadding(byte[] v, int l) {
        return v.length % l == 0
                ? new byte[0]
                : new byte[l - (v.length % l)];
    }
}
