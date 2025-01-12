package br.com.purchasing.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ClienteDto {
    private String nome;
    private String cpf;
    private List<CompraDto> compras;
}
