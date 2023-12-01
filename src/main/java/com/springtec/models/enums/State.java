package com.springtec.models.enums;

public interface State {
    char ACTIVE = '1';
    char INACTIVE = '0';
    Integer CANCELED = 0;
    Integer PENDING = 1;
    Integer IN_PROCESS = 2;
    Integer CLOSED = 3;
}
