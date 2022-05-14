package bbr.bbremailservice.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> {

    /**
     * 1: OK
     * 0: ERROR
     */
    Integer status;

    String msg;

    T data;

}
