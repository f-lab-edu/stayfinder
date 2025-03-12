package com.vacation.platform.corp.corpuser.service;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corpuser.dto.CorpUserRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CorpUserRequestService {

    StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO, List<MultipartFile> files);

}
