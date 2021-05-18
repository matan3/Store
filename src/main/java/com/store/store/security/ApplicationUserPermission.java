package com.store.store.security;

public enum ApplicationUserPermission {
    CUSTOMER_READ("customer:read"),
    OWNER_READ("owner:read"),
    OWNER_WRITE("owner:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
