package br.com.purchasing.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecomendacaoVinhoResponseDto {
    private String nomeCliente;
    private String cpfCliente;
    private String tipoVinhoRecomendado;
}
