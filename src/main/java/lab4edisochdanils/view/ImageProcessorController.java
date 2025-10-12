package lab4edisochdanils.view;

import lab4edisochdanils.model.ImageProcessorModel;

public class ImageProcessorController {
    private ImageProcessorView mainView;
    private ImageProcessorModel model;

    public ImageProcessorController(ImageProcessorModel model, ImageProcessorView view) {
        this.model = model;
        this.mainView = view;
    }

    public void onGrayScaleSelected() {
        model.grayScale();
        mainView.updateFromModel();
    }
    
    public void onBlurSelected(){
        model.blur();
        mainView.updateFromModel();
        
    }

    public void onSharpenSelected() {
        model.sharpen();
        mainView.updateFromModel();
    }

    public void onLoadImage() {
        // TODO: Implementera FileChooser för att ladda bilder
        System.out.println("Load image - not implemented yet");
    }

    public void onSaveImage() {
        // TODO: Implementera FileChooser för att spara bilder
        System.out.println("Save image - not implemented yet");
    }

    public void onInvertSelected() {
        model.invertColors();
        mainView.updateFromModel();
    }

    public void onResetToOriginal() {
        model.revertToOriginal();
        mainView.updateFromModel();
    }
}