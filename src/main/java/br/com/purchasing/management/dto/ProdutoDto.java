package br.com.purchasing.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProdutoDto {
    private Long codigo;
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    private BigDecimal preco;
    private String safra;
    @JsonProperty("ano_compra")
    private Integer anoCompra;
}
