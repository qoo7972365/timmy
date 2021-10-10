package sun.net;

import java.net.URL;

public interface ProgressMeteringPolicy {
  boolean shouldMeterInput(URL paramURL, String paramString);
  
  int getProgressUpdateThreshold();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ProgressMeteringPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */