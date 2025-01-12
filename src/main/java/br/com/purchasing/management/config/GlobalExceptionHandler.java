package br.com.purchasing.management.config;

import br.com.purchasing.management.exception.ClienteNotFoundException;
import br.com.purchasing.management.exception.JsonClienteProcessingException;
import br.com.purchasing.management.exception.NoPurchasesFoundException;
import br.com.purchasing.management.exception.JsonProdutoProcessingException;
import br.com.purchasing.management.exception.CompraNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(JsonClienteProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleJsonClientProcessingException(JsonClienteProcessingException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoPurchasesFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoPurchasesFoundException(NoPurchasesFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(JsonProdutoProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleJsonProdutoProcessingException(JsonProdutoProcessingException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CompraNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCompraNotFoundException(CompraNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado: " + ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
