package com.project.promotion.locations;

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
@RequestMapping("/api/locations")
public class LocationController {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable String id) {
        Optional<Location> location = locationService.findById(id);
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.save(location);
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

            Optional<Location> locationOptional = locationService.findById(id);
            if (locationOptional.isPresent()) {
                Location location = locationOptional.get();
                location.setImageURL("/uploads/" + fileName.replace("\\", "/"));
                locationService.save(location);
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
    public ResponseEntity<Location> updateLocation(@PathVariable String id, @RequestBody Location location) {
        if (locationService.findById(id).isPresent()) {
            location.setId(id);
            return ResponseEntity.ok(locationService.save(location));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        if (locationService.findById(id).isPresent()) {
            locationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
