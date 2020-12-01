package com.github.ivigns.abbyy.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.note_detailed_view.view.*
import kotlinx.coroutines.*
import java.io.File

class NoteFragment : Fragment() {

    var job: Job? = null

    companion object {
        const val UNDEFINED_NOTE_ID = -1L
        const val NOTE_ID = "noteId"
        const val TAG = "noteFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.note_detailed_view, container, false)
        activity?.setTitle(R.string.caption_note)

        val noteId = arguments?.getLong(NOTE_ID, UNDEFINED_NOTE_ID)
        job = GlobalScope.launch(Dispatchers.Main) {
            val note = getNoteWithIdTask(noteId ?: UNDEFINED_NOTE_ID)
            if (note != null) {
                view.noteImage.setOnClickListener {
                    val intent = Intent(activity, ImageActivity::class.java)
                    intent.putExtra(NOTE_ID, noteId)
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER)
                }
                view.noteText.text = note.text
                view.noteText.movementMethod = ScrollingMovementMethod()
                Picasso.with(context).load(File(note.drawablePath)).fit().centerInside().into(view.noteImage)
                view.noteImage.contentDescription = note.text
            }
        }

        return view
    }

    private suspend fun getNoteWithIdTask(id: Long) = withContext(Dispatchers.IO) {
        return@withContext App.noteRepository.getNoteWithId(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
