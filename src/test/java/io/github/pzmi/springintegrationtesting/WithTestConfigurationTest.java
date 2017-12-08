package io.github.pzmi.springintegrationtesting;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class WithTestConfigurationTest {
    @Autowired
    private MockMvc mvc;

    @TestConfiguration
    static class AskerTestConfiguration {
        @Bean
        Asker greeter() {
            return new Asker() {
                @Override
                public String askQuestion(String name) {
                    return "doh!";
                }
            };
        }
    }

    @Test
    public void shouldAddNameToTheGreeting() throws Exception {
        mvc.perform(get("/{name}", "andrzeju")
            .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("doh!"));
    }

    @Test
    public void shouldReturnNotFoundWhenNotRequestingRootPath() throws Exception {
        mvc.perform(get("/aaa/bbb/ccc/ddd")
            .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isNotFound());
    }

    @Test
    @Ignore
    public void shouldFail() throws Exception {
        mvc.perform(get("/aaa/bbb/ccc/ddd")
            .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isUnauthorized());
    }
}