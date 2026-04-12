package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniverseNewsController {

    private final UniverseNewsService universeNewsService;

    public UniverseNewsController(UniverseNewsService universeNewsService) {
        this.universeNewsService = universeNewsService;
    }

    @GetMapping("/api/universe-news")
    public UniverseNews universeNews(@RequestParam(required = false) String topic) {
        return universeNewsService.generate(topic);
    }
}
