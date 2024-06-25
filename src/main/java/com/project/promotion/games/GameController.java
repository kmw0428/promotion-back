package com.project.promotion.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        Optional<Game> game = gameService.findById(id);
        if(game.isPresent()) {
            return ResponseEntity.ok(game.get());
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameService.save(game);
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("image") MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is empty");
        }

        try {
            String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path filePath = fileStorageLocation.resolve(fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageFile.getBytes());

            Optional<Game> gameOptional = gameService.findById(id);
            if (gameOptional.isPresent()) {
                Game game = gameOptional.get();
                game.setImageURL("/uploads/" + fileName.replace("\\", "/"));
                gameService.save(game);
                return ResponseEntity.ok("Image uploaded successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable String id, @RequestBody Game game) {
        if (gameService.findById(id).isPresent()) {
            game.setId(id);
            return ResponseEntity.ok(gameService.save(game));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        if (gameService.findById(id).isPresent()) {
            gameService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
