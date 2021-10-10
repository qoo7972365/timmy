package javax.xml.soap;

import java.util.Locale;
import javax.xml.namespace.QName;
import org.w3c.dom.Document;

public interface SOAPBody extends SOAPElement {
  SOAPFault addFault() throws SOAPException;
  
  SOAPFault addFault(Name paramName, String paramString, Locale paramLocale) throws SOAPException;
  
  SOAPFault addFault(QName paramQName, String paramString, Locale paramLocale) throws SOAPException;
  
  SOAPFault addFault(Name paramName, String paramString) throws SOAPException;
  
  SOAPFault addFault(QName paramQName, String paramString) throws SOAPException;
  
  boolean hasFault();
  
  SOAPFault getFault();
  
  SOAPBodyElement addBodyElement(Name paramName) throws SOAPException;
  
  SOAPBodyElement addBodyElement(QName paramQName) throws SOAPException;
  
  SOAPBodyElement addDocument(Document paramDocument) throws SOAPException;
  
  Document extractContentAsDocument() throws SOAPException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/SOAPBody.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */