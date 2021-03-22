package com.spacex.spacexlaunchers.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.spacex.spacexlaunchers.R
import com.spacex.spacexlaunchers.databinding.FragmentLauncherDetailsBinding

class LauncherDetailsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentLauncherDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentLauncherDetailsBinding>(
        inflater, R.layout.fragment_launcher_details, container,
        false
    ).also {

        viewDataBinding = it

    }.root
}