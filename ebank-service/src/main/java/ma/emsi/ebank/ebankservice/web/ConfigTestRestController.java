package ma.emsi.ebank.ebankservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigTestRestController {

    @Value("${ebank.welcome-message:Message par défaut (config non chargée)}")
    private String welcomeMessage;

    @GetMapping("/api/config-test")
    public Map<String, String> configTest() {
        return Map.of("message", welcomeMessage);
    }
}
