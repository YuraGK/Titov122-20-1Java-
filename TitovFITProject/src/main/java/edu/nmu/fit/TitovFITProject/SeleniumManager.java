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
    ChromeOptions co = null;
    WebDriver driver = null;
    public SeleniumManager(){
        co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--headless");
        driver = new ChromeDriver(co);
    }
    public List<String> getAttributes(Element webElement){
        List<String> output = new ArrayList<String>();
        String text = null;
        text = webElement.child(3).getElementsByAttribute("title").attr("title");
        if(text == ""){
            text = webElement.child(4).getElementsByAttribute("title").attr("title");
            output.add(String.valueOf(ITEM_NUM));
            output.add(text);

            text = webElement.child(4).getElementsByAttribute("title").attr("href");
            output.add(text);
            try {
                text = webElement.child(6).child(1).text();
            }catch (IndexOutOfBoundsException e){
                try {
                    text = webElement.child(6).text();
                }catch (IndexOutOfBoundsException a){
                    return null;
                }
            }

            output.add(text);
        }else{
            output.add(String.valueOf(ITEM_NUM));
            output.add(text);
            text = webElement.child(3).getElementsByAttribute("title").attr("href");
            output.add(text);
            try {
                text = webElement.child(5).child(1).text();
            }catch (IndexOutOfBoundsException e){
                try {
                    text = webElement.child(5).text();
                }catch (IndexOutOfBoundsException a){
                    return null;
                }
            }
            output.add(text);
        }

        ITEM_NUM++;
        return output;
    }

    public Elements getHairdryers(String item){
        ITEM_NUM=1;


        driver.get("https://rozetka.com.ua/ua/search/?text="+item+"&sort=cheap");
        List<WebElement> webElements = driver.findElement(new By.ByXPath("//ul[@class='catalog-grid ng-star-inserted']")).findElements(new By.ByTagName("li"));

        Document doc = Jsoup.parse(driver.getPageSource());

        return doc.getElementsByAttributeValue("class","goods-tile__inner");
    }
}
