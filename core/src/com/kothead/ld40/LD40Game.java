package com.kothead.ld40;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kothead.ld40.data.Images;
import com.kothead.ld40.data.Sounds;
import com.kothead.ld40.screen.GameScreen;
import com.kothead.ld40.screen.MessageScreen;
import com.kothead.ld40.screen.WinScreen;

public class LD40Game extends Game {

    private Engine engine;

	@Override
	public void create () {
		engine = new Engine();

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
		engine.removeAllEntities();
		ImmutableArray<EntitySystem> systems = engine.getSystems();
		for (EntitySystem system: systems) {
			engine.removeSystem(system);
		}

		setScreen(new GameScreen(this));
	}

	public void setMessageScreen(String text) {
		setScreen(new MessageScreen(this, text));
	}

	public void setWinScreen(int score) {
		setScreen(new WinScreen(this, score));
	}

	public Engine getEngine() {
	    return engine;
    }
}
