package ma.emsi.ebank.ebankbot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 ChatMemory chatMemory,
                                 ToolCallbackProvider tools) {
        return builder
                .defaultSystem("""
                        Tu es EbankBot, l'assistant d'une banque en ligne.
                        Tu aides uniquement pour les opérations bancaires : clients, comptes, soldes.
                        Utilise toujours les outils disponibles pour obtenir les données réelles.
                        N'invente jamais de clients, de comptes ou de soldes.
                        Si la question n'a aucun rapport avec la banque, refuse poliment
                        et rappelle ce que tu peux faire.
                        Réponds dans la langue de l'utilisateur.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(tools)
                .build();
    }
}
