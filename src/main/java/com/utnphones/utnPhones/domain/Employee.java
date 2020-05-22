package com.utnphones.utnPhones.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value="2")
public class Employee extends Person {

}
