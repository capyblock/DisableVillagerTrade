# Disable Villager Trade

## Overview

DisableVillagerTrade is a lightweight plugin crafted to block trading interactions exclusively with villagers while preserving interactions with other non-player characters (NPCs). This ensures a balanced and immersive gameplay experience on your server.

## ✨ Key Features

- **Custom Interaction Messages** - Personalize interaction messages to match your server's tone and style, enhancing player engagement.
- **Toggle Interaction Messages** - Easily enable or disable interaction messages according to server preferences.
- **World-Specific Disablement** - Fine-tune gameplay dynamics by enabling or disabling the plugin on a per-world basis, granting precise control over trading functionalities.
- **Bypass Permission** - Allow staff to trade with `disabletrade.bypass` permission.
- **Smart Profession Detection** - Villagers with no profession (NONE) can still be interacted with.

## 📦 Installation

1. Download the latest version
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure in `plugins/DisableVillagerTrade/config.yml`

## ⚙️ Configuration

```yaml
# Message settings
message:
  enabled: true
  text: "&cYou can't trade with villagers on this server."

# Worlds where trading is ALLOWED
disabled-worlds:
  - example-world
```

## 🔑 Permissions

| Permission | Description |
|------------|-------------|
| `disabletrade.bypass` | Allows the player to trade with villagers |

## 💻 Supported Versions

- **Minecraft**: 1.14 - 1.21.4+ (and future versions)
- **Server Software**: Spigot, Paper, Purpur, and other Spigot-based forks
- **Java**: 21+

## 🔗 Links

- [Source Code](https://github.com/capyblock/DisableVillagerTrade)
- [Issue Tracker](https://github.com/capyblock/DisableVillagerTrade/issues)
- [Changelog](https://github.com/capyblock/DisableVillagerTrade/releases)

## 📄 License

This project is open source under the [MIT License](https://github.com/capyblock/DisableVillagerTrade/blob/master/LICENSE).

