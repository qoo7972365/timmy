/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.sound.midi.ControllerEventListener;
/*      */ import javax.sound.midi.InvalidMidiDataException;
/*      */ import javax.sound.midi.MetaEventListener;
/*      */ import javax.sound.midi.MetaMessage;
/*      */ import javax.sound.midi.MidiDevice;
/*      */ import javax.sound.midi.MidiEvent;
/*      */ import javax.sound.midi.MidiMessage;
/*      */ import javax.sound.midi.MidiSystem;
/*      */ import javax.sound.midi.MidiUnavailableException;
/*      */ import javax.sound.midi.Receiver;
/*      */ import javax.sound.midi.Sequence;
/*      */ import javax.sound.midi.Sequencer;
/*      */ import javax.sound.midi.ShortMessage;
/*      */ import javax.sound.midi.Synthesizer;
/*      */ import javax.sound.midi.Track;
/*      */ import javax.sound.midi.Transmitter;
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
/*      */ final class RealTimeSequencer
/*      */   extends AbstractMidiDevice
/*      */   implements Sequencer, AutoConnectSequencer
/*      */ {
/*      */   private static final boolean DEBUG_PUMP = false;
/*      */   private static final boolean DEBUG_PUMP_ALL = false;
/*   61 */   private static final Map<ThreadGroup, EventDispatcher> dispatchers = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   static final RealTimeSequencerInfo info = new RealTimeSequencerInfo();
/*      */ 
/*      */   
/*   70 */   private static final Sequencer.SyncMode[] masterSyncModes = new Sequencer.SyncMode[] { Sequencer.SyncMode.INTERNAL_CLOCK };
/*   71 */   private static final Sequencer.SyncMode[] slaveSyncModes = new Sequencer.SyncMode[] { Sequencer.SyncMode.NO_SYNC };
/*      */   
/*   73 */   private static final Sequencer.SyncMode masterSyncMode = Sequencer.SyncMode.INTERNAL_CLOCK;
/*   74 */   private static final Sequencer.SyncMode slaveSyncMode = Sequencer.SyncMode.NO_SYNC;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   private Sequence sequence = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   private double cacheTempoMPQ = -1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   private float cacheTempoFactor = -1.0F;
/*      */ 
/*      */ 
/*      */   
/*   99 */   private boolean[] trackMuted = null;
/*      */   
/*  101 */   private boolean[] trackSolo = null;
/*      */ 
/*      */   
/*  104 */   private final MidiUtils.TempoCache tempoCache = new MidiUtils.TempoCache();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean running = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PlayThread playThread;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean recording = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  125 */   private final List recordingTracks = new ArrayList();
/*      */ 
/*      */   
/*  128 */   private long loopStart = 0L;
/*  129 */   private long loopEnd = -1L;
/*  130 */   private int loopCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private final ArrayList metaEventListeners = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   private final ArrayList controllerEventListeners = new ArrayList();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoConnect = false;
/*      */ 
/*      */   
/*      */   private boolean doAutoConnectAtNextOpen = false;
/*      */ 
/*      */   
/*  152 */   Receiver autoConnectedReceiver = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   RealTimeSequencer() throws MidiUnavailableException {
/*  158 */     super(info);
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
/*      */   public synchronized void setSequence(Sequence paramSequence) throws InvalidMidiDataException {
/*  172 */     if (paramSequence != this.sequence) {
/*  173 */       if (this.sequence != null && paramSequence == null) {
/*  174 */         setCaches();
/*  175 */         stop();
/*      */         
/*  177 */         this.trackMuted = null;
/*  178 */         this.trackSolo = null;
/*  179 */         this.loopStart = 0L;
/*  180 */         this.loopEnd = -1L;
/*  181 */         this.loopCount = 0;
/*  182 */         if (getDataPump() != null) {
/*  183 */           getDataPump().setTickPos(0L);
/*  184 */           getDataPump().resetLoopCount();
/*      */         } 
/*      */       } 
/*      */       
/*  188 */       if (this.playThread != null) {
/*  189 */         this.playThread.setSequence(paramSequence);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  194 */       this.sequence = paramSequence;
/*      */       
/*  196 */       if (paramSequence != null) {
/*  197 */         this.tempoCache.refresh(paramSequence);
/*      */         
/*  199 */         setTickPosition(0L);
/*      */         
/*  201 */         propagateCaches();
/*      */       }
/*      */     
/*  204 */     } else if (paramSequence != null) {
/*  205 */       this.tempoCache.refresh(paramSequence);
/*  206 */       if (this.playThread != null) {
/*  207 */         this.playThread.setSequence(paramSequence);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setSequence(InputStream paramInputStream) throws IOException, InvalidMidiDataException {
/*  219 */     if (paramInputStream == null) {
/*  220 */       setSequence((Sequence)null);
/*      */       
/*      */       return;
/*      */     } 
/*  224 */     Sequence sequence = MidiSystem.getSequence(paramInputStream);
/*      */     
/*  226 */     setSequence(sequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sequence getSequence() {
/*  234 */     return this.sequence;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void start() {
/*  242 */     if (!isOpen()) {
/*  243 */       throw new IllegalStateException("sequencer not open");
/*      */     }
/*      */ 
/*      */     
/*  247 */     if (this.sequence == null) {
/*  248 */       throw new IllegalStateException("sequence not set");
/*      */     }
/*      */ 
/*      */     
/*  252 */     if (this.running == true) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  257 */     implStart();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void stop() {
/*  266 */     if (!isOpen()) {
/*  267 */       throw new IllegalStateException("sequencer not open");
/*      */     }
/*  269 */     stopRecording();
/*      */ 
/*      */     
/*  272 */     if (!this.running) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  278 */     implStop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRunning() {
/*  285 */     return this.running;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startRecording() {
/*  290 */     if (!isOpen()) {
/*  291 */       throw new IllegalStateException("Sequencer not open");
/*      */     }
/*      */     
/*  294 */     start();
/*  295 */     this.recording = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopRecording() {
/*  300 */     if (!isOpen()) {
/*  301 */       throw new IllegalStateException("Sequencer not open");
/*      */     }
/*  303 */     this.recording = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRecording() {
/*  308 */     return this.recording;
/*      */   }
/*      */ 
/*      */   
/*      */   public void recordEnable(Track paramTrack, int paramInt) {
/*  313 */     if (!findTrack(paramTrack)) {
/*  314 */       throw new IllegalArgumentException("Track does not exist in the current sequence");
/*      */     }
/*      */     
/*  317 */     synchronized (this.recordingTracks) {
/*  318 */       RecordingTrack recordingTrack = RecordingTrack.get(this.recordingTracks, paramTrack);
/*  319 */       if (recordingTrack != null) {
/*  320 */         recordingTrack.channel = paramInt;
/*      */       } else {
/*  322 */         this.recordingTracks.add(new RecordingTrack(paramTrack, paramInt));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void recordDisable(Track paramTrack) {
/*  330 */     synchronized (this.recordingTracks) {
/*  331 */       RecordingTrack recordingTrack = RecordingTrack.get(this.recordingTracks, paramTrack);
/*  332 */       if (recordingTrack != null) {
/*  333 */         this.recordingTracks.remove(recordingTrack);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findTrack(Track paramTrack) {
/*  341 */     boolean bool = false;
/*  342 */     if (this.sequence != null) {
/*  343 */       Track[] arrayOfTrack = this.sequence.getTracks();
/*  344 */       for (byte b = 0; b < arrayOfTrack.length; b++) {
/*  345 */         if (paramTrack == arrayOfTrack[b]) {
/*  346 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  351 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTempoInBPM() {
/*  358 */     return (float)MidiUtils.convertTempo(getTempoInMPQ());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTempoInBPM(float paramFloat) {
/*  364 */     if (paramFloat <= 0.0F)
/*      */     {
/*  366 */       paramFloat = 1.0F;
/*      */     }
/*      */     
/*  369 */     setTempoInMPQ((float)MidiUtils.convertTempo(paramFloat));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTempoInMPQ() {
/*  376 */     if (needCaching()) {
/*      */       
/*  378 */       if (this.cacheTempoMPQ != -1.0D) {
/*  379 */         return (float)this.cacheTempoMPQ;
/*      */       }
/*      */       
/*  382 */       if (this.sequence != null) {
/*  383 */         return this.tempoCache.getTempoMPQAt(getTickPosition());
/*      */       }
/*      */ 
/*      */       
/*  387 */       return 500000.0F;
/*      */     } 
/*  389 */     return getDataPump().getTempoMPQ();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTempoInMPQ(float paramFloat) {
/*  394 */     if (paramFloat <= 0.0F)
/*      */     {
/*  396 */       paramFloat = 1.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  401 */     if (needCaching()) {
/*      */       
/*  403 */       this.cacheTempoMPQ = paramFloat;
/*      */     } else {
/*      */       
/*  406 */       getDataPump().setTempoMPQ(paramFloat);
/*      */ 
/*      */       
/*  409 */       this.cacheTempoMPQ = -1.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTempoFactor(float paramFloat) {
/*  415 */     if (paramFloat <= 0.0F) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     if (needCaching()) {
/*  423 */       this.cacheTempoFactor = paramFloat;
/*      */     } else {
/*  425 */       getDataPump().setTempoFactor(paramFloat);
/*      */       
/*  427 */       this.cacheTempoFactor = -1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTempoFactor() {
/*  435 */     if (needCaching()) {
/*  436 */       if (this.cacheTempoFactor != -1.0F) {
/*  437 */         return this.cacheTempoFactor;
/*      */       }
/*  439 */       return 1.0F;
/*      */     } 
/*  441 */     return getDataPump().getTempoFactor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTickLength() {
/*  448 */     if (this.sequence == null) {
/*  449 */       return 0L;
/*      */     }
/*      */     
/*  452 */     return this.sequence.getTickLength();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized long getTickPosition() {
/*  459 */     if (getDataPump() == null || this.sequence == null) {
/*  460 */       return 0L;
/*      */     }
/*      */     
/*  463 */     return getDataPump().getTickPos();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setTickPosition(long paramLong) {
/*  468 */     if (paramLong < 0L) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  475 */     if (getDataPump() == null) {
/*  476 */       if (paramLong != 0L);
/*      */ 
/*      */     
/*      */     }
/*  480 */     else if (this.sequence == null) {
/*  481 */       if (paramLong != 0L);
/*      */     }
/*      */     else {
/*      */       
/*  485 */       getDataPump().setTickPos(paramLong);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMicrosecondLength() {
/*  493 */     if (this.sequence == null) {
/*  494 */       return 0L;
/*      */     }
/*      */     
/*  497 */     return this.sequence.getMicrosecondLength();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMicrosecondPosition() {
/*  504 */     if (getDataPump() == null || this.sequence == null) {
/*  505 */       return 0L;
/*      */     }
/*  507 */     synchronized (this.tempoCache) {
/*  508 */       return MidiUtils.tick2microsecond(this.sequence, getDataPump().getTickPos(), this.tempoCache);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMicrosecondPosition(long paramLong) {
/*  514 */     if (paramLong < 0L) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  521 */     if (getDataPump() == null) {
/*  522 */       if (paramLong != 0L);
/*      */ 
/*      */     
/*      */     }
/*  526 */     else if (this.sequence == null) {
/*  527 */       if (paramLong != 0L);
/*      */     }
/*      */     else {
/*      */       
/*  531 */       synchronized (this.tempoCache) {
/*  532 */         setTickPosition(MidiUtils.microsecond2tick(this.sequence, paramLong, this.tempoCache));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMasterSyncMode(Sequencer.SyncMode paramSyncMode) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public Sequencer.SyncMode getMasterSyncMode() {
/*  544 */     return masterSyncMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public Sequencer.SyncMode[] getMasterSyncModes() {
/*  549 */     Sequencer.SyncMode[] arrayOfSyncMode = new Sequencer.SyncMode[masterSyncModes.length];
/*  550 */     System.arraycopy(masterSyncModes, 0, arrayOfSyncMode, 0, masterSyncModes.length);
/*  551 */     return arrayOfSyncMode;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlaveSyncMode(Sequencer.SyncMode paramSyncMode) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public Sequencer.SyncMode getSlaveSyncMode() {
/*  561 */     return slaveSyncMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public Sequencer.SyncMode[] getSlaveSyncModes() {
/*  566 */     Sequencer.SyncMode[] arrayOfSyncMode = new Sequencer.SyncMode[slaveSyncModes.length];
/*  567 */     System.arraycopy(slaveSyncModes, 0, arrayOfSyncMode, 0, slaveSyncModes.length);
/*  568 */     return arrayOfSyncMode;
/*      */   }
/*      */   
/*      */   int getTrackCount() {
/*  572 */     Sequence sequence = getSequence();
/*  573 */     if (sequence != null)
/*      */     {
/*  575 */       return (this.sequence.getTracks()).length;
/*      */     }
/*  577 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setTrackMute(int paramInt, boolean paramBoolean) {
/*  583 */     int i = getTrackCount();
/*  584 */     if (paramInt < 0 || paramInt >= getTrackCount())
/*  585 */       return;  this.trackMuted = ensureBoolArraySize(this.trackMuted, i);
/*  586 */     this.trackMuted[paramInt] = paramBoolean;
/*  587 */     if (getDataPump() != null) {
/*  588 */       getDataPump().muteSoloChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean getTrackMute(int paramInt) {
/*  594 */     if (paramInt < 0 || paramInt >= getTrackCount()) return false; 
/*  595 */     if (this.trackMuted == null || this.trackMuted.length <= paramInt) return false; 
/*  596 */     return this.trackMuted[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setTrackSolo(int paramInt, boolean paramBoolean) {
/*  601 */     int i = getTrackCount();
/*  602 */     if (paramInt < 0 || paramInt >= getTrackCount())
/*  603 */       return;  this.trackSolo = ensureBoolArraySize(this.trackSolo, i);
/*  604 */     this.trackSolo[paramInt] = paramBoolean;
/*  605 */     if (getDataPump() != null) {
/*  606 */       getDataPump().muteSoloChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean getTrackSolo(int paramInt) {
/*  612 */     if (paramInt < 0 || paramInt >= getTrackCount()) return false; 
/*  613 */     if (this.trackSolo == null || this.trackSolo.length <= paramInt) return false; 
/*  614 */     return this.trackSolo[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addMetaEventListener(MetaEventListener paramMetaEventListener) {
/*  619 */     synchronized (this.metaEventListeners) {
/*  620 */       if (!this.metaEventListeners.contains(paramMetaEventListener))
/*      */       {
/*  622 */         this.metaEventListeners.add(paramMetaEventListener);
/*      */       }
/*  624 */       return true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeMetaEventListener(MetaEventListener paramMetaEventListener) {
/*  630 */     synchronized (this.metaEventListeners) {
/*  631 */       int i = this.metaEventListeners.indexOf(paramMetaEventListener);
/*  632 */       if (i >= 0) {
/*  633 */         this.metaEventListeners.remove(i);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] addControllerEventListener(ControllerEventListener paramControllerEventListener, int[] paramArrayOfint) {
/*  640 */     synchronized (this.controllerEventListeners) {
/*      */ 
/*      */ 
/*      */       
/*  644 */       ControllerListElement controllerListElement = null;
/*  645 */       boolean bool = false;
/*  646 */       for (byte b = 0; b < this.controllerEventListeners.size(); b++) {
/*      */         
/*  648 */         controllerListElement = this.controllerEventListeners.get(b);
/*      */         
/*  650 */         if (controllerListElement.listener.equals(paramControllerEventListener)) {
/*  651 */           controllerListElement.addControllers(paramArrayOfint);
/*  652 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  656 */       if (!bool) {
/*  657 */         controllerListElement = new ControllerListElement(paramControllerEventListener, paramArrayOfint);
/*  658 */         this.controllerEventListeners.add(controllerListElement);
/*      */       } 
/*      */ 
/*      */       
/*  662 */       return controllerListElement.getControllers();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] removeControllerEventListener(ControllerEventListener paramControllerEventListener, int[] paramArrayOfint) {
/*  668 */     synchronized (this.controllerEventListeners) {
/*  669 */       ControllerListElement controllerListElement = null;
/*  670 */       boolean bool = false; int i;
/*  671 */       for (i = 0; i < this.controllerEventListeners.size(); i++) {
/*  672 */         controllerListElement = this.controllerEventListeners.get(i);
/*  673 */         if (controllerListElement.listener.equals(paramControllerEventListener)) {
/*  674 */           controllerListElement.removeControllers(paramArrayOfint);
/*  675 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  679 */       if (!bool) {
/*  680 */         return new int[0];
/*      */       }
/*  682 */       if (paramArrayOfint == null) {
/*  683 */         i = this.controllerEventListeners.indexOf(controllerListElement);
/*  684 */         if (i >= 0) {
/*  685 */           this.controllerEventListeners.remove(i);
/*      */         }
/*  687 */         return new int[0];
/*      */       } 
/*  689 */       return controllerListElement.getControllers();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoopStartPoint(long paramLong) {
/*  697 */     if (paramLong > getTickLength() || (this.loopEnd != -1L && paramLong > this.loopEnd) || paramLong < 0L)
/*      */     {
/*      */       
/*  700 */       throw new IllegalArgumentException("invalid loop start point: " + paramLong);
/*      */     }
/*  702 */     this.loopStart = paramLong;
/*      */   }
/*      */   
/*      */   public long getLoopStartPoint() {
/*  706 */     return this.loopStart;
/*      */   }
/*      */   
/*      */   public void setLoopEndPoint(long paramLong) {
/*  710 */     if (paramLong > getTickLength() || (this.loopStart > paramLong && paramLong != -1L) || paramLong < -1L)
/*      */     {
/*      */       
/*  713 */       throw new IllegalArgumentException("invalid loop end point: " + paramLong);
/*      */     }
/*  715 */     this.loopEnd = paramLong;
/*      */   }
/*      */   
/*      */   public long getLoopEndPoint() {
/*  719 */     return this.loopEnd;
/*      */   }
/*      */   
/*      */   public void setLoopCount(int paramInt) {
/*  723 */     if (paramInt != -1 && paramInt < 0)
/*      */     {
/*  725 */       throw new IllegalArgumentException("illegal value for loop count: " + paramInt);
/*      */     }
/*  727 */     this.loopCount = paramInt;
/*  728 */     if (getDataPump() != null) {
/*  729 */       getDataPump().resetLoopCount();
/*      */     }
/*      */   }
/*      */   
/*      */   public int getLoopCount() {
/*  734 */     return this.loopCount;
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
/*      */   protected void implOpen() throws MidiUnavailableException {
/*  748 */     this.playThread = new PlayThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  754 */     if (this.sequence != null) {
/*  755 */       this.playThread.setSequence(this.sequence);
/*      */     }
/*      */ 
/*      */     
/*  759 */     propagateCaches();
/*      */     
/*  761 */     if (this.doAutoConnectAtNextOpen) {
/*  762 */       doAutoConnect();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doAutoConnect() {
/*  769 */     Receiver receiver = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  775 */       Synthesizer synthesizer = MidiSystem.getSynthesizer();
/*  776 */       if (synthesizer instanceof ReferenceCountingDevice) {
/*  777 */         receiver = ((ReferenceCountingDevice)synthesizer).getReceiverReferenceCounting();
/*      */       } else {
/*  779 */         synthesizer.open();
/*      */         try {
/*  781 */           receiver = synthesizer.getReceiver();
/*      */         } finally {
/*      */           
/*  784 */           if (receiver == null) {
/*  785 */             synthesizer.close();
/*      */           }
/*      */         } 
/*      */       } 
/*  789 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  792 */     if (receiver == null) {
/*      */       
/*      */       try {
/*  795 */         receiver = MidiSystem.getReceiver();
/*  796 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/*  800 */     if (receiver != null) {
/*  801 */       this.autoConnectedReceiver = receiver;
/*      */       try {
/*  803 */         getTransmitter().setReceiver(receiver);
/*  804 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void propagateCaches() {
/*  811 */     if (this.sequence != null && isOpen()) {
/*  812 */       if (this.cacheTempoFactor != -1.0F) {
/*  813 */         setTempoFactor(this.cacheTempoFactor);
/*      */       }
/*  815 */       if (this.cacheTempoMPQ == -1.0D) {
/*  816 */         setTempoInMPQ((new MidiUtils.TempoCache(this.sequence)).getTempoMPQAt(getTickPosition()));
/*      */       } else {
/*  818 */         setTempoInMPQ((float)this.cacheTempoMPQ);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized void setCaches() {
/*  825 */     this.cacheTempoFactor = getTempoFactor();
/*  826 */     this.cacheTempoMPQ = getTempoInMPQ();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void implClose() {
/*  834 */     if (this.playThread != null) {
/*      */ 
/*      */ 
/*      */       
/*  838 */       this.playThread.close();
/*  839 */       this.playThread = null;
/*      */     } 
/*      */     
/*  842 */     super.implClose();
/*      */     
/*  844 */     this.sequence = null;
/*  845 */     this.running = false;
/*  846 */     this.cacheTempoMPQ = -1.0D;
/*  847 */     this.cacheTempoFactor = -1.0F;
/*  848 */     this.trackMuted = null;
/*  849 */     this.trackSolo = null;
/*  850 */     this.loopStart = 0L;
/*  851 */     this.loopEnd = -1L;
/*  852 */     this.loopCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  857 */     this.doAutoConnectAtNextOpen = this.autoConnect;
/*      */     
/*  859 */     if (this.autoConnectedReceiver != null) {
/*      */       try {
/*  861 */         this.autoConnectedReceiver.close();
/*  862 */       } catch (Exception exception) {}
/*  863 */       this.autoConnectedReceiver = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void implStart() {
/*  872 */     if (this.playThread == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  877 */     this.tempoCache.refresh(this.sequence);
/*  878 */     if (!this.running) {
/*  879 */       this.running = true;
/*  880 */       this.playThread.start();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void implStop() {
/*  889 */     if (this.playThread == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  894 */     this.recording = false;
/*  895 */     if (this.running) {
/*  896 */       this.running = false;
/*  897 */       this.playThread.stop();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EventDispatcher getEventDispatcher() {
/*  905 */     ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/*  906 */     synchronized (dispatchers) {
/*  907 */       EventDispatcher eventDispatcher = dispatchers.get(threadGroup);
/*  908 */       if (eventDispatcher == null) {
/*  909 */         eventDispatcher = new EventDispatcher();
/*  910 */         dispatchers.put(threadGroup, eventDispatcher);
/*  911 */         eventDispatcher.start();
/*      */       } 
/*  913 */       return eventDispatcher;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void sendMetaEvents(MidiMessage paramMidiMessage) {
/*  922 */     if (this.metaEventListeners.size() == 0) {
/*      */       return;
/*      */     }
/*  925 */     getEventDispatcher().sendAudioEvents(paramMidiMessage, this.metaEventListeners);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void sendControllerEvents(MidiMessage paramMidiMessage) {
/*  932 */     int i = this.controllerEventListeners.size();
/*  933 */     if (i == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  937 */     if (!(paramMidiMessage instanceof ShortMessage)) {
/*      */       return;
/*      */     }
/*      */     
/*  941 */     ShortMessage shortMessage = (ShortMessage)paramMidiMessage;
/*  942 */     int j = shortMessage.getData1();
/*  943 */     ArrayList<ControllerEventListener> arrayList = new ArrayList();
/*  944 */     for (byte b = 0; b < i; b++) {
/*  945 */       ControllerListElement controllerListElement = this.controllerEventListeners.get(b);
/*  946 */       for (byte b1 = 0; b1 < controllerListElement.controllers.length; b1++) {
/*  947 */         if (controllerListElement.controllers[b1] == j) {
/*  948 */           arrayList.add(controllerListElement.listener);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  953 */     getEventDispatcher().sendAudioEvents(paramMidiMessage, arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needCaching() {
/*  959 */     return (!isOpen() || this.sequence == null || this.playThread == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DataPump getDataPump() {
/*  969 */     if (this.playThread != null) {
/*  970 */       return this.playThread.getDataPump();
/*      */     }
/*  972 */     return null;
/*      */   }
/*      */   
/*      */   private MidiUtils.TempoCache getTempoCache() {
/*  976 */     return this.tempoCache;
/*      */   }
/*      */   
/*      */   private static boolean[] ensureBoolArraySize(boolean[] paramArrayOfboolean, int paramInt) {
/*  980 */     if (paramArrayOfboolean == null) {
/*  981 */       return new boolean[paramInt];
/*      */     }
/*  983 */     if (paramArrayOfboolean.length < paramInt) {
/*  984 */       boolean[] arrayOfBoolean = new boolean[paramInt];
/*  985 */       System.arraycopy(paramArrayOfboolean, 0, arrayOfBoolean, 0, paramArrayOfboolean.length);
/*  986 */       return arrayOfBoolean;
/*      */     } 
/*  988 */     return paramArrayOfboolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasReceivers() {
/*  995 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Receiver createReceiver() throws MidiUnavailableException {
/* 1000 */     return new SequencerReceiver();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean hasTransmitters() {
/* 1005 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Transmitter createTransmitter() throws MidiUnavailableException {
/* 1010 */     return new SequencerTransmitter();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoConnect(Receiver paramReceiver) {
/* 1016 */     this.autoConnect = (paramReceiver != null);
/* 1017 */     this.autoConnectedReceiver = paramReceiver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SequencerTransmitter
/*      */     extends AbstractMidiDevice.BasicTransmitter
/*      */   {
/*      */     private SequencerTransmitter() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final class SequencerReceiver
/*      */     extends AbstractMidiDevice.AbstractReceiver
/*      */   {
/*      */     void implSend(MidiMessage param1MidiMessage, long param1Long) {
/* 1038 */       if (RealTimeSequencer.this.recording) {
/* 1039 */         long l = 0L;
/*      */ 
/*      */         
/* 1042 */         if (param1Long < 0L) {
/* 1043 */           l = RealTimeSequencer.this.getTickPosition();
/*      */         } else {
/* 1045 */           synchronized (RealTimeSequencer.this.tempoCache) {
/* 1046 */             l = MidiUtils.microsecond2tick(RealTimeSequencer.this.sequence, param1Long, RealTimeSequencer.this.tempoCache);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1051 */         Track track = null;
/*      */ 
/*      */         
/* 1054 */         if (param1MidiMessage.getLength() > 1) {
/* 1055 */           if (param1MidiMessage instanceof ShortMessage) {
/* 1056 */             ShortMessage shortMessage = (ShortMessage)param1MidiMessage;
/*      */             
/* 1058 */             if ((shortMessage.getStatus() & 0xF0) != 240) {
/* 1059 */               track = RealTimeSequencer.RecordingTrack.get(RealTimeSequencer.this.recordingTracks, shortMessage.getChannel());
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1064 */             track = RealTimeSequencer.RecordingTrack.get(RealTimeSequencer.this.recordingTracks, -1);
/*      */           } 
/* 1066 */           if (track != null) {
/*      */             
/* 1068 */             if (param1MidiMessage instanceof ShortMessage) {
/* 1069 */               param1MidiMessage = new FastShortMessage((ShortMessage)param1MidiMessage);
/*      */             } else {
/* 1071 */               param1MidiMessage = (MidiMessage)param1MidiMessage.clone();
/*      */             } 
/*      */ 
/*      */             
/* 1075 */             MidiEvent midiEvent = new MidiEvent(param1MidiMessage, l);
/* 1076 */             track.add(midiEvent);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RealTimeSequencerInfo
/*      */     extends MidiDevice.Info
/*      */   {
/*      */     private static final String name = "Real Time Sequencer";
/*      */     private static final String vendor = "Oracle Corporation";
/*      */     private static final String description = "Software sequencer";
/*      */     private static final String version = "Version 1.0";
/*      */     
/*      */     private RealTimeSequencerInfo() {
/* 1092 */       super("Real Time Sequencer", "Oracle Corporation", "Software sequencer", "Version 1.0");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ControllerListElement
/*      */   {
/*      */     int[] controllers;
/*      */ 
/*      */     
/*      */     final ControllerEventListener listener;
/*      */ 
/*      */     
/*      */     private ControllerListElement(ControllerEventListener param1ControllerEventListener, int[] param1ArrayOfint) {
/* 1107 */       this.listener = param1ControllerEventListener;
/* 1108 */       if (param1ArrayOfint == null) {
/* 1109 */         param1ArrayOfint = new int[128];
/* 1110 */         for (byte b = 0; b < ''; b++) {
/* 1111 */           param1ArrayOfint[b] = b;
/*      */         }
/*      */       } 
/* 1114 */       this.controllers = param1ArrayOfint;
/*      */     }
/*      */ 
/*      */     
/*      */     private void addControllers(int[] param1ArrayOfint) {
/* 1119 */       if (param1ArrayOfint == null) {
/* 1120 */         this.controllers = new int[128];
/* 1121 */         for (byte b = 0; b < ''; b++) {
/* 1122 */           this.controllers[b] = b;
/*      */         }
/*      */         return;
/*      */       } 
/* 1126 */       int[] arrayOfInt1 = new int[this.controllers.length + param1ArrayOfint.length];
/*      */       
/*      */       byte b1;
/*      */       
/* 1130 */       for (b1 = 0; b1 < this.controllers.length; b1++) {
/* 1131 */         arrayOfInt1[b1] = this.controllers[b1];
/*      */       }
/* 1133 */       int i = this.controllers.length;
/*      */       
/* 1135 */       for (b1 = 0; b1 < param1ArrayOfint.length; b1++) {
/* 1136 */         boolean bool = false;
/*      */         
/* 1138 */         for (byte b = 0; b < this.controllers.length; b++) {
/* 1139 */           if (param1ArrayOfint[b1] == this.controllers[b]) {
/* 1140 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1144 */         if (!bool) {
/* 1145 */           arrayOfInt1[i++] = param1ArrayOfint[b1];
/*      */         }
/*      */       } 
/*      */       
/* 1149 */       int[] arrayOfInt2 = new int[i];
/* 1150 */       for (byte b2 = 0; b2 < i; b2++) {
/* 1151 */         arrayOfInt2[b2] = arrayOfInt1[b2];
/*      */       }
/* 1153 */       this.controllers = arrayOfInt2;
/*      */     }
/*      */ 
/*      */     
/*      */     private void removeControllers(int[] param1ArrayOfint) {
/* 1158 */       if (param1ArrayOfint == null) {
/* 1159 */         this.controllers = new int[0];
/*      */       } else {
/* 1161 */         int[] arrayOfInt1 = new int[this.controllers.length];
/* 1162 */         byte b1 = 0;
/*      */ 
/*      */         
/* 1165 */         for (byte b2 = 0; b2 < this.controllers.length; b2++) {
/* 1166 */           boolean bool = false;
/* 1167 */           for (byte b = 0; b < param1ArrayOfint.length; b++) {
/* 1168 */             if (this.controllers[b2] == param1ArrayOfint[b]) {
/* 1169 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1173 */           if (!bool) {
/* 1174 */             arrayOfInt1[b1++] = this.controllers[b2];
/*      */           }
/*      */         } 
/*      */         
/* 1178 */         int[] arrayOfInt2 = new int[b1];
/* 1179 */         for (byte b3 = 0; b3 < b1; b3++) {
/* 1180 */           arrayOfInt2[b3] = arrayOfInt1[b3];
/*      */         }
/* 1182 */         this.controllers = arrayOfInt2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int[] getControllers() {
/* 1191 */       if (this.controllers == null) {
/* 1192 */         return null;
/*      */       }
/*      */       
/* 1195 */       int[] arrayOfInt = new int[this.controllers.length];
/*      */       
/* 1197 */       for (byte b = 0; b < this.controllers.length; b++) {
/* 1198 */         arrayOfInt[b] = this.controllers[b];
/*      */       }
/* 1200 */       return arrayOfInt;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class RecordingTrack
/*      */   {
/*      */     private final Track track;
/*      */     
/*      */     private int channel;
/*      */     
/*      */     RecordingTrack(Track param1Track, int param1Int) {
/* 1212 */       this.track = param1Track;
/* 1213 */       this.channel = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     static RecordingTrack get(List<RecordingTrack> param1List, Track param1Track) {
/* 1218 */       synchronized (param1List) {
/* 1219 */         int i = param1List.size();
/*      */         
/* 1221 */         for (byte b = 0; b < i; b++) {
/* 1222 */           RecordingTrack recordingTrack = param1List.get(b);
/* 1223 */           if (recordingTrack.track == param1Track) {
/* 1224 */             return recordingTrack;
/*      */           }
/*      */         } 
/*      */       } 
/* 1228 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     static Track get(List<RecordingTrack> param1List, int param1Int) {
/* 1233 */       synchronized (param1List) {
/* 1234 */         int i = param1List.size();
/* 1235 */         for (byte b = 0; b < i; b++) {
/* 1236 */           RecordingTrack recordingTrack = param1List.get(b);
/* 1237 */           if (recordingTrack.channel == param1Int || recordingTrack.channel == -1) {
/* 1238 */             return recordingTrack.track;
/*      */           }
/*      */         } 
/*      */       } 
/* 1242 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class PlayThread
/*      */     implements Runnable
/*      */   {
/*      */     private Thread thread;
/* 1250 */     private final Object lock = new Object();
/*      */     
/*      */     boolean interrupted = false;
/*      */     
/*      */     boolean isPumping = false;
/*      */     
/* 1256 */     private final RealTimeSequencer.DataPump dataPump = new RealTimeSequencer.DataPump();
/*      */ 
/*      */ 
/*      */     
/*      */     PlayThread() {
/* 1261 */       byte b = 8;
/*      */       
/* 1263 */       this.thread = JSSecurityManager.createThread(this, "Java Sound Sequencer", false, b, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     RealTimeSequencer.DataPump getDataPump() {
/* 1271 */       return this.dataPump;
/*      */     }
/*      */     
/*      */     synchronized void setSequence(Sequence param1Sequence) {
/* 1275 */       this.dataPump.setSequence(param1Sequence);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void start() {
/* 1282 */       RealTimeSequencer.this.running = true;
/*      */       
/* 1284 */       if (!this.dataPump.hasCachedTempo()) {
/* 1285 */         long l = RealTimeSequencer.this.getTickPosition();
/* 1286 */         this.dataPump.setTempoMPQ(RealTimeSequencer.this.tempoCache.getTempoMPQAt(l));
/*      */       } 
/* 1288 */       this.dataPump.checkPointMillis = 0L;
/* 1289 */       this.dataPump.clearNoteOnCache();
/* 1290 */       this.dataPump.needReindex = true;
/*      */       
/* 1292 */       this.dataPump.resetLoopCount();
/*      */ 
/*      */       
/* 1295 */       synchronized (this.lock) {
/* 1296 */         this.lock.notifyAll();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void stop() {
/* 1305 */       playThreadImplStop();
/* 1306 */       long l = System.nanoTime() / 1000000L;
/* 1307 */       while (this.isPumping) {
/* 1308 */         synchronized (this.lock) {
/*      */           try {
/* 1310 */             this.lock.wait(2000L);
/* 1311 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1316 */         if (System.nanoTime() / 1000000L - l > 1900L);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void playThreadImplStop() {
/* 1325 */       RealTimeSequencer.this.running = false;
/* 1326 */       synchronized (this.lock) {
/* 1327 */         this.lock.notifyAll();
/*      */       } 
/*      */     }
/*      */     
/*      */     void close() {
/* 1332 */       Thread thread = null;
/* 1333 */       synchronized (this) {
/*      */         
/* 1335 */         this.interrupted = true;
/* 1336 */         thread = this.thread;
/* 1337 */         this.thread = null;
/*      */       } 
/* 1339 */       if (thread != null)
/*      */       {
/* 1341 */         synchronized (this.lock) {
/* 1342 */           this.lock.notifyAll();
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1347 */       if (thread != null) {
/*      */         try {
/* 1349 */           thread.join(2000L);
/* 1350 */         } catch (InterruptedException interruptedException) {}
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/* 1363 */       while (!this.interrupted) {
/* 1364 */         boolean bool1 = false;
/* 1365 */         boolean bool2 = RealTimeSequencer.this.running;
/* 1366 */         this.isPumping = (!this.interrupted && RealTimeSequencer.this.running);
/* 1367 */         while (!bool1 && !this.interrupted && RealTimeSequencer.this.running) {
/* 1368 */           bool1 = this.dataPump.pump();
/*      */           
/*      */           try {
/* 1371 */             Thread.sleep(1L);
/* 1372 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1383 */         playThreadImplStop();
/* 1384 */         if (bool2) {
/* 1385 */           this.dataPump.notesOff(true);
/*      */         }
/* 1387 */         if (bool1) {
/* 1388 */           this.dataPump.setTickPos(RealTimeSequencer.this.sequence.getTickLength());
/*      */ 
/*      */           
/* 1391 */           MetaMessage metaMessage = new MetaMessage();
/*      */           try {
/* 1393 */             metaMessage.setMessage(47, new byte[0], 0);
/* 1394 */           } catch (InvalidMidiDataException invalidMidiDataException) {}
/* 1395 */           RealTimeSequencer.this.sendMetaEvents(metaMessage);
/*      */         } 
/* 1397 */         synchronized (this.lock) {
/* 1398 */           this.isPumping = false;
/*      */           
/* 1400 */           this.lock.notifyAll();
/* 1401 */           while (!RealTimeSequencer.this.running && !this.interrupted) {
/*      */             try {
/* 1403 */               this.lock.wait();
/* 1404 */             } catch (Exception exception) {}
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class DataPump
/*      */   {
/*      */     private float currTempo;
/*      */     
/*      */     private float tempoFactor;
/*      */     
/*      */     private float inverseTempoFactor;
/*      */     
/*      */     private long ignoreTempoEventAt;
/*      */     
/*      */     private int resolution;
/*      */     private float divisionType;
/*      */     private long checkPointMillis;
/*      */     private long checkPointTick;
/*      */     private int[] noteOnCache;
/*      */     private Track[] tracks;
/*      */     private boolean[] trackDisabled;
/*      */     private int[] trackReadPos;
/*      */     private long lastTick;
/*      */     private boolean needReindex = false;
/* 1432 */     private int currLoopCounter = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DataPump() {
/* 1439 */       init();
/*      */     }
/*      */     
/*      */     synchronized void init() {
/* 1443 */       this.ignoreTempoEventAt = -1L;
/* 1444 */       this.tempoFactor = 1.0F;
/* 1445 */       this.inverseTempoFactor = 1.0F;
/* 1446 */       this.noteOnCache = new int[128];
/* 1447 */       this.tracks = null;
/* 1448 */       this.trackDisabled = null;
/*      */     }
/*      */     
/*      */     synchronized void setTickPos(long param1Long) {
/* 1452 */       long l = param1Long;
/* 1453 */       this.lastTick = param1Long;
/* 1454 */       if (RealTimeSequencer.this.running) {
/* 1455 */         notesOff(false);
/*      */       }
/* 1457 */       if (RealTimeSequencer.this.running || param1Long > 0L) {
/*      */         
/* 1459 */         chaseEvents(l, param1Long);
/*      */       } else {
/* 1461 */         this.needReindex = true;
/*      */       } 
/* 1463 */       if (!hasCachedTempo()) {
/* 1464 */         setTempoMPQ(RealTimeSequencer.this.getTempoCache().getTempoMPQAt(this.lastTick, this.currTempo));
/*      */         
/* 1466 */         this.ignoreTempoEventAt = -1L;
/*      */       } 
/*      */       
/* 1469 */       this.checkPointMillis = 0L;
/*      */     }
/*      */     
/*      */     long getTickPos() {
/* 1473 */       return this.lastTick;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean hasCachedTempo() {
/* 1478 */       if (this.ignoreTempoEventAt != this.lastTick) {
/* 1479 */         this.ignoreTempoEventAt = -1L;
/*      */       }
/* 1481 */       return (this.ignoreTempoEventAt >= 0L);
/*      */     }
/*      */ 
/*      */     
/*      */     synchronized void setTempoMPQ(float param1Float) {
/* 1486 */       if (param1Float > 0.0F && param1Float != this.currTempo) {
/* 1487 */         this.ignoreTempoEventAt = this.lastTick;
/* 1488 */         this.currTempo = param1Float;
/*      */         
/* 1490 */         this.checkPointMillis = 0L;
/*      */       } 
/*      */     }
/*      */     
/*      */     float getTempoMPQ() {
/* 1495 */       return this.currTempo;
/*      */     }
/*      */     
/*      */     synchronized void setTempoFactor(float param1Float) {
/* 1499 */       if (param1Float > 0.0F && param1Float != this.tempoFactor) {
/* 1500 */         this.tempoFactor = param1Float;
/* 1501 */         this.inverseTempoFactor = 1.0F / param1Float;
/*      */         
/* 1503 */         this.checkPointMillis = 0L;
/*      */       } 
/*      */     }
/*      */     
/*      */     float getTempoFactor() {
/* 1508 */       return this.tempoFactor;
/*      */     }
/*      */     
/*      */     synchronized void muteSoloChanged() {
/* 1512 */       boolean[] arrayOfBoolean = makeDisabledArray();
/* 1513 */       if (RealTimeSequencer.this.running) {
/* 1514 */         applyDisabledTracks(this.trackDisabled, arrayOfBoolean);
/*      */       }
/* 1516 */       this.trackDisabled = arrayOfBoolean;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void setSequence(Sequence param1Sequence) {
/* 1522 */       if (param1Sequence == null) {
/* 1523 */         init();
/*      */         return;
/*      */       } 
/* 1526 */       this.tracks = param1Sequence.getTracks();
/* 1527 */       muteSoloChanged();
/* 1528 */       this.resolution = param1Sequence.getResolution();
/* 1529 */       this.divisionType = param1Sequence.getDivisionType();
/* 1530 */       this.trackReadPos = new int[this.tracks.length];
/*      */       
/* 1532 */       this.checkPointMillis = 0L;
/* 1533 */       this.needReindex = true;
/*      */     }
/*      */     
/*      */     synchronized void resetLoopCount() {
/* 1537 */       this.currLoopCounter = RealTimeSequencer.this.loopCount;
/*      */     }
/*      */     
/*      */     void clearNoteOnCache() {
/* 1541 */       for (byte b = 0; b < ''; b++) {
/* 1542 */         this.noteOnCache[b] = 0;
/*      */       }
/*      */     }
/*      */     
/*      */     void notesOff(boolean param1Boolean) {
/* 1547 */       byte b1 = 0;
/* 1548 */       for (byte b2 = 0; b2 < 16; b2++) {
/* 1549 */         int i = 1 << b2;
/* 1550 */         for (byte b = 0; b < ''; b++) {
/* 1551 */           if ((this.noteOnCache[b] & i) != 0) {
/* 1552 */             this.noteOnCache[b] = this.noteOnCache[b] ^ i;
/*      */             
/* 1554 */             RealTimeSequencer.this.getTransmitterList().sendMessage(0x90 | b2 | b << 8, -1L);
/* 1555 */             b1++;
/*      */           } 
/*      */         } 
/*      */         
/* 1559 */         RealTimeSequencer.this.getTransmitterList().sendMessage(0xB0 | b2 | 0x7B00, -1L);
/*      */         
/* 1561 */         RealTimeSequencer.this.getTransmitterList().sendMessage(0xB0 | b2 | 0x4000, -1L);
/* 1562 */         if (param1Boolean) {
/*      */           
/* 1564 */           RealTimeSequencer.this.getTransmitterList().sendMessage(0xB0 | b2 | 0x7900, -1L);
/* 1565 */           b1++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean[] makeDisabledArray() {
/*      */       boolean[] arrayOfBoolean2, arrayOfBoolean3;
/* 1573 */       if (this.tracks == null) {
/* 1574 */         return null;
/*      */       }
/* 1576 */       boolean[] arrayOfBoolean1 = new boolean[this.tracks.length];
/*      */ 
/*      */       
/* 1579 */       synchronized (RealTimeSequencer.this) {
/* 1580 */         arrayOfBoolean3 = RealTimeSequencer.this.trackMuted;
/* 1581 */         arrayOfBoolean2 = RealTimeSequencer.this.trackSolo;
/*      */       } 
/*      */       
/* 1584 */       boolean bool = false;
/* 1585 */       if (arrayOfBoolean2 != null) {
/* 1586 */         for (byte b = 0; b < arrayOfBoolean2.length; b++) {
/* 1587 */           if (arrayOfBoolean2[b]) {
/* 1588 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 1593 */       if (bool) {
/*      */         
/* 1595 */         for (byte b = 0; b < arrayOfBoolean1.length; b++) {
/* 1596 */           arrayOfBoolean1[b] = (b >= arrayOfBoolean2.length || !arrayOfBoolean2[b]);
/*      */         }
/*      */       } else {
/*      */         
/* 1600 */         for (byte b = 0; b < arrayOfBoolean1.length; b++) {
/* 1601 */           arrayOfBoolean1[b] = (arrayOfBoolean3 != null && b < arrayOfBoolean3.length && arrayOfBoolean3[b]);
/*      */         }
/*      */       } 
/* 1604 */       return arrayOfBoolean1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void sendNoteOffIfOn(Track param1Track, long param1Long) {
/* 1616 */       int i = param1Track.size();
/* 1617 */       byte b = 0;
/*      */       try {
/* 1619 */         for (byte b1 = 0; b1 < i; b1++) {
/* 1620 */           MidiEvent midiEvent = param1Track.get(b1);
/* 1621 */           if (midiEvent.getTick() > param1Long)
/* 1622 */             break;  MidiMessage midiMessage = midiEvent.getMessage();
/* 1623 */           int j = midiMessage.getStatus();
/* 1624 */           int k = midiMessage.getLength();
/* 1625 */           if (k == 3 && (j & 0xF0) == 144) {
/* 1626 */             int m = -1;
/* 1627 */             if (midiMessage instanceof ShortMessage) {
/* 1628 */               ShortMessage shortMessage = (ShortMessage)midiMessage;
/* 1629 */               if (shortMessage.getData2() > 0)
/*      */               {
/* 1631 */                 m = shortMessage.getData1();
/*      */               }
/*      */             } else {
/* 1634 */               byte[] arrayOfByte = midiMessage.getMessage();
/* 1635 */               if ((arrayOfByte[2] & Byte.MAX_VALUE) > 0)
/*      */               {
/* 1637 */                 m = arrayOfByte[1] & Byte.MAX_VALUE;
/*      */               }
/*      */             } 
/* 1640 */             if (m >= 0) {
/* 1641 */               int n = 1 << (j & 0xF);
/* 1642 */               if ((this.noteOnCache[m] & n) != 0) {
/*      */                 
/* 1644 */                 RealTimeSequencer.this.getTransmitterList().sendMessage(j | m << 8, -1L);
/*      */                 
/* 1646 */                 this.noteOnCache[m] = this.noteOnCache[m] & (0xFFFF ^ n);
/* 1647 */                 b++;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1652 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     }
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
/*      */     private void applyDisabledTracks(boolean[] param1ArrayOfboolean1, boolean[] param1ArrayOfboolean2) {
/* 1666 */       byte[][] arrayOfByte = (byte[][])null;
/* 1667 */       synchronized (RealTimeSequencer.this) {
/* 1668 */         for (byte b = 0; b < param1ArrayOfboolean2.length; b++) {
/* 1669 */           if ((param1ArrayOfboolean1 == null || b >= param1ArrayOfboolean1.length || !param1ArrayOfboolean1[b]) && param1ArrayOfboolean2[b]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1677 */             if (this.tracks.length > b) {
/* 1678 */               sendNoteOffIfOn(this.tracks[b], this.lastTick);
/*      */             }
/*      */           }
/* 1681 */           else if (param1ArrayOfboolean1 != null && b < param1ArrayOfboolean1.length && param1ArrayOfboolean1[b] && !param1ArrayOfboolean2[b]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1687 */             if (arrayOfByte == null) {
/* 1688 */               arrayOfByte = new byte[128][16];
/*      */             }
/* 1690 */             chaseTrackEvents(b, 0L, this.lastTick, true, arrayOfByte);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
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
/*      */     private void chaseTrackEvents(int param1Int, long param1Long1, long param1Long2, boolean param1Boolean, byte[][] param1ArrayOfbyte) {
/* 1708 */       if (param1Long1 > param1Long2)
/*      */       {
/* 1710 */         param1Long1 = 0L;
/*      */       }
/* 1712 */       byte[] arrayOfByte = new byte[16];
/*      */       
/* 1714 */       for (byte b1 = 0; b1 < 16; b1++) {
/* 1715 */         arrayOfByte[b1] = -1;
/* 1716 */         for (byte b = 0; b < ''; b++) {
/* 1717 */           param1ArrayOfbyte[b][b1] = -1;
/*      */         }
/*      */       } 
/* 1720 */       Track track = this.tracks[param1Int];
/* 1721 */       int i = track.size();
/*      */       try {
/* 1723 */         for (byte b = 0; b < i; b++) {
/* 1724 */           MidiEvent midiEvent = track.get(b);
/* 1725 */           if (midiEvent.getTick() >= param1Long2) {
/* 1726 */             if (param1Boolean && param1Int < this.trackReadPos.length) {
/* 1727 */               this.trackReadPos[param1Int] = (b > 0) ? (b - 1) : 0;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/* 1732 */           MidiMessage midiMessage = midiEvent.getMessage();
/* 1733 */           int j = midiMessage.getStatus();
/* 1734 */           int k = midiMessage.getLength();
/* 1735 */           if (k == 3 && (j & 0xF0) == 176) {
/* 1736 */             if (midiMessage instanceof ShortMessage) {
/* 1737 */               ShortMessage shortMessage = (ShortMessage)midiMessage;
/* 1738 */               param1ArrayOfbyte[shortMessage.getData1() & 0x7F][j & 0xF] = (byte)shortMessage.getData2();
/*      */             } else {
/* 1740 */               byte[] arrayOfByte1 = midiMessage.getMessage();
/* 1741 */               param1ArrayOfbyte[arrayOfByte1[1] & Byte.MAX_VALUE][j & 0xF] = arrayOfByte1[2];
/*      */             } 
/*      */           }
/* 1744 */           if (k == 2 && (j & 0xF0) == 192) {
/* 1745 */             if (midiMessage instanceof ShortMessage) {
/* 1746 */               ShortMessage shortMessage = (ShortMessage)midiMessage;
/* 1747 */               arrayOfByte[j & 0xF] = (byte)shortMessage.getData1();
/*      */             } else {
/* 1749 */               byte[] arrayOfByte1 = midiMessage.getMessage();
/* 1750 */               arrayOfByte[j & 0xF] = arrayOfByte1[1];
/*      */             } 
/*      */           }
/*      */         } 
/* 1754 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */ 
/*      */ 
/*      */       
/* 1758 */       byte b2 = 0;
/*      */       
/* 1760 */       for (byte b3 = 0; b3 < 16; b3++) {
/* 1761 */         for (byte b = 0; b < ''; b++) {
/* 1762 */           byte b4 = param1ArrayOfbyte[b][b3];
/* 1763 */           if (b4 >= 0) {
/* 1764 */             int j = 0xB0 | b3 | b << 8 | b4 << 16;
/* 1765 */             RealTimeSequencer.this.getTransmitterList().sendMessage(j, -1L);
/* 1766 */             b2++;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1771 */         if (arrayOfByte[b3] >= 0) {
/* 1772 */           RealTimeSequencer.this.getTransmitterList().sendMessage(0xC0 | b3 | arrayOfByte[b3] << 8, -1L);
/*      */         }
/* 1774 */         if (arrayOfByte[b3] >= 0 || param1Long1 == 0L || param1Long2 == 0L) {
/*      */           
/* 1776 */           RealTimeSequencer.this.getTransmitterList().sendMessage(0xE0 | b3 | 0x400000, -1L);
/*      */           
/* 1778 */           RealTimeSequencer.this.getTransmitterList().sendMessage(0xB0 | b3 | 0x4000, -1L);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void chaseEvents(long param1Long1, long param1Long2) {
/* 1788 */       byte[][] arrayOfByte = new byte[128][16];
/* 1789 */       for (byte b = 0; b < this.tracks.length; b++) {
/* 1790 */         if (this.trackDisabled == null || this.trackDisabled.length <= b || !this.trackDisabled[b])
/*      */         {
/*      */ 
/*      */           
/* 1794 */           chaseTrackEvents(b, param1Long1, param1Long2, true, arrayOfByte);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long getCurrentTimeMillis() {
/* 1804 */       return System.nanoTime() / 1000000L;
/*      */     }
/*      */ 
/*      */     
/*      */     private long millis2tick(long param1Long) {
/* 1809 */       if (this.divisionType != 0.0F) {
/* 1810 */         double d = param1Long * this.tempoFactor * this.divisionType * this.resolution / 1000.0D;
/*      */ 
/*      */ 
/*      */         
/* 1814 */         return (long)d;
/*      */       } 
/* 1816 */       return MidiUtils.microsec2ticks(param1Long * 1000L, (this.currTempo * this.inverseTempoFactor), this.resolution);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private long tick2millis(long param1Long) {
/* 1822 */       if (this.divisionType != 0.0F) {
/* 1823 */         double d = param1Long * 1000.0D / this.tempoFactor * this.divisionType * this.resolution;
/*      */         
/* 1825 */         return (long)d;
/*      */       } 
/* 1827 */       return MidiUtils.ticks2microsec(param1Long, (this.currTempo * this.inverseTempoFactor), this.resolution) / 1000L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void ReindexTrack(int param1Int, long param1Long) {
/* 1833 */       if (param1Int < this.trackReadPos.length && param1Int < this.tracks.length) {
/* 1834 */         this.trackReadPos[param1Int] = MidiUtils.tick2index(this.tracks[param1Int], param1Long);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean dispatchMessage(int param1Int, MidiEvent param1MidiEvent) {
/* 1841 */       boolean bool = false;
/* 1842 */       MidiMessage midiMessage = param1MidiEvent.getMessage();
/* 1843 */       int i = midiMessage.getStatus();
/* 1844 */       int j = midiMessage.getLength();
/* 1845 */       if (i == 255 && j >= 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1852 */         if (param1Int == 0) {
/* 1853 */           int k = MidiUtils.getTempoMPQ(midiMessage);
/* 1854 */           if (k > 0) {
/* 1855 */             if (param1MidiEvent.getTick() != this.ignoreTempoEventAt) {
/* 1856 */               setTempoMPQ(k);
/* 1857 */               bool = true;
/*      */             } 
/*      */             
/* 1860 */             this.ignoreTempoEventAt = -1L;
/*      */           } 
/*      */         } 
/*      */         
/* 1864 */         RealTimeSequencer.this.sendMetaEvents(midiMessage);
/*      */       } else {
/*      */         int k; ShortMessage shortMessage;
/*      */         int m, n;
/* 1868 */         RealTimeSequencer.this.getTransmitterList().sendMessage(midiMessage, -1L);
/*      */         
/* 1870 */         switch (i & 0xF0) {
/*      */           
/*      */           case 128:
/* 1873 */             k = ((ShortMessage)midiMessage).getData1() & 0x7F;
/* 1874 */             this.noteOnCache[k] = this.noteOnCache[k] & (0xFFFF ^ 1 << (i & 0xF));
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 144:
/* 1880 */             shortMessage = (ShortMessage)midiMessage;
/* 1881 */             m = shortMessage.getData1() & 0x7F;
/* 1882 */             n = shortMessage.getData2() & 0x7F;
/* 1883 */             if (n > 0) {
/*      */               
/* 1885 */               this.noteOnCache[m] = this.noteOnCache[m] | 1 << (i & 0xF);
/*      */               break;
/*      */             } 
/* 1888 */             this.noteOnCache[m] = this.noteOnCache[m] & (0xFFFF ^ 1 << (i & 0xF));
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 176:
/* 1895 */             RealTimeSequencer.this.sendControllerEvents(midiMessage);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/* 1900 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized boolean pump() {
/* 1909 */       long l2 = this.lastTick;
/*      */       
/* 1911 */       boolean bool = false;
/* 1912 */       boolean bool1 = false;
/* 1913 */       boolean bool2 = false;
/*      */       
/* 1915 */       long l1 = getCurrentTimeMillis();
/* 1916 */       byte b = 0;
/*      */       do {
/* 1918 */         bool = false;
/*      */ 
/*      */         
/* 1921 */         if (this.needReindex) {
/*      */           
/* 1923 */           if (this.trackReadPos.length < this.tracks.length) {
/* 1924 */             this.trackReadPos = new int[this.tracks.length];
/*      */           }
/* 1926 */           for (byte b2 = 0; b2 < this.tracks.length; b2++) {
/* 1927 */             ReindexTrack(b2, l2);
/*      */           }
/*      */           
/* 1930 */           this.needReindex = false;
/* 1931 */           this.checkPointMillis = 0L;
/*      */         } 
/*      */ 
/*      */         
/* 1935 */         if (this.checkPointMillis == 0L) {
/*      */           
/* 1937 */           l1 = getCurrentTimeMillis();
/* 1938 */           this.checkPointMillis = l1;
/* 1939 */           l2 = this.lastTick;
/* 1940 */           this.checkPointTick = l2;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1946 */           l2 = this.checkPointTick + millis2tick(l1 - this.checkPointMillis);
/*      */           
/* 1948 */           if (RealTimeSequencer.this.loopEnd != -1L && ((RealTimeSequencer.this
/* 1949 */             .loopCount > 0 && this.currLoopCounter > 0) || RealTimeSequencer.this
/* 1950 */             .loopCount == -1) && 
/* 1951 */             this.lastTick <= RealTimeSequencer.this.loopEnd && l2 >= RealTimeSequencer.this.loopEnd) {
/*      */ 
/*      */             
/* 1954 */             l2 = RealTimeSequencer.this.loopEnd - 1L;
/* 1955 */             bool1 = true;
/*      */           } 
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
/* 1967 */           this.lastTick = l2;
/*      */         } 
/*      */         
/* 1970 */         b = 0;
/*      */         
/* 1972 */         for (byte b1 = 0; b1 < this.tracks.length; b1++) {
/*      */           try {
/* 1974 */             boolean bool3 = this.trackDisabled[b1];
/* 1975 */             Track track = this.tracks[b1];
/* 1976 */             int i = this.trackReadPos[b1];
/* 1977 */             int j = track.size();
/*      */             MidiEvent midiEvent;
/* 1979 */             while (!bool && i < j && (
/* 1980 */               midiEvent = track.get(i)).getTick() <= l2) {
/*      */               
/* 1982 */               if (i == j - 1 && MidiUtils.isMetaEndOfTrack(midiEvent.getMessage())) {
/*      */                 
/* 1984 */                 i = j;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/* 1990 */               i++;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1995 */               if (!bool3 || (b1 == 0 && 
/* 1996 */                 MidiUtils.isMetaTempo(midiEvent.getMessage()))) {
/* 1997 */                 bool = dispatchMessage(b1, midiEvent);
/*      */               }
/*      */             } 
/* 2000 */             if (i >= j) {
/* 2001 */               b++;
/*      */             }
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
/* 2021 */             this.trackReadPos[b1] = i;
/* 2022 */           } catch (Exception exception) {
/*      */ 
/*      */             
/* 2025 */             if (exception instanceof ArrayIndexOutOfBoundsException) {
/* 2026 */               this.needReindex = true;
/* 2027 */               bool = true;
/*      */             } 
/*      */           } 
/* 2030 */           if (bool) {
/*      */             break;
/*      */           }
/*      */         } 
/* 2034 */         bool2 = (b == this.tracks.length) ? true : false;
/* 2035 */         if (!bool1 && (((RealTimeSequencer.this
/* 2036 */           .loopCount <= 0 || this.currLoopCounter <= 0) && RealTimeSequencer.this
/* 2037 */           .loopCount != -1) || bool || RealTimeSequencer.this
/*      */           
/* 2039 */           .loopEnd != -1L || !bool2)) {
/*      */           continue;
/*      */         }
/* 2042 */         long l3 = this.checkPointMillis;
/* 2043 */         long l4 = RealTimeSequencer.this.loopEnd;
/* 2044 */         if (l4 == -1L) {
/* 2045 */           l4 = this.lastTick;
/*      */         }
/*      */ 
/*      */         
/* 2049 */         if (RealTimeSequencer.this.loopCount != -1) {
/* 2050 */           this.currLoopCounter--;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2056 */         setTickPos(RealTimeSequencer.this.loopStart);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2065 */         this.checkPointMillis = l3 + tick2millis(l4 - this.checkPointTick);
/* 2066 */         this.checkPointTick = RealTimeSequencer.this.loopStart;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2071 */         this.needReindex = false;
/* 2072 */         bool = false;
/*      */         
/* 2074 */         bool1 = false;
/* 2075 */         bool2 = false;
/*      */       }
/* 2077 */       while (bool);
/*      */       
/* 2079 */       return bool2;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/RealTimeSequencer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */