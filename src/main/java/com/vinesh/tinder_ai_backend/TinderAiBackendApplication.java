package com.vinesh.tinder_ai_backend;

import com.vinesh.tinder_ai_backend.conversations.ChatMessage;
import com.vinesh.tinder_ai_backend.conversations.Conversation;
import com.vinesh.tinder_ai_backend.conversations.ConversationRepository;
import com.vinesh.tinder_ai_backend.profiles.Gender;
import com.vinesh.tinder_ai_backend.profiles.Profile;
import com.vinesh.tinder_ai_backend.profiles.ProfileRepository;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TinderAiBackendApplication.class);

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private OpenAiChatModel chatModel;

    public static void main(String[] args) {
        SpringApplication.run(TinderAiBackendApplication.class, args);
    }

    public void run(String... args) {


        String response =  this.chatModel.call(new Prompt("Who are you?")).getResult().getOutput().getContent();
        System.out.println(response);

        profileRepository.deleteAll();
        conversationRepository.deleteAll();

        Profile profile = new Profile(
                "1",
                "Vinesh",
                "Kumar",
                25,
                "Indian",
                Gender.MALE,
                "Software Engineer",
                "foo.jpg",
                "INTP"
        );


        Profile profile2 = new Profile(
                "2",
                "Anuj",
                "Kumar",
                24,
                "Indian",
                Gender.MALE,
                "Software Engineer",
                "foo.jpg",
                "INTP"
        );

        profileRepository.save(profile);
        profileRepository.save(profile2);
        profileRepository.findAll().forEach(p -> log.info(p.toString()));

        Conversation conversation = new Conversation("1",
                profile.id(),
                List.of(
                        new ChatMessage("Hello", profile.id(), LocalDateTime.now())
                )
        );

        conversationRepository.save(conversation);
        conversationRepository.findAll().forEach(c -> log.info(c.toString()));
    }

}
