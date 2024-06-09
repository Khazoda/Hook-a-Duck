package com.seacroak.duck.entity;

import com.seacroak.duck.Constants;
import com.seacroak.duck.registry.MainRegistry;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class DuckBoats {
    public static final Identifier DUCK_BOAT_ID = Identifier.of(Constants.DUCK_ID, "duck_boat");

    public static final RegistryKey<TerraformBoatType> DUCK_BOAT_KEY = TerraformBoatTypeRegistry.createKey(DUCK_BOAT_ID);

    public static void registerBoats(){
        TerraformBoatType duckBoat = new TerraformBoatType.Builder()
                .item(MainRegistry.DUCK_BOAT)
                .build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE,DUCK_BOAT_KEY,duckBoat);


    }

}
