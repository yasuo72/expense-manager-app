package com.naveenapps.expensemanager.core.domain.usecase.settings.reminder

import com.naveenapps.expensemanager.core.model.ReminderTimeState
import com.naveenapps.expensemanager.core.model.Resource
import javax.inject.Inject

class SaveReminderTimeUseCase @Inject constructor(
    private val repository: com.naveenapps.expensemanager.core.repository.ReminderTimeRepository,
) {
    suspend operator fun invoke(reminderTimeState: ReminderTimeState): Resource<Boolean> {
        return Resource.Success(repository.saveReminderTime(reminderTimeState))
    }
}
