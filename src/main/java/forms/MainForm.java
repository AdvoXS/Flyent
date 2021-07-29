package forms;

import com.turn.ttorrent.client.LoadedTorrent;
import com.turn.ttorrent.client.SharedTorrent;
import utils.form.GUIUtils;
import torrent.TorrentListener;
import utils.xml.XmlMenuCreator;
import utils.xml.XmlReader;

import javax.swing.*;
import java.awt.Dimension;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MainForm extends JFrame implements TorrentListener {
  Logger logger = Logger.getLogger(MainForm.class.getName());

  private JPanel mainBackPanel;
  private JPanel leftSidePanel;
  private JPanel toolsPanel;
  private JPanel tableViewPanel;
  private JPanel menuPanel;
  private JTable torrentTable;

  public MainForm() {
    pack();
    setContentPane(mainBackPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(800, 600));
  }

  private void createUIComponents() {
    initPanels();
    createMenu();
  }

  private void initPanels() {
    mainBackPanel = new JPanel();
    leftSidePanel = new JPanel();
    toolsPanel = new JPanel();
    tableViewPanel = new JPanel();
    menuPanel = new JPanel();
  }

  private void createMenu() {
    try {
      ClassLoader loader = getClass().getClassLoader();
      XmlReader xmlReader = new XmlReader(loader.getResource("main_menu_bar.xml").getFile(), "UTF8");
      XmlMenuCreator menuCreator = new XmlMenuCreator(xmlReader.getInputSource());
      menuCreator.parse();
      setJMenuBar(menuCreator.getMenuBar("mainMenu"));
      menuCreator.addActionListener("exit", e -> System.exit(0));
      menuCreator.addActionListener("open", e -> {
        List<File> files = GUIUtils.getChooseFiles(this, "Torrent files", "torrent");
        String testDownPath = System.getProperty("user.home") + "/torrents/ling";
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateInfo(List<LoadedTorrent> loadedTorrents, List<SharedTorrent> sharedTorrents) {
    //logger.info("Updated torrents in main form!");
  }
}
