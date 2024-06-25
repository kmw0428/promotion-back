package com.project.promotion.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(String id) {
        return postRepository.findById(id);
    }

    public List<Post> findByBoardId(String boardId) {
        return postRepository.findByBoardId(boardId);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deleteById(String id) {
        postRepository.deleteById(id);
    }
}
