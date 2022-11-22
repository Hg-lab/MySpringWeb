package com.kideveloper_dart.my_spring_web.cinema;

import lombok.Data;

import java.util.List;

@Data
public class District {

    private String district;
    private List<Cinema> cinemas;
}
