package com.belajar.submissionjetpackcompose.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.belajar.submissionjetpackcompose.model.Food
import com.belajar.submissionjetpackcompose.ui.screen.detail.DetailInformation
import com.belajar.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataFood = Food(
        id = "1",
        name = "Telur",
        photoUrl = "https://cdn.britannica.com/94/151894-050-F72A5317/Brown-eggs.jpg",
        description = "Telur adalah salah satu sumber protein hewani yang memiliki rasa yang lezat, mudah dicerna, dan bergizi tinggi.",
        category = "Hewani"
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme {
                DetailInformation(
                    id = fakeDataFood.id,
                    name = fakeDataFood.name,
                    image = fakeDataFood.photoUrl,
                    description = fakeDataFood.description,
                    category = fakeDataFood.category,
                    isFavorite = fakeDataFood.isFavorite,
                    navigateBack = { },
                    onFavoriteButtonClicked = {_->}
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithText(fakeDataFood.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataFood.category).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataFood.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favButton").assertHasClickAction()
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        composeTestRule.onNodeWithTag("favButton").assertIsDisplayed()

        val isFavorite = fakeDataFood.isFavorite
        val expectedContentDescription = if (isFavorite) {
            "Remove fav"
        } else {
            "Add to Fav"
        }

        composeTestRule.onNodeWithTag("favButton")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}