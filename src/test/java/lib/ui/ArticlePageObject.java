package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            NAME_OF_FOLDER_TPL;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getNameOfFolderXpath(String substring)
    {
        return NAME_OF_FOLDER_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
                "Can't find title of the article",
                15
        );
    }

    public String getArticleTitle()
    {
        WebElement title_element = this.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()){
            return title_element.getAttribute("wdName");
        } else {
            return title_element.getText();
        }
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40);
        } else {
            scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Can't find the end of the article",
                    40
            );
        }
    }

    public void addArticleToMyListWithOnboarding(String name_of_folder) throws InterruptedException {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can't find element to open article options",
                5
        );

        this.waitForOneSecond();

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Can't find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Can't find overlay input field",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Can't find overlay input field to enter provided text",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Can't press OK button",
                5
        );
    }

    public void addArticleToMyListWithoutOnboarding(String name_of_folder) throws InterruptedException
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can't find element to open article options",
                5
        );

        this.waitForOneSecond();


        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5
        );

        String xpath_with_name_of_folder = getNameOfFolderXpath(name_of_folder);
        this.waitForElementAndClick(
                xpath_with_name_of_folder,
                "Folder '" + name_of_folder + "' with articles wasn't found",
                15
        );
    }

    public void addArticleToMySaved()
    {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5);
    }

    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Can't click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Can't find button to add an article to saved list after removing it from this list before"
            );
        }
    }

    public void closeArticle()
    {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Can't close article by clicking X button",
                    5
            );
        } else {
            System.out.println("Method closeArticle() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void assertTitlePresent()
    {
        this.waitForTitleElement();
        this.assertElementPresent(TITLE, "Title wasn't found on the page");
    }

    public String returnSomeAttr()
    {
        WebElement e = this.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return e.getAttribute("text");
        } else {
            return e.getAttribute("wdName");
        }
    }
}
