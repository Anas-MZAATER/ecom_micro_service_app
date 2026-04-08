package net.anas.billing_service.web;

import net.anas.billing_service.myConfig.MyConsulConfig;
import net.anas.billing_service.myConfig.MyVaultConfig;
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

    private final MyConsulConfig myConsulConfig;
    private final MyVaultConfig myVaultConfig;
    public ConsulConfigRestController(MyConsulConfig myConsulConfig,
                                      MyVaultConfig myVaultConfig) {
        this.myConsulConfig = myConsulConfig;
        this.myVaultConfig = myVaultConfig;
    }

    @GetMapping("/myConfig")
    public Map<String, Object> MyConfig() {
        return Map.of("myConsulConfig", myConsulConfig, "myVaultConfig", myVaultConfig);
    }

}
