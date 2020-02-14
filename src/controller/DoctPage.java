package controller;

import entity.DepartmentMan;
import entity.MainOp;
import entity.Specialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctPage implements Initializable  {
    @FXML
    private TableView<Specialist> tableView;
    @FXML
    private TableColumn<Specialist,String> Doctor;
    @FXML
    private TableColumn Position;
    @FXML
    private TableColumn Specialty;
    @FXML
    private TableColumn Fee;
    @FXML
    private TableColumn Patientnum;
    @FXML
    private TableColumn Worktime;

    @FXML
    /**
     * 显示医生的信息
     */
    private void showList(){
        ObservableList<Specialist> list = FXCollections.observableArrayList();
        DepartmentMan de=DepartmentMan.getInstance();
        for( Specialist docs : de.getDepartment().getDoctors()){
            list.add(docs);
        }
        //选择医生的按钮实现
        Doctor.setCellFactory((col) -> {
                    TableCell<Specialist, String> cell = new TableCell<Specialist, String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            this.setText(null);
                            this.setGraphic(null);
                            if (!empty) {
                                Specialist doc = this.getTableView().getItems().get(this.getIndex());
                                Button Btn = new Button(doc.getName());
                               this.setGraphic(Btn);
                                Btn.setOnMouseClicked((me) -> {
                                    DepartmentMan de=DepartmentMan.getInstance();
                                    de.setDoctn(Btn.getText());
                                        MainOp change=new MainOp();
                                    try {
                                        change.showWindow("../view/AppointPage.fxml");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });

                            }
                        }
                    };
            return cell;
                });
     Position.setCellValueFactory(new PropertyValueFactory<>("position"));
     Specialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
     Fee.setCellValueFactory(new PropertyValueFactory<>("registrationFee"));
     Patientnum.setCellValueFactory(new PropertyValueFactory<>("patientnum"));
     Worktime .setCellValueFactory(new PropertyValueFactory<>("worktime"));
        tableView.setItems(list);
    }

    /**
     * 初始化界面
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       showList();
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
