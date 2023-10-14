package org.fzt.entities;

import com.almasb.fxgl.entity.component.Component;

/**
 * Removes entity from the world if it lifetime ends
 */
public class LifetimeComponent extends Component {
    final double _lifetime;
    double _existsFor = 0;

    /**
     * @param lifetime time in seconds
     */
    public LifetimeComponent(double lifetime) {
        _lifetime = lifetime;
    }

    @Override
    public void onUpdate(double tpf) {
        _existsFor+=tpf;
        if(_existsFor > _lifetime)
            onExpire();
    }

    /**
     * Override this method to define custom deletion effects
     */
    public void onExpire(){
        entity.removeFromWorld();
    }
}
