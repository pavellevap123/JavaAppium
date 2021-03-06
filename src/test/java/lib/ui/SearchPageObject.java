package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_INDEX_WITH_SUBSTRING_TPL,
            SEARCH_EMPTY_SEARCH_TEXT,
            SEARCH_EMPTY_SEARCH_IMAGE,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_CLEAR_TEXT;


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementWithIndex(int index, String substring)
    {
        String xpath_with_replaced_substring = SEARCH_RESULT_BY_INDEX_WITH_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
        return xpath_with_replaced_substring.replace("{INDEX}", String.valueOf(index));
    }

    private static String getResultSearchElementWithTitleAndDescription(String title, String description)
    {
        String xpath_with_replaced_title = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title);
        return xpath_with_replaced_title.replace("{DESCRIPTION}", description);
    }

    private static String[] getResultSearchElementByCellWithIndexAndText(int number_of_cell, String title, String description)
    {
        String[] xpaths_with_replaced_data = new String[2];

        String xpath_with_replaced_index_and_title = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{INDEX}", String.valueOf(number_of_cell));
        xpath_with_replaced_index_and_title = xpath_with_replaced_index_and_title.replace("{SUBSTRING}", title);
        xpaths_with_replaced_data[0] = xpath_with_replaced_index_and_title;

        String xpath_with_replaced_index_and_description = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{INDEX}", String.valueOf(number_of_cell));
        xpath_with_replaced_index_and_description = xpath_with_replaced_index_and_description.replace("{SUBSTRING}", description);
        xpaths_with_replaced_data[1] = xpath_with_replaced_index_and_description;

        return xpaths_with_replaced_data;
    }
    /* TEMPLATE METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Can't find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Can't find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Can't find search cancel button", 5);
    }
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Can't find and click  search cancel button", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Can't find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Can't find search result with substring "+ substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Can't find and click search result with substring "+ substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Can't find anything by request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Can't find empty result component", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed to find no any results");
    }

    public void assureSearchResultArticlesIncludeKeyword(int amount_of_articles, String keyword)
    {
        for (int i=0; i<amount_of_articles; i++)
        {
            int humanReadableIndex = i+1;
            String dynamic_xpath;

            if (Platform.getInstance().isAndroid()) {
                dynamic_xpath = getResultSearchElementWithIndex(i, keyword);
            } else {
                dynamic_xpath = getResultSearchElementWithIndex(humanReadableIndex, keyword);
            }

            this.waitForElementPresent(
                    dynamic_xpath,
                    String.format("There is no article #%x of %x with mention of '%s' keyword", humanReadableIndex, amount_of_articles, keyword),
                    5
            );
        }
    }

    public void waitForEmptySearchImage()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_SEARCH_IMAGE,
                "Search empty message placeholder wasn't found",
                5
        );
    }

    public void waitForEmptySearchLabel()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_SEARCH_TEXT,
                "Search empty image placeholder wasn't found",
                5
        );
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String modified_xpath_with_title_and_desc = getResultSearchElementWithTitleAndDescription(title, description);
        this.waitForElementPresent(
                modified_xpath_with_title_and_desc,
                "Element with title '" + title + "' and description '" + description + "' wasn't found",
                15)
        ;
    }

    public void waitForElementByTitleAndDescriptionUsingCell(int number_of_cell, String title, String description)
    {
        String[] modified_xpath_with_title_and_desc = getResultSearchElementByCellWithIndexAndText(number_of_cell, title, description);
        this.waitForElementPresent(
                modified_xpath_with_title_and_desc[0],
                "Element with title '" + title + " wasn't found",
                15
        );

        this.waitForElementPresent(
                modified_xpath_with_title_and_desc[1],
                "Element with description '" + description + "' wasn't found",
                15
        );
    }

    public void clickClearButtonInSearchField()
    {
        waitForElementAndClick(
                SEARCH_CLEAR_TEXT,
                "Can't find clear button in search text field",
                15
        );
    }
}
