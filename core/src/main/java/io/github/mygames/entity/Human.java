/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.TypeComponent.TypeEnum;
import io.github.mygames.Components.WeaponComponent.WeaponType;

/**
 *
 * @author Admin
 */
public class Human extends NpcGenericEntity{
    
    public Human(Engine engine, World world) {
        super(engine, world);
        
        //configure components
        this.type_cmp.type = TypeEnum.CHARACTER;
        wpn_cmp.init_weapon(WeaponType.UNARMMED);
    }
    
    public void giveWeapon(WeaponType wpn) {
        wpn_cmp.init_weapon(wpn);
    }
}
