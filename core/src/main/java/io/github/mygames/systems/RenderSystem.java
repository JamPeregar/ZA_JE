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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.enums.TypeEnum;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *
 * @author Admin
 */
public class RenderSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private final SpriteBatch batch;
    ShapeDrawer shape;
    
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<TextureComponent> texture_mapper = ComponentMapper.getFor(TextureComponent.class);
    ///private final ComponentMapper<StateComponent> state_mapper = ComponentMapper.getFor(StateComponent.class);
    private final ComponentMapper<TypeComponent> type_mapper = ComponentMapper.getFor(TypeComponent.class);
    
    private final Family render_family = Family.all(TextureComponent.class, TransformComponent.class,TypeComponent.class).get();

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
        shape = new ShapeDrawer(batch);
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
            //StateComponent state = state_mapper.get(entity);
            TypeComponent type_cmp = type_mapper.get(entity);
            
            if (position.is_hidden) {
                continue;
            }
            if (type_cmp.type == TypeEnum.SHAPE) {
                shape.setTextureRegion(texture_cmp.texture_region);
                //shape.setColor(Color.RED);
                shape.line(position.coords.x, position.coords.y,200,200);
                continue;
            }
            batch.draw(texture_cmp.texture_region,
                    position.coords.x - texture_cmp.texture_region.getTexture().getHeight()/2,
                    position.coords.y - texture_cmp.texture_region.getTexture().getWidth()/2);
            //coming soon...
        }
        batch.end();
    }
    
    
}
