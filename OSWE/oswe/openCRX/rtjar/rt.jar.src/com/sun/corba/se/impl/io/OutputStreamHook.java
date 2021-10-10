/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.NotActiveException;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.portable.ValueOutputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OutputStreamHook
/*     */   extends ObjectOutputStream
/*     */ {
/*  46 */   private HookPutFields putFields = null;
/*     */   
/*     */   protected byte streamFormatVersion;
/*     */   protected WriteObjectState writeObjectState;
/*     */   
/*     */   private class HookPutFields
/*     */     extends ObjectOutputStream.PutField
/*     */   {
/*  54 */     private Map<String, Object> fields = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, boolean param1Boolean) {
/*  60 */       this.fields.put(param1String, new Boolean(param1Boolean));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, char param1Char) {
/*  67 */       this.fields.put(param1String, new Character(param1Char));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, byte param1Byte) {
/*  74 */       this.fields.put(param1String, new Byte(param1Byte));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, short param1Short) {
/*  81 */       this.fields.put(param1String, new Short(param1Short));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, int param1Int) {
/*  88 */       this.fields.put(param1String, new Integer(param1Int));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, long param1Long) {
/*  95 */       this.fields.put(param1String, new Long(param1Long));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, float param1Float) {
/* 103 */       this.fields.put(param1String, new Float(param1Float));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, double param1Double) {
/* 110 */       this.fields.put(param1String, new Double(param1Double));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(String param1String, Object param1Object) {
/* 117 */       this.fields.put(param1String, param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(ObjectOutput param1ObjectOutput) throws IOException {
/* 124 */       OutputStreamHook outputStreamHook = (OutputStreamHook)param1ObjectOutput;
/*     */       
/* 126 */       ObjectStreamField[] arrayOfObjectStreamField = outputStreamHook.getFieldsNoCopy();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       for (byte b = 0; b < arrayOfObjectStreamField.length; b++) {
/*     */         
/* 133 */         Object object = this.fields.get(arrayOfObjectStreamField[b].getName());
/*     */         
/* 135 */         outputStreamHook.writeField(arrayOfObjectStreamField[b], object);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private HookPutFields() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void defaultWriteObject() throws IOException {
/* 149 */     this.writeObjectState.defaultWriteObject(this);
/*     */     
/* 151 */     defaultWriteObjectDelegate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutputStream.PutField putFields() throws IOException {
/* 158 */     if (this.putFields == null) {
/* 159 */       this.putFields = new HookPutFields();
/*     */     }
/* 161 */     return this.putFields;
/*     */   }
/*     */   public byte getStreamFormatVersion() {
/*     */     return this.streamFormatVersion;
/* 165 */   } public OutputStreamHook() throws IOException { this.streamFormatVersion = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     this.writeObjectState = NOT_IN_WRITE_OBJECT; }
/*     */   public void writeFields() throws IOException { this.writeObjectState.defaultWriteObject(this); if (this.putFields != null) { this.putFields.write(this); }
/*     */     else { throw new NotActiveException("no current PutField object"); }
/* 202 */      } protected void setState(WriteObjectState paramWriteObjectState) { this.writeObjectState = paramWriteObjectState; }
/*     */ 
/*     */   
/*     */   protected static class WriteObjectState {
/*     */     public void enterWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {}
/*     */     
/*     */     public void exitWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {}
/*     */     
/*     */     public void defaultWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {}
/*     */     
/*     */     public void writeData(OutputStreamHook param1OutputStreamHook) throws IOException {} }
/*     */   
/*     */   protected static class DefaultState extends WriteObjectState { public void enterWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 215 */       param1OutputStreamHook.setState(OutputStreamHook.IN_WRITE_OBJECT);
/*     */     } }
/*     */ 
/*     */   
/* 219 */   protected static final WriteObjectState NOT_IN_WRITE_OBJECT = new DefaultState();
/* 220 */   protected static final WriteObjectState IN_WRITE_OBJECT = new InWriteObjectState();
/* 221 */   protected static final WriteObjectState WROTE_DEFAULT_DATA = new WroteDefaultDataState();
/* 222 */   protected static final WriteObjectState WROTE_CUSTOM_DATA = new WroteCustomDataState(); abstract void writeField(ObjectStreamField paramObjectStreamField, Object paramObject) throws IOException;
/*     */   public abstract void defaultWriteObjectDelegate();
/*     */   abstract ObjectStreamField[] getFieldsNoCopy();
/*     */   abstract OutputStream getOrbStream();
/*     */   protected abstract void beginOptionalCustomData();
/*     */   protected static class InWriteObjectState extends WriteObjectState { public void enterWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 228 */       throw new IOException("Internal state failure: Entered writeObject twice");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void exitWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 235 */       param1OutputStreamHook.getOrbStream().write_boolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (param1OutputStreamHook.getStreamFormatVersion() == 2) {
/* 241 */         param1OutputStreamHook.getOrbStream().write_long(0);
/*     */       }
/* 243 */       param1OutputStreamHook.setState(OutputStreamHook.NOT_IN_WRITE_OBJECT);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void defaultWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 251 */       param1OutputStreamHook.getOrbStream().write_boolean(true);
/*     */       
/* 253 */       param1OutputStreamHook.setState(OutputStreamHook.WROTE_DEFAULT_DATA);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeData(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 263 */       param1OutputStreamHook.getOrbStream().write_boolean(false);
/* 264 */       param1OutputStreamHook.beginOptionalCustomData();
/* 265 */       param1OutputStreamHook.setState(OutputStreamHook.WROTE_CUSTOM_DATA);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class WroteDefaultDataState
/*     */     extends InWriteObjectState
/*     */   {
/*     */     public void exitWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 276 */       if (param1OutputStreamHook.getStreamFormatVersion() == 2) {
/* 277 */         param1OutputStreamHook.getOrbStream().write_long(0);
/*     */       }
/* 279 */       param1OutputStreamHook.setState(OutputStreamHook.NOT_IN_WRITE_OBJECT);
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 284 */       throw new IOException("Called defaultWriteObject/writeFields twice");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeData(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 292 */       param1OutputStreamHook.beginOptionalCustomData();
/*     */       
/* 294 */       param1OutputStreamHook.setState(OutputStreamHook.WROTE_CUSTOM_DATA);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class WroteCustomDataState
/*     */     extends InWriteObjectState
/*     */   {
/*     */     public void exitWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 303 */       if (param1OutputStreamHook.getStreamFormatVersion() == 2) {
/* 304 */         ((ValueOutputStream)param1OutputStreamHook.getOrbStream()).end_value();
/*     */       }
/* 306 */       param1OutputStreamHook.setState(OutputStreamHook.NOT_IN_WRITE_OBJECT);
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultWriteObject(OutputStreamHook param1OutputStreamHook) throws IOException {
/* 311 */       throw new IOException("Cannot call defaultWriteObject/writeFields after writing custom data in RMI-IIOP");
/*     */     }
/*     */     
/*     */     public void writeData(OutputStreamHook param1OutputStreamHook) throws IOException {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/OutputStreamHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */