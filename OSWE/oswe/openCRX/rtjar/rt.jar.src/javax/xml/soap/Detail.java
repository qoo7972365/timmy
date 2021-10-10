package javax.xml.soap;

import java.util.Iterator;
import javax.xml.namespace.QName;

public interface Detail extends SOAPFaultElement {
  DetailEntry addDetailEntry(Name paramName) throws SOAPException;
  
  DetailEntry addDetailEntry(QName paramQName) throws SOAPException;
  
  Iterator getDetailEntries();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/Detail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */