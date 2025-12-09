/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class StatisticsComponent implements Component{
    public String name;
    public int health = 100;
    public int armor = 0;
    public int accuracy = 60;
    public int attack_rate = 100;
    public int strengh = 0;
    public float sense_range = 100;
    public boolean is_dead = false;
    public ArrayList inventory;
}
