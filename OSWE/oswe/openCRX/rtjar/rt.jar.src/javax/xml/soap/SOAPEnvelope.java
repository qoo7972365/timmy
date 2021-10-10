package javax.xml.soap;

public interface SOAPEnvelope extends SOAPElement {
  Name createName(String paramString1, String paramString2, String paramString3) throws SOAPException;
  
  Name createName(String paramString) throws SOAPException;
  
  SOAPHeader getHeader() throws SOAPException;
  
  SOAPBody getBody() throws SOAPException;
  
  SOAPHeader addHeader() throws SOAPException;
  
  SOAPBody addBody() throws SOAPException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/SOAPEnvelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */