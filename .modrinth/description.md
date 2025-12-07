# Disable Villager Trade

A lightweight Spigot/Paper plugin that prevents players from trading with villagers on your Minecraft server.

## ✨ Features

- **Block Villager Trading** - Prevents players from opening the villager trade interface
- **Configurable Messages** - Customize the message shown when trading is blocked
- **Bypass Permission** - Allow staff to trade with `disabletrade.bypass` permission
- **Per-World Support** - Disable trading only in specific worlds
- **Profession Filter** - Villagers with no profession (NONE) can still be interacted with

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

