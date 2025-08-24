package io.github.mygames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

public class MainMenuScreen implements Screen {

    final ZAFW drop_game;
    OrthographicCamera camera;


    public MainMenuScreen(final ZAFW dropGame) {
        drop_game = dropGame;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        drop_game.batch.setProjectionMatrix(camera.combined);

        drop_game.batch.begin();
        drop_game.font.draw(drop_game.batch, "Welcome to Drop!!! ", 100, 150);
        drop_game.font.draw(drop_game.batch, "Tap anywhere to begin!", 100, 100);
        drop_game.batch.end();

        if (Gdx.input.isTouched()) {
            //drop_game.setScreen(new Main(drop_game));
            drop_game.setScreen(new Ashley_test(drop_game));
            dispose();
        }
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
