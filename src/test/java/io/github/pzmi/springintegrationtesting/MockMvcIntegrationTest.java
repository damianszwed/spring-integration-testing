package io.github.pzmi.springintegrationtesting;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockMvcIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldAddNameToTheGreeting() throws Exception {
        mvc.perform(get("/{name}", "andrzeju")
            .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("andrzeju, jak Ci na imie"));
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
