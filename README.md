<div align="center">
<h1>
<a href="https://mise.jdx.dev">
  <img alt="The icon of zithian" src="doc/resources/images/zithian-icon.svg" title="Zithian" height="60"/>
</a>
</h1>
<img alt="Maven Central Version" src="https://img.shields.io/maven-central/v/com.adamglin/zithian">
</div>

## Install

**libs.versions.toml**

``` file="libs.versions.toml"
[versions]
zithian = "latest-version"
[libraries]
zithian-compose = { module = "com.adamglin.zithian:compose", version.ref = "zithian" }
zithian-navigation3 = { module = "com.adamglin.zithian:navigation3", version.ref = "zithian" }
zithian-emulator = { module = "com.adamglin.zithian:emulator", version.ref = "zithian" }
```

**build.gradle.kts**

``` kts
implementation(libs.zithian.compose)
implementation(libs.zithian.navigation3)
implementation(libs.zithian.emulator)
```

## Modules

### Compose

_It is currently in the early development stage._

This is the implementation of Zithian in [Compose multiplatform](https://github.com/JetBrains/compose-multiplatform).

### Navigation3

_It is currently in the early development stage._

This is the jetpack navigation3 extension used in conjunction with Zithian Compose.

### Emulator

_It is currently in the early development stage._

This is a mobile appearance simulator running on the JVM. It only simulates the device’s visual appearance rather than
full functionality.

We developed it to work in conjunction with the [Compose Hot Reload](https://github.com/JetBrains/compose-hot-reload)
￼project, enabling mobile UI simulation on the JVM to make debugging easier and more efficient.


