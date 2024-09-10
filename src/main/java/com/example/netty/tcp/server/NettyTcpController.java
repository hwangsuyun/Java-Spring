package com.example.netty.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SuppressWarnings("unused")
@Controller
public class NettyTcpController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private NettySocketServer server;

    @PostConstruct
    private void start() {
        new Thread(() -> {
            try {
                server = new NettySocketServer(5010);
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 바인딩된 NettyServer 를 종료한다.
     * // @throws InterruptedException
     */
    @PreDestroy
    private void destroy() throws InterruptedException {
        logger.info("================ Spring @PreDestroy ================");
        server.serverDestroy();
    }
}
