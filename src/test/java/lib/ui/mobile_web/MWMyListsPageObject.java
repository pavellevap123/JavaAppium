package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://*[@id='mw-content-text']//h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "css:#mw-content-text > ul > li > a.mw-ui-icon.mw-ui-icon-wikimedia-unStar-progressive.mw-ui-icon-element.watch-this-article.watched";
        LOGIN_SYNC_CLOSE_BUTTON = "xpath://XCUIElementTypeButton[@name='Close']";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
