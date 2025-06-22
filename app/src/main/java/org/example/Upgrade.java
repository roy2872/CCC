package org.example;



public class Upgrade {
    public UpgradeType type;
    public double basePrice;
    public double currentPrice;
    public double cpsAddon;
    public int amountOwned;

    public Upgrade() {
    } // Needed for Gson

    public Upgrade(UpgradeType type, double basePrice, double cpsAddon) {
        this.type = type;
        this.basePrice = basePrice;
        this.cpsAddon = cpsAddon;
        this.currentPrice = basePrice;
        this.amountOwned = 0;
    }

    public void buy() {
        amountOwned++;
        increasePrice();
    }

    private void increasePrice() {
        currentPrice = basePrice * Math.pow(1.15, amountOwned);
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

    public String name(){
        return type.name().replace('_', ' ').toLowerCase();
    }
}

