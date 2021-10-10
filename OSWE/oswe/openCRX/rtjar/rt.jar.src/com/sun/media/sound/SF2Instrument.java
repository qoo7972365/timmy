/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.sound.midi.Patch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SF2Instrument
/*     */   extends ModelInstrument
/*     */ {
/*  41 */   String name = "";
/*  42 */   int preset = 0;
/*  43 */   int bank = 0;
/*  44 */   long library = 0L;
/*  45 */   long genre = 0L;
/*  46 */   long morphology = 0L;
/*  47 */   SF2GlobalRegion globalregion = null;
/*  48 */   List<SF2InstrumentRegion> regions = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public SF2Instrument() {
/*  52 */     super(null, null, null, null);
/*     */   }
/*     */   
/*     */   public SF2Instrument(SF2Soundbank paramSF2Soundbank) {
/*  56 */     super(paramSF2Soundbank, null, null, null);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  60 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/*  64 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public Patch getPatch() {
/*  68 */     if (this.bank == 128) {
/*  69 */       return new ModelPatch(0, this.preset, true);
/*     */     }
/*  71 */     return new ModelPatch(this.bank << 7, this.preset, false);
/*     */   }
/*     */   
/*     */   public void setPatch(Patch paramPatch) {
/*  75 */     if (paramPatch instanceof ModelPatch && ((ModelPatch)paramPatch).isPercussion()) {
/*  76 */       this.bank = 128;
/*  77 */       this.preset = paramPatch.getProgram();
/*     */     } else {
/*  79 */       this.bank = paramPatch.getBank() >> 7;
/*  80 */       this.preset = paramPatch.getProgram();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getData() {
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public long getGenre() {
/*  89 */     return this.genre;
/*     */   }
/*     */   
/*     */   public void setGenre(long paramLong) {
/*  93 */     this.genre = paramLong;
/*     */   }
/*     */   
/*     */   public long getLibrary() {
/*  97 */     return this.library;
/*     */   }
/*     */   
/*     */   public void setLibrary(long paramLong) {
/* 101 */     this.library = paramLong;
/*     */   }
/*     */   
/*     */   public long getMorphology() {
/* 105 */     return this.morphology;
/*     */   }
/*     */   
/*     */   public void setMorphology(long paramLong) {
/* 109 */     this.morphology = paramLong;
/*     */   }
/*     */   
/*     */   public List<SF2InstrumentRegion> getRegions() {
/* 113 */     return this.regions;
/*     */   }
/*     */   
/*     */   public SF2GlobalRegion getGlobalRegion() {
/* 117 */     return this.globalregion;
/*     */   }
/*     */   
/*     */   public void setGlobalZone(SF2GlobalRegion paramSF2GlobalRegion) {
/* 121 */     this.globalregion = paramSF2GlobalRegion;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 125 */     if (this.bank == 128) {
/* 126 */       return "Drumkit: " + this.name + " preset #" + this.preset;
/*     */     }
/* 128 */     return "Instrument: " + this.name + " bank #" + this.bank + " preset #" + this.preset;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPerformer[] getPerformers() {
/* 133 */     int i = 0;
/* 134 */     for (SF2InstrumentRegion sF2InstrumentRegion : this.regions)
/* 135 */       i += sF2InstrumentRegion.getLayer().getRegions().size(); 
/* 136 */     ModelPerformer[] arrayOfModelPerformer = new ModelPerformer[i];
/* 137 */     byte b = 0;
/*     */     
/* 139 */     SF2GlobalRegion sF2GlobalRegion = this.globalregion;
/* 140 */     for (SF2InstrumentRegion sF2InstrumentRegion : this.regions) {
/* 141 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 142 */       hashMap.putAll(sF2InstrumentRegion.getGenerators());
/* 143 */       if (sF2GlobalRegion != null) {
/* 144 */         hashMap.putAll(sF2GlobalRegion.getGenerators());
/*     */       }
/* 146 */       SF2Layer sF2Layer = sF2InstrumentRegion.getLayer();
/* 147 */       SF2GlobalRegion sF2GlobalRegion1 = sF2Layer.getGlobalRegion();
/* 148 */       for (SF2LayerRegion sF2LayerRegion : sF2Layer.getRegions()) {
/* 149 */         ModelPerformer modelPerformer = new ModelPerformer();
/* 150 */         if (sF2LayerRegion.getSample() != null) {
/* 151 */           modelPerformer.setName(sF2LayerRegion.getSample().getName());
/*     */         } else {
/* 153 */           modelPerformer.setName(sF2Layer.getName());
/*     */         } 
/* 155 */         arrayOfModelPerformer[b++] = modelPerformer;
/*     */         
/* 157 */         byte b1 = 0;
/* 158 */         byte b2 = Byte.MAX_VALUE;
/* 159 */         byte b3 = 0;
/* 160 */         byte b4 = Byte.MAX_VALUE;
/*     */         
/* 162 */         if (sF2LayerRegion.contains(57)) {
/* 163 */           modelPerformer.setExclusiveClass(sF2LayerRegion.getInteger(57));
/*     */         }
/*     */         
/* 166 */         if (sF2LayerRegion.contains(43)) {
/* 167 */           byte[] arrayOfByte = sF2LayerRegion.getBytes(43);
/*     */           
/* 169 */           if (arrayOfByte[0] >= 0 && 
/* 170 */             arrayOfByte[0] > b1)
/* 171 */             b1 = arrayOfByte[0]; 
/* 172 */           if (arrayOfByte[1] >= 0 && 
/* 173 */             arrayOfByte[1] < b2)
/* 174 */             b2 = arrayOfByte[1]; 
/*     */         } 
/* 176 */         if (sF2LayerRegion.contains(44)) {
/* 177 */           byte[] arrayOfByte = sF2LayerRegion.getBytes(44);
/*     */           
/* 179 */           if (arrayOfByte[0] >= 0 && 
/* 180 */             arrayOfByte[0] > b3)
/* 181 */             b3 = arrayOfByte[0]; 
/* 182 */           if (arrayOfByte[1] >= 0 && 
/* 183 */             arrayOfByte[1] < b4)
/* 184 */             b4 = arrayOfByte[1]; 
/*     */         } 
/* 186 */         if (sF2InstrumentRegion.contains(43)) {
/* 187 */           byte[] arrayOfByte = sF2InstrumentRegion.getBytes(43);
/*     */           
/* 189 */           if (arrayOfByte[0] > b1)
/* 190 */             b1 = arrayOfByte[0]; 
/* 191 */           if (arrayOfByte[1] < b2)
/* 192 */             b2 = arrayOfByte[1]; 
/*     */         } 
/* 194 */         if (sF2InstrumentRegion.contains(44)) {
/* 195 */           byte[] arrayOfByte = sF2InstrumentRegion.getBytes(44);
/*     */           
/* 197 */           if (arrayOfByte[0] > b3)
/* 198 */             b3 = arrayOfByte[0]; 
/* 199 */           if (arrayOfByte[1] < b4)
/* 200 */             b4 = arrayOfByte[1]; 
/*     */         } 
/* 202 */         modelPerformer.setKeyFrom(b1);
/* 203 */         modelPerformer.setKeyTo(b2);
/* 204 */         modelPerformer.setVelFrom(b3);
/* 205 */         modelPerformer.setVelTo(b4);
/*     */         
/* 207 */         short s1 = sF2LayerRegion.getShort(0);
/*     */         
/* 209 */         short s2 = sF2LayerRegion.getShort(1);
/*     */         
/* 211 */         short s3 = sF2LayerRegion.getShort(2);
/*     */         
/* 213 */         short s4 = sF2LayerRegion.getShort(3);
/*     */ 
/*     */         
/* 216 */         int j = s1 + sF2LayerRegion.getShort(4) * 32768;
/*     */         
/* 218 */         int k = s2 + sF2LayerRegion.getShort(12) * 32768;
/*     */         
/* 220 */         int m = s3 + sF2LayerRegion.getShort(45) * 32768;
/*     */         
/* 222 */         int n = s4 + sF2LayerRegion.getShort(50) * 32768;
/*     */         
/* 224 */         m -= j;
/* 225 */         n -= j;
/*     */         
/* 227 */         SF2Sample sF2Sample = sF2LayerRegion.getSample();
/* 228 */         int i1 = sF2Sample.originalPitch;
/* 229 */         if (sF2LayerRegion.getShort(58) != -1) {
/* 230 */           i1 = sF2LayerRegion.getShort(58);
/*     */         }
/*     */         
/* 233 */         float f = (-i1 * 100 + sF2Sample.pitchCorrection);
/* 234 */         ModelByteBuffer modelByteBuffer1 = sF2Sample.getDataBuffer();
/* 235 */         ModelByteBuffer modelByteBuffer2 = sF2Sample.getData24Buffer();
/*     */         
/* 237 */         if (j != 0 || k != 0) {
/* 238 */           modelByteBuffer1 = modelByteBuffer1.subbuffer((j * 2), modelByteBuffer1
/* 239 */               .capacity() + (k * 2));
/* 240 */           if (modelByteBuffer2 != null) {
/* 241 */             modelByteBuffer2 = modelByteBuffer2.subbuffer(j, modelByteBuffer2
/* 242 */                 .capacity() + k);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 266 */         ModelByteBufferWavetable modelByteBufferWavetable = new ModelByteBufferWavetable(modelByteBuffer1, sF2Sample.getFormat(), f);
/* 267 */         if (modelByteBuffer2 != null) {
/* 268 */           modelByteBufferWavetable.set8BitExtensionBuffer(modelByteBuffer2);
/*     */         }
/* 270 */         HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 271 */         if (sF2GlobalRegion1 != null)
/* 272 */           hashMap1.putAll(sF2GlobalRegion1.getGenerators()); 
/* 273 */         hashMap1.putAll(sF2LayerRegion.getGenerators());
/* 274 */         for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
/*     */           
/* 276 */           if (!hashMap1.containsKey(entry.getKey())) {
/* 277 */             s = sF2LayerRegion.getShort(((Integer)entry.getKey()).intValue());
/*     */           } else {
/* 279 */             s = ((Short)hashMap1.get(entry.getKey())).shortValue();
/* 280 */           }  short s = (short)(s + ((Short)entry.getValue()).shortValue());
/* 281 */           hashMap1.put(entry.getKey(), Short.valueOf(s));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 290 */         short s5 = getGeneratorValue((Map)hashMap1, 54);
/*     */         
/* 292 */         if ((s5 == 1 || s5 == 3) && 
/* 293 */           sF2Sample.startLoop >= 0L && sF2Sample.endLoop > 0L) {
/* 294 */           modelByteBufferWavetable.setLoopStart((int)(sF2Sample.startLoop + m));
/*     */           
/* 296 */           modelByteBufferWavetable.setLoopLength((int)(sF2Sample.endLoop - sF2Sample.startLoop + n - m));
/*     */           
/* 298 */           if (s5 == 1)
/* 299 */             modelByteBufferWavetable.setLoopType(1); 
/* 300 */           if (s5 == 3) {
/* 301 */             modelByteBufferWavetable.setLoopType(2);
/*     */           }
/*     */         } 
/* 304 */         modelPerformer.getOscillators().add(modelByteBufferWavetable);
/*     */ 
/*     */         
/* 307 */         short s6 = getGeneratorValue((Map)hashMap1, 33);
/*     */         
/* 309 */         short s7 = getGeneratorValue((Map)hashMap1, 34);
/*     */         
/* 311 */         short s8 = getGeneratorValue((Map)hashMap1, 35);
/*     */         
/* 313 */         short s9 = getGeneratorValue((Map)hashMap1, 36);
/*     */         
/* 315 */         short s10 = getGeneratorValue((Map)hashMap1, 37);
/*     */         
/* 317 */         short s11 = getGeneratorValue((Map)hashMap1, 38);
/*     */ 
/*     */         
/* 320 */         if (s8 != -12000) {
/* 321 */           short s = getGeneratorValue((Map)hashMap1, 39);
/*     */           
/* 323 */           s8 = (short)(s8 + 60 * s);
/* 324 */           float f1 = (-s * 128);
/* 325 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_NOTEON_KEYNUMBER;
/* 326 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_EG1_HOLD;
/* 327 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), f1, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */         
/* 331 */         if (s9 != -12000) {
/* 332 */           short s = getGeneratorValue((Map)hashMap1, 40);
/*     */           
/* 334 */           s9 = (short)(s9 + 60 * s);
/* 335 */           float f1 = (-s * 128);
/* 336 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_NOTEON_KEYNUMBER;
/* 337 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_EG1_DECAY;
/* 338 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), f1, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 343 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG1_DELAY, s6);
/*     */         
/* 345 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG1_ATTACK, s7);
/*     */         
/* 347 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG1_HOLD, s8);
/*     */         
/* 349 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG1_DECAY, s9);
/*     */ 
/*     */ 
/*     */         
/* 353 */         s10 = (short)(1000 - s10);
/* 354 */         if (s10 < 0)
/* 355 */           s10 = 0; 
/* 356 */         if (s10 > 1000) {
/* 357 */           s10 = 1000;
/*     */         }
/* 359 */         addValue(modelPerformer, ModelDestination.DESTINATION_EG1_SUSTAIN, s10);
/*     */         
/* 361 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG1_RELEASE, s11);
/*     */ 
/*     */         
/* 364 */         if (getGeneratorValue((Map)hashMap1, 11) != 0 || 
/*     */           
/* 366 */           getGeneratorValue((Map)hashMap1, 7) != 0) {
/*     */           
/* 368 */           short s14 = getGeneratorValue((Map)hashMap1, 25);
/*     */           
/* 370 */           short s15 = getGeneratorValue((Map)hashMap1, 26);
/*     */           
/* 372 */           short s16 = getGeneratorValue((Map)hashMap1, 27);
/*     */           
/* 374 */           short s17 = getGeneratorValue((Map)hashMap1, 28);
/*     */           
/* 376 */           short s18 = getGeneratorValue((Map)hashMap1, 29);
/*     */           
/* 378 */           short s19 = getGeneratorValue((Map)hashMap1, 30);
/*     */ 
/*     */ 
/*     */           
/* 382 */           if (s16 != -12000) {
/* 383 */             short s = getGeneratorValue((Map)hashMap1, 31);
/*     */             
/* 385 */             s16 = (short)(s16 + 60 * s);
/* 386 */             float f1 = (-s * 128);
/* 387 */             ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_NOTEON_KEYNUMBER;
/* 388 */             ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_EG2_HOLD;
/* 389 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), f1, new ModelDestination(modelIdentifier2)));
/*     */           } 
/*     */ 
/*     */           
/* 393 */           if (s17 != -12000) {
/* 394 */             short s = getGeneratorValue((Map)hashMap1, 32);
/*     */             
/* 396 */             s17 = (short)(s17 + 60 * s);
/* 397 */             float f1 = (-s * 128);
/* 398 */             ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_NOTEON_KEYNUMBER;
/* 399 */             ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_EG2_DECAY;
/* 400 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), f1, new ModelDestination(modelIdentifier2)));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 405 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG2_DELAY, s14);
/*     */           
/* 407 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG2_ATTACK, s15);
/*     */           
/* 409 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG2_HOLD, s16);
/*     */           
/* 411 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG2_DECAY, s17);
/*     */           
/* 413 */           if (s18 < 0)
/* 414 */             s18 = 0; 
/* 415 */           if (s18 > 1000)
/* 416 */             s18 = 1000; 
/* 417 */           addValue(modelPerformer, ModelDestination.DESTINATION_EG2_SUSTAIN, (1000 - s18));
/*     */           
/* 419 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_EG2_RELEASE, s19);
/*     */ 
/*     */           
/* 422 */           if (getGeneratorValue((Map)hashMap1, 11) != 0) {
/*     */             
/* 424 */             double d = getGeneratorValue((Map)hashMap1, 11);
/*     */             
/* 426 */             ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_EG2;
/* 427 */             ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_FILTER_FREQ;
/*     */             
/* 429 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), d, new ModelDestination(modelIdentifier2)));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 434 */           if (getGeneratorValue((Map)hashMap1, 7) != 0) {
/*     */             
/* 436 */             double d = getGeneratorValue((Map)hashMap1, 7);
/*     */             
/* 438 */             ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_EG2;
/* 439 */             ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_PITCH;
/* 440 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), d, new ModelDestination(modelIdentifier2)));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 447 */         if (getGeneratorValue((Map)hashMap1, 10) != 0 || 
/*     */           
/* 449 */           getGeneratorValue((Map)hashMap1, 5) != 0 || 
/*     */           
/* 451 */           getGeneratorValue((Map)hashMap1, 13) != 0) {
/*     */           
/* 453 */           short s14 = getGeneratorValue((Map)hashMap1, 22);
/*     */           
/* 455 */           short s15 = getGeneratorValue((Map)hashMap1, 21);
/*     */           
/* 457 */           addTimecentValue(modelPerformer, ModelDestination.DESTINATION_LFO1_DELAY, s15);
/*     */           
/* 459 */           addValue(modelPerformer, ModelDestination.DESTINATION_LFO1_FREQ, s14);
/*     */         } 
/*     */ 
/*     */         
/* 463 */         short s12 = getGeneratorValue((Map)hashMap1, 24);
/*     */         
/* 465 */         short s13 = getGeneratorValue((Map)hashMap1, 23);
/*     */         
/* 467 */         addTimecentValue(modelPerformer, ModelDestination.DESTINATION_LFO2_DELAY, s13);
/*     */         
/* 469 */         addValue(modelPerformer, ModelDestination.DESTINATION_LFO2_FREQ, s12);
/*     */ 
/*     */ 
/*     */         
/* 473 */         if (getGeneratorValue((Map)hashMap1, 6) != 0) {
/*     */           
/* 475 */           double d = getGeneratorValue((Map)hashMap1, 6);
/*     */           
/* 477 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_LFO2;
/* 478 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_PITCH;
/* 479 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1, false, true), d, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 487 */         if (getGeneratorValue((Map)hashMap1, 10) != 0) {
/*     */           
/* 489 */           double d = getGeneratorValue((Map)hashMap1, 10);
/*     */           
/* 491 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_LFO1;
/* 492 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_FILTER_FREQ;
/* 493 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1, false, true), d, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 501 */         if (getGeneratorValue((Map)hashMap1, 5) != 0) {
/*     */           
/* 503 */           double d = getGeneratorValue((Map)hashMap1, 5);
/*     */           
/* 505 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_LFO1;
/* 506 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_PITCH;
/* 507 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1, false, true), d, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 515 */         if (getGeneratorValue((Map)hashMap1, 13) != 0) {
/*     */           
/* 517 */           double d = getGeneratorValue((Map)hashMap1, 13);
/*     */           
/* 519 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_LFO1;
/* 520 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_GAIN;
/* 521 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1, false, true), d, new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 529 */         if (sF2LayerRegion.getShort(46) != -1) {
/* 530 */           double d = sF2LayerRegion.getShort(46) / 128.0D;
/* 531 */           addValue(modelPerformer, ModelDestination.DESTINATION_KEYNUMBER, d);
/*     */         } 
/*     */         
/* 534 */         if (sF2LayerRegion.getShort(47) != -1) {
/* 535 */           double d = sF2LayerRegion.getShort(47) / 128.0D;
/*     */           
/* 537 */           addValue(modelPerformer, ModelDestination.DESTINATION_VELOCITY, d);
/*     */         } 
/*     */         
/* 540 */         if (getGeneratorValue((Map)hashMap1, 8) < 13500) {
/*     */           
/* 542 */           short s14 = getGeneratorValue((Map)hashMap1, 8);
/*     */           
/* 544 */           short s15 = getGeneratorValue((Map)hashMap1, 9);
/*     */           
/* 546 */           addValue(modelPerformer, ModelDestination.DESTINATION_FILTER_FREQ, s14);
/*     */           
/* 548 */           addValue(modelPerformer, ModelDestination.DESTINATION_FILTER_Q, s15);
/*     */         } 
/*     */ 
/*     */         
/* 552 */         int i2 = 100 * getGeneratorValue((Map)hashMap1, 51);
/*     */         
/* 554 */         i2 += getGeneratorValue((Map)hashMap1, 52);
/*     */         
/* 556 */         if (i2 != 0) {
/* 557 */           addValue(modelPerformer, ModelDestination.DESTINATION_PITCH, (short)i2);
/*     */         }
/*     */         
/* 560 */         if (getGeneratorValue((Map)hashMap1, 17) != 0) {
/* 561 */           short s = getGeneratorValue((Map)hashMap1, 17);
/*     */           
/* 563 */           addValue(modelPerformer, ModelDestination.DESTINATION_PAN, s);
/*     */         } 
/* 565 */         if (getGeneratorValue((Map)hashMap1, 48) != 0) {
/* 566 */           short s = getGeneratorValue((Map)hashMap1, 48);
/*     */           
/* 568 */           addValue(modelPerformer, ModelDestination.DESTINATION_GAIN, (-0.376287F * s));
/*     */         } 
/*     */         
/* 571 */         if (getGeneratorValue((Map)hashMap1, 15) != 0) {
/*     */           
/* 573 */           short s = getGeneratorValue((Map)hashMap1, 15);
/*     */           
/* 575 */           addValue(modelPerformer, ModelDestination.DESTINATION_CHORUS, s);
/*     */         } 
/* 577 */         if (getGeneratorValue((Map)hashMap1, 16) != 0) {
/*     */           
/* 579 */           short s = getGeneratorValue((Map)hashMap1, 16);
/*     */           
/* 581 */           addValue(modelPerformer, ModelDestination.DESTINATION_REVERB, s);
/*     */         } 
/* 583 */         if (getGeneratorValue((Map)hashMap1, 56) != 100) {
/*     */           
/* 585 */           short s = getGeneratorValue((Map)hashMap1, 56);
/*     */           
/* 587 */           if (s == 0) {
/* 588 */             ModelIdentifier modelIdentifier = ModelDestination.DESTINATION_PITCH;
/* 589 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(null, (i1 * 100), new ModelDestination(modelIdentifier)));
/*     */           }
/*     */           else {
/*     */             
/* 593 */             ModelIdentifier modelIdentifier = ModelDestination.DESTINATION_PITCH;
/* 594 */             modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(null, (i1 * (100 - s)), new ModelDestination(modelIdentifier)));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 599 */           ModelIdentifier modelIdentifier1 = ModelSource.SOURCE_NOTEON_KEYNUMBER;
/* 600 */           ModelIdentifier modelIdentifier2 = ModelDestination.DESTINATION_PITCH;
/* 601 */           modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(modelIdentifier1), (128 * s), new ModelDestination(modelIdentifier2)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 607 */         modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_NOTEON_VELOCITY, new ModelTransform()
/*     */                 {
/*     */                   
/*     */                   public double transform(double param1Double)
/*     */                   {
/* 612 */                     if (param1Double < 0.5D) {
/* 613 */                       return 1.0D - param1Double * 2.0D;
/*     */                     }
/* 615 */                     return 0.0D;
/*     */                   }
/*     */                 }), -2400.0D, new ModelDestination(ModelDestination.DESTINATION_FILTER_FREQ)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 623 */         modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_LFO2, false, true, 0), new ModelSource(new ModelIdentifier("midi_cc", "1", 0), false, false, 0), 50.0D, new ModelDestination(ModelDestination.DESTINATION_PITCH)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 636 */         if (sF2Layer.getGlobalRegion() != null)
/*     */         {
/* 638 */           for (SF2Modulator sF2Modulator : sF2Layer.getGlobalRegion().getModulators()) {
/* 639 */             convertModulator(modelPerformer, sF2Modulator);
/*     */           }
/*     */         }
/* 642 */         for (SF2Modulator sF2Modulator : sF2LayerRegion.getModulators()) {
/* 643 */           convertModulator(modelPerformer, sF2Modulator);
/*     */         }
/* 645 */         if (sF2GlobalRegion != null)
/* 646 */           for (SF2Modulator sF2Modulator : sF2GlobalRegion.getModulators()) {
/* 647 */             convertModulator(modelPerformer, sF2Modulator);
/*     */           } 
/* 649 */         for (SF2Modulator sF2Modulator : sF2InstrumentRegion.getModulators()) {
/* 650 */           convertModulator(modelPerformer, sF2Modulator);
/*     */         }
/*     */       } 
/*     */     } 
/* 654 */     return arrayOfModelPerformer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertModulator(ModelPerformer paramModelPerformer, SF2Modulator paramSF2Modulator) {
/* 659 */     ModelSource modelSource1 = convertSource(paramSF2Modulator.getSourceOperator());
/* 660 */     ModelSource modelSource2 = convertSource(paramSF2Modulator.getAmountSourceOperator());
/* 661 */     if (modelSource1 == null && paramSF2Modulator.getSourceOperator() != 0)
/*     */       return; 
/* 663 */     if (modelSource2 == null && paramSF2Modulator.getAmountSourceOperator() != 0)
/*     */       return; 
/* 665 */     double d = paramSF2Modulator.getAmount();
/* 666 */     double[] arrayOfDouble = new double[1];
/* 667 */     ModelSource[] arrayOfModelSource = new ModelSource[1];
/* 668 */     arrayOfDouble[0] = 1.0D;
/* 669 */     ModelDestination modelDestination = convertDestination(paramSF2Modulator
/* 670 */         .getDestinationOperator(), arrayOfDouble, arrayOfModelSource);
/* 671 */     d *= arrayOfDouble[0];
/* 672 */     if (modelDestination == null)
/*     */       return; 
/* 674 */     if (paramSF2Modulator.getTransportOperator() == 2) {
/* 675 */       ((ModelStandardTransform)modelDestination.getTransform()).setTransform(4);
/*     */     }
/*     */     
/* 678 */     ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(modelSource1, modelSource2, d, modelDestination);
/* 679 */     if (arrayOfModelSource[0] != null)
/* 680 */       modelConnectionBlock.addSource(arrayOfModelSource[0]); 
/* 681 */     paramModelPerformer.getConnectionBlocks().add(modelConnectionBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ModelSource convertSource(int paramInt) {
/* 686 */     if (paramInt == 0)
/* 687 */       return null; 
/* 688 */     ModelIdentifier modelIdentifier = null;
/* 689 */     int i = paramInt & 0x7F;
/* 690 */     if ((paramInt & 0x80) != 0) {
/* 691 */       modelIdentifier = new ModelIdentifier("midi_cc", Integer.toString(i));
/*     */     } else {
/* 693 */       if (i == 2)
/* 694 */         modelIdentifier = ModelSource.SOURCE_NOTEON_VELOCITY; 
/* 695 */       if (i == 3)
/* 696 */         modelIdentifier = ModelSource.SOURCE_NOTEON_KEYNUMBER; 
/* 697 */       if (i == 10)
/* 698 */         modelIdentifier = ModelSource.SOURCE_MIDI_POLY_PRESSURE; 
/* 699 */       if (i == 13)
/* 700 */         modelIdentifier = ModelSource.SOURCE_MIDI_CHANNEL_PRESSURE; 
/* 701 */       if (i == 14)
/* 702 */         modelIdentifier = ModelSource.SOURCE_MIDI_PITCH; 
/* 703 */       if (i == 16)
/* 704 */         modelIdentifier = new ModelIdentifier("midi_rpn", "0"); 
/*     */     } 
/* 706 */     if (modelIdentifier == null) {
/* 707 */       return null;
/*     */     }
/* 709 */     ModelSource modelSource = new ModelSource(modelIdentifier);
/*     */     
/* 711 */     ModelStandardTransform modelStandardTransform = (ModelStandardTransform)modelSource.getTransform();
/*     */     
/* 713 */     if ((0x100 & paramInt) != 0) {
/* 714 */       modelStandardTransform.setDirection(true);
/*     */     } else {
/* 716 */       modelStandardTransform.setDirection(false);
/*     */     } 
/* 718 */     if ((0x200 & paramInt) != 0) {
/* 719 */       modelStandardTransform.setPolarity(true);
/*     */     } else {
/* 721 */       modelStandardTransform.setPolarity(false);
/*     */     } 
/* 723 */     if ((0x400 & paramInt) != 0)
/* 724 */       modelStandardTransform.setTransform(1); 
/* 725 */     if ((0x800 & paramInt) != 0)
/* 726 */       modelStandardTransform.setTransform(2); 
/* 727 */     if ((0xC00 & paramInt) != 0) {
/* 728 */       modelStandardTransform.setTransform(3);
/*     */     }
/* 730 */     return modelSource;
/*     */   }
/*     */ 
/*     */   
/*     */   static ModelDestination convertDestination(int paramInt, double[] paramArrayOfdouble, ModelSource[] paramArrayOfModelSource) {
/* 735 */     ModelIdentifier modelIdentifier = null;
/* 736 */     switch (paramInt) {
/*     */       case 8:
/* 738 */         modelIdentifier = ModelDestination.DESTINATION_FILTER_FREQ;
/*     */         break;
/*     */       case 9:
/* 741 */         modelIdentifier = ModelDestination.DESTINATION_FILTER_Q;
/*     */         break;
/*     */       case 15:
/* 744 */         modelIdentifier = ModelDestination.DESTINATION_CHORUS;
/*     */         break;
/*     */       case 16:
/* 747 */         modelIdentifier = ModelDestination.DESTINATION_REVERB;
/*     */         break;
/*     */       case 17:
/* 750 */         modelIdentifier = ModelDestination.DESTINATION_PAN;
/*     */         break;
/*     */       case 21:
/* 753 */         modelIdentifier = ModelDestination.DESTINATION_LFO1_DELAY;
/*     */         break;
/*     */       case 22:
/* 756 */         modelIdentifier = ModelDestination.DESTINATION_LFO1_FREQ;
/*     */         break;
/*     */       case 23:
/* 759 */         modelIdentifier = ModelDestination.DESTINATION_LFO2_DELAY;
/*     */         break;
/*     */       case 24:
/* 762 */         modelIdentifier = ModelDestination.DESTINATION_LFO2_FREQ;
/*     */         break;
/*     */       
/*     */       case 25:
/* 766 */         modelIdentifier = ModelDestination.DESTINATION_EG2_DELAY;
/*     */         break;
/*     */       case 26:
/* 769 */         modelIdentifier = ModelDestination.DESTINATION_EG2_ATTACK;
/*     */         break;
/*     */       case 27:
/* 772 */         modelIdentifier = ModelDestination.DESTINATION_EG2_HOLD;
/*     */         break;
/*     */       case 28:
/* 775 */         modelIdentifier = ModelDestination.DESTINATION_EG2_DECAY;
/*     */         break;
/*     */       case 29:
/* 778 */         modelIdentifier = ModelDestination.DESTINATION_EG2_SUSTAIN;
/* 779 */         paramArrayOfdouble[0] = -1.0D;
/*     */         break;
/*     */       case 30:
/* 782 */         modelIdentifier = ModelDestination.DESTINATION_EG2_RELEASE;
/*     */         break;
/*     */       case 33:
/* 785 */         modelIdentifier = ModelDestination.DESTINATION_EG1_DELAY;
/*     */         break;
/*     */       case 34:
/* 788 */         modelIdentifier = ModelDestination.DESTINATION_EG1_ATTACK;
/*     */         break;
/*     */       case 35:
/* 791 */         modelIdentifier = ModelDestination.DESTINATION_EG1_HOLD;
/*     */         break;
/*     */       case 36:
/* 794 */         modelIdentifier = ModelDestination.DESTINATION_EG1_DECAY;
/*     */         break;
/*     */       case 37:
/* 797 */         modelIdentifier = ModelDestination.DESTINATION_EG1_SUSTAIN;
/* 798 */         paramArrayOfdouble[0] = -1.0D;
/*     */         break;
/*     */       case 38:
/* 801 */         modelIdentifier = ModelDestination.DESTINATION_EG1_RELEASE;
/*     */         break;
/*     */       case 46:
/* 804 */         modelIdentifier = ModelDestination.DESTINATION_KEYNUMBER;
/*     */         break;
/*     */       case 47:
/* 807 */         modelIdentifier = ModelDestination.DESTINATION_VELOCITY;
/*     */         break;
/*     */       
/*     */       case 51:
/* 811 */         paramArrayOfdouble[0] = 100.0D;
/* 812 */         modelIdentifier = ModelDestination.DESTINATION_PITCH;
/*     */         break;
/*     */       
/*     */       case 52:
/* 816 */         modelIdentifier = ModelDestination.DESTINATION_PITCH;
/*     */         break;
/*     */       
/*     */       case 48:
/* 820 */         modelIdentifier = ModelDestination.DESTINATION_GAIN;
/* 821 */         paramArrayOfdouble[0] = -0.3762870132923126D;
/*     */         break;
/*     */       
/*     */       case 6:
/* 825 */         modelIdentifier = ModelDestination.DESTINATION_PITCH;
/* 826 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_LFO2, false, true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 833 */         modelIdentifier = ModelDestination.DESTINATION_PITCH;
/* 834 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_LFO1, false, true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 841 */         modelIdentifier = ModelDestination.DESTINATION_FILTER_FREQ;
/* 842 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_LFO1, false, true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 13:
/* 849 */         modelIdentifier = ModelDestination.DESTINATION_GAIN;
/* 850 */         paramArrayOfdouble[0] = -0.3762870132923126D;
/* 851 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_LFO1, false, true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 858 */         modelIdentifier = ModelDestination.DESTINATION_PITCH;
/* 859 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_EG2, false, true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 11:
/* 866 */         modelIdentifier = ModelDestination.DESTINATION_FILTER_FREQ;
/* 867 */         paramArrayOfModelSource[0] = new ModelSource(ModelSource.SOURCE_EG2, false, true);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 876 */     if (modelIdentifier != null)
/* 877 */       return new ModelDestination(modelIdentifier); 
/* 878 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTimecentValue(ModelPerformer paramModelPerformer, ModelIdentifier paramModelIdentifier, short paramShort) {
/*     */     double d;
/* 884 */     if (paramShort == -12000) {
/* 885 */       d = Double.NEGATIVE_INFINITY;
/*     */     } else {
/* 887 */       d = paramShort;
/* 888 */     }  paramModelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(d, new ModelDestination(paramModelIdentifier)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addValue(ModelPerformer paramModelPerformer, ModelIdentifier paramModelIdentifier, short paramShort) {
/* 894 */     double d = paramShort;
/* 895 */     paramModelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(d, new ModelDestination(paramModelIdentifier)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addValue(ModelPerformer paramModelPerformer, ModelIdentifier paramModelIdentifier, double paramDouble) {
/* 901 */     double d = paramDouble;
/* 902 */     paramModelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(d, new ModelDestination(paramModelIdentifier)));
/*     */   }
/*     */ 
/*     */   
/*     */   private short getGeneratorValue(Map<Integer, Short> paramMap, int paramInt) {
/* 907 */     if (paramMap.containsKey(Integer.valueOf(paramInt)))
/* 908 */       return ((Short)paramMap.get(Integer.valueOf(paramInt))).shortValue(); 
/* 909 */     return SF2Region.getDefaultValue(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SF2Instrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */