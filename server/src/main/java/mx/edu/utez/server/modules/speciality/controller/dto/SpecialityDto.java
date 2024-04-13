package mx.edu.utez.server.modules.speciality.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.area.controller.dto.AreaDto;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.status.model.Status;

@Data
public class SpecialityDto {
    @NotNull(groups = {SpecialityGroups.Update.class})
    @Positive(groups = {SpecialityGroups.Update.class})
    private Long id;

    @NotBlank(groups = {SpecialityGroups.Update.class, SpecialityGroups.Save.class})
    @Size(min = 1, max = 45, groups = {SpecialityGroups.Update.class, SpecialityGroups.Save.class})
    @Size(max = 45, groups = {SpecialityGroups.GetAll.class})
    private String name;

    @Size(max = 200, groups = {SpecialityGroups.Save.class, SpecialityGroups.Update.class})
    private String description;

    @NotNull(groups = {SpecialityGroups.Update.class, SpecialityGroups.Save.class})
    @Min(value = 0, groups = {SpecialityGroups.Save.class, SpecialityGroups.Update.class})
    @Max(value = 10000, groups = {SpecialityGroups.Save.class, SpecialityGroups.Update.class})
    @PositiveOrZero(groups = {SpecialityGroups.Save.class, SpecialityGroups.Update.class})
    private Double cost;

    private String bannerImage;

    @Valid
    private AreaDto area;

    // Relationships <-
    private Status status;

    public Speciality getSpecialityEntity() {
        return new Speciality(
                getId(),
                getName(),
                getDescription(),
                getCost(),
                getBannerImage(),
                area.getAreaEntity(),
                getStatus()
        );
    }
}
