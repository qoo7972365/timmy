/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.NotActiveException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.portable.ValueInputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputStreamHook
/*     */   extends ObjectInputStream
/*     */ {
/*  56 */   static final OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
/*     */ 
/*     */   
/*  59 */   static final UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
/*     */   protected ReadObjectState readObjectState;
/*     */   
/*  62 */   private class HookGetFields extends ObjectInputStream.GetField { private Map fields = null;
/*     */     
/*     */     HookGetFields(Map param1Map) {
/*  65 */       this.fields = param1Map;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectStreamClass getObjectStreamClass() {
/*  74 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean defaulted(String param1String) throws IOException, IllegalArgumentException {
/*  83 */       return !this.fields.containsKey(param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean get(String param1String, boolean param1Boolean) throws IOException, IllegalArgumentException {
/*  91 */       if (defaulted(param1String))
/*  92 */         return param1Boolean; 
/*  93 */       return ((Boolean)this.fields.get(param1String)).booleanValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char get(String param1String, char param1Char) throws IOException, IllegalArgumentException {
/* 101 */       if (defaulted(param1String))
/* 102 */         return param1Char; 
/* 103 */       return ((Character)this.fields.get(param1String)).charValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte get(String param1String, byte param1Byte) throws IOException, IllegalArgumentException {
/* 112 */       if (defaulted(param1String))
/* 113 */         return param1Byte; 
/* 114 */       return ((Byte)this.fields.get(param1String)).byteValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short get(String param1String, short param1Short) throws IOException, IllegalArgumentException {
/* 123 */       if (defaulted(param1String))
/* 124 */         return param1Short; 
/* 125 */       return ((Short)this.fields.get(param1String)).shortValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int get(String param1String, int param1Int) throws IOException, IllegalArgumentException {
/* 134 */       if (defaulted(param1String))
/* 135 */         return param1Int; 
/* 136 */       return ((Integer)this.fields.get(param1String)).intValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long get(String param1String, long param1Long) throws IOException, IllegalArgumentException {
/* 145 */       if (defaulted(param1String))
/* 146 */         return param1Long; 
/* 147 */       return ((Long)this.fields.get(param1String)).longValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float get(String param1String, float param1Float) throws IOException, IllegalArgumentException {
/* 156 */       if (defaulted(param1String))
/* 157 */         return param1Float; 
/* 158 */       return ((Float)this.fields.get(param1String)).floatValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double get(String param1String, double param1Double) throws IOException, IllegalArgumentException {
/* 167 */       if (defaulted(param1String))
/* 168 */         return param1Double; 
/* 169 */       return ((Double)this.fields.get(param1String)).doubleValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object get(String param1String, Object param1Object) throws IOException, IllegalArgumentException {
/* 178 */       if (defaulted(param1String))
/* 179 */         return param1Object; 
/* 180 */       return this.fields.get(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 185 */       return this.fields.toString();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStreamHook() throws IOException {
/* 268 */     this.readObjectState = DEFAULT_STATE;
/*     */   } public void defaultReadObject() throws IOException, ClassNotFoundException, NotActiveException { this.readObjectState.beginDefaultReadObject(this); defaultReadObjectDelegate();
/* 270 */     this.readObjectState.endDefaultReadObject(this); } abstract void defaultReadObjectDelegate(); abstract void readFields(Map paramMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException; protected static final ReadObjectState DEFAULT_STATE = new DefaultState(); public ObjectInputStream.GetField readFields() throws IOException, ClassNotFoundException, NotActiveException { HashMap<Object, Object> hashMap = new HashMap<>(); readFields(hashMap); this.readObjectState.endDefaultReadObject(this); return new HookGetFields(hashMap); } protected void setState(ReadObjectState paramReadObjectState) { this.readObjectState = paramReadObjectState; } protected static class ReadObjectState {
/* 271 */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) throws IOException {} public void endUnmarshalCustomValue(InputStreamHook param1InputStreamHook) throws IOException {} public void beginDefaultReadObject(InputStreamHook param1InputStreamHook) throws IOException {} public void endDefaultReadObject(InputStreamHook param1InputStreamHook) throws IOException {} public void readData(InputStreamHook param1InputStreamHook) throws IOException {} } protected static final ReadObjectState IN_READ_OBJECT_OPT_DATA = new InReadObjectOptionalDataState();
/*     */   
/* 273 */   protected static final ReadObjectState IN_READ_OBJECT_NO_MORE_OPT_DATA = new InReadObjectNoMoreOptionalDataState();
/*     */   
/* 275 */   protected static final ReadObjectState IN_READ_OBJECT_DEFAULTS_SENT = new InReadObjectDefaultsSentState();
/*     */   
/* 277 */   protected static final ReadObjectState NO_READ_OBJECT_DEFAULTS_SENT = new NoReadObjectDefaultsSentState();
/*     */ 
/*     */   
/* 280 */   protected static final ReadObjectState IN_READ_OBJECT_REMOTE_NOT_CUSTOM_MARSHALED = new InReadObjectRemoteDidNotUseWriteObjectState();
/*     */   
/* 282 */   protected static final ReadObjectState IN_READ_OBJECT_PAST_DEFAULTS_REMOTE_NOT_CUSTOM = new InReadObjectPastDefaultsRemoteDidNotUseWOState();
/*     */   
/*     */   protected abstract byte getStreamFormatVersion();
/*     */   
/*     */   abstract InputStream getOrbStream();
/*     */   
/*     */   protected static class DefaultState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) throws IOException {
/* 292 */       if (param1Boolean2) {
/* 293 */         if (param1Boolean1) {
/* 294 */           param1InputStreamHook.setState(InputStreamHook.IN_READ_OBJECT_DEFAULTS_SENT);
/*     */         } else {
/*     */           try {
/* 297 */             if (param1InputStreamHook.getStreamFormatVersion() == 2)
/* 298 */               ((ValueInputStream)param1InputStreamHook.getOrbStream()).start_value(); 
/* 299 */           } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 308 */           param1InputStreamHook.setState(InputStreamHook.IN_READ_OBJECT_OPT_DATA);
/*     */         }
/*     */       
/* 311 */       } else if (param1Boolean1) {
/* 312 */         param1InputStreamHook.setState(InputStreamHook.NO_READ_OBJECT_DEFAULTS_SENT);
/*     */       } else {
/*     */         
/* 315 */         throw new StreamCorruptedException("No default data sent");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class InReadObjectRemoteDidNotUseWriteObjectState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) {
/* 329 */       throw InputStreamHook.utilWrapper.badBeginUnmarshalCustomValue();
/*     */     }
/*     */     
/*     */     public void endDefaultReadObject(InputStreamHook param1InputStreamHook) {
/* 333 */       param1InputStreamHook.setState(InputStreamHook.IN_READ_OBJECT_PAST_DEFAULTS_REMOTE_NOT_CUSTOM);
/*     */     }
/*     */     
/*     */     public void readData(InputStreamHook param1InputStreamHook) {
/* 337 */       param1InputStreamHook.throwOptionalDataIncompatibleException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class InReadObjectPastDefaultsRemoteDidNotUseWOState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) {
/* 347 */       throw InputStreamHook.utilWrapper.badBeginUnmarshalCustomValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void beginDefaultReadObject(InputStreamHook param1InputStreamHook) throws IOException {
/* 353 */       throw new StreamCorruptedException("Default data already read");
/*     */     }
/*     */ 
/*     */     
/*     */     public void readData(InputStreamHook param1InputStreamHook) {
/* 358 */       param1InputStreamHook.throwOptionalDataIncompatibleException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void throwOptionalDataIncompatibleException() {
/* 364 */     throw omgWrapper.rmiiiopOptionalDataIncompatible2();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class InReadObjectDefaultsSentState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) {
/* 374 */       throw InputStreamHook.utilWrapper.badBeginUnmarshalCustomValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endUnmarshalCustomValue(InputStreamHook param1InputStreamHook) {
/* 383 */       if (param1InputStreamHook.getStreamFormatVersion() == 2) {
/* 384 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).start_value();
/* 385 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).end_value();
/*     */       } 
/*     */       
/* 388 */       param1InputStreamHook.setState(InputStreamHook.DEFAULT_STATE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void endDefaultReadObject(InputStreamHook param1InputStreamHook) throws IOException {
/* 394 */       if (param1InputStreamHook.getStreamFormatVersion() == 2) {
/* 395 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).start_value();
/*     */       }
/* 397 */       param1InputStreamHook.setState(InputStreamHook.IN_READ_OBJECT_OPT_DATA);
/*     */     }
/*     */     
/*     */     public void readData(InputStreamHook param1InputStreamHook) throws IOException {
/* 401 */       ORB oRB = param1InputStreamHook.getOrbStream().orb();
/* 402 */       if (oRB == null || !(oRB instanceof ORB))
/*     */       {
/* 404 */         throw new StreamCorruptedException("Default data must be read first");
/*     */       }
/*     */ 
/*     */       
/* 408 */       ORBVersion oRBVersion = ((ORB)oRB).getORBVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       if (ORBVersionFactory.getPEORB().compareTo(oRBVersion) <= 0 || oRBVersion
/* 415 */         .equals(ORBVersionFactory.getFOREIGN()))
/*     */       {
/* 417 */         throw new StreamCorruptedException("Default data must be read first");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class InReadObjectOptionalDataState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void beginUnmarshalCustomValue(InputStreamHook param1InputStreamHook, boolean param1Boolean1, boolean param1Boolean2) {
/* 429 */       throw InputStreamHook.utilWrapper.badBeginUnmarshalCustomValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void endUnmarshalCustomValue(InputStreamHook param1InputStreamHook) throws IOException {
/* 434 */       if (param1InputStreamHook.getStreamFormatVersion() == 2) {
/* 435 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).end_value();
/*     */       }
/* 437 */       param1InputStreamHook.setState(InputStreamHook.DEFAULT_STATE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void beginDefaultReadObject(InputStreamHook param1InputStreamHook) throws IOException {
/* 443 */       throw new StreamCorruptedException("Default data not sent or already read/passed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class InReadObjectNoMoreOptionalDataState
/*     */     extends InReadObjectOptionalDataState
/*     */   {
/*     */     public void readData(InputStreamHook param1InputStreamHook) throws IOException {
/* 453 */       param1InputStreamHook.throwOptionalDataIncompatibleException();
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class NoReadObjectDefaultsSentState
/*     */     extends ReadObjectState
/*     */   {
/*     */     public void endUnmarshalCustomValue(InputStreamHook param1InputStreamHook) throws IOException {
/* 461 */       if (param1InputStreamHook.getStreamFormatVersion() == 2) {
/* 462 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).start_value();
/* 463 */         ((ValueInputStream)param1InputStreamHook.getOrbStream()).end_value();
/*     */       } 
/*     */       
/* 466 */       param1InputStreamHook.setState(InputStreamHook.DEFAULT_STATE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/InputStreamHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */