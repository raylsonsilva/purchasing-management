package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.response.CompraResponseDto;
import br.com.purchasing.management.entity.Compra;
import br.com.purchasing.management.exception.CompraNotFoundException;
import br.com.purchasing.management.mapper.CompraMapper;
import br.com.purchasing.management.repository.CompraRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final CompraMapper compraMapper;

    @Autowired
    public CompraService(CompraRepository compraRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
    }

    public Page<CompraResponseDto> listarComprasOrdenadasPorValor(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Compra> comprasPaginadas = compraRepository.findAllByOrderByValorTotalAsc(pageRequest);

        List<CompraResponseDto> compraDtos = comprasPaginadas.getContent().stream()
                .map(compraMapper::toResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(compraDtos, pageRequest, comprasPaginadas.getTotalElements());
    }

    public CompraResponseDto obterMaiorCompraPorAno(Integer ano) {
        return compraRepository.findAll().stream()
                .filter(compra -> compra.getProduto().getAnoCompra() != null && compra.getProduto().getAnoCompra().equals(ano))
                .max(Comparator.comparing(Compra::getValorTotal))
                .map(compraMapper::toResponseDto)
                .orElseThrow(() -> new CompraNotFoundException("Nenhuma compra encontrada para o ano: " + ano));
    }
}
