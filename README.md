# Disable Villager Trade

[![Build](https://github.com/capyblock/DisableVillagerTrade/actions/workflows/test.yml/badge.svg)](https://github.com/capyblock/DisableVillagerTrade/actions/workflows/test.yml)
[![GitHub Release](https://img.shields.io/github/v/release/capyblock/DisableVillagerTrade?label=release)](https://github.com/capyblock/DisableVillagerTrade/releases)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/disable-villager-trade?logo=modrinth&label=modrinth)](https://modrinth.com/plugin/disable-villager-trade)
[![License](https://img.shields.io/github/license/capyblock/DisableVillagerTrade)](LICENSE)

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

## Permissions
| Permission | Description |
|------------|-------------|
| `disabletrade.bypass` | Allows the player to trade with villagers |

## Building

```bash
mvn clean package
```

## Testing

Run the test suite:

```bash
mvn test
```

The project uses:
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework

Tests are automatically run on every push and pull request via GitHub Actions.

## Downloads

| Platform | Link |
|----------|------|
| GitHub Releases | [Download](https://github.com/capyblock/DisableVillagerTrade/releases) |
| Modrinth | [Download](https://modrinth.com/plugin/disable-villager-trade) |

## Release Pipeline

This project uses a simple **two-branch model**:

```
develop → master
```

### Branches

| Branch | Purpose | Version | Published To |
|--------|---------|---------|--------------|
| `develop` | 🔧 Development builds | `1.2.3-dev.456` | GitHub (pre-release) |
| `master` | 🚀 Stable releases | `1.2.3` | GitHub + Modrinth |

### Workflow

1. **Develop** on `develop` branch → automatic dev builds on GitHub
2. **Release** by merging `develop` → `master` → stable release to GitHub + Modrinth

### Version Bumping

| Commit Type | Example | Version Change |
|-------------|---------|----------------|
| `fix:` | `fix: resolve config loading error` | 1.6.0 → 1.6.1 (Patch) |
| `feat:` | `feat: add wandering trader support` | 1.6.0 → 1.7.0 (Minor) |
| `feat!:` or `BREAKING CHANGE:` | `feat!: new config format` | 1.6.0 → 2.0.0 (Major) |

### Release Platform Setup (For Maintainers)

<details>
<summary><b>🔧 Setup Instructions</b></summary>

#### Repository Variables (Settings → Secrets and variables → Actions → Variables)

| Variable | Description |
|----------|-------------|
| `MODRINTH_PROJECT_ID` | Your Modrinth project ID (found in project URL or settings) |

#### Repository Secrets (Settings → Secrets and variables → Actions → Secrets)

| Secret | How to Get |
|--------|------------|
| `MODRINTH_TOKEN` | [Modrinth Settings](https://modrinth.com/settings/pats) → Create PAT with `Write projects` scope |

</details>

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on:

- Commit message conventions
- Pull request process
- Development setup

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
