package core.general;

import com.badlogic.gdx.math.Vector2;
import core.preferences.WindowMode;

public final class Constants {
    public static final class Physics {
        public static final float Scale = 16f;
        public static final Vector2 Gravity = new Vector2(0, -15f);
        public static final float PlayerJumpSpeed = 10f;
        public static final float PlayerMoveSpeed = 10f;
        public static final float EntitiesMoveSpeed = 2f;
        public static final Vector2 DeletedLocation = new Vector2(-100, -100);
        public static final float Friction = 17f;
    }

    public static final class LayerNames {
        public static final String Collision = "collision";
        public static final String CollisionNoJump = "collisionNoJump";
        public static final String Tiles = "tiles";
        public static final String Finishing = "finish";
        public static final String Deadly = "deadly";
        public static final String Entities = "entities";
        public static final String BodyEntities = "bodyEntities";
        public static final String Checkpoint = "checkpoint";
    }

    public static final class LevelNames {
        public static final String Prefix = "map/";
        public static final String[] List = new String[]{
                "map.tmx",
                "map2.tmx",
                "newMap.tmx",
        };
        public static final String Background = "background.tmx";
    }

    public static final class Preferences {
        public static final float DEFAULT_MUSIC_VOLUME = 0.5f;
        public static final float DEFAULT_SOUND_VOLUME = 0.5f;
        public static final boolean DEFAULT_MUSIC_ENABLED = true;
        public static final boolean DEFAULT_SOUND_ENABLED = true;

        public static final boolean DEFAULT_FULLSCREEN_ENABLED = false;
        public static final int DEFAULT_WINDOW_WIDTH = 800;
        public static final int DEFAULT_WINDOW_HEIGHT = 600;
        public static final int DEFAULT_FPS = 60;

        public static final boolean DEFAULT_VSYNC = true;

        public static final String DEFAULT_USER_NAME = "Anon";
        public static final WindowMode[] WINDOW_MODES = {
                new WindowMode(800, 600),
                new WindowMode(1280, 720),
                new WindowMode(1920, 1080),
                new WindowMode(2560, 1440),
        };

        public static final Integer[] FPS_OPTIONS = {60, 120};

        public static final String SERVER_URL = "http://20.123.61.66:8080";
    }
}
