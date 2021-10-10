/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import javax.sound.midi.Instrument;
/*      */ import javax.sound.midi.Patch;
/*      */ import javax.sound.midi.Soundbank;
/*      */ import javax.sound.midi.SoundbankResource;
/*      */ import javax.sound.sampled.AudioFormat;
/*      */ import javax.sound.sampled.AudioInputStream;
/*      */ import javax.sound.sampled.AudioSystem;
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
/*      */ public final class DLSSoundbank
/*      */   implements Soundbank
/*      */ {
/*      */   private static final int DLS_CDL_AND = 1;
/*      */   private static final int DLS_CDL_OR = 2;
/*      */   private static final int DLS_CDL_XOR = 3;
/*      */   private static final int DLS_CDL_ADD = 4;
/*      */   private static final int DLS_CDL_SUBTRACT = 5;
/*      */   private static final int DLS_CDL_MULTIPLY = 6;
/*      */   private static final int DLS_CDL_DIVIDE = 7;
/*      */   private static final int DLS_CDL_LOGICAL_AND = 8;
/*      */   private static final int DLS_CDL_LOGICAL_OR = 9;
/*      */   private static final int DLS_CDL_LT = 10;
/*      */   private static final int DLS_CDL_LE = 11;
/*      */   private static final int DLS_CDL_GT = 12;
/*      */   private static final int DLS_CDL_GE = 13;
/*      */   private static final int DLS_CDL_EQ = 14;
/*      */   private static final int DLS_CDL_NOT = 15;
/*      */   private static final int DLS_CDL_CONST = 16;
/*      */   private static final int DLS_CDL_QUERY = 17;
/*      */   private static final int DLS_CDL_QUERYSUPPORTED = 18;
/*      */   
/*      */   private static class DLSID
/*      */   {
/*      */     long i1;
/*      */     int s1;
/*      */     int s2;
/*      */     int x1;
/*      */     int x2;
/*      */     int x3;
/*      */     int x4;
/*      */     int x5;
/*      */     int x6;
/*      */     int x7;
/*      */     int x8;
/*      */     
/*      */     private DLSID() {}
/*      */     
/*      */     DLSID(long param1Long, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10) {
/*   74 */       this.i1 = param1Long;
/*   75 */       this.s1 = param1Int1;
/*   76 */       this.s2 = param1Int2;
/*   77 */       this.x1 = param1Int3;
/*   78 */       this.x2 = param1Int4;
/*   79 */       this.x3 = param1Int5;
/*   80 */       this.x4 = param1Int6;
/*   81 */       this.x5 = param1Int7;
/*   82 */       this.x6 = param1Int8;
/*   83 */       this.x7 = param1Int9;
/*   84 */       this.x8 = param1Int10;
/*      */     }
/*      */     
/*      */     public static DLSID read(RIFFReader param1RIFFReader) throws IOException {
/*   88 */       DLSID dLSID = new DLSID();
/*   89 */       dLSID.i1 = param1RIFFReader.readUnsignedInt();
/*   90 */       dLSID.s1 = param1RIFFReader.readUnsignedShort();
/*   91 */       dLSID.s2 = param1RIFFReader.readUnsignedShort();
/*   92 */       dLSID.x1 = param1RIFFReader.readUnsignedByte();
/*   93 */       dLSID.x2 = param1RIFFReader.readUnsignedByte();
/*   94 */       dLSID.x3 = param1RIFFReader.readUnsignedByte();
/*   95 */       dLSID.x4 = param1RIFFReader.readUnsignedByte();
/*   96 */       dLSID.x5 = param1RIFFReader.readUnsignedByte();
/*   97 */       dLSID.x6 = param1RIFFReader.readUnsignedByte();
/*   98 */       dLSID.x7 = param1RIFFReader.readUnsignedByte();
/*   99 */       dLSID.x8 = param1RIFFReader.readUnsignedByte();
/*  100 */       return dLSID;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  104 */       return (int)this.i1;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  108 */       if (!(param1Object instanceof DLSID)) {
/*  109 */         return false;
/*      */       }
/*  111 */       DLSID dLSID = (DLSID)param1Object;
/*  112 */       return (this.i1 == dLSID.i1 && this.s1 == dLSID.s1 && this.s2 == dLSID.s2 && this.x1 == dLSID.x1 && this.x2 == dLSID.x2 && this.x3 == dLSID.x3 && this.x4 == dLSID.x4 && this.x5 == dLSID.x5 && this.x6 == dLSID.x6 && this.x7 == dLSID.x7 && this.x8 == dLSID.x8);
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
/*  155 */   private static final DLSID DLSID_GMInHardware = new DLSID(395259684L, 50020, 4561, 167, 96, 0, 0, 248, 117, 172, 18);
/*      */   
/*  157 */   private static final DLSID DLSID_GSInHardware = new DLSID(395259685L, 50020, 4561, 167, 96, 0, 0, 248, 117, 172, 18);
/*      */   
/*  159 */   private static final DLSID DLSID_XGInHardware = new DLSID(395259686L, 50020, 4561, 167, 96, 0, 0, 248, 117, 172, 18);
/*      */   
/*  161 */   private static final DLSID DLSID_SupportsDLS1 = new DLSID(395259687L, 50020, 4561, 167, 96, 0, 0, 248, 117, 172, 18);
/*      */   
/*  163 */   private static final DLSID DLSID_SupportsDLS2 = new DLSID(-247096859L, 18057, 4562, 175, 166, 0, 170, 0, 36, 216, 182);
/*      */   
/*  165 */   private static final DLSID DLSID_SampleMemorySize = new DLSID(395259688L, 50020, 4561, 167, 96, 0, 0, 248, 117, 172, 18);
/*      */   
/*  167 */   private static final DLSID DLSID_ManufacturersID = new DLSID(-1338109567L, 32917, 4562, 161, 239, 0, 96, 8, 51, 219, 216);
/*      */   
/*  169 */   private static final DLSID DLSID_ProductID = new DLSID(-1338109566L, 32917, 4562, 161, 239, 0, 96, 8, 51, 219, 216);
/*      */   
/*  171 */   private static final DLSID DLSID_SamplePlaybackRate = new DLSID(714209043L, 42175, 4562, 187, 223, 0, 96, 8, 51, 219, 216);
/*      */ 
/*      */   
/*  174 */   private long major = -1L;
/*  175 */   private long minor = -1L;
/*      */   
/*  177 */   private final DLSInfo info = new DLSInfo();
/*      */   
/*  179 */   private final List<DLSInstrument> instruments = new ArrayList<>();
/*  180 */   private final List<DLSSample> samples = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean largeFormat = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File sampleFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<DLSRegion, Long> temp_rgnassign;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readSoundbank(InputStream paramInputStream) throws IOException {
/*  213 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/*  214 */     if (!rIFFReader.getFormat().equals("RIFF")) {
/*  215 */       throw new RIFFInvalidFormatException("Input stream is not a valid RIFF stream!");
/*      */     }
/*      */     
/*  218 */     if (!rIFFReader.getType().equals("DLS ")) {
/*  219 */       throw new RIFFInvalidFormatException("Input stream is not a valid DLS soundbank!");
/*      */     }
/*      */     
/*  222 */     while (rIFFReader.hasNextChunk()) {
/*  223 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  224 */       if (rIFFReader1.getFormat().equals("LIST")) {
/*  225 */         if (rIFFReader1.getType().equals("INFO"))
/*  226 */           readInfoChunk(rIFFReader1); 
/*  227 */         if (rIFFReader1.getType().equals("lins"))
/*  228 */           readLinsChunk(rIFFReader1); 
/*  229 */         if (rIFFReader1.getType().equals("wvpl"))
/*  230 */           readWvplChunk(rIFFReader1);  continue;
/*      */       } 
/*  232 */       if (rIFFReader1.getFormat().equals("cdl ") && 
/*  233 */         !readCdlChunk(rIFFReader1)) {
/*  234 */         throw new RIFFInvalidFormatException("DLS file isn't supported!");
/*      */       }
/*      */ 
/*      */       
/*  238 */       if (rIFFReader1.getFormat().equals("colh"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  243 */       if (rIFFReader1.getFormat().equals("ptbl"));
/*      */ 
/*      */ 
/*      */       
/*  247 */       if (rIFFReader1.getFormat().equals("vers")) {
/*  248 */         this.major = rIFFReader1.readUnsignedInt();
/*  249 */         this.minor = rIFFReader1.readUnsignedInt();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  254 */     for (Map.Entry<DLSRegion, Long> entry : this.temp_rgnassign.entrySet()) {
/*  255 */       ((DLSRegion)entry.getKey()).sample = this.samples.get((int)((Long)entry.getValue()).longValue());
/*      */     }
/*      */     
/*  258 */     this.temp_rgnassign = null;
/*      */   }
/*      */   
/*      */   private boolean cdlIsQuerySupported(DLSID paramDLSID) {
/*  262 */     return (paramDLSID.equals(DLSID_GMInHardware) || paramDLSID
/*  263 */       .equals(DLSID_GSInHardware) || paramDLSID
/*  264 */       .equals(DLSID_XGInHardware) || paramDLSID
/*  265 */       .equals(DLSID_SupportsDLS1) || paramDLSID
/*  266 */       .equals(DLSID_SupportsDLS2) || paramDLSID
/*  267 */       .equals(DLSID_SampleMemorySize) || paramDLSID
/*  268 */       .equals(DLSID_ManufacturersID) || paramDLSID
/*  269 */       .equals(DLSID_ProductID) || paramDLSID
/*  270 */       .equals(DLSID_SamplePlaybackRate));
/*      */   }
/*      */   
/*      */   private long cdlQuery(DLSID paramDLSID) {
/*  274 */     if (paramDLSID.equals(DLSID_GMInHardware))
/*  275 */       return 1L; 
/*  276 */     if (paramDLSID.equals(DLSID_GSInHardware))
/*  277 */       return 0L; 
/*  278 */     if (paramDLSID.equals(DLSID_XGInHardware))
/*  279 */       return 0L; 
/*  280 */     if (paramDLSID.equals(DLSID_SupportsDLS1))
/*  281 */       return 1L; 
/*  282 */     if (paramDLSID.equals(DLSID_SupportsDLS2))
/*  283 */       return 1L; 
/*  284 */     if (paramDLSID.equals(DLSID_SampleMemorySize))
/*  285 */       return Runtime.getRuntime().totalMemory(); 
/*  286 */     if (paramDLSID.equals(DLSID_ManufacturersID))
/*  287 */       return 0L; 
/*  288 */     if (paramDLSID.equals(DLSID_ProductID))
/*  289 */       return 0L; 
/*  290 */     if (paramDLSID.equals(DLSID_SamplePlaybackRate))
/*  291 */       return 44100L; 
/*  292 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean readCdlChunk(RIFFReader paramRIFFReader) throws IOException {
/*  303 */     Stack<Long> stack = new Stack();
/*      */     
/*  305 */     while (paramRIFFReader.available() != 0) {
/*  306 */       DLSID dLSID; long l1, l2; int i = paramRIFFReader.readUnsignedShort();
/*  307 */       switch (i) {
/*      */         case 1:
/*  309 */           l1 = ((Long)stack.pop()).longValue();
/*  310 */           l2 = ((Long)stack.pop()).longValue();
/*  311 */           stack.push(Long.valueOf((l1 != 0L && l2 != 0L) ? 1L : 0L));
/*      */         
/*      */         case 2:
/*  314 */           l1 = ((Long)stack.pop()).longValue();
/*  315 */           l2 = ((Long)stack.pop()).longValue();
/*  316 */           stack.push(Long.valueOf((l1 != 0L || l2 != 0L) ? 1L : 0L));
/*      */         
/*      */         case 3:
/*  319 */           l1 = ((Long)stack.pop()).longValue();
/*  320 */           l2 = ((Long)stack.pop()).longValue();
/*  321 */           stack.push(Long.valueOf(((((l1 != 0L) ? 1 : 0) ^ ((l2 != 0L) ? 1 : 0)) != 0) ? 1L : 0L));
/*      */         
/*      */         case 4:
/*  324 */           l1 = ((Long)stack.pop()).longValue();
/*  325 */           l2 = ((Long)stack.pop()).longValue();
/*  326 */           stack.push(Long.valueOf(l1 + l2));
/*      */         
/*      */         case 5:
/*  329 */           l1 = ((Long)stack.pop()).longValue();
/*  330 */           l2 = ((Long)stack.pop()).longValue();
/*  331 */           stack.push(Long.valueOf(l1 - l2));
/*      */         
/*      */         case 6:
/*  334 */           l1 = ((Long)stack.pop()).longValue();
/*  335 */           l2 = ((Long)stack.pop()).longValue();
/*  336 */           stack.push(Long.valueOf(l1 * l2));
/*      */         
/*      */         case 7:
/*  339 */           l1 = ((Long)stack.pop()).longValue();
/*  340 */           l2 = ((Long)stack.pop()).longValue();
/*  341 */           stack.push(Long.valueOf(l1 / l2));
/*      */         
/*      */         case 8:
/*  344 */           l1 = ((Long)stack.pop()).longValue();
/*  345 */           l2 = ((Long)stack.pop()).longValue();
/*  346 */           stack.push(Long.valueOf((l1 != 0L && l2 != 0L) ? 1L : 0L));
/*      */         
/*      */         case 9:
/*  349 */           l1 = ((Long)stack.pop()).longValue();
/*  350 */           l2 = ((Long)stack.pop()).longValue();
/*  351 */           stack.push(Long.valueOf((l1 != 0L || l2 != 0L) ? 1L : 0L));
/*      */         
/*      */         case 10:
/*  354 */           l1 = ((Long)stack.pop()).longValue();
/*  355 */           l2 = ((Long)stack.pop()).longValue();
/*  356 */           stack.push(Long.valueOf((l1 < l2) ? 1L : 0L));
/*      */         
/*      */         case 11:
/*  359 */           l1 = ((Long)stack.pop()).longValue();
/*  360 */           l2 = ((Long)stack.pop()).longValue();
/*  361 */           stack.push(Long.valueOf((l1 <= l2) ? 1L : 0L));
/*      */         
/*      */         case 12:
/*  364 */           l1 = ((Long)stack.pop()).longValue();
/*  365 */           l2 = ((Long)stack.pop()).longValue();
/*  366 */           stack.push(Long.valueOf((l1 > l2) ? 1L : 0L));
/*      */         
/*      */         case 13:
/*  369 */           l1 = ((Long)stack.pop()).longValue();
/*  370 */           l2 = ((Long)stack.pop()).longValue();
/*  371 */           stack.push(Long.valueOf((l1 >= l2) ? 1L : 0L));
/*      */         
/*      */         case 14:
/*  374 */           l1 = ((Long)stack.pop()).longValue();
/*  375 */           l2 = ((Long)stack.pop()).longValue();
/*  376 */           stack.push(Long.valueOf((l1 == l2) ? 1L : 0L));
/*      */         
/*      */         case 15:
/*  379 */           l1 = ((Long)stack.pop()).longValue();
/*  380 */           l2 = ((Long)stack.pop()).longValue();
/*  381 */           stack.push(Long.valueOf((l1 == 0L) ? 1L : 0L));
/*      */         
/*      */         case 16:
/*  384 */           stack.push(Long.valueOf(paramRIFFReader.readUnsignedInt()));
/*      */         
/*      */         case 17:
/*  387 */           dLSID = DLSID.read(paramRIFFReader);
/*  388 */           stack.push(Long.valueOf(cdlQuery(dLSID)));
/*      */         
/*      */         case 18:
/*  391 */           dLSID = DLSID.read(paramRIFFReader);
/*  392 */           stack.push(Long.valueOf(cdlIsQuerySupported(dLSID) ? 1L : 0L));
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  398 */     if (stack.isEmpty()) {
/*  399 */       return false;
/*      */     }
/*  401 */     return (((Long)stack.pop()).longValue() == 1L);
/*      */   }
/*      */   
/*      */   private void readInfoChunk(RIFFReader paramRIFFReader) throws IOException {
/*  405 */     this.info.name = null;
/*  406 */     while (paramRIFFReader.hasNextChunk()) {
/*  407 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  408 */       String str = rIFFReader.getFormat();
/*  409 */       if (str.equals("INAM")) {
/*  410 */         this.info.name = rIFFReader.readString(rIFFReader.available()); continue;
/*  411 */       }  if (str.equals("ICRD")) {
/*  412 */         this.info.creationDate = rIFFReader.readString(rIFFReader.available()); continue;
/*  413 */       }  if (str.equals("IENG")) {
/*  414 */         this.info.engineers = rIFFReader.readString(rIFFReader.available()); continue;
/*  415 */       }  if (str.equals("IPRD")) {
/*  416 */         this.info.product = rIFFReader.readString(rIFFReader.available()); continue;
/*  417 */       }  if (str.equals("ICOP")) {
/*  418 */         this.info.copyright = rIFFReader.readString(rIFFReader.available()); continue;
/*  419 */       }  if (str.equals("ICMT")) {
/*  420 */         this.info.comments = rIFFReader.readString(rIFFReader.available()); continue;
/*  421 */       }  if (str.equals("ISFT")) {
/*  422 */         this.info.tools = rIFFReader.readString(rIFFReader.available()); continue;
/*  423 */       }  if (str.equals("IARL")) {
/*  424 */         this.info.archival_location = rIFFReader.readString(rIFFReader.available()); continue;
/*  425 */       }  if (str.equals("IART")) {
/*  426 */         this.info.artist = rIFFReader.readString(rIFFReader.available()); continue;
/*  427 */       }  if (str.equals("ICMS")) {
/*  428 */         this.info.commissioned = rIFFReader.readString(rIFFReader.available()); continue;
/*  429 */       }  if (str.equals("IGNR")) {
/*  430 */         this.info.genre = rIFFReader.readString(rIFFReader.available()); continue;
/*  431 */       }  if (str.equals("IKEY")) {
/*  432 */         this.info.keywords = rIFFReader.readString(rIFFReader.available()); continue;
/*  433 */       }  if (str.equals("IMED")) {
/*  434 */         this.info.medium = rIFFReader.readString(rIFFReader.available()); continue;
/*  435 */       }  if (str.equals("ISBJ")) {
/*  436 */         this.info.subject = rIFFReader.readString(rIFFReader.available()); continue;
/*  437 */       }  if (str.equals("ISRC")) {
/*  438 */         this.info.source = rIFFReader.readString(rIFFReader.available()); continue;
/*  439 */       }  if (str.equals("ISRF")) {
/*  440 */         this.info.source_form = rIFFReader.readString(rIFFReader.available()); continue;
/*  441 */       }  if (str.equals("ITCH"))
/*  442 */         this.info.technician = rIFFReader.readString(rIFFReader.available()); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readLinsChunk(RIFFReader paramRIFFReader) throws IOException {
/*  447 */     while (paramRIFFReader.hasNextChunk()) {
/*  448 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  449 */       if (rIFFReader.getFormat().equals("LIST") && 
/*  450 */         rIFFReader.getType().equals("ins ")) {
/*  451 */         readInsChunk(rIFFReader);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readInsChunk(RIFFReader paramRIFFReader) throws IOException {
/*  457 */     DLSInstrument dLSInstrument = new DLSInstrument(this);
/*      */     
/*  459 */     while (paramRIFFReader.hasNextChunk()) {
/*  460 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  461 */       String str = rIFFReader.getFormat();
/*  462 */       if (str.equals("LIST")) {
/*  463 */         if (rIFFReader.getType().equals("INFO")) {
/*  464 */           readInsInfoChunk(dLSInstrument, rIFFReader);
/*      */         }
/*  466 */         if (rIFFReader.getType().equals("lrgn"))
/*  467 */           while (rIFFReader.hasNextChunk()) {
/*  468 */             RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  469 */             if (rIFFReader1.getFormat().equals("LIST")) {
/*  470 */               if (rIFFReader1.getType().equals("rgn ")) {
/*  471 */                 DLSRegion dLSRegion = new DLSRegion();
/*  472 */                 if (readRgnChunk(dLSRegion, rIFFReader1))
/*  473 */                   dLSInstrument.getRegions().add(dLSRegion); 
/*      */               } 
/*  475 */               if (rIFFReader1.getType().equals("rgn2")) {
/*      */                 
/*  477 */                 DLSRegion dLSRegion = new DLSRegion();
/*  478 */                 if (readRgnChunk(dLSRegion, rIFFReader1)) {
/*  479 */                   dLSInstrument.getRegions().add(dLSRegion);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }  
/*  484 */         if (rIFFReader.getType().equals("lart")) {
/*  485 */           ArrayList<DLSModulator> arrayList = new ArrayList();
/*  486 */           while (rIFFReader.hasNextChunk()) {
/*  487 */             RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  488 */             if (rIFFReader.getFormat().equals("cdl ") && 
/*  489 */               !readCdlChunk(rIFFReader)) {
/*  490 */               arrayList.clear();
/*      */               
/*      */               break;
/*      */             } 
/*  494 */             if (rIFFReader1.getFormat().equals("art1"))
/*  495 */               readArt1Chunk(arrayList, rIFFReader1); 
/*      */           } 
/*  497 */           dLSInstrument.getModulators().addAll(arrayList);
/*      */         } 
/*  499 */         if (rIFFReader.getType().equals("lar2")) {
/*      */           
/*  501 */           ArrayList<DLSModulator> arrayList = new ArrayList();
/*  502 */           while (rIFFReader.hasNextChunk()) {
/*  503 */             RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  504 */             if (rIFFReader.getFormat().equals("cdl ") && 
/*  505 */               !readCdlChunk(rIFFReader)) {
/*  506 */               arrayList.clear();
/*      */               
/*      */               break;
/*      */             } 
/*  510 */             if (rIFFReader1.getFormat().equals("art2"))
/*  511 */               readArt2Chunk(arrayList, rIFFReader1); 
/*      */           } 
/*  513 */           dLSInstrument.getModulators().addAll(arrayList);
/*      */         }  continue;
/*      */       } 
/*  516 */       if (str.equals("dlid")) {
/*  517 */         dLSInstrument.guid = new byte[16];
/*  518 */         rIFFReader.readFully(dLSInstrument.guid);
/*      */       } 
/*  520 */       if (str.equals("insh")) {
/*  521 */         rIFFReader.readUnsignedInt();
/*      */         
/*  523 */         int i = rIFFReader.read();
/*  524 */         i += (rIFFReader.read() & 0x7F) << 7;
/*  525 */         rIFFReader.read();
/*  526 */         int j = rIFFReader.read();
/*      */         
/*  528 */         int k = rIFFReader.read() & 0x7F;
/*  529 */         rIFFReader.read();
/*  530 */         rIFFReader.read();
/*  531 */         rIFFReader.read();
/*      */         
/*  533 */         dLSInstrument.bank = i;
/*  534 */         dLSInstrument.preset = k;
/*  535 */         dLSInstrument.druminstrument = ((j & 0x80) > 0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     this.instruments.add(dLSInstrument);
/*      */   }
/*      */ 
/*      */   
/*      */   private void readArt1Chunk(List<DLSModulator> paramList, RIFFReader paramRIFFReader) throws IOException {
/*  547 */     long l1 = paramRIFFReader.readUnsignedInt();
/*  548 */     long l2 = paramRIFFReader.readUnsignedInt();
/*      */     
/*  550 */     if (l1 - 8L != 0L) {
/*  551 */       paramRIFFReader.skip(l1 - 8L);
/*      */     }
/*  553 */     for (byte b = 0; b < l2; b++) {
/*  554 */       DLSModulator dLSModulator = new DLSModulator();
/*  555 */       dLSModulator.version = 1;
/*  556 */       dLSModulator.source = paramRIFFReader.readUnsignedShort();
/*  557 */       dLSModulator.control = paramRIFFReader.readUnsignedShort();
/*  558 */       dLSModulator.destination = paramRIFFReader.readUnsignedShort();
/*  559 */       dLSModulator.transform = paramRIFFReader.readUnsignedShort();
/*  560 */       dLSModulator.scale = paramRIFFReader.readInt();
/*  561 */       paramList.add(dLSModulator);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readArt2Chunk(List<DLSModulator> paramList, RIFFReader paramRIFFReader) throws IOException {
/*  567 */     long l1 = paramRIFFReader.readUnsignedInt();
/*  568 */     long l2 = paramRIFFReader.readUnsignedInt();
/*      */     
/*  570 */     if (l1 - 8L != 0L) {
/*  571 */       paramRIFFReader.skip(l1 - 8L);
/*      */     }
/*  573 */     for (byte b = 0; b < l2; b++) {
/*  574 */       DLSModulator dLSModulator = new DLSModulator();
/*  575 */       dLSModulator.version = 2;
/*  576 */       dLSModulator.source = paramRIFFReader.readUnsignedShort();
/*  577 */       dLSModulator.control = paramRIFFReader.readUnsignedShort();
/*  578 */       dLSModulator.destination = paramRIFFReader.readUnsignedShort();
/*  579 */       dLSModulator.transform = paramRIFFReader.readUnsignedShort();
/*  580 */       dLSModulator.scale = paramRIFFReader.readInt();
/*  581 */       paramList.add(dLSModulator);
/*      */     } 
/*      */   }
/*      */   
/*  585 */   public DLSSoundbank() { this.temp_rgnassign = new HashMap<>(); } public DLSSoundbank(URL paramURL) throws IOException { this.temp_rgnassign = new HashMap<>(); InputStream inputStream = paramURL.openStream(); try { readSoundbank(inputStream); } finally { inputStream.close(); }  } public DLSSoundbank(File paramFile) throws IOException { this.temp_rgnassign = new HashMap<>(); this.largeFormat = true; this.sampleFile = paramFile; FileInputStream fileInputStream = new FileInputStream(paramFile); try { readSoundbank(fileInputStream); } finally { fileInputStream.close(); }  } public DLSSoundbank(InputStream paramInputStream) throws IOException { this.temp_rgnassign = new HashMap<>();
/*      */     readSoundbank(paramInputStream); }
/*      */   
/*      */   private boolean readRgnChunk(DLSRegion paramDLSRegion, RIFFReader paramRIFFReader) throws IOException {
/*  589 */     while (paramRIFFReader.hasNextChunk()) {
/*  590 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  591 */       String str = rIFFReader.getFormat();
/*  592 */       if (str.equals("LIST")) {
/*  593 */         if (rIFFReader.getType().equals("lart")) {
/*  594 */           ArrayList<DLSModulator> arrayList = new ArrayList();
/*  595 */           while (rIFFReader.hasNextChunk()) {
/*  596 */             RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  597 */             if (rIFFReader.getFormat().equals("cdl ") && 
/*  598 */               !readCdlChunk(rIFFReader)) {
/*  599 */               arrayList.clear();
/*      */               
/*      */               break;
/*      */             } 
/*  603 */             if (rIFFReader1.getFormat().equals("art1"))
/*  604 */               readArt1Chunk(arrayList, rIFFReader1); 
/*      */           } 
/*  606 */           paramDLSRegion.getModulators().addAll(arrayList);
/*      */         } 
/*  608 */         if (rIFFReader.getType().equals("lar2")) {
/*      */           
/*  610 */           ArrayList<DLSModulator> arrayList = new ArrayList();
/*  611 */           while (rIFFReader.hasNextChunk()) {
/*  612 */             RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*  613 */             if (rIFFReader.getFormat().equals("cdl ") && 
/*  614 */               !readCdlChunk(rIFFReader)) {
/*  615 */               arrayList.clear();
/*      */               
/*      */               break;
/*      */             } 
/*  619 */             if (rIFFReader1.getFormat().equals("art2"))
/*  620 */               readArt2Chunk(arrayList, rIFFReader1); 
/*      */           } 
/*  622 */           paramDLSRegion.getModulators().addAll(arrayList);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  626 */       if (str.equals("cdl ") && 
/*  627 */         !readCdlChunk(rIFFReader)) {
/*  628 */         return false;
/*      */       }
/*  630 */       if (str.equals("rgnh")) {
/*  631 */         paramDLSRegion.keyfrom = rIFFReader.readUnsignedShort();
/*  632 */         paramDLSRegion.keyto = rIFFReader.readUnsignedShort();
/*  633 */         paramDLSRegion.velfrom = rIFFReader.readUnsignedShort();
/*  634 */         paramDLSRegion.velto = rIFFReader.readUnsignedShort();
/*  635 */         paramDLSRegion.options = rIFFReader.readUnsignedShort();
/*  636 */         paramDLSRegion.exclusiveClass = rIFFReader.readUnsignedShort();
/*      */       } 
/*  638 */       if (str.equals("wlnk")) {
/*  639 */         paramDLSRegion.fusoptions = rIFFReader.readUnsignedShort();
/*  640 */         paramDLSRegion.phasegroup = rIFFReader.readUnsignedShort();
/*  641 */         paramDLSRegion.channel = rIFFReader.readUnsignedInt();
/*  642 */         long l = rIFFReader.readUnsignedInt();
/*  643 */         this.temp_rgnassign.put(paramDLSRegion, Long.valueOf(l));
/*      */       } 
/*  645 */       if (str.equals("wsmp")) {
/*  646 */         paramDLSRegion.sampleoptions = new DLSSampleOptions();
/*  647 */         readWsmpChunk(paramDLSRegion.sampleoptions, rIFFReader);
/*      */       } 
/*      */     } 
/*      */     
/*  651 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void readWsmpChunk(DLSSampleOptions paramDLSSampleOptions, RIFFReader paramRIFFReader) throws IOException {
/*  656 */     long l1 = paramRIFFReader.readUnsignedInt();
/*  657 */     paramDLSSampleOptions.unitynote = paramRIFFReader.readUnsignedShort();
/*  658 */     paramDLSSampleOptions.finetune = paramRIFFReader.readShort();
/*  659 */     paramDLSSampleOptions.attenuation = paramRIFFReader.readInt();
/*  660 */     paramDLSSampleOptions.options = paramRIFFReader.readUnsignedInt();
/*  661 */     long l2 = paramRIFFReader.readInt();
/*      */     
/*  663 */     if (l1 > 20L) {
/*  664 */       paramRIFFReader.skip(l1 - 20L);
/*      */     }
/*  666 */     for (byte b = 0; b < l2; b++) {
/*  667 */       DLSSampleLoop dLSSampleLoop = new DLSSampleLoop();
/*  668 */       long l = paramRIFFReader.readUnsignedInt();
/*  669 */       dLSSampleLoop.type = paramRIFFReader.readUnsignedInt();
/*  670 */       dLSSampleLoop.start = paramRIFFReader.readUnsignedInt();
/*  671 */       dLSSampleLoop.length = paramRIFFReader.readUnsignedInt();
/*  672 */       paramDLSSampleOptions.loops.add(dLSSampleLoop);
/*  673 */       if (l > 16L) {
/*  674 */         paramRIFFReader.skip(l - 16L);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readInsInfoChunk(DLSInstrument paramDLSInstrument, RIFFReader paramRIFFReader) throws IOException {
/*  680 */     paramDLSInstrument.info.name = null;
/*  681 */     while (paramRIFFReader.hasNextChunk()) {
/*  682 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  683 */       String str = rIFFReader.getFormat();
/*  684 */       if (str.equals("INAM")) {
/*  685 */         paramDLSInstrument.info.name = rIFFReader.readString(rIFFReader.available()); continue;
/*  686 */       }  if (str.equals("ICRD")) {
/*  687 */         paramDLSInstrument.info
/*  688 */           .creationDate = rIFFReader.readString(rIFFReader.available()); continue;
/*  689 */       }  if (str.equals("IENG")) {
/*  690 */         paramDLSInstrument.info
/*  691 */           .engineers = rIFFReader.readString(rIFFReader.available()); continue;
/*  692 */       }  if (str.equals("IPRD")) {
/*  693 */         paramDLSInstrument.info.product = rIFFReader.readString(rIFFReader.available()); continue;
/*  694 */       }  if (str.equals("ICOP")) {
/*  695 */         paramDLSInstrument.info
/*  696 */           .copyright = rIFFReader.readString(rIFFReader.available()); continue;
/*  697 */       }  if (str.equals("ICMT")) {
/*  698 */         paramDLSInstrument.info
/*  699 */           .comments = rIFFReader.readString(rIFFReader.available()); continue;
/*  700 */       }  if (str.equals("ISFT")) {
/*  701 */         paramDLSInstrument.info.tools = rIFFReader.readString(rIFFReader.available()); continue;
/*  702 */       }  if (str.equals("IARL")) {
/*  703 */         paramDLSInstrument.info
/*  704 */           .archival_location = rIFFReader.readString(rIFFReader.available()); continue;
/*  705 */       }  if (str.equals("IART")) {
/*  706 */         paramDLSInstrument.info.artist = rIFFReader.readString(rIFFReader.available()); continue;
/*  707 */       }  if (str.equals("ICMS")) {
/*  708 */         paramDLSInstrument.info
/*  709 */           .commissioned = rIFFReader.readString(rIFFReader.available()); continue;
/*  710 */       }  if (str.equals("IGNR")) {
/*  711 */         paramDLSInstrument.info.genre = rIFFReader.readString(rIFFReader.available()); continue;
/*  712 */       }  if (str.equals("IKEY")) {
/*  713 */         paramDLSInstrument.info
/*  714 */           .keywords = rIFFReader.readString(rIFFReader.available()); continue;
/*  715 */       }  if (str.equals("IMED")) {
/*  716 */         paramDLSInstrument.info.medium = rIFFReader.readString(rIFFReader.available()); continue;
/*  717 */       }  if (str.equals("ISBJ")) {
/*  718 */         paramDLSInstrument.info.subject = rIFFReader.readString(rIFFReader.available()); continue;
/*  719 */       }  if (str.equals("ISRC")) {
/*  720 */         paramDLSInstrument.info.source = rIFFReader.readString(rIFFReader.available()); continue;
/*  721 */       }  if (str.equals("ISRF")) {
/*  722 */         paramDLSInstrument.info
/*  723 */           .source_form = rIFFReader.readString(rIFFReader.available()); continue;
/*  724 */       }  if (str.equals("ITCH")) {
/*  725 */         paramDLSInstrument.info
/*  726 */           .technician = rIFFReader.readString(rIFFReader.available());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readWvplChunk(RIFFReader paramRIFFReader) throws IOException {
/*  732 */     while (paramRIFFReader.hasNextChunk()) {
/*  733 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  734 */       if (rIFFReader.getFormat().equals("LIST") && 
/*  735 */         rIFFReader.getType().equals("wave")) {
/*  736 */         readWaveChunk(rIFFReader);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readWaveChunk(RIFFReader paramRIFFReader) throws IOException {
/*  742 */     DLSSample dLSSample = new DLSSample(this);
/*      */     
/*  744 */     while (paramRIFFReader.hasNextChunk()) {
/*  745 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  746 */       String str = rIFFReader.getFormat();
/*  747 */       if (str.equals("LIST")) {
/*  748 */         if (rIFFReader.getType().equals("INFO"))
/*  749 */           readWaveInfoChunk(dLSSample, rIFFReader); 
/*      */         continue;
/*      */       } 
/*  752 */       if (str.equals("dlid")) {
/*  753 */         dLSSample.guid = new byte[16];
/*  754 */         rIFFReader.readFully(dLSSample.guid);
/*      */       } 
/*      */       
/*  757 */       if (str.equals("fmt ")) {
/*  758 */         int i = rIFFReader.readUnsignedShort();
/*  759 */         if (i != 1 && i != 3) {
/*  760 */           throw new RIFFInvalidDataException("Only PCM samples are supported!");
/*      */         }
/*      */         
/*  763 */         int j = rIFFReader.readUnsignedShort();
/*  764 */         long l = rIFFReader.readUnsignedInt();
/*      */         
/*  766 */         rIFFReader.readUnsignedInt();
/*      */         
/*  768 */         int k = rIFFReader.readUnsignedShort();
/*  769 */         int m = rIFFReader.readUnsignedShort();
/*  770 */         AudioFormat audioFormat = null;
/*  771 */         if (i == 1) {
/*  772 */           if (m == 8) {
/*  773 */             audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, (float)l, m, j, k, (float)l, false);
/*      */           }
/*      */           else {
/*      */             
/*  777 */             audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float)l, m, j, k, (float)l, false);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  782 */         if (i == 3) {
/*  783 */           audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, (float)l, m, j, k, (float)l, false);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  788 */         dLSSample.format = audioFormat;
/*      */       } 
/*      */       
/*  791 */       if (str.equals("data")) {
/*  792 */         if (this.largeFormat) {
/*  793 */           dLSSample.setData(new ModelByteBuffer(this.sampleFile, rIFFReader
/*  794 */                 .getFilePointer(), rIFFReader.available()));
/*      */         } else {
/*  796 */           byte[] arrayOfByte = new byte[rIFFReader.available()];
/*      */           
/*  798 */           dLSSample.setData(arrayOfByte);
/*      */           
/*  800 */           int i = 0;
/*  801 */           int j = rIFFReader.available();
/*  802 */           while (i != j) {
/*  803 */             if (j - i > 65536) {
/*  804 */               rIFFReader.readFully(arrayOfByte, i, 65536);
/*  805 */               i += 65536; continue;
/*      */             } 
/*  807 */             rIFFReader.readFully(arrayOfByte, i, j - i);
/*  808 */             i = j;
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  814 */       if (str.equals("wsmp")) {
/*  815 */         dLSSample.sampleoptions = new DLSSampleOptions();
/*  816 */         readWsmpChunk(dLSSample.sampleoptions, rIFFReader);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  821 */     this.samples.add(dLSSample);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readWaveInfoChunk(DLSSample paramDLSSample, RIFFReader paramRIFFReader) throws IOException {
/*  827 */     paramDLSSample.info.name = null;
/*  828 */     while (paramRIFFReader.hasNextChunk()) {
/*  829 */       RIFFReader rIFFReader = paramRIFFReader.nextChunk();
/*  830 */       String str = rIFFReader.getFormat();
/*  831 */       if (str.equals("INAM")) {
/*  832 */         paramDLSSample.info.name = rIFFReader.readString(rIFFReader.available()); continue;
/*  833 */       }  if (str.equals("ICRD")) {
/*  834 */         paramDLSSample.info
/*  835 */           .creationDate = rIFFReader.readString(rIFFReader.available()); continue;
/*  836 */       }  if (str.equals("IENG")) {
/*  837 */         paramDLSSample.info.engineers = rIFFReader.readString(rIFFReader.available()); continue;
/*  838 */       }  if (str.equals("IPRD")) {
/*  839 */         paramDLSSample.info.product = rIFFReader.readString(rIFFReader.available()); continue;
/*  840 */       }  if (str.equals("ICOP")) {
/*  841 */         paramDLSSample.info.copyright = rIFFReader.readString(rIFFReader.available()); continue;
/*  842 */       }  if (str.equals("ICMT")) {
/*  843 */         paramDLSSample.info.comments = rIFFReader.readString(rIFFReader.available()); continue;
/*  844 */       }  if (str.equals("ISFT")) {
/*  845 */         paramDLSSample.info.tools = rIFFReader.readString(rIFFReader.available()); continue;
/*  846 */       }  if (str.equals("IARL")) {
/*  847 */         paramDLSSample.info
/*  848 */           .archival_location = rIFFReader.readString(rIFFReader.available()); continue;
/*  849 */       }  if (str.equals("IART")) {
/*  850 */         paramDLSSample.info.artist = rIFFReader.readString(rIFFReader.available()); continue;
/*  851 */       }  if (str.equals("ICMS")) {
/*  852 */         paramDLSSample.info
/*  853 */           .commissioned = rIFFReader.readString(rIFFReader.available()); continue;
/*  854 */       }  if (str.equals("IGNR")) {
/*  855 */         paramDLSSample.info.genre = rIFFReader.readString(rIFFReader.available()); continue;
/*  856 */       }  if (str.equals("IKEY")) {
/*  857 */         paramDLSSample.info.keywords = rIFFReader.readString(rIFFReader.available()); continue;
/*  858 */       }  if (str.equals("IMED")) {
/*  859 */         paramDLSSample.info.medium = rIFFReader.readString(rIFFReader.available()); continue;
/*  860 */       }  if (str.equals("ISBJ")) {
/*  861 */         paramDLSSample.info.subject = rIFFReader.readString(rIFFReader.available()); continue;
/*  862 */       }  if (str.equals("ISRC")) {
/*  863 */         paramDLSSample.info.source = rIFFReader.readString(rIFFReader.available()); continue;
/*  864 */       }  if (str.equals("ISRF")) {
/*  865 */         paramDLSSample.info.source_form = rIFFReader.readString(rIFFReader.available()); continue;
/*  866 */       }  if (str.equals("ITCH")) {
/*  867 */         paramDLSSample.info.technician = rIFFReader.readString(rIFFReader.available());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void save(String paramString) throws IOException {
/*  873 */     writeSoundbank(new RIFFWriter(paramString, "DLS "));
/*      */   }
/*      */   
/*      */   public void save(File paramFile) throws IOException {
/*  877 */     writeSoundbank(new RIFFWriter(paramFile, "DLS "));
/*      */   }
/*      */   
/*      */   public void save(OutputStream paramOutputStream) throws IOException {
/*  881 */     writeSoundbank(new RIFFWriter(paramOutputStream, "DLS "));
/*      */   }
/*      */   
/*      */   private void writeSoundbank(RIFFWriter paramRIFFWriter) throws IOException {
/*  885 */     RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("colh");
/*  886 */     rIFFWriter1.writeUnsignedInt(this.instruments.size());
/*      */     
/*  888 */     if (this.major != -1L && this.minor != -1L) {
/*  889 */       RIFFWriter rIFFWriter = paramRIFFWriter.writeChunk("vers");
/*  890 */       rIFFWriter.writeUnsignedInt(this.major);
/*  891 */       rIFFWriter.writeUnsignedInt(this.minor);
/*      */     } 
/*      */     
/*  894 */     writeInstruments(paramRIFFWriter.writeList("lins"));
/*      */     
/*  896 */     RIFFWriter rIFFWriter2 = paramRIFFWriter.writeChunk("ptbl");
/*  897 */     rIFFWriter2.writeUnsignedInt(8L);
/*  898 */     rIFFWriter2.writeUnsignedInt(this.samples.size());
/*  899 */     long l1 = paramRIFFWriter.getFilePointer();
/*  900 */     for (byte b = 0; b < this.samples.size(); b++) {
/*  901 */       rIFFWriter2.writeUnsignedInt(0L);
/*      */     }
/*  903 */     RIFFWriter rIFFWriter3 = paramRIFFWriter.writeList("wvpl");
/*  904 */     long l2 = rIFFWriter3.getFilePointer();
/*  905 */     ArrayList<Long> arrayList = new ArrayList();
/*  906 */     for (DLSSample dLSSample : this.samples) {
/*  907 */       arrayList.add(Long.valueOf(rIFFWriter3.getFilePointer() - l2));
/*  908 */       writeSample(rIFFWriter3.writeList("wave"), dLSSample);
/*      */     } 
/*      */ 
/*      */     
/*  912 */     long l3 = paramRIFFWriter.getFilePointer();
/*  913 */     paramRIFFWriter.seek(l1);
/*  914 */     paramRIFFWriter.setWriteOverride(true);
/*  915 */     for (Long long_ : arrayList)
/*  916 */       paramRIFFWriter.writeUnsignedInt(long_.longValue()); 
/*  917 */     paramRIFFWriter.setWriteOverride(false);
/*  918 */     paramRIFFWriter.seek(l3);
/*      */     
/*  920 */     writeInfo(paramRIFFWriter.writeList("INFO"), this.info);
/*      */     
/*  922 */     paramRIFFWriter.close();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeSample(RIFFWriter paramRIFFWriter, DLSSample paramDLSSample) throws IOException {
/*  928 */     AudioFormat audioFormat = paramDLSSample.getFormat();
/*      */     
/*  930 */     AudioFormat.Encoding encoding = audioFormat.getEncoding();
/*  931 */     float f1 = audioFormat.getSampleRate();
/*  932 */     int i = audioFormat.getSampleSizeInBits();
/*  933 */     int j = audioFormat.getChannels();
/*  934 */     int k = audioFormat.getFrameSize();
/*  935 */     float f2 = audioFormat.getFrameRate();
/*  936 */     boolean bool = audioFormat.isBigEndian();
/*      */     
/*  938 */     boolean bool1 = false;
/*      */     
/*  940 */     if (audioFormat.getSampleSizeInBits() == 8) {
/*  941 */       if (!encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*  942 */         encoding = AudioFormat.Encoding.PCM_UNSIGNED;
/*  943 */         bool1 = true;
/*      */       } 
/*      */     } else {
/*  946 */       if (!encoding.equals(AudioFormat.Encoding.PCM_SIGNED)) {
/*  947 */         encoding = AudioFormat.Encoding.PCM_SIGNED;
/*  948 */         bool1 = true;
/*      */       } 
/*  950 */       if (bool) {
/*  951 */         bool = false;
/*  952 */         bool1 = true;
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     if (bool1) {
/*  957 */       audioFormat = new AudioFormat(encoding, f1, i, j, k, f2, bool);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  962 */     RIFFWriter rIFFWriter = paramRIFFWriter.writeChunk("fmt ");
/*  963 */     byte b = 0;
/*  964 */     if (audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*  965 */       b = 1;
/*  966 */     } else if (audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
/*  967 */       b = 1;
/*  968 */     } else if (audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_FLOAT)) {
/*  969 */       b = 3;
/*      */     } 
/*  971 */     rIFFWriter.writeUnsignedShort(b);
/*  972 */     rIFFWriter.writeUnsignedShort(audioFormat.getChannels());
/*  973 */     rIFFWriter.writeUnsignedInt((long)audioFormat.getSampleRate());
/*  974 */     long l = (long)audioFormat.getFrameRate() * audioFormat.getFrameSize();
/*  975 */     rIFFWriter.writeUnsignedInt(l);
/*  976 */     rIFFWriter.writeUnsignedShort(audioFormat.getFrameSize());
/*  977 */     rIFFWriter.writeUnsignedShort(audioFormat.getSampleSizeInBits());
/*  978 */     rIFFWriter.write(0);
/*  979 */     rIFFWriter.write(0);
/*      */     
/*  981 */     writeSampleOptions(paramRIFFWriter.writeChunk("wsmp"), paramDLSSample.sampleoptions);
/*      */     
/*  983 */     if (bool1) {
/*  984 */       RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("data");
/*  985 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFormat, (AudioInputStream)paramDLSSample
/*  986 */           .getData());
/*  987 */       byte[] arrayOfByte = new byte[1024];
/*      */       int m;
/*  989 */       while ((m = audioInputStream.read(arrayOfByte)) != -1) {
/*  990 */         rIFFWriter1.write(arrayOfByte, 0, m);
/*      */       }
/*      */     } else {
/*  993 */       RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("data");
/*  994 */       ModelByteBuffer modelByteBuffer = paramDLSSample.getDataBuffer();
/*  995 */       modelByteBuffer.writeTo(rIFFWriter1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     writeInfo(paramRIFFWriter.writeList("INFO"), paramDLSSample.info);
/*      */   }
/*      */   
/*      */   private void writeInstruments(RIFFWriter paramRIFFWriter) throws IOException {
/* 1007 */     for (DLSInstrument dLSInstrument : this.instruments) {
/* 1008 */       writeInstrument(paramRIFFWriter.writeList("ins "), dLSInstrument);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeInstrument(RIFFWriter paramRIFFWriter, DLSInstrument paramDLSInstrument) throws IOException {
/* 1015 */     byte b1 = 0;
/* 1016 */     byte b2 = 0;
/* 1017 */     for (DLSModulator dLSModulator : paramDLSInstrument.getModulators()) {
/* 1018 */       if (dLSModulator.version == 1)
/* 1019 */         b1++; 
/* 1020 */       if (dLSModulator.version == 2)
/* 1021 */         b2++; 
/*      */     } 
/* 1023 */     for (DLSRegion dLSRegion : paramDLSInstrument.regions) {
/* 1024 */       for (DLSModulator dLSModulator : dLSRegion.getModulators()) {
/* 1025 */         if (dLSModulator.version == 1)
/* 1026 */           b1++; 
/* 1027 */         if (dLSModulator.version == 2) {
/* 1028 */           b2++;
/*      */         }
/*      */       } 
/*      */     } 
/* 1032 */     byte b3 = 1;
/* 1033 */     if (b2 > 0) {
/* 1034 */       b3 = 2;
/*      */     }
/* 1036 */     RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("insh");
/* 1037 */     rIFFWriter1.writeUnsignedInt(paramDLSInstrument.getRegions().size());
/* 1038 */     rIFFWriter1.writeUnsignedInt(paramDLSInstrument.bank + (paramDLSInstrument.druminstrument ? 2147483648L : 0L));
/*      */     
/* 1040 */     rIFFWriter1.writeUnsignedInt(paramDLSInstrument.preset);
/*      */     
/* 1042 */     RIFFWriter rIFFWriter2 = paramRIFFWriter.writeList("lrgn");
/* 1043 */     for (DLSRegion dLSRegion : paramDLSInstrument.regions) {
/* 1044 */       writeRegion(rIFFWriter2, dLSRegion, b3);
/*      */     }
/* 1046 */     writeArticulators(paramRIFFWriter, paramDLSInstrument.getModulators());
/*      */     
/* 1048 */     writeInfo(paramRIFFWriter.writeList("INFO"), paramDLSInstrument.info);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeArticulators(RIFFWriter paramRIFFWriter, List<DLSModulator> paramList) throws IOException {
/* 1054 */     byte b1 = 0;
/* 1055 */     byte b2 = 0;
/* 1056 */     for (DLSModulator dLSModulator : paramList) {
/* 1057 */       if (dLSModulator.version == 1)
/* 1058 */         b1++; 
/* 1059 */       if (dLSModulator.version == 2)
/* 1060 */         b2++; 
/*      */     } 
/* 1062 */     if (b1 > 0) {
/* 1063 */       RIFFWriter rIFFWriter1 = paramRIFFWriter.writeList("lart");
/* 1064 */       RIFFWriter rIFFWriter2 = rIFFWriter1.writeChunk("art1");
/* 1065 */       rIFFWriter2.writeUnsignedInt(8L);
/* 1066 */       rIFFWriter2.writeUnsignedInt(b1);
/* 1067 */       for (DLSModulator dLSModulator : paramList) {
/* 1068 */         if (dLSModulator.version == 1) {
/* 1069 */           rIFFWriter2.writeUnsignedShort(dLSModulator.source);
/* 1070 */           rIFFWriter2.writeUnsignedShort(dLSModulator.control);
/* 1071 */           rIFFWriter2.writeUnsignedShort(dLSModulator.destination);
/* 1072 */           rIFFWriter2.writeUnsignedShort(dLSModulator.transform);
/* 1073 */           rIFFWriter2.writeInt(dLSModulator.scale);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1077 */     if (b2 > 0) {
/* 1078 */       RIFFWriter rIFFWriter1 = paramRIFFWriter.writeList("lar2");
/* 1079 */       RIFFWriter rIFFWriter2 = rIFFWriter1.writeChunk("art2");
/* 1080 */       rIFFWriter2.writeUnsignedInt(8L);
/* 1081 */       rIFFWriter2.writeUnsignedInt(b2);
/* 1082 */       for (DLSModulator dLSModulator : paramList) {
/* 1083 */         if (dLSModulator.version == 2) {
/* 1084 */           rIFFWriter2.writeUnsignedShort(dLSModulator.source);
/* 1085 */           rIFFWriter2.writeUnsignedShort(dLSModulator.control);
/* 1086 */           rIFFWriter2.writeUnsignedShort(dLSModulator.destination);
/* 1087 */           rIFFWriter2.writeUnsignedShort(dLSModulator.transform);
/* 1088 */           rIFFWriter2.writeInt(dLSModulator.scale);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeRegion(RIFFWriter paramRIFFWriter, DLSRegion paramDLSRegion, int paramInt) throws IOException {
/* 1096 */     RIFFWriter rIFFWriter1 = null;
/* 1097 */     if (paramInt == 1)
/* 1098 */       rIFFWriter1 = paramRIFFWriter.writeList("rgn "); 
/* 1099 */     if (paramInt == 2)
/* 1100 */       rIFFWriter1 = paramRIFFWriter.writeList("rgn2"); 
/* 1101 */     if (rIFFWriter1 == null) {
/*      */       return;
/*      */     }
/* 1104 */     RIFFWriter rIFFWriter2 = rIFFWriter1.writeChunk("rgnh");
/* 1105 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.keyfrom);
/* 1106 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.keyto);
/* 1107 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.velfrom);
/* 1108 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.velto);
/* 1109 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.options);
/* 1110 */     rIFFWriter2.writeUnsignedShort(paramDLSRegion.exclusiveClass);
/*      */     
/* 1112 */     if (paramDLSRegion.sampleoptions != null) {
/* 1113 */       writeSampleOptions(rIFFWriter1.writeChunk("wsmp"), paramDLSRegion.sampleoptions);
/*      */     }
/* 1115 */     if (paramDLSRegion.sample != null && 
/* 1116 */       this.samples.indexOf(paramDLSRegion.sample) != -1) {
/* 1117 */       RIFFWriter rIFFWriter = rIFFWriter1.writeChunk("wlnk");
/* 1118 */       rIFFWriter.writeUnsignedShort(paramDLSRegion.fusoptions);
/* 1119 */       rIFFWriter.writeUnsignedShort(paramDLSRegion.phasegroup);
/* 1120 */       rIFFWriter.writeUnsignedInt(paramDLSRegion.channel);
/* 1121 */       rIFFWriter.writeUnsignedInt(this.samples.indexOf(paramDLSRegion.sample));
/*      */     } 
/*      */     
/* 1124 */     writeArticulators(rIFFWriter1, paramDLSRegion.getModulators());
/* 1125 */     rIFFWriter1.close();
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSampleOptions(RIFFWriter paramRIFFWriter, DLSSampleOptions paramDLSSampleOptions) throws IOException {
/* 1130 */     paramRIFFWriter.writeUnsignedInt(20L);
/* 1131 */     paramRIFFWriter.writeUnsignedShort(paramDLSSampleOptions.unitynote);
/* 1132 */     paramRIFFWriter.writeShort(paramDLSSampleOptions.finetune);
/* 1133 */     paramRIFFWriter.writeInt(paramDLSSampleOptions.attenuation);
/* 1134 */     paramRIFFWriter.writeUnsignedInt(paramDLSSampleOptions.options);
/* 1135 */     paramRIFFWriter.writeInt(paramDLSSampleOptions.loops.size());
/*      */     
/* 1137 */     for (DLSSampleLoop dLSSampleLoop : paramDLSSampleOptions.loops) {
/* 1138 */       paramRIFFWriter.writeUnsignedInt(16L);
/* 1139 */       paramRIFFWriter.writeUnsignedInt(dLSSampleLoop.type);
/* 1140 */       paramRIFFWriter.writeUnsignedInt(dLSSampleLoop.start);
/* 1141 */       paramRIFFWriter.writeUnsignedInt(dLSSampleLoop.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInfoStringChunk(RIFFWriter paramRIFFWriter, String paramString1, String paramString2) throws IOException {
/* 1147 */     if (paramString2 == null)
/*      */       return; 
/* 1149 */     RIFFWriter rIFFWriter = paramRIFFWriter.writeChunk(paramString1);
/* 1150 */     rIFFWriter.writeString(paramString2);
/* 1151 */     int i = (paramString2.getBytes("ascii")).length;
/* 1152 */     rIFFWriter.write(0);
/* 1153 */     i++;
/* 1154 */     if (i % 2 != 0)
/* 1155 */       rIFFWriter.write(0); 
/*      */   }
/*      */   
/*      */   private void writeInfo(RIFFWriter paramRIFFWriter, DLSInfo paramDLSInfo) throws IOException {
/* 1159 */     writeInfoStringChunk(paramRIFFWriter, "INAM", paramDLSInfo.name);
/* 1160 */     writeInfoStringChunk(paramRIFFWriter, "ICRD", paramDLSInfo.creationDate);
/* 1161 */     writeInfoStringChunk(paramRIFFWriter, "IENG", paramDLSInfo.engineers);
/* 1162 */     writeInfoStringChunk(paramRIFFWriter, "IPRD", paramDLSInfo.product);
/* 1163 */     writeInfoStringChunk(paramRIFFWriter, "ICOP", paramDLSInfo.copyright);
/* 1164 */     writeInfoStringChunk(paramRIFFWriter, "ICMT", paramDLSInfo.comments);
/* 1165 */     writeInfoStringChunk(paramRIFFWriter, "ISFT", paramDLSInfo.tools);
/* 1166 */     writeInfoStringChunk(paramRIFFWriter, "IARL", paramDLSInfo.archival_location);
/* 1167 */     writeInfoStringChunk(paramRIFFWriter, "IART", paramDLSInfo.artist);
/* 1168 */     writeInfoStringChunk(paramRIFFWriter, "ICMS", paramDLSInfo.commissioned);
/* 1169 */     writeInfoStringChunk(paramRIFFWriter, "IGNR", paramDLSInfo.genre);
/* 1170 */     writeInfoStringChunk(paramRIFFWriter, "IKEY", paramDLSInfo.keywords);
/* 1171 */     writeInfoStringChunk(paramRIFFWriter, "IMED", paramDLSInfo.medium);
/* 1172 */     writeInfoStringChunk(paramRIFFWriter, "ISBJ", paramDLSInfo.subject);
/* 1173 */     writeInfoStringChunk(paramRIFFWriter, "ISRC", paramDLSInfo.source);
/* 1174 */     writeInfoStringChunk(paramRIFFWriter, "ISRF", paramDLSInfo.source_form);
/* 1175 */     writeInfoStringChunk(paramRIFFWriter, "ITCH", paramDLSInfo.technician);
/*      */   }
/*      */   
/*      */   public DLSInfo getInfo() {
/* 1179 */     return this.info;
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1183 */     return this.info.name;
/*      */   }
/*      */   
/*      */   public String getVersion() {
/* 1187 */     return this.major + "." + this.minor;
/*      */   }
/*      */   
/*      */   public String getVendor() {
/* 1191 */     return this.info.engineers;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/* 1195 */     return this.info.comments;
/*      */   }
/*      */   
/*      */   public void setName(String paramString) {
/* 1199 */     this.info.name = paramString;
/*      */   }
/*      */   
/*      */   public void setVendor(String paramString) {
/* 1203 */     this.info.engineers = paramString;
/*      */   }
/*      */   
/*      */   public void setDescription(String paramString) {
/* 1207 */     this.info.comments = paramString;
/*      */   }
/*      */   
/*      */   public SoundbankResource[] getResources() {
/* 1211 */     SoundbankResource[] arrayOfSoundbankResource = new SoundbankResource[this.samples.size()];
/* 1212 */     byte b1 = 0;
/* 1213 */     for (byte b2 = 0; b2 < this.samples.size(); b2++)
/* 1214 */       arrayOfSoundbankResource[b1++] = this.samples.get(b2); 
/* 1215 */     return arrayOfSoundbankResource;
/*      */   }
/*      */ 
/*      */   
/*      */   public DLSInstrument[] getInstruments() {
/* 1220 */     DLSInstrument[] arrayOfDLSInstrument = this.instruments.<DLSInstrument>toArray(new DLSInstrument[this.instruments.size()]);
/* 1221 */     Arrays.sort(arrayOfDLSInstrument, new ModelInstrumentComparator());
/* 1222 */     return arrayOfDLSInstrument;
/*      */   }
/*      */   
/*      */   public DLSSample[] getSamples() {
/* 1226 */     return this.samples.<DLSSample>toArray(new DLSSample[this.samples.size()]);
/*      */   }
/*      */   
/*      */   public Instrument getInstrument(Patch paramPatch) {
/* 1230 */     int i = paramPatch.getProgram();
/* 1231 */     int j = paramPatch.getBank();
/* 1232 */     boolean bool = false;
/* 1233 */     if (paramPatch instanceof ModelPatch)
/* 1234 */       bool = ((ModelPatch)paramPatch).isPercussion(); 
/* 1235 */     for (Instrument instrument : this.instruments) {
/* 1236 */       Patch patch = instrument.getPatch();
/* 1237 */       int k = patch.getProgram();
/* 1238 */       int m = patch.getBank();
/* 1239 */       if (i == k && j == m) {
/* 1240 */         boolean bool1 = false;
/* 1241 */         if (patch instanceof ModelPatch)
/* 1242 */           bool1 = ((ModelPatch)patch).isPercussion(); 
/* 1243 */         if (bool == bool1)
/* 1244 */           return instrument; 
/*      */       } 
/*      */     } 
/* 1247 */     return null;
/*      */   }
/*      */   
/*      */   public void addResource(SoundbankResource paramSoundbankResource) {
/* 1251 */     if (paramSoundbankResource instanceof DLSInstrument)
/* 1252 */       this.instruments.add((DLSInstrument)paramSoundbankResource); 
/* 1253 */     if (paramSoundbankResource instanceof DLSSample)
/* 1254 */       this.samples.add((DLSSample)paramSoundbankResource); 
/*      */   }
/*      */   
/*      */   public void removeResource(SoundbankResource paramSoundbankResource) {
/* 1258 */     if (paramSoundbankResource instanceof DLSInstrument)
/* 1259 */       this.instruments.remove(paramSoundbankResource); 
/* 1260 */     if (paramSoundbankResource instanceof DLSSample)
/* 1261 */       this.samples.remove(paramSoundbankResource); 
/*      */   }
/*      */   
/*      */   public void addInstrument(DLSInstrument paramDLSInstrument) {
/* 1265 */     this.instruments.add(paramDLSInstrument);
/*      */   }
/*      */   
/*      */   public void removeInstrument(DLSInstrument paramDLSInstrument) {
/* 1269 */     this.instruments.remove(paramDLSInstrument);
/*      */   }
/*      */   
/*      */   public long getMajor() {
/* 1273 */     return this.major;
/*      */   }
/*      */   
/*      */   public void setMajor(long paramLong) {
/* 1277 */     this.major = paramLong;
/*      */   }
/*      */   
/*      */   public long getMinor() {
/* 1281 */     return this.minor;
/*      */   }
/*      */   
/*      */   public void setMinor(long paramLong) {
/* 1285 */     this.minor = paramLong;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DLSSoundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */