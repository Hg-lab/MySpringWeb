package com.kideveloper_dart.my_spring_web.dart.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.CompanyDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
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

    private final CompanyRepository companyRepository;
    private final DocumentationRepository documentationRepository;

    @Transactional
    public DartDocsResponseDTO getDocs(DartDocsRequestDTO dartDocsRequestDTO){

        OpenDartRequestDTO openDartRequestDTO = OpenDartRequestDTO.from(dartDocsRequestDTO);

        String corpCode = dartDocsRequestDTO.getCorpCode();
        Company company = companyRepository.findCompanyByCorpCode(corpCode);

        List financialStatementsDTOs = new ArrayList<>();
        dartAPI.callAPI(openDartRequestDTO).forEach(apiFinStatsDTO -> {
            financialStatementsDTOs.add(FinancialStatementsDTO.from(apiFinStatsDTO));
        });

        // Company company = ?
        DartDocsResponseDTO dartDocsResponseDTO = DartDocsResponseDTO.builder().build();

        Documentation documentation = new Documentation(company, dartDocsRequestDTO.getBusinessYear(), "SCE");
        documentationRepository.save(documentation);

        return dartDocsResponseDTO;
    }
}
