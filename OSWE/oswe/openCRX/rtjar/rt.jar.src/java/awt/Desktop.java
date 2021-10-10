/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.DesktopPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.DesktopBrowse;
/*     */ import sun.awt.SunToolkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Desktop
/*     */ {
/*     */   private DesktopPeer peer;
/*     */   
/*     */   public enum Action
/*     */   {
/*  95 */     OPEN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     EDIT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     PRINT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     MAIL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     BROWSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Desktop() {
/* 125 */     this.peer = Toolkit.getDefaultToolkit().createDesktopPeer(this);
/*     */   }
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
/*     */   public static synchronized Desktop getDesktop() {
/* 142 */     if (GraphicsEnvironment.isHeadless()) throw new HeadlessException(); 
/* 143 */     if (!isDesktopSupported()) {
/* 144 */       throw new UnsupportedOperationException("Desktop API is not supported on the current platform");
/*     */     }
/*     */ 
/*     */     
/* 148 */     AppContext appContext = AppContext.getAppContext();
/* 149 */     Desktop desktop = (Desktop)appContext.get(Desktop.class);
/*     */     
/* 151 */     if (desktop == null) {
/* 152 */       desktop = new Desktop();
/* 153 */       appContext.put(Desktop.class, desktop);
/*     */     } 
/*     */     
/* 156 */     return desktop;
/*     */   }
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
/*     */   public static boolean isDesktopSupported() {
/* 169 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 170 */     if (toolkit instanceof SunToolkit) {
/* 171 */       return ((SunToolkit)toolkit).isDesktopSupported();
/*     */     }
/* 173 */     return false;
/*     */   }
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
/*     */   public boolean isSupported(Action paramAction) {
/* 193 */     return this.peer.isSupported(paramAction);
/*     */   }
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
/*     */   private static void checkFileValidation(File paramFile) {
/* 206 */     if (!paramFile.exists()) {
/* 207 */       throw new IllegalArgumentException("The file: " + paramFile
/* 208 */           .getPath() + " doesn't exist.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkActionSupport(Action paramAction) {
/* 220 */     if (!isSupported(paramAction)) {
/* 221 */       throw new UnsupportedOperationException("The " + paramAction.name() + " action is not supported on the current platform!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAWTPermission() {
/* 233 */     SecurityManager securityManager = System.getSecurityManager();
/* 234 */     if (securityManager != null) {
/* 235 */       securityManager.checkPermission(new AWTPermission("showWindowWithoutWarningBanner"));
/*     */     }
/*     */   }
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
/*     */   
/*     */   public void open(File paramFile) throws IOException {
/* 263 */     paramFile = new File(paramFile.getPath());
/* 264 */     checkAWTPermission();
/* 265 */     checkExec();
/* 266 */     checkActionSupport(Action.OPEN);
/* 267 */     checkFileValidation(paramFile);
/*     */     
/* 269 */     this.peer.open(paramFile);
/*     */   }
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
/*     */   public void edit(File paramFile) throws IOException {
/* 295 */     paramFile = new File(paramFile.getPath());
/* 296 */     checkAWTPermission();
/* 297 */     checkExec();
/* 298 */     checkActionSupport(Action.EDIT);
/* 299 */     paramFile.canWrite();
/* 300 */     checkFileValidation(paramFile);
/*     */     
/* 302 */     this.peer.edit(paramFile);
/*     */   }
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
/*     */   public void print(File paramFile) throws IOException {
/* 326 */     paramFile = new File(paramFile.getPath());
/* 327 */     checkExec();
/* 328 */     SecurityManager securityManager = System.getSecurityManager();
/* 329 */     if (securityManager != null) {
/* 330 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 332 */     checkActionSupport(Action.PRINT);
/* 333 */     checkFileValidation(paramFile);
/*     */     
/* 335 */     this.peer.print(paramFile);
/*     */   }
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
/*     */   public void browse(URI paramURI) throws IOException {
/* 373 */     SecurityException securityException = null;
/*     */     try {
/* 375 */       checkAWTPermission();
/* 376 */       checkExec();
/* 377 */     } catch (SecurityException securityException1) {
/* 378 */       securityException = securityException1;
/*     */     } 
/* 380 */     checkActionSupport(Action.BROWSE);
/* 381 */     if (paramURI == null) {
/* 382 */       throw new NullPointerException();
/*     */     }
/* 384 */     if (securityException == null) {
/* 385 */       this.peer.browse(paramURI);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 392 */     URL uRL = null;
/*     */     try {
/* 394 */       uRL = paramURI.toURL();
/* 395 */     } catch (MalformedURLException malformedURLException) {
/* 396 */       throw new IllegalArgumentException("Unable to convert URI to URL", malformedURLException);
/*     */     } 
/* 398 */     DesktopBrowse desktopBrowse = DesktopBrowse.getInstance();
/* 399 */     if (desktopBrowse == null)
/*     */     {
/* 401 */       throw securityException;
/*     */     }
/* 403 */     desktopBrowse.browse(uRL);
/*     */   }
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
/*     */   public void mail() throws IOException {
/* 422 */     checkAWTPermission();
/* 423 */     checkExec();
/* 424 */     checkActionSupport(Action.MAIL);
/* 425 */     URI uRI = null;
/*     */     try {
/* 427 */       uRI = new URI("mailto:?");
/* 428 */       this.peer.mail(uRI);
/* 429 */     } catch (URISyntaxException uRISyntaxException) {}
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mail(URI paramURI) throws IOException {
/* 464 */     checkAWTPermission();
/* 465 */     checkExec();
/* 466 */     checkActionSupport(Action.MAIL);
/* 467 */     if (paramURI == null) throw new NullPointerException();
/*     */     
/* 469 */     if (!"mailto".equalsIgnoreCase(paramURI.getScheme())) {
/* 470 */       throw new IllegalArgumentException("URI scheme is not \"mailto\"");
/*     */     }
/*     */     
/* 473 */     this.peer.mail(paramURI);
/*     */   }
/*     */   
/*     */   private void checkExec() throws SecurityException {
/* 477 */     SecurityManager securityManager = System.getSecurityManager();
/* 478 */     if (securityManager != null)
/* 479 */       securityManager.checkPermission(new FilePermission("<<ALL FILES>>", "execute")); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Desktop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */