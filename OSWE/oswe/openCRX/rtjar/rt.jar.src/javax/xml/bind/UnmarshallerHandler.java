package javax.xml.bind;

import org.xml.sax.ContentHandler;

public interface UnmarshallerHandler extends ContentHandler {
  Object getResult() throws JAXBException, IllegalStateException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/UnmarshallerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */