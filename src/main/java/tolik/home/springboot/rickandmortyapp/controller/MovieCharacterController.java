package tolik.home.springboot.rickandmortyapp.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Get random character from.")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = characterService.getRandomCharacters();
        return characterMapper.toResponseDto(character);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "Get characters by part of the name.")
    public List<CharacterResponseDto> findByName(
            @ApiParam(value = "Rick and rick not the same.")
            @RequestParam("name") String namePart) {
        return characterService.findAllByNameContains(namePart).stream()
                .map(characterMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
