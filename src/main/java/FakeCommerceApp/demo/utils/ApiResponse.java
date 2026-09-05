package FakeCommerceApp.demo.utils;

import org.springframework.web.ErrorResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    
    private boolean Success;
    private String message;
    private String error;
    private T data;

    public static <T> ApiResponse<T> SuccessResponse(T data, String message){
        return  ApiResponse.<T>builder()
                .Success(true)
                .message(message)
                .error("")
                .data(data)
                .build();

    }

    public static <T> ApiResponse<T> ErrorResponse(String error, String message){
        return  ApiResponse.<T>builder()
                .Success(false)
                .message(message)
                .error(error)
                .build();
    }
}
