package ru.headsandhands.game.storage;

import ru.headsandhands.game.model.Monster;

import java.util.Collection;

public interface MonsterStorage {
    Monster save(Monster monster);
    Monster findByName(String name);
    void deleteByName(String name);
    Collection<Monster> findAll();
}
