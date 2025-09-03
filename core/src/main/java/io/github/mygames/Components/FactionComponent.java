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
public class FactionComponent implements Component{
    public static final int NONE = 0;
    public static final int ZOMBIE = 1;
    public static final int BANDIT = 2;
    public static final int SURVIVOR = 3;
    public static final int ARMY = 4;
    public static final int MILITIA = 5;
    public static final int ASSHOLE = 6;
    
    public int faction = NONE;
}
