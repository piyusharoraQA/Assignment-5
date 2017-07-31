package com.mycompany.anmolb_assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author piyusharora
 */
public class Tatoc_Basic {

    static WebDriver driver;

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

        driver.get("http://10.0.1.86/tatoc");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[text()='Basic Course']")).click();
    }

    /**
     * For Selecting Green Box Problem
     */
    public static void selecting_greenbox() {

        driver.findElement(By.xpath("//*[@class='greenbox']")).click();
    }

    /**
     * For Comparing Boxes Color Problem
     */
    public static void box_color_comparison() {

        driver.switchTo().frame("main");
        WebElement element1 = driver.findElement(By.xpath("//div[@id='answer']"));
        String Box1;
        String Box2;
        Box1 = element1.getAttribute("class");

        driver.switchTo().frame("child");
        WebElement element2 = driver.findElement(By.xpath(".//*[@id='answer']"));
        Box2 = element2.getAttribute("class");

        System.out.println(Box1);

        System.out.println(Box2);

        driver.switchTo().parentFrame();
        while (!Box1.equals(Box2)) {
            driver.findElement(By.xpath("//*[text()='Repaint Box 2']")).click();
            driver.switchTo().frame("child");
            WebElement element3 = driver.findElement(By.xpath(".//*[@id='answer']"));
            Box2 = element3.getAttribute("class");
            driver.switchTo().parentFrame();
        }

        driver.findElement(By.xpath("//*[text()='Proceed']")).click();
    }

    /**
     * For Drag And Drop Problem
     */
    public static void drag_and_drop() {
        Actions act = new Actions(driver);
        WebElement drag = driver.findElement(By.xpath("//*[text()='DRAG ME']"));
        WebElement drop = driver.findElement(By.xpath("//*[text()='DROPBOX']"));

        act.dragAndDrop(drag, drop).perform();
        driver.findElement(By.xpath("//*[text()='Proceed']")).click();
    }

    /**
     * For Popup handling Problem
     *
     * @throws InterruptedException
     */
    public static void popup_handling() throws InterruptedException {
        String windowhandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//*[text()='Launch Popup Window']")).click();
        for (String handle : driver.getWindowHandles()) {

            driver.switchTo().window(handle);

        }

        driver.findElement(By.xpath("//*[@id='name']")).sendKeys("Piyush Arora");
        driver.findElement(By.xpath("//*[@id='submit']")).click();
        driver.switchTo().window(windowhandle);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[text()='Proceed']")).click();
    }

    /**
     * For Cookie Based Problem
     */
    public static void cookie_add() {
        driver.findElement(By.xpath("//*[text()='Generate Token']")).click();
        String token = driver.findElement(By.xpath("//*[@id='token']")).getText();
        String tkn = token.split(": ")[1];
        Cookie cookie = new Cookie("Token", tkn);
        driver.manage().addCookie(cookie);
        driver.findElement(By.xpath("//*[text()='Proceed']")).click();
    }

    /**
     * End Of Tatoc Basic Displaying The End Message
     *
     * @throws InterruptedException
     */
    public static void end_message() throws InterruptedException {
        System.out.println(driver.findElement(By.xpath("//div[@class='page']//span")).getText());
        Thread.sleep(3000);
        driver.quit();
    }
}
