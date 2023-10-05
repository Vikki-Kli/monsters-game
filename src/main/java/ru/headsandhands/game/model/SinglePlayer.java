package ru.headsandhands.game.model;

public class SinglePlayer extends Player {

    private static SinglePlayer PLAYER;

    private SinglePlayer(String name, int attack, int defense, int health, int damageMin, int damageMax) {
        super(name, attack, defense, health, damageMin, damageMax);
    }

    public static SinglePlayer getPlayer(String name, int attack, int defense, int health, int damageMin, int damageMax) {
        if (PLAYER == null) PLAYER = new SinglePlayer(name, attack, defense, health, damageMin, damageMax);
        return PLAYER;
    }
}
