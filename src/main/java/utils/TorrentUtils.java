package utils;

import torrent.Torrent;
import utils.torrent.TorrentClient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class TorrentUtils {
  public static void startDownload(List<File> files) {
    try {
      InetAddress address = InetAddress.getLocalHost();
      TorrentClient client = Torrent.CLIENT.client();
      if (files != null) {
        for (File file : files)
          client.downloadTorrent(file.getAbsolutePath(), System.getProperty("user.home"), address);
      }
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}
