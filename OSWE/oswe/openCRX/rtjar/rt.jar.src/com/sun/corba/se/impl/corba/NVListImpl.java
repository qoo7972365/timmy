/*     */ package com.sun.corba.se.impl.corba;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Vector;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Bounds;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NVListImpl
/*     */   extends NVList
/*     */ {
/*  45 */   private final int INITIAL_CAPACITY = 4;
/*  46 */   private final int CAPACITY_INCREMENT = 2;
/*     */   
/*     */   private Vector _namedValues;
/*     */   
/*     */   private ORB orb;
/*     */ 
/*     */   
/*     */   public NVListImpl(ORB paramORB) {
/*  54 */     this.orb = paramORB;
/*  55 */     this._namedValues = new Vector(4, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public NVListImpl(ORB paramORB, int paramInt) {
/*  60 */     this.orb = paramORB;
/*     */ 
/*     */     
/*  63 */     this._namedValues = new Vector(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int count() {
/*  69 */     return this._namedValues.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamedValue add(int paramInt) {
/*  74 */     NamedValueImpl namedValueImpl = new NamedValueImpl(this.orb, "", new AnyImpl(this.orb), paramInt);
/*  75 */     this._namedValues.addElement(namedValueImpl);
/*  76 */     return namedValueImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamedValue add_item(String paramString, int paramInt) {
/*  81 */     NamedValueImpl namedValueImpl = new NamedValueImpl(this.orb, paramString, new AnyImpl(this.orb), paramInt);
/*     */     
/*  83 */     this._namedValues.addElement(namedValueImpl);
/*  84 */     return namedValueImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamedValue add_value(String paramString, Any paramAny, int paramInt) {
/*  89 */     NamedValueImpl namedValueImpl = new NamedValueImpl(this.orb, paramString, paramAny, paramInt);
/*  90 */     this._namedValues.addElement(namedValueImpl);
/*  91 */     return namedValueImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamedValue item(int paramInt) throws Bounds {
/*     */     try {
/*  98 */       return this._namedValues.elementAt(paramInt);
/*  99 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 100 */       throw new Bounds();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int paramInt) throws Bounds {
/*     */     try {
/* 108 */       this._namedValues.removeElementAt(paramInt);
/* 109 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 110 */       throw new Bounds();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/NVListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */