package sun.util.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public abstract class XmlPropertiesProvider {
  public abstract void load(Properties paramProperties, InputStream paramInputStream) throws IOException, InvalidPropertiesFormatException;
  
  public abstract void store(Properties paramProperties, OutputStream paramOutputStream, String paramString1, String paramString2) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/spi/XmlPropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */