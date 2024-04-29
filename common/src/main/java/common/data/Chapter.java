package common.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Chapter class
 */
public class Chapter implements Serializable {
    /**
     * Chapter class construction.
     * @param name
     * @param world
     */
    public Chapter(String name, String world) {
        this.name = name;
        this.world = world;
    }

    /**
     * Name of a chapter.
     */
    private final String name;
    /**
     * World of a chapter.
     */
    private final String world;

    /**
     * Chapter name getter.
     * @return Name of a chapter
     */
    public String getName() {
        return name;
    }

    /**
     * Chapter world getter.
     * @return World of a chapter
     */
    public String getWorld() {
        return world;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Chapter other = (Chapter) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        return this.world.equals(other.world);
    }

}
