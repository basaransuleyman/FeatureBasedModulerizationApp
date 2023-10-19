package com.example.feature.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.changeTextRandomColor
import com.example.core.extensions.setImageUrl
import com.example.core.extensions.setRandomColorWithPosition
import com.example.core.model.Card
import com.example.feature.search.ui.databinding.HeroItemBinding

class SearchAdapter(private val heroClickListener: (Card) -> Unit) :
    ListAdapter<Card, SearchAdapter.SearchListViewHolder>(CardDiffCallback()) {

    class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder(
            HeroItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val hero = getItem(position)

        holder.bind(hero, heroClickListener = { heroCard ->
            heroClickListener(heroCard)
        })
    }

    inner class SearchListViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hero: Card, heroClickListener: (Card) -> Unit) {
            with(binding) {
                setRandomColorWithPosition(cardConstraintLayout, adapterPosition)
                heroNameTextView.text = hero.name
                heroHpTextView.text = hero.hp
                heroImageView.setImageUrl(hero.imageUrl)
                hero.attacks?.forEach { attack ->
                    heroFirstSubTextView.text = attack.name
                    changeTextRandomColor(heroFirstSubTextView)
                    heroFirstDescriptionTextView.text = attack.text
                }
                root.setOnClickListener {
                    heroClickListener.invoke(hero)
                }
            }
        }
    }
}