package utils;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * from http://java-online.ru/swing-menu.xhtml
 */
public class XMLMenuParser extends DefaultHandler {
  private JMenuBar currentMenuBar; // текущая строка меню
  // хранилище для всех частей системы меню
  private Map<String, Object> menuStorage = new HashMap<String, Object>();
  // список для упорядочения выпадающих меню
  private LinkedList<JMenu> menus = new LinkedList<JMenu>();

  private final String ATTRIB_menubar = "menubar";
  private final String ATTRIB_menu = "menu";
  private final String ATTRIB_menuitem = "menuitem";
  private final String ATTRIB_name = "name";
  private final String ATTRIB_text = "text";
  private final String ATTRIB_mnemonic = "mnemonic";
  private final String ATTRIB_accelerator = "accelerator";
  private final String ATTRIB_enabled = "enabled";
  private final String ATTRIB_separator = "separator";

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Новый узел тега XML
  public void startElement(String uri, String localName, String qName,
                           Attributes attributes) {
    // Определяем тип узла
    switch (qName) {
      case ATTRIB_menubar:
        parseMenuBar(attributes);
        break;
      case ATTRIB_menu:
        parseMenu(attributes);
        break;
      case ATTRIB_menuitem:
        parseMenuItem(attributes);
        break;
    }
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Конец узла, используется для смены выпадающих пунктов меню
  public void endElement(String uri, String localName, String qName) {
    if (qName.equals(ATTRIB_menu)) menus.removeFirst();
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Создание новой строки меню JMenuBar
  private void parseMenuBar(Attributes attrs) {
    JMenuBar menuBar = new JMenuBar();
    // Определение имени
    String name = attrs.getValue(ATTRIB_name);
    menuStorage.put(name, menuBar);
    currentMenuBar = menuBar;
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Создание выпадающего меню
  private void parseMenu(Attributes attrs) {
    // Создание меню
    JMenu menu = new JMenu();
    String name = attrs.getValue(ATTRIB_name);
    // Настройка общих атрибутов
    adjustProperties(menu, attrs);
    menuStorage.put(name, menu);
    // Добавление меню к предыдущему выпадающему меню или к строке меню
    if (menus.size() != 0) {
      menus.getFirst().add(menu);
    }
    else {
      currentMenuBar.add(menu);
    }
    // Добавлние в список выпадающих меню
    menus.addFirst(menu);
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Создание пункта меню
  private void parseMenuItem(Attributes attrs) {
    // Проверка, не разделитель ли это
    String name = attrs.getValue(ATTRIB_name);
    if (name.equals(ATTRIB_separator)) {
      menus.getFirst().addSeparator();
      return;
    }
    // Создание пункта меню
    JMenuItem menuItem = new JMenuItem();
    // настраиваем свойства
    adjustProperties(menuItem, attrs);
    menuStorage.put(name, menuItem);
    // Добавление к текущему выпадающему меню
    menus.getFirst().add(menuItem);
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Настройка общих атрибутов пунктов меню
  private void adjustProperties(JMenuItem menuItem, Attributes attrs) {
    // Чтение поддерживаемых атрибутов
    String text = attrs.getValue(ATTRIB_text);
    String mnemonic = attrs.getValue(ATTRIB_mnemonic);
    String accelerator = attrs.getValue(ATTRIB_accelerator);
    String enabled = attrs.getValue(ATTRIB_enabled);
    // Определение заголовка элемента меню
    menuItem.setText(text);
    if (mnemonic != null) {
      menuItem.setMnemonic(mnemonic.charAt(0));
    }
    if (accelerator != null) {
      menuItem.setAccelerator(
          KeyStroke.getKeyStroke(accelerator));
    }
    if (enabled != null) {
      boolean isEnabled = !enabled.equals(String.valueOf(false));
      menuItem.setEnabled(isEnabled);
    }
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public Map<String, Object> getMenuStorage() {
    return menuStorage;
  }
}