package com.bruno.lumen.feature.drawing.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bruno.lumen.feature.drawing.vm.DrawingViewModel
import com.bruno.lumen.ui.common.theme.*

@Composable
fun DrawingScreen(viewModel: DrawingViewModel) {
    var showOnboarding by remember { mutableStateOf(true) }

    if (showOnboarding) {
        OnboardingScreen(onStart = { showOnboarding = false })
    } else {
        DrawingContent(viewModel)
    }
}

@Composable
private fun AnimatedToolIcon(
    icon: ImageVector,
    toolName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "iconAnim")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (toolName == "CUBE_3D" || toolName == "GALAXY") 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (toolName == "GALAXY") 8000 else 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .graphicsLayer {
                scaleX = if (isSelected) scale else 1f
                scaleY = if (isSelected) scale else 1f
                rotationZ = rotation
            }
    ) {
        Icon(
            icon, 
            contentDescription = toolName, 
            tint = if (isSelected) NeonBlue else Color.White.copy(alpha = 0.6f),
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun DrawingContent(viewModel: DrawingViewModel) {
    val neonColors = listOf(NeonBlue, NeonPink, NeonGreen, NeonYellow, LumenPurple, Color.White)
    val selectedColorInt by viewModel.currentColor.collectAsState()
    val selectedColor = Color(selectedColorInt)
    
    val handStatus by viewModel.handStatus.collectAsState(
        initial = com.bruno.lumen.domain.vision.HandStatus(isPresent = false)
    )
    val zoomScale by viewModel.zoomScale.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LumenBlack)
    ) {
        // Camera and Drawing Layer
        Box(modifier = Modifier.fillMaxSize().graphicsLayer(scaleX = zoomScale, scaleY = zoomScale)) {
            CameraDrawingOverlay(viewModel = viewModel)
        }

        // Action Buttons (Top Right)
        Row(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = { viewModel.undo() },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo", tint = Color.White)
            }
            IconButton(
                onClick = { viewModel.clear() },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color.White)
            }
        }

        // UI Controls - Bottom Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(45.dp)),
            color = Color.Black.copy(alpha = 0.8f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Colors
                LazyRow(
                    modifier = Modifier.width(130.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(neonColors) { color ->
                        Box(
                            modifier = Modifier
                                .size(if (selectedColor == color) 30.dp else 22.dp)
                                .background(color, CircleShape)
                                .border(
                                    width = if (selectedColor == color) 2.dp else 0.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                                .clickable { viewModel.updateColor(color.toArgb()) }
                        )
                    }
                }

                VerticalDivider(modifier = Modifier.height(30.dp).width(1.dp), color = Color.White.copy(alpha = 0.2f))

                // Tools Selection
                val currentTool by viewModel.currentTool.collectAsState()
                val tools = listOf(
                    DrawingViewModel.Tool.BRUSH to Icons.Default.Brush,
                    DrawingViewModel.Tool.SPRAY to Icons.Default.Grain,
                    DrawingViewModel.Tool.AIRBRUSH to Icons.Default.BlurOn,
                    DrawingViewModel.Tool.NEON_PULSE to Icons.Default.SettingsInputAntenna,
                    DrawingViewModel.Tool.LIGHTNING to Icons.Default.ElectricBolt,
                    DrawingViewModel.Tool.FIRE to Icons.Default.Whatshot,
                    DrawingViewModel.Tool.GALAXY to Icons.Default.AutoAwesome,
                    DrawingViewModel.Tool.WEB to Icons.Default.Hub,
                    DrawingViewModel.Tool.CUBE_3D to Icons.Default.ViewInAr,
                    DrawingViewModel.Tool.CRYSTAL to Icons.Default.Diamond,
                    DrawingViewModel.Tool.CALLIGRAPHY to Icons.Default.HistoryEdu,
                    DrawingViewModel.Tool.RAINBOW to Icons.Default.ColorLens,
                    DrawingViewModel.Tool.HEART to Icons.Default.Favorite,
                    DrawingViewModel.Tool.STAR to Icons.Default.Star,
                    DrawingViewModel.Tool.CIRCLE to Icons.Default.RadioButtonUnchecked,
                    DrawingViewModel.Tool.SQUARE to Icons.Default.CropSquare,
                    DrawingViewModel.Tool.ERASER to Icons.Default.AutoFixNormal
                )

                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(tools) { (toolType, icon) ->
                        AnimatedToolIcon(
                            icon = icon,
                            toolName = toolType.name,
                            isSelected = currentTool == toolType,
                            onClick = { viewModel.selectTool(toolType) }
                        )
                    }
                }
            }
        }
    }
}
