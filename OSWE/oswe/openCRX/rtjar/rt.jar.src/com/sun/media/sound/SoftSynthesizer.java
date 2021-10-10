/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.prefs.BackingStoreException;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.sound.midi.Instrument;
/*      */ import javax.sound.midi.MidiChannel;
/*      */ import javax.sound.midi.MidiDevice;
/*      */ import javax.sound.midi.MidiSystem;
/*      */ import javax.sound.midi.MidiUnavailableException;
/*      */ import javax.sound.midi.Patch;
/*      */ import javax.sound.midi.Receiver;
/*      */ import javax.sound.midi.Soundbank;
/*      */ import javax.sound.midi.Transmitter;
/*      */ import javax.sound.midi.VoiceStatus;
/*      */ import javax.sound.sampled.AudioFormat;
/*      */ import javax.sound.sampled.AudioInputStream;
/*      */ import javax.sound.sampled.AudioSystem;
/*      */ import javax.sound.sampled.LineUnavailableException;
/*      */ import javax.sound.sampled.SourceDataLine;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SoftSynthesizer
/*      */   implements AudioSynthesizer, ReferenceCountingDevice
/*      */ {
/*      */   static final String INFO_NAME = "Gervill";
/*      */   static final String INFO_VENDOR = "OpenJDK";
/*      */   static final String INFO_DESCRIPTION = "Software MIDI Synthesizer";
/*      */   static final String INFO_VERSION = "1.0";
/*      */   
/*      */   protected static final class WeakAudioStream
/*      */     extends InputStream
/*      */   {
/*      */     private volatile AudioInputStream stream;
/*   76 */     public SoftAudioPusher pusher = null;
/*   77 */     public AudioInputStream jitter_stream = null;
/*   78 */     public SourceDataLine sourceDataLine = null;
/*   79 */     public volatile long silent_samples = 0L;
/*   80 */     private int framesize = 0;
/*      */     private WeakReference<AudioInputStream> weak_stream_link;
/*      */     private AudioFloatConverter converter;
/*   83 */     private float[] silentbuffer = null;
/*      */     
/*      */     private int samplesize;
/*      */     
/*      */     public void setInputStream(AudioInputStream param1AudioInputStream) {
/*   88 */       this.stream = param1AudioInputStream;
/*      */     }
/*      */     
/*      */     public int available() throws IOException {
/*   92 */       AudioInputStream audioInputStream = this.stream;
/*   93 */       if (audioInputStream != null)
/*   94 */         return audioInputStream.available(); 
/*   95 */       return 0;
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/*   99 */       byte[] arrayOfByte = new byte[1];
/*  100 */       if (read(arrayOfByte) == -1)
/*  101 */         return -1; 
/*  102 */       return arrayOfByte[0] & 0xFF;
/*      */     }
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  106 */       AudioInputStream audioInputStream = this.stream;
/*  107 */       if (audioInputStream != null) {
/*  108 */         return audioInputStream.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */       }
/*      */       
/*  111 */       int i = param1Int2 / this.samplesize;
/*  112 */       if (this.silentbuffer == null || this.silentbuffer.length < i)
/*  113 */         this.silentbuffer = new float[i]; 
/*  114 */       this.converter.toByteArray(this.silentbuffer, i, param1ArrayOfbyte, param1Int1);
/*      */       
/*  116 */       this.silent_samples += (param1Int2 / this.framesize);
/*      */       
/*  118 */       if (this.pusher != null && 
/*  119 */         this.weak_stream_link.get() == null) {
/*      */         
/*  121 */         Runnable runnable = new Runnable()
/*      */           {
/*  123 */             SoftAudioPusher _pusher = SoftSynthesizer.WeakAudioStream.this.pusher;
/*  124 */             AudioInputStream _jitter_stream = SoftSynthesizer.WeakAudioStream.this.jitter_stream;
/*  125 */             SourceDataLine _sourceDataLine = SoftSynthesizer.WeakAudioStream.this.sourceDataLine;
/*      */             
/*      */             public void run() {
/*  128 */               this._pusher.stop();
/*  129 */               if (this._jitter_stream != null)
/*      */                 try {
/*  131 */                   this._jitter_stream.close();
/*  132 */                 } catch (IOException iOException) {
/*  133 */                   iOException.printStackTrace();
/*      */                 }  
/*  135 */               if (this._sourceDataLine != null)
/*  136 */                 this._sourceDataLine.close(); 
/*      */             }
/*      */           };
/*  139 */         this.pusher = null;
/*  140 */         this.jitter_stream = null;
/*  141 */         this.sourceDataLine = null;
/*  142 */         (new Thread(runnable)).start();
/*      */       } 
/*  144 */       return param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     public WeakAudioStream(AudioInputStream param1AudioInputStream) {
/*  149 */       this.stream = param1AudioInputStream;
/*  150 */       this.weak_stream_link = new WeakReference<>(param1AudioInputStream);
/*  151 */       this.converter = AudioFloatConverter.getConverter(param1AudioInputStream.getFormat());
/*  152 */       this.samplesize = param1AudioInputStream.getFormat().getFrameSize() / param1AudioInputStream.getFormat().getChannels();
/*  153 */       this.framesize = param1AudioInputStream.getFormat().getFrameSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public AudioInputStream getAudioInputStream() {
/*  158 */       return new AudioInputStream(this, this.stream.getFormat(), -1L);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/*  163 */       AudioInputStream audioInputStream = this.weak_stream_link.get();
/*  164 */       if (audioInputStream != null)
/*  165 */         audioInputStream.close(); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Info extends MidiDevice.Info {
/*      */     Info() {
/*  171 */       super("Gervill", "OpenJDK", "Software MIDI Synthesizer", "1.0");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  179 */   static final MidiDevice.Info info = new Info();
/*      */   
/*  181 */   private static SourceDataLine testline = null;
/*      */   
/*  183 */   private static Soundbank defaultSoundBank = null;
/*      */   
/*  185 */   WeakAudioStream weakstream = null;
/*      */   
/*  187 */   final Object control_mutex = this;
/*      */   
/*  189 */   int voiceIDCounter = 0;
/*      */ 
/*      */ 
/*      */   
/*  193 */   int voice_allocation_mode = 0;
/*      */   
/*      */   boolean load_default_soundbank = false;
/*      */   
/*      */   boolean reverb_light = true;
/*      */   boolean reverb_on = true;
/*      */   boolean chorus_on = true;
/*      */   boolean agc_on = true;
/*      */   SoftChannel[] channels;
/*  202 */   SoftChannelProxy[] external_channels = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean largemode = false;
/*      */ 
/*      */   
/*  209 */   private int gmmode = 0;
/*      */   
/*  211 */   private int deviceid = 0;
/*      */   
/*  213 */   private AudioFormat format = new AudioFormat(44100.0F, 16, 2, true, false);
/*      */   
/*  215 */   private SourceDataLine sourceDataLine = null;
/*      */   
/*  217 */   private SoftAudioPusher pusher = null;
/*  218 */   private AudioInputStream pusher_stream = null;
/*      */   
/*  220 */   private float controlrate = 147.0F;
/*      */   
/*      */   private boolean open = false;
/*      */   
/*      */   private boolean implicitOpen = false;
/*  225 */   private String resamplerType = "linear";
/*  226 */   private SoftResampler resampler = new SoftLinearResampler();
/*      */   
/*  228 */   private int number_of_midi_channels = 16;
/*  229 */   private int maxpoly = 64;
/*  230 */   private long latency = 200000L;
/*      */   
/*      */   private boolean jitter_correction = false;
/*      */   
/*      */   private SoftMainMixer mainmixer;
/*      */   private SoftVoice[] voices;
/*  236 */   private Map<String, SoftTuning> tunings = new HashMap<>();
/*      */   
/*  238 */   private Map<String, SoftInstrument> inslist = new HashMap<>();
/*      */   
/*  240 */   private Map<String, ModelInstrument> loadedlist = new HashMap<>();
/*      */ 
/*      */   
/*  243 */   private ArrayList<Receiver> recvslist = new ArrayList<>();
/*      */ 
/*      */   
/*      */   private void getBuffers(ModelInstrument paramModelInstrument, List<ModelByteBuffer> paramList) {
/*  247 */     for (ModelPerformer modelPerformer : paramModelInstrument.getPerformers()) {
/*  248 */       if (modelPerformer.getOscillators() != null)
/*  249 */         for (ModelOscillator modelOscillator : modelPerformer.getOscillators()) {
/*  250 */           if (modelOscillator instanceof ModelByteBufferWavetable) {
/*  251 */             ModelByteBufferWavetable modelByteBufferWavetable = (ModelByteBufferWavetable)modelOscillator;
/*  252 */             ModelByteBuffer modelByteBuffer = modelByteBufferWavetable.getBuffer();
/*  253 */             if (modelByteBuffer != null)
/*  254 */               paramList.add(modelByteBuffer); 
/*  255 */             modelByteBuffer = modelByteBufferWavetable.get8BitExtensionBuffer();
/*  256 */             if (modelByteBuffer != null) {
/*  257 */               paramList.add(modelByteBuffer);
/*      */             }
/*      */           } 
/*      */         }  
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean loadSamples(List<ModelInstrument> paramList) {
/*  265 */     if (this.largemode)
/*  266 */       return true; 
/*  267 */     ArrayList<ModelByteBuffer> arrayList = new ArrayList();
/*  268 */     for (ModelInstrument modelInstrument : paramList)
/*  269 */       getBuffers(modelInstrument, arrayList); 
/*      */     try {
/*  271 */       ModelByteBuffer.loadAll(arrayList);
/*  272 */     } catch (IOException iOException) {
/*  273 */       return false;
/*      */     } 
/*  275 */     return true;
/*      */   }
/*      */   
/*      */   private boolean loadInstruments(List<ModelInstrument> paramList) {
/*  279 */     if (!isOpen())
/*  280 */       return false; 
/*  281 */     if (!loadSamples(paramList)) {
/*  282 */       return false;
/*      */     }
/*  284 */     synchronized (this.control_mutex) {
/*  285 */       if (this.channels != null)
/*  286 */         for (SoftChannel softChannel : this.channels) {
/*      */           
/*  288 */           softChannel.current_instrument = null;
/*  289 */           softChannel.current_director = null;
/*      */         }  
/*  291 */       for (Instrument instrument : paramList) {
/*  292 */         String str = patchToString(instrument.getPatch());
/*  293 */         SoftInstrument softInstrument = new SoftInstrument((ModelInstrument)instrument);
/*      */         
/*  295 */         this.inslist.put(str, softInstrument);
/*  296 */         this.loadedlist.put(str, (ModelInstrument)instrument);
/*      */       } 
/*      */     } 
/*      */     
/*  300 */     return true;
/*      */   }
/*      */   
/*      */   private void processPropertyInfo(Map<String, Object> paramMap) {
/*  304 */     AudioSynthesizerPropertyInfo[] arrayOfAudioSynthesizerPropertyInfo = getPropertyInfo(paramMap);
/*      */     
/*  306 */     String str = (String)(arrayOfAudioSynthesizerPropertyInfo[0]).value;
/*  307 */     if (str.equalsIgnoreCase("point")) {
/*      */       
/*  309 */       this.resampler = new SoftPointResampler();
/*  310 */       this.resamplerType = "point";
/*      */     }
/*  312 */     else if (str.equalsIgnoreCase("linear")) {
/*      */       
/*  314 */       this.resampler = new SoftLinearResampler2();
/*  315 */       this.resamplerType = "linear";
/*      */     }
/*  317 */     else if (str.equalsIgnoreCase("linear1")) {
/*      */       
/*  319 */       this.resampler = new SoftLinearResampler();
/*  320 */       this.resamplerType = "linear1";
/*      */     }
/*  322 */     else if (str.equalsIgnoreCase("linear2")) {
/*      */       
/*  324 */       this.resampler = new SoftLinearResampler2();
/*  325 */       this.resamplerType = "linear2";
/*      */     }
/*  327 */     else if (str.equalsIgnoreCase("cubic")) {
/*      */       
/*  329 */       this.resampler = new SoftCubicResampler();
/*  330 */       this.resamplerType = "cubic";
/*      */     }
/*  332 */     else if (str.equalsIgnoreCase("lanczos")) {
/*      */       
/*  334 */       this.resampler = new SoftLanczosResampler();
/*  335 */       this.resamplerType = "lanczos";
/*      */     }
/*  337 */     else if (str.equalsIgnoreCase("sinc")) {
/*      */       
/*  339 */       this.resampler = new SoftSincResampler();
/*  340 */       this.resamplerType = "sinc";
/*      */     } 
/*      */     
/*  343 */     setFormat((AudioFormat)(arrayOfAudioSynthesizerPropertyInfo[2]).value);
/*  344 */     this.controlrate = ((Float)(arrayOfAudioSynthesizerPropertyInfo[1]).value).floatValue();
/*  345 */     this.latency = ((Long)(arrayOfAudioSynthesizerPropertyInfo[3]).value).longValue();
/*  346 */     this.deviceid = ((Integer)(arrayOfAudioSynthesizerPropertyInfo[4]).value).intValue();
/*  347 */     this.maxpoly = ((Integer)(arrayOfAudioSynthesizerPropertyInfo[5]).value).intValue();
/*  348 */     this.reverb_on = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[6]).value).booleanValue();
/*  349 */     this.chorus_on = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[7]).value).booleanValue();
/*  350 */     this.agc_on = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[8]).value).booleanValue();
/*  351 */     this.largemode = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[9]).value).booleanValue();
/*  352 */     this.number_of_midi_channels = ((Integer)(arrayOfAudioSynthesizerPropertyInfo[10]).value).intValue();
/*  353 */     this.jitter_correction = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[11]).value).booleanValue();
/*  354 */     this.reverb_light = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[12]).value).booleanValue();
/*  355 */     this.load_default_soundbank = ((Boolean)(arrayOfAudioSynthesizerPropertyInfo[13]).value).booleanValue();
/*      */   }
/*      */   
/*      */   private String patchToString(Patch paramPatch) {
/*  359 */     if (paramPatch instanceof ModelPatch && ((ModelPatch)paramPatch).isPercussion()) {
/*  360 */       return "p." + paramPatch.getProgram() + "." + paramPatch.getBank();
/*      */     }
/*  362 */     return paramPatch.getProgram() + "." + paramPatch.getBank();
/*      */   }
/*      */   
/*      */   private void setFormat(AudioFormat paramAudioFormat) {
/*  366 */     if (paramAudioFormat.getChannels() > 2) {
/*  367 */       throw new IllegalArgumentException("Only mono and stereo audio supported.");
/*      */     }
/*      */     
/*  370 */     if (AudioFloatConverter.getConverter(paramAudioFormat) == null)
/*  371 */       throw new IllegalArgumentException("Audio format not supported."); 
/*  372 */     this.format = paramAudioFormat;
/*      */   }
/*      */   
/*      */   void removeReceiver(Receiver paramReceiver) {
/*  376 */     boolean bool = false;
/*  377 */     synchronized (this.control_mutex) {
/*  378 */       if (this.recvslist.remove(paramReceiver) && 
/*  379 */         this.implicitOpen && this.recvslist.isEmpty()) {
/*  380 */         bool = true;
/*      */       }
/*      */     } 
/*  383 */     if (bool)
/*  384 */       close(); 
/*      */   }
/*      */   
/*      */   SoftMainMixer getMainMixer() {
/*  388 */     if (!isOpen())
/*  389 */       return null; 
/*  390 */     return this.mainmixer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SoftInstrument findInstrument(int paramInt1, int paramInt2, int paramInt3) {
/*      */     String str;
/*  399 */     if (paramInt2 >> 7 == 120 || paramInt2 >> 7 == 121) {
/*      */       String str1;
/*  401 */       SoftInstrument softInstrument1 = this.inslist.get(paramInt1 + "." + paramInt2);
/*  402 */       if (softInstrument1 != null) {
/*  403 */         return softInstrument1;
/*      */       }
/*      */       
/*  406 */       if (paramInt2 >> 7 == 120) {
/*  407 */         str1 = "p.";
/*      */       } else {
/*  409 */         str1 = "";
/*      */       } 
/*      */       
/*  412 */       softInstrument1 = this.inslist.get(str1 + paramInt1 + "." + ((paramInt2 & 0x80) << 7));
/*      */       
/*  414 */       if (softInstrument1 != null) {
/*  415 */         return softInstrument1;
/*      */       }
/*  417 */       softInstrument1 = this.inslist.get(str1 + paramInt1 + "." + (paramInt2 & 0x80));
/*      */       
/*  419 */       if (softInstrument1 != null) {
/*  420 */         return softInstrument1;
/*      */       }
/*  422 */       softInstrument1 = this.inslist.get(str1 + paramInt1 + ".0");
/*  423 */       if (softInstrument1 != null) {
/*  424 */         return softInstrument1;
/*      */       }
/*  426 */       softInstrument1 = this.inslist.get(str1 + paramInt1 + "0.0");
/*  427 */       if (softInstrument1 != null)
/*  428 */         return softInstrument1; 
/*  429 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  434 */     if (paramInt3 == 9) {
/*  435 */       str = "p.";
/*      */     } else {
/*  437 */       str = "";
/*      */     } 
/*      */     
/*  440 */     SoftInstrument softInstrument = this.inslist.get(str + paramInt1 + "." + paramInt2);
/*  441 */     if (softInstrument != null) {
/*  442 */       return softInstrument;
/*      */     }
/*  444 */     softInstrument = this.inslist.get(str + paramInt1 + ".0");
/*  445 */     if (softInstrument != null) {
/*  446 */       return softInstrument;
/*      */     }
/*  448 */     softInstrument = this.inslist.get(str + "0.0");
/*  449 */     if (softInstrument != null)
/*  450 */       return softInstrument; 
/*  451 */     return null;
/*      */   }
/*      */   
/*      */   int getVoiceAllocationMode() {
/*  455 */     return this.voice_allocation_mode;
/*      */   }
/*      */   
/*      */   int getGeneralMidiMode() {
/*  459 */     return this.gmmode;
/*      */   }
/*      */   
/*      */   void setGeneralMidiMode(int paramInt) {
/*  463 */     this.gmmode = paramInt;
/*      */   }
/*      */   
/*      */   int getDeviceID() {
/*  467 */     return this.deviceid;
/*      */   }
/*      */   
/*      */   float getControlRate() {
/*  471 */     return this.controlrate;
/*      */   }
/*      */   
/*      */   SoftVoice[] getVoices() {
/*  475 */     return this.voices;
/*      */   }
/*      */   
/*      */   SoftTuning getTuning(Patch paramPatch) {
/*  479 */     String str = patchToString(paramPatch);
/*  480 */     SoftTuning softTuning = this.tunings.get(str);
/*  481 */     if (softTuning == null) {
/*  482 */       softTuning = new SoftTuning(paramPatch);
/*  483 */       this.tunings.put(str, softTuning);
/*      */     } 
/*  485 */     return softTuning;
/*      */   }
/*      */   
/*      */   public long getLatency() {
/*  489 */     synchronized (this.control_mutex) {
/*  490 */       return this.latency;
/*      */     } 
/*      */   }
/*      */   
/*      */   public AudioFormat getFormat() {
/*  495 */     synchronized (this.control_mutex) {
/*  496 */       return this.format;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getMaxPolyphony() {
/*  501 */     synchronized (this.control_mutex) {
/*  502 */       return this.maxpoly;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public MidiChannel[] getChannels() {
/*  508 */     synchronized (this.control_mutex) {
/*      */       MidiChannel[] arrayOfMidiChannel;
/*      */ 
/*      */       
/*  512 */       if (this.external_channels == null) {
/*  513 */         this.external_channels = new SoftChannelProxy[16];
/*  514 */         for (byte b1 = 0; b1 < this.external_channels.length; b1++) {
/*  515 */           this.external_channels[b1] = new SoftChannelProxy();
/*      */         }
/*      */       } 
/*  518 */       if (isOpen()) {
/*  519 */         arrayOfMidiChannel = new MidiChannel[this.channels.length];
/*      */       } else {
/*  521 */         arrayOfMidiChannel = new MidiChannel[16];
/*  522 */       }  for (byte b = 0; b < arrayOfMidiChannel.length; b++)
/*  523 */         arrayOfMidiChannel[b] = this.external_channels[b]; 
/*  524 */       return arrayOfMidiChannel;
/*      */     } 
/*      */   }
/*      */   
/*      */   public VoiceStatus[] getVoiceStatus() {
/*  529 */     if (!isOpen()) {
/*      */       
/*  531 */       VoiceStatus[] arrayOfVoiceStatus = new VoiceStatus[getMaxPolyphony()];
/*  532 */       for (byte b = 0; b < arrayOfVoiceStatus.length; b++) {
/*  533 */         VoiceStatus voiceStatus = new VoiceStatus();
/*  534 */         voiceStatus.active = false;
/*  535 */         voiceStatus.bank = 0;
/*  536 */         voiceStatus.channel = 0;
/*  537 */         voiceStatus.note = 0;
/*  538 */         voiceStatus.program = 0;
/*  539 */         voiceStatus.volume = 0;
/*  540 */         arrayOfVoiceStatus[b] = voiceStatus;
/*      */       } 
/*  542 */       return arrayOfVoiceStatus;
/*      */     } 
/*      */     
/*  545 */     synchronized (this.control_mutex) {
/*  546 */       VoiceStatus[] arrayOfVoiceStatus = new VoiceStatus[this.voices.length];
/*  547 */       for (byte b = 0; b < this.voices.length; b++) {
/*  548 */         SoftVoice softVoice = this.voices[b];
/*  549 */         VoiceStatus voiceStatus = new VoiceStatus();
/*  550 */         voiceStatus.active = softVoice.active;
/*  551 */         voiceStatus.bank = softVoice.bank;
/*  552 */         voiceStatus.channel = softVoice.channel;
/*  553 */         voiceStatus.note = softVoice.note;
/*  554 */         voiceStatus.program = softVoice.program;
/*  555 */         voiceStatus.volume = softVoice.volume;
/*  556 */         arrayOfVoiceStatus[b] = voiceStatus;
/*      */       } 
/*  558 */       return arrayOfVoiceStatus;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isSoundbankSupported(Soundbank paramSoundbank) {
/*  563 */     for (Instrument instrument : paramSoundbank.getInstruments()) {
/*  564 */       if (!(instrument instanceof ModelInstrument))
/*  565 */         return false; 
/*  566 */     }  return true;
/*      */   }
/*      */   
/*      */   public boolean loadInstrument(Instrument paramInstrument) {
/*  570 */     if (paramInstrument == null || !(paramInstrument instanceof ModelInstrument)) {
/*  571 */       throw new IllegalArgumentException("Unsupported instrument: " + paramInstrument);
/*      */     }
/*      */     
/*  574 */     ArrayList<ModelInstrument> arrayList = new ArrayList();
/*  575 */     arrayList.add((ModelInstrument)paramInstrument);
/*  576 */     return loadInstruments(arrayList);
/*      */   }
/*      */   
/*      */   public void unloadInstrument(Instrument paramInstrument) {
/*  580 */     if (paramInstrument == null || !(paramInstrument instanceof ModelInstrument)) {
/*  581 */       throw new IllegalArgumentException("Unsupported instrument: " + paramInstrument);
/*      */     }
/*      */     
/*  584 */     if (!isOpen()) {
/*      */       return;
/*      */     }
/*  587 */     String str = patchToString(paramInstrument.getPatch());
/*  588 */     synchronized (this.control_mutex) {
/*  589 */       for (SoftChannel softChannel : this.channels)
/*  590 */         softChannel.current_instrument = null; 
/*  591 */       this.inslist.remove(str);
/*  592 */       this.loadedlist.remove(str);
/*  593 */       for (byte b = 0; b < this.channels.length; b++) {
/*  594 */         this.channels[b].allSoundOff();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remapInstrument(Instrument paramInstrument1, Instrument paramInstrument2) {
/*  601 */     if (paramInstrument1 == null)
/*  602 */       throw new NullPointerException(); 
/*  603 */     if (paramInstrument2 == null)
/*  604 */       throw new NullPointerException(); 
/*  605 */     if (!(paramInstrument1 instanceof ModelInstrument)) {
/*  606 */       throw new IllegalArgumentException("Unsupported instrument: " + paramInstrument1
/*  607 */           .toString());
/*      */     }
/*  609 */     if (!(paramInstrument2 instanceof ModelInstrument)) {
/*  610 */       throw new IllegalArgumentException("Unsupported instrument: " + paramInstrument2
/*  611 */           .toString());
/*      */     }
/*  613 */     if (!isOpen()) {
/*  614 */       return false;
/*      */     }
/*  616 */     synchronized (this.control_mutex) {
/*  617 */       if (!this.loadedlist.containsValue(paramInstrument2))
/*  618 */         throw new IllegalArgumentException("Instrument to is not loaded."); 
/*  619 */       unloadInstrument(paramInstrument1);
/*      */       
/*  621 */       ModelMappedInstrument modelMappedInstrument = new ModelMappedInstrument((ModelInstrument)paramInstrument2, paramInstrument1.getPatch());
/*  622 */       return loadInstrument(modelMappedInstrument);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Soundbank getDefaultSoundbank() {
/*  627 */     synchronized (SoftSynthesizer.class) {
/*  628 */       if (defaultSoundBank != null) {
/*  629 */         return defaultSoundBank;
/*      */       }
/*  631 */       ArrayList arrayList = new ArrayList();
/*      */ 
/*      */       
/*  634 */       arrayList.add(new PrivilegedAction<InputStream>()
/*      */           {
/*      */             public InputStream run() {
/*  637 */               File file1 = new File(System.getProperties().getProperty("java.home"));
/*  638 */               File file2 = new File(new File(file1, "lib"), "audio");
/*  639 */               if (file2.exists()) {
/*  640 */                 File file = null;
/*  641 */                 File[] arrayOfFile = file2.listFiles();
/*  642 */                 if (arrayOfFile != null) {
/*  643 */                   for (byte b = 0; b < arrayOfFile.length; b++) {
/*  644 */                     File file3 = arrayOfFile[b];
/*  645 */                     if (file3.isFile()) {
/*  646 */                       String str = file3.getName().toLowerCase();
/*  647 */                       if ((str.endsWith(".sf2") || str
/*  648 */                         .endsWith(".dls")) && (
/*  649 */                         file == null || file3
/*  650 */                         .length() > file
/*  651 */                         .length())) {
/*  652 */                         file = file3;
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */                 }
/*      */                 
/*  658 */                 if (file != null) {
/*      */                   try {
/*  660 */                     return new FileInputStream(file);
/*  661 */                   } catch (IOException iOException) {}
/*      */                 }
/*      */               } 
/*      */               
/*  665 */               return null;
/*      */             }
/*      */           });
/*      */       
/*  669 */       arrayList.add(new PrivilegedAction<InputStream>() {
/*      */             public InputStream run() {
/*  671 */               if (System.getProperties().getProperty("os.name")
/*  672 */                 .startsWith("Linux")) {
/*      */                 
/*  674 */                 File[] arrayOfFile = { new File("/usr/share/soundfonts/"), new File("/usr/local/share/soundfonts/"), new File("/usr/share/sounds/sf2/"), new File("/usr/local/share/sounds/sf2/") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  686 */                 for (File file : arrayOfFile) {
/*  687 */                   if (file.exists()) {
/*  688 */                     File file1 = new File(file, "default.sf2");
/*  689 */                     if (file1.exists()) {
/*      */                       try {
/*  691 */                         return new FileInputStream(file1);
/*  692 */                       } catch (IOException iOException) {}
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  699 */               return null;
/*      */             }
/*      */           });
/*      */       
/*  703 */       arrayList.add(new PrivilegedAction<InputStream>() {
/*      */             public InputStream run() {
/*  705 */               if (System.getProperties().getProperty("os.name")
/*  706 */                 .startsWith("Windows")) {
/*  707 */                 File file = new File(System.getenv("SystemRoot") + "\\system32\\drivers\\gm.dls");
/*      */                 
/*  709 */                 if (file.exists()) {
/*      */                   try {
/*  711 */                     return new FileInputStream(file);
/*  712 */                   } catch (IOException iOException) {}
/*      */                 }
/*      */               } 
/*      */               
/*  716 */               return null;
/*      */             }
/*      */           });
/*      */       
/*  720 */       arrayList.add(new PrivilegedAction<InputStream>()
/*      */           {
/*      */             
/*      */             public InputStream run()
/*      */             {
/*  725 */               File file1 = new File(System.getProperty("user.home"), ".gervill");
/*      */               
/*  727 */               File file2 = new File(file1, "soundbank-emg.sf2");
/*      */               
/*  729 */               if (file2.exists()) {
/*      */                 try {
/*  731 */                   return new FileInputStream(file2);
/*  732 */                 } catch (IOException iOException) {}
/*      */               }
/*      */               
/*  735 */               return null;
/*      */             }
/*      */           });
/*      */       
/*  739 */       for (PrivilegedAction<InputStream> privilegedAction : (Iterable<PrivilegedAction<InputStream>>)arrayList) {
/*      */         try {
/*  741 */           Soundbank soundbank; InputStream inputStream = AccessController.<InputStream>doPrivileged(privilegedAction);
/*  742 */           if (inputStream == null)
/*      */             continue; 
/*      */           try {
/*  745 */             soundbank = MidiSystem.getSoundbank(new BufferedInputStream(inputStream));
/*      */           } finally {
/*  747 */             inputStream.close();
/*      */           } 
/*  749 */           if (soundbank != null) {
/*  750 */             defaultSoundBank = soundbank;
/*  751 */             return defaultSoundBank;
/*      */           } 
/*  753 */         } catch (Exception exception) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  761 */         defaultSoundBank = EmergencySoundbank.createSoundbank();
/*  762 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  765 */       if (defaultSoundBank != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  770 */         OutputStream outputStream = AccessController.<OutputStream>doPrivileged(() -> {
/*      */               try {
/*      */                 File file1 = new File(System.getProperty("user.home"), ".gervill");
/*      */ 
/*      */                 
/*      */                 if (!file1.exists()) {
/*      */                   file1.mkdirs();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 File file2 = new File(file1, "soundbank-emg.sf2");
/*      */                 
/*      */                 return file2.exists() ? null : new FileOutputStream(file2);
/*  783 */               } catch (FileNotFoundException fileNotFoundException) {
/*      */                 return null;
/*      */               } 
/*      */             });
/*  787 */         if (outputStream != null) {
/*      */           try {
/*  789 */             ((SF2Soundbank)defaultSoundBank).save(outputStream);
/*  790 */             outputStream.close();
/*  791 */           } catch (IOException iOException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  796 */     return defaultSoundBank;
/*      */   }
/*      */   
/*      */   public Instrument[] getAvailableInstruments() {
/*  800 */     Soundbank soundbank = getDefaultSoundbank();
/*  801 */     if (soundbank == null)
/*  802 */       return new Instrument[0]; 
/*  803 */     Instrument[] arrayOfInstrument = soundbank.getInstruments();
/*  804 */     Arrays.sort(arrayOfInstrument, new ModelInstrumentComparator());
/*  805 */     return arrayOfInstrument;
/*      */   }
/*      */   
/*      */   public Instrument[] getLoadedInstruments() {
/*  809 */     if (!isOpen()) {
/*  810 */       return new Instrument[0];
/*      */     }
/*  812 */     synchronized (this.control_mutex) {
/*      */       
/*  814 */       ModelInstrument[] arrayOfModelInstrument = new ModelInstrument[this.loadedlist.values().size()];
/*  815 */       this.loadedlist.values().toArray((Object[])arrayOfModelInstrument);
/*  816 */       Arrays.sort(arrayOfModelInstrument, new ModelInstrumentComparator());
/*  817 */       return (Instrument[])arrayOfModelInstrument;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean loadAllInstruments(Soundbank paramSoundbank) {
/*  822 */     ArrayList<ModelInstrument> arrayList = new ArrayList();
/*  823 */     for (Instrument instrument : paramSoundbank.getInstruments()) {
/*  824 */       if (instrument == null || !(instrument instanceof ModelInstrument)) {
/*  825 */         throw new IllegalArgumentException("Unsupported instrument: " + instrument);
/*      */       }
/*      */       
/*  828 */       arrayList.add((ModelInstrument)instrument);
/*      */     } 
/*  830 */     return loadInstruments(arrayList);
/*      */   }
/*      */   
/*      */   public void unloadAllInstruments(Soundbank paramSoundbank) {
/*  834 */     if (paramSoundbank == null || !isSoundbankSupported(paramSoundbank)) {
/*  835 */       throw new IllegalArgumentException("Unsupported soundbank: " + paramSoundbank);
/*      */     }
/*  837 */     if (!isOpen()) {
/*      */       return;
/*      */     }
/*  840 */     for (Instrument instrument : paramSoundbank.getInstruments()) {
/*  841 */       if (instrument instanceof ModelInstrument) {
/*  842 */         unloadInstrument(instrument);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean loadInstruments(Soundbank paramSoundbank, Patch[] paramArrayOfPatch) {
/*  848 */     ArrayList<ModelInstrument> arrayList = new ArrayList();
/*  849 */     for (Patch patch : paramArrayOfPatch) {
/*  850 */       Instrument instrument = paramSoundbank.getInstrument(patch);
/*  851 */       if (instrument == null || !(instrument instanceof ModelInstrument)) {
/*  852 */         throw new IllegalArgumentException("Unsupported instrument: " + instrument);
/*      */       }
/*      */       
/*  855 */       arrayList.add((ModelInstrument)instrument);
/*      */     } 
/*  857 */     return loadInstruments(arrayList);
/*      */   }
/*      */   
/*      */   public void unloadInstruments(Soundbank paramSoundbank, Patch[] paramArrayOfPatch) {
/*  861 */     if (paramSoundbank == null || !isSoundbankSupported(paramSoundbank)) {
/*  862 */       throw new IllegalArgumentException("Unsupported soundbank: " + paramSoundbank);
/*      */     }
/*  864 */     if (!isOpen()) {
/*      */       return;
/*      */     }
/*  867 */     for (Patch patch : paramArrayOfPatch) {
/*  868 */       Instrument instrument = paramSoundbank.getInstrument(patch);
/*  869 */       if (instrument instanceof ModelInstrument) {
/*  870 */         unloadInstrument(instrument);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public MidiDevice.Info getDeviceInfo() {
/*  876 */     return info;
/*      */   }
/*      */   
/*      */   private Properties getStoredProperties() {
/*  880 */     return 
/*  881 */       AccessController.<Properties>doPrivileged(() -> {
/*      */           Properties properties = new Properties();
/*      */           String str = "/com/sun/media/sound/softsynthesizer";
/*      */           try {
/*      */             Preferences preferences = Preferences.userRoot();
/*      */             if (preferences.nodeExists(str)) {
/*      */               Preferences preferences1 = preferences.node(str);
/*      */               String[] arrayOfString = preferences1.keys();
/*      */               for (String str1 : arrayOfString) {
/*      */                 String str2 = preferences1.get(str1, null);
/*      */                 if (str2 != null) {
/*      */                   properties.setProperty(str1, str2);
/*      */                 }
/*      */               } 
/*      */             } 
/*  896 */           } catch (BackingStoreException backingStoreException) {}
/*      */           return properties;
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public AudioSynthesizerPropertyInfo[] getPropertyInfo(Map<String, Object> paramMap) {
/*  903 */     ArrayList<AudioSynthesizerPropertyInfo> arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  912 */     boolean bool = (paramMap == null && this.open) ? true : false;
/*      */     
/*  914 */     AudioSynthesizerPropertyInfo audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("interpolation", bool ? this.resamplerType : "linear");
/*  915 */     audioSynthesizerPropertyInfo.choices = (Object[])new String[] { "linear", "linear1", "linear2", "cubic", "lanczos", "sinc", "point" };
/*      */     
/*  917 */     audioSynthesizerPropertyInfo.description = "Interpolation method";
/*  918 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  920 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("control rate", Float.valueOf(bool ? this.controlrate : 147.0F));
/*  921 */     audioSynthesizerPropertyInfo.description = "Control rate";
/*  922 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  924 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("format", bool ? this.format : new AudioFormat(44100.0F, 16, 2, true, false));
/*      */     
/*  926 */     audioSynthesizerPropertyInfo.description = "Default audio format";
/*  927 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  929 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("latency", Long.valueOf(bool ? this.latency : 120000L));
/*  930 */     audioSynthesizerPropertyInfo.description = "Default latency";
/*  931 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  933 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("device id", Integer.valueOf(bool ? this.deviceid : 0));
/*  934 */     audioSynthesizerPropertyInfo.description = "Device ID for SysEx Messages";
/*  935 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  937 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("max polyphony", Integer.valueOf(bool ? this.maxpoly : 64));
/*  938 */     audioSynthesizerPropertyInfo.description = "Maximum polyphony";
/*  939 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  941 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("reverb", Boolean.valueOf(bool ? this.reverb_on : true));
/*  942 */     audioSynthesizerPropertyInfo.description = "Turn reverb effect on or off";
/*  943 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  945 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("chorus", Boolean.valueOf(bool ? this.chorus_on : true));
/*  946 */     audioSynthesizerPropertyInfo.description = "Turn chorus effect on or off";
/*  947 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  949 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("auto gain control", Boolean.valueOf(bool ? this.agc_on : true));
/*  950 */     audioSynthesizerPropertyInfo.description = "Turn auto gain control on or off";
/*  951 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  953 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("large mode", Boolean.valueOf(bool ? this.largemode : false));
/*  954 */     audioSynthesizerPropertyInfo.description = "Turn large mode on or off.";
/*  955 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  957 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("midi channels", Integer.valueOf(bool ? this.channels.length : 16));
/*  958 */     audioSynthesizerPropertyInfo.description = "Number of midi channels.";
/*  959 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  961 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("jitter correction", Boolean.valueOf(bool ? this.jitter_correction : true));
/*  962 */     audioSynthesizerPropertyInfo.description = "Turn jitter correction on or off.";
/*  963 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  965 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("light reverb", Boolean.valueOf(bool ? this.reverb_light : true));
/*  966 */     audioSynthesizerPropertyInfo.description = "Turn light reverb mode on or off";
/*  967 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */     
/*  969 */     audioSynthesizerPropertyInfo = new AudioSynthesizerPropertyInfo("load default soundbank", Boolean.valueOf(bool ? this.load_default_soundbank : true));
/*  970 */     audioSynthesizerPropertyInfo.description = "Enabled/disable loading default soundbank";
/*  971 */     arrayList.add(audioSynthesizerPropertyInfo);
/*      */ 
/*      */     
/*  974 */     AudioSynthesizerPropertyInfo[] arrayOfAudioSynthesizerPropertyInfo = arrayList.<AudioSynthesizerPropertyInfo>toArray(new AudioSynthesizerPropertyInfo[arrayList.size()]);
/*      */     
/*  976 */     Properties properties = getStoredProperties();
/*      */     
/*  978 */     for (AudioSynthesizerPropertyInfo audioSynthesizerPropertyInfo1 : arrayOfAudioSynthesizerPropertyInfo) {
/*  979 */       V v = (paramMap == null) ? null : (V)paramMap.get(audioSynthesizerPropertyInfo1.name);
/*  980 */       v = (v != null) ? v : (V)properties.getProperty(audioSynthesizerPropertyInfo1.name);
/*  981 */       if (v != null) {
/*  982 */         Class<Boolean> clazz = audioSynthesizerPropertyInfo1.valueClass;
/*  983 */         if (clazz.isInstance(v)) {
/*  984 */           audioSynthesizerPropertyInfo1.value = v;
/*  985 */         } else if (v instanceof String) {
/*  986 */           String str = (String)v;
/*  987 */           if (clazz == Boolean.class) {
/*  988 */             if (str.equalsIgnoreCase("true"))
/*  989 */               audioSynthesizerPropertyInfo1.value = Boolean.TRUE; 
/*  990 */             if (str.equalsIgnoreCase("false"))
/*  991 */               audioSynthesizerPropertyInfo1.value = Boolean.FALSE; 
/*  992 */           } else if (clazz == AudioFormat.class) {
/*  993 */             int i = 2;
/*  994 */             boolean bool1 = true;
/*  995 */             boolean bool2 = false;
/*  996 */             int j = 16;
/*  997 */             float f = 44100.0F;
/*      */             try {
/*  999 */               StringTokenizer stringTokenizer = new StringTokenizer(str, ", ");
/* 1000 */               String str1 = "";
/* 1001 */               while (stringTokenizer.hasMoreTokens()) {
/* 1002 */                 String str2 = stringTokenizer.nextToken().toLowerCase();
/* 1003 */                 if (str2.equals("mono"))
/* 1004 */                   i = 1; 
/* 1005 */                 if (str2.startsWith("channel"))
/* 1006 */                   i = Integer.parseInt(str1); 
/* 1007 */                 if (str2.contains("unsigned"))
/* 1008 */                   bool1 = false; 
/* 1009 */                 if (str2.equals("big-endian"))
/* 1010 */                   bool2 = true; 
/* 1011 */                 if (str2.equals("bit"))
/* 1012 */                   j = Integer.parseInt(str1); 
/* 1013 */                 if (str2.equals("hz"))
/* 1014 */                   f = Float.parseFloat(str1); 
/* 1015 */                 str1 = str2;
/*      */               } 
/* 1017 */               audioSynthesizerPropertyInfo1.value = new AudioFormat(f, j, i, bool1, bool2);
/*      */             }
/* 1019 */             catch (NumberFormatException numberFormatException) {}
/*      */           } else {
/*      */ 
/*      */             
/*      */             try {
/* 1024 */               if (clazz == Byte.class)
/* 1025 */               { audioSynthesizerPropertyInfo1.value = Byte.valueOf(str); }
/* 1026 */               else if (clazz == Short.class)
/* 1027 */               { audioSynthesizerPropertyInfo1.value = Short.valueOf(str); }
/* 1028 */               else if (clazz == Integer.class)
/* 1029 */               { audioSynthesizerPropertyInfo1.value = Integer.valueOf(str); }
/* 1030 */               else if (clazz == Long.class)
/* 1031 */               { audioSynthesizerPropertyInfo1.value = Long.valueOf(str); }
/* 1032 */               else if (clazz == Float.class)
/* 1033 */               { audioSynthesizerPropertyInfo1.value = Float.valueOf(str); }
/* 1034 */               else if (clazz == Double.class)
/* 1035 */               { audioSynthesizerPropertyInfo1.value = Double.valueOf(str); } 
/* 1036 */             } catch (NumberFormatException numberFormatException) {}
/*      */           } 
/* 1038 */         } else if (v instanceof Number) {
/* 1039 */           Number number = (Number)v;
/* 1040 */           if (clazz == Byte.class)
/* 1041 */             audioSynthesizerPropertyInfo1.value = Byte.valueOf(number.byteValue()); 
/* 1042 */           if (clazz == Short.class)
/* 1043 */             audioSynthesizerPropertyInfo1.value = Short.valueOf(number.shortValue()); 
/* 1044 */           if (clazz == Integer.class)
/* 1045 */             audioSynthesizerPropertyInfo1.value = Integer.valueOf(number.intValue()); 
/* 1046 */           if (clazz == Long.class)
/* 1047 */             audioSynthesizerPropertyInfo1.value = Long.valueOf(number.longValue()); 
/* 1048 */           if (clazz == Float.class)
/* 1049 */             audioSynthesizerPropertyInfo1.value = Float.valueOf(number.floatValue()); 
/* 1050 */           if (clazz == Double.class) {
/* 1051 */             audioSynthesizerPropertyInfo1.value = Double.valueOf(number.doubleValue());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1056 */     return arrayOfAudioSynthesizerPropertyInfo;
/*      */   }
/*      */   
/*      */   public void open() throws MidiUnavailableException {
/* 1060 */     if (isOpen()) {
/* 1061 */       synchronized (this.control_mutex) {
/* 1062 */         this.implicitOpen = false;
/*      */       } 
/*      */       return;
/*      */     } 
/* 1066 */     open(null, null);
/*      */   }
/*      */   
/*      */   public void open(SourceDataLine paramSourceDataLine, Map<String, Object> paramMap) throws MidiUnavailableException {
/* 1070 */     if (isOpen()) {
/* 1071 */       synchronized (this.control_mutex) {
/* 1072 */         this.implicitOpen = false;
/*      */       } 
/*      */       return;
/*      */     } 
/* 1076 */     synchronized (this.control_mutex) {
/*      */       try {
/* 1078 */         if (paramSourceDataLine != null)
/*      */         {
/* 1080 */           setFormat(paramSourceDataLine.getFormat());
/*      */         }
/*      */         
/* 1083 */         AudioInputStream audioInputStream = openStream(getFormat(), paramMap);
/*      */         
/* 1085 */         this.weakstream = new WeakAudioStream(audioInputStream);
/* 1086 */         audioInputStream = this.weakstream.getAudioInputStream();
/*      */         
/* 1088 */         if (paramSourceDataLine == null)
/*      */         {
/* 1090 */           if (testline != null) {
/* 1091 */             paramSourceDataLine = testline;
/*      */           }
/*      */           else {
/*      */             
/* 1095 */             paramSourceDataLine = AudioSystem.getSourceDataLine(getFormat());
/*      */           } 
/*      */         }
/*      */         
/* 1099 */         double d = this.latency;
/*      */         
/* 1101 */         if (!paramSourceDataLine.isOpen()) {
/*      */           
/* 1103 */           int k = getFormat().getFrameSize() * (int)(getFormat().getFrameRate() * d / 1000000.0D);
/*      */ 
/*      */           
/* 1106 */           paramSourceDataLine.open(getFormat(), k);
/*      */ 
/*      */ 
/*      */           
/* 1110 */           this.sourceDataLine = paramSourceDataLine;
/*      */         } 
/* 1112 */         if (!paramSourceDataLine.isActive()) {
/* 1113 */           paramSourceDataLine.start();
/*      */         }
/* 1115 */         int i = 512;
/*      */         try {
/* 1117 */           i = audioInputStream.available();
/* 1118 */         } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1127 */         int j = paramSourceDataLine.getBufferSize();
/* 1128 */         j -= j % i;
/*      */         
/* 1130 */         if (j < 3 * i) {
/* 1131 */           j = 3 * i;
/*      */         }
/* 1133 */         if (this.jitter_correction) {
/* 1134 */           audioInputStream = new SoftJitterCorrector(audioInputStream, j, i);
/*      */           
/* 1136 */           if (this.weakstream != null)
/* 1137 */             this.weakstream.jitter_stream = audioInputStream; 
/*      */         } 
/* 1139 */         this.pusher = new SoftAudioPusher(paramSourceDataLine, audioInputStream, i);
/* 1140 */         this.pusher_stream = audioInputStream;
/* 1141 */         this.pusher.start();
/*      */         
/* 1143 */         if (this.weakstream != null)
/*      */         {
/* 1145 */           this.weakstream.pusher = this.pusher;
/* 1146 */           this.weakstream.sourceDataLine = this.sourceDataLine;
/*      */         }
/*      */       
/* 1149 */       } catch (LineUnavailableException|SecurityException|IllegalArgumentException lineUnavailableException) {
/*      */         
/* 1151 */         if (isOpen()) {
/* 1152 */           close();
/*      */         }
/*      */         
/* 1155 */         MidiUnavailableException midiUnavailableException = new MidiUnavailableException("Can not open line");
/*      */         
/* 1157 */         midiUnavailableException.initCause(lineUnavailableException);
/* 1158 */         throw midiUnavailableException;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AudioInputStream openStream(AudioFormat paramAudioFormat, Map<String, Object> paramMap) throws MidiUnavailableException {
/* 1166 */     if (isOpen()) {
/* 1167 */       throw new MidiUnavailableException("Synthesizer is already open");
/*      */     }
/* 1169 */     synchronized (this.control_mutex) {
/*      */       
/* 1171 */       this.gmmode = 0;
/* 1172 */       this.voice_allocation_mode = 0;
/*      */       
/* 1174 */       processPropertyInfo(paramMap);
/*      */       
/* 1176 */       this.open = true;
/* 1177 */       this.implicitOpen = false;
/*      */       
/* 1179 */       if (paramAudioFormat != null) {
/* 1180 */         setFormat(paramAudioFormat);
/*      */       }
/* 1182 */       if (this.load_default_soundbank) {
/*      */         
/* 1184 */         Soundbank soundbank = getDefaultSoundbank();
/* 1185 */         if (soundbank != null) {
/* 1186 */           loadAllInstruments(soundbank);
/*      */         }
/*      */       } 
/*      */       
/* 1190 */       this.voices = new SoftVoice[this.maxpoly]; byte b;
/* 1191 */       for (b = 0; b < this.maxpoly; b++) {
/* 1192 */         this.voices[b] = new SoftVoice(this);
/*      */       }
/* 1194 */       this.mainmixer = new SoftMainMixer(this);
/*      */       
/* 1196 */       this.channels = new SoftChannel[this.number_of_midi_channels];
/* 1197 */       for (b = 0; b < this.channels.length; b++) {
/* 1198 */         this.channels[b] = new SoftChannel(this, b);
/*      */       }
/* 1200 */       if (this.external_channels == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1205 */         if (this.channels.length < 16) {
/* 1206 */           this.external_channels = new SoftChannelProxy[16];
/*      */         } else {
/* 1208 */           this.external_channels = new SoftChannelProxy[this.channels.length];
/* 1209 */         }  for (b = 0; b < this.external_channels.length; b++) {
/* 1210 */           this.external_channels[b] = new SoftChannelProxy();
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1215 */       else if (this.channels.length > this.external_channels.length) {
/* 1216 */         SoftChannelProxy[] arrayOfSoftChannelProxy = new SoftChannelProxy[this.channels.length];
/*      */         int i;
/* 1218 */         for (i = 0; i < this.external_channels.length; i++)
/* 1219 */           arrayOfSoftChannelProxy[i] = this.external_channels[i]; 
/* 1220 */         i = this.external_channels.length;
/* 1221 */         for (; i < arrayOfSoftChannelProxy.length; i++) {
/* 1222 */           arrayOfSoftChannelProxy[i] = new SoftChannelProxy();
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1227 */       for (b = 0; b < this.channels.length; b++) {
/* 1228 */         this.external_channels[b].setChannel(this.channels[b]);
/*      */       }
/* 1230 */       for (SoftVoice softVoice : getVoices()) {
/* 1231 */         softVoice.resampler = this.resampler.openStreamer();
/*      */       }
/* 1233 */       for (Receiver receiver : getReceivers()) {
/* 1234 */         SoftReceiver softReceiver = (SoftReceiver)receiver;
/* 1235 */         softReceiver.open = this.open;
/* 1236 */         softReceiver.mainmixer = this.mainmixer;
/* 1237 */         softReceiver.midimessages = this.mainmixer.midimessages;
/*      */       } 
/*      */       
/* 1240 */       return this.mainmixer.getInputStream();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() {
/* 1246 */     if (!isOpen()) {
/*      */       return;
/*      */     }
/* 1249 */     SoftAudioPusher softAudioPusher = null;
/* 1250 */     AudioInputStream audioInputStream = null;
/* 1251 */     synchronized (this.control_mutex) {
/* 1252 */       if (this.pusher != null) {
/* 1253 */         softAudioPusher = this.pusher;
/* 1254 */         audioInputStream = this.pusher_stream;
/* 1255 */         this.pusher = null;
/* 1256 */         this.pusher_stream = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1260 */     if (softAudioPusher != null) {
/*      */ 
/*      */ 
/*      */       
/* 1264 */       softAudioPusher.stop();
/*      */       
/*      */       try {
/* 1267 */         audioInputStream.close();
/* 1268 */       } catch (IOException iOException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     synchronized (this.control_mutex) {
/*      */       
/* 1275 */       if (this.mainmixer != null)
/* 1276 */         this.mainmixer.close(); 
/* 1277 */       this.open = false;
/* 1278 */       this.implicitOpen = false;
/* 1279 */       this.mainmixer = null;
/* 1280 */       this.voices = null;
/* 1281 */       this.channels = null;
/*      */       
/* 1283 */       if (this.external_channels != null)
/* 1284 */         for (byte b = 0; b < this.external_channels.length; b++) {
/* 1285 */           this.external_channels[b].setChannel(null);
/*      */         } 
/* 1287 */       if (this.sourceDataLine != null) {
/* 1288 */         this.sourceDataLine.close();
/* 1289 */         this.sourceDataLine = null;
/*      */       } 
/*      */       
/* 1292 */       this.inslist.clear();
/* 1293 */       this.loadedlist.clear();
/* 1294 */       this.tunings.clear();
/*      */       
/* 1296 */       while (this.recvslist.size() != 0) {
/* 1297 */         ((Receiver)this.recvslist.get(this.recvslist.size() - 1)).close();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isOpen() {
/* 1303 */     synchronized (this.control_mutex) {
/* 1304 */       return this.open;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getMicrosecondPosition() {
/* 1310 */     if (!isOpen()) {
/* 1311 */       return 0L;
/*      */     }
/* 1313 */     synchronized (this.control_mutex) {
/* 1314 */       return this.mainmixer.getMicrosecondPosition();
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getMaxReceivers() {
/* 1319 */     return -1;
/*      */   }
/*      */   
/*      */   public int getMaxTransmitters() {
/* 1323 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Receiver getReceiver() throws MidiUnavailableException {
/* 1328 */     synchronized (this.control_mutex) {
/* 1329 */       SoftReceiver softReceiver = new SoftReceiver(this);
/* 1330 */       softReceiver.open = this.open;
/* 1331 */       this.recvslist.add(softReceiver);
/* 1332 */       return softReceiver;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Receiver> getReceivers() {
/* 1338 */     synchronized (this.control_mutex) {
/* 1339 */       ArrayList<Receiver> arrayList = new ArrayList();
/* 1340 */       arrayList.addAll(this.recvslist);
/* 1341 */       return arrayList;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Transmitter getTransmitter() throws MidiUnavailableException {
/* 1347 */     throw new MidiUnavailableException("No transmitter available");
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Transmitter> getTransmitters() {
/* 1352 */     return new ArrayList<>();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Receiver getReceiverReferenceCounting() throws MidiUnavailableException {
/* 1358 */     if (!isOpen()) {
/* 1359 */       open();
/* 1360 */       synchronized (this.control_mutex) {
/* 1361 */         this.implicitOpen = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1365 */     return getReceiver();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException {
/* 1371 */     throw new MidiUnavailableException("No transmitter available");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftSynthesizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */