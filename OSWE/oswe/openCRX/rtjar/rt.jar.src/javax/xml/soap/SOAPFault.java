package javax.xml.soap;

import java.util.Iterator;
import java.util.Locale;
import javax.xml.namespace.QName;

public interface SOAPFault extends SOAPBodyElement {
  void setFaultCode(Name paramName) throws SOAPException;
  
  void setFaultCode(QName paramQName) throws SOAPException;
  
  void setFaultCode(String paramString) throws SOAPException;
  
  Name getFaultCodeAsName();
  
  QName getFaultCodeAsQName();
  
  Iterator getFaultSubcodes();
  
  void removeAllFaultSubcodes();
  
  void appendFaultSubcode(QName paramQName) throws SOAPException;
  
  String getFaultCode();
  
  void setFaultActor(String paramString) throws SOAPException;
  
  String getFaultActor();
  
  void setFaultString(String paramString) throws SOAPException;
  
  void setFaultString(String paramString, Locale paramLocale) throws SOAPException;
  
  String getFaultString();
  
  Locale getFaultStringLocale();
  
  boolean hasDetail();
  
  Detail getDetail();
  
  Detail addDetail() throws SOAPException;
  
  Iterator getFaultReasonLocales() throws SOAPException;
  
  Iterator getFaultReasonTexts() throws SOAPException;
  
  String getFaultReasonText(Locale paramLocale) throws SOAPException;
  
  void addFaultReasonText(String paramString, Locale paramLocale) throws SOAPException;
  
  String getFaultNode();
  
  void setFaultNode(String paramString) throws SOAPException;
  
  String getFaultRole();
  
  void setFaultRole(String paramString) throws SOAPException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/SOAPFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */