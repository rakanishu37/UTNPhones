package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PageableResponse<T> {
    private List<T> currentPage;
    private int totalPages;
    private Long totalElements;
}
