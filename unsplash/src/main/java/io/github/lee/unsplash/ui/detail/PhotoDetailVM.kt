package io.github.lee.unsplash.ui.detail

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lee.core.vm.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailVM @Inject constructor(application: Application) :
    BaseViewModel(application) {


}