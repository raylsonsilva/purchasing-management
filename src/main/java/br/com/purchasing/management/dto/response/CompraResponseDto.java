package br.com.purchasing.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CompraResponseDto {

    private String nomeCliente;
    private String cpfCliente;
    private Long codigoProduto;
    private String tipoVinho;
    private BigDecimal precoProduto;
    private Integer quantidade;
    private BigDecimal valorTotal;

}

