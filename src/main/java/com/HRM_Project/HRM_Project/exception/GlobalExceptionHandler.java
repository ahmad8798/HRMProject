package com.HRM_Project.HRM_Project.exception;

import com.HRM_Project.HRM_Project.dto.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String,ConstraintErrorInfo> CONSTRAINT_VIOLATION_MAP;

    static {
        CONSTRAINT_VIOLATION_MAP = Map.of(
                "uk_office_location_city", new ConstraintErrorInfo("city", "A location with this city already exists."),
                "uk_employee_email", new ConstraintErrorInfo("email", "Employee with this email already exists"),
                "uk_user_username", new ConstraintErrorInfo("username", "Employee with this username already exists"),
                "uk_user_employee", new ConstraintErrorInfo("employee_id", "Employee with following employee_id already exists")
        );

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<?> response = ApiResponse.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<?> response = ApiResponse.validationError("Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if(ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();
            String errorMessage = cause.getSQLException().getMessage();

            // Log the full error message for debugging
            System.out.println("SQL Error Message: " + errorMessage);

            // Extract constraint name from PostgreSQL error message
            Optional<String> constraintNameOpt = extractConstraintName(errorMessage);

            if(constraintNameOpt.isPresent()) {
                String constraintName = constraintNameOpt.get();
                System.out.println("Extracted constraint name: " + constraintName);

                if(CONSTRAINT_VIOLATION_MAP.containsKey(constraintName)) {
                    ConstraintErrorInfo constraintErrorInfo = CONSTRAINT_VIOLATION_MAP.get(constraintName);
                    Map<String, String> fieldErrors = new HashMap<>();
                    fieldErrors.put(constraintErrorInfo.getFieldName(), constraintErrorInfo.getMessage());
                    return new ResponseEntity<>(ApiResponse.validationError("Data integrity violation", fieldErrors), HttpStatus.BAD_REQUEST);
                }
            }

            // If we can't extract the constraint name or it's not in our map,
            // we can still try to provide a meaningful error
            if (errorMessage.toLowerCase().contains("duplicate") && errorMessage.toLowerCase().contains("city")) {
                Map<String, String> fieldErrors = new HashMap<>();
                fieldErrors.put("city", "A location with this city already exists.");
                return new ResponseEntity<>(ApiResponse.validationError("Data integrity violation", fieldErrors), HttpStatus.BAD_REQUEST);
            }
        }

        ApiResponse<?> response = ApiResponse.error("Data integrity violation");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<?> response = ApiResponse.error("An unexpected error occurred. Please try again later.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Optional<String> extractConstraintName(String errorMessage) {
        // PostgreSQL constraint format patterns
        String[] patterns = {
                // Pattern for unique constraint violations in PostgreSQL
                "duplicate key value violates unique constraint \"([\\w_]+)\"",
                // Pattern for general constraint violations
                "constraint \"([\\w_]+)\"",
                // Pattern for foreign key constraint violations
                "violates foreign key constraint \"([\\w_]+)\"",
                // Pattern for check constraint violations
                "check constraint \"([\\w_]+)\"",
                // MySQL format
                "constraint \\[\"(.*?)\"\\]",
                // Another MySQL format
                "key '(.*?)'",
                // General pattern that might catch other formats
                "constraint [\"']?([\\w_]+)[\"']?"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(errorMessage);
            if (matcher.find()) {
                return Optional.of(matcher.group(1));
            }
        }

        return Optional.empty();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(ValidationException ex) {
        ApiResponse<?> response = ApiResponse.validationError(ex.getMessage(), ex.getFieldErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Keep the existing IllegalArgumentException handler for other cases
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // If it's our custom ValidationException, it should be handled by the specific handler above
        // This handles other IllegalArgumentExceptions
        ApiResponse<?> response = ApiResponse.error(ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}