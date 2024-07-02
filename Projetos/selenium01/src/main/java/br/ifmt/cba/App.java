package br.ifmt.cba;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        
        //definindo a localizacao do drive do navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Software\\chromedriver\\chromedriver.exe");
        //instanciando um objeto que ira interagir com o navegador
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://www.google.com.br"); //acessando uma pagina valida
        driver.manage().window().maximize(); //maximizando a janela do navegador
        System.out.println("Titulo: "+driver.getTitle()); //obtendo o titulo da pagina
        System.out.println("URL: "+driver.getCurrentUrl()); //obtendo a URL atual
        //driver.quit(); //encerrando a instancia do navegador
    }
}
