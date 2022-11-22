package tolik.home.springboot.rickandmortyapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tolik.home.springboot.rickandmortyapp.dto.CharacterResponseDto;
import tolik.home.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;
import tolik.home.springboot.rickandmortyapp.service.MovieCharacterService;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService characterService;
    private final MovieCharacterMapper characterMapper;

    public MovieCharacterController(MovieCharacterService characterService,
            MovieCharacterMapper characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    @GetMapping("/random")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = characterService.getRandomCharacters();
        return characterMapper.toResponseDto(character);
    }

    @GetMapping("/by-name")
    public List<CharacterResponseDto> findByName(@RequestParam("name") String namePart) {
        return characterService.findAllByNameContains(namePart).stream()
                .map(characterMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
