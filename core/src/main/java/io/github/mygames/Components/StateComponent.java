/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author Admin
 */
public class StateComponent implements Component{
    public static final int FREEZE = 0;
    public static final int STAYING = 1;
    public static final int MOVING = 2;
    public static final int FIGHTING = 3;
    
    public static final int SHAPE = 4;
    
    public int the_state = 1;
    
}
