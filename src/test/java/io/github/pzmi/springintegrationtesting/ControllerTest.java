package io.github.pzmi.springintegrationtesting;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private Asker asker;

    @Test
    public void shouldAddNameToTheGreeting() throws Exception {
        when(asker.askQuestion(anyString())).thenReturn("Hello");
        mvc.perform(get("/{name}", "andrzeju")
            .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("Hello"));
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