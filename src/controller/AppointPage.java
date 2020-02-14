package controller;

import entity.DepartmentMan;

import entity.MainOp;
import entity.Patient;
import entity.Specialist;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.IDN;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppointPage implements Initializable {
    @FXML
    private Label doctn;
    @FXML
    private Label doctt;
    @FXML
    private TextField patn;
    @FXML
    private TextField patid;
    @FXML
    private TextField patt;
    @FXML
    private Label epatt;
    @FXML
    private Label epatn;
    @FXML
    private Label epatid;
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/demo"+"?serverTimezone=GMT%2B8";
    //用户名
    private static final String USER_NAME = "root";
    //密码
    private static final String PASSWORD = "7415963";

    private DepartmentMan de=DepartmentMan.getInstance();
    private static Specialist doc=new Specialist();

    /**
     * 初始化界面
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findDoct();
        doctn.setText(doc.getName());
        doctt.setText(doc.getWorktime());
    }

    /**
     * 找到对应的医生
     */
    private void findDoct(){
        for(Specialist d:de.getDepartment().getDoctors())
            if(d.getName().equals(de.getDoctn()))
            {
                doc=d;
                break;
            }
            getPatient();
    }

    /**
     * 获得已有的病人信息
     */
    private void getPatient(){
        de.getPatients(doc);
    }

    /**
     * 创建新的病人
     * @throws Exception
     */
    public void addPatient()throws Exception{
        boolean id=false;
        boolean name=false;
        boolean time=false;
        if(patid.getText().isEmpty())
            epatid.setText("ID不能为空");
        else if(checkID(patid.getText())){
           id=true;
           for(Patient p:doc.getPatients()){
               if(patid.getText().equals(p.getID()))
               {id=false;
               epatid.setText("此ID已预约");
               break;}
           }
        }
        else {
            patid.setText("");
            epatid.setText("ID格式错误");
        }
        if(patt.getText().isEmpty())
        epatt.setText("预约时间不能为空");
        time=true;
         if(patn.getText().isEmpty())
         epatn.setText("名字不能为空");
        name=true;
         if(id&&time&&name) {
             Patient p = new Patient();
             p.setName(patn.getText());
             p.setID(patid.getText());
             p.setPfee(doc.getRegistrationFee());
             p.setPtime(patt.getText());
             int num=doc.getPatientnum();
             doc.setPatientnum(++num);
             setPatient(p);
             showStatue();
             de.clear();
             MainOp change=new MainOp();
             change.showWindow("../view/MainPage.fxml");
         }
    }
    public void changeWindow() throws Exception {
        MainOp change=new MainOp();
        change.showWindow("../view/DoctPage.fxml");
    }

    /**
     * 检查身份证的正则表达式
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
     * 加入病人进数据库
     * @param p
     */
    private void setPatient(Patient p){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "insert into "+doc.getPatdb()+"(id,name,fee,time) value(?,?,?,?)";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setString(1,p.getID());
            prst.setString(2,p.getName());
            prst.setInt(3,p.getPfee());
            prst.setString(4,p.getPtime());
            prst.executeUpdate();
            sql="update "+de.getDocb()+ " set patientnum = "+doc.getPatientnum()+" where name = '"+doc.getName()+"'";
            prst = connection.prepareStatement(sql);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示是否预约成功
     */
    private void showStatue(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        Label check=new Label("预约成功");
        check.setTextFill(Color.web("RED"));
        check.setFont(new Font("Arial", 20));
        Label tip=new Label("本窗口将在3秒后关闭");
        grid.add(check,0,0);
        grid.add(tip,0,1);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                if (stage.isShowing()) {
                    Platform.runLater(() -> stage.close());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

    }
}
