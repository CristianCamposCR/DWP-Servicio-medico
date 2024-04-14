package mx.edu.utez.server.modules.review.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.review.controller.dto.ReviewDto;
import mx.edu.utez.server.modules.review.model.Review;
import mx.edu.utez.server.modules.review.service.ReviewService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/review")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('PATIENT', 'DOCTOR')")
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Review>>> findAllByDoctor(@RequestParam(defaultValue = "0", required = false) int page,
                                                                     @RequestParam(defaultValue = "10", required = false) int size,
                                                                     @RequestParam(defaultValue = "id", required = false) String sort,
                                                                     @RequestParam(defaultValue = "asc", required = false) String direction) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Review>> responseApi = this.reviewService.findAllByDoctor(username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Boolean>> save(@Valid @RequestBody ReviewDto reviewDto) {
        try {
            ResponseApi<Boolean> responseApi = reviewService.save(reviewDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name()));
        }
    }
}