package br.com.purchasing.management.mapper;

import br.com.purchasing.management.dto.ProdutoDto;
import br.com.purchasing.management.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toEntity(ProdutoDto produtoDto);
}
