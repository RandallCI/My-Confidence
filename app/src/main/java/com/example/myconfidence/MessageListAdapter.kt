package com.example.myconfidence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myconfidence.roompersistence.MotivationalMessage

class MessageListAdapter : ListAdapter<MotivationalMessage, MessageListAdapter.MessageViewHolder>(MessagesComparator()) {
    //Create a listener object
    private lateinit var messageListener: OnItemClickListener
    //Create an interface which will link the adapter to the recycler view.
    interface MessageClickListener : OnItemClickListener {
        fun onMessageClicked(position: Int)
    }

    fun setMessageClickListener(listener: MessageClickListener) {
        messageListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return MessageViewHolder(itemView as ViewGroup, messageListener as MessageClickListener)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val current = getItem(position)
        holder.theMessage.text = current.motivation
    }

    class MessageViewHolder(itemView: View, listener: MessageClickListener): RecyclerView.ViewHolder(itemView) {
        private val messageItemView: TextView = itemView.findViewById(R.id.message_text)
        
        val theMessage: TextView = itemView.findViewById(R.id.message_text)
        //Get the position of the clicked item.
        init {
            itemView.setOnClickListener { listener.onMessageClicked(adapterPosition) }
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