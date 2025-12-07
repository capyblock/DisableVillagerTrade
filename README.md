# Disable Villager Trade

[![Build](https://github.com/dodoflix/DisableVillagerTrade/actions/workflows/test.yml/badge.svg)](https://github.com/dodoflix/DisableVillagerTrade/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/dodoflix/DisableVillagerTrade/graph/badge.svg)](https://codecov.io/gh/dodoflix/DisableVillagerTrade)
[![GitHub Release](https://img.shields.io/github/v/release/dodoflix/DisableVillagerTrade?label=release)](https://github.com/dodoflix/DisableVillagerTrade/releases)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/disable-villager-trade?logo=modrinth&label=modrinth)](https://modrinth.com/plugin/disable-villager-trade)
[![License](https://img.shields.io/github/license/dodoflix/DisableVillagerTrade)](LICENSE)

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
- Automatic update checker with notifications
- Admin commands for management

## Installation
1. Download the latest release
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure in `plugins/DisableVillagerTrade/config.yml`

## Configuration

```yaml
# Message settings
message:
  # Whether to show a message when trading is blocked
  enabled: true
  # The message to show (supports color codes with &)
  text: "&cYou can't trade with villagers on this server."

# Worlds where villager trading is ALLOWED (not blocked)
disabled-worlds:
  - example-world
```

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/disabletrade reload` | Reload the plugin configuration | `disabletrade.admin` |
| `/disabletrade status` | Show plugin status and settings | `disabletrade.admin` |
| `/disabletrade toggle [player]` | Check bypass permission status | `disabletrade.admin` |
| `/disabletrade help` | Show help message | `disabletrade.admin` |

**Aliases:** `/dvt`, `/tradetoggle`

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `disabletrade.admin` | Access to all admin commands | OP |
| `disabletrade.bypass` | Allows the player to bypass trade block | OP |
| `disabletrade.update` | Receives update notifications on join | OP |

## Downloads

| Platform | Link |
|----------|------|
| GitHub Releases | [Download](https://github.com/dodoflix/DisableVillagerTrade/releases) |
| Modrinth | [Download](https://modrinth.com/plugin/disable-villager-trade) |

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on:

- Commit message conventions
- Pull request process
- Development setup

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
