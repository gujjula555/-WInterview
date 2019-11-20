package com.example.wiprointerview.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.wiprointerview.R
import com.example.wiprointerview.adapter.ItemAdapter
import com.example.wiprointerview.databinding.MainFragmentBinding
import com.example.wiprointerview.model.Item
import com.example.wiprointerview.network.Result
import com.example.wiprointerview.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var mainViewModel: MainViewModel;
    private lateinit var mainFragmentBinding: MainFragmentBinding;
    private var listData: List<Item> = ArrayList<Item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        return mainFragmentBinding.getRoot()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentBinding.refresh.setOnRefreshListener(this)

        var adapter = ItemAdapter(listData)
        mainFragmentBinding.recyclerView.adapter = adapter
        mainFragmentBinding.progressCircular.visibility = View.VISIBLE;
        mainViewModel.data.observe(this, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    mainFragmentBinding.recyclerView.visibility = View.VISIBLE
                    mainFragmentBinding.progressCircular.visibility = View.GONE;
                    adapter.list = it.data?.rows!!
                }
                Result.Status.ERROR -> {
                    mainFragmentBinding.recyclerView.visibility = View.GONE
                    mainFragmentBinding.progressCircular.visibility = View.GONE;
                    it.message?.let { it1 -> showSnackbar(it1) }
                }
                Result.Status.LOADING -> {
                    mainFragmentBinding.recyclerView.visibility = View.GONE
                    mainFragmentBinding.progressCircular.visibility = View.VISIBLE;
                }


            }


        })

    }

    fun showSnackbar(msg: String) {
        Snackbar.make(
            mainFragmentBinding.root,
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        mainViewModel.jobCancel()
        super.onDestroy()
    }

    override fun onRefresh() {
        mainViewModel.callApi()
        mainFragmentBinding.refresh.isRefreshing = false
    }

}
