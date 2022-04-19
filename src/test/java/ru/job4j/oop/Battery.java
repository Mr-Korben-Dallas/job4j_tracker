package ru.job4j.oop;

public class Battery {
    private int load;

    public Battery(int load) {
        this.load = load;
    }

    public int getLoad() {
        return load;
    }

    public void exchange(Battery another) {
        int currentLoad = this.load;
        another.load += currentLoad;
        this.load = this.load - currentLoad;
    }
}
