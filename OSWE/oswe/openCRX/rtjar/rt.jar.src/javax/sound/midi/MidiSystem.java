/*      */ package javax.sound.midi;
/*      */ 
/*      */ import com.sun.media.sound.AutoConnectSequencer;
/*      */ import com.sun.media.sound.JDK13Services;
/*      */ import com.sun.media.sound.MidiDeviceReceiverEnvelope;
/*      */ import com.sun.media.sound.MidiDeviceTransmitterEnvelope;
/*      */ import com.sun.media.sound.ReferenceCountingDevice;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.sound.midi.spi.MidiDeviceProvider;
/*      */ import javax.sound.midi.spi.MidiFileReader;
/*      */ import javax.sound.midi.spi.MidiFileWriter;
/*      */ import javax.sound.midi.spi.SoundbankReader;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MidiSystem
/*      */ {
/*      */   public static MidiDevice.Info[] getMidiDeviceInfo() {
/*  190 */     ArrayList<MidiDevice.Info> arrayList = new ArrayList();
/*  191 */     List<MidiDeviceProvider> list = getMidiDeviceProviders();
/*      */     
/*  193 */     for (byte b = 0; b < list.size(); b++) {
/*  194 */       MidiDeviceProvider midiDeviceProvider = list.get(b);
/*  195 */       MidiDevice.Info[] arrayOfInfo = midiDeviceProvider.getDeviceInfo();
/*  196 */       for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*  197 */         arrayList.add(arrayOfInfo[b1]);
/*      */       }
/*      */     } 
/*  200 */     return arrayList.<MidiDevice.Info>toArray(new MidiDevice.Info[0]);
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
/*      */   public static MidiDevice getMidiDevice(MidiDevice.Info paramInfo) throws MidiUnavailableException {
/*  217 */     List<MidiDeviceProvider> list = getMidiDeviceProviders();
/*      */     
/*  219 */     for (byte b = 0; b < list.size(); b++) {
/*  220 */       MidiDeviceProvider midiDeviceProvider = list.get(b);
/*  221 */       if (midiDeviceProvider.isDeviceSupported(paramInfo)) {
/*  222 */         return midiDeviceProvider.getDevice(paramInfo);
/*      */       }
/*      */     } 
/*      */     
/*  226 */     throw new IllegalArgumentException("Requested device not installed: " + paramInfo);
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
/*      */   public static Receiver getReceiver() throws MidiUnavailableException {
/*      */     Receiver receiver;
/*  270 */     MidiDevice midiDevice = getDefaultDeviceWrapper(Receiver.class);
/*      */     
/*  272 */     if (midiDevice instanceof ReferenceCountingDevice) {
/*  273 */       receiver = ((ReferenceCountingDevice)midiDevice).getReceiverReferenceCounting();
/*      */     } else {
/*  275 */       receiver = midiDevice.getReceiver();
/*      */     } 
/*  277 */     if (!(receiver instanceof MidiDeviceReceiver)) {
/*  278 */       receiver = new MidiDeviceReceiverEnvelope(midiDevice, receiver);
/*      */     }
/*  280 */     return receiver;
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
/*      */   public static Transmitter getTransmitter() throws MidiUnavailableException {
/*      */     Transmitter transmitter;
/*  321 */     MidiDevice midiDevice = getDefaultDeviceWrapper(Transmitter.class);
/*      */     
/*  323 */     if (midiDevice instanceof ReferenceCountingDevice) {
/*  324 */       transmitter = ((ReferenceCountingDevice)midiDevice).getTransmitterReferenceCounting();
/*      */     } else {
/*  326 */       transmitter = midiDevice.getTransmitter();
/*      */     } 
/*  328 */     if (!(transmitter instanceof MidiDeviceTransmitter)) {
/*  329 */       transmitter = new MidiDeviceTransmitterEnvelope(midiDevice, transmitter);
/*      */     }
/*  331 */     return transmitter;
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
/*      */   public static Synthesizer getSynthesizer() throws MidiUnavailableException {
/*  351 */     return (Synthesizer)getDefaultDeviceWrapper(Synthesizer.class);
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
/*      */   public static Sequencer getSequencer() throws MidiUnavailableException {
/*  392 */     return getSequencer(true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Sequencer getSequencer(boolean paramBoolean) throws MidiUnavailableException {
/*  444 */     Sequencer sequencer = (Sequencer)getDefaultDeviceWrapper(Sequencer.class);
/*      */     
/*  446 */     if (paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  453 */       Receiver receiver = null;
/*  454 */       MidiUnavailableException midiUnavailableException = null;
/*      */ 
/*      */       
/*      */       try {
/*  458 */         Synthesizer synthesizer = getSynthesizer();
/*  459 */         if (synthesizer instanceof ReferenceCountingDevice) {
/*  460 */           receiver = ((ReferenceCountingDevice)synthesizer).getReceiverReferenceCounting();
/*      */         } else {
/*  462 */           synthesizer.open();
/*      */           try {
/*  464 */             receiver = synthesizer.getReceiver();
/*      */           } finally {
/*      */             
/*  467 */             if (receiver == null) {
/*  468 */               synthesizer.close();
/*      */             }
/*      */           } 
/*      */         } 
/*  472 */       } catch (MidiUnavailableException midiUnavailableException1) {
/*      */         
/*  474 */         if (midiUnavailableException1 instanceof MidiUnavailableException) {
/*  475 */           midiUnavailableException = midiUnavailableException1;
/*      */         }
/*      */       } 
/*  478 */       if (receiver == null) {
/*      */         
/*      */         try {
/*  481 */           receiver = getReceiver();
/*  482 */         } catch (Exception exception) {
/*      */           
/*  484 */           if (exception instanceof MidiUnavailableException) {
/*  485 */             midiUnavailableException = (MidiUnavailableException)exception;
/*      */           }
/*      */         } 
/*      */       }
/*  489 */       if (receiver != null) {
/*  490 */         sequencer.getTransmitter().setReceiver(receiver);
/*  491 */         if (sequencer instanceof AutoConnectSequencer) {
/*  492 */           ((AutoConnectSequencer)sequencer).setAutoConnect(receiver);
/*      */         }
/*      */       } else {
/*  495 */         if (midiUnavailableException != null) {
/*  496 */           throw midiUnavailableException;
/*      */         }
/*  498 */         throw new MidiUnavailableException("no receiver available");
/*      */       } 
/*      */     } 
/*  501 */     return sequencer;
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
/*      */   public static Soundbank getSoundbank(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*  528 */     SoundbankReader soundbankReader = null;
/*  529 */     Soundbank soundbank = null;
/*      */     
/*  531 */     List<SoundbankReader> list = getSoundbankReaders();
/*      */     
/*  533 */     for (byte b = 0; b < list.size(); b++) {
/*  534 */       soundbankReader = list.get(b);
/*  535 */       soundbank = soundbankReader.getSoundbank(paramInputStream);
/*      */       
/*  537 */       if (soundbank != null) {
/*  538 */         return soundbank;
/*      */       }
/*      */     } 
/*  541 */     throw new InvalidMidiDataException("cannot get soundbank from stream");
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
/*      */   public static Soundbank getSoundbank(URL paramURL) throws InvalidMidiDataException, IOException {
/*  559 */     SoundbankReader soundbankReader = null;
/*  560 */     Soundbank soundbank = null;
/*      */     
/*  562 */     List<SoundbankReader> list = getSoundbankReaders();
/*      */     
/*  564 */     for (byte b = 0; b < list.size(); b++) {
/*  565 */       soundbankReader = list.get(b);
/*  566 */       soundbank = soundbankReader.getSoundbank(paramURL);
/*      */       
/*  568 */       if (soundbank != null) {
/*  569 */         return soundbank;
/*      */       }
/*      */     } 
/*  572 */     throw new InvalidMidiDataException("cannot get soundbank from stream");
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
/*      */   public static Soundbank getSoundbank(File paramFile) throws InvalidMidiDataException, IOException {
/*  591 */     SoundbankReader soundbankReader = null;
/*  592 */     Soundbank soundbank = null;
/*      */     
/*  594 */     List<SoundbankReader> list = getSoundbankReaders();
/*      */     
/*  596 */     for (byte b = 0; b < list.size(); b++) {
/*  597 */       soundbankReader = list.get(b);
/*  598 */       soundbank = soundbankReader.getSoundbank(paramFile);
/*      */       
/*  600 */       if (soundbank != null) {
/*  601 */         return soundbank;
/*      */       }
/*      */     } 
/*  604 */     throw new InvalidMidiDataException("cannot get soundbank from stream");
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
/*      */   public static MidiFileFormat getMidiFileFormat(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*  644 */     List<MidiFileReader> list = getMidiFileReaders();
/*  645 */     MidiFileFormat midiFileFormat = null;
/*      */     
/*  647 */     for (byte b = 0; b < list.size(); b++) {
/*  648 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  650 */         midiFileFormat = midiFileReader.getMidiFileFormat(paramInputStream);
/*      */         break;
/*  652 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  657 */     if (midiFileFormat == null) {
/*  658 */       throw new InvalidMidiDataException("input stream is not a supported file type");
/*      */     }
/*  660 */     return midiFileFormat;
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
/*      */   public static MidiFileFormat getMidiFileFormat(URL paramURL) throws InvalidMidiDataException, IOException {
/*  690 */     List<MidiFileReader> list = getMidiFileReaders();
/*  691 */     MidiFileFormat midiFileFormat = null;
/*      */     
/*  693 */     for (byte b = 0; b < list.size(); b++) {
/*  694 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  696 */         midiFileFormat = midiFileReader.getMidiFileFormat(paramURL);
/*      */         break;
/*  698 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  703 */     if (midiFileFormat == null) {
/*  704 */       throw new InvalidMidiDataException("url is not a supported file type");
/*      */     }
/*  706 */     return midiFileFormat;
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
/*      */   public static MidiFileFormat getMidiFileFormat(File paramFile) throws InvalidMidiDataException, IOException {
/*  736 */     List<MidiFileReader> list = getMidiFileReaders();
/*  737 */     MidiFileFormat midiFileFormat = null;
/*      */     
/*  739 */     for (byte b = 0; b < list.size(); b++) {
/*  740 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  742 */         midiFileFormat = midiFileReader.getMidiFileFormat(paramFile);
/*      */         break;
/*  744 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  749 */     if (midiFileFormat == null) {
/*  750 */       throw new InvalidMidiDataException("file is not a supported file type");
/*      */     }
/*  752 */     return midiFileFormat;
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
/*      */   public static Sequence getSequence(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*  791 */     List<MidiFileReader> list = getMidiFileReaders();
/*  792 */     Sequence sequence = null;
/*      */     
/*  794 */     for (byte b = 0; b < list.size(); b++) {
/*  795 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  797 */         sequence = midiFileReader.getSequence(paramInputStream);
/*      */         break;
/*  799 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  804 */     if (sequence == null) {
/*  805 */       throw new InvalidMidiDataException("could not get sequence from input stream");
/*      */     }
/*  807 */     return sequence;
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
/*      */   public static Sequence getSequence(URL paramURL) throws InvalidMidiDataException, IOException {
/*  835 */     List<MidiFileReader> list = getMidiFileReaders();
/*  836 */     Sequence sequence = null;
/*      */     
/*  838 */     for (byte b = 0; b < list.size(); b++) {
/*  839 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  841 */         sequence = midiFileReader.getSequence(paramURL);
/*      */         break;
/*  843 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  848 */     if (sequence == null) {
/*  849 */       throw new InvalidMidiDataException("could not get sequence from URL");
/*      */     }
/*  851 */     return sequence;
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
/*      */   public static Sequence getSequence(File paramFile) throws InvalidMidiDataException, IOException {
/*  879 */     List<MidiFileReader> list = getMidiFileReaders();
/*  880 */     Sequence sequence = null;
/*      */     
/*  882 */     for (byte b = 0; b < list.size(); b++) {
/*  883 */       MidiFileReader midiFileReader = list.get(b);
/*      */       try {
/*  885 */         sequence = midiFileReader.getSequence(paramFile);
/*      */         break;
/*  887 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  892 */     if (sequence == null) {
/*  893 */       throw new InvalidMidiDataException("could not get sequence from file");
/*      */     }
/*  895 */     return sequence;
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
/*      */   public static int[] getMidiFileTypes() {
/*  908 */     List<MidiFileWriter> list = getMidiFileWriters();
/*  909 */     HashSet<Integer> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */     
/*  913 */     for (byte b1 = 0; b1 < list.size(); b1++) {
/*  914 */       MidiFileWriter midiFileWriter = list.get(b1);
/*  915 */       int[] arrayOfInt1 = midiFileWriter.getMidiFileTypes();
/*  916 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/*  917 */         hashSet.add(new Integer(arrayOfInt1[b]));
/*      */       }
/*      */     } 
/*  920 */     int[] arrayOfInt = new int[hashSet.size()];
/*  921 */     byte b2 = 0;
/*  922 */     Iterator<Integer> iterator = hashSet.iterator();
/*  923 */     while (iterator.hasNext()) {
/*  924 */       Integer integer = iterator.next();
/*  925 */       arrayOfInt[b2++] = integer.intValue();
/*      */     } 
/*  927 */     return arrayOfInt;
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
/*      */   public static boolean isFileTypeSupported(int paramInt) {
/*  940 */     List<MidiFileWriter> list = getMidiFileWriters();
/*      */     
/*  942 */     for (byte b = 0; b < list.size(); b++) {
/*  943 */       MidiFileWriter midiFileWriter = list.get(b);
/*  944 */       if (midiFileWriter.isFileTypeSupported(paramInt)) {
/*  945 */         return true;
/*      */       }
/*      */     } 
/*  948 */     return false;
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
/*      */   public static int[] getMidiFileTypes(Sequence paramSequence) {
/*  962 */     List<MidiFileWriter> list = getMidiFileWriters();
/*  963 */     HashSet<Integer> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */     
/*  967 */     for (byte b1 = 0; b1 < list.size(); b1++) {
/*  968 */       MidiFileWriter midiFileWriter = list.get(b1);
/*  969 */       int[] arrayOfInt1 = midiFileWriter.getMidiFileTypes(paramSequence);
/*  970 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/*  971 */         hashSet.add(new Integer(arrayOfInt1[b]));
/*      */       }
/*      */     } 
/*  974 */     int[] arrayOfInt = new int[hashSet.size()];
/*  975 */     byte b2 = 0;
/*  976 */     Iterator<Integer> iterator = hashSet.iterator();
/*  977 */     while (iterator.hasNext()) {
/*  978 */       Integer integer = iterator.next();
/*  979 */       arrayOfInt[b2++] = integer.intValue();
/*      */     } 
/*  981 */     return arrayOfInt;
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
/*      */   public static boolean isFileTypeSupported(int paramInt, Sequence paramSequence) {
/*  996 */     List<MidiFileWriter> list = getMidiFileWriters();
/*      */     
/*  998 */     for (byte b = 0; b < list.size(); b++) {
/*  999 */       MidiFileWriter midiFileWriter = list.get(b);
/* 1000 */       if (midiFileWriter.isFileTypeSupported(paramInt, paramSequence)) {
/* 1001 */         return true;
/*      */       }
/*      */     } 
/* 1004 */     return false;
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
/*      */   public static int write(Sequence paramSequence, int paramInt, OutputStream paramOutputStream) throws IOException {
/* 1023 */     List<MidiFileWriter> list = getMidiFileWriters();
/*      */     
/* 1025 */     int i = -2;
/*      */     
/* 1027 */     for (byte b = 0; b < list.size(); b++) {
/* 1028 */       MidiFileWriter midiFileWriter = list.get(b);
/* 1029 */       if (midiFileWriter.isFileTypeSupported(paramInt, paramSequence)) {
/*      */         
/* 1031 */         i = midiFileWriter.write(paramSequence, paramInt, paramOutputStream);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1035 */     if (i == -2) {
/* 1036 */       throw new IllegalArgumentException("MIDI file type is not supported");
/*      */     }
/* 1038 */     return i;
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
/*      */   public static int write(Sequence paramSequence, int paramInt, File paramFile) throws IOException {
/* 1057 */     List<MidiFileWriter> list = getMidiFileWriters();
/*      */     
/* 1059 */     int i = -2;
/*      */     
/* 1061 */     for (byte b = 0; b < list.size(); b++) {
/* 1062 */       MidiFileWriter midiFileWriter = list.get(b);
/* 1063 */       if (midiFileWriter.isFileTypeSupported(paramInt, paramSequence)) {
/*      */         
/* 1065 */         i = midiFileWriter.write(paramSequence, paramInt, paramFile);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1069 */     if (i == -2) {
/* 1070 */       throw new IllegalArgumentException("MIDI file type is not supported");
/*      */     }
/* 1072 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getMidiDeviceProviders() {
/* 1080 */     return getProviders(MidiDeviceProvider.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List getSoundbankReaders() {
/* 1085 */     return getProviders(SoundbankReader.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List getMidiFileWriters() {
/* 1090 */     return getProviders(MidiFileWriter.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List getMidiFileReaders() {
/* 1095 */     return getProviders(MidiFileReader.class);
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
/*      */   private static MidiDevice getDefaultDeviceWrapper(Class paramClass) throws MidiUnavailableException {
/*      */     try {
/* 1115 */       return getDefaultDevice(paramClass);
/* 1116 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 1117 */       MidiUnavailableException midiUnavailableException = new MidiUnavailableException();
/* 1118 */       midiUnavailableException.initCause(illegalArgumentException);
/* 1119 */       throw midiUnavailableException;
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
/*      */   private static MidiDevice getDefaultDevice(Class<?> paramClass) {
/* 1132 */     List list = getMidiDeviceProviders();
/* 1133 */     String str1 = JDK13Services.getDefaultProviderClassName(paramClass);
/* 1134 */     String str2 = JDK13Services.getDefaultInstanceName(paramClass);
/*      */ 
/*      */     
/* 1137 */     if (str1 != null) {
/* 1138 */       MidiDeviceProvider midiDeviceProvider = getNamedProvider(str1, list);
/* 1139 */       if (midiDeviceProvider != null) {
/* 1140 */         if (str2 != null) {
/* 1141 */           MidiDevice midiDevice2 = getNamedDevice(str2, midiDeviceProvider, paramClass);
/* 1142 */           if (midiDevice2 != null) {
/* 1143 */             return midiDevice2;
/*      */           }
/*      */         } 
/* 1146 */         MidiDevice midiDevice1 = getFirstDevice(midiDeviceProvider, paramClass);
/* 1147 */         if (midiDevice1 != null) {
/* 1148 */           return midiDevice1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1156 */     if (str2 != null) {
/* 1157 */       MidiDevice midiDevice1 = getNamedDevice(str2, list, paramClass);
/* 1158 */       if (midiDevice1 != null) {
/* 1159 */         return midiDevice1;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     MidiDevice midiDevice = getFirstDevice(list, paramClass);
/* 1166 */     if (midiDevice != null) {
/* 1167 */       return midiDevice;
/*      */     }
/* 1169 */     throw new IllegalArgumentException("Requested device not installed");
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
/*      */   private static MidiDeviceProvider getNamedProvider(String paramString, List<MidiDeviceProvider> paramList) {
/* 1183 */     for (byte b = 0; b < paramList.size(); b++) {
/* 1184 */       MidiDeviceProvider midiDeviceProvider = paramList.get(b);
/* 1185 */       if (midiDeviceProvider.getClass().getName().equals(paramString)) {
/* 1186 */         return midiDeviceProvider;
/*      */       }
/*      */     } 
/* 1189 */     return null;
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
/*      */   private static MidiDevice getNamedDevice(String paramString, MidiDeviceProvider paramMidiDeviceProvider, Class<Receiver> paramClass) {
/* 1206 */     MidiDevice midiDevice = getNamedDevice(paramString, paramMidiDeviceProvider, paramClass, false, false);
/*      */     
/* 1208 */     if (midiDevice != null) {
/* 1209 */       return midiDevice;
/*      */     }
/*      */     
/* 1212 */     if (paramClass == Receiver.class) {
/*      */       
/* 1214 */       midiDevice = getNamedDevice(paramString, paramMidiDeviceProvider, paramClass, true, false);
/*      */       
/* 1216 */       if (midiDevice != null) {
/* 1217 */         return midiDevice;
/*      */       }
/*      */     } 
/*      */     
/* 1221 */     return null;
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
/*      */   private static MidiDevice getNamedDevice(String paramString, MidiDeviceProvider paramMidiDeviceProvider, Class paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 1238 */     MidiDevice.Info[] arrayOfInfo = paramMidiDeviceProvider.getDeviceInfo();
/* 1239 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 1240 */       if (arrayOfInfo[b].getName().equals(paramString)) {
/* 1241 */         MidiDevice midiDevice = paramMidiDeviceProvider.getDevice(arrayOfInfo[b]);
/* 1242 */         if (isAppropriateDevice(midiDevice, paramClass, paramBoolean1, paramBoolean2))
/*      */         {
/* 1244 */           return midiDevice;
/*      */         }
/*      */       } 
/*      */     } 
/* 1248 */     return null;
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
/*      */   private static MidiDevice getNamedDevice(String paramString, List paramList, Class<Receiver> paramClass) {
/* 1266 */     MidiDevice midiDevice = getNamedDevice(paramString, paramList, paramClass, false, false);
/*      */     
/* 1268 */     if (midiDevice != null) {
/* 1269 */       return midiDevice;
/*      */     }
/*      */     
/* 1272 */     if (paramClass == Receiver.class) {
/*      */       
/* 1274 */       midiDevice = getNamedDevice(paramString, paramList, paramClass, true, false);
/*      */       
/* 1276 */       if (midiDevice != null) {
/* 1277 */         return midiDevice;
/*      */       }
/*      */     } 
/*      */     
/* 1281 */     return null;
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
/*      */   private static MidiDevice getNamedDevice(String paramString, List<MidiDeviceProvider> paramList, Class paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 1299 */     for (byte b = 0; b < paramList.size(); b++) {
/* 1300 */       MidiDeviceProvider midiDeviceProvider = paramList.get(b);
/* 1301 */       MidiDevice midiDevice = getNamedDevice(paramString, midiDeviceProvider, paramClass, paramBoolean1, paramBoolean2);
/*      */ 
/*      */ 
/*      */       
/* 1305 */       if (midiDevice != null) {
/* 1306 */         return midiDevice;
/*      */       }
/*      */     } 
/* 1309 */     return null;
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
/*      */   private static MidiDevice getFirstDevice(MidiDeviceProvider paramMidiDeviceProvider, Class<Receiver> paramClass) {
/* 1324 */     MidiDevice midiDevice = getFirstDevice(paramMidiDeviceProvider, paramClass, false, false);
/*      */     
/* 1326 */     if (midiDevice != null) {
/* 1327 */       return midiDevice;
/*      */     }
/*      */     
/* 1330 */     if (paramClass == Receiver.class) {
/*      */       
/* 1332 */       midiDevice = getFirstDevice(paramMidiDeviceProvider, paramClass, true, false);
/*      */       
/* 1334 */       if (midiDevice != null) {
/* 1335 */         return midiDevice;
/*      */       }
/*      */     } 
/*      */     
/* 1339 */     return null;
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
/*      */   private static MidiDevice getFirstDevice(MidiDeviceProvider paramMidiDeviceProvider, Class paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 1354 */     MidiDevice.Info[] arrayOfInfo = paramMidiDeviceProvider.getDeviceInfo();
/* 1355 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 1356 */       MidiDevice midiDevice = paramMidiDeviceProvider.getDevice(arrayOfInfo[b]);
/* 1357 */       if (isAppropriateDevice(midiDevice, paramClass, paramBoolean1, paramBoolean2))
/*      */       {
/* 1359 */         return midiDevice;
/*      */       }
/*      */     } 
/* 1362 */     return null;
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
/*      */   private static MidiDevice getFirstDevice(List paramList, Class<Receiver> paramClass) {
/* 1378 */     MidiDevice midiDevice = getFirstDevice(paramList, paramClass, false, false);
/*      */     
/* 1380 */     if (midiDevice != null) {
/* 1381 */       return midiDevice;
/*      */     }
/*      */     
/* 1384 */     if (paramClass == Receiver.class) {
/*      */       
/* 1386 */       midiDevice = getFirstDevice(paramList, paramClass, true, false);
/*      */       
/* 1388 */       if (midiDevice != null) {
/* 1389 */         return midiDevice;
/*      */       }
/*      */     } 
/*      */     
/* 1393 */     return null;
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
/*      */   private static MidiDevice getFirstDevice(List<MidiDeviceProvider> paramList, Class paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 1409 */     for (byte b = 0; b < paramList.size(); b++) {
/* 1410 */       MidiDeviceProvider midiDeviceProvider = paramList.get(b);
/* 1411 */       MidiDevice midiDevice = getFirstDevice(midiDeviceProvider, paramClass, paramBoolean1, paramBoolean2);
/*      */ 
/*      */       
/* 1414 */       if (midiDevice != null) {
/* 1415 */         return midiDevice;
/*      */       }
/*      */     } 
/* 1418 */     return null;
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
/*      */   private static boolean isAppropriateDevice(MidiDevice paramMidiDevice, Class<Receiver> paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 1447 */     if (paramClass.isInstance(paramMidiDevice))
/*      */     {
/*      */       
/* 1450 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1458 */     if ((!(paramMidiDevice instanceof Sequencer) && !(paramMidiDevice instanceof Synthesizer)) || (paramMidiDevice instanceof Sequencer && paramBoolean2) || (paramMidiDevice instanceof Synthesizer && paramBoolean1))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1464 */       if ((paramClass == Receiver.class && paramMidiDevice
/* 1465 */         .getMaxReceivers() != 0) || (paramClass == Transmitter.class && paramMidiDevice
/*      */         
/* 1467 */         .getMaxTransmitters() != 0)) {
/* 1468 */         return true;
/*      */       }
/*      */     }
/*      */     
/* 1472 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getProviders(Class<?> paramClass) {
/* 1483 */     return JDK13Services.getProviders(paramClass);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/MidiSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */