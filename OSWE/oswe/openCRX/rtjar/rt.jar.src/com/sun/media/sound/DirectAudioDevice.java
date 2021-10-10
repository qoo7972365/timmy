/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.Vector;
/*      */ import javax.sound.sampled.AudioFormat;
/*      */ import javax.sound.sampled.AudioInputStream;
/*      */ import javax.sound.sampled.BooleanControl;
/*      */ import javax.sound.sampled.Clip;
/*      */ import javax.sound.sampled.Control;
/*      */ import javax.sound.sampled.DataLine;
/*      */ import javax.sound.sampled.FloatControl;
/*      */ import javax.sound.sampled.Line;
/*      */ import javax.sound.sampled.LineUnavailableException;
/*      */ import javax.sound.sampled.SourceDataLine;
/*      */ import javax.sound.sampled.TargetDataLine;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DirectAudioDevice
/*      */   extends AbstractMixer
/*      */ {
/*      */   private static final int CLIP_BUFFER_TIME = 1000;
/*      */   private static final int DEFAULT_LINE_BUFFER_TIME = 500;
/*   55 */   private int deviceCountOpened = 0;
/*      */ 
/*      */   
/*   58 */   private int deviceCountStarted = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   DirectAudioDevice(DirectAudioDeviceProvider.DirectAudioDeviceInfo paramDirectAudioDeviceInfo) {
/*   63 */     super(paramDirectAudioDeviceInfo, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   71 */     DirectDLI directDLI1 = createDataLineInfo(true);
/*   72 */     if (directDLI1 != null) {
/*   73 */       this.sourceLineInfo = new Line.Info[2];
/*      */       
/*   75 */       this.sourceLineInfo[0] = directDLI1;
/*      */       
/*   77 */       this.sourceLineInfo[1] = new DirectDLI(Clip.class, directDLI1.getFormats(), directDLI1
/*   78 */           .getHardwareFormats(), 32, -1);
/*      */     }
/*      */     else {
/*      */       
/*   82 */       this.sourceLineInfo = new Line.Info[0];
/*      */     } 
/*      */ 
/*      */     
/*   86 */     DirectDLI directDLI2 = createDataLineInfo(false);
/*   87 */     if (directDLI2 != null) {
/*   88 */       this.targetLineInfo = new Line.Info[1];
/*   89 */       this.targetLineInfo[0] = directDLI2;
/*      */     } else {
/*   91 */       this.targetLineInfo = new Line.Info[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private DirectDLI createDataLineInfo(boolean paramBoolean) {
/*   97 */     Vector<AudioFormat> vector = new Vector();
/*   98 */     AudioFormat[] arrayOfAudioFormat1 = null;
/*   99 */     AudioFormat[] arrayOfAudioFormat2 = null;
/*      */     
/*  101 */     synchronized (vector) {
/*  102 */       nGetFormats(getMixerIndex(), getDeviceID(), paramBoolean, vector);
/*      */ 
/*      */       
/*  105 */       if (vector.size() > 0) {
/*  106 */         int i = vector.size();
/*  107 */         int j = i;
/*  108 */         arrayOfAudioFormat1 = new AudioFormat[i]; byte b1;
/*  109 */         for (b1 = 0; b1 < i; b1++) {
/*  110 */           AudioFormat audioFormat = vector.elementAt(b1);
/*  111 */           arrayOfAudioFormat1[b1] = audioFormat;
/*  112 */           int k = audioFormat.getSampleSizeInBits();
/*  113 */           boolean bool1 = audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED);
/*  114 */           boolean bool2 = audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED);
/*  115 */           if (bool1 || bool2)
/*      */           {
/*  117 */             j++;
/*      */           }
/*      */         } 
/*  120 */         arrayOfAudioFormat2 = new AudioFormat[j];
/*  121 */         b1 = 0;
/*  122 */         for (byte b2 = 0; b2 < i; b2++) {
/*  123 */           AudioFormat audioFormat = arrayOfAudioFormat1[b2];
/*  124 */           arrayOfAudioFormat2[b1++] = audioFormat;
/*  125 */           int k = audioFormat.getSampleSizeInBits();
/*  126 */           boolean bool1 = audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED);
/*  127 */           boolean bool2 = audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED);
/*      */           
/*  129 */           if (k == 8) {
/*      */             
/*  131 */             if (bool1) {
/*  132 */               arrayOfAudioFormat2[b1++] = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, audioFormat
/*      */                   
/*  134 */                   .getSampleRate(), k, audioFormat.getChannels(), audioFormat
/*  135 */                   .getFrameSize(), audioFormat.getSampleRate(), audioFormat
/*  136 */                   .isBigEndian());
/*      */             }
/*  138 */             else if (bool2) {
/*  139 */               arrayOfAudioFormat2[b1++] = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat
/*      */                   
/*  141 */                   .getSampleRate(), k, audioFormat.getChannels(), audioFormat
/*  142 */                   .getFrameSize(), audioFormat.getSampleRate(), audioFormat
/*  143 */                   .isBigEndian());
/*      */             } 
/*  145 */           } else if (k > 8 && (bool1 || bool2)) {
/*      */             
/*  147 */             arrayOfAudioFormat2[b1++] = new AudioFormat(audioFormat
/*  148 */                 .getEncoding(), audioFormat
/*  149 */                 .getSampleRate(), k, audioFormat
/*  150 */                 .getChannels(), audioFormat
/*  151 */                 .getFrameSize(), audioFormat
/*  152 */                 .getSampleRate(), 
/*  153 */                 !audioFormat.isBigEndian());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  160 */     if (arrayOfAudioFormat2 != null) {
/*  161 */       return new DirectDLI(paramBoolean ? SourceDataLine.class : TargetDataLine.class, arrayOfAudioFormat2, arrayOfAudioFormat1, 32, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  166 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Line getLine(Line.Info paramInfo) throws LineUnavailableException {
/*  172 */     Line.Info info = getLineInfo(paramInfo);
/*  173 */     if (info == null) {
/*  174 */       throw new IllegalArgumentException("Line unsupported: " + paramInfo);
/*      */     }
/*  176 */     if (info instanceof DataLine.Info) {
/*      */       AudioFormat audioFormat;
/*  178 */       DataLine.Info info1 = (DataLine.Info)info;
/*      */       
/*  180 */       int i = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  185 */       AudioFormat[] arrayOfAudioFormat = null;
/*      */       
/*  187 */       if (paramInfo instanceof DataLine.Info) {
/*  188 */         arrayOfAudioFormat = ((DataLine.Info)paramInfo).getFormats();
/*  189 */         i = ((DataLine.Info)paramInfo).getMaxBufferSize();
/*      */       } 
/*      */       
/*  192 */       if (arrayOfAudioFormat == null || arrayOfAudioFormat.length == 0) {
/*      */         
/*  194 */         audioFormat = null;
/*      */       }
/*      */       else {
/*      */         
/*  198 */         audioFormat = arrayOfAudioFormat[arrayOfAudioFormat.length - 1];
/*      */ 
/*      */         
/*  201 */         if (!Toolkit.isFullySpecifiedPCMFormat(audioFormat)) {
/*  202 */           audioFormat = null;
/*      */         }
/*      */       } 
/*      */       
/*  206 */       if (info1.getLineClass().isAssignableFrom(DirectSDL.class)) {
/*  207 */         return new DirectSDL(info1, audioFormat, i, this);
/*      */       }
/*  209 */       if (info1.getLineClass().isAssignableFrom(DirectClip.class)) {
/*  210 */         return new DirectClip(info1, audioFormat, i, this);
/*      */       }
/*  212 */       if (info1.getLineClass().isAssignableFrom(DirectTDL.class)) {
/*  213 */         return new DirectTDL(info1, audioFormat, i, this);
/*      */       }
/*      */     } 
/*  216 */     throw new IllegalArgumentException("Line unsupported: " + paramInfo);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxLines(Line.Info paramInfo) {
/*  221 */     Line.Info info = getLineInfo(paramInfo);
/*      */ 
/*      */     
/*  224 */     if (info == null) {
/*  225 */       return 0;
/*      */     }
/*      */     
/*  228 */     if (info instanceof DataLine.Info)
/*      */     {
/*  230 */       return getMaxSimulLines();
/*      */     }
/*      */     
/*  233 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implOpen() throws LineUnavailableException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implClose() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implStart() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implStop() {}
/*      */ 
/*      */ 
/*      */   
/*      */   int getMixerIndex() {
/*  257 */     return ((DirectAudioDeviceProvider.DirectAudioDeviceInfo)getMixerInfo()).getIndex();
/*      */   }
/*      */   
/*      */   int getDeviceID() {
/*  261 */     return ((DirectAudioDeviceProvider.DirectAudioDeviceInfo)getMixerInfo()).getDeviceID();
/*      */   }
/*      */   
/*      */   int getMaxSimulLines() {
/*  265 */     return ((DirectAudioDeviceProvider.DirectAudioDeviceInfo)getMixerInfo()).getMaxSimulLines();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addFormat(Vector<AudioFormat> paramVector, int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4, boolean paramBoolean1, boolean paramBoolean2) {
/*  270 */     AudioFormat.Encoding encoding = null;
/*  271 */     switch (paramInt4) {
/*      */       case 0:
/*  273 */         encoding = paramBoolean1 ? AudioFormat.Encoding.PCM_SIGNED : AudioFormat.Encoding.PCM_UNSIGNED;
/*      */         break;
/*      */       case 1:
/*  276 */         encoding = AudioFormat.Encoding.ULAW;
/*  277 */         if (paramInt1 != 8) {
/*      */           
/*  279 */           paramInt1 = 8; paramInt2 = paramInt3;
/*      */         } 
/*      */         break;
/*      */       case 2:
/*  283 */         encoding = AudioFormat.Encoding.ALAW;
/*  284 */         if (paramInt1 != 8) {
/*      */           
/*  286 */           paramInt1 = 8; paramInt2 = paramInt3;
/*      */         } 
/*      */         break;
/*      */     } 
/*  290 */     if (encoding == null) {
/*      */       return;
/*      */     }
/*      */     
/*  294 */     if (paramInt2 <= 0) {
/*  295 */       if (paramInt3 > 0) {
/*  296 */         paramInt2 = (paramInt1 + 7) / 8 * paramInt3;
/*      */       } else {
/*  298 */         paramInt2 = -1;
/*      */       } 
/*      */     }
/*  301 */     paramVector.add(new AudioFormat(encoding, paramFloat, paramInt1, paramInt3, paramInt2, paramFloat, paramBoolean2));
/*      */   }
/*      */   
/*      */   protected static AudioFormat getSignOrEndianChangedFormat(AudioFormat paramAudioFormat) {
/*  305 */     boolean bool1 = paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED);
/*  306 */     boolean bool2 = paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED);
/*  307 */     if (paramAudioFormat.getSampleSizeInBits() > 8 && bool1)
/*      */     {
/*  309 */       return new AudioFormat(paramAudioFormat.getEncoding(), paramAudioFormat
/*  310 */           .getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat
/*  311 */           .getFrameSize(), paramAudioFormat.getFrameRate(), !paramAudioFormat.isBigEndian());
/*      */     }
/*  313 */     if (paramAudioFormat.getSampleSizeInBits() == 8 && (bool1 || bool2))
/*      */     {
/*  315 */       return new AudioFormat(bool1 ? AudioFormat.Encoding.PCM_UNSIGNED : AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat
/*  316 */           .getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat
/*  317 */           .getFrameSize(), paramAudioFormat.getFrameRate(), paramAudioFormat.isBigEndian());
/*      */     }
/*  319 */     return null;
/*      */   }
/*      */   private static native void nGetFormats(int paramInt1, int paramInt2, boolean paramBoolean, Vector paramVector);
/*      */   private static native long nOpen(int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, float paramFloat, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean2, boolean paramBoolean3, int paramInt7) throws LineUnavailableException;
/*      */   private static native void nStart(long paramLong, boolean paramBoolean);
/*      */   private static native void nStop(long paramLong, boolean paramBoolean);
/*      */   private static native void nClose(long paramLong, boolean paramBoolean);
/*      */   private static native int nWrite(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2);
/*      */   private static native int nRead(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3);
/*      */   private static native int nGetBufferSize(long paramLong, boolean paramBoolean);
/*      */   private static native boolean nIsStillDraining(long paramLong, boolean paramBoolean);
/*      */   
/*      */   private static native void nFlush(long paramLong, boolean paramBoolean);
/*      */   
/*      */   private static native int nAvailable(long paramLong, boolean paramBoolean);
/*      */   
/*      */   private static native long nGetBytePosition(long paramLong1, boolean paramBoolean, long paramLong2);
/*      */   
/*      */   private static native void nSetBytePosition(long paramLong1, boolean paramBoolean, long paramLong2);
/*      */   
/*      */   private static native boolean nRequiresServicing(long paramLong, boolean paramBoolean);
/*      */   
/*      */   private static native void nService(long paramLong, boolean paramBoolean);
/*      */   
/*      */   private static final class DirectDLI extends DataLine.Info { private DirectDLI(Class<?> param1Class, AudioFormat[] param1ArrayOfAudioFormat1, AudioFormat[] param1ArrayOfAudioFormat2, int param1Int1, int param1Int2) {
/*  344 */       super(param1Class, param1ArrayOfAudioFormat1, param1Int1, param1Int2);
/*  345 */       this.hardwareFormats = param1ArrayOfAudioFormat2;
/*      */     }
/*      */     final AudioFormat[] hardwareFormats;
/*      */     public boolean isFormatSupportedInHardware(AudioFormat param1AudioFormat) {
/*  349 */       if (param1AudioFormat == null) return false; 
/*  350 */       for (byte b = 0; b < this.hardwareFormats.length; b++) {
/*  351 */         if (param1AudioFormat.matches(this.hardwareFormats[b])) {
/*  352 */           return true;
/*      */         }
/*      */       } 
/*  355 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AudioFormat[] getHardwareFormats() {
/*  365 */       return this.hardwareFormats;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DirectDL
/*      */     extends AbstractDataLine
/*      */     implements EventDispatcher.LineMonitor
/*      */   {
/*      */     protected final int mixerIndex;
/*      */     
/*      */     protected final int deviceID;
/*      */     
/*      */     protected long id;
/*      */     protected int waitTime;
/*      */     protected volatile boolean flushing = false;
/*      */     protected final boolean isSource;
/*      */     protected volatile long bytePosition;
/*      */     protected volatile boolean doIO = false;
/*      */     protected volatile boolean stoppedWritten = false;
/*      */     protected volatile boolean drained = false;
/*      */     protected boolean monitoring = false;
/*  387 */     protected int softwareConversionSize = 0;
/*      */     
/*      */     protected AudioFormat hardwareFormat;
/*  390 */     private final Gain gainControl = new Gain();
/*  391 */     private final Mute muteControl = new Mute();
/*  392 */     private final Balance balanceControl = new Balance();
/*  393 */     private final Pan panControl = new Pan();
/*      */     
/*      */     private float leftGain;
/*      */     private float rightGain;
/*      */     protected volatile boolean noService = false;
/*  398 */     protected final Object lockNative = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected DirectDL(DataLine.Info param1Info, DirectAudioDevice param1DirectAudioDevice, AudioFormat param1AudioFormat, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
/*  408 */       super(param1Info, param1DirectAudioDevice, (Control[])null, param1AudioFormat, param1Int1);
/*      */       
/*  410 */       this.mixerIndex = param1Int2;
/*  411 */       this.deviceID = param1Int3;
/*  412 */       this.waitTime = 10;
/*  413 */       this.isSource = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implOpen(AudioFormat param1AudioFormat, int param1Int) throws LineUnavailableException {
/*  426 */       Toolkit.isFullySpecifiedAudioFormat(param1AudioFormat);
/*      */ 
/*      */       
/*  429 */       if (!this.isSource) {
/*  430 */         JSSecurityManager.checkRecordPermission();
/*      */       }
/*  432 */       byte b = 0;
/*  433 */       if (param1AudioFormat.getEncoding().equals(AudioFormat.Encoding.ULAW)) {
/*  434 */         b = 1;
/*      */       }
/*  436 */       else if (param1AudioFormat.getEncoding().equals(AudioFormat.Encoding.ALAW)) {
/*  437 */         b = 2;
/*      */       } 
/*      */       
/*  440 */       if (param1Int <= -1) {
/*  441 */         param1Int = (int)Toolkit.millis2bytes(param1AudioFormat, 500L);
/*      */       }
/*      */       
/*  444 */       DirectAudioDevice.DirectDLI directDLI = null;
/*  445 */       if (this.info instanceof DirectAudioDevice.DirectDLI) {
/*  446 */         directDLI = (DirectAudioDevice.DirectDLI)this.info;
/*      */       }
/*      */ 
/*      */       
/*  450 */       if (this.isSource) {
/*  451 */         if (!param1AudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && 
/*  452 */           !param1AudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*      */           
/*  454 */           this.controls = new Control[0];
/*      */         }
/*  456 */         else if (param1AudioFormat.getChannels() > 2 || param1AudioFormat
/*  457 */           .getSampleSizeInBits() > 16) {
/*      */           
/*  459 */           this.controls = new Control[0];
/*      */         } else {
/*  461 */           if (param1AudioFormat.getChannels() == 1) {
/*  462 */             this.controls = new Control[2];
/*      */           } else {
/*  464 */             this.controls = new Control[4];
/*  465 */             this.controls[2] = this.balanceControl;
/*      */ 
/*      */ 
/*      */             
/*  469 */             this.controls[3] = this.panControl;
/*      */           } 
/*  471 */           this.controls[0] = this.gainControl;
/*  472 */           this.controls[1] = this.muteControl;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  477 */       this.hardwareFormat = param1AudioFormat;
/*      */ 
/*      */       
/*  480 */       this.softwareConversionSize = 0;
/*  481 */       if (directDLI != null && !directDLI.isFormatSupportedInHardware(param1AudioFormat)) {
/*  482 */         AudioFormat audioFormat = DirectAudioDevice.getSignOrEndianChangedFormat(param1AudioFormat);
/*  483 */         if (directDLI.isFormatSupportedInHardware(audioFormat)) {
/*      */           
/*  485 */           this.hardwareFormat = audioFormat;
/*      */           
/*  487 */           this.softwareConversionSize = param1AudioFormat.getFrameSize() / param1AudioFormat.getChannels();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  498 */       param1Int = param1Int / param1AudioFormat.getFrameSize() * param1AudioFormat.getFrameSize();
/*      */       
/*  500 */       this.id = DirectAudioDevice.nOpen(this.mixerIndex, this.deviceID, this.isSource, b, this.hardwareFormat
/*      */           
/*  502 */           .getSampleRate(), this.hardwareFormat
/*  503 */           .getSampleSizeInBits(), this.hardwareFormat
/*  504 */           .getFrameSize(), this.hardwareFormat
/*  505 */           .getChannels(), this.hardwareFormat
/*  506 */           .getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED), this.hardwareFormat
/*      */           
/*  508 */           .isBigEndian(), param1Int);
/*      */ 
/*      */       
/*  511 */       if (this.id == 0L)
/*      */       {
/*  513 */         throw new LineUnavailableException("line with format " + param1AudioFormat + " not supported.");
/*      */       }
/*      */ 
/*      */       
/*  517 */       this.bufferSize = DirectAudioDevice.nGetBufferSize(this.id, this.isSource);
/*  518 */       if (this.bufferSize < 1)
/*      */       {
/*  520 */         this.bufferSize = param1Int;
/*      */       }
/*  522 */       this.format = param1AudioFormat;
/*      */       
/*  524 */       this.waitTime = (int)Toolkit.bytes2millis(param1AudioFormat, this.bufferSize) / 4;
/*  525 */       if (this.waitTime < 10) {
/*  526 */         this.waitTime = 1;
/*      */       }
/*  528 */       else if (this.waitTime > 1000) {
/*      */ 
/*      */         
/*  531 */         this.waitTime = 1000;
/*      */       } 
/*  533 */       this.bytePosition = 0L;
/*  534 */       this.stoppedWritten = false;
/*  535 */       this.doIO = false;
/*  536 */       calcVolume();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implStart() {
/*  546 */       if (!this.isSource) {
/*  547 */         JSSecurityManager.checkRecordPermission();
/*      */       }
/*      */       
/*  550 */       synchronized (this.lockNative) {
/*      */         
/*  552 */         DirectAudioDevice.nStart(this.id, this.isSource);
/*      */       } 
/*      */       
/*  555 */       this.monitoring = requiresServicing();
/*  556 */       if (this.monitoring) {
/*  557 */         getEventDispatcher().addLineMonitor(this);
/*      */       }
/*      */       
/*  560 */       synchronized (this.lock) {
/*  561 */         this.doIO = true;
/*      */ 
/*      */ 
/*      */         
/*  565 */         if (this.isSource && this.stoppedWritten) {
/*  566 */           setStarted(true);
/*  567 */           setActive(true);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implStop() {
/*  578 */       if (!this.isSource) {
/*  579 */         JSSecurityManager.checkRecordPermission();
/*      */       }
/*      */       
/*  582 */       if (this.monitoring) {
/*  583 */         getEventDispatcher().removeLineMonitor(this);
/*  584 */         this.monitoring = false;
/*      */       } 
/*  586 */       synchronized (this.lockNative) {
/*  587 */         DirectAudioDevice.nStop(this.id, this.isSource);
/*      */       } 
/*      */       
/*  590 */       synchronized (this.lock) {
/*      */ 
/*      */ 
/*      */         
/*  594 */         this.doIO = false;
/*  595 */         setActive(false);
/*  596 */         setStarted(false);
/*  597 */         this.lock.notifyAll();
/*      */       } 
/*  599 */       this.stoppedWritten = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implClose() {
/*  608 */       if (!this.isSource) {
/*  609 */         JSSecurityManager.checkRecordPermission();
/*      */       }
/*      */ 
/*      */       
/*  613 */       if (this.monitoring) {
/*  614 */         getEventDispatcher().removeLineMonitor(this);
/*  615 */         this.monitoring = false;
/*      */       } 
/*      */       
/*  618 */       this.doIO = false;
/*  619 */       long l = this.id;
/*  620 */       this.id = 0L;
/*  621 */       synchronized (this.lockNative) {
/*  622 */         DirectAudioDevice.nClose(l, this.isSource);
/*      */       } 
/*  624 */       this.bytePosition = 0L;
/*  625 */       this.softwareConversionSize = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int available() {
/*      */       int i;
/*  632 */       if (this.id == 0L) {
/*  633 */         return 0;
/*      */       }
/*      */       
/*  636 */       synchronized (this.lockNative) {
/*  637 */         i = DirectAudioDevice.nAvailable(this.id, this.isSource);
/*      */       } 
/*  639 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public void drain() {
/*  644 */       this.noService = true;
/*      */ 
/*      */ 
/*      */       
/*  648 */       byte b = 0;
/*  649 */       long l = getLongFramePosition();
/*  650 */       int i = 0;
/*  651 */       while (!this.drained) {
/*  652 */         synchronized (this.lockNative) {
/*  653 */           if (this.id == 0L || !this.doIO || !DirectAudioDevice.nIsStillDraining(this.id, this.isSource)) {
/*      */             break;
/*      */           }
/*      */         } 
/*  657 */         if (b % 5 == 4) {
/*  658 */           long l1 = getLongFramePosition();
/*  659 */           i |= (l1 != l) ? 1 : 0;
/*  660 */           if (b % 50 > 45) {
/*      */ 
/*      */             
/*  663 */             if (i == 0) {
/*      */               break;
/*      */             }
/*      */             
/*  667 */             i = 0;
/*  668 */             l = l1;
/*      */           } 
/*      */         } 
/*  671 */         b++;
/*  672 */         synchronized (this.lock) {
/*      */           try {
/*  674 */             this.lock.wait(10L);
/*  675 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */       } 
/*      */       
/*  679 */       if (this.doIO && this.id != 0L) {
/*  680 */         this.drained = true;
/*      */       }
/*  682 */       this.noService = false;
/*      */     }
/*      */     
/*      */     public void flush() {
/*  686 */       if (this.id != 0L) {
/*      */         
/*  688 */         this.flushing = true;
/*  689 */         synchronized (this.lock) {
/*  690 */           this.lock.notifyAll();
/*      */         } 
/*  692 */         synchronized (this.lockNative) {
/*  693 */           if (this.id != 0L)
/*      */           {
/*  695 */             DirectAudioDevice.nFlush(this.id, this.isSource);
/*      */           }
/*      */         } 
/*  698 */         this.drained = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongFramePosition() {
/*      */       long l;
/*  705 */       synchronized (this.lockNative) {
/*  706 */         l = DirectAudioDevice.nGetBytePosition(this.id, this.isSource, this.bytePosition);
/*      */       } 
/*      */       
/*  709 */       if (l < 0L)
/*      */       {
/*      */         
/*  712 */         l = 0L;
/*      */       }
/*  714 */       return l / getFormat().getFrameSize();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/*  724 */       this.flushing = false;
/*  725 */       if (param1Int2 == 0) {
/*  726 */         return 0;
/*      */       }
/*  728 */       if (param1Int2 < 0) {
/*  729 */         throw new IllegalArgumentException("illegal len: " + param1Int2);
/*      */       }
/*  731 */       if (param1Int2 % getFormat().getFrameSize() != 0) {
/*  732 */         throw new IllegalArgumentException("illegal request to write non-integral number of frames (" + param1Int2 + " bytes, frameSize = " + 
/*      */ 
/*      */             
/*  735 */             getFormat().getFrameSize() + " bytes)");
/*      */       }
/*  737 */       if (param1Int1 < 0) {
/*  738 */         throw new ArrayIndexOutOfBoundsException(param1Int1);
/*      */       }
/*  740 */       if (param1Int1 + param1Int2 > param1ArrayOfbyte.length) {
/*  741 */         throw new ArrayIndexOutOfBoundsException(param1ArrayOfbyte.length);
/*      */       }
/*  743 */       synchronized (this.lock) {
/*  744 */         if (!isActive() && this.doIO) {
/*      */ 
/*      */ 
/*      */           
/*  748 */           setActive(true);
/*  749 */           setStarted(true);
/*      */         } 
/*      */       } 
/*  752 */       int i = 0;
/*  753 */       while (!this.flushing) {
/*      */         int j;
/*  755 */         synchronized (this.lockNative) {
/*  756 */           j = DirectAudioDevice.nWrite(this.id, param1ArrayOfbyte, param1Int1, param1Int2, this.softwareConversionSize, this.leftGain, this.rightGain);
/*      */ 
/*      */           
/*  759 */           if (j < 0) {
/*      */             break;
/*      */           }
/*      */           
/*  763 */           this.bytePosition += j;
/*  764 */           if (j > 0) {
/*  765 */             this.drained = false;
/*      */           }
/*      */         } 
/*  768 */         param1Int2 -= j;
/*  769 */         i += j;
/*  770 */         if (this.doIO && param1Int2 > 0) {
/*  771 */           param1Int1 += j;
/*  772 */           synchronized (this.lock) {
/*      */             try {
/*  774 */               this.lock.wait(this.waitTime);
/*  775 */             } catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  781 */       if (i > 0 && !this.doIO) {
/*  782 */         this.stoppedWritten = true;
/*      */       }
/*  784 */       return i;
/*      */     }
/*      */     
/*      */     protected boolean requiresServicing() {
/*  788 */       return DirectAudioDevice.nRequiresServicing(this.id, this.isSource);
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkLine() {
/*  793 */       synchronized (this.lockNative) {
/*  794 */         if (this.monitoring && this.doIO && this.id != 0L && !this.flushing && !this.noService)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  799 */           DirectAudioDevice.nService(this.id, this.isSource);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private void calcVolume() {
/*  805 */       if (getFormat() == null) {
/*      */         return;
/*      */       }
/*  808 */       if (this.muteControl.getValue()) {
/*  809 */         this.leftGain = 0.0F;
/*  810 */         this.rightGain = 0.0F;
/*      */         return;
/*      */       } 
/*  813 */       float f = this.gainControl.getLinearGain();
/*  814 */       if (getFormat().getChannels() == 1) {
/*      */         
/*  816 */         this.leftGain = f;
/*  817 */         this.rightGain = f;
/*      */       } else {
/*      */         
/*  820 */         float f1 = this.balanceControl.getValue();
/*  821 */         if (f1 < 0.0F) {
/*      */           
/*  823 */           this.leftGain = f;
/*  824 */           this.rightGain = f * (f1 + 1.0F);
/*      */         } else {
/*  826 */           this.leftGain = f * (1.0F - f1);
/*  827 */           this.rightGain = f;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected final class Gain
/*      */       extends FloatControl
/*      */     {
/*  837 */       private float linearGain = 1.0F;
/*      */ 
/*      */       
/*      */       private Gain() {
/*  841 */         super(FloatControl.Type.MASTER_GAIN, 
/*  842 */             Toolkit.linearToDB(0.0F), 
/*  843 */             Toolkit.linearToDB(2.0F), 
/*  844 */             Math.abs(Toolkit.linearToDB(1.0F) - Toolkit.linearToDB(0.0F)) / 128.0F, -1, 0.0F, "dB", "Minimum", "", "Maximum");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setValue(float param2Float) {
/*  855 */         float f = Toolkit.dBToLinear(param2Float);
/*  856 */         super.setValue(Toolkit.linearToDB(f));
/*      */         
/*  858 */         this.linearGain = f;
/*  859 */         DirectAudioDevice.DirectDL.this.calcVolume();
/*      */       }
/*      */       
/*      */       float getLinearGain() {
/*  863 */         return this.linearGain;
/*      */       }
/*      */     }
/*      */     
/*      */     private final class Mute
/*      */       extends BooleanControl
/*      */     {
/*      */       private Mute() {
/*  871 */         super(BooleanControl.Type.MUTE, false, "True", "False");
/*      */       }
/*      */       
/*      */       public void setValue(boolean param2Boolean) {
/*  875 */         super.setValue(param2Boolean);
/*  876 */         DirectAudioDevice.DirectDL.this.calcVolume();
/*      */       }
/*      */     }
/*      */     
/*      */     private final class Balance
/*      */       extends FloatControl {
/*      */       private Balance() {
/*  883 */         super(FloatControl.Type.BALANCE, -1.0F, 1.0F, 0.0078125F, -1, 0.0F, "", "Left", "Center", "Right");
/*      */       }
/*      */ 
/*      */       
/*      */       public void setValue(float param2Float) {
/*  888 */         setValueImpl(param2Float);
/*  889 */         DirectAudioDevice.DirectDL.this.panControl.setValueImpl(param2Float);
/*  890 */         DirectAudioDevice.DirectDL.this.calcVolume();
/*      */       }
/*      */       
/*      */       void setValueImpl(float param2Float) {
/*  894 */         super.setValue(param2Float);
/*      */       }
/*      */     }
/*      */     
/*      */     private final class Pan
/*      */       extends FloatControl
/*      */     {
/*      */       private Pan() {
/*  902 */         super(FloatControl.Type.PAN, -1.0F, 1.0F, 0.0078125F, -1, 0.0F, "", "Left", "Center", "Right");
/*      */       }
/*      */ 
/*      */       
/*      */       public void setValue(float param2Float) {
/*  907 */         setValueImpl(param2Float);
/*  908 */         DirectAudioDevice.DirectDL.this.balanceControl.setValueImpl(param2Float);
/*  909 */         DirectAudioDevice.DirectDL.this.calcVolume();
/*      */       }
/*      */       void setValueImpl(float param2Float) {
/*  912 */         super.setValue(param2Float);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DirectSDL
/*      */     extends DirectDL
/*      */     implements SourceDataLine
/*      */   {
/*      */     private DirectSDL(DataLine.Info param1Info, AudioFormat param1AudioFormat, int param1Int, DirectAudioDevice param1DirectAudioDevice) {
/*  932 */       super(param1Info, param1DirectAudioDevice, param1AudioFormat, param1Int, param1DirectAudioDevice.getMixerIndex(), param1DirectAudioDevice.getDeviceID(), true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DirectTDL
/*      */     extends DirectDL
/*      */     implements TargetDataLine
/*      */   {
/*      */     private DirectTDL(DataLine.Info param1Info, AudioFormat param1AudioFormat, int param1Int, DirectAudioDevice param1DirectAudioDevice) {
/*  949 */       super(param1Info, param1DirectAudioDevice, param1AudioFormat, param1Int, param1DirectAudioDevice.getMixerIndex(), param1DirectAudioDevice.getDeviceID(), false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/*  956 */       this.flushing = false;
/*  957 */       if (param1Int2 == 0) {
/*  958 */         return 0;
/*      */       }
/*  960 */       if (param1Int2 < 0) {
/*  961 */         throw new IllegalArgumentException("illegal len: " + param1Int2);
/*      */       }
/*  963 */       if (param1Int2 % getFormat().getFrameSize() != 0) {
/*  964 */         throw new IllegalArgumentException("illegal request to read non-integral number of frames (" + param1Int2 + " bytes, frameSize = " + 
/*      */ 
/*      */             
/*  967 */             getFormat().getFrameSize() + " bytes)");
/*      */       }
/*  969 */       if (param1Int1 < 0) {
/*  970 */         throw new ArrayIndexOutOfBoundsException(param1Int1);
/*      */       }
/*  972 */       if (param1Int1 + param1Int2 > param1ArrayOfbyte.length) {
/*  973 */         throw new ArrayIndexOutOfBoundsException(param1ArrayOfbyte.length);
/*      */       }
/*  975 */       synchronized (this.lock) {
/*  976 */         if (!isActive() && this.doIO) {
/*      */ 
/*      */ 
/*      */           
/*  980 */           setActive(true);
/*  981 */           setStarted(true);
/*      */         } 
/*      */       } 
/*  984 */       int i = 0;
/*  985 */       while (this.doIO && !this.flushing) {
/*      */         int j;
/*  987 */         synchronized (this.lockNative) {
/*  988 */           j = DirectAudioDevice.nRead(this.id, param1ArrayOfbyte, param1Int1, param1Int2, this.softwareConversionSize);
/*  989 */           if (j < 0) {
/*      */             break;
/*      */           }
/*      */           
/*  993 */           this.bytePosition += j;
/*  994 */           if (j > 0) {
/*  995 */             this.drained = false;
/*      */           }
/*      */         } 
/*  998 */         param1Int2 -= j;
/*  999 */         i += j;
/* 1000 */         if (param1Int2 > 0) {
/* 1001 */           param1Int1 += j;
/* 1002 */           synchronized (this.lock) {
/*      */             try {
/* 1004 */               this.lock.wait(this.waitTime);
/* 1005 */             } catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1011 */       if (this.flushing) {
/* 1012 */         i = 0;
/*      */       }
/* 1014 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DirectClip
/*      */     extends DirectDL
/*      */     implements Clip, Runnable, AutoClosingClip
/*      */   {
/*      */     private volatile Thread thread;
/*      */ 
/*      */     
/* 1027 */     private volatile byte[] audioData = null;
/*      */     
/*      */     private volatile int frameSize;
/*      */     
/*      */     private volatile int m_lengthInFrames;
/*      */     
/*      */     private volatile int loopCount;
/*      */     
/*      */     private volatile int clipBytePosition;
/*      */     
/*      */     private volatile int newFramePosition;
/*      */     
/*      */     private volatile int loopStartFrame;
/*      */     private volatile int loopEndFrame;
/*      */     private boolean autoclosing = false;
/*      */     
/*      */     private DirectClip(DataLine.Info param1Info, AudioFormat param1AudioFormat, int param1Int, DirectAudioDevice param1DirectAudioDevice) {
/* 1044 */       super(param1Info, param1DirectAudioDevice, param1AudioFormat, param1Int, param1DirectAudioDevice.getMixerIndex(), param1DirectAudioDevice.getDeviceID(), true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void open(AudioFormat param1AudioFormat, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws LineUnavailableException {
/* 1054 */       Toolkit.isFullySpecifiedAudioFormat(param1AudioFormat);
/*      */       
/* 1056 */       byte[] arrayOfByte = new byte[param1Int2];
/* 1057 */       System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte, 0, param1Int2);
/* 1058 */       open(param1AudioFormat, arrayOfByte, param1Int2 / param1AudioFormat.getFrameSize());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void open(AudioFormat param1AudioFormat, byte[] param1ArrayOfbyte, int param1Int) throws LineUnavailableException {
/* 1066 */       Toolkit.isFullySpecifiedAudioFormat(param1AudioFormat);
/*      */       
/* 1068 */       synchronized (this.mixer) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1073 */         if (isOpen()) {
/* 1074 */           throw new IllegalStateException("Clip is already open with format " + getFormat() + " and frame lengh of " + 
/* 1075 */               getFrameLength());
/*      */         }
/*      */         
/* 1078 */         this.audioData = param1ArrayOfbyte;
/* 1079 */         this.frameSize = param1AudioFormat.getFrameSize();
/* 1080 */         this.m_lengthInFrames = param1Int;
/*      */         
/* 1082 */         this.bytePosition = 0L;
/* 1083 */         this.clipBytePosition = 0;
/* 1084 */         this.newFramePosition = -1;
/* 1085 */         this.loopStartFrame = 0;
/* 1086 */         this.loopEndFrame = param1Int - 1;
/* 1087 */         this.loopCount = 0;
/*      */ 
/*      */         
/*      */         try {
/* 1091 */           open(param1AudioFormat, (int)Toolkit.millis2bytes(param1AudioFormat, 1000L));
/* 1092 */         } catch (LineUnavailableException lineUnavailableException) {
/* 1093 */           this.audioData = null;
/* 1094 */           throw lineUnavailableException;
/* 1095 */         } catch (IllegalArgumentException illegalArgumentException) {
/* 1096 */           this.audioData = null;
/* 1097 */           throw illegalArgumentException;
/*      */         } 
/*      */ 
/*      */         
/* 1101 */         byte b = 6;
/*      */         
/* 1103 */         this.thread = JSSecurityManager.createThread(this, "Direct Clip", true, b, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1111 */         this.thread.start();
/*      */       } 
/*      */       
/* 1114 */       if (isAutoClosing()) {
/* 1115 */         getEventDispatcher().autoClosingClipOpened(this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void open(AudioInputStream param1AudioInputStream) throws LineUnavailableException, IOException {
/* 1124 */       Toolkit.isFullySpecifiedAudioFormat(this.format);
/*      */       
/* 1126 */       synchronized (this.mixer) {
/*      */         
/* 1128 */         byte[] arrayOfByte = null;
/*      */         
/* 1130 */         if (isOpen()) {
/* 1131 */           throw new IllegalStateException("Clip is already open with format " + getFormat() + " and frame lengh of " + 
/* 1132 */               getFrameLength());
/*      */         }
/* 1134 */         int i = (int)param1AudioInputStream.getFrameLength();
/*      */ 
/*      */         
/* 1137 */         int j = 0;
/* 1138 */         if (i != -1) {
/*      */           
/* 1140 */           int k = i * param1AudioInputStream.getFormat().getFrameSize();
/* 1141 */           arrayOfByte = new byte[k];
/*      */           
/* 1143 */           int m = k;
/* 1144 */           int n = 0;
/* 1145 */           while (m > 0 && n) {
/* 1146 */             n = param1AudioInputStream.read(arrayOfByte, j, m);
/* 1147 */             if (n > 0) {
/* 1148 */               j += n;
/* 1149 */               m -= n; continue;
/*      */             } 
/* 1151 */             if (n == 0) {
/* 1152 */               Thread.yield();
/*      */             
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1160 */           char c = '䀀';
/* 1161 */           DirectAudioDevice.DirectBAOS directBAOS = new DirectAudioDevice.DirectBAOS();
/* 1162 */           byte[] arrayOfByte1 = new byte[c];
/* 1163 */           int k = 0;
/* 1164 */           while (k) {
/* 1165 */             k = param1AudioInputStream.read(arrayOfByte1, 0, arrayOfByte1.length);
/* 1166 */             if (k > 0) {
/* 1167 */               directBAOS.write(arrayOfByte1, 0, k);
/* 1168 */               j += k; continue;
/*      */             } 
/* 1170 */             if (k == 0) {
/* 1171 */               Thread.yield();
/*      */             }
/*      */           } 
/* 1174 */           arrayOfByte = directBAOS.getInternalBuffer();
/*      */         } 
/* 1176 */         i = j / param1AudioInputStream.getFormat().getFrameSize();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1181 */         open(param1AudioInputStream.getFormat(), arrayOfByte, i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getFrameLength() {
/* 1189 */       return this.m_lengthInFrames;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getMicrosecondLength() {
/* 1194 */       return Toolkit.frames2micros(getFormat(), getFrameLength());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setFramePosition(int param1Int) {
/* 1201 */       if (param1Int < 0) {
/* 1202 */         param1Int = 0;
/*      */       }
/* 1204 */       else if (param1Int >= getFrameLength()) {
/* 1205 */         param1Int = getFrameLength();
/*      */       } 
/* 1207 */       if (this.doIO) {
/* 1208 */         this.newFramePosition = param1Int;
/*      */       } else {
/* 1210 */         this.clipBytePosition = param1Int * this.frameSize;
/* 1211 */         this.newFramePosition = -1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1217 */       this.bytePosition = (param1Int * this.frameSize);
/*      */ 
/*      */       
/* 1220 */       flush();
/*      */ 
/*      */ 
/*      */       
/* 1224 */       synchronized (this.lockNative) {
/* 1225 */         DirectAudioDevice.nSetBytePosition(this.id, this.isSource, (param1Int * this.frameSize));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getLongFramePosition() {
/* 1249 */       return super.getLongFramePosition();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void setMicrosecondPosition(long param1Long) {
/* 1256 */       long l = Toolkit.micros2frames(getFormat(), param1Long);
/* 1257 */       setFramePosition((int)l);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLoopPoints(int param1Int1, int param1Int2) {
/* 1265 */       if (param1Int1 < 0 || param1Int1 >= getFrameLength()) {
/* 1266 */         throw new IllegalArgumentException("illegal value for start: " + param1Int1);
/*      */       }
/* 1268 */       if (param1Int2 >= getFrameLength()) {
/* 1269 */         throw new IllegalArgumentException("illegal value for end: " + param1Int2);
/*      */       }
/*      */       
/* 1272 */       if (param1Int2 == -1) {
/* 1273 */         param1Int2 = getFrameLength() - 1;
/* 1274 */         if (param1Int2 < 0) {
/* 1275 */           param1Int2 = 0;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1280 */       if (param1Int2 < param1Int1) {
/* 1281 */         throw new IllegalArgumentException("End position " + param1Int2 + "  preceeds start position " + param1Int1);
/*      */       }
/*      */ 
/*      */       
/* 1285 */       this.loopStartFrame = param1Int1;
/* 1286 */       this.loopEndFrame = param1Int2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void loop(int param1Int) {
/* 1296 */       this.loopCount = param1Int;
/* 1297 */       start();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implOpen(AudioFormat param1AudioFormat, int param1Int) throws LineUnavailableException {
/* 1307 */       if (this.audioData == null) {
/* 1308 */         throw new IllegalArgumentException("illegal call to open() in interface Clip");
/*      */       }
/* 1310 */       super.implOpen(param1AudioFormat, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implClose() {
/* 1317 */       Thread thread = this.thread;
/* 1318 */       this.thread = null;
/* 1319 */       this.doIO = false;
/* 1320 */       if (thread != null) {
/*      */         
/* 1322 */         synchronized (this.lock) {
/* 1323 */           this.lock.notifyAll();
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/* 1328 */           thread.join(2000L);
/* 1329 */         } catch (InterruptedException interruptedException) {}
/*      */       } 
/* 1331 */       super.implClose();
/*      */       
/* 1333 */       this.audioData = null;
/* 1334 */       this.newFramePosition = -1;
/*      */ 
/*      */       
/* 1337 */       getEventDispatcher().autoClosingClipClosed(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implStart() {
/* 1345 */       super.implStart();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void implStop() {
/* 1352 */       super.implStop();
/*      */ 
/*      */       
/* 1355 */       this.loopCount = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/* 1364 */       Thread thread = Thread.currentThread();
/* 1365 */       while (this.thread == thread) {
/*      */ 
/*      */ 
/*      */         
/* 1369 */         synchronized (this.lock) {
/* 1370 */           if (!this.doIO) {
/*      */             
/* 1372 */             try { this.lock.wait(); }
/* 1373 */             catch (InterruptedException interruptedException) {  }
/*      */             finally
/* 1375 */             { if (this.thread != thread) {
/*      */                 break;
/*      */               } }
/*      */           
/*      */           }
/*      */         } 
/* 1381 */         while (this.doIO) {
/* 1382 */           if (this.newFramePosition >= 0) {
/* 1383 */             this.clipBytePosition = this.newFramePosition * this.frameSize;
/* 1384 */             this.newFramePosition = -1;
/*      */           } 
/* 1386 */           int i = getFrameLength() - 1;
/* 1387 */           if (this.loopCount > 0 || this.loopCount == -1) {
/* 1388 */             i = this.loopEndFrame;
/*      */           }
/* 1390 */           long l = (this.clipBytePosition / this.frameSize);
/* 1391 */           int j = (int)(i - l + 1L);
/* 1392 */           int k = j * this.frameSize;
/* 1393 */           if (k > getBufferSize()) {
/* 1394 */             k = Toolkit.align(getBufferSize(), this.frameSize);
/*      */           }
/* 1396 */           int m = write(this.audioData, this.clipBytePosition, k);
/* 1397 */           this.clipBytePosition += m;
/*      */           
/* 1399 */           if (this.doIO && this.newFramePosition < 0 && m >= 0) {
/* 1400 */             l = (this.clipBytePosition / this.frameSize);
/*      */ 
/*      */ 
/*      */             
/* 1404 */             if (l > i) {
/*      */               
/* 1406 */               if (this.loopCount > 0 || this.loopCount == -1) {
/* 1407 */                 if (this.loopCount != -1) {
/* 1408 */                   this.loopCount--;
/*      */                 }
/* 1410 */                 this.newFramePosition = this.loopStartFrame;
/*      */ 
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */               
/* 1416 */               drain();
/* 1417 */               stop();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAutoClosing() {
/* 1434 */       return this.autoclosing;
/*      */     }
/*      */     
/*      */     public void setAutoClosing(boolean param1Boolean) {
/* 1438 */       if (param1Boolean != this.autoclosing) {
/* 1439 */         if (isOpen()) {
/* 1440 */           if (param1Boolean) {
/* 1441 */             getEventDispatcher().autoClosingClipOpened(this);
/*      */           } else {
/* 1443 */             getEventDispatcher().autoClosingClipClosed(this);
/*      */           } 
/*      */         }
/* 1446 */         this.autoclosing = param1Boolean;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean requiresServicing() {
/* 1452 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DirectBAOS
/*      */     extends ByteArrayOutputStream
/*      */   {
/*      */     public byte[] getInternalBuffer() {
/* 1467 */       return this.buf;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DirectAudioDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */