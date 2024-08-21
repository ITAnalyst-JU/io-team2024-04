package core.utilities;

import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final class Physics {
        public static final float Scale = 16f;
        public static final Vector2 Gravity = new Vector2(0, -15f);
    }

    public static final class LayerNames {
        public static final String Collision = "collision";
        public static final String Tiles = "tiles";
        public static final String Finishing = "finish";
        public static final String Deadly = "deadly";
        public static final String Entities = "Entities Layer";
    }
}
