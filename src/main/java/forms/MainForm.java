package forms;

import com.turn.ttorrent.client.SimpleClient;
import com.turn.ttorrent.client.TorrentManager;
import torrent.Torrent;
import utils.TorrentUtils;
import utils.form.GUIUtils;
import utils.torrent.TorrentClient;
import utils.xml.XmlMenuCreator;
import utils.xml.XmlReader;

import javax.swing.*;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainForm extends JFrame {
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
       // TorrentClient client = Torrent.CLIENT.client();
        TorrentManager manager = TorrentUtils.addTorrents(files, System.getProperty("user.home")+"/torrents/recoil");
        try {
          TorrentUtils.startDownload(InetAddress.getLocalHost());
        } catch (UnknownHostException unknownHostException) {
          unknownHostException.printStackTrace();
        }
        //TorrentUtils.startDownload(files);

      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
