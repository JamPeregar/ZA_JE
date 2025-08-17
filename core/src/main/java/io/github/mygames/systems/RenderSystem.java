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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.mygames.components.TextureComponent;

/**
 *
 * @author Admin
 */
public class RenderSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private SpriteBatch batch;
    
    private final ComponentMapper<TextureComponent> texture_mapper = ComponentMapper.getFor(TextureComponent.class);
    private final Family render_family = Family.all(TextureComponent.class).get();
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(render_family);
        //this.batch = batch;
        
        System.out.printf("added movement sys, entities: %d \n",entities.size());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            TextureComponent texture_cmp = texture_mapper.get(entity);
            batch.draw(texture_cmp.texture, deltaTime, deltaTime);
            //coming soon...
        }
    }
    
    
}
