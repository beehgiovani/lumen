package com.bruno.lumen.feature.drawing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bruno.lumen.ui.common.theme.NeonBlue

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Brush

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LÚMEN",
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NeonBlue,
                textAlign = TextAlign.Center,
                letterSpacing = 8.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Image(
                painter = painterResource(id = com.bruno.lumen.feature.drawing.R.drawable.logo),
                contentDescription = "Lúmen Logo",
                modifier = Modifier.size(160.dp).clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(32.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "CRIE NO ESPAÇO",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            InstructionItem(Icons.Default.Brush, "INDICADOR", "Aponte para desenhar no ar")
            InstructionItem(Icons.Default.FlashOn, "PUNHO", "Feche a mão para usar a borracha")
            InstructionItem(Icons.Default.PanTool, "PALMA", "Mão aberta para mover e selecionar")
            InstructionItem(Icons.Default.ZoomIn, "PINÇA", "Polegar e indicador para Zoom")
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = NeonBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text("INICIAR EXPERIÊNCIA", color = Color.Black, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            }
        }
    }
}

@Composable
private fun InstructionItem(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(NeonBlue.copy(alpha = 0.1f), CircleShape)
                .border(1.dp, NeonBlue.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = NeonBlue, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(title, color = NeonBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text(description, color = Color.White, fontSize = 15.sp)
        }
    }
}
