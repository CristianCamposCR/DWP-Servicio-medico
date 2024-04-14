package mx.edu.utez.server.modules.review.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.review.model.Review;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private String comment;
    @Max(5)
    @PositiveOrZero
    private Integer ranking;
    @NotNull
    private Boolean wasSkipped;

    @NotNull
    private Appointment appointment;

    public Review getReviewEntity() {
        return new Review(
                getComment(),
                getRanking(),
                getWasSkipped()
        );
    }
}
