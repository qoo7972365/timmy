package javax.xml.bind;

import java.io.IOException;
import javax.xml.transform.Result;

public abstract class SchemaOutputResolver {
  public abstract Result createOutput(String paramString1, String paramString2) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/SchemaOutputResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */