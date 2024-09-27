package com.example.web.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ContainerConfig {
    @Value("${tomcat.ajp.protocol}")
    String ajpProtocol;

    @Value("${tomcat.ajp.port}")
    int ajpPort;

    @Value("${tomcat.ajp.address}")
    String ajpAddress;

    @Value("${tomcat.ajp.allowedRequestAttributesPattern}")
    String ajpAllowedRequestAttributesPattern;

    @Value("${server.port.http}")
    int serverPortHttp;

    @Value("${server.port}")
    int serverPortHttps;

    @Bean
    public ServletWebServerFactory servletContainer() {
         /* Multi Connector 를 이용하여 8888 http 포트를 사용하는 방식
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createAjpConnector());
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());

        return tomcat;
        */

        /* redirect 시키는 방법 */
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
                @Override
                protected void postProcessContext(Context context) {
                    SecurityConstraint securityConstraint = new SecurityConstraint();
                    securityConstraint.setUserConstraint("CONFIDENTIAL");
                    SecurityCollection collection = new SecurityCollection();
                    collection.addPattern("/*");
                    securityConstraint.addCollection(collection);
                    context.addConstraint(securityConstraint);
                }
        };
        tomcat.addAdditionalTomcatConnectors(createAjpConnector());
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());

        return tomcat;
    }

    private Connector createAjpConnector() {
        Connector ajpConnector = new Connector(ajpProtocol);
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(false);
        ajpConnector.setAllowTrace(false);
        ajpConnector.setScheme("http");
        ajpConnector.setProperty("address", ajpAddress);
        ajpConnector.setProperty("allowedRequestAttributesPattern", ajpAllowedRequestAttributesPattern);
        ((AbstractAjpProtocol<?>)ajpConnector.getProtocolHandler()).setSecretRequired(false);
        return ajpConnector;
    }

    /* Multi Connector 를 이용하여 8888 http 포트를 사용하는 방식
    private Connector createStandardConnector() {
        Connector connector =
                new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8888); // 포트번호는 HTTPS 에서 쓰는 포트와 다르게 해줘야 한다
        return connector;
    }
     */

    /* redirect 시키는 방법 */
    private Connector createStandardConnector() {
        Connector connector =
                new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(serverPortHttp);
        connector.setRedirectPort(serverPortHttps); // RedirectPort 메서드 추가
        return connector;
    }
}
