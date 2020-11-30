package com.github.ivigns.abbyy.android

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object ImageToTextConverter {
    suspend fun getText(context: Context, imagePath: Uri): String? {
        return suspendCoroutine { continuation ->
            val image = InputImage.fromFilePath(context, imagePath)
            val recognizer = TextRecognition.getClient()
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    continuation.resume(visionText.text)
                }
                .addOnFailureListener { e ->
                    continuation.resume(null)
                }
        }
    }
}