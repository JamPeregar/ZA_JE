/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Admin
 */
public class TransformComponent implements Component{
    public Vector3 coords = new Vector3(0,0,0);
    public float angle;
    public Vector2 vel = new Vector2(0,0);
    public float acceleration = 100f;
    public float rotate_speed = 10f;
    public float maxLinearAcceleration = acceleration*2;
    
    public Vector3 move_to_coords = new Vector3();
    
    public boolean is_hidden = false;
    
    public byte FORM_FLAGS;
    
    public static final float NAV_RANGE = 1.5f; 
    
    //flags - simple bit set
    public static final byte NO_COL_DETECTION = 1; //0001
    public static final byte NOCLIP = 1 << 1; //0010
    public static final byte FREEZE = 1 << 2; //0100
    
    public static Vector2 getVector2FromAngle(Vector2 outVector, float a) {
        return new Vector2(-(float)Math.sin(a),(float)Math.cos(a));
    }
    
    public static float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }
    
    public static Vector2 Vector3ToVector2(Vector3 vec3) {
        return new Vector2(vec3.x, vec3.y);
    }
    
    public static Vector2 getVelocityFromVector3Angle(Vector3 outVector3, float a) {
        return new Vector2(TransformComponent.getVector2FromAngle(TransformComponent.Vector3ToVector2(outVector3),a));
    }
}
