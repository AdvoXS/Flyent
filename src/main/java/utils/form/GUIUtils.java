package utils.form;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIUtils {
  public static List<File> getChooseFiles(Component parentComponent, String fileExtName, String... fileExt) {
    List<File> selectedFiles = null;
    JFileChooser chooser = new JFileChooser();
    chooser.setMultiSelectionEnabled(true);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(fileExtName, fileExt);
    chooser.setFileFilter(filter);
    chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    int result = chooser.showOpenDialog(parentComponent);
    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFiles = new ArrayList<>(Arrays.asList(chooser.getSelectedFiles()));
      System.out.println("Selected files: " + selectedFiles.size());
    }
    return selectedFiles;
  }

  public static void showErrorDialog(String header, String message){
    JOptionPane.showMessageDialog(null, message, header, JOptionPane.ERROR_MESSAGE);
  }

  public static void showMessageDialog(String header, String message){
    JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
  }
}
