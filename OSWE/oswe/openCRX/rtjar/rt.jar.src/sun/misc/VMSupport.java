/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ import java.util.jar.Attributes;
/*    */ import java.util.jar.JarFile;
/*    */ import java.util.jar.Manifest;
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
/*    */ public class VMSupport
/*    */ {
/* 40 */   private static Properties agentProps = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized Properties getAgentProperties() {
/* 45 */     if (agentProps == null) {
/* 46 */       agentProps = new Properties();
/* 47 */       initAgentProperties(agentProps);
/*    */     } 
/* 49 */     return agentProps;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static native Properties initAgentProperties(Properties paramProperties);
/*    */ 
/*    */ 
/*    */   
/*    */   private static byte[] serializePropertiesToByteArray(Properties paramProperties) throws IOException {
/* 59 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
/*    */     
/* 61 */     Properties properties = new Properties();
/*    */ 
/*    */     
/* 64 */     Set<String> set = paramProperties.stringPropertyNames();
/* 65 */     for (String str1 : set) {
/* 66 */       String str2 = paramProperties.getProperty(str1);
/* 67 */       properties.put(str1, str2);
/*    */     } 
/*    */     
/* 70 */     properties.store(byteArrayOutputStream, (String)null);
/* 71 */     return byteArrayOutputStream.toByteArray();
/*    */   }
/*    */   
/*    */   public static byte[] serializePropertiesToByteArray() throws IOException {
/* 75 */     return serializePropertiesToByteArray(System.getProperties());
/*    */   }
/*    */   
/*    */   public static byte[] serializeAgentPropertiesToByteArray() throws IOException {
/* 79 */     return serializePropertiesToByteArray(getAgentProperties());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isClassPathAttributePresent(String paramString) {
/*    */     try {
/* 89 */       Manifest manifest = (new JarFile(paramString)).getManifest();
/* 90 */       if (manifest != null && 
/* 91 */         manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH) != null) {
/* 92 */         return true;
/*    */       }
/*    */       
/* 95 */       return false;
/* 96 */     } catch (IOException iOException) {
/* 97 */       throw new RuntimeException(iOException.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static native String getVMTemporaryDirectory();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/VMSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */