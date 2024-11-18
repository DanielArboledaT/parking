package co.com.poli.api.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private HttpStatus status;
    private String message;
    private T data;

}
