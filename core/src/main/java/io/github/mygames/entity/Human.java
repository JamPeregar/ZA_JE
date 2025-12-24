/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.AIComponent;
import io.github.mygames.Components.FactionComponent;
import io.github.mygames.Components.TypeComponent.TypeEnum;
import io.github.mygames.Components.WeaponComponent;
import io.github.mygames.Components.WeaponComponent.WeaponType;

/**
 *
 * @author Admin
 */
public class Human extends NpcGenericEntity{
    final AIComponent ai_cmp;
    
    public Human(Engine engine, World world) {
        super(engine, world);
        this.ai_cmp = engine.createComponent(AIComponent.class);
        //configure components
        this.type_cmp.type = TypeEnum.CHARACTER;
        wpn_cmp.init_weapon(WeaponType.UNARMED);
        //additional
        if (faction_cmp.self_aware == FactionComponent.FactionEnum.PLAYER) {
            ai_cmp.state = AIComponent.AIState.GOSLING;
        }
        base_entity.add(ai_cmp);
    }
    
    public void giveWeapon(WeaponType wpn) {
        wpn_cmp.init_weapon(wpn);
    }
}
