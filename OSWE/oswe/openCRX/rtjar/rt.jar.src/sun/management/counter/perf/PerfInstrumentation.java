/*     */ package sun.management.counter.perf;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.management.counter.Counter;
/*     */ import sun.management.counter.Units;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerfInstrumentation
/*     */ {
/*     */   private ByteBuffer buffer;
/*     */   private Prologue prologue;
/*     */   private long lastModificationTime;
/*     */   private long lastUsed;
/*     */   private int nextEntry;
/*     */   private SortedMap<String, Counter> map;
/*     */   
/*     */   public PerfInstrumentation(ByteBuffer paramByteBuffer) {
/*  42 */     this.prologue = new Prologue(paramByteBuffer);
/*  43 */     this.buffer = paramByteBuffer;
/*  44 */     this.buffer.order(this.prologue.getByteOrder());
/*     */ 
/*     */     
/*  47 */     int i = getMajorVersion();
/*  48 */     int j = getMinorVersion();
/*     */ 
/*     */     
/*  51 */     if (i < 2) {
/*  52 */       throw new InstrumentationException("Unsupported version: " + i + "." + j);
/*     */     }
/*     */     
/*  55 */     rewind();
/*     */   }
/*     */   
/*     */   public int getMajorVersion() {
/*  59 */     return this.prologue.getMajorVersion();
/*     */   }
/*     */   
/*     */   public int getMinorVersion() {
/*  63 */     return this.prologue.getMinorVersion();
/*     */   }
/*     */   
/*     */   public long getModificationTimeStamp() {
/*  67 */     return this.prologue.getModificationTimeStamp();
/*     */   }
/*     */ 
/*     */   
/*     */   void rewind() {
/*  72 */     this.buffer.rewind();
/*  73 */     this.buffer.position(this.prologue.getEntryOffset());
/*  74 */     this.nextEntry = this.buffer.position();
/*     */     
/*  76 */     this.map = new TreeMap<>();
/*     */   }
/*     */   
/*     */   boolean hasNext() {
/*  80 */     return (this.nextEntry < this.prologue.getUsed());
/*     */   }
/*     */   Counter getNextCounter() {
/*     */     PerfLongArrayCounter perfLongArrayCounter;
/*  84 */     if (!hasNext()) {
/*  85 */       return null;
/*     */     }
/*     */     
/*  88 */     if (this.nextEntry % 4 != 0)
/*     */     {
/*  90 */       throw new InstrumentationException("Entry index not properly aligned: " + this.nextEntry);
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (this.nextEntry < 0 || this.nextEntry > this.buffer.limit())
/*     */     {
/*  96 */       throw new InstrumentationException("Entry index out of bounds: nextEntry = " + this.nextEntry + ", limit = " + this.buffer
/*     */           
/*  98 */           .limit());
/*     */     }
/*     */     
/* 101 */     this.buffer.position(this.nextEntry);
/* 102 */     PerfDataEntry perfDataEntry = new PerfDataEntry(this.buffer);
/* 103 */     this.nextEntry += perfDataEntry.size();
/*     */     
/* 105 */     PerfStringCounter perfStringCounter = null;
/* 106 */     PerfDataType perfDataType = perfDataEntry.type();
/* 107 */     if (perfDataType == PerfDataType.BYTE) {
/* 108 */       if (perfDataEntry.units() == Units.STRING && perfDataEntry.vectorLength() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         perfStringCounter = new PerfStringCounter(perfDataEntry.name(), perfDataEntry.variability(), perfDataEntry.flags(), perfDataEntry.vectorLength(), perfDataEntry.byteData());
/* 114 */       } else if (perfDataEntry.vectorLength() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 120 */         PerfByteArrayCounter perfByteArrayCounter = new PerfByteArrayCounter(perfDataEntry.name(), perfDataEntry.units(), perfDataEntry.variability(), perfDataEntry.flags(), perfDataEntry.vectorLength(), perfDataEntry.byteData());
/*     */       }
/*     */       else {
/*     */         
/*     */         assert false;
/*     */       } 
/* 126 */     } else if (perfDataType == PerfDataType.LONG) {
/* 127 */       if (perfDataEntry.vectorLength() == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         PerfLongCounter perfLongCounter = new PerfLongCounter(perfDataEntry.name(), perfDataEntry.units(), perfDataEntry.variability(), perfDataEntry.flags(), perfDataEntry.longData());
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 139 */         perfLongArrayCounter = new PerfLongArrayCounter(perfDataEntry.name(), perfDataEntry.units(), perfDataEntry.variability(), perfDataEntry.flags(), perfDataEntry.vectorLength(), perfDataEntry.longData());
/*     */       } 
/*     */     } else {
/*     */       assert false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return perfLongArrayCounter;
/*     */   }
/*     */   
/*     */   public synchronized List<Counter> getAllCounters() {
/* 151 */     while (hasNext()) {
/* 152 */       Counter counter = getNextCounter();
/* 153 */       if (counter != null) {
/* 154 */         this.map.put(counter.getName(), counter);
/*     */       }
/*     */     } 
/* 157 */     return new ArrayList<>(this.map.values());
/*     */   }
/*     */   
/*     */   public synchronized List<Counter> findByPattern(String paramString) {
/* 161 */     while (hasNext()) {
/* 162 */       Counter counter = getNextCounter();
/* 163 */       if (counter != null) {
/* 164 */         this.map.put(counter.getName(), counter);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     Pattern pattern = Pattern.compile(paramString);
/* 169 */     Matcher matcher = pattern.matcher("");
/* 170 */     ArrayList<Counter> arrayList = new ArrayList();
/*     */ 
/*     */     
/* 173 */     for (Map.Entry<String, Counter> entry : this.map.entrySet()) {
/* 174 */       String str = (String)entry.getKey();
/*     */ 
/*     */       
/* 177 */       matcher.reset(str);
/*     */ 
/*     */       
/* 180 */       if (matcher.lookingAt()) {
/* 181 */         arrayList.add(entry.getValue());
/*     */       }
/*     */     } 
/* 184 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfInstrumentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */