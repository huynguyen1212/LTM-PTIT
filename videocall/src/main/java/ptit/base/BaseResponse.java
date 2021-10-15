package ptit.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T>  {
    private String message;
    private String code;
    private T body;
}
