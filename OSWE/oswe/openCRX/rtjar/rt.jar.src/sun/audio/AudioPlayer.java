/*     */ package sun.audio;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ public final class AudioPlayer
/*     */   extends Thread
/*     */ {
/*     */   private final AudioDevice devAudio;
/*     */   private static final boolean DEBUG = false;
/*  75 */   public static final AudioPlayer player = getAudioPlayer();
/*     */ 
/*     */ 
/*     */   
/*     */   private static ThreadGroup getAudioThreadGroup() {
/*  80 */     ThreadGroup threadGroup = currentThread().getThreadGroup();
/*  81 */     while (threadGroup.getParent() != null && threadGroup
/*  82 */       .getParent().getParent() != null) {
/*  83 */       threadGroup = threadGroup.getParent();
/*     */     }
/*  85 */     return threadGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AudioPlayer getAudioPlayer() {
/*  96 */     PrivilegedAction<AudioPlayer> privilegedAction = new PrivilegedAction() {
/*     */         public Object run() {
/*  98 */           AudioPlayer audioPlayer = new AudioPlayer();
/*  99 */           audioPlayer.setPriority(10);
/* 100 */           audioPlayer.setDaemon(true);
/* 101 */           audioPlayer.start();
/* 102 */           return audioPlayer;
/*     */         }
/*     */       };
/* 105 */     return AccessController.<AudioPlayer>doPrivileged(privilegedAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioPlayer() {
/* 114 */     super(getAudioThreadGroup(), "Audio Player");
/*     */     
/* 116 */     this.devAudio = AudioDevice.device;
/* 117 */     this.devAudio.open();
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
/*     */   public synchronized void start(InputStream paramInputStream) {
/* 133 */     this.devAudio.openChannel(paramInputStream);
/* 134 */     notify();
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
/*     */   public synchronized void stop(InputStream paramInputStream) {
/* 152 */     this.devAudio.closeChannel(paramInputStream);
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
/*     */   public void run() {
/* 170 */     this.devAudio.play();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/* 176 */         Thread.sleep(5000L);
/*     */       }
/* 178 */     } catch (Exception exception) {
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/audio/AudioPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */