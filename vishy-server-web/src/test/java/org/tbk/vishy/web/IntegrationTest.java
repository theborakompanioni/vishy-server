package org.tbk.vishy.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tbk.openmrc.mother.InitialRequests;
import org.tbk.openmrc.mother.StatusRequests;
import org.tbk.openmrc.mother.protobuf.SummaryRequests;
import org.tbk.vishy.VishyOpenMrcConfig;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {VishyOpenMrcConfig.class})
@WebAppConfiguration
public class IntegrationTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHelloGet() throws Exception {
        mockMvc.perform(get("/openmrc/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testHelloPost() throws Exception {
        mockMvc.perform(post("/openmrc/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithoutRequestBody() throws Exception {
        mockMvc.perform(post("/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWithEmptyRequestBody() throws Throwable {
        send("").andExpect(status().isBadRequest());
        send("{}").andExpect(status().isBadRequest());
    }

    @Test
    public void testWithRequestBodyWithoutTypeInformation() throws Throwable {
        send("{ test: \"Test\" }").andExpect(status().isBadRequest());
    }

    @Test
    public void testWithInitialEventRequestBody() throws Exception {
        String requestJson = InitialRequests.json().standardInitialRequest();
        send(requestJson).andExpect(status().isAccepted());

        String requestWithExtensionJson = InitialRequests.json().initialRequestWithBrowserExtension();
        send(requestWithExtensionJson).andExpect(status().isAccepted());
    }

    @Test
    public void testWithInitialEventWithBrowserExtensionRequestBody() throws Exception {
        String requestWithExtensionJson = InitialRequests.json().initialRequestWithBrowserExtension();
        send(requestWithExtensionJson).andExpect(status().isAccepted());
    }

    @Test
    public void testWithStatusEventRequestBody() throws Exception {
        String requestJson = StatusRequests.json().standardStatusRequest();
        send(requestJson).andExpect(status().isAccepted());
    }

    @Test
    public void testWithSummaryEventRequestBody() throws Exception {
        String requestJson = SummaryRequests.json().standardSummaryRequest();
        send(requestJson).andExpect(status().isAccepted());
    }

    private ResultActions send(String json) throws Exception {
        return mockMvc.perform(post("/openmrc/consume")
                .header("User-Agent", "Firefox")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(json));
    }
}
