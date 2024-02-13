import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpreadJsTest {

    @Test
    void interactWithSpreadJs() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:4200");
//        driver.get("https://developer.mescius.com/spreadjs/demos/sample/features/worksheet/initialize-sheet/purejs/");

        int SLEEP_TIME = 1000;
        Thread.sleep(2000);
        String inputText = "Welcome to spreadJS with Selenium demo";

        Thread.sleep(1000);
        int inputVal1 = 3;
        int inputVal2 = 5;
        int sumRes = inputVal1 + inputVal2;
        int minRes = Math.min(inputVal1, inputVal2);

        setValue(driver,0,0,inputText);

        Thread.sleep(SLEEP_TIME);

        // Write labels
        setValue(driver,1,0, "Val 1");
        setValue(driver,2,0, "Val 2");
        setValue(driver,3,0, "SUM=");
        setValue(driver,4,0, "MIN=");

        Thread.sleep(SLEEP_TIME);

        // Write Values
        setValue(driver,1,1,inputVal1);
        setValue(driver,2,1,inputVal2);

        Thread.sleep(SLEEP_TIME);
        // Enter formulas
        setFormula(driver, 3,1, "=B2+B3");
        setFormula(driver, 4,1, "=MIN(B2:B3)");

        // Read results from formula cells
        int sumResInSS = ((Long)getValue(driver, 3,1)).intValue();
        int minResInSS = ((Long)getValue(driver, 4,1)).intValue();

        System.out.println("Sum value is " + sumResInSS);
        System.out.println("Min value is " + minResInSS);
        assertEquals(sumResInSS,  sumRes, "Sum value");
        assertEquals(minResInSS, minRes, "Min value");

//        driver.quit();


    //        JavascriptExecutor js = (JavascriptExecutor)driver;
    //        String script = "let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
    //                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
    //                "const sheet = spreadInstance.getActiveSheet(); " +
    //                "sheet.setValue(0,0, 'Hello from Selenium');";
    //        String getValueScript = "let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
    //                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
    //                "const sheet = spreadInstance.getActiveSheet(); " +
    //                "return sheet.getValue(0,0);";
    //        js.executeScript(script);
    //        String text = (String) js.executeScript(getValueScript);
    //        System.out.println(text);

    }

    void setValue(WebDriver driver, int row, int col, int value){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String script = String.format(
                "let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
                "const sheet = spreadInstance.getActiveSheet(); " +
                "sheet.setValue(%s,%s, %s);",row, col, value);
        js.executeScript(script);
    }

    void setValue(WebDriver driver, int row, int col, String value){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String script = String.format("let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
                "const sheet = spreadInstance.getActiveSheet(); " +
                "sheet.setValue(%s,%s, '%s');",row, col, value);
        js.executeScript(script);

    }

    void setFormula(WebDriver driver, int row, int col, String formula){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String script = String.format("let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
                "const sheet = spreadInstance.getActiveSheet(); " +
                "sheet.setFormula(%s,%s, '%s');",row, col, formula);
        js.executeScript(script);
    }

    Object getValue(WebDriver driver, int row, int col){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String script = String.format("let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
                "const sheet = spreadInstance.getActiveSheet(); " +
                "return sheet.getValue(%s,%s);",row, col);
        return js.executeScript(script);
    }

    //    Test... executeScripts only returns One of Boolean, Long, Double, String, List, Map or WebElement. Or null.
    // Not able to return other js object, I knew it, just wanted to confirm :D. Getting js exception circular reference
    // https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/JavascriptExecutor.html#executeScript-java.lang.String-java.lang.Object...-

    //    Object getSheet(WebDriver driver){
    //        JavascriptExecutor js = (JavascriptExecutor)driver;
    //        String script = String.format("let spreadHostElement = document.querySelector('[gcuielement=\"gcSpread\"]'); " +
    //                "let spreadInstance = GC.Spread.Sheets.findControl(spreadHostElement);" +
    //                "return spreadInstance.getActiveSheet();");
    //       Object sheet =  js.executeScript(script);
    //       return js.executeScript("arguments[0].getValue(0,0)",sheet);
    //
    //    }

}
