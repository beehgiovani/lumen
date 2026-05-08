package com.bruno.lumen.feature.drawing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.bruno.lumen.domain.vision.HandStatus
import com.bruno.lumen.ui.common.theme.NeonBlue
import com.bruno.lumen.ui.common.theme.NeonPink

@Composable
fun HandOverlay(
    handStatus: HandStatus,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    Box(modifier = modifier.fillMaxSize()) {
        handStatus.hands.forEach { hand ->
            val tip = hand.indexTip ?: return@forEach
            
            val cursorColor = when (hand.gesture) {
                com.bruno.lumen.domain.vision.Gesture.POINTING -> NeonBlue
                com.bruno.lumen.domain.vision.Gesture.FIST,
                com.bruno.lumen.domain.vision.Gesture.BACK_HAND -> Color.Red
                com.bruno.lumen.domain.vision.Gesture.PINCH_ZOOM -> NeonPink
                else -> Color.White.copy(alpha = 0.6f)
            }

            val icon = when (hand.gesture) {
                com.bruno.lumen.domain.vision.Gesture.POINTING -> Icons.Default.Brush
                com.bruno.lumen.domain.vision.Gesture.FIST,
                com.bruno.lumen.domain.vision.Gesture.BACK_HAND -> Icons.Default.AutoFixNormal
                com.bruno.lumen.domain.vision.Gesture.PINCH_ZOOM -> Icons.Default.ZoomOutMap
                com.bruno.lumen.domain.vision.Gesture.OPEN_HAND -> Icons.Default.PanTool
                else -> Icons.Default.BackHand
            }

            Column(
                modifier = Modifier
                    .offset(
                        x = (tip.x * screenWidth).dp - 32.dp,
                        y = (tip.y * screenHeight).dp - 32.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(cursorColor.copy(alpha = 0.1f), CircleShape)
                        .border(2.dp, cursorColor.copy(alpha = 0.8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = cursorColor,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mode Label
                val modeText = when (hand.gesture) {
                    com.bruno.lumen.domain.vision.Gesture.POINTING -> "DESENHAR"
                    com.bruno.lumen.domain.vision.Gesture.FIST -> "BORRACHA"
                    com.bruno.lumen.domain.vision.Gesture.OPEN_HAND -> "MOVER"
                    com.bruno.lumen.domain.vision.Gesture.PINCH_ZOOM -> {
                        val scale = (hand.confidence * 100).toInt()
                        "ZOOM: $scale%"
                    }
                    else -> ""
                }

                if (modeText.isNotEmpty()) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = modeText,
                            color = cursorColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
