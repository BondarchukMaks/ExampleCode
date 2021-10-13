package com.digicode.dodobattery.view.ui.tutorial

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.MediaController
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.extensions.popBackStack
import com.digicode.dodobattery.utils.extensions.show
import com.digicode.dodobattery.view.ui.base.fragment.BaseViewModelFragment
import com.digicode.dodobattery.viewModel.tutorial.TutorialViewModel
import kotlinx.android.synthetic.main.fragment_menu.headerLayout
import kotlinx.android.synthetic.main.fragment_tutorial.*

class TutorialFragment : BaseViewModelFragment<TutorialViewModel>() {

    override fun getLayoutRes() = R.layout.fragment_tutorial

    override fun getViewModelKey() = TutorialViewModel::class

    private lateinit var mediaController: MediaController

    override fun initView() {
        initListeners()
        initVideoView()
        initMediaController()
    }

    private fun initVideoView() {
        val displayMetrics = DisplayMetrics()
        activity?.let {
            it.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

    }

    private fun initListeners() {
        headerLayout.setButtonClickListener {
            popBackStack()
        }
    }

    override fun subscribeOnViewModelEvents() {
        viewModel.getVideo().observeNonNull {
            playVideo(it)
        }

    }

    private fun playVideo(videoStream: String) {
        videoView.show(true)
        activity?.runOnUiThread {
            videoView.setVideoPath(videoStream)
            videoView.requestFocus()
            videoView.setOnPreparedListener { mp ->
                videoView.visibility = View.VISIBLE
                mp.isLooping = true
                videoView.start()
            }
        }
    }

    private fun initMediaController() {
        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

    }
}