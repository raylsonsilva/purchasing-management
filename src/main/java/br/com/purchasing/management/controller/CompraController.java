package br.com.purchasing.management.controller;

import br.com.purchasing.management.dto.response.CompraResponseDto;
import br.com.purchasing.management.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @Operation(summary = "Listar compras ordenadas por valor", description = "Retorna uma lista de compras ordenadas de forma crescente pelo valor total, de forma paginada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de compras ordenadas.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompraResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao listar compras.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<CompraResponseDto>> listarComprasOrdenadasPorValor(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CompraResponseDto> compras = compraService.listarComprasOrdenadasPorValor(page, size);
        return ResponseEntity.ok(compras);
    }


    @Operation(summary = "Obter maior compra por ano", description = "Retorna a maior compra realizada no ano especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes da maior compra do ano.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompraResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma compra encontrada para o ano especificado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar a maior compra.", content = @Content)
    })
    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<CompraResponseDto> obterMaiorCompraPorAno(@PathVariable Integer ano) {
        CompraResponseDto maiorCompra = compraService.obterMaiorCompraPorAno(ano);
        return ResponseEntity.ok(maiorCompra);
    }
}
