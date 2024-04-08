package mx.edu.utez.server.modules.email.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDto {
    private String email;
    private String fullName;
    private String subject;
    private String title;
    private String body;
}