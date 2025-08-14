package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    // 一覧表示
    @GetMapping
    public String listPost(Model model, @AuthenticationPrincipal UserDetails user) {
        // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        model.addAttribute("posts", postService.findAll());
        model.addAttribute("loginName", user != null ? user.getUsername() : "ゲスト");
        model.addAttribute("loggedInUserId", loggedInUser.getId());
        return "posts/list";
    }

    // 詳細表示
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        
        model.addAttribute("post", postService.findById(id).orElseThrow());
        model.addAttribute("comments", commentService.findByPostId(id));
        model.addAttribute("loggedInUserId", loggedInUser.getId());
        return "posts/detail";
    }

    // 新規投稿フォーム
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());

        return "posts/new";
    }

    // 編集フォーム
    @GetMapping("{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
          // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        Post post = postService.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        // 投稿の所有者の確認
        if(!postService.verifyOwnership(post, loggedInUser)){
            return "redirect:/posts?error-notAuthorized";
        }

        model.addAttribute("post", post);
        return "posts/edit";
    }

    // 投稿更新
    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
        // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();
        
        Post existingPost = postService.findById(id).orElseThrow();


        // 投稿の所有者の確認
        if(!postService.verifyOwnership(existingPost, loggedInUser)){
            return "redirect:/posts?error-notAuthorized";
        }




        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        postService.save(existingPost);
        return "redirect:/posts";
    }

    // 新規投稿
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        User user = userService.getCurrentUser();

        post.setUser(user);
        postService.save(post);
        return "redirect:/posts";

    }

    // 投稿削除
    @PostMapping("{id}/delete")
    public String deletePost(@PathVariable Long id) {
        // 現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        Post post = postService.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        // 投稿の所有者の確認
        if(!postService.verifyOwnership(post, loggedInUser)){
            return "redirect:/posts?error-notAuthorized";
        }

        postService.deleteById(id);
        return "redirect:/posts";
    }

    

}
