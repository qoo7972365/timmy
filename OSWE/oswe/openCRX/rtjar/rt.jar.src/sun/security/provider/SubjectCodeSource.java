/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.security.auth.Subject;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SubjectCodeSource
/*     */   extends CodeSource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6039418085604715275L;
/*     */   
/*  52 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*     */         public ResourceBundle run() {
/*  54 */           return 
/*  55 */             ResourceBundle.getBundle("sun.security.util.AuthResources");
/*     */         }
/*     */       });
/*     */   
/*     */   private Subject subject;
/*     */   private LinkedList<PolicyParser.PrincipalEntry> principals;
/*  61 */   private static final Class<?>[] PARAMS = new Class[] { String.class };
/*     */   
/*  63 */   private static final Debug debug = Debug.getInstance("auth", "\t[Auth Access]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader sysClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SubjectCodeSource(Subject paramSubject, LinkedList<PolicyParser.PrincipalEntry> paramLinkedList, URL paramURL, Certificate[] paramArrayOfCertificate) {
/*  93 */     super(paramURL, paramArrayOfCertificate);
/*  94 */     this.subject = paramSubject;
/*  95 */     this.principals = (paramLinkedList == null) ? new LinkedList<>() : new LinkedList<>(paramLinkedList);
/*     */ 
/*     */     
/*  98 */     this
/*  99 */       .sysClassLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public ClassLoader run() {
/* 101 */             return ClassLoader.getSystemClassLoader();
/*     */           }
/*     */         });
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
/*     */   LinkedList<PolicyParser.PrincipalEntry> getPrincipals() {
/* 118 */     return this.principals;
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
/*     */   Subject getSubject() {
/* 133 */     return this.subject;
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
/*     */   public boolean implies(CodeSource paramCodeSource) {
/* 170 */     LinkedList<PolicyParser.PrincipalEntry> linkedList = null;
/*     */     
/* 172 */     if (paramCodeSource == null || !(paramCodeSource instanceof SubjectCodeSource) || 
/*     */       
/* 174 */       !super.implies(paramCodeSource)) {
/*     */       
/* 176 */       if (debug != null)
/* 177 */         debug.println("\tSubjectCodeSource.implies: FAILURE 1"); 
/* 178 */       return false;
/*     */     } 
/*     */     
/* 181 */     SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (this.principals == null) {
/* 188 */       if (debug != null)
/* 189 */         debug.println("\tSubjectCodeSource.implies: PASS 1"); 
/* 190 */       return true;
/*     */     } 
/*     */     
/* 193 */     if (subjectCodeSource.getSubject() == null || subjectCodeSource
/* 194 */       .getSubject().getPrincipals().size() == 0) {
/* 195 */       if (debug != null)
/* 196 */         debug.println("\tSubjectCodeSource.implies: FAILURE 2"); 
/* 197 */       return false;
/*     */     } 
/*     */     
/* 200 */     ListIterator<PolicyParser.PrincipalEntry> listIterator = this.principals.listIterator(0);
/* 201 */     while (listIterator.hasNext()) {
/* 202 */       PolicyParser.PrincipalEntry principalEntry = listIterator.next();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 207 */         Class<?> clazz = Class.forName(principalEntry.principalClass, true, this.sysClassLoader);
/*     */         
/* 209 */         if (!Principal.class.isAssignableFrom(clazz))
/*     */         {
/* 211 */           throw new ClassCastException(principalEntry.principalClass + " is not a Principal");
/*     */         }
/*     */         
/* 214 */         Constructor<?> constructor = clazz.getConstructor(PARAMS);
/* 215 */         Principal principal = (Principal)constructor.newInstance(new Object[] { principalEntry.principalName });
/*     */ 
/*     */         
/* 218 */         if (!principal.implies(subjectCodeSource.getSubject())) {
/* 219 */           if (debug != null)
/* 220 */             debug.println("\tSubjectCodeSource.implies: FAILURE 3"); 
/* 221 */           return false;
/*     */         } 
/* 223 */         if (debug != null)
/* 224 */           debug.println("\tSubjectCodeSource.implies: PASS 2"); 
/* 225 */         return true;
/*     */       }
/* 227 */       catch (Exception exception) {
/*     */ 
/*     */ 
/*     */         
/* 231 */         if (linkedList == null) {
/*     */           
/* 233 */           if (subjectCodeSource.getSubject() == null) {
/* 234 */             if (debug != null) {
/* 235 */               debug.println("\tSubjectCodeSource.implies: FAILURE 4");
/*     */             }
/* 237 */             return false;
/*     */           } 
/*     */           
/* 240 */           Iterator<Principal> iterator = subjectCodeSource.getSubject().getPrincipals().iterator();
/*     */           
/* 242 */           linkedList = new LinkedList();
/* 243 */           while (iterator.hasNext()) {
/* 244 */             Principal principal = iterator.next();
/*     */             
/* 246 */             PolicyParser.PrincipalEntry principalEntry1 = new PolicyParser.PrincipalEntry(principal.getClass().getName(), principal.getName());
/* 247 */             linkedList.add(principalEntry1);
/*     */           } 
/*     */         } 
/*     */         
/* 251 */         if (!subjectListImpliesPrincipalEntry(linkedList, principalEntry)) {
/* 252 */           if (debug != null)
/* 253 */             debug.println("\tSubjectCodeSource.implies: FAILURE 5"); 
/* 254 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     if (debug != null)
/* 260 */       debug.println("\tSubjectCodeSource.implies: PASS 3"); 
/* 261 */     return true;
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
/*     */   private boolean subjectListImpliesPrincipalEntry(LinkedList<PolicyParser.PrincipalEntry> paramLinkedList, PolicyParser.PrincipalEntry paramPrincipalEntry) {
/* 288 */     ListIterator<PolicyParser.PrincipalEntry> listIterator = paramLinkedList.listIterator(0);
/* 289 */     while (listIterator.hasNext()) {
/* 290 */       PolicyParser.PrincipalEntry principalEntry = listIterator.next();
/*     */       
/* 292 */       if (paramPrincipalEntry.getPrincipalClass()
/* 293 */         .equals("WILDCARD_PRINCIPAL_CLASS") || paramPrincipalEntry
/* 294 */         .getPrincipalClass().equals(principalEntry.getPrincipalClass()))
/*     */       {
/* 296 */         if (paramPrincipalEntry.getPrincipalName()
/* 297 */           .equals("WILDCARD_PRINCIPAL_NAME") || paramPrincipalEntry
/* 298 */           .getPrincipalName().equals(principalEntry.getPrincipalName()))
/* 299 */           return true; 
/*     */       }
/*     */     } 
/* 302 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 323 */     if (paramObject == this) {
/* 324 */       return true;
/*     */     }
/* 326 */     if (!super.equals(paramObject)) {
/* 327 */       return false;
/*     */     }
/* 329 */     if (!(paramObject instanceof SubjectCodeSource)) {
/* 330 */       return false;
/*     */     }
/* 332 */     SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramObject;
/*     */ 
/*     */     
/*     */     try {
/* 336 */       if (getSubject() != subjectCodeSource.getSubject())
/* 337 */         return false; 
/* 338 */     } catch (SecurityException securityException) {
/* 339 */       return false;
/*     */     } 
/*     */     
/* 342 */     if ((this.principals == null && subjectCodeSource.principals != null) || (this.principals != null && subjectCodeSource.principals == null))
/*     */     {
/* 344 */       return false;
/*     */     }
/* 346 */     if (this.principals != null && subjectCodeSource.principals != null && (
/* 347 */       !this.principals.containsAll(subjectCodeSource.principals) || 
/* 348 */       !subjectCodeSource.principals.containsAll(this.principals)))
/*     */     {
/* 350 */       return false;
/*     */     }
/*     */     
/* 353 */     return true;
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
/* 364 */     return super.hashCode();
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
/* 375 */     String str = super.toString();
/* 376 */     if (getSubject() != null) {
/* 377 */       if (debug != null) {
/* 378 */         final Subject finalSubject = getSubject();
/*     */ 
/*     */         
/* 381 */         str = str + "\n" + (String)AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */               public String run() {
/* 383 */                 return finalSubject.toString();
/*     */               }
/*     */             });
/*     */       } else {
/* 387 */         str = str + "\n" + getSubject().toString();
/*     */       } 
/*     */     }
/* 390 */     if (this.principals != null) {
/* 391 */       ListIterator<PolicyParser.PrincipalEntry> listIterator = this.principals.listIterator();
/* 392 */       while (listIterator.hasNext()) {
/* 393 */         PolicyParser.PrincipalEntry principalEntry = listIterator.next();
/*     */ 
/*     */         
/* 396 */         str = str + rb.getString("NEWLINE") + principalEntry.getPrincipalClass() + " " + principalEntry.getPrincipalName();
/*     */       } 
/*     */     } 
/* 399 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/SubjectCodeSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */