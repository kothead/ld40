package com.kothead.ld40.data;

import com.badlogic.gdx.Gdx;

public class Music {

    private static final String MUSIC_DIR = "music/";
    private static final String MUSIC_EXT = ".mp3";
    private static final float VOLUME = 1f;

    public static final String EERIE = "eerie";
    private static String key;
    private static com.badlogic.gdx.audio.Music music;

    public static void play() {
        if (key != null) play(key);
    }

    public static void play(String key) {
        if (music != null) {
            if (Music.key.equals(key)) {
                if (!music.isPlaying()) {
                    music.play();
                }
                return;
            } else {
                music.dispose();
            }
        }

        Music.key = key;
        String path  = MUSIC_DIR + key + MUSIC_EXT;
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(true);
        music.setVolume(VOLUME);
        music.play();
    }

    public static boolean isPlaying() {
        if (music == null) return false;
        return music.isPlaying();
    }

    public static void pause() {
        if (isPlaying()) music.pause();
    }

    public static void dispose() {
        if (music != null) music.dispose();
        music = null;
        key = null;
    }
}
