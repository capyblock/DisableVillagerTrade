package me.dodo.disablevillagertrade.events;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.dodo.disablevillagertrade.DisableVillagerTrade;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PlayerInteractEntity Event Tests")
class PlayerInteractEntityTest {
    
    private ServerMock server;
    private DisableVillagerTrade plugin;
    private PlayerMock player;
    private World world;
    
    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DisableVillagerTrade.class);
        player = server.addPlayer("TestPlayer");
        world = server.addSimpleWorld("world");
    }
    
    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
    
    @Test
    @DisplayName("Should cancel trade with professional villager")
    void shouldCancelTradeWithProfessionalVillager() {
        // Create a mock villager with a profession
        Villager villager = mock(Villager.class);
        when(villager.getType()).thenReturn(EntityType.VILLAGER);
        when(villager.getProfession()).thenReturn(Villager.Profession.FARMER);
        when(villager.hasAI()).thenReturn(true);
        when(villager.hasGravity()).thenReturn(true);
        when(villager.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        
        // Create the event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, villager, EquipmentSlot.HAND);
        
        // Create listener and handle event
        PlayerInteractEntity listener = new PlayerInteractEntity();
        listener.onPlayerInteractEntity(event);
        
        // Verify event was cancelled
        assertTrue(event.isCancelled(), "Trade with professional villager should be cancelled");
    }
    
    @Test
    @DisplayName("Should allow trade with NONE profession villager")
    void shouldAllowTradeWithNoneProfessionVillager() {
        // Create a mock villager with NONE profession
        Villager villager = mock(Villager.class);
        when(villager.getType()).thenReturn(EntityType.VILLAGER);
        when(villager.getProfession()).thenReturn(Villager.Profession.NONE);
        when(villager.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        
        // Create the event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, villager, EquipmentSlot.HAND);
        
        // Create listener and handle event
        PlayerInteractEntity listener = new PlayerInteractEntity();
        listener.onPlayerInteractEntity(event);
        
        // Verify event was NOT cancelled
        assertFalse(event.isCancelled(), "Trade with NONE profession villager should be allowed");
    }
    
    @Test
    @DisplayName("Should allow trade for player with bypass permission")
    void shouldAllowTradeForPlayerWithBypassPermission() {
        // Give player bypass permission
        player.addAttachment(plugin, "disabletrade.bypass", true);
        
        // Create a mock villager with a profession
        Villager villager = mock(Villager.class);
        when(villager.getType()).thenReturn(EntityType.VILLAGER);
        when(villager.getProfession()).thenReturn(Villager.Profession.FARMER);
        when(villager.hasAI()).thenReturn(true);
        when(villager.hasGravity()).thenReturn(true);
        when(villager.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        
        // Create the event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, villager, EquipmentSlot.HAND);
        
        // Create listener and handle event
        PlayerInteractEntity listener = new PlayerInteractEntity();
        listener.onPlayerInteractEntity(event);
        
        // Verify event was NOT cancelled
        assertFalse(event.isCancelled(), "Trade should be allowed for player with bypass permission");
    }
    
    @Test
    @DisplayName("Should not affect non-villager entities")
    void shouldNotAffectNonVillagerEntities() {
        // Create a mock pig (non-villager)
        org.bukkit.entity.Pig pig = mock(org.bukkit.entity.Pig.class);
        when(pig.getType()).thenReturn(EntityType.PIG);
        when(pig.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        
        // Create the event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, pig, EquipmentSlot.HAND);
        
        // Create listener and handle event
        PlayerInteractEntity listener = new PlayerInteractEntity();
        listener.onPlayerInteractEntity(event);
        
        // Verify event was NOT cancelled
        assertFalse(event.isCancelled(), "Non-villager entities should not be affected");
    }
    
    @Test
    @DisplayName("Should allow trade with villager without AI")
    void shouldAllowTradeWithVillagerWithoutAI() {
        // Create a mock villager without AI (e.g., armor stand villager)
        Villager villager = mock(Villager.class);
        when(villager.getType()).thenReturn(EntityType.VILLAGER);
        when(villager.getProfession()).thenReturn(Villager.Profession.FARMER);
        when(villager.hasAI()).thenReturn(false);
        when(villager.hasGravity()).thenReturn(true);
        when(villager.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        
        // Create the event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, villager, EquipmentSlot.HAND);
        
        // Create listener and handle event
        PlayerInteractEntity listener = new PlayerInteractEntity();
        listener.onPlayerInteractEntity(event);
        
        // Verify event was NOT cancelled (villager without AI should be allowed)
        assertFalse(event.isCancelled(), "Villager without AI should allow interaction");
    }
}

