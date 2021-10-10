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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionMap
/*     */   implements Serializable
/*     */ {
/*     */   private transient ArrayTable arrayTable;
/*     */   private ActionMap parent;
/*     */   
/*     */   public void setParent(ActionMap paramActionMap) {
/*  78 */     this.parent = paramActionMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionMap getParent() {
/*  88 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(Object paramObject, Action paramAction) {
/*  99 */     if (paramObject == null) {
/*     */       return;
/*     */     }
/* 102 */     if (paramAction == null) {
/* 103 */       remove(paramObject);
/*     */     } else {
/*     */       
/* 106 */       if (this.arrayTable == null) {
/* 107 */         this.arrayTable = new ArrayTable();
/*     */       }
/* 109 */       this.arrayTable.put(paramObject, paramAction);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action get(Object paramObject) {
/* 119 */     Action action = (this.arrayTable == null) ? null : (Action)this.arrayTable.get(paramObject);
/*     */     
/* 121 */     if (action == null) {
/* 122 */       ActionMap actionMap = getParent();
/*     */       
/* 124 */       if (actionMap != null) {
/* 125 */         return actionMap.get(paramObject);
/*     */       }
/*     */     } 
/* 128 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Object paramObject) {
/* 135 */     if (this.arrayTable != null) {
/* 136 */       this.arrayTable.remove(paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 144 */     if (this.arrayTable != null) {
/* 145 */       this.arrayTable.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] keys() {
/* 153 */     if (this.arrayTable == null) {
/* 154 */       return null;
/*     */     }
/* 156 */     return this.arrayTable.getKeys(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 165 */     if (this.arrayTable == null) {
/* 166 */       return 0;
/*     */     }
/* 168 */     return this.arrayTable.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] allKeys() {
/* 177 */     int i = size();
/* 178 */     ActionMap actionMap = getParent();
/*     */     
/* 180 */     if (i == 0) {
/* 181 */       if (actionMap != null) {
/* 182 */         return actionMap.allKeys();
/*     */       }
/* 184 */       return keys();
/*     */     } 
/* 186 */     if (actionMap == null) {
/* 187 */       return keys();
/*     */     }
/* 189 */     Object[] arrayOfObject1 = keys();
/* 190 */     Object[] arrayOfObject2 = actionMap.allKeys();
/*     */     
/* 192 */     if (arrayOfObject2 == null) {
/* 193 */       return arrayOfObject1;
/*     */     }
/* 195 */     if (arrayOfObject1 == null)
/*     */     {
/*     */       
/* 198 */       return arrayOfObject2;
/*     */     }
/*     */     
/* 201 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/*     */     int j;
/* 204 */     for (j = arrayOfObject1.length - 1; j >= 0; j--) {
/* 205 */       hashMap.put(arrayOfObject1[j], arrayOfObject1[j]);
/*     */     }
/* 207 */     for (j = arrayOfObject2.length - 1; j >= 0; j--) {
/* 208 */       hashMap.put(arrayOfObject2[j], arrayOfObject2[j]);
/*     */     }
/* 210 */     return hashMap.keySet().toArray();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 214 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 216 */     ArrayTable.writeArrayTable(paramObjectOutputStream, this.arrayTable);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 221 */     paramObjectInputStream.defaultReadObject();
/* 222 */     for (int i = paramObjectInputStream.readInt() - 1; i >= 0; i--)
/* 223 */       put(paramObjectInputStream.readObject(), (Action)paramObjectInputStream.readObject()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ActionMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */