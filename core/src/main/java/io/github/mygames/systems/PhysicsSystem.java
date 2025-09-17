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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.CollisionComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *Moves col obj and obj origin
 * @author Admin
 */
public class PhysicsSystem extends IteratingSystem {
    private static final float MAX_STEP_TIME = 1/45f;
    private static float accumulator = 0f;
 
    private World world;
    private Array<Entity> bodiesQueue;
 
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
 
    @SuppressWarnings("unchecked")
	public PhysicsSystem(World world) {
        super(Family.all(B2dBodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<>();
    }
 
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : bodiesQueue) {
            TransformComponent tfm = tm.get(entity);
            B2dBodyComponent bodyComp = bm.get(entity);
            Vector2 position = bodyComp.body.getPosition();
            tfm.coords.x = position.x;
            tfm.coords.y = position.y;
            tfm.angle = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
        }
        bodiesQueue.clear();
    }
 
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }
}
