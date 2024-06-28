package com.example.superherosmobile.utils

import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView

class Animations {
    fun scaleImage(image:ImageView){
        //para que al clickar en el boton del heroe me salga la animacion a escala del ImageView
        val scaleAnimation = ScaleAnimation(
            0f, 1f, // Escala de X: de 0% a 100%
            0f, 1f, // Escala de Y: de 0% a 100%
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X: en el centro de la vista
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot Y: en el centro de la vista
        ).apply {
            duration = 2000 // Duración de la animación en milisegundos
            fillAfter = true // Mantener el estado final después de la animación
        }
        // Iniciar la animación
        image.startAnimation(scaleAnimation)
    }
}