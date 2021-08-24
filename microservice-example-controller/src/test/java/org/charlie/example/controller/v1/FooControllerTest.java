package org.charlie.example.controller.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.charlie.example.entities.vo.FooVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebAppConfiguration
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class FooControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenCRUDSuccess() throws Exception {

        // init test data
        List<FooVO> fooVOs = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            fooVOs.add(FooVO.builder().id(i).name("foo" + String.valueOf(i)).build());
        }

        for (FooVO fooVO: fooVOs) {

            // create test
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/foo/")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            // read test
            log.info(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foo/" + fooVO.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString());
        }

        // update test
        for (FooVO fooVO: fooVOs) {

            String newName = fooVO.getName() + "update";
            fooVO.setName(newName);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/foo/")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            // read test
            log.info(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foo/" + fooVO.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$..name").value(newName))
                    .andReturn()
                    .getResponse()
                    .getContentAsString());
        }

        // update test
        for (FooVO fooVO: fooVOs) {

            String newName = fooVO.getName() + "patch";
            fooVO.setName(newName);

            // update test
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/foo/" + fooVO.getId())
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            // read test
            log.info(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foo/" + fooVO.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$..name").value(newName))
                    .andReturn()
                    .getResponse()
                    .getContentAsString());

        }

        // delete test
        for (FooVO fooVO: fooVOs) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/foo/" + fooVO.getId())
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            log.info(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foo")
                    .contentType(MediaType.APPLICATION_JSON))
//                    .content(objectMapper.writeValueAsString(fooVO)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString());
        }


    }

    @Test
    public void whenCreateSuccess() throws Exception {
        FooVO fooVO = FooVO.builder().id(1).name("foo1").build();

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/foo/").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fooVO)))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(10000))
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.debug("返回结果：{}", result);
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        FooVO fooVO = FooVO.builder().id(1).name("foo1").build();
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foo/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fooVO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.debug("返回结果：{}", result);
    }
}
