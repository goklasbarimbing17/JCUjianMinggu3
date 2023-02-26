package com.juaracoding.exam3;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestShopDemoQa {
    WebDriver driver;
    JavascriptExecutor js;
    WebElement element;
    Select selectObjek;

    public void getUrl(String url) {
        driver.get(url);
    }

    public void delayTime(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void scroll(String pixel) {
        js = (JavascriptExecutor) driver;
        js.executeScript(pixel);
    }

    public void setChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\Software Testing\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void setUp() {
        setChromeDriver();
        getUrl("https://shop.demoqa.com/my-account/");
    }

    @Test
    public void accountRegis() {
        delayTime(2);
        scroll("window.scrollBy(0,580)");
        driver.findElement(By.xpath("//input[@id='reg_username']")).sendKeys("bimosakti");
        driver.findElement(By.xpath("//input[@id='reg_email']")).sendKeys("bimosakti@gmail.com");
        driver.findElement(By.xpath("//input[@id='reg_password']")).sendKeys("BimoSakti123$");
        driver.findElement(By.xpath("//button[@name='register']")).click();
    }

    @Test(priority = 1, description = "Login with valid credentials, valid username and valid password")
    public void testLogin1() { //Bug : setelah proses registrasi selesai. di inputan username bagian login username terisi otomatis
        scroll("window.scrollBy(0,580)");
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("bimosakti");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("BimoSakti123$");
        driver.findElement(By.xpath("//button[@name='login']")).click();

        scroll("window.scrollBy(0,400)");
        String txtHelloUser = driver.findElement(By.xpath("//div[@id='primary']//p[1]")).getText();
        Assert.assertTrue(txtHelloUser.contains("bimosakti"));
    }

    @Test(priority = 2, description = "Login with invalid credentials, valid username and invalid password")
    public void testLogin2() {
        scroll("window.scrollBy(0,580)");
        delayTime(2);
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("bimosakti");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("BimoSakti");
        driver.findElement(By.xpath("//button[@name='login']")).click();
        scroll("window.scrollBy(0,515)");
        delayTime(2);

        String msgInvalidLogin = driver.findElement(By.xpath("//ul[@role='alert']//li")).getText();
        Assert.assertTrue(msgInvalidLogin.contains("ERROR: The username or password you entered is incorrect. Lost your password?"));
    }

    @Test(priority = 3, description = "Login with invalid credentials, invalid username and valid password")
    public void testLogin3() {
        scroll("window.scrollBy(0,580)");
        delayTime(2);
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("bimosak");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("BimoSakti123$");
        driver.findElement(By.xpath("//button[@name='login']")).click();
        scroll("window.scrollBy(0,515)");

        String msgInvalidLogin = driver.findElement(By.xpath("//div[@id='primary']//li[1]")).getText();
        Assert.assertTrue(msgInvalidLogin.contains("ERROR: The username or password you entered is incorrect. Lost your password?"));
    }

    @Test(priority = 4, description = "Login with providing one credential, don't enter username and valid password")
    public void testLogin4() {
        delayTime(2);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("BimoSakti123$");
        driver.findElement(By.xpath("//button[@name='login']")).click();
        scroll("window.scrollBy(0,515)");

        String msgInvalidLogin = driver.findElement(By.xpath("//div[@id='primary']//li[1]")).getText();
        Assert.assertTrue(msgInvalidLogin.contains("Error: Username is required."));
    }

    @Test(priority = 5, description = "Login with providing one credential, valid username and don't entered password")
    public void testLogin5() {
        scroll("window.scrollBy(0,580)");
        delayTime(2);
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("bimosakti");
        driver.findElement(By.xpath("//button[@name='login']")).click();
        scroll("window.scrollBy(0,515)");
        delayTime(1);
        String msgInvalidLogin = driver.findElement(By.xpath("//div[@id='primary']//li[1]")).getText();
        Assert.assertTrue(msgInvalidLogin.contains("Error: The password field is empty."));
    }

    @Test(priority = 6, description = "Login without providing any credential, don't entered username and password")
    public void testLogin6() {
        scroll("window.scrollBy(0,580)");
        delayTime(2);
        driver.findElement(By.xpath("//button[@name='login']")).click();
        scroll("window.scrollBy(0,515)");
        delayTime(1);
        String msgInvalidLogin = driver.findElement(By.xpath("//div[@id='primary']//li[1]")).getText();
        Assert.assertTrue(msgInvalidLogin.contains("Error: Username is required."));
    }

    @Test (priority = 7)
    public void testAddProduk() {
        testLogin1();
        scroll("window.scrollBy(0,-400)");
        driver.findElement(By.xpath("//i[@class='icon_bag_alt']")).click();
        scroll("window.scrollBy(0, 390)");
        driver.findElement(By.xpath("//a[@class='button wc-backward wp-element-button']")).click();
        scroll("window.scrollBy(0, 515)");
        driver.findElement(By.xpath("//div[@class='noo-product-item one noo-product-sm-4 not_featured post-1441 product type-product status-publish has-post-thumbnail product_cat-bodycon-dresses product_tag-bodycon-dresses product_tag-women has-featured last instock shipping-taxable purchasable product-type-variable']//div[@class='owl-item active']//img[@class='product-one-thumb']")).click();
        scroll("window.scrollBy(0, 610)");
        //select color
        element = driver.findElement(By.xpath("//select[@id='pa_color']"));
        selectObjek = new Select(element);
        selectObjek.selectByValue("black");
        //select size
        element = driver.findElement(By.xpath("//select[@id='pa_size']"));
        selectObjek = new Select(element);
        selectObjek.selectByValue("medium");

        //validasi select black and medium
        String txtColor =  driver.findElement(By.xpath("//select[@id='pa_color']")).getText();
        String txtSize =  driver.findElement(By.xpath("//select[@id='pa_size']")).getText();
        Assert.assertTrue(txtColor.contains("Black"));
        Assert.assertTrue(txtSize.contains("Medium"));

        driver.findElement(By.xpath("//button[normalize-space()='Add to cart']")).click();
        scroll("window.scrollBy(0, 200)");

        //click viewCart
        driver.findElement(By.xpath("//a[@class='button wc-forward wp-element-button']")).click();
        scroll("window.scrollBy(0, 580)");
        String nameProdukInCart = driver.findElement(By.xpath("//a[contains(text(),'black ribbed short sleeve lettuce hem midi dress -')]")).getText();
        Assert.assertTrue(nameProdukInCart.contains("BLACK RIBBED SHORT SLEEVE LETTUCE HEM MIDI DRESS - BLACK"));

    }

    @AfterMethod
    public void tearDown() {
        delayTime(3);
        driver.quit();
    }

}
