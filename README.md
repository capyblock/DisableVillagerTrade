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
| Modrinth | [Download](https://modrinth.com/plugin/disablevillagertrade) |

## Release Pipeline

This project uses a **three-stage release pipeline** with automated deployments:

```
develop → pre-release → master (production)
```

### Branches & Release Types

| Branch | Release Type | Version Format | Published To |
|--------|--------------|----------------|--------------|
| `develop` | 🔧 Development | `1.2.3-SNAPSHOT.123` | GitHub only |
| `pre-release` | 🧪 Pre-Release (RC) | `1.2.3-RC.123` | GitHub + Modrinth (beta) |
| `master` | 🚀 Production | `1.2.3` | GitHub + Modrinth (release) |

### Development Workflow

1. **Development**: Work on `develop` branch
   - Automatic snapshot releases for testing
   - Not published to Modrinth

2. **Pre-Release**: Merge `develop` → `pre-release`
   - Creates Release Candidate (RC) version
   - Published to Modrinth as **beta**
   - Test on staging servers

3. **Production**: Merge `pre-release` → `master`
   - Creates stable production release
   - Published to Modrinth as **release**
   - Version bumped permanently

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
