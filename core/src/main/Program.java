package main;

import com.badlogic.gdx.Game;
import main.screens.Level1;

public class Program extends Game {

	@Override
	public void create() {
		setScreen(new Level1(this));
	}

	@Override
	public void resize(int i, int i1) {
		super.resize(i, i1);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
