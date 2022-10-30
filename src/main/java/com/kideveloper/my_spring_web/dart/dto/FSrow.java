package com.kideveloper.my_spring_web.dart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FSrow {

    private final String order;
    private final String accountName;
    private final String thisTermAmount;
    private final String fromTermAmount;
    private final String beforeTermAmount;
}
