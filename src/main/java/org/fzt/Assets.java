package org.fzt;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;

public class Assets {

    /**
     *
     * @param path to the texture file in assets/textures/
     * @return Texture with size 64x64 pixels
     */
    public static Texture loadTexture64(String path){
        return FXGL.getAssetLoader().loadTexture(path, 64, 64);
    }
}
