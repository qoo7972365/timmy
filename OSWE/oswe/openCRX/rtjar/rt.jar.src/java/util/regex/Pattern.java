/*      */ package java.util.regex;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterators;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.text.Normalizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Pattern
/*      */   implements Serializable
/*      */ {
/*      */   public static final int UNIX_LINES = 1;
/*      */   public static final int CASE_INSENSITIVE = 2;
/*      */   public static final int COMMENTS = 4;
/*      */   public static final int MULTILINE = 8;
/*      */   public static final int LITERAL = 16;
/*      */   public static final int DOTALL = 32;
/*      */   public static final int UNICODE_CASE = 64;
/*      */   public static final int CANON_EQ = 128;
/*      */   public static final int UNICODE_CHARACTER_CLASS = 256;
/*      */   private static final long serialVersionUID = 5073258162644648461L;
/*      */   private String pattern;
/*      */   private int flags;
/*      */   private volatile transient boolean compiled = false;
/*      */   private transient String normalizedPattern;
/*      */   transient Node root;
/*      */   transient Node matchRoot;
/*      */   transient int[] buffer;
/*      */   volatile transient Map<String, Integer> namedGroups;
/*      */   transient GroupHead[] groupNodes;
/*      */   private transient int[] temp;
/*      */   transient int capturingGroupCount;
/*      */   transient int localCount;
/*      */   private transient int cursor;
/*      */   private transient int patternLength;
/*      */   private transient boolean hasSupplementary;
/*      */   static final int MAX_REPS = 2147483647;
/*      */   static final int GREEDY = 0;
/*      */   static final int LAZY = 1;
/*      */   static final int POSSESSIVE = 2;
/*      */   static final int INDEPENDENT = 3;
/*      */   
/*      */   public static Pattern compile(String paramString) {
/* 1028 */     return new Pattern(paramString, 0);
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
/*      */   public static Pattern compile(String paramString, int paramInt) {
/* 1054 */     return new Pattern(paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String pattern() {
/* 1063 */     return this.pattern;
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
/*      */   public String toString() {
/* 1075 */     return this.pattern;
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
/*      */   public Matcher matcher(CharSequence paramCharSequence) {
/* 1087 */     if (!this.compiled)
/* 1088 */       synchronized (this) {
/* 1089 */         if (!this.compiled) {
/* 1090 */           compile();
/*      */         }
/*      */       }  
/* 1093 */     return new Matcher(this, paramCharSequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int flags() {
/* 1103 */     return this.flags;
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
/*      */   public static boolean matches(String paramString, CharSequence paramCharSequence) {
/* 1133 */     Pattern pattern = compile(paramString);
/* 1134 */     Matcher matcher = pattern.matcher(paramCharSequence);
/* 1135 */     return matcher.matches();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] split(CharSequence paramCharSequence, int paramInt) {
/* 1203 */     int i = 0;
/* 1204 */     boolean bool = (paramInt > 0) ? true : false;
/* 1205 */     ArrayList<String> arrayList = new ArrayList();
/* 1206 */     Matcher matcher = matcher(paramCharSequence);
/*      */ 
/*      */     
/* 1209 */     while (matcher.find()) {
/* 1210 */       if (!bool || arrayList.size() < paramInt - 1) {
/* 1211 */         if (!i && i == matcher.start() && matcher.start() == matcher.end()) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1216 */         String str = paramCharSequence.subSequence(i, matcher.start()).toString();
/* 1217 */         arrayList.add(str);
/* 1218 */         i = matcher.end(); continue;
/* 1219 */       }  if (arrayList.size() == paramInt - 1) {
/*      */         
/* 1221 */         String str = paramCharSequence.subSequence(i, paramCharSequence.length()).toString();
/* 1222 */         arrayList.add(str);
/* 1223 */         i = matcher.end();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1228 */     if (i == 0) {
/* 1229 */       return new String[] { paramCharSequence.toString() };
/*      */     }
/*      */     
/* 1232 */     if (!bool || arrayList.size() < paramInt) {
/* 1233 */       arrayList.add(paramCharSequence.subSequence(i, paramCharSequence.length()).toString());
/*      */     }
/*      */     
/* 1236 */     int j = arrayList.size();
/* 1237 */     if (paramInt == 0)
/* 1238 */       while (j > 0 && ((String)arrayList.get(j - 1)).equals(""))
/* 1239 */         j--;  
/* 1240 */     String[] arrayOfString = new String[j];
/* 1241 */     return (String[])arrayList.subList(0, j).toArray((Object[])arrayOfString);
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
/*      */   public String[] split(CharSequence paramCharSequence) {
/* 1273 */     return split(paramCharSequence, 0);
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
/*      */   public static String quote(String paramString) {
/* 1291 */     int i = paramString.indexOf("\\E");
/* 1292 */     if (i == -1) {
/* 1293 */       return "\\Q" + paramString + "\\E";
/*      */     }
/* 1295 */     StringBuilder stringBuilder = new StringBuilder(paramString.length() * 2);
/* 1296 */     stringBuilder.append("\\Q");
/* 1297 */     i = 0;
/* 1298 */     int j = 0;
/* 1299 */     while ((i = paramString.indexOf("\\E", j)) != -1) {
/* 1300 */       stringBuilder.append(paramString.substring(j, i));
/* 1301 */       j = i + 2;
/* 1302 */       stringBuilder.append("\\E\\\\E\\Q");
/*      */     } 
/* 1304 */     stringBuilder.append(paramString.substring(j, paramString.length()));
/* 1305 */     stringBuilder.append("\\E");
/* 1306 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1317 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1320 */     this.capturingGroupCount = 1;
/* 1321 */     this.localCount = 0;
/*      */ 
/*      */     
/* 1324 */     this.compiled = false;
/* 1325 */     if (this.pattern.length() == 0) {
/* 1326 */       this.root = new Start(lastAccept);
/* 1327 */       this.matchRoot = lastAccept;
/* 1328 */       this.compiled = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Pattern(String paramString, int paramInt) {
/* 1339 */     this.pattern = paramString;
/* 1340 */     this.flags = paramInt;
/*      */ 
/*      */     
/* 1343 */     if ((this.flags & 0x100) != 0) {
/* 1344 */       this.flags |= 0x40;
/*      */     }
/*      */     
/* 1347 */     this.capturingGroupCount = 1;
/* 1348 */     this.localCount = 0;
/*      */     
/* 1350 */     if (this.pattern.length() > 0) {
/*      */       try {
/* 1352 */         compile();
/* 1353 */       } catch (StackOverflowError stackOverflowError) {
/* 1354 */         throw error("Stack overflow during pattern compilation");
/*      */       } 
/*      */     } else {
/* 1357 */       this.root = new Start(lastAccept);
/* 1358 */       this.matchRoot = lastAccept;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void normalize() {
/* 1367 */     boolean bool = false;
/* 1368 */     int i = -1;
/*      */ 
/*      */     
/* 1371 */     this.normalizedPattern = Normalizer.normalize(this.pattern, Normalizer.Form.NFD);
/* 1372 */     this.patternLength = this.normalizedPattern.length();
/*      */ 
/*      */     
/* 1375 */     StringBuilder stringBuilder = new StringBuilder(this.patternLength); int j;
/* 1376 */     for (j = 0; j < this.patternLength; ) {
/* 1377 */       int k = this.normalizedPattern.codePointAt(j);
/*      */       
/* 1379 */       if (Character.getType(k) == 6 && i != -1) {
/*      */         
/* 1381 */         StringBuilder stringBuilder1 = new StringBuilder();
/* 1382 */         stringBuilder1.appendCodePoint(i);
/* 1383 */         stringBuilder1.appendCodePoint(k);
/* 1384 */         while (Character.getType(k) == 6) {
/* 1385 */           j += Character.charCount(k);
/* 1386 */           if (j >= this.patternLength)
/*      */             break; 
/* 1388 */           k = this.normalizedPattern.codePointAt(j);
/* 1389 */           stringBuilder1.appendCodePoint(k);
/*      */         } 
/* 1391 */         String str = produceEquivalentAlternation(stringBuilder1
/* 1392 */             .toString());
/* 1393 */         stringBuilder.setLength(stringBuilder.length() - Character.charCount(i));
/* 1394 */         stringBuilder.append("(?:").append(str).append(")");
/* 1395 */       } else if (k == 91 && i != 92) {
/* 1396 */         j = normalizeCharClass(stringBuilder, j);
/*      */       } else {
/* 1398 */         stringBuilder.appendCodePoint(k);
/*      */       } 
/* 1400 */       i = k;
/* 1401 */       j += Character.charCount(k);
/*      */     } 
/* 1403 */     this.normalizedPattern = stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int normalizeCharClass(StringBuilder paramStringBuilder, int paramInt) {
/*      */     String str;
/* 1412 */     StringBuilder stringBuilder1 = new StringBuilder();
/* 1413 */     StringBuilder stringBuilder2 = null;
/* 1414 */     int i = -1;
/*      */ 
/*      */     
/* 1417 */     paramInt++;
/* 1418 */     if (paramInt == this.normalizedPattern.length())
/* 1419 */       throw error("Unclosed character class"); 
/* 1420 */     stringBuilder1.append("[");
/*      */     while (true) {
/* 1422 */       int j = this.normalizedPattern.codePointAt(paramInt);
/*      */ 
/*      */       
/* 1425 */       if (j == 93 && i != 92) {
/* 1426 */         stringBuilder1.append((char)j); break;
/*      */       } 
/* 1428 */       if (Character.getType(j) == 6) {
/* 1429 */         StringBuilder stringBuilder = new StringBuilder();
/* 1430 */         stringBuilder.appendCodePoint(i);
/* 1431 */         while (Character.getType(j) == 6) {
/* 1432 */           stringBuilder.appendCodePoint(j);
/* 1433 */           paramInt += Character.charCount(j);
/* 1434 */           if (paramInt >= this.normalizedPattern.length())
/*      */             break; 
/* 1436 */           j = this.normalizedPattern.codePointAt(paramInt);
/*      */         } 
/* 1438 */         String str1 = produceEquivalentAlternation(stringBuilder
/* 1439 */             .toString());
/*      */         
/* 1441 */         stringBuilder1.setLength(stringBuilder1.length() - Character.charCount(i));
/* 1442 */         if (stringBuilder2 == null)
/* 1443 */           stringBuilder2 = new StringBuilder(); 
/* 1444 */         stringBuilder2.append('|');
/* 1445 */         stringBuilder2.append(str1);
/*      */       } else {
/* 1447 */         stringBuilder1.appendCodePoint(j);
/* 1448 */         paramInt++;
/*      */       } 
/* 1450 */       if (paramInt == this.normalizedPattern.length())
/* 1451 */         throw error("Unclosed character class"); 
/* 1452 */       i = j;
/*      */     } 
/*      */     
/* 1455 */     if (stringBuilder2 != null) {
/* 1456 */       str = "(?:" + stringBuilder1.toString() + stringBuilder2.toString() + ")";
/*      */     } else {
/* 1458 */       str = stringBuilder1.toString();
/*      */     } 
/*      */     
/* 1461 */     paramStringBuilder.append(str);
/* 1462 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String produceEquivalentAlternation(String paramString) {
/* 1471 */     int i = countChars(paramString, 0, 1);
/* 1472 */     if (paramString.length() == i)
/*      */     {
/* 1474 */       return paramString;
/*      */     }
/* 1476 */     String str1 = paramString.substring(0, i);
/* 1477 */     String str2 = paramString.substring(i);
/*      */     
/* 1479 */     String[] arrayOfString = producePermutations(str2);
/* 1480 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/*      */ 
/*      */     
/* 1483 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1484 */       String str = str1 + arrayOfString[b];
/* 1485 */       if (b > 0)
/* 1486 */         stringBuilder.append("|" + str); 
/* 1487 */       str = composeOneStep(str);
/* 1488 */       if (str != null)
/* 1489 */         stringBuilder.append("|" + produceEquivalentAlternation(str)); 
/*      */     } 
/* 1491 */     return stringBuilder.toString();
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
/*      */   private String[] producePermutations(String paramString) {
/* 1504 */     if (paramString.length() == countChars(paramString, 0, 1)) {
/* 1505 */       return new String[] { paramString };
/*      */     }
/* 1507 */     if (paramString.length() == countChars(paramString, 0, 2)) {
/* 1508 */       int n = Character.codePointAt(paramString, 0);
/* 1509 */       int i1 = Character.codePointAt(paramString, Character.charCount(n));
/* 1510 */       if (getClass(i1) == getClass(n)) {
/* 1511 */         return new String[] { paramString };
/*      */       }
/* 1513 */       String[] arrayOfString = new String[2];
/* 1514 */       arrayOfString[0] = paramString;
/* 1515 */       StringBuilder stringBuilder = new StringBuilder(2);
/* 1516 */       stringBuilder.appendCodePoint(i1);
/* 1517 */       stringBuilder.appendCodePoint(n);
/* 1518 */       arrayOfString[1] = stringBuilder.toString();
/* 1519 */       return arrayOfString;
/*      */     } 
/*      */     
/* 1522 */     int i = 1;
/* 1523 */     int j = countCodePoints(paramString);
/* 1524 */     for (byte b1 = 1; b1 < j; b1++) {
/* 1525 */       i *= b1 + 1;
/*      */     }
/* 1527 */     String[] arrayOfString1 = new String[i];
/*      */     
/* 1529 */     int[] arrayOfInt = new int[j]; byte b2; int k;
/* 1530 */     for (b2 = 0, k = 0; b2 < j; b2++) {
/* 1531 */       int n = Character.codePointAt(paramString, k);
/* 1532 */       arrayOfInt[b2] = getClass(n);
/* 1533 */       k += Character.charCount(n);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1538 */     b2 = 0;
/*      */     
/*      */     int m;
/* 1541 */     for (byte b3 = 0; b3 < j; b3++, m += k) {
/* 1542 */       k = countChars(paramString, m, 1);
/* 1543 */       boolean bool = false;
/* 1544 */       for (int n = b3 - 1; n >= 0; n--) {
/* 1545 */         if (arrayOfInt[n] == arrayOfInt[b3]) {
/*      */           break label43;
/*      */         }
/*      */       } 
/* 1549 */       StringBuilder stringBuilder = new StringBuilder(paramString);
/* 1550 */       String str1 = stringBuilder.delete(m, m + k).toString();
/* 1551 */       String[] arrayOfString = producePermutations(str1);
/*      */       
/* 1553 */       String str2 = paramString.substring(m, m + k); byte b;
/* 1554 */       label43: for (b = 0; b < arrayOfString.length; b++)
/* 1555 */         arrayOfString1[b2++] = str2 + arrayOfString[b]; 
/*      */     } 
/* 1557 */     String[] arrayOfString2 = new String[b2];
/* 1558 */     for (m = 0; m < b2; m++)
/* 1559 */       arrayOfString2[m] = arrayOfString1[m]; 
/* 1560 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */   private int getClass(int paramInt) {
/* 1564 */     return Normalizer.getCombiningClass(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String composeOneStep(String paramString) {
/* 1575 */     int i = countChars(paramString, 0, 2);
/* 1576 */     String str1 = paramString.substring(0, i);
/* 1577 */     String str2 = Normalizer.normalize(str1, Normalizer.Form.NFC);
/*      */     
/* 1579 */     if (str2.equals(str1)) {
/* 1580 */       return null;
/*      */     }
/* 1582 */     String str3 = paramString.substring(i);
/* 1583 */     return str2 + str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void RemoveQEQuoting() {
/* 1592 */     int i = this.patternLength;
/* 1593 */     byte b1 = 0;
/* 1594 */     while (b1 < i - 1) {
/* 1595 */       if (this.temp[b1] != 92) {
/* 1596 */         b1++; continue;
/* 1597 */       }  if (this.temp[b1 + 1] != 81) {
/* 1598 */         b1 += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1602 */     if (b1 >= i - 1)
/*      */       return; 
/* 1604 */     byte b2 = b1;
/* 1605 */     b1 += 2;
/* 1606 */     int[] arrayOfInt = new int[b2 + 3 * (i - b1) + 2];
/* 1607 */     System.arraycopy(this.temp, 0, arrayOfInt, 0, b2);
/*      */     
/* 1609 */     boolean bool1 = true;
/* 1610 */     boolean bool2 = true;
/* 1611 */     while (b1 < i) {
/* 1612 */       int j = this.temp[b1++];
/* 1613 */       if (!ASCII.isAscii(j) || ASCII.isAlpha(j)) {
/* 1614 */         arrayOfInt[b2++] = j;
/* 1615 */       } else if (ASCII.isDigit(j)) {
/* 1616 */         if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1622 */           arrayOfInt[b2++] = 92;
/* 1623 */           arrayOfInt[b2++] = 120;
/* 1624 */           arrayOfInt[b2++] = 51;
/*      */         } 
/* 1626 */         arrayOfInt[b2++] = j;
/* 1627 */       } else if (j != 92) {
/* 1628 */         if (bool1) arrayOfInt[b2++] = 92; 
/* 1629 */         arrayOfInt[b2++] = j;
/* 1630 */       } else if (bool1) {
/* 1631 */         if (this.temp[b1] == 69) {
/* 1632 */           b1++;
/* 1633 */           bool1 = false;
/*      */         } else {
/* 1635 */           arrayOfInt[b2++] = 92;
/* 1636 */           arrayOfInt[b2++] = 92;
/*      */         } 
/*      */       } else {
/* 1639 */         if (this.temp[b1] == 81) {
/* 1640 */           b1++;
/* 1641 */           bool1 = true;
/* 1642 */           bool2 = true;
/*      */           continue;
/*      */         } 
/* 1645 */         arrayOfInt[b2++] = j;
/* 1646 */         if (b1 != i) {
/* 1647 */           arrayOfInt[b2++] = this.temp[b1++];
/*      */         }
/*      */       } 
/*      */       
/* 1651 */       bool2 = false;
/*      */     } 
/*      */     
/* 1654 */     this.patternLength = b2;
/* 1655 */     this.temp = Arrays.copyOf(arrayOfInt, b2 + 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compile() {
/* 1664 */     if (has(128) && !has(16)) {
/* 1665 */       normalize();
/*      */     } else {
/* 1667 */       this.normalizedPattern = this.pattern;
/*      */     } 
/* 1669 */     this.patternLength = this.normalizedPattern.length();
/*      */ 
/*      */ 
/*      */     
/* 1673 */     this.temp = new int[this.patternLength + 2];
/*      */     
/* 1675 */     this.hasSupplementary = false;
/* 1676 */     byte b = 0;
/*      */     
/* 1678 */     for (int i = 0; i < this.patternLength; i += Character.charCount(j)) {
/* 1679 */       int j = this.normalizedPattern.codePointAt(i);
/* 1680 */       if (isSupplementary(j)) {
/* 1681 */         this.hasSupplementary = true;
/*      */       }
/* 1683 */       this.temp[b++] = j;
/*      */     } 
/*      */     
/* 1686 */     this.patternLength = b;
/*      */     
/* 1688 */     if (!has(16)) {
/* 1689 */       RemoveQEQuoting();
/*      */     }
/*      */     
/* 1692 */     this.buffer = new int[32];
/* 1693 */     this.groupNodes = new GroupHead[10];
/* 1694 */     this.namedGroups = null;
/*      */     
/* 1696 */     if (has(16)) {
/*      */       
/* 1698 */       this.matchRoot = newSlice(this.temp, this.patternLength, this.hasSupplementary);
/* 1699 */       this.matchRoot.next = lastAccept;
/*      */     } else {
/*      */       
/* 1702 */       this.matchRoot = expr(lastAccept);
/*      */       
/* 1704 */       if (this.patternLength != this.cursor) {
/* 1705 */         if (peek() == 41) {
/* 1706 */           throw error("Unmatched closing ')'");
/*      */         }
/* 1708 */         throw error("Unexpected internal error");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     if (this.matchRoot instanceof Slice) {
/* 1715 */       this.root = BnM.optimize(this.matchRoot);
/* 1716 */       if (this.root == this.matchRoot) {
/* 1717 */         this.root = this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot);
/*      */       }
/* 1719 */     } else if (this.matchRoot instanceof Begin || this.matchRoot instanceof First) {
/* 1720 */       this.root = this.matchRoot;
/*      */     } else {
/* 1722 */       this.root = this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot);
/*      */     } 
/*      */ 
/*      */     
/* 1726 */     this.temp = null;
/* 1727 */     this.buffer = null;
/* 1728 */     this.groupNodes = null;
/* 1729 */     this.patternLength = 0;
/* 1730 */     this.compiled = true;
/*      */   }
/*      */   
/*      */   Map<String, Integer> namedGroups() {
/* 1734 */     if (this.namedGroups == null)
/* 1735 */       this.namedGroups = new HashMap<>(2); 
/* 1736 */     return this.namedGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printObjectTree(Node paramNode) {
/* 1743 */     while (paramNode != null) {
/* 1744 */       if (paramNode instanceof Prolog)
/* 1745 */       { System.out.println(paramNode);
/* 1746 */         printObjectTree(((Prolog)paramNode).loop);
/* 1747 */         System.out.println("**** end contents prolog loop"); }
/* 1748 */       else if (paramNode instanceof Loop)
/* 1749 */       { System.out.println(paramNode);
/* 1750 */         printObjectTree(((Loop)paramNode).body);
/* 1751 */         System.out.println("**** end contents Loop body"); }
/* 1752 */       else if (paramNode instanceof Curly)
/* 1753 */       { System.out.println(paramNode);
/* 1754 */         printObjectTree(((Curly)paramNode).atom);
/* 1755 */         System.out.println("**** end contents Curly body"); }
/* 1756 */       else if (paramNode instanceof GroupCurly)
/* 1757 */       { System.out.println(paramNode);
/* 1758 */         printObjectTree(((GroupCurly)paramNode).atom);
/* 1759 */         System.out.println("**** end contents GroupCurly body"); }
/* 1760 */       else { if (paramNode instanceof GroupTail) {
/* 1761 */           System.out.println(paramNode);
/* 1762 */           System.out.println("Tail next is " + paramNode.next);
/*      */           return;
/*      */         } 
/* 1765 */         System.out.println(paramNode); }
/*      */       
/* 1767 */       paramNode = paramNode.next;
/* 1768 */       if (paramNode != null)
/* 1769 */         System.out.println("->next:"); 
/* 1770 */       if (paramNode == accept) {
/* 1771 */         System.out.println("Accept Node");
/* 1772 */         paramNode = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeInfo
/*      */   {
/*      */     int minLength;
/*      */     
/*      */     int maxLength;
/*      */     
/*      */     boolean maxValid;
/*      */     boolean deterministic;
/*      */     
/*      */     TreeInfo() {
/* 1788 */       reset();
/*      */     }
/*      */     void reset() {
/* 1791 */       this.minLength = 0;
/* 1792 */       this.maxLength = 0;
/* 1793 */       this.maxValid = true;
/* 1794 */       this.deterministic = true;
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
/*      */   
/*      */   private boolean has(int paramInt) {
/* 1808 */     return ((this.flags & paramInt) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void accept(int paramInt, String paramString) {
/* 1815 */     int i = this.temp[this.cursor++];
/* 1816 */     if (has(4))
/* 1817 */       i = parsePastWhitespace(i); 
/* 1818 */     if (paramInt != i) {
/* 1819 */       throw error(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mark(int paramInt) {
/* 1827 */     this.temp[this.patternLength] = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peek() {
/* 1834 */     int i = this.temp[this.cursor];
/* 1835 */     if (has(4))
/* 1836 */       i = peekPastWhitespace(i); 
/* 1837 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int read() {
/* 1844 */     int i = this.temp[this.cursor++];
/* 1845 */     if (has(4))
/* 1846 */       i = parsePastWhitespace(i); 
/* 1847 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readEscaped() {
/* 1855 */     return this.temp[this.cursor++];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int next() {
/* 1863 */     int i = this.temp[++this.cursor];
/* 1864 */     if (has(4))
/* 1865 */       i = peekPastWhitespace(i); 
/* 1866 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextEscaped() {
/* 1874 */     return this.temp[++this.cursor];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekPastWhitespace(int paramInt) {
/* 1882 */     while (ASCII.isSpace(paramInt) || paramInt == 35) {
/* 1883 */       while (ASCII.isSpace(paramInt))
/* 1884 */         paramInt = this.temp[++this.cursor]; 
/* 1885 */       if (paramInt == 35) {
/* 1886 */         paramInt = peekPastLine();
/*      */       }
/*      */     } 
/* 1889 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parsePastWhitespace(int paramInt) {
/* 1896 */     while (ASCII.isSpace(paramInt) || paramInt == 35) {
/* 1897 */       while (ASCII.isSpace(paramInt))
/* 1898 */         paramInt = this.temp[this.cursor++]; 
/* 1899 */       if (paramInt == 35)
/* 1900 */         paramInt = parsePastLine(); 
/*      */     } 
/* 1902 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parsePastLine() {
/* 1909 */     int i = this.temp[this.cursor++];
/* 1910 */     while (i != 0 && !isLineSeparator(i))
/* 1911 */       i = this.temp[this.cursor++]; 
/* 1912 */     if (i == 0 && this.cursor > this.patternLength) {
/* 1913 */       this.cursor = this.patternLength;
/* 1914 */       i = this.temp[this.cursor++];
/*      */     } 
/* 1916 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekPastLine() {
/* 1923 */     int i = this.temp[++this.cursor];
/* 1924 */     while (i != 0 && !isLineSeparator(i))
/* 1925 */       i = this.temp[++this.cursor]; 
/* 1926 */     if (i == 0 && this.cursor > this.patternLength) {
/* 1927 */       this.cursor = this.patternLength;
/* 1928 */       i = this.temp[this.cursor];
/*      */     } 
/* 1930 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLineSeparator(int paramInt) {
/* 1937 */     if (has(1)) {
/* 1938 */       return (paramInt == 10);
/*      */     }
/* 1940 */     return (paramInt == 10 || paramInt == 13 || (paramInt | 0x1) == 8233 || paramInt == 133);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skip() {
/* 1951 */     int i = this.cursor;
/* 1952 */     int j = this.temp[i + 1];
/* 1953 */     this.cursor = i + 2;
/* 1954 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unread() {
/* 1961 */     this.cursor--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PatternSyntaxException error(String paramString) {
/* 1969 */     return new PatternSyntaxException(paramString, this.normalizedPattern, this.cursor - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findSupplementary(int paramInt1, int paramInt2) {
/* 1977 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1978 */       if (isSupplementary(this.temp[i]))
/* 1979 */         return true; 
/*      */     } 
/* 1981 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean isSupplementary(int paramInt) {
/* 1989 */     return (paramInt >= 65536 || 
/* 1990 */       Character.isSurrogate((char)paramInt));
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
/*      */   private Node expr(Node paramNode) {
/* 2004 */     Node node1 = null;
/* 2005 */     Node node2 = null;
/* 2006 */     Node node3 = null;
/* 2007 */     BranchConn branchConn = null;
/*      */     
/*      */     while (true) {
/* 2010 */       Node node4 = sequence(paramNode);
/* 2011 */       Node node5 = this.root;
/* 2012 */       if (node1 == null) {
/* 2013 */         node1 = node4;
/* 2014 */         node2 = node5;
/*      */       } else {
/*      */         
/* 2017 */         if (branchConn == null) {
/* 2018 */           branchConn = new BranchConn();
/* 2019 */           branchConn.next = paramNode;
/*      */         } 
/* 2021 */         if (node4 == paramNode) {
/*      */ 
/*      */ 
/*      */           
/* 2025 */           node4 = null;
/*      */         } else {
/*      */           
/* 2028 */           node5.next = branchConn;
/*      */         } 
/* 2030 */         if (node1 == node3) {
/* 2031 */           node3.add(node4);
/*      */         } else {
/* 2033 */           if (node1 == paramNode) {
/* 2034 */             node1 = null;
/*      */           }
/*      */           else {
/*      */             
/* 2038 */             node2.next = branchConn;
/*      */           } 
/* 2040 */           node1 = node3 = new Branch(node1, node4, branchConn);
/*      */         } 
/*      */       } 
/* 2043 */       if (peek() != 124) {
/* 2044 */         return node1;
/*      */       }
/* 2046 */       next();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node sequence(Node paramNode) {
/* 2055 */     Node node1 = null;
/* 2056 */     Node node2 = null;
/* 2057 */     Node node3 = null;
/*      */     
/*      */     while (true) {
/* 2060 */       int i = peek();
/* 2061 */       switch (i) {
/*      */ 
/*      */         
/*      */         case 40:
/* 2065 */           node3 = group0();
/*      */           
/* 2067 */           if (node3 == null)
/*      */             continue; 
/* 2069 */           if (node1 == null) {
/* 2070 */             node1 = node3;
/*      */           } else {
/* 2072 */             node2.next = node3;
/*      */           } 
/* 2074 */           node2 = this.root;
/*      */           continue;
/*      */         case 91:
/* 2077 */           node3 = clazz(true);
/*      */           break;
/*      */         case 92:
/* 2080 */           i = nextEscaped();
/* 2081 */           if (i == 112 || i == 80) {
/* 2082 */             boolean bool1 = true;
/* 2083 */             boolean bool2 = (i == 80) ? true : false;
/* 2084 */             i = next();
/* 2085 */             if (i != 123) {
/* 2086 */               unread();
/*      */             } else {
/* 2088 */               bool1 = false;
/*      */             } 
/* 2090 */             node3 = family(bool1, bool2); break;
/*      */           } 
/* 2092 */           unread();
/* 2093 */           node3 = atom();
/*      */           break;
/*      */         
/*      */         case 94:
/* 2097 */           next();
/* 2098 */           if (has(8)) {
/* 2099 */             if (has(1)) {
/* 2100 */               node3 = new UnixCaret(); break;
/*      */             } 
/* 2102 */             node3 = new Caret(); break;
/*      */           } 
/* 2104 */           node3 = new Begin();
/*      */           break;
/*      */         
/*      */         case 36:
/* 2108 */           next();
/* 2109 */           if (has(1)) {
/* 2110 */             node3 = new UnixDollar(has(8)); break;
/*      */           } 
/* 2112 */           node3 = new Dollar(has(8));
/*      */           break;
/*      */         case 46:
/* 2115 */           next();
/* 2116 */           if (has(32)) {
/* 2117 */             node3 = new All(); break;
/*      */           } 
/* 2119 */           if (has(1)) {
/* 2120 */             node3 = new UnixDot(); break;
/*      */           } 
/* 2122 */           node3 = new Dot();
/*      */           break;
/*      */         
/*      */         case 41:
/*      */         case 124:
/*      */           break;
/*      */         
/*      */         case 93:
/*      */         case 125:
/* 2131 */           node3 = atom();
/*      */           break;
/*      */         case 42:
/*      */         case 43:
/*      */         case 63:
/* 2136 */           next();
/* 2137 */           throw error("Dangling meta character '" + (char)i + "'");
/*      */         case 0:
/* 2139 */           if (this.cursor >= this.patternLength) {
/*      */             break;
/*      */           }
/*      */         
/*      */         default:
/* 2144 */           node3 = atom();
/*      */           break;
/*      */       } 
/*      */       
/* 2148 */       node3 = closure(node3);
/*      */       
/* 2150 */       if (node1 == null) {
/* 2151 */         node1 = node2 = node3; continue;
/*      */       } 
/* 2153 */       node2.next = node3;
/* 2154 */       node2 = node3;
/*      */     } 
/*      */     
/* 2157 */     if (node1 == null) {
/* 2158 */       return paramNode;
/*      */     }
/* 2160 */     node2.next = paramNode;
/* 2161 */     this.root = node2;
/* 2162 */     return node1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node atom() {
/* 2170 */     byte b = 0;
/* 2171 */     int i = -1;
/* 2172 */     boolean bool = false;
/* 2173 */     int j = peek();
/*      */     while (true) {
/* 2175 */       switch (j) {
/*      */         case 42:
/*      */         case 43:
/*      */         case 63:
/*      */         case 123:
/* 2180 */           if (b > 1) {
/* 2181 */             this.cursor = i;
/* 2182 */             b--;
/*      */           } 
/*      */           break;
/*      */         case 36:
/*      */         case 40:
/*      */         case 41:
/*      */         case 46:
/*      */         case 91:
/*      */         case 94:
/*      */         case 124:
/*      */           break;
/*      */         case 92:
/* 2194 */           j = nextEscaped();
/* 2195 */           if (j == 112 || j == 80) {
/* 2196 */             if (b > 0) {
/* 2197 */               unread();
/*      */               break;
/*      */             } 
/* 2200 */             boolean bool1 = (j == 80) ? true : false;
/* 2201 */             boolean bool2 = true;
/* 2202 */             j = next();
/* 2203 */             if (j != 123) {
/* 2204 */               unread();
/*      */             } else {
/* 2206 */               bool2 = false;
/* 2207 */             }  return family(bool2, bool1);
/*      */           } 
/*      */           
/* 2210 */           unread();
/* 2211 */           i = this.cursor;
/* 2212 */           j = escape(false, (b == 0), false);
/* 2213 */           if (j >= 0) {
/* 2214 */             append(j, b);
/* 2215 */             b++;
/* 2216 */             if (isSupplementary(j)) {
/* 2217 */               bool = true;
/*      */             }
/* 2219 */             j = peek(); continue;
/*      */           } 
/* 2221 */           if (b == 0) {
/* 2222 */             return this.root;
/*      */           }
/*      */           
/* 2225 */           this.cursor = i;
/*      */           break;
/*      */         case 0:
/* 2228 */           if (this.cursor >= this.patternLength) {
/*      */             break;
/*      */           }
/*      */           break;
/*      */       } 
/* 2233 */       i = this.cursor;
/* 2234 */       append(j, b);
/* 2235 */       b++;
/* 2236 */       if (isSupplementary(j)) {
/* 2237 */         bool = true;
/*      */       }
/* 2239 */       j = next();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2244 */     if (b == 1) {
/* 2245 */       return newSingle(this.buffer[0]);
/*      */     }
/* 2247 */     return newSlice(this.buffer, b, bool);
/*      */   }
/*      */ 
/*      */   
/*      */   private void append(int paramInt1, int paramInt2) {
/* 2252 */     if (paramInt2 >= this.buffer.length) {
/* 2253 */       int[] arrayOfInt = new int[paramInt2 + paramInt2];
/* 2254 */       System.arraycopy(this.buffer, 0, arrayOfInt, 0, paramInt2);
/* 2255 */       this.buffer = arrayOfInt;
/*      */     } 
/* 2257 */     this.buffer[paramInt2] = paramInt1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node ref(int paramInt) {
/* 2267 */     boolean bool = false;
/* 2268 */     while (!bool) {
/* 2269 */       int j, i = peek();
/* 2270 */       switch (i) {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/* 2281 */           j = paramInt * 10 + i - 48;
/*      */ 
/*      */           
/* 2284 */           if (this.capturingGroupCount - 1 < j) {
/* 2285 */             bool = true;
/*      */             continue;
/*      */           } 
/* 2288 */           paramInt = j;
/* 2289 */           read();
/*      */           continue;
/*      */       } 
/* 2292 */       bool = true;
/*      */     } 
/*      */ 
/*      */     
/* 2296 */     if (has(2)) {
/* 2297 */       return new CIBackRef(paramInt, has(64));
/*      */     }
/* 2299 */     return new BackRef(paramInt);
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
/*      */   private int escape(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 2311 */     int i = skip();
/* 2312 */     switch (i) {
/*      */       case 48:
/* 2314 */         return o();
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 2324 */         if (!paramBoolean1)
/* 2325 */         { if (paramBoolean2) {
/* 2326 */             this.root = ref(i - 48);
/*      */           }
/* 2328 */           return -1; } 
/*      */       case 65:
/* 2330 */         if (!paramBoolean1)
/* 2331 */         { if (paramBoolean2) this.root = new Begin(); 
/* 2332 */           return -1; } 
/*      */       case 66:
/* 2334 */         if (!paramBoolean1) {
/* 2335 */           if (paramBoolean2) this.root = new Bound(Bound.NONE, has(256)); 
/* 2336 */           return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 67:
/* 2485 */         throw error("Illegal/unsupported escape sequence");case 68: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.DIGIT)).complement() : (new Ctype(1024)).complement();  return -1;case 69: case 70: throw error("Illegal/unsupported escape sequence");case 71: if (!paramBoolean1) { if (paramBoolean2) this.root = new LastMatch();  return -1; } case 72: if (paramBoolean2) this.root = (new HorizWS()).complement();  return -1;case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: case 81: throw error("Illegal/unsupported escape sequence");case 82: if (!paramBoolean1) { if (paramBoolean2) this.root = new LineEnding();  return -1; } case 83: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.WHITE_SPACE)).complement() : (new Ctype(2048)).complement();  return -1;case 84: case 85: throw error("Illegal/unsupported escape sequence");case 86: if (paramBoolean2) this.root = (new VertWS()).complement();  return -1;case 87: if (paramBoolean2) this.root = has(256) ? (new Utype(UnicodeProp.WORD)).complement() : (new Ctype(67328)).complement();  return -1;case 88: case 89: throw error("Illegal/unsupported escape sequence");case 90: if (!paramBoolean1) { if (paramBoolean2) if (has(1)) { this.root = new UnixDollar(false); } else { this.root = new Dollar(false); }   return -1; } case 97: return 7;case 98: if (!paramBoolean1) { if (paramBoolean2) this.root = new Bound(Bound.BOTH, has(256));  return -1; } case 99: return c();case 100: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.DIGIT) : new Ctype(1024);  return -1;case 101: return 27;case 102: return 12;case 103: throw error("Illegal/unsupported escape sequence");case 104: if (paramBoolean2) this.root = new HorizWS();  return -1;case 105: case 106: throw error("Illegal/unsupported escape sequence");case 107: if (!paramBoolean1) { if (read() != 60) throw error("\\k is not followed by '<' for named capturing group");  String str = groupname(read()); if (!namedGroups().containsKey(str)) throw error("(named capturing group <" + str + "> does not exit");  if (paramBoolean2) if (has(2)) { this.root = new CIBackRef(((Integer)namedGroups().get(str)).intValue(), has(64)); } else { this.root = new BackRef(((Integer)namedGroups().get(str)).intValue()); }   return -1; } case 108: case 109: throw error("Illegal/unsupported escape sequence");case 110: return 10;case 111: case 112: case 113: throw error("Illegal/unsupported escape sequence");case 114: return 13;case 115: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.WHITE_SPACE) : new Ctype(2048);  return -1;case 116: return 9;case 117: return u();case 118: if (paramBoolean3) return 11;  if (paramBoolean2) this.root = new VertWS();  return -1;case 119: if (paramBoolean2) this.root = has(256) ? new Utype(UnicodeProp.WORD) : new Ctype(67328);  return -1;case 120: return x();case 121: throw error("Illegal/unsupported escape sequence");
/*      */       case 122:
/*      */         if (!paramBoolean1) {
/*      */           if (paramBoolean2)
/*      */             this.root = new End(); 
/*      */           return -1;
/*      */         } 
/*      */     } 
/*      */     return i;
/*      */   }
/*      */   private CharProperty clazz(boolean paramBoolean) {
/* 2496 */     CharProperty charProperty1 = null;
/* 2497 */     CharProperty charProperty2 = null;
/* 2498 */     BitClass bitClass = new BitClass();
/* 2499 */     boolean bool1 = true;
/* 2500 */     boolean bool2 = true;
/* 2501 */     int i = next();
/*      */     while (true) {
/* 2503 */       switch (i) {
/*      */         
/*      */         case 94:
/* 2506 */           if (!bool2 || 
/* 2507 */             this.temp[this.cursor - 1] != 91)
/*      */             break; 
/* 2509 */           i = next();
/* 2510 */           bool1 = !bool1 ? true : false;
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 91:
/* 2517 */           bool2 = false;
/* 2518 */           charProperty2 = clazz(true);
/* 2519 */           if (charProperty1 == null) {
/* 2520 */             charProperty1 = charProperty2;
/*      */           } else {
/* 2522 */             charProperty1 = union(charProperty1, charProperty2);
/* 2523 */           }  i = peek();
/*      */           continue;
/*      */         case 38:
/* 2526 */           bool2 = false;
/* 2527 */           i = next();
/* 2528 */           if (i == 38) {
/* 2529 */             i = next();
/* 2530 */             CharProperty charProperty = null;
/* 2531 */             while (i != 93 && i != 38) {
/* 2532 */               if (i == 91)
/* 2533 */               { if (charProperty == null) {
/* 2534 */                   charProperty = clazz(true);
/*      */                 } else {
/* 2536 */                   charProperty = union(charProperty, clazz(true));
/*      */                 }  }
/* 2538 */               else { unread();
/* 2539 */                 charProperty = clazz(false); }
/*      */               
/* 2541 */               i = peek();
/*      */             } 
/* 2543 */             if (charProperty != null)
/* 2544 */               charProperty2 = charProperty; 
/* 2545 */             if (charProperty1 == null) {
/* 2546 */               if (charProperty == null) {
/* 2547 */                 throw error("Bad class syntax");
/*      */               }
/* 2549 */               charProperty1 = charProperty; continue;
/*      */             } 
/* 2551 */             charProperty1 = intersection(charProperty1, charProperty2);
/*      */             
/*      */             continue;
/*      */           } 
/* 2555 */           unread();
/*      */           break;
/*      */ 
/*      */         
/*      */         case 0:
/* 2560 */           bool2 = false;
/* 2561 */           if (this.cursor >= this.patternLength)
/* 2562 */             throw error("Unclosed character class"); 
/*      */           break;
/*      */         case 93:
/* 2565 */           bool2 = false;
/* 2566 */           if (charProperty1 != null) {
/* 2567 */             if (paramBoolean)
/* 2568 */               next(); 
/* 2569 */             return charProperty1;
/*      */           } 
/*      */           break;
/*      */         default:
/* 2573 */           bool2 = false;
/*      */           break;
/*      */       } 
/* 2576 */       charProperty2 = range(bitClass);
/* 2577 */       if (bool1) {
/* 2578 */         if (charProperty1 == null) {
/* 2579 */           charProperty1 = charProperty2;
/*      */         }
/* 2581 */         else if (charProperty1 != charProperty2) {
/* 2582 */           charProperty1 = union(charProperty1, charProperty2);
/*      */         }
/*      */       
/* 2585 */       } else if (charProperty1 == null) {
/* 2586 */         charProperty1 = charProperty2.complement();
/*      */       }
/* 2588 */       else if (charProperty1 != charProperty2) {
/* 2589 */         charProperty1 = setDifference(charProperty1, charProperty2);
/*      */       } 
/*      */       
/* 2592 */       i = peek();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty bitsOrSingle(BitClass paramBitClass, int paramInt) {
/* 2615 */     if (paramInt < 256 && (
/* 2616 */       !has(2) || !has(64) || (paramInt != 255 && paramInt != 181 && paramInt != 73 && paramInt != 105 && paramInt != 83 && paramInt != 115 && paramInt != 75 && paramInt != 107 && paramInt != 197 && paramInt != 229)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2622 */       return paramBitClass.add(paramInt, flags()); } 
/* 2623 */     return newSingle(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty range(BitClass paramBitClass) {
/* 2631 */     int i = peek();
/* 2632 */     if (i == 92) {
/* 2633 */       i = nextEscaped();
/* 2634 */       if (i == 112 || i == 80) {
/* 2635 */         boolean bool1 = (i == 80) ? true : false;
/* 2636 */         boolean bool2 = true;
/*      */         
/* 2638 */         i = next();
/* 2639 */         if (i != 123) {
/* 2640 */           unread();
/*      */         } else {
/* 2642 */           bool2 = false;
/* 2643 */         }  return family(bool2, bool1);
/*      */       } 
/* 2645 */       boolean bool = (this.temp[this.cursor + 1] == 45) ? true : false;
/* 2646 */       unread();
/* 2647 */       i = escape(true, true, bool);
/* 2648 */       if (i == -1) {
/* 2649 */         return (CharProperty)this.root;
/*      */       }
/*      */     } else {
/* 2652 */       next();
/*      */     } 
/* 2654 */     if (i >= 0) {
/* 2655 */       if (peek() == 45) {
/* 2656 */         int j = this.temp[this.cursor + 1];
/* 2657 */         if (j == 91) {
/* 2658 */           return bitsOrSingle(paramBitClass, i);
/*      */         }
/* 2660 */         if (j != 93) {
/* 2661 */           next();
/* 2662 */           int k = peek();
/* 2663 */           if (k == 92) {
/* 2664 */             k = escape(true, false, true);
/*      */           } else {
/* 2666 */             next();
/*      */           } 
/* 2668 */           if (k < i) {
/* 2669 */             throw error("Illegal character range");
/*      */           }
/* 2671 */           if (has(2)) {
/* 2672 */             return caseInsensitiveRangeFor(i, k);
/*      */           }
/* 2674 */           return rangeFor(i, k);
/*      */         } 
/*      */       } 
/* 2677 */       return bitsOrSingle(paramBitClass, i);
/*      */     } 
/* 2679 */     throw error("Unexpected character '" + (char)i + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty family(boolean paramBoolean1, boolean paramBoolean2) {
/*      */     String str;
/* 2688 */     next();
/*      */     
/* 2690 */     CharProperty charProperty = null;
/*      */     
/* 2692 */     if (paramBoolean1) {
/* 2693 */       int j = this.temp[this.cursor];
/* 2694 */       if (!Character.isSupplementaryCodePoint(j)) {
/* 2695 */         str = String.valueOf((char)j);
/*      */       } else {
/* 2697 */         str = new String(this.temp, this.cursor, 1);
/*      */       } 
/* 2699 */       read();
/*      */     } else {
/* 2701 */       int j = this.cursor;
/* 2702 */       mark(125);
/* 2703 */       while (read() != 125);
/*      */       
/* 2705 */       mark(0);
/* 2706 */       int k = this.cursor;
/* 2707 */       if (k > this.patternLength)
/* 2708 */         throw error("Unclosed character family"); 
/* 2709 */       if (j + 1 >= k)
/* 2710 */         throw error("Empty character family"); 
/* 2711 */       str = new String(this.temp, j, k - j - 1);
/*      */     } 
/*      */     
/* 2714 */     int i = str.indexOf('=');
/* 2715 */     if (i != -1) {
/*      */       
/* 2717 */       String str1 = str.substring(i + 1);
/* 2718 */       str = str.substring(0, i).toLowerCase(Locale.ENGLISH);
/* 2719 */       if ("sc".equals(str) || "script".equals(str)) {
/* 2720 */         charProperty = unicodeScriptPropertyFor(str1);
/* 2721 */       } else if ("blk".equals(str) || "block".equals(str)) {
/* 2722 */         charProperty = unicodeBlockPropertyFor(str1);
/* 2723 */       } else if ("gc".equals(str) || "general_category".equals(str)) {
/* 2724 */         charProperty = charPropertyNodeFor(str1);
/*      */       } else {
/* 2726 */         throw error("Unknown Unicode property {name=<" + str + ">, value=<" + str1 + ">}");
/*      */       }
/*      */     
/*      */     }
/* 2730 */     else if (str.startsWith("In")) {
/*      */       
/* 2732 */       charProperty = unicodeBlockPropertyFor(str.substring(2));
/* 2733 */     } else if (str.startsWith("Is")) {
/*      */       
/* 2735 */       str = str.substring(2);
/* 2736 */       UnicodeProp unicodeProp = UnicodeProp.forName(str);
/* 2737 */       if (unicodeProp != null)
/* 2738 */         charProperty = new Utype(unicodeProp); 
/* 2739 */       if (charProperty == null)
/* 2740 */         charProperty = CharPropertyNames.charPropertyFor(str); 
/* 2741 */       if (charProperty == null)
/* 2742 */         charProperty = unicodeScriptPropertyFor(str); 
/*      */     } else {
/* 2744 */       if (has(256)) {
/* 2745 */         UnicodeProp unicodeProp = UnicodeProp.forPOSIXName(str);
/* 2746 */         if (unicodeProp != null)
/* 2747 */           charProperty = new Utype(unicodeProp); 
/*      */       } 
/* 2749 */       if (charProperty == null) {
/* 2750 */         charProperty = charPropertyNodeFor(str);
/*      */       }
/*      */     } 
/* 2753 */     if (paramBoolean2) {
/* 2754 */       if (charProperty instanceof Category || charProperty instanceof Block)
/* 2755 */         this.hasSupplementary = true; 
/* 2756 */       charProperty = charProperty.complement();
/*      */     } 
/* 2758 */     return charProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty unicodeScriptPropertyFor(String paramString) {
/*      */     Character.UnicodeScript unicodeScript;
/*      */     try {
/* 2769 */       unicodeScript = Character.UnicodeScript.forName(paramString);
/* 2770 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 2771 */       throw error("Unknown character script name {" + paramString + "}");
/*      */     } 
/* 2773 */     return new Script(unicodeScript);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty unicodeBlockPropertyFor(String paramString) {
/*      */     Character.UnicodeBlock unicodeBlock;
/*      */     try {
/* 2782 */       unicodeBlock = Character.UnicodeBlock.forName(paramString);
/* 2783 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 2784 */       throw error("Unknown character block name {" + paramString + "}");
/*      */     } 
/* 2786 */     return new Block(unicodeBlock);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty charPropertyNodeFor(String paramString) {
/* 2793 */     CharProperty charProperty = CharPropertyNames.charPropertyFor(paramString);
/* 2794 */     if (charProperty == null)
/* 2795 */       throw error("Unknown character property name {" + paramString + "}"); 
/* 2796 */     return charProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String groupname(int paramInt) {
/* 2804 */     StringBuilder stringBuilder = new StringBuilder();
/* 2805 */     stringBuilder.append(Character.toChars(paramInt));
/* 2806 */     while (ASCII.isLower(paramInt = read()) || ASCII.isUpper(paramInt) || 
/* 2807 */       ASCII.isDigit(paramInt)) {
/* 2808 */       stringBuilder.append(Character.toChars(paramInt));
/*      */     }
/* 2810 */     if (stringBuilder.length() == 0)
/* 2811 */       throw error("named capturing group has 0 length name"); 
/* 2812 */     if (paramInt != 62)
/* 2813 */       throw error("named capturing group is missing trailing '>'"); 
/* 2814 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node group0() {
/* 2823 */     boolean bool = false;
/* 2824 */     Node node1 = null;
/* 2825 */     Node node2 = null;
/* 2826 */     int i = this.flags;
/* 2827 */     this.root = null;
/* 2828 */     int j = next();
/* 2829 */     if (j == 63) {
/* 2830 */       int k; TreeInfo treeInfo; boolean bool1; j = skip();
/* 2831 */       switch (j) {
/*      */         case 58:
/* 2833 */           node1 = createGroup(true);
/* 2834 */           node2 = this.root;
/* 2835 */           node1.next = expr(node2);
/*      */           break;
/*      */         case 33:
/*      */         case 61:
/* 2839 */           node1 = createGroup(true);
/* 2840 */           node2 = this.root;
/* 2841 */           node1.next = expr(node2);
/* 2842 */           if (j == 61) {
/* 2843 */             node1 = node2 = new Pos(node1); break;
/*      */           } 
/* 2845 */           node1 = node2 = new Neg(node1);
/*      */           break;
/*      */         
/*      */         case 62:
/* 2849 */           node1 = createGroup(true);
/* 2850 */           node2 = this.root;
/* 2851 */           node1.next = expr(node2);
/* 2852 */           node1 = node2 = new Ques(node1, 3);
/*      */           break;
/*      */         case 60:
/* 2855 */           j = read();
/* 2856 */           if (ASCII.isLower(j) || ASCII.isUpper(j)) {
/*      */             
/* 2858 */             String str = groupname(j);
/* 2859 */             if (namedGroups().containsKey(str)) {
/* 2860 */               throw error("Named capturing group <" + str + "> is already defined");
/*      */             }
/* 2862 */             bool = true;
/* 2863 */             node1 = createGroup(false);
/* 2864 */             node2 = this.root;
/* 2865 */             namedGroups().put(str, Integer.valueOf(this.capturingGroupCount - 1));
/* 2866 */             node1.next = expr(node2);
/*      */             break;
/*      */           } 
/* 2869 */           k = this.cursor;
/* 2870 */           node1 = createGroup(true);
/* 2871 */           node2 = this.root;
/* 2872 */           node1.next = expr(node2);
/* 2873 */           node2.next = lookbehindEnd;
/* 2874 */           treeInfo = new TreeInfo();
/* 2875 */           node1.study(treeInfo);
/* 2876 */           if (!treeInfo.maxValid) {
/* 2877 */             throw error("Look-behind group does not have an obvious maximum length");
/*      */           }
/*      */           
/* 2880 */           bool1 = findSupplementary(k, this.patternLength);
/* 2881 */           if (j == 61) {
/* 2882 */             node1 = node2 = bool1 ? new BehindS(node1, treeInfo.maxLength, treeInfo.minLength) : new Behind(node1, treeInfo.maxLength, treeInfo.minLength);
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 2887 */           if (j == 33) {
/* 2888 */             node1 = node2 = bool1 ? new NotBehindS(node1, treeInfo.maxLength, treeInfo.minLength) : new NotBehind(node1, treeInfo.maxLength, treeInfo.minLength);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 2894 */           throw error("Unknown look-behind group");
/*      */ 
/*      */         
/*      */         case 36:
/*      */         case 64:
/* 2899 */           throw error("Unknown group type");
/*      */         default:
/* 2901 */           unread();
/* 2902 */           addFlag();
/* 2903 */           j = read();
/* 2904 */           if (j == 41) {
/* 2905 */             return null;
/*      */           }
/* 2907 */           if (j != 58) {
/* 2908 */             throw error("Unknown inline modifier");
/*      */           }
/* 2910 */           node1 = createGroup(true);
/* 2911 */           node2 = this.root;
/* 2912 */           node1.next = expr(node2);
/*      */           break;
/*      */       } 
/*      */     } else {
/* 2916 */       bool = true;
/* 2917 */       node1 = createGroup(false);
/* 2918 */       node2 = this.root;
/* 2919 */       node1.next = expr(node2);
/*      */     } 
/*      */     
/* 2922 */     accept(41, "Unclosed group");
/* 2923 */     this.flags = i;
/*      */ 
/*      */     
/* 2926 */     Node node3 = closure(node1);
/* 2927 */     if (node3 == node1) {
/* 2928 */       this.root = node2;
/* 2929 */       return node3;
/*      */     } 
/* 2931 */     if (node1 == node2) {
/* 2932 */       this.root = node3;
/* 2933 */       return node3;
/*      */     } 
/*      */     
/* 2936 */     if (node3 instanceof Ques) {
/* 2937 */       Ques ques = (Ques)node3;
/* 2938 */       if (ques.type == 2) {
/* 2939 */         this.root = node3;
/* 2940 */         return node3;
/*      */       } 
/* 2942 */       node2.next = new BranchConn();
/* 2943 */       node2 = node2.next;
/* 2944 */       if (ques.type == 0) {
/* 2945 */         node1 = new Branch(node1, null, node2);
/*      */       } else {
/* 2947 */         node1 = new Branch(null, node1, node2);
/*      */       } 
/* 2949 */       this.root = node2;
/* 2950 */       return node1;
/* 2951 */     }  if (node3 instanceof Curly) {
/* 2952 */       Loop loop; Curly curly = (Curly)node3;
/* 2953 */       if (curly.type == 2) {
/* 2954 */         this.root = node3;
/* 2955 */         return node3;
/*      */       } 
/*      */       
/* 2958 */       TreeInfo treeInfo = new TreeInfo();
/* 2959 */       if (node1.study(treeInfo)) {
/* 2960 */         GroupTail groupTail = (GroupTail)node2;
/* 2961 */         node1 = this.root = new GroupCurly(node1.next, curly.cmin, curly.cmax, curly.type, ((GroupTail)node2).localIndex, ((GroupTail)node2).groupIndex, bool);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2966 */         return node1;
/*      */       } 
/* 2968 */       int k = ((GroupHead)node1).localIndex;
/*      */       
/* 2970 */       if (curly.type == 0) {
/* 2971 */         loop = new Loop(this.localCount, k);
/*      */       } else {
/* 2973 */         loop = new LazyLoop(this.localCount, k);
/* 2974 */       }  Prolog prolog = new Prolog(loop);
/* 2975 */       this.localCount++;
/* 2976 */       loop.cmin = curly.cmin;
/* 2977 */       loop.cmax = curly.cmax;
/* 2978 */       loop.body = node1;
/* 2979 */       node2.next = loop;
/* 2980 */       this.root = loop;
/* 2981 */       return prolog;
/*      */     } 
/*      */     
/* 2984 */     throw error("Internal logic error");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node createGroup(boolean paramBoolean) {
/* 2993 */     int i = this.localCount++;
/* 2994 */     int j = 0;
/* 2995 */     if (!paramBoolean)
/* 2996 */       j = this.capturingGroupCount++; 
/* 2997 */     GroupHead groupHead = new GroupHead(i);
/* 2998 */     this.root = new GroupTail(i, j);
/* 2999 */     if (!paramBoolean && j < 10)
/* 3000 */       this.groupNodes[j] = groupHead; 
/* 3001 */     return groupHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addFlag() {
/* 3009 */     int i = peek();
/*      */     while (true) {
/* 3011 */       switch (i) {
/*      */         case 105:
/* 3013 */           this.flags |= 0x2;
/*      */           break;
/*      */         case 109:
/* 3016 */           this.flags |= 0x8;
/*      */           break;
/*      */         case 115:
/* 3019 */           this.flags |= 0x20;
/*      */           break;
/*      */         case 100:
/* 3022 */           this.flags |= 0x1;
/*      */           break;
/*      */         case 117:
/* 3025 */           this.flags |= 0x40;
/*      */           break;
/*      */         case 99:
/* 3028 */           this.flags |= 0x80;
/*      */           break;
/*      */         case 120:
/* 3031 */           this.flags |= 0x4;
/*      */           break;
/*      */         case 85:
/* 3034 */           this.flags |= 0x140;
/*      */           break;
/*      */         case 45:
/* 3037 */           i = next();
/* 3038 */           subFlag();
/*      */         default:
/*      */           return;
/*      */       } 
/* 3042 */       i = next();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void subFlag() {
/* 3052 */     int i = peek();
/*      */     while (true) {
/* 3054 */       switch (i) {
/*      */         case 105:
/* 3056 */           this.flags &= 0xFFFFFFFD;
/*      */           break;
/*      */         case 109:
/* 3059 */           this.flags &= 0xFFFFFFF7;
/*      */           break;
/*      */         case 115:
/* 3062 */           this.flags &= 0xFFFFFFDF;
/*      */           break;
/*      */         case 100:
/* 3065 */           this.flags &= 0xFFFFFFFE;
/*      */           break;
/*      */         case 117:
/* 3068 */           this.flags &= 0xFFFFFFBF;
/*      */           break;
/*      */         case 99:
/* 3071 */           this.flags &= 0xFFFFFF7F;
/*      */           break;
/*      */         case 120:
/* 3074 */           this.flags &= 0xFFFFFFFB;
/*      */           break;
/*      */         case 85:
/* 3077 */           this.flags &= 0xFFFFFEBF;
/*      */         default:
/*      */           return;
/*      */       } 
/* 3081 */       i = next();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node closure(Node paramNode) {
/* 3102 */     int i = peek();
/* 3103 */     switch (i) {
/*      */       case 63:
/* 3105 */         i = next();
/* 3106 */         if (i == 63) {
/* 3107 */           next();
/* 3108 */           return new Ques(paramNode, 1);
/* 3109 */         }  if (i == 43) {
/* 3110 */           next();
/* 3111 */           return new Ques(paramNode, 2);
/*      */         } 
/* 3113 */         return new Ques(paramNode, 0);
/*      */       case 42:
/* 3115 */         i = next();
/* 3116 */         if (i == 63) {
/* 3117 */           next();
/* 3118 */           return new Curly(paramNode, 0, 2147483647, 1);
/* 3119 */         }  if (i == 43) {
/* 3120 */           next();
/* 3121 */           return new Curly(paramNode, 0, 2147483647, 2);
/*      */         } 
/* 3123 */         return new Curly(paramNode, 0, 2147483647, 0);
/*      */       case 43:
/* 3125 */         i = next();
/* 3126 */         if (i == 63) {
/* 3127 */           next();
/* 3128 */           return new Curly(paramNode, 1, 2147483647, 1);
/* 3129 */         }  if (i == 43) {
/* 3130 */           next();
/* 3131 */           return new Curly(paramNode, 1, 2147483647, 2);
/*      */         } 
/* 3133 */         return new Curly(paramNode, 1, 2147483647, 0);
/*      */       case 123:
/* 3135 */         i = this.temp[this.cursor + 1];
/* 3136 */         if (ASCII.isDigit(i)) {
/* 3137 */           Curly curly; skip();
/* 3138 */           int j = 0;
/*      */           do {
/* 3140 */             j = j * 10 + i - 48;
/* 3141 */           } while (ASCII.isDigit(i = read()));
/* 3142 */           int k = j;
/* 3143 */           if (i == 44) {
/* 3144 */             i = read();
/* 3145 */             k = Integer.MAX_VALUE;
/* 3146 */             if (i != 125) {
/* 3147 */               k = 0;
/* 3148 */               while (ASCII.isDigit(i)) {
/* 3149 */                 k = k * 10 + i - 48;
/* 3150 */                 i = read();
/*      */               } 
/*      */             } 
/*      */           } 
/* 3154 */           if (i != 125)
/* 3155 */             throw error("Unclosed counted closure"); 
/* 3156 */           if ((j | k | k - j) < 0) {
/* 3157 */             throw error("Illegal repetition range");
/*      */           }
/* 3159 */           i = peek();
/* 3160 */           if (i == 63) {
/* 3161 */             next();
/* 3162 */             curly = new Curly(paramNode, j, k, 1);
/* 3163 */           } else if (i == 43) {
/* 3164 */             next();
/* 3165 */             curly = new Curly(paramNode, j, k, 2);
/*      */           } else {
/* 3167 */             curly = new Curly(paramNode, j, k, 0);
/*      */           } 
/* 3169 */           return curly;
/*      */         } 
/* 3171 */         throw error("Illegal repetition");
/*      */     } 
/*      */     
/* 3174 */     return paramNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int c() {
/* 3182 */     if (this.cursor < this.patternLength) {
/* 3183 */       return read() ^ 0x40;
/*      */     }
/* 3185 */     throw error("Illegal control escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int o() {
/* 3192 */     int i = read();
/* 3193 */     if ((i - 48 | 55 - i) >= 0) {
/* 3194 */       int j = read();
/* 3195 */       if ((j - 48 | 55 - j) >= 0) {
/* 3196 */         int k = read();
/* 3197 */         if ((k - 48 | 55 - k) >= 0 && (i - 48 | 51 - i) >= 0) {
/* 3198 */           return (i - 48) * 64 + (j - 48) * 8 + k - 48;
/*      */         }
/* 3200 */         unread();
/* 3201 */         return (i - 48) * 8 + j - 48;
/*      */       } 
/* 3203 */       unread();
/* 3204 */       return i - 48;
/*      */     } 
/* 3206 */     throw error("Illegal octal escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int x() {
/* 3213 */     int i = read();
/* 3214 */     if (ASCII.isHexDigit(i)) {
/* 3215 */       int j = read();
/* 3216 */       if (ASCII.isHexDigit(j)) {
/* 3217 */         return ASCII.toDigit(i) * 16 + ASCII.toDigit(j);
/*      */       }
/* 3219 */     } else if (i == 123 && ASCII.isHexDigit(peek())) {
/* 3220 */       int j = 0;
/* 3221 */       while (ASCII.isHexDigit(i = read())) {
/* 3222 */         j = (j << 4) + ASCII.toDigit(i);
/* 3223 */         if (j > 1114111)
/* 3224 */           throw error("Hexadecimal codepoint is too big"); 
/*      */       } 
/* 3226 */       if (i != 125)
/* 3227 */         throw error("Unclosed hexadecimal escape sequence"); 
/* 3228 */       return j;
/*      */     } 
/* 3230 */     throw error("Illegal hexadecimal escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int cursor() {
/* 3237 */     return this.cursor;
/*      */   }
/*      */   
/*      */   private void setcursor(int paramInt) {
/* 3241 */     this.cursor = paramInt;
/*      */   }
/*      */   
/*      */   private int uxxxx() {
/* 3245 */     int i = 0;
/* 3246 */     for (byte b = 0; b < 4; b++) {
/* 3247 */       int j = read();
/* 3248 */       if (!ASCII.isHexDigit(j)) {
/* 3249 */         throw error("Illegal Unicode escape sequence");
/*      */       }
/* 3251 */       i = i * 16 + ASCII.toDigit(j);
/*      */     } 
/* 3253 */     return i;
/*      */   }
/*      */   
/*      */   private int u() {
/* 3257 */     int i = uxxxx();
/* 3258 */     if (Character.isHighSurrogate((char)i)) {
/* 3259 */       int j = cursor();
/* 3260 */       if (read() == 92 && read() == 117) {
/* 3261 */         int k = uxxxx();
/* 3262 */         if (Character.isLowSurrogate((char)k))
/* 3263 */           return Character.toCodePoint((char)i, (char)k); 
/*      */       } 
/* 3265 */       setcursor(j);
/*      */     } 
/* 3267 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int countChars(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 3277 */     if (paramInt2 == 1 && !Character.isHighSurrogate(paramCharSequence.charAt(paramInt1))) {
/* 3278 */       assert paramInt1 >= 0 && paramInt1 < paramCharSequence.length();
/* 3279 */       return 1;
/*      */     } 
/* 3281 */     int i = paramCharSequence.length();
/* 3282 */     int j = paramInt1;
/* 3283 */     if (paramInt2 >= 0) {
/* 3284 */       assert paramInt1 >= 0 && paramInt1 < i;
/* 3285 */       for (byte b1 = 0; j < i && b1 < paramInt2; b1++) {
/* 3286 */         if (Character.isHighSurrogate(paramCharSequence.charAt(j++)) && 
/* 3287 */           j < i && Character.isLowSurrogate(paramCharSequence.charAt(j))) {
/* 3288 */           j++;
/*      */         }
/*      */       } 
/*      */       
/* 3292 */       return j - paramInt1;
/*      */     } 
/*      */     
/* 3295 */     assert paramInt1 >= 0 && paramInt1 <= i;
/* 3296 */     if (paramInt1 == 0) {
/* 3297 */       return 0;
/*      */     }
/* 3299 */     int k = -paramInt2;
/* 3300 */     for (byte b = 0; j > 0 && b < k; b++) {
/* 3301 */       if (Character.isLowSurrogate(paramCharSequence.charAt(--j)) && 
/* 3302 */         j > 0 && Character.isHighSurrogate(paramCharSequence.charAt(j - 1))) {
/* 3303 */         j--;
/*      */       }
/*      */     } 
/*      */     
/* 3307 */     return paramInt1 - j;
/*      */   }
/*      */   
/*      */   private static final int countCodePoints(CharSequence paramCharSequence) {
/* 3311 */     int i = paramCharSequence.length();
/* 3312 */     byte b1 = 0;
/* 3313 */     for (byte b2 = 0; b2 < i; ) {
/* 3314 */       b1++;
/* 3315 */       if (Character.isHighSurrogate(paramCharSequence.charAt(b2++)) && 
/* 3316 */         b2 < i && Character.isLowSurrogate(paramCharSequence.charAt(b2))) {
/* 3317 */         b2++;
/*      */       }
/*      */     } 
/*      */     
/* 3321 */     return b1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class BitClass
/*      */     extends BmpCharProperty
/*      */   {
/*      */     final boolean[] bits;
/*      */     
/*      */     BitClass() {
/* 3331 */       this.bits = new boolean[256]; } private BitClass(boolean[] param1ArrayOfboolean) {
/* 3332 */       this.bits = param1ArrayOfboolean;
/*      */     } BitClass add(int param1Int1, int param1Int2) {
/* 3334 */       assert param1Int1 >= 0 && param1Int1 <= 255;
/* 3335 */       if ((param1Int2 & 0x2) != 0) {
/* 3336 */         if (ASCII.isAscii(param1Int1)) {
/* 3337 */           this.bits[ASCII.toUpper(param1Int1)] = true;
/* 3338 */           this.bits[ASCII.toLower(param1Int1)] = true;
/* 3339 */         } else if ((param1Int2 & 0x40) != 0) {
/* 3340 */           this.bits[Character.toLowerCase(param1Int1)] = true;
/* 3341 */           this.bits[Character.toUpperCase(param1Int1)] = true;
/*      */         } 
/*      */       }
/* 3344 */       this.bits[param1Int1] = true;
/* 3345 */       return this;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3348 */       return (param1Int < 256 && this.bits[param1Int]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty newSingle(int paramInt) {
/* 3356 */     if (has(2))
/*      */     {
/* 3358 */       if (has(64)) {
/* 3359 */         int j = Character.toUpperCase(paramInt);
/* 3360 */         int i = Character.toLowerCase(j);
/* 3361 */         if (j != i)
/* 3362 */           return new SingleU(i); 
/* 3363 */       } else if (ASCII.isAscii(paramInt)) {
/* 3364 */         int i = ASCII.toLower(paramInt);
/* 3365 */         int j = ASCII.toUpper(paramInt);
/* 3366 */         if (i != j)
/* 3367 */           return new SingleI(i, j); 
/*      */       } 
/*      */     }
/* 3370 */     if (isSupplementary(paramInt))
/* 3371 */       return new SingleS(paramInt); 
/* 3372 */     return new Single(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node newSlice(int[] paramArrayOfint, int paramInt, boolean paramBoolean) {
/* 3379 */     int[] arrayOfInt = new int[paramInt];
/* 3380 */     if (has(2)) {
/* 3381 */       if (has(64)) {
/* 3382 */         for (byte b2 = 0; b2 < paramInt; b2++) {
/* 3383 */           arrayOfInt[b2] = Character.toLowerCase(
/* 3384 */               Character.toUpperCase(paramArrayOfint[b2]));
/*      */         }
/* 3386 */         return paramBoolean ? new SliceUS(arrayOfInt) : new SliceU(arrayOfInt);
/*      */       } 
/* 3388 */       for (byte b1 = 0; b1 < paramInt; b1++) {
/* 3389 */         arrayOfInt[b1] = ASCII.toLower(paramArrayOfint[b1]);
/*      */       }
/* 3391 */       return paramBoolean ? new SliceIS(arrayOfInt) : new SliceI(arrayOfInt);
/*      */     } 
/* 3393 */     for (byte b = 0; b < paramInt; b++) {
/* 3394 */       arrayOfInt[b] = paramArrayOfint[b];
/*      */     }
/* 3396 */     return paramBoolean ? new SliceS(arrayOfInt) : new Slice(arrayOfInt);
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
/*      */   static class Node
/*      */   {
/* 3415 */     Node next = Pattern.accept;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3421 */       param1Matcher.last = param1Int;
/* 3422 */       param1Matcher.groups[0] = param1Matcher.first;
/* 3423 */       param1Matcher.groups[1] = param1Matcher.last;
/* 3424 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3430 */       if (this.next != null) {
/* 3431 */         return this.next.study(param1TreeInfo);
/*      */       }
/* 3433 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class LastNode
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3445 */       if (param1Matcher.acceptMode == 1 && param1Int != param1Matcher.to)
/* 3446 */         return false; 
/* 3447 */       param1Matcher.last = param1Int;
/* 3448 */       param1Matcher.groups[0] = param1Matcher.first;
/* 3449 */       param1Matcher.groups[1] = param1Matcher.last;
/* 3450 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Start
/*      */     extends Node
/*      */   {
/*      */     int minLength;
/*      */ 
/*      */     
/*      */     Start(Pattern.Node param1Node) {
/* 3463 */       this.next = param1Node;
/* 3464 */       Pattern.TreeInfo treeInfo = new Pattern.TreeInfo();
/* 3465 */       this.next.study(treeInfo);
/* 3466 */       this.minLength = treeInfo.minLength;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3469 */       if (param1Int > param1Matcher.to - this.minLength) {
/* 3470 */         param1Matcher.hitEnd = true;
/* 3471 */         return false;
/*      */       } 
/* 3473 */       int i = param1Matcher.to - this.minLength;
/* 3474 */       for (; param1Int <= i; param1Int++) {
/* 3475 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3476 */           param1Matcher.first = param1Int;
/* 3477 */           param1Matcher.groups[0] = param1Matcher.first;
/* 3478 */           param1Matcher.groups[1] = param1Matcher.last;
/* 3479 */           return true;
/*      */         } 
/*      */       } 
/* 3482 */       param1Matcher.hitEnd = true;
/* 3483 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3486 */       this.next.study(param1TreeInfo);
/* 3487 */       param1TreeInfo.maxValid = false;
/* 3488 */       param1TreeInfo.deterministic = false;
/* 3489 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class StartS
/*      */     extends Start
/*      */   {
/*      */     StartS(Pattern.Node param1Node) {
/* 3498 */       super(param1Node);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3501 */       if (param1Int > param1Matcher.to - this.minLength) {
/* 3502 */         param1Matcher.hitEnd = true;
/* 3503 */         return false;
/*      */       } 
/* 3505 */       int i = param1Matcher.to - this.minLength;
/* 3506 */       while (param1Int <= i) {
/*      */         
/* 3508 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3509 */           param1Matcher.first = param1Int;
/* 3510 */           param1Matcher.groups[0] = param1Matcher.first;
/* 3511 */           param1Matcher.groups[1] = param1Matcher.last;
/* 3512 */           return true;
/*      */         } 
/* 3514 */         if (param1Int == i) {
/*      */           break;
/*      */         }
/*      */         
/* 3518 */         if (Character.isHighSurrogate(param1CharSequence.charAt(param1Int++)) && 
/* 3519 */           param1Int < param1CharSequence.length() && 
/* 3520 */           Character.isLowSurrogate(param1CharSequence.charAt(param1Int))) {
/* 3521 */           param1Int++;
/*      */         }
/*      */       } 
/*      */       
/* 3525 */       param1Matcher.hitEnd = true;
/* 3526 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Begin
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3537 */       byte b = param1Matcher.anchoringBounds ? param1Matcher.from : 0;
/*      */       
/* 3539 */       if (param1Int == b && this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 3540 */         param1Matcher.first = param1Int;
/* 3541 */         param1Matcher.groups[0] = param1Int;
/* 3542 */         param1Matcher.groups[1] = param1Matcher.last;
/* 3543 */         return true;
/*      */       } 
/* 3545 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class End
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3557 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3558 */       if (param1Int == i) {
/* 3559 */         param1Matcher.hitEnd = true;
/* 3560 */         return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 3562 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Caret
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3572 */       int i = param1Matcher.from;
/* 3573 */       int j = param1Matcher.to;
/* 3574 */       if (!param1Matcher.anchoringBounds) {
/* 3575 */         i = 0;
/* 3576 */         j = param1Matcher.getTextLength();
/*      */       } 
/*      */       
/* 3579 */       if (param1Int == j) {
/* 3580 */         param1Matcher.hitEnd = true;
/* 3581 */         return false;
/*      */       } 
/* 3583 */       if (param1Int > i) {
/* 3584 */         char c = param1CharSequence.charAt(param1Int - 1);
/* 3585 */         if (c != '\n' && c != '\r' && (c | 0x1) != 8233 && c != '')
/*      */         {
/*      */           
/* 3588 */           return false;
/*      */         }
/*      */         
/* 3591 */         if (c == '\r' && param1CharSequence.charAt(param1Int) == '\n')
/* 3592 */           return false; 
/*      */       } 
/* 3594 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class UnixCaret
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3603 */       int i = param1Matcher.from;
/* 3604 */       int j = param1Matcher.to;
/* 3605 */       if (!param1Matcher.anchoringBounds) {
/* 3606 */         i = 0;
/* 3607 */         j = param1Matcher.getTextLength();
/*      */       } 
/*      */       
/* 3610 */       if (param1Int == j) {
/* 3611 */         param1Matcher.hitEnd = true;
/* 3612 */         return false;
/*      */       } 
/* 3614 */       if (param1Int > i) {
/* 3615 */         char c = param1CharSequence.charAt(param1Int - 1);
/* 3616 */         if (c != '\n') {
/* 3617 */           return false;
/*      */         }
/*      */       } 
/* 3620 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LastMatch
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3630 */       if (param1Int != param1Matcher.oldLast)
/* 3631 */         return false; 
/* 3632 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Dollar
/*      */     extends Node
/*      */   {
/*      */     boolean multiline;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Dollar(boolean param1Boolean) {
/* 3652 */       this.multiline = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3656 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3657 */       if (!this.multiline) {
/* 3658 */         if (param1Int < i - 2)
/* 3659 */           return false; 
/* 3660 */         if (param1Int == i - 2) {
/* 3661 */           char c = param1CharSequence.charAt(param1Int);
/* 3662 */           if (c != '\r')
/* 3663 */             return false; 
/* 3664 */           c = param1CharSequence.charAt(param1Int + 1);
/* 3665 */           if (c != '\n') {
/* 3666 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3677 */       if (param1Int < i) {
/* 3678 */         char c = param1CharSequence.charAt(param1Int);
/* 3679 */         if (c == '\n') {
/*      */           
/* 3681 */           if (param1Int > 0 && param1CharSequence.charAt(param1Int - 1) == '\r')
/* 3682 */             return false; 
/* 3683 */           if (this.multiline)
/* 3684 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/* 3685 */         } else if (c == '\r' || c == '' || (c | 0x1) == 8233) {
/*      */           
/* 3687 */           if (this.multiline)
/* 3688 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */         } else {
/* 3690 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/* 3694 */       param1Matcher.hitEnd = true;
/*      */ 
/*      */       
/* 3697 */       param1Matcher.requireEnd = true;
/* 3698 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3701 */       this.next.study(param1TreeInfo);
/* 3702 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class UnixDollar
/*      */     extends Node
/*      */   {
/*      */     boolean multiline;
/*      */     
/*      */     UnixDollar(boolean param1Boolean) {
/* 3713 */       this.multiline = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3717 */       int i = param1Matcher.anchoringBounds ? param1Matcher.to : param1Matcher.getTextLength();
/* 3718 */       if (param1Int < i) {
/* 3719 */         char c = param1CharSequence.charAt(param1Int);
/* 3720 */         if (c == '\n') {
/*      */ 
/*      */           
/* 3723 */           if (!this.multiline && param1Int != i - 1) {
/* 3724 */             return false;
/*      */           }
/*      */           
/* 3727 */           if (this.multiline)
/* 3728 */             return this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */         } else {
/* 3730 */           return false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3735 */       param1Matcher.hitEnd = true;
/*      */ 
/*      */       
/* 3738 */       param1Matcher.requireEnd = true;
/* 3739 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3742 */       this.next.study(param1TreeInfo);
/* 3743 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LineEnding
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3753 */       if (param1Int < param1Matcher.to) {
/* 3754 */         char c = param1CharSequence.charAt(param1Int);
/* 3755 */         if (c == '\n' || c == '\013' || c == '\f' || c == '' || c == ' ' || c == ' ')
/*      */         {
/* 3757 */           return this.next.match(param1Matcher, param1Int + 1, param1CharSequence); } 
/* 3758 */         if (c == '\r') {
/* 3759 */           param1Int++;
/* 3760 */           if (param1Int < param1Matcher.to && param1CharSequence.charAt(param1Int) == '\n')
/* 3761 */             param1Int++; 
/* 3762 */           return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */         } 
/*      */       } else {
/* 3765 */         param1Matcher.hitEnd = true;
/*      */       } 
/* 3767 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3770 */       param1TreeInfo.minLength++;
/* 3771 */       param1TreeInfo.maxLength += 2;
/* 3772 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class CharProperty
/*      */     extends Node
/*      */   {
/*      */     private CharProperty() {}
/*      */     
/*      */     CharProperty complement() {
/* 3783 */       return new CharProperty() {
/*      */           boolean isSatisfiedBy(int param2Int) {
/* 3785 */             return !Pattern.CharProperty.this.isSatisfiedBy(param2Int); }
/*      */         };
/*      */     } boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3788 */       if (param1Int < param1Matcher.to) {
/* 3789 */         int i = Character.codePointAt(param1CharSequence, param1Int);
/* 3790 */         return (isSatisfiedBy(i) && this.next
/* 3791 */           .match(param1Matcher, param1Int + Character.charCount(i), param1CharSequence));
/*      */       } 
/* 3793 */       param1Matcher.hitEnd = true;
/* 3794 */       return false;
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3798 */       param1TreeInfo.minLength++;
/* 3799 */       param1TreeInfo.maxLength++;
/* 3800 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */     
/*      */     abstract boolean isSatisfiedBy(int param1Int);
/*      */   }
/*      */   
/*      */   private static abstract class BmpCharProperty extends CharProperty {
/*      */     private BmpCharProperty() {}
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3810 */       if (param1Int < param1Matcher.to) {
/* 3811 */         return (isSatisfiedBy(param1CharSequence.charAt(param1Int)) && this.next
/* 3812 */           .match(param1Matcher, param1Int + 1, param1CharSequence));
/*      */       }
/* 3814 */       param1Matcher.hitEnd = true;
/* 3815 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleS
/*      */     extends CharProperty
/*      */   {
/*      */     final int c;
/*      */     
/*      */     SingleS(int param1Int) {
/* 3825 */       this.c = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3827 */       return (param1Int == this.c);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Single
/*      */     extends BmpCharProperty {
/*      */     final int c;
/*      */     
/*      */     Single(int param1Int) {
/* 3836 */       this.c = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3838 */       return (param1Int == this.c);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleI
/*      */     extends BmpCharProperty
/*      */   {
/*      */     final int lower;
/*      */     final int upper;
/*      */     
/*      */     SingleI(int param1Int1, int param1Int2) {
/* 3849 */       this.lower = param1Int1;
/* 3850 */       this.upper = param1Int2;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3853 */       return (param1Int == this.lower || param1Int == this.upper);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class SingleU
/*      */     extends CharProperty
/*      */   {
/*      */     final int lower;
/*      */     
/*      */     SingleU(int param1Int) {
/* 3863 */       this.lower = param1Int;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3866 */       return (this.lower == param1Int || this.lower == 
/* 3867 */         Character.toLowerCase(Character.toUpperCase(param1Int)));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Block
/*      */     extends CharProperty
/*      */   {
/*      */     final Character.UnicodeBlock block;
/*      */     
/*      */     Block(Character.UnicodeBlock param1UnicodeBlock) {
/* 3877 */       this.block = param1UnicodeBlock;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3880 */       return (this.block == Character.UnicodeBlock.of(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Script
/*      */     extends CharProperty
/*      */   {
/*      */     final Character.UnicodeScript script;
/*      */     
/*      */     Script(Character.UnicodeScript param1UnicodeScript) {
/* 3890 */       this.script = param1UnicodeScript;
/*      */     }
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3893 */       return (this.script == Character.UnicodeScript.of(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Category
/*      */     extends CharProperty {
/*      */     final int typeMask;
/*      */     
/*      */     Category(int param1Int) {
/* 3902 */       this.typeMask = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3904 */       return ((this.typeMask & 1 << Character.getType(param1Int)) != 0);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Utype
/*      */     extends CharProperty {
/*      */     final UnicodeProp uprop;
/*      */     
/*      */     Utype(UnicodeProp param1UnicodeProp) {
/* 3913 */       this.uprop = param1UnicodeProp;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3915 */       return this.uprop.is(param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Ctype
/*      */     extends BmpCharProperty {
/*      */     final int ctype;
/*      */     
/*      */     Ctype(int param1Int) {
/* 3924 */       this.ctype = param1Int;
/*      */     } boolean isSatisfiedBy(int param1Int) {
/* 3926 */       return (param1Int < 128 && ASCII.isType(param1Int, this.ctype));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class VertWS
/*      */     extends BmpCharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3935 */       return ((param1Int >= 10 && param1Int <= 13) || param1Int == 133 || param1Int == 8232 || param1Int == 8233);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class HorizWS
/*      */     extends BmpCharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 3945 */       return (param1Int == 9 || param1Int == 32 || param1Int == 160 || param1Int == 5760 || param1Int == 6158 || (param1Int >= 8192 && param1Int <= 8202) || param1Int == 8239 || param1Int == 8287 || param1Int == 12288);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceNode
/*      */     extends Node
/*      */   {
/*      */     int[] buffer;
/*      */ 
/*      */     
/*      */     SliceNode(int[] param1ArrayOfint) {
/* 3958 */       this.buffer = param1ArrayOfint;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 3961 */       param1TreeInfo.minLength += this.buffer.length;
/* 3962 */       param1TreeInfo.maxLength += this.buffer.length;
/* 3963 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Slice
/*      */     extends SliceNode
/*      */   {
/*      */     Slice(int[] param1ArrayOfint) {
/* 3973 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3976 */       int[] arrayOfInt = this.buffer;
/* 3977 */       int i = arrayOfInt.length;
/* 3978 */       for (byte b = 0; b < i; b++) {
/* 3979 */         if (param1Int + b >= param1Matcher.to) {
/* 3980 */           param1Matcher.hitEnd = true;
/* 3981 */           return false;
/*      */         } 
/* 3983 */         if (arrayOfInt[b] != param1CharSequence.charAt(param1Int + b))
/* 3984 */           return false; 
/*      */       } 
/* 3986 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceI
/*      */     extends SliceNode
/*      */   {
/*      */     SliceI(int[] param1ArrayOfint) {
/* 3996 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 3999 */       int[] arrayOfInt = this.buffer;
/* 4000 */       int i = arrayOfInt.length;
/* 4001 */       for (byte b = 0; b < i; b++) {
/* 4002 */         if (param1Int + b >= param1Matcher.to) {
/* 4003 */           param1Matcher.hitEnd = true;
/* 4004 */           return false;
/*      */         } 
/* 4006 */         char c = param1CharSequence.charAt(param1Int + b);
/* 4007 */         if (arrayOfInt[b] != c && arrayOfInt[b] != 
/* 4008 */           ASCII.toLower(c))
/* 4009 */           return false; 
/*      */       } 
/* 4011 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceU
/*      */     extends SliceNode
/*      */   {
/*      */     SliceU(int[] param1ArrayOfint) {
/* 4021 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4024 */       int[] arrayOfInt = this.buffer;
/* 4025 */       int i = arrayOfInt.length;
/* 4026 */       for (byte b = 0; b < i; b++) {
/* 4027 */         if (param1Int + b >= param1Matcher.to) {
/* 4028 */           param1Matcher.hitEnd = true;
/* 4029 */           return false;
/*      */         } 
/* 4031 */         char c = param1CharSequence.charAt(param1Int + b);
/* 4032 */         if (arrayOfInt[b] != c && arrayOfInt[b] != 
/* 4033 */           Character.toLowerCase(Character.toUpperCase(c)))
/* 4034 */           return false; 
/*      */       } 
/* 4036 */       return this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceS
/*      */     extends SliceNode
/*      */   {
/*      */     SliceS(int[] param1ArrayOfint) {
/* 4046 */       super(param1ArrayOfint);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4049 */       int[] arrayOfInt = this.buffer;
/* 4050 */       int i = param1Int;
/* 4051 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 4052 */         if (i >= param1Matcher.to) {
/* 4053 */           param1Matcher.hitEnd = true;
/* 4054 */           return false;
/*      */         } 
/* 4056 */         int j = Character.codePointAt(param1CharSequence, i);
/* 4057 */         if (arrayOfInt[b] != j)
/* 4058 */           return false; 
/* 4059 */         i += Character.charCount(j);
/* 4060 */         if (i > param1Matcher.to) {
/* 4061 */           param1Matcher.hitEnd = true;
/* 4062 */           return false;
/*      */         } 
/*      */       } 
/* 4065 */       return this.next.match(param1Matcher, i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SliceIS
/*      */     extends SliceNode
/*      */   {
/*      */     SliceIS(int[] param1ArrayOfint) {
/* 4075 */       super(param1ArrayOfint);
/*      */     }
/*      */     int toLower(int param1Int) {
/* 4078 */       return ASCII.toLower(param1Int);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4081 */       int[] arrayOfInt = this.buffer;
/* 4082 */       int i = param1Int;
/* 4083 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 4084 */         if (i >= param1Matcher.to) {
/* 4085 */           param1Matcher.hitEnd = true;
/* 4086 */           return false;
/*      */         } 
/* 4088 */         int j = Character.codePointAt(param1CharSequence, i);
/* 4089 */         if (arrayOfInt[b] != j && arrayOfInt[b] != toLower(j))
/* 4090 */           return false; 
/* 4091 */         i += Character.charCount(j);
/* 4092 */         if (i > param1Matcher.to) {
/* 4093 */           param1Matcher.hitEnd = true;
/* 4094 */           return false;
/*      */         } 
/*      */       } 
/* 4097 */       return this.next.match(param1Matcher, i, param1CharSequence);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SliceUS
/*      */     extends SliceIS
/*      */   {
/*      */     SliceUS(int[] param1ArrayOfint) {
/* 4107 */       super(param1ArrayOfint);
/*      */     }
/*      */     int toLower(int param1Int) {
/* 4110 */       return Character.toLowerCase(Character.toUpperCase(param1Int));
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean inRange(int paramInt1, int paramInt2, int paramInt3) {
/* 4115 */     return (paramInt1 <= paramInt2 && paramInt2 <= paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty rangeFor(final int lower, final int upper) {
/* 4123 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 4125 */           return Pattern.inRange(lower, param1Int, upper);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharProperty caseInsensitiveRangeFor(final int lower, final int upper) {
/* 4134 */     if (has(64))
/* 4135 */       return new CharProperty()
/*      */         {
/* 4137 */           boolean isSatisfiedBy(int param1Int) { if (Pattern.inRange(lower, param1Int, upper))
/* 4138 */               return true; 
/* 4139 */             int i = Character.toUpperCase(param1Int);
/* 4140 */             return (Pattern.inRange(lower, i, upper) || Pattern
/* 4141 */               .inRange(lower, Character.toLowerCase(i), upper)); } }; 
/* 4142 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 4144 */           return (Pattern.inRange(lower, param1Int, upper) || (
/* 4145 */             ASCII.isAscii(param1Int) && (Pattern
/* 4146 */             .inRange(lower, ASCII.toUpper(param1Int), upper) || Pattern
/* 4147 */             .inRange(lower, ASCII.toLower(param1Int), upper))));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static final class All
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4157 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Dot
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4166 */       return (param1Int != 10 && param1Int != 13 && (param1Int | 0x1) != 8233 && param1Int != 133);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class UnixDot
/*      */     extends CharProperty
/*      */   {
/*      */     boolean isSatisfiedBy(int param1Int) {
/* 4178 */       return (param1Int != 10);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Ques
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     int type;
/*      */     
/*      */     Ques(Pattern.Node param1Node, int param1Int) {
/* 4189 */       this.atom = param1Node;
/* 4190 */       this.type = param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4193 */       switch (this.type) {
/*      */         case 0:
/* 4195 */           return ((this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence)) || this.next
/* 4196 */             .match(param1Matcher, param1Int, param1CharSequence));
/*      */         case 1:
/* 4198 */           return (this.next.match(param1Matcher, param1Int, param1CharSequence) || (this.atom
/* 4199 */             .match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence)));
/*      */         case 2:
/* 4201 */           if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) param1Int = param1Matcher.last; 
/* 4202 */           return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4204 */       return (this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next.match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4208 */       if (this.type != 3) {
/* 4209 */         int i = param1TreeInfo.minLength;
/* 4210 */         this.atom.study(param1TreeInfo);
/* 4211 */         param1TreeInfo.minLength = i;
/* 4212 */         param1TreeInfo.deterministic = false;
/* 4213 */         return this.next.study(param1TreeInfo);
/*      */       } 
/* 4215 */       this.atom.study(param1TreeInfo);
/* 4216 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Curly
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     
/*      */     int type;
/*      */     
/*      */     int cmin;
/*      */     
/*      */     int cmax;
/*      */     
/*      */     Curly(Pattern.Node param1Node, int param1Int1, int param1Int2, int param1Int3) {
/* 4233 */       this.atom = param1Node;
/* 4234 */       this.type = param1Int3;
/* 4235 */       this.cmin = param1Int1;
/* 4236 */       this.cmax = param1Int2;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/*      */       byte b;
/* 4240 */       for (b = 0; b < this.cmin; b++) {
/* 4241 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4242 */           param1Int = param1Matcher.last;
/*      */         } else {
/*      */           
/* 4245 */           return false;
/*      */         } 
/* 4247 */       }  if (this.type == 0)
/* 4248 */         return match0(param1Matcher, param1Int, b, param1CharSequence); 
/* 4249 */       if (this.type == 1) {
/* 4250 */         return match1(param1Matcher, param1Int, b, param1CharSequence);
/*      */       }
/* 4252 */       return match2(param1Matcher, param1Int, b, param1CharSequence);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match0(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4258 */       if (param1Int2 >= this.cmax)
/*      */       {
/*      */         
/* 4261 */         return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */       }
/* 4263 */       int i = param1Int2;
/* 4264 */       if (this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */         
/* 4266 */         int j = param1Matcher.last - param1Int1;
/* 4267 */         if (j != 0) {
/*      */ 
/*      */           
/* 4270 */           param1Int1 = param1Matcher.last;
/* 4271 */           param1Int2++;
/*      */           
/* 4273 */           while (param1Int2 < this.cmax && 
/* 4274 */             this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */             
/* 4276 */             if (param1Int1 + j != param1Matcher.last) {
/* 4277 */               if (match0(param1Matcher, param1Matcher.last, param1Int2 + 1, param1CharSequence))
/* 4278 */                 return true; 
/*      */               break;
/*      */             } 
/* 4281 */             param1Int1 += j;
/* 4282 */             param1Int2++;
/*      */           } 
/*      */           
/* 4285 */           while (param1Int2 >= i) {
/* 4286 */             if (this.next.match(param1Matcher, param1Int1, param1CharSequence))
/* 4287 */               return true; 
/* 4288 */             param1Int1 -= j;
/* 4289 */             param1Int2--;
/*      */           } 
/* 4291 */           return false;
/*      */         } 
/* 4293 */       }  return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean match1(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/*      */       while (true) {
/* 4301 */         if (this.next.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4302 */           return true;
/*      */         }
/* 4304 */         if (param1Int2 >= this.cmax) {
/* 4305 */           return false;
/*      */         }
/* 4307 */         if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4308 */           return false;
/*      */         }
/* 4310 */         if (param1Int1 == param1Matcher.last) {
/* 4311 */           return false;
/*      */         }
/* 4313 */         param1Int1 = param1Matcher.last;
/* 4314 */         param1Int2++;
/*      */       } 
/*      */     }
/*      */     boolean match2(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4318 */       for (; param1Int2 < this.cmax && 
/* 4319 */         this.atom.match(param1Matcher, param1Int1, param1CharSequence); param1Int2++) {
/*      */         
/* 4321 */         if (param1Int1 == param1Matcher.last)
/*      */           break; 
/* 4323 */         param1Int1 = param1Matcher.last;
/*      */       } 
/* 4325 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4329 */       int i = param1TreeInfo.minLength;
/* 4330 */       int j = param1TreeInfo.maxLength;
/* 4331 */       boolean bool1 = param1TreeInfo.maxValid;
/* 4332 */       boolean bool2 = param1TreeInfo.deterministic;
/* 4333 */       param1TreeInfo.reset();
/*      */       
/* 4335 */       this.atom.study(param1TreeInfo);
/*      */       
/* 4337 */       int k = param1TreeInfo.minLength * this.cmin + i;
/* 4338 */       if (k < i) {
/* 4339 */         k = 268435455;
/*      */       }
/* 4341 */       param1TreeInfo.minLength = k;
/*      */       
/* 4343 */       if (bool1 & param1TreeInfo.maxValid) {
/* 4344 */         k = param1TreeInfo.maxLength * this.cmax + j;
/* 4345 */         param1TreeInfo.maxLength = k;
/* 4346 */         if (k < j) {
/* 4347 */           param1TreeInfo.maxValid = false;
/*      */         }
/*      */       } else {
/* 4350 */         param1TreeInfo.maxValid = false;
/*      */       } 
/*      */       
/* 4353 */       if (param1TreeInfo.deterministic && this.cmin == this.cmax) {
/* 4354 */         param1TreeInfo.deterministic = bool2;
/*      */       } else {
/* 4356 */         param1TreeInfo.deterministic = false;
/* 4357 */       }  return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class GroupCurly
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */     
/*      */     int type;
/*      */     
/*      */     int cmin;
/*      */     
/*      */     int cmax;
/*      */     
/*      */     int localIndex;
/*      */     
/*      */     int groupIndex;
/*      */     
/*      */     boolean capture;
/*      */     
/*      */     GroupCurly(Pattern.Node param1Node, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean) {
/* 4380 */       this.atom = param1Node;
/* 4381 */       this.type = param1Int3;
/* 4382 */       this.cmin = param1Int1;
/* 4383 */       this.cmax = param1Int2;
/* 4384 */       this.localIndex = param1Int4;
/* 4385 */       this.groupIndex = param1Int5;
/* 4386 */       this.capture = param1Boolean;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4389 */       int[] arrayOfInt1 = param1Matcher.groups;
/* 4390 */       int[] arrayOfInt2 = param1Matcher.locals;
/* 4391 */       int i = arrayOfInt2[this.localIndex];
/* 4392 */       int j = 0;
/* 4393 */       int k = 0;
/*      */       
/* 4395 */       if (this.capture) {
/* 4396 */         j = arrayOfInt1[this.groupIndex];
/* 4397 */         k = arrayOfInt1[this.groupIndex + 1];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4402 */       arrayOfInt2[this.localIndex] = -1;
/*      */       
/* 4404 */       boolean bool = true;
/* 4405 */       for (byte b = 0; b < this.cmin; b++) {
/* 4406 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4407 */           if (this.capture) {
/* 4408 */             arrayOfInt1[this.groupIndex] = param1Int;
/* 4409 */             arrayOfInt1[this.groupIndex + 1] = param1Matcher.last;
/*      */           } 
/* 4411 */           param1Int = param1Matcher.last;
/*      */         } else {
/* 4413 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/* 4417 */       if (bool) {
/* 4418 */         if (this.type == 0) {
/* 4419 */           bool = match0(param1Matcher, param1Int, this.cmin, param1CharSequence);
/* 4420 */         } else if (this.type == 1) {
/* 4421 */           bool = match1(param1Matcher, param1Int, this.cmin, param1CharSequence);
/*      */         } else {
/* 4423 */           bool = match2(param1Matcher, param1Int, this.cmin, param1CharSequence);
/*      */         } 
/*      */       }
/* 4426 */       if (!bool) {
/* 4427 */         arrayOfInt2[this.localIndex] = i;
/* 4428 */         if (this.capture) {
/* 4429 */           arrayOfInt1[this.groupIndex] = j;
/* 4430 */           arrayOfInt1[this.groupIndex + 1] = k;
/*      */         } 
/*      */       } 
/* 4433 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean match0(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4438 */       int i = param1Int2;
/* 4439 */       int[] arrayOfInt = param1Matcher.groups;
/* 4440 */       int j = 0;
/* 4441 */       int k = 0;
/* 4442 */       if (this.capture) {
/* 4443 */         j = arrayOfInt[this.groupIndex];
/* 4444 */         k = arrayOfInt[this.groupIndex + 1];
/*      */       } 
/*      */       
/* 4447 */       if (param1Int2 < this.cmax)
/*      */       {
/* 4449 */         if (this.atom.match(param1Matcher, param1Int1, param1CharSequence)) {
/*      */           
/* 4451 */           int m = param1Matcher.last - param1Int1;
/* 4452 */           if (m <= 0) {
/* 4453 */             if (this.capture) {
/* 4454 */               arrayOfInt[this.groupIndex] = param1Int1;
/* 4455 */               arrayOfInt[this.groupIndex + 1] = param1Int1 + m;
/*      */             } 
/* 4457 */             param1Int1 += m;
/*      */           } else {
/*      */             
/*      */             while (true) {
/* 4461 */               if (this.capture) {
/* 4462 */                 arrayOfInt[this.groupIndex] = param1Int1;
/* 4463 */                 arrayOfInt[this.groupIndex + 1] = param1Int1 + m;
/*      */               } 
/* 4465 */               param1Int1 += m;
/* 4466 */               if (++param1Int2 >= this.cmax)
/*      */                 break; 
/* 4468 */               if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence))
/*      */                 break; 
/* 4470 */               if (param1Int1 + m != param1Matcher.last) {
/* 4471 */                 if (match0(param1Matcher, param1Int1, param1Int2, param1CharSequence))
/* 4472 */                   return true; 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 4476 */             while (param1Int2 > i) {
/* 4477 */               if (this.next.match(param1Matcher, param1Int1, param1CharSequence)) {
/* 4478 */                 if (this.capture) {
/* 4479 */                   arrayOfInt[this.groupIndex + 1] = param1Int1;
/* 4480 */                   arrayOfInt[this.groupIndex] = param1Int1 - m;
/*      */                 } 
/* 4482 */                 return true;
/*      */               } 
/*      */               
/* 4485 */               param1Int1 -= m;
/* 4486 */               if (this.capture) {
/* 4487 */                 arrayOfInt[this.groupIndex + 1] = param1Int1;
/* 4488 */                 arrayOfInt[this.groupIndex] = param1Int1 - m;
/*      */               } 
/* 4490 */               param1Int2--;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 4495 */       if (this.capture) {
/* 4496 */         arrayOfInt[this.groupIndex] = j;
/* 4497 */         arrayOfInt[this.groupIndex + 1] = k;
/*      */       } 
/* 4499 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean match1(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/*      */       while (true) {
/* 4504 */         if (this.next.match(param1Matcher, param1Int1, param1CharSequence))
/* 4505 */           return true; 
/* 4506 */         if (param1Int2 >= this.cmax)
/* 4507 */           return false; 
/* 4508 */         if (!this.atom.match(param1Matcher, param1Int1, param1CharSequence))
/* 4509 */           return false; 
/* 4510 */         if (param1Int1 == param1Matcher.last)
/* 4511 */           return false; 
/* 4512 */         if (this.capture) {
/* 4513 */           param1Matcher.groups[this.groupIndex] = param1Int1;
/* 4514 */           param1Matcher.groups[this.groupIndex + 1] = param1Matcher.last;
/*      */         } 
/* 4516 */         param1Int1 = param1Matcher.last;
/* 4517 */         param1Int2++;
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean match2(Matcher param1Matcher, int param1Int1, int param1Int2, CharSequence param1CharSequence) {
/* 4522 */       for (; param1Int2 < this.cmax && 
/* 4523 */         this.atom.match(param1Matcher, param1Int1, param1CharSequence); param1Int2++) {
/*      */ 
/*      */         
/* 4526 */         if (this.capture) {
/* 4527 */           param1Matcher.groups[this.groupIndex] = param1Int1;
/* 4528 */           param1Matcher.groups[this.groupIndex + 1] = param1Matcher.last;
/*      */         } 
/* 4530 */         if (param1Int1 == param1Matcher.last) {
/*      */           break;
/*      */         }
/* 4533 */         param1Int1 = param1Matcher.last;
/*      */       } 
/* 4535 */       return this.next.match(param1Matcher, param1Int1, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4539 */       int i = param1TreeInfo.minLength;
/* 4540 */       int j = param1TreeInfo.maxLength;
/* 4541 */       boolean bool1 = param1TreeInfo.maxValid;
/* 4542 */       boolean bool2 = param1TreeInfo.deterministic;
/* 4543 */       param1TreeInfo.reset();
/*      */       
/* 4545 */       this.atom.study(param1TreeInfo);
/*      */       
/* 4547 */       int k = param1TreeInfo.minLength * this.cmin + i;
/* 4548 */       if (k < i) {
/* 4549 */         k = 268435455;
/*      */       }
/* 4551 */       param1TreeInfo.minLength = k;
/*      */       
/* 4553 */       if (bool1 & param1TreeInfo.maxValid) {
/* 4554 */         k = param1TreeInfo.maxLength * this.cmax + j;
/* 4555 */         param1TreeInfo.maxLength = k;
/* 4556 */         if (k < j) {
/* 4557 */           param1TreeInfo.maxValid = false;
/*      */         }
/*      */       } else {
/* 4560 */         param1TreeInfo.maxValid = false;
/*      */       } 
/*      */       
/* 4563 */       if (param1TreeInfo.deterministic && this.cmin == this.cmax) {
/* 4564 */         param1TreeInfo.deterministic = bool2;
/*      */       } else {
/* 4566 */         param1TreeInfo.deterministic = false;
/*      */       } 
/* 4568 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class BranchConn
/*      */     extends Node
/*      */   {
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4582 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4585 */       return param1TreeInfo.deterministic;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Branch
/*      */     extends Node
/*      */   {
/* 4595 */     Pattern.Node[] atoms = new Pattern.Node[2];
/* 4596 */     int size = 2; Pattern.Node conn;
/*      */     
/*      */     Branch(Pattern.Node param1Node1, Pattern.Node param1Node2, Pattern.Node param1Node3) {
/* 4599 */       this.conn = param1Node3;
/* 4600 */       this.atoms[0] = param1Node1;
/* 4601 */       this.atoms[1] = param1Node2;
/*      */     }
/*      */     
/*      */     void add(Pattern.Node param1Node) {
/* 4605 */       if (this.size >= this.atoms.length) {
/* 4606 */         Pattern.Node[] arrayOfNode = new Pattern.Node[this.atoms.length * 2];
/* 4607 */         System.arraycopy(this.atoms, 0, arrayOfNode, 0, this.atoms.length);
/* 4608 */         this.atoms = arrayOfNode;
/*      */       } 
/* 4610 */       this.atoms[this.size++] = param1Node;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4614 */       for (byte b = 0; b < this.size; b++) {
/* 4615 */         if (this.atoms[b] == null) {
/* 4616 */           if (this.conn.next.match(param1Matcher, param1Int, param1CharSequence))
/* 4617 */             return true; 
/* 4618 */         } else if (this.atoms[b].match(param1Matcher, param1Int, param1CharSequence)) {
/* 4619 */           return true;
/*      */         } 
/*      */       } 
/* 4622 */       return false;
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4626 */       int i = param1TreeInfo.minLength;
/* 4627 */       int j = param1TreeInfo.maxLength;
/* 4628 */       boolean bool = param1TreeInfo.maxValid;
/*      */       
/* 4630 */       int k = Integer.MAX_VALUE;
/* 4631 */       int m = -1;
/* 4632 */       for (byte b = 0; b < this.size; b++) {
/* 4633 */         param1TreeInfo.reset();
/* 4634 */         if (this.atoms[b] != null)
/* 4635 */           this.atoms[b].study(param1TreeInfo); 
/* 4636 */         k = Math.min(k, param1TreeInfo.minLength);
/* 4637 */         m = Math.max(m, param1TreeInfo.maxLength);
/* 4638 */         bool &= param1TreeInfo.maxValid;
/*      */       } 
/*      */       
/* 4641 */       i += k;
/* 4642 */       j += m;
/*      */       
/* 4644 */       param1TreeInfo.reset();
/* 4645 */       this.conn.next.study(param1TreeInfo);
/*      */       
/* 4647 */       param1TreeInfo.minLength += i;
/* 4648 */       param1TreeInfo.maxLength += j;
/* 4649 */       param1TreeInfo.maxValid &= bool;
/* 4650 */       param1TreeInfo.deterministic = false;
/* 4651 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class GroupHead
/*      */     extends Node
/*      */   {
/*      */     int localIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GroupHead(int param1Int) {
/* 4667 */       this.localIndex = param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4670 */       int i = param1Matcher.locals[this.localIndex];
/* 4671 */       param1Matcher.locals[this.localIndex] = param1Int;
/* 4672 */       boolean bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/* 4673 */       param1Matcher.locals[this.localIndex] = i;
/* 4674 */       return bool;
/*      */     }
/*      */     boolean matchRef(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4677 */       int i = param1Matcher.locals[this.localIndex];
/* 4678 */       param1Matcher.locals[this.localIndex] = param1Int ^ 0xFFFFFFFF;
/* 4679 */       boolean bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/* 4680 */       param1Matcher.locals[this.localIndex] = i;
/* 4681 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class GroupRef
/*      */     extends Node
/*      */   {
/*      */     Pattern.GroupHead head;
/*      */ 
/*      */     
/*      */     GroupRef(Pattern.GroupHead param1GroupHead) {
/* 4693 */       this.head = param1GroupHead;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4696 */       return (this.head.matchRef(param1Matcher, param1Int, param1CharSequence) && this.next
/* 4697 */         .match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4700 */       param1TreeInfo.maxValid = false;
/* 4701 */       param1TreeInfo.deterministic = false;
/* 4702 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class GroupTail
/*      */     extends Node
/*      */   {
/*      */     int localIndex;
/*      */ 
/*      */     
/*      */     int groupIndex;
/*      */ 
/*      */     
/*      */     GroupTail(int param1Int1, int param1Int2) {
/* 4718 */       this.localIndex = param1Int1;
/* 4719 */       this.groupIndex = param1Int2 + param1Int2;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4722 */       int i = param1Matcher.locals[this.localIndex];
/* 4723 */       if (i >= 0) {
/*      */ 
/*      */         
/* 4726 */         int j = param1Matcher.groups[this.groupIndex];
/* 4727 */         int k = param1Matcher.groups[this.groupIndex + 1];
/*      */         
/* 4729 */         param1Matcher.groups[this.groupIndex] = i;
/* 4730 */         param1Matcher.groups[this.groupIndex + 1] = param1Int;
/* 4731 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4732 */           return true;
/*      */         }
/* 4734 */         param1Matcher.groups[this.groupIndex] = j;
/* 4735 */         param1Matcher.groups[this.groupIndex + 1] = k;
/* 4736 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 4740 */       param1Matcher.last = param1Int;
/* 4741 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Prolog
/*      */     extends Node
/*      */   {
/*      */     Pattern.Loop loop;
/*      */     
/*      */     Prolog(Pattern.Loop param1Loop) {
/* 4752 */       this.loop = param1Loop;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4755 */       return this.loop.matchInit(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4758 */       return this.loop.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Loop
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node body;
/*      */     
/*      */     int countIndex;
/*      */     int beginIndex;
/*      */     int cmin;
/*      */     int cmax;
/*      */     
/*      */     Loop(int param1Int1, int param1Int2) {
/* 4774 */       this.countIndex = param1Int1;
/* 4775 */       this.beginIndex = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4779 */       if (param1Int > param1Matcher.locals[this.beginIndex]) {
/* 4780 */         int i = param1Matcher.locals[this.countIndex];
/*      */ 
/*      */ 
/*      */         
/* 4784 */         if (i < this.cmin) {
/* 4785 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4786 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4789 */           if (!bool) {
/* 4790 */             param1Matcher.locals[this.countIndex] = i;
/*      */           }
/*      */           
/* 4793 */           return bool;
/*      */         } 
/*      */ 
/*      */         
/* 4797 */         if (i < this.cmax) {
/* 4798 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4799 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4802 */           if (!bool) {
/* 4803 */             param1Matcher.locals[this.countIndex] = i;
/*      */           } else {
/* 4805 */             return true;
/*      */           } 
/*      */         } 
/* 4808 */       }  return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean matchInit(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4811 */       int i = param1Matcher.locals[this.countIndex];
/* 4812 */       boolean bool = false;
/* 4813 */       if (0 < this.cmin) {
/* 4814 */         param1Matcher.locals[this.countIndex] = 1;
/* 4815 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4816 */       } else if (0 < this.cmax) {
/* 4817 */         param1Matcher.locals[this.countIndex] = 1;
/* 4818 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4819 */         if (!bool)
/* 4820 */           bool = this.next.match(param1Matcher, param1Int, param1CharSequence); 
/*      */       } else {
/* 4822 */         bool = this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4824 */       param1Matcher.locals[this.countIndex] = i;
/* 4825 */       return bool;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4828 */       param1TreeInfo.maxValid = false;
/* 4829 */       param1TreeInfo.deterministic = false;
/* 4830 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LazyLoop
/*      */     extends Loop
/*      */   {
/*      */     LazyLoop(int param1Int1, int param1Int2) {
/* 4842 */       super(param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4846 */       if (param1Int > param1Matcher.locals[this.beginIndex]) {
/* 4847 */         int i = param1Matcher.locals[this.countIndex];
/* 4848 */         if (i < this.cmin) {
/* 4849 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4850 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4853 */           if (!bool)
/* 4854 */             param1Matcher.locals[this.countIndex] = i; 
/* 4855 */           return bool;
/*      */         } 
/* 4857 */         if (this.next.match(param1Matcher, param1Int, param1CharSequence))
/* 4858 */           return true; 
/* 4859 */         if (i < this.cmax) {
/* 4860 */           param1Matcher.locals[this.countIndex] = i + 1;
/* 4861 */           boolean bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */ 
/*      */           
/* 4864 */           if (!bool)
/* 4865 */             param1Matcher.locals[this.countIndex] = i; 
/* 4866 */           return bool;
/*      */         } 
/* 4868 */         return false;
/*      */       } 
/* 4870 */       return this.next.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     boolean matchInit(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4873 */       int i = param1Matcher.locals[this.countIndex];
/* 4874 */       boolean bool = false;
/* 4875 */       if (0 < this.cmin) {
/* 4876 */         param1Matcher.locals[this.countIndex] = 1;
/* 4877 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/* 4878 */       } else if (this.next.match(param1Matcher, param1Int, param1CharSequence)) {
/* 4879 */         bool = true;
/* 4880 */       } else if (0 < this.cmax) {
/* 4881 */         param1Matcher.locals[this.countIndex] = 1;
/* 4882 */         bool = this.body.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } 
/* 4884 */       param1Matcher.locals[this.countIndex] = i;
/* 4885 */       return bool;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4888 */       param1TreeInfo.maxValid = false;
/* 4889 */       param1TreeInfo.deterministic = false;
/* 4890 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class BackRef
/*      */     extends Node
/*      */   {
/*      */     int groupIndex;
/*      */ 
/*      */     
/*      */     BackRef(int param1Int) {
/* 4902 */       this.groupIndex = param1Int + param1Int;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4905 */       int i = param1Matcher.groups[this.groupIndex];
/* 4906 */       int j = param1Matcher.groups[this.groupIndex + 1];
/*      */       
/* 4908 */       int k = j - i;
/*      */       
/* 4910 */       if (i < 0) {
/* 4911 */         return false;
/*      */       }
/*      */       
/* 4914 */       if (param1Int + k > param1Matcher.to) {
/* 4915 */         param1Matcher.hitEnd = true;
/* 4916 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 4920 */       for (byte b = 0; b < k; b++) {
/* 4921 */         if (param1CharSequence.charAt(param1Int + b) != param1CharSequence.charAt(i + b))
/* 4922 */           return false; 
/*      */       } 
/* 4924 */       return this.next.match(param1Matcher, param1Int + k, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4927 */       param1TreeInfo.maxValid = false;
/* 4928 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */   
/*      */   static class CIBackRef extends Node {
/*      */     int groupIndex;
/*      */     boolean doUnicodeCase;
/*      */     
/*      */     CIBackRef(int param1Int, boolean param1Boolean) {
/* 4937 */       this.groupIndex = param1Int + param1Int;
/* 4938 */       this.doUnicodeCase = param1Boolean;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4941 */       int i = param1Matcher.groups[this.groupIndex];
/* 4942 */       int j = param1Matcher.groups[this.groupIndex + 1];
/*      */       
/* 4944 */       int k = j - i;
/*      */ 
/*      */       
/* 4947 */       if (i < 0) {
/* 4948 */         return false;
/*      */       }
/*      */       
/* 4951 */       if (param1Int + k > param1Matcher.to) {
/* 4952 */         param1Matcher.hitEnd = true;
/* 4953 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4958 */       int m = param1Int;
/* 4959 */       for (byte b = 0; b < k; b++) {
/* 4960 */         int n = Character.codePointAt(param1CharSequence, m);
/* 4961 */         int i1 = Character.codePointAt(param1CharSequence, i);
/* 4962 */         if (n != i1) {
/* 4963 */           if (this.doUnicodeCase) {
/* 4964 */             int i2 = Character.toUpperCase(n);
/* 4965 */             int i3 = Character.toUpperCase(i1);
/* 4966 */             if (i2 != i3 && 
/* 4967 */               Character.toLowerCase(i2) != 
/* 4968 */               Character.toLowerCase(i3)) {
/* 4969 */               return false;
/*      */             }
/* 4971 */           } else if (ASCII.toLower(n) != ASCII.toLower(i1)) {
/* 4972 */             return false;
/*      */           } 
/*      */         }
/* 4975 */         m += Character.charCount(n);
/* 4976 */         i += Character.charCount(i1);
/*      */       } 
/*      */       
/* 4979 */       return this.next.match(param1Matcher, param1Int + k, param1CharSequence);
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 4982 */       param1TreeInfo.maxValid = false;
/* 4983 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class First
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node atom;
/*      */ 
/*      */     
/*      */     First(Pattern.Node param1Node) {
/* 4996 */       this.atom = Pattern.BnM.optimize(param1Node);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 4999 */       if (this.atom instanceof Pattern.BnM) {
/* 5000 */         return (this.atom.match(param1Matcher, param1Int, param1CharSequence) && this.next
/* 5001 */           .match(param1Matcher, param1Matcher.last, param1CharSequence));
/*      */       }
/*      */       while (true) {
/* 5004 */         if (param1Int > param1Matcher.to) {
/* 5005 */           param1Matcher.hitEnd = true;
/* 5006 */           return false;
/*      */         } 
/* 5008 */         if (this.atom.match(param1Matcher, param1Int, param1CharSequence)) {
/* 5009 */           return this.next.match(param1Matcher, param1Matcher.last, param1CharSequence);
/*      */         }
/* 5011 */         param1Int += Pattern.countChars(param1CharSequence, param1Int, 1);
/* 5012 */         param1Matcher.first++;
/*      */       } 
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5016 */       this.atom.study(param1TreeInfo);
/* 5017 */       param1TreeInfo.maxValid = false;
/* 5018 */       param1TreeInfo.deterministic = false;
/* 5019 */       return this.next.study(param1TreeInfo);
/*      */     } }
/*      */   static final class Conditional extends Node { Pattern.Node cond;
/*      */     Pattern.Node yes;
/*      */     Pattern.Node not;
/*      */     
/*      */     Conditional(Pattern.Node param1Node1, Pattern.Node param1Node2, Pattern.Node param1Node3) {
/* 5026 */       this.cond = param1Node1;
/* 5027 */       this.yes = param1Node2;
/* 5028 */       this.not = param1Node3;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5031 */       if (this.cond.match(param1Matcher, param1Int, param1CharSequence)) {
/* 5032 */         return this.yes.match(param1Matcher, param1Int, param1CharSequence);
/*      */       }
/* 5034 */       return this.not.match(param1Matcher, param1Int, param1CharSequence);
/*      */     }
/*      */     
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5038 */       int i = param1TreeInfo.minLength;
/* 5039 */       int j = param1TreeInfo.maxLength;
/* 5040 */       boolean bool1 = param1TreeInfo.maxValid;
/* 5041 */       param1TreeInfo.reset();
/* 5042 */       this.yes.study(param1TreeInfo);
/*      */       
/* 5044 */       int k = param1TreeInfo.minLength;
/* 5045 */       int m = param1TreeInfo.maxLength;
/* 5046 */       boolean bool2 = param1TreeInfo.maxValid;
/* 5047 */       param1TreeInfo.reset();
/* 5048 */       this.not.study(param1TreeInfo);
/*      */       
/* 5050 */       param1TreeInfo.minLength = i + Math.min(k, param1TreeInfo.minLength);
/* 5051 */       param1TreeInfo.maxLength = j + Math.max(m, param1TreeInfo.maxLength);
/* 5052 */       param1TreeInfo.maxValid = bool1 & bool2 & param1TreeInfo.maxValid;
/* 5053 */       param1TreeInfo.deterministic = false;
/* 5054 */       return this.next.study(param1TreeInfo);
/*      */     } }
/*      */ 
/*      */   
/*      */   static final class Pos
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node cond;
/*      */     
/*      */     Pos(Pattern.Node param1Node) {
/* 5064 */       this.cond = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5067 */       int i = param1Matcher.to;
/* 5068 */       boolean bool = false;
/*      */ 
/*      */       
/* 5071 */       if (param1Matcher.transparentBounds)
/* 5072 */         param1Matcher.to = param1Matcher.getTextLength(); 
/*      */       try {
/* 5074 */         bool = this.cond.match(param1Matcher, param1Int, param1CharSequence);
/*      */       } finally {
/*      */         
/* 5077 */         param1Matcher.to = i;
/*      */       } 
/* 5079 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Neg
/*      */     extends Node
/*      */   {
/*      */     Pattern.Node cond;
/*      */     
/*      */     Neg(Pattern.Node param1Node) {
/* 5089 */       this.cond = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5092 */       int i = param1Matcher.to;
/* 5093 */       boolean bool = false;
/*      */ 
/*      */       
/* 5096 */       if (param1Matcher.transparentBounds)
/* 5097 */         param1Matcher.to = param1Matcher.getTextLength(); 
/*      */       try {
/* 5099 */         if (param1Int < param1Matcher.to) {
/* 5100 */           bool = !this.cond.match(param1Matcher, param1Int, param1CharSequence) ? true : false;
/*      */         }
/*      */         else {
/*      */           
/* 5104 */           param1Matcher.requireEnd = true;
/* 5105 */           bool = !this.cond.match(param1Matcher, param1Int, param1CharSequence) ? true : false;
/*      */         } 
/*      */       } finally {
/*      */         
/* 5109 */         param1Matcher.to = i;
/*      */       } 
/* 5111 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5119 */   static Node lookbehindEnd = new Node() {
/*      */       boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5121 */         return (param1Int == param1Matcher.lookbehindTo);
/*      */       }
/*      */     };
/*      */   
/*      */   static class Behind
/*      */     extends Node {
/*      */     Pattern.Node cond;
/*      */     int rmax;
/*      */     int rmin;
/*      */     
/*      */     Behind(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5132 */       this.cond = param1Node;
/* 5133 */       this.rmax = param1Int1;
/* 5134 */       this.rmin = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5138 */       int i = param1Matcher.from;
/* 5139 */       boolean bool = false;
/* 5140 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5142 */       int j = Math.max(param1Int - this.rmax, bool1);
/*      */       
/* 5144 */       int k = param1Matcher.lookbehindTo;
/* 5145 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5147 */       if (param1Matcher.transparentBounds)
/* 5148 */         param1Matcher.from = 0; 
/* 5149 */       for (int m = param1Int - this.rmin; !bool && m >= j; m--) {
/* 5150 */         bool = this.cond.match(param1Matcher, m, param1CharSequence);
/*      */       }
/* 5152 */       param1Matcher.from = i;
/* 5153 */       param1Matcher.lookbehindTo = k;
/* 5154 */       return (bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class BehindS
/*      */     extends Behind
/*      */   {
/*      */     BehindS(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5164 */       super(param1Node, param1Int1, param1Int2);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5167 */       int i = Pattern.countChars(param1CharSequence, param1Int, -this.rmax);
/* 5168 */       int j = Pattern.countChars(param1CharSequence, param1Int, -this.rmin);
/* 5169 */       int k = param1Matcher.from;
/* 5170 */       boolean bool = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5172 */       boolean bool1 = false;
/* 5173 */       int m = Math.max(param1Int - i, bool);
/*      */       
/* 5175 */       int n = param1Matcher.lookbehindTo;
/* 5176 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5178 */       if (param1Matcher.transparentBounds) {
/* 5179 */         param1Matcher.from = 0;
/*      */       }
/* 5181 */       int i1 = param1Int - j;
/* 5182 */       for (; !bool1 && i1 >= m; 
/* 5183 */         i1 -= (i1 > m) ? Pattern.countChars(param1CharSequence, i1, -1) : 1) {
/* 5184 */         bool1 = this.cond.match(param1Matcher, i1, param1CharSequence);
/*      */       }
/* 5186 */       param1Matcher.from = k;
/* 5187 */       param1Matcher.lookbehindTo = n;
/* 5188 */       return (bool1 && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */   
/*      */   static class NotBehind
/*      */     extends Node {
/*      */     Pattern.Node cond;
/*      */     int rmax;
/*      */     int rmin;
/*      */     
/*      */     NotBehind(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5199 */       this.cond = param1Node;
/* 5200 */       this.rmax = param1Int1;
/* 5201 */       this.rmin = param1Int2;
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5205 */       int i = param1Matcher.lookbehindTo;
/* 5206 */       int j = param1Matcher.from;
/* 5207 */       boolean bool = false;
/* 5208 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5210 */       int k = Math.max(param1Int - this.rmax, bool1);
/* 5211 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5213 */       if (param1Matcher.transparentBounds)
/* 5214 */         param1Matcher.from = 0; 
/* 5215 */       for (int m = param1Int - this.rmin; !bool && m >= k; m--) {
/* 5216 */         bool = this.cond.match(param1Matcher, m, param1CharSequence);
/*      */       }
/*      */       
/* 5219 */       param1Matcher.from = j;
/* 5220 */       param1Matcher.lookbehindTo = i;
/* 5221 */       return (!bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class NotBehindS
/*      */     extends NotBehind
/*      */   {
/*      */     NotBehindS(Pattern.Node param1Node, int param1Int1, int param1Int2) {
/* 5231 */       super(param1Node, param1Int1, param1Int2);
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5234 */       int i = Pattern.countChars(param1CharSequence, param1Int, -this.rmax);
/* 5235 */       int j = Pattern.countChars(param1CharSequence, param1Int, -this.rmin);
/* 5236 */       int k = param1Matcher.from;
/* 5237 */       int m = param1Matcher.lookbehindTo;
/* 5238 */       boolean bool = false;
/* 5239 */       boolean bool1 = !param1Matcher.transparentBounds ? param1Matcher.from : false;
/*      */       
/* 5241 */       int n = Math.max(param1Int - i, bool1);
/* 5242 */       param1Matcher.lookbehindTo = param1Int;
/*      */       
/* 5244 */       if (param1Matcher.transparentBounds)
/* 5245 */         param1Matcher.from = 0; 
/* 5246 */       int i1 = param1Int - j;
/* 5247 */       for (; !bool && i1 >= n; 
/* 5248 */         i1 -= (i1 > n) ? Pattern.countChars(param1CharSequence, i1, -1) : 1) {
/* 5249 */         bool = this.cond.match(param1Matcher, i1, param1CharSequence);
/*      */       }
/*      */       
/* 5252 */       param1Matcher.from = k;
/* 5253 */       param1Matcher.lookbehindTo = m;
/* 5254 */       return (!bool && this.next.match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty union(final CharProperty lhs, final CharProperty rhs) {
/* 5263 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5265 */           return (lhs.isSatisfiedBy(param1Int) || rhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty intersection(final CharProperty lhs, final CharProperty rhs) {
/* 5273 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5275 */           return (lhs.isSatisfiedBy(param1Int) && rhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharProperty setDifference(final CharProperty lhs, final CharProperty rhs) {
/* 5283 */     return new CharProperty() {
/*      */         boolean isSatisfiedBy(int param1Int) {
/* 5285 */           return (!rhs.isSatisfiedBy(param1Int) && lhs.isSatisfiedBy(param1Int));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Bound
/*      */     extends Node
/*      */   {
/* 5296 */     static int LEFT = 1;
/* 5297 */     static int RIGHT = 2;
/* 5298 */     static int BOTH = 3;
/* 5299 */     static int NONE = 4; int type;
/*      */     boolean useUWORD;
/*      */     
/*      */     Bound(int param1Int, boolean param1Boolean) {
/* 5303 */       this.type = param1Int;
/* 5304 */       this.useUWORD = param1Boolean;
/*      */     }
/*      */     
/*      */     boolean isWord(int param1Int) {
/* 5308 */       return this.useUWORD ? UnicodeProp.WORD.is(param1Int) : ((param1Int == 95 || 
/* 5309 */         Character.isLetterOrDigit(param1Int)));
/*      */     }
/*      */ 
/*      */     
/*      */     int check(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5314 */       boolean bool1 = false;
/* 5315 */       int i = param1Matcher.from;
/* 5316 */       int j = param1Matcher.to;
/* 5317 */       if (param1Matcher.transparentBounds) {
/* 5318 */         i = 0;
/* 5319 */         j = param1Matcher.getTextLength();
/*      */       } 
/* 5321 */       if (param1Int > i) {
/* 5322 */         int k = Character.codePointBefore(param1CharSequence, param1Int);
/*      */ 
/*      */         
/* 5325 */         bool1 = (isWord(k) || (Character.getType(k) == 6 && Pattern.hasBaseCharacter(param1Matcher, param1Int - 1, param1CharSequence))) ? true : false;
/*      */       } 
/* 5327 */       boolean bool2 = false;
/* 5328 */       if (param1Int < j) {
/* 5329 */         int k = Character.codePointAt(param1CharSequence, param1Int);
/*      */ 
/*      */         
/* 5332 */         bool2 = (isWord(k) || (Character.getType(k) == 6 && Pattern.hasBaseCharacter(param1Matcher, param1Int, param1CharSequence))) ? true : false;
/*      */       } else {
/*      */         
/* 5335 */         param1Matcher.hitEnd = true;
/*      */         
/* 5337 */         param1Matcher.requireEnd = true;
/*      */       } 
/* 5339 */       return ((bool1 ^ bool2) != 0) ? (bool2 ? LEFT : RIGHT) : NONE;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5342 */       return ((check(param1Matcher, param1Int, param1CharSequence) & this.type) > 0 && this.next
/* 5343 */         .match(param1Matcher, param1Int, param1CharSequence));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasBaseCharacter(Matcher paramMatcher, int paramInt, CharSequence paramCharSequence) {
/* 5354 */     byte b = !paramMatcher.transparentBounds ? paramMatcher.from : 0;
/*      */     
/* 5356 */     for (int i = paramInt; i >= b; ) {
/* 5357 */       int j = Character.codePointAt(paramCharSequence, i);
/* 5358 */       if (Character.isLetterOrDigit(j))
/* 5359 */         return true; 
/* 5360 */       if (Character.getType(j) == 6) {
/*      */         i--; continue;
/* 5362 */       }  return false;
/*      */     } 
/* 5364 */     return false;
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
/*      */   static class BnM
/*      */     extends Node
/*      */   {
/*      */     int[] buffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] lastOcc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] optoSft;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Pattern.Node optimize(Pattern.Node param1Node) {
/* 5408 */       if (!(param1Node instanceof Pattern.Slice)) {
/* 5409 */         return param1Node;
/*      */       }
/*      */       
/* 5412 */       int[] arrayOfInt1 = ((Pattern.Slice)param1Node).buffer;
/* 5413 */       int i = arrayOfInt1.length;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5418 */       if (i < 4) {
/* 5419 */         return param1Node;
/*      */       }
/*      */       
/* 5422 */       int[] arrayOfInt2 = new int[128];
/* 5423 */       int[] arrayOfInt3 = new int[i];
/*      */       
/*      */       int j;
/*      */       
/* 5427 */       for (j = 0; j < i; j++) {
/* 5428 */         arrayOfInt2[arrayOfInt1[j] & 0x7F] = j + 1;
/*      */       }
/*      */ 
/*      */       
/* 5432 */       for (j = i; j > 0; j--) {
/*      */         
/* 5434 */         int k = i - 1; while (true) { if (k >= j) {
/*      */             
/* 5436 */             if (arrayOfInt1[k] == arrayOfInt1[k - j]) {
/*      */               
/* 5438 */               arrayOfInt3[k - 1] = j;
/*      */ 
/*      */               
/*      */               k--;
/*      */             } 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 5448 */           while (k > 0)
/* 5449 */             arrayOfInt3[--k] = j; 
/*      */           break; }
/*      */       
/*      */       } 
/* 5453 */       arrayOfInt3[i - 1] = 1;
/* 5454 */       if (param1Node instanceof Pattern.SliceS)
/* 5455 */         return new Pattern.BnMS(arrayOfInt1, arrayOfInt2, arrayOfInt3, param1Node.next); 
/* 5456 */       return new BnM(arrayOfInt1, arrayOfInt2, arrayOfInt3, param1Node.next);
/*      */     }
/*      */     BnM(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, Pattern.Node param1Node) {
/* 5459 */       this.buffer = param1ArrayOfint1;
/* 5460 */       this.lastOcc = param1ArrayOfint2;
/* 5461 */       this.optoSft = param1ArrayOfint3;
/* 5462 */       this.next = param1Node;
/*      */     }
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5465 */       int[] arrayOfInt = this.buffer;
/* 5466 */       int i = arrayOfInt.length;
/* 5467 */       int j = param1Matcher.to - i;
/*      */ 
/*      */       
/* 5470 */       label19: while (param1Int <= j) {
/*      */         
/* 5472 */         for (int k = i - 1; k >= 0; k--) {
/* 5473 */           char c = param1CharSequence.charAt(param1Int + k);
/* 5474 */           if (c != arrayOfInt[k]) {
/*      */ 
/*      */             
/* 5477 */             param1Int += Math.max(k + 1 - this.lastOcc[c & 0x7F], this.optoSft[k]);
/*      */             
/*      */             continue label19;
/*      */           } 
/*      */         } 
/* 5482 */         param1Matcher.first = param1Int;
/* 5483 */         boolean bool = this.next.match(param1Matcher, param1Int + i, param1CharSequence);
/* 5484 */         if (bool) {
/* 5485 */           param1Matcher.first = param1Int;
/* 5486 */           param1Matcher.groups[0] = param1Matcher.first;
/* 5487 */           param1Matcher.groups[1] = param1Matcher.last;
/* 5488 */           return true;
/*      */         } 
/* 5490 */         param1Int++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5495 */       param1Matcher.hitEnd = true;
/* 5496 */       return false;
/*      */     }
/*      */     boolean study(Pattern.TreeInfo param1TreeInfo) {
/* 5499 */       param1TreeInfo.minLength += this.buffer.length;
/* 5500 */       param1TreeInfo.maxValid = false;
/* 5501 */       return this.next.study(param1TreeInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BnMS
/*      */     extends BnM
/*      */   {
/*      */     int lengthInChars;
/*      */ 
/*      */     
/*      */     BnMS(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, Pattern.Node param1Node) {
/* 5513 */       super(param1ArrayOfint1, param1ArrayOfint2, param1ArrayOfint3, param1Node);
/* 5514 */       for (byte b = 0; b < this.buffer.length; b++)
/* 5515 */         this.lengthInChars += Character.charCount(this.buffer[b]); 
/*      */     }
/*      */     
/*      */     boolean match(Matcher param1Matcher, int param1Int, CharSequence param1CharSequence) {
/* 5519 */       int[] arrayOfInt = this.buffer;
/* 5520 */       int i = arrayOfInt.length;
/* 5521 */       int j = param1Matcher.to - this.lengthInChars;
/*      */ 
/*      */       
/* 5524 */       label19: while (param1Int <= j) {
/*      */ 
/*      */         
/* 5527 */         int k = Pattern.countChars(param1CharSequence, param1Int, i), m = i - 1;
/* 5528 */         for (; k > 0; k -= Character.charCount(n), m--) {
/* 5529 */           int n = Character.codePointBefore(param1CharSequence, param1Int + k);
/* 5530 */           if (n != arrayOfInt[m]) {
/*      */ 
/*      */             
/* 5533 */             int i1 = Math.max(m + 1 - this.lastOcc[n & 0x7F], this.optoSft[m]);
/* 5534 */             param1Int += Pattern.countChars(param1CharSequence, param1Int, i1);
/*      */             
/*      */             continue label19;
/*      */           } 
/*      */         } 
/* 5539 */         param1Matcher.first = param1Int;
/* 5540 */         boolean bool = this.next.match(param1Matcher, param1Int + this.lengthInChars, param1CharSequence);
/* 5541 */         if (bool) {
/* 5542 */           param1Matcher.first = param1Int;
/* 5543 */           param1Matcher.groups[0] = param1Matcher.first;
/* 5544 */           param1Matcher.groups[1] = param1Matcher.last;
/* 5545 */           return true;
/*      */         } 
/* 5547 */         param1Int += Pattern.countChars(param1CharSequence, param1Int, 1);
/*      */       } 
/* 5549 */       param1Matcher.hitEnd = true;
/* 5550 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5560 */   static Node accept = new Node();
/*      */   
/* 5562 */   static Node lastAccept = new LastNode();
/*      */   
/*      */   private static class CharPropertyNames
/*      */   {
/*      */     static Pattern.CharProperty charPropertyFor(String param1String) {
/* 5567 */       CharPropertyFactory charPropertyFactory = map.get(param1String);
/* 5568 */       return (charPropertyFactory == null) ? null : charPropertyFactory.make();
/*      */     }
/*      */     
/*      */     private static abstract class CharPropertyFactory {
/*      */       private CharPropertyFactory() {}
/*      */       
/*      */       abstract Pattern.CharProperty make(); }
/*      */     
/*      */     private static void defCategory(String param1String, final int typeMask) {
/* 5577 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5578 */               return new Pattern.Category(typeMask);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static void defRange(String param1String, final int lower, final int upper) {
/* 5583 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5584 */               return Pattern.rangeFor(lower, upper);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static void defCtype(String param1String, final int ctype) {
/* 5589 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5590 */               return new Pattern.Ctype(ctype);
/*      */             } }
/*      */         );
/*      */     }
/*      */     private static abstract class CloneableProperty extends Pattern.CharProperty implements Cloneable { private CloneableProperty() {}
/*      */       
/*      */       public CloneableProperty clone() {
/*      */         try {
/* 5598 */           return (CloneableProperty)super.clone();
/* 5599 */         } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 5600 */           throw new AssertionError(cloneNotSupportedException);
/*      */         } 
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void defClone(String param1String, final CloneableProperty p) {
/* 5607 */       map.put(param1String, new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5608 */               return p.clone();
/*      */             } }
/*      */         );
/* 5611 */     } private static final HashMap<String, CharPropertyFactory> map = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 5617 */       defCategory("Cn", 1);
/* 5618 */       defCategory("Lu", 2);
/* 5619 */       defCategory("Ll", 4);
/* 5620 */       defCategory("Lt", 8);
/* 5621 */       defCategory("Lm", 16);
/* 5622 */       defCategory("Lo", 32);
/* 5623 */       defCategory("Mn", 64);
/* 5624 */       defCategory("Me", 128);
/* 5625 */       defCategory("Mc", 256);
/* 5626 */       defCategory("Nd", 512);
/* 5627 */       defCategory("Nl", 1024);
/* 5628 */       defCategory("No", 2048);
/* 5629 */       defCategory("Zs", 4096);
/* 5630 */       defCategory("Zl", 8192);
/* 5631 */       defCategory("Zp", 16384);
/* 5632 */       defCategory("Cc", 32768);
/* 5633 */       defCategory("Cf", 65536);
/* 5634 */       defCategory("Co", 262144);
/* 5635 */       defCategory("Cs", 524288);
/* 5636 */       defCategory("Pd", 1048576);
/* 5637 */       defCategory("Ps", 2097152);
/* 5638 */       defCategory("Pe", 4194304);
/* 5639 */       defCategory("Pc", 8388608);
/* 5640 */       defCategory("Po", 16777216);
/* 5641 */       defCategory("Sm", 33554432);
/* 5642 */       defCategory("Sc", 67108864);
/* 5643 */       defCategory("Sk", 134217728);
/* 5644 */       defCategory("So", 268435456);
/* 5645 */       defCategory("Pi", 536870912);
/* 5646 */       defCategory("Pf", 1073741824);
/* 5647 */       defCategory("L", 62);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5652 */       defCategory("M", 448);
/*      */ 
/*      */       
/* 5655 */       defCategory("N", 3584);
/*      */ 
/*      */       
/* 5658 */       defCategory("Z", 28672);
/*      */ 
/*      */       
/* 5661 */       defCategory("C", 884736);
/*      */ 
/*      */ 
/*      */       
/* 5665 */       defCategory("P", 1643118592);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5672 */       defCategory("S", 503316480);
/*      */ 
/*      */ 
/*      */       
/* 5676 */       defCategory("LC", 14);
/*      */ 
/*      */       
/* 5679 */       defCategory("LD", 574);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5685 */       defRange("L1", 0, 255);
/* 5686 */       map.put("all", new CharPropertyFactory() { Pattern.CharProperty make() {
/* 5687 */               return new Pattern.All();
/*      */             } }
/*      */         );
/*      */       
/* 5691 */       defRange("ASCII", 0, 127);
/* 5692 */       defCtype("Alnum", 1792);
/* 5693 */       defCtype("Alpha", 768);
/* 5694 */       defCtype("Blank", 16384);
/* 5695 */       defCtype("Cntrl", 8192);
/* 5696 */       defRange("Digit", 48, 57);
/* 5697 */       defCtype("Graph", 5888);
/* 5698 */       defRange("Lower", 97, 122);
/* 5699 */       defRange("Print", 32, 126);
/* 5700 */       defCtype("Punct", 4096);
/* 5701 */       defCtype("Space", 2048);
/* 5702 */       defRange("Upper", 65, 90);
/* 5703 */       defCtype("XDigit", 32768);
/*      */ 
/*      */       
/* 5706 */       defClone("javaLowerCase", new CloneableProperty()
/*      */           {
/* 5708 */             boolean isSatisfiedBy(int param2Int) { return Character.isLowerCase(param2Int); } });
/* 5709 */       defClone("javaUpperCase", new CloneableProperty()
/*      */           {
/* 5711 */             boolean isSatisfiedBy(int param2Int) { return Character.isUpperCase(param2Int); } });
/* 5712 */       defClone("javaAlphabetic", new CloneableProperty()
/*      */           {
/* 5714 */             boolean isSatisfiedBy(int param2Int) { return Character.isAlphabetic(param2Int); } });
/* 5715 */       defClone("javaIdeographic", new CloneableProperty()
/*      */           {
/* 5717 */             boolean isSatisfiedBy(int param2Int) { return Character.isIdeographic(param2Int); } });
/* 5718 */       defClone("javaTitleCase", new CloneableProperty()
/*      */           {
/* 5720 */             boolean isSatisfiedBy(int param2Int) { return Character.isTitleCase(param2Int); } });
/* 5721 */       defClone("javaDigit", new CloneableProperty()
/*      */           {
/* 5723 */             boolean isSatisfiedBy(int param2Int) { return Character.isDigit(param2Int); } });
/* 5724 */       defClone("javaDefined", new CloneableProperty()
/*      */           {
/* 5726 */             boolean isSatisfiedBy(int param2Int) { return Character.isDefined(param2Int); } });
/* 5727 */       defClone("javaLetter", new CloneableProperty()
/*      */           {
/* 5729 */             boolean isSatisfiedBy(int param2Int) { return Character.isLetter(param2Int); } });
/* 5730 */       defClone("javaLetterOrDigit", new CloneableProperty()
/*      */           {
/* 5732 */             boolean isSatisfiedBy(int param2Int) { return Character.isLetterOrDigit(param2Int); } });
/* 5733 */       defClone("javaJavaIdentifierStart", new CloneableProperty()
/*      */           {
/* 5735 */             boolean isSatisfiedBy(int param2Int) { return Character.isJavaIdentifierStart(param2Int); } });
/* 5736 */       defClone("javaJavaIdentifierPart", new CloneableProperty()
/*      */           {
/* 5738 */             boolean isSatisfiedBy(int param2Int) { return Character.isJavaIdentifierPart(param2Int); } });
/* 5739 */       defClone("javaUnicodeIdentifierStart", new CloneableProperty()
/*      */           {
/* 5741 */             boolean isSatisfiedBy(int param2Int) { return Character.isUnicodeIdentifierStart(param2Int); } });
/* 5742 */       defClone("javaUnicodeIdentifierPart", new CloneableProperty()
/*      */           {
/* 5744 */             boolean isSatisfiedBy(int param2Int) { return Character.isUnicodeIdentifierPart(param2Int); } });
/* 5745 */       defClone("javaIdentifierIgnorable", new CloneableProperty()
/*      */           {
/* 5747 */             boolean isSatisfiedBy(int param2Int) { return Character.isIdentifierIgnorable(param2Int); } });
/* 5748 */       defClone("javaSpaceChar", new CloneableProperty()
/*      */           {
/* 5750 */             boolean isSatisfiedBy(int param2Int) { return Character.isSpaceChar(param2Int); } });
/* 5751 */       defClone("javaWhitespace", new CloneableProperty()
/*      */           {
/* 5753 */             boolean isSatisfiedBy(int param2Int) { return Character.isWhitespace(param2Int); } });
/* 5754 */       defClone("javaISOControl", new CloneableProperty()
/*      */           {
/* 5756 */             boolean isSatisfiedBy(int param2Int) { return Character.isISOControl(param2Int); } });
/* 5757 */       defClone("javaMirrored", new CloneableProperty() {
/*      */             boolean isSatisfiedBy(int param2Int) {
/* 5759 */               return Character.isMirrored(param2Int);
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Predicate<String> asPredicate() {
/* 5770 */     return paramString -> matcher(paramString).find();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stream<String> splitAsStream(final CharSequence input) {
/*      */     class MatcherIterator
/*      */       implements Iterator<String>
/*      */     {
/*      */       private final Matcher matcher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private int current;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private String nextElement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private int emptyElementCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       MatcherIterator() {
/* 5817 */         this.matcher = Pattern.this.matcher(input);
/*      */       }
/*      */       
/*      */       public String next() {
/* 5821 */         if (!hasNext()) {
/* 5822 */           throw new NoSuchElementException();
/*      */         }
/* 5824 */         if (this.emptyElementCount == 0) {
/* 5825 */           String str = this.nextElement;
/* 5826 */           this.nextElement = null;
/* 5827 */           return str;
/*      */         } 
/* 5829 */         this.emptyElementCount--;
/* 5830 */         return "";
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/* 5835 */         if (this.nextElement != null || this.emptyElementCount > 0) {
/* 5836 */           return true;
/*      */         }
/* 5838 */         if (this.current == input.length()) {
/* 5839 */           return false;
/*      */         }
/*      */ 
/*      */         
/* 5843 */         while (this.matcher.find()) {
/* 5844 */           this.nextElement = input.subSequence(this.current, this.matcher.start()).toString();
/* 5845 */           this.current = this.matcher.end();
/* 5846 */           if (!this.nextElement.isEmpty())
/* 5847 */             return true; 
/* 5848 */           if (this.current > 0)
/*      */           {
/* 5850 */             this.emptyElementCount++;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 5855 */         this.nextElement = input.subSequence(this.current, input.length()).toString();
/* 5856 */         this.current = input.length();
/* 5857 */         if (!this.nextElement.isEmpty()) {
/* 5858 */           return true;
/*      */         }
/*      */         
/* 5861 */         this.emptyElementCount = 0;
/* 5862 */         this.nextElement = null;
/* 5863 */         return false;
/*      */       }
/*      */     };
/*      */     
/* 5867 */     return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new MatcherIterator(), 272), false);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/regex/Pattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */