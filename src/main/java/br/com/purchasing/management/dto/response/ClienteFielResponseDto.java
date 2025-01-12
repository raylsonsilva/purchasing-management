package br.com.purchasing.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ClienteFielResponseDto {
    private String nome;
    private String cpf;
    private BigDecimal totalCompras;
}
