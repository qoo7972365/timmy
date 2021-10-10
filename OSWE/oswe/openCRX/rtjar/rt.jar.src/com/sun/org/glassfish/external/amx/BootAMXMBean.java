package com.sun.org.glassfish.external.amx;

import com.sun.org.glassfish.external.arc.Stability;
import com.sun.org.glassfish.external.arc.Taxonomy;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

@Taxonomy(stability = Stability.UNCOMMITTED)
public interface BootAMXMBean {
  public static final String BOOT_AMX_OPERATION_NAME = "bootAMX";
  
  ObjectName bootAMX();
  
  JMXServiceURL[] getJMXServiceURLs();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/amx/BootAMXMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */