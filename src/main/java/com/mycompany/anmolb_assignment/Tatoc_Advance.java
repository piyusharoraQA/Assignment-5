package com.mycompany.anmolb_assignment;

import com.jayway.restassured.RestAssured;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author piyusharora
 */
public class Tatoc_Advanced {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        String user_dir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", user_dir + "//src//main//resources//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        base_address();
        hover_menu();
        database_query();
        rest_api();
        file_handler();

    }

    /**
     * Firing Up Chrome And Loading Base Address
     */
    public static void base_address() {
        driver.get("http://10.0.1.86/tatoc/end");
        driver.findElement(By.xpath("//*[text()='Advanced Course']")).click();
    }

    /**
     * Hover And Click Go Next From Menu Problem
     * @throws InterruptedException
     */
    public static void hover_menu() throws InterruptedException {
        WebElement menu2 = driver.findElement(By.xpath("//*[text()='Menu 2']"));
        Actions act = new Actions(driver);
        Thread.sleep(1000);
        act.moveToElement(menu2).build().perform();
        driver.findElement(By.xpath("//*[text()='Go Next']")).click();
    }

    /**
     * Database Querying
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void database_query() throws ClassNotFoundException, SQLException {
        String dbUrl = "jdbc:mysql://10.0.1.86/tatoc";
        String username = "tatocuser";
        String password = "tatoc01";
        String passkey = "";
        String name_full = driver.findElement(By.xpath("//*[@id='symboldisplay']")).getText();
        char name = name_full.charAt(0);
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbUrl, username, password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select name,passkey from credentials;");
        while (rs.next()) {
            if (name == (rs.getString(1)).charAt(0)) {
                passkey = rs.getString(2);
            }
        }
        String name_string = Character.toString(name);
        driver.findElement(By.xpath("//*[@id='name']")).sendKeys(name_string);
        driver.findElement(By.xpath("//*[@id='passkey']")).sendKeys(passkey);
        driver.findElement(By.xpath("//*[@id='submit']")).click();
        // closing DB Connection		
        con.close();
    }

    /**
     * Rest Web Services
     */
    public static void rest_api() {
        driver.get("http://10.0.1.86/tatoc/advanced/rest/");
        String session_id_string = driver.findElement(By.xpath("//*[@id='session_id']")).getText();
        String session_id = session_id_string.split(": ")[1];
        String token = RestAssured.get("http://10.0.1.86/tatoc/advanced/rest/service/token/" + session_id).jsonPath().getString("token");
        RestAssured.given().parameters("id", session_id, "signature", token, "allow_access", "1").post("http://10.0.1.86/tatoc/advanced/rest/service/register");
        driver.findElement(By.xpath("//*[text()='Proceed']")).click();
    }

    /**
     * File Handling
     * @throws IOException
     * @throws InterruptedException
     */
    public static void file_handler() throws IOException, InterruptedException {
        try {
            File file = new File("C:\\Users\\piyusharora\\Downloads\\file_handle_test.dat");
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception ex) {
            System.out.println("Exception Thrown"+ex);
       }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[text()='Download File']")).click();
        Thread.sleep(2000);
        FileReader file = new FileReader("C:\\Users\\piyusharora\\Downloads\\file_handle_test.dat");
        BufferedReader bf = new BufferedReader(file);
        String pointer = "";
        String line = "";
        while ((pointer = bf.readLine()) != null) {
            line = pointer;
        }
        System.out.println(line);
        String signature = line.split(": ")[1];
        driver.findElement(By.xpath("//*[@id='signature']")).sendKeys(signature);
        driver.findElement(By.xpath("//*[@value='Proceed']")).click();

    }
}
