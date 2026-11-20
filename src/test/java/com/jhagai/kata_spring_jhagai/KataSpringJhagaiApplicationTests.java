package com.jhagai.kata_spring_jhagai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class KataSpringJhagaiApplicationTests {

	@Test
	void contextLoads() {
	}

}
