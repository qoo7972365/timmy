/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftPerformer
/*     */ {
/*  42 */   static ModelConnectionBlock[] defaultconnections = new ModelConnectionBlock[42];
/*     */ 
/*     */   
/*     */   static {
/*  46 */     byte b = 0;
/*  47 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("noteon", "on", 0), false, false, 0), 1.0D, new ModelDestination(new ModelIdentifier("eg", "on", 0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("noteon", "on", 0), false, false, 0), 1.0D, new ModelDestination(new ModelIdentifier("eg", "on", 1)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("eg", "active", 0), false, false, 0), 1.0D, new ModelDestination(new ModelIdentifier("mixer", "active", 0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("eg", 0), true, false, 0), -960.0D, new ModelDestination(new ModelIdentifier("mixer", "gain")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("noteon", "velocity"), true, false, 1), -960.0D, new ModelDestination(new ModelIdentifier("mixer", "gain")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi", "pitch"), false, true, 0), new ModelSource(new ModelIdentifier("midi_rpn", "0"), new ModelTransform()
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public double transform(double param1Double)
/*     */             {
/*  96 */               int i = (int)(param1Double * 16384.0D);
/*  97 */               int j = i >> 7;
/*  98 */               int k = i & 0x7F;
/*  99 */               return (j * 100 + k);
/*     */             }
/*     */           }), new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */     
/* 104 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("noteon", "keynumber"), false, false, 0), 12800.0D, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "7"), true, false, 1), -960.0D, new ModelDestination(new ModelIdentifier("mixer", "gain")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "8"), false, false, 0), 1000.0D, new ModelDestination(new ModelIdentifier("mixer", "balance")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "10"), false, false, 0), 1000.0D, new ModelDestination(new ModelIdentifier("mixer", "pan")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "11"), true, false, 1), -960.0D, new ModelDestination(new ModelIdentifier("mixer", "gain")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "91"), false, false, 0), 1000.0D, new ModelDestination(new ModelIdentifier("mixer", "reverb")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "93"), false, false, 0), 1000.0D, new ModelDestination(new ModelIdentifier("mixer", "chorus")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "71"), false, true, 0), 200.0D, new ModelDestination(new ModelIdentifier("filter", "q")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "74"), false, true, 0), 9600.0D, new ModelDestination(new ModelIdentifier("filter", "freq")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "72"), false, true, 0), 6000.0D, new ModelDestination(new ModelIdentifier("eg", "release2")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "73"), false, true, 0), 2000.0D, new ModelDestination(new ModelIdentifier("eg", "attack2")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "75"), false, true, 0), 6000.0D, new ModelDestination(new ModelIdentifier("eg", "decay2")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "67"), false, false, 3), -50.0D, new ModelDestination(ModelDestination.DESTINATION_GAIN));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "67"), false, false, 3), -2400.0D, new ModelDestination(ModelDestination.DESTINATION_FILTER_FREQ));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_rpn", "1"), false, true, 0), 100.0D, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_rpn", "2"), false, true, 0), 12800.0D, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("master", "fine_tuning"), false, true, 0), 100.0D, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     defaultconnections[b++] = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("master", "coarse_tuning"), false, true, 0), 12800.0D, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     defaultconnections[b++] = new ModelConnectionBlock(13500.0D, new ModelDestination(new ModelIdentifier("filter", "freq", 0)));
/*     */ 
/*     */     
/* 250 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "delay", 0)));
/*     */ 
/*     */     
/* 253 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "attack", 0)));
/*     */ 
/*     */     
/* 256 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "hold", 0)));
/*     */ 
/*     */     
/* 259 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "decay", 0)));
/*     */ 
/*     */     
/* 262 */     defaultconnections[b++] = new ModelConnectionBlock(1000.0D, new ModelDestination(new ModelIdentifier("eg", "sustain", 0)));
/*     */     
/* 264 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "release", 0)));
/*     */ 
/*     */     
/* 267 */     defaultconnections[b++] = new ModelConnectionBlock(1200.0D * 
/* 268 */         Math.log(0.015D) / Math.log(2.0D), new ModelDestination(new ModelIdentifier("eg", "shutdown", 0)));
/*     */ 
/*     */     
/* 271 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "delay", 1)));
/*     */ 
/*     */     
/* 274 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "attack", 1)));
/*     */ 
/*     */     
/* 277 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "hold", 1)));
/*     */ 
/*     */     
/* 280 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "decay", 1)));
/*     */ 
/*     */     
/* 283 */     defaultconnections[b++] = new ModelConnectionBlock(1000.0D, new ModelDestination(new ModelIdentifier("eg", "sustain", 1)));
/*     */     
/* 285 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("eg", "release", 1)));
/*     */ 
/*     */ 
/*     */     
/* 289 */     defaultconnections[b++] = new ModelConnectionBlock(-8.51318D, new ModelDestination(new ModelIdentifier("lfo", "freq", 0)));
/*     */     
/* 291 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("lfo", "delay", 0)));
/*     */ 
/*     */     
/* 294 */     defaultconnections[b++] = new ModelConnectionBlock(-8.51318D, new ModelDestination(new ModelIdentifier("lfo", "freq", 1)));
/*     */     
/* 296 */     defaultconnections[b++] = new ModelConnectionBlock(Double.NEGATIVE_INFINITY, new ModelDestination(new ModelIdentifier("lfo", "delay", 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 301 */   public int keyFrom = 0;
/* 302 */   public int keyTo = 127;
/* 303 */   public int velFrom = 0;
/* 304 */   public int velTo = 127;
/* 305 */   public int exclusiveClass = 0;
/*     */   public boolean selfNonExclusive = false;
/*     */   public boolean forcedVelocity = false;
/*     */   public boolean forcedKeynumber = false;
/*     */   public ModelPerformer performer;
/*     */   public ModelConnectionBlock[] connections;
/*     */   public ModelOscillator[] oscillators;
/* 312 */   public Map<Integer, int[]> midi_rpn_connections = (Map)new HashMap<>();
/* 313 */   public Map<Integer, int[]> midi_nrpn_connections = (Map)new HashMap<>();
/*     */   public int[][] midi_ctrl_connections;
/*     */   public int[][] midi_connections;
/*     */   public int[] ctrl_connections;
/* 317 */   private List<Integer> ctrl_connections_list = new ArrayList<>();
/*     */   
/*     */   private static class KeySortComparator implements Comparator<ModelSource> { private KeySortComparator() {}
/*     */     
/*     */     public int compare(ModelSource param1ModelSource1, ModelSource param1ModelSource2) {
/* 322 */       return param1ModelSource1.getIdentifier().toString().compareTo(param1ModelSource2
/* 323 */           .getIdentifier().toString());
/*     */     } }
/*     */   
/* 326 */   private static KeySortComparator keySortComparator = new KeySortComparator();
/*     */   
/*     */   private String extractKeys(ModelConnectionBlock paramModelConnectionBlock) {
/* 329 */     StringBuffer stringBuffer = new StringBuffer();
/* 330 */     if (paramModelConnectionBlock.getSources() != null) {
/* 331 */       stringBuffer.append("[");
/* 332 */       ModelSource[] arrayOfModelSource1 = paramModelConnectionBlock.getSources();
/* 333 */       ModelSource[] arrayOfModelSource2 = new ModelSource[arrayOfModelSource1.length]; byte b;
/* 334 */       for (b = 0; b < arrayOfModelSource1.length; b++)
/* 335 */         arrayOfModelSource2[b] = arrayOfModelSource1[b]; 
/* 336 */       Arrays.sort(arrayOfModelSource2, keySortComparator);
/* 337 */       for (b = 0; b < arrayOfModelSource1.length; b++) {
/* 338 */         stringBuffer.append(arrayOfModelSource1[b].getIdentifier());
/* 339 */         stringBuffer.append(";");
/*     */       } 
/* 341 */       stringBuffer.append("]");
/*     */     } 
/* 343 */     stringBuffer.append(";");
/* 344 */     if (paramModelConnectionBlock.getDestination() != null) {
/* 345 */       stringBuffer.append(paramModelConnectionBlock.getDestination().getIdentifier());
/*     */     }
/* 347 */     stringBuffer.append(";");
/* 348 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private void processSource(ModelSource paramModelSource, int paramInt) {
/* 352 */     ModelIdentifier modelIdentifier = paramModelSource.getIdentifier();
/* 353 */     String str = modelIdentifier.getObject();
/* 354 */     if (str.equals("midi_cc"))
/* 355 */     { processMidiControlSource(paramModelSource, paramInt); }
/* 356 */     else if (str.equals("midi_rpn"))
/* 357 */     { processMidiRpnSource(paramModelSource, paramInt); }
/* 358 */     else if (str.equals("midi_nrpn"))
/* 359 */     { processMidiNrpnSource(paramModelSource, paramInt); }
/* 360 */     else if (str.equals("midi"))
/* 361 */     { processMidiSource(paramModelSource, paramInt); }
/* 362 */     else if (str.equals("noteon"))
/* 363 */     { processNoteOnSource(paramModelSource, paramInt); }
/* 364 */     else { if (str.equals("osc"))
/*     */         return; 
/* 366 */       if (str.equals("mixer")) {
/*     */         return;
/*     */       }
/* 369 */       this.ctrl_connections_list.add(Integer.valueOf(paramInt)); }
/*     */   
/*     */   }
/*     */   private void processMidiControlSource(ModelSource paramModelSource, int paramInt) {
/* 373 */     String str = paramModelSource.getIdentifier().getVariable();
/* 374 */     if (str == null)
/*     */       return; 
/* 376 */     int i = Integer.parseInt(str);
/* 377 */     if (this.midi_ctrl_connections[i] == null) {
/* 378 */       (new int[1])[0] = paramInt; this.midi_ctrl_connections[i] = new int[1];
/*     */     } else {
/* 380 */       int[] arrayOfInt1 = this.midi_ctrl_connections[i];
/* 381 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 382 */       for (byte b = 0; b < arrayOfInt1.length; b++)
/* 383 */         arrayOfInt2[b] = arrayOfInt1[b]; 
/* 384 */       arrayOfInt2[arrayOfInt2.length - 1] = paramInt;
/* 385 */       this.midi_ctrl_connections[i] = arrayOfInt2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processNoteOnSource(ModelSource paramModelSource, int paramInt) {
/* 390 */     String str = paramModelSource.getIdentifier().getVariable();
/* 391 */     byte b = -1;
/* 392 */     if (str.equals("on"))
/* 393 */       b = 3; 
/* 394 */     if (str.equals("keynumber"))
/* 395 */       b = 4; 
/* 396 */     if (b == -1)
/*     */       return; 
/* 398 */     if (this.midi_connections[b] == null) {
/* 399 */       (new int[1])[0] = paramInt; this.midi_connections[b] = new int[1];
/*     */     } else {
/* 401 */       int[] arrayOfInt1 = this.midi_connections[b];
/* 402 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 403 */       for (byte b1 = 0; b1 < arrayOfInt1.length; b1++)
/* 404 */         arrayOfInt2[b1] = arrayOfInt1[b1]; 
/* 405 */       arrayOfInt2[arrayOfInt2.length - 1] = paramInt;
/* 406 */       this.midi_connections[b] = arrayOfInt2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processMidiSource(ModelSource paramModelSource, int paramInt) {
/* 411 */     String str = paramModelSource.getIdentifier().getVariable();
/* 412 */     byte b = -1;
/* 413 */     if (str.equals("pitch"))
/* 414 */       b = 0; 
/* 415 */     if (str.equals("channel_pressure"))
/* 416 */       b = 1; 
/* 417 */     if (str.equals("poly_pressure"))
/* 418 */       b = 2; 
/* 419 */     if (b == -1)
/*     */       return; 
/* 421 */     if (this.midi_connections[b] == null) {
/* 422 */       (new int[1])[0] = paramInt; this.midi_connections[b] = new int[1];
/*     */     } else {
/* 424 */       int[] arrayOfInt1 = this.midi_connections[b];
/* 425 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 426 */       for (byte b1 = 0; b1 < arrayOfInt1.length; b1++)
/* 427 */         arrayOfInt2[b1] = arrayOfInt1[b1]; 
/* 428 */       arrayOfInt2[arrayOfInt2.length - 1] = paramInt;
/* 429 */       this.midi_connections[b] = arrayOfInt2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processMidiRpnSource(ModelSource paramModelSource, int paramInt) {
/* 434 */     String str = paramModelSource.getIdentifier().getVariable();
/* 435 */     if (str == null)
/*     */       return; 
/* 437 */     int i = Integer.parseInt(str);
/* 438 */     if (this.midi_rpn_connections.get(Integer.valueOf(i)) == null) {
/* 439 */       this.midi_rpn_connections.put(Integer.valueOf(i), new int[] { paramInt });
/*     */     } else {
/* 441 */       int[] arrayOfInt1 = this.midi_rpn_connections.get(Integer.valueOf(i));
/* 442 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 443 */       for (byte b = 0; b < arrayOfInt1.length; b++)
/* 444 */         arrayOfInt2[b] = arrayOfInt1[b]; 
/* 445 */       arrayOfInt2[arrayOfInt2.length - 1] = paramInt;
/* 446 */       this.midi_rpn_connections.put(Integer.valueOf(i), arrayOfInt2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processMidiNrpnSource(ModelSource paramModelSource, int paramInt) {
/* 451 */     String str = paramModelSource.getIdentifier().getVariable();
/* 452 */     if (str == null)
/*     */       return; 
/* 454 */     int i = Integer.parseInt(str);
/* 455 */     if (this.midi_nrpn_connections.get(Integer.valueOf(i)) == null) {
/* 456 */       this.midi_nrpn_connections.put(Integer.valueOf(i), new int[] { paramInt });
/*     */     } else {
/* 458 */       int[] arrayOfInt1 = this.midi_nrpn_connections.get(Integer.valueOf(i));
/* 459 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 460 */       for (byte b = 0; b < arrayOfInt1.length; b++)
/* 461 */         arrayOfInt2[b] = arrayOfInt1[b]; 
/* 462 */       arrayOfInt2[arrayOfInt2.length - 1] = paramInt;
/* 463 */       this.midi_nrpn_connections.put(Integer.valueOf(i), arrayOfInt2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SoftPerformer(ModelPerformer paramModelPerformer) {
/* 468 */     this.performer = paramModelPerformer;
/*     */     
/* 470 */     this.keyFrom = paramModelPerformer.getKeyFrom();
/* 471 */     this.keyTo = paramModelPerformer.getKeyTo();
/* 472 */     this.velFrom = paramModelPerformer.getVelFrom();
/* 473 */     this.velTo = paramModelPerformer.getVelTo();
/* 474 */     this.exclusiveClass = paramModelPerformer.getExclusiveClass();
/* 475 */     this.selfNonExclusive = paramModelPerformer.isSelfNonExclusive();
/*     */     
/* 477 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 479 */     ArrayList<ModelConnectionBlock> arrayList1 = new ArrayList();
/* 480 */     arrayList1.addAll(paramModelPerformer.getConnectionBlocks());
/*     */     
/* 482 */     if (paramModelPerformer.isDefaultConnectionsEnabled()) {
/*     */ 
/*     */ 
/*     */       
/* 486 */       boolean bool1 = false; byte b3;
/* 487 */       for (b3 = 0; b3 < arrayList1.size(); b3++) {
/* 488 */         ModelConnectionBlock modelConnectionBlock = arrayList1.get(b3);
/* 489 */         ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 490 */         ModelDestination modelDestination = modelConnectionBlock.getDestination();
/* 491 */         boolean bool3 = false;
/* 492 */         if (modelDestination != null && arrayOfModelSource != null && arrayOfModelSource.length > 1) {
/* 493 */           for (byte b = 0; b < arrayOfModelSource.length; b++) {
/*     */ 
/*     */             
/* 496 */             if (arrayOfModelSource[b].getIdentifier().getObject().equals("midi_cc"))
/*     */             {
/* 498 */               if (arrayOfModelSource[b].getIdentifier().getVariable()
/* 499 */                 .equals("1")) {
/* 500 */                 bool3 = true;
/* 501 */                 bool1 = true;
/*     */                 break;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/* 507 */         if (bool3) {
/*     */           
/* 509 */           ModelConnectionBlock modelConnectionBlock4 = new ModelConnectionBlock();
/* 510 */           modelConnectionBlock4.setSources(modelConnectionBlock.getSources());
/* 511 */           modelConnectionBlock4.setDestination(modelConnectionBlock.getDestination());
/* 512 */           modelConnectionBlock4.addSource(new ModelSource(new ModelIdentifier("midi_rpn", "5")));
/*     */           
/* 514 */           modelConnectionBlock4.setScale(modelConnectionBlock.getScale() * 256.0D);
/* 515 */           arrayList1.set(b3, modelConnectionBlock4);
/*     */         } 
/*     */       } 
/*     */       
/* 519 */       if (!bool1) {
/* 520 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_LFO1, false, true, 0), new ModelSource(new ModelIdentifier("midi_cc", "1", 0), false, false, 0), 50.0D, new ModelDestination(ModelDestination.DESTINATION_PITCH));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 531 */         modelConnectionBlock.addSource(new ModelSource(new ModelIdentifier("midi_rpn", "5")));
/*     */         
/* 533 */         modelConnectionBlock.setScale(modelConnectionBlock.getScale() * 256.0D);
/* 534 */         arrayList1.add(modelConnectionBlock);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 539 */       b3 = 0;
/* 540 */       boolean bool2 = false;
/* 541 */       ModelConnectionBlock modelConnectionBlock1 = null;
/* 542 */       byte b4 = 0;
/*     */       
/* 544 */       for (ModelConnectionBlock modelConnectionBlock : arrayList1) {
/* 545 */         ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 546 */         ModelDestination modelDestination = modelConnectionBlock.getDestination();
/*     */         
/* 548 */         if (modelDestination != null && arrayOfModelSource != null) {
/* 549 */           for (byte b = 0; b < arrayOfModelSource.length; b++) {
/* 550 */             ModelIdentifier modelIdentifier = arrayOfModelSource[b].getIdentifier();
/*     */ 
/*     */             
/* 553 */             if (modelIdentifier.getObject().equals("midi_cc") && 
/* 554 */               modelIdentifier.getVariable().equals("1")) {
/* 555 */               modelConnectionBlock1 = modelConnectionBlock;
/* 556 */               b4 = b;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 561 */             if (modelIdentifier.getObject().equals("midi")) {
/* 562 */               if (modelIdentifier.getVariable().equals("channel_pressure"))
/* 563 */                 b3 = 1; 
/* 564 */               if (modelIdentifier.getVariable().equals("poly_pressure")) {
/* 565 */                 bool2 = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 572 */       if (modelConnectionBlock1 != null) {
/* 573 */         if (b3 == 0) {
/* 574 */           ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock();
/* 575 */           modelConnectionBlock.setDestination(modelConnectionBlock1.getDestination());
/* 576 */           modelConnectionBlock.setScale(modelConnectionBlock1.getScale());
/* 577 */           ModelSource[] arrayOfModelSource1 = modelConnectionBlock1.getSources();
/* 578 */           ModelSource[] arrayOfModelSource2 = new ModelSource[arrayOfModelSource1.length];
/* 579 */           for (byte b = 0; b < arrayOfModelSource2.length; b++)
/* 580 */             arrayOfModelSource2[b] = arrayOfModelSource1[b]; 
/* 581 */           arrayOfModelSource2[b4] = new ModelSource(new ModelIdentifier("midi", "channel_pressure"));
/*     */           
/* 583 */           modelConnectionBlock.setSources(arrayOfModelSource2);
/* 584 */           hashMap.put(extractKeys(modelConnectionBlock), modelConnectionBlock);
/*     */         } 
/* 586 */         if (!bool2) {
/* 587 */           ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock();
/* 588 */           modelConnectionBlock.setDestination(modelConnectionBlock1.getDestination());
/* 589 */           modelConnectionBlock.setScale(modelConnectionBlock1.getScale());
/* 590 */           ModelSource[] arrayOfModelSource1 = modelConnectionBlock1.getSources();
/* 591 */           ModelSource[] arrayOfModelSource2 = new ModelSource[arrayOfModelSource1.length];
/* 592 */           for (byte b = 0; b < arrayOfModelSource2.length; b++)
/* 593 */             arrayOfModelSource2[b] = arrayOfModelSource1[b]; 
/* 594 */           arrayOfModelSource2[b4] = new ModelSource(new ModelIdentifier("midi", "poly_pressure"));
/*     */           
/* 596 */           modelConnectionBlock.setSources(arrayOfModelSource2);
/* 597 */           hashMap.put(extractKeys(modelConnectionBlock), modelConnectionBlock);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 602 */       ModelConnectionBlock modelConnectionBlock2 = null;
/* 603 */       for (ModelConnectionBlock modelConnectionBlock : arrayList1) {
/* 604 */         ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 605 */         if (arrayOfModelSource.length != 0 && arrayOfModelSource[0]
/* 606 */           .getIdentifier().getObject().equals("lfo") && 
/* 607 */           modelConnectionBlock.getDestination().getIdentifier().equals(ModelDestination.DESTINATION_PITCH)) {
/*     */           
/* 609 */           if (modelConnectionBlock2 == null) {
/* 610 */             modelConnectionBlock2 = modelConnectionBlock; continue;
/*     */           } 
/* 612 */           if ((modelConnectionBlock2.getSources()).length > arrayOfModelSource.length) {
/* 613 */             modelConnectionBlock2 = modelConnectionBlock; continue;
/* 614 */           }  if (modelConnectionBlock2.getSources()[0]
/* 615 */             .getIdentifier().getInstance() < 1 && 
/* 616 */             modelConnectionBlock2.getSources()[0]
/* 617 */             .getIdentifier().getInstance() > arrayOfModelSource[0]
/* 618 */             .getIdentifier().getInstance()) {
/* 619 */             modelConnectionBlock2 = modelConnectionBlock;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 628 */       int i = 1;
/*     */       
/* 630 */       if (modelConnectionBlock2 != null)
/*     */       {
/* 632 */         i = modelConnectionBlock2.getSources()[0].getIdentifier().getInstance();
/*     */       }
/*     */ 
/*     */       
/* 636 */       ModelConnectionBlock modelConnectionBlock3 = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "78"), false, true, 0), 2000.0D, new ModelDestination(new ModelIdentifier("lfo", "delay2", i)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 643 */       hashMap.put(extractKeys(modelConnectionBlock3), modelConnectionBlock3);
/*     */ 
/*     */       
/* 646 */       final double scale = (modelConnectionBlock2 == null) ? 0.0D : modelConnectionBlock2.getScale();
/* 647 */       modelConnectionBlock3 = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("lfo", i)), new ModelSource(new ModelIdentifier("midi_cc", "77"), new ModelTransform()
/*     */             {
/*     */ 
/*     */               
/* 651 */               double s = scale;
/*     */               public double transform(double param1Double) {
/* 653 */                 param1Double = param1Double * 2.0D - 1.0D;
/* 654 */                 param1Double *= 600.0D;
/* 655 */                 if (this.s == 0.0D)
/* 656 */                   return param1Double; 
/* 657 */                 if (this.s > 0.0D) {
/* 658 */                   if (param1Double < -this.s)
/* 659 */                     param1Double = -this.s; 
/* 660 */                   return param1Double;
/*     */                 } 
/* 662 */                 if (param1Double < this.s)
/* 663 */                   param1Double = -this.s; 
/* 664 */                 return -param1Double;
/*     */               }
/*     */             }), new ModelDestination(ModelDestination.DESTINATION_PITCH));
/*     */       
/* 668 */       hashMap.put(extractKeys(modelConnectionBlock3), modelConnectionBlock3);
/*     */       
/* 670 */       modelConnectionBlock3 = new ModelConnectionBlock(new ModelSource(new ModelIdentifier("midi_cc", "76"), false, true, 0), 2400.0D, new ModelDestination(new ModelIdentifier("lfo", "freq", i)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 677 */       hashMap.put(extractKeys(modelConnectionBlock3), modelConnectionBlock3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 682 */     if (paramModelPerformer.isDefaultConnectionsEnabled())
/* 683 */       for (ModelConnectionBlock modelConnectionBlock : defaultconnections) {
/* 684 */         hashMap.put(extractKeys(modelConnectionBlock), modelConnectionBlock);
/*     */       } 
/* 686 */     for (ModelConnectionBlock modelConnectionBlock : arrayList1) {
/* 687 */       hashMap.put(extractKeys(modelConnectionBlock), modelConnectionBlock);
/*     */     }
/*     */     
/* 690 */     ArrayList<ModelConnectionBlock> arrayList2 = new ArrayList();
/*     */     
/* 692 */     this.midi_ctrl_connections = new int[128][]; byte b1;
/* 693 */     for (b1 = 0; b1 < this.midi_ctrl_connections.length; b1++) {
/* 694 */       this.midi_ctrl_connections[b1] = null;
/*     */     }
/* 696 */     this.midi_connections = new int[5][];
/* 697 */     for (b1 = 0; b1 < this.midi_connections.length; b1++) {
/* 698 */       this.midi_connections[b1] = null;
/*     */     }
/*     */     
/* 701 */     b1 = 0;
/* 702 */     boolean bool = false;
/*     */     
/* 704 */     for (ModelConnectionBlock modelConnectionBlock : hashMap.values()) {
/* 705 */       if (modelConnectionBlock.getDestination() != null) {
/* 706 */         ModelDestination modelDestination = modelConnectionBlock.getDestination();
/* 707 */         ModelIdentifier modelIdentifier = modelDestination.getIdentifier();
/* 708 */         if (modelIdentifier.getObject().equals("noteon")) {
/* 709 */           bool = true;
/* 710 */           if (modelIdentifier.getVariable().equals("keynumber"))
/* 711 */             this.forcedKeynumber = true; 
/* 712 */           if (modelIdentifier.getVariable().equals("velocity"))
/* 713 */             this.forcedVelocity = true; 
/*     */         } 
/*     */       } 
/* 716 */       if (bool) {
/* 717 */         arrayList2.add(0, modelConnectionBlock);
/* 718 */         bool = false; continue;
/*     */       } 
/* 720 */       arrayList2.add(modelConnectionBlock);
/*     */     } 
/*     */     
/* 723 */     for (ModelConnectionBlock modelConnectionBlock : arrayList2) {
/* 724 */       if (modelConnectionBlock.getSources() != null) {
/* 725 */         ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 726 */         for (byte b = 0; b < arrayOfModelSource.length; b++) {
/* 727 */           processSource(arrayOfModelSource[b], b1);
/*     */         }
/*     */       } 
/* 730 */       b1++;
/*     */     } 
/*     */     
/* 733 */     this.connections = new ModelConnectionBlock[arrayList2.size()];
/* 734 */     arrayList2.toArray(this.connections);
/*     */     
/* 736 */     this.ctrl_connections = new int[this.ctrl_connections_list.size()];
/*     */     
/* 738 */     for (byte b2 = 0; b2 < this.ctrl_connections.length; b2++) {
/* 739 */       this.ctrl_connections[b2] = ((Integer)this.ctrl_connections_list.get(b2)).intValue();
/*     */     }
/* 741 */     this.oscillators = new ModelOscillator[paramModelPerformer.getOscillators().size()];
/* 742 */     paramModelPerformer.getOscillators().toArray(this.oscillators);
/*     */     
/* 744 */     for (ModelConnectionBlock modelConnectionBlock : arrayList2) {
/* 745 */       if (modelConnectionBlock.getDestination() != null && 
/* 746 */         isUnnecessaryTransform(modelConnectionBlock.getDestination().getTransform())) {
/* 747 */         modelConnectionBlock.getDestination().setTransform(null);
/*     */       }
/*     */       
/* 750 */       if (modelConnectionBlock.getSources() != null) {
/* 751 */         for (ModelSource modelSource : modelConnectionBlock.getSources()) {
/* 752 */           if (isUnnecessaryTransform(modelSource.getTransform())) {
/* 753 */             modelSource.setTransform(null);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isUnnecessaryTransform(ModelTransform paramModelTransform) {
/* 762 */     if (paramModelTransform == null)
/* 763 */       return false; 
/* 764 */     if (!(paramModelTransform instanceof ModelStandardTransform))
/* 765 */       return false; 
/* 766 */     ModelStandardTransform modelStandardTransform = (ModelStandardTransform)paramModelTransform;
/* 767 */     if (modelStandardTransform.getDirection())
/* 768 */       return false; 
/* 769 */     if (modelStandardTransform.getPolarity())
/* 770 */       return false; 
/* 771 */     if (modelStandardTransform.getTransform() != 0)
/* 772 */       return false; 
/* 773 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftPerformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */