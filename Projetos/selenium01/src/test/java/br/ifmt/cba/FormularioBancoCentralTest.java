package br.ifmt.cba;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FormularioBancoCentralTest {

    private static WebDriver driver;

    @BeforeAll
    public static void iniciando(){
        System.setProperty("webdriver.chrome.driver", "C:\\Software\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void valorParcelaTest() {
        driver.navigate().to("https://www3.bcb.gov.br/CALCIDADAO/publico/exibirFormFinanciamentoPrestacoesFixas.do?method=exibirFormFinanciamentoPrestacoesFixas");
        driver.manage().window().maximize();

        WebElement campoMeses = driver.findElement(By.name("meses"));
        campoMeses.clear();
        campoMeses.sendKeys("120,0");

        WebElement campoTaxa = driver.findElement(By.name("taxaJurosMensal"));
        campoTaxa.clear();
        campoTaxa.sendKeys("1,0");

        WebElement campoValorFinanciado = driver.findElement(By.name("valorFinanciado"));
        campoValorFinanciado.clear();
        campoValorFinanciado.sendKeys("300000,00");
                        
        WebElement botaoCalcular = driver.findElement(By.name("botaoCalcular"));
        botaoCalcular.submit();

        WebElement campoValorPrestacao = driver.findElement(By.name("valorPrestacao"));
        
        assertEquals("4.304,13", campoValorPrestacao.getAttribute("value"));
    }

    @AfterAll
    public static void encerrando(){
        driver.quit();
    }

}
