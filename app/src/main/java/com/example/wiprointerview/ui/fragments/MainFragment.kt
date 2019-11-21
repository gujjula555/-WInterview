package com.example.wiprointerview.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
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

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mMainFragmentBinding: MainFragmentBinding
    private var mListItemsData: List<Item> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        return mMainFragmentBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMainFragmentBinding.refresh.setOnRefreshListener(this)

        val adapter = ItemAdapter(mListItemsData)
        mMainFragmentBinding.recyclerView.adapter = adapter
        mMainFragmentBinding.progressCircular.visibility = View.VISIBLE
        mMainViewModel.mItemListData.observe(this, Observer {
             when (it.status) {
                Result.Status.SUCCESS -> {
                    mMainFragmentBinding.recyclerView.visibility = View.VISIBLE
                    mMainFragmentBinding.progressCircular.visibility = View.GONE
                   if(it.data !=null) {
                       adapter.list = it.data?.rows!!
                   }

                }
                Result.Status.ERROR -> {
                    mMainFragmentBinding.recyclerView.visibility = View.GONE
                    mMainFragmentBinding.progressCircular.visibility = View.GONE
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Result.Status.LOADING -> {
                    mMainFragmentBinding.recyclerView.visibility = View.GONE
                    mMainFragmentBinding.progressCircular.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            mMainFragmentBinding.root,
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        mMainViewModel.jobCancel()
        super.onDestroy()
    }

    override fun onRefresh() {
        mMainViewModel.callApi()
        mMainFragmentBinding.refresh.isRefreshing = false
    }
}
