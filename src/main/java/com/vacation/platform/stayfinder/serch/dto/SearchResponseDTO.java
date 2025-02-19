package com.vacation.platform.stayfinder.serch.dto;

import com.vacation.platform.stayfinder.corpuser.entity.BusinessCategory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchResponseDTO {
    private String businessTitle;
    private BusinessCategory businessCategory;
    private String businessAddress;
}
