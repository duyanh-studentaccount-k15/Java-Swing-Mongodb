package com.onlyu.mongodbdesktopapp.work;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coffee {

    private String name;
    private int supId;
    private double price;
    private int sales;
    private double total;

}
