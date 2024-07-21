package com.example.ecom.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PanierRequest {

    private Long id;
    private double totalPrice;
    private Date date;
    private Long product_id;

    private String user_username;
}
