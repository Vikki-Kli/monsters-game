package ru.headsandhands.game.service;

import ru.headsandhands.game.exception.NotUniqueNameException;
import ru.headsandhands.game.model.Creature;
import ru.headsandhands.game.model.Monster;
import ru.headsandhands.game.model.Player;
import ru.headsandhands.game.model.SinglePlayer;
import ru.headsandhands.game.storage.MonsterStorage;
import ru.headsandhands.game.storage.MonsterStorageInMemory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class HHGameService implements GameService{

    private final MonsterStorage monsterStorage = new MonsterStorageInMemory();

    public HHGameService() {
    }

    @Override
    public Player createPlayer(String name, int attack, int defense, int health, int damageMin, int damageMax) {
        return SinglePlayer.getPlayer(name, attack, defense, health, damageMin, damageMax);
    }

    @Override
    public Monster createMonster(String name, int attack, int defense, int health, int damageMin, int damageMax) throws NotUniqueNameException {
        return monsterStorage.save(new Monster(name, attack, defense, health, damageMin, damageMax));
    }

    @Override
    public Monster getRandomMonsterForPlayer(Player player) {
        List<Monster> monsters = (List<Monster>) monsterStorage.findAll();
        //Отфильтрованы те монстры, которые загоняют игрока в бесконечный цикл
        // (если защита игрока больше атаки монстра и наоборот, т.е. никто никого не может атаковать)
        //Но по-прежнему попадаются слишком сильные монстры, при битве с которыми игрок 100% умирает
        monsters = monsters.stream()
                .filter(s -> s.getAttack() >= player.getDefense() || s.getDefense() <= player.getAttack())
                .toList();
        if (monsters.size() == 0) return new Monster("Defenseless and Powerful", 30, 1, 20, 1, 30);
        else return (Monster) monsters.get((int) (Math.random() * monsters.size())).clone();
    }

    @Override
    public Monster getMonsterByName(String name) throws NoSuchElementException {
        return monsterStorage.findByName(name);
    }

    @Override
    public void deleteMonsterByName(String name) {
        monsterStorage.deleteByName(name);
    }

    @Override
    public String stageAttack(Creature attacker, Creature defender) {
        int attackModifier = attacker.getAttack() - defender.getDefense() + 1;
        if (attackModifier < 1) return "Failed attack!";

        boolean isSuccess = false;

        while (attackModifier != 0) {
            int diceRoll = (int) (Math.random() * 6) + 1;
            if (diceRoll > 4) {
                isSuccess = true;
                break;
            }
            attackModifier--;
        }

        if (isSuccess) {
            Random random = new Random();
            int diff = attacker.getDamageMax() - attacker.getDamageMin();
            int randomDamage = random.nextInt(diff + 1) + attacker.getDamageMin();
            defender.setActualHealth(defender.getActualHealth() - randomDamage);
            return "Good attack, " + attacker.getName() + ", damage " + randomDamage + "! " + healthCheck(defender);
        }
        else return "Failed attack!";
    }

    private String healthCheck(Creature creature) {
        if (creature.getActualHealth() < 1) return creature.getName() + " is dead";
        else return creature.getName() + "'s health is " + creature.getActualHealth();
    }

}
