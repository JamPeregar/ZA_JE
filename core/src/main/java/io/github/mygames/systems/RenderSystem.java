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
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;

/**
 *
 * @author Admin
 */
public class RenderSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private SpriteBatch batch;
    
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<TextureComponent> texture_mapper = ComponentMapper.getFor(TextureComponent.class);
    private final Family render_family = Family.all(TextureComponent.class, TransformComponent.class).get();

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
    }
    
    
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(render_family);
        //this.batch = batch;
        
        System.out.printf("added render sys, entities: %d \n",entities.size());
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            TextureComponent texture_cmp = texture_mapper.get(entity);
            TransformComponent position = pos_mapper.get(entity);
            
            if (position.is_hidden) {
                return;
            }
            
            batch.draw(texture_cmp.texture_region, position.coords.x, position.coords.y);
            //coming soon...
        }
        batch.end();
    }
    
    
}
