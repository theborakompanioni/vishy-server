package org.tbk.vishy.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tbk.vishy.VishyServerConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {VishyServerConfiguration.class},
        webEnvironment = RANDOM_PORT
)
public class AnalyticsScriptIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void itShouldFetchAnalyticsScript() throws Exception {
        mockMvc.perform(get("/static/vishy-analytics/dist/vishy-analytics.min.js")
                .accept("application/javascript"))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnErrorOnFetchDemoScriptWithMissingElementId() throws Exception {
        mockMvc.perform(get("/openmrc/vishy/demo.js")
                .param("elementId", "")
                .accept("application/javascript"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/openmrc/vishy/demo.js")
                .param("elementId", "my-element")
                .accept("application/javascript"))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldFetchDemoScript() throws Exception {
        mockMvc.perform(get("/openmrc/vishy/demo.js")
                .param("elementId", "my-element")
                .accept("application/javascript"))
                .andExpect(status().isOk());
    }

}
