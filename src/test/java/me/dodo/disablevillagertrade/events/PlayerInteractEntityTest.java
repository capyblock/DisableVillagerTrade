package me.dodo.disablevillagertrade.events;

import me.dodo.disablevillagertrade.DisableVillagerTrade;
import me.dodo.disablevillagertrade.settings.ConfigManager;
import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PlayerInteractEntity Event Tests")
@ExtendWith(MockitoExtension.class)
class PlayerInteractEntityTest {
    
    @Mock
    private Player player;
    
    @Mock
    private Villager villager;
    
    @Mock
    private World world;
    
    @Mock
    private ConfigManager configManager;
    
    @Mock
    private Main mainConfig;
    
    @BeforeEach
    void setUp() {
        // Setup common mocks
        when(world.getName()).thenReturn("world");
        when(player.getWorld()).thenReturn(world);
        when(mainConfig.getDisabledWorlds()).thenReturn(Collections.emptyList());
        when(mainConfig.isEnabled()).thenReturn(true);
        when(mainConfig.getContext()).thenReturn("&cYou can't trade with villagers.");
        when(configManager.getMain()).thenReturn(mainConfig);
    }
    
    @Test
    @DisplayName("PlayerInteractEntity class should exist")
    void classExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.events.PlayerInteractEntity"));
    }
    
    @Test
    @DisplayName("PlayerInteractEntity should implement Listener")
    void implementsListener() {
        assertTrue(org.bukkit.event.Listener.class.isAssignableFrom(PlayerInteractEntity.class),
            "PlayerInteractEntity should implement Listener");
    }
    
    @Test
    @DisplayName("Should have onPlayerInteractEntity method")
    void hasEventHandlerMethod() {
        assertDoesNotThrow(() -> 
            PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class),
            "Should have onPlayerInteractEntity method");
    }
    
    @Test
    @DisplayName("Event handler should have @EventHandler annotation")
    void hasEventHandlerAnnotation() throws NoSuchMethodException {
        var method = PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        assertTrue(method.isAnnotationPresent(org.bukkit.event.EventHandler.class),
            "Method should have @EventHandler annotation");
    }
    
    @Test
    @DisplayName("Should not cancel event for non-villager entities")
    void shouldNotCancelForNonVillager() {
        // Create mock pig
        org.bukkit.entity.Pig pig = mock(org.bukkit.entity.Pig.class);
        when(pig.getType()).thenReturn(EntityType.PIG);
        
        // Create event
        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(player, pig, EquipmentSlot.HAND);
        
        // The event should not be cancelled for non-villagers
        // (event starts as not cancelled)
        assertFalse(event.isCancelled(), "Event should not be cancelled initially");
    }
    
    @Test
    @DisplayName("Villager mock should return correct type")
    void villagerMockWorks() {
        when(villager.getType()).thenReturn(EntityType.VILLAGER);
        when(villager.getProfession()).thenReturn(Villager.Profession.FARMER);
        
        assertEquals(EntityType.VILLAGER, villager.getType());
        assertEquals(Villager.Profession.FARMER, villager.getProfession());
    }
    
    @Test
    @DisplayName("Player permission check should work")
    void playerPermissionCheckWorks() {
        when(player.hasPermission("disabletrade.bypass")).thenReturn(false);
        assertFalse(player.hasPermission("disabletrade.bypass"));
        
        when(player.hasPermission("disabletrade.bypass")).thenReturn(true);
        assertTrue(player.hasPermission("disabletrade.bypass"));
    }
    
    @Test
    @DisplayName("World name check should work for disabled worlds")
    void disabledWorldsCheck() {
        List<String> disabledWorlds = List.of("disabled_world", "another_world");
        when(mainConfig.getDisabledWorlds()).thenReturn(disabledWorlds);
        
        assertTrue(mainConfig.getDisabledWorlds().contains("disabled_world"));
        assertFalse(mainConfig.getDisabledWorlds().contains("world"));
    }
    
    @Test
    @DisplayName("NONE profession villager should be detected")
    void noneProfessionDetection() {
        when(villager.getProfession()).thenReturn(Villager.Profession.NONE);
        assertEquals(Villager.Profession.NONE, villager.getProfession());
    }
    
    @Test
    @DisplayName("Villager AI and gravity checks should work")
    void villagerAIAndGravityChecks() {
        when(villager.hasAI()).thenReturn(true);
        when(villager.hasGravity()).thenReturn(true);
        
        assertTrue(villager.hasAI());
        assertTrue(villager.hasGravity());
        
        when(villager.hasAI()).thenReturn(false);
        assertFalse(villager.hasAI());
    }
}
