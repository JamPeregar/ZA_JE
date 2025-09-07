/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import io.github.mygames.Components.enums.TypeEnum;

/**
 *
 * @author Admin
 */
public class TypeComponent implements Component{
    public TypeEnum type = TypeEnum.CHARACTER;
}
