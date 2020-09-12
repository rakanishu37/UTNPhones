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

    void setCityOrigin(String cityOrigin);
    void setPhoneNumberOrigin(String phoneNumberOrigin);
    void setCityDestiny(String cityDestiny);
    void setPhoneNumberDestiny(String phoneNumberDestiny);
    void setDuration(Integer duration);
    void setTotalPrice(Float totalPrice);
    void setDate(Date date);
}
