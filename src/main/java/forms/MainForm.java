package forms;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
  private JPanel mainBackPanel;
  private JPanel leftSidePanel;
  private JPanel toolsPanel;
  private JPanel tableViewPanel;
  private JPanel menuPanel;

  public MainForm() {
    pack();
    setContentPane(mainBackPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(800,600));
  }

  private void createUIComponents() {
    initPanels();

  }

  private void initPanels() {
    mainBackPanel = new JPanel();
    leftSidePanel = new JPanel();
    toolsPanel = new JPanel();
    tableViewPanel = new JPanel();
    menuPanel = new JPanel();
    // mainBackPanel.add(leftSidePanel);
   //  mainBackPanel.add(toolsPanel);
   // mainBackPanel.add(menuPanel);
   // mainBackPanel.add(tableViewPanel);
    //add(mainBackPanel);
  }

}
