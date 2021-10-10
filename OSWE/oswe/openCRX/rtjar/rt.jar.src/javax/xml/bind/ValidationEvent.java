package javax.xml.bind;

public interface ValidationEvent {
  public static final int WARNING = 0;
  
  public static final int ERROR = 1;
  
  public static final int FATAL_ERROR = 2;
  
  int getSeverity();
  
  String getMessage();
  
  Throwable getLinkedException();
  
  ValidationEventLocator getLocator();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/ValidationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */