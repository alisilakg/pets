package ru.homework.pets.owner.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerDto {
    private String name;
    private List<Integer> petsIds;
}
