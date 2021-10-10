/*    */ package com.sun.xml.internal.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.spi.PrefixMapper;
/*    */ import java.util.HashMap;
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
/*    */ public class EncodingPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 38 */   private static final Map<String, String> prefixMap = new HashMap<>();
/*    */   
/*    */   static {
/* 41 */     prefixMap.put("http://schemas.xmlsoap.org/ws/2004/09/policy/encoding", "wspe");
/* 42 */     prefixMap.put("http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization", "wsoma");
/* 43 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/encoding/client", "cenc");
/* 44 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/fastinfoset/service", "fi");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 48 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/policy/EncodingPrefixMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */