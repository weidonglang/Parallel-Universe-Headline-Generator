package com.example.demo;

import java.util.List;

public record UniverseNews(
        String anchor,
        String headline,
        String scoop,
        String quote,
        String action,
        List<String> tags
) {
}
