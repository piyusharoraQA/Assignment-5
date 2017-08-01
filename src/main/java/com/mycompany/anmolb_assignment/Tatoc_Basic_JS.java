package com.mycompany.anmolb_assignment;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author piyusharora
 */
public class Tatoc_Basic_JS {

    static WebDriver driver;
    static WebElement element;

    public static void main(String[] args) throws InterruptedException {
        String user_dir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", user_dir + "\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        start_up_chrome();
        selecting_greenbox();
        box_color_comparison();
        drag_and_drop();
        popup_handling();
        cookie_add();
        end_message();

    }

    /**
     * Firing Up Chrome And Loading Base Address
     *
     * @throws InterruptedException
     */
    public static void start_up_chrome() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("http://10.0.1.86/tatoc");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        element = (WebElement) js.executeScript("return document.evaluate( \"//a[text()='Basic Course']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        element.click();

    }

    /**
     * For Selecting Green Box Problem
     */
    public static void selecting_greenbox() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        element = (WebElement) js.executeScript("return document.evaluate( \" //*[@class='greenbox']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        element.click();
    }

    /**
     * For Comparing Boxes Color Problem
     */
    public static void box_color_comparison() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.switchTo().frame("main");
        WebElement element1 = (WebElement) js.executeScript("return document.evaluate( \"//div[@id='answer']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        String Box1;
        String Box2;
        Box1 = element1.getAttribute("class");
        driver.switchTo().frame("child");
        WebElement element2 = (WebElement) js.executeScript("return document.evaluate( \"//*[@id='answer']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        Box2 = element2.getAttribute("class");
        System.out.println(Box1);
        System.out.println(Box2);
        driver.switchTo().parentFrame();
        while (!Box1.equals(Box2)) {

            WebElement Repaint = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Repaint Box 2']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
            Repaint.click();
            driver.switchTo().frame("child");
            WebElement element3 = (WebElement) js.executeScript("return document.evaluate( \"//*[@id='answer']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
            Box2 = element3.getAttribute("class");
            driver.switchTo().parentFrame();
        }

        WebElement proceed = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Proceed']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        proceed.click();
    }

    /**
     * For Drag And Drop Problem
     */
    public static void drag_and_drop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions act = new Actions(driver);
        WebElement drag = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='DRAG ME']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        WebElement drop = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='DROPBOX']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        act.dragAndDrop(drag, drop).perform();
        WebElement proceed = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Proceed']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        proceed.click();
    }

    /**
     * For Popup handling Problem
     *
     * @throws InterruptedException
     */
    public static void popup_handling() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String windowhandle = driver.getWindowHandle();
        WebElement popup = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Launch Popup Window']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        popup.click();
        for (String handle : driver.getWindowHandles()) {

            driver.switchTo().window(handle);

        }

        WebElement input = (WebElement) js.executeScript("return document.evaluate( \"//*[@id='name']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        input.sendKeys("Piyush Arora");
        WebElement submit = (WebElement) js.executeScript("return document.evaluate( \"//*[@id='submit']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        submit.click();
        driver.switchTo().window(windowhandle);
        Thread.sleep(1000);
        WebElement proceed = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Proceed']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        proceed.click();

    }

    /**
     * For Cookie Based Problem
     */
    public static void cookie_add() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement token_gen = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Generate Token']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        token_gen.click();
        WebElement token = (WebElement) js.executeScript("return document.evaluate( \"//*[@id='token']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        String text = token.getText();
        String tkn = text.split(": ")[1];
        Cookie cookie = new Cookie("Token", tkn);
        driver.manage().addCookie(cookie);
        WebElement proceed = (WebElement) js.executeScript("return document.evaluate( \"//*[text()='Proceed']\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        proceed.click();
    }

    /**
     * End Of Tatoc Basic Displaying The End Message
     *
     * @throws InterruptedException
     */
    public static void end_message() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement finale = (WebElement) js.executeScript("return document.evaluate( \"//div[@class='page']//span\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
        System.out.println(finale.getText());
        Thread.sleep(3000);
        driver.quit();
    }
}
