package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class BingHomePage {
    WebDriver driver;
    WebDriverWait wait;

    public BingHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> getAllLinks() {
        return driver.findElements(By.tagName("a"));
    }

    public WebElement getSearchBox() {
        return driver.findElement(By.name("q"));
    }

    public List<WebElement> getSuggestions() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']//li")));
        return driver.findElements(By.xpath("//ul[@role='listbox']//li"));
    }

    public List<WebElement> getAllResults() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("b_results")));
        return driver.findElements(By.xpath("//ol[@id='b_results']//li"));
    }

    public WebElement getTab(String tabName) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + tabName + "')]")));
    }

    public List<WebElement> getTabItems(String xpath) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        return driver.findElements(By.xpath(xpath));
    }
}
