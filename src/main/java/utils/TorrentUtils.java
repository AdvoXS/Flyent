package utils;

import com.turn.ttorrent.client.TorrentManager;
import torrent.Torrent;
import utils.torrent.TorrentClient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class TorrentUtils {
  private static final TorrentClient client = Torrent.CLIENT.client();

  public static TorrentManager addTorrents(List<File> files, String downloadPath) {
    for (File file : files) {
      try {
       return client.communicationManager.addTorrent(file.getAbsolutePath(), downloadPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
  public static void startDownload(InetAddress iPv4Address){
    try {
      client.communicationManager.start(new InetAddress[]{iPv4Address});
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static void startDownload(List<File> files) {
    try {
      InetAddress address = InetAddress.getLocalHost();
      if (files != null) {
        for (File file : files)
          client.downloadTorrent(file.getAbsolutePath(), System.getProperty("user.home"), address);
      }
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}
