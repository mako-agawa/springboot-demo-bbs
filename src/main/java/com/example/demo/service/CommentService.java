package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> findById(Long id){
        return commentRepository.findById(id);
    }

    public List<Comment> findByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }

    public boolean verifyOwnership(Comment comment, User user){
        if (comment.getUser() == null){
            return false;
        }
        if(!comment.getUser().getId().equals(user.getId())){
            return false;
        }
        return true;
    }
}
