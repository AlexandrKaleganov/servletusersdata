package ru.job4j.architecture;

import java.time.LocalDateTime;
import java.util.Objects;

public class Err {

    String error;
    LocalDateTime dateTime;

    Err() {

    }

    public Err(String error, LocalDateTime datetime) {
        this.error = error;
        this.dateTime = datetime;
    }

    Err(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Err err = (Err) o;
        return Objects.equals(error, err.error)
                && Objects.equals(dateTime, err.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, dateTime);
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Err{"
                + "error='" + error + '\'' + ", dateTime=" + dateTime + '}';
    }
}
