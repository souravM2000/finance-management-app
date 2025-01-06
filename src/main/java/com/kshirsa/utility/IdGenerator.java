package com.kshirsa.utility;

import io.jsonwebtoken.io.Encoders;

import java.net.Inet4Address;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    public static String generateId()  {
        String machineId;
        try {
            machineId = String.valueOf(Inet4Address.getLocalHost().getAddress()[3]);
        } catch (Exception e) {
            machineId = "c";
        }
        return ( UUID.randomUUID().toString().substring(1,6) + machineId +
                Encoders.BASE64URL.encode(String.valueOf(Instant.now().toEpochMilli()*Math.random()).getBytes()).substring(3,8) +
                 new Random().nextInt(101,9999) );
    }
}
