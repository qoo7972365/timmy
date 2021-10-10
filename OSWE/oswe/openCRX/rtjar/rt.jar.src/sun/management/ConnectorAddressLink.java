/*     */ package sun.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import sun.management.counter.Counter;
/*     */ import sun.management.counter.Units;
/*     */ import sun.management.counter.perf.PerfInstrumentation;
/*     */ import sun.misc.Perf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectorAddressLink
/*     */ {
/*     */   private static final String CONNECTOR_ADDRESS_COUNTER = "sun.management.JMXConnectorServer.address";
/*     */   private static final String REMOTE_CONNECTOR_COUNTER_PREFIX = "sun.management.JMXConnectorServer.";
/*  81 */   private static AtomicInteger counter = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void export(String paramString) {
/*  91 */     if (paramString == null || paramString.length() == 0) {
/*  92 */       throw new IllegalArgumentException("address not specified");
/*     */     }
/*  94 */     Perf perf = Perf.getPerf();
/*  95 */     perf.createString("sun.management.JMXConnectorServer.address", 1, Units.STRING
/*  96 */         .intValue(), paramString);
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
/*     */   public static String importFrom(int paramInt) throws IOException {
/*     */     ByteBuffer byteBuffer;
/* 113 */     Perf perf = Perf.getPerf();
/*     */     
/*     */     try {
/* 116 */       byteBuffer = perf.attach(paramInt, "r");
/* 117 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 118 */       throw new IOException(illegalArgumentException.getMessage());
/*     */     } 
/*     */     
/* 121 */     List<Counter> list = (new PerfInstrumentation(byteBuffer)).findByPattern("sun.management.JMXConnectorServer.address");
/* 122 */     Iterator<Counter> iterator = list.iterator();
/* 123 */     if (iterator.hasNext()) {
/* 124 */       Counter counter = iterator.next();
/* 125 */       return (String)counter.getValue();
/*     */     } 
/* 127 */     return null;
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
/*     */   public static void exportRemote(Map<String, String> paramMap) {
/* 140 */     int i = counter.getAndIncrement();
/* 141 */     Perf perf = Perf.getPerf();
/* 142 */     for (Map.Entry<String, String> entry : paramMap.entrySet()) {
/* 143 */       perf.createString("sun.management.JMXConnectorServer." + i + "." + (String)entry
/* 144 */           .getKey(), 1, Units.STRING.intValue(), (String)entry.getValue());
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
/*     */   public static Map<String, String> importRemoteFrom(int paramInt) throws IOException {
/*     */     ByteBuffer byteBuffer;
/* 163 */     Perf perf = Perf.getPerf();
/*     */     
/*     */     try {
/* 166 */       byteBuffer = perf.attach(paramInt, "r");
/* 167 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 168 */       throw new IOException(illegalArgumentException.getMessage());
/*     */     } 
/* 170 */     List<Counter> list = (new PerfInstrumentation(byteBuffer)).getAllCounters();
/* 171 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 172 */     for (Counter counter : list) {
/* 173 */       String str = counter.getName();
/* 174 */       if (str.startsWith("sun.management.JMXConnectorServer.") && 
/* 175 */         !str.equals("sun.management.JMXConnectorServer.address")) {
/* 176 */         hashMap.put(str, counter.getValue().toString());
/*     */       }
/*     */     } 
/* 179 */     return (Map)hashMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/ConnectorAddressLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */