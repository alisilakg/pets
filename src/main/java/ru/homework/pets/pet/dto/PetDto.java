package ru.homework.pets.pet.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    private int id;
    private String type;
    private String name;
    private int age;
}
