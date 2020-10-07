package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String name_of_folder2 = "Tech companies";
    private static final String
            login = "Pave111",
            password = "Lolkekazaza123";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListWithOnboarding(name_of_folder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickCancelSearch();
        } else {
            articlePageObject.addArticleToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticleToMySaved();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        } else {
            myListsPageObject.closeLoginToSyncPopup();
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticles() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Apple");
        searchPageObject.clickByArticleWithSubstring("Apple Inc.");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListWithOnboarding(name_of_folder2);
            articlePageObject.closeArticle();
            searchPageObject.initSearchInput();
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickClearButtonInSearchField();
        } else {
            articlePageObject.addArticleToMySaved();
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();
            articlePageObject.addArticleToMySaved();
            searchPageObject.initSearchInput();
        }

        searchPageObject.typeSearchLine("Google");
        searchPageObject.clickByArticleWithSubstring("American");

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListWithoutOnboarding(name_of_folder2);
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickCancelSearch();
        } else {
            articlePageObject.addArticleToMySaved();
            NavigationUI navigationUI = NavigationUIFactory.get(driver);
            navigationUI.openNavigation();
            navigationUI.clickMyLists();
            MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
            myListsPageObject.swipeByArticleToDelete("Apple Inc.");
            return;
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder2);
        } else {
            myListsPageObject.closeLoginToSyncPopup();
        }
        myListsPageObject.swipeByArticleToDelete("Apple Inc.");
    }
}
