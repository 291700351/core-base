package io.github.lee.unsplash.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.SharedElementCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.util.ext.*
import io.github.lee.unsplash.R
import io.github.lee.unsplash.databinding.FragmentPhotoDetailBinding
import io.github.lee.unsplash.databinding.ToolbarDetailTransparentBinding
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.ui.main.MainVM
import io.github.lee.unsplash.ui.main.currentShare
import io.github.lee.unsplash.uitl.blurhash.BlurHash

@AndroidEntryPoint
class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding, PhotoDetailVM>() {
    private val blurHash: BlurHash by lazy {
        BlurHash(mContent, lruSize = 20, punch = 1F)
    }
    private val mainVm by lazy {
        ViewModelProvider(this)[MainVM::class.java]
    }
    private val adapter by lazy { PhotoDetailAdapter(this@PhotoDetailFragment, blurHash) }

    companion object {
        private const val KEY_POSITION = "POSITION"
        private const val KEY_PHOTO = "PHOTO"
        fun mainNavigateDetail(
            fragment: BaseFragment<*, *>,
            photoList: List<PhotoBean>,
            position: Int,
            vararg sharedElements: Pair<View, String>
        ) {
            val list = photoList.toTypedArray()

            fragment.findNavController().navigate(
                R.id.action_mainFragment_to_photoDetailFragment,
                bundleOf(KEY_PHOTO to list, KEY_POSITION to position), null,
                FragmentNavigatorExtras(*sharedElements)
            )
        }
    }
    //===Desc:=====================================================================================

    override fun onCreateVM() = ViewModelProvider(this)[PhotoDetailVM::class.java]
    override fun onCreateVB() = FragmentPhotoDetailBinding.inflate(layoutInflater)


    override fun onCreateToolbar(): Toolbar =
        ToolbarDetailTransparentBinding.inflate(layoutInflater).toolbar

    override fun onDestroy() {
        super.onDestroy()
        blurHash.clean()
    }
    //===Desc:=====================================================================================

    override fun onInitData(savedInstanceState: Bundle?) {
        super.onInitData(savedInstanceState)
        postponeEnterTransition()
        isHover = true
        sharedElementEnterTransition = TransitionInflater.from(mContent)
            .inflateTransition(android.R.transition.move)
    }

    override fun onObserveData() {
        super.onObserveData()
        observe(mainVm.listPhoto) {
            adapter.addData(it)
        }
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?, sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                if (null == names || names.isEmpty()) {
                    return
                }
                val name = names[0]
                val view = adapter.getViewByPosition(currentShare, R.id.pvPhoto)
                    ?: return
                sharedElements?.put(name, view)
            }
        })

        vb?.vpContent?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == adapter.data.size - 2) {
                    mainVm.onRefreshOrLoadMord(false)
                }
                currentShare = position
            }
        })

    }

    override fun onSetViewData() {
        super.onSetViewData()
        translucentStatusBar(true)
        lightStatusBars(false)
        setToolbarLeftImage(R.drawable.ic_back) { navigateUp() }

        val currentPosition = getArgumentValue(KEY_POSITION, 0) ?: 0
        val list = getArgumentParcelableArray<PhotoBean>(KEY_PHOTO)
        vb?.apply {
            vpContent.orientation = ViewPager2.ORIENTATION_VERTICAL
            vpContent.adapter = adapter
            adapter.setNewInstance(list.toMutableList())
            vpContent.setCurrentItem(currentPosition, false)
        }
    }


}