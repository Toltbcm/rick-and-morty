package tolik.home.springboot.rickandmortyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
}
