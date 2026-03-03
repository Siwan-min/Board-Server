package com.fastcampus.boardserver.exception;

public class DuplicatedIdException extends RuntimeException {
    public DuplicatedIdException(String msg) {
        // super class 를 통해 runtime exception 발생시 상위 객체에 return ;
        super(msg);
    }
}
