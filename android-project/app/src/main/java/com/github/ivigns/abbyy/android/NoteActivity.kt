package com.github.ivigns.abbyy.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detailed_view.view.*

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)
        val view = findViewById<View>(R.id.detailedView)
        this.setTitle(R.string.caption_note)

        val noteId = intent.getSerializableExtra("noteId") as Long?
        val note = NotesRepository.getNoteWithId(noteId ?: 1)
        if (note != null) {
            view.noteText.setText(note.textId)
            view.noteImage.setImageDrawable(getDrawable(note.drawableId))
            view.noteImage.contentDescription = getString(note.textId)
        }
    }
}
