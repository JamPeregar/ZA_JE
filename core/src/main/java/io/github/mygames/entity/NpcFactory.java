/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.FactionComponent.FactionEnum;

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
        human_template.texture_cmp.texture_region = texture_region; //new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")));
        human_template.setCoords(x, y, z);
        human_template.setFaction(faction);
        human_template.restoreRelationships();
        //specify spawn later
        switch (faction) {
            case ZOMBIE:
                human_template.setName("Infected");
                human_template.setFaction(FactionEnum.ZOMBIE);
                break;
            case SURVIVOR:
                human_template.setName("Survivor");
                human_template.setFaction(FactionEnum.SURVIVOR);
                break;
            case BANDIT:
                //human_template = new Human(base_engine,base_world);
                //configure components
                human_template.setName("Bandit");
                human_template.setFaction(FactionEnum.BANDIT);
                break;
            case FARMER:
                //human_template = new Human(base_engine,base_world);
                //configure components
                human_template.setName("Farmer");
                human_template.setFaction(FactionEnum.FARMER);
                break;
            case ARMY:
                //human_template = new Human(base_engine,base_world);
                //configure components
                human_template.setName("Army");
                human_template.setFaction(FactionEnum.ARMY);
                break;
            default:
                //throw new AssertionError("unknown type");
                human_template.setName("???");
        }
        return human_template;
    }
    
}
