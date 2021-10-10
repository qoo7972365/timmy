/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.sound.midi.Instrument;
/*     */ import javax.sound.midi.Patch;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.midi.SoundbankResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleSoundbank
/*     */   implements Soundbank
/*     */ {
/*  43 */   String name = "";
/*  44 */   String version = "";
/*  45 */   String vendor = "";
/*  46 */   String description = "";
/*  47 */   List<SoundbankResource> resources = new ArrayList<>();
/*  48 */   List<Instrument> instruments = new ArrayList<>();
/*     */   
/*     */   public String getName() {
/*  51 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  55 */     return this.version;
/*     */   }
/*     */   
/*     */   public String getVendor() {
/*  59 */     return this.vendor;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  63 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String paramString) {
/*  67 */     this.description = paramString;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/*  71 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public void setVendor(String paramString) {
/*  75 */     this.vendor = paramString;
/*     */   }
/*     */   
/*     */   public void setVersion(String paramString) {
/*  79 */     this.version = paramString;
/*     */   }
/*     */   
/*     */   public SoundbankResource[] getResources() {
/*  83 */     return this.resources.<SoundbankResource>toArray(new SoundbankResource[this.resources.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instrument[] getInstruments() {
/*  88 */     Instrument[] arrayOfInstrument = this.instruments.<Instrument>toArray(new Instrument[this.resources.size()]);
/*  89 */     Arrays.sort(arrayOfInstrument, new ModelInstrumentComparator());
/*  90 */     return arrayOfInstrument;
/*     */   }
/*     */   
/*     */   public Instrument getInstrument(Patch paramPatch) {
/*  94 */     int i = paramPatch.getProgram();
/*  95 */     int j = paramPatch.getBank();
/*  96 */     boolean bool = false;
/*  97 */     if (paramPatch instanceof ModelPatch)
/*  98 */       bool = ((ModelPatch)paramPatch).isPercussion(); 
/*  99 */     for (Instrument instrument : this.instruments) {
/* 100 */       Patch patch = instrument.getPatch();
/* 101 */       int k = patch.getProgram();
/* 102 */       int m = patch.getBank();
/* 103 */       if (i == k && j == m) {
/* 104 */         boolean bool1 = false;
/* 105 */         if (patch instanceof ModelPatch)
/* 106 */           bool1 = ((ModelPatch)patch).isPercussion(); 
/* 107 */         if (bool == bool1)
/* 108 */           return instrument; 
/*     */       } 
/*     */     } 
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public void addResource(SoundbankResource paramSoundbankResource) {
/* 115 */     if (paramSoundbankResource instanceof Instrument) {
/* 116 */       this.instruments.add((Instrument)paramSoundbankResource);
/*     */     } else {
/* 118 */       this.resources.add(paramSoundbankResource);
/*     */     } 
/*     */   }
/*     */   public void removeResource(SoundbankResource paramSoundbankResource) {
/* 122 */     if (paramSoundbankResource instanceof Instrument) {
/* 123 */       this.instruments.remove(paramSoundbankResource);
/*     */     } else {
/* 125 */       this.resources.remove(paramSoundbankResource);
/*     */     } 
/*     */   }
/*     */   public void addInstrument(Instrument paramInstrument) {
/* 129 */     this.instruments.add(paramInstrument);
/*     */   }
/*     */   
/*     */   public void removeInstrument(Instrument paramInstrument) {
/* 133 */     this.instruments.remove(paramInstrument);
/*     */   }
/*     */   
/*     */   public void addAllInstruments(Soundbank paramSoundbank) {
/* 137 */     for (Instrument instrument : paramSoundbank.getInstruments())
/* 138 */       addInstrument(instrument); 
/*     */   }
/*     */   
/*     */   public void removeAllInstruments(Soundbank paramSoundbank) {
/* 142 */     for (Instrument instrument : paramSoundbank.getInstruments())
/* 143 */       removeInstrument(instrument); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SimpleSoundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */