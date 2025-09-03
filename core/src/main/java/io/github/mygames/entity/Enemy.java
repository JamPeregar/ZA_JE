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
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;

/**
 *
 * @author Admin
 */
public class Enemy {
    //private final Engine engine;
    private final TransformComponent position;
    private final TextureComponent texture;
    private final StateComponent state;
    private final Entity base_entity;
    //private final Body base_body; //if box2d needed

    public Enemy(Engine engine) {
        base_entity = engine.createEntity();

        this.position = engine.createComponent(TransformComponent.class);
        this.texture = engine.createComponent(TextureComponent.class);
        this.state = engine.createComponent(StateComponent.class);
        texture.texture_region = new TextureRegion(new Texture(Gdx.files.internal("models/enemy.png")));
        //texture.texture.setRegion(20,20,50,50);
        base_entity.add(position);
        base_entity.add(texture);
        base_entity.add(state);
        engine.addEntity(base_entity);
    }

    public void setMoveTo(Vector3 dest) {
        position.move_to_coords = dest;
    }

    public Vector3 getCoords() {
        return position.coords;
    }

    public void setCoords(float x, float y, float z) {
        position.coords.set(x, y, z);
    }

    public void setVelocity(float x, float y) {
        position.vel = new Vector2(x, y);
    }

    public void setVelocity(Vector2 vel) {
        position.vel.set(vel);
    }

    public Entity getBase_entity() {
        return base_entity;
    }

    public TransformComponent getPositionComponent() {
        return position;
    }

    public void setTexture(Texture texture) {
        this.texture.texture_region = new TextureRegion(texture);
    }

    public TextureComponent getTexture() {
        return texture;
    }

    public void setHidden(boolean hide) {
        this.position.is_hidden = hide;
    }

    public void setFreeze(boolean freezes) {
        if (freezes) {
            this.state.the_state = StateComponent.FREEZE;
        } else {
            this.state.the_state = StateComponent.STAYING;
        }

    }

}
