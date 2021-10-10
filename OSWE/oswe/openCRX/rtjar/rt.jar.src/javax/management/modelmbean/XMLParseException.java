/*     */ package javax.management.modelmbean;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLParseException
/*     */   extends Exception
/*     */ {
/*     */   private static final long oldSerialVersionUID = -7780049316655891976L;
/*     */   private static final long newSerialVersionUID = 3176664577895105181L;
/*  69 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("msgStr", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[0];
/*     */   
/*     */   private static final long serialVersionUID;
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */   private static boolean compat = false;
/*     */   
/*     */   static {
/*     */     try {
/*  83 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  84 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  85 */       compat = (str != null && str.equals("1.0"));
/*  86 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  89 */     if (compat) {
/*  90 */       serialPersistentFields = oldSerialPersistentFields;
/*  91 */       serialVersionUID = -7780049316655891976L;
/*     */     } else {
/*  93 */       serialPersistentFields = newSerialPersistentFields;
/*  94 */       serialVersionUID = 3176664577895105181L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParseException() {
/* 105 */     super("XML Parse Exception.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParseException(String paramString) {
/* 115 */     super("XML Parse Exception: " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParseException(Exception paramException, String paramString) {
/* 125 */     super("XML Parse Exception: " + paramString + ":" + paramException.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 134 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 143 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 147 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 148 */       putField.put("msgStr", getMessage());
/* 149 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 155 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/XMLParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */