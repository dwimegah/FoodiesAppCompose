package com.belajar.submissionjetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.belajar.submissionjetpackcompose.model.FoodData
import com.belajar.submissionjetpackcompose.ui.navigation.Screen
import com.belajar.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FoodAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController
    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                FoodApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("foodList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FoodData.food[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithText(FoodData.food[5].name).assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("foodList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FoodData.food[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun searchShowListFood() {
        val key = "Telur"
        composeTestRule.onNodeWithStringId(R.string.search_food).performTextInput(key)
        composeTestRule.onNodeWithTag("foodList").performScrollToIndex(0)
    }

    @Test
    fun searchNotShowListFood() {
        val key = "Mangga"
        composeTestRule.onNodeWithStringId(R.string.search_food).performTextInput(key)
        composeTestRule.onNodeWithTag("emptyText").assertIsDisplayed()
    }

    @Test
    fun favoriteClickAndDeleteInDetailScreen_NotShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(FoodData.food[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithTag("favButton").performClick()
        composeTestRule.onNodeWithTag("favButton").performClick()
        composeTestRule.onNodeWithTag("backHome").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithTag("emptyFav").assertIsDisplayed()
    }

    @Test
    fun favoriteClickInDetailScreen_ShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(FoodData.food[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithTag("favButton").performClick()
        composeTestRule.onNodeWithTag("backHome").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(FoodData.food[0].name).assertIsDisplayed()
    }
}