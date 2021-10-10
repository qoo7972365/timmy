/*     */ package com.sun.media.sound;
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
/*     */ public final class SoftLowFrequencyOscillator
/*     */   implements SoftProcess
/*     */ {
/*  34 */   private final int max_count = 10;
/*  35 */   private int used_count = 0;
/*  36 */   private final double[][] out = new double[10][1];
/*  37 */   private final double[][] delay = new double[10][1];
/*  38 */   private final double[][] delay2 = new double[10][1];
/*  39 */   private final double[][] freq = new double[10][1];
/*  40 */   private final int[] delay_counter = new int[10];
/*  41 */   private final double[] sin_phase = new double[10];
/*  42 */   private final double[] sin_stepfreq = new double[10];
/*  43 */   private final double[] sin_step = new double[10];
/*  44 */   private double control_time = 0.0D;
/*  45 */   private double sin_factor = 0.0D;
/*     */   
/*     */   private static final double PI2 = 6.283185307179586D;
/*     */   
/*     */   public SoftLowFrequencyOscillator() {
/*  50 */     for (byte b = 0; b < this.sin_stepfreq.length; b++) {
/*  51 */       this.sin_stepfreq[b] = Double.NEGATIVE_INFINITY;
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/*  56 */     for (byte b = 0; b < this.used_count; b++) {
/*  57 */       this.out[b][0] = 0.0D;
/*  58 */       this.delay[b][0] = 0.0D;
/*  59 */       this.delay2[b][0] = 0.0D;
/*  60 */       this.freq[b][0] = 0.0D;
/*  61 */       this.delay_counter[b] = 0;
/*  62 */       this.sin_phase[b] = 0.0D;
/*     */       
/*  64 */       this.sin_stepfreq[b] = Double.NEGATIVE_INFINITY;
/*  65 */       this.sin_step[b] = 0.0D;
/*     */     } 
/*  67 */     this.used_count = 0;
/*     */   }
/*     */   
/*     */   public void init(SoftSynthesizer paramSoftSynthesizer) {
/*  71 */     this.control_time = 1.0D / paramSoftSynthesizer.getControlRate();
/*  72 */     this.sin_factor = this.control_time * 2.0D * Math.PI;
/*  73 */     for (byte b = 0; b < this.used_count; b++) {
/*  74 */       this.delay_counter[b] = (int)(Math.pow(2.0D, this.delay[b][0] / 1200.0D) / this.control_time);
/*     */       
/*  76 */       this.delay_counter[b] = this.delay_counter[b] + (int)(this.delay2[b][0] / this.control_time * 1000.0D);
/*     */     } 
/*  78 */     processControlLogic();
/*     */   }
/*     */   
/*     */   public void processControlLogic() {
/*  82 */     for (byte b = 0; b < this.used_count; b++) {
/*  83 */       if (this.delay_counter[b] > 0) {
/*  84 */         this.delay_counter[b] = this.delay_counter[b] - 1;
/*  85 */         this.out[b][0] = 0.5D;
/*     */       } else {
/*  87 */         double d1 = this.freq[b][0];
/*     */         
/*  89 */         if (this.sin_stepfreq[b] != d1) {
/*  90 */           this.sin_stepfreq[b] = d1;
/*  91 */           double d = 440.0D * Math.exp((d1 - 6900.0D) * 
/*  92 */               Math.log(2.0D) / 1200.0D);
/*  93 */           this.sin_step[b] = d * this.sin_factor;
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
/* 106 */         double d2 = this.sin_phase[b];
/* 107 */         d2 += this.sin_step[b];
/* 108 */         while (d2 > 6.283185307179586D)
/* 109 */           d2 -= 6.283185307179586D; 
/* 110 */         this.out[b][0] = 0.5D + Math.sin(d2) * 0.5D;
/* 111 */         this.sin_phase[b] = d2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] get(int paramInt, String paramString) {
/* 118 */     if (paramInt >= this.used_count)
/* 119 */       this.used_count = paramInt + 1; 
/* 120 */     if (paramString == null)
/* 121 */       return this.out[paramInt]; 
/* 122 */     if (paramString.equals("delay"))
/* 123 */       return this.delay[paramInt]; 
/* 124 */     if (paramString.equals("delay2"))
/* 125 */       return this.delay2[paramInt]; 
/* 126 */     if (paramString.equals("freq"))
/* 127 */       return this.freq[paramInt]; 
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftLowFrequencyOscillator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */