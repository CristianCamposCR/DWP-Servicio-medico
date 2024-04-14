package mx.edu.utez.server.modules.area.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityGroups;
import mx.edu.utez.server.modules.status.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AreaDto {
    @NotNull(groups = {AreaGroups.Update.class, SpecialityGroups.Save.class, SpecialityGroups.Update.class})
    @Positive(groups = {AreaGroups.Update.class, SpecialityGroups.Save.class, SpecialityGroups.Update.class, SpecialityGroups.GetAll.class})
    private Long id;

    @NotBlank(groups = {AreaGroups.Update.class, AreaGroups.Save.class})
    @Size(min = 1, max = 45, groups = {AreaGroups.Update.class, AreaGroups.Save.class})
    @Size(max = 45, groups = {AreaGroups.GetAll.class})
    private String name;

    @Size(max = 200, groups = {AreaGroups.Update.class, AreaGroups.Save.class})
    private String description;

    private String bannerImage;

    // Relationships <-
    private Status status;

    public Area getAreaEntity() {
        return new Area(getId(), getName(), getDescription(), getBannerImage(), getStatus());
    }
}
