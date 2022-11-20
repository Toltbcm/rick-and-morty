package tolik.home.springboot.rickandmortyapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tolik.home.springboot.rickandmortyapp.dto.ApiResponseDto;
import tolik.home.springboot.rickandmortyapp.service.HttpClient;

@Log4j2
@RestController
@RequestMapping("/demo")
public class DemoController {
    private final HttpClient httpClient;

    public DemoController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping
    public String runDemo() {
        ApiResponseDto apiResponseDto = httpClient.get(
                "https://rickandmortyapi.com/api/character", ApiResponseDto.class);
        log.info("API response {}", apiResponseDto);
        return "Done!";
    }
}
