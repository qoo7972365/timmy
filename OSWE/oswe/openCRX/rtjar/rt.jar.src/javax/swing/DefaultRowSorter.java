/*      */ package javax.swing;
/*      */ 
/*      */ import java.text.Collator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DefaultRowSorter<M, I>
/*      */   extends RowSorter<M>
/*      */ {
/*      */   private boolean sortsOnUpdates;
/*      */   private Row[] viewToModel;
/*      */   private int[] modelToView;
/*      */   private Comparator[] comparators;
/*      */   private boolean[] isSortable;
/*      */   private RowSorter.SortKey[] cachedSortKeys;
/*      */   private Comparator[] sortComparators;
/*      */   private RowFilter<? super M, ? super I> filter;
/*      */   private FilterEntry filterEntry;
/*  196 */   private List<RowSorter.SortKey> sortKeys = Collections.emptyList(); private boolean[] useToString;
/*  197 */   private int maxSortKeys = 3;
/*      */ 
/*      */   
/*      */   private boolean sorted;
/*      */ 
/*      */   
/*      */   private ModelWrapper<M, I> modelWrapper;
/*      */ 
/*      */   
/*      */   private int modelRowCount;
/*      */ 
/*      */   
/*      */   protected final void setModelWrapper(ModelWrapper<M, I> paramModelWrapper) {
/*  210 */     if (paramModelWrapper == null) {
/*  211 */       throw new IllegalArgumentException("modelWrapper most be non-null");
/*      */     }
/*      */     
/*  214 */     ModelWrapper<M, I> modelWrapper = this.modelWrapper;
/*  215 */     this.modelWrapper = paramModelWrapper;
/*  216 */     if (modelWrapper != null) {
/*  217 */       modelStructureChanged();
/*      */     }
/*      */     else {
/*      */       
/*  221 */       this.modelRowCount = getModelWrapper().getRowCount();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ModelWrapper<M, I> getModelWrapper() {
/*  233 */     return this.modelWrapper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final M getModel() {
/*  242 */     return getModelWrapper().getModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSortable(int paramInt, boolean paramBoolean) {
/*  261 */     checkColumn(paramInt);
/*  262 */     if (this.isSortable == null) {
/*  263 */       this.isSortable = new boolean[getModelWrapper().getColumnCount()];
/*  264 */       for (int i = this.isSortable.length - 1; i >= 0; i--) {
/*  265 */         this.isSortable[i] = true;
/*      */       }
/*      */     } 
/*  268 */     this.isSortable[paramInt] = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSortable(int paramInt) {
/*  281 */     checkColumn(paramInt);
/*  282 */     return (this.isSortable == null) ? true : this.isSortable[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSortKeys(List<? extends RowSorter.SortKey> paramList) {
/*  299 */     List<RowSorter.SortKey> list = this.sortKeys;
/*  300 */     if (paramList != null && paramList.size() > 0) {
/*  301 */       int i = getModelWrapper().getColumnCount();
/*  302 */       for (RowSorter.SortKey sortKey : paramList) {
/*  303 */         if (sortKey == null || sortKey.getColumn() < 0 || sortKey
/*  304 */           .getColumn() >= i) {
/*  305 */           throw new IllegalArgumentException("Invalid SortKey");
/*      */         }
/*      */       } 
/*  308 */       this.sortKeys = Collections.unmodifiableList(new ArrayList<>(paramList));
/*      */     }
/*      */     else {
/*      */       
/*  312 */       this.sortKeys = Collections.emptyList();
/*      */     } 
/*  314 */     if (!this.sortKeys.equals(list)) {
/*  315 */       fireSortOrderChanged();
/*  316 */       if (this.viewToModel == null) {
/*      */ 
/*      */         
/*  319 */         sort();
/*      */       } else {
/*  321 */         sortExistingData();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<? extends RowSorter.SortKey> getSortKeys() {
/*  335 */     return this.sortKeys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxSortKeys(int paramInt) {
/*  367 */     if (paramInt < 1) {
/*  368 */       throw new IllegalArgumentException("Invalid max");
/*      */     }
/*  370 */     this.maxSortKeys = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSortKeys() {
/*  379 */     return this.maxSortKeys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSortsOnUpdates(boolean paramBoolean) {
/*  392 */     this.sortsOnUpdates = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSortsOnUpdates() {
/*  402 */     return this.sortsOnUpdates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowFilter(RowFilter<? super M, ? super I> paramRowFilter) {
/*  423 */     this.filter = paramRowFilter;
/*  424 */     sort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowFilter<? super M, ? super I> getRowFilter() {
/*  434 */     return this.filter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleSortOrder(int paramInt) {
/*  452 */     checkColumn(paramInt);
/*  453 */     if (isSortable(paramInt)) {
/*  454 */       List<RowSorter.SortKey> list = new ArrayList<>(getSortKeys());
/*      */       
/*      */       int i;
/*  457 */       for (i = list.size() - 1; i >= 0 && (
/*  458 */         (RowSorter.SortKey)list.get(i)).getColumn() != paramInt; i--);
/*      */ 
/*      */ 
/*      */       
/*  462 */       if (i == -1) {
/*      */         
/*  464 */         RowSorter.SortKey sortKey = new RowSorter.SortKey(paramInt, SortOrder.ASCENDING);
/*  465 */         list.add(0, sortKey);
/*      */       }
/*  467 */       else if (i == 0) {
/*      */         
/*  469 */         list.set(0, toggle(list.get(0)));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  474 */         list.remove(i);
/*  475 */         list.add(0, new RowSorter.SortKey(paramInt, SortOrder.ASCENDING));
/*      */       } 
/*  477 */       if (list.size() > getMaxSortKeys()) {
/*  478 */         list = list.subList(0, getMaxSortKeys());
/*      */       }
/*  480 */       setSortKeys(list);
/*      */     } 
/*      */   }
/*      */   
/*      */   private RowSorter.SortKey toggle(RowSorter.SortKey paramSortKey) {
/*  485 */     if (paramSortKey.getSortOrder() == SortOrder.ASCENDING) {
/*  486 */       return new RowSorter.SortKey(paramSortKey.getColumn(), SortOrder.DESCENDING);
/*      */     }
/*  488 */     return new RowSorter.SortKey(paramSortKey.getColumn(), SortOrder.ASCENDING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int convertRowIndexToView(int paramInt) {
/*  497 */     if (this.modelToView == null) {
/*  498 */       if (paramInt < 0 || paramInt >= getModelWrapper().getRowCount()) {
/*  499 */         throw new IndexOutOfBoundsException("Invalid index");
/*      */       }
/*  501 */       return paramInt;
/*      */     } 
/*  503 */     return this.modelToView[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int convertRowIndexToModel(int paramInt) {
/*  512 */     if (this.viewToModel == null) {
/*  513 */       if (paramInt < 0 || paramInt >= getModelWrapper().getRowCount()) {
/*  514 */         throw new IndexOutOfBoundsException("Invalid index");
/*      */       }
/*  516 */       return paramInt;
/*      */     } 
/*  518 */     return (this.viewToModel[paramInt]).modelIndex;
/*      */   }
/*      */   
/*      */   private boolean isUnsorted() {
/*  522 */     List<? extends RowSorter.SortKey> list = getSortKeys();
/*  523 */     int i = list.size();
/*  524 */     return (i == 0 || ((RowSorter.SortKey)list.get(0)).getSortOrder() == SortOrder.UNSORTED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sortExistingData() {
/*  533 */     int[] arrayOfInt = getViewToModelAsInts(this.viewToModel);
/*      */     
/*  535 */     updateUseToString();
/*  536 */     cacheSortKeys(getSortKeys());
/*      */     
/*  538 */     if (isUnsorted()) {
/*  539 */       if (getRowFilter() == null) {
/*  540 */         this.viewToModel = null;
/*  541 */         this.modelToView = null;
/*      */       } else {
/*  543 */         byte b1 = 0;
/*  544 */         for (byte b2 = 0; b2 < this.modelToView.length; b2++) {
/*  545 */           if (this.modelToView[b2] != -1) {
/*  546 */             (this.viewToModel[b1]).modelIndex = b2;
/*  547 */             this.modelToView[b2] = b1++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  553 */       Arrays.sort((Object[])this.viewToModel);
/*      */ 
/*      */       
/*  556 */       setModelToViewFromViewToModel(false);
/*      */     } 
/*  558 */     fireRowSorterChanged(arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort() {
/*  571 */     this.sorted = true;
/*  572 */     int[] arrayOfInt = getViewToModelAsInts(this.viewToModel);
/*  573 */     updateUseToString();
/*  574 */     if (isUnsorted()) {
/*      */       
/*  576 */       this.cachedSortKeys = new RowSorter.SortKey[0];
/*  577 */       if (getRowFilter() == null) {
/*      */         
/*  579 */         if (this.viewToModel != null)
/*      */         {
/*  581 */           this.viewToModel = null;
/*  582 */           this.modelToView = null;
/*      */         }
/*      */         else
/*      */         {
/*      */           return;
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  592 */         initializeFilteredMapping();
/*      */       } 
/*      */     } else {
/*      */       
/*  596 */       cacheSortKeys(getSortKeys());
/*      */       
/*  598 */       if (getRowFilter() != null) {
/*  599 */         initializeFilteredMapping();
/*      */       } else {
/*      */         
/*  602 */         createModelToView(getModelWrapper().getRowCount());
/*  603 */         createViewToModel(getModelWrapper().getRowCount());
/*      */       } 
/*      */ 
/*      */       
/*  607 */       Arrays.sort((Object[])this.viewToModel);
/*      */ 
/*      */       
/*  610 */       setModelToViewFromViewToModel(false);
/*      */     } 
/*  612 */     fireRowSorterChanged(arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateUseToString() {
/*  619 */     int i = getModelWrapper().getColumnCount();
/*  620 */     if (this.useToString == null || this.useToString.length != i) {
/*  621 */       this.useToString = new boolean[i];
/*      */     }
/*  623 */     for (; --i >= 0; i--) {
/*  624 */       this.useToString[i] = useToString(i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeFilteredMapping() {
/*  633 */     int i = getModelWrapper().getRowCount();
/*      */     
/*  635 */     byte b3 = 0;
/*      */ 
/*      */     
/*  638 */     createModelToView(i); byte b1;
/*  639 */     for (b1 = 0; b1 < i; b1++) {
/*  640 */       if (include(b1)) {
/*  641 */         this.modelToView[b1] = b1 - b3;
/*      */       } else {
/*      */         
/*  644 */         this.modelToView[b1] = -1;
/*  645 */         b3++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  650 */     createViewToModel(i - b3); byte b2;
/*  651 */     for (b1 = 0, b2 = 0; b1 < i; b1++) {
/*  652 */       if (this.modelToView[b1] != -1) {
/*  653 */         (this.viewToModel[b2++]).modelIndex = b1;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createModelToView(int paramInt) {
/*  662 */     if (this.modelToView == null || this.modelToView.length != paramInt) {
/*  663 */       this.modelToView = new int[paramInt];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createViewToModel(int paramInt) {
/*  671 */     int i = 0;
/*  672 */     if (this.viewToModel != null) {
/*  673 */       i = Math.min(paramInt, this.viewToModel.length);
/*  674 */       if (this.viewToModel.length != paramInt) {
/*  675 */         Row[] arrayOfRow = this.viewToModel;
/*  676 */         this.viewToModel = new Row[paramInt];
/*  677 */         System.arraycopy(arrayOfRow, 0, this.viewToModel, 0, i);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  682 */       this.viewToModel = new Row[paramInt];
/*      */     } 
/*      */     int j;
/*  685 */     for (j = 0; j < i; j++) {
/*  686 */       (this.viewToModel[j]).modelIndex = j;
/*      */     }
/*  688 */     for (j = i; j < paramInt; j++) {
/*  689 */       this.viewToModel[j] = new Row(this, j);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cacheSortKeys(List<? extends RowSorter.SortKey> paramList) {
/*  697 */     int i = paramList.size();
/*  698 */     this.sortComparators = new Comparator[i];
/*  699 */     for (byte b = 0; b < i; b++) {
/*  700 */       this.sortComparators[b] = getComparator0(((RowSorter.SortKey)paramList.get(b)).getColumn());
/*      */     }
/*  702 */     this.cachedSortKeys = paramList.<RowSorter.SortKey>toArray(new RowSorter.SortKey[i]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean useToString(int paramInt) {
/*  718 */     return (getComparator(paramInt) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setModelToViewFromViewToModel(boolean paramBoolean) {
/*  728 */     if (paramBoolean) {
/*  729 */       for (int j = this.modelToView.length - 1; j >= 0; j--) {
/*  730 */         this.modelToView[j] = -1;
/*      */       }
/*      */     }
/*  733 */     for (int i = this.viewToModel.length - 1; i >= 0; i--) {
/*  734 */       this.modelToView[(this.viewToModel[i]).modelIndex] = i;
/*      */     }
/*      */   }
/*      */   
/*      */   private int[] getViewToModelAsInts(Row[] paramArrayOfRow) {
/*  739 */     if (paramArrayOfRow != null) {
/*  740 */       int[] arrayOfInt = new int[paramArrayOfRow.length];
/*  741 */       for (int i = paramArrayOfRow.length - 1; i >= 0; i--) {
/*  742 */         arrayOfInt[i] = (paramArrayOfRow[i]).modelIndex;
/*      */       }
/*  744 */       return arrayOfInt;
/*      */     } 
/*  746 */     return new int[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComparator(int paramInt, Comparator<?> paramComparator) {
/*  761 */     checkColumn(paramInt);
/*  762 */     if (this.comparators == null) {
/*  763 */       this.comparators = new Comparator[getModelWrapper().getColumnCount()];
/*      */     }
/*  765 */     this.comparators[paramInt] = paramComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<?> getComparator(int paramInt) {
/*  780 */     checkColumn(paramInt);
/*  781 */     if (this.comparators != null) {
/*  782 */       return this.comparators[paramInt];
/*      */     }
/*  784 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Comparator getComparator0(int paramInt) {
/*  790 */     Comparator<?> comparator = getComparator(paramInt);
/*  791 */     if (comparator != null) {
/*  792 */       return comparator;
/*      */     }
/*      */ 
/*      */     
/*  796 */     return Collator.getInstance();
/*      */   }
/*      */   
/*      */   private RowFilter.Entry<M, I> getFilterEntry(int paramInt) {
/*  800 */     if (this.filterEntry == null) {
/*  801 */       this.filterEntry = new FilterEntry();
/*      */     }
/*  803 */     this.filterEntry.modelIndex = paramInt;
/*  804 */     return this.filterEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewRowCount() {
/*  811 */     if (this.viewToModel != null)
/*      */     {
/*  813 */       return this.viewToModel.length;
/*      */     }
/*  815 */     return getModelWrapper().getRowCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getModelRowCount() {
/*  822 */     return getModelWrapper().getRowCount();
/*      */   }
/*      */   
/*      */   private void allChanged() {
/*  826 */     this.modelToView = null;
/*  827 */     this.viewToModel = null;
/*  828 */     this.comparators = null;
/*  829 */     this.isSortable = null;
/*  830 */     if (isUnsorted()) {
/*      */ 
/*      */       
/*  833 */       sort();
/*      */     } else {
/*  835 */       setSortKeys((List<? extends RowSorter.SortKey>)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void modelStructureChanged() {
/*  843 */     allChanged();
/*  844 */     this.modelRowCount = getModelWrapper().getRowCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void allRowsChanged() {
/*  851 */     this.modelRowCount = getModelWrapper().getRowCount();
/*  852 */     sort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rowsInserted(int paramInt1, int paramInt2) {
/*  861 */     checkAgainstModel(paramInt1, paramInt2);
/*  862 */     int i = getModelWrapper().getRowCount();
/*  863 */     if (paramInt2 >= i) {
/*  864 */       throw new IndexOutOfBoundsException("Invalid range");
/*      */     }
/*  866 */     this.modelRowCount = i;
/*  867 */     if (shouldOptimizeChange(paramInt1, paramInt2)) {
/*  868 */       rowsInserted0(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rowsDeleted(int paramInt1, int paramInt2) {
/*  878 */     checkAgainstModel(paramInt1, paramInt2);
/*  879 */     if (paramInt1 >= this.modelRowCount || paramInt2 >= this.modelRowCount) {
/*  880 */       throw new IndexOutOfBoundsException("Invalid range");
/*      */     }
/*  882 */     this.modelRowCount = getModelWrapper().getRowCount();
/*  883 */     if (shouldOptimizeChange(paramInt1, paramInt2)) {
/*  884 */       rowsDeleted0(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rowsUpdated(int paramInt1, int paramInt2) {
/*  894 */     checkAgainstModel(paramInt1, paramInt2);
/*  895 */     if (paramInt1 >= this.modelRowCount || paramInt2 >= this.modelRowCount) {
/*  896 */       throw new IndexOutOfBoundsException("Invalid range");
/*      */     }
/*  898 */     if (getSortsOnUpdates()) {
/*  899 */       if (shouldOptimizeChange(paramInt1, paramInt2)) {
/*  900 */         rowsUpdated0(paramInt1, paramInt2);
/*      */       }
/*      */     } else {
/*      */       
/*  904 */       this.sorted = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rowsUpdated(int paramInt1, int paramInt2, int paramInt3) {
/*  914 */     checkColumn(paramInt3);
/*  915 */     rowsUpdated(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   private void checkAgainstModel(int paramInt1, int paramInt2) {
/*  919 */     if (paramInt1 > paramInt2 || paramInt1 < 0 || paramInt2 < 0 || paramInt1 > this.modelRowCount)
/*      */     {
/*  921 */       throw new IndexOutOfBoundsException("Invalid range");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean include(int paramInt) {
/*  929 */     RowFilter<? super M, ? super I> rowFilter = getRowFilter();
/*  930 */     if (rowFilter != null) {
/*  931 */       return rowFilter.include(getFilterEntry(paramInt));
/*      */     }
/*      */     
/*  934 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int compare(int paramInt1, int paramInt2) {
/*  944 */     for (byte b = 0; b < this.cachedSortKeys.length; b++) {
/*  945 */       int j, i = this.cachedSortKeys[b].getColumn();
/*  946 */       SortOrder sortOrder = this.cachedSortKeys[b].getSortOrder();
/*  947 */       if (sortOrder == SortOrder.UNSORTED) {
/*  948 */         j = paramInt1 - paramInt2;
/*      */       } else {
/*      */         Object object1; Object object2;
/*  951 */         if (this.useToString[i]) {
/*  952 */           object1 = getModelWrapper().getStringValueAt(paramInt1, i);
/*  953 */           object2 = getModelWrapper().getStringValueAt(paramInt2, i);
/*      */         } else {
/*  955 */           object1 = getModelWrapper().getValueAt(paramInt1, i);
/*  956 */           object2 = getModelWrapper().getValueAt(paramInt2, i);
/*      */         } 
/*      */         
/*  959 */         if (object1 == null) {
/*  960 */           if (object2 == null) {
/*  961 */             j = 0;
/*      */           } else {
/*  963 */             j = -1;
/*      */           } 
/*  965 */         } else if (object2 == null) {
/*  966 */           j = 1;
/*      */         } else {
/*  968 */           j = this.sortComparators[b].compare(object1, object2);
/*      */         } 
/*  970 */         if (sortOrder == SortOrder.DESCENDING) {
/*  971 */           j *= -1;
/*      */         }
/*      */       } 
/*  974 */       if (j != 0) {
/*  975 */         return j;
/*      */       }
/*      */     } 
/*      */     
/*  979 */     return paramInt1 - paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isTransformed() {
/*  986 */     return (this.viewToModel != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertInOrder(List<Row> paramList, Row[] paramArrayOfRow) {
/*  996 */     int i = 0;
/*      */     
/*  998 */     int j = paramList.size();
/*  999 */     for (byte b = 0; b < j; b++) {
/* 1000 */       int k = Arrays.binarySearch((Object[])paramArrayOfRow, paramList.get(b));
/* 1001 */       if (k < 0) {
/* 1002 */         k = -1 - k;
/*      */       }
/* 1004 */       System.arraycopy(paramArrayOfRow, i, this.viewToModel, i + b, k - i);
/*      */       
/* 1006 */       this.viewToModel[k + b] = paramList.get(b);
/* 1007 */       i = k;
/*      */     } 
/* 1009 */     System.arraycopy(paramArrayOfRow, i, this.viewToModel, i + j, paramArrayOfRow.length - i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldOptimizeChange(int paramInt1, int paramInt2) {
/* 1019 */     if (!isTransformed())
/*      */     {
/* 1021 */       return false;
/*      */     }
/* 1023 */     if (!this.sorted || paramInt2 - paramInt1 > this.viewToModel.length / 10) {
/*      */       
/* 1025 */       sort();
/* 1026 */       return false;
/*      */     } 
/* 1028 */     return true;
/*      */   }
/*      */   
/*      */   private void rowsInserted0(int paramInt1, int paramInt2) {
/* 1032 */     int[] arrayOfInt = getViewToModelAsInts(this.viewToModel);
/*      */     
/* 1034 */     int j = paramInt2 - paramInt1 + 1;
/* 1035 */     ArrayList<Row> arrayList = new ArrayList(j);
/*      */     
/*      */     int i;
/* 1038 */     for (i = paramInt1; i <= paramInt2; i++) {
/* 1039 */       if (include(i)) {
/* 1040 */         arrayList.add(new Row(this, i));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1046 */     for (i = this.modelToView.length - 1; i >= paramInt1; i--) {
/* 1047 */       int k = this.modelToView[i];
/* 1048 */       if (k != -1) {
/* 1049 */         (this.viewToModel[k]).modelIndex += j;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1054 */     if (arrayList.size() > 0) {
/* 1055 */       Collections.sort(arrayList);
/* 1056 */       Row[] arrayOfRow = this.viewToModel;
/* 1057 */       this.viewToModel = new Row[this.viewToModel.length + arrayList.size()];
/* 1058 */       insertInOrder(arrayList, arrayOfRow);
/*      */     } 
/*      */ 
/*      */     
/* 1062 */     createModelToView(getModelWrapper().getRowCount());
/* 1063 */     setModelToViewFromViewToModel(true);
/*      */ 
/*      */     
/* 1066 */     fireRowSorterChanged(arrayOfInt);
/*      */   }
/*      */   
/*      */   private void rowsDeleted0(int paramInt1, int paramInt2) {
/* 1070 */     int[] arrayOfInt = getViewToModelAsInts(this.viewToModel);
/* 1071 */     byte b = 0;
/*      */ 
/*      */     
/*      */     int i;
/*      */     
/* 1076 */     for (i = paramInt1; i <= paramInt2; i++) {
/* 1077 */       int k = this.modelToView[i];
/* 1078 */       if (k != -1) {
/* 1079 */         b++;
/* 1080 */         this.viewToModel[k] = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1085 */     int j = paramInt2 - paramInt1 + 1;
/* 1086 */     for (i = this.modelToView.length - 1; i > paramInt2; i--) {
/* 1087 */       int k = this.modelToView[i];
/* 1088 */       if (k != -1) {
/* 1089 */         (this.viewToModel[k]).modelIndex -= j;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1094 */     if (b > 0) {
/* 1095 */       Row[] arrayOfRow = new Row[this.viewToModel.length - b];
/*      */       
/* 1097 */       int k = 0;
/* 1098 */       int m = 0;
/* 1099 */       for (i = 0; i < this.viewToModel.length; i++) {
/* 1100 */         if (this.viewToModel[i] == null) {
/* 1101 */           System.arraycopy(this.viewToModel, m, arrayOfRow, k, i - m);
/*      */           
/* 1103 */           k += i - m;
/* 1104 */           m = i + 1;
/*      */         } 
/*      */       } 
/* 1107 */       System.arraycopy(this.viewToModel, m, arrayOfRow, k, this.viewToModel.length - m);
/*      */       
/* 1109 */       this.viewToModel = arrayOfRow;
/*      */     } 
/*      */ 
/*      */     
/* 1113 */     createModelToView(getModelWrapper().getRowCount());
/* 1114 */     setModelToViewFromViewToModel(true);
/*      */ 
/*      */     
/* 1117 */     fireRowSorterChanged(arrayOfInt);
/*      */   }
/*      */   
/*      */   private void rowsUpdated0(int paramInt1, int paramInt2) {
/* 1121 */     int[] arrayOfInt = getViewToModelAsInts(this.viewToModel);
/*      */     
/* 1123 */     int i = paramInt2 - paramInt1 + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     if (getRowFilter() == null) {
/*      */ 
/*      */ 
/*      */       
/* 1132 */       Row[] arrayOfRow1 = new Row[i]; int j; byte b;
/* 1133 */       for (b = 0, j = paramInt1; j <= paramInt2; j++, b++) {
/* 1134 */         arrayOfRow1[b] = this.viewToModel[this.modelToView[j]];
/*      */       }
/*      */ 
/*      */       
/* 1138 */       Arrays.sort((Object[])arrayOfRow1);
/*      */ 
/*      */ 
/*      */       
/* 1142 */       Row[] arrayOfRow2 = new Row[this.viewToModel.length - i];
/* 1143 */       for (j = 0, b = 0; j < this.viewToModel.length; j++) {
/* 1144 */         int k = (this.viewToModel[j]).modelIndex;
/* 1145 */         if (k < paramInt1 || k > paramInt2) {
/* 1146 */           arrayOfRow2[b++] = this.viewToModel[j];
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1151 */       insertInOrder(Arrays.asList(arrayOfRow1), arrayOfRow2);
/*      */ 
/*      */       
/* 1154 */       setModelToViewFromViewToModel(false);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1161 */       ArrayList<Row> arrayList = new ArrayList(i);
/* 1162 */       byte b2 = 0;
/* 1163 */       byte b3 = 0;
/* 1164 */       byte b4 = 0; int j;
/* 1165 */       for (j = paramInt1; j <= paramInt2; j++) {
/* 1166 */         if (this.modelToView[j] == -1) {
/*      */           
/* 1168 */           if (include(j))
/*      */           {
/* 1170 */             arrayList.add(new Row(this, j));
/* 1171 */             b2++;
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1177 */           if (!include(j)) {
/* 1178 */             b3++;
/*      */           } else {
/*      */             
/* 1181 */             arrayList.add(this.viewToModel[this.modelToView[j]]);
/*      */           } 
/* 1183 */           this.modelToView[j] = -2;
/* 1184 */           b4++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1189 */       Collections.sort(arrayList);
/*      */ 
/*      */ 
/*      */       
/* 1193 */       Row[] arrayOfRow = new Row[this.viewToModel.length - b4]; byte b1;
/* 1194 */       for (j = 0, b1 = 0; j < this.viewToModel.length; j++) {
/* 1195 */         int k = (this.viewToModel[j]).modelIndex;
/* 1196 */         if (this.modelToView[k] != -2) {
/* 1197 */           arrayOfRow[b1++] = this.viewToModel[j];
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1202 */       if (b2 != b3) {
/* 1203 */         this.viewToModel = new Row[this.viewToModel.length + b2 - b3];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1208 */       insertInOrder(arrayList, arrayOfRow);
/*      */ 
/*      */       
/* 1211 */       setModelToViewFromViewToModel(true);
/*      */     } 
/*      */     
/* 1214 */     fireRowSorterChanged(arrayOfInt);
/*      */   }
/*      */   
/*      */   private void checkColumn(int paramInt) {
/* 1218 */     if (paramInt < 0 || paramInt >= getModelWrapper().getColumnCount()) {
/* 1219 */       throw new IndexOutOfBoundsException("column beyond range of TableModel");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static abstract class ModelWrapper<M, I>
/*      */   {
/*      */     public abstract M getModel();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getColumnCount();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getRowCount();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Object getValueAt(int param1Int1, int param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getStringValueAt(int param1Int1, int param1Int2) {
/* 1301 */       Object object = getValueAt(param1Int1, param1Int2);
/* 1302 */       if (object == null) {
/* 1303 */         return "";
/*      */       }
/* 1305 */       String str = object.toString();
/* 1306 */       if (str == null) {
/* 1307 */         return "";
/*      */       }
/* 1309 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract I getIdentifier(int param1Int);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class FilterEntry
/*      */     extends RowFilter.Entry<M, I>
/*      */   {
/*      */     int modelIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FilterEntry() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public M getModel() {
/* 1340 */       return (M)DefaultRowSorter.this.getModelWrapper().getModel();
/*      */     }
/*      */     
/*      */     public int getValueCount() {
/* 1344 */       return DefaultRowSorter.this.getModelWrapper().getColumnCount();
/*      */     }
/*      */     
/*      */     public Object getValue(int param1Int) {
/* 1348 */       return DefaultRowSorter.this.getModelWrapper().getValueAt(this.modelIndex, param1Int);
/*      */     }
/*      */     
/*      */     public String getStringValue(int param1Int) {
/* 1352 */       return DefaultRowSorter.this.getModelWrapper().getStringValueAt(this.modelIndex, param1Int);
/*      */     }
/*      */     
/*      */     public I getIdentifier() {
/* 1356 */       return (I)DefaultRowSorter.this.getModelWrapper().getIdentifier(this.modelIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Row
/*      */     implements Comparable<Row>
/*      */   {
/*      */     private DefaultRowSorter sorter;
/*      */     
/*      */     int modelIndex;
/*      */ 
/*      */     
/*      */     public Row(DefaultRowSorter param1DefaultRowSorter, int param1Int) {
/* 1371 */       this.sorter = param1DefaultRowSorter;
/* 1372 */       this.modelIndex = param1Int;
/*      */     }
/*      */     
/*      */     public int compareTo(Row param1Row) {
/* 1376 */       return this.sorter.compare(this.modelIndex, param1Row.modelIndex);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DefaultRowSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */