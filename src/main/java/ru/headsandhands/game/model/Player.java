package ru.headsandhands.game.model;

public abstract class Player extends Creature{
    private int regenerationCount = 4;

    public Player(String name, int attack, int defense, int health, int damageMin, int damageMax) {
        super(name, attack, defense, health, damageMin, damageMax);
    }

    public boolean regenerate(){
        if (regenerationCount > 0) {
            int maxHealth = getMaxHealth();
            int newHealth = (int) (maxHealth * 0.3) + getActualHealth();
            setActualHealth(Math.min(newHealth, maxHealth));
            regenerationCount--;
            return true;
        }
        else return false;
    }
}
