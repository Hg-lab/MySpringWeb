package com.kideveloper_dart.my_spring_web.dart.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.CompanyDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import com.kideveloper_dart.my_spring_web.dart.domain.repository.CompanyRepository;
import com.kideveloper_dart.my_spring_web.dart.domain.repository.DocumentationRepository;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.DartAPI;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.request.OpenDartRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DartService {

    private final DartAPI dartAPI;

    private final DartDataParser dartDataParser;

    private final CompanyRepository companyRepository;
    private final DocumentationRepository documentationRepository;

    @Transactional
    public DartDocsResponseDTO getDocs(DartDocsRequestDTO dartDocsRequestDTO){


        String stockCode = dartDocsRequestDTO.getStockCode();
        Company company = companyRepository.findCompanyByStockCode(stockCode);
        Integer businessYear = dartDocsRequestDTO.getBusinessYear();

        if(!documentationRepository.existsByCompanyAndBusinessYear(company,businessYear)) {

        } else {
            Documentation documentation = new Documentation(company, dartDocsRequestDTO.getBusinessYear(), DocumentationType.BS);
            Documentation savedDocumentation = documentationRepository.save(documentation);

            OpenDartRequestDTO openDartRequestDTO = OpenDartRequestDTO.from(dartDocsRequestDTO);
            openDartRequestDTO.setCorpCode(company.getCorpCode());
            List financialStatementsDTOs = new ArrayList<>();
            dartAPI.callAPI(openDartRequestDTO).forEach(apiFinStatsDTO -> {
                financialStatementsDTOs.add(FinancialStatementsDTO.from(apiFinStatsDTO));
            });
            //////////////////////////////////////////////////
            /*
             * financialStatementsDTOs -> Cells
             * */
            //////////////////////////////////////////////////
            List<Cell> cells = dartDataParser.parse(financialStatementsDTOs);
            cells.forEach(cell -> {
                cell.setDocumentation(savedDocumentation);});
            System.out.println("cells = " + cells);


        }

        DartDocsResponseDTO dartDocsResponseDTO = DartDocsResponseDTO.builder().build();
        return dartDocsResponseDTO;
    }
}
