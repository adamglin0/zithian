# InteractType

在 Zithian 中，InteractType 表示用户和系统的交互方式。它主要用于区分触摸操作（Touch）和鼠标/指针操作（Pointer），以便 UI 能够做出适应性的调整。

## 定义

`InteractType` 是一个枚举类，包含以下两种类型：

### Touch

Touch 表示用户和系统的交互方式为触摸。
- **特性**：
    - 可点击组件的目标尺寸建议在 **48dp** 左右，以方便手指触控。
    - 组件**不应该**有 Hover（悬停）效果。
    - 部分组件可能会有针对触摸优化的差异化样式。

### Pointer

Pointer 表示用户和系统的交互方式为鼠标指针或者触摸板。
- **特性**：
    - 可交互的组件在 Hover 时**应该**有形态变化（如背景色改变）和指针变化（如变为手型图标）。
    - 点击目标的尺寸要求可以相对较小（相较于 Touch）。

## 代码使用

### 获取当前交互类型

在 Composable 函数中，可以通过 `LocalInteractType` 获取当前的交互类型：

```kotlin
val currentInteractType = LocalInteractType.current

when (currentInteractType) {
    InteractType.Touch -> { /* 触摸模式下的逻辑 */ }
    InteractType.Pointer -> { /* 指针模式下的逻辑 */ }
}
```

### 设置交互类型

`InteractType` 通常作为 `ZithianTheme` 的参数进行传递。你可以强制指定应用的交互类型：

```kotlin
ZithianTheme(
    interactType = InteractType.Touch // 强制使用 Touch 模式
) {
    // App content
}
```

如果不指定，将使用各平台的默认值。

## 平台默认行为 (Platform Defaults)

`InteractType.platformDefault` 提供了各平台的默认交互类型：

| 平台 | 默认值 | 备注 |
| :--- | :--- | :--- |
| **Android** | `Touch` | 移动设备默认主要为触摸交互。 |
| **iOS** | `Touch` | 移动设备默认主要为触摸交互。 |
| **JVM (Desktop)** | `Pointer` | 桌面环境默认为鼠标/键盘交互。 |
| **Web** | *Dynamic* | 根据 CSS Media Query `(any-hover: hover)` 和 `(any-pointer: fine)` 动态判断。如果有精确指针（如鼠标），则为 `Pointer`，否则为 `Touch`。 |
