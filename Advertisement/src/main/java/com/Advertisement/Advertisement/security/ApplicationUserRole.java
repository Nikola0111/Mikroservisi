package com.Advertisement.Advertisement.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;
import com.Advertisement.Advertisement.security.*;

public enum ApplicationUserRole {
ENDUSER(Sets.newHashSet(ApplicationUserPermission.ADVERTISEMENT_READ, ApplicationUserPermission.ADVERTISEMENT_WRITE, ApplicationUserPermission.BOOKING_REQUEST_READ, 
    ApplicationUserPermission.BOOKING_REQUEST_WRITE, ApplicationUserPermission.ITEM_IN_CART_READ, ApplicationUserPermission.BOOKING_REQUEST_PLACE,
    ApplicationUserPermission.ITEM_IN_CART_WRITE, ApplicationUserPermission.COMMENT_READ, 
    ApplicationUserPermission.COMMENT_WRITE, ApplicationUserPermission.CAR_DETAILS_READ)),
AGENT(Sets.newHashSet(ApplicationUserPermission.ADVERTISEMENT_READ, ApplicationUserPermission.ADVERTISEMENT_WRITE,
    ApplicationUserPermission.BOOKING_REQUEST_READ, ApplicationUserPermission.BOOKING_REQUEST_WRITE, ApplicationUserPermission.BOOKING_REQUEST_ACCEPT,
    ApplicationUserPermission.COMMENT_READ,
    ApplicationUserPermission.COMMENT_WRITE, ApplicationUserPermission.STATISTIC_READ)),
ADMIN(Sets.newHashSet(ApplicationUserPermission.ADVERTISEMENT_READ, ApplicationUserPermission.CAR_DETAILS_WRITE, ApplicationUserPermission.USER_READ, ApplicationUserPermission.USER_WRITE, ApplicationUserPermission.CAR_DETAILS_READ, ApplicationUserPermission.CAR_DETAILS_WRITE, ApplicationUserPermission.COMMENT_READ, ApplicationUserPermission.COMMENT_WRITE));


    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}