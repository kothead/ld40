package com.kothead.ld40.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kothead.ld40.LD40Game;
import com.kothead.ld40.controller.state.PlayerState;
import com.kothead.ld40.data.Animations;
import com.kothead.ld40.data.Images;
import com.kothead.ld40.model.Direction;

public class WinScreen extends BaseScreen implements InputProcessor {

    private Animation<TextureRegion> animation;
    private Sprite ghost;
    private float state;
    private Label label;
    private BitmapFont font;
    private int score;

    public WinScreen(LD40Game game, int score) {
        super(game);
        this.score = score;

        animation = Animations.get(PlayerState.GHOST_WALK, Direction.RIGHT);
        ghost = new Sprite();
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
        state += delta;

        TextureRegion region = animation.getKeyFrame(state);
        ghost.setRegion(region);
        ghost.setSize(region.getRegionWidth(), region.getRegionHeight());
        ghost.setPosition(75, 65);

        Gdx.gl20.glClearColor(0.0235f, 0.0157f, 0.0196f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch().begin();
        ghost.draw(batch());
        font.draw(batch(), " x " + score, 140, 125);
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
