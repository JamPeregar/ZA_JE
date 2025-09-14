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
import io.github.mygames.Components.BulletComponent;
import io.github.mygames.Components.FactionComponent;
import io.github.mygames.Components.StateComponent;
import io.github.mygames.Components.TextureComponent;
import io.github.mygames.Components.TransformComponent;
import io.github.mygames.Components.TypeComponent;
import io.github.mygames.Components.enums.TypeEnum;

/**
 *
 * @author Admin
 */
public final class Shoot {
    private final BulletComponent bullet_cmp;
    private final TypeComponent type_cmp;
    
    private final Entity base_entity;
    //private final Engine base_engine;
    
    public Shoot(Engine engine) {
        //base_engine = engine;
        base_entity = engine.createEntity();
        
        this.type_cmp = engine.createComponent(TypeComponent.class);
        this.bullet_cmp = engine.createComponent(BulletComponent.class);
        
        bullet_cmp.set(Vector2.Zero, Vector2.X, 0, 0, base_entity);
        type_cmp.type = TypeEnum.SHAPE;
        
        base_entity.add(bullet_cmp);
        base_entity.add(type_cmp);
        
        engine.addEntity(base_entity);
    }
    
    public BulletComponent getBulletComponent() {
        return bullet_cmp;
    }
}
