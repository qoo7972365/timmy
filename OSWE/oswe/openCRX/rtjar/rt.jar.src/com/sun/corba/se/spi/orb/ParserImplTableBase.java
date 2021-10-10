/*     */ package com.sun.corba.se.spi.orb;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ParserImplTableBase
/*     */   extends ParserImplBase
/*     */ {
/*     */   private final ParserData[] entries;
/*     */   
/*     */   public ParserImplTableBase(ParserData[] paramArrayOfParserData) {
/*  46 */     this.entries = paramArrayOfParserData;
/*  47 */     setDefaultValues();
/*     */   }
/*     */ 
/*     */   
/*     */   protected PropertyParser makeParser() {
/*  52 */     PropertyParser propertyParser = new PropertyParser();
/*  53 */     for (byte b = 0; b < this.entries.length; b++) {
/*  54 */       ParserData parserData = this.entries[b];
/*  55 */       parserData.addToParser(propertyParser);
/*     */     } 
/*     */     
/*  58 */     return propertyParser;
/*     */   }
/*     */   
/*     */   private static final class MapEntry
/*     */     implements Map.Entry {
/*     */     private Object key;
/*     */     private Object value;
/*     */     
/*     */     public MapEntry(Object param1Object) {
/*  67 */       this.key = param1Object;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getKey() {
/*  72 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValue() {
/*  77 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object setValue(Object param1Object) {
/*  82 */       Object object = this.value;
/*  83 */       this.value = param1Object;
/*  84 */       return object;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*  89 */       if (!(param1Object instanceof MapEntry)) {
/*  90 */         return false;
/*     */       }
/*  92 */       MapEntry mapEntry = (MapEntry)param1Object;
/*     */       
/*  94 */       return (this.key.equals(mapEntry.key) && this.value
/*  95 */         .equals(mapEntry.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 100 */       return this.key.hashCode() ^ this.value.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FieldMap
/*     */     extends AbstractMap
/*     */   {
/*     */     private final ParserData[] entries;
/*     */ 
/*     */     
/*     */     private final boolean useDefault;
/*     */ 
/*     */ 
/*     */     
/*     */     public FieldMap(ParserData[] param1ArrayOfParserData, boolean param1Boolean) {
/* 117 */       this.entries = param1ArrayOfParserData;
/* 118 */       this.useDefault = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set entrySet() {
/* 123 */       return new AbstractSet()
/*     */         {
/*     */           public Iterator iterator()
/*     */           {
/* 127 */             return new Iterator()
/*     */               {
/* 129 */                 int ctr = 0;
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 133 */                   return (this.ctr < ParserImplTableBase.FieldMap.this.entries.length);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public Object next() {
/* 138 */                   ParserData parserData = ParserImplTableBase.FieldMap.this.entries[this.ctr++];
/* 139 */                   ParserImplTableBase.MapEntry mapEntry = new ParserImplTableBase.MapEntry(parserData.getFieldName());
/* 140 */                   if (ParserImplTableBase.FieldMap.this.useDefault) {
/* 141 */                     mapEntry.setValue(parserData.getDefaultValue());
/*     */                   } else {
/* 143 */                     mapEntry.setValue(parserData.getTestValue());
/* 144 */                   }  return mapEntry;
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 149 */                   throw new UnsupportedOperationException();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public int size() {
/* 156 */             return ParserImplTableBase.FieldMap.this.entries.length;
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 164 */     FieldMap fieldMap = new FieldMap(this.entries, true);
/* 165 */     setFields(fieldMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTestValues() {
/* 170 */     FieldMap fieldMap = new FieldMap(this.entries, false);
/* 171 */     setFields(fieldMap);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/ParserImplTableBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */