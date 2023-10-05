package ru.headsandhands.game.storage;

import ru.headsandhands.game.exception.NotUniqueNameException;
import ru.headsandhands.game.model.Monster;

import java.util.*;

public class MonsterStorageInMemory implements MonsterStorage {

    private final Map<String, Monster> monsters = new HashMap<>();

    public MonsterStorageInMemory(){
    }

    @Override
    public Monster save(Monster monster) throws NotUniqueNameException {
        if (monsters.containsKey(monster.getName())) throw new NotUniqueNameException("Monster with this name already exists");
        monsters.put(monster.getName(), monster);
        return monster;
    }

    @Override
    public Monster findByName(String name) throws NoSuchElementException {
        if (!monsters.containsKey(name)) throw new NoSuchElementException("Monster with this name doesn't exist");
        return monsters.get(name);
    }

    @Override
    public void deleteByName(String name) {
        monsters.remove(name);
    }

    @Override
    public ArrayList<Monster> findAll() {
        return new ArrayList<>(monsters.values());
    }
}
