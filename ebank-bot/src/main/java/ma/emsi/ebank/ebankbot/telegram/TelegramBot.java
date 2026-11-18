package ma.emsi.ebank.ebankbot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class TelegramBot implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private final ChatClient chatClient;
    private final RestClient http = RestClient.create();

    @Value("${telegram.bot-token:}")
    private String token;

    public TelegramBot(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (token == null || token.isBlank()) {
            log.info("Telegram désactivé : aucun token");
            return;
        }
        Thread thread = new Thread(this::poll, "telegram-polling");
        thread.setDaemon(true);
        thread.start();
        log.info("Telegram : écoute démarrée");
    }

    @SuppressWarnings("unchecked")
    private void poll() {
        String base = "https://api.telegram.org/bot" + token;
        long offset = 0;
        while (true) {
            try {
                Map<String, Object> res = http.get()
                        .uri(base + "/getUpdates?timeout=20&offset=" + offset)
                        .retrieve()
                        .body(new ParameterizedTypeReference<Map<String, Object>>() {});
                List<Map<String, Object>> updates = (List<Map<String, Object>>) res.get("result");
                for (Map<String, Object> update : updates) {
                    offset = ((Number) update.get("update_id")).longValue() + 1;
                    Map<String, Object> message = (Map<String, Object>) update.get("message");
                    if (message == null || message.get("text") == null) continue;

                    Number chatId = (Number) ((Map<String, Object>) message.get("chat")).get("id");
                    String text = (String) message.get("text");

                    String answer = chatClient.prompt()
                            .user(text)
                            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "telegram-" + chatId))
                            .call()
                            .content();

                    http.post()
                            .uri(base + "/sendMessage")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Map.of("chat_id", chatId, "text", answer))
                            .retrieve()
                            .toBodilessEntity();
                }
            } catch (Exception e) {
                log.warn("Telegram : erreur {}", e.toString());
                try { Thread.sleep(3000); } catch (InterruptedException ie) { return; }
            }
        }
    }
}
