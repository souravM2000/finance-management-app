package com.kshirsa.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshirsa.utility.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetails {

    @Id
    private String userId;
    @Column(unique = true)
    private String userEmail;
    private String name;
    private String phoneNumber;
    private String countryCode;
    private String country;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @JsonIgnore
    private List<String> loggedInDevices;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonIgnore
    private LocalDateTime createdOn;

    public UserDetails(String userEmail,LocalDateTime createdOn, String deviceId){
        this.userId = IdGenerator.generateId();
        this.userEmail=userEmail;
        this.createdOn=createdOn;
        this.loggedInDevices = List.of(deviceId);
    }

    public UserDetails(String userId){
        this.userId=userId;
    }
}
