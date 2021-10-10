/*    */ package com.sun.xml.internal.ws.policy.privateutil;
/*    */ 
/*    */ import com.sun.istack.internal.logging.Logger;
/*    */ import java.lang.reflect.Field;
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
/*    */ public final class PolicyLogger
/*    */   extends Logger
/*    */ {
/*    */   private static final String POLICY_PACKAGE_ROOT = "com.sun.xml.internal.ws.policy";
/*    */   
/*    */   private PolicyLogger(String policyLoggerName, String className) {
/* 53 */     super(policyLoggerName, className);
/*    */   }
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
/*    */   public static PolicyLogger getLogger(Class<?> componentClass) {
/* 66 */     String componentClassName = componentClass.getName();
/*    */     
/* 68 */     if (componentClassName.startsWith("com.sun.xml.internal.ws.policy")) {
/* 69 */       return new PolicyLogger(getLoggingSubsystemName() + componentClassName.substring("com.sun.xml.internal.ws.policy".length()), componentClassName);
/*    */     }
/*    */     
/* 72 */     return new PolicyLogger(getLoggingSubsystemName() + "." + componentClassName, componentClassName);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getLoggingSubsystemName() {
/* 77 */     String loggingSubsystemName = "wspolicy";
/*    */ 
/*    */     
/*    */     try {
/* 81 */       Class<?> jaxwsConstants = Class.forName("com.sun.xml.internal.ws.util.Constants");
/* 82 */       Field loggingDomainField = jaxwsConstants.getField("LoggingDomain");
/* 83 */       Object loggingDomain = loggingDomainField.get(null);
/* 84 */       loggingSubsystemName = loggingDomain.toString().concat(".wspolicy");
/* 85 */     } catch (RuntimeException runtimeException) {
/*    */ 
/*    */     
/* 88 */     } catch (Exception exception) {}
/*    */ 
/*    */     
/* 91 */     return loggingSubsystemName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/privateutil/PolicyLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */