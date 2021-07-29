package torrent;

public enum Torrent {
  INSTANCE(new TorrentClient());

  private final TorrentClient client;

  Torrent(TorrentClient client) {
    this.client = client;
  }

  public TorrentClient get() {
    return client;
  }
}
