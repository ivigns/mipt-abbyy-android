package com.github.ivigns.abbyy.android

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var view = itemView
    private var note: Note? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val context = itemView.context
        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra("noteId", note?.id)
        context.startActivity(intent)
    }

    fun bind(note: Note) {
        this.note = note
        view.noteText.setText(note.textId)
        view.noteDate.text = SimpleDateFormat(view.context.getString(R.string.date_format),
            Locale.UK).format(note.date)
        view.noteImage.setImageDrawable(view.context.getDrawable(note.drawableId))
    }

}
