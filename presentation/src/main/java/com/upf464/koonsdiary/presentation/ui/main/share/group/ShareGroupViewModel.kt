package com.upf464.koonsdiary.presentation.ui.main.share.group

import androidx.lifecycle.ViewModel
import com.upf464.koonsdiary.presentation.common.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareGroupViewModel @Inject constructor(
    val dateTimeUtil: DateTimeUtil
) : ViewModel() {


}
