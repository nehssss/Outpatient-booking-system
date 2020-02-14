package controller;

import entity.DepartmentMan;
import entity.MainOp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
public class DepartPage implements Initializable {
    @FXML
    private Button firstDepartment;
    @FXML
    private Button secondDepartment;
    @FXML
     private Button thirdDepartment;
    @FXML
    private Button fourthDepartment;
    @FXML
    private Button fifthDepartment;
    @FXML
    private Button sixthDepartment;
    @FXML
    private Button seventhDepartment;
    @FXML
    private Button eighthDepartment;
        @Override
        public void initialize(URL arg0, ResourceBundle arg1) {

            // TODO Auto-generated method stub
        }
        //转化窗口
        public void changeWindow(Event e) throws Exception {
            DepartmentMan de=DepartmentMan.getInstance();
            de.getMessage(((Button)e.getSource()).getText());
            MainOp change=new MainOp();
            change.showWindow("../view/DoctPage.fxml");
        }
    }

