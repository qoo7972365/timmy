/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NoInitialContextException;
/*     */ import javax.naming.NotContextException;
/*     */ import javax.naming.directory.InitialDirContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InitialLdapContext
/*     */   extends InitialDirContext
/*     */   implements LdapContext
/*     */ {
/*     */   private static final String BIND_CONTROLS_PROPERTY = "java.naming.ldap.control.connect";
/*     */   
/*     */   public InitialLdapContext() throws NamingException {
/* 102 */     super((Hashtable<?, ?>)null);
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
/*     */   public InitialLdapContext(Hashtable<?, ?> paramHashtable, Control[] paramArrayOfControl) throws NamingException {
/* 136 */     super(true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     Hashtable<String, Control[]> hashtable = (paramHashtable == null) ? new Hashtable<>(11) : (Hashtable)paramHashtable.clone();
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (paramArrayOfControl != null) {
/* 146 */       Control[] arrayOfControl = new Control[paramArrayOfControl.length];
/* 147 */       System.arraycopy(paramArrayOfControl, 0, arrayOfControl, 0, paramArrayOfControl.length);
/* 148 */       hashtable.put("java.naming.ldap.control.connect", arrayOfControl);
/*     */     } 
/*     */     
/* 151 */     hashtable.put("java.naming.ldap.version", "3");
/*     */ 
/*     */     
/* 154 */     init(hashtable);
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
/*     */   private LdapContext getDefaultLdapInitCtx() throws NamingException {
/* 166 */     Context context = getDefaultInitCtx();
/*     */     
/* 168 */     if (!(context instanceof LdapContext)) {
/* 169 */       if (context == null) {
/* 170 */         throw new NoInitialContextException();
/*     */       }
/* 172 */       throw new NotContextException("Not an instance of LdapContext");
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return (LdapContext)context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedResponse extendedOperation(ExtendedRequest paramExtendedRequest) throws NamingException {
/* 184 */     return getDefaultLdapInitCtx().extendedOperation(paramExtendedRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public LdapContext newInstance(Control[] paramArrayOfControl) throws NamingException {
/* 189 */     return getDefaultLdapInitCtx().newInstance(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public void reconnect(Control[] paramArrayOfControl) throws NamingException {
/* 193 */     getDefaultLdapInitCtx().reconnect(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public Control[] getConnectControls() throws NamingException {
/* 197 */     return getDefaultLdapInitCtx().getConnectControls();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRequestControls(Control[] paramArrayOfControl) throws NamingException {
/* 202 */     getDefaultLdapInitCtx().setRequestControls(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public Control[] getRequestControls() throws NamingException {
/* 206 */     return getDefaultLdapInitCtx().getRequestControls();
/*     */   }
/*     */   
/*     */   public Control[] getResponseControls() throws NamingException {
/* 210 */     return getDefaultLdapInitCtx().getResponseControls();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/InitialLdapContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */