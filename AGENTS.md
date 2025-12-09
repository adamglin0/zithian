# Zithian Component Development Conventions

This document outlines the coding standards and patterns for developing components in the Zithian design system.

## 1. Component Architecture

### Base & Variants Pattern
*   **Base Component (`BasicX`)**: Implement the core logic, layout, state management, and interaction handling in a base component (e.g., `BasicButton`). This component should be highly configurable.
*   **Variants (`PrimaryX`, `NeutralX`)**: specific implementations should wrap the base component.
    *   **Delegation**: Forward standard parameters to the base component.
    *   **Styling Defaults**: Define the variant's look primarily by overriding default parameter values (colors, dimensions).
    *   **Modifiers**: Use modifiers for structural additions like borders (e.g., `OutlinedButton` uses `.innerBorder()`).
*   **Independent Components**: If a component's behavior diverges significantly (e.g., `TextButton`), it may be implemented independently but should still adhere to the naming and parameter conventions.

## 2. API Design & Parameters

Maintain a consistent parameter order to ensure predictability:

1.  **Actions**: Primary callbacks (e.g., `onClick`).
2.  **Modifier**: `modifier: Modifier = Modifier`.
3.  **Dimensions**: `dimens: {Component}Dimens = {Component}Defaults.dimens()`.
4.  **Interaction**: `interactionSource: MutableInteractionSource`.
5.  **Colors**
    *   **Data Class**: Create an `@Immutable` data class (e.g., `BasicButtonColors`) to hold color values for various states (e.g., `backgroundColor`, `foregroundColor`).
    *   **Defaults Object**: Provide a composable helper in `{Component}Defaults` (e.g., `colors()`) to return instances of the color data class, defaulting to `ZithianTheme.colors`.
    *   **Parameter**: Pass the `colors` object as a single parameter to the component.
    *   **State Handling**: Calculate dynamic colors (hover, pressed) *inside* the component logic using the provided base colors and interaction state.
6.  **Typography**: `textStyle`.
7.  **Slots**: `leading`, `trailing` (composable lambdas).
8.  **State**: `enabled`, `selected`, etc.
9.  **Content**: Trailing lambda `content`.

## 3. Dimensions & Responsiveness

Zithian components must adapt to different interaction types (Pointer vs. Touch).

*   **Dimens Data Class**: Create an `@Immutable` data class (e.g., `BasicButtonDimens`) to hold layout metrics (padding, corner radius, spacing).
*   **Presets**: Define `Pointer` and `Touch` presets in the companion object.
*   **Factory**: Implement an `of(interactType)` function.
*   **Defaults Object**: Provide a composable helper to retrieve dimensions based on `LocalInteractType`.

```kotlin
@Immutable
data class BasicButtonDimens(
    val contentPadding: PaddingValues,
    val cornerRadius: Dp,
    // ...
) {
    companion object {
        internal val Pointer = BasicButtonDimens(...)
        internal val Touch = BasicButtonDimens(...)
        
        fun of(interactType: InteractType) = when (interactType) {
            InteractType.Pointer -> Pointer
            InteractType.Touch -> Touch
        }
    }
}

internal object BasicButtonDefaults {
    @Composable
    fun dimens(type: InteractType = LocalInteractType.current) = BasicButtonDimens.of(type)
}
```

## 4. Interaction & State

*   **InteractType**: Use `LocalInteractType.current` to conditionally apply styles or modifiers (e.g., hover effects are often only for `Pointer`).
*   **InteractionSource**: Always hoist `MutableInteractionSource`. Use it to derive `isPressed` and `isHovered` states.
*   **Custom Modifiers**:
    *   Use `.interactPointer(type, enabled)` for standard pointer interaction handling.
    *   Use `.ifTrue` and `.ifNotNull` utilities to keep modifier chains readable.
*   **Hover Effects**: Apply hover shadows or background changes only when `InteractType.Pointer` is active.

## 5. Styling

*   **Theme Tokens**: Always access colors via `ZithianTheme.colors`.
*   **Shapes**: Use `ContinuousRoundedCornerShape` for organic rounded corners.
*   **Layout**: Use `CompositionLocalProvider` to pass `LocalContentColor` and `LocalTextStyle` to children content.
*   **Disabled State**: Generally handled via `.alpha()` on the container rather than separate color tokens, unless specific "disabled" colors are defined in the design.

## 6. File Organization

*   Place the basic component and its variants in the same package (e.g., `com.adamglin.zithian.compose.button`).
*   Name files after the component they contain.
