package de.dvdgeisler.iot.dirigera.dirigeraclient.http;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpsTrustManager implements X509TrustManager {
    private static final TrustManager[] trustManagers = new TrustManager[]{new HttpsTrustManager()};
    private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};

    @Override
    public void checkClientTrusted(
            X509Certificate[] x509Certificates, String s)
            throws java.security.cert.CertificateException {

    }

    @Override
    public void checkServerTrusted(final X509Certificate[] x509Certificates, final String s) {

    }

    public boolean isClientTrusted(final X509Certificate[] chain) {
        return true;
    }

    public boolean isServerTrusted(final X509Certificate[] chain) {
        return true;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    public static void allowAllSSL() throws NoSuchAlgorithmException, KeyManagementException {
        final SSLContext context;

        HttpsURLConnection.setDefaultHostnameVerifier((arg0, arg1) -> true);

        context = SSLContext.getInstance("TLS");
        context.init(null, trustManagers, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(context != null ? context.getSocketFactory() : null);
    }
}
