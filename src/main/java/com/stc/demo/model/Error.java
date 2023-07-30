package com.stc.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    String errorCode;
    String status;
    String message;
}
