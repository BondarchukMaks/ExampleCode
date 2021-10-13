package com.digicode.dodobattery.viewModel.tutorial

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.digicode.dodobattery.data.repository.AppRepository
import com.digicode.dodobattery.viewModel.base.BaseViewModel
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo
import javax.inject.Inject

class TutorialViewModel @Inject constructor(
    private val appRepository: AppRepository
): BaseViewModel() {

    private var tutorialVideo = MutableLiveData<String>()

    init {
        fetchVideo()
    }

    fun getVideo() = toLiveData(tutorialVideo)

    private fun fetchVideo() {
        val link = appRepository.getTutorialVideoLink()
        link?.let {
            VimeoExtractor.getInstance().fetchVideoWithURL(link, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val videoStream = video.streams["720p"]
                    videoStream?.let{
                        tutorialVideo.postValue(videoStream)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.d("DoDo", "error: $throwable")
                    showError(throwable)
                }
            })
        }
    }

}