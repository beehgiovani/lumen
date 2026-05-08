package com.bruno.lumen.domain.usecase

import com.bruno.lumen.domain.model.DrawingRepository
import java.io.OutputStream

class ExportDrawingUseCase(private val repository: DrawingRepository) {
    // A lógica real de exportação para PNG exigirá uma interface para o Android Bitmap,
    // mas no domínio definimos apenas a intenção de exportar os traços atuais.
    fun execute(outputStream: OutputStream) {
        // Implementação delegada ao adaptador de infraestrutura
    }
}
