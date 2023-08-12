package com.example.service1.domains.perfils.entities;

import com.example.service1.lib.entities.BaseEntities;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity()
@Table(name = "perfils")
@Data()
public class PerfilEntity  extends BaseEntities {

    private Long id;

    private String name;

    private String surname;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDay;

    private String address;
}
