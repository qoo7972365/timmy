/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModelStandardIndexedDirector
/*     */   implements ModelDirector
/*     */ {
/*     */   private final ModelPerformer[] performers;
/*     */   private final ModelDirectedPlayer player;
/*     */   private boolean noteOnUsed = false;
/*     */   private boolean noteOffUsed = false;
/*     */   private byte[][] trantables;
/*     */   private int[] counters;
/*     */   private int[][] mat;
/*     */   
/*     */   public ModelStandardIndexedDirector(ModelPerformer[] paramArrayOfModelPerformer, ModelDirectedPlayer paramModelDirectedPlayer) {
/*  49 */     this.performers = Arrays.<ModelPerformer>copyOf(paramArrayOfModelPerformer, paramArrayOfModelPerformer.length);
/*  50 */     this.player = paramModelDirectedPlayer;
/*  51 */     for (ModelPerformer modelPerformer : this.performers) {
/*  52 */       if (modelPerformer.isReleaseTriggered()) {
/*  53 */         this.noteOffUsed = true;
/*     */       } else {
/*  55 */         this.noteOnUsed = true;
/*     */       } 
/*     */     } 
/*  58 */     buildindex();
/*     */   }
/*     */   
/*     */   private int[] lookupIndex(int paramInt1, int paramInt2) {
/*  62 */     if (paramInt1 >= 0 && paramInt1 < 128 && paramInt2 >= 0 && paramInt2 < 128) {
/*  63 */       byte b1 = this.trantables[0][paramInt1];
/*  64 */       byte b2 = this.trantables[1][paramInt2];
/*  65 */       if (b1 != -1 && b2 != -1) {
/*  66 */         return this.mat[b1 + b2 * this.counters[0]];
/*     */       }
/*     */     } 
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   private int restrict(int paramInt) {
/*  73 */     if (paramInt < 0) return 0; 
/*  74 */     if (paramInt > 127) return 127; 
/*  75 */     return paramInt;
/*     */   }
/*     */   
/*     */   private void buildindex() {
/*  79 */     this.trantables = new byte[2][129];
/*  80 */     this.counters = new int[this.trantables.length];
/*  81 */     for (ModelPerformer modelPerformer : this.performers) {
/*  82 */       int i = modelPerformer.getKeyFrom();
/*  83 */       int j = modelPerformer.getKeyTo();
/*  84 */       int k = modelPerformer.getVelFrom();
/*  85 */       int m = modelPerformer.getVelTo();
/*  86 */       if (i <= j && 
/*  87 */         k <= m) {
/*  88 */         i = restrict(i);
/*  89 */         j = restrict(j);
/*  90 */         k = restrict(k);
/*  91 */         m = restrict(m);
/*  92 */         this.trantables[0][i] = 1;
/*  93 */         this.trantables[0][j + 1] = 1;
/*  94 */         this.trantables[1][k] = 1;
/*  95 */         this.trantables[1][m + 1] = 1;
/*     */       } 
/*  97 */     }  byte b; for (b = 0; b < this.trantables.length; b++) {
/*  98 */       byte[] arrayOfByte = this.trantables[b];
/*  99 */       int i = arrayOfByte.length; int j;
/* 100 */       for (j = i - 1; j >= 0; j--) {
/* 101 */         if (arrayOfByte[j] == 1) {
/* 102 */           arrayOfByte[j] = -1;
/*     */           break;
/*     */         } 
/* 105 */         arrayOfByte[j] = -1;
/*     */       } 
/* 107 */       j = -1;
/* 108 */       for (byte b1 = 0; b1 < i; b1++) {
/* 109 */         if (arrayOfByte[b1] != 0) {
/* 110 */           j++;
/* 111 */           if (arrayOfByte[b1] == -1)
/*     */             break; 
/*     */         } 
/* 114 */         arrayOfByte[b1] = (byte)j;
/*     */       } 
/* 116 */       this.counters[b] = j;
/*     */     } 
/* 118 */     this.mat = new int[this.counters[0] * this.counters[1]][];
/* 119 */     b = 0;
/* 120 */     for (ModelPerformer modelPerformer : this.performers) {
/* 121 */       int i = modelPerformer.getKeyFrom();
/* 122 */       int j = modelPerformer.getKeyTo();
/* 123 */       int k = modelPerformer.getVelFrom();
/* 124 */       int m = modelPerformer.getVelTo();
/* 125 */       if (i <= j && 
/* 126 */         k <= m) {
/* 127 */         i = restrict(i);
/* 128 */         j = restrict(j);
/* 129 */         k = restrict(k);
/* 130 */         m = restrict(m);
/* 131 */         byte b1 = this.trantables[0][i];
/* 132 */         int n = this.trantables[0][j + 1];
/* 133 */         byte b2 = this.trantables[1][k];
/* 134 */         int i1 = this.trantables[1][m + 1];
/* 135 */         if (n == -1)
/* 136 */           n = this.counters[0]; 
/* 137 */         if (i1 == -1)
/* 138 */           i1 = this.counters[1]; 
/* 139 */         for (byte b3 = b2; b3 < i1; b3++) {
/* 140 */           int i2 = b1 + b3 * this.counters[0];
/* 141 */           for (byte b4 = b1; b4 < n; b4++) {
/* 142 */             int[] arrayOfInt = this.mat[i2];
/* 143 */             if (arrayOfInt == null) {
/* 144 */               (new int[1])[0] = b; this.mat[i2] = new int[1];
/*     */             } else {
/* 146 */               int[] arrayOfInt1 = new int[arrayOfInt.length + 1];
/* 147 */               arrayOfInt1[arrayOfInt1.length - 1] = b;
/* 148 */               for (byte b5 = 0; b5 < arrayOfInt.length; b5++)
/* 149 */                 arrayOfInt1[b5] = arrayOfInt[b5]; 
/* 150 */               this.mat[i2] = arrayOfInt1;
/*     */             } 
/* 152 */             i2++;
/*     */           } 
/*     */         } 
/* 155 */         b++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   public void noteOff(int paramInt1, int paramInt2) {
/* 163 */     if (!this.noteOffUsed)
/*     */       return; 
/* 165 */     int[] arrayOfInt = lookupIndex(paramInt1, paramInt2);
/* 166 */     if (arrayOfInt == null)
/* 167 */       return;  for (int i : arrayOfInt) {
/* 168 */       ModelPerformer modelPerformer = this.performers[i];
/* 169 */       if (modelPerformer.isReleaseTriggered()) {
/* 170 */         this.player.play(i, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void noteOn(int paramInt1, int paramInt2) {
/* 176 */     if (!this.noteOnUsed)
/*     */       return; 
/* 178 */     int[] arrayOfInt = lookupIndex(paramInt1, paramInt2);
/* 179 */     if (arrayOfInt == null)
/* 180 */       return;  for (int i : arrayOfInt) {
/* 181 */       ModelPerformer modelPerformer = this.performers[i];
/* 182 */       if (!modelPerformer.isReleaseTriggered())
/* 183 */         this.player.play(i, null); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelStandardIndexedDirector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */