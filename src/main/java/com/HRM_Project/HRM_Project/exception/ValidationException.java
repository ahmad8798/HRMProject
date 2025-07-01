package com.HRM_Project.HRM_Project.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom exception that extends IllegalArgumentException to support field-level errors.
 * This allows for more detailed error reporting while maintaining compatibility
 * with standard exception handling patterns.
 */
public class ValidationException extends IllegalArgumentException {
    
    private final Map<String, String> fieldErrors;
    private final String errorCode;
    
    // Constructor for single field error
    public ValidationException(String fieldName, String fieldError) {
        super("Validation failed for field: " + fieldName);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(fieldName, fieldError);
        this.errorCode = "VALIDATION_ERROR";
    }
    
    // Constructor for single field error with custom message
    public ValidationException(String message, String fieldName, String fieldError) {
        super(message);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(fieldName, fieldError);
        this.errorCode = "VALIDATION_ERROR";
    }
    
    // Constructor for multiple field errors
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = new HashMap<>(fieldErrors);
        this.errorCode = "VALIDATION_ERROR";
    }
    
    // Constructor with error code
    public ValidationException(String message, String fieldName, String fieldError, String errorCode) {
        super(message);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(fieldName, fieldError);
        this.errorCode = errorCode;
    }
    
    // Constructor for multiple field errors with error code
    public ValidationException(String message, Map<String, String> fieldErrors, String errorCode) {
        super(message);
        this.fieldErrors = new HashMap<>(fieldErrors);
        this.errorCode = errorCode;
    }
    
    // Getters
    public Map<String, String> getFieldErrors() {
        return new HashMap<>(fieldErrors);
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    // Helper methods for common validations
    public static ValidationException duplicateEmail(String email) {
        return new ValidationException(
            "Employee with this email already exists", 
            "email", 
            "Employee with email '" + email + "' already exists",
            "DUPLICATE_EMAIL"
        );
    }
    
    public static ValidationException duplicateUsername(String username) {
        return new ValidationException(
            "Username already exists", 
            "email", 
            "Username '" + username + "' is already taken",
            "DUPLICATE_USERNAME"
        );
    }
    
    public static ValidationException userAlreadyExists(Integer employeeId) {
        return new ValidationException(
            "User account already exists for this employee", 
            "employeeId", 
            "Employee with ID " + employeeId + " already has a user account",
            "USER_EXISTS"
        );
    }
    
    // Method to add more field errors
    public ValidationException addFieldError(String fieldName, String error) {
        Map<String, String> newFieldErrors = new HashMap<>(this.fieldErrors);
        newFieldErrors.put(fieldName, error);
        return new ValidationException(this.getMessage(), newFieldErrors, this.errorCode);
    }
}