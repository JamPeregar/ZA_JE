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
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.CollisionComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *Detects collisions
 * @author Admin
 */
public class CollisionSystem extends IteratingSystem {
    
    private final ComponentMapper<CollisionComponent> col_mapper;
    private final ComponentMapper<TransformComponent> pos_mapper;
    private final ComponentMapper<TypeComponent> type_mapper;
    //private final Family col_family;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
    }
    
    

    public CollisionSystem() {
	// only need to worry about player collisions
        
	super(Family.all(CollisionComponent.class, TransformComponent.class).get());
        //col_family = Family.all(CollisionComponent.class, TransformComponent.class).get();
	
	col_mapper = ComponentMapper.getFor(CollisionComponent.class);
        pos_mapper = ComponentMapper.getFor(TransformComponent.class);
        type_mapper = ComponentMapper.getFor(TypeComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = col_mapper.get(entity);
        TransformComponent pos = pos_mapper.get(entity);
        
        
        Entity collidedEntity = cc.collisionEntity;
        if (collidedEntity != null) {
            TypeComponent type = type_mapper.get(collidedEntity);
            if (type != null) {
                switch (type.type) {
                    case CHARACTER:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        break;
                    case SHAPE:
                        //do player hit scenery thing
                        System.out.println("player hit scenery");
                        break;		
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }
    }
    
    
    
}
