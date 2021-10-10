/*     */ package java.util.concurrent;
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
/*     */ public enum TimeUnit
/*     */ {
/*  75 */   NANOSECONDS {
/*  76 */     public long toNanos(long param1Long) { return param1Long; }
/*  77 */     public long toMicros(long param1Long) { return param1Long / 1000L; }
/*  78 */     public long toMillis(long param1Long) { return param1Long / 1000000L; }
/*  79 */     public long toSeconds(long param1Long) { return param1Long / 1000000000L; }
/*  80 */     public long toMinutes(long param1Long) { return param1Long / 60000000000L; }
/*  81 */     public long toHours(long param1Long) { return param1Long / 3600000000000L; }
/*  82 */     public long toDays(long param1Long) { return param1Long / 86400000000000L; }
/*  83 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toNanos(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/*  84 */       return (int)(param1Long1 - param1Long2 * 1000000L);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/*  90 */   MICROSECONDS {
/*  91 */     public long toNanos(long param1Long) { return null.x(param1Long, 1000L, 9223372036854775L); }
/*  92 */     public long toMicros(long param1Long) { return param1Long; }
/*  93 */     public long toMillis(long param1Long) { return param1Long / 1000L; }
/*  94 */     public long toSeconds(long param1Long) { return param1Long / 1000000L; }
/*  95 */     public long toMinutes(long param1Long) { return param1Long / 60000000L; }
/*  96 */     public long toHours(long param1Long) { return param1Long / 3600000000L; }
/*  97 */     public long toDays(long param1Long) { return param1Long / 86400000000L; }
/*  98 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toMicros(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/*  99 */       return (int)(param1Long1 * 1000L - param1Long2 * 1000000L);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/* 105 */   MILLISECONDS {
/* 106 */     public long toNanos(long param1Long) { return null.x(param1Long, 1000000L, 9223372036854L); }
/* 107 */     public long toMicros(long param1Long) { return null.x(param1Long, 1000L, 9223372036854775L); }
/* 108 */     public long toMillis(long param1Long) { return param1Long; }
/* 109 */     public long toSeconds(long param1Long) { return param1Long / 1000L; }
/* 110 */     public long toMinutes(long param1Long) { return param1Long / 60000L; }
/* 111 */     public long toHours(long param1Long) { return param1Long / 3600000L; }
/* 112 */     public long toDays(long param1Long) { return param1Long / 86400000L; }
/* 113 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toMillis(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/* 114 */       return 0;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/* 120 */   SECONDS {
/* 121 */     public long toNanos(long param1Long) { return null.x(param1Long, 1000000000L, 9223372036L); }
/* 122 */     public long toMicros(long param1Long) { return null.x(param1Long, 1000000L, 9223372036854L); }
/* 123 */     public long toMillis(long param1Long) { return null.x(param1Long, 1000L, 9223372036854775L); }
/* 124 */     public long toSeconds(long param1Long) { return param1Long; }
/* 125 */     public long toMinutes(long param1Long) { return param1Long / 60L; }
/* 126 */     public long toHours(long param1Long) { return param1Long / 3600L; }
/* 127 */     public long toDays(long param1Long) { return param1Long / 86400L; }
/* 128 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toSeconds(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/* 129 */       return 0;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/* 135 */   MINUTES {
/* 136 */     public long toNanos(long param1Long) { return null.x(param1Long, 60000000000L, 153722867L); }
/* 137 */     public long toMicros(long param1Long) { return null.x(param1Long, 60000000L, 153722867280L); }
/* 138 */     public long toMillis(long param1Long) { return null.x(param1Long, 60000L, 153722867280912L); }
/* 139 */     public long toSeconds(long param1Long) { return null.x(param1Long, 60L, 153722867280912930L); }
/* 140 */     public long toMinutes(long param1Long) { return param1Long; }
/* 141 */     public long toHours(long param1Long) { return param1Long / 60L; }
/* 142 */     public long toDays(long param1Long) { return param1Long / 1440L; }
/* 143 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toMinutes(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/* 144 */       return 0;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/* 150 */   HOURS {
/* 151 */     public long toNanos(long param1Long) { return null.x(param1Long, 3600000000000L, 2562047L); }
/* 152 */     public long toMicros(long param1Long) { return null.x(param1Long, 3600000000L, 2562047788L); }
/* 153 */     public long toMillis(long param1Long) { return null.x(param1Long, 3600000L, 2562047788015L); }
/* 154 */     public long toSeconds(long param1Long) { return null.x(param1Long, 3600L, 2562047788015215L); }
/* 155 */     public long toMinutes(long param1Long) { return null.x(param1Long, 60L, 153722867280912930L); }
/* 156 */     public long toHours(long param1Long) { return param1Long; }
/* 157 */     public long toDays(long param1Long) { return param1Long / 24L; }
/* 158 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toHours(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/* 159 */       return 0;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */   
/* 165 */   DAYS {
/* 166 */     public long toNanos(long param1Long) { return null.x(param1Long, 86400000000000L, 106751L); }
/* 167 */     public long toMicros(long param1Long) { return null.x(param1Long, 86400000000L, 106751991L); }
/* 168 */     public long toMillis(long param1Long) { return null.x(param1Long, 86400000L, 106751991167L); }
/* 169 */     public long toSeconds(long param1Long) { return null.x(param1Long, 86400L, 106751991167300L); }
/* 170 */     public long toMinutes(long param1Long) { return null.x(param1Long, 1440L, 6405119470038038L); }
/* 171 */     public long toHours(long param1Long) { return null.x(param1Long, 24L, 384307168202282325L); }
/* 172 */     public long toDays(long param1Long) { return param1Long; }
/* 173 */     public long convert(long param1Long, TimeUnit param1TimeUnit) { return param1TimeUnit.toDays(param1Long); } int excessNanos(long param1Long1, long param1Long2) {
/* 174 */       return 0;
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   static final long C0 = 1L;
/*     */   
/*     */   static final long C1 = 1000L;
/*     */   
/*     */   static final long C2 = 1000000L;
/*     */   
/*     */   static final long C3 = 1000000000L;
/*     */   
/*     */   static final long C4 = 60000000000L;
/*     */   static final long C5 = 3600000000000L;
/*     */   static final long C6 = 86400000000000L;
/*     */   static final long MAX = 9223372036854775807L;
/*     */   
/*     */   static long x(long paramLong1, long paramLong2, long paramLong3) {
/* 193 */     if (paramLong1 > paramLong3) return Long.MAX_VALUE; 
/* 194 */     if (paramLong1 < -paramLong3) return Long.MIN_VALUE; 
/* 195 */     return paramLong1 * paramLong2;
/*     */   }
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
/*     */   public long convert(long paramLong, TimeUnit paramTimeUnit) {
/* 222 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toNanos(long paramLong) {
/* 234 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toMicros(long paramLong) {
/* 246 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toMillis(long paramLong) {
/* 258 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toSeconds(long paramLong) {
/* 270 */     throw new AbstractMethodError();
/*     */   }
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
/*     */   public long toMinutes(long paramLong) {
/* 283 */     throw new AbstractMethodError();
/*     */   }
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
/*     */   public long toHours(long paramLong) {
/* 296 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toDays(long paramLong) {
/* 307 */     throw new AbstractMethodError();
/*     */   }
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
/*     */   public void timedWait(Object paramObject, long paramLong) throws InterruptedException {
/* 345 */     if (paramLong > 0L) {
/* 346 */       long l = toMillis(paramLong);
/* 347 */       int i = excessNanos(paramLong, l);
/* 348 */       paramObject.wait(l, i);
/*     */     } 
/*     */   }
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
/*     */   public void timedJoin(Thread paramThread, long paramLong) throws InterruptedException {
/* 365 */     if (paramLong > 0L) {
/* 366 */       long l = toMillis(paramLong);
/* 367 */       int i = excessNanos(paramLong, l);
/* 368 */       paramThread.join(l, i);
/*     */     } 
/*     */   }
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
/*     */   public void sleep(long paramLong) throws InterruptedException {
/* 383 */     if (paramLong > 0L) {
/* 384 */       long l = toMillis(paramLong);
/* 385 */       int i = excessNanos(paramLong, l);
/* 386 */       Thread.sleep(l, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   abstract int excessNanos(long paramLong1, long paramLong2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/TimeUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */