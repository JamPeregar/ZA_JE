/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.systems.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.mygames.Components.AIComponent;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TaskComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.StateComponent.StateEnum;
import io.github.mygames.Components.TaskComponent.TaskEnum;
import io.github.mygames.Components.TypeComponent.TypeEnum;
import sun.awt.www.content.audio.aiff;

/**
 *Provides high level interface - makes entities to do things
 * @author Admin
 */
public class NavigationSystem extends EntitySystem{
    public ImmutableArray<Entity> entities;
    
    private final ComponentMapper<TransformComponent> pos_mapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<B2dBodyComponent> body_mapper = ComponentMapper.getFor(B2dBodyComponent.class);
    private final ComponentMapper<StateComponent> state_mapper = ComponentMapper.getFor(StateComponent.class);
    private final ComponentMapper<TypeComponent> type_mapper = ComponentMapper.getFor(TypeComponent.class);
    private final ComponentMapper<TaskComponent> task_mapper = ComponentMapper.getFor(TaskComponent.class);
    private final ComponentMapper<AIComponent> ai_mapper = ComponentMapper.getFor(AIComponent.class);
    private final Family nav_family = Family.all(TransformComponent.class, B2dBodyComponent.class, StateComponent.class, TypeComponent.class, TaskComponent.class).get();
    private Vector3 new_vel3;
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(nav_family);
        System.out.printf("added nav sys, entities: %d \n",entities.size());
        
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            TransformComponent position = pos_mapper.get(entity);
            B2dBodyComponent body_cmp = body_mapper.get(entity);
            StateComponent state = state_mapper.get(entity);
            TypeComponent type_cmp = type_mapper.get(entity);
            TaskComponent task_cmp = task_mapper.get(entity);
            AIComponent ai_cmp = ai_mapper.get(entity);
            if (state.the_state == StateEnum.FREEZE 
                    || type_cmp.type != TypeEnum.CHARACTER) {
                continue;
            }
            
            switch (task_cmp.the_task) {
                case MOVE_FORWARD:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case MOVE_BACKWARD:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(-position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case MOVE_LEFT:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case MOVE_RIGHT:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case ROTATE_LEFT:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case ROTATE_RIGHT:
                    position.vel.set(TransformComponent.getVelocityFromVector3Angle(position.coords,position.angle).nor().scl(position.acceleration));
                    state.the_state = StateEnum.MOVING;
                    break;
                case MOVE_VEL:
                    state.the_state = StateEnum.MOVING;
                    break;
                case MOVE_TO_POINT_SIMPLE:
                    if (position.coords.dst(position.move_to_coords) > TransformComponent.NAV_RANGE) {
                        new_vel3 = position.move_to_coords.cpy().sub(position.coords.cpy()).clamp(-position.acceleration, position.acceleration);
                        position.vel.set(new_vel3.x, new_vel3.y);
                        state.the_state = StateEnum.MOVING;
                        //body_cmp.body.applyForceToCenter(position.vel.cpy().scl(position.acceleration), false);
                         //body_cmp.body.setLinearVelocity(position.vel.cpy().scl(position.acceleration));
                        //System.out.println("nav changed");
                    }
                    if (position.coords.dst(position.move_to_coords) <= TransformComponent.NAV_RANGE && state.the_state == StateEnum.MOVING) {
                        position.vel.set(0f, 0f);
                        task_cmp.the_task = TaskEnum.NONE;
                        state.the_state = StateEnum.STAYING;
                        //body_cmp.body.setLinearVelocity(Vector2.Zero);
                        //System.out.println("STOPPED");
                    }
                    break;
                case WANDER:
                    if (ai_cmp != null) {
                        ai_cmp.state = AIComponent.AIState.WANDER;
                        //task_cmp.the_task = TaskEnum.NONE;
                    }
                    break;
                case STOP_MOVING:
                    state.the_state = StateEnum.STAYING;
                    body_cmp.body.setLinearVelocity(Vector2.Zero);
                    body_cmp.body.setAngularVelocity(0f);
                    break;
                case NONE:
                    state.the_state = StateEnum.STAYING;
                    body_cmp.body.setLinearVelocity(Vector2.Zero);
                    body_cmp.body.setAngularVelocity(0f);
                    break;
            }
            
        }
    }
}
