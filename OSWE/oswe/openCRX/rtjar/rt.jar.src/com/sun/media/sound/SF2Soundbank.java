/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SF2Soundbank
/*     */   implements Soundbank
/*     */ {
/*  56 */   int major = 2;
/*  57 */   int minor = 1;
/*     */   
/*  59 */   String targetEngine = "EMU8000";
/*     */   
/*  61 */   String name = "untitled";
/*     */   
/*  63 */   String romName = null;
/*     */   
/*  65 */   int romVersionMajor = -1;
/*  66 */   int romVersionMinor = -1;
/*     */   
/*  68 */   String creationDate = null;
/*     */   
/*  70 */   String engineers = null;
/*     */   
/*  72 */   String product = null;
/*     */   
/*  74 */   String copyright = null;
/*     */   
/*  76 */   String comments = null;
/*     */   
/*  78 */   String tools = null;
/*     */   
/*  80 */   private ModelByteBuffer sampleData = null;
/*  81 */   private ModelByteBuffer sampleData24 = null;
/*  82 */   private File sampleFile = null;
/*     */   private boolean largeFormat = false;
/*  84 */   private final List<SF2Instrument> instruments = new ArrayList<>();
/*  85 */   private final List<SF2Layer> layers = new ArrayList<>();
/*  86 */   private final List<SF2Sample> samples = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SF2Soundbank(URL paramURL) throws IOException {
/*  93 */     InputStream inputStream = paramURL.openStream();
/*     */     try {
/*  95 */       readSoundbank(inputStream);
/*     */     } finally {
/*  97 */       inputStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public SF2Soundbank(File paramFile) throws IOException {
/* 102 */     this.largeFormat = true;
/* 103 */     this.sampleFile = paramFile;
/* 104 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*     */     try {
/* 106 */       readSoundbank(fileInputStream);
/*     */     } finally {
/* 108 */       fileInputStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public SF2Soundbank(InputStream paramInputStream) throws IOException {
/* 113 */     readSoundbank(paramInputStream);
/*     */   }
/*     */   
/*     */   private void readSoundbank(InputStream paramInputStream) throws IOException {
/* 117 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/* 118 */     if (!rIFFReader.getFormat().equals("RIFF")) {
/* 119 */       throw new RIFFInvalidFormatException("Input stream is not a valid RIFF stream!");
/*     */     }
/*     */     
/* 122 */     if (!rIFFReader.getType().equals("sfbk")) {
/* 123 */       throw new RIFFInvalidFormatException("Input stream is not a valid SoundFont!");
/*     */     }
/*     */     
/* 126 */     while (rIFFReader.hasNextChunk()) {
/* 127 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/* 128 */       if (rIFFReader1.getFormat().equals("LIST")) {
/* 129 */         if (rIFFReader1.getType().equals("INFO"))
/* 130 */           readInfoChunk(rIFFReader1); 
/* 131 */         if (rIFFReader1.getType().equals("sdta"))
/* 132 */           readSdtaChunk(rIFFReader1); 
/* 133 */         if (rIFFReader1.getType().equals("pdta"))
/* 134 */           readPdtaChunk(rIFFReader1); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readInfoChunk(RIFFReader paramRIFFReader) throws IOException {
/* 140 */     while (paramRIFFReader.hasNextChunk()) {
/* 141 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/* 142 */       String str = rIFFReader.getFormat();
/* 143 */       if (str.equals("ifil")) {
/* 144 */         this.major = rIFFReader.readUnsignedShort();
/* 145 */         this.minor = rIFFReader.readUnsignedShort(); continue;
/* 146 */       }  if (str.equals("isng")) {
/* 147 */         this.targetEngine = rIFFReader.readString(rIFFReader.available()); continue;
/* 148 */       }  if (str.equals("INAM")) {
/* 149 */         this.name = rIFFReader.readString(rIFFReader.available()); continue;
/* 150 */       }  if (str.equals("irom")) {
/* 151 */         this.romName = rIFFReader.readString(rIFFReader.available()); continue;
/* 152 */       }  if (str.equals("iver")) {
/* 153 */         this.romVersionMajor = rIFFReader.readUnsignedShort();
/* 154 */         this.romVersionMinor = rIFFReader.readUnsignedShort(); continue;
/* 155 */       }  if (str.equals("ICRD")) {
/* 156 */         this.creationDate = rIFFReader.readString(rIFFReader.available()); continue;
/* 157 */       }  if (str.equals("IENG")) {
/* 158 */         this.engineers = rIFFReader.readString(rIFFReader.available()); continue;
/* 159 */       }  if (str.equals("IPRD")) {
/* 160 */         this.product = rIFFReader.readString(rIFFReader.available()); continue;
/* 161 */       }  if (str.equals("ICOP")) {
/* 162 */         this.copyright = rIFFReader.readString(rIFFReader.available()); continue;
/* 163 */       }  if (str.equals("ICMT")) {
/* 164 */         this.comments = rIFFReader.readString(rIFFReader.available()); continue;
/* 165 */       }  if (str.equals("ISFT")) {
/* 166 */         this.tools = rIFFReader.readString(rIFFReader.available());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readSdtaChunk(RIFFReader paramRIFFReader) throws IOException {
/* 173 */     while (paramRIFFReader.hasNextChunk()) {
/* 174 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/* 175 */       if (rIFFReader.getFormat().equals("smpl")) {
/* 176 */         if (!this.largeFormat) {
/* 177 */           byte[] arrayOfByte = new byte[rIFFReader.available()];
/*     */           
/* 179 */           int i = 0;
/* 180 */           int j = rIFFReader.available();
/* 181 */           while (i != j) {
/* 182 */             if (j - i > 65536) {
/* 183 */               rIFFReader.readFully(arrayOfByte, i, 65536);
/* 184 */               i += 65536; continue;
/*     */             } 
/* 186 */             rIFFReader.readFully(arrayOfByte, i, j - i);
/* 187 */             i = j;
/*     */           } 
/*     */ 
/*     */           
/* 191 */           this.sampleData = new ModelByteBuffer(arrayOfByte);
/*     */         } else {
/*     */           
/* 194 */           this
/* 195 */             .sampleData = new ModelByteBuffer(this.sampleFile, rIFFReader.getFilePointer(), rIFFReader.available());
/*     */         } 
/*     */       }
/* 198 */       if (rIFFReader.getFormat().equals("sm24")) {
/* 199 */         if (!this.largeFormat) {
/* 200 */           byte[] arrayOfByte = new byte[rIFFReader.available()];
/*     */ 
/*     */           
/* 203 */           int i = 0;
/* 204 */           int j = rIFFReader.available();
/* 205 */           while (i != j) {
/* 206 */             if (j - i > 65536) {
/* 207 */               rIFFReader.readFully(arrayOfByte, i, 65536);
/* 208 */               i += 65536; continue;
/*     */             } 
/* 210 */             rIFFReader.readFully(arrayOfByte, i, j - i);
/* 211 */             i = j;
/*     */           } 
/*     */ 
/*     */           
/* 215 */           this.sampleData24 = new ModelByteBuffer(arrayOfByte); continue;
/*     */         } 
/* 217 */         this
/* 218 */           .sampleData24 = new ModelByteBuffer(this.sampleFile, rIFFReader.getFilePointer(), rIFFReader.available());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPdtaChunk(RIFFReader paramRIFFReader) throws IOException {
/* 227 */     ArrayList<SF2Instrument> arrayList = new ArrayList();
/* 228 */     ArrayList<Integer> arrayList1 = new ArrayList();
/* 229 */     ArrayList<SF2InstrumentRegion> arrayList2 = new ArrayList();
/*     */     
/* 231 */     ArrayList<SF2InstrumentRegion> arrayList3 = new ArrayList();
/*     */ 
/*     */     
/* 234 */     ArrayList<SF2Layer> arrayList4 = new ArrayList();
/* 235 */     ArrayList<Integer> arrayList5 = new ArrayList();
/* 236 */     ArrayList<SF2LayerRegion> arrayList6 = new ArrayList();
/*     */     
/* 238 */     ArrayList<SF2LayerRegion> arrayList7 = new ArrayList();
/*     */ 
/*     */     
/* 241 */     while (paramRIFFReader.hasNextChunk()) {
/* 242 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/* 243 */       String str = rIFFReader.getFormat();
/* 244 */       if (str.equals("phdr")) {
/*     */         
/* 246 */         if (rIFFReader.available() % 38 != 0)
/* 247 */           throw new RIFFInvalidDataException(); 
/* 248 */         int i = rIFFReader.available() / 38;
/* 249 */         for (byte b = 0; b < i; b++) {
/* 250 */           SF2Instrument sF2Instrument = new SF2Instrument(this);
/* 251 */           sF2Instrument.name = rIFFReader.readString(20);
/* 252 */           sF2Instrument.preset = rIFFReader.readUnsignedShort();
/* 253 */           sF2Instrument.bank = rIFFReader.readUnsignedShort();
/* 254 */           arrayList1.add(Integer.valueOf(rIFFReader.readUnsignedShort()));
/* 255 */           sF2Instrument.library = rIFFReader.readUnsignedInt();
/* 256 */           sF2Instrument.genre = rIFFReader.readUnsignedInt();
/* 257 */           sF2Instrument.morphology = rIFFReader.readUnsignedInt();
/* 258 */           arrayList.add(sF2Instrument);
/* 259 */           if (b != i - 1)
/* 260 */             this.instruments.add(sF2Instrument); 
/*     */         }  continue;
/* 262 */       }  if (str.equals("pbag")) {
/*     */         
/* 264 */         if (rIFFReader.available() % 4 != 0)
/* 265 */           throw new RIFFInvalidDataException(); 
/* 266 */         int i = rIFFReader.available() / 4;
/*     */ 
/*     */ 
/*     */         
/* 270 */         int j = rIFFReader.readUnsignedShort();
/* 271 */         int k = rIFFReader.readUnsignedShort();
/* 272 */         while (arrayList2.size() < j)
/* 273 */           arrayList2.add(null); 
/* 274 */         while (arrayList3.size() < k)
/* 275 */           arrayList3.add(null); 
/* 276 */         i--;
/*     */ 
/*     */         
/* 279 */         if (arrayList1.isEmpty()) {
/* 280 */           throw new RIFFInvalidDataException();
/*     */         }
/* 282 */         j = ((Integer)arrayList1.get(0)).intValue();
/*     */         
/* 284 */         for (k = 0; k < j; k++) {
/* 285 */           if (i == 0)
/* 286 */             throw new RIFFInvalidDataException(); 
/* 287 */           int m = rIFFReader.readUnsignedShort();
/* 288 */           int n = rIFFReader.readUnsignedShort();
/* 289 */           while (arrayList2.size() < m)
/* 290 */             arrayList2.add(null); 
/* 291 */           while (arrayList3.size() < n)
/* 292 */             arrayList3.add(null); 
/* 293 */           i--;
/*     */         } 
/*     */         
/* 296 */         for (k = 0; k < arrayList1.size() - 1; k++) {
/*     */           
/* 298 */           int m = ((Integer)arrayList1.get(k + 1)).intValue() - ((Integer)arrayList1.get(k)).intValue();
/* 299 */           SF2Instrument sF2Instrument = arrayList.get(k);
/* 300 */           for (byte b = 0; b < m; b++) {
/* 301 */             if (i == 0)
/* 302 */               throw new RIFFInvalidDataException(); 
/* 303 */             int n = rIFFReader.readUnsignedShort();
/* 304 */             int i1 = rIFFReader.readUnsignedShort();
/* 305 */             SF2InstrumentRegion sF2InstrumentRegion = new SF2InstrumentRegion();
/* 306 */             sF2Instrument.regions.add(sF2InstrumentRegion);
/* 307 */             while (arrayList2.size() < n)
/* 308 */               arrayList2.add(sF2InstrumentRegion); 
/* 309 */             while (arrayList3.size() < i1)
/* 310 */               arrayList3.add(sF2InstrumentRegion); 
/* 311 */             i--;
/*     */           } 
/*     */         }  continue;
/* 314 */       }  if (str.equals("pmod")) {
/*     */         
/* 316 */         for (byte b = 0; b < arrayList3.size(); b++) {
/* 317 */           SF2Modulator sF2Modulator = new SF2Modulator();
/* 318 */           sF2Modulator.sourceOperator = rIFFReader.readUnsignedShort();
/* 319 */           sF2Modulator.destinationOperator = rIFFReader.readUnsignedShort();
/* 320 */           sF2Modulator.amount = rIFFReader.readShort();
/* 321 */           sF2Modulator.amountSourceOperator = rIFFReader.readUnsignedShort();
/* 322 */           sF2Modulator.transportOperator = rIFFReader.readUnsignedShort();
/* 323 */           SF2InstrumentRegion sF2InstrumentRegion = arrayList3.get(b);
/* 324 */           if (sF2InstrumentRegion != null)
/* 325 */             sF2InstrumentRegion.modulators.add(sF2Modulator); 
/*     */         }  continue;
/* 327 */       }  if (str.equals("pgen")) {
/*     */         
/* 329 */         for (byte b = 0; b < arrayList2.size(); b++) {
/* 330 */           int i = rIFFReader.readUnsignedShort();
/* 331 */           short s = rIFFReader.readShort();
/* 332 */           SF2InstrumentRegion sF2InstrumentRegion = arrayList2.get(b);
/* 333 */           if (sF2InstrumentRegion != null)
/* 334 */             sF2InstrumentRegion.generators.put(Integer.valueOf(i), Short.valueOf(s)); 
/*     */         }  continue;
/* 336 */       }  if (str.equals("inst")) {
/*     */         
/* 338 */         if (rIFFReader.available() % 22 != 0)
/* 339 */           throw new RIFFInvalidDataException(); 
/* 340 */         int i = rIFFReader.available() / 22;
/* 341 */         for (byte b = 0; b < i; b++) {
/* 342 */           SF2Layer sF2Layer = new SF2Layer(this);
/* 343 */           sF2Layer.name = rIFFReader.readString(20);
/* 344 */           arrayList5.add(Integer.valueOf(rIFFReader.readUnsignedShort()));
/* 345 */           arrayList4.add(sF2Layer);
/* 346 */           if (b != i - 1)
/* 347 */             this.layers.add(sF2Layer); 
/*     */         }  continue;
/* 349 */       }  if (str.equals("ibag")) {
/*     */         
/* 351 */         if (rIFFReader.available() % 4 != 0)
/* 352 */           throw new RIFFInvalidDataException(); 
/* 353 */         int i = rIFFReader.available() / 4;
/*     */ 
/*     */ 
/*     */         
/* 357 */         int j = rIFFReader.readUnsignedShort();
/* 358 */         int k = rIFFReader.readUnsignedShort();
/* 359 */         while (arrayList6.size() < j)
/* 360 */           arrayList6.add(null); 
/* 361 */         while (arrayList7.size() < k)
/* 362 */           arrayList7.add(null); 
/* 363 */         i--;
/*     */ 
/*     */         
/* 366 */         if (arrayList5.isEmpty()) {
/* 367 */           throw new RIFFInvalidDataException();
/*     */         }
/* 369 */         j = ((Integer)arrayList5.get(0)).intValue();
/*     */         
/* 371 */         for (k = 0; k < j; k++) {
/* 372 */           if (i == 0)
/* 373 */             throw new RIFFInvalidDataException(); 
/* 374 */           int m = rIFFReader.readUnsignedShort();
/* 375 */           int n = rIFFReader.readUnsignedShort();
/* 376 */           while (arrayList6.size() < m)
/* 377 */             arrayList6.add(null); 
/* 378 */           while (arrayList7.size() < n)
/* 379 */             arrayList7.add(null); 
/* 380 */           i--;
/*     */         } 
/*     */         
/* 383 */         for (k = 0; k < arrayList5.size() - 1; k++) {
/* 384 */           int m = ((Integer)arrayList5.get(k + 1)).intValue() - ((Integer)arrayList5.get(k)).intValue();
/* 385 */           SF2Layer sF2Layer = this.layers.get(k);
/* 386 */           for (byte b = 0; b < m; b++) {
/* 387 */             if (i == 0)
/* 388 */               throw new RIFFInvalidDataException(); 
/* 389 */             int n = rIFFReader.readUnsignedShort();
/* 390 */             int i1 = rIFFReader.readUnsignedShort();
/* 391 */             SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 392 */             sF2Layer.regions.add(sF2LayerRegion);
/* 393 */             while (arrayList6.size() < n)
/* 394 */               arrayList6.add(sF2LayerRegion); 
/* 395 */             while (arrayList7.size() < i1)
/* 396 */               arrayList7.add(sF2LayerRegion); 
/* 397 */             i--;
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 401 */       if (str.equals("imod")) {
/*     */         
/* 403 */         for (byte b = 0; b < arrayList7.size(); b++) {
/* 404 */           SF2Modulator sF2Modulator = new SF2Modulator();
/* 405 */           sF2Modulator.sourceOperator = rIFFReader.readUnsignedShort();
/* 406 */           sF2Modulator.destinationOperator = rIFFReader.readUnsignedShort();
/* 407 */           sF2Modulator.amount = rIFFReader.readShort();
/* 408 */           sF2Modulator.amountSourceOperator = rIFFReader.readUnsignedShort();
/* 409 */           sF2Modulator.transportOperator = rIFFReader.readUnsignedShort();
/* 410 */           if (b < 0 || b >= arrayList6.size()) {
/* 411 */             throw new RIFFInvalidDataException();
/*     */           }
/* 413 */           SF2LayerRegion sF2LayerRegion = arrayList6.get(b);
/* 414 */           if (sF2LayerRegion != null)
/* 415 */             sF2LayerRegion.modulators.add(sF2Modulator); 
/*     */         }  continue;
/* 417 */       }  if (str.equals("igen")) {
/*     */         
/* 419 */         for (byte b = 0; b < arrayList6.size(); b++) {
/* 420 */           int i = rIFFReader.readUnsignedShort();
/* 421 */           short s = rIFFReader.readShort();
/* 422 */           SF2LayerRegion sF2LayerRegion = arrayList6.get(b);
/* 423 */           if (sF2LayerRegion != null)
/* 424 */             sF2LayerRegion.generators.put(Integer.valueOf(i), Short.valueOf(s)); 
/*     */         }  continue;
/* 426 */       }  if (str.equals("shdr")) {
/*     */         
/* 428 */         if (rIFFReader.available() % 46 != 0)
/* 429 */           throw new RIFFInvalidDataException(); 
/* 430 */         int i = rIFFReader.available() / 46;
/* 431 */         for (byte b = 0; b < i; b++) {
/* 432 */           SF2Sample sF2Sample = new SF2Sample(this);
/* 433 */           sF2Sample.name = rIFFReader.readString(20);
/* 434 */           long l1 = rIFFReader.readUnsignedInt();
/* 435 */           long l2 = rIFFReader.readUnsignedInt();
/* 436 */           if (this.sampleData != null)
/* 437 */             sF2Sample.data = this.sampleData.subbuffer(l1 * 2L, l2 * 2L, true); 
/* 438 */           if (this.sampleData24 != null) {
/* 439 */             sF2Sample.data24 = this.sampleData24.subbuffer(l1, l2, true);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 447 */           sF2Sample.startLoop = rIFFReader.readUnsignedInt() - l1;
/* 448 */           sF2Sample.endLoop = rIFFReader.readUnsignedInt() - l1;
/* 449 */           if (sF2Sample.startLoop < 0L)
/* 450 */             sF2Sample.startLoop = -1L; 
/* 451 */           if (sF2Sample.endLoop < 0L)
/* 452 */             sF2Sample.endLoop = -1L; 
/* 453 */           sF2Sample.sampleRate = rIFFReader.readUnsignedInt();
/* 454 */           sF2Sample.originalPitch = rIFFReader.readUnsignedByte();
/* 455 */           sF2Sample.pitchCorrection = rIFFReader.readByte();
/* 456 */           sF2Sample.sampleLink = rIFFReader.readUnsignedShort();
/* 457 */           sF2Sample.sampleType = rIFFReader.readUnsignedShort();
/* 458 */           if (b != i - 1) {
/* 459 */             this.samples.add(sF2Sample);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 464 */     Iterator<SF2Layer> iterator = this.layers.iterator();
/* 465 */     while (iterator.hasNext()) {
/* 466 */       SF2Layer sF2Layer = iterator.next();
/* 467 */       Iterator<SF2LayerRegion> iterator2 = sF2Layer.regions.iterator();
/* 468 */       SF2LayerRegion sF2LayerRegion = null;
/* 469 */       while (iterator2.hasNext()) {
/* 470 */         SF2LayerRegion sF2LayerRegion1 = iterator2.next();
/* 471 */         if (sF2LayerRegion1.generators.get(Integer.valueOf(53)) != null) {
/* 472 */           short s = ((Short)sF2LayerRegion1.generators.get(
/* 473 */               Integer.valueOf(53))).shortValue();
/* 474 */           sF2LayerRegion1.generators.remove(Integer.valueOf(53));
/* 475 */           if (s < 0 || s >= this.samples.size()) {
/* 476 */             throw new RIFFInvalidDataException();
/*     */           }
/* 478 */           sF2LayerRegion1.sample = this.samples.get(s); continue;
/*     */         } 
/* 480 */         sF2LayerRegion = sF2LayerRegion1;
/*     */       } 
/*     */       
/* 483 */       if (sF2LayerRegion != null) {
/* 484 */         sF2Layer.getRegions().remove(sF2LayerRegion);
/* 485 */         SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 486 */         sF2GlobalRegion.generators = sF2LayerRegion.generators;
/* 487 */         sF2GlobalRegion.modulators = sF2LayerRegion.modulators;
/* 488 */         sF2Layer.setGlobalZone(sF2GlobalRegion);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 493 */     Iterator<SF2Instrument> iterator1 = this.instruments.iterator();
/* 494 */     while (iterator1.hasNext()) {
/* 495 */       SF2Instrument sF2Instrument = iterator1.next();
/* 496 */       Iterator<SF2InstrumentRegion> iterator2 = sF2Instrument.regions.iterator();
/* 497 */       SF2InstrumentRegion sF2InstrumentRegion = null;
/* 498 */       while (iterator2.hasNext()) {
/* 499 */         SF2InstrumentRegion sF2InstrumentRegion1 = iterator2.next();
/* 500 */         if (sF2InstrumentRegion1.generators.get(Integer.valueOf(41)) != null) {
/* 501 */           short s = ((Short)sF2InstrumentRegion1.generators.get(
/* 502 */               Integer.valueOf(41))).shortValue();
/* 503 */           sF2InstrumentRegion1.generators.remove(Integer.valueOf(41));
/* 504 */           if (s < 0 || s >= this.layers.size()) {
/* 505 */             throw new RIFFInvalidDataException();
/*     */           }
/* 507 */           sF2InstrumentRegion1.layer = this.layers.get(s); continue;
/*     */         } 
/* 509 */         sF2InstrumentRegion = sF2InstrumentRegion1;
/*     */       } 
/*     */ 
/*     */       
/* 513 */       if (sF2InstrumentRegion != null) {
/* 514 */         sF2Instrument.getRegions().remove(sF2InstrumentRegion);
/* 515 */         SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 516 */         sF2GlobalRegion.generators = sF2InstrumentRegion.generators;
/* 517 */         sF2GlobalRegion.modulators = sF2InstrumentRegion.modulators;
/* 518 */         sF2Instrument.setGlobalZone(sF2GlobalRegion);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(String paramString) throws IOException {
/* 525 */     writeSoundbank(new RIFFWriter(paramString, "sfbk"));
/*     */   }
/*     */   
/*     */   public void save(File paramFile) throws IOException {
/* 529 */     writeSoundbank(new RIFFWriter(paramFile, "sfbk"));
/*     */   }
/*     */   
/*     */   public void save(OutputStream paramOutputStream) throws IOException {
/* 533 */     writeSoundbank(new RIFFWriter(paramOutputStream, "sfbk"));
/*     */   }
/*     */   
/*     */   private void writeSoundbank(RIFFWriter paramRIFFWriter) throws IOException {
/* 537 */     writeInfo(paramRIFFWriter.writeList("INFO"));
/* 538 */     writeSdtaChunk(paramRIFFWriter.writeList("sdta"));
/* 539 */     writePdtaChunk(paramRIFFWriter.writeList("pdta"));
/* 540 */     paramRIFFWriter.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeInfoStringChunk(RIFFWriter paramRIFFWriter, String paramString1, String paramString2) throws IOException {
/* 545 */     if (paramString2 == null)
/*     */       return; 
/* 547 */     RIFFWriter rIFFWriter = paramRIFFWriter.writeChunk(paramString1);
/* 548 */     rIFFWriter.writeString(paramString2);
/* 549 */     int i = (paramString2.getBytes("ascii")).length;
/* 550 */     rIFFWriter.write(0);
/* 551 */     i++;
/* 552 */     if (i % 2 != 0)
/* 553 */       rIFFWriter.write(0); 
/*     */   }
/*     */   
/*     */   private void writeInfo(RIFFWriter paramRIFFWriter) throws IOException {
/* 557 */     if (this.targetEngine == null)
/* 558 */       this.targetEngine = "EMU8000"; 
/* 559 */     if (this.name == null) {
/* 560 */       this.name = "";
/*     */     }
/* 562 */     RIFFWriter rIFFWriter = paramRIFFWriter.writeChunk("ifil");
/* 563 */     rIFFWriter.writeUnsignedShort(this.major);
/* 564 */     rIFFWriter.writeUnsignedShort(this.minor);
/* 565 */     writeInfoStringChunk(paramRIFFWriter, "isng", this.targetEngine);
/* 566 */     writeInfoStringChunk(paramRIFFWriter, "INAM", this.name);
/* 567 */     writeInfoStringChunk(paramRIFFWriter, "irom", this.romName);
/* 568 */     if (this.romVersionMajor != -1) {
/* 569 */       RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("iver");
/* 570 */       rIFFWriter1.writeUnsignedShort(this.romVersionMajor);
/* 571 */       rIFFWriter1.writeUnsignedShort(this.romVersionMinor);
/*     */     } 
/* 573 */     writeInfoStringChunk(paramRIFFWriter, "ICRD", this.creationDate);
/* 574 */     writeInfoStringChunk(paramRIFFWriter, "IENG", this.engineers);
/* 575 */     writeInfoStringChunk(paramRIFFWriter, "IPRD", this.product);
/* 576 */     writeInfoStringChunk(paramRIFFWriter, "ICOP", this.copyright);
/* 577 */     writeInfoStringChunk(paramRIFFWriter, "ICMT", this.comments);
/* 578 */     writeInfoStringChunk(paramRIFFWriter, "ISFT", this.tools);
/*     */     
/* 580 */     paramRIFFWriter.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeSdtaChunk(RIFFWriter paramRIFFWriter) throws IOException {
/* 585 */     byte[] arrayOfByte = new byte[32];
/*     */     
/* 587 */     RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("smpl");
/* 588 */     for (SF2Sample sF2Sample : this.samples) {
/* 589 */       ModelByteBuffer modelByteBuffer = sF2Sample.getDataBuffer();
/* 590 */       modelByteBuffer.writeTo(rIFFWriter1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 596 */       rIFFWriter1.write(arrayOfByte);
/* 597 */       rIFFWriter1.write(arrayOfByte);
/*     */     } 
/* 599 */     if (this.major < 2)
/*     */       return; 
/* 601 */     if (this.major == 2 && this.minor < 4) {
/*     */       return;
/*     */     }
/*     */     
/* 605 */     for (SF2Sample sF2Sample : this.samples) {
/* 606 */       ModelByteBuffer modelByteBuffer = sF2Sample.getData24Buffer();
/* 607 */       if (modelByteBuffer == null) {
/*     */         return;
/*     */       }
/*     */     } 
/* 611 */     RIFFWriter rIFFWriter2 = paramRIFFWriter.writeChunk("sm24");
/* 612 */     for (SF2Sample sF2Sample : this.samples) {
/* 613 */       ModelByteBuffer modelByteBuffer = sF2Sample.getData24Buffer();
/* 614 */       modelByteBuffer.writeTo(rIFFWriter2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 619 */       rIFFWriter1.write(arrayOfByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeModulators(RIFFWriter paramRIFFWriter, List<SF2Modulator> paramList) throws IOException {
/* 625 */     for (SF2Modulator sF2Modulator : paramList) {
/* 626 */       paramRIFFWriter.writeUnsignedShort(sF2Modulator.sourceOperator);
/* 627 */       paramRIFFWriter.writeUnsignedShort(sF2Modulator.destinationOperator);
/* 628 */       paramRIFFWriter.writeShort(sF2Modulator.amount);
/* 629 */       paramRIFFWriter.writeUnsignedShort(sF2Modulator.amountSourceOperator);
/* 630 */       paramRIFFWriter.writeUnsignedShort(sF2Modulator.transportOperator);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGenerators(RIFFWriter paramRIFFWriter, Map<Integer, Short> paramMap) throws IOException {
/* 636 */     Short short_1 = paramMap.get(Integer.valueOf(43));
/* 637 */     Short short_2 = paramMap.get(Integer.valueOf(44));
/* 638 */     if (short_1 != null) {
/* 639 */       paramRIFFWriter.writeUnsignedShort(43);
/* 640 */       paramRIFFWriter.writeShort(short_1.shortValue());
/*     */     } 
/* 642 */     if (short_2 != null) {
/* 643 */       paramRIFFWriter.writeUnsignedShort(44);
/* 644 */       paramRIFFWriter.writeShort(short_2.shortValue());
/*     */     } 
/* 646 */     for (Map.Entry<Integer, Short> entry : paramMap.entrySet()) {
/* 647 */       if (((Integer)entry.getKey()).intValue() == 43)
/*     */         continue; 
/* 649 */       if (((Integer)entry.getKey()).intValue() == 44)
/*     */         continue; 
/* 651 */       paramRIFFWriter.writeUnsignedShort(((Integer)entry.getKey()).intValue());
/* 652 */       paramRIFFWriter.writeShort(((Short)entry.getValue()).shortValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writePdtaChunk(RIFFWriter paramRIFFWriter) throws IOException {
/* 658 */     RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("phdr");
/* 659 */     int i = 0;
/* 660 */     for (SF2Instrument sF2Instrument : this.instruments) {
/* 661 */       rIFFWriter1.writeString(sF2Instrument.name, 20);
/* 662 */       rIFFWriter1.writeUnsignedShort(sF2Instrument.preset);
/* 663 */       rIFFWriter1.writeUnsignedShort(sF2Instrument.bank);
/* 664 */       rIFFWriter1.writeUnsignedShort(i);
/* 665 */       if (sF2Instrument.getGlobalRegion() != null)
/* 666 */         i++; 
/* 667 */       i += sF2Instrument.getRegions().size();
/* 668 */       rIFFWriter1.writeUnsignedInt(sF2Instrument.library);
/* 669 */       rIFFWriter1.writeUnsignedInt(sF2Instrument.genre);
/* 670 */       rIFFWriter1.writeUnsignedInt(sF2Instrument.morphology);
/*     */     } 
/* 672 */     rIFFWriter1.writeString("EOP", 20);
/* 673 */     rIFFWriter1.writeUnsignedShort(0);
/* 674 */     rIFFWriter1.writeUnsignedShort(0);
/* 675 */     rIFFWriter1.writeUnsignedShort(i);
/* 676 */     rIFFWriter1.writeUnsignedInt(0L);
/* 677 */     rIFFWriter1.writeUnsignedInt(0L);
/* 678 */     rIFFWriter1.writeUnsignedInt(0L);
/*     */ 
/*     */     
/* 681 */     RIFFWriter rIFFWriter2 = paramRIFFWriter.writeChunk("pbag");
/* 682 */     int j = 0;
/* 683 */     int k = 0;
/* 684 */     for (SF2Instrument sF2Instrument : this.instruments) {
/* 685 */       if (sF2Instrument.getGlobalRegion() != null) {
/* 686 */         rIFFWriter2.writeUnsignedShort(j);
/* 687 */         rIFFWriter2.writeUnsignedShort(k);
/* 688 */         j += sF2Instrument.getGlobalRegion().getGenerators().size();
/* 689 */         k += sF2Instrument.getGlobalRegion().getModulators().size();
/*     */       } 
/* 691 */       for (SF2InstrumentRegion sF2InstrumentRegion : sF2Instrument.getRegions()) {
/* 692 */         rIFFWriter2.writeUnsignedShort(j);
/* 693 */         rIFFWriter2.writeUnsignedShort(k);
/* 694 */         if (this.layers.indexOf(sF2InstrumentRegion.layer) != -1)
/*     */         {
/* 696 */           j++;
/*     */         }
/* 698 */         j += sF2InstrumentRegion.getGenerators().size();
/* 699 */         k += sF2InstrumentRegion.getModulators().size();
/*     */       } 
/*     */     } 
/*     */     
/* 703 */     rIFFWriter2.writeUnsignedShort(j);
/* 704 */     rIFFWriter2.writeUnsignedShort(k);
/*     */     
/* 706 */     RIFFWriter rIFFWriter3 = paramRIFFWriter.writeChunk("pmod");
/* 707 */     for (SF2Instrument sF2Instrument : this.instruments) {
/* 708 */       if (sF2Instrument.getGlobalRegion() != null) {
/* 709 */         writeModulators(rIFFWriter3, sF2Instrument
/* 710 */             .getGlobalRegion().getModulators());
/*     */       }
/* 712 */       for (SF2InstrumentRegion sF2InstrumentRegion : sF2Instrument.getRegions())
/* 713 */         writeModulators(rIFFWriter3, sF2InstrumentRegion.getModulators()); 
/*     */     } 
/* 715 */     rIFFWriter3.write(new byte[10]);
/*     */     
/* 717 */     RIFFWriter rIFFWriter4 = paramRIFFWriter.writeChunk("pgen");
/* 718 */     for (SF2Instrument sF2Instrument : this.instruments) {
/* 719 */       if (sF2Instrument.getGlobalRegion() != null) {
/* 720 */         writeGenerators(rIFFWriter4, sF2Instrument
/* 721 */             .getGlobalRegion().getGenerators());
/*     */       }
/* 723 */       for (SF2InstrumentRegion sF2InstrumentRegion : sF2Instrument.getRegions()) {
/* 724 */         writeGenerators(rIFFWriter4, sF2InstrumentRegion.getGenerators());
/* 725 */         int i2 = this.layers.indexOf(sF2InstrumentRegion.layer);
/* 726 */         if (i2 != -1) {
/* 727 */           rIFFWriter4.writeUnsignedShort(41);
/* 728 */           rIFFWriter4.writeShort((short)i2);
/*     */         } 
/*     */       } 
/*     */     } 
/* 732 */     rIFFWriter4.write(new byte[4]);
/*     */     
/* 734 */     RIFFWriter rIFFWriter5 = paramRIFFWriter.writeChunk("inst");
/* 735 */     int m = 0;
/* 736 */     for (SF2Layer sF2Layer : this.layers) {
/* 737 */       rIFFWriter5.writeString(sF2Layer.name, 20);
/* 738 */       rIFFWriter5.writeUnsignedShort(m);
/* 739 */       if (sF2Layer.getGlobalRegion() != null)
/* 740 */         m++; 
/* 741 */       m += sF2Layer.getRegions().size();
/*     */     } 
/* 743 */     rIFFWriter5.writeString("EOI", 20);
/* 744 */     rIFFWriter5.writeUnsignedShort(m);
/*     */ 
/*     */     
/* 747 */     RIFFWriter rIFFWriter6 = paramRIFFWriter.writeChunk("ibag");
/* 748 */     int n = 0;
/* 749 */     int i1 = 0;
/* 750 */     for (SF2Layer sF2Layer : this.layers) {
/* 751 */       if (sF2Layer.getGlobalRegion() != null) {
/* 752 */         rIFFWriter6.writeUnsignedShort(n);
/* 753 */         rIFFWriter6.writeUnsignedShort(i1);
/* 754 */         n += sF2Layer
/* 755 */           .getGlobalRegion().getGenerators().size();
/* 756 */         i1 += sF2Layer
/* 757 */           .getGlobalRegion().getModulators().size();
/*     */       } 
/* 759 */       for (SF2LayerRegion sF2LayerRegion : sF2Layer.getRegions()) {
/* 760 */         rIFFWriter6.writeUnsignedShort(n);
/* 761 */         rIFFWriter6.writeUnsignedShort(i1);
/* 762 */         if (this.samples.indexOf(sF2LayerRegion.sample) != -1)
/*     */         {
/* 764 */           n++;
/*     */         }
/* 766 */         n += sF2LayerRegion.getGenerators().size();
/* 767 */         i1 += sF2LayerRegion.getModulators().size();
/*     */       } 
/*     */     } 
/*     */     
/* 771 */     rIFFWriter6.writeUnsignedShort(n);
/* 772 */     rIFFWriter6.writeUnsignedShort(i1);
/*     */ 
/*     */     
/* 775 */     RIFFWriter rIFFWriter7 = paramRIFFWriter.writeChunk("imod");
/* 776 */     for (SF2Layer sF2Layer : this.layers) {
/* 777 */       if (sF2Layer.getGlobalRegion() != null) {
/* 778 */         writeModulators(rIFFWriter7, sF2Layer
/* 779 */             .getGlobalRegion().getModulators());
/*     */       }
/* 781 */       for (SF2LayerRegion sF2LayerRegion : sF2Layer.getRegions())
/* 782 */         writeModulators(rIFFWriter7, sF2LayerRegion.getModulators()); 
/*     */     } 
/* 784 */     rIFFWriter7.write(new byte[10]);
/*     */     
/* 786 */     RIFFWriter rIFFWriter8 = paramRIFFWriter.writeChunk("igen");
/* 787 */     for (SF2Layer sF2Layer : this.layers) {
/* 788 */       if (sF2Layer.getGlobalRegion() != null) {
/* 789 */         writeGenerators(rIFFWriter8, sF2Layer
/* 790 */             .getGlobalRegion().getGenerators());
/*     */       }
/* 792 */       for (SF2LayerRegion sF2LayerRegion : sF2Layer.getRegions()) {
/* 793 */         writeGenerators(rIFFWriter8, sF2LayerRegion.getGenerators());
/* 794 */         int i2 = this.samples.indexOf(sF2LayerRegion.sample);
/* 795 */         if (i2 != -1) {
/* 796 */           rIFFWriter8.writeUnsignedShort(53);
/* 797 */           rIFFWriter8.writeShort((short)i2);
/*     */         } 
/*     */       } 
/*     */     } 
/* 801 */     rIFFWriter8.write(new byte[4]);
/*     */ 
/*     */     
/* 804 */     RIFFWriter rIFFWriter9 = paramRIFFWriter.writeChunk("shdr");
/* 805 */     long l = 0L;
/* 806 */     for (SF2Sample sF2Sample : this.samples) {
/* 807 */       rIFFWriter9.writeString(sF2Sample.name, 20);
/* 808 */       long l1 = l;
/* 809 */       l += sF2Sample.data.capacity() / 2L;
/* 810 */       long l2 = l;
/* 811 */       long l3 = sF2Sample.startLoop + l1;
/* 812 */       long l4 = sF2Sample.endLoop + l1;
/* 813 */       if (l3 < l1)
/* 814 */         l3 = l1; 
/* 815 */       if (l4 > l2)
/* 816 */         l4 = l2; 
/* 817 */       rIFFWriter9.writeUnsignedInt(l1);
/* 818 */       rIFFWriter9.writeUnsignedInt(l2);
/* 819 */       rIFFWriter9.writeUnsignedInt(l3);
/* 820 */       rIFFWriter9.writeUnsignedInt(l4);
/* 821 */       rIFFWriter9.writeUnsignedInt(sF2Sample.sampleRate);
/* 822 */       rIFFWriter9.writeUnsignedByte(sF2Sample.originalPitch);
/* 823 */       rIFFWriter9.writeByte(sF2Sample.pitchCorrection);
/* 824 */       rIFFWriter9.writeUnsignedShort(sF2Sample.sampleLink);
/* 825 */       rIFFWriter9.writeUnsignedShort(sF2Sample.sampleType);
/* 826 */       l += 32L;
/*     */     } 
/* 828 */     rIFFWriter9.writeString("EOS", 20);
/* 829 */     rIFFWriter9.write(new byte[26]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 834 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 838 */     return this.major + "." + this.minor;
/*     */   }
/*     */   
/*     */   public String getVendor() {
/* 842 */     return this.engineers;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 846 */     return this.comments;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/* 850 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public void setVendor(String paramString) {
/* 854 */     this.engineers = paramString;
/*     */   }
/*     */   
/*     */   public void setDescription(String paramString) {
/* 858 */     this.comments = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundbankResource[] getResources() {
/* 863 */     SoundbankResource[] arrayOfSoundbankResource = new SoundbankResource[this.layers.size() + this.samples.size()];
/* 864 */     byte b1 = 0; byte b2;
/* 865 */     for (b2 = 0; b2 < this.layers.size(); b2++)
/* 866 */       arrayOfSoundbankResource[b1++] = this.layers.get(b2); 
/* 867 */     for (b2 = 0; b2 < this.samples.size(); b2++)
/* 868 */       arrayOfSoundbankResource[b1++] = this.samples.get(b2); 
/* 869 */     return arrayOfSoundbankResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public SF2Instrument[] getInstruments() {
/* 874 */     SF2Instrument[] arrayOfSF2Instrument = this.instruments.<SF2Instrument>toArray(new SF2Instrument[this.instruments.size()]);
/* 875 */     Arrays.sort(arrayOfSF2Instrument, new ModelInstrumentComparator());
/* 876 */     return arrayOfSF2Instrument;
/*     */   }
/*     */   
/*     */   public SF2Layer[] getLayers() {
/* 880 */     return this.layers.<SF2Layer>toArray(new SF2Layer[this.layers.size()]);
/*     */   }
/*     */   
/*     */   public SF2Sample[] getSamples() {
/* 884 */     return this.samples.<SF2Sample>toArray(new SF2Sample[this.samples.size()]);
/*     */   }
/*     */   
/*     */   public Instrument getInstrument(Patch paramPatch) {
/* 888 */     int i = paramPatch.getProgram();
/* 889 */     int j = paramPatch.getBank();
/* 890 */     boolean bool = false;
/* 891 */     if (paramPatch instanceof ModelPatch)
/* 892 */       bool = ((ModelPatch)paramPatch).isPercussion(); 
/* 893 */     for (Instrument instrument : this.instruments) {
/* 894 */       Patch patch = instrument.getPatch();
/* 895 */       int k = patch.getProgram();
/* 896 */       int m = patch.getBank();
/* 897 */       if (i == k && j == m) {
/* 898 */         boolean bool1 = false;
/* 899 */         if (patch instanceof ModelPatch)
/* 900 */           bool1 = ((ModelPatch)patch).isPercussion(); 
/* 901 */         if (bool == bool1)
/* 902 */           return instrument; 
/*     */       } 
/*     */     } 
/* 905 */     return null;
/*     */   }
/*     */   
/*     */   public String getCreationDate() {
/* 909 */     return this.creationDate;
/*     */   }
/*     */   
/*     */   public void setCreationDate(String paramString) {
/* 913 */     this.creationDate = paramString;
/*     */   }
/*     */   
/*     */   public String getProduct() {
/* 917 */     return this.product;
/*     */   }
/*     */   
/*     */   public void setProduct(String paramString) {
/* 921 */     this.product = paramString;
/*     */   }
/*     */   
/*     */   public String getRomName() {
/* 925 */     return this.romName;
/*     */   }
/*     */   
/*     */   public void setRomName(String paramString) {
/* 929 */     this.romName = paramString;
/*     */   }
/*     */   
/*     */   public int getRomVersionMajor() {
/* 933 */     return this.romVersionMajor;
/*     */   }
/*     */   
/*     */   public void setRomVersionMajor(int paramInt) {
/* 937 */     this.romVersionMajor = paramInt;
/*     */   }
/*     */   
/*     */   public int getRomVersionMinor() {
/* 941 */     return this.romVersionMinor;
/*     */   }
/*     */   
/*     */   public void setRomVersionMinor(int paramInt) {
/* 945 */     this.romVersionMinor = paramInt;
/*     */   }
/*     */   
/*     */   public String getTargetEngine() {
/* 949 */     return this.targetEngine;
/*     */   }
/*     */   
/*     */   public void setTargetEngine(String paramString) {
/* 953 */     this.targetEngine = paramString;
/*     */   }
/*     */   
/*     */   public String getTools() {
/* 957 */     return this.tools;
/*     */   }
/*     */   
/*     */   public void setTools(String paramString) {
/* 961 */     this.tools = paramString;
/*     */   }
/*     */   
/*     */   public void addResource(SoundbankResource paramSoundbankResource) {
/* 965 */     if (paramSoundbankResource instanceof SF2Instrument)
/* 966 */       this.instruments.add((SF2Instrument)paramSoundbankResource); 
/* 967 */     if (paramSoundbankResource instanceof SF2Layer)
/* 968 */       this.layers.add((SF2Layer)paramSoundbankResource); 
/* 969 */     if (paramSoundbankResource instanceof SF2Sample)
/* 970 */       this.samples.add((SF2Sample)paramSoundbankResource); 
/*     */   }
/*     */   
/*     */   public void removeResource(SoundbankResource paramSoundbankResource) {
/* 974 */     if (paramSoundbankResource instanceof SF2Instrument)
/* 975 */       this.instruments.remove(paramSoundbankResource); 
/* 976 */     if (paramSoundbankResource instanceof SF2Layer)
/* 977 */       this.layers.remove(paramSoundbankResource); 
/* 978 */     if (paramSoundbankResource instanceof SF2Sample)
/* 979 */       this.samples.remove(paramSoundbankResource); 
/*     */   }
/*     */   
/*     */   public void addInstrument(SF2Instrument paramSF2Instrument) {
/* 983 */     this.instruments.add(paramSF2Instrument);
/*     */   }
/*     */   
/*     */   public void removeInstrument(SF2Instrument paramSF2Instrument) {
/* 987 */     this.instruments.remove(paramSF2Instrument);
/*     */   }
/*     */   
/*     */   public SF2Soundbank() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SF2Soundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */