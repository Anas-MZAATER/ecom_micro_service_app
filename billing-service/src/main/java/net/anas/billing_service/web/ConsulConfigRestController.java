package net.anas.billing_service.web;

import net.anas.billing_service.myConfig.ConsulConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
//@RefreshScope
public class ConsulConfigRestController {
//    @Value("${token.accessTokenTimeout}")
//    private Long accessTokenTimeout;
//    @Value("${token.refreshTokenTimeout}")
//    private Long refreshTokenTimeout;
//
//    @GetMapping("/myConfig")
//    public Map<String, Long> myConfig() {
//        return Map.of("accessTokenTimeout", accessTokenTimeout,"refreshTokenTimeout", refreshTokenTimeout);
//    }

    private final ConsulConfig myConsulConfig;

    public ConsulConfigRestController(ConsulConfig myConsulConfig) {
        this.myConsulConfig = myConsulConfig;
    }

    @GetMapping("/myConfig")
    public ConsulConfig MyConsulConfig() {
        return myConsulConfig;
    }

}
