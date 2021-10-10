/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.sound.sampled.BooleanControl;
/*     */ import javax.sound.sampled.CompoundControl;
/*     */ import javax.sound.sampled.Control;
/*     */ import javax.sound.sampled.FloatControl;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.Port;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PortMixer
/*     */   extends AbstractMixer
/*     */ {
/*     */   private static final int SRC_UNKNOWN = 1;
/*     */   private static final int SRC_MICROPHONE = 2;
/*     */   private static final int SRC_LINE_IN = 3;
/*     */   private static final int SRC_COMPACT_DISC = 4;
/*     */   private static final int SRC_MASK = 255;
/*     */   private static final int DST_UNKNOWN = 256;
/*     */   private static final int DST_SPEAKER = 512;
/*     */   private static final int DST_HEADPHONE = 768;
/*     */   private static final int DST_LINE_OUT = 1024;
/*     */   private static final int DST_MASK = 65280;
/*     */   private Port.Info[] portInfos;
/*     */   private PortMixerPort[] ports;
/*  65 */   private long id = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   PortMixer(PortMixerProvider.PortMixerInfo paramPortMixerInfo) {
/*  70 */     super(paramPortMixerInfo, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     int i = 0;
/*  78 */     int j = 0;
/*  79 */     int k = 0;
/*     */     
/*     */     try {
/*     */       try {
/*  83 */         this.id = nOpen(getMixerIndex());
/*  84 */         if (this.id != 0L) {
/*  85 */           i = nGetPortCount(this.id);
/*  86 */           if (i < 0)
/*     */           {
/*  88 */             i = 0;
/*     */           }
/*     */         } 
/*  91 */       } catch (Exception exception) {}
/*     */       
/*  93 */       this.portInfos = new Port.Info[i];
/*     */       
/*  95 */       for (byte b1 = 0; b1 < i; b1++) {
/*  96 */         int m = nGetPortType(this.id, b1);
/*  97 */         j += ((m & 0xFF) != 0) ? 1 : 0;
/*  98 */         k += ((m & 0xFF00) != 0) ? 1 : 0;
/*  99 */         this.portInfos[b1] = getPortInfo(b1, m);
/*     */       } 
/*     */     } finally {
/* 102 */       if (this.id != 0L) {
/* 103 */         nClose(this.id);
/*     */       }
/* 105 */       this.id = 0L;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     this.sourceLineInfo = (Line.Info[])new Port.Info[j];
/* 110 */     this.targetLineInfo = (Line.Info[])new Port.Info[k];
/*     */     
/* 112 */     j = 0; k = 0;
/* 113 */     for (byte b = 0; b < i; b++) {
/* 114 */       if (this.portInfos[b].isSource()) {
/* 115 */         this.sourceLineInfo[j++] = this.portInfos[b];
/*     */       } else {
/* 117 */         this.targetLineInfo[k++] = this.portInfos[b];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line getLine(Line.Info paramInfo) throws LineUnavailableException {
/* 128 */     Line.Info info = getLineInfo(paramInfo);
/*     */     
/* 130 */     if (info != null && info instanceof Port.Info) {
/* 131 */       for (byte b = 0; b < this.portInfos.length; b++) {
/* 132 */         if (info.equals(this.portInfos[b])) {
/* 133 */           return getPort(b);
/*     */         }
/*     */       } 
/*     */     }
/* 137 */     throw new IllegalArgumentException("Line unsupported: " + paramInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxLines(Line.Info paramInfo) {
/* 142 */     Line.Info info = getLineInfo(paramInfo);
/*     */ 
/*     */     
/* 145 */     if (info == null) {
/* 146 */       return 0;
/*     */     }
/*     */     
/* 149 */     if (info instanceof Port.Info)
/*     */     {
/* 151 */       return 1;
/*     */     }
/* 153 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void implOpen() throws LineUnavailableException {
/* 161 */     this.id = nOpen(getMixerIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void implClose() {
/* 170 */     long l = this.id;
/* 171 */     this.id = 0L;
/* 172 */     nClose(l);
/* 173 */     if (this.ports != null) {
/* 174 */       for (byte b = 0; b < this.ports.length; b++) {
/* 175 */         if (this.ports[b] != null) {
/* 176 */           this.ports[b].disposeControls();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implStart() {}
/*     */ 
/*     */   
/*     */   protected void implStop() {}
/*     */ 
/*     */   
/*     */   private Port.Info getPortInfo(int paramInt1, int paramInt2) {
/* 190 */     switch (paramInt2) { case 1:
/* 191 */         return new PortInfo(nGetPortName(getID(), paramInt1), true);
/* 192 */       case 2: return Port.Info.MICROPHONE;
/* 193 */       case 3: return Port.Info.LINE_IN;
/* 194 */       case 4: return Port.Info.COMPACT_DISC;
/*     */       case 256:
/* 196 */         return new PortInfo(nGetPortName(getID(), paramInt1), false);
/* 197 */       case 512: return Port.Info.SPEAKER;
/* 198 */       case 768: return Port.Info.HEADPHONE;
/* 199 */       case 1024: return Port.Info.LINE_OUT; }
/*     */ 
/*     */ 
/*     */     
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   int getMixerIndex() {
/* 207 */     return ((PortMixerProvider.PortMixerInfo)getMixerInfo()).getIndex();
/*     */   }
/*     */   
/*     */   Port getPort(int paramInt) {
/* 211 */     if (this.ports == null) {
/* 212 */       this.ports = new PortMixerPort[this.portInfos.length];
/*     */     }
/* 214 */     if (this.ports[paramInt] == null) {
/* 215 */       this.ports[paramInt] = new PortMixerPort(this.portInfos[paramInt], this, paramInt);
/* 216 */       return this.ports[paramInt];
/*     */     } 
/*     */     
/* 219 */     return this.ports[paramInt];
/*     */   } private static native long nOpen(int paramInt) throws LineUnavailableException; private static native void nClose(long paramLong); private static native int nGetPortCount(long paramLong); private static native int nGetPortType(long paramLong, int paramInt);
/*     */   private static native String nGetPortName(long paramLong, int paramInt);
/*     */   long getID() {
/* 223 */     return this.id;
/*     */   }
/*     */   
/*     */   private static native void nGetControls(long paramLong, int paramInt, Vector paramVector);
/*     */   
/*     */   private static native void nControlSetIntValue(long paramLong, int paramInt);
/*     */   
/*     */   private static native int nControlGetIntValue(long paramLong);
/*     */   
/*     */   private static native void nControlSetFloatValue(long paramLong, float paramFloat);
/*     */   
/*     */   private static native float nControlGetFloatValue(long paramLong);
/*     */   
/*     */   private static final class PortMixerPort
/*     */     extends AbstractLine
/*     */     implements Port
/*     */   {
/*     */     private PortMixerPort(Port.Info param1Info, PortMixer param1PortMixer, int param1Int) {
/* 241 */       super(param1Info, param1PortMixer, null);
/*     */       
/* 243 */       this.portIndex = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     private final int portIndex;
/*     */     
/*     */     private long id;
/*     */ 
/*     */     
/*     */     void implOpen() throws LineUnavailableException {
/* 253 */       long l = ((PortMixer)this.mixer).getID();
/* 254 */       if (this.id == 0L || l != this.id || this.controls.length == 0) {
/* 255 */         this.id = l;
/* 256 */         Vector<Control> vector = new Vector();
/* 257 */         synchronized (vector) {
/* 258 */           PortMixer.nGetControls(this.id, this.portIndex, vector);
/* 259 */           this.controls = new Control[vector.size()];
/* 260 */           for (byte b = 0; b < this.controls.length; b++) {
/* 261 */             this.controls[b] = vector.elementAt(b);
/*     */           }
/*     */         } 
/*     */       } else {
/* 265 */         enableControls(this.controls, true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void enableControls(Control[] param1ArrayOfControl, boolean param1Boolean) {
/* 271 */       for (byte b = 0; b < param1ArrayOfControl.length; b++) {
/* 272 */         if (param1ArrayOfControl[b] instanceof PortMixer.BoolCtrl) {
/* 273 */           ((PortMixer.BoolCtrl)param1ArrayOfControl[b]).closed = !param1Boolean;
/*     */         }
/* 275 */         else if (param1ArrayOfControl[b] instanceof PortMixer.FloatCtrl) {
/* 276 */           ((PortMixer.FloatCtrl)param1ArrayOfControl[b]).closed = !param1Boolean;
/*     */         }
/* 278 */         else if (param1ArrayOfControl[b] instanceof CompoundControl) {
/* 279 */           enableControls(((CompoundControl)param1ArrayOfControl[b]).getMemberControls(), param1Boolean);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void disposeControls() {
/* 285 */       enableControls(this.controls, false);
/* 286 */       this.controls = new Control[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void implClose() {
/* 293 */       enableControls(this.controls, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void open() throws LineUnavailableException {
/* 301 */       synchronized (this.mixer) {
/*     */         
/* 303 */         if (!isOpen()) {
/*     */ 
/*     */           
/* 306 */           this.mixer.open(this);
/*     */           
/*     */           try {
/* 309 */             implOpen();
/*     */ 
/*     */             
/* 312 */             setOpen(true);
/* 313 */           } catch (LineUnavailableException lineUnavailableException) {
/*     */             
/* 315 */             this.mixer.close(this);
/* 316 */             throw lineUnavailableException;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/* 325 */       synchronized (this.mixer) {
/* 326 */         if (isOpen()) {
/*     */ 
/*     */ 
/*     */           
/* 330 */           setOpen(false);
/*     */ 
/*     */           
/* 333 */           implClose();
/*     */ 
/*     */           
/* 336 */           this.mixer.close(this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class BoolCtrl
/*     */     extends BooleanControl
/*     */   {
/*     */     private final long controlID;
/*     */     
/*     */     private boolean closed = false;
/*     */ 
/*     */     
/*     */     private static BooleanControl.Type createType(String param1String) {
/* 353 */       if (param1String.equals("Mute")) {
/* 354 */         return BooleanControl.Type.MUTE;
/*     */       }
/* 356 */       if (param1String.equals("Select"));
/*     */ 
/*     */ 
/*     */       
/* 360 */       return new BCT(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     private BoolCtrl(long param1Long, String param1String) {
/* 365 */       this(param1Long, createType(param1String));
/*     */     }
/*     */     
/*     */     private BoolCtrl(long param1Long, BooleanControl.Type param1Type) {
/* 369 */       super(param1Type, false);
/* 370 */       this.controlID = param1Long;
/*     */     }
/*     */     
/*     */     public void setValue(boolean param1Boolean) {
/* 374 */       if (!this.closed) {
/* 375 */         PortMixer.nControlSetIntValue(this.controlID, param1Boolean ? 1 : 0);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean getValue() {
/* 380 */       if (!this.closed)
/*     */       {
/* 382 */         return (PortMixer.nControlGetIntValue(this.controlID) != 0);
/*     */       }
/*     */       
/* 385 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private static final class BCT
/*     */       extends BooleanControl.Type
/*     */     {
/*     */       private BCT(String param2String) {
/* 393 */         super(param2String);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class CompCtrl
/*     */     extends CompoundControl
/*     */   {
/*     */     private CompCtrl(String param1String, Control[] param1ArrayOfControl) {
/* 403 */       super(new CCT(param1String, null), param1ArrayOfControl);
/*     */     }
/*     */ 
/*     */     
/*     */     private static final class CCT
/*     */       extends CompoundControl.Type
/*     */     {
/*     */       private CCT(String param2String) {
/* 411 */         super(param2String);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class FloatCtrl
/*     */     extends FloatControl
/*     */   {
/*     */     private final long controlID;
/*     */     
/*     */     private boolean closed = false;
/*     */     
/* 425 */     private static final FloatControl.Type[] FLOAT_CONTROL_TYPES = new FloatControl.Type[] { null, FloatControl.Type.BALANCE, FloatControl.Type.MASTER_GAIN, FloatControl.Type.PAN, FloatControl.Type.VOLUME };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private FloatCtrl(long param1Long, String param1String1, float param1Float1, float param1Float2, float param1Float3, String param1String2) {
/* 435 */       this(param1Long, new FCT(param1String1, null), param1Float1, param1Float2, param1Float3, param1String2);
/*     */     }
/*     */ 
/*     */     
/*     */     private FloatCtrl(long param1Long, int param1Int, float param1Float1, float param1Float2, float param1Float3, String param1String) {
/* 440 */       this(param1Long, FLOAT_CONTROL_TYPES[param1Int], param1Float1, param1Float2, param1Float3, param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     private FloatCtrl(long param1Long, FloatControl.Type param1Type, float param1Float1, float param1Float2, float param1Float3, String param1String) {
/* 445 */       super(param1Type, param1Float1, param1Float2, param1Float3, 1000, param1Float1, param1String);
/* 446 */       this.controlID = param1Long;
/*     */     }
/*     */     
/*     */     public void setValue(float param1Float) {
/* 450 */       if (!this.closed) {
/* 451 */         PortMixer.nControlSetFloatValue(this.controlID, param1Float);
/*     */       }
/*     */     }
/*     */     
/*     */     public float getValue() {
/* 456 */       if (!this.closed)
/*     */       {
/* 458 */         return PortMixer.nControlGetFloatValue(this.controlID);
/*     */       }
/*     */       
/* 461 */       return getMinimum();
/*     */     }
/*     */ 
/*     */     
/*     */     private static final class FCT
/*     */       extends FloatControl.Type
/*     */     {
/*     */       private FCT(String param2String) {
/* 469 */         super(param2String);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class PortInfo
/*     */     extends Port.Info
/*     */   {
/*     */     private PortInfo(String param1String, boolean param1Boolean) {
/* 479 */       super(Port.class, param1String, param1Boolean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/PortMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */