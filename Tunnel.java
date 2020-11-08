package geekbrains;

import geekbrains.Stage;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static Semaphore semaphore = new Semaphore(Main.CARS_COUNT / 2);
    private long finishTime;

    public Tunnel(int length) {
        this.length = length;
        this.description = "Tunnel " + length + " m";
        // лог
    }

    @Override
    public void go(geekbrains.Car c, int stagePos, int stageCount, long startTime) {
        try {
            try {
                System.out.println(c.getName() + " waiting for: " + description);
                semaphore.acquire();
                // лог
                System.out.println(c.getName() + " started: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // лог
            } finally {
                finishTime = System.currentTimeMillis() - startTime;
                System.out.println(c.getName() + " has done: " + description + ", time from begin = " + ((float)(finishTime) / 1000));
                semaphore.release();
                if (stagePos == stageCount && Main.firstFinish) {
                    Main.firstFinish = false;
                    System.out.println(c.getName() + " WIN!!!");// лог
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // лог
        }
    }

    @Override
    public void go(Car c) {

    }
}
