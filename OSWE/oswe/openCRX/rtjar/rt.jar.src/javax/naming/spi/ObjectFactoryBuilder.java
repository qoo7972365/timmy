package javax.naming.spi;

import java.util.Hashtable;
import javax.naming.NamingException;

public interface ObjectFactoryBuilder {
  ObjectFactory createObjectFactory(Object paramObject, Hashtable<?, ?> paramHashtable) throws NamingException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/ObjectFactoryBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */