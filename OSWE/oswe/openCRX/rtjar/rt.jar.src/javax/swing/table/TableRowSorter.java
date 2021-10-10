/*     */ package javax.swing.table;
/*     */ 
/*     */ import java.text.Collator;
/*     */ import java.util.Comparator;
/*     */ import javax.swing.DefaultRowSorter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableRowSorter<M extends TableModel>
/*     */   extends DefaultRowSorter<M, Integer>
/*     */ {
/* 134 */   private static final Comparator COMPARABLE_COMPARATOR = new ComparableComparator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private M tableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TableStringConverter stringConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableRowSorter() {
/* 152 */     this((M)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableRowSorter(M paramM) {
/* 163 */     setModel(paramM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(M paramM) {
/* 174 */     this.tableModel = paramM;
/* 175 */     setModelWrapper(new TableRowSorterModelWrapper());
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
/*     */   public void setStringConverter(TableStringConverter paramTableStringConverter) {
/* 188 */     this.stringConverter = paramTableStringConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableStringConverter getStringConverter() {
/* 198 */     return this.stringConverter;
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
/*     */   public Comparator<?> getComparator(int paramInt) {
/* 217 */     Comparator<?> comparator = super.getComparator(paramInt);
/* 218 */     if (comparator != null) {
/* 219 */       return comparator;
/*     */     }
/* 221 */     Class<?> clazz = ((TableModel)getModel()).getColumnClass(paramInt);
/* 222 */     if (clazz == String.class) {
/* 223 */       return Collator.getInstance();
/*     */     }
/* 225 */     if (Comparable.class.isAssignableFrom(clazz)) {
/* 226 */       return COMPARABLE_COMPARATOR;
/*     */     }
/* 228 */     return Collator.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useToString(int paramInt) {
/* 237 */     Comparator<?> comparator = super.getComparator(paramInt);
/* 238 */     if (comparator != null) {
/* 239 */       return false;
/*     */     }
/* 241 */     Class<?> clazz = ((TableModel)getModel()).getColumnClass(paramInt);
/* 242 */     if (clazz == String.class) {
/* 243 */       return false;
/*     */     }
/* 245 */     if (Comparable.class.isAssignableFrom(clazz)) {
/* 246 */       return false;
/*     */     }
/* 248 */     return true;
/*     */   }
/*     */   
/*     */   private class TableRowSorterModelWrapper
/*     */     extends DefaultRowSorter.ModelWrapper<M, Integer>
/*     */   {
/*     */     private TableRowSorterModelWrapper() {}
/*     */     
/*     */     public M getModel() {
/* 257 */       return TableRowSorter.this.tableModel;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 261 */       return (TableRowSorter.this.tableModel == null) ? 0 : TableRowSorter.this.tableModel.getColumnCount();
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 265 */       return (TableRowSorter.this.tableModel == null) ? 0 : TableRowSorter.this.tableModel.getRowCount();
/*     */     }
/*     */     
/*     */     public Object getValueAt(int param1Int1, int param1Int2) {
/* 269 */       return TableRowSorter.this.tableModel.getValueAt(param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public String getStringValueAt(int param1Int1, int param1Int2) {
/* 273 */       TableStringConverter tableStringConverter = TableRowSorter.this.getStringConverter();
/* 274 */       if (tableStringConverter != null) {
/*     */         
/* 276 */         String str1 = tableStringConverter.toString(
/* 277 */             (TableModel)TableRowSorter.this.tableModel, param1Int1, param1Int2);
/* 278 */         if (str1 != null) {
/* 279 */           return str1;
/*     */         }
/* 281 */         return "";
/*     */       } 
/*     */ 
/*     */       
/* 285 */       Object object = getValueAt(param1Int1, param1Int2);
/* 286 */       if (object == null) {
/* 287 */         return "";
/*     */       }
/* 289 */       String str = object.toString();
/* 290 */       if (str == null) {
/* 291 */         return "";
/*     */       }
/* 293 */       return str;
/*     */     }
/*     */     
/*     */     public Integer getIdentifier(int param1Int) {
/* 297 */       return Integer.valueOf(param1Int);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ComparableComparator implements Comparator {
/*     */     private ComparableComparator() {}
/*     */     
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/* 305 */       return ((Comparable<Object>)param1Object1).compareTo(param1Object2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/table/TableRowSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */