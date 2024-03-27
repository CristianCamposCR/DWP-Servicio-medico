package mx.edu.utez.server.modules.area.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.dtoGroups.GetAll;
import mx.edu.utez.server.utils.dtoGroups.Save;
import mx.edu.utez.server.utils.dtoGroups.Update;

@Data
public class AreaDto {
    @NotBlank(groups = {Update.class})
    private String idStr;
    private Long id;

    @NotBlank(groups = {Update.class, Save.class})
    @Size(min = 1, max = 45, groups = {Update.class, Save.class, GetAll.class})
    private String name;

    @Size(max = 200)
    private String description;

    private String bannerImage;

    // Relationships <-
    private Status status;

    public Area getArea() {
        return new Area(
                getId(),
                getName(),
                getDescription(),
                getBannerImage(),
                getStatus()
        );
    }
}
