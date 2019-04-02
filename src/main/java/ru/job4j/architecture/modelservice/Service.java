package ru.job4j.architecture.modelservice;

import java.time.LocalDateTime;

/**
 * услуга, id  услуги, имя, максимально количество услуги,
 */
public class Service {
    private int id;
    private String name;
    private int maxCount;
    private LocalDateTime dateTime;
    private Double summary;
    private int numerschet;

    public Service(int id, String name, int maxCount, LocalDateTime dateTime, Double summary, int numerschet) {
        this.id = id;
        this.name = name;
        this.maxCount = maxCount;
        this.dateTime = dateTime;
        this.summary = summary;
        this.numerschet = numerschet;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", name='" + name + '\'' + ", maxCount=" + maxCount
                + ", dateTime=" + dateTime + ", summary=" + summary + ", summary=" + numerschet + '}';
    }
}
