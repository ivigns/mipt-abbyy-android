package com.github.ivigns.abbyy.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MainFragment.NoteListener {

    var noteId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle(R.string.caption_main)

        if (supportFragmentManager.findFragmentByTag(NoteFragment.TAG) == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainContainer, MainFragment())
            fragmentTransaction.commit()
        }
    }

    override fun onNoteClick(id: Long) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(NoteFragment.NOTE_ID)
        val fragment = NoteFragment()
        val args = Bundle()
        args.putLong(NoteFragment.NOTE_ID, id)
        fragment.arguments = args
        noteId = id
        fragmentTransaction.replace(R.id.mainContainer, fragment, NoteFragment.TAG)

        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.setTitle(R.string.caption_main)
    }

}
