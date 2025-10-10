## Modifying an image
An example on an implementation of the interface IImageProcessor modifying an image.

NB!
- This application loads an internal image (from the recources directory). Your application should use a FileChooser when loading and saving.
- The model in this case is just a single interface implementation. The model in your application will consist of multiple classes. Use a facade to manage theses classes.

## utils.ImagePixelsConverter, utils.ColorConverter
ImagePixelsConverter has methods for converting between a javafx.scene.image.Image object, representing an image in
the user interface, and a pixel matrix (int[][]), representing the same image in the model.

ColorConverter has methods for extracting the alpha (opacity), red green and blue components from a pixel in a pixel
matrix, and a method to get a pixel value (from the alpha and colors values) to set in a pixel matrix.

You may use the ImagePixelConverter and ColorConverter classes in your solution.

## Test run
Use the green "Code" button and "Download zip". Save the zip-file in some (known) directory, then open IntelliJ,
select File/Open and navigate to the project.