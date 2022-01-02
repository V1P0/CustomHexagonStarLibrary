import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppWindow extends Application {
    @Override
    public void start(Stage stage){
        Group group = new Group();
        try {
            ChineseCheckersBoard board = new ChineseCheckersBoardBuilder().setSize(5).setNumberOfPlayers(6).build();
            board.printInTerminal();
            ChineseCheckersBoardAdapter BoardAdapter = new ChineseCheckersBoardAdapter(board);
            Field[][] fields = BoardAdapter.getFields();
            for(Field[] a : fields){
                for(Field c : a){
                    if(c!=null) group.getChildren().add(c);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    public void launchApp(){
        launch();
    }
}