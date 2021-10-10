/*     */ package javax.management.remote;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMXPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4184480100214577411L;
/*     */   private String name;
/*     */   
/*     */   public JMXPrincipal(String paramString) {
/*  70 */     validate(paramString);
/*  71 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return "JMXPrincipal:  " + this.name;
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
/*     */   public boolean equals(Object paramObject) {
/* 111 */     if (paramObject == null) {
/* 112 */       return false;
/*     */     }
/* 114 */     if (this == paramObject) {
/* 115 */       return true;
/*     */     }
/* 117 */     if (!(paramObject instanceof JMXPrincipal))
/* 118 */       return false; 
/* 119 */     JMXPrincipal jMXPrincipal = (JMXPrincipal)paramObject;
/*     */     
/* 121 */     return getName().equals(jMXPrincipal.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this.name.hashCode();
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 136 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 137 */     String str = (String)getField.get("name", (Object)null);
/*     */     try {
/* 139 */       validate(str);
/* 140 */       this.name = str;
/* 141 */     } catch (NullPointerException nullPointerException) {
/* 142 */       throw new InvalidObjectException(nullPointerException.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validate(String paramString) throws NullPointerException {
/* 147 */     if (paramString == null)
/* 148 */       throw new NullPointerException("illegal null input"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */