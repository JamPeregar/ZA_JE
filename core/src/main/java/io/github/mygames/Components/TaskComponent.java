/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import io.github.mygames.Components.enums.StateEnum;
import io.github.mygames.Components.enums.TaskEnum;

/**
 *
 * @author Admin
 */
public class TaskComponent implements Component{
    public TaskEnum the_task = TaskEnum.NONE;
    
}
