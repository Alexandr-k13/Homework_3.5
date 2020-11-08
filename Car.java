package geekbrains;

import geekbrains.Race;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch cdlReady;
    private CountDownLatch cdlFinish;
    private long startTime;
    private static CountDownLatch countDownLatch = new CountDownLatch(Main.CARS_COUNT);

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch cdlReady, CountDownLatch cdlFinish) {
        this.race = race;
        this.speed = speed;
        this.cdlReady = cdlReady;
        this.cdlFinish = cdlFinish;
        CARS_COUNT++;
        this.name = "Speeder #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " is preparing");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " is ready");// лог
            countDownLatch.countDown();
            cdlReady.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this, i + 1, race.getStages().size(), startTime);
        }
        cdlFinish.countDown();
    }
}
