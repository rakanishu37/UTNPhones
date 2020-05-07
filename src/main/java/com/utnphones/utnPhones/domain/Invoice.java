package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {
    @Id
    @Column(name = "id_invoice")
    private Integer id;
//TODO completar atributos
    @OneToMany(mappedBy = "invoice")
    private List<Call> calls;
}
