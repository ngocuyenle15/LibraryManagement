package com.ptit.librarymanagement.common.transactionmanager;

public class TransactionException extends RuntimeException{
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
