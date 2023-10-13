package org.fzt.entities.npc;

import com.almasb.fxgl.entity.component.Component;

public abstract class AIComponent extends Component {
    // sets onUpdate() necessary to override in child classes
    @Override
    public abstract void onUpdate(double tpf);
}
