package tolik.home.springboot.rickandmortyapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tolik.home.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import tolik.home.springboot.rickandmortyapp.dto.external.ApiInfoDto;
import tolik.home.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import tolik.home.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import tolik.home.springboot.rickandmortyapp.model.Gender;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;
import tolik.home.springboot.rickandmortyapp.model.Status;
import tolik.home.springboot.rickandmortyapp.repository.MovieCharacterRepository;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;

    @Mock
    private MovieCharacterRepository movieCharacterRepository;
    @Mock
    private MovieCharacterMapper movieCharacterMapper;

    @Test
    void saveDtoToDbShouldSaveDataToDbOk() {
        ApiCharacterDto rickSanchezDto = new ApiCharacterDto();
        rickSanchezDto.setId(1L);
        rickSanchezDto.setName("Rick Sanchez");
        rickSanchezDto.setGender("Male");
        rickSanchezDto.setStatus("Alive");
        ApiCharacterDto mortySmithDto = new ApiCharacterDto();
        mortySmithDto.setId(2L);
        mortySmithDto.setName("Morty Smith");
        mortySmithDto.setGender("Male");
        mortySmithDto.setStatus("Alive");
        ApiCharacterDto summerSmithDto = new ApiCharacterDto();
        summerSmithDto.setId(3L);
        summerSmithDto.setName("Summer Smith");
        summerSmithDto.setGender("Female");
        summerSmithDto.setStatus("Alive");

        MovieCharacter rickSanchez = new MovieCharacter();
        rickSanchez.setExternalId(1L);
        rickSanchez.setName("Rick Sanchez");
        rickSanchez.setGender(Gender.MALE);
        rickSanchez.setStatus(Status.ALIVE);
        MovieCharacter mortySmith = new MovieCharacter();
        mortySmith.setExternalId(2L);
        mortySmith.setName("Morty Smith");
        mortySmith.setGender(Gender.MALE);
        mortySmith.setStatus(Status.ALIVE);
        MovieCharacter summerSmith = new MovieCharacter();
        summerSmith.setExternalId(3L);
        summerSmith.setName("Summer Smith");
        summerSmith.setGender(Gender.FEMALE);
        summerSmith.setStatus(Status.ALIVE);

        List<MovieCharacter> movieCharactersToSave = List.of(rickSanchez, summerSmith);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(new ApiInfoDto());
        apiResponseDto.setResults(new ApiCharacterDto[]
                {rickSanchezDto, mortySmithDto, summerSmithDto});

        Set<Long> externalIds = new HashSet<>();
        externalIds.add(rickSanchezDto.getId());
        externalIds.add(mortySmithDto.getId());
        externalIds.add(summerSmithDto.getId());

        MovieCharacter rickSanchezWithId = new MovieCharacter();
        rickSanchezWithId.setId(1L);
        rickSanchezWithId.setExternalId(1L);
        rickSanchezWithId.setName("Rick Sanchez");
        rickSanchezWithId.setGender(Gender.MALE);
        rickSanchezWithId.setStatus(Status.ALIVE);
        MovieCharacter summerSmithWithID = new MovieCharacter();
        summerSmithWithID.setId(3L);
        summerSmithWithID.setExternalId(3L);
        summerSmithWithID.setName("Summer Smith");
        summerSmithWithID.setGender(Gender.FEMALE);
        summerSmithWithID.setStatus(Status.ALIVE);
        List<MovieCharacter> expected = List.of(rickSanchezWithId, summerSmithWithID);

        Mockito.when(movieCharacterRepository.findAllByExternalIdIn(externalIds))
                .thenReturn(List.of(mortySmith));
        Mockito.when(movieCharacterRepository.saveAll(movieCharactersToSave))
                .thenReturn(expected);
        Mockito.when(movieCharacterMapper.parseApiCharacterResponseDto(rickSanchezDto))
                .thenReturn(rickSanchez);
        Mockito.when(movieCharacterMapper.parseApiCharacterResponseDto(summerSmithDto))
                .thenReturn(summerSmith);

        List<MovieCharacter> actual = movieCharacterService.saveDtoToDb(apiResponseDto);

        Assertions.assertEquals(expected, actual);
    }
}
