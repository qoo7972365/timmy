/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MidiDevice;
/*     */ import javax.sound.midi.MidiDeviceReceiver;
/*     */ import javax.sound.midi.MidiDeviceTransmitter;
/*     */ import javax.sound.midi.MidiMessage;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Receiver;
/*     */ import javax.sound.midi.Transmitter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractMidiDevice
/*     */   implements MidiDevice, ReferenceCountingDevice
/*     */ {
/*     */   private static final boolean TRACE_TRANSMITTER = false;
/*     */   private ArrayList<Receiver> receiverList;
/*     */   private TransmitterList transmitterList;
/*  59 */   private final Object traRecLock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private final MidiDevice.Info info;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean open = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private int openRefCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private List openKeepingObjects;
/*     */ 
/*     */   
/*  78 */   protected long id = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractMidiDevice(MidiDevice.Info paramInfo) {
/*  96 */     this.info = paramInfo;
/*  97 */     this.openRefCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MidiDevice.Info getDeviceInfo() {
/* 106 */     return this.info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void open() throws MidiUnavailableException {
/* 116 */     synchronized (this) {
/* 117 */       this.openRefCount = -1;
/* 118 */       doOpen();
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
/*     */ 
/*     */   
/*     */   private void openInternal(Object paramObject) throws MidiUnavailableException {
/* 138 */     synchronized (this) {
/* 139 */       if (this.openRefCount != -1) {
/* 140 */         this.openRefCount++;
/* 141 */         getOpenKeepingObjects().add(paramObject);
/*     */       } 
/*     */       
/* 144 */       doOpen();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doOpen() throws MidiUnavailableException {
/* 152 */     synchronized (this) {
/* 153 */       if (!isOpen()) {
/* 154 */         implOpen();
/* 155 */         this.open = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() {
/* 164 */     synchronized (this) {
/* 165 */       doClose();
/* 166 */       this.openRefCount = 0;
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
/*     */ 
/*     */   
/*     */   public final void closeInternal(Object paramObject) {
/* 186 */     synchronized (this) {
/* 187 */       if (getOpenKeepingObjects().remove(paramObject) && 
/* 188 */         this.openRefCount > 0) {
/* 189 */         this.openRefCount--;
/* 190 */         if (this.openRefCount == 0) {
/* 191 */           doClose();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void doClose() {
/* 202 */     synchronized (this) {
/* 203 */       if (isOpen()) {
/* 204 */         implClose();
/* 205 */         this.open = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isOpen() {
/* 213 */     return this.open;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implClose() {
/* 218 */     synchronized (this.traRecLock) {
/* 219 */       if (this.receiverList != null) {
/*     */         
/* 221 */         for (byte b = 0; b < this.receiverList.size(); b++) {
/* 222 */           ((Receiver)this.receiverList.get(b)).close();
/*     */         }
/* 224 */         this.receiverList.clear();
/*     */       } 
/* 226 */       if (this.transmitterList != null)
/*     */       {
/* 228 */         this.transmitterList.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMicrosecondPosition() {
/* 240 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMaxReceivers() {
/* 249 */     if (hasReceivers()) {
/* 250 */       return -1;
/*     */     }
/* 252 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMaxTransmitters() {
/* 262 */     if (hasTransmitters()) {
/* 263 */       return -1;
/*     */     }
/* 265 */     return 0;
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
/*     */   public final Receiver getReceiver() throws MidiUnavailableException {
/*     */     Receiver receiver;
/* 279 */     synchronized (this.traRecLock) {
/* 280 */       receiver = createReceiver();
/* 281 */       getReceiverList().add(receiver);
/*     */     } 
/* 283 */     return receiver;
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<Receiver> getReceivers() {
/*     */     List<?> list;
/* 289 */     synchronized (this.traRecLock) {
/* 290 */       if (this.receiverList == null) {
/* 291 */         list = Collections.unmodifiableList(new ArrayList(0));
/*     */       } else {
/*     */         
/* 294 */         list = Collections.unmodifiableList((List)this.receiverList.clone());
/*     */       } 
/*     */     } 
/* 297 */     return (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Transmitter getTransmitter() throws MidiUnavailableException {
/*     */     Transmitter transmitter;
/* 308 */     synchronized (this.traRecLock) {
/* 309 */       transmitter = createTransmitter();
/* 310 */       getTransmitterList().add(transmitter);
/*     */     } 
/* 312 */     return transmitter;
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<Transmitter> getTransmitters() {
/*     */     List<?> list;
/* 318 */     synchronized (this.traRecLock) {
/* 319 */       if (this.transmitterList == null || this.transmitterList
/* 320 */         .transmitters.size() == 0) {
/* 321 */         list = Collections.unmodifiableList(new ArrayList(0));
/*     */       } else {
/* 323 */         list = Collections.unmodifiableList((List)this.transmitterList.transmitters.clone());
/*     */       } 
/*     */     } 
/* 326 */     return (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long getId() {
/* 333 */     return this.id;
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
/*     */   public final Receiver getReceiverReferenceCounting() throws MidiUnavailableException {
/*     */     Receiver receiver;
/* 348 */     synchronized (this.traRecLock) {
/* 349 */       receiver = getReceiver();
/* 350 */       openInternal(receiver);
/*     */     } 
/* 352 */     return receiver;
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
/*     */   public final Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException {
/*     */     Transmitter transmitter;
/* 365 */     synchronized (this.traRecLock) {
/* 366 */       transmitter = getTransmitter();
/* 367 */       openInternal(transmitter);
/*     */     } 
/* 369 */     return transmitter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized List getOpenKeepingObjects() {
/* 376 */     if (this.openKeepingObjects == null) {
/* 377 */       this.openKeepingObjects = new ArrayList();
/*     */     }
/* 379 */     return this.openKeepingObjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Receiver> getReceiverList() {
/* 390 */     synchronized (this.traRecLock) {
/* 391 */       if (this.receiverList == null) {
/* 392 */         this.receiverList = new ArrayList<>();
/*     */       }
/*     */     } 
/* 395 */     return this.receiverList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasReceivers() {
/* 406 */     return false;
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
/*     */   protected Receiver createReceiver() throws MidiUnavailableException {
/* 418 */     throw new MidiUnavailableException("MIDI IN receiver not available");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final TransmitterList getTransmitterList() {
/* 428 */     synchronized (this.traRecLock) {
/* 429 */       if (this.transmitterList == null) {
/* 430 */         this.transmitterList = new TransmitterList();
/*     */       }
/*     */     } 
/* 433 */     return this.transmitterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasTransmitters() {
/* 444 */     return false;
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
/*     */   protected Transmitter createTransmitter() throws MidiUnavailableException {
/* 456 */     throw new MidiUnavailableException("MIDI OUT transmitter not available");
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
/*     */   protected final void finalize() {
/* 468 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void implOpen() throws MidiUnavailableException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract class AbstractReceiver
/*     */     implements MidiDeviceReceiver
/*     */   {
/*     */     private boolean open = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final synchronized void send(MidiMessage param1MidiMessage, long param1Long) {
/* 491 */       if (!this.open) {
/* 492 */         throw new IllegalStateException("Receiver is not open");
/*     */       }
/* 494 */       implSend(param1MidiMessage, param1Long);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void implSend(MidiMessage param1MidiMessage, long param1Long);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void close() {
/* 506 */       this.open = false;
/* 507 */       synchronized (AbstractMidiDevice.this.traRecLock) {
/* 508 */         AbstractMidiDevice.this.getReceiverList().remove(this);
/*     */       } 
/* 510 */       AbstractMidiDevice.this.closeInternal(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public final MidiDevice getMidiDevice() {
/* 515 */       return AbstractMidiDevice.this;
/*     */     }
/*     */     
/*     */     final boolean isOpen() {
/* 519 */       return this.open;
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
/*     */ 
/*     */   
/*     */   class BasicTransmitter
/*     */     implements MidiDeviceTransmitter
/*     */   {
/* 541 */     private Receiver receiver = null;
/* 542 */     AbstractMidiDevice.TransmitterList tlist = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setTransmitterList(AbstractMidiDevice.TransmitterList param1TransmitterList) {
/* 548 */       this.tlist = param1TransmitterList;
/*     */     }
/*     */     
/*     */     public final void setReceiver(Receiver param1Receiver) {
/* 552 */       if (this.tlist != null && this.receiver != param1Receiver) {
/*     */         
/* 554 */         this.tlist.receiverChanged(this, this.receiver, param1Receiver);
/* 555 */         this.receiver = param1Receiver;
/*     */       } 
/*     */     }
/*     */     
/*     */     public final Receiver getReceiver() {
/* 560 */       return this.receiver;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void close() {
/* 570 */       AbstractMidiDevice.this.closeInternal(this);
/* 571 */       if (this.tlist != null) {
/* 572 */         this.tlist.receiverChanged(this, this.receiver, null);
/* 573 */         this.tlist.remove(this);
/* 574 */         this.tlist = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public final MidiDevice getMidiDevice() {
/* 579 */       return AbstractMidiDevice.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class TransmitterList
/*     */   {
/* 590 */     private final ArrayList<Transmitter> transmitters = new ArrayList<>();
/*     */ 
/*     */     
/*     */     private MidiOutDevice.MidiOutReceiver midiOutReceiver;
/*     */     
/* 595 */     private int optimizedReceiverCount = 0;
/*     */ 
/*     */     
/*     */     private void add(Transmitter param1Transmitter) {
/* 599 */       synchronized (this.transmitters) {
/* 600 */         this.transmitters.add(param1Transmitter);
/*     */       } 
/* 602 */       if (param1Transmitter instanceof AbstractMidiDevice.BasicTransmitter) {
/* 603 */         ((AbstractMidiDevice.BasicTransmitter)param1Transmitter).setTransmitterList(this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void remove(Transmitter param1Transmitter) {
/* 609 */       synchronized (this.transmitters) {
/* 610 */         int i = this.transmitters.indexOf(param1Transmitter);
/* 611 */         if (i >= 0) {
/* 612 */           this.transmitters.remove(i);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void receiverChanged(AbstractMidiDevice.BasicTransmitter param1BasicTransmitter, Receiver param1Receiver1, Receiver param1Receiver2) {
/* 621 */       synchronized (this.transmitters) {
/*     */         
/* 623 */         if (this.midiOutReceiver == param1Receiver1) {
/* 624 */           this.midiOutReceiver = null;
/*     */         }
/* 626 */         if (param1Receiver2 != null && 
/* 627 */           param1Receiver2 instanceof MidiOutDevice.MidiOutReceiver && this.midiOutReceiver == null)
/*     */         {
/* 629 */           this.midiOutReceiver = (MidiOutDevice.MidiOutReceiver)param1Receiver2;
/*     */         }
/*     */         
/* 632 */         this.optimizedReceiverCount = (this.midiOutReceiver != null) ? 1 : 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void close() {
/* 641 */       synchronized (this.transmitters) {
/* 642 */         for (byte b = 0; b < this.transmitters.size(); b++) {
/* 643 */           ((Transmitter)this.transmitters.get(b)).close();
/*     */         }
/* 645 */         this.transmitters.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void sendMessage(int param1Int, long param1Long) {
/*     */       try {
/* 660 */         synchronized (this.transmitters) {
/* 661 */           int i = this.transmitters.size();
/* 662 */           if (this.optimizedReceiverCount == i) {
/* 663 */             if (this.midiOutReceiver != null)
/*     */             {
/* 665 */               this.midiOutReceiver.sendPackedMidiMessage(param1Int, param1Long);
/*     */             }
/*     */           } else {
/*     */             
/* 669 */             for (byte b = 0; b < i; b++) {
/* 670 */               Receiver receiver = ((Transmitter)this.transmitters.get(b)).getReceiver();
/* 671 */               if (receiver != null) {
/* 672 */                 if (this.optimizedReceiverCount > 0) {
/* 673 */                   if (receiver instanceof MidiOutDevice.MidiOutReceiver) {
/* 674 */                     ((MidiOutDevice.MidiOutReceiver)receiver).sendPackedMidiMessage(param1Int, param1Long);
/*     */                   } else {
/* 676 */                     receiver.send(new FastShortMessage(param1Int), param1Long);
/*     */                   } 
/*     */                 } else {
/* 679 */                   receiver.send(new FastShortMessage(param1Int), param1Long);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 685 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void sendMessage(byte[] param1ArrayOfbyte, long param1Long) {
/*     */       try {
/* 692 */         synchronized (this.transmitters) {
/* 693 */           int i = this.transmitters.size();
/*     */           
/* 695 */           for (byte b = 0; b < i; b++) {
/* 696 */             Receiver receiver = ((Transmitter)this.transmitters.get(b)).getReceiver();
/* 697 */             if (receiver != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 703 */               receiver.send(new FastSysexMessage(param1ArrayOfbyte), param1Long);
/*     */             }
/*     */           } 
/*     */         } 
/* 707 */       } catch (InvalidMidiDataException invalidMidiDataException) {
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void sendMessage(MidiMessage param1MidiMessage, long param1Long) {
/* 718 */       if (param1MidiMessage instanceof FastShortMessage) {
/* 719 */         sendMessage(((FastShortMessage)param1MidiMessage).getPackedMsg(), param1Long);
/*     */         return;
/*     */       } 
/* 722 */       synchronized (this.transmitters) {
/* 723 */         int i = this.transmitters.size();
/* 724 */         if (this.optimizedReceiverCount == i) {
/* 725 */           if (this.midiOutReceiver != null)
/*     */           {
/* 727 */             this.midiOutReceiver.send(param1MidiMessage, param1Long);
/*     */           }
/*     */         } else {
/*     */           
/* 731 */           for (byte b = 0; b < i; b++) {
/* 732 */             Receiver receiver = ((Transmitter)this.transmitters.get(b)).getReceiver();
/* 733 */             if (receiver != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 741 */               receiver.send(param1MidiMessage, param1Long);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AbstractMidiDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */