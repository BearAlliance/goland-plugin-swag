# goland-plugin-swag

GoLand plugin for [swag](https://github.com/swaggo/swag) comment syntax highlighting.

Highlights `@Annotation` tags in Go comments so swag annotations like `@Summary`, `@Router`, `@Param`, etc. stand out visually from regular comments.

## Features

- Highlights swag annotation tags (`@Summary`, `@Description`, `@Router`, etc.)
- Highlights HTTP methods in `@Router` annotations (e.g., `[get]`)
- Highlights status codes in `@Success` / `@Failure` annotations
- Highlights parameter names in `@Param` annotations
- Customizable colors under **Settings > Editor > Color Scheme > Swag**
- Supports all swag annotations including `@x-` extensions and `@securityDefinitions.*`

## Installation

Install the plugin from the built zip file:

1. Build the plugin (see below) or download a release
2. In GoLand, go to **Settings > Plugins > ⚙️ > Install Plugin from Disk...**
3. Select `build/distributions/goland-plugin-swag-1.0.0.zip`
4. Restart the IDE

## Local Development

### Prerequisites

- **JDK 17+** — required by the IntelliJ Platform SDK
- No Gradle installation needed — the project includes a Gradle wrapper

### Build

```sh
./gradlew build
```

The plugin zip is output to `build/distributions/`.

### Run in a Sandbox IDE

Launch a sandboxed GoLand/IntelliJ instance with the plugin loaded:

```sh
./gradlew runIde
```

This downloads and runs IntelliJ Community with the plugin installed. To test in GoLand specifically, set the `alternativeIdePath` in `build.gradle.kts` or pass it via the command line:

```sh
./gradlew runIde -PalternativeIdePath="/Applications/GoLand.app"
```

### Useful Gradle Tasks

| Task | Description |
|---|---|
| `./gradlew build` | Compile and package the plugin |
| `./gradlew runIde` | Run a sandbox IDE with the plugin |
| `./gradlew buildPlugin` | Build the plugin zip only |
| `./gradlew verifyPlugin` | Verify plugin structure and compatibility |
| `./gradlew clean` | Clean build outputs |
