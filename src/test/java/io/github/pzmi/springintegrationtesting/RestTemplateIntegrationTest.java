package io.github.pzmi.springintegrationtesting;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; // does not clash with your application configured RestTemplate

    @Test
    public void shouldAddNameToTheGreeting() throws Exception {
        assertThat(
            restTemplate
                .getForObject("http://localhost:" + port + "/andrzeju", String.class))
            .contains("andrzeju, jak Ci na imie");
    }

    @Test
    public void shouldReturnNotFoundWhenNotRequestingRootPath() throws Exception {
        ResponseEntity<String> response = restTemplate
            .getForEntity("http://localhost:" + port + "/aaa/bbb/ccc/ddd", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Ignore
    public void invalidAssumption() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/aaa/bbb/ccc/ddd", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
