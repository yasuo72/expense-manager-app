package com.naveenapps.expensemanager.feature.account.list

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.naveenapps.expensemanager.core.testing.TestMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountListScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestMainActivity>()

    @Test
    fun openAccountListScreenAndClickCreateButton() {
        // Start the app
        composeTestRule.setContent {
            AccountListContentView(
                state = AccountListState(
                    accounts = emptyList(),
                    showReOrder = false
                ),
                onAction = { },
            )
        }

        composeTestRule.onNodeWithTag("Create").performClick()
    }

    @Test
    fun openAccountListScreenWithDataShouldShowAccountList() {
        composeTestRule.setContent {
            AccountListContentView(
                state = AccountListState(
                    accounts = getRandomAccountUiModel(5),
                    showReOrder = false
                ),
                onAction = { },
            )
        }

        composeTestRule.onNodeWithTag("Create").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("Item").assertCountEquals(5)
    }

    @Test
    fun showAccountListEmptyStateWhenNoItemsAvailable() {
        composeTestRule.setContent {
            AccountListContentView(
                state = AccountListState(
                    accounts = emptyList(),
                    showReOrder = false
                ),
                onAction = { },
            )
        }

        composeTestRule.onNodeWithTag("Create").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("Item").assertCountEquals(0)
    }
}