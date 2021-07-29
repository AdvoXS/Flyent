package torrent;

import com.turn.ttorrent.client.CommunicationManager;
import com.turn.ttorrent.client.TorrentListener;
import com.turn.ttorrent.client.TorrentListenerWrapper;
import com.turn.ttorrent.client.TorrentManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TorrentClient {
  private static final int DEFAULT_EXECUTOR_SIZE = 10;
  public final CommunicationManager communicationManager;

  public TorrentClient() {
    this(10, 10);
  }

  public TorrentClient(int workingExecutorSize, int validatorExecutorSize) {
    this.communicationManager = new CommunicationManager(Executors.newFixedThreadPool(workingExecutorSize), Executors.newFixedThreadPool(validatorExecutorSize));
  }

  public void stop() {
    this.communicationManager.stop();
    Exception interruptedException = null;
    boolean anyFailedByTimeout = false;
    Iterator<ExecutorService> it = Arrays.asList(this.communicationManager.getExecutor(), this.communicationManager.getPieceValidatorExecutor()).iterator();

    while(it.hasNext()) {
      ExecutorService executorService = it.next();
      executorService.shutdown();
      if (!Thread.currentThread().isInterrupted()) {
        try {
          if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
            anyFailedByTimeout = true;
          }
        } catch (InterruptedException var8) {
          interruptedException = var8;
        }
      }
    }
    if (interruptedException != null) {
      throw new RuntimeException("Thread was interrupted, shutdown methods are invoked but maybe tasks are not finished yet", interruptedException);
    } else if (anyFailedByTimeout) {
      throw new RuntimeException("At least one executor was not fully shutdown because timeout was elapsed");
    }
  }

  public void downloadTorrent(String torrentFile, String downloadDir, InetAddress iPv4Address) throws IOException, InterruptedException {
    this.communicationManager.start(new InetAddress[]{iPv4Address});
    final Semaphore semaphore = new Semaphore(0);
    List<TorrentListener> listeners = Collections.singletonList(new TorrentListenerWrapper() {
      public void validationComplete(int validpieces, int totalpieces) {
        if (validpieces == totalpieces) {
          semaphore.release();
        }

      }

      public void downloadComplete() {
        semaphore.release();
      }
    });
    this.communicationManager.addTorrent(torrentFile, downloadDir, listeners);
    semaphore.acquire();
  }

  private TorrentManager startDownloading(String torrentFile, String downloadDir, InetAddress iPv4Address) throws IOException {
    this.communicationManager.start(new InetAddress[]{iPv4Address});
    return this.communicationManager.addTorrent(torrentFile, downloadDir);
  }

  public TorrentManager downloadTorrentAsync(String torrentFile, String downloadDir, InetAddress iPv4Address) throws IOException {
    return this.startDownloading(torrentFile, downloadDir, iPv4Address);
  }
}
