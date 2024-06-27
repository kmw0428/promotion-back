package com.project.promotion.chatbot;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CultureRepository extends MongoRepository<Culture, String> {
}
