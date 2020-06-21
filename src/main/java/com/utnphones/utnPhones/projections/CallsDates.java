package com.utnphones.utnPhones.projections;

import java.util.Date;

public interface CallsDates {
    String getCityOrigin();
    String getPhoneNumberOrigin();
    String getCityDestiny();
    String getPhoneNumberDestiny();
    Integer getDuration();
    Float getTotalPrice();
    Date getDate();

}
