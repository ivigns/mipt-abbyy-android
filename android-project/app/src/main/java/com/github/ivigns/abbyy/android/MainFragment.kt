package com.github.ivigns.abbyy.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

        val plusButton = view.findViewById<ImageButton>(R.id.plusButton)
        plusButton.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, Activity.RESULT_FIRST_USER)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NoteAdapter(emptyList(), null)
        startLoadingNotes(recyclerView)

        return view
    }

    private suspend fun listNotesTask() = withContext(Dispatchers.IO) {
        return@withContext App.noteRepository.listNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun startLoadingNotes(recyclerView: RecyclerView) {
        GlobalScope.launch(Dispatchers.Main) {
            recyclerView.adapter = NoteAdapter(listNotesTask(), activity as NoteListener?)
        }
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recyclerView) ?: return
        startLoadingNotes(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let { activity?.intent?.putExtras(it) }
    }
}
