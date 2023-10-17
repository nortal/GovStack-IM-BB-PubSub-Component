package com.govstack.information_mediator.pubsub.management.xroad_admin;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Slf4j
@Configuration
@PropertySource("classpath:management-api-xroad-admin.properties")
public class XRoadClientConfiguration {

    @Value("${management.xroad-admin-client.security-server-base-url}")
    String securityServerBaseUrl;
    @Value("${management.xroad-admin-client.api-key}")
    String apiKey;
    @Value("${management.xroad-admin-client.api-key-prefix}")
    String apiKeyPrefix;

    @Value("${management.xroad-admin-client.truststore}")
    Resource truststore;
    @Value("${management.xroad-admin-client.truststore-password}")
    String truststorePassword;

    @Value("${management.xroad-admin-client.disable-tls-certificate-verification}")
    boolean disableTlsCertificateVerification = false;

    @Bean
    public SslContext sslContext() {
        try {
            return SslContextBuilder.forClient()
                .trustManager(getTrustManagerFactory())
                .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SSLContext", e);
        }
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        var sslContext = sslContext();
        var httpClient = HttpClient.create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
        var connector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
            .baseUrl(securityServerBaseUrl)
            .clientConnector(connector);
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient(webClientBuilder().build());
        apiClient.setBasePath(securityServerBaseUrl);
        apiClient.setApiKey(apiKey);
        apiClient.setApiKeyPrefix(apiKeyPrefix);
        return apiClient;
    }

    private KeyStore loadTruststore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream trustStoreStream = truststore.getInputStream()) {
            trustStore.load(trustStoreStream, truststorePassword.toCharArray());
        }
        return trustStore;
    }

    private TrustManagerFactory getTrustManagerFactory() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {

        if (disableTlsCertificateVerification) {
            log.warn("**** WARNING!!! **** TLS certificate verification for X-Road Admin API client has been disabled. " +
                "This leaves management-api vulnerable for man-in-the-middle attacks. Please check configuration and " +
                "verify if parameter management.xroad-admin-client.disable-tls-certificate-verification=true is " +
                "actually required.");
            return new TrustAllTrustManagerFactory();
        }

        KeyStore trustStore = loadTruststore();
        var tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        return tmf;
    }

}
