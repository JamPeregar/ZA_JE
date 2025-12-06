/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.enums.FactionEnum;
import static io.github.mygames.Components.enums.FactionEnum.SURVIVOR;
import static io.github.mygames.Components.enums.FactionEnum.ZOMBIE;
import io.github.mygames.Components.enums.TypeEnum;
import io.github.mygames.Components.enums.WeaponType;

/**
 *
 * @author Admin
 */
public class NpcFactory {
    final Engine base_engine;
    final World base_world;
    

    public NpcFactory(Engine engine, World world) {
        base_engine = engine;
        base_world = world;
    }
    
    public Human createNPCFaction(float x,float y, float z, FactionEnum faction, TextureRegion texture_region) {
        Human human_template = new Human(base_engine,base_world);
        //specify spawn later
        switch (faction) {
            case ZOMBIE:
                //human_template = 
                break;
            case SURVIVOR:
                //human_template = new Human(base_engine,base_world);
                break;
            case BANDIT:
                //human_template = new Human(base_engine,base_world);
                //configure components
                break;
            default:
                throw new AssertionError("unknown type");
        }
        human_template.texture_cmp.texture_region = texture_region; //new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")));
        human_template.setCoords(x, y, z);
        return human_template;
    }
    
}
