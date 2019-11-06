package com.example.todo_app.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(message: String, duration: Int) {
  Snackbar.make(this, message, duration).run {
    addCallback(object : Snackbar.Callback() {
      override fun onShown(sb: Snackbar?) {
        EspressoIdlingResource.increment()
      }

      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        super.onDismissed(transientBottomBar, event)
        EspressoIdlingResource.decrement()
      }
    })
    show()
  }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
  lifecycleOwner: LifecycleOwner,
  snackbarEvent: LiveData<Event<String>>,
  duration: Int
) {
  snackbarEvent.observe(lifecycleOwner, Observer { event ->
    event.getContentIfNotHandled()?.let {
      showSnackbar(it, duration)
    }
  })
}
