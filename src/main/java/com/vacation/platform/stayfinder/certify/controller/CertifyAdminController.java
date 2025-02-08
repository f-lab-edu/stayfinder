package com.vacation.platform.stayfinder.certify.controller;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/certify")
@AllArgsConstructor
public class CertifyAdminController {

    private final CertifyService certifyService;

    @DeleteMapping("/delete")
    public StayFinderResponseDTO<?> deleteCertify(@Valid @RequestBody CertifyRequestDto certifyRequestDto) {
        return certifyService.certifyDelete(certifyRequestDto);
    }

}
