/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.mygames.Components.DamageComponent;
import io.github.mygames.Components.StatisticsComponent;

/**
 *
 * @author Admin
 */
public class DamageBrokerSystem extends IteratingSystem{
    
    public DamageBrokerSystem() {
        super(Family.all(DamageComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageComponent dmg = entity.getComponent(DamageComponent.class);
        
        // Находим компонент здоровья у получателя
        StatisticsComponent stats = dmg.victim.getComponent(StatisticsComponent.class);
        if (stats != null) {
            stats.health -= dmg.dmg;
            
            // Логика смерти
            if (stats.health <= 0 && !stats.is_dead) {
                // Помечаем сущность для удаления или запускаем анимацию смерти
                //dmg.victim.add(new DeadComponent());
                stats.is_dead = true;
                System.out.println(stats.name + " died with hp " + stats.health);
            }
            System.out.printf("%s has damaged\n",stats.name);
        }
        
        // Удаляем компонент урона после обработки
        entity.remove(DamageComponent.class);
    }

}
