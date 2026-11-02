package ma.emsi.ebank.ebankbot.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRestController {

    private final ChatClient chatClient;

    public ChatRestController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultSystem("""
                        Tu es EbankBot, l'assistant d'une banque en ligne.
                        Tu aides uniquement pour les opérations bancaires : clients, comptes, soldes.
                        Si la question n'a aucun rapport avec la banque, refuse poliment
                        et rappelle ce que tu peux faire.
                        Réponds dans la langue de l'utilisateur.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String question,
                       @RequestParam(defaultValue = "default") String conversationId) {
        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}