/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import io.github.mygames.Components.DamageComponent.DamageType;

/**
 *
 * @author Admin
 */
public class ProjectileComponent {
    public Entity owner;           // Кто выпустил снаряд
    public float speed;            // Скорость полета
    public int damage;             // Урон
    public DamageType damageType;  // Тип урона
    public float maxRange;         // Максимальная дистанция полета
    public Vector2 startPosition = new Vector2(); // Начальная позиция
    
    public static enum ProjectileType {
    BULLET,
    PROJECTILE,
    MELEE,
    FRAC
}
}
