/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.naming.internal.FactoryEnumeration;
/*     */ import com.sun.naming.internal.ResourceManager;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Context;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ControlFactory
/*     */ {
/*     */   public abstract Control getControlInstance(Control paramControl) throws NamingException;
/*     */   
/*     */   public static Control getControlInstance(Control paramControl, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 140 */     FactoryEnumeration factoryEnumeration = ResourceManager.getFactories("java.naming.factory.control", paramHashtable, paramContext);
/*     */ 
/*     */     
/* 143 */     if (factoryEnumeration == null) {
/* 144 */       return paramControl;
/*     */     }
/*     */ 
/*     */     
/* 148 */     Control control = null;
/*     */     
/* 150 */     while (control == null && factoryEnumeration.hasMore()) {
/* 151 */       ControlFactory controlFactory = (ControlFactory)factoryEnumeration.next();
/* 152 */       control = controlFactory.getControlInstance(paramControl);
/*     */     } 
/*     */     
/* 155 */     return (control != null) ? control : paramControl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/ControlFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */