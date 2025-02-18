package com.vacation.platform.stayfinder.corpuser.service;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CorpUserRequestService {

    StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO, List<MultipartFile> files);

}
