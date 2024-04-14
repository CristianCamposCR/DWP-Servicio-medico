package mx.edu.utez.server.modules.review.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.review.controller.dto.ReviewDto;
import mx.edu.utez.server.modules.review.model.IReviewRepository;
import mx.edu.utez.server.modules.review.model.Review;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final IReviewRepository iReviewRepository;
    private final IAppointmentRepository iAppointmentRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> save(ReviewDto reviewDto) {
        try {
            Appointment appointment = getAppointment(reviewDto.getAppointment().getId());
            if (appointment == null)
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

            if (appointment.getHasReview())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_WAS_ALREADY_REVIEWED.name());


            if (!reviewDto.getWasSkipped()) {
                Review review = reviewDto.getReviewEntity();
                review.setDoctor(appointment.getDoctor());
                this.iReviewRepository.saveAndFlush(review);
            }

            appointment.setHasReview(true);
            iAppointmentRepository.saveAndFlush(appointment);

            return new ResponseApi<>(
                    true,
                    HttpStatus.OK,
                    false,
                    "Reseña guardada"
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<Review>> findAllByDoctor(String username, Pageable pageable) {
        try {
            return new ResponseApi<>(
                    this.iReviewRepository.findAllByDoctorPersonUserUsername(username, pageable),
                    HttpStatus.OK,
                    false,
                    "Lista de reseñas."
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    private Appointment getAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = iAppointmentRepository.findById(appointmentId);
        return optionalAppointment.orElse(null);
    }
}