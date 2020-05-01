package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client extends Person {
    @NotNull
    //@OneToMany(mappedBy = "")
    private List<PhoneLine> phoneLines;

    @Override
    public String toString() {
        return  super.toString() +  "Client{" +
                "phoneLines=" + phoneLines +
                '}';
    }
}
