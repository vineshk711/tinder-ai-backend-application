package com.vinesh.tinder_ai_backend.conversations;

import com.vinesh.tinder_ai_backend.profiles.ProfileRepository;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@Slf4j
public class ConversationController {

    private static final Logger log = LoggerFactory.getLogger(ConversationController.class);

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(
            ConversationRepository conversationRepository,
            ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request) {
        profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profile not found with ID: " + request.profileId()
                ));

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        log.info("Saved conversation: {}", conversation);
        return conversation;
    }

    @PutMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conversation not found with ID: " + conversationId
                ));

        profileRepository.findById(conversation.profileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profile not found with author ID: " + chatMessage.authorId()
                ));

        // TODO validation

        ChatMessage chatMessageToAdd = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(chatMessageToAdd);
        conversationRepository.save(conversation);
        return conversation;
    }

    public record CreateConversationRequest(
            String profileId
    ) {
    }
}
