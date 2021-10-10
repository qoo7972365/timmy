/*     */ package sun.applet;
/*     */ 
/*     */ import com.sun.media.sound.JavaSoundAudioClip;
/*     */ import java.applet.AudioClip;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
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
/*     */ public class AppletAudioClip
/*     */   implements AudioClip
/*     */ {
/*  47 */   private URL url = null;
/*     */ 
/*     */   
/*  50 */   private AudioClip audioClip = null;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean DEBUG = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppletAudioClip(URL paramURL) {
/*  60 */     this.url = paramURL;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  65 */       InputStream inputStream = paramURL.openStream();
/*  66 */       createAppletAudioClip(inputStream);
/*     */     }
/*  68 */     catch (IOException iOException) {
/*     */       
/*  70 */       if (this.DEBUG) {
/*  71 */         System.err.println("IOException creating AppletAudioClip" + iOException);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppletAudioClip(URLConnection paramURLConnection) {
/*     */     try {
/*  84 */       createAppletAudioClip(paramURLConnection.getInputStream());
/*     */     }
/*  86 */     catch (IOException iOException) {
/*     */       
/*  88 */       if (this.DEBUG) {
/*  89 */         System.err.println("IOException creating AppletAudioClip" + iOException);
/*     */       }
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
/*     */   public AppletAudioClip(byte[] paramArrayOfbyte) {
/*     */     try {
/* 105 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*     */       
/* 107 */       createAppletAudioClip(byteArrayInputStream);
/*     */     }
/* 109 */     catch (IOException iOException) {
/*     */       
/* 111 */       if (this.DEBUG) {
/* 112 */         System.err.println("IOException creating AppletAudioClip " + iOException);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createAppletAudioClip(InputStream paramInputStream) throws IOException {
/*     */     try {
/* 125 */       this.audioClip = new JavaSoundAudioClip(paramInputStream);
/* 126 */     } catch (Exception exception) {
/*     */       
/* 128 */       throw new IOException("Failed to construct the AudioClip: " + exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void play() {
/* 135 */     if (this.audioClip != null) {
/* 136 */       this.audioClip.play();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void loop() {
/* 142 */     if (this.audioClip != null) {
/* 143 */       this.audioClip.loop();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void stop() {
/* 148 */     if (this.audioClip != null)
/* 149 */       this.audioClip.stop(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletAudioClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */