package torrent;

import com.turn.ttorrent.client.SimpleClient;

public enum Torrent {
  CLIENT(new SimpleClient());

  SimpleClient client;

  Torrent(SimpleClient client){
    this.client = client;
  }

  public SimpleClient client(){
    return client();
  }
}
