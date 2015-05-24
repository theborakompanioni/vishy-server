package org.tbk.vishy.utils.ip;

import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;

/**
 * Created by void on 17.03.15.
 */

@Slf4j
@Builder
public class RemoteAddressExtractor {
    public static RemoteAddressExtractor create() {
        return new RemoteAddressExtractor();
    }

    public static RemoteAddressExtractor withLocalSubstitude(String localSubstitude) {
        return new RemoteAddressExtractor(localSubstitude);
    }

    private final String localSubstitude;

    private RemoteAddressExtractor() {
        this(null);
    }

    private RemoteAddressExtractor(String localSubstitude) {
        this.localSubstitude = localSubstitude;
    }

    public Optional<String> getIpAddress(HttpServletRequest request) {
        return IpUtils.getIpAddress(request).map(ip -> {
            if (localSubstitude != null && InetAddress.getLoopbackAddress().getHostAddress().equals(ip)) {
                return localSubstitude;
            }
            return ip;
        });
    }
}
