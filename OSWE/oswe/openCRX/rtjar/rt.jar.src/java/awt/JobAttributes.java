/*      */ package java.awt;
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
/*      */ public final class JobAttributes
/*      */   implements Cloneable
/*      */ {
/*      */   private int copies;
/*      */   private DefaultSelectionType defaultSelection;
/*      */   private DestinationType destination;
/*      */   private DialogType dialog;
/*      */   private String fileName;
/*      */   private int fromPage;
/*      */   private int maxPage;
/*      */   private int minPage;
/*      */   private MultipleDocumentHandlingType multipleDocumentHandling;
/*      */   private int[][] pageRanges;
/*      */   private int prFirst;
/*      */   private int prLast;
/*      */   private String printer;
/*      */   private SidesType sides;
/*      */   private int toPage;
/*      */   
/*      */   public static final class DefaultSelectionType
/*      */     extends AttributeValue
/*      */   {
/*      */     private static final int I_ALL = 0;
/*      */     private static final int I_RANGE = 1;
/*      */     private static final int I_SELECTION = 2;
/*   70 */     private static final String[] NAMES = new String[] { "all", "range", "selection" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   78 */     public static final DefaultSelectionType ALL = new DefaultSelectionType(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   84 */     public static final DefaultSelectionType RANGE = new DefaultSelectionType(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   90 */     public static final DefaultSelectionType SELECTION = new DefaultSelectionType(2);
/*      */ 
/*      */     
/*      */     private DefaultSelectionType(int param1Int) {
/*   94 */       super(param1Int, NAMES);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class DestinationType
/*      */     extends AttributeValue
/*      */   {
/*      */     private static final int I_FILE = 0;
/*      */     
/*      */     private static final int I_PRINTER = 1;
/*      */     
/*  106 */     private static final String[] NAMES = new String[] { "file", "printer" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  114 */     public static final DestinationType FILE = new DestinationType(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  120 */     public static final DestinationType PRINTER = new DestinationType(1);
/*      */ 
/*      */     
/*      */     private DestinationType(int param1Int) {
/*  124 */       super(param1Int, NAMES);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class DialogType
/*      */     extends AttributeValue
/*      */   {
/*      */     private static final int I_COMMON = 0;
/*      */     
/*      */     private static final int I_NATIVE = 1;
/*      */     
/*      */     private static final int I_NONE = 2;
/*  137 */     private static final String[] NAMES = new String[] { "common", "native", "none" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  145 */     public static final DialogType COMMON = new DialogType(0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  150 */     public static final DialogType NATIVE = new DialogType(1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     public static final DialogType NONE = new DialogType(2);
/*      */     
/*      */     private DialogType(int param1Int) {
/*  158 */       super(param1Int, NAMES);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class MultipleDocumentHandlingType
/*      */     extends AttributeValue
/*      */   {
/*      */     private static final int I_SEPARATE_DOCUMENTS_COLLATED_COPIES = 0;
/*      */ 
/*      */     
/*      */     private static final int I_SEPARATE_DOCUMENTS_UNCOLLATED_COPIES = 1;
/*      */ 
/*      */     
/*  173 */     private static final String[] NAMES = new String[] { "separate-documents-collated-copies", "separate-documents-uncollated-copies" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  183 */     public static final MultipleDocumentHandlingType SEPARATE_DOCUMENTS_COLLATED_COPIES = new MultipleDocumentHandlingType(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  191 */     public static final MultipleDocumentHandlingType SEPARATE_DOCUMENTS_UNCOLLATED_COPIES = new MultipleDocumentHandlingType(1);
/*      */ 
/*      */ 
/*      */     
/*      */     private MultipleDocumentHandlingType(int param1Int) {
/*  196 */       super(param1Int, NAMES);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class SidesType
/*      */     extends AttributeValue
/*      */   {
/*      */     private static final int I_ONE_SIDED = 0;
/*      */     
/*      */     private static final int I_TWO_SIDED_LONG_EDGE = 1;
/*      */     
/*      */     private static final int I_TWO_SIDED_SHORT_EDGE = 2;
/*      */     
/*  210 */     private static final String[] NAMES = new String[] { "one-sided", "two-sided-long-edge", "two-sided-short-edge" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  219 */     public static final SidesType ONE_SIDED = new SidesType(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  227 */     public static final SidesType TWO_SIDED_LONG_EDGE = new SidesType(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     public static final SidesType TWO_SIDED_SHORT_EDGE = new SidesType(2);
/*      */ 
/*      */     
/*      */     private SidesType(int param1Int) {
/*  240 */       super(param1Int, NAMES);
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
/*      */   public JobAttributes() {
/*  273 */     setCopiesToDefault();
/*  274 */     setDefaultSelection(DefaultSelectionType.ALL);
/*  275 */     setDestination(DestinationType.PRINTER);
/*  276 */     setDialog(DialogType.NATIVE);
/*  277 */     setMaxPage(2147483647);
/*  278 */     setMinPage(1);
/*  279 */     setMultipleDocumentHandlingToDefault();
/*  280 */     setSidesToDefault();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JobAttributes(JobAttributes paramJobAttributes) {
/*  290 */     set(paramJobAttributes);
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
/*      */   public JobAttributes(int paramInt1, DefaultSelectionType paramDefaultSelectionType, DestinationType paramDestinationType, DialogType paramDialogType, String paramString1, int paramInt2, int paramInt3, MultipleDocumentHandlingType paramMultipleDocumentHandlingType, int[][] paramArrayOfint, String paramString2, SidesType paramSidesType) {
/*  340 */     setCopies(paramInt1);
/*  341 */     setDefaultSelection(paramDefaultSelectionType);
/*  342 */     setDestination(paramDestinationType);
/*  343 */     setDialog(paramDialogType);
/*  344 */     setFileName(paramString1);
/*  345 */     setMaxPage(paramInt2);
/*  346 */     setMinPage(paramInt3);
/*  347 */     setMultipleDocumentHandling(paramMultipleDocumentHandlingType);
/*  348 */     setPageRanges(paramArrayOfint);
/*  349 */     setPrinter(paramString2);
/*  350 */     setSides(paramSidesType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/*  361 */       return super.clone();
/*  362 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  364 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(JobAttributes paramJobAttributes) {
/*  375 */     this.copies = paramJobAttributes.copies;
/*  376 */     this.defaultSelection = paramJobAttributes.defaultSelection;
/*  377 */     this.destination = paramJobAttributes.destination;
/*  378 */     this.dialog = paramJobAttributes.dialog;
/*  379 */     this.fileName = paramJobAttributes.fileName;
/*  380 */     this.fromPage = paramJobAttributes.fromPage;
/*  381 */     this.maxPage = paramJobAttributes.maxPage;
/*  382 */     this.minPage = paramJobAttributes.minPage;
/*  383 */     this.multipleDocumentHandling = paramJobAttributes.multipleDocumentHandling;
/*      */     
/*  385 */     this.pageRanges = paramJobAttributes.pageRanges;
/*  386 */     this.prFirst = paramJobAttributes.prFirst;
/*  387 */     this.prLast = paramJobAttributes.prLast;
/*  388 */     this.printer = paramJobAttributes.printer;
/*  389 */     this.sides = paramJobAttributes.sides;
/*  390 */     this.toPage = paramJobAttributes.toPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCopies() {
/*  401 */     return this.copies;
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
/*      */   public void setCopies(int paramInt) {
/*  414 */     if (paramInt <= 0) {
/*  415 */       throw new IllegalArgumentException("Invalid value for attribute copies");
/*      */     }
/*      */     
/*  418 */     this.copies = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCopiesToDefault() {
/*  426 */     setCopies(1);
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
/*      */   public DefaultSelectionType getDefaultSelection() {
/*  439 */     return this.defaultSelection;
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
/*      */   public void setDefaultSelection(DefaultSelectionType paramDefaultSelectionType) {
/*  453 */     if (paramDefaultSelectionType == null) {
/*  454 */       throw new IllegalArgumentException("Invalid value for attribute defaultSelection");
/*      */     }
/*      */     
/*  457 */     this.defaultSelection = paramDefaultSelectionType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DestinationType getDestination() {
/*  468 */     return this.destination;
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
/*      */   public void setDestination(DestinationType paramDestinationType) {
/*  480 */     if (paramDestinationType == null) {
/*  481 */       throw new IllegalArgumentException("Invalid value for attribute destination");
/*      */     }
/*      */     
/*  484 */     this.destination = paramDestinationType;
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
/*      */   public DialogType getDialog() {
/*  502 */     return this.dialog;
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
/*      */   public void setDialog(DialogType paramDialogType) {
/*  521 */     if (paramDialogType == null) {
/*  522 */       throw new IllegalArgumentException("Invalid value for attribute dialog");
/*      */     }
/*      */     
/*  525 */     this.dialog = paramDialogType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFileName() {
/*  535 */     return this.fileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileName(String paramString) {
/*  545 */     this.fileName = paramString;
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
/*      */   public int getFromPage() {
/*  562 */     if (this.fromPage != 0)
/*  563 */       return this.fromPage; 
/*  564 */     if (this.toPage != 0)
/*  565 */       return getMinPage(); 
/*  566 */     if (this.pageRanges != null) {
/*  567 */       return this.prFirst;
/*      */     }
/*  569 */     return getMinPage();
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
/*      */   public void setFromPage(int paramInt) {
/*  589 */     if (paramInt <= 0 || (this.toPage != 0 && paramInt > this.toPage) || paramInt < this.minPage || paramInt > this.maxPage)
/*      */     {
/*      */ 
/*      */       
/*  593 */       throw new IllegalArgumentException("Invalid value for attribute fromPage");
/*      */     }
/*      */     
/*  596 */     this.fromPage = paramInt;
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
/*      */   public int getMaxPage() {
/*  609 */     return this.maxPage;
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
/*      */   public void setMaxPage(int paramInt) {
/*  623 */     if (paramInt <= 0 || paramInt < this.minPage) {
/*  624 */       throw new IllegalArgumentException("Invalid value for attribute maxPage");
/*      */     }
/*      */     
/*  627 */     this.maxPage = paramInt;
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
/*      */   public int getMinPage() {
/*  640 */     return this.minPage;
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
/*      */   public void setMinPage(int paramInt) {
/*  654 */     if (paramInt <= 0 || paramInt > this.maxPage) {
/*  655 */       throw new IllegalArgumentException("Invalid value for attribute minPage");
/*      */     }
/*      */     
/*  658 */     this.minPage = paramInt;
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
/*      */   public MultipleDocumentHandlingType getMultipleDocumentHandling() {
/*  671 */     return this.multipleDocumentHandling;
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
/*      */   public void setMultipleDocumentHandling(MultipleDocumentHandlingType paramMultipleDocumentHandlingType) {
/*  687 */     if (paramMultipleDocumentHandlingType == null) {
/*  688 */       throw new IllegalArgumentException("Invalid value for attribute multipleDocumentHandling");
/*      */     }
/*      */     
/*  691 */     this.multipleDocumentHandling = paramMultipleDocumentHandlingType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMultipleDocumentHandlingToDefault() {
/*  700 */     setMultipleDocumentHandling(MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES);
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
/*      */   public int[][] getPageRanges() {
/*  723 */     if (this.pageRanges != null) {
/*      */ 
/*      */ 
/*      */       
/*  727 */       int[][] arrayOfInt = new int[this.pageRanges.length][2];
/*  728 */       for (byte b = 0; b < this.pageRanges.length; b++) {
/*  729 */         arrayOfInt[b][0] = this.pageRanges[b][0];
/*  730 */         arrayOfInt[b][1] = this.pageRanges[b][1];
/*      */       } 
/*  732 */       return arrayOfInt;
/*  733 */     }  if (this.fromPage != 0 || this.toPage != 0) {
/*  734 */       int j = getFromPage();
/*  735 */       int k = getToPage();
/*  736 */       return new int[][] { { j, k } };
/*      */     } 
/*  738 */     int i = getMinPage();
/*  739 */     return new int[][] { { i, i } };
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
/*      */   public void setPageRanges(int[][] paramArrayOfint) {
/*  769 */     String str = "Invalid value for attribute pageRanges";
/*  770 */     int i = 0;
/*  771 */     int j = 0;
/*      */     
/*  773 */     if (paramArrayOfint == null) {
/*  774 */       throw new IllegalArgumentException(str);
/*      */     }
/*      */     
/*  777 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/*  778 */       if (paramArrayOfint[b1] == null || (paramArrayOfint[b1]).length != 2 || paramArrayOfint[b1][0] <= j || paramArrayOfint[b1][1] < paramArrayOfint[b1][0])
/*      */       {
/*      */ 
/*      */         
/*  782 */         throw new IllegalArgumentException(str);
/*      */       }
/*  784 */       j = paramArrayOfint[b1][1];
/*  785 */       if (!i) {
/*  786 */         i = paramArrayOfint[b1][0];
/*      */       }
/*      */     } 
/*      */     
/*  790 */     if (i < this.minPage || j > this.maxPage) {
/*  791 */       throw new IllegalArgumentException(str);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  797 */     int[][] arrayOfInt = new int[paramArrayOfint.length][2];
/*  798 */     for (byte b2 = 0; b2 < paramArrayOfint.length; b2++) {
/*  799 */       arrayOfInt[b2][0] = paramArrayOfint[b2][0];
/*  800 */       arrayOfInt[b2][1] = paramArrayOfint[b2][1];
/*      */     } 
/*  802 */     this.pageRanges = arrayOfInt;
/*  803 */     this.prFirst = i;
/*  804 */     this.prLast = j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrinter() {
/*  814 */     return this.printer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrinter(String paramString) {
/*  824 */     this.printer = paramString;
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
/*      */   public SidesType getSides() {
/*  847 */     return this.sides;
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
/*      */   public void setSides(SidesType paramSidesType) {
/*  871 */     if (paramSidesType == null) {
/*  872 */       throw new IllegalArgumentException("Invalid value for attribute sides");
/*      */     }
/*      */     
/*  875 */     this.sides = paramSidesType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSidesToDefault() {
/*  884 */     setSides(SidesType.ONE_SIDED);
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
/*      */   public int getToPage() {
/*  901 */     if (this.toPage != 0)
/*  902 */       return this.toPage; 
/*  903 */     if (this.fromPage != 0)
/*  904 */       return this.fromPage; 
/*  905 */     if (this.pageRanges != null) {
/*  906 */       return this.prLast;
/*      */     }
/*  908 */     return getMinPage();
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
/*      */   public void setToPage(int paramInt) {
/*  928 */     if (paramInt <= 0 || (this.fromPage != 0 && paramInt < this.fromPage) || paramInt < this.minPage || paramInt > this.maxPage)
/*      */     {
/*      */ 
/*      */       
/*  932 */       throw new IllegalArgumentException("Invalid value for attribute toPage");
/*      */     }
/*      */     
/*  935 */     this.toPage = paramInt;
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
/*      */   public boolean equals(Object paramObject) {
/*  952 */     if (!(paramObject instanceof JobAttributes)) {
/*  953 */       return false;
/*      */     }
/*  955 */     JobAttributes jobAttributes = (JobAttributes)paramObject;
/*      */     
/*  957 */     if (this.fileName == null) {
/*  958 */       if (jobAttributes.fileName != null) {
/*  959 */         return false;
/*      */       }
/*      */     }
/*  962 */     else if (!this.fileName.equals(jobAttributes.fileName)) {
/*  963 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  967 */     if (this.pageRanges == null) {
/*  968 */       if (jobAttributes.pageRanges != null) {
/*  969 */         return false;
/*      */       }
/*      */     } else {
/*  972 */       if (jobAttributes.pageRanges == null || this.pageRanges.length != jobAttributes.pageRanges.length)
/*      */       {
/*  974 */         return false;
/*      */       }
/*  976 */       for (byte b = 0; b < this.pageRanges.length; b++) {
/*  977 */         if (this.pageRanges[b][0] != jobAttributes.pageRanges[b][0] || this.pageRanges[b][1] != jobAttributes.pageRanges[b][1])
/*      */         {
/*  979 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  984 */     if (this.printer == null) {
/*  985 */       if (jobAttributes.printer != null) {
/*  986 */         return false;
/*      */       }
/*      */     }
/*  989 */     else if (!this.printer.equals(jobAttributes.printer)) {
/*  990 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  994 */     return (this.copies == jobAttributes.copies && this.defaultSelection == jobAttributes.defaultSelection && this.destination == jobAttributes.destination && this.dialog == jobAttributes.dialog && this.fromPage == jobAttributes.fromPage && this.maxPage == jobAttributes.maxPage && this.minPage == jobAttributes.minPage && this.multipleDocumentHandling == jobAttributes.multipleDocumentHandling && this.prFirst == jobAttributes.prFirst && this.prLast == jobAttributes.prLast && this.sides == jobAttributes.sides && this.toPage == jobAttributes.toPage);
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
/*      */   public int hashCode() {
/* 1014 */     int i = (this.copies + this.fromPage + this.maxPage + this.minPage + this.prFirst + this.prLast + this.toPage) * 31 << 21;
/*      */     
/* 1016 */     if (this.pageRanges != null) {
/* 1017 */       int j = 0;
/* 1018 */       for (byte b = 0; b < this.pageRanges.length; b++) {
/* 1019 */         j += this.pageRanges[b][0] + this.pageRanges[b][1];
/*      */       }
/* 1021 */       i ^= j * 31 << 11;
/*      */     } 
/* 1023 */     if (this.fileName != null) {
/* 1024 */       i ^= this.fileName.hashCode();
/*      */     }
/* 1026 */     if (this.printer != null) {
/* 1027 */       i ^= this.printer.hashCode();
/*      */     }
/* 1029 */     return this.defaultSelection.hashCode() << 6 ^ this.destination
/* 1030 */       .hashCode() << 5 ^ this.dialog
/* 1031 */       .hashCode() << 3 ^ this.multipleDocumentHandling
/* 1032 */       .hashCode() << 2 ^ this.sides
/* 1033 */       .hashCode() ^ i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1043 */     int[][] arrayOfInt = getPageRanges();
/* 1044 */     String str = "[";
/* 1045 */     boolean bool = true;
/* 1046 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1047 */       if (bool) {
/* 1048 */         bool = false;
/*      */       } else {
/* 1050 */         str = str + ",";
/*      */       } 
/* 1052 */       str = str + arrayOfInt[b][0] + ":" + arrayOfInt[b][1];
/*      */     } 
/* 1054 */     str = str + "]";
/*      */     
/* 1056 */     return "copies=" + getCopies() + ",defaultSelection=" + 
/* 1057 */       getDefaultSelection() + ",destination=" + getDestination() + ",dialog=" + 
/* 1058 */       getDialog() + ",fileName=" + getFileName() + ",fromPage=" + 
/* 1059 */       getFromPage() + ",maxPage=" + getMaxPage() + ",minPage=" + 
/* 1060 */       getMinPage() + ",multiple-document-handling=" + 
/* 1061 */       getMultipleDocumentHandling() + ",page-ranges=" + str + ",printer=" + 
/* 1062 */       getPrinter() + ",sides=" + getSides() + ",toPage=" + 
/* 1063 */       getToPage();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/JobAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */