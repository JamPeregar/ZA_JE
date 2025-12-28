/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TaskComponent implements Component{
    public TaskEnum the_task = TaskEnum.NONE;
    public List<TaskEnum> task_sequence = new ArrayList<>();
    public int task_seq_id = 0;
    public boolean is_important = false;
    
    public enum TaskEnum {
    NONE,
    STOP_MOVING,
    MOVE_VEL,
    MOVE_FORWARD,
    MOVE_BACKWARD,
    MOVE_LEFT,
    MOVE_RIGHT,
    ROTATE_LEFT,
    ROTATE_RIGHT,
    ROTATE_VEL,
    MOVE_TO_POINT_SIMPLE,
    WANDER,
    AIM_AT_VEL,
    ATTACK_ENTITY
}
}
