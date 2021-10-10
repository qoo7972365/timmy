/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.security.AccessController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassAttributeValueExp
/*     */   extends AttributeValueExp
/*     */ {
/*     */   private static final long oldSerialVersionUID = -2212731951078526753L;
/*     */   private static final long newSerialVersionUID = -1081892073854801359L;
/*     */   private static final long serialVersionUID;
/*     */   private String attr;
/*     */   
/*     */   static {
/*  59 */     boolean bool = false;
/*     */     try {
/*  61 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  62 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  63 */       bool = (str != null && str.equals("1.0")) ? true : false;
/*  64 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  67 */     if (bool) {
/*  68 */       serialVersionUID = -2212731951078526753L;
/*     */     } else {
/*  70 */       serialVersionUID = -1081892073854801359L;
/*     */     } 
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
/*     */   public ClassAttributeValueExp() {
/*  86 */     super("Class");
/*  87 */     this.attr = "Class";
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
/*     */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 106 */     Object object = getValue(paramObjectName);
/* 107 */     if (object instanceof String) {
/* 108 */       return new StringValueExp((String)object);
/*     */     }
/* 110 */     throw new BadAttributeValueExpException(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return this.attr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getValue(ObjectName paramObjectName) {
/*     */     try {
/* 125 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/* 126 */       return mBeanServer.getObjectInstance(paramObjectName).getClassName();
/* 127 */     } catch (Exception exception) {
/* 128 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/ClassAttributeValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */