/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Admin
 */
public class TransformComponent implements Component{
    public Vector3 coords = new Vector3(0,0,0);
    public float angle = 0f;
    public Vector2 vel = new Vector2(0,0);
    public float acceleration = 100f;
    public float rotate_speed = 100f;
    public float rotate_deg = 0f;
    public float maxLinearAcceleration = acceleration*2;
    
    public Vector3 move_to_coords = new Vector3();
    
    public boolean is_hidden = false;
    
    public byte FORM_FLAGS;
    
    public static final float NAV_RANGE = 1.5f; 
    
    //flags - simple bit set
    public static final byte NO_COL_DETECTION = 1; //0001
    public static final byte NOCLIP = 1 << 1; //0010
    public static final byte FREEZE = 1 << 2; //0100
    
    //NOT CORRECT FORMULAS
    /*public static Vector2 getVector2FromAngle(Vector2 outVector, float a) {
        //return new Vector2(-(float)Math.sin(a),(float)Math.cos(a));
        return new Vector2((float)Math.cos(a),-(float)Math.cos(a));
    }*/
    
    public static Vector2 getVector2FromAngle(float a) {
        //ТАКЖЕ СМ Vector.angleDeg()
        //return new Vector2(-(float)Math.sin(a),(float)Math.cos(a));
        return new Vector2((float)Math.cos(a),-(float)Math.sin(a));
    }
    
    /*public static float getAngleFromVector2(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }*/
    
    public static Vector2 Vector3ToVector2(Vector3 vec3) {
        return new Vector2(vec3.x, vec3.y);
    }
    
    /*public static Vector2 getVelocityFromVector3Angle(Vector3 outVector3, float a) {
        return new Vector2(TransformComponent.getVector2FromAngle(TransformComponent.Vector3ToVector2(outVector3),a));
    }*/
    public static Vector2 getNorVector2FromAngle(float a) {
        //return new Vector2(TransformComponent.getVector2FromAngle(a)).cpy().nor();
        float angleRad = a * MathUtils.degreesToRadians;
    
    // Возвращаем нормализованный вектор направления
        return new Vector2(MathUtils.cos(angleRad), MathUtils.sin(angleRad));
    }
    
    /**Angle to 0-360 format (useless?)
     * @return float 0-360**/
    /*
    public static float prettyAngle(float a) {
        a = a % 360;
            if (a < 0) {
                a += 360;
            } //to make sure its 0-360 degree
        return a;
    }*/
}
