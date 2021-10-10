package javax.xml.transform;

public interface ErrorListener {
  void warning(TransformerException paramTransformerException) throws TransformerException;
  
  void error(TransformerException paramTransformerException) throws TransformerException;
  
  void fatalError(TransformerException paramTransformerException) throws TransformerException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/ErrorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */