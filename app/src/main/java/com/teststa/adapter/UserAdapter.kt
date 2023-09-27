package com.teststa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teststa.base.BaseRecyclerViewAdapter
import com.teststa.databinding.ItemUserGithubBinding
import com.teststa.remote.Repo


class RepoViewHolder(
    private val binding: ItemUserGithubBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(item: Repo) {
        binding.apply {
            idTxt.text = item.id.toString()
            nmUserGithub.text = item.name
            htmlTxt.text = item.htmlUrl
        }
    }
}

class UserAdapter : BaseRecyclerViewAdapter<RepoViewHolder, Int, Repo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            ItemUserGithubBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
    }

}