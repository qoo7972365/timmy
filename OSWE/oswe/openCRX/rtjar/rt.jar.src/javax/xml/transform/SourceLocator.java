package javax.xml.transform;

public interface SourceLocator {
  String getPublicId();
  
  String getSystemId();
  
  int getLineNumber();
  
  int getColumnNumber();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/SourceLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */