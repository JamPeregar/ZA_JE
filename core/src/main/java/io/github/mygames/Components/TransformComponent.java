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
    
    public Vector3 move_to_coords = new Vector3();
    
    public boolean is_hidden = false;
    
    public byte FORM_FLAGS;
    
    public static final float NAV_RANGE = 1.5f; 
    
    //flags
    public static final byte NO_COL_DETECTION = 1; //1 bit for sign 
    public static final byte NOCLIP = 2;
    public static final byte FREEZE = 4;
    
}
