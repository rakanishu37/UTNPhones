package com.utnphones.utnPhones.projections;

import java.util.Date;

public interface CallsDates {
    String getPhoneLineFrom();
    String getPhoneLineTo();
    Float getFare();
    Integer getDuration();
    Float getTotalPrice();
    Date getDate();
}
