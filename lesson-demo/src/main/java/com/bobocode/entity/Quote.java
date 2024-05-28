package com.bobocode.entity;

import com.bobocode.bibernate.annotation.Column;
import com.bobocode.bibernate.annotation.Entity;
import com.bobocode.bibernate.annotation.Id;
import com.bobocode.bibernate.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table("quotes")
@Data
@NoArgsConstructor
public class Quote {
    @Id
    private Integer id;

    private String body;

    private String author;
    
    private LocalDateTime createdAt;
}