/*package tests;

import utils.ExcelUtils;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import ru.yandex.qatools.ashot.*;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BingSearchCaseStudy {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() throws IOException {
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.bing.com");
        new File("screenshot").mkdirs();
        ExcelUtils.openExcel("Sheet1");
    }

    @Test(priority = 1)
    public void getHomePageLinks() throws IOException {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        String result = "Total links: " + links.size();
        System.out.println(result);
        String status = (links.size() > 0) ? "Pass" : "Fail";
        ExcelUtils.writeResult(1, result, status);
    }

    @Test(priority = 2)
    public void searchAndCaptureSuggestions() throws IOException {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Cognizant");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']//li")));
        List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@role='listbox']//li"));

        String result = "Suggestions: " + suggestions.size();
        System.out.println(result);
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File("screenshot/suggestions.png"));
        String status = (suggestions.size() > 0) ? "Pass" : "Fail";
        ExcelUtils.writeResult(2, result, status);

        searchBox.sendKeys(Keys.ENTER);
    }

    @Test(priority = 3)
    public void allTabResults() throws IOException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("b_results")));
        List<WebElement> results = driver.findElements(By.xpath("//ol[@id='b_results']//li"));
        String result = "All tab results: " + results.size();
        System.out.println(result);
        captureFullPage("allTab.png");
        String status = (results.size() > 0) ? "Pass" : "Fail";
        ExcelUtils.writeResult(3, result, status);
    }

    @Test(priority = 4)
    public void newsTabResults() throws IOException {
        clickTabAndCapture("News", "//div[contains(@class,'news-card')]", "newsTab.png", 4);
    }

    @Test(priority = 5)
    public void imagesTabResults() throws IOException {
        clickTabAndCapture("Images", "//a[contains(@class,'iusc')]", "imagesTab.png", 5);
    }

    @Test(priority = 6)
    public void videosTabResults() throws IOException {
        clickTabAndCapture("Videos", "//div[starts-with(@id,'mc_vtvc_video')]", "videosTab.png", 6);
    }

    public void clickTabAndCapture(String tabName, String xpathForLinks, String screenshotName, int rowIndex) throws IOException {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + tabName + "')]")));
            tab.click();
        } catch (TimeoutException e) {
            System.out.println("Tab not found: " + tabName);
            ExcelUtils.writeResult(rowIndex, tabName + ": Tab not found", "Fail");
            return;
        }

        switchToNewTab(driver.getWindowHandle());

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathForLinks)));
        List<WebElement> items = driver.findElements(By.xpath(xpathForLinks));
        String result = tabName + " tab results: " + items.size();
        System.out.println(result);
        captureFullPage(screenshotName);
        String status = (items.size() > 0) ? "Pass" : "Fail";
        ExcelUtils.writeResult(rowIndex, result, status);
    }

    public void captureFullPage(String filename) throws IOException {
        Screenshot screenshot = new AShot()
            .shootingStrategy(ShootingStrategies.simple())
            .takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG", new File("screenshot/" + filename));
    }

    public void switchToNewTab(String originalTab) {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                driver.switchTo().window(originalTab).close();
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    @AfterClass
    public void tearDown() throws IOException {
        ExcelUtils.printResultsInConsole();
        if (driver != null) driver.quit();
    }
}*/

package tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BingHomePage;
import utils.ExcelUtils;
import ru.yandex.qatools.ashot.*;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BingSearchCaseStudy {
    WebDriver driver;
    BingHomePage bing;
    String originalTab;

    @BeforeClass
    public void setup() throws IOException {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.bing.com");
        new File("screenshot").mkdirs();
        ExcelUtils.openExcel("Sheet1");
        bing = new BingHomePage(driver);
        originalTab = driver.getWindowHandle();
    }

    @Test(priority = 1)
    public void getHomePageLinks() throws IOException {
        List<WebElement> links = bing.getAllLinks();
        String result = "Total links: " + links.size();
        System.out.println(result);
        ExcelUtils.writeResult(1, result, links.size() > 0 ? "Pass" : "Fail");
    }

    @Test(priority = 2)
    public void searchAndCaptureSuggestions() throws IOException {
        WebElement searchBox = bing.getSearchBox();
        searchBox.sendKeys("Cognizant");
        List<WebElement> suggestions = bing.getSuggestions();
        String result = "Suggestions: " + suggestions.size();
        System.out.println(result);
        takeScreenshot("suggestions.png");
        ExcelUtils.writeResult(2, result, suggestions.size() > 0 ? "Pass" : "Fail");
        searchBox.sendKeys(Keys.ENTER);
    }

    @Test(priority = 3)
    public void allTabResults() throws IOException {
        List<WebElement> results = bing.getAllResults();
        String result = "All tab results: " + results.size();
        System.out.println(result);
        captureFullPage("allTab.png");
        ExcelUtils.writeResult(3, result, results.size() > 0 ? "Pass" : "Fail");
    }

    @Test(priority = 4)
    public void newsTabResults() throws IOException {
        clickTabAndCapture("News", "//div[contains(@class,'news-card')]", "newsTab.png", 4);
    }

    @Test(priority = 5)
    public void imagesTabResults() throws IOException {
        clickTabAndCapture("Images", "//a[contains(@class,'iusc')]", "imagesTab.png", 5);
    }

    @Test(priority = 6)
    public void videosTabResults() throws IOException {
        clickTabAndCapture("Videos", "//div[starts-with(@id,'mc_vtvc_video')]", "videosTab.png", 6);
    }

    public void clickTabAndCapture(String tabName, String xpath, String filename, int row) throws IOException {
        try {
            bing.getTab(tabName).click();
        } catch (TimeoutException e) {
            ExcelUtils.writeResult(row, tabName + " Tab Not Found", "Fail");
            return;
        }

        switchToNewTab();
        List<WebElement> items = bing.getTabItems(xpath);
        String result = tabName + " tab results: " + items.size();
        System.out.println(result);
        captureFullPage(filename);
        ExcelUtils.writeResult(row, result, items.size() > 0 ? "Pass" : "Fail");
    }

    public void switchToNewTab() {
        Set<String> handles = driver.getWindowHandles();
        for (String h : handles) {
            if (!h.equals(originalTab)) {
                driver.switchTo().window(h);

                // Wait for page to load completely
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
                );
                break;
            }
        }
    }


    public void captureFullPage(String filename) throws IOException {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.simple())
                .takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG", new File("screenshot/" + filename));
    }

    public void takeScreenshot(String filename) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshot/" + filename);
        org.openqa.selenium.io.FileHandler.copy(src, dest);
    }

    @AfterClass
    public void tearDown() throws IOException {
        ExcelUtils.printResultsInConsole();
        driver.quit();
    }
}

