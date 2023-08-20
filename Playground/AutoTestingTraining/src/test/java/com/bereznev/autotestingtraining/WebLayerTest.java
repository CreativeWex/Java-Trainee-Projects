package com.bereznev.autotestingtraining;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOk() throws Exception {
        mockMvc.perform(get("/health/check"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("OK")));
    }

    @Test
    public void shouldReturnKo() throws Exception {
        mockMvc.perform(get("/health/checka"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
