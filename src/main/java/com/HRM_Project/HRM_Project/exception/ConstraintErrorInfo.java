package com.HRM_Project.HRM_Project.exception;

import lombok.Getter;

@Getter
public class ConstraintErrorInfo {
    private final String fieldName;
    private final String message;

    public ConstraintErrorInfo(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

}
