package ru.headsandhands.game.model;

import ru.headsandhands.game.exception.CreatureIsNotValidException;

public abstract class Creature implements Cloneable{
    private final int attack;
    private final int defense;
    private final int maxHealth;
    private int actualHealth;
    private final int damageMin;
    private final int damageMax;
    private final String name;

    public Creature(String name, int attack, int defense, int maxHealth, int damageMin, int damageMax)
            throws CreatureIsNotValidException {
        if (attack < 1 || attack > 30 || defense < 1 || defense > 30 || maxHealth < 1 || damageMin < 1 || damageMin >= damageMax)
                throw new CreatureIsNotValidException("""
                        Sorry, you have to stick to this rules:\s
                        1 <= attack <= 30,\s
                        1 <= defense <= 30,\s
                        health > 0,\s
                        1 <= damageMin < damageMax""");
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.actualHealth = maxHealth;
    }

    public String getName(){
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public int getActualHealth() {
        return actualHealth;
    }

    public int getDamageMin() {
        return damageMin;
    }

    public int getDamageMax() {
        return damageMax;
    }

    public void setActualHealth(int actualHealth) {
        this.actualHealth = actualHealth;
    }

    @Override
    public String toString() {
        return name + ": " +
                "attack=" + attack +
                ", defense=" + defense +
                ", maxHealth=" + maxHealth +
                ", damageMin=" + damageMin +
                ", damageMax=" + damageMax;
    }

    @Override
    public Creature clone() {
        try {
            return (Creature) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
