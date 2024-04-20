package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.util.Date;

public class Main {
    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static final Point CLOSE_ADD_BUTTON_POINT = new Point(986, 126);
    public static final Point FILL_BUTTON_POINT = new Point(830, 650);
    public static final Point BACK_SYSTEM_BUTTON_POINT = new Point(1010, 730);
    public static final Point BOOSTS_BUTTON_POINT = new Point(770, 730);
    public static final Point TWO_PIXELS_BUTTON_POINT = new Point(750, 300);
    public static final Point BUY_BOOST_BUTTON = new Point(750, 700);
    public static final long WAIT_ADD_MILLIS = 1000 * 15; // 15 seconds
    public static final long SEVEN_MINUTES_MILLIS = 1000 * 60 * 7;
    public static final long THREE_MINUTES_MILLIS = 1000 * 60 * 3;

    public static void main(String[] args) throws AWTException, InterruptedException {

        Robot robot = new Robot();
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new Listener(), AWTEvent.KEY_EVENT_MASK); //| AWTEvent.FOCUS_EVENT_MASK);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,100);
        frame.setVisible(true);

        new Thread(() -> {
            while (true) {

                Date dateNowBeforeAdSkip = new Date();
                long dateNowBeforeAdSkipMillis = dateNowBeforeAdSkip.getTime();
                long dateAdSkipTimeMillis = dateNowBeforeAdSkipMillis + SEVEN_MINUTES_MILLIS;

                System.out.println("Текущее время: " + new Date(dateNowBeforeAdSkipMillis));

                while (dateNowBeforeAdSkipMillis < dateAdSkipTimeMillis) {
                    try { buyTwoPixelBoost(robot); } catch (Exception ignored) {}

                    long dateBoostStart = new Date().getTime();
                    long dateBoostEnd = dateBoostStart + THREE_MINUTES_MILLIS;

                    System.out.println("Время покупки буста: " + new Date(dateBoostStart));
                    System.out.println("Время окончания действия буста: " + new Date(dateBoostEnd));

                    while (dateBoostStart < dateBoostEnd) {
                        try { farm(robot); } catch (Exception ignored) {}
                        try { clickWindowAwt(robot); } catch (Exception ignored) {}
                        dateBoostStart = new Date().getTime();
                    }
                    dateNowBeforeAdSkipMillis = new Date().getTime();
                }
                try { skipAd(robot); } catch (Exception ignored) {}
            }
        }).start();


    }
    private static class Listener implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
            // System.out.print(MouseInfo.getPointerInfo().getLocation() + " | ");
            System.out.println("Конец");
            System.exit(0);
        }
    }

    private static void skipAd(Robot robot) throws InterruptedException {
        robot.mouseMove(BACK_SYSTEM_BUTTON_POINT.x, BACK_SYSTEM_BUTTON_POINT.y);
        leftMouseClick(robot);
        sleep(WAIT_ADD_MILLIS);
        robot.mouseMove(CLOSE_ADD_BUTTON_POINT.x, CLOSE_ADD_BUTTON_POINT.y);
        leftMouseClick(robot);
        sleep(5 * 1000);
    }

    private static void leftMouseClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private static void farm(Robot robot) throws InterruptedException {
        robot.mouseMove(FILL_BUTTON_POINT.x, FILL_BUTTON_POINT.y);
        leftMouseClick(robot);
        Thread.sleep(2 * 1000);
    }

    private static void buyTwoPixelBoost(Robot robot) throws InterruptedException {
        robot.mouseMove(BOOSTS_BUTTON_POINT.x, BOOSTS_BUTTON_POINT.y);
        leftMouseClick(robot);
        Thread.sleep(2000);

        robot.mouseMove(TWO_PIXELS_BUTTON_POINT.x, TWO_PIXELS_BUTTON_POINT.y);
        leftMouseClick(robot);
        Thread.sleep(2000);

        robot.mouseMove(BUY_BOOST_BUTTON.x, BUY_BOOST_BUTTON.y);
        leftMouseClick(robot);
        Thread.sleep(2000);
    }

    private static void clickWindowAwt(Robot robot) throws InterruptedException {
        robot.mouseMove(50, 50);
        leftMouseClick(robot);
        Thread.sleep(7 * 1000);
    }
}