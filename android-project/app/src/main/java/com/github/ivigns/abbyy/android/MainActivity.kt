package com.github.ivigns.abbyy.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), MainFragment.NoteListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle(R.string.caption_main)

        val noteFragment = supportFragmentManager.findFragmentByTag(NoteFragment.TAG)
        val twoFragmentsOnScreen = !(resources.getBoolean(R.bool.isPhone) ||
                resources.getBoolean(R.bool.isPortrait))

        if ((noteFragment == null) || twoFragmentsOnScreen) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            val fragment = if (twoFragmentsOnScreen) R.id.mainListContainer else R.id.mainContainer
            fragmentTransaction.replace(fragment, MainFragment())

            fragmentTransaction.commit()
        }
        if (noteFragment != null) {
            noteFragment.arguments?.getLong(NoteFragment.NOTE_ID)?.let {
                onNoteClick(it)
            }
        }
    }

    override fun onNoteClick(id: Long) {
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

    override fun onBackPressed() {
        super.onBackPressed()
        this.setTitle(R.string.caption_main)
    }

}
