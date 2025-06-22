package org.example;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    
    public float cookieCount = 0;
    public float totalEarned = 0;
    public float passiveCookies = 0;
    public Map<UpgradeType, Upgrade> upgrades = new HashMap<>();


    public GameState() {}
    public GameState(float cookieCount, float totalEarned, float passiveCookies, Map<UpgradeType, Upgrade> upgrades) {
        this.cookieCount = cookieCount;
        this.totalEarned = totalEarned;
        this.passiveCookies = passiveCookies;
        this.upgrades = upgrades;
    }

    public GameState(GameState other) {
        this.cookieCount = other.cookieCount;
        this.totalEarned = other.totalEarned;
        this.passiveCookies = other.passiveCookies;
    }

    public void setCookieCount(float cookieCount) {
        this.cookieCount = cookieCount;
    }

    public void setTotalEarned(float totalEarned) {
        this.totalEarned = totalEarned;
    }

    public void setPassiveCookies(float passiveCookies) {
        this.passiveCookies = passiveCookies;
    }
    public void setUpgrades(Map<UpgradeType, Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public void update(float cookieCount, float totalEarned, float passiveCookies, 
                Map<UpgradeType, Upgrade> upgrades) {
        this.cookieCount = cookieCount;
        this.totalEarned = totalEarned;
        this.passiveCookies = passiveCookies;
        this.upgrades = upgrades;
    }


    public static GameState newDefaultState() {
        GameState gs = new GameState();
        gs.upgrades.put(UpgradeType.CURSOR, new Upgrade(UpgradeType.CURSOR, 1.5e1, 0.1));
        gs.upgrades.put(UpgradeType.GRANDMA, new Upgrade(UpgradeType.GRANDMA, 1.0e2, 1));
        gs.upgrades.put(UpgradeType.FARM, new Upgrade(UpgradeType.FARM, 1.1e3, 8));
        gs.upgrades.put(UpgradeType.FACTORY, new Upgrade(UpgradeType.FACTORY, 1.2e4, 47));
        gs.upgrades.put(UpgradeType.MINE, new Upgrade(UpgradeType.MINE, 1.3e5, 260));
        gs.upgrades.put(UpgradeType.BANK, new Upgrade(UpgradeType.BANK, 1.4e6, 1400));
        gs.upgrades.put(UpgradeType.TEMPLE, new Upgrade(UpgradeType.TEMPLE, 2.0e7, 7800));
        gs.upgrades.put(UpgradeType.WIZARD_TOWER, new Upgrade(UpgradeType.WIZARD_TOWER, 3.3e8, 44000));
        gs.upgrades.put(UpgradeType.SHIPMENT, new Upgrade(UpgradeType.SHIPMENT, 5.1e9, 260000));
        gs.upgrades.put(UpgradeType.ALCHEMY_LAB, new Upgrade(UpgradeType.ALCHEMY_LAB, 7.5e10, 1.6e6));
        gs.upgrades.put(UpgradeType.PORTAL, new Upgrade(UpgradeType.PORTAL, 1.0e12, 1.0e7));
        gs.upgrades.put(UpgradeType.TIME_MACHINE, new Upgrade(UpgradeType.TIME_MACHINE, 1.4e13, 6.5e7));
        gs.upgrades.put(UpgradeType.ANTIMATTER_CONDENSER, new Upgrade(UpgradeType.ANTIMATTER_CONDENSER, 1.7e14, 4.3e8));
        gs.upgrades.put(UpgradeType.PRISM, new Upgrade(UpgradeType.PRISM, 2.1e15, 2.9e9));
        gs.upgrades.put(UpgradeType.CHANCEMAKER, new Upgrade(UpgradeType.CHANCEMAKER, 2.6e16, 2.1e10));
        gs.upgrades.put(UpgradeType.FRACTAL_ENGINE, new Upgrade(UpgradeType.FRACTAL_ENGINE, 3.1e17, 1.5e11));
        gs.upgrades.put(UpgradeType.JAVASCRIPT_CONSOLE, new Upgrade(UpgradeType.JAVASCRIPT_CONSOLE, 7.1e19, 1.1e12));
        gs.upgrades.put(UpgradeType.IDLEVERSE, new Upgrade(UpgradeType.IDLEVERSE, 1.2e22, 8.3e12));
        gs.upgrades.put(UpgradeType.CORTEX_BAKER, new Upgrade(UpgradeType.CORTEX_BAKER, 1.9e24, 6.4e13));
        gs.upgrades.put(UpgradeType.YOU, new Upgrade(UpgradeType.YOU, 5.4e26, 5.1e14));

        return gs;
    }

}
