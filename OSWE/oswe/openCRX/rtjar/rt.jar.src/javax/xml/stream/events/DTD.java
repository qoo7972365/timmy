package javax.xml.stream.events;

import java.util.List;

public interface DTD extends XMLEvent {
  String getDocumentTypeDeclaration();
  
  Object getProcessedDTD();
  
  List getNotations();
  
  List getEntities();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/events/DTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */