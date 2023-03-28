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

    public Elements getHairdryers(String item) throws InterruptedException {
        ITEM_NUM=1;


        driver.get("https://rozetka.com.ua/ua/search/?text="+item+"&sort=cheap");

        Document doc = Jsoup.parse(driver.getPageSource());
        Elements elements = doc.getElementsByAttributeValue("class","goods-tile__inner");
        Elements buttons = doc.getElementsByAttributeValue("class","button button--gray button--medium pagination__direction pagination__direction--forward ng-star-inserted");
        Element button = buttons.first();
        Elements temp = null;
        while(button!=null){
            driver.get("https://rozetka.com.ua"+button.attr("href"));
            //driver.findElement(By.xpath("//a[@class='goods-tile__picture ng-star-inserted']")).sendKeys("cheese" + Keys.ENTER);
            WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='goods-tile__picture ng-star-inserted']")));
            System.out.println(firstResult.getText());
            doc = Jsoup.parse(driver.getPageSource());
            temp = doc.getElementsByAttributeValue("class","goods-tile__inner");
            for(int i = 0; i <temp.size();i++){
                elements.add(temp.get(i));
            }
            buttons = doc.getElementsByAttributeValue("class","button button--gray button--medium pagination__direction pagination__direction--forward ng-star-inserted");
            button = buttons.first();
        }

        return elements;
    }
}
