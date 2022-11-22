package tolik.home.springboot.rickandmortyapp.service;

import java.util.List;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacters();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
