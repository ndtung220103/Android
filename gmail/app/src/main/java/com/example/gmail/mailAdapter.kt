package com.example.gmail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import java.util.Locale
import kotlin.random.Random

class mailAdapter(val mails: List<mailModel>) : BaseAdapter() {
    val colors = arrayOf(
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA
    )

    override fun getCount(): Int = mails.size

    override fun getItem(p0: Int): Any = mails[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.mail_item_layout, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }
        val randomColor = colors[Random.nextInt(colors.size)]
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(randomColor)
        }


        val mail = mails[position]
        val username: String = mail.username
        viewHolder.iconavt.text = username[0].toString().uppercase(Locale.ROOT)
        viewHolder.iconavt.background = drawable
        viewHolder.name.text = username
        viewHolder.mailname.text = mail.mailname
        viewHolder.checkSelected.isChecked = mail.selected
        viewHolder.time.text = mail.time
        viewHolder.content.text = mail.content

        viewHolder.checkSelected.setOnClickListener {
            mail.selected = viewHolder.checkSelected.isChecked
            notifyDataSetChanged()
        }

        return itemView
    }

    class ViewHolder(itemView: View) {
        val iconavt : TextView
        val name : TextView
        val mailname : TextView
        val content : TextView
        val time : TextView
        val checkSelected : CheckBox
        init {
            iconavt = itemView.findViewById(R.id.iconavt)
            name = itemView.findViewById(R.id.username)
            mailname = itemView.findViewById(R.id.mailname)
            content = itemView.findViewById(R.id.content)
            time = itemView.findViewById(R.id.time)
            checkSelected = itemView.findViewById(R.id.checkBox)
        }
    }
}