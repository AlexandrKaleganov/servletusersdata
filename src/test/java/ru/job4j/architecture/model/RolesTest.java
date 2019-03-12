package ru.job4j.architecture.model;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class RolesTest {

    @Test
    public void getRoles() {
        Roles roles = new Roles("1", "Admin");
        Roles roles1 = new Roles("1", "User");
        Roles roles2 = new Roles("1", "Admin");
        assertThat(roles.equals(roles1), Is.is(false));
        assertThat(roles.equals(roles2), Is.is(true));
    }
}