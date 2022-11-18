package com.kideveloper.my_spring_web.cinema;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class District {

    private String district;
    private List<Cinema> cinemas;
}
