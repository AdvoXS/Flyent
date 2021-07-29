package torrent;

import com.turn.ttorrent.client.LoadedTorrent;
import com.turn.ttorrent.client.SharedTorrent;

import java.util.List;

public interface TorrentListener {
  void updateInfo(List<LoadedTorrent> loadedTorrents, List<SharedTorrent> sharedTorrents);
}
