package com.ftn.anticancerdrugrecord.model.user;

public enum UserRole {

    DOCTOR("DOCTOR"),
    PHARMACIST("PHARMACIST");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
