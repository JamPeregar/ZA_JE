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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
import io.github.mygames.Components.enums.Faction;
import io.github.mygames.Components.enums.StateEnum;
import io.github.mygames.Components.enums.TaskEnum;
import io.github.mygames.Components.enums.TypeEnum;
import io.github.mygames.ZAFW;

/**
 *
 * @author Admin
 */
public class NpcGenericEntity {
    //components that entity use
    private final TransformComponent position_cmp;
    private final TextureComponent texture_cmp;
    private final StateComponent state_cmp;
    private final FactionComponent faction_cmp;
    private final StatisticsComponent stats_cmp;
    private final TypeComponent type_cmp;
    private final TaskComponent task_cmp;
    private final BulletComponent bullet_cmp;
    private final B2dBodyComponent bod_cmp;
    private final CollisionComponent col_cmp;
    
    private final Engine base_engine;
    private final World base_world;
    private final Entity base_entity;
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
        
        //configure components
        this.type_cmp.type = TypeEnum.CHARACTER;
        texture_cmp.texture_region = new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")));
        bod_cmp.body = B2dBodyComponent.createCharBody(world);
                
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
        
        engine.addEntity(base_entity);
    }
    
    //-------Attack----------//
    
    public void makeSimpleShoot(Vector2 dest, float maxDist, int dmg) {
        if (bullet_cmp.elapsed < bullet_cmp.delay) {
            return;
        }
        bullet_cmp.set(new Vector2(position_cmp.coords.x,position_cmp.coords.y), dest, maxDist, dmg, base_entity);
    }
    
    //--------Transform methods------------//
    
    public Vector3 getMoveTo(Vector3 dest) {
        return position_cmp.move_to_coords;
    }
    /**
     * Set movement destination target
     * @param dest
     **/
    public void setMoveTo(Vector3 dest) {
        position_cmp.move_to_coords = dest;
        task_cmp.the_task = TaskEnum.MOVE_TO_POINT_SIMPLE;
    }
    /**Get movement destination point
     * @return **/
    public float getSpeed() {
        return position_cmp.acceleration;
    }
    
    public void setSpeed(float speed) {
        position_cmp.acceleration = speed;
    }

    public Vector3 getCoords() {
        return position_cmp.coords;
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
    
    public boolean isCollides() {
        return col_cmp.col_detection;
    }
    
    public void toggleCollisions(boolean nocol) {
        col_cmp.col_detection = nocol;
    }
    
    public boolean isCollidedWithEntity(Entity a) {
        return false;
    }
    
    //-------------Faction methods------------//
    
    public Faction getFaction() {
        return faction_cmp.self_aware;
    }
    
    public void setFaction(Faction faction) {
        faction_cmp.self_aware = faction;
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
    
    //--------------Other methods------------//
    
    public String getName() {
        return stats_cmp.name;
    }
    
    public void setName(final String new_name) {
        stats_cmp.name = new_name;
    }
    
    public Entity getBase_entity() {
        return base_entity;
    }
    
    public TypeEnum getType() {
        return type_cmp.type;
    }
    
}
