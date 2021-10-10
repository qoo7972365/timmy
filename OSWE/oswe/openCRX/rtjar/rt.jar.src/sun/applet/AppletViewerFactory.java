package sun.applet;

import java.awt.MenuBar;
import java.net.URL;
import java.util.Hashtable;

public interface AppletViewerFactory {
  AppletViewer createAppletViewer(int paramInt1, int paramInt2, URL paramURL, Hashtable paramHashtable);
  
  MenuBar getBaseMenuBar();
  
  boolean isStandalone();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletViewerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */