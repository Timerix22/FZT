package org.fzt;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;

/**
 * Contains functions to deal with all kinds of assets
 */
public class Assets {

    /**
     * @param path to the texture file in assets/textures/
     * @return Texture with size 64x64 pixels
     */
    public static Texture loadTexture64(String path){
        return loadTexture(path, 64, 64);
    }
    public static Texture loadTexture(String path, double w, double h){
        return FXGL.getAssetLoader().loadTexture(path, w, h);
    }
}
