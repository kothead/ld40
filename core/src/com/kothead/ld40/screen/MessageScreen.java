package com.kothead.ld40.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kothead.ld40.LD40Game;
import com.kothead.ld40.data.Images;

public class MessageScreen extends BaseScreen implements InputProcessor {

    private String text;
    private Label label;
    private BitmapFont font;

    public MessageScreen(LD40Game game, String text) {
        super(game);

        this.text = text;
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Images.getTexture("font"));
        font.setColor(Color.WHITE);
        font.getData().setScale(3f);

        addInputProcessor(this);
    }

    @Override
    protected void layoutViewsLandscape(int width, int height) {

    }

    @Override
    protected void layoutViewsPortrait(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0.0235f, 0.0157f, 0.0196f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float width = 20 * text.length();
        batch().begin();
        font.draw(batch(), text, (getWorldWidth() - width) / 2f, 125);
        batch().end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ENTER
                || keycode == Input.Keys.ESCAPE) {
            getGame().setGameScreen();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
