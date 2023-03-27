package edu.nmu.fit.TitovFITProject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class SeleniumManager {
    private static int ITEM_NUM = 1;
    public List<String> getAttributes(Element webElement){
        List<String> output = new ArrayList<String>();
        String text = null;
        text = webElement.child(3).getElementsByAttribute("title").attr("title");
        if(text == ""){
            text = webElement.child(4).getElementsByAttribute("title").attr("title");
            output.add(text);
            output.add(String.valueOf(ITEM_NUM));
            text = webElement.child(4).getElementsByAttribute("title").attr("href");
            output.add(text);
            text = webElement.child(6).child(1).child(0).text();
            output.add(text);
        }else{
            output.add(text);
            output.add(String.valueOf(ITEM_NUM));
            text = webElement.child(3).getElementsByAttribute("title").attr("href");
            output.add(text);
            text = webElement.child(5).child(0).child(0).child(0).text();
            output.add(text);
        }

        ITEM_NUM++;
        return output;
    }

    public Elements getHairdryers(){
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--headless");
        WebDriver driver = new ChromeDriver(co);

        driver.get("https://bt.rozetka.com.ua/ua/hairdryers/c81227/regim=turbo-regim;sort=cheap/");
        List<WebElement> webElements = driver.findElement(new By.ByXPath("//ul[@class='catalog-grid ng-star-inserted']")).findElements(new By.ByTagName("li"));

        Document doc = Jsoup.parse(driver.getPageSource());

        return doc.getElementsByAttributeValue("class","goods-tile__inner");
    }
}
