package com.github.ivigns.abbyy.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    var job: Job? = null

    interface NoteListener {
        fun onNoteClick(id: Long)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NoteAdapter(emptyList(), null)
        GlobalScope.launch(Dispatchers.Main) {
            recyclerView.adapter = NoteAdapter(listNotesTask(), activity as NoteListener?)
        }
        return view
    }

    private suspend fun listNotesTask() = withContext(Dispatchers.IO) {
        return@withContext App.noteRepository.listNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
