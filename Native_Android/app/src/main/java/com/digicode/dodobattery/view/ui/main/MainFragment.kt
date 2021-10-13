package com.digicode.dodobattery.view.ui.main

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.digicode.dodobattery.R
import com.digicode.dodobattery.battery.BatteryReceiver
import com.digicode.dodobattery.data.model.GotFreeWidgetStatus
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.WidgetNameKey
import com.digicode.dodobattery.provider.WidgetRemoteViewsFactory
import com.digicode.dodobattery.view.navigation.NavRouter
import com.digicode.dodobattery.view.ui.base.fragment.BaseViewModelFragment
import com.digicode.dodobattery.view.ui.zodiacs.ZodiacsDialog
import com.digicode.dodobattery.viewModel.main.MainViewModel
import com.yarolegovich.discretescrollview.DiscreteScrollView.OnItemChangedListener
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_status.view.*


class MainFragment : BaseViewModelFragment<MainViewModel>(), ZodiacsDialog.OnZodiacItemClickListener ,
    OnItemChangedListener<WidgetsAdapter.WidgetsItemViewHolder> {

    private lateinit var widgetsAdapter: WidgetsAdapter
    private lateinit var batteryReceiver: BatteryReceiver

    override fun getLayoutRes() = R.layout.fragment_main
    override fun getViewModelKey() = MainViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBatteryReceiver()
        initRecyclerView()
        initParallaxBG()
    }

    override fun initView() {
        initListeners()
    }

    override fun subscribeOnViewModelEvents() {

        viewModel.getWidgets().observeNonNull {
            widgetsAdapter.setItems(it)
        }

        viewModel.setOnWidgetStatusChangeListener {
            widgetsAdapter.notifyItemChanged(it)

            if(viewModel.selectedWidgetIndex == it){
                updateStatusLayout(it)
                updateSourceWidgets()
            }
        }

        viewModel.getIsGotFreeWidget().observeNonNull {

            if(it == GotFreeWidgetStatus.DEEP_LINK){
                NavRouter.showFreeWidgetDialog(this,false)
                viewModel.updateIsGotFreeWidget(0)
            }
            else if(it == GotFreeWidgetStatus.FRIEND){
                NavRouter.showFreeWidgetDialog(this,true)
                viewModel.updateIsGotFreeWidget(0)
            }
        }

        viewModel.getFreeAvalaibleWidgets().observeNonNull {
            updateStatusLayout(viewModel.selectedWidgetIndex)
        }
    }

    private fun initListeners() {

        statusLayout.setBuyButtonClickListener {
            statusLayout.buyButton.startAnimation()

            viewModel.unlockSelectedWidget({
                statusLayout.buyButton.revertAnimation()
            },{
                
                statusLayout.buyButton.revertAnimation()
                if(it.second != "1")
                    NavRouter.showAlertErrorDialog(this,"Something went wrong.")
            })
        }


        statusLayout.setShareButtonClickListener {
            viewModel.getShareLink{
                NavRouter.showShareDialog(this,it)
            }
        }

        menuButton.setOnClickListener {
            NavRouter.startMenuScreen(this)
        }
        tutorialLayout.setOnClickListener {
            NavRouter.startTutorialScreen(this)
        }
    }

    private fun initParallaxBG(){
        var modifier = 9 / 2
        parallaxScrollView.scrollBy((parallaxImageView.right / modifier),0)
        widgetsScrollView.scrollToPosition(viewModel.selectedWidgetIndex)
    }

    private fun initRecyclerView() {
        widgetsAdapter = WidgetsAdapter(object : OnWidgetClickListener {
            override fun onWidgetClick(widget: Widget) {
                Log.d("MyLog", "onWidgetClick - ${widget.name}")
            }

            override fun onExpandButtonClick(items: IntArray?, type: String) {
                items?.let {
                    if (type == WidgetNameKey.ZODIAC) {
                        val selected = viewModel.selectedZodiac
                        NavRouter.showZodiacsDialog(this@MainFragment, items, selected)
                    }
                }
            }
        }, context)

        widgetsScrollView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                parallaxScrollView.scrollBy(dx / 5,0)
                widgetsAdapter.animAvalaible = false
            }
        })

        initDelayScrollView()
    }

    private fun initDelayScrollView(){

        Handler().postDelayed({
            widgetsScrollView.adapter = widgetsAdapter
            widgetsScrollView.setSlideOnFling(true)
            widgetsScrollView.setSlideOnFlingThreshold(1000)
            widgetsScrollView.scrollToPosition(viewModel.selectedWidgetIndex)

            widgetsScrollView.setHasFixedSize(true)
            widgetsScrollView.setItemViewCacheSize(9);


            widgetsScrollView.addOnItemChangedListener(this)
            widgetsScrollView.setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(1.4f)
                    .setMaxScale(2f)
                    .build()
            )

            updateStatusLayout(viewModel.selectedWidgetIndex)

        }, 220)
    }

    override fun onCurrentItemChanged(holder: WidgetsAdapter.WidgetsItemViewHolder?, position: Int) {
        viewModel.updateSelectedWidget(position)
        updateStatusLayout(position)
        updateSourceWidgets()
    }

    private fun initBatteryReceiver() {
        batteryReceiver = BatteryReceiver { level, status ->
            widgetsAdapter.setBatteryIndicators(Pair(level, status))
          //  updateSourceWidgets()
        }
        activity?.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }


    private fun updateStatusLayout(widgetIndex: Int) {
        val widget = widgetsAdapter.findItemById(widgetIndex)

        widget?.let {
            statusLayout.update(widget, viewModel.getFreeAvalaibleWidgets().value ?: 0)
        }
    }


    private fun updateSourceWidgets(){
        context?.let {
            WidgetRemoteViewsFactory.updateWidget(it)
        }
    }

    override fun onZodiacClick(selected: Int) {
        viewModel.updateSelectedZodiac(selected)
        updateSourceWidgets()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(batteryReceiver)
    }

}
