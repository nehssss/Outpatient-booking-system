package entity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class MainOp extends Application{
    Stage stage=new Stage();
    static Stage tempstage=new Stage();
    static String ViewID= "../view/MainPage.fxml";
    static Image image=new Image("file:src/timg.jpg");
        @Override
        public void start(Stage primaryStage) throws Exception{
            tempstage=primaryStage;
            tempstage.getIcons().add(image);
            Parent root = FXMLLoader.load(getClass().getResource(ViewID));
            tempstage.setScene(new Scene(root));
            tempstage.show();
        }
        public static void main(String[] args) {
            launch(args);
        }
    public void  showWindow(String ViewID) throws Exception {
            tempstage.close();
            this.ViewID=ViewID;
            start(stage);
    }
}
