package com.adamglin.zithian.compose.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp

/**
 * Calculates the inner corner radius for a nested rounded rectangle.
 *
 * When placing a rounded Box B inside another rounded Box A, to maintain
 * visually concentric corners, the inner radius should be calculated as:
 * `innerRadius = max(0, outerRadius - padding)`
 *
 * This ensures that:
 * - When padding >= outerRadius: inner corners become square (0 radius)
 *   since B is positioned within A's straight edge region
 * - When padding < outerRadius: corners remain visually concentric
 *
 * @param outerCornerRadius The corner radius of the outer container (Box A)
 * @param padding The distance between the edges of Box B and Box A
 * @return The calculated corner radius for the inner Box B
 *
 * Example:
 * ```
 * val outerRadius = 16.dp
 * val padding = 4.dp
 * val innerRadius = calculateInnerCornerRadius(outerRadius, padding) // 12.dp
 * ```
 */
fun calculateInnerCornerRadius(outerCornerRadius: Dp, padding: Dp): Dp {
    return (outerCornerRadius - padding).coerceAtLeast(0.dp)
}

/**
 * Calculates the padding (distance) between nested rounded rectangles
 * to achieve a specific inner corner radius.
 *
 * This is the inverse operation of [calculateInnerCornerRadius].
 *
 * For a CircleShape inner element (where innerCornerRadius equals half
 * the element's shorter dimension), pass that radius value to get the
 * padding needed for concentric circles.
 *
 * @param outerCornerRadius The corner radius of the outer container (Box A)
 * @param innerCornerRadius The desired corner radius for the inner Box B.
 *        For CircleShape, this should be half of the inner element's
 *        shorter dimension (width or height).
 * @return The required padding between Box A's edge and Box B's edge
 *
 * Example - Regular rounded corners:
 * ```
 * val outerRadius = 16.dp
 * val innerRadius = 12.dp
 * val padding = calculateConcentricPadding(outerRadius, innerRadius) // 4.dp
 * ```
 *
 * Example - CircleShape (concentric circles):
 * ```
 * val outerRadius = 24.dp
 * val innerCircleRadius = 16.dp // half of inner element's size
 * val padding = calculateConcentricPadding(outerRadius, innerCircleRadius) // 8.dp
 * ```
 */
fun calculateConcentricPadding(outerCornerRadius: Dp, innerCornerRadius: Dp): Dp {
    return (outerCornerRadius - innerCornerRadius).coerceAtLeast(0.dp)
}