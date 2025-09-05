/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class FactionComponent implements Component{
    // May be add some dynamic
    public int[] relationships = new int[11]; // relations to other factions
    public int self_aware = NONE;
    
    public static final int NONE = 0;
    public static final int PLAYER = 1;
    public static final int ZOMBIE = 2;
    public static final int BANDIT = 3;
    public static final int SURVIVOR = 4;
    public static final int ARMY = 5;
    public static final int MILITIA = 6;
    public static final int ASSHOLE = 7;
    
    public static final int SPEC1 = 8;
    public static final int SPEC2 = 9;
    public static final int SPEC3 = 10;
    
}
