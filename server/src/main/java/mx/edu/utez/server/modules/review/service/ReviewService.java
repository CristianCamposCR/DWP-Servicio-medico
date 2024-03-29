package mx.edu.utez.server.modules.review.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.review.model.IReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final IReviewRepository iReviewRepository;
}