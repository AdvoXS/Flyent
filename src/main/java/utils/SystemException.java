package utils;

import utils.form.GUIUtils;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SystemException extends RuntimeException{
    public SystemException(String message){
      super(message);
    }

  @Override
  public void printStackTrace() {
    super.printStackTrace();
  }

  @Override
  public void printStackTrace(PrintStream s) {
    GUIUtils.showErrorDialog("Ошибка","Произошла ошибка, обратитесь к разработчику!\nОписание ошибки: "+ getMessage());
  }

  @Override
  public void printStackTrace(PrintWriter s) {
    super.printStackTrace(s);
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    return super.getStackTrace();
  }
}
