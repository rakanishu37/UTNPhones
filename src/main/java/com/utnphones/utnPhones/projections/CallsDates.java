package com.utnphones.utnPhones.projections;

import java.util.Date;

public interface CallsDates {
    String getOrigin();
    String getDestiny();
    Float getFare();
    Integer getDuration();
    Float getTotalPrice();
    Date getDate();
}
