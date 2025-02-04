/*
 * Copyright (c) 2022.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package exposed.hydrogen.resources;

import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.platforms.spigot.SpigotChameleon;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ResourcesSpigot extends JavaPlugin {
    @Getter private static ResourcesSpigot instance;
    private SpigotChameleon chameleon;
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new ResourcePackSendListener(), this);
        try {
            chameleon = new SpigotChameleon(Resources.class, this, Resources.getPluginData());
            chameleon.onEnable();
        } catch (ChameleonInstantiationException ex) {
            ex.printStackTrace();
        }
        /*if(!MinecraftServer.getServer()
                .R()
                .orElse(new MinecraftServer.ServerResourcePackInfo("","",false,null))
                .a().isEmpty()
        ) {
            chameleon.getLogger().warn("Please clear the resource-pack and resource-pack-sha1 properties from server.properties");
        }*/

    }

    @Override
    public void onDisable() {
        chameleon = null;
    }
}
