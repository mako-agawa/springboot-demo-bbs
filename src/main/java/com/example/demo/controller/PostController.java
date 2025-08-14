package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //一覧表示
    @GetMapping
    public String listPost(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }
    // 詳細表示
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findById(id).orElseThrow());
        return "posts/detail";
    }

    // 新規投稿フォーム
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        
        return "posts/new";
    }

    //編集フォーム
    @GetMapping("{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findById(id).orElseThrow());
        return "posts/edit";
    }

    //投稿更新
    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
       Post existingPost =  postService.findById(id).orElseThrow();
       existingPost.setTitle(post.getTitle());
       existingPost.setContent(post.getContent());
       postService.save(existingPost);
       return "redirect:/posts";
    }
    
    

    //新規投稿
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts";

    }
    
    //投稿削除
    @PostMapping("{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
    

}
