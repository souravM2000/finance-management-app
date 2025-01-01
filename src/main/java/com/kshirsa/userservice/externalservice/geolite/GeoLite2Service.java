package com.kshirsa.userservice.externalservice.geolite;

import com.kshirsa.userservice.UserConstants;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.InetAddress;

@Service
@Slf4j
public class GeoLite2Service {

    public LocationFromIpResponse getClientLocation(HttpServletRequest request) {
        String ip = request.getHeader(UserConstants.X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        } else {
            // In case of multiple proxies, the first one is the real client IP
            ip = ip.split(",")[0];
        }
        return getLocation(ip);
    }

    public LocationFromIpResponse getLocation(String ip) {
        try {
            ClassPathResource resource = new ClassPathResource("geolite2/GeoLite2-City.mmdb");
            InputStream databaseStream = resource.getInputStream();
            DatabaseReader dbReader = new DatabaseReader.Builder(databaseStream).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);
            return new LocationFromIpResponse((response.getCity().getName() + ", " + response.getCountry().getName()),ip);

        } catch (Exception e) {
            log.error("Error while fetching location from IP: {}", ip);
            log.error(e.getMessage(), e);
            return new LocationFromIpResponse("Unknown", ip);
        }
    }
}