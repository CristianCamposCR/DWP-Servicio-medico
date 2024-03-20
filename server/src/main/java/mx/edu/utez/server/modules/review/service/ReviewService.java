package mx.edu.utez.server.modules.review.service;

import mx.edu.utez.server.modules.review.model.IReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
    private final IReviewRepository iReviewRepository;

    public ReviewService(IReviewRepository iReviewRepository) {
        this.iReviewRepository = iReviewRepository;
    }
}