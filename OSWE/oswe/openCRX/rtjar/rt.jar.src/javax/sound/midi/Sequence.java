/*     */ package javax.sound.midi;
/*     */ 
/*     */ import com.sun.media.sound.MidiUtils;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sequence
/*     */ {
/*     */   public static final float PPQ = 0.0F;
/*     */   public static final float SMPTE_24 = 24.0F;
/*     */   public static final float SMPTE_25 = 25.0F;
/*     */   public static final float SMPTE_30DROP = 29.97F;
/*     */   public static final float SMPTE_30 = 30.0F;
/*     */   protected float divisionType;
/*     */   protected int resolution;
/* 114 */   protected Vector<Track> tracks = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence(float paramFloat, int paramInt) throws InvalidMidiDataException {
/* 144 */     if (paramFloat == 0.0F)
/* 145 */     { this.divisionType = 0.0F; }
/* 146 */     else if (paramFloat == 24.0F)
/* 147 */     { this.divisionType = 24.0F; }
/* 148 */     else if (paramFloat == 25.0F)
/* 149 */     { this.divisionType = 25.0F; }
/* 150 */     else if (paramFloat == 29.97F)
/* 151 */     { this.divisionType = 29.97F; }
/* 152 */     else if (paramFloat == 30.0F)
/* 153 */     { this.divisionType = 30.0F; }
/* 154 */     else { throw new InvalidMidiDataException("Unsupported division type: " + paramFloat); }
/*     */     
/* 156 */     this.resolution = paramInt;
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
/*     */   public Sequence(float paramFloat, int paramInt1, int paramInt2) throws InvalidMidiDataException {
/* 190 */     if (paramFloat == 0.0F)
/* 191 */     { this.divisionType = 0.0F; }
/* 192 */     else if (paramFloat == 24.0F)
/* 193 */     { this.divisionType = 24.0F; }
/* 194 */     else if (paramFloat == 25.0F)
/* 195 */     { this.divisionType = 25.0F; }
/* 196 */     else if (paramFloat == 29.97F)
/* 197 */     { this.divisionType = 29.97F; }
/* 198 */     else if (paramFloat == 30.0F)
/* 199 */     { this.divisionType = 30.0F; }
/* 200 */     else { throw new InvalidMidiDataException("Unsupported division type: " + paramFloat); }
/*     */     
/* 202 */     this.resolution = paramInt1;
/*     */     
/* 204 */     for (byte b = 0; b < paramInt2; b++) {
/* 205 */       this.tracks.addElement(new Track());
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
/*     */   
/*     */   public float getDivisionType() {
/* 223 */     return this.divisionType;
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
/*     */   public int getResolution() {
/* 238 */     return this.resolution;
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
/*     */   public Track createTrack() {
/* 252 */     Track track = new Track();
/* 253 */     this.tracks.addElement(track);
/*     */     
/* 255 */     return track;
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
/*     */   public boolean deleteTrack(Track paramTrack) {
/* 270 */     synchronized (this.tracks) {
/*     */       
/* 272 */       return this.tracks.removeElement(paramTrack);
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
/*     */   public Track[] getTracks() {
/* 287 */     return this.tracks.<Track>toArray(new Track[this.tracks.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMicrosecondLength() {
/* 297 */     return MidiUtils.tick2microsecond(this, getTickLength(), null);
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
/*     */   public long getTickLength() {
/* 310 */     long l = 0L;
/*     */     
/* 312 */     synchronized (this.tracks) {
/*     */       
/* 314 */       for (byte b = 0; b < this.tracks.size(); b++) {
/* 315 */         long l1 = ((Track)this.tracks.elementAt(b)).ticks();
/* 316 */         if (l1 > l) {
/* 317 */           l = l1;
/*     */         }
/*     */       } 
/* 320 */       return l;
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
/*     */   
/*     */   public Patch[] getPatchList() {
/* 338 */     return new Patch[0];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/Sequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */