import forms.MainForm;
import torrent.TorrentListener;
import torrent.TorrentManager;


import java.util.ArrayList;
import java.util.List;

public class FlyentMain {

  public static void main(String[] args) {
    List<TorrentListener> listenerList = new ArrayList<>();
    MainForm form = new MainForm();
    form.setVisible(true);
    listenerList.add(form);
    TorrentManager manager = TorrentManager.getInstance();
    manager.addTorrentListener(listenerList);

    manager.start();
  }

}
