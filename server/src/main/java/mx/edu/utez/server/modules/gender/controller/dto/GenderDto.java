package mx.edu.utez.server.modules.gender.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import mx.edu.utez.server.modules.gender.model.Gender;

@Data
public class GenderDto {
    @NotNull
    @Positive
    private Long id;

    public Gender getGenderEntity() {
        return new Gender(getId());
    }
}