package com.kideveloper_dart.my_spring_web.dart.application;


import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import com.kideveloper_dart.my_spring_web.dart.domain.repository.CellRepository;
import com.kideveloper_dart.my_spring_web.dart.domain.repository.CompanyRepository;
import com.kideveloper_dart.my_spring_web.dart.domain.repository.DocumentationRepository;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.DartAPI;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.request.OpenDartRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DartService {

    private final DartAPI dartAPI;

    private final DartDataParser dartDataParser;

    private final CompanyRepository companyRepository;
    private final DocumentationRepository documentationRepository;
    private final CellRepository cellRepository;

    @Transactional
    public DartDocsResponseDTO getDocs(DartDocsRequestDTO dartDocsRequestDTO){


        String stockCode = dartDocsRequestDTO.getStockCode();
        Company company = companyRepository.findCompanyByStockCode(stockCode);
        Integer businessYear = dartDocsRequestDTO.getBusinessYear();

        DartDocsResponseDTO dartDocsResponseDTO;

        // 요청한적 있는 documentation은 바로 조회
        if(!documentationRepository.existsByCompanyAndBusinessYear(company,businessYear)) {

        } else {
            // API 요청에 따른 documentation 객체 영속화
            Documentation documentation = new Documentation(company, dartDocsRequestDTO.getBusinessYear(), DocumentationType.BS);
            Documentation savedDocumentation = documentationRepository.save(documentation);

            OpenDartRequestDTO openDartRequestDTO = OpenDartRequestDTO.from(dartDocsRequestDTO);
            openDartRequestDTO.setCorpCode(company.getCorpCode());
            List financialStatementsDTOs = new ArrayList<>();
            dartAPI.callAPI(openDartRequestDTO).forEach(apiFinStatsDTO -> {
                financialStatementsDTOs.add(FinancialStatementsDTO.from(apiFinStatsDTO));
            });

            List<Cell>  cells = dartDataParser.parse(financialStatementsDTOs);

            for (int idx = 0; idx < cells.size(); idx++) {
                Cell cell = cells.get(idx);
                cell.setDocumentation(savedDocumentation);
                Cell savedCell = cellRepository.save(cell);
                cells.set(idx, savedCell);
            }

        }

        dartDocsResponseDTO = DartDocsResponseDTO.builder().build();
        return dartDocsResponseDTO;
    }
}
