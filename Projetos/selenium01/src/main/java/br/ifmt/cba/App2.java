package br.ifmt.cba;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App2 {
    public static void main(String[] args) {
        
        //definindo a localizacao do drive do navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Software\\chromedriver\\chromedriver.exe");
        //instanciando um objeto que ira interagir com o navegador
        WebDriver driver = new ChromeDriver();
        
        driver.navigate().to("http://www.google.com.br"); //acessando uma pagina valida
        WebElement caixaPesquisa = driver.findElement(By.name("q")); //acessando um elemento pelo nome
        caixaPesquisa.clear(); //limpando o conteudo da caixa
        caixaPesquisa.sendKeys("Selenium Java"); //setando um valor como conteudo da caixa
        WebElement botaoPesquisar = driver.findElement(By.name("btnK")); //acessando o botao
        botaoPesquisar.submit(); //acionando o botao
        //driver.quit(); //encerrando a instancia do navegador
    }
}
