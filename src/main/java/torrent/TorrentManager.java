package torrent;

import com.turn.ttorrent.client.CommunicationManager;
import com.turn.ttorrent.client.TorrentsStorage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TorrentManager {
  private static TorrentManager instance = null;
  private final CommunicationManager communicationManager = Torrent.INSTANCE.get().communicationManager;
  private final TorrentsStorage torrentsStorage = communicationManager.getTorrentsStorage();
  private final List<TorrentListener> listeners;
  private TorrentManagerState state;
  private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

  private TorrentManager() {
    this.listeners = new ArrayList<>();
  }

  public static TorrentManager getInstance() {
    if (instance == null)
      instance = new TorrentManager();
    return instance;
  }

  public void addTorrentListener(TorrentListener listener) {
    this.listeners.add(listener);
  }

  public void addTorrentListener(List<TorrentListener> listeners) {
    this.listeners.addAll(listeners);
  }

  public void removeTorrentListener(TorrentListener listener) {
    this.listeners.remove(listener);
  }

  public void start() {
    try {
      communicationManager.start(InetAddress.getLocalHost());
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    state = TorrentManagerState.RUNNING;
    Thread torrentThread = new Thread(() -> {
      while (getState().equals(TorrentManagerState.RUNNING)) {
        for (TorrentListener listener : listeners) {
          listener.updateInfo(torrentsStorage.getLoadedTorrents(), torrentsStorage.activeTorrents());
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    torrentThread.start();
  }

  public void addTorrents(List<File> files, String downloadPath) {
    for (File file : files) {
      try {
        communicationManager.addTorrent(file.getAbsolutePath(), downloadPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public TorrentManagerState getState() {
    rwl.readLock().lock();
    TorrentManagerState state = this.state;
    rwl.readLock().unlock();
    return state;
  }

  public void setState(TorrentManagerState state) {
    rwl.writeLock().lock();
    this.state = state;
    rwl.writeLock().unlock();
  }
}

