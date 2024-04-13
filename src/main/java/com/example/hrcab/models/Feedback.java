package com.example.hrcab.models;


import jakarta.persistence.*;
import lombok.*;


//Модель отклика
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vacancy vacancy;

    @ManyToOne
    private Users users;

    private String status;
}
