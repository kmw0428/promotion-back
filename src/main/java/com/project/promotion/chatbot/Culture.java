package com.project.promotion.chatbot;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cultures")
public class Culture {
    @Id
    private String id;

    private String question;
    private String answer;

    public Culture(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Culture() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
