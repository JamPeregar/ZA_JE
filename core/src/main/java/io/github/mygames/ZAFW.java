package io.github.mygames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class ZAFW extends Game {
    SpriteBatch batch;
    BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        // libGDX по умолчанию использует Arial шрифт.
        font = new BitmapFont();

        this.setScreen(new MainMenuScreen(this));

        Box2D.init(); //not necessary but for backwards compatibility
    }

    public void render() {
        super.render(); // важно!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
