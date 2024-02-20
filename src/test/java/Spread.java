import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Spread {

    ArrayList<String[]> spreadData;
    WebDriver driver;
    WebElement canvasElement;

    public Spread(WebDriver driver, WebElement canvasElement) throws IOException, UnsupportedFlavorException {
        this.driver = driver;
        this.canvasElement = canvasElement;
        getSpreadData();
    }

    public String getValue(int row, int col) throws IOException, UnsupportedFlavorException {
        return getValue(row,col,false);
    }

    public String getValue(int row, int col, boolean update) throws IOException, UnsupportedFlavorException {
        if (update) getSpreadData() ;
        return spreadData.get(row)[col];
    }

    public void setValue(int row, int col, String value){
        spreadData.get(row)[col] = value;
        pasteSpreadData();
    }

    private void getSpreadData() throws IOException, UnsupportedFlavorException {
        Dimension canvasSize = canvasElement.getSize();
        Actions action = new Actions(driver);
        action.moveToElement(canvasElement, -canvasSize.width/2+1, -canvasSize.height/2+1).click().perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
        String text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor); // Gets a TSV
        spreadData = tsvToArrayList(text);
    }

    private void pasteSpreadData() {
        StringSelection tsvString = new StringSelection(getSpreadDataInTsv());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(tsvString, null);
        Dimension canvasSize = canvasElement.getSize();
        Actions action = new Actions(driver);
        action.moveToElement(canvasElement, -canvasSize.width/2+1, -canvasSize.height/2+1).click().perform();
        action.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    private ArrayList<String[]> tsvToArrayList(String tsv) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(tsv));
        ArrayList<String[]> data = new ArrayList<>();
        String line;
        reader.readLine(); //Remove header
        while((line = reader.readLine()) != null){
            String[] fullRow = line.split("\t",-1);
            data.add(Arrays.copyOfRange(fullRow,1,fullRow.length)); // Remove row number
        }
        return data;
    }

    private String getSpreadDataInTsv(){
        AtomicReference<String> dataTsv = new AtomicReference<>("");
        spreadData.forEach(row -> dataTsv.set(dataTsv + String.join("\t", row) + "\n"));
        return dataTsv.get();
    }


}
