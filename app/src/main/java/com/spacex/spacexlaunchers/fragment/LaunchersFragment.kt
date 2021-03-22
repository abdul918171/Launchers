package com.spacex.spacexlaunchers.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.spacex.spacexlaunchers.R
import com.spacex.spacexlaunchers.adapter.LaunchersAdapter
import com.spacex.spacexlaunchers.databinding.FragmentLaunchersBinding
import com.spacex.spacexlaunchers.viewmodel.LauncherListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchersFragment : Fragment() {


    /**
     * The ViewModel instance is provided by Koin using the viewModelModule from AppModule
     * also the SAME INSTANCE of ViewModel (with data retained) is provided by the Koin after
     * the orientation gets changed.
     */
    private val launcherListViewModel: LauncherListViewModel by viewModel()

    private val launchersAdapter by lazy {
        LaunchersAdapter()
    }

    lateinit var viewDataBinding: FragmentLaunchersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        DataBindingUtil.inflate<FragmentLaunchersBinding>(
            inflater, R.layout.fragment_launchers,
            container, false
        ).also {
            viewDataBinding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.apply {
            launcherListViewModel.ldOnLaunchersListLoaded.observe(viewLifecycleOwner, Observer {
                launchersAdapter.update(it)
                frmProgress.visibility = View.GONE
            })

            launcherListViewModel.ldOnError.observe(viewLifecycleOwner, Observer { error ->

                error?.let {

                    swpRfrshLaunchers.isEnabled = true
                    frmProgress.visibility = View.GONE
                    Snackbar.make(root, it, Snackbar.LENGTH_LONG).show()

                    /**
                     * resetting 'mtldOnError' back to null after once this value is notified,
                     * otherwise the value would be stored in the LiveData and every-time user
                     * changes the orientation then LiveData will instantly listen this value
                     * as soon as we attach the Observer to it and SnackBar would be shown
                     * to the user representing the earlier error.
                     */
                    launcherListViewModel.clearError()
                }
            })


            rclr.apply {

                adapter = launchersAdapter
                addItemDecoration(ItemDecoration(resources.getDimensionPixelSize(R.dimen.rclr_decoration_ht_fragment_launchers)))

            }

            swpRfrshLaunchers.setOnRefreshListener {

                swpRfrshLaunchers.isRefreshing = false
                frmProgress.visibility = View.VISIBLE
                launchersAdapter.clear()
                launcherListViewModel.loadLauncherList()

            }

            (requireActivity() as AppCompatActivity).setSupportActionBar(tlbr)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * if (vm.ldOnLaunchersListLoaded.value == null) then load Launcher list
         */
        launcherListViewModel.ldOnLaunchersListLoaded.value
            ?: launcherListViewModel.loadLauncherList()
    }
}

private class ItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {

        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) top = margin
            left = margin
            right = margin
            bottom = margin
        }

    }
}