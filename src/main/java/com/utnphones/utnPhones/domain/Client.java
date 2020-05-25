package com.utnphones.utnPhones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    //Todo verificar que no haga un stackoverflow debugeando
}
