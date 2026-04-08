package net.anas.billing_service.myConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "user")
@Getter
@Setter
public class MyVaultConfig {
    private String username;
    private String password;
    private String otp;
}