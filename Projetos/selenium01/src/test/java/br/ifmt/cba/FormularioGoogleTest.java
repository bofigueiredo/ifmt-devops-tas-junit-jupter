package br.ifmt.cba;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FormularioGoogleTest {

    private static WebDriver driver;

    @BeforeAll
    public static void iniciando(){
        System.setProperty("webdriver.chrome.driver", "C:\\Software\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }


    @Test
    public void testando() {
        driver.navigate().to("http://www.google.com.br");
        System.out.println("Titulo: "+driver.getTitle());
        driver.manage().window().maximize();
        WebElement caixaPesquisa = driver.findElement(By.name("q"));
        caixaPesquisa.clear();
        caixaPesquisa.sendKeys("Selenium Java");
        WebElement botaoPesquisar = driver.findElement(By.name("btnK"));
        botaoPesquisar.submit();        
    }

    @AfterAll
    public static void encerrando(){
        driver.quit();
    }

}
