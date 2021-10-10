/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.org.glassfish.gmbal.AMXClient;
/*     */ import com.sun.org.glassfish.gmbal.GmbalMBean;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RewritingMOM
/*     */   implements ManagedObjectManager
/*     */ {
/*     */   private final ManagedObjectManager mom;
/*     */   private static final String gmbalQuotingCharsRegex = "\n|\\|\"|\\*|\\?|:|=|,";
/*     */   private static final String replacementChar = "-";
/*     */   
/*     */   RewritingMOM(ManagedObjectManager mom) {
/* 366 */     this.mom = mom;
/*     */   }
/*     */   private String rewrite(String x) {
/* 369 */     return x.replaceAll("\n|\\|\"|\\*|\\?|:|=|,", "-");
/*     */   }
/*     */ 
/*     */   
/*     */   public void suspendJMXRegistration() {
/* 374 */     this.mom.suspendJMXRegistration(); }
/* 375 */   public void resumeJMXRegistration() { this.mom.resumeJMXRegistration(); }
/* 376 */   public GmbalMBean createRoot() { return this.mom.createRoot(); } public GmbalMBean createRoot(Object root) {
/* 377 */     return this.mom.createRoot(root);
/*     */   } public GmbalMBean createRoot(Object root, String name) {
/* 379 */     return this.mom.createRoot(root, rewrite(name));
/*     */   } public Object getRoot() {
/* 381 */     return this.mom.getRoot();
/*     */   } public GmbalMBean register(Object parent, Object obj, String name) {
/* 383 */     return this.mom.register(parent, obj, rewrite(name));
/*     */   } public GmbalMBean register(Object parent, Object obj) {
/* 385 */     return this.mom.register(parent, obj);
/*     */   } public GmbalMBean registerAtRoot(Object obj, String name) {
/* 387 */     return this.mom.registerAtRoot(obj, rewrite(name));
/*     */   }
/* 389 */   public GmbalMBean registerAtRoot(Object obj) { return this.mom.registerAtRoot(obj); }
/* 390 */   public void unregister(Object obj) { this.mom.unregister(obj); }
/* 391 */   public ObjectName getObjectName(Object obj) { return this.mom.getObjectName(obj); }
/* 392 */   public AMXClient getAMXClient(Object obj) { return this.mom.getAMXClient(obj); }
/* 393 */   public Object getObject(ObjectName oname) { return this.mom.getObject(oname); }
/* 394 */   public void stripPrefix(String... str) { this.mom.stripPrefix(str); }
/* 395 */   public void stripPackagePrefix() { this.mom.stripPackagePrefix(); }
/* 396 */   public String getDomain() { return this.mom.getDomain(); }
/* 397 */   public void setMBeanServer(MBeanServer server) { this.mom.setMBeanServer(server); }
/* 398 */   public MBeanServer getMBeanServer() { return this.mom.getMBeanServer(); }
/* 399 */   public void setResourceBundle(ResourceBundle rb) { this.mom.setResourceBundle(rb); }
/* 400 */   public ResourceBundle getResourceBundle() { return this.mom.getResourceBundle(); }
/* 401 */   public void addAnnotation(AnnotatedElement element, Annotation annotation) { this.mom.addAnnotation(element, annotation); }
/* 402 */   public void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) { this.mom.setRegistrationDebug(level); }
/* 403 */   public void setRuntimeDebug(boolean flag) { this.mom.setRuntimeDebug(flag); }
/* 404 */   public void setTypelibDebug(int level) { this.mom.setTypelibDebug(level); }
/* 405 */   public String dumpSkeleton(Object obj) { return this.mom.dumpSkeleton(obj); }
/* 406 */   public void suppressDuplicateRootReport(boolean suppressReport) { this.mom.suppressDuplicateRootReport(suppressReport); }
/* 407 */   public void close() throws IOException { this.mom.close(); }
/* 408 */   public void setJMXRegistrationDebug(boolean x) { this.mom.setJMXRegistrationDebug(x); } public boolean isManagedObject(Object x) {
/* 409 */     return this.mom.isManagedObject(x);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/RewritingMOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */