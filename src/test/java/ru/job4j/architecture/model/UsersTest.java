package ru.job4j.architecture.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.model.Users;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class UsersTest {
    @Test
    public void tstUsersrSet() {
        Users users = new Users();
        users.setId("12");
        users.setMail("login");
        users.setName("name");
        Assert.assertThat(users, Is.is(new Users("12", "name", "login", "roo", "roo", "roo", "ADMIN")));
    }

    @Test
    public void isdatatest() throws IOException {
        Users user = new Users("1", "name", "login", "as", "roo", "roo", "ADMIN");
        Users user1 = new Users("1", "name", "login", "as", "roo", "roo", "ADMIN");
        StringBuilder builder = new StringBuilder("{\"id\":\"37\",\"name\":\"Калег\","
                + "\"mail\":\"alexmur07\",\"password\":\"pass\",\"country\":\"country\",\"city\":\"city\",\"roles\":\"ADMIN\"}");
        ObjectMapper mapper = new ObjectMapper();
        Users userrr = mapper.readValue(builder.toString(), Users.class);
        assertThat(userrr.getCountry(), Is.is("country"));
        assertThat(userrr.getCountry(), Is.is("ADMIN"));
    }

}