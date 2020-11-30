package com.github.ivigns.abbyy.android

import android.view.View
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
    }

}
