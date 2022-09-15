package com.example.myconfidence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myconfidence.roompersistence.MotivationalMessage

class MessageListAdapter: ListAdapter<MotivationalMessage, MessageListAdapter.MessageViewHolder>(MessagesComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.motivation)
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val messageItemView: TextView = itemView.findViewById(R.id.message_text)

        fun bind(text: String?) {
            messageItemView.text = text
        }
        companion object {
            fun create(parent: ViewGroup): MessageViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_item, parent, false)
                return MessageViewHolder(view)
            }
        }
    }


    class MessagesComparator : DiffUtil.ItemCallback<MotivationalMessage>() {
        override fun areItemsTheSame(oldItem: MotivationalMessage, newItem: MotivationalMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MotivationalMessage, newItem: MotivationalMessage): Boolean {
            return oldItem.motivation == newItem.motivation
        }
    }

}