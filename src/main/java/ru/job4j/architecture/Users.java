package ru.job4j.architecture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class Users {
    private String id;
    private String name;
    private String mail;
    private String password;
    private String country;
    private String city;
    //конструктор

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Users(String id, String name, String mail, String password, String country, String city) {
        this.id = iscorrectedID(id);
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.country = country;
        this.city = city;
    }

    public Users() {
        this.id = "0";
    }


    private boolean isformatDate(String date) {
        boolean rsl = false;
        if (date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
            String[] dat = date.split("-");
            if (Integer.valueOf(dat[1]) >= 0 && Integer.valueOf(dat[1]) <= 12) {
                if (Integer.valueOf(dat[2]) >= 0 && Integer.valueOf(dat[1]) <= 31) {
                    rsl = true;
                }
            }
        }
        return rsl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = iscorrectedID(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + this.id
                + ", name=" + name + " , mail=" + mail + ", country=" + country + ", city=" + city + "}";
    }


    private String iscorrectedID(String id) {
        return id != null && id.length() > 0 ? id : "0";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        return Objects.equals(name, users.name) && Objects.equals(mail, users.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mail);
    }
}
