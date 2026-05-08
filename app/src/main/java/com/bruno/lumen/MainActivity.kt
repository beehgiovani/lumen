package com.bruno.lumen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bruno.lumen.feature.drawing.ui.DrawingScreen
import com.bruno.lumen.feature.drawing.vm.DrawingViewModel
import com.bruno.lumen.ui.common.theme.LumenTheme
import com.bruno.lumen.vision.HandTrackingSourceImpl
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permissão concedida, o Compose vai recompor e abrir a câmera
        } else {
            // Tratar negação (ex: mostrar mensagem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        checkCameraPermission()
        
        // Injeção manual simplificada
        val trackingSource = HandTrackingSourceImpl(this, this)
        val viewModel = DrawingViewModel(handTrackingSource = trackingSource)
        
        setContent {
            LumenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawingScreen(viewModel = viewModel)
                }
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Já tem permissão
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
