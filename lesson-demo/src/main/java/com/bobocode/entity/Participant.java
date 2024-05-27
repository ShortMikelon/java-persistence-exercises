package com.bobocode.entity;

import com.bobocode.bibernate.annotation.Column;
import com.bobocode.bibernate.annotation.Entity;
import com.bobocode.bibernate.annotation.Id;
import com.bobocode.bibernate.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table("participants")
@Data
@NoArgsConstructor
public class Participant {
    @Id
    private Integer id;
    
    private String firstName;

    private String lastName;

    private String city;

    private String company;

    private String position;

    private Integer yearsOfExperience;

    @Column(value = "created_at")
    private LocalDateTime createdAt;
}