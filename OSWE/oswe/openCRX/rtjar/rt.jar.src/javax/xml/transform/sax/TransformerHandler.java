package javax.xml.transform.sax;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ext.LexicalHandler;

public interface TransformerHandler extends ContentHandler, LexicalHandler, DTDHandler {
  void setResult(Result paramResult) throws IllegalArgumentException;
  
  void setSystemId(String paramString);
  
  String getSystemId();
  
  Transformer getTransformer();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/sax/TransformerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */