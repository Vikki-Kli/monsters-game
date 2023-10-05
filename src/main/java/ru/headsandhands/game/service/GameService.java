package ru.headsandhands.game.service;

import ru.headsandhands.game.model.Creature;
import ru.headsandhands.game.model.Monster;
import ru.headsandhands.game.model.Player;

public interface GameService {
    Player createPlayer(String name, int attack, int defense, int health, int damageMin, int damageMax);
    Monster createMonster(String name, int attack, int defense, int health, int damageMin, int damageMax);
    Monster getRandomMonsterForPlayer(Player player);
    Monster getMonsterByName(String name);
    void deleteMonsterByName(String name);
    String stageAttack(Creature attacker, Creature defender);
}
