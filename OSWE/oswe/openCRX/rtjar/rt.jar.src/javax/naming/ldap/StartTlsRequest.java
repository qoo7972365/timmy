/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.naming.internal.VersionHelper;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceLoader;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.NamingException;
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
/*     */ public class StartTlsRequest
/*     */   implements ExtendedRequest
/*     */ {
/*     */   public static final String OID = "1.3.6.1.4.1.1466.20037";
/*     */   private static final long serialVersionUID = 4441679576360753397L;
/*     */   
/*     */   public String getID() {
/* 107 */     return "1.3.6.1.4.1.1466.20037";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncodedValue() {
/* 118 */     return null;
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
/*     */   public ExtendedResponse createExtendedResponse(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws NamingException {
/* 175 */     if (paramString != null && !paramString.equals("1.3.6.1.4.1.1466.20037")) {
/* 176 */       throw new ConfigurationException("Start TLS received the following response instead of 1.3.6.1.4.1.1466.20037: " + paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 181 */     StartTlsResponse startTlsResponse = null;
/*     */     
/* 183 */     ServiceLoader<StartTlsResponse> serviceLoader = ServiceLoader.load(StartTlsResponse.class, 
/* 184 */         getContextClassLoader());
/* 185 */     Iterator<StartTlsResponse> iterator = serviceLoader.iterator();
/*     */     
/* 187 */     while (startTlsResponse == null && privilegedHasNext(iterator)) {
/* 188 */       startTlsResponse = iterator.next();
/*     */     }
/* 190 */     if (startTlsResponse != null) {
/* 191 */       return startTlsResponse;
/*     */     }
/*     */     try {
/* 194 */       VersionHelper versionHelper = VersionHelper.getVersionHelper();
/* 195 */       Class<?> clazz = versionHelper.loadClass("com.sun.jndi.ldap.ext.StartTlsResponseImpl");
/*     */ 
/*     */       
/* 198 */       startTlsResponse = (StartTlsResponse)clazz.newInstance();
/*     */     }
/* 200 */     catch (IllegalAccessException illegalAccessException) {
/* 201 */       throw wrapException(illegalAccessException);
/*     */     }
/* 203 */     catch (InstantiationException instantiationException) {
/* 204 */       throw wrapException(instantiationException);
/*     */     }
/* 206 */     catch (ClassNotFoundException classNotFoundException) {
/* 207 */       throw wrapException(classNotFoundException);
/*     */     } 
/*     */     
/* 210 */     return startTlsResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ConfigurationException wrapException(Exception paramException) {
/* 218 */     ConfigurationException configurationException = new ConfigurationException("Cannot load implementation of javax.naming.ldap.StartTlsResponse");
/*     */ 
/*     */     
/* 221 */     configurationException.setRootCause(paramException);
/* 222 */     return configurationException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassLoader getContextClassLoader() {
/* 229 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public ClassLoader run() {
/* 232 */             return Thread.currentThread().getContextClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static final boolean privilegedHasNext(final Iterator<StartTlsResponse> iter) {
/* 239 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/* 242 */             return Boolean.valueOf(iter.hasNext());
/*     */           }
/*     */         });
/* 245 */     return bool.booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/StartTlsRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */