package me.dodo.disablevillagertrade.forge;

import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.common.TradeBlocker;
import me.dodo.disablevillagertrade.common.UpdateChecker;
import me.dodo.disablevillagertrade.forge.config.ForgeConfig;
import me.dodo.disablevillagertrade.forge.events.VillagerTradeHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main mod class for DisableVillagerTrade (Forge).
 */
@Mod(Constants.MOD_ID)
public class DisableVillagerTradeForge {
    
    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_NAME);
    
    private static DisableVillagerTradeForge instance;
    private static ForgeConfig config;
    private static TradeBlocker tradeBlocker;
    private static UpdateChecker updateChecker;
    private static ScheduledExecutorService scheduler;
    
    public DisableVillagerTradeForge(ModContainer modContainer) {
        instance = this;
        
        LOGGER.info("Initializing DisableVillagerTrade for Forge...");
        
        // Register config
        modContainer.registerConfig(ModConfig.Type.SERVER, ForgeConfig.SPEC, Constants.MOD_ID + "-server.toml");
        config = new ForgeConfig();
        
        // Initialize trade blocker
        tradeBlocker = new TradeBlocker();
        
        // Register event handlers using new Forge 60.x API
        BusGroup.DEFAULT.register(MethodHandles.lookup(), this);
        BusGroup.DEFAULT.register(MethodHandles.lookup(), new VillagerTradeHandler());
        
        LOGGER.info("DisableVillagerTrade initialized!");
    }
    
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        // Initialize update checker
        if (ForgeConfig.UPDATE_CHECKER_ENABLED.get()) {
            String version = ModList.get()
                .getModContainerById(Constants.MOD_ID)
                .map(c -> c.getModInfo().getVersion().toString())
                .orElse("unknown");
            
            updateChecker = new UpdateChecker(
                version,
                "DisableVillagerTrade-Forge/" + version,
                msg -> LOGGER.info(msg)
            );
            
            // Start periodic update checks
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(
                () -> updateChecker.checkForUpdates(),
                0,
                ForgeConfig.UPDATE_CHECK_INTERVAL.get(),
                TimeUnit.HOURS
            );
        }
    }
    
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
    
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (ForgeConfig.NOTIFY_ON_JOIN.get() && updateChecker != null && updateChecker.isUpdateAvailable()) {
                // Check if player has update permission (op level 2+)
                if (player.hasPermissions(2)) {
                    String message = ForgeConfig.UPDATE_MESSAGE.get()
                        .replace("%current%", updateChecker.getCurrentVersion())
                        .replace("%latest%", updateChecker.getLatestVersion());
                    
                    player.sendSystemMessage(Component.literal(message));
                }
            }
        }
    }
    
    public static DisableVillagerTradeForge getInstance() {
        return instance;
    }
    
    public static ForgeConfig getConfig() {
        return config;
    }
    
    public static TradeBlocker getTradeBlocker() {
        return tradeBlocker;
    }
}
