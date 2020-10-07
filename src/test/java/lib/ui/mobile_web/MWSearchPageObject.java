package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div > div.header-action > button";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
//        SEARCH_RESULT_BY_INDEX_WITH_SUBSTRING_TPL = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell[{INDEX}]//XCUIElementTypeOther[2]/XCUIElementTypeStaticText[contains(@name, '{SUBSTRING}')]";
//        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell[{INDEX}]//XCUIElementTypeOther[2]/XCUIElementTypeStaticText[contains(@name, '{SUBSTRING}')]";
//        SEARCH_CLEAR_TEXT = "id:Clear text";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
