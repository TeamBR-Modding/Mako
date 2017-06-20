package com.teambr.mako.api.mako;

import com.teambr.mako.utils.Reference;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class MakoRegistry {

    private static MakoRegistry ourInstance = new MakoRegistry();

    public static MakoRegistry getInstance() {
        return ourInstance;
    }

    private HashMap<String, IMako> makoRegistry;

    private MakoRegistry() {
        makoRegistry = new HashMap<>();
        this.addMako(new SimpleMako("air", new ResourceLocation(Reference.MODID, "icons/air"), 0));
        this.addMako(new SimpleMako("water", new ResourceLocation(Reference.MODID, "icons/water"), 0));
        this.addMako(new SimpleMako("nature", new ResourceLocation(Reference.MODID, "icons/nature"), 0));
        this.addMako(new SimpleMako("fire", new ResourceLocation(Reference.MODID, "icons/fire"), 0));
        this.addMako(new CombinedMako("ice", null, 0, this.getMako("water"), this.getMako("air")));
        this.addMako(new CombinedMako("storm", null, 0, this.getMako("fire"), this.getMako("air")));
        this.addMako(new CombinedMako("mystic", null, 0, this.getMako("nature"), this.getMako("water")));
        this.addMako(new CombinedMako("magma", null, 0, this.getMako("nature"), this.getMako("fire")));
    }

    public void addMako(IMako mako) {
        if (!makoRegistry.containsKey(mako.getName())) {
            makoRegistry.put(mako.getName(), mako);
        }
    }

    public IMako getMako(String name) {
        if (makoRegistry.containsKey(name)) {
            return makoRegistry.get(name);
        }
        return null;
    }
}