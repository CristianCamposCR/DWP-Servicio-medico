package mx.edu.utez.server.modules.review.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.review.service.ReviewService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/review")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
}