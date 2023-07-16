package edu.nmu.fit.TitovFITProject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumManager {
    private static int ITEM_NUM = 1;
    private static final int DURATION_OF_SECONDS = 10;
    ChromeOptions co;
    WebDriver driver;
    public SeleniumManager(){
        co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--headless");
        driver = new ChromeDriver(co);
    }
    public List<String> getAttributes(Element webElement){
        List<String> output = new ArrayList<>();
        String text;
        text = webElement.child(3).getElementsByAttribute("title").attr("title");
        if(text.equals("")){
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

        Document doc = Jsoup.parse(driver.getPageSource());
        Elements elements = doc.getElementsByAttributeValue("class","goods-tile__inner");
        Elements buttons = doc.getElementsByAttributeValue("class","button button--gray button--medium pagination__direction pagination__direction--forward ng-star-inserted");
        Element button = buttons.first();
        Elements temp;
        while(button!=null){
            driver.get("https://rozetka.com.ua"+button.attr("href"));
            WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(DURATION_OF_SECONDS))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='goods-tile__picture ng-star-inserted']")));
            doc = Jsoup.parse(driver.getPageSource());
            temp = doc.getElementsByAttributeValue("class","goods-tile__inner");
            elements.addAll(temp);
            buttons = doc.getElementsByAttributeValue("class","button button--gray button--medium pagination__direction pagination__direction--forward ng-star-inserted");
            button = buttons.first();
        }

        return elements;
    }
}
