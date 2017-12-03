package com.kothead.ld40;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kothead.ld40.data.Images;
import com.kothead.ld40.data.Skins;
import com.kothead.ld40.data.Sounds;
import com.kothead.ld40.screen.GameScreen;

public class LD40Game extends Game {

    private Engine engine;

	@Override
	public void create () {
		Images.load();
//		Skins.load();
		Sounds.load();
		Gdx.input.setCatchBackKey(true);
		setGameScreen();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		Screen screen = getScreen();
		if (screen != null) screen.dispose();
		super.dispose();
	}

	@Override
	public void setScreen(Screen screen) {
		Screen old = getScreen();
		super.setScreen(screen);
		if (old != null) old.dispose();
	}

	public void setGameScreen() {
		setScreen(new GameScreen(this));
	}

	public Engine getEngine() {
	    return engine;
    }
}
