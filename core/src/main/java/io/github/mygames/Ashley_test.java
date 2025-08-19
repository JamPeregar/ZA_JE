/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.mygames.entity.Enemy;
import io.github.mygames.systems.MovementSystem;
import io.github.mygames.systems.RenderSystem;

/**
 *
 * @author Admin
 */
public class Ashley_test implements Screen{
    ZAFW dropGame;
    Engine engine;
    //SpriteBatch batch;
    
    MovementSystem mv_sys;
    RenderSystem r_sys;
    
    Enemy test_actor;
    Vector3 touchPos;
    OrthographicCamera camera;

    public Ashley_test(final ZAFW dropGame) {
        this.dropGame = dropGame;
        engine = new Engine();
        //batch = new SpriteBatch();
        mv_sys = new MovementSystem();
        r_sys = new RenderSystem(dropGame.batch);
        
        
        test_actor = new Enemy(engine);
        engine.addSystem(mv_sys);
        engine.addSystem(r_sys);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        touchPos = new Vector3();
    }
    
    

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        //dropGame.batch.setProjectionMatrix(camera.combined);
        
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x > test_actor.getPosition().pos.x) {
                test_actor.setVelocity(100.0f, 0f);
            } else if (touchPos.x < test_actor.getPosition().pos.x) {
                test_actor.setVelocity(-100.0f, 0f);
            }
            if (touchPos.y > test_actor.getPosition().pos.y) {
                test_actor.setVelocity(0, 100.0f);
            } else if (touchPos.y < test_actor.getPosition().pos.y) {
                test_actor.setVelocity(0, -100.0f);
            }
        }
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
