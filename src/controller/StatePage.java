package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StatePage implements Initializable {
    @FXML
    TextArea statement;
    @Override
    /**
     * 初始化界面
     */
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getStatement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中得到说明
     * @throws IOException
     */
    private void getStatement()throws IOException{
        File file=new File("src/statement.txt");
        FileInputStream fis = new FileInputStream(file);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        try {
            InputStreamReader reader = new InputStreamReader(fis,"GBK");
            BufferedReader br = new BufferedReader(reader);
            String temp = null;
            StringBuffer sb = new StringBuffer();
            temp = br.readLine();
            while (temp != null) {
                sb.append(temp).append('\n');
                temp = br.readLine();
            }
            statement.setFont(new Font("Arial", 15));
            statement.setText(sb.toString());
            br.close();
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
