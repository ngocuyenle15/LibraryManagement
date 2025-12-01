package com.ptit.librarymanagement.common.enums;

public enum Role {
    ADMIN(1, "Admin"),
    STAFF (2, "Staff")
    ;

    private final Integer id;
    private final String roleName;

    Role(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Role mapRole (String roleName) {
        return switch (roleName) {
            case "Admin" -> ADMIN;
            case "Staff" -> STAFF;
            default -> null;
        };

    }
    public static Role mapRole (Integer id) {
        return switch (id) {
            case 1 -> ADMIN;
            case 2 -> STAFF;
            default -> null;
        };

    }

    public Integer getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
