package com.techelevator.dao;

import com.techelevator.model.Review;

public interface ReviewDao {
    Review addReview();
    Review getReviewsByBeerId();

}
