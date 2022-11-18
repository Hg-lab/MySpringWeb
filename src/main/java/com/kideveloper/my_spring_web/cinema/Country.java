package com.kideveloper.my_spring_web.cinema;

import lombok.Data;

import java.util.List;

@Data
public class Country {

    private String country;
    private List<District> districts;

}
