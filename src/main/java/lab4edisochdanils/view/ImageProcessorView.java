package lab4edisochdanils.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImageProcessorView extends VBox {
    private ImageView imageView;
    private ScrollPane scrollPane;
    private HistogramView histogramView;
    private MenuBar menuBar;
    private VBox operationPanel;
    private Image currentImage;

    public ImageProcessorView(Image img) {
        this.currentImage = img;
        this.imageView = new ImageView();
        this.imageView.setImage(currentImage);
        this.imageView.setPreserveRatio(true);

        // ScrollPane för bilden
        this.scrollPane = new ScrollPane(imageView);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setPannable(true);

        // Histogram
        this.histogramView = new HistogramView();

        // Skapa menyrad
        createMenuBar();

        // Skapa operation panel
        this.operationPanel = new VBox();
        this.operationPanel.setAlignment(Pos.CENTER);
        this.operationPanel.setSpacing(10);

        // Layout: meny, sedan HBox med histogram + bild, sedan operation panel
        HBox mainContent = new HBox();
        mainContent.setSpacing(10);
        mainContent.getChildren().addAll(histogramView, scrollPane);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().addAll(menuBar, mainContent, operationPanel);
    }

    private void createMenuBar() {
        this.menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load Image...");
        MenuItem saveItem = new MenuItem("Save Image...");
        fileMenu.getItems().addAll(loadItem, saveItem);
        
        Menu editMenu = new Menu("Edit");
        MenuItem invertItem = new MenuItem("Invert Colors");
        MenuItem grayScaleItem = new MenuItem("Gray Scale");
        MenuItem resetItem = new MenuItem("Reset to Original");
        editMenu.getItems().addAll(invertItem, grayScaleItem, resetItem);


        
        menuBar.getMenus().addAll(fileMenu, editMenu);
    }

    public void addOperationView(VBox operationView) {
        operationPanel.getChildren().add(operationView);
    }

    public void clearOperationViews() {
        operationPanel.getChildren().clear();
    }

    public void setCurrentImage(Image img) {
        currentImage = img;
        imageView.setImage(currentImage);
    }

    public void updateHistogram(int[][] histogramData) {
        histogramView.updateView(histogramData);
    }

    public void clearHistogram() {
        histogramView.clear();
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getFileMenu() {
        return menuBar.getMenus().get(0);
    }

    public Menu getEditMenu() {
        return menuBar.getMenus().get(1);
    }
}
