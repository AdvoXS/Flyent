package utils.xml;

import org.xml.sax.InputSource;

import java.io.*;

public class XmlReader {
  private InputSource source;  // источник данных XML

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  /**
   * Конструктор класса
   *
   * @param fname   полный путь к файлу XML
   * @param charset кодировка содержимого XML файла
   * @throws FileNotFoundException
   */
  public XmlReader(final String fname, final String charset)
      throws FileNotFoundException {
    this(new FileInputStream(fname), charset);
  }
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  /**
   * Конструктор класса
   *
   * @param stream  поток данных
   * @param charset кодировка типа UTF8
   */
  public XmlReader(final InputStream stream, final String charset) {
    // Чтение данных XML
    try {
      Reader reader = new InputStreamReader(stream, charset);
      source = new InputSource(reader);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  /**
   * Функция получения источника данных XML
   *
   * @return InputSource источник данных XML
   */
  public InputSource getInputSource() {
    return source;
  }
}