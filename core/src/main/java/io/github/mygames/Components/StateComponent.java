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
    public StateEnum the_state = StateEnum.STAYING;
    
    public enum StateEnum {
    FREEZE,
    STAYING,
    MOVING,
    ROTATE,
    FLYING
}
}
