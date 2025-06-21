package org.example;

import java.util.Timer;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class App extends PApplet {
    // Constants for canvas size and other parameters
    int canvasWidth = 800;
    int canvasHeight = 600;
    int lastTickTime = 0;
    float scrollY = 0;
    float scrollSpeed = 20;
    
    // Cookie parameters
    int cookieX = canvasWidth / 2 - canvasWidth / 3;
    int cookieY = canvasHeight / 2;
    float size = 200;
    int normalSize = (int) size;
    int pressedSize = (int)(size * 0.95); // Shrink to 95% when clicked

    
    // Game state variables
    boolean isPressed = false;
    float cookieCount = 0f;
    float passiveCookies = 0f;
    float totalEarned = 0f;

    // Upgrade Names
    public enum Upgrade {
        CURSOR(1.5e1, 0.1),
        GRANDMA(1.0e2, 1),
        FARM(1.1e3, 8),
        FACTORY(1.2e4, 47),
        MINE(1.3e5, 260),
        BANK(1.4e6, 1400),
        TEMPLE(2.0e7, 7800),
        WIZARD_TOWER(3.3e8, 44000),
        SHIPMENT(5.1e9, 260000),
        ALCHEMY_LAB(7.5e10, 1.6e6),
        PORTAL(1.0e12, 1.0e7),
        TIME_MACHINE(1.4e13, 6.5e7),
        ANTIMATTER_CONDENSER(1.7e14, 4.3e8),
        PRISM(2.1e15, 2.9e9),
        CHANCEMAKER(2.6e16, 2.1e10),
        FRACTAL_ENGINE(3.1e17, 1.5e11),
        JAVASCRIPT_CONSOLE(7.1e19, 1.1e12),
        IDLEVERSE(1.2e22, 8.3e12),
        CORTEX_BAKER(1.9e24, 6.4e13),
        YOU(5.4e26, 5.1e14);
    
        private double basePrice;
        private double currentPrice;
        private double cpsAddon;
        private int amountOwned = 0;
    
        private static final float priceMultiplier = 1.15f;
    
        Upgrade(double basePrice, double cpsAddon) {
            this.basePrice = basePrice;
            this.currentPrice = basePrice;
            this.cpsAddon = cpsAddon;
        }
    
        public double getCurrentPrice() {
            return currentPrice;
        }
    
        public double getCpsAddon() {
            return cpsAddon;
        }
    
        public int getAmountOwned() {
            return amountOwned;
        }
    
        public void buy() {
            amountOwned++;
            increasePrice();
        }
    
        private void increasePrice() {
            currentPrice = basePrice * Math.pow(priceMultiplier, amountOwned);
        }
    }

    @Override
    public void settings() {
        size(canvasWidth, canvasHeight);
        
    }

    @Override
    public void draw() {
        int currentTime = millis();
        if (currentTime - lastTickTime >= 100) {
            lastTickTime = currentTime;
            cookieCount += passiveCookies/10;
            totalEarned += passiveCookies/10;

        }
        background(255);
        fill(205, 105, 25);

        float targetSize = isPressed ? pressedSize : normalSize;
        size = lerp(size, targetSize, 0.7f);
        ellipse(cookieX, cookieY, size, size);


        fill(230);
        rect(600, 50, 180, 400);

        pushMatrix();
        translate(600, 50- scrollY);


        for (int i = 0; i < Upgrade.values().length; i++) {
            Upgrade upgrade = Upgrade.values()[i];
            double price = upgrade.getCurrentPrice();
        
            if (cookieCount >= price) {
                fill(150); // Can buy
            } else if (totalEarned >= price * 0.8) {
                fill(100); // Getting close
            } else {
                fill(0);   // Too far
            }
        
            rect(0, i * 50, 180, 40);
        
            fill(255);
            textAlign(LEFT, CENTER);
            textSize(16);
            text(upgrade.name() + " (" + upgrade.getAmountOwned() + ")", 10, i * 50 + 20);
            text(formatNumber(upgrade.getCurrentPrice()), 130, i * 50 + 20);
        }
        

        popMatrix();

        fill(0);
        textSize(64);
        textAlign(LEFT,CENTER);
        text("Cookies:" + formatNumber(cookieCount), 0 , canvasHeight / 2 - canvasHeight / 3);


        textAlign(LEFT,CENTER);
        textSize(20);
        text("Cookies Per Second: " + formatNumber(passiveCookies), 0, canvasHeight / 2 - canvasHeight / 4);

        
    }

    
    public void mousePressed() {
        if (dist(mouseX, mouseY, cookieX, cookieY) < size / 2) {
            isPressed = true;
            cookieCount++;
            totalEarned++;
        } else if (mouseX >= 600 && mouseX <= 780 && mouseY >= 50 && mouseY <= 450) {
            // Convert mouseY to panel-relative Y position, accounting for scroll
            float relativeY = mouseY - 50 + scrollY;
        
            // Calculate which item index was clicked
            int itemIndex = (int)(relativeY / 50);
        
            if (itemIndex >= 0 && itemIndex < Upgrade.values().length) {
                Upgrade upgrade = Upgrade.values()[itemIndex];
                if (cookieCount >= upgrade.getCurrentPrice()) {
                    cookieCount -= upgrade.getCurrentPrice();
                    passiveCookies += upgrade.getCpsAddon();
                    upgrade.buy();
                    println("Bought " + upgrade.name() + "! Now own: " + upgrade.getAmountOwned());
                } else {
                    println("Not enough cookies for " + upgrade.name());
                }
            }
        }
        

        
    }

    @Override
    public void mouseReleased() {
        isPressed = false;
    }

    public void mouseWheel(MouseEvent event) {
            float e = event.getCount();
            scrollY += e * scrollSpeed;
          
            // clamp scrollY to min/max scroll values
            scrollY = constrain(scrollY, 0, Upgrade.values().length * 50 - 400);
    }


    public static String formatNumber(double number) {
        String[] suffixes = {
            "", "K", "M", "B", "T",       // Thousand to Trillion
            "Qa", "Qi", "Sx", "Sp", "Oc", // Quadrillion to Octillion
            "No"                          // Nonillion
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