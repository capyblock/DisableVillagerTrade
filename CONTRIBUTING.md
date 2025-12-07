# Contributing to DisableVillagerTrade

Thank you for your interest in contributing to DisableVillagerTrade! This document provides guidelines and information for contributors.

## Getting Started

1. Fork the repository
2. Clone your fork locally
3. Create a new branch from `develop` for your feature or fix
4. Make your changes
5. Submit a pull request to `develop`

## Branch Strategy

```
develop → master
   ↑
 Your PR
```

| Branch | Purpose |
|--------|---------|
| `develop` | Active development, submit PRs here |
| `master` | Stable production releases |

**Always create PRs targeting `develop`**, not `master`.

## Development Setup

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- A Spigot/Paper test server (optional, for testing)

### Building

```bash
mvn clean package
```

The compiled JAR will be in the `target/` directory.

## Commit Message Convention

This project uses **Conventional Commits** for automatic versioning and changelog generation. Please follow this format for your commit messages:

### Format

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

### Types

| Type | Description | Version Bump |
|------|-------------|--------------|
| `feat` | A new feature | Minor (1.0.0 → 1.1.0) |
| `fix` | A bug fix | Patch (1.0.0 → 1.0.1) |
| `docs` | Documentation only changes | Patch |
| `style` | Code style changes (formatting, etc.) | Patch |
| `refactor` | Code refactoring | Patch |
| `perf` | Performance improvements | Patch |
| `test` | Adding or updating tests | Patch |
| `chore` | Maintenance tasks | Patch |

### Breaking Changes

For breaking changes that require a **major** version bump, either:

- Add `!` after the type: `feat!: remove deprecated API`
- Include `BREAKING CHANGE:` in the commit body

### Examples

```bash
# New feature (minor bump)
feat: add support for wandering traders

# Bug fix (patch bump)
fix: resolve null pointer when config is empty

# Feature with scope
feat(config): add per-biome disable option

# Breaking change (major bump)
feat!: change config format from YAML to JSON

# With body and footer
fix: prevent trade window from opening

The trade window was still briefly visible before closing.
This fix prevents the event entirely.

Closes #42
```

## Pull Request Process

1. **Create a descriptive PR title** following the commit convention format
2. **Fill out the PR template** with relevant information
3. **Ensure all checks pass** (build, tests if applicable)
4. **Request a review** from maintainers

### PR Title Examples

- `feat: add configurable cooldown message`
- `fix: correct world name comparison`
- `docs: update installation instructions`

## Project Structure

```
src/main/java/me/dodo/disablevillagertrade/
├── DisableVillagerTrade.java    # Main plugin class
├── config/
│   └── PluginConfig.java        # Configuration handling
├── listeners/
│   └── VillagerTradeListener.java  # Event listener
└── logic/
    └── TradeBlocker.java        # Core business logic (testable)
```

## Code Style

- Follow existing code conventions in the project
- Use meaningful variable and method names
- Add JavaDocs for public methods
- Keep methods focused and concise
- Separate business logic from Bukkit code for testability

## Testing

Before submitting a PR:

1. Run tests: `mvn test`
2. Build the project: `mvn clean package`
3. Test the plugin on a local Spigot/Paper server
4. Verify your changes work as expected
5. Check for any console errors or warnings

**Note:** All PRs must pass the automated test suite.

## Code Coverage

This project enforces a **minimum 90% code coverage** on testable code.

### Running Coverage Locally

```bash
# Run tests with coverage report
mvn test

# View coverage report
open target/site/jacoco/index.html

# Verify coverage threshold
mvn jacoco:check
```

### Coverage Rules

| Rule | Threshold |
|------|-----------|
| Instruction Coverage | ≥ 90% |

### What's Covered

The following classes must maintain 90%+ coverage:

- `PluginConfig` - Configuration handling
- `ConfigMigrator` - Config migration logic
- `TradeBlocker` - Core trade blocking logic
- `UpdateNotifyListener` - Update notification logic

### What's Excluded

Some classes are excluded from coverage requirements because they depend on Bukkit runtime:

- `DisableVillagerTrade` - Main plugin lifecycle
- `VillagerTradeListener` - Direct Bukkit entity interaction
- `UpdateChecker` - Bukkit scheduler and HTTP calls

### Writing Testable Code

To maintain high coverage:

1. **Separate business logic** from Bukkit API calls (see `TradeBlocker`)
2. **Use dependency injection** for testability
3. **Mock external dependencies** using Mockito
4. **Test edge cases** and error conditions

### Coverage Enforcement

- ❌ PRs will fail CI if coverage drops below 90%
- ✅ Coverage reports are generated on every PR
- 📊 Codecov provides coverage diff on PRs

## Reporting Issues

When reporting bugs, please include:

- Minecraft version
- Server software and version (Spigot, Paper, etc.)
- Plugin version
- Steps to reproduce
- Expected vs actual behavior
- Relevant console logs/errors

## Release Pipeline (For Maintainers)

This project uses a **two-branch model** with automatic releases:

```
develop → master
```

| Branch | Purpose | Version | Published To |
|--------|---------|---------|--------------|
| `develop` | 🔧 Development builds | `1.2.3-dev.456` | GitHub (pre-release) |
| `master` | 🚀 Stable releases | `1.2.3` | GitHub + Modrinth |

### Release Workflow

1. **Develop** on `develop` branch → automatic dev builds on GitHub
2. **Release** by merging `develop` → `master` → stable release to GitHub + Modrinth

### Release Platform Setup

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

## Questions?

Feel free to open an issue for any questions about contributing.

---

Thank you for contributing! 🎉

