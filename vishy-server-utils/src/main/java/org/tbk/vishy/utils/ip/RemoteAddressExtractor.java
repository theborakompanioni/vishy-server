package org.tbk.vishy.utils.ip;

import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;

@Slf4j
@Builder
public class RemoteAddressExtractor {
    public static RemoteAddressExtractor create() {
        return new RemoteAddressExtractor();
    }

    public static RemoteAddressExtractor withLocalSubstitute(String localSubstitute) {
        return new RemoteAddressExtractor(localSubstitute);
    }

    private final String localSubstitute;

    private RemoteAddressExtractor() {
        this(null);
    }

    private RemoteAddressExtractor(String localSubstitute) {
        this.localSubstitute = localSubstitute;
    }

    public Optional<String> getIpAddress(HttpServletRequest request) {
        return IpUtils.getIpAddress(request).map(ip -> {
            if (localSubstitute != null && InetAddress.getLoopbackAddress().getHostAddress().equals(ip)) {
                return localSubstitute;
            }
            return ip;
        });
    }
}
