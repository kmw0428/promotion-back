package com.project.promotion.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public String handleChat(@RequestBody ChatRequest chatRequest) throws IOException {
        return chatbotService.getResponse(chatRequest.getMessage());
    }
}
