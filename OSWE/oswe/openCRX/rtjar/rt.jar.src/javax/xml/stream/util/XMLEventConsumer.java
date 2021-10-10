package javax.xml.stream.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public interface XMLEventConsumer {
  void add(XMLEvent paramXMLEvent) throws XMLStreamException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/util/XMLEventConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */