package forms;

import com.turn.ttorrent.client.SharedTorrent;
import org.jetbrains.annotations.Nls;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class TorrentTableModel implements TableModel {
  List<SharedTorrent> torrents = new ArrayList<>();
  List<String> headers = new ArrayList<>(1);

  {
    headers.add("Загружено");
  }

  @Override
  public int getRowCount() {
    return torrents.size();
  }

  @Override
  public int getColumnCount() {
    return headers.size();
  }

  @Nls
  @Override
  public String getColumnName(int columnIndex) {
    return headers.get(columnIndex);
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex){
      case 0:
        return Object.class;
      default:
        return null;
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;

  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return torrents.get(rowIndex);
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if(aValue instanceof SharedTorrent)
      torrents.add((SharedTorrent) aValue);
  }

  @Override
  public void addTableModelListener(TableModelListener l) {

  }

  @Override
  public void removeTableModelListener(TableModelListener l) {

  }
}
