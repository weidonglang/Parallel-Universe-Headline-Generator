package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniverseNewsServiceTests {

    private final UniverseNewsService universeNewsService = new UniverseNewsService();

    @Test
    void generatesStableNewsForSameTopic() {
        UniverseNews first = universeNewsService.generate("周一早会");
        UniverseNews second = universeNewsService.generate("周一早会");

        assertEquals(first, second);
        assertEquals("周一早会", first.anchor());
        assertFalse(first.tags().isEmpty());
    }

    @Test
    void fallsBackWhenTopicIsBlank() {
        UniverseNews news = universeNewsService.generate("   ");

        assertEquals("今天的你", news.anchor());
        assertFalse(news.headline().isBlank());
    }

    @Test
    void genericPetDoesNotRepeatSubject() {
        UniverseNews news = universeNewsService.generate("宠物");

        assertTrue(news.headline().contains("这只宠物"));
        assertFalse(news.headline().contains("这只宠物这只宠物"));
    }

    @Test
    void genericObjectUsesObjectTemplate() {
        UniverseNews news = universeNewsService.generate("物件");

        assertTrue(news.headline().contains("这个物件"));
        assertFalse(news.headline().contains("摸鱼艺术家"));
    }

    @Test
    void genericMeetingDoesNotRepeatSubject() {
        UniverseNews news = universeNewsService.generate("会议");

        assertTrue(news.headline().contains("这场会议"));
        assertFalse(news.headline().contains("这场会议这场会议"));
    }

    @Test
    void tagsAreUnique() {
        UniverseNews news = universeNewsService.generate("哈哈");

        assertEquals(news.tags().size(), new HashSet<>(news.tags()).size());
    }
}
