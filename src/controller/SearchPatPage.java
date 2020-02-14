package controller;

import entity.DepartmentMan;
import entity.Patient;
import entity.Specialist;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class SearchPatPage implements Initializable {
    @FXML
    private Label eoffice;
    @FXML
    private Label eID;
    @FXML
    private TextField office;
    @FXML
    private TextField ID;
    private DepartmentMan de=DepartmentMan.getInstance();
    //查询到的病人
    private Patient pat=new Patient();
    //查询到的医生
    private Specialist doct=new Specialist();
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 查找病人的界面
     * @throws Exception
     */
    public void onSearch()throws Exception{
        boolean boffice=false;
        boolean bID=false;
        if(office.getText().isEmpty())
            eoffice.setText("科室名不能为空");
        else boffice=true;
        if(ID.getText().isEmpty())
            eID.setText("身份证号不能为空");
        else if(checkID(ID.getText())){
            bID=true;
        }
        else {
           ID.setText("");
            eID.setText("ID格式错误");
        }
        if(boffice&&bID) {
            de.getMessage(office.getText());
            getPatient();
            outer:
            for (Specialist doc : de.getDepartment().getDoctors()) {
                for (Patient p : doc.getPatients()) {
                    if (ID.getText().equals(p.getID())) {
                        pat = p;
                        doct = doc;
                        break outer;
                    }
                }
            }

        }
        if(pat.getID()!=null) {
            showPatient();
        office.setText("");
        ID.setText("");}
        else {
            warning();
        office.setText("");
        ID.setText("");}
    }

    /**
     * 得到病人的信息
     */
    private void getPatient(){
        for(Specialist doc:de.getDepartment().getDoctors()){
            de.getPatients(doc);
        }
    }

    /**
     * 显示找到的病人
     */
    private void showPatient(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setHgap(60);
        grid.setVgap(20);
        Button noBtn = new Button("exit");
        Button delBtn=new Button("delete");
        Label name=new Label(pat.getName());
        Label ID=new Label(pat.getID());
        Label time=new Label(pat.getPtime());
        Label fee=new Label(Integer.toString(pat.getPfee()));
        Label docn=new Label(doct.getName());
        Label docs=new Label(doct.getSpecialty());
        Label docp=new Label(doct.getPosition());
        Label office=new Label(de.getDepartment().getName());
        Label ename=new Label("姓名：");
        Label eID=new Label("身份证号：");
        Label etime=new Label("预约时间:");
        Label efee=new Label("预约费用：");
        Label edocn=new Label("预约医生：");
        Label edocs=new Label("医生专长：");
        Label edocp=new Label("医生职位：");
        Label eoffice=new Label("预约科室：");
        //退出按钮
       noBtn.setOnAction(e -> {
            stage.close();
            de.clear();
           pat=new Patient();
           doct=new Specialist();
        });
       //删除按钮
       delBtn.setOnAction(e->{
           de.delPatient(doct,pat);
           stage.close();
           de.clear();
           pat=new Patient();
           doct=new Specialist();
           deleSuccess();
       });
        HBox root = new HBox();
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setSpacing(20);
        root.getChildren().addAll(delBtn,noBtn);
        grid.add(eoffice,0,0);
        grid.add(office,1,0);
        grid.add(edocn,0,1);
        grid.add(docn,1,1);
        grid.add(edocp,0,2);
        grid.add(docp,1,2);
        grid.add(edocs,0,3);
        grid.add(docs,1,3);
        grid.add(ename,0,4);
        grid.add(name,1,4);
        grid.add(eID,0,5);
        grid.add(ID,1,5);
        grid.add(etime,0,6);
        grid.add(time,1,6);
        grid.add(efee,0,7);
        grid.add(fee,1,7);
        grid.add(root,0,8);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * 检查身份证的标准性
     * @param ID
     * @return
     */
    private boolean checkID(String ID){
        String p="(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        boolean isMatch=ID.matches(p);
        return isMatch;
    }

    /**
     * 显示是否查找成功
     */
    private void warning(){
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(20);
        stage.setMinHeight(100);
        stage.setMinWidth(200);
        Label warning=new Label("查无此人");
        warning.setFont(new Font("Arial", 20));
        warning.setTextFill(Color.web("RED"));
        grid.add(warning,2,2);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                if (stage.isShowing()) {
                    Platform.runLater(() -> stage.close());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 检查是否删除成功
     */
    private void deleSuccess(){
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(20);
        stage.setMinHeight(100);
        stage.setMinWidth(200);
        Label warning=new Label("删除成功");
        warning.setFont(new Font("Arial", 20));
        warning.setTextFill(Color.web("RED"));
        grid.add(warning,2,2);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
         //控制小窗口的存在时间
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1200);
                if (stage.isShowing()) {
                    Platform.runLater(() -> stage.close());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
