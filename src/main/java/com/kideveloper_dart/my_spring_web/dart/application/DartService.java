package com.kideveloper_dart.my_spring_web.dart.application;


import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.TableAssembler;
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

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DartService {

    private final DartAPI dartAPI;
    private final DartDataParser dartDataParser;
    private final TableAssembler tableAssembler;

    private final CompanyRepository companyRepository;
    private final DocumentationRepository documentationRepository;
    private final CellRepository cellRepository;

    @Transactional
    public DartDocsResponseDTO getDocs(DartDocsRequestDTO dartDocsRequestDTO){

        Company company = companyRepository.findCompanyByStockCode(dartDocsRequestDTO.getStockCode());
        Integer businessYear = dartDocsRequestDTO.getBusinessYear();

        // 요청한적 있는 documentation은 바로 조회
        if (documentationRepository
                .existsByCompanyAndBusinessYear(company, businessYear)) {
            Documentation documentation = documentationRepository
                    .findByCompanyAndAndBusinessYear(company, businessYear);
            List<Cell> cells = cellRepository.findByDocumentation(documentation);
            return tableAssembler.assembleTableByCells(cells);
        }

        OpenDartRequestDTO openDartRequestDTO = OpenDartRequestDTO.from(dartDocsRequestDTO);
        openDartRequestDTO.setCorpCode(company.getCorpCode());
        List APIFinStatsDTOList = dartAPI.callAPI(openDartRequestDTO);

        List<Cell> cells = dartDataParser.parse(APIFinStatsDTOList);

        Documentation documentation = Documentation.builder()
                .company(company)
                .businessYear(businessYear)
                .documentationType(DocumentationType.BS)
                .cells(cells)
                .build();

        Documentation savedDocumentation = documentationRepository.save(documentation);

        cells.stream().forEach(cell -> {
            cell.setDocumentation(savedDocumentation);
            cellRepository.save(cell);
        });

        return tableAssembler.assembleTableByCells(cells);
    }

}
