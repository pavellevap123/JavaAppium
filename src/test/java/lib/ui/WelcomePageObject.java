package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEAR_MORE_LINK = "id:Learn more about Wikipedia",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
            NEXT_LINK = "id:Next",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(STEP_LEAR_MORE_LINK, "Can't find 'Learn more about Wikipedia'", 10);
    }

    public void waitForNewWayToExploreText()
    {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Can't find 'New ways to explore'", 10);
    }

    public void waitForAddOrEditPreferredLangText()
    {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Can't find 'Add or edit preferred languages'", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Can't find 'Learn more about data collected'", 10);
    }

    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_LINK, "Can't find and click 'Next' button", 10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Can't find and click 'Get started' button", 10);
    }

    public void clickSkip() {
        this.waitForElementAndClick(SKIP, "Can't find and click 'Skip' button", 5);
    }
}
