/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Admin
 */
public class TextureComponent implements Component{
    public TextureRegion texture_region;
    
    public static TextureRegion getSimpleModelFromSource(String filepath) {
        return new TextureRegion(new Texture(Gdx.files.internal(filepath)));
    }
}
