package com.kideveloper.my_spring_web.dart.dto;


import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class DartResponseDTO {

    private DocTermList docTermList;
    private BizState bizState;
    private IncomeState incomeState;
    private CommonIncomeState commonIncomeState;
    private CashFlow cashFlow;
    private StateChangeEquity stateChangeEquity;
}
