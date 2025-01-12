package br.com.purchasing.management.mapper;

import br.com.purchasing.management.dto.ClienteDto;
import br.com.purchasing.management.dto.response.ClienteFielResponseDto;
import br.com.purchasing.management.entity.Cliente;
import br.com.purchasing.management.entity.Compra;
import br.com.purchasing.management.entity.Produto;
import br.com.purchasing.management.repository.ProdutoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ClienteMapper {

    @Autowired
    protected ProdutoRepository produtoRepository;

    @Mapping(target = "compras", expression = "java(mapCompras(clienteDto.getCompras(), cliente))")
    public abstract Cliente toEntity(ClienteDto clienteDto);

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "totalCompras", expression = "java(calcularTotalCompras(cliente))")
    public abstract ClienteFielResponseDto toFielResponseDto(Cliente cliente);

    protected List<Compra> mapCompras(List<br.com.purchasing.management.dto.CompraDto> compraDtos, Cliente cliente) {
        return compraDtos.stream()
                .map(compraDto -> {
                    Produto produto = produtoRepository.findById(compraDto.getCodigo())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + compraDto.getCodigo()));

                    Compra compra = new Compra();
                    compra.setProduto(produto);
                    compra.setQuantidade(compraDto.getQuantidade());
                    compra.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(compraDto.getQuantidade())));
                    compra.setCliente(cliente);
                    return compra;
                })
                .collect(Collectors.toList());
    }

    protected BigDecimal calcularTotalCompras(Cliente cliente) {
        return cliente.getCompras().stream()
                .map(Compra::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
