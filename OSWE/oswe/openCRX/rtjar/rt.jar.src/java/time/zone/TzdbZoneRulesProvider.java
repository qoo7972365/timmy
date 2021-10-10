/*     */ package java.time.zone;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TzdbZoneRulesProvider
/*     */   extends ZoneRulesProvider
/*     */ {
/*     */   private List<String> regionIds;
/*     */   private String versionId;
/*  99 */   private final Map<String, Object> regionToRules = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TzdbZoneRulesProvider() {
/*     */     try {
/* 109 */       String str = System.getProperty("java.home") + File.separator + "lib";
/* 110 */       try (DataInputStream null = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(str, "tzdb.dat"))))) {
/*     */ 
/*     */         
/* 113 */         load(dataInputStream);
/*     */       } 
/* 115 */     } catch (Exception exception) {
/* 116 */       throw new ZoneRulesException("Unable to load TZDB time-zone rules", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<String> provideZoneIds() {
/* 122 */     return new HashSet<>(this.regionIds);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ZoneRules provideRules(String paramString, boolean paramBoolean) {
/* 128 */     Object object = this.regionToRules.get(paramString);
/* 129 */     if (object == null) {
/* 130 */       throw new ZoneRulesException("Unknown time-zone ID: " + paramString);
/*     */     }
/*     */     try {
/* 133 */       if (object instanceof byte[]) {
/* 134 */         byte[] arrayOfByte = (byte[])object;
/* 135 */         DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(arrayOfByte));
/* 136 */         object = Ser.read(dataInputStream);
/* 137 */         this.regionToRules.put(paramString, object);
/*     */       } 
/* 139 */       return (ZoneRules)object;
/* 140 */     } catch (Exception exception) {
/* 141 */       throw new ZoneRulesException("Invalid binary time-zone data: TZDB:" + paramString + ", version: " + this.versionId, exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected NavigableMap<String, ZoneRules> provideVersions(String paramString) {
/* 147 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 148 */     ZoneRules zoneRules = getRules(paramString, false);
/* 149 */     if (zoneRules != null) {
/* 150 */       treeMap.put(this.versionId, zoneRules);
/*     */     }
/* 152 */     return (NavigableMap)treeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void load(DataInputStream paramDataInputStream) throws Exception {
/* 162 */     if (paramDataInputStream.readByte() != 1) {
/* 163 */       throw new StreamCorruptedException("File format not recognised");
/*     */     }
/*     */     
/* 166 */     String str = paramDataInputStream.readUTF();
/* 167 */     if (!"TZDB".equals(str)) {
/* 168 */       throw new StreamCorruptedException("File format not recognised");
/*     */     }
/*     */     
/* 171 */     short s = paramDataInputStream.readShort(); short s1;
/* 172 */     for (s1 = 0; s1 < s; s1++) {
/* 173 */       this.versionId = paramDataInputStream.readUTF();
/*     */     }
/*     */     
/* 176 */     s1 = paramDataInputStream.readShort();
/* 177 */     String[] arrayOfString = new String[s1]; short s2;
/* 178 */     for (s2 = 0; s2 < s1; s2++) {
/* 179 */       arrayOfString[s2] = paramDataInputStream.readUTF();
/*     */     }
/* 181 */     this.regionIds = Arrays.asList(arrayOfString);
/*     */     
/* 183 */     s2 = paramDataInputStream.readShort();
/* 184 */     Object[] arrayOfObject = new Object[s2]; byte b;
/* 185 */     for (b = 0; b < s2; b++) {
/* 186 */       byte[] arrayOfByte = new byte[paramDataInputStream.readShort()];
/* 187 */       paramDataInputStream.readFully(arrayOfByte);
/* 188 */       arrayOfObject[b] = arrayOfByte;
/*     */     } 
/*     */     
/* 191 */     for (b = 0; b < s; b++) {
/* 192 */       short s3 = paramDataInputStream.readShort();
/* 193 */       this.regionToRules.clear();
/* 194 */       for (byte b1 = 0; b1 < s3; b1++) {
/* 195 */         String str1 = arrayOfString[paramDataInputStream.readShort()];
/* 196 */         Object object = arrayOfObject[paramDataInputStream.readShort() & 0xFFFF];
/* 197 */         this.regionToRules.put(str1, object);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 204 */     return "TZDB[" + this.versionId + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/TzdbZoneRulesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */