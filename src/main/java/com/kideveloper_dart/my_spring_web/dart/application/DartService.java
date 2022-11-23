package com.kideveloper_dart.my_spring_web.dart.application;


import com.kideveloper_dart.my_spring_web.dart.application.dto.request.CompanyDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.DartAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DartService {

    private final DartAPI dartAPI;

    @Transactional
    public DartDocsResponseDTO getDocs(DartDocsRequestDTO dartDocsRequestDTO) {

        CompanyDTO companyDTO = CompanyDTO.builder().build();
        dartAPI.callAPI(companyDTO);

        // Company company = ?
        DartDocsResponseDTO dartDocsResponseDTO = DartDocsResponseDTO.builder().build();
        return dartDocsResponseDTO;
    }
}
