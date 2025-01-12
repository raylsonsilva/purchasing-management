package br.com.purchasing.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompraDto {
    private Long codigo;
    private Integer quantidade;
}
