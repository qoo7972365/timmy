package javax.xml.soap;

import java.util.Iterator;
import javax.xml.namespace.QName;

public interface SOAPHeader extends SOAPElement {
  SOAPHeaderElement addHeaderElement(Name paramName) throws SOAPException;
  
  SOAPHeaderElement addHeaderElement(QName paramQName) throws SOAPException;
  
  Iterator examineMustUnderstandHeaderElements(String paramString);
  
  Iterator examineHeaderElements(String paramString);
  
  Iterator extractHeaderElements(String paramString);
  
  SOAPHeaderElement addNotUnderstoodHeaderElement(QName paramQName) throws SOAPException;
  
  SOAPHeaderElement addUpgradeHeaderElement(Iterator paramIterator) throws SOAPException;
  
  SOAPHeaderElement addUpgradeHeaderElement(String[] paramArrayOfString) throws SOAPException;
  
  SOAPHeaderElement addUpgradeHeaderElement(String paramString) throws SOAPException;
  
  Iterator examineAllHeaderElements();
  
  Iterator extractAllHeaderElements();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/SOAPHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */