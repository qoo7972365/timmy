package javax.xml.bind;

public interface Validator {
  void setEventHandler(ValidationEventHandler paramValidationEventHandler) throws JAXBException;
  
  ValidationEventHandler getEventHandler() throws JAXBException;
  
  boolean validate(Object paramObject) throws JAXBException;
  
  boolean validateRoot(Object paramObject) throws JAXBException;
  
  void setProperty(String paramString, Object paramObject) throws PropertyException;
  
  Object getProperty(String paramString) throws PropertyException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/Validator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */