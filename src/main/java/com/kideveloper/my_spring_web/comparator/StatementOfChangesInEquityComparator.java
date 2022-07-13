package com.kideveloper.my_spring_web.comparator;

import com.kideveloper.my_spring_web.model.StatementOfChangesInEquity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatementOfChangesInEquityComparator implements Comparator<StatementOfChangesInEquity> {

    // When compare SCE in column, this reference is column header on template.
    public ArrayList<String> reference;

    public StatementOfChangesInEquityComparator(ArrayList<String> reference) {
        this.reference = reference;
    }

    @Override
    public int compare(StatementOfChangesInEquity o1, StatementOfChangesInEquity o2) {
        if(reference.indexOf(o1.getAccount_detail()) > reference.indexOf(o2.getAccount_detail())) {
            return 1;
        } else if (reference.indexOf(o1.getAccount_detail()) < reference.indexOf(o2.getAccount_detail())) {
            return -1;
        }
         return 0;
    }
}
