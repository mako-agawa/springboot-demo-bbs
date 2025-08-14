package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService,PostService postService, UserService userService){
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    // コメント追加
    @PostMapping("/add")
    public String addComment(@RequestParam Long postId, @RequestParam String content){
        Post post = postService.findById(postId).orElseThrow();
        User user = userService.getCurrentUser();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setUser(user);

        commentService.save(comment);
        return "redirect:/posts/" + postId;
    }

    // コメント削除
    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id, @RequestParam Long postId) {
        // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        // コメントを取得
        Comment comment = commentService.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        // コメントの所有者の確認
        if(!commentService.verifyOwnership(comment, loggedInUser)){
            return "redirect:/posts/" + postId + "?error-notAuthorized";
        }

        //　コメントを削除
        commentService.deleteById(id);
        return "redirect:/posts/" + postId;
    }
    
}
