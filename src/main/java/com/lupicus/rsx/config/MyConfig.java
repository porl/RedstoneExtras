package com.lupicus.rsx.config;

import org.apache.commons.lang3.tuple.Pair;

import com.lupicus.rsx.Main;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class MyConfig
{
	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	static
	{
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static double energyFactor;

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent)
	{
		if (configEvent.getConfig().getSpec() == MyConfig.SERVER_SPEC)
		{
			bakeConfig();
		}
	}

	public static void bakeConfig()
	{
		energyFactor = SERVER.energyFactor.get();
	}

	public static class Server
	{
		public final DoubleValue energyFactor;

		public Server(ForgeConfigSpec.Builder builder)
		{
			String baseTrans = Main.MODID + ".config.";
			String sectionTrans;
			sectionTrans = baseTrans + "general.";

			energyFactor = builder
					.comment("Energy Factor")
					.translation(sectionTrans + "energy_factor")
					.defineInRange("EnergyFactor", () -> 0.3, 0.0, 1.0);
		}
	}
}
