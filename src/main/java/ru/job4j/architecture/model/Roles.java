package ru.job4j.architecture.model;

import java.util.Objects;

public class Roles {
    private String iduser;
    private String roles;

    public Roles(String iduser, String roles) {
        this.iduser = iduser;
        this.roles = roles;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Roles roles1 = (Roles) o;
        return Objects.equals(iduser, roles1.iduser) && Objects.equals(roles, roles1.roles);
    }

    public int hashCode() {
        return Objects.hash(iduser, roles);
    }

    @Override
    public String toString() {
        return "Roles{" + "iduser='" + iduser + '\'' + ", roles='" + roles + '\'' + '}';
    }
}
