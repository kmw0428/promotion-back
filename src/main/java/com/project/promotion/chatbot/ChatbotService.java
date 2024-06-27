package com.project.promotion.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ChatbotService {

    @Autowired
    private CultureRepository cultureRepository;

    public String getResponse(String message) throws IOException {
        String[] questions = splitQuestions(message);
        StringBuilder finalResponse = new StringBuilder();

        for (String question : questions) {
            String response = getResponseByKeywordMatching(question.trim());
            if (!response.equals("해당 주제에 대한 정보를 찾을 수 없습니다. 다른 질문을 해 주세요.")) {
                finalResponse.append(response).append("\n");
            }
        }

        if (finalResponse.length() == 0) {
            return "해당 주제에 대한 정보를 찾을 수 없습니다. 다른 질문을 해 주세요.";
        }

        return finalResponse.toString().trim();
    }

    private String getResponseByKeywordMatching(String message) throws IOException {
        List<Culture> cultures = cultureRepository.findAll();
        StringBuilder responseBuilder = new StringBuilder();

        // 전처리된 키워드 추출
        String preprocessedMessage = TextSimilarityUtil.preprocessQuestion(message);

        for (Culture culture : cultures) {
            String preprocessedQuestion = TextSimilarityUtil.preprocessQuestion(culture.getQuestion());

            if (preprocessedMessage.contains(preprocessedQuestion)) {
                responseBuilder.append(culture.getAnswer()).append("\n");
            }
        }

        if (responseBuilder.length() == 0) {
            return "해당 주제에 대한 정보를 찾을 수 없습니다. 다른 질문을 해 주세요.";
        }

        return responseBuilder.toString().trim();
    }

    private String[] splitQuestions(String message) {
        return message.split("[.?!]");
    }
}
