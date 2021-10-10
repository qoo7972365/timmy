/*     */ package javax.sound.midi;
/*     */ 
/*     */ import com.sun.media.sound.MidiUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Track
/*     */ {
/*  69 */   private ArrayList eventsList = new ArrayList();
/*     */ 
/*     */   
/*  72 */   private HashSet set = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MidiEvent eotEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Track() {
/*  83 */     ImmutableEndOfTrack immutableEndOfTrack = new ImmutableEndOfTrack();
/*  84 */     this.eotEvent = new MidiEvent(immutableEndOfTrack, 0L);
/*  85 */     this.eventsList.add(this.eotEvent);
/*  86 */     this.set.add(this.eotEvent);
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
/*     */   public boolean add(MidiEvent paramMidiEvent) {
/* 100 */     if (paramMidiEvent == null) {
/* 101 */       return false;
/*     */     }
/* 103 */     synchronized (this.eventsList) {
/*     */       
/* 105 */       if (!this.set.contains(paramMidiEvent)) {
/* 106 */         int i = this.eventsList.size();
/*     */ 
/*     */         
/* 109 */         MidiEvent midiEvent = null;
/* 110 */         if (i > 0) {
/* 111 */           midiEvent = this.eventsList.get(i - 1);
/*     */         }
/*     */         
/* 114 */         if (midiEvent != this.eotEvent) {
/*     */           
/* 116 */           if (midiEvent != null) {
/*     */             
/* 118 */             this.eotEvent.setTick(midiEvent.getTick());
/*     */           } else {
/*     */             
/* 121 */             this.eotEvent.setTick(0L);
/*     */           } 
/*     */ 
/*     */           
/* 125 */           this.eventsList.add(this.eotEvent);
/* 126 */           this.set.add(this.eotEvent);
/* 127 */           i = this.eventsList.size();
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 132 */         if (MidiUtils.isMetaEndOfTrack(paramMidiEvent.getMessage())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 138 */           if (paramMidiEvent.getTick() > this.eotEvent.getTick()) {
/* 139 */             this.eotEvent.setTick(paramMidiEvent.getTick());
/*     */           }
/* 141 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 145 */         this.set.add(paramMidiEvent);
/*     */ 
/*     */ 
/*     */         
/* 149 */         int j = i;
/* 150 */         for (; j > 0 && 
/* 151 */           paramMidiEvent.getTick() < ((MidiEvent)this.eventsList.get(j - 1)).getTick(); j--);
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (j == i) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 162 */           this.eventsList.set(i - 1, paramMidiEvent);
/*     */           
/* 164 */           if (this.eotEvent.getTick() < paramMidiEvent.getTick()) {
/* 165 */             this.eotEvent.setTick(paramMidiEvent.getTick());
/*     */           }
/*     */           
/* 168 */           this.eventsList.add(this.eotEvent);
/*     */         } else {
/* 170 */           this.eventsList.add(j, paramMidiEvent);
/*     */         } 
/* 172 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return false;
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
/*     */   public boolean remove(MidiEvent paramMidiEvent) {
/* 198 */     synchronized (this.eventsList) {
/* 199 */       if (this.set.remove(paramMidiEvent)) {
/* 200 */         int i = this.eventsList.indexOf(paramMidiEvent);
/* 201 */         if (i >= 0) {
/* 202 */           this.eventsList.remove(i);
/* 203 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 207 */     return false;
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
/*     */   public MidiEvent get(int paramInt) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/* 222 */       synchronized (this.eventsList) {
/* 223 */         return this.eventsList.get(paramInt);
/*     */       } 
/* 225 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 226 */       throw new ArrayIndexOutOfBoundsException(indexOutOfBoundsException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 236 */     synchronized (this.eventsList) {
/* 237 */       return this.eventsList.size();
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
/*     */   public long ticks() {
/* 253 */     long l = 0L;
/* 254 */     synchronized (this.eventsList) {
/* 255 */       if (this.eventsList.size() > 0) {
/* 256 */         l = ((MidiEvent)this.eventsList.get(this.eventsList.size() - 1)).getTick();
/*     */       }
/*     */     } 
/* 259 */     return l;
/*     */   }
/*     */   
/*     */   private static class ImmutableEndOfTrack extends MetaMessage {
/*     */     private ImmutableEndOfTrack() {
/* 264 */       super(new byte[3]);
/* 265 */       this.data[0] = -1;
/* 266 */       this.data[1] = 47;
/* 267 */       this.data[2] = 0;
/*     */     }
/*     */     
/*     */     public void setMessage(int param1Int1, byte[] param1ArrayOfbyte, int param1Int2) throws InvalidMidiDataException {
/* 271 */       throw new InvalidMidiDataException("cannot modify end of track message");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/Track.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */