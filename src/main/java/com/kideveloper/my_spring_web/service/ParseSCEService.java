package com.kideveloper.my_spring_web.service;

import com.kideveloper.my_spring_web.comparator.StatementOfChangesInEquityComparator;
import com.kideveloper.my_spring_web.model.FnlttSinglAcnt;
import com.kideveloper.my_spring_web.model.StatementOfChangesInEquity;
import com.kideveloper.my_spring_web.model.StatementOfChangesInEquityBundle;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParseSCEService {

    static ArrayList<String> allColumns;

    public Object parseSCE(Map<String,FnlttSinglAcnt> result2, Map<String,Integer> numOfSjDiv) {

        Map<String, StatementOfChangesInEquityBundle> parseResult = new LinkedHashMap<String,StatementOfChangesInEquityBundle>();

        String orderInMap = "1";
        List<StatementOfChangesInEquity> statementOfChangesInEquities = new ArrayList<>();
        List<StatementOfChangesInEquityBundle> statementOfChangesInEquityBundles = new ArrayList<>();

        allColumns = (ArrayList<String>) parseSCEAllColumns(result2);
        allColumns.add("지배기업 소유주지분");
        allColumns.add("비지배지분");
        allColumns.add("연결재무제표");

        int ord = 1;
        for(String key : result2.keySet()) {

            if(result2.get(key).getSj_div().equals("SCE")) {
                // Generate SCE instance, will put it in bundle to return all in once.
                StatementOfChangesInEquity statementOfChangesInEquity = new StatementOfChangesInEquity(
                        result2.get(key).getBsns_year(),
                        result2.get(key).getOrd(),
                        result2.get(key).getAccount_id(),
                        result2.get(key).getAccount_nm(),
                        parseSCEColumn(result2.get(key).getAccount_detail()),
                        result2.get(key).getBfefrmtrm_amount(),
                        result2.get(key).getFrmtrm_amount(),
                        result2.get(key).getThstrm_amount()
                );

                // If 'ord' are same, it will be in one same row.
                // Make it together depending on ord.
                if(result2.get(key).getOrd().equals(Integer.toString(ord))) {
                    statementOfChangesInEquities.add(statementOfChangesInEquity);
                } else {
                    // Generate SCE Bundle instance, will put it in return map
                    StatementOfChangesInEquityBundle statementOfChangesInEquityBundle = new StatementOfChangesInEquityBundle();
                    // Write back to forward in the table.
                    Collections.reverse(statementOfChangesInEquities);
                    statementOfChangesInEquityBundle.setStatementOfChangesInEquitieList(statementOfChangesInEquities);
                    statementOfChangesInEquityBundles.add(statementOfChangesInEquityBundle);

                    statementOfChangesInEquities = new ArrayList<>();
                    statementOfChangesInEquities.add(statementOfChangesInEquity);

                    ord++;
                }

            }
        }

//        System.out.println(statementOfChangesInEquityBundles.toString());
//        organizeBundles(statementOfChangesInEquityBundles);
        return organizeBundles(statementOfChangesInEquityBundles);
    }

    public Object organizeBundles(List<StatementOfChangesInEquityBundle> statementOfChangesInEquityBundles) {
        for(StatementOfChangesInEquityBundle sBundle : statementOfChangesInEquityBundles) {
//            System.out.println(sBundle.getStatementOfChangesInEquitieList().size());
            if(sBundle.getStatementOfChangesInEquitieList().size()==allColumns.size()) {
                continue;
            } else {
                List<String> thisRowColumns = new ArrayList<>();
                for(StatementOfChangesInEquity sInstance : sBundle.getStatementOfChangesInEquitieList()) {
                    thisRowColumns.add(sInstance.getAccount_detail());
                }
                for(String column : allColumns) {
                    if(thisRowColumns.indexOf(column) == -1) {
                        StatementOfChangesInEquity emptySCE = new StatementOfChangesInEquity(
                                "",
                                "",
                                "",
                                "",
                                column,
                                "",
                                "",
                                ""
                        );
                        List<StatementOfChangesInEquity> sCEListAddedEmptyColumn = new ArrayList<>();
                        sCEListAddedEmptyColumn = sBundle.getStatementOfChangesInEquitieList();
                        sCEListAddedEmptyColumn.add(emptySCE);
                        Collections.sort(sCEListAddedEmptyColumn, new StatementOfChangesInEquityComparator(allColumns));
                        sBundle.setStatementOfChangesInEquitieList(sCEListAddedEmptyColumn);

                    }
                }
            }




        }
        return statementOfChangesInEquityBundles;
    }

    public Object parseSCEAllColumns(Map<String,FnlttSinglAcnt> result2) {
        List<String> columns = new ArrayList<String>();

        for(String key : result2.keySet()) {
            if (result2.get(key).getSj_div().equals("SCE")) {
                // Define columns which are used in templates.
                // Only ord=1's account_detail is needed.

                if (result2.get(key).getOrd().equals("1")) {
                    String col = result2.get(key).getAccount_detail();
                    col = col.replaceAll("[\\[\\w\\]]", "");
                    col = col.replaceAll("\\s\\|", "\\|");

                    List<String> colList = new ArrayList<String>();
                    colList = Arrays.asList(col.split("\\|"));

                    // Only child colums are put in list. Parent Column head is not needed.
                    if (colList.size() > 2) {
                        col = colList.get(colList.size() - 1).trim();
                        columns.add(col);
                    }
                }
            }
        }
        Collections.reverse(columns);
        return columns;
    }

    public String parseSCEColumn(String account_detail) {
        String col = account_detail;
        col = col.replaceAll("[\\[\\w\\]]", "");
        col = col.replaceAll("\\s\\|", "\\|");
        List<String> colList = new ArrayList<String>();
        colList = Arrays.asList(col.split("\\|"));

        // Only child colums are put in list. Parent Column head is not needed.
        if (colList.size() >= 2) {
            col = colList.get(colList.size() - 1).trim();
        }

        col = col.replaceAll("\\,","").trim();

        return col;

    }

}
