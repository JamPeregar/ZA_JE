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
import com.badlogic.gdx.math.Vector3;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TransformComponent;

/**
 *
 * @author Admin
 */
public class NavigationSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<StateComponent> state_mapper = ComponentMapper.getFor(StateComponent.class);
    private final Family movement_family = Family.all(TransformComponent.class, StateComponent.class).get();
    private Vector3 new_vel3;
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(movement_family);
        System.out.printf("added nav sys, entities: %d \n",entities.size());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            TransformComponent position = pos_mapper.get(entity);
            StateComponent state = state_mapper.get(entity);
            
            if (state.the_state != StateComponent.FREEZE && position.coords.dst(position.move_to_coords) > 1.0f) {
                
                new_vel3 = position.move_to_coords.cpy().sub(position.coords.cpy()).clamp(-position.acceleration, position.acceleration);
                
                position.vel = new Vector2(new_vel3.x, new_vel3.y);
                state.the_state = StateComponent.MOVING;
                //System.out.println("nav changed");
            } else if (state.the_state == StateComponent.MOVING && position.coords.dst(position.move_to_coords) < 1.0f) {
                position.vel.set(0f,0f);
                state.the_state = StateComponent.STAYING;
                System.out.println("STOPPED");
            }
        }
    }
}
