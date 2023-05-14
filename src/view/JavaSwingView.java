package view;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.NewGuiController;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Adds GUI functionality to the view using JSwing graphics. Adds buttons to load and save as
 * well as opens a file selector to choose an image. Selected filters call the controller to
 * call the model and applies filters to the given images/layers.
 */
public final class JavaSwingView extends JFrame implements GuiView {
  private JPanel listBoxes;
  private JPanel filterList;
  private JPanel end;
  private JLabel imgName;
  private JScrollPane imgWindow;
  private JButton openButton;
  private JButton saveButton;
  private JButton selectionBox;
  private DefaultListModel<String> imgPixels;
  private JList<String> imgList;
  private JList<String> cmdList;

  /**
   * Initializes the view box and takes in dimensions for the project's borders.
   */
  public JavaSwingView(int x, int y) {
    super("Image Transformer (Created by Keefer :D)");
    setPreferredSize(new Dimension(x, y));
    setLayout(new BorderLayout());
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsToPane(getContentPane());
    pack();
    setVisible(true);
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this, message,
            "Message", JOptionPane.PLAIN_MESSAGE);
  }
  
  @Override
  public void addListeners(NewGuiController options) {
    openButton.addActionListener(evt -> {
      try {
        options.loadImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });


    imgList.addListSelectionListener(evt ->
            options.selectAndDisplayImage(imgList.getSelectedValue()));
    selectionBox.addActionListener(evt -> {
      try {
        options.filter(cmdList.getSelectedValue(),
                imgList.getSelectedValue());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    saveButton.addActionListener(evt -> {
      try {
        options.saveImage(imgList.getSelectedValue());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public void addImage(String destName, model.Image toSave) {
    this.updateToCurrent(destName, toSave);

    if (this.imgPixels.contains(destName)) {
      for (int i = 0; i < this.imgPixels.size(); i++) {
        if (destName.equals(this.imgPixels.get(i))) {
          this.imgList.setSelectedIndex(i);
        }
      }
      return;
    }

    this.imgPixels.add(0, destName);
    this.imgList.setSelectedIndex(0);
  }

  @Override
  public void updateToCurrent(String imageName, model.Image toSave) {
    BufferedImage src = new BufferedImage(toSave.getWidth(), toSave.getHeight(), TYPE_INT_RGB);

    for (int col = 0; col < src.getHeight(); col++) {
      for (int row = 0; row < src.getWidth(); row++) {
        Color color = new Color(
                toSave.getPixel(col, row).getRed(),
                toSave.getPixel(col, row).getGreen(),
                toSave.getPixel(col, row).getBlue(),
                toSave.getPixel(col, row).getAlpha());
        src.setRGB(row, col, color.getRGB());
      }
    }

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      ImageIO.write(src, "jpg", stream);
    } catch (IOException e) {
      this.renderMessage("Unable to render image.");
      return;
    }

    byte[] bytes = stream.toByteArray();
    ImageIcon img1 = new ImageIcon(bytes);
    ImageIcon img2 = img1;

    if (img1.getIconWidth() < 400 || img1.getIconHeight() < 400) {
      double wFrac = (double) imgWindow.getWidth() / (double) img1.getIconWidth();
      double hFrac = (double) imgWindow.getHeight() / (double) img1.getIconHeight();
      double ratio = Math.min(wFrac, hFrac);

      Dimension dimension = new Dimension((int) (img1.getIconWidth() * ratio),
              (int) (img1.getIconHeight() * ratio));

      Image img = img1.getImage();
      img2 = new ImageIcon(img.getScaledInstance(dimension.width, dimension.height,
              Image.SCALE_SMOOTH));
    }

    this.imgName.setIcon(img2);
  }

  @Override
  public DefaultListModel<String> getImgPixels() {
    DefaultListModel<String> copy = new DefaultListModel<>();
    for (int i = 0; i < this.imgPixels.getSize(); i++) {
      copy.add(i, this.imgPixels.get(i));
    }
    return copy;
  }

  /**
   * Adds all the completed components together.
   * @param backG - the Container to be used a background to sum all the
   *              components together on.
   */
  private void addComponentsToPane(Container backG) {
    JPanel mainPanel;
    JPanel imagePanel;
    JPanel bottomArea;
    JPanel userInputPanel;
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    imagePanel = new JPanel();
    imagePanel.setBackground(new Color(255, 255, 255));
    imagePanel.setOpaque(true);
    this.makeImgBorder();
    imagePanel.add(imgWindow);
    mainPanel.add(imagePanel, BorderLayout.CENTER);
    bottomArea = new JPanel();
    bottomArea.setLayout(new BorderLayout());
    userInputPanel = new JPanel();
    userInputPanel.setLayout(new BorderLayout());
    this.makeLoadSave();
    userInputPanel.add(listBoxes, BorderLayout.PAGE_START);
    this.makeFilterOptions();
    userInputPanel.add(filterList, BorderLayout.CENTER);
    this.makeEnter();
    userInputPanel.add(end, BorderLayout.PAGE_END);
    bottomArea.add(userInputPanel, BorderLayout.CENTER);
    mainPanel.add(bottomArea, BorderLayout.PAGE_END);
    backG.add(mainPanel, BorderLayout.CENTER);
  }

  /**
   * Creates the area which to view the image.
   */
  private void makeImgBorder() {
    imgName = new JLabel();
    imgWindow = new JScrollPane(imgName);
    imgWindow.setPreferredSize(new Dimension(900, 470));
  }

  // LOAD AND SAVE BUTTONS PANEL
  private void makeLoadSave() {
    listBoxes = new JPanel();
    listBoxes.setLayout(new FlowLayout());
    listBoxes.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // OPEN BUTTON:
    openButton = new JButton("Open");
    openButton.setActionCommand("Open");
    listBoxes.add(openButton);

    // SAVE BUTTON:
    saveButton = new JButton("Save");
    saveButton.setActionCommand("Save");
    listBoxes.add(saveButton);
  }

  private void makeFilterOptions() {
    JPanel optionImages;
    JPanel optionCommandPanel;

    filterList = new JPanel();
    filterList.setLayout(new FlowLayout());
    List<String> commands = new ArrayList<>();
    commands.add("red-component");
    commands.add("green-component");
    commands.add("blue-component");
    commands.add("value-component");
    commands.add("intensity-component");
    commands.add("luma-component");
    commands.add("darken-multiply");
    commands.add("brighten-screen");
    commands.add("brighten-screen");
    commands.add("difference");
    commands.add("add-layer");
    commands.add("add-image-to-layer");
    commands.add("set-filter");

    optionImages = new JPanel();
    optionImages.setPreferredSize(new Dimension(200, 200));
    optionImages.setBorder(BorderFactory.createTitledBorder("Choose an image:"));
    optionImages.setLayout(new BoxLayout(optionImages, BoxLayout.X_AXIS));
    filterList.add(optionImages, BorderLayout.PAGE_START);

    imgPixels = new DefaultListModel<>();
    imgList = new JList<>(imgPixels);
    imgList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    optionImages.add(new JScrollPane(imgList));

    optionCommandPanel = new JPanel();
    optionCommandPanel.setPreferredSize(new Dimension(200, 200));
    optionCommandPanel.setBorder(BorderFactory.createTitledBorder("Choose a command:"));
    optionCommandPanel.setLayout(new BoxLayout(optionCommandPanel, BoxLayout.X_AXIS));
    filterList.add(optionCommandPanel, BorderLayout.PAGE_START);
    DefaultListModel<String> dataForCommandList = new DefaultListModel<>();
    for (String s : commands) {
      dataForCommandList.addElement(s);
    }
    cmdList = new JList<>(dataForCommandList);
    cmdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    optionCommandPanel.add(new JScrollPane(cmdList));
  }

  /**
   * Makes the enter button when selecting an image.
   */
  private void makeEnter() {
    end = new JPanel();
    end.setLayout(new FlowLayout());

    selectionBox = new JButton("Enter");
    selectionBox.setActionCommand("Enter Button");
    selectionBox.setPreferredSize(new Dimension(250, 40));
    end.add(selectionBox);
  }
}