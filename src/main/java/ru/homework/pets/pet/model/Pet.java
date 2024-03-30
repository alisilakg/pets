package ru.homework.pets.pet.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private int id;
    private String type;
    private String name;
    private int age;
}
