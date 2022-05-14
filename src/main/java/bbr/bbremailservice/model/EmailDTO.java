package bbr.bbremailservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailDTO {

    private String toEmail;

    private String subject;

    private String message;

}
