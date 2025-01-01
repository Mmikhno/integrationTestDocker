package ru.netology.springFirst;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringFirstApplicationTests {
    private static final int PROD_PORT = 8081;
    private static final int DEV_PORT = 8080;
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(DEV_PORT);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(PROD_PORT);
    @Autowired
    TestRestTemplate testRestTemplate;
    @BeforeAll
    public static void setUp(){
        devApp.start();
        prodApp.start();
    }
    private static String getUri(int port){
        return String.format("http://localhost:%d/profile",port);
    }
    @Test
    void shouldReturnStringForProduction(){
        ResponseEntity<String> entity = testRestTemplate.getForEntity(getUri(prodApp.getMappedPort(PROD_PORT)), String.class);
        Assertions.assertEquals("Current profile is production",entity.getBody());
    }
    @Test
    void shouldReturnStringForDev(){
        ResponseEntity<String> entity = testRestTemplate.getForEntity(getUri(devApp.getMappedPort(DEV_PORT)),String.class);
        Assertions.assertEquals("Current profile is dev",entity.getBody());
    }

}
