/*      */ package javax.security.auth;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.DomainCombiner;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import sun.security.util.ResourcesMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Subject
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -8308522755600156056L;
/*      */   Set<Principal> principals;
/*      */   transient Set<Object> pubCredentials;
/*      */   transient Set<Object> privCredentials;
/*      */   private volatile boolean readOnly = false;
/*      */   private static final int PRINCIPAL_SET = 1;
/*      */   private static final int PUB_CREDENTIAL_SET = 2;
/*      */   private static final int PRIV_CREDENTIAL_SET = 3;
/*  134 */   private static final ProtectionDomain[] NULL_PD_ARRAY = new ProtectionDomain[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Subject() {
/*  156 */     this
/*  157 */       .principals = Collections.synchronizedSet(new SecureSet<>(this, 1));
/*  158 */     this
/*  159 */       .pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2));
/*  160 */     this
/*  161 */       .privCredentials = Collections.synchronizedSet(new SecureSet(this, 3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Subject(boolean paramBoolean, Set<? extends Principal> paramSet, Set<?> paramSet1, Set<?> paramSet2) {
/*  203 */     if (paramSet == null || paramSet1 == null || paramSet2 == null)
/*      */     {
/*      */       
/*  206 */       throw new NullPointerException(
/*  207 */           ResourcesMgr.getString("invalid.null.input.s."));
/*      */     }
/*  209 */     this.principals = Collections.synchronizedSet(new SecureSet<>(this, 1, paramSet));
/*      */     
/*  211 */     this.pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2, paramSet1));
/*      */     
/*  213 */     this.privCredentials = Collections.synchronizedSet(new SecureSet(this, 3, paramSet2));
/*      */     
/*  215 */     this.readOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly() {
/*  239 */     SecurityManager securityManager = System.getSecurityManager();
/*  240 */     if (securityManager != null) {
/*  241 */       securityManager.checkPermission(AuthPermissionHolder.SET_READ_ONLY_PERMISSION);
/*      */     }
/*      */     
/*  244 */     this.readOnly = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  255 */     return this.readOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Subject getSubject(final AccessControlContext acc) {
/*  285 */     SecurityManager securityManager = System.getSecurityManager();
/*  286 */     if (securityManager != null) {
/*  287 */       securityManager.checkPermission(AuthPermissionHolder.GET_SUBJECT_PERMISSION);
/*      */     }
/*      */     
/*  290 */     if (acc == null) {
/*  291 */       throw new NullPointerException(
/*  292 */           ResourcesMgr.getString("invalid.null.AccessControlContext.provided"));
/*      */     }
/*      */ 
/*      */     
/*  296 */     return 
/*  297 */       AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>() {
/*      */           public Subject run() {
/*  299 */             DomainCombiner domainCombiner = acc.getDomainCombiner();
/*  300 */             if (!(domainCombiner instanceof SubjectDomainCombiner))
/*  301 */               return null; 
/*  302 */             SubjectDomainCombiner subjectDomainCombiner = (SubjectDomainCombiner)domainCombiner;
/*  303 */             return subjectDomainCombiner.getSubject();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T doAs(Subject paramSubject, PrivilegedAction<T> paramPrivilegedAction) {
/*  346 */     SecurityManager securityManager = System.getSecurityManager();
/*  347 */     if (securityManager != null) {
/*  348 */       securityManager.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
/*      */     }
/*  350 */     if (paramPrivilegedAction == null) {
/*  351 */       throw new NullPointerException(
/*  352 */           ResourcesMgr.getString("invalid.null.action.provided"));
/*      */     }
/*      */ 
/*      */     
/*  356 */     AccessControlContext accessControlContext = AccessController.getContext();
/*      */ 
/*      */     
/*  359 */     return 
/*  360 */       AccessController.doPrivileged(paramPrivilegedAction, 
/*  361 */         createContext(paramSubject, accessControlContext));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T doAs(Subject paramSubject, PrivilegedExceptionAction<T> paramPrivilegedExceptionAction) throws PrivilegedActionException {
/*  408 */     SecurityManager securityManager = System.getSecurityManager();
/*  409 */     if (securityManager != null) {
/*  410 */       securityManager.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
/*      */     }
/*      */     
/*  413 */     if (paramPrivilegedExceptionAction == null) {
/*  414 */       throw new NullPointerException(
/*  415 */           ResourcesMgr.getString("invalid.null.action.provided"));
/*      */     }
/*      */     
/*  418 */     AccessControlContext accessControlContext = AccessController.getContext();
/*      */ 
/*      */     
/*  421 */     return 
/*  422 */       AccessController.doPrivileged(paramPrivilegedExceptionAction, 
/*  423 */         createContext(paramSubject, accessControlContext));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T doAsPrivileged(Subject paramSubject, PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext) {
/*  465 */     SecurityManager securityManager = System.getSecurityManager();
/*  466 */     if (securityManager != null) {
/*  467 */       securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
/*      */     }
/*      */     
/*  470 */     if (paramPrivilegedAction == null) {
/*  471 */       throw new NullPointerException(
/*  472 */           ResourcesMgr.getString("invalid.null.action.provided"));
/*      */     }
/*      */ 
/*      */     
/*  476 */     AccessControlContext accessControlContext = (paramAccessControlContext == null) ? new AccessControlContext(NULL_PD_ARRAY) : paramAccessControlContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  482 */     return 
/*  483 */       AccessController.doPrivileged(paramPrivilegedAction, 
/*  484 */         createContext(paramSubject, accessControlContext));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T doAsPrivileged(Subject paramSubject, PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext) throws PrivilegedActionException {
/*  532 */     SecurityManager securityManager = System.getSecurityManager();
/*  533 */     if (securityManager != null) {
/*  534 */       securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
/*      */     }
/*      */     
/*  537 */     if (paramPrivilegedExceptionAction == null) {
/*  538 */       throw new NullPointerException(
/*  539 */           ResourcesMgr.getString("invalid.null.action.provided"));
/*      */     }
/*      */     
/*  542 */     AccessControlContext accessControlContext = (paramAccessControlContext == null) ? new AccessControlContext(NULL_PD_ARRAY) : paramAccessControlContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  548 */     return 
/*  549 */       AccessController.doPrivileged(paramPrivilegedExceptionAction, 
/*  550 */         createContext(paramSubject, accessControlContext));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AccessControlContext createContext(final Subject subject, final AccessControlContext acc) {
/*  557 */     return 
/*  558 */       AccessController.<AccessControlContext>doPrivileged(new PrivilegedAction<AccessControlContext>() {
/*      */           public AccessControlContext run() {
/*  560 */             if (subject == null) {
/*  561 */               return new AccessControlContext(acc, null);
/*      */             }
/*  563 */             return new AccessControlContext(acc, new SubjectDomainCombiner(subject));
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Principal> getPrincipals() {
/*  589 */     return this.principals;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Principal> Set<T> getPrincipals(Class<T> paramClass) {
/*  618 */     if (paramClass == null) {
/*  619 */       throw new NullPointerException(
/*  620 */           ResourcesMgr.getString("invalid.null.Class.provided"));
/*      */     }
/*      */ 
/*      */     
/*  624 */     return new ClassSet<>(1, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Object> getPublicCredentials() {
/*  645 */     return this.pubCredentials;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Object> getPrivateCredentials() {
/*  685 */     return this.privCredentials;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Set<T> getPublicCredentials(Class<T> paramClass) {
/*  714 */     if (paramClass == null) {
/*  715 */       throw new NullPointerException(
/*  716 */           ResourcesMgr.getString("invalid.null.Class.provided"));
/*      */     }
/*      */ 
/*      */     
/*  720 */     return new ClassSet<>(2, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Set<T> getPrivateCredentials(Class<T> paramClass) {
/*  761 */     if (paramClass == null) {
/*  762 */       throw new NullPointerException(
/*  763 */           ResourcesMgr.getString("invalid.null.Class.provided"));
/*      */     }
/*      */ 
/*      */     
/*  767 */     return new ClassSet<>(3, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  793 */     if (paramObject == null) {
/*  794 */       return false;
/*      */     }
/*  796 */     if (this == paramObject) {
/*  797 */       return true;
/*      */     }
/*  799 */     if (paramObject instanceof Subject) {
/*      */       HashSet<Principal> hashSet; HashSet hashSet1, hashSet2;
/*  801 */       Subject subject = (Subject)paramObject;
/*      */ 
/*      */ 
/*      */       
/*  805 */       synchronized (subject.principals) {
/*      */         
/*  807 */         hashSet = new HashSet<>(subject.principals);
/*      */       } 
/*  809 */       if (!this.principals.equals(hashSet)) {
/*  810 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  814 */       synchronized (subject.pubCredentials) {
/*      */         
/*  816 */         hashSet1 = new HashSet(subject.pubCredentials);
/*      */       } 
/*  818 */       if (!this.pubCredentials.equals(hashSet1)) {
/*  819 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  823 */       synchronized (subject.privCredentials) {
/*      */         
/*  825 */         hashSet2 = new HashSet(subject.privCredentials);
/*      */       } 
/*  827 */       if (!this.privCredentials.equals(hashSet2)) {
/*  828 */         return false;
/*      */       }
/*  830 */       return true;
/*      */     } 
/*  832 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  843 */     return toString(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String toString(boolean paramBoolean) {
/*  853 */     String str1 = ResourcesMgr.getString("Subject.");
/*  854 */     String str2 = "";
/*      */     
/*  856 */     synchronized (this.principals) {
/*  857 */       Iterator<Principal> iterator = this.principals.iterator();
/*  858 */       while (iterator.hasNext()) {
/*  859 */         Principal principal = iterator.next();
/*      */         
/*  861 */         str2 = str2 + ResourcesMgr.getString(".Principal.") + principal.toString() + ResourcesMgr.getString("NEWLINE");
/*      */       } 
/*      */     } 
/*      */     
/*  865 */     synchronized (this.pubCredentials) {
/*  866 */       Iterator<Object> iterator = this.pubCredentials.iterator();
/*  867 */       while (iterator.hasNext()) {
/*  868 */         Object object = iterator.next();
/*      */ 
/*      */         
/*  871 */         str2 = str2 + ResourcesMgr.getString(".Public.Credential.") + object.toString() + ResourcesMgr.getString("NEWLINE");
/*      */       } 
/*      */     } 
/*      */     
/*  875 */     if (paramBoolean) {
/*  876 */       synchronized (this.privCredentials) {
/*  877 */         Iterator<Object> iterator = this.privCredentials.iterator();
/*  878 */         while (iterator.hasNext()) {
/*      */           try {
/*  880 */             Object object = iterator.next();
/*      */ 
/*      */ 
/*      */             
/*  884 */             str2 = str2 + ResourcesMgr.getString(".Private.Credential.") + object.toString() + ResourcesMgr.getString("NEWLINE");
/*  885 */           } catch (SecurityException securityException) {
/*      */             
/*  887 */             str2 = str2 + ResourcesMgr.getString(".Private.Credential.inaccessible.");
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  893 */     return str1 + str2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  919 */     int i = 0;
/*      */     
/*  921 */     synchronized (this.principals) {
/*  922 */       Iterator<Principal> iterator = this.principals.iterator();
/*  923 */       while (iterator.hasNext()) {
/*  924 */         Principal principal = iterator.next();
/*  925 */         i ^= principal.hashCode();
/*      */       } 
/*      */     } 
/*      */     
/*  929 */     synchronized (this.pubCredentials) {
/*  930 */       Iterator iterator = this.pubCredentials.iterator();
/*  931 */       while (iterator.hasNext()) {
/*  932 */         i ^= getCredHashCode(iterator.next());
/*      */       }
/*      */     } 
/*  935 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCredHashCode(Object paramObject) {
/*      */     try {
/*  943 */       return paramObject.hashCode();
/*  944 */     } catch (IllegalStateException illegalStateException) {
/*  945 */       return paramObject.getClass().toString().hashCode();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  954 */     synchronized (this.principals) {
/*  955 */       paramObjectOutputStream.defaultWriteObject();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  966 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */     
/*  968 */     this.readOnly = getField.get("readOnly", false);
/*      */     
/*  970 */     Set<? extends Principal> set = (Set)getField.get("principals", (Object)null);
/*      */ 
/*      */     
/*  973 */     if (set == null) {
/*  974 */       throw new NullPointerException(
/*  975 */           ResourcesMgr.getString("invalid.null.input.s."));
/*      */     }
/*      */     try {
/*  978 */       this.principals = Collections.synchronizedSet(new SecureSet<>(this, 1, set));
/*      */     }
/*  980 */     catch (NullPointerException nullPointerException) {
/*      */ 
/*      */       
/*  983 */       this
/*  984 */         .principals = Collections.synchronizedSet(new SecureSet<>(this, 1));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  989 */     this
/*  990 */       .pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2));
/*  991 */     this
/*  992 */       .privCredentials = Collections.synchronizedSet(new SecureSet(this, 3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SecureSet<E>
/*      */     extends AbstractSet<E>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7911754171111800359L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("this$0", Subject.class), new ObjectStreamField("elements", LinkedList.class), new ObjectStreamField("which", int.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Subject subject;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LinkedList<E> elements;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int which;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SecureSet(Subject param1Subject, int param1Int) {
/* 1032 */       this.subject = param1Subject;
/* 1033 */       this.which = param1Int;
/* 1034 */       this.elements = new LinkedList<>();
/*      */     }
/*      */     
/*      */     SecureSet(Subject param1Subject, int param1Int, Set<? extends E> param1Set) {
/* 1038 */       this.subject = param1Subject;
/* 1039 */       this.which = param1Int;
/* 1040 */       this.elements = new LinkedList<>(param1Set);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1044 */       return this.elements.size();
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 1048 */       final LinkedList<E> list = this.elements;
/* 1049 */       return new Iterator<E>() {
/* 1050 */           ListIterator<E> i = list.listIterator(0);
/*      */           public boolean hasNext() {
/* 1052 */             return this.i.hasNext();
/*      */           }
/*      */           public E next() {
/* 1055 */             if (Subject.SecureSet.this.which != 3) {
/* 1056 */               return this.i.next();
/*      */             }
/*      */             
/* 1059 */             SecurityManager securityManager = System.getSecurityManager();
/* 1060 */             if (securityManager != null) {
/*      */               try {
/* 1062 */                 securityManager.checkPermission(new PrivateCredentialPermission(list
/* 1063 */                       .get(this.i.nextIndex()).getClass().getName(), Subject.SecureSet.this.subject
/* 1064 */                       .getPrincipals()));
/* 1065 */               } catch (SecurityException securityException) {
/* 1066 */                 this.i.next();
/* 1067 */                 throw securityException;
/*      */               } 
/*      */             }
/* 1070 */             return this.i.next();
/*      */           }
/*      */ 
/*      */           
/*      */           public void remove() {
/* 1075 */             if (Subject.SecureSet.this.subject.isReadOnly()) {
/* 1076 */               throw new IllegalStateException(
/* 1077 */                   ResourcesMgr.getString("Subject.is.read.only"));
/*      */             }
/*      */             
/* 1080 */             SecurityManager securityManager = System.getSecurityManager();
/* 1081 */             if (securityManager != null) {
/* 1082 */               switch (Subject.SecureSet.this.which) {
/*      */                 case 1:
/* 1084 */                   securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
/*      */                   break;
/*      */                 case 2:
/* 1087 */                   securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
/*      */                   break;
/*      */                 default:
/* 1090 */                   securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
/*      */                   break;
/*      */               } 
/*      */             }
/* 1094 */             this.i.remove();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(E param1E) {
/* 1101 */       if (this.subject.isReadOnly()) {
/* 1102 */         throw new IllegalStateException(
/* 1103 */             ResourcesMgr.getString("Subject.is.read.only"));
/*      */       }
/*      */       
/* 1106 */       SecurityManager securityManager = System.getSecurityManager();
/* 1107 */       if (securityManager != null) {
/* 1108 */         switch (this.which) {
/*      */           case 1:
/* 1110 */             securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
/*      */             break;
/*      */           case 2:
/* 1113 */             securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
/*      */             break;
/*      */           default:
/* 1116 */             securityManager.checkPermission(Subject.AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
/*      */             break;
/*      */         } 
/*      */       
/*      */       }
/* 1121 */       switch (this.which) {
/*      */         case 1:
/* 1123 */           if (!(param1E instanceof Principal)) {
/* 1124 */             throw new SecurityException(
/* 1125 */                 ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.java.security.Principal.to.a.Subject.s.Principal.Set"));
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1134 */       if (!this.elements.contains(param1E)) {
/* 1135 */         return this.elements.add(param1E);
/*      */       }
/* 1137 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object) {
/* 1142 */       final Iterator<E> e = iterator();
/* 1143 */       while (iterator.hasNext()) {
/*      */         Object object;
/* 1145 */         if (this.which != 3) {
/* 1146 */           E e = iterator.next();
/*      */         } else {
/*      */           
/* 1149 */           object = AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction<E>() {
/*      */                 public E run() {
/* 1151 */                   return e.next();
/*      */                 }
/*      */               });
/*      */         } 
/*      */         
/* 1156 */         if (object == null) {
/* 1157 */           if (param1Object == null) {
/* 1158 */             iterator.remove();
/* 1159 */             return true;
/*      */           }  continue;
/* 1161 */         }  if (object.equals(param1Object)) {
/* 1162 */           iterator.remove();
/* 1163 */           return true;
/*      */         } 
/*      */       } 
/* 1166 */       return false;
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 1170 */       final Iterator<E> e = iterator();
/* 1171 */       while (iterator.hasNext()) {
/*      */         Object object;
/* 1173 */         if (this.which != 3) {
/* 1174 */           E e = iterator.next();
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 1183 */           SecurityManager securityManager = System.getSecurityManager();
/* 1184 */           if (securityManager != null) {
/* 1185 */             securityManager.checkPermission(new PrivateCredentialPermission(param1Object
/* 1186 */                   .getClass().getName(), this.subject
/* 1187 */                   .getPrincipals()));
/*      */           }
/*      */           
/* 1190 */           object = AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction<E>() {
/*      */                 public E run() {
/* 1192 */                   return e.next();
/*      */                 }
/*      */               });
/*      */         } 
/*      */         
/* 1197 */         if (object == null) {
/* 1198 */           if (param1Object == null)
/* 1199 */             return true;  continue;
/*      */         } 
/* 1201 */         if (object.equals(param1Object)) {
/* 1202 */           return true;
/*      */         }
/*      */       } 
/* 1205 */       return false;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 1209 */       Objects.requireNonNull(param1Collection);
/* 1210 */       boolean bool = false;
/* 1211 */       final Iterator<E> e = iterator();
/* 1212 */       while (iterator.hasNext()) {
/*      */         Object object;
/* 1214 */         if (this.which != 3) {
/* 1215 */           E e = iterator.next();
/*      */         } else {
/*      */           
/* 1218 */           object = AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction<E>() {
/*      */                 public E run() {
/* 1220 */                   return e.next();
/*      */                 }
/*      */               });
/*      */         } 
/*      */         
/* 1225 */         Iterator<?> iterator1 = param1Collection.iterator();
/* 1226 */         while (iterator1.hasNext()) {
/* 1227 */           Object object1 = iterator1.next();
/* 1228 */           if (object == null) {
/* 1229 */             if (object1 == null) {
/* 1230 */               iterator.remove();
/* 1231 */               bool = true; break;
/*      */             }  continue;
/*      */           } 
/* 1234 */           if (object.equals(object1)) {
/* 1235 */             iterator.remove();
/* 1236 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1241 */       return bool;
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 1245 */       Objects.requireNonNull(param1Collection);
/* 1246 */       boolean bool1 = false;
/* 1247 */       boolean bool2 = false;
/* 1248 */       final Iterator<E> e = iterator();
/* 1249 */       while (iterator.hasNext()) {
/* 1250 */         Object object; bool2 = false;
/*      */         
/* 1252 */         if (this.which != 3) {
/* 1253 */           E e = iterator.next();
/*      */         } else {
/*      */           
/* 1256 */           object = AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction<E>() {
/*      */                 public E run() {
/* 1258 */                   return e.next();
/*      */                 }
/*      */               });
/*      */         } 
/*      */         
/* 1263 */         Iterator<?> iterator1 = param1Collection.iterator();
/* 1264 */         while (iterator1.hasNext()) {
/* 1265 */           Object object1 = iterator1.next();
/* 1266 */           if (object == null) {
/* 1267 */             if (object1 == null) {
/* 1268 */               bool2 = true; break;
/*      */             }  continue;
/*      */           } 
/* 1271 */           if (object.equals(object1)) {
/* 1272 */             bool2 = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1277 */         if (!bool2) {
/* 1278 */           iterator.remove();
/* 1279 */           bool2 = false;
/* 1280 */           bool1 = true;
/*      */         } 
/*      */       } 
/* 1283 */       return bool1;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1287 */       final Iterator<E> e = iterator();
/* 1288 */       while (iterator.hasNext()) {
/*      */         
/* 1290 */         if (this.which != 3) {
/* 1291 */           E e = iterator.next();
/*      */         } else {
/*      */           
/* 1294 */           Object object = AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction<E>() {
/*      */                 public E run() {
/* 1296 */                   return e.next();
/*      */                 }
/*      */               });
/*      */         } 
/* 1300 */         iterator.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 1318 */       if (this.which == 3) {
/*      */         
/* 1320 */         Iterator<E> iterator = iterator();
/* 1321 */         while (iterator.hasNext()) {
/* 1322 */           iterator.next();
/*      */         }
/*      */       } 
/* 1325 */       ObjectOutputStream.PutField putField = param1ObjectOutputStream.putFields();
/* 1326 */       putField.put("this$0", this.subject);
/* 1327 */       putField.put("elements", this.elements);
/* 1328 */       putField.put("which", this.which);
/* 1329 */       param1ObjectOutputStream.writeFields();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 1336 */       ObjectInputStream.GetField getField = param1ObjectInputStream.readFields();
/* 1337 */       this.subject = (Subject)getField.get("this$0", (Object)null);
/* 1338 */       this.which = getField.get("which", 0);
/*      */       
/* 1340 */       LinkedList<? extends E> linkedList = (LinkedList)getField.get("elements", (Object)null);
/* 1341 */       if (linkedList.getClass() != LinkedList.class) {
/* 1342 */         this.elements = new LinkedList<>(linkedList);
/*      */       } else {
/* 1344 */         this.elements = (LinkedList)linkedList;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class ClassSet<T>
/*      */     extends AbstractSet<T>
/*      */   {
/*      */     private int which;
/*      */     
/*      */     private Class<T> c;
/*      */     
/*      */     private Set<T> set;
/*      */     
/*      */     ClassSet(int param1Int, Class<T> param1Class) {
/* 1360 */       this.which = param1Int;
/* 1361 */       this.c = param1Class;
/* 1362 */       this.set = new HashSet<>();
/*      */       
/* 1364 */       switch (param1Int) {
/*      */         case 1:
/* 1366 */           synchronized (Subject.this.principals) { populateSet(); }
/*      */            return;
/*      */         case 2:
/* 1369 */           synchronized (Subject.this.pubCredentials) { populateSet(); }
/*      */            return;
/*      */       } 
/* 1372 */       synchronized (Subject.this.privCredentials) { populateSet(); }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void populateSet() {
/*      */       final Iterator<Principal> iterator;
/* 1380 */       switch (this.which) {
/*      */         case 1:
/* 1382 */           iterator = Subject.this.principals.iterator();
/*      */           break;
/*      */         case 2:
/* 1385 */           iterator = Subject.this.pubCredentials.iterator();
/*      */           break;
/*      */         default:
/* 1388 */           iterator = Subject.this.privCredentials.iterator();
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1395 */       while (iterator.hasNext()) {
/*      */         T t;
/* 1397 */         if (this.which == 3) {
/*      */           
/* 1399 */           t = AccessController.doPrivileged(new PrivilegedAction() {
/*      */                 public Object run() {
/* 1401 */                   return iterator.next();
/*      */                 }
/*      */               });
/*      */         } else {
/* 1405 */           t = (T)iterator.next();
/*      */         } 
/* 1407 */         if (this.c.isAssignableFrom(t.getClass())) {
/* 1408 */           if (this.which != 3) {
/* 1409 */             this.set.add(t);
/*      */             continue;
/*      */           } 
/* 1412 */           SecurityManager securityManager = System.getSecurityManager();
/* 1413 */           if (securityManager != null) {
/* 1414 */             securityManager.checkPermission(new PrivateCredentialPermission(t
/* 1415 */                   .getClass().getName(), Subject.this
/* 1416 */                   .getPrincipals()));
/*      */           }
/* 1418 */           this.set.add(t);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1425 */       return this.set.size();
/*      */     }
/*      */     
/*      */     public Iterator<T> iterator() {
/* 1429 */       return this.set.iterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(T param1T) {
/* 1434 */       if (!param1T.getClass().isAssignableFrom(this.c)) {
/*      */         
/* 1436 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.class"));
/* 1437 */         Object[] arrayOfObject = { this.c.toString() };
/* 1438 */         throw new SecurityException(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */       
/* 1441 */       return this.set.add(param1T);
/*      */     }
/*      */   }
/*      */   
/*      */   static class AuthPermissionHolder {
/* 1446 */     static final AuthPermission DO_AS_PERMISSION = new AuthPermission("doAs");
/*      */ 
/*      */     
/* 1449 */     static final AuthPermission DO_AS_PRIVILEGED_PERMISSION = new AuthPermission("doAsPrivileged");
/*      */ 
/*      */     
/* 1452 */     static final AuthPermission SET_READ_ONLY_PERMISSION = new AuthPermission("setReadOnly");
/*      */ 
/*      */     
/* 1455 */     static final AuthPermission GET_SUBJECT_PERMISSION = new AuthPermission("getSubject");
/*      */ 
/*      */     
/* 1458 */     static final AuthPermission MODIFY_PRINCIPALS_PERMISSION = new AuthPermission("modifyPrincipals");
/*      */ 
/*      */     
/* 1461 */     static final AuthPermission MODIFY_PUBLIC_CREDENTIALS_PERMISSION = new AuthPermission("modifyPublicCredentials");
/*      */ 
/*      */     
/* 1464 */     static final AuthPermission MODIFY_PRIVATE_CREDENTIALS_PERMISSION = new AuthPermission("modifyPrivateCredentials");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/Subject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */