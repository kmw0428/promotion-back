package com.project.promotion.unityContents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnityContentService {

    @Autowired
    private UnityContentRepository unityContentRepository;

    public List<UnityContent> findAll() {
        return unityContentRepository.findAll();
    }

    public Optional<UnityContent> findById(String id) {
        return unityContentRepository.findById(id);
    }

    public UnityContent save(UnityContent unityContent) {
        return unityContentRepository.save(unityContent);
    }

    public void deleteById(String id) {
        unityContentRepository.deleteById(id);
    }
}
