package FakeCommerceApp.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import FakeCommerceApp.demo.utils.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // @ExceptionHandler(Exception.class)
    // ResponseEntity<String> handleAllGeneralException(Exception ex){
    //     return  ResponseEntity
    //             .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(ex.getMessage());
    // }

    // @ExceptionHandler(ResourceNotFoundException.class)
    // ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex){
    //     return  ResponseEntity
    //             .status(HttpStatus.NOT_FOUND)
    //             .body(ex.getMessage());
    // }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> handleAllGeneralException(Exception ex){
        return  ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.ErrorResponse(ex.getMessage(), "Something went wrong"));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiResponse<Void>> resourceNotFoundException(ResourceNotFoundException ex){
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.ErrorResponse(ex.getMessage(), "Resource Not Found"));
    }

    @ExceptionHandler(ResourceDeletionException.class)
    ResponseEntity<ApiResponse<Void>> handleDeleteException(ResourceDeletionException ex){
        return  ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.ErrorResponse(ex.getMessage(), "Resource Not Found for delete"));
    }
}
