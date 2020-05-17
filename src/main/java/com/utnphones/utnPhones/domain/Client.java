package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value="1")
public class Client extends Person {
    @NotNull
    @OneToMany(mappedBy = "client")
    private List<PhoneLine> phoneLines;

}
