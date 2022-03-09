package io.github.lee.tmdb.ui.main.movie

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import io.github.lee.tmdb.R
import io.github.lee.tmdb.databinding.ABinding

class MainMovieAdapter :
    BaseQuickAdapter<Any, BaseDataBindingHolder<ABinding>>(R.layout.a) {



    override fun convert(holder: BaseDataBindingHolder<ABinding>, item: Any) {
//        ABinding.bind(holder.itemView)
           holder.dataBinding?.str = item.toString()

//        holder.dataBinding?.apply {
//            str = item.toString()
//        }
    }

}