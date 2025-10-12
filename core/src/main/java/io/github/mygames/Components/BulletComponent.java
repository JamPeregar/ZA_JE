/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Admin
 */
public class BulletComponent implements Component {
    public Vector2 startPoint = new Vector2();
    public Vector2 endPoint = new Vector2();
    public Vector2 direction = new Vector2();
    public float maxDistance;
    public float widh = 1.0f;
    public float duration = 0.05f;
    public float delay = 0.1f;
    public float elapsed;
    public int damage;
    public boolean isActive;
    public Entity owner; 
    public TextureRegion texture_r;
    
    public BulletComponent() {}
    
    public BulletComponent(Vector2 start, Vector2 dest) {
        startPoint.set(start);
        //direction.set(dir).nor();
        endPoint.set(dest); //.setLength(maxDist); //.clamp(0, maxDist);
        //maxDistance = maxDist;
        isActive = true;
        elapsed = 0f;
        
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.drawPixel(0, 0);
        //Texture texture = new Texture(pixmap); //remember to dispose of later
        texture_r = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();
    }
    
    public void set(Vector2 start, Vector2 dest) {
        startPoint.set(start);
        endPoint.set(dest);
        //direction.set(dir).nor();
        //endPoint.set(dest); //.setLength(maxDist); //.clamp(0, maxDist);
        //maxDistance = maxDist;
        //damage = dmg;
        //owner = ownerEntity;
        isActive = true;
        elapsed = 0f;
        
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.drawPixel(0, 0);
        //Texture texture = new Texture(pixmap); //remember to dispose of later
        texture_r = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();
    }
}
