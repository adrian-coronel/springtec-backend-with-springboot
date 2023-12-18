package com.springtec.models.enums;

public interface State {
    char ACTIVE = '1';
    char INACTIVE = '0';
    Integer CANCELED = 4;
    Integer PENDING = 1;
    Integer IN_PROCESS = 2;
    Integer CLOSURE_REQUEST = 5;
    Integer CLOSED = 3;
}
