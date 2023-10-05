package ru.headsandhands.game;

import ru.headsandhands.game.model.Monster;
import ru.headsandhands.game.model.Player;
import ru.headsandhands.game.service.GameService;
import ru.headsandhands.game.service.HHGameService;

import java.util.Scanner;

public class Main {

    //Реализация игры с консоли, без сохранения
    static Scanner scan = new Scanner(System.in);
    static GameService gameService = new HHGameService();

    public static void main(String[] args) {
        // Рандомные монстры, загружаются предварительно. Возможности создавать их у пользователя нет.
        // Нужно обернуть в try-catch, если создатель не уверен в том, что делает:
        // конструктор Creature и MonsterStorageInMemory выбрасывают исключения
        gameService.createMonster("Ghost", 1, 1, 1, 1,2);
        gameService.createMonster("Skeleton", 5, 5, 10, 1,3);
        gameService.createMonster("Zombie", 7, 3, 10, 3,7);
        gameService.createMonster("Mummy", 10, 10, 20, 7,15);
        gameService.createMonster("Vampire", 15, 15, 20, 15,20);
        gameService.createMonster("Lich", 20, 25, 50, 20,50);
        gameService.createMonster("Math teacher", 30, 29, 40, 30,60);

        //Создание игрока. Для большего интереса рекомендую параметры 30-5-500-1-15
        Player player = meeting();

        //Имитация главного экрана игры. Игра длится бесконечно, пока юзер не нажмет "l" - leave, либо не умрет в одной из битв.
        while(true) {
            boolean isPlayerAlive = true;
            Monster monster = gameService.getRandomMonsterForPlayer(player);
            System.out.println("\n Leeeeet's do it! Enter single \"f\" for FIGHT! But if you wanna leave, enter single \"l\".");
            while (true) {
                String command = scan.next();
                if (command.equals("f")) {
                    isPlayerAlive = stageBattle(monster, player);
                    break;
                }
                else if (command.equals("l")) {
                    isPlayerAlive = false;
                    break;
                }
            }
            if (!isPlayerAlive) {
                System.out.println("Good luck on the road.");
                break;
            }
        }
    }

    private static Player meeting() {

        System.out.println("Hello, brave stranger! Wanna try yourself in some bloody battle? Tell me more:" +
                "\n \t What is your name?");
        String name = scan.next();
        System.out.println("Ok, " + name + ", what about your skills? How powerful is your attack? From 1 to 30 \n");

        // Проверка входных данных на корректность производится внутри сущности, но т.к. в этом методе мы принимаем их
        //      на вход в интерактивном режиме, полезно продублировать проверку здесь.
        // Также можно было снизить интерактивность и положить все в один try-catch. Сейчас каждая порция данных
        //      принимается после строчки текста.
        // Любой прием данных внутри бесконечного цикла, чтобы программа не вылетала каждый раз, когда юзер ошибется с вводом.

        int attack;
        while (true) {
            try {
                attack = Integer.parseInt(scan.next());
                if (attack < 1 || attack > 30) throw new RuntimeException();
                break;
            }
            catch (Exception e) {
                System.out.println("Just a number, from 1 to 30");
            }
        }

        System.out.println("\t Good. And your defense. Also from 1 to 30");
        int defense;
        while (true) {
            try {
                defense = Integer.parseInt(scan.next());
                if (defense < 1 || defense > 30) throw new RuntimeException();
                break;
            }
            catch (Exception e) {
                System.out.println("Do you hear me? Just a number, from 1 to 30");
            }
        }

        System.out.println("\t Well. Health! Any number, kiddo");
        int health;
        while (true) {
            try {
                health = Integer.parseInt(scan.next());
                if (health < 1) throw new RuntimeException();
                break;
            }
            catch (Exception e) {
                System.out.println("Errrrrr, no, gimme a natural number. You know: 5, 20, 183... And no so much.");
            }
        }

        System.out.println("\t Very nice. Now tell me how much is your minimal damage.");
        int damageMin;
        while (true) {
            try {
                damageMin = Integer.parseInt(scan.next());
                if (damageMin < 1) throw new RuntimeException();
                break;
            }
            catch (Exception e) {
                System.out.println("How about just a natural number? Not huge, you are not a God.");
            }
        }

        System.out.println("\t ...And maximal damage.");
        int damageMax;
        while (true) {
            try {
                damageMax = Integer.parseInt(scan.next());
                if (damageMax < 1 || damageMin >= damageMax) throw new RuntimeException();
                break;
            }
            catch (Exception e) {
                System.out.println("Na-tu-ral-num-ber, nothing new. And something greater than the previous one.");
            }
        }

        return gameService.createPlayer(name, attack, defense, health, damageMin, damageMax);
    }

    // Битва с монстром, которая не закончится, пока один не умрет
    private static boolean stageBattle(Monster monster, Player player) {

        boolean isMonsterDead = false;

        System.out.println("Are you ready? Your opponent is " + monster + "! Hit first!\n");

        while (true) {
            //Ход игрока
            System.out.println("For attack enter \"a\". For regenerate enter \"r\".");
            while (true) {
                String command = scan.next();
                if (command.equals("a")) {
                    System.out.println(gameService.stageAttack(player, monster));
                    if (monster.getActualHealth() < 1) {
                        isMonsterDead = true;
                        System.out.println("Congrats!");
                    }
                    break;
                }
                else if (command.equals("r")) {
                    if (player.regenerate()) System.out.println("Bless you, dear, your health is " + player.getActualHealth() + " now.");
                    else System.out.println("Oops, you can't regenerate anymore.");
                }
            }
            if (isMonsterDead) return true;

            //Ход монстра
            System.out.println("Nice try! His turn now.");
            System.out.println(gameService.stageAttack(monster, player));
            if (player.getActualHealth() < 1) {
                System.out.println("Oh, dude, so sorry. Create a more powerful character next time!");
                return false;
            }
        }
    }
}