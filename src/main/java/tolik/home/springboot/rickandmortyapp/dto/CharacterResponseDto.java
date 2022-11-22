package tolik.home.springboot.rickandmortyapp.dto;

import lombok.Data;
import tolik.home.springboot.rickandmortyapp.model.Gender;
import tolik.home.springboot.rickandmortyapp.model.Status;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
