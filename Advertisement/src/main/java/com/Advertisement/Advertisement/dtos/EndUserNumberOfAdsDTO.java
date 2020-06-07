package com.Advertisement.Advertisement.dtos;

public class EndUserNumberOfAdsDTO {
    private Long id;
    private int numberOfAds;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfAds() {
        return this.numberOfAds;
    }

    public void setNumberOfAds(int numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public EndUserNumberOfAdsDTO() {
    }

    public EndUserNumberOfAdsDTO(Long id, int numberOfAds) {
        this.id = id;
        this.numberOfAds = numberOfAds;
    }

}