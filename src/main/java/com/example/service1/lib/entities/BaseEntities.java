// file: src/main/com/example/service1/lib/entities/BaseEntities.java
package com.example.service1.lib.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table
@Data
public abstract class BaseEntities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE )
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt = new Date();
    
}
