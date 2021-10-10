/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Desktop;
/*     */ import java.awt.peer.DesktopPeer;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import sun.awt.UNIXToolkit;
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
/*     */ public class XDesktopPeer
/*     */   implements DesktopPeer
/*     */ {
/*  51 */   private static final List<Desktop.Action> supportedActions = new ArrayList<>(
/*  52 */       Arrays.asList(new Desktop.Action[] { Desktop.Action.OPEN, Desktop.Action.MAIL, Desktop.Action.BROWSE }));
/*     */   
/*     */   private static boolean nativeLibraryLoaded = false;
/*     */   private static boolean initExecuted = false;
/*     */   
/*     */   private static void initWithLock() {
/*  58 */     XToolkit.awtLock();
/*     */     try {
/*  60 */       if (!initExecuted) {
/*  61 */         nativeLibraryLoaded = init(UNIXToolkit.getEnabledGtkVersion()
/*  62 */             .getNumber(), UNIXToolkit.isGtkVerbose());
/*     */       }
/*     */     } finally {
/*  65 */       initExecuted = true;
/*  66 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   XDesktopPeer() {
/*  72 */     initWithLock();
/*     */   }
/*     */   
/*     */   static boolean isDesktopSupported() {
/*  76 */     initWithLock();
/*  77 */     return (nativeLibraryLoaded && !supportedActions.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean isSupported(Desktop.Action paramAction) {
/*  81 */     return supportedActions.contains(paramAction);
/*     */   }
/*     */   
/*     */   public void open(File paramFile) throws IOException {
/*     */     try {
/*  86 */       launch(paramFile.toURI());
/*  87 */     } catch (MalformedURLException malformedURLException) {
/*  88 */       throw new IOException(paramFile.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void edit(File paramFile) throws IOException {
/*  93 */     throw new UnsupportedOperationException("The current platform doesn't support the EDIT action.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void print(File paramFile) throws IOException {
/*  98 */     throw new UnsupportedOperationException("The current platform doesn't support the PRINT action.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void mail(URI paramURI) throws IOException {
/* 103 */     launch(paramURI);
/*     */   }
/*     */   
/*     */   public void browse(URI paramURI) throws IOException {
/* 107 */     launch(paramURI);
/*     */   }
/*     */   
/*     */   private void launch(URI paramURI) throws IOException {
/* 111 */     byte[] arrayOfByte = (paramURI.toString() + Character.MIN_VALUE).getBytes();
/* 112 */     boolean bool = false;
/* 113 */     XToolkit.awtLock();
/*     */     try {
/* 115 */       if (!nativeLibraryLoaded) {
/* 116 */         throw new IOException("Failed to load native libraries.");
/*     */       }
/* 118 */       bool = gnome_url_show(arrayOfByte);
/*     */     } finally {
/* 120 */       XToolkit.awtUnlock();
/*     */     } 
/* 122 */     if (!bool)
/* 123 */       throw new IOException("Failed to show URI:" + paramURI); 
/*     */   }
/*     */   
/*     */   private native boolean gnome_url_show(byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native boolean init(int paramInt, boolean paramBoolean);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDesktopPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */