package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.corp.corperation.dto.CorpUserRequestDTO;
import com.vacation.platform.corp.corperation.entity.BusinessLicenseFile;
import com.vacation.platform.corp.corperation.entity.CorporationRequest;
import com.vacation.platform.corp.corperation.entity.room.RequestStatus;
import com.vacation.platform.corp.corperation.repository.BusinessLicenseFileRepository;
import com.vacation.platform.corp.corperation.repository.CorpUserRequestRepository;
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
public class CorporationDBService {

    private final CorpUserRequestRepository corpUserRequestRepository;

    private final BusinessLicenseFileRepository businessLicenseFileRepository;

    @Transactional
    protected void corpUserRequestSave(CorpUserRequestDTO corpUserRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();

        CorporationRequest corporationRequest = modelMapper.map(corpUserRequestDTO, CorporationRequest.class);

        corporationRequest.setBusinessCategory(corpUserRequestDTO.getBusinessCategory());
        corporationRequest.setStatus(RequestStatus.PENDING);

        corpUserRequestRepository.saveAndFlush(corporationRequest);
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
