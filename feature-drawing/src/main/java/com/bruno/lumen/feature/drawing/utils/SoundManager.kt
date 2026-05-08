package com.bruno.lumen.feature.drawing.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.bruno.lumen.feature.drawing.R

class SoundManager(private val context: Context) {
    private var soundPool: SoundPool? = null
    private val sounds = mutableMapOf<String, Int>()
    private var lastPlayTime = 0L
    private val MIN_INTERVAL = 50L //ms
    
    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
            
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attributes)
            .build()
            
        // Mapeamento de sons (esperando arquivos na pasta res/raw)
        // loadSound("BRUSH", R.raw.brush_sound)
        // loadSound("SPRAY", R.raw.spray_sound)
        // loadSound("LIGHTNING", R.raw.zap_sound)
    }
    
    private fun loadSound(key: String, resId: Int) {
        sounds[key] = soundPool?.load(context, resId, 1) ?: 0
    }
    
    fun playToolSound(toolName: String, speed: Float) {
        val now = System.currentTimeMillis()
        if (now - lastPlayTime < MIN_INTERVAL) return
        lastPlayTime = now

        val soundId = sounds[toolName] ?: sounds["BRUSH"] ?: return
        if (soundId == 0) return
        
        // Ajusta o volume e o pitch baseado na velocidade (agora mais baixo)
        val volume = (speed / 150f).coerceIn(0.05f, 0.4f)
        val pitch = (0.9f + (speed / 1200f)).coerceIn(0.8f, 1.2f)
        
        soundPool?.play(soundId, volume, volume, 1, 0, pitch)
    }
    
    fun release() {
        soundPool?.release()
        soundPool = null
    }
}
