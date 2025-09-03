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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.mygames.entity.Enemy;
import io.github.mygames.systems.MovementSystem;
import io.github.mygames.systems.NavigationSystem;
import io.github.mygames.systems.RenderSystem;

/**
 *
 * @author Admin
 */
public class Ashley_test implements Screen{
    ZAFW dropGame;
    Engine engine;
    //SpriteBatch batch;
    //Texture image;

    MovementSystem mv_sys;
    RenderSystem r_sys;
    NavigationSystem nav_sys;

    Enemy test_actor;
    Enemy test_marker;
    Vector3 touchPos;
    OrthographicCamera camera;

    public Ashley_test(final ZAFW dropGame) {
        this.dropGame = dropGame;
        engine = new Engine();
        //batch = new SpriteBatch();
        mv_sys = new MovementSystem();
        r_sys = new RenderSystem(dropGame.batch);
        nav_sys = new NavigationSystem();


        test_actor = new Enemy(engine);
        //test_actor.setFreeze(true);
        test_marker = new Enemy(engine);
        test_marker.setTexture(new Texture("models/target.png"));
        test_marker.setHidden(true);
        test_marker.setFreeze(true);

        engine.addSystem(mv_sys);
        engine.addSystem(r_sys);
        engine.addSystem(nav_sys);

        camera = new OrthographicCamera();
        //camera.setToOrtho(false);
        camera.setToOrtho(false, ZAFW.MAIN_WIDH, ZAFW.MAIN_HEIGHT);
        touchPos = new Vector3();

        //image = new Texture("target.png");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        //dropGame.batch.setProjectionMatrix(camera.combined);
        //test_actor.setVelocity(100, 0);
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            test_actor.setMoveTo(touchPos);
            test_marker.setCoords(touchPos.x, touchPos.y, 0);
            test_marker.setHidden(false);
            System.out.printf("\n nav from %s to %s", test_actor.getCoords().toString(),test_marker.getCoords().toString());
            //
            //touchPos.set(Gdx.input.getX(), 0, 0);
        }
        //test_marker.setCoords(100, 0, 0);

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
