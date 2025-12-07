package me.dodo.disablevillagertrade.common;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TradeBlocker - Core Business Logic Tests")
class TradeBlockerTest {
    
    private TradeBlocker tradeBlocker;
    
    @BeforeEach
    void setUp() {
        tradeBlocker = new TradeBlocker();
    }
    
    @Nested
    @DisplayName("Non-Villager Entities")
    class NonVillagerTests {
        
        @Test
        @DisplayName("Should NOT block interaction with non-villager entities")
        void shouldNotBlockNonVillager() {
            boolean result = tradeBlocker.shouldBlockTrade(
                false,  // not a villager
                "FARMER",
                true,
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertFalse(result, "Non-villager entities should never be blocked");
        }
    }
    
    @Nested
    @DisplayName("Villager Profession Tests")
    class ProfessionTests {
        
        @Test
        @DisplayName("Should NOT block NONE profession villagers (unemployed)")
        void shouldNotBlockNoneProfession() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "NONE",  // unemployed villager
                true,
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertFalse(result, "NONE profession villagers should be interactable");
        }
        
        @ParameterizedTest
        @DisplayName("Should block villagers with professions")
        @ValueSource(strings = {"FARMER", "LIBRARIAN", "ARMORER", "BUTCHER", "CARTOGRAPHER", 
                               "CLERIC", "FISHERMAN", "FLETCHER", "LEATHERWORKER", 
                               "MASON", "SHEPHERD", "TOOLSMITH", "WEAPONSMITH"})
        void shouldBlockProfessionalVillagers(String profession) {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                profession,
                true,
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertTrue(result, "Villagers with " + profession + " profession should be blocked");
        }
        
        @Test
        @DisplayName("Should NOT block NITWIT villagers")
        void shouldHandleNitwitVillager() {
            // NITWIT villagers can't trade anyway, but we still block them
            // as they're not NONE profession
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "NITWIT",
                true,
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertTrue(result, "NITWIT villagers should be blocked (not NONE profession)");
        }
    }
    
    @Nested
    @DisplayName("Bypass Permission Tests")
    class BypassPermissionTests {
        
        @Test
        @DisplayName("Should NOT block when player has bypass permission")
        void shouldNotBlockWithBypassPermission() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "world",
                Collections.emptyList(),
                true  // has bypass permission
            );
            
            assertFalse(result, "Players with bypass permission should be able to trade");
        }
        
        @Test
        @DisplayName("Should block when player does NOT have bypass permission")
        void shouldBlockWithoutBypassPermission() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "world",
                Collections.emptyList(),
                false  // no bypass permission
            );
            
            assertTrue(result, "Players without bypass permission should be blocked");
        }
    }
    
    @Nested
    @DisplayName("Disabled Worlds Tests")
    class DisabledWorldsTests {
        
        @Test
        @DisplayName("Should NOT block in disabled worlds (trading allowed)")
        void shouldNotBlockInDisabledWorld() {
            List<String> disabledWorlds = Arrays.asList("spawn", "lobby");
            
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "spawn",  // this world is in disabled list
                disabledWorlds,
                false
            );
            
            assertFalse(result, "Trading should be allowed in disabled worlds");
        }
        
        @Test
        @DisplayName("Should block in worlds NOT in disabled list")
        void shouldBlockInEnabledWorld() {
            List<String> disabledWorlds = Arrays.asList("spawn", "lobby");
            
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "survival",  // this world is NOT in disabled list
                disabledWorlds,
                false
            );
            
            assertTrue(result, "Trading should be blocked in non-disabled worlds");
        }
        
        @Test
        @DisplayName("Should handle empty disabled worlds list")
        void shouldHandleEmptyDisabledWorldsList() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertTrue(result, "Should block when disabled worlds list is empty");
        }
        
        @Test
        @DisplayName("Should handle null disabled worlds list")
        void shouldHandleNullDisabledWorldsList() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                true,
                "world",
                null,
                false
            );
            
            assertTrue(result, "Should block when disabled worlds list is null");
        }
    }
    
    @Nested
    @DisplayName("Villager AI and Gravity Tests")
    class AIAndGravityTests {
        
        @Test
        @DisplayName("Should NOT block villagers without AI (e.g., NPC plugins)")
        void shouldNotBlockVillagerWithoutAI() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                false,  // no AI
                true,
                "world",
                Collections.emptyList(),
                false
            );
            
            assertFalse(result, "Villagers without AI should not be blocked (NPC plugins)");
        }
        
        @Test
        @DisplayName("Should NOT block villagers without gravity")
        void shouldNotBlockVillagerWithoutGravity() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,
                false,  // no gravity
                "world",
                Collections.emptyList(),
                false
            );
            
            assertFalse(result, "Villagers without gravity should not be blocked");
        }
        
        @Test
        @DisplayName("Should block normal villagers with AI and gravity")
        void shouldBlockNormalVillager() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "FARMER",
                true,  // has AI
                true,  // has gravity
                "world",
                Collections.emptyList(),
                false
            );
            
            assertTrue(result, "Normal villagers with AI and gravity should be blocked");
        }
    }
    
    @Nested
    @DisplayName("Combined Conditions Tests")
    class CombinedConditionsTests {
        
        @Test
        @DisplayName("Bypass permission should override all other conditions")
        void bypassOverridesEverything() {
            // Even with all conditions that would normally block,
            // bypass permission should allow trading
            boolean result = tradeBlocker.shouldBlockTrade(
                true,       // is villager
                "FARMER",   // has profession
                true,       // has AI
                true,       // has gravity
                "survival", // not in disabled worlds
                Collections.emptyList(),
                true        // HAS bypass permission
            );
            
            assertFalse(result, "Bypass permission should override all blocking conditions");
        }
        
        @Test
        @DisplayName("NONE profession should allow trading regardless of other conditions")
        void noneProfessionAllowsTrading() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,
                "NONE",     // NONE profession
                true,
                true,
                "survival",
                Collections.emptyList(),
                false       // no bypass permission
            );
            
            assertFalse(result, "NONE profession should allow trading");
        }
        
        @Test
        @DisplayName("All blocking conditions met should block trade")
        void allConditionsBlockTrade() {
            boolean result = tradeBlocker.shouldBlockTrade(
                true,           // is villager
                "LIBRARIAN",    // has profession
                true,           // has AI
                true,           // has gravity
                "survival",     // not in disabled worlds
                Arrays.asList("spawn"),
                false           // no bypass permission
            );
            
            assertTrue(result, "Trade should be blocked when all conditions are met");
        }
    }
}

