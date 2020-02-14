package controller;


import entity.DepartmentMan;
import entity.MainOp;
import entity.Specialist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class JoinDoctPage implements Initializable {
    @FXML
    private TextField depart;
    @FXML
    private TextField name;
    @FXML
    private TextField position;
    @FXML
    private TextField spe;
    @FXML
    private TextField fee;
    @FXML
    private TextField time;
    @FXML
    private Label wname;
    @FXML
    private Label wposition;
    @FXML
    private Label wspe;
    @FXML
    private Label wfee;
    @FXML
    private Label wtime;
    @FXML
    private Label wdepart;

    private DepartmentMan de=DepartmentMan.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 编辑医生信息
     * @throws Exception
     */
    public void editSpecialist() throws Exception {
        boolean rname=false;
        boolean rposition=false;
        boolean rspe=false;
        boolean rfee=false;
        boolean rtime=false;
        if(name.getText().isEmpty())
        wname.setText("名字不能为空");
         if(position.getText().isEmpty())
          wposition.setText("职位不能为空");
         else rposition=true;
         if(spe.getText().isEmpty())
         wspe.setText("专长不能为空");
         else rspe=true;
         if(fee.getText().isEmpty())
         wfee.setText("诊金不能为空");
         else rfee=true;
         if(time.getText().isEmpty())
         wtime.setText("时间不能为空");
         else rtime=true;
         if(depart.getText().isEmpty())
         {wdepart.setText("科室不能为空");}
         else if(true){
             getDoct();
             rname=true;
            for(Specialist doc:de.getDepartment().getDoctors()){
                if(doc.getName().equals(name.getText())) {
                    rname = false;
                    wname.setText("该专家已注册");
                    break;
                }
            }
         }
         if(rname&&rfee&&rposition&&rspe&&rtime){
             Specialist doc=new Specialist();
             doc.setName(name.getText());
             doc.setPatientnum(0);
             doc.setRegistrationFee(Integer.valueOf(fee.getText()));
             doc.setPosition(position.getText());
             doc.setSpecialty(spe.getText());
             doc.setWorktime(time.getText());
             setDoct(doc);
         }
         changeWindow();
    }

    /**
     * 获取医生信息
     */
    private void getDoct(){
        de.getMessage(depart.getText());
    }

    /**
     * 加入医生
     * @param doc
     */
    private void setDoct(Specialist doc){
        de.setDoct(doc);
    }
    public void changeWindow() throws Exception {
        DepartmentMan de=DepartmentMan.getInstance();
        de.clear();
        MainOp change=new MainOp();
        change.showWindow("../view/fifthPage.fxml");
    }
}
