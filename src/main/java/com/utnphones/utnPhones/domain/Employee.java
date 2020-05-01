package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee extends Person {
    private String dummy;

}
