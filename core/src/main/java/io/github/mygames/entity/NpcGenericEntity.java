/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import io.github.mygames.Components.B2dBodyComponent;
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.CollisionComponent;
import io.github.mygames.Components.FactionComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.StatisticsComponent;
import io.github.mygames.Components.TaskComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.WeaponComponent;
import io.github.mygames.Components.FactionComponent.FactionEnum;
import io.github.mygames.Components.StateComponent.StateEnum;
import io.github.mygames.Components.TaskComponent.TaskEnum;
import io.github.mygames.Components.TypeComponent.TypeEnum;
import io.github.mygames.Components.WeaponComponent.WeaponType;
import io.github.mygames.ZAFW;

/**
 *
 * @author Admin
 */
public abstract class NpcGenericEntity {
    //components that entity use
    final TransformComponent position_cmp; //no modifer - childrens can use
    final TextureComponent texture_cmp;
    final StateComponent state_cmp;
    final FactionComponent faction_cmp;
    final StatisticsComponent stats_cmp;
    final TypeComponent type_cmp;
    final TaskComponent task_cmp;
    final BulletComponent bullet_cmp;
    final B2dBodyComponent bod_cmp;
    final CollisionComponent col_cmp;
    final WeaponComponent wpn_cmp;
    
    final Engine base_engine;
    final World base_world;
    final Entity base_entity;
    //private final Body base_body; //if box2d needed

    public NpcGenericEntity(Engine engine, World world) {
        base_entity = engine.createEntity();
        base_engine = engine;
        base_world = world;
        //create components
        this.position_cmp = engine.createComponent(TransformComponent.class);
        this.texture_cmp = engine.createComponent(TextureComponent.class);
        this.state_cmp = engine.createComponent(StateComponent.class);
        this.faction_cmp = engine.createComponent(FactionComponent.class);
        this.type_cmp = engine.createComponent(TypeComponent.class);
        this.task_cmp = engine.createComponent(TaskComponent.class);
        this.bullet_cmp = engine.createComponent(BulletComponent.class);
        this.bod_cmp = engine.createComponent(B2dBodyComponent.class);
        this.col_cmp = engine.createComponent(CollisionComponent.class);
        this.stats_cmp = engine.createComponent(StatisticsComponent.class);
        this.wpn_cmp = engine.createComponent(WeaponComponent.class);
        
          //configure other in children     
          //configure components
        //this.type_cmp.type = TypeEnum.CHARACTER;
        bod_cmp.body = B2dBodyComponent.createCharBody(world);
        bod_cmp.body.setUserData(base_entity); // body belongs to entity
        //bod_cmp.hitboxFx = bod_cmp.body.getFixtureList().first();
        
        //add components
        base_entity.add(type_cmp);
        base_entity.add(position_cmp);
        base_entity.add(texture_cmp);
        base_entity.add(state_cmp);
        base_entity.add(faction_cmp);
        base_entity.add(task_cmp);
        base_entity.add(bullet_cmp);
        base_entity.add(bod_cmp);
        base_entity.add(stats_cmp);
        base_entity.add(col_cmp);
        base_entity.add(wpn_cmp);
        
        engine.addEntity(base_entity);
    }
    
    //-------High level methods----------//
    @Deprecated
    public void makeSimpleShoot(Vector2 dest, float maxDist, int dmg) {
        if (bullet_cmp.elapsed < bullet_cmp.delay) {
            return;
        }
        //bullet_cmp.set(new Vector2(position_cmp.coords.x,position_cmp.coords.y), base_entity);
    }
    
    public void performTask(TaskEnum task) {
        task_cmp.the_task = task;
    }
    /**
     * Set movement destination target
     * @param dest
     **/
    public void setMoveTo(Vector3 dest) {
        position_cmp.move_to_coords = dest;
        performTask(TaskEnum.MOVE_TO_POINT_SIMPLE);
        //task_cmp.the_task = TaskEnum.MOVE_TO_POINT_SIMPLE;
    }
    
    
    //--------Transform methods------------//
    
    public Vector3 getMoveTo(Vector3 dest) {
        return position_cmp.move_to_coords;
    }
    
    public float getSpeed() {
        return position_cmp.acceleration;
    }
    
    public void setSpeed(float speed) {
        position_cmp.acceleration = speed;
    }
    
    public Vector3 getCoords() {
        return position_cmp.coords;
    }
    
    public float getAngle() {
        return position_cmp.angle;
    }

    public void setCoords(float x, float y, float z) {
        position_cmp.coords = new Vector3(x, y, z);
        position_cmp.move_to_coords = position_cmp.coords;
    }

    public Vector2 getVelocity() {
        return position_cmp.vel;
    }
    
    public void setVelocity(float x, float y) {
        position_cmp.vel = new Vector2(x, y);
    }

    public void setVelocity(Vector2 vel) {
        position_cmp.vel.set(vel);
    }
    
    public void setFreeze(boolean freezes) {
        if (freezes) {
            this.state_cmp.the_state = StateEnum.FREEZE;
        } else {
            this.state_cmp.the_state = StateEnum.STAYING;
        }

    }
    
    public void setAngle(float a) {
        position_cmp.angle = a;
        position_cmp.angle = position_cmp.angle % 360;
            if (position_cmp.angle < 0) {
                position_cmp.angle += 360;
            } //to make sure its 0-360 degree
    }
    
    public void aimAtPoint(Vector2 dest) {
        wpn_cmp.aimPoint.set(dest);
        //bullet_cmp.endPoint = dest;
        float targetAngle = (float) Math.atan2(
                dest.y - position_cmp.coords.y,
                dest.x - position_cmp.coords.x
            );
        setAngle((float) Math.toDegrees(targetAngle));
    }
    
    
    public boolean isCollides() {
        return col_cmp.col_detection;
    }
    
    public void toggleCollisions(boolean nocol) {
        col_cmp.col_detection = nocol;
    }
    
    public B2dBodyComponent getBod_cmp() {
        return bod_cmp;
    }

    public CollisionComponent getCol_cmp() {
        return col_cmp;
    }
    
    public boolean isCollidedWithEntity(Entity a) {
        return false;
    }
    
    //-------------Faction methods------------//
    
    public FactionEnum getFaction() {
        return faction_cmp.self_aware;
    }
    
    public void setFaction(FactionEnum faction) {
        faction_cmp.self_aware = faction;
    }
    /**Get Char's relationship list
     * @return relationship for each faction**/
    public int[] getRelationships() {
        return faction_cmp.relationships;
    }
    
    public void setRelationship(int faction, int disposition) {
        faction_cmp.relationships[faction] = disposition;
    }
    
    //-------------Render methods------------//
    
    public TextureComponent getTexture() {
        return texture_cmp;
    }
    
    public void setTexture(Texture texture) {
        this.texture_cmp.texture_region = new TextureRegion(texture);
    }

    public void setHidden(boolean hide) {
        this.position_cmp.is_hidden = hide;
    }
    
    //--------------Statistics------------//
    
    public String getName() {
        return stats_cmp.name;
    }
    
    public void setName(final String new_name) {
        stats_cmp.name = new_name;
    }
    
    //--------------Tasks------------//
    
    public TaskComponent getTask_cmp() {
        return task_cmp;
    }
    
    /**Make simple attack of specified point **/
    public void makeshoot(boolean fire_pressed) {
        
        if (fire_pressed) {
            wpn_cmp.make_shoot = true;
            //aimAtPoint(Vector2.Zero);
            //makeSimpleShoot(Vector2.Zero, 0, 0);
            //bullet_cmp.isActive = true;
            //bullet_cmp.elapsed = 0f;
            //bullet_cmp.set(new Vector2(position_cmp.coords.x,position_cmp.coords.y), bullet_cmp.endPoint);
        } else {
            wpn_cmp.make_shoot = false;
        }
    }
    
     //--------------Other------------//
    public Entity getBase_entity() {
        return base_entity;
    }
    
    public TypeEnum getType() {
        return type_cmp.type;
    }

    public TextureComponent getTexture_cmp() {
        return texture_cmp;
    }

    public StateComponent getState_cmp() {
        return state_cmp;
    }

    public StatisticsComponent getStats_cmp() {
        return stats_cmp;
    }

    public TypeComponent getType_cmp() {
        return type_cmp;
    }

    public BulletComponent getBullet_cmp() {
        return bullet_cmp;
    }

    public WeaponComponent getWpn_cmp() {
        return wpn_cmp;
    }

    public Engine getBase_engine() {
        return base_engine;
    }

    public World getBase_world() {
        return base_world;
    }
    
}
