# Disable Villager Trade
Disable villager trade on your minecraft spigot based server!

## Supported Versions
- **Minecraft**: 1.14 - 1.21.4+ (and future versions)
- **Server Software**: Spigot, Paper, Purpur, and other Spigot-based forks
- **Java**: 21+

## Features
- Prevent players from trading with villagers
- Configurable message when trading is blocked
- Bypass permission (`disabletrade.bypass`) for staff
- Per-world disable configuration
- Villagers with no profession (NONE) can still be interacted with

## Installation
1. Download the latest release
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure in `plugins/DisableVillagerTrade/config.yml`

## Configuration
```yaml
message:
  enabled: true
  context: "&cYou can't trade with villagers on this server."
disabled-worlds:
  - example-world
```

## Permissions
| Permission | Description |
|------------|-------------|
| `disabletrade.bypass` | Allows the player to trade with villagers |

## Building
```bash
mvn clean package
```

# LICENSE
This project is under MIT License.
