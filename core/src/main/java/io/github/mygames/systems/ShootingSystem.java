/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.enums.StateEnum;
import io.github.mygames.Components.enums.TypeEnum;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *
 * @author Admin
 */
public class ShootingSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private final SpriteBatch batch;
    
    private final ComponentMapper<BulletComponent> bullet_mapper = ComponentMapper.getFor(BulletComponent.class);
    //private final ComponentMapper<TypeComponent> type_mapper = ComponentMapper.getFor(TypeComponent.class);
    private final Family shoot_family = Family.all(BulletComponent.class).get();
    
    //ShapeRenderer shaper;
    ShapeDrawer shaper;
    
    public ShootingSystem(SpriteBatch batch, ShapeDrawer drawler) {
        this.batch = batch;
        shaper = drawler;
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(shoot_family);
        System.out.printf("added shoot sys, entities: %d \n",entities.size());
    }

    @Override
    public void update(float deltaTime) {
        //shaper.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            //TypeComponent type_cmp = type_mapper.get(entity);
            BulletComponent bullet_cmp = bullet_mapper.get(entity);
            bullet_cmp.elapsed += deltaTime;
            
            if (!bullet_cmp.isActive) {
                continue;
            }
            
            if (bullet_cmp.elapsed > bullet_cmp.duration) {
                bullet_cmp.isActive = false;
            }
                //shaper.setColor(Color.RED);
            shaper.setTextureRegion(bullet_cmp.texture_r);
            shaper.line(bullet_cmp.startPoint, bullet_cmp.endPoint, bullet_cmp.widh);
            
                
            
            
        }
        //shaper.end();
        batch.end();
    }
    
    public void addEntity(Engine engine) {
        entities = engine.getEntitiesFor(shoot_family);
        System.out.printf("added movement sys, entities: %d \n",entities.size());
    }
    
    
}
