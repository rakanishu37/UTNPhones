package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
@SuperBuilder
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value="2")
public class Employee extends Person {

}
