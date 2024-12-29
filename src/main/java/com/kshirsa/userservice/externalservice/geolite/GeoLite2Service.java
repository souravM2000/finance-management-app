package com.kshirsa.userservice.externalservice.geolite;

import com.kshirsa.userservice.UserConstants;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.InetAddress;

@Service
@Slf4j
public class GeoLite2Service {

    String dbPath = "src/main/resources/geolite2/GeoLite2-City.mmdb";

    public String getClientLocation(HttpServletRequest request) {
        String ip = request.getHeader(UserConstants.X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        } else {
            // In case of multiple proxies, the first one is the real client IP
            ip = ip.split(",")[0];
        }
        return getLocation(ip);
    }

    public String getLocation(String ip) {
        try {
            File database = new File(dbPath);
            DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);
            return (response.getCity().getName() + ", " + response.getCountry().getName());

        } catch (Exception e) {
            log.error("Error while fetching location from IP: {}", ip);
            log.error(e.getMessage(), e);
            return "Unknown";
        }
    }
}
