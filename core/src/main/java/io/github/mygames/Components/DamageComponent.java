/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import io.github.mygames.Components.enums.DamageType;

/**
 *
 * @author Admin
 */
public class DamageComponent implements Component{
    public int dmg;
    public DamageType dmgtype;
    public Entity attacker;
    public Entity victim;
    
    public DamageComponent() {
    }
    
    public DamageComponent(Entity victim, Entity attacker, int damage, DamageType type) {
        this.victim = victim;
        this.attacker = attacker;
        this.dmg = damage;
        this.dmgtype = type;
    }
}
