/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputMap
/*     */   implements Serializable
/*     */ {
/*     */   private transient ArrayTable arrayTable;
/*     */   private InputMap parent;
/*     */   
/*     */   public void setParent(InputMap paramInputMap) {
/*  75 */     this.parent = paramInputMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputMap getParent() {
/*  85 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(KeyStroke paramKeyStroke, Object paramObject) {
/*  94 */     if (paramKeyStroke == null) {
/*     */       return;
/*     */     }
/*  97 */     if (paramObject == null) {
/*  98 */       remove(paramKeyStroke);
/*     */     } else {
/*     */       
/* 101 */       if (this.arrayTable == null) {
/* 102 */         this.arrayTable = new ArrayTable();
/*     */       }
/* 104 */       this.arrayTable.put(paramKeyStroke, paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(KeyStroke paramKeyStroke) {
/* 113 */     if (this.arrayTable == null) {
/* 114 */       InputMap inputMap = getParent();
/*     */       
/* 116 */       if (inputMap != null) {
/* 117 */         return inputMap.get(paramKeyStroke);
/*     */       }
/* 119 */       return null;
/*     */     } 
/* 121 */     Object object = this.arrayTable.get(paramKeyStroke);
/*     */     
/* 123 */     if (object == null) {
/* 124 */       InputMap inputMap = getParent();
/*     */       
/* 126 */       if (inputMap != null) {
/* 127 */         return inputMap.get(paramKeyStroke);
/*     */       }
/*     */     } 
/* 130 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(KeyStroke paramKeyStroke) {
/* 138 */     if (this.arrayTable != null) {
/* 139 */       this.arrayTable.remove(paramKeyStroke);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 147 */     if (this.arrayTable != null) {
/* 148 */       this.arrayTable.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyStroke[] keys() {
/* 156 */     if (this.arrayTable == null) {
/* 157 */       return null;
/*     */     }
/* 159 */     KeyStroke[] arrayOfKeyStroke = new KeyStroke[this.arrayTable.size()];
/* 160 */     this.arrayTable.getKeys((Object[])arrayOfKeyStroke);
/* 161 */     return arrayOfKeyStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 168 */     if (this.arrayTable == null) {
/* 169 */       return 0;
/*     */     }
/* 171 */     return this.arrayTable.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyStroke[] allKeys() {
/* 180 */     int i = size();
/* 181 */     InputMap inputMap = getParent();
/*     */     
/* 183 */     if (i == 0) {
/* 184 */       if (inputMap != null) {
/* 185 */         return inputMap.allKeys();
/*     */       }
/* 187 */       return keys();
/*     */     } 
/* 189 */     if (inputMap == null) {
/* 190 */       return keys();
/*     */     }
/* 192 */     KeyStroke[] arrayOfKeyStroke1 = keys();
/* 193 */     KeyStroke[] arrayOfKeyStroke2 = inputMap.allKeys();
/*     */     
/* 195 */     if (arrayOfKeyStroke2 == null) {
/* 196 */       return arrayOfKeyStroke1;
/*     */     }
/* 198 */     if (arrayOfKeyStroke1 == null)
/*     */     {
/*     */       
/* 201 */       return arrayOfKeyStroke2;
/*     */     }
/*     */     
/* 204 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/*     */     int j;
/* 207 */     for (j = arrayOfKeyStroke1.length - 1; j >= 0; j--) {
/* 208 */       hashMap.put(arrayOfKeyStroke1[j], arrayOfKeyStroke1[j]);
/*     */     }
/* 210 */     for (j = arrayOfKeyStroke2.length - 1; j >= 0; j--) {
/* 211 */       hashMap.put(arrayOfKeyStroke2[j], arrayOfKeyStroke2[j]);
/*     */     }
/*     */     
/* 214 */     KeyStroke[] arrayOfKeyStroke3 = new KeyStroke[hashMap.size()];
/*     */     
/* 216 */     return (KeyStroke[])hashMap.keySet().toArray((Object[])arrayOfKeyStroke3);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 220 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 222 */     ArrayTable.writeArrayTable(paramObjectOutputStream, this.arrayTable);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 227 */     paramObjectInputStream.defaultReadObject();
/* 228 */     for (int i = paramObjectInputStream.readInt() - 1; i >= 0; i--)
/* 229 */       put((KeyStroke)paramObjectInputStream.readObject(), paramObjectInputStream.readObject()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/InputMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */