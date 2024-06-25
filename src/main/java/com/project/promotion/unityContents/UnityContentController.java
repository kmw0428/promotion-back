package com.project.promotion.unityContents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/unityContent")
public class UnityContentController {

    @Autowired
    private UnityContentService unityContentService;

    @GetMapping
    public List<UnityContent> getAllUnityContent() {
        return unityContentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnityContent> getUnityContentById(@PathVariable String id) {
        Optional<UnityContent> unityContent = unityContentService.findById(id);
        if (unityContent.isPresent()) {
            return ResponseEntity.ok(unityContent.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UnityContent createUnityContent(@RequestBody UnityContent unityContent) {
        return unityContentService.save(unityContent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnityContent> updateUnityContent(@PathVariable String id, @RequestBody UnityContent unityContent) {
        if (unityContentService.findById(id).isPresent()) {
            unityContent.setId(id);
            return ResponseEntity.ok(unityContentService.save(unityContent));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnityContent(@PathVariable String id) {
        if (unityContentService.findById(id).isPresent()) {
            unityContentService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
