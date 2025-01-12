package br.com.purchasing.management.exception;

public class NoPurchasesFoundException extends RuntimeException {
    public NoPurchasesFoundException(String message) {
        super(message);
    }
}
