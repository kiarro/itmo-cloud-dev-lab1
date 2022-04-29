package com.itmoclouddev.lab1.dao;

public class DaoException extends Exception {
    public DaoException(String msg, Throwable innerException){
        super(msg, innerException);
    }
}
