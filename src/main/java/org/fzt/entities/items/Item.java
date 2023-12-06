package org.fzt.entities.items;

import com.almasb.fxgl.entity.Entity;
import org.fzt.Assets;
import org.fzt.entities.CharacterStats;

public class Item extends Entity {
    protected CharacterStats _stats;
    private final String _textureName;

    public Item(String textureName, CharacterStats stats){
        _stats = stats;
        _textureName = textureName;
        getViewComponent().addChild(Assets.loadTexture64(textureName));
    }

    public CharacterStats getStats(){
        return _stats;
    }

    @Override
    public String toString() {
        return "{ texture: '"+ _textureName +"', stats: " + _stats + " }";
    }
}
