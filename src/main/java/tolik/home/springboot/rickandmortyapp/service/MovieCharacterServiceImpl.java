package tolik.home.springboot.rickandmortyapp.service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tolik.home.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import tolik.home.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import tolik.home.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;
import tolik.home.springboot.rickandmortyapp.repository.MovieCharacterRepository;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository repository;
    private final MovieCharacterMapper mapper;
    @Value("${characters.synchronize.url}")
    private String charactersUrl;

    public MovieCharacterServiceImpl(HttpClient httpClient,
            MovieCharacterRepository movieCharacterRepository,
            MovieCharacterMapper movieCharacterMapper) {
        this.httpClient = httpClient;
        this.repository = movieCharacterRepository;
        this.mapper = movieCharacterMapper;
    }

    @PostConstruct
    @Scheduled(cron = "${cronjob.synchronize.characters}")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient.get(charactersUrl, ApiResponseDto.class);
        saveDtoToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtoToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacters() {
        long count = repository.count();
        long randomId = (long) (Math.random() * count) + 1;
        return repository.getReferenceById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return repository.findAllByNameContains(namePart);
    }

    public List<MovieCharacter> saveDtoToDb(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();
        List<MovieCharacter> existingCharacters = repository.findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> mapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());
        return repository.saveAll(charactersToSave);
    }
}
