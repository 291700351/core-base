package io.github.lee.tmdb.ui.main

import android.graphics.Color
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.tmdb.R

class A : BaseFragment<ViewDataBinding, BaseViewModel>() {

    override fun onSetViewListener() {
        super.onSetViewListener()
        getContentRoot().setOnClickListener {
            findNavController().navigate(R.id.action_a2_to_b)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        setStatusBarColor(Color.YELLOW)
        getContentRoot().setBackgroundColor(Color.GREEN)
    }

    override fun onCreateVB(): ViewDataBinding? = null

    override fun onCreateVM(): BaseViewModel? = null

}

class B : BaseFragment<ViewDataBinding, BaseViewModel>() {
    override fun onSetViewListener() {
        super.onSetViewListener()
        getContentRoot().setOnClickListener {
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        getContentRoot().setBackgroundColor(Color.YELLOW)
    }

    override fun onCreateVB(): ViewDataBinding? {
        return null
    }

    override fun onCreateVM(): BaseViewModel?=null
}

class C : BaseFragment<ViewDataBinding, BaseViewModel>() {
    override fun onSetViewListener() {
        super.onSetViewListener()
        getContentRoot().setOnClickListener {
            //            startActivity(Intent(mContent, Global2Activity::class.java))
            findNavController().navigate(R.id.action_mainFragment_to_a2)
        }
    }


    override fun onCreateVB(): ViewDataBinding? {
        return null
    }

    override fun onCreateVM(): BaseViewModel?=null
    override fun onSetViewData() {
        super.onSetViewData()
        getContentRoot().setBackgroundColor(Color.GRAY)
    }

    override fun onResume() {
        super.onResume()
        translucentStatusBar()
    }
}

class D : BaseFragment<ViewDataBinding, BaseViewModel>() {
    override fun onSetViewData() {
        super.onSetViewData()
        getContentRoot().setBackgroundColor(Color.BLUE)
    }


    override fun onCreateVB(): ViewDataBinding? {
        return null
    }

    override fun onCreateVM(): BaseViewModel?=null
    override fun onResume() {
        super.onResume()
        translucentStatusBar()
    }
}


class MainPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment.childFragmentManager,
    LifecycleRegistry(fragment).apply {
        currentState = Lifecycle.State.RESUMED
    }) {


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        //        return when (position) {
        //            0 -> MainMovieFragment.newInstance()
        //            1 -> C()
        //            2 -> D()
        //            else ->MainMovieFragment.newInstance()
        //        }

        return pages[position] ?: throw IllegalAccessError("Can not find page")
    }
}