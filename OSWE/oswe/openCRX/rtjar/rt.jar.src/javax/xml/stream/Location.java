package javax.xml.stream;

public interface Location {
  int getLineNumber();
  
  int getColumnNumber();
  
  int getCharacterOffset();
  
  String getPublicId();
  
  String getSystemId();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/Location.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */