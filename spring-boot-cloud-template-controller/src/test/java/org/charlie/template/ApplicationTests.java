package org.charlie.template;

import org.charlie.template.controller.FooController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ApplicationTests {

    @Autowired
    FooController fooController;

    @Test
    public void contextLoads() {
        assertThat(fooController).isNotNull();
    }
}
