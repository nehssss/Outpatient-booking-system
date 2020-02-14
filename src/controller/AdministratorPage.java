package controller;

import entity.MainOp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorPage implements Initializable {
    @FXML
   private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label wusername;
    @FXML
    private Label wpassword;

    private static final String rusername="root";
    private static final String rpassword="123456";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 管理员登陆界面
     * @throws Exception
     */
    public void check() throws Exception {
        boolean success=false;
        if(username.getText().isEmpty())
        wusername.setText("用户名不能为空");
         if(password.getText().isEmpty())
         wpassword.setText("密码不能为空");
        else if(password.getText().equals(rpassword)&&username.getText().equals(rusername))
         success=true;
        else
        {wpassword.setText("密码格式不对");
         username.setText("");
         password.setText("");
        }
        if(success){
            changeWindows();
        }
    }

    /**
     * 选择操作功能
     * 
     */
    private void changeWindows() {
        Stage stage = new Stage();
        stage.setMinWidth(1000);
        stage.setMinHeight(1000);
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        Button editspe=new Button("管理专家");
        Button joinspe=new Button("加入专家");
        Button total=new Button("统计数据");
        editspe.setMinHeight(50);
        editspe.setMinWidth(50);
        editspe.setOnAction(e -> {
            MainOp change=new MainOp();
            try {
                change.showWindow("../view/SearchDoctPage.fxml");
                stage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
                stage.close();
            }
        });
        joinspe.setMinHeight(50);
        joinspe.setMinWidth(50);
        joinspe.setOnAction(e->{
            MainOp change=new MainOp();
            try {
                stage.close();
                change.showWindow("../view/JoinDoctPage.fxml");
            } catch (Exception e1) {
                e1.printStackTrace();
                stage.close();
            }
        });
        total.setMinHeight(50);
        total.setMinWidth(50);
        total.setOnAction(e->{
            MainOp change=new MainOp();
            try {
                change.showWindow("../view/TotalPage.fxml");
                stage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
                stage.close();
            }
        });
        HBox root = new HBox();
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setSpacing(20);
        root.setMinHeight(100);
        root.setMinWidth(100);
        root.getChildren().addAll(joinspe,editspe,total);
        grid.add(root,0,0);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }
}
