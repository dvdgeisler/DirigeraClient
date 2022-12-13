package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DirigeraTLVTag {
    PAIRING_METHOD((byte) 0x00), USERNAME((byte) 0x01), SALT((byte) 0x02), PUBLIC_KEY((byte) 0x03), PROOF((byte) 0x04), ENCRYPTED_DATA((byte) 0x05), SEQUENCE((byte) 0x06), ERROR_CODE((byte) 0x07), UNKNOWN_008((byte) 0x08), CERTIFICATE((byte) 0x09), SIGNATURE((byte) 0x0A), UNKNOWN_011((byte) 0x0B), UNKNOWN_012((byte) 0x0C), UNKNOWN_013((byte) 0x0D), UNKNOWN_014((byte) 0x0E), UNKNOWN_015((byte) 0x0F),
    UNKNOWN_016((byte) 0x10), UNKNOWN_017((byte) 0x11), UNKNOWN_018((byte) 0x12), UNKNOWN_019((byte) 0x13), UNKNOWN_020((byte) 0x14), UNKNOWN_021((byte) 0x15), UNKNOWN_022((byte) 0x16), UNKNOWN_023((byte) 0x17), UNKNOWN_024((byte) 0x18), UNKNOWN_025((byte) 0x19), UNKNOWN_026((byte) 0x1A), UNKNOWN_027((byte) 0x1B), UNKNOWN_028((byte) 0x1C), UNKNOWN_029((byte) 0x1D), UNKNOWN_010((byte) 0x1E), UNKNOWN_031((byte) 0x1F),
    UNKNOWN_032((byte) 0x20), UNKNOWN_033((byte) 0x21), UNKNOWN_034((byte) 0x22), UNKNOWN_035((byte) 0x23), UNKNOWN_036((byte) 0x24), UNKNOWN_037((byte) 0x25), UNKNOWN_038((byte) 0x26), UNKNOWN_039((byte) 0x27), UNKNOWN_040((byte) 0x28), UNKNOWN_041((byte) 0x29), UNKNOWN_042((byte) 0x2A), UNKNOWN_043((byte) 0x2B), UNKNOWN_044((byte) 0x2C), UNKNOWN_045((byte) 0x2D), UNKNOWN_046((byte) 0x2E), UNKNOWN_047((byte) 0x2F),
    UNKNOWN_048((byte) 0x30), UNKNOWN_049((byte) 0x31), UNKNOWN_050((byte) 0x32), UNKNOWN_051((byte) 0x33), UNKNOWN_052((byte) 0x34), UNKNOWN_053((byte) 0x35), UNKNOWN_054((byte) 0x36), UNKNOWN_055((byte) 0x37), UNKNOWN_056((byte) 0x38), UNKNOWN_057((byte) 0x39), UNKNOWN_058((byte) 0x3A), UNKNOWN_059((byte) 0x3B), UNKNOWN_060((byte) 0x3C), UNKNOWN_061((byte) 0x3D), UNKNOWN_062((byte) 0x3E), UNKNOWN_063((byte) 0x3F),
    UNKNOWN_064((byte) 0x40), UNKNOWN_065((byte) 0x41), UNKNOWN_066((byte) 0x42), UNKNOWN_067((byte) 0x43), UNKNOWN_068((byte) 0x44), UNKNOWN_069((byte) 0x45), UNKNOWN_070((byte) 0x46), UNKNOWN_071((byte) 0x47), UNKNOWN_072((byte) 0x48), UNKNOWN_073((byte) 0x49), UNKNOWN_074((byte) 0x4A), UNKNOWN_075((byte) 0x4B), UNKNOWN_076((byte) 0x4C), UNKNOWN_077((byte) 0x4D), UNKNOWN_078((byte) 0x4E), UNKNOWN_079((byte) 0x4F),
    UNKNOWN_080((byte) 0x50), UNKNOWN_081((byte) 0x51), UNKNOWN_082((byte) 0x52), UNKNOWN_083((byte) 0x53), UNKNOWN_084((byte) 0x54), UNKNOWN_085((byte) 0x55), UNKNOWN_086((byte) 0x56), UNKNOWN_087((byte) 0x57), UNKNOWN_088((byte) 0x58), UNKNOWN_089((byte) 0x59), UNKNOWN_090((byte) 0x5A), UNKNOWN_091((byte) 0x5B), UNKNOWN_092((byte) 0x5C), UNKNOWN_093((byte) 0x5D), UNKNOWN_094((byte) 0x5E), UNKNOWN_095((byte) 0x5F),
    UNKNOWN_096((byte) 0x60), UNKNOWN_097((byte) 0x61), UNKNOWN_098((byte) 0x62), UNKNOWN_099((byte) 0x63), UNKNOWN_100((byte) 0x64), UNKNOWN_101((byte) 0x65), UNKNOWN_102((byte) 0x66), UNKNOWN_103((byte) 0x67), UNKNOWN_104((byte) 0x68), UNKNOWN_105((byte) 0x69), UNKNOWN_106((byte) 0x6A), UNKNOWN_107((byte) 0x6B), UNKNOWN_108((byte) 0x6C), UNKNOWN_109((byte) 0x6D), UNKNOWN_110((byte) 0x6E), UNKNOWN_111((byte) 0x6F),
    UNKNOWN_112((byte) 0x70), UNKNOWN_113((byte) 0x71), UNKNOWN_114((byte) 0x72), UNKNOWN_115((byte) 0x73), UNKNOWN_116((byte) 0x74), UNKNOWN_117((byte) 0x75), UNKNOWN_118((byte) 0x76), UNKNOWN_119((byte) 0x77), UNKNOWN_120((byte) 0x78), UNKNOWN_121((byte) 0x79), UNKNOWN_122((byte) 0x7A), UNKNOWN_123((byte) 0x7B), UNKNOWN_124((byte) 0x7C), UNKNOWN_125((byte) 0x7D), UNKNOWN_126((byte) 0x7E), UNKNOWN_127((byte) 0x7F),
    UNKNOWN_128((byte) 0x80), UNKNOWN_129((byte) 0x81), UNKNOWN_130((byte) 0x82), UNKNOWN_131((byte) 0x83), UNKNOWN_132((byte) 0x84), UNKNOWN_133((byte) 0x85), UNKNOWN_134((byte) 0x86), UNKNOWN_135((byte) 0x87), UNKNOWN_136((byte) 0x88), UNKNOWN_137((byte) 0x89), UNKNOWN_138((byte) 0x8A), UNKNOWN_139((byte) 0x8B), UNKNOWN_140((byte) 0x8C), UNKNOWN_141((byte) 0x8D), UNKNOWN_142((byte) 0x8E), UNKNOWN_143((byte) 0x8F),
    UNKNOWN_144((byte) 0x90), UNKNOWN_145((byte) 0x91), UNKNOWN_146((byte) 0x92), UNKNOWN_147((byte) 0x93), UNKNOWN_148((byte) 0x94), UNKNOWN_149((byte) 0x95), UNKNOWN_150((byte) 0x96), UNKNOWN_151((byte) 0x97), UNKNOWN_152((byte) 0x98), UNKNOWN_153((byte) 0x99), UNKNOWN_154((byte) 0x9A), UNKNOWN_155((byte) 0x9B), UNKNOWN_156((byte) 0x9C), UNKNOWN_157((byte) 0x9D), UNKNOWN_158((byte) 0x9E), UNKNOWN_159((byte) 0x9F),
    UNKNOWN_160((byte) 0xA0), UNKNOWN_161((byte) 0xA1), UNKNOWN_162((byte) 0xA2), UNKNOWN_163((byte) 0xA3), UNKNOWN_164((byte) 0xA4), UNKNOWN_165((byte) 0xA5), UNKNOWN_166((byte) 0xA6), UNKNOWN_167((byte) 0xA7), UNKNOWN_168((byte) 0xA8), UNKNOWN_169((byte) 0xA9), UNKNOWN_170((byte) 0xAA), UNKNOWN_171((byte) 0xAB), UNKNOWN_172((byte) 0xAC), UNKNOWN_173((byte) 0xAD), UNKNOWN_174((byte) 0xAE), UNKNOWN_175((byte) 0xAF),
    UNKNOWN_176((byte) 0xB0), UNKNOWN_177((byte) 0xB1), UNKNOWN_178((byte) 0xB2), UNKNOWN_179((byte) 0xB3), UNKNOWN_180((byte) 0xB4), UNKNOWN_181((byte) 0xB5), UNKNOWN_182((byte) 0xB6), UNKNOWN_183((byte) 0xB7), UNKNOWN_184((byte) 0xB8), UNKNOWN_185((byte) 0xB9), UNKNOWN_186((byte) 0xBA), UNKNOWN_187((byte) 0xBB), UNKNOWN_188((byte) 0xBC), UNKNOWN_189((byte) 0xBD), UNKNOWN_190((byte) 0xBE), UNKNOWN_191((byte) 0xBF),
    UNKNOWN_192((byte) 0xC0), UNKNOWN_193((byte) 0xC1), UNKNOWN_194((byte) 0xC2), UNKNOWN_195((byte) 0xC3), UNKNOWN_196((byte) 0xC4), UNKNOWN_197((byte) 0xC5), UNKNOWN_198((byte) 0xC6), UNKNOWN_199((byte) 0xC7), UNKNOWN_200((byte) 0xC8), UNKNOWN_201((byte) 0xC9), UNKNOWN_202((byte) 0xCA), UNKNOWN_203((byte) 0xCB), UNKNOWN_204((byte) 0xCC), UNKNOWN_205((byte) 0xCD), UNKNOWN_206((byte) 0xCE), UNKNOWN_207((byte) 0xCF),
    UNKNOWN_208((byte) 0xD0), UNKNOWN_209((byte) 0xD1), UNKNOWN_210((byte) 0xD2), UNKNOWN_211((byte) 0xD3), UNKNOWN_212((byte) 0xD4), UNKNOWN_213((byte) 0xD5), UNKNOWN_214((byte) 0xD6), UNKNOWN_215((byte) 0xD7), UNKNOWN_216((byte) 0xD8), UNKNOWN_217((byte) 0xD9), UNKNOWN_218((byte) 0xDA), UNKNOWN_219((byte) 0xDB), UNKNOWN_220((byte) 0xDC), UNKNOWN_221((byte) 0xDD), UNKNOWN_222((byte) 0xDE), UNKNOWN_223((byte) 0xDF),
    UNKNOWN_224((byte) 0xE0), UNKNOWN_225((byte) 0xE1), UNKNOWN_226((byte) 0xE2), UNKNOWN_227((byte) 0xE3), UNKNOWN_228((byte) 0xE4), UNKNOWN_229((byte) 0xE5), UNKNOWN_230((byte) 0xE6), UNKNOWN_231((byte) 0xE7), UNKNOWN_232((byte) 0xE8), UNKNOWN_233((byte) 0xE9), UNKNOWN_234((byte) 0xEA), UNKNOWN_235((byte) 0xEB), UNKNOWN_236((byte) 0xEC), UNKNOWN_237((byte) 0xED), UNKNOWN_238((byte) 0xEE), UNKNOWN_239((byte) 0xEF),
    UNKNOWN_240((byte) 0xF0), UNKNOWN_241((byte) 0xF1), UNKNOWN_242((byte) 0xF2), UNKNOWN_243((byte) 0xF3), UNKNOWN_244((byte) 0xF4), UNKNOWN_245((byte) 0xF5), UNKNOWN_246((byte) 0xF6), UNKNOWN_247((byte) 0xF7), UNKNOWN_248((byte) 0xF8), UNKNOWN_249((byte) 0xF9), UNKNOWN_250((byte) 0xFA), UNKNOWN_251((byte) 0xFB), UNKNOWN_252((byte) 0xFC), UNKNOWN_253((byte) 0xFD), UNKNOWN_254((byte) 0xFE), UNKNOWN_255((byte) 0xFF);

    private final static Map<Byte, DirigeraTLVTag> code2tag = Arrays.stream(DirigeraTLVTag.values()).collect(Collectors.toMap(tag -> tag.code, tag -> tag));

    private final byte code;


    DirigeraTLVTag(byte code) {
        this.code = code;
    }

    public static void encode(final DirigeraTLVTag tag, final OutputStream os) throws IOException {
        os.write(tag.code);
    }

    public static DirigeraTLVTag decode(final InputStream os) throws IOException {
        return decode(os.readNBytes(1));
    }

    public static DirigeraTLVTag decode(final byte[] bytes) {
        return code2tag.get(bytes[0]);
    }
}
