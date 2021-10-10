/*    */ package com.sun.org.apache.xml.internal.security.keys;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*    */ import java.io.PrintStream;
/*    */ import java.security.PublicKey;
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
/*    */ public class KeyUtils
/*    */ {
/*    */   public static void prinoutKeyInfo(KeyInfo paramKeyInfo, PrintStream paramPrintStream) throws XMLSecurityException {
/*    */     byte b;
/* 55 */     for (b = 0; b < paramKeyInfo.lengthKeyName(); b++) {
/* 56 */       KeyName keyName = paramKeyInfo.itemKeyName(b);
/*    */       
/* 58 */       paramPrintStream.println("KeyName(" + b + ")=\"" + keyName.getKeyName() + "\"");
/*    */     } 
/*    */     
/* 61 */     for (b = 0; b < paramKeyInfo.lengthKeyValue(); b++) {
/* 62 */       KeyValue keyValue = paramKeyInfo.itemKeyValue(b);
/* 63 */       PublicKey publicKey = keyValue.getPublicKey();
/*    */       
/* 65 */       paramPrintStream.println("KeyValue Nr. " + b);
/* 66 */       paramPrintStream.println(publicKey);
/*    */     } 
/*    */     
/* 69 */     for (b = 0; b < paramKeyInfo.lengthMgmtData(); b++) {
/* 70 */       MgmtData mgmtData = paramKeyInfo.itemMgmtData(b);
/*    */       
/* 72 */       paramPrintStream.println("MgmtData(" + b + ")=\"" + mgmtData.getMgmtData() + "\"");
/*    */     } 
/*    */     
/* 75 */     for (b = 0; b < paramKeyInfo.lengthX509Data(); b++) {
/* 76 */       X509Data x509Data = paramKeyInfo.itemX509Data(b);
/*    */       
/* 78 */       paramPrintStream.println("X509Data(" + b + ")=\"" + (x509Data.containsCertificate() ? "Certificate " : "") + (
/* 79 */           x509Data.containsIssuerSerial() ? "IssuerSerial " : "") + "\"");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/KeyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */