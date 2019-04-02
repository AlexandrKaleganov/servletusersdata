package ru.job4j.architecture.modeluser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ErrTest {

    @Test
    public void equals() {
        Err err1 = new Err();
        Err err2 = new Err();
        assertThat(err1.equals(err2), is(true));
    }

}