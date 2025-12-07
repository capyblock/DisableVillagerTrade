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

## Downloads

| Platform | Link |
|----------|------|
| GitHub Releases | [Download](https://github.com/capyblock/DisableVillagerTrade/releases) |
| Modrinth | Coming soon |

## Releases

This project uses automated releases via GitHub Actions. When changes are pushed or merged to the `master` branch:

1. The version is automatically bumped based on [Conventional Commits](https://www.conventionalcommits.org/)
2. A new GitHub Release is created with the compiled JAR
3. The plugin is automatically published to **Modrinth**
4. A changelog is automatically generated

### Version Bumping

| Commit Type | Example | Version Change |
|-------------|---------|----------------|
| `fix:` | `fix: resolve config loading error` | 1.6.0 → 1.6.1 (Patch) |
| `feat:` | `feat: add wandering trader support` | 1.6.0 → 1.7.0 (Minor) |
| `feat!:` or `BREAKING CHANGE:` | `feat!: new config format` | 1.6.0 → 2.0.0 (Major) |

### Release Platform Setup (For Maintainers)

To enable auto-publishing to Modrinth, configure the following in your GitHub repository:

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
