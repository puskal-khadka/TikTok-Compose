package com.puskal.setting

import com.puskal.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 4/1/2023.
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
) : BaseViewModel<ViewState, SettingEvent>() {

    init {
        updateState(ViewState(settingUiData = settingUiModel))
    }

    override fun onTriggerEvent(event: SettingEvent) {
    }


}