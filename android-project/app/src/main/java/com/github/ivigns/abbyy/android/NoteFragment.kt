package com.github.ivigns.abbyy.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.note_detailed_view.view.*
import kotlinx.coroutines.*

class NoteFragment : Fragment() {

    var job: Job? = null

    companion object {
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

        val noteId = arguments?.getLong(NOTE_ID, 1)
        job = GlobalScope.launch(Dispatchers.Main) {
            val note = getNoteWithIdTask(noteId ?: 1)
            if (note != null) {
                view.noteText.setText(note.textId)
                view.noteImage.setImageDrawable(activity?.getDrawable(note.drawableId))
                view.noteImage.contentDescription = getString(note.textId)
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
