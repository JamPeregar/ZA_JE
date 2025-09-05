/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.mygames.Components.FactionComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;

/**
 *
 * @author Admin
 */
public final class Bullet {
    private final TransformComponent position_cmp;
    private final StateComponent state_cmp;
    private final TextureComponent texture_cmp; // no need but for render system
    
    private final Entity base_entity;
    
    public Bullet(Engine engine) {
        base_entity = engine.createEntity();
        
        this.position_cmp = engine.createComponent(TransformComponent.class);
        this.state_cmp = engine.createComponent(StateComponent.class);
        this.texture_cmp = engine.createComponent(TextureComponent.class);
        state_cmp.the_state = StateComponent.SHAPE;
        //texture_cmp.texture_region = new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")));
        loadTextureRegion();
        //texture.texture.setRegion(20,20,50,50);
        base_entity.add(position_cmp);
        base_entity.add(state_cmp);
        base_entity.add(texture_cmp);
        engine.addEntity(base_entity);
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
            this.state_cmp.the_state = StateComponent.FREEZE;
        } else {
            this.state_cmp.the_state = StateComponent.STAYING;
        }

    }
    
    public void loadTextureRegion() {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        texture_cmp.texture_region = new TextureRegion(texture, 0, 0, 1, 1);
        pixmap.dispose();
        //texture.dispose();
    }
    
}
