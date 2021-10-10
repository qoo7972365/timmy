/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.sound.midi.MidiEvent;
/*     */ import javax.sound.midi.MidiMessage;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.Track;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MidiUtils
/*     */ {
/*     */   public static final int DEFAULT_TEMPO_MPQ = 500000;
/*     */   public static final int META_END_OF_TRACK_TYPE = 47;
/*     */   public static final int META_TEMPO_TYPE = 81;
/*     */   
/*     */   public static boolean isMetaEndOfTrack(MidiMessage paramMidiMessage) {
/*  54 */     if (paramMidiMessage.getLength() != 3 || paramMidiMessage
/*  55 */       .getStatus() != 255) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     byte[] arrayOfByte = paramMidiMessage.getMessage();
/*  60 */     return ((arrayOfByte[1] & 0xFF) == 47 && arrayOfByte[2] == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMetaTempo(MidiMessage paramMidiMessage) {
/*  67 */     if (paramMidiMessage.getLength() != 6 || paramMidiMessage
/*  68 */       .getStatus() != 255) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     byte[] arrayOfByte = paramMidiMessage.getMessage();
/*     */     
/*  74 */     return ((arrayOfByte[1] & 0xFF) == 81 && arrayOfByte[2] == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTempoMPQ(MidiMessage paramMidiMessage) {
/*  83 */     if (paramMidiMessage.getLength() != 6 || paramMidiMessage
/*  84 */       .getStatus() != 255) {
/*  85 */       return -1;
/*     */     }
/*  87 */     byte[] arrayOfByte = paramMidiMessage.getMessage();
/*  88 */     if ((arrayOfByte[1] & 0xFF) != 81 || arrayOfByte[2] != 3) {
/*  89 */       return -1;
/*     */     }
/*  91 */     return arrayOfByte[5] & 0xFF | (arrayOfByte[4] & 0xFF) << 8 | (arrayOfByte[3] & 0xFF) << 16;
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
/*     */   public static double convertTempo(double paramDouble) {
/* 104 */     if (paramDouble <= 0.0D) {
/* 105 */       paramDouble = 1.0D;
/*     */     }
/* 107 */     return 6.0E7D / paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long ticks2microsec(long paramLong, double paramDouble, int paramInt) {
/* 117 */     return (long)(paramLong * paramDouble / paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long microsec2ticks(long paramLong, double paramDouble, int paramInt) {
/* 128 */     return (long)(paramLong * paramInt / paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long tick2microsecond(Sequence paramSequence, long paramLong, TempoCache paramTempoCache) {
/* 137 */     if (paramSequence.getDivisionType() != 0.0F) {
/* 138 */       double d = paramLong / (paramSequence.getDivisionType() * paramSequence.getResolution());
/* 139 */       return (long)(1000000.0D * d);
/*     */     } 
/*     */     
/* 142 */     if (paramTempoCache == null) {
/* 143 */       paramTempoCache = new TempoCache(paramSequence);
/*     */     }
/*     */     
/* 146 */     int i = paramSequence.getResolution();
/*     */     
/* 148 */     long[] arrayOfLong = paramTempoCache.ticks;
/* 149 */     int[] arrayOfInt = paramTempoCache.tempos;
/* 150 */     int j = arrayOfInt.length;
/*     */ 
/*     */     
/* 153 */     int k = paramTempoCache.snapshotIndex;
/* 154 */     int m = paramTempoCache.snapshotMicro;
/*     */ 
/*     */     
/* 157 */     long l = 0L;
/*     */     
/* 159 */     if (k <= 0 || k >= j || arrayOfLong[k] > paramLong) {
/*     */ 
/*     */       
/* 162 */       m = 0;
/* 163 */       k = 0;
/*     */     } 
/* 165 */     if (j > 0) {
/*     */       
/* 167 */       int n = k + 1;
/* 168 */       while (n < j && arrayOfLong[n] <= paramLong) {
/* 169 */         m = (int)(m + ticks2microsec(arrayOfLong[n] - arrayOfLong[n - 1], arrayOfInt[n - 1], i));
/* 170 */         k = n;
/* 171 */         n++;
/*     */       } 
/*     */       
/* 174 */       l = m + ticks2microsec(paramLong - arrayOfLong[k], arrayOfInt[k], i);
/*     */     } 
/*     */ 
/*     */     
/* 178 */     paramTempoCache.snapshotIndex = k;
/* 179 */     paramTempoCache.snapshotMicro = m;
/* 180 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long microsecond2tick(Sequence paramSequence, long paramLong, TempoCache paramTempoCache) {
/* 188 */     if (paramSequence.getDivisionType() != 0.0F) {
/*     */ 
/*     */       
/* 191 */       double d = paramLong * paramSequence.getDivisionType() * paramSequence.getResolution() / 1000000.0D;
/*     */       
/* 193 */       long l = (long)d;
/* 194 */       if (paramTempoCache != null) {
/* 195 */         paramTempoCache.currTempo = (int)paramTempoCache.getTempoMPQAt(l);
/*     */       }
/* 197 */       return l;
/*     */     } 
/*     */     
/* 200 */     if (paramTempoCache == null) {
/* 201 */       paramTempoCache = new TempoCache(paramSequence);
/*     */     }
/* 203 */     long[] arrayOfLong = paramTempoCache.ticks;
/* 204 */     int[] arrayOfInt = paramTempoCache.tempos;
/* 205 */     int i = arrayOfInt.length;
/*     */     
/* 207 */     int j = paramSequence.getResolution();
/*     */     
/* 209 */     long l1 = 0L, l2 = 0L; boolean bool = false; byte b = 1;
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (paramLong > 0L && i > 0) {
/*     */       
/* 215 */       while (b < i) {
/* 216 */         long l = l1 + ticks2microsec(arrayOfLong[b] - arrayOfLong[b - 1], arrayOfInt[b - 1], j);
/*     */         
/* 218 */         if (l > paramLong) {
/*     */           break;
/*     */         }
/* 221 */         l1 = l;
/* 222 */         b++;
/*     */       } 
/* 224 */       l2 = arrayOfLong[b - 1] + microsec2ticks(paramLong - l1, arrayOfInt[b - 1], j);
/*     */     } 
/*     */ 
/*     */     
/* 228 */     paramTempoCache.currTempo = arrayOfInt[b - 1];
/* 229 */     return l2;
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
/*     */   public static int tick2index(Track paramTrack, long paramLong) {
/* 241 */     int i = 0;
/* 242 */     if (paramLong > 0L) {
/* 243 */       int j = 0;
/* 244 */       int k = paramTrack.size() - 1;
/* 245 */       while (j < k) {
/*     */         
/* 247 */         i = j + k >> 1;
/*     */         
/* 249 */         long l = paramTrack.get(i).getTick();
/* 250 */         if (l == paramLong)
/*     */           break; 
/* 252 */         if (l < paramLong) {
/*     */           
/* 254 */           if (j == k - 1) {
/*     */             
/* 256 */             i++;
/*     */             break;
/*     */           } 
/* 259 */           j = i;
/*     */           continue;
/*     */         } 
/* 262 */         k = i;
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class TempoCache
/*     */   {
/*     */     long[] ticks;
/*     */     int[] tempos;
/* 274 */     int snapshotIndex = 0;
/*     */     
/* 276 */     int snapshotMicro = 0;
/*     */     
/*     */     int currTempo;
/*     */     
/*     */     private boolean firstTempoIsFake = false;
/*     */ 
/*     */     
/*     */     public TempoCache() {
/* 284 */       this.ticks = new long[1];
/* 285 */       this.tempos = new int[1];
/* 286 */       this.tempos[0] = 500000;
/* 287 */       this.snapshotIndex = 0;
/* 288 */       this.snapshotMicro = 0;
/*     */     }
/*     */     
/*     */     public TempoCache(Sequence param1Sequence) {
/* 292 */       this();
/* 293 */       refresh(param1Sequence);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void refresh(Sequence param1Sequence) {
/* 298 */       ArrayList<MidiEvent> arrayList = new ArrayList();
/* 299 */       Track[] arrayOfTrack = param1Sequence.getTracks();
/* 300 */       if (arrayOfTrack.length > 0) {
/*     */         
/* 302 */         Track track = arrayOfTrack[0];
/* 303 */         int j = track.size();
/* 304 */         for (byte b = 0; b < j; b++) {
/* 305 */           MidiEvent midiEvent = track.get(b);
/* 306 */           MidiMessage midiMessage = midiEvent.getMessage();
/* 307 */           if (MidiUtils.isMetaTempo(midiMessage))
/*     */           {
/* 309 */             arrayList.add(midiEvent);
/*     */           }
/*     */         } 
/*     */       } 
/* 313 */       int i = arrayList.size() + 1;
/* 314 */       this.firstTempoIsFake = true;
/* 315 */       if (i > 1 && ((MidiEvent)arrayList
/* 316 */         .get(0)).getTick() == 0L) {
/*     */         
/* 318 */         i--;
/* 319 */         this.firstTempoIsFake = false;
/*     */       } 
/* 321 */       this.ticks = new long[i];
/* 322 */       this.tempos = new int[i];
/* 323 */       byte b1 = 0;
/* 324 */       if (this.firstTempoIsFake) {
/*     */         
/* 326 */         this.ticks[0] = 0L;
/* 327 */         this.tempos[0] = 500000;
/* 328 */         b1++;
/*     */       } 
/* 330 */       for (byte b2 = 0; b2 < arrayList.size(); b2++, b1++) {
/* 331 */         MidiEvent midiEvent = arrayList.get(b2);
/* 332 */         this.ticks[b1] = midiEvent.getTick();
/* 333 */         this.tempos[b1] = MidiUtils.getTempoMPQ(midiEvent.getMessage());
/*     */       } 
/* 335 */       this.snapshotIndex = 0;
/* 336 */       this.snapshotMicro = 0;
/*     */     }
/*     */     
/*     */     public int getCurrTempoMPQ() {
/* 340 */       return this.currTempo;
/*     */     }
/*     */     
/*     */     float getTempoMPQAt(long param1Long) {
/* 344 */       return getTempoMPQAt(param1Long, -1.0F);
/*     */     }
/*     */     
/*     */     synchronized float getTempoMPQAt(long param1Long, float param1Float) {
/* 348 */       for (byte b = 0; b < this.ticks.length; b++) {
/* 349 */         if (this.ticks[b] > param1Long) {
/* 350 */           if (b > 0) b--; 
/* 351 */           if (param1Float > 0.0F && b == 0 && this.firstTempoIsFake) {
/* 352 */             return param1Float;
/*     */           }
/* 354 */           return this.tempos[b];
/*     */         } 
/*     */       } 
/* 357 */       return this.tempos[this.tempos.length - 1];
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */