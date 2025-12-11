/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ModelLoader {
    private final List<TextureRegion> model_list;
    private final List<String> model_request_list;
    private final List<String> modelpath_list;

    public ModelLoader() {
        model_request_list = new ArrayList<>();
        model_list = new ArrayList<>();
        
        modelpath_list = new ArrayList<>();
        FileHandle modelsDir = Gdx.files.internal("models");
        
        if (modelsDir.exists() && modelsDir.isDirectory()) {
            // Получаем все файлы с расширением .png
            FileHandle[] files_paths = modelsDir.list(".png");
            
            for (FileHandle file : files_paths) {
                // Добавляем полный путь относительно assets
                modelpath_list.add(file.toString());
                System.out.println(file.toString());
            }
        }
    }
    
    public TextureRegion getTextureRegionFromFileName(String modelname) {
        for (int i = 0; i < modelpath_list.size(); i++) {
            if (modelpath_list.get(i).contains(modelname)) {
                return new TextureRegion(new Texture(Gdx.files.internal(modelpath_list.get(i))));
            }
        }
        return new TextureRegion(new Texture(Gdx.files.internal("models/" + "error.png")));
    }
    
    public void request_model(String modelpath) {
        model_request_list.add("models/" + modelpath);
    }
    
    public void load_all_models() {
        for (int i = 0; i < model_request_list.size(); i++) {
            model_list.add(ModelLoader.getModelFromSource(model_request_list.get(i)));
        }
    }
    
    public void unload_all_models() {
        for (int i = 0; i < model_request_list.size(); i++) {
            model_list.forEach(txt->txt.getTexture().dispose());
        }
    }
    
    public static TextureRegion getModelFromSource(String modelpath) {
        return new TextureRegion(new Texture(Gdx.files.internal("models/" + modelpath)));
    }
    
    public static Sprite getSpriteFromTextureReg(TextureRegion txtreg) {
        return new Sprite(txtreg);
    }
    
    public static Sprite getSpriteFromTextureReg(String modelpath) {
        return new Sprite(new TextureRegion(new Texture(Gdx.files.internal("models/" + modelpath))));
    }
}
