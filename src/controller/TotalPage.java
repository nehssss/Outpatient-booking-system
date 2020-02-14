package controller;

import entity.DepartmentMan;
import entity.MainOp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class TotalPage implements Initializable {
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private BarChart<String,Integer> bc ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTotal();
    }

    /**
     * 显示费用表
     */
    public void showTotal() {
        bc.setBarGap(3);
        bc.setCategoryGap(20);
        xAxis.setLabel("Department");
        yAxis.setLabel("Fee");
        XYChart.Series series = new XYChart.Series();
        DepartmentMan de=DepartmentMan.getInstance();
        de.totalFee(series);
        bc.getData().add(series);
        de.clear();
    }
    /**
     * 转换窗口
     * @throws Exception
     */
    public void changeWindow() throws Exception {
        DepartmentMan de=DepartmentMan.getInstance();
        de.clear();
        MainOp change=new MainOp();
        change.showWindow("../view/MainPage.fxml");
    }
}
