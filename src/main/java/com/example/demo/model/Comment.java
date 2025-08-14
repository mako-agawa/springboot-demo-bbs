package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @PrePersist                      // ← PerPersist ではなく PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();  // ← now() はメソッド呼び出し
        updatedAt = createdAt;            // 作成時は同じ値にしておく
    }

    @PreUpdate                       // ← PerUpdate ではなく PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
