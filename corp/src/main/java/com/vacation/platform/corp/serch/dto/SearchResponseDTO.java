package com.vacation.platform.corp.serch.dto;

import com.vacation.platform.corp.corpuser.entity.BusinessCategory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchResponseDTO {
    private String businessTitle;
    private BusinessCategory businessCategory;
    private String businessAddress;
}
