package com.resttest.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Call {
    private Date date;
    private Integer duration;
    private String numberFrom;
    private String numberTo;
}
