package org.tbk.vishy.hystrix;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HystrixVishyOpenMrcHttpRequestServiceTest {

    @Test
    public void it_should_return_a_non_null_value_when_constructed_without_consumers() {
        final OpenMrcHttpRequestMapper mapperMock = mock(OpenMrcHttpRequestMapper.class);
        final HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test"));
        final HystrixVishyOpenMrcHttpRequestService sut = new HystrixVishyOpenMrcHttpRequestService(
                mapperMock, setter, Lists.newArrayList());

        final OpenMrc.Response.Builder builder = sut.processRequest(OpenMrc.Request.newBuilder()
                .buildPartial());

        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void it_should_return_a_non_null_value_when_constructed_with_one_consumer() {
        final OpenMrcHttpRequestMapper mapperMock = mock(OpenMrcHttpRequestMapper.class);

        OpenMrcRequestConsumer consumerSpy = spy(OpenMrcRequestConsumer.class);
        final HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test"));
        final HystrixVishyOpenMrcHttpRequestService sut = new HystrixVishyOpenMrcHttpRequestService(
                mapperMock, setter, Lists.newArrayList(consumerSpy));

        final OpenMrc.Response.Builder builder = sut.processRequest(OpenMrc.Request.newBuilder()
                .buildPartial());

        assertThat(builder, is(notNullValue()));
        verify(consumerSpy, times(1)).accept(any());
    }
}
