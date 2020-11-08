package geekbrains;

import geekbrains.Car;

public abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);

    public abstract void go(Car c, int stagePos, int stageCount, long startTime);
}
