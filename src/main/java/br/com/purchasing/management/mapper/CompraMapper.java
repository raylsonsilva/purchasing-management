package br.com.purchasing.management.mapper;

import br.com.purchasing.management.dto.CompraDto;
import br.com.purchasing.management.dto.response.CompraResponseDto;
import br.com.purchasing.management.entity.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    Compra toEntity(CompraDto compraDto);

    @Mapping(target = "nomeCliente", source = "cliente.nome")
    @Mapping(target = "cpfCliente", source = "cliente.cpf")
    @Mapping(target = "codigoProduto", source = "produto.codigo")
    @Mapping(target = "tipoVinho", source = "produto.tipoVinho")
    @Mapping(target = "precoProduto", source = "produto.preco")
    @Mapping(target = "quantidade", source = "quantidade")
    @Mapping(target = "valorTotal", source = "valorTotal")
    CompraResponseDto toResponseDto(Compra compra);
}
