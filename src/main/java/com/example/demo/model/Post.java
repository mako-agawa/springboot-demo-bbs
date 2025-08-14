package com.example.demo.model;

import jakarta.persistence.*;   // ← jakart ではなく jakarta
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← アノテーション行の末尾に ; は不要
    private Long id;

    private String title;
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();   // ← 命名揃える
    private LocalDateTime updatedAt = LocalDateTime.now();

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