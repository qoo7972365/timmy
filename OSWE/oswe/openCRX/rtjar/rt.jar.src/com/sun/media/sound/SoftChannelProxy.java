/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.MidiChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftChannelProxy
/*     */   implements MidiChannel
/*     */ {
/*  37 */   private MidiChannel channel = null;
/*     */   
/*     */   public MidiChannel getChannel() {
/*  40 */     return this.channel;
/*     */   }
/*     */   
/*     */   public void setChannel(MidiChannel paramMidiChannel) {
/*  44 */     this.channel = paramMidiChannel;
/*     */   }
/*     */   
/*     */   public void allNotesOff() {
/*  48 */     if (this.channel == null)
/*     */       return; 
/*  50 */     this.channel.allNotesOff();
/*     */   }
/*     */   
/*     */   public void allSoundOff() {
/*  54 */     if (this.channel == null)
/*     */       return; 
/*  56 */     this.channel.allSoundOff();
/*     */   }
/*     */   
/*     */   public void controlChange(int paramInt1, int paramInt2) {
/*  60 */     if (this.channel == null)
/*     */       return; 
/*  62 */     this.channel.controlChange(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int getChannelPressure() {
/*  66 */     if (this.channel == null)
/*  67 */       return 0; 
/*  68 */     return this.channel.getChannelPressure();
/*     */   }
/*     */   
/*     */   public int getController(int paramInt) {
/*  72 */     if (this.channel == null)
/*  73 */       return 0; 
/*  74 */     return this.channel.getController(paramInt);
/*     */   }
/*     */   
/*     */   public boolean getMono() {
/*  78 */     if (this.channel == null)
/*  79 */       return false; 
/*  80 */     return this.channel.getMono();
/*     */   }
/*     */   
/*     */   public boolean getMute() {
/*  84 */     if (this.channel == null)
/*  85 */       return false; 
/*  86 */     return this.channel.getMute();
/*     */   }
/*     */   
/*     */   public boolean getOmni() {
/*  90 */     if (this.channel == null)
/*  91 */       return false; 
/*  92 */     return this.channel.getOmni();
/*     */   }
/*     */   
/*     */   public int getPitchBend() {
/*  96 */     if (this.channel == null)
/*  97 */       return 8192; 
/*  98 */     return this.channel.getPitchBend();
/*     */   }
/*     */   
/*     */   public int getPolyPressure(int paramInt) {
/* 102 */     if (this.channel == null)
/* 103 */       return 0; 
/* 104 */     return this.channel.getPolyPressure(paramInt);
/*     */   }
/*     */   
/*     */   public int getProgram() {
/* 108 */     if (this.channel == null)
/* 109 */       return 0; 
/* 110 */     return this.channel.getProgram();
/*     */   }
/*     */   
/*     */   public boolean getSolo() {
/* 114 */     if (this.channel == null)
/* 115 */       return false; 
/* 116 */     return this.channel.getSolo();
/*     */   }
/*     */   
/*     */   public boolean localControl(boolean paramBoolean) {
/* 120 */     if (this.channel == null)
/* 121 */       return false; 
/* 122 */     return this.channel.localControl(paramBoolean);
/*     */   }
/*     */   
/*     */   public void noteOff(int paramInt) {
/* 126 */     if (this.channel == null)
/*     */       return; 
/* 128 */     this.channel.noteOff(paramInt);
/*     */   }
/*     */   
/*     */   public void noteOff(int paramInt1, int paramInt2) {
/* 132 */     if (this.channel == null)
/*     */       return; 
/* 134 */     this.channel.noteOff(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void noteOn(int paramInt1, int paramInt2) {
/* 138 */     if (this.channel == null)
/*     */       return; 
/* 140 */     this.channel.noteOn(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void programChange(int paramInt) {
/* 144 */     if (this.channel == null)
/*     */       return; 
/* 146 */     this.channel.programChange(paramInt);
/*     */   }
/*     */   
/*     */   public void programChange(int paramInt1, int paramInt2) {
/* 150 */     if (this.channel == null)
/*     */       return; 
/* 152 */     this.channel.programChange(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void resetAllControllers() {
/* 156 */     if (this.channel == null)
/*     */       return; 
/* 158 */     this.channel.resetAllControllers();
/*     */   }
/*     */   
/*     */   public void setChannelPressure(int paramInt) {
/* 162 */     if (this.channel == null)
/*     */       return; 
/* 164 */     this.channel.setChannelPressure(paramInt);
/*     */   }
/*     */   
/*     */   public void setMono(boolean paramBoolean) {
/* 168 */     if (this.channel == null)
/*     */       return; 
/* 170 */     this.channel.setMono(paramBoolean);
/*     */   }
/*     */   
/*     */   public void setMute(boolean paramBoolean) {
/* 174 */     if (this.channel == null)
/*     */       return; 
/* 176 */     this.channel.setMute(paramBoolean);
/*     */   }
/*     */   
/*     */   public void setOmni(boolean paramBoolean) {
/* 180 */     if (this.channel == null)
/*     */       return; 
/* 182 */     this.channel.setOmni(paramBoolean);
/*     */   }
/*     */   
/*     */   public void setPitchBend(int paramInt) {
/* 186 */     if (this.channel == null)
/*     */       return; 
/* 188 */     this.channel.setPitchBend(paramInt);
/*     */   }
/*     */   
/*     */   public void setPolyPressure(int paramInt1, int paramInt2) {
/* 192 */     if (this.channel == null)
/*     */       return; 
/* 194 */     this.channel.setPolyPressure(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void setSolo(boolean paramBoolean) {
/* 198 */     if (this.channel == null)
/*     */       return; 
/* 200 */     this.channel.setSolo(paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftChannelProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */