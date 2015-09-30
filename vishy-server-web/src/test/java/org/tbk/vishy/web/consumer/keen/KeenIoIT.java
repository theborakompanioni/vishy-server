package org.tbk.vishy.web.consumer.keen;

import com.google.common.net.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tbk.vishy.VishyServerConfiguration;
import org.tbk.vishy.client.keenio.KeenIoProperties;
import org.tbk.vishy.client.keenio.KeenOpenMrcClientAdapter;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        VishyServerConfiguration.class, KeenITConfiguration.class})
@WebIntegrationTest("server.port:0")
public class KeenIoIT {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private KeenIoProperties properties;

    @Autowired
    private KeenOpenMrcClientAdapter clientAdapter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void itShouldHaveKeenProperties() throws Exception {
        assertThat("Keen project id not specified.", properties.getProjectId(), not(isEmptyOrNullString()));
        assertThat("Keen write key not specified.", properties.getWriteKey(), not(isEmptyOrNullString()));
        assertThat("Keen read key not specified.", properties.getReadKey(), not(isEmptyOrNullString()));
    }

    @Test
    public void itShouldHaveAnKeenOpenMrcClientAdapter() throws Exception {
        assertThat(clientAdapter, is(notNullValue()));
    }

    @Test
    public void itShouldAcceptValidInitialRequests() throws Exception {
        String requestJson = "{\"type\":\"INITIAL\",\"monitorId\":\"abce50bd-28f7-4eae-8cd2-964377bcb770\",\"initial\":{\"timeStarted\":1435007078201,\"state\":{\"code\":2,\"state\":\"fullyvisible\",\"percentage\":1,\"fullyvisible\":true,\"visible\":true,\"hidden\":false}},\"sessionId\":\"7a4d9892-e091-4b58-bca6-c2dae1fc88e9\",\"viewport\":{\"width\":1920,\"height\":372},\"vishy\":{\"id\":\"42\",\"projectId\":\"myElement\"}}";
        send(requestJson).andExpect(status().isAccepted());

        verify(clientAdapter, times(1)).accept(any());
    }

    private ResultActions send(String json) throws Exception {
        return mockMvc.perform(post("/openmrc/consume")
                .header(HttpHeaders.USER_AGENT, "Firefox")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(json));
    }
}
