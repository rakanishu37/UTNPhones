package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "line_types")
public class LineType {
    @Id
    @Column(name = "id_line_type")
    private Integer id;

    @NotNull
    @Column(name = "type_name")
    private String typeName;
}
