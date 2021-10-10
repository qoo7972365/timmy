package sun.java2d.opengl;

import sun.awt.image.SurfaceManager;
import sun.java2d.SurfaceData;
import sun.java2d.pipe.BufferedContext;
import sun.java2d.pipe.hw.AccelGraphicsConfig;

interface OGLGraphicsConfig extends AccelGraphicsConfig, SurfaceManager.ProxiedGraphicsConfig {
  OGLContext getContext();
  
  long getNativeConfigInfo();
  
  boolean isCapPresent(int paramInt);
  
  SurfaceData createManagedSurface(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLGraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */