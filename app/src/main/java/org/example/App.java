package org.example;

import java.util.Map;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class App extends PApplet {
    int canvasWidth = 800;
    int canvasHeight = 600;
    int lastTickTime = 0;
    float scrollY = 0;
    float scrollSpeed = 20;

    int cookieX = canvasWidth / 2 - canvasWidth / 3;
    int cookieY = canvasHeight / 2;
    float size = 200;
    int normalSize = (int) size;
    int pressedSize = (int) (size * 0.95);

    boolean isPressed = false;

    GameState gameState = SaveManager.load();

    float cookieCount = gameState.cookieCount;
    float passiveCookies = gameState.passiveCookies;
    float totalEarned = gameState.totalEarned;

    public Map<UpgradeType, Upgrade> upgrades = gameState.upgrades;

    @Override
    public void settings() {
        size(canvasWidth, canvasHeight);
    }

    @Override
    public void draw() {
        int currentTime = millis();
        if (currentTime - lastTickTime >= 100) {
            lastTickTime = currentTime;
            cookieCount += passiveCookies / 10;
            totalEarned += passiveCookies / 10;
        }

        background(255);
        fill(205, 105, 25);
        float targetSize = isPressed ? pressedSize : normalSize;
        size = lerp(size, targetSize, 0.7f);
        ellipse(cookieX, cookieY, size, size);

        fill(230);
        rect(600, 50, 180, 400);

        pushMatrix();
        translate(600, 50 - scrollY);

        int index = 0;
        for (UpgradeType type : UpgradeType.values()) {
            Upgrade upgrade = upgrades.get(type);
            double price = upgrade.getCurrentPrice();

            if (cookieCount >= price) fill(150);
            else if (totalEarned >= price * 0.8) fill(100);
            else fill(0);

            rect(0, index * 50, 180, 40);

            fill(255);
            textAlign(LEFT, CENTER);
            textSize(16);
            text(type.name() + " (" + upgrade.getAmountOwned() + ")", 10, index * 50 + 20);
            text(formatNumber(price), 130, index * 50 + 20);
            index++;
        }

        popMatrix();

        fill(0);
        textSize(64);
        textAlign(LEFT, CENTER);
        text("Cookies: " + formatNumber(cookieCount), 0, canvasHeight / 2 - canvasHeight / 3);

        textAlign(LEFT, CENTER);
        textSize(20);
        text("Cookies Per Second: " + formatNumber(passiveCookies), 0, canvasHeight / 2 - canvasHeight / 4);

        // Reset button
        fill(255, 0, 0);
        rect(0, 550, 50, 50);

        gameState.update(cookieCount, totalEarned, passiveCookies, upgrades);
    }

    @Override
    public void exit() {
        println("Saving game...");
        SaveManager.save(gameState);
        super.exit();
    }

    @Override
    public void mousePressed() {
        if (dist(mouseX, mouseY, cookieX, cookieY) < size / 2) {
            isPressed = true;
            cookieCount++;
            totalEarned++;
        } else if (mouseX >= 600 && mouseX <= 780 && mouseY >= 50 && mouseY <= 450) {
            float relativeY = mouseY - 50 + scrollY;
            int itemIndex = (int) (relativeY / 50);

            if (itemIndex >= 0 && itemIndex < UpgradeType.values().length) {
                UpgradeType type = UpgradeType.values()[itemIndex];
                Upgrade upgrade = upgrades.get(type);

                if (cookieCount >= upgrade.getCurrentPrice()) {
                    cookieCount -= upgrade.getCurrentPrice();
                    passiveCookies += upgrade.getCpsAddon();
                    upgrade.buy();
                    println("Bought " + type.name() + "! Now own: " + upgrade.getAmountOwned());
                } else {
                    println("Not enough cookies for " + type.name());
                }
            }
        } else if (mouseX >= 0 && mouseX <= 50 && mouseY >= 550 && mouseY <= 600) {
            SaveManager.save(GameState.newDefaultState());
            println("Game reset!");
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased() {
        isPressed = false;
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        scrollY += e * scrollSpeed;
        scrollY = constrain(scrollY, 0, UpgradeType.values().length * 50 - 400);
    }

    public static String formatNumber(double number) {
        String[] suffixes = {
            "", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No"
        };
        int tier = 0;
        while (number >= 1000 && tier < suffixes.length - 1) {
            number /= 1000.0;
            tier++;
        }
        return String.format("%.2f", number).replaceAll("\\.00$", "") + suffixes[tier];
    }

    public static void main(String[] args) {
        PApplet.main("org.example.App");
    }
}