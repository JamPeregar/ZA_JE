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
import com.badlogic.gdx.math.Vector2;
import io.github.mygames.components.StateComponent;
import io.github.mygames.components.TransformComponent;

/**
 *
 * @author Admin
 */
public class MovementSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<StateComponent> state_mapper = ComponentMapper.getFor(StateComponent.class);
    private final Family movement_family = Family.all(TransformComponent.class, StateComponent.class).get();
    float stepX, stepY;
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(movement_family);
        System.out.printf("added movement sys, entities: %d \n",entities.size());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            TransformComponent position = pos_mapper.get(entity);
            StateComponent state = state_mapper.get(entity);
            
            if (state.the_state != StateComponent.MOVING) {
                return;
            }
            //System.out.println("move");
            //move_to = new Vector2(position.velocity.x - position.pos.x, position.velocity.y - position.pos.y);
            stepX = position.velocity.x;
            stepY = position.velocity.y;
            //System.out.printf("\n stepX %f step Y %f; state = %d",position.velocity.x,position.velocity.y,state.the_state);
            //stepY = position.velocity.y * deltaTime;
            position.pos.add(stepX * deltaTime, stepY * deltaTime, 0);
            position.velocity.x = 0;
            position.velocity.y = 0;
            
        }
    }
    
    
}
