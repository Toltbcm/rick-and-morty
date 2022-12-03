package tolik.home.springboot.rickandmortyapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "movie_characters")
public class MovieCharacter {
    @Id
    @GeneratedValue(generator = "movie_characters_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movie_characters_id_seq",
            sequenceName = "movie_characters_id_seq",
            allocationSize = 1)
    private Long id;
    private Long externalId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
