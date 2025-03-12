package com.vacation.platform.corp.corpuser.service.impl;

import com.vacation.platform.corp.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.corp.corpuser.entity.BusinessCategory;
import com.vacation.platform.corp.corpuser.entity.BusinessLicenseFile;
import com.vacation.platform.corp.corpuser.entity.CorpUserRequest;
import com.vacation.platform.corp.corpuser.entity.RequestStatus;
import com.vacation.platform.corp.corpuser.repository.BusinessLicenseFileRepository;
import com.vacation.platform.corp.corpuser.repository.CorpUserRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserDBService {

    private final CorpUserRequestRepository corpUserRequestRepository;

    private final BusinessLicenseFileRepository businessLicenseFileRepository;

    @Transactional
    protected void corpUserRequestSave(CorpUserRequestDTO corpUserRequestDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        CorpUserRequest corpUserRequest = modelMapper.map(corpUserRequestDTO, CorpUserRequest.class);

        corpUserRequest.setBusinessCategory(BusinessCategory.getByDesc(corpUserRequestDTO.getBusinessCategory()));
        corpUserRequest.setStatus(RequestStatus.PENDING);

        corpUserRequestRepository.saveAndFlush(corpUserRequest);
    }

    @Transactional
    protected List<String> corpUserBusinessLicenseFileSave(@NotNull List<MultipartFile> files, Long id) throws IOException {
        return files.stream().map(file -> {
            try {
                return fileSave(file, id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    protected String fileSave(MultipartFile file, Long id) throws IOException {
        if(file == null){
            throw new IOException();
        }

        String uploadDirectory = "src/resources/static/uploads/";

        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            throw new IOException();
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException();
        }

        String extension = fileName.substring(fileName.lastIndexOf("."));

        String saveFileName = UUID.randomUUID() + extension;

        Path path = Paths.get(uploadDirectory + saveFileName);

        Files.write(path, file.getBytes());

        BusinessLicenseFile businessLicenseFile = new BusinessLicenseFile();
        businessLicenseFile.setCorpUserRequestId(id);
        businessLicenseFile.setFileName(fileName);
        businessLicenseFile.setFileType(extension);
        businessLicenseFile.setFileSize(file.getSize());

        businessLicenseFileRepository.saveAndFlush(businessLicenseFile);

        return saveFileName;
    }

}
