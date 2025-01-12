package br.com.purchasing.management.controller;

import br.com.purchasing.management.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Importar dados de produtos", description = "Importa uma lista de produtos a partir de um JSON externo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos importados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao importar produtos.", content = @Content)
    })
    @PostMapping("/importar")
    public ResponseEntity<String> importarProdutos() {
        String url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json";

        produtoService.consumirJsonProdutos(url);

        return ResponseEntity.ok("Produtos importados com sucesso!");
    }
}