package com.kideveloper.my_spring_web.model;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class StatementOfChangesInEquityBundle {

        private List<StatementOfChangesInEquity> statementOfChangesInEquitieList;
        private List<String> allColumns;
}
