package com.bbr.game.Utils;

import com.badlogic.gdx.physics.box2d.Body;

public interface Collider {
    public default void collide(Object body){};
}
