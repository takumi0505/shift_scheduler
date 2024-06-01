package com.example.shift_scheduler.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // テーブル名を変更
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


}
