/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Entity;
import io.github.mygames.Components.DamageComponent;
import io.github.mygames.Components.enums.DamageType;

/**
 *
 * @author Admin
 */
public class Bullet {
    public final DamageComponent bullet_data;

    public Bullet(Entity victim, Entity attacker, int damage, DamageType type) {
        bullet_data = new DamageComponent(victim, attacker, damage, type);
    }
}
