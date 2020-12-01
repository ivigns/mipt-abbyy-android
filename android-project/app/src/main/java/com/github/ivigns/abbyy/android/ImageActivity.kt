package com.github.ivigns.abbyy.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File

class ImageActivity : AppCompatActivity() {

    var noteId: Long? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val view = findViewById<ImageView>(R.id.fullScreenImage)

        noteId = intent.getLongExtra(NoteFragment.NOTE_ID, NoteFragment.UNDEFINED_NOTE_ID)

        noteId?.let {
            job = GlobalScope.launch(Dispatchers.Main) {
                val note = getNoteWithIdTask(it)
                if (note != null) {
                    Picasso.with(this@ImageActivity).load(File(note.drawablePath)).fit()
                        .centerInside()
                        .into(view)
                    view.contentDescription = note.text
                } else {
                    finish()
                }
            }
        }
    }

    private suspend fun getNoteWithIdTask(id: Long) = withContext(Dispatchers.IO) {
        return@withContext App.noteRepository.getNoteWithId(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        val intent = Intent()
        noteId?.let { intent.putExtra(NoteFragment.NOTE_ID, it) }
        setResult(Activity.RESULT_OK, intent)
    }
}
