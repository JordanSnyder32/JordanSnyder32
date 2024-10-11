package com.techelevator.model;

import java.time.LocalDate;

public class Review {
    private int id;
    private int userId;
    private int beerId;
    private String review;
    private LocalDate reviewDate;


    public Review(int id, int userId, int beerId, String review, LocalDate reviewDate) {
        this.id = id;
        this.userId = userId;
        this.beerId = beerId;
        this.review = review;
        this.reviewDate = reviewDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}