import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import models.User;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class app{
	public static void main(String []args) throws InterruptedException, IOException{
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        WebDriverWait waitVar = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
		// navigate to the website
        driver.get("https://www.tunisianet.com.tn/");
        Thread.sleep(1000);
        
        // get the user icon
        WebElement userIcon = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userIcon.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]")));
        WebElement connexionButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]"));
        connexionButton.click();

        // signIn page
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("no-account")));
        WebElement createAccountButton = driver.findElement(By.className("no-account"));
        createAccountButton.click();
        Thread.sleep(1000);

        // signUp page
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/span/following-sibling::hr")));

        // user generation
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
        String codeForEmailAndPwd = RandomStringUtils.random(10, true, true);
        String codeForNames = RandomStringUtils.random(10, true, false);
        User generatedUser = new User(
                codeForNames + "firstname",
                codeForNames + "lastname",
                codeForEmailAndPwd.substring(5) + "test@test.com",
                codeForEmailAndPwd,
                new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1) * 365 * 22) 
        );
        // Fill user info
        WebElement maleRadioInput = driver.findElement(By.xpath("//input[@name='id_gender' and @value='1']"));
        maleRadioInput.click();
        List<WebElement> formFields = driver.findElements(By.cssSelector("input.form-control"));
        formFields.get(1).sendKeys(generatedUser.firstName);
        formFields.get(2).sendKeys(generatedUser.lastName);
        formFields.get(3).sendKeys(generatedUser.email);
        formFields.get(4).sendKeys(generatedUser.password);
        formFields.get(5).sendKeys(dateFormatter.format(generatedUser.birthday));
        
        // scroll down and sign up
        js.executeScript("window.scrollBy(0,10)", "");
        Thread.sleep(1000);
        WebElement signupButton = driver.findElement(By.className("form-control-submit"));
        signupButton.click();
        Thread.sleep(2000);

        // sign out
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]")));
        userIcon = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userIcon.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//a[@class='logout']")));
        WebElement deconnexionButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//a[@class='logout']"));
        deconnexionButton.click();

        Thread.sleep(2000);

        // signIn with the generated user
        userIcon = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userIcon.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]")));
        connexionButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]"));
        connexionButton.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'  Connectez-vous à votre compte')]")));

        // fill generated user info
        WebElement emailElement = driver.findElement(By.xpath("//input[contains(@class,'form-control')][@name='email']"));
        WebElement passwordElement = driver.findElement(By.xpath("//input[contains(@class,'form-control')][@name='password']"));
        WebElement submitButton = driver.findElement(By.id("submit-login"));
        emailElement.sendKeys(generatedUser.email);
        passwordElement.sendKeys(generatedUser.password);
        submitButton.click();
        Thread.sleep(2000);
        
        // search for the product
        WebElement searchBar = driver.findElement(By.className("search_query"));
        searchBar.sendKeys("PC portable MacBook M1 13.3");
        WebElement searchButton = driver.findElement(By.cssSelector("#sp-btn-search > button"));
        searchButton.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Résultats de la recherche')]")));
        js.executeScript("window.scrollBy(0,20)", "");
        Thread.sleep(2000);

        // Click on the first product
        Thread.sleep(1500);
        List<WebElement> firstSearchResult = driver.findElements(By.className("product-title"));
        firstSearchResult.get(0).click();

        // Add product to cart
        Thread.sleep(1500);
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();

        // Click to order
        Thread.sleep(1500);
        WebElement orderButton = driver.findElement(By.cssSelector("a.btn-block"));
        orderButton.click();

        // quit the web driver
        driver.quit();
	}}



