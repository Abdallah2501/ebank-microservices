package ma.emsi.ebank.ebankbot.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot extends ListenerAdapter implements ApplicationRunner {

    private final ChatClient chatClient;

    @Value("${discord.bot-token:}")
    private String token;

    public DiscordBot(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (token == null || token.isBlank()) return;
        JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String text = event.getMessage().getContentRaw();
        String conversationId = "discord-" + event.getChannel().getId();

        String answer = chatClient.prompt()
                .user(text)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        if (answer != null && answer.length() > 1900) {
            answer = answer.substring(0, 1900) + "...";
        }
        event.getChannel().sendMessage(answer).queue();
    }
}