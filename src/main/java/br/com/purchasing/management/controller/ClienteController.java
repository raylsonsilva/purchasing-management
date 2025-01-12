package br.com.purchasing.management.controller;

import br.com.purchasing.management.dto.response.ClienteFielResponseDto;
import br.com.purchasing.management.dto.response.RecomendacaoVinhoResponseDto;
import br.com.purchasing.management.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Importar dados de clientes", description = "Importa uma lista de clientes a partir de um JSON externo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes importados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao importar clientes.", content = @Content)
    })
    @PostMapping("/importar")
    public ResponseEntity<String> importarClientes() {
        String url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json";

        clienteService.consumirJsonClientes(url);

        return ResponseEntity.ok("Clientes importados com sucesso!");
    }

    @Operation(summary = "Obter clientes mais fiéis", description = "Retorna o Top 3 clientes mais fiéis com base no valor total de compras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista dos clientes mais fiéis.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteFielResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao obter clientes.", content = @Content)
    })
    @GetMapping("/fieis")
    public ResponseEntity<List<ClienteFielResponseDto>> obterClientesFieis() {
        List<ClienteFielResponseDto> clientesFieis = clienteService.obterClientesFieis();
        return ResponseEntity.ok(clientesFieis);
    }

    @Operation(summary = "Recomendar tipo de vinho", description = "Retorna uma recomendação de vinho baseada no tipo mais comprado pelo cliente especificado pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendação de tipo de vinho.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecomendacaoVinhoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao gerar recomendação.", content = @Content)
    })
    @GetMapping("/recomendacao/{cpf}/tipo")
    public ResponseEntity<RecomendacaoVinhoResponseDto> obterRecomendacaoVinho(@PathVariable String cpf) {
        RecomendacaoVinhoResponseDto recomendacao = clienteService.obterRecomendacaoVinho(cpf);
        return ResponseEntity.ok(recomendacao);
    }

}