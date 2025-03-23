package com.naveenapps.expensemanager.feature.export

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.designsystem.ui.utils.UiText
import com.naveenapps.expensemanager.core.domain.usecase.settings.export.ExportFileUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.model.AccountUiModel
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.ExportData
import com.naveenapps.expensemanager.core.model.ExportFileType
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    getDateRangeUseCase: GetDateRangeUseCase,
    private val exportFileUseCase: ExportFileUseCase,
    private val appComposeNavigator: AppComposeNavigator,
) : ViewModel() {

    private val _error = MutableSharedFlow<UiText?>()
    val error = _error.asSharedFlow()

    private val _success = MutableSharedFlow<ExportedMessage?>()
    val success = _success.asSharedFlow()

    private val _selectedDateRange = MutableStateFlow<UiText?>(null)
    val selectedDateRange = _selectedDateRange.asStateFlow()

    private val _exportFileType = MutableStateFlow(ExportFileType.CSV)
    val exportFileType = _exportFileType.asStateFlow()

    private val _accountCount = MutableStateFlow<UiText>(UiText.StringResource(R.string.all))
    val accountCount = _accountCount.asStateFlow()

    private var selectedDateRangeType = DateRangeType.TODAY
    private var selectedAccounts = emptyList<AccountUiModel>()
    private var isAllAccountsSelected = true

    init {
        getDateRangeUseCase.invoke().map {
            selectedDateRangeType = it.type
            _selectedDateRange.value = if (it.description.isBlank()) {
                UiText.StringResource(R.string.all_time)
            } else {
                UiText.DynamicString(it.description)
            }
        }.launchIn(viewModelScope)
    }

    fun setExportFileType(exportFileType: ExportFileType) {
        this._exportFileType.value = exportFileType
    }

    fun setAccounts(selectedAccounts: List<AccountUiModel>, isAllSelected: Boolean) {
        this.selectedAccounts = selectedAccounts
        this.isAllAccountsSelected = isAllSelected
        _accountCount.value = if (isAllSelected) {
            UiText.StringResource(R.string.all_time)
        } else {
            UiText.DynamicString(selectedAccounts.size.toString())
        }
    }

    fun export(uri: Uri?) {
        viewModelScope.launch {
            val response = exportFileUseCase.invoke(
                _exportFileType.value,
                uri?.toString(),
                selectedDateRangeType,
                selectedAccounts,
                isAllAccountsSelected,
            )
            when (response) {
                is Resource.Error -> {
                    _error.emit(UiText.StringResource(R.string.export_error_message))
                }

                is Resource.Success -> {
                    _success.emit(
                        ExportedMessage(
                            message = UiText.StringResource(R.string.export_success_message),
                            exportData = response.data
                        ),
                    )
                }
            }
        }
    }

    fun closePage() {
        appComposeNavigator.popBackStack()
    }

    fun resetSuccess() {
        viewModelScope.launch { _success.emit(null) }
    }
}

data class ExportedMessage(
    val message: UiText,
    val exportData: ExportData,
)
