/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.peer.CanvasPeer;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.X11GraphicsConfig;
/*     */ import sun.awt.X11GraphicsDevice;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XCanvasPeer
/*     */   extends XComponentPeer
/*     */   implements CanvasPeer
/*     */ {
/*     */   private boolean eraseBackgroundDisabled;
/*     */   
/*     */   XCanvasPeer() {}
/*     */   
/*     */   XCanvasPeer(XCreateWindowParams paramXCreateWindowParams) {
/*  42 */     super(paramXCreateWindowParams);
/*     */   }
/*     */   
/*     */   XCanvasPeer(Component paramComponent) {
/*  46 */     super(paramComponent);
/*     */   }
/*     */   
/*     */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  50 */     super.preInit(paramXCreateWindowParams);
/*  51 */     if (SunToolkit.getSunAwtNoerasebackground()) {
/*  52 */       disableBackgroundErase();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration getAppropriateGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration) {
/*  62 */     if (this.graphicsConfig == null || paramGraphicsConfiguration == null) {
/*  63 */       return paramGraphicsConfiguration;
/*     */     }
/*     */ 
/*     */     
/*  67 */     int i = ((X11GraphicsDevice)paramGraphicsConfiguration.getDevice()).getScreen();
/*     */ 
/*     */ 
/*     */     
/*  71 */     int j = this.graphicsConfig.getVisual();
/*     */ 
/*     */ 
/*     */     
/*  75 */     X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[i];
/*     */     
/*  77 */     for (byte b = 0; b < x11GraphicsDevice.getNumConfigs(i); b++) {
/*  78 */       if (j == x11GraphicsDevice.getConfigVisualId(b, i)) {
/*     */         
/*  80 */         this.graphicsConfig = (X11GraphicsConfig)x11GraphicsDevice.getConfigurations()[b];
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  85 */     if (this.graphicsConfig == null) {
/*  86 */       this
/*     */ 
/*     */         
/*  89 */         .graphicsConfig = (X11GraphicsConfig)GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[i].getDefaultConfiguration();
/*     */     }
/*     */     
/*  92 */     return (GraphicsConfiguration)this.graphicsConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldFocusOnClick() {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public void disableBackgroundErase() {
/* 101 */     this.eraseBackgroundDisabled = true;
/*     */   }
/*     */   protected boolean doEraseBackground() {
/* 104 */     return !this.eraseBackgroundDisabled;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XCanvasPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */