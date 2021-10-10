/*    */ package jdk.internal.org.objectweb.asm.commons;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleRemapper
/*    */   extends Remapper
/*    */ {
/*    */   private final Map<String, String> mapping;
/*    */   
/*    */   public SimpleRemapper(Map<String, String> paramMap) {
/* 75 */     this.mapping = paramMap;
/*    */   }
/*    */   
/*    */   public SimpleRemapper(String paramString1, String paramString2) {
/* 79 */     this.mapping = Collections.singletonMap(paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapMethodName(String paramString1, String paramString2, String paramString3) {
/* 84 */     String str = map(paramString1 + '.' + paramString2 + paramString3);
/* 85 */     return (str == null) ? paramString2 : str;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapFieldName(String paramString1, String paramString2, String paramString3) {
/* 90 */     String str = map(paramString1 + '.' + paramString2);
/* 91 */     return (str == null) ? paramString2 : str;
/*    */   }
/*    */ 
/*    */   
/*    */   public String map(String paramString) {
/* 96 */     return this.mapping.get(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/SimpleRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */