package com.example.todo_app.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.todo_app.BR
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, DB : ViewDataBinding> :
  DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  protected abstract fun getViewModel(): Class<VM>

  @LayoutRes
  protected abstract fun getLayoutRes(): Int

  protected lateinit var viewModel: VM
  protected lateinit var binding: DB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.setVariable(BR.viewModel, viewModel)
    return binding.root
  }
}