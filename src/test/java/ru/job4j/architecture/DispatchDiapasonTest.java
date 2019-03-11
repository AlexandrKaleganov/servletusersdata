package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 12
 * @since 05/12/2018
 * тест класса диспатч
 * исключение будет пробрасываться выше в сервлеты
 * и там либо я получаю объект и вывожу сообщение либо получаю либо получаю сообщение об ошибке
 */
public class DispatchDiapasonTest {


    private void fulltest(BiConEx<DispatchDiapason, Users> fanc) {
        var users = new Users("1", "user", "user123", "passs", "Russia", "Novosibirsk");
        var disp = new DispatchDiapason().init();
        try {
            var exp = disp.access("add", users);
            fanc.accept(disp, exp);
        } catch (Exception e) {
        } finally {
            try {
                disp.access("deleteAll", new Users(), new ArrayList<Users>());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * тест добавления и получения пользователя по id
     */
    @Test
    public void testAdd() {
        this.fulltest((disp, exp) -> {
            Assert.assertThat(disp.access("findbyid", exp), is(exp));
        });
    }


    /**
     * тест получения всех пользователей
     */
    @Test
    public void findall() {
        this.fulltest((disp, exp) -> {
            Assert.assertThat(disp.access("findall", new Users(), new ArrayList<Users>()).get(0), Is.is(exp));
        });
    }

    /**
     * обновление пользователя
     */
    @Test
    public void update() {
        this.fulltest((disp, exp) -> {
            disp.access("update", new Users(exp.getId(), "вася", "vasia2", "pass", "Russia", "Novosibirsk"));
            Assert.assertThat(disp.access("findbyid", exp).getName(), is("вася"));
        });
    }

    @Test
    public void delete() {
        this.fulltest((disp, exp) -> {
            Assert.assertThat(disp.access("delete", exp), is(exp));
            Assert.assertThat(disp.access("findbyid", exp).getId(), is("0"));
        });
    }

}