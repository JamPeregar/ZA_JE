/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.enums.StateEnum;

/**
 *
 * @author Admin
 */
public class MovementSystem extends IteratingSystem{
    //public ImmutableArray<Entity> entities; //is overrides parent
    private World world;
    
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<StateComponent> state_mapper = ComponentMapper.getFor(StateComponent.class);
    //private final Family movement_family = Family.all(B2dBodyComponent.class, TransformComponent.class, StateComponent.class,B2dBodyComponent.class).get();
    float stepX, stepY;
    
    @SuppressWarnings("unchecked")
    public MovementSystem(World world) {
        super(Family.all(B2dBodyComponent.class, TransformComponent.class, StateComponent.class).get());
        this.world = world;
        
    }
    
    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < getEntities().size(); i++) {
            Entity entity = getEntities().get(i);
            B2dBodyComponent body_cmp = bm.get(entity);
            TransformComponent position = pos_mapper.get(entity);
            StateComponent state = state_mapper.get(entity);
            
            if (state.the_state != StateEnum.MOVING) {
                continue;
            }
            //System.out.println("move");
            //move_to = new Vector2(position.velocity.x - position.pos.x, position.velocity.y - position.pos.y);
            //stepX = position.vel.x;
            //stepY = position.vel.y;
            
            //position.coords.add(stepX * deltaTime, stepY * deltaTime, 0);
            //position.vel.x = 0;
            //position.vel.y = 0;
            body_cmp.body.applyForceToCenter(position.vel.cpy().scl(position.acceleration), false);
            
            
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
