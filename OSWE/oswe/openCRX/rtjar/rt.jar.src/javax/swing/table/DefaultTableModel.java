/*     */ package javax.swing.table;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTableModel
/*     */   extends AbstractTableModel
/*     */   implements Serializable
/*     */ {
/*     */   protected Vector dataVector;
/*     */   protected Vector columnIdentifiers;
/*     */   
/*     */   public DefaultTableModel() {
/*  86 */     this(0, 0);
/*     */   }
/*     */   
/*     */   private static Vector newVector(int paramInt) {
/*  90 */     Vector vector = new Vector(paramInt);
/*  91 */     vector.setSize(paramInt);
/*  92 */     return vector;
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
/*     */   public DefaultTableModel(int paramInt1, int paramInt2) {
/* 106 */     this(newVector(paramInt2), paramInt1);
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
/*     */   public DefaultTableModel(Vector paramVector, int paramInt) {
/* 124 */     setDataVector(newVector(paramInt), paramVector);
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
/*     */   public DefaultTableModel(Object[] paramArrayOfObject, int paramInt) {
/* 142 */     this(convertToVector(paramArrayOfObject), paramInt);
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
/*     */   public DefaultTableModel(Vector paramVector1, Vector paramVector2) {
/* 159 */     setDataVector(paramVector1, paramVector2);
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
/*     */   public DefaultTableModel(Object[][] paramArrayOfObject, Object[] paramArrayOfObject1) {
/* 175 */     setDataVector(paramArrayOfObject, paramArrayOfObject1);
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
/*     */   public Vector getDataVector() {
/* 194 */     return this.dataVector;
/*     */   }
/*     */   
/*     */   private static Vector nonNullVector(Vector paramVector) {
/* 198 */     return (paramVector != null) ? paramVector : new Vector();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataVector(Vector paramVector1, Vector paramVector2) {
/* 222 */     this.dataVector = nonNullVector(paramVector1);
/* 223 */     this.columnIdentifiers = nonNullVector(paramVector2);
/* 224 */     justifyRows(0, getRowCount());
/* 225 */     fireTableStructureChanged();
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
/*     */   public void setDataVector(Object[][] paramArrayOfObject, Object[] paramArrayOfObject1) {
/* 240 */     setDataVector(convertToVector(paramArrayOfObject), convertToVector(paramArrayOfObject1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newDataAvailable(TableModelEvent paramTableModelEvent) {
/* 250 */     fireTableChanged(paramTableModelEvent);
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
/*     */   private void justifyRows(int paramInt1, int paramInt2) {
/* 262 */     this.dataVector.setSize(getRowCount());
/*     */     
/* 264 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 265 */       if (this.dataVector.elementAt(i) == null) {
/* 266 */         this.dataVector.setElementAt(new Vector(), i);
/*     */       }
/* 268 */       ((Vector)this.dataVector.elementAt(i)).setSize(getColumnCount());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newRowsAdded(TableModelEvent paramTableModelEvent) {
/* 288 */     justifyRows(paramTableModelEvent.getFirstRow(), paramTableModelEvent.getLastRow() + 1);
/* 289 */     fireTableChanged(paramTableModelEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rowsRemoved(TableModelEvent paramTableModelEvent) {
/* 299 */     fireTableChanged(paramTableModelEvent);
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
/*     */   public void setNumRows(int paramInt) {
/* 315 */     int i = getRowCount();
/* 316 */     if (i == paramInt) {
/*     */       return;
/*     */     }
/* 319 */     this.dataVector.setSize(paramInt);
/* 320 */     if (paramInt <= i) {
/* 321 */       fireTableRowsDeleted(paramInt, i - 1);
/*     */     } else {
/*     */       
/* 324 */       justifyRows(i, paramInt);
/* 325 */       fireTableRowsInserted(i, paramInt - 1);
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
/*     */   public void setRowCount(int paramInt) {
/* 339 */     setNumRows(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRow(Vector paramVector) {
/* 350 */     insertRow(getRowCount(), paramVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRow(Object[] paramArrayOfObject) {
/* 361 */     addRow(convertToVector(paramArrayOfObject));
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
/*     */   public void insertRow(int paramInt, Vector paramVector) {
/* 374 */     this.dataVector.insertElementAt(paramVector, paramInt);
/* 375 */     justifyRows(paramInt, paramInt + 1);
/* 376 */     fireTableRowsInserted(paramInt, paramInt);
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
/*     */   public void insertRow(int paramInt, Object[] paramArrayOfObject) {
/* 389 */     insertRow(paramInt, convertToVector(paramArrayOfObject));
/*     */   }
/*     */   
/*     */   private static int gcd(int paramInt1, int paramInt2) {
/* 393 */     return (paramInt2 == 0) ? paramInt1 : gcd(paramInt2, paramInt1 % paramInt2);
/*     */   }
/*     */   
/*     */   private static void rotate(Vector<Object> paramVector, int paramInt1, int paramInt2, int paramInt3) {
/* 397 */     int i = paramInt2 - paramInt1;
/* 398 */     int j = i - paramInt3;
/* 399 */     int k = gcd(i, j);
/* 400 */     for (byte b = 0; b < k; b++) {
/* 401 */       int m = b;
/* 402 */       Object object = paramVector.elementAt(paramInt1 + m); int n;
/* 403 */       for (n = (m + j) % i; n != b; n = (m + j) % i) {
/* 404 */         paramVector.setElementAt(paramVector.elementAt(paramInt1 + n), paramInt1 + m);
/* 405 */         m = n;
/*     */       } 
/* 407 */       paramVector.setElementAt(object, paramInt1 + m);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveRow(int paramInt1, int paramInt2, int paramInt3) {
/* 439 */     int j, k, i = paramInt3 - paramInt1;
/*     */     
/* 441 */     if (i < 0) {
/* 442 */       j = paramInt3;
/* 443 */       k = paramInt2;
/*     */     } else {
/*     */       
/* 446 */       j = paramInt1;
/* 447 */       k = paramInt3 + paramInt2 - paramInt1;
/*     */     } 
/* 449 */     rotate(this.dataVector, j, k + 1, i);
/*     */     
/* 451 */     fireTableRowsUpdated(j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRow(int paramInt) {
/* 462 */     this.dataVector.removeElementAt(paramInt);
/* 463 */     fireTableRowsDeleted(paramInt, paramInt);
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
/*     */ 
/*     */   
/*     */   public void setColumnIdentifiers(Vector paramVector) {
/* 484 */     setDataVector(this.dataVector, paramVector);
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
/*     */   public void setColumnIdentifiers(Object[] paramArrayOfObject) {
/* 501 */     setColumnIdentifiers(convertToVector(paramArrayOfObject));
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
/*     */   public void setColumnCount(int paramInt) {
/* 517 */     this.columnIdentifiers.setSize(paramInt);
/* 518 */     justifyRows(0, getRowCount());
/* 519 */     fireTableStructureChanged();
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
/*     */   public void addColumn(Object paramObject) {
/* 533 */     addColumn(paramObject, (Vector)null);
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
/*     */   public void addColumn(Object paramObject, Vector paramVector) {
/* 550 */     this.columnIdentifiers.addElement(paramObject);
/* 551 */     if (paramVector != null) {
/* 552 */       int i = paramVector.size();
/* 553 */       if (i > getRowCount()) {
/* 554 */         this.dataVector.setSize(i);
/*     */       }
/* 556 */       justifyRows(0, getRowCount());
/* 557 */       int j = getColumnCount() - 1;
/* 558 */       for (byte b = 0; b < i; b++) {
/* 559 */         Vector vector = this.dataVector.elementAt(b);
/* 560 */         vector.setElementAt(paramVector.elementAt(b), j);
/*     */       } 
/*     */     } else {
/*     */       
/* 564 */       justifyRows(0, getRowCount());
/*     */     } 
/*     */     
/* 567 */     fireTableStructureChanged();
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
/*     */   public void addColumn(Object paramObject, Object[] paramArrayOfObject) {
/* 582 */     addColumn(paramObject, convertToVector(paramArrayOfObject));
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
/*     */   public int getRowCount() {
/* 594 */     return this.dataVector.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 602 */     return this.columnIdentifiers.size();
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
/*     */   public String getColumnName(int paramInt) {
/* 615 */     Object object = null;
/*     */ 
/*     */     
/* 618 */     if (paramInt < this.columnIdentifiers.size() && paramInt >= 0) {
/* 619 */       object = this.columnIdentifiers.elementAt(paramInt);
/*     */     }
/* 621 */     return (object == null) ? super.getColumnName(paramInt) : object
/* 622 */       .toString();
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
/*     */   public boolean isCellEditable(int paramInt1, int paramInt2) {
/* 634 */     return true;
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
/*     */   public Object getValueAt(int paramInt1, int paramInt2) {
/* 648 */     Vector vector = this.dataVector.elementAt(paramInt1);
/* 649 */     return vector.elementAt(paramInt2);
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
/*     */   public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
/* 664 */     Vector<Object> vector = this.dataVector.elementAt(paramInt1);
/* 665 */     vector.setElementAt(paramObject, paramInt2);
/* 666 */     fireTableCellUpdated(paramInt1, paramInt2);
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
/*     */   protected static Vector convertToVector(Object[] paramArrayOfObject) {
/* 680 */     if (paramArrayOfObject == null) {
/* 681 */       return null;
/*     */     }
/* 683 */     Vector<Object> vector = new Vector(paramArrayOfObject.length);
/* 684 */     for (Object object : paramArrayOfObject) {
/* 685 */       vector.addElement(object);
/*     */     }
/* 687 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Vector convertToVector(Object[][] paramArrayOfObject) {
/* 697 */     if (paramArrayOfObject == null) {
/* 698 */       return null;
/*     */     }
/* 700 */     Vector<Vector> vector = new Vector(paramArrayOfObject.length);
/* 701 */     for (Object[] arrayOfObject : paramArrayOfObject) {
/* 702 */       vector.addElement(convertToVector(arrayOfObject));
/*     */     }
/* 704 */     return vector;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/table/DefaultTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */