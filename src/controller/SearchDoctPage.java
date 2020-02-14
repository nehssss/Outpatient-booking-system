package controller;

import entity.DepartmentMan;
import entity.MainOp;
import entity.Patient;
import entity.Specialist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchDoctPage implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField depart;
    private DepartmentMan de=DepartmentMan.getInstance();
    private Specialist doct=new Specialist();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 得到医生信息
     */
    public void getDoct(){
        boolean check=false;
        de.getMessage(depart.getText());
        for(Specialist s:de.getDepartment().getDoctors()){
            if(s.getName().equals(name.getText())){
                check=true;
                doct=s;
                break;
            }
        }
        if(check)
            showDoct();
    }

    /**
     * 展示医生信息
     */
    private void showDoct(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setHgap(60);
        grid.setVgap(20);
        Button noBtn = new Button("exit");
        Button delBtn=new Button("delete");
        Label name=new Label(doct.getName());
        Label position=new Label(doct.getPosition());
        Label spe=new Label(doct.getSpecialty());
        Label fee=new Label(Integer.toString(doct.getRegistrationFee()));
        Label time=new Label(doct.getWorktime());
        Label patnum=new Label(doct.getSpecialty());
        Label office=new Label(de.getDepartment().getName());
        Label ename=new Label("姓名：");
        Label eposition=new Label("职位：");
        Label espe=new Label("专长:");
        Label efee=new Label("诊金：");
        Label etime=new Label("工作时间：");
        Label epatnum=new Label("预约人数：");
        Label eoffice=new Label("预约科室：");
        //退出按钮
        noBtn.setOnAction(e -> {
            stage.close();
            de.clear();
            doct=new Specialist();
        });
        //删除按钮
        delBtn.setOnAction(e->{
            stage.close();
            de.delDoct(doct);
            try {
                changeWindow();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        HBox root = new HBox();
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setSpacing(20);
        root.getChildren().addAll(delBtn,noBtn);
        grid.add(eoffice,0,0);
        grid.add(office,1,0);
        grid.add(ename,0,1);
        grid.add(name,1,1);
        grid.add(eposition,0,2);
        grid.add(position,1,2);
        grid.add(espe,0,3);
        grid.add(spe,1,3);
        grid.add(efee,0,4);
        grid.add(fee,1,4);
        grid.add(etime,0,5);
        grid.add(time,1,5);
        grid.add(epatnum,0,6);
        grid.add(patnum,1,6);
        grid.add(root,0,7);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
    public void changeWindow() throws Exception {
        DepartmentMan de=DepartmentMan.getInstance();
        de.clear();
        MainOp change=new MainOp();
        change.showWindow("../view/MainPage.fxml");
    }
}
