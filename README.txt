# Collaging Program

This repository contains a Java program for creating collages from multiple images. The program is designed to be run with a graphical user interface (GUI) that allows the user to select, add, modify, and save images while working in multiple different layers that contribute to a total image. 

Modifications on images are done by applying any of the following filters:

- normal: does nothing to the image
- red-component: when applied, only uses the red portion of the RGB. Similar for green-component and blue-component
- brighten-value: when applied, adds the brightness value pixel by pixel according to value from the corresponding pixel on the current layer. Similar for brighten-intensity and brighten-luma. Only affects the current layer.
- darken-value: when applied, removes the brightness value pixel by pixel according to value from the corresponding pixel on the current layer. Similar for darken-intensity and darken-luma. Only affects the current layer.

The program is built using the Model-View-Controller (MVC) design pattern. The model contains the business logic for the program, including the image processing operations that can be applied to images. The view provides the user interface, and the controller manages the communication between the view and the model.

## Classes and Interfaces

Interface CollageController - Represents a controller for the user to run and interact with the Collage operations.
Class CollageControllerImpl - Class represents the controller to allow user to perform image processing effects via text interface
Interface CollageOperations - Interface for different operations the user can run.
Class GuiController - Class represents the controller to allow user to perform image processing effects via text interface.

Class BoardPanel - 
Interface ProjectGuiView - represents the view for the GUI of a given project
Class ProjectView - implements ProjectGuiView
Class SwingFeaturesFrame - opens a main menu with all commands available to the user
Class SwingGuiView - This class displays the newGui.Main Menu before a project is made.

Interface CollageModel - Interface represents the image processing operations that can be performed on an image.
Class CollageModelImpl - Class represents an image processor for PPM files.
Interface CollageViewModel - Represents an interface for viewing the model.
Enum FileType - Represents different file types for various images.
Interface Func - Represents a function that takes in three inputs and changes a specific value.
Type parameters:
<T> – first input <Q> – second input <S> – third input <R> – type of output
Interface Image - Interface representing an Image.
Class ImageImpl - Represents an image containing a 2d array of pixels.
Class ImageLayer - Represents a layer that images can be applied to.
Class ImageUtil - This class contains utility methods to read a PPM image from file and save a PPM image to a file.
Class Layer - Represents a layer of images.
Class Pixel - Represents a Pixel object that makes up an image.

Class BlankView - A Blank View which is has no function other than as a placeholder in a controller delegate that is used for the GUI.
Class GuiControllerImlp - Class represents the controller to allow user to perform image processing effects via text interface. 
Interface GuiView - represents new gui view. 
Class JFrameView - GUI view implementation, which uses Java Swing to display the image processor graphically.
Class Main - Accepts command line arguments or input from "System.in". Command line arguments are accepted are in the format of ".txt" files where you enter "-file nameOfScript.txt". If you want to use the text based version of the program, pass into the command line argument "-text". If none are supplied, the user will then be prompoted to ue the GUI version of the program.
Interface NewGuiController - Loads image to the GUI. Signaled by click on detection on the "Open" button. Uses JOption Pane pop-ups to get information from the user.

Interface CollageView - Represents the view for a Collage.
Class CollageViewImpl - Class that displays user interaction with an image.

## Requirements

To run the program, you need to have Java Development Kit (JDK) 8 or later installed.

## Usage

To use the program, download the repository and open it in an IDE such as Eclipse or IntelliJ IDEA. Run the `GuiControllerImlp.java` file to start the program. This will launch the GUI.

The GUI provides several options for selecting and modifying images. To load an image, click the "Load Image" button and select an image file from your file system. The image will be displayed in the GUI. To modify the image, select a filter operation from the drop-down menu and click the "Apply" button. You can also save the modified image by clicking the "Save Image" button.

Our collage project can set load, save, and set filters on images using a layer system, similar to Photoshop. The user can use commands from our script to either alter singular images or place them on different layers and make a collage. For now, the program only supports the PPM format but is easily extendable in the future. The user can enter inputs through the Intellij console as well as run commands through the script provided.

Credit for `k.PPM` - an original photo taken by Keefer Wu on an iPhone. 
