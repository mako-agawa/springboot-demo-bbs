package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;


@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public Post save(Post post){
        return postRepository.save(post);

    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    public boolean verifyOwnership(Post post, User user){
        if (post.getUser() == null){
            return false;
        }
        if(!post.getUser().getId().equals(user.getId())){
            return false;
        }
        return true;
    }
}
