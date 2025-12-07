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

## Code Style

- Follow existing code conventions in the project
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise

## Testing

Before submitting a PR:

1. Build the project successfully: `mvn clean package`
2. Test the plugin on a local Spigot/Paper server
3. Verify your changes work as expected
4. Check for any console errors or warnings

## Reporting Issues

When reporting bugs, please include:

- Minecraft version
- Server software and version (Spigot, Paper, etc.)
- Plugin version
- Steps to reproduce
- Expected vs actual behavior
- Relevant console logs/errors

## Questions?

Feel free to open an issue for any questions about contributing.

---

Thank you for contributing! 🎉

