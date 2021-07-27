package torrent;

import com.turn.ttorrent.client.SimpleClient;
import utils.torrent.TorrentClient;

public enum Torrent {
  CLIENT(new TorrentClient());

  TorrentClient client;

  Torrent(TorrentClient client){
    this.client = client;
  }

  public TorrentClient client(){
    return client();
  }
}
