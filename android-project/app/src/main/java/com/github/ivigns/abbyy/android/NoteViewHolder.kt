package com.github.ivigns.abbyy.android

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.github.ivigns.abbyy.android.db.Note
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_note.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder(itemView: View, private val listener: MainFragment.NoteListener?)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var view = itemView
    private var note: Note? = null

    init {
        itemView.setOnClickListener(this)
    }

    private fun showPopupMenu(view: View, noteId: Long) {
        val menu = PopupMenu(view.context, view.noteMenu, Gravity.END)
        menu.inflate(R.menu.popup_options)
        menu.setOnMenuItemClickListener {
            onMenuClick(it, noteId)
            true
        }
        menu.show()
    }

    private fun onMenuClick(item: MenuItem, noteId: Long) {
        when(item.itemId) {
            R.id.popup_item_share -> listener?.onShareNote(noteId)
            R.id.popup_item_delete -> listener?.onDeleteNote(noteId)
        }
    }

    override fun onClick(v: View?) {
        note?.let { listener?.onNoteClick(it.id) }
    }

    fun bind(note: Note) {
        this.note = note
        view.noteText.text = note.text
        view.noteDate.text = SimpleDateFormat(view.context.getString(R.string.date_format),
            Locale.UK).format(note.date)
        Picasso.with(view.context).load(File(note.drawablePath)).fit().centerInside()
            .into(view.noteImage)
        view.noteImage.contentDescription = note.text
        view.noteMenu.setOnClickListener {
            showPopupMenu(it, note.id)
        }
    }

}
