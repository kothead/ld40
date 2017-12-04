package com.kothead.ld40.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

public class Sounds {

    private static final String SOUND_DIR = "sounds/";
    private static final String SOUND_EXT = ".wav";

    private static ObjectMap<String, Sound> sounds;

    public static final String FALL = "fall";
    public static final String WALK = "walk";
    public static final String DIVIDE = "divide";
    public static final String DIE = "die";

    public static void load() {
        sounds = new ObjectMap<String, Sound>();

        String[] keys = {
                FALL, WALK, DIVIDE, DIE
        };
        for (String key: keys) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(SOUND_DIR + key + SOUND_EXT));
            sounds.put(key, sound);
        }
    }

    public static void play(String key) {
        sounds.get(key).play(1f);
    }

    public static void loop(String key) {
        sounds.get(key).loop(1f);
    }

    public static void stop(String key) {
        sounds.get(key).stop();
    }
}
