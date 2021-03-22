package com.spacex.spacexlaunchers.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spacex.api.model.Launcher
import com.spacex.spacexlaunchers.R
import com.spacex.spacexlaunchers.databinding.RvRowLauncherBinding
import com.spacex.spacexlaunchers.util.dateFormatter

class LaunchersAdapter: RecyclerView.Adapter<LaunchersAdapter.LauncherListVH>() {

    var launcherList: List<Launcher>? = null
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LauncherListVH(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_row_launcher, parent, false
        )
    )

    override fun getItemCount() = launcherList?.size ?: 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: LauncherListVH, position: Int) {
        launcherList?.let {

            it[position].let { launchers ->

                holder.binding.apply {

                    launcher = launchers
                    holder.binding.txtDate.text = launchers.date_local?.let { it1 ->
                        dateFormatter(
                            it1
                        )
                    }

                }
            }
        }
        /*holder.itemView.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_launchersFragment_to_launcherDetailsFragment)
        }*/
    }

    override fun onViewRecycled(holder: LauncherListVH) {
        super.onViewRecycled(holder)
        holder.binding.imgLauncher.setImageResource(R.mipmap.ic_launcher)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    /*
    * It clears the list whenever user performs pull to refresh operations
    * */
    fun clear() {
        launcherList = null
        notifyDataSetChanged()
    }

    fun update(list: List<Launcher>?) {
        launcherList = list
        notifyDataSetChanged()
    }

    class LauncherListVH(val binding: RvRowLauncherBinding): RecyclerView.ViewHolder(binding.root)
}
