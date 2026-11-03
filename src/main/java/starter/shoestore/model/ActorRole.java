package starter.shoestore.model;

public enum ActorRole {
    GUEST,
    CUSTOMER,
    ADMIN;

    public static ActorRole fromString(String role) {
        if (role == null) {
            return GUEST;
        }
        return ActorRole.valueOf(role.trim().toUpperCase());
    }
}

