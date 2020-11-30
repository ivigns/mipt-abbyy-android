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
                    val text = visionText.text
                    if (text.isEmpty()) {
                        continuation.resume(null)
                    } else {
                        continuation.resume(visionText.text)
                    }
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }
}