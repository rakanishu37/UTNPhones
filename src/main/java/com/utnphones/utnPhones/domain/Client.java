package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@DiscriminatorValue(value="1")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Client extends Person {
    @NotNull
    @OneToMany(mappedBy = "client")
    private List<PhoneLine> phoneLines;

}
