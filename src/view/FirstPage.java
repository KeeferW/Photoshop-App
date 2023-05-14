package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.CollageModel;
import model.CollageModelFactory;
import model.CollageModelImplFactory;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


/**
 * This class displays the newGui.Main Menu before a project is made. Prompts the user to
 * create a project with a name and dimensions.
 */
public class FirstPage extends JFrame implements ProjectGuiView {

  //a label to display any messages to the user
  private final JLabel messageLabel;
  CollageModelFactory factory = new CollageModelImplFactory();
  CollageModel model = factory.createCollageModel();

  /**
   * Constructs the gui view for a Project.
   */
  public FirstPage() {
    super("My App");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel contentPane = new JPanel();
    contentPane.setLayout(null);
    contentPane.setPreferredSize(new Dimension(400, 300));
    setContentPane(contentPane);

    messageLabel = new JLabel("Welcome to my app!");
    messageLabel.setHorizontalAlignment(JLabel.CENTER);
    messageLabel.setBounds(0, 10, 400, 30);
    contentPane.add(messageLabel);

    this.renderMessage("Welcome to the Collaging Program, start by Creating a project");

    JButton button = new JButton("Create Project");
    button.setPreferredSize(new Dimension(150, 75));
    button.setBounds(125, 100, 150, 75);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField textField = new JTextField();
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
          "Enter project name:", textField,
          "Enter x:", xField,
          "Enter y:", yField
        };
        int option = JOptionPane.showConfirmDialog(null, message,
                "Create a New Project", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          String projectName = textField.getText();
          int x = Integer.parseInt(xField.getText());
          int y = Integer.parseInt(yField.getText());
          model.createNewProject(x, y);
          JavaSwingView pv = new JavaSwingView(x, y);
          // hide the current JFrame
          setVisible(false);
        }
      }
    });

    contentPane.add(button);

    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    FirstPage view = new FirstPage();
    //view.renderMessage("Hello, world!");
  }



  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and therefore it must be redrawn.
   */
  @Override
  public void refresh(JPanel panel) {
    panel.repaint();
  }

  /**
   * Display a message in a suitable area of the GUI.
   *
   * @param message the message to be displayed
   */
  @Override
  public void renderMessage(String message) {
    messageLabel.setText(message);
  }

}