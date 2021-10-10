package com.sun.org.omg.SendingContext;

import com.sun.org.omg.CORBA.Repository;
import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.SendingContext.RunTimeOperations;

public interface CodeBaseOperations extends RunTimeOperations {
  Repository get_ir();
  
  String implementation(String paramString);
  
  String[] implementations(String[] paramArrayOfString);
  
  FullValueDescription meta(String paramString);
  
  FullValueDescription[] metas(String[] paramArrayOfString);
  
  String[] bases(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/CodeBaseOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */