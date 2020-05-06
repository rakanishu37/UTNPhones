package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "provinces")
public class Province {
        @Id
        @Basic(optional = false)
        @Column(name = "id_province",unique=true, nullable = false)
        private Integer id;

    @Column(name = "province_name")
    private String name;
}
