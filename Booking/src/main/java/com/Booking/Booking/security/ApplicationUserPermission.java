package com.Booking.Booking.security;

public enum ApplicationUserPermission {
    ADVERTISEMENT_READ("advertisement:read"),
    ADVERTISEMENT_WRITE("advertisement:write"),
    BOOKING_REQUEST_READ("booking:read"),
    BOOKING_REQUEST_ACCEPT("booking:accept"),
    BOOKING_REQUEST_DECLINE("booking:decline"),
    BOOKING_REQUEST_PLACE("booking:place"),
    BOOKING_REQUEST_WRITE("booking:write"),
    ITEM_IN_CART_READ( "itemInCart:read"),
    ITEM_IN_CART_WRITE("itemInCart:write"),
    STATISTIC_READ("statistic:read"),
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    CAR_DETAILS_READ("cardetails:read"),
    CAR_DETAILS_WRITE("cardetails:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}