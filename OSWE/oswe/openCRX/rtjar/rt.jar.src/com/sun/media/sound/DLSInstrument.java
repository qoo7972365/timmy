/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DLSInstrument
/*     */   extends ModelInstrument
/*     */ {
/*  45 */   int preset = 0;
/*  46 */   int bank = 0;
/*     */   boolean druminstrument = false;
/*  48 */   byte[] guid = null;
/*  49 */   DLSInfo info = new DLSInfo();
/*  50 */   List<DLSRegion> regions = new ArrayList<>();
/*  51 */   List<DLSModulator> modulators = new ArrayList<>();
/*     */   
/*     */   public DLSInstrument() {
/*  54 */     super(null, null, null, null);
/*     */   }
/*     */   
/*     */   public DLSInstrument(DLSSoundbank paramDLSSoundbank) {
/*  58 */     super(paramDLSSoundbank, null, null, null);
/*     */   }
/*     */   
/*     */   public DLSInfo getInfo() {
/*  62 */     return this.info;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  66 */     return this.info.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/*  70 */     this.info.name = paramString;
/*     */   }
/*     */   
/*     */   public ModelPatch getPatch() {
/*  74 */     return new ModelPatch(this.bank, this.preset, this.druminstrument);
/*     */   }
/*     */   
/*     */   public void setPatch(Patch paramPatch) {
/*  78 */     if (paramPatch instanceof ModelPatch && ((ModelPatch)paramPatch).isPercussion()) {
/*  79 */       this.druminstrument = true;
/*  80 */       this.bank = paramPatch.getBank();
/*  81 */       this.preset = paramPatch.getProgram();
/*     */     } else {
/*  83 */       this.druminstrument = false;
/*  84 */       this.bank = paramPatch.getBank();
/*  85 */       this.preset = paramPatch.getProgram();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getData() {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public List<DLSRegion> getRegions() {
/*  94 */     return this.regions;
/*     */   }
/*     */   
/*     */   public List<DLSModulator> getModulators() {
/*  98 */     return this.modulators;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 102 */     if (this.druminstrument) {
/* 103 */       return "Drumkit: " + this.info.name + " bank #" + this.bank + " preset #" + this.preset;
/*     */     }
/*     */     
/* 106 */     return "Instrument: " + this.info.name + " bank #" + this.bank + " preset #" + this.preset;
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelIdentifier convertToModelDest(int paramInt) {
/* 111 */     if (paramInt == 0)
/* 112 */       return null; 
/* 113 */     if (paramInt == 1)
/* 114 */       return ModelDestination.DESTINATION_GAIN; 
/* 115 */     if (paramInt == 3)
/* 116 */       return ModelDestination.DESTINATION_PITCH; 
/* 117 */     if (paramInt == 4) {
/* 118 */       return ModelDestination.DESTINATION_PAN;
/*     */     }
/* 120 */     if (paramInt == 260)
/* 121 */       return ModelDestination.DESTINATION_LFO1_FREQ; 
/* 122 */     if (paramInt == 261) {
/* 123 */       return ModelDestination.DESTINATION_LFO1_DELAY;
/*     */     }
/* 125 */     if (paramInt == 518)
/* 126 */       return ModelDestination.DESTINATION_EG1_ATTACK; 
/* 127 */     if (paramInt == 519)
/* 128 */       return ModelDestination.DESTINATION_EG1_DECAY; 
/* 129 */     if (paramInt == 521)
/* 130 */       return ModelDestination.DESTINATION_EG1_RELEASE; 
/* 131 */     if (paramInt == 522) {
/* 132 */       return ModelDestination.DESTINATION_EG1_SUSTAIN;
/*     */     }
/* 134 */     if (paramInt == 778)
/* 135 */       return ModelDestination.DESTINATION_EG2_ATTACK; 
/* 136 */     if (paramInt == 779)
/* 137 */       return ModelDestination.DESTINATION_EG2_DECAY; 
/* 138 */     if (paramInt == 781)
/* 139 */       return ModelDestination.DESTINATION_EG2_RELEASE; 
/* 140 */     if (paramInt == 782) {
/* 141 */       return ModelDestination.DESTINATION_EG2_SUSTAIN;
/*     */     }
/*     */     
/* 144 */     if (paramInt == 5) {
/* 145 */       return ModelDestination.DESTINATION_KEYNUMBER;
/*     */     }
/* 147 */     if (paramInt == 128)
/* 148 */       return ModelDestination.DESTINATION_CHORUS; 
/* 149 */     if (paramInt == 129) {
/* 150 */       return ModelDestination.DESTINATION_REVERB;
/*     */     }
/* 152 */     if (paramInt == 276)
/* 153 */       return ModelDestination.DESTINATION_LFO2_FREQ; 
/* 154 */     if (paramInt == 277) {
/* 155 */       return ModelDestination.DESTINATION_LFO2_DELAY;
/*     */     }
/* 157 */     if (paramInt == 523)
/* 158 */       return ModelDestination.DESTINATION_EG1_DELAY; 
/* 159 */     if (paramInt == 524)
/* 160 */       return ModelDestination.DESTINATION_EG1_HOLD; 
/* 161 */     if (paramInt == 525) {
/* 162 */       return ModelDestination.DESTINATION_EG1_SHUTDOWN;
/*     */     }
/* 164 */     if (paramInt == 783)
/* 165 */       return ModelDestination.DESTINATION_EG2_DELAY; 
/* 166 */     if (paramInt == 784) {
/* 167 */       return ModelDestination.DESTINATION_EG2_HOLD;
/*     */     }
/* 169 */     if (paramInt == 1280)
/* 170 */       return ModelDestination.DESTINATION_FILTER_FREQ; 
/* 171 */     if (paramInt == 1281) {
/* 172 */       return ModelDestination.DESTINATION_FILTER_Q;
/*     */     }
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   private ModelIdentifier convertToModelSrc(int paramInt) {
/* 178 */     if (paramInt == 0) {
/* 179 */       return null;
/*     */     }
/* 181 */     if (paramInt == 1)
/* 182 */       return ModelSource.SOURCE_LFO1; 
/* 183 */     if (paramInt == 2)
/* 184 */       return ModelSource.SOURCE_NOTEON_VELOCITY; 
/* 185 */     if (paramInt == 3)
/* 186 */       return ModelSource.SOURCE_NOTEON_KEYNUMBER; 
/* 187 */     if (paramInt == 4)
/* 188 */       return ModelSource.SOURCE_EG1; 
/* 189 */     if (paramInt == 5)
/* 190 */       return ModelSource.SOURCE_EG2; 
/* 191 */     if (paramInt == 6)
/* 192 */       return ModelSource.SOURCE_MIDI_PITCH; 
/* 193 */     if (paramInt == 129)
/* 194 */       return new ModelIdentifier("midi_cc", "1", 0); 
/* 195 */     if (paramInt == 135)
/* 196 */       return new ModelIdentifier("midi_cc", "7", 0); 
/* 197 */     if (paramInt == 138)
/* 198 */       return new ModelIdentifier("midi_cc", "10", 0); 
/* 199 */     if (paramInt == 139)
/* 200 */       return new ModelIdentifier("midi_cc", "11", 0); 
/* 201 */     if (paramInt == 256)
/* 202 */       return new ModelIdentifier("midi_rpn", "0", 0); 
/* 203 */     if (paramInt == 257) {
/* 204 */       return new ModelIdentifier("midi_rpn", "1", 0);
/*     */     }
/* 206 */     if (paramInt == 7)
/* 207 */       return ModelSource.SOURCE_MIDI_POLY_PRESSURE; 
/* 208 */     if (paramInt == 8)
/* 209 */       return ModelSource.SOURCE_MIDI_CHANNEL_PRESSURE; 
/* 210 */     if (paramInt == 9)
/* 211 */       return ModelSource.SOURCE_LFO2; 
/* 212 */     if (paramInt == 10) {
/* 213 */       return ModelSource.SOURCE_MIDI_CHANNEL_PRESSURE;
/*     */     }
/* 215 */     if (paramInt == 219)
/* 216 */       return new ModelIdentifier("midi_cc", "91", 0); 
/* 217 */     if (paramInt == 221) {
/* 218 */       return new ModelIdentifier("midi_cc", "93", 0);
/*     */     }
/* 220 */     return null;
/*     */   }
/*     */   private ModelConnectionBlock convertToModel(DLSModulator paramDLSModulator) {
/*     */     double d;
/* 224 */     ModelIdentifier modelIdentifier1 = convertToModelSrc(paramDLSModulator.getSource());
/* 225 */     ModelIdentifier modelIdentifier2 = convertToModelSrc(paramDLSModulator.getControl());
/*     */     
/* 227 */     ModelIdentifier modelIdentifier3 = convertToModelDest(paramDLSModulator.getDestination());
/*     */     
/* 229 */     int i = paramDLSModulator.getScale();
/*     */     
/* 231 */     if (i == Integer.MIN_VALUE) {
/* 232 */       d = Double.NEGATIVE_INFINITY;
/*     */     } else {
/* 234 */       d = i / 65536.0D;
/*     */     } 
/* 236 */     if (modelIdentifier3 != null) {
/* 237 */       ModelSource modelSource1 = null;
/* 238 */       ModelSource modelSource2 = null;
/* 239 */       ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock();
/* 240 */       if (modelIdentifier2 != null) {
/* 241 */         ModelSource modelSource = new ModelSource();
/* 242 */         if (modelIdentifier2 == ModelSource.SOURCE_MIDI_PITCH) {
/* 243 */           ((ModelStandardTransform)modelSource.getTransform()).setPolarity(true);
/*     */         }
/* 245 */         else if (modelIdentifier2 == ModelSource.SOURCE_LFO1 || modelIdentifier2 == ModelSource.SOURCE_LFO2) {
/*     */           
/* 247 */           ((ModelStandardTransform)modelSource.getTransform()).setPolarity(true);
/*     */         } 
/*     */         
/* 250 */         modelSource.setIdentifier(modelIdentifier2);
/* 251 */         modelConnectionBlock.addSource(modelSource);
/* 252 */         modelSource2 = modelSource;
/*     */       } 
/* 254 */       if (modelIdentifier1 != null) {
/* 255 */         ModelSource modelSource = new ModelSource();
/* 256 */         if (modelIdentifier1 == ModelSource.SOURCE_MIDI_PITCH) {
/* 257 */           ((ModelStandardTransform)modelSource.getTransform()).setPolarity(true);
/*     */         }
/* 259 */         else if (modelIdentifier1 == ModelSource.SOURCE_LFO1 || modelIdentifier1 == ModelSource.SOURCE_LFO2) {
/*     */           
/* 261 */           ((ModelStandardTransform)modelSource.getTransform()).setPolarity(true);
/*     */         } 
/*     */         
/* 264 */         modelSource.setIdentifier(modelIdentifier1);
/* 265 */         modelConnectionBlock.addSource(modelSource);
/* 266 */         modelSource1 = modelSource;
/*     */       } 
/* 268 */       ModelDestination modelDestination = new ModelDestination();
/* 269 */       modelDestination.setIdentifier(modelIdentifier3);
/* 270 */       modelConnectionBlock.setDestination(modelDestination);
/*     */       
/* 272 */       if (paramDLSModulator.getVersion() == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         if (paramDLSModulator.getTransform() == 1) {
/* 279 */           if (modelSource1 != null) {
/* 280 */             ((ModelStandardTransform)modelSource1.getTransform())
/* 281 */               .setTransform(1);
/*     */             
/* 283 */             ((ModelStandardTransform)modelSource1.getTransform())
/* 284 */               .setDirection(true);
/*     */           } 
/*     */           
/* 287 */           if (modelSource2 != null) {
/* 288 */             ((ModelStandardTransform)modelSource2.getTransform())
/* 289 */               .setTransform(1);
/*     */             
/* 291 */             ((ModelStandardTransform)modelSource2.getTransform())
/* 292 */               .setDirection(true);
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 297 */       } else if (paramDLSModulator.getVersion() == 2) {
/* 298 */         int j = paramDLSModulator.getTransform();
/* 299 */         int k = j >> 15 & 0x1;
/* 300 */         int m = j >> 14 & 0x1;
/* 301 */         int n = j >> 10 & 0x8;
/* 302 */         int i1 = j >> 9 & 0x1;
/* 303 */         int i2 = j >> 8 & 0x1;
/* 304 */         int i3 = j >> 4 & 0x8;
/*     */ 
/*     */         
/* 307 */         if (modelSource1 != null) {
/* 308 */           byte b = 0;
/* 309 */           if (n == 3)
/* 310 */             b = 3; 
/* 311 */           if (n == 1)
/* 312 */             b = 1; 
/* 313 */           if (n == 2)
/* 314 */             b = 2; 
/* 315 */           ((ModelStandardTransform)modelSource1.getTransform())
/* 316 */             .setTransform(b);
/* 317 */           ((ModelStandardTransform)modelSource1.getTransform())
/* 318 */             .setPolarity((m == 1));
/* 319 */           ((ModelStandardTransform)modelSource1.getTransform())
/* 320 */             .setDirection((k == 1));
/*     */         } 
/*     */ 
/*     */         
/* 324 */         if (modelSource2 != null) {
/* 325 */           byte b = 0;
/* 326 */           if (i3 == 3)
/* 327 */             b = 3; 
/* 328 */           if (i3 == 1)
/* 329 */             b = 1; 
/* 330 */           if (i3 == 2)
/* 331 */             b = 2; 
/* 332 */           ((ModelStandardTransform)modelSource2.getTransform())
/* 333 */             .setTransform(b);
/* 334 */           ((ModelStandardTransform)modelSource2.getTransform())
/* 335 */             .setPolarity((i2 == 1));
/* 336 */           ((ModelStandardTransform)modelSource2.getTransform())
/* 337 */             .setDirection((i1 == 1));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       modelConnectionBlock.setScale(d);
/*     */       
/* 359 */       return modelConnectionBlock;
/*     */     } 
/*     */     
/* 362 */     return null;
/*     */   }
/*     */   
/*     */   public ModelPerformer[] getPerformers() {
/* 366 */     ArrayList<ModelPerformer> arrayList = new ArrayList();
/*     */     
/* 368 */     HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 369 */     for (DLSModulator dLSModulator : getModulators()) {
/* 370 */       hashMap1.put(dLSModulator.getSource() + "x" + dLSModulator.getControl() + "=" + dLSModulator
/* 371 */           .getDestination(), dLSModulator);
/*     */     }
/*     */     
/* 374 */     HashMap<Object, Object> hashMap2 = new HashMap<>();
/*     */ 
/*     */     
/* 377 */     for (DLSRegion dLSRegion : this.regions) {
/* 378 */       ModelPerformer modelPerformer = new ModelPerformer();
/* 379 */       modelPerformer.setName(dLSRegion.getSample().getName());
/* 380 */       modelPerformer.setSelfNonExclusive(((dLSRegion.getFusoptions() & 0x1) != 0));
/*     */       
/* 382 */       modelPerformer.setExclusiveClass(dLSRegion.getExclusiveClass());
/* 383 */       modelPerformer.setKeyFrom(dLSRegion.getKeyfrom());
/* 384 */       modelPerformer.setKeyTo(dLSRegion.getKeyto());
/* 385 */       modelPerformer.setVelFrom(dLSRegion.getVelfrom());
/* 386 */       modelPerformer.setVelTo(dLSRegion.getVelto());
/*     */       
/* 388 */       hashMap2.clear();
/* 389 */       hashMap2.putAll(hashMap1);
/* 390 */       for (DLSModulator dLSModulator : dLSRegion.getModulators()) {
/* 391 */         hashMap2.put(dLSModulator.getSource() + "x" + dLSModulator.getControl() + "=" + dLSModulator
/* 392 */             .getDestination(), dLSModulator);
/*     */       }
/*     */       
/* 395 */       List<ModelConnectionBlock> list = modelPerformer.getConnectionBlocks();
/* 396 */       for (DLSModulator dLSModulator : hashMap2.values()) {
/* 397 */         ModelConnectionBlock modelConnectionBlock = convertToModel(dLSModulator);
/* 398 */         if (modelConnectionBlock != null) {
/* 399 */           list.add(modelConnectionBlock);
/*     */         }
/*     */       } 
/*     */       
/* 403 */       DLSSample dLSSample = dLSRegion.getSample();
/* 404 */       DLSSampleOptions dLSSampleOptions = dLSRegion.getSampleoptions();
/* 405 */       if (dLSSampleOptions == null) {
/* 406 */         dLSSampleOptions = dLSSample.getSampleoptions();
/*     */       }
/* 408 */       ModelByteBuffer modelByteBuffer = dLSSample.getDataBuffer();
/*     */       
/* 410 */       float f = (-dLSSampleOptions.unitynote * 100 + dLSSampleOptions.finetune);
/*     */ 
/*     */ 
/*     */       
/* 414 */       ModelByteBufferWavetable modelByteBufferWavetable = new ModelByteBufferWavetable(modelByteBuffer, dLSSample.getFormat(), f);
/* 415 */       modelByteBufferWavetable.setAttenuation(modelByteBufferWavetable.getAttenuation() / 65536.0F);
/* 416 */       if (dLSSampleOptions.getLoops().size() != 0) {
/* 417 */         DLSSampleLoop dLSSampleLoop = dLSSampleOptions.getLoops().get(0);
/* 418 */         modelByteBufferWavetable.setLoopStart((int)dLSSampleLoop.getStart());
/* 419 */         modelByteBufferWavetable.setLoopLength((int)dLSSampleLoop.getLength());
/* 420 */         if (dLSSampleLoop.getType() == 0L)
/* 421 */           modelByteBufferWavetable.setLoopType(1); 
/* 422 */         if (dLSSampleLoop.getType() == 1L) {
/* 423 */           modelByteBufferWavetable.setLoopType(2);
/*     */         } else {
/* 425 */           modelByteBufferWavetable.setLoopType(1);
/*     */         } 
/*     */       } 
/* 428 */       modelPerformer.getConnectionBlocks().add(new ModelConnectionBlock(1.0D, new ModelDestination(new ModelIdentifier("filter", "type", 1))));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 433 */       modelPerformer.getOscillators().add(modelByteBufferWavetable);
/*     */       
/* 435 */       arrayList.add(modelPerformer);
/*     */     } 
/*     */ 
/*     */     
/* 439 */     return arrayList.<ModelPerformer>toArray(new ModelPerformer[arrayList.size()]);
/*     */   }
/*     */   
/*     */   public byte[] getGuid() {
/* 443 */     return (this.guid == null) ? null : Arrays.copyOf(this.guid, this.guid.length);
/*     */   }
/*     */   
/*     */   public void setGuid(byte[] paramArrayOfbyte) {
/* 447 */     this.guid = (paramArrayOfbyte == null) ? null : Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DLSInstrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */