package com.github.ivigns.abbyy.android

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), MainFragment.NoteListener {

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle(R.string.caption_main)

        val noteFragment = supportFragmentManager.findFragmentByTag(NoteFragment.TAG)

        if ((noteFragment == null) || isTwoFragmentLayout()) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(getMainFragmentId(), MainFragment())
            fragmentTransaction.commit()
        }

        noteFragment?.arguments?.getLong(NoteFragment.NOTE_ID)?.let { onNoteClick(it) }
    }


    override fun onNoteClick(id: Long) {
        if (id == NoteFragment.UNDEFINED_NOTE_ID) {
            return
        }

        supportFragmentManager.popBackStackImmediate(NoteFragment.NOTE_ID,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(NoteFragment.NOTE_ID)
        val fragment = NoteFragment()
        val args = Bundle()
        args.putLong(NoteFragment.NOTE_ID, id)
        fragment.arguments = args
        val container = if (resources.getBoolean(R.bool.isPhone) or resources.getBoolean(R.bool.isPortrait))
            R.id.mainContainer
        else
            R.id.mainDetailContainer
        fragmentTransaction.replace(container, fragment, NoteFragment.TAG)

        fragmentTransaction.commit()
    }

    override fun onShareNote(id: Long) {
        job = GlobalScope.launch(context = Dispatchers.Main) {
            val note = getNoteWithIdTask(id) ?: return@launch
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, note.text)
            sendIntent.type = "text/plain"
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onDeleteNote(id: Long) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.popup_delete_confirm))
            .setPositiveButton(R.string.yes) {_, _ ->
                job = GlobalScope.launch(context = Dispatchers.Main) {
                    App.noteRepository.deleteNote(id)
                    findViewById<RecyclerView>(R.id.recyclerView)?.let {
                        val mainFragment = supportFragmentManager.findFragmentById(getMainFragmentId()) as MainFragment?
                        mainFragment?.onResume()
                        val noteFragment = supportFragmentManager.findFragmentByTag(NoteFragment.TAG)
                        noteFragment?.arguments?.getLong(NoteFragment.NOTE_ID)?.let {
                            if (it == id) {
                                onBackPressed()
                            }
                        }
                    }
                }
            }
            .setNegativeButton(R.string.no) {_, _ ->}
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.setTitle(R.string.caption_main)
    }

    override fun onResume() {
        super.onResume()
        val noteId = intent.getLongExtra(NoteFragment.NOTE_ID, NoteFragment.UNDEFINED_NOTE_ID)
        intent.removeExtra(NoteFragment.NOTE_ID)
        onNoteClick(noteId)
    }

    private fun isTwoFragmentLayout(): Boolean {
        return !(resources.getBoolean(R.bool.isPhone) || resources.getBoolean(R.bool.isPortrait))
    }

    private fun getMainFragmentId(): Int {
        return if (isTwoFragmentLayout()) R.id.mainListContainer else R.id.mainContainer
    }

    private suspend fun getNoteWithIdTask(id: Long) = withContext(Dispatchers.IO) {
        return@withContext App.noteRepository.getNoteWithId(id)
    }

}
