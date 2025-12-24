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
public class TaskComponent implements Component{
    public TaskEnum the_task = TaskEnum.NONE;
    public boolean is_important = false;
    
    public enum TaskEnum {
    NONE,
    STOP_MOVING,
    MOVE_FORWARD,
    MOVE_TO_POINT_SIMPLE,
    WANDER,
    ATTACK_ENTITY
}
}
