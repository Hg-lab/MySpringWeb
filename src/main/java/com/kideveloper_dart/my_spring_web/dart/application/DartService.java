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
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
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
        DocumentationType documentationType = dartDocsRequestDTO.getDocumentationType();

        // 요청한적 있는 documentation은 바로 조회
        if (canGetCells(company, businessYear, documentationType)) {
            List<Cell> cells = getCellsFromDB(company, businessYear, documentationType);
            return tableAssembler.assembleTableByCells(cells);
        }

        List<Cell> cells = getCellsFromAPI(dartDocsRequestDTO, company, documentationType);

        Documentation savedDocumentation = documentationRepository.save(
                Documentation.builder()
                .company(company)
                .businessYear(businessYear)
                .documentationType(documentationType)
                .cells(cells)
                .build());

        cells.forEach(cell -> {
            cell.setDocumentation(savedDocumentation);
            cellRepository.save(cell);
        });

        return tableAssembler.assembleTableByCells(cells);
    }

    private boolean canGetCells(Company company, Integer businessYear, DocumentationType documentationType) {
        return documentationRepository
                .existsByCompanyAndBusinessYearAndDocumentationType(company, businessYear, documentationType);
    }

    private List<Cell> getCellsFromDB(Company company, Integer businessYear, DocumentationType documentationType) {
        Documentation documentation = documentationRepository
                .findByCompanyAndBusinessYearAndDocumentationType(company, businessYear, documentationType);
        return cellRepository.findByDocumentation(documentation);
    }

    private List<Cell> getCellsFromAPI(DartDocsRequestDTO dartDocsRequestDTO, Company company, DocumentationType documentationType) {
        OpenDartRequestDTO openDartRequestDTO = OpenDartRequestDTO.from(dartDocsRequestDTO);
        openDartRequestDTO.setCorpCode(company.getCorpCode());
        List<APIFinStatsDTO> apiFinStatsDTOList = dartAPI.callAPI(openDartRequestDTO);

        return dartDataParser.parse(apiFinStatsDTOList, documentationType);
    }

}
