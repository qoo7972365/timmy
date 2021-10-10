package javax.xml.transform;

import java.util.Properties;

public interface Templates {
  Transformer newTransformer() throws TransformerConfigurationException;
  
  Properties getOutputProperties();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/Templates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */