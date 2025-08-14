package com.example.demo.model;

import jakarta.persistence.*; // ← jakart ではなく jakarta
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ← アノテーション行の末尾に ; は不要
    private Long id;

    private String title;
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now(); // ← 命名揃える
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist // ← PerPersist ではなく PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now(); // ← now() はメソッド呼び出し
        updatedAt = createdAt; // 作成時は同じ値にしておく
    }

    @PreUpdate // ← PerUpdate ではなく PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}