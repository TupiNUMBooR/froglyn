package me.keller.froglyn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppTests extends AbstractTest {

    @Autowired
    private Config config;

    @Test
    void contextLoads() {
    }
}
