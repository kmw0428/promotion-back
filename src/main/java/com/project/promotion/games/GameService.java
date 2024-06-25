package com.project.promotion.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(String id) {
        return gameRepository.findById(id);
    }

    public Game save(Game game) {
        return gameRepository,save(game);
    }

    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }
}
