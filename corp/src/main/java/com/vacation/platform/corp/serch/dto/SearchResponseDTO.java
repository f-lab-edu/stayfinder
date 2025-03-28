package com.vacation.platform.corp.serch.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchResponseDTO {
    private String businessTitle;
    private String businessCategory;
    private String businessAddress;
}
