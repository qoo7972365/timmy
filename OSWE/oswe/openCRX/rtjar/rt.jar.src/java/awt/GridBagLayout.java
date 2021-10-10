/*      */ package java.awt;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GridBagLayout
/*      */   implements LayoutManager2, Serializable
/*      */ {
/*      */   static final int EMPIRICMULTIPLIER = 2;
/*      */   protected static final int MAXGRIDSIZE = 512;
/*      */   protected static final int MINSIZE = 1;
/*      */   protected static final int PREFERREDSIZE = 2;
/*  495 */   protected Hashtable<Component, GridBagConstraints> comptable = new Hashtable<>();
/*  496 */   protected GridBagConstraints defaultConstraints = new GridBagConstraints();
/*      */   protected GridBagLayoutInfo layoutInfo;
/*      */   public int[] columnWidths;
/*      */   public int[] rowHeights;
/*      */   public double[] columnWeights;
/*      */   public double[] rowWeights;
/*      */   private Component componentAdjusting;
/*      */   
/*      */   public void setConstraints(Component paramComponent, GridBagConstraints paramGridBagConstraints) {
/*  505 */     this.comptable.put(paramComponent, (GridBagConstraints)paramGridBagConstraints.clone());
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
/*      */   public GridBagConstraints getConstraints(Component paramComponent) {
/*  517 */     GridBagConstraints gridBagConstraints = this.comptable.get(paramComponent);
/*  518 */     if (gridBagConstraints == null) {
/*  519 */       setConstraints(paramComponent, this.defaultConstraints);
/*  520 */       gridBagConstraints = this.comptable.get(paramComponent);
/*      */     } 
/*  522 */     return (GridBagConstraints)gridBagConstraints.clone();
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
/*      */   protected GridBagConstraints lookupConstraints(Component paramComponent) {
/*  539 */     GridBagConstraints gridBagConstraints = this.comptable.get(paramComponent);
/*  540 */     if (gridBagConstraints == null) {
/*  541 */       setConstraints(paramComponent, this.defaultConstraints);
/*  542 */       gridBagConstraints = this.comptable.get(paramComponent);
/*      */     } 
/*  544 */     return gridBagConstraints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeConstraints(Component paramComponent) {
/*  552 */     this.comptable.remove(paramComponent);
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
/*      */   public Point getLayoutOrigin() {
/*  568 */     Point point = new Point(0, 0);
/*  569 */     if (this.layoutInfo != null) {
/*  570 */       point.x = this.layoutInfo.startx;
/*  571 */       point.y = this.layoutInfo.starty;
/*      */     } 
/*  573 */     return point;
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
/*      */   public int[][] getLayoutDimensions() {
/*  586 */     if (this.layoutInfo == null) {
/*  587 */       return new int[2][0];
/*      */     }
/*  589 */     int[][] arrayOfInt = new int[2][];
/*  590 */     arrayOfInt[0] = new int[this.layoutInfo.width];
/*  591 */     arrayOfInt[1] = new int[this.layoutInfo.height];
/*      */     
/*  593 */     System.arraycopy(this.layoutInfo.minWidth, 0, arrayOfInt[0], 0, this.layoutInfo.width);
/*  594 */     System.arraycopy(this.layoutInfo.minHeight, 0, arrayOfInt[1], 0, this.layoutInfo.height);
/*      */     
/*  596 */     return arrayOfInt;
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
/*      */   public double[][] getLayoutWeights() {
/*  612 */     if (this.layoutInfo == null) {
/*  613 */       return new double[2][0];
/*      */     }
/*  615 */     double[][] arrayOfDouble = new double[2][];
/*  616 */     arrayOfDouble[0] = new double[this.layoutInfo.width];
/*  617 */     arrayOfDouble[1] = new double[this.layoutInfo.height];
/*      */     
/*  619 */     System.arraycopy(this.layoutInfo.weightX, 0, arrayOfDouble[0], 0, this.layoutInfo.width);
/*  620 */     System.arraycopy(this.layoutInfo.weightY, 0, arrayOfDouble[1], 0, this.layoutInfo.height);
/*      */     
/*  622 */     return arrayOfDouble;
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
/*      */   public Point location(int paramInt1, int paramInt2) {
/*  653 */     Point point = new Point(0, 0);
/*      */ 
/*      */     
/*  656 */     if (this.layoutInfo == null) {
/*  657 */       return point;
/*      */     }
/*  659 */     int j = this.layoutInfo.startx;
/*  660 */     if (!this.rightToLeft) {
/*  661 */       for (i = 0; i < this.layoutInfo.width; i++) {
/*  662 */         j += this.layoutInfo.minWidth[i];
/*  663 */         if (j > paramInt1)
/*      */           break; 
/*      */       } 
/*      */     } else {
/*  667 */       for (i = this.layoutInfo.width - 1; i >= 0 && 
/*  668 */         j <= paramInt1; i--)
/*      */       {
/*  670 */         j += this.layoutInfo.minWidth[i];
/*      */       }
/*  672 */       i++;
/*      */     } 
/*  674 */     point.x = i;
/*      */     
/*  676 */     j = this.layoutInfo.starty; int i;
/*  677 */     for (i = 0; i < this.layoutInfo.height; i++) {
/*  678 */       j += this.layoutInfo.minHeight[i];
/*  679 */       if (j > paramInt2)
/*      */         break; 
/*      */     } 
/*  682 */     point.y = i;
/*      */     
/*  684 */     return point;
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
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLayoutComponent(Component paramComponent, Object paramObject) {
/*  705 */     if (paramObject instanceof GridBagConstraints) {
/*  706 */       setConstraints(paramComponent, (GridBagConstraints)paramObject);
/*  707 */     } else if (paramObject != null) {
/*  708 */       throw new IllegalArgumentException("cannot add to layout: constraints must be a GridBagConstraint");
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
/*      */   public void removeLayoutComponent(Component paramComponent) {
/*  721 */     removeConstraints(paramComponent);
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
/*      */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  736 */     GridBagLayoutInfo gridBagLayoutInfo = getLayoutInfo(paramContainer, 2);
/*  737 */     return getMinSize(paramContainer, gridBagLayoutInfo);
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
/*      */   public Dimension minimumLayoutSize(Container paramContainer) {
/*  750 */     GridBagLayoutInfo gridBagLayoutInfo = getLayoutInfo(paramContainer, 1);
/*  751 */     return getMinSize(paramContainer, gridBagLayoutInfo);
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
/*      */   public Dimension maximumLayoutSize(Container paramContainer) {
/*  764 */     return new Dimension(2147483647, 2147483647);
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
/*      */   public float getLayoutAlignmentX(Container paramContainer) {
/*  777 */     return 0.5F;
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
/*      */   public float getLayoutAlignmentY(Container paramContainer) {
/*  790 */     return 0.5F;
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
/*      */   public void invalidateLayout(Container paramContainer) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void layoutContainer(Container paramContainer) {
/*  812 */     arrangeGrid(paramContainer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  820 */     return getClass().getName();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GridBagLayoutInfo getLayoutInfo(Container paramContainer, int paramInt) {
/*  916 */     return GetLayoutInfo(paramContainer, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long[] preInitMaximumArraySizes(Container paramContainer) {
/*  926 */     Component[] arrayOfComponent = paramContainer.getComponents();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  931 */     int i = 0;
/*  932 */     int j = 0;
/*  933 */     long[] arrayOfLong = new long[2];
/*      */     
/*  935 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/*  936 */       Component component = arrayOfComponent[b];
/*  937 */       if (component.isVisible()) {
/*      */ 
/*      */ 
/*      */         
/*  941 */         GridBagConstraints gridBagConstraints = lookupConstraints(component);
/*  942 */         int k = gridBagConstraints.gridx;
/*  943 */         int m = gridBagConstraints.gridy;
/*  944 */         int n = gridBagConstraints.gridwidth;
/*  945 */         int i1 = gridBagConstraints.gridheight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  952 */         if (k < 0) {
/*  953 */           k = ++j;
/*      */         }
/*  955 */         if (m < 0) {
/*  956 */           m = ++i;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  961 */         if (n <= 0) {
/*  962 */           n = 1;
/*      */         }
/*  964 */         if (i1 <= 0) {
/*  965 */           i1 = 1;
/*      */         }
/*      */         
/*  968 */         i = Math.max(m + i1, i);
/*  969 */         j = Math.max(k + n, j);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  975 */     arrayOfLong[0] = i;
/*  976 */     arrayOfLong[1] = j;
/*  977 */     return arrayOfLong;
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
/*      */   protected GridBagLayoutInfo GetLayoutInfo(Container paramContainer, int paramInt) {
/*  989 */     synchronized (paramContainer.getTreeLock()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  994 */       Component[] arrayOfComponent = paramContainer.getComponents();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1006 */       int n = 0;
/* 1007 */       int i1 = 0;
/* 1008 */       int i2 = 1;
/* 1009 */       int i3 = 1;
/*      */ 
/*      */       
/* 1012 */       int i6 = 0;
/* 1013 */       int i7 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1023 */       int j = 0, i = j;
/* 1024 */       int i5 = -1, i4 = i5;
/* 1025 */       long[] arrayOfLong = preInitMaximumArraySizes(paramContainer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1034 */       i6 = (2L * arrayOfLong[0] > 2147483647L) ? Integer.MAX_VALUE : (2 * (int)arrayOfLong[0]);
/* 1035 */       i7 = (2L * arrayOfLong[1] > 2147483647L) ? Integer.MAX_VALUE : (2 * (int)arrayOfLong[1]);
/*      */       
/* 1037 */       if (this.rowHeights != null) {
/* 1038 */         i6 = Math.max(i6, this.rowHeights.length);
/*      */       }
/* 1040 */       if (this.columnWidths != null) {
/* 1041 */         i7 = Math.max(i7, this.columnWidths.length);
/*      */       }
/*      */       
/* 1044 */       int[] arrayOfInt1 = new int[i6];
/* 1045 */       int[] arrayOfInt2 = new int[i7];
/*      */       
/* 1047 */       boolean bool = false; byte b;
/* 1048 */       for (b = 0; b < arrayOfComponent.length; b++) {
/* 1049 */         Component component = arrayOfComponent[b];
/* 1050 */         if (component.isVisible()) {
/*      */           Dimension dimension;
/* 1052 */           GridBagConstraints gridBagConstraints = lookupConstraints(component);
/*      */           
/* 1054 */           n = gridBagConstraints.gridx;
/* 1055 */           i1 = gridBagConstraints.gridy;
/* 1056 */           i2 = gridBagConstraints.gridwidth;
/* 1057 */           if (i2 <= 0)
/* 1058 */             i2 = 1; 
/* 1059 */           i3 = gridBagConstraints.gridheight;
/* 1060 */           if (i3 <= 0) {
/* 1061 */             i3 = 1;
/*      */           }
/*      */           
/* 1064 */           if (n < 0 && i1 < 0)
/* 1065 */             if (i4 >= 0) {
/* 1066 */               i1 = i4;
/* 1067 */             } else if (i5 >= 0) {
/* 1068 */               n = i5;
/*      */             } else {
/* 1070 */               i1 = 0;
/*      */             }  
/* 1072 */           if (n < 0) {
/* 1073 */             int i12 = 0;
/* 1074 */             for (int i11 = i1; i11 < i1 + i3; i11++) {
/* 1075 */               i12 = Math.max(i12, arrayOfInt1[i11]);
/*      */             }
/*      */             
/* 1078 */             n = i12 - n - 1;
/* 1079 */             if (n < 0) {
/* 1080 */               n = 0;
/*      */             }
/* 1082 */           } else if (i1 < 0) {
/* 1083 */             int i12 = 0;
/* 1084 */             for (int i11 = n; i11 < n + i2; i11++) {
/* 1085 */               i12 = Math.max(i12, arrayOfInt2[i11]);
/*      */             }
/* 1087 */             i1 = i12 - i1 - 1;
/* 1088 */             if (i1 < 0) {
/* 1089 */               i1 = 0;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1095 */           int i9 = n + i2;
/* 1096 */           if (i < i9) {
/* 1097 */             i = i9;
/*      */           }
/* 1099 */           int i10 = i1 + i3;
/* 1100 */           if (j < i10) {
/* 1101 */             j = i10;
/*      */           }
/*      */           
/*      */           int i8;
/* 1105 */           for (i8 = n; i8 < n + i2; i8++) {
/* 1106 */             arrayOfInt2[i8] = i10;
/*      */           }
/* 1108 */           for (i8 = i1; i8 < i1 + i3; i8++) {
/* 1109 */             arrayOfInt1[i8] = i9;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1114 */           if (paramInt == 2) {
/* 1115 */             dimension = component.getPreferredSize();
/*      */           } else {
/* 1117 */             dimension = component.getMinimumSize();
/* 1118 */           }  gridBagConstraints.minWidth = dimension.width;
/* 1119 */           gridBagConstraints.minHeight = dimension.height;
/* 1120 */           if (calculateBaseline(component, gridBagConstraints, dimension)) {
/* 1121 */             bool = true;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1126 */           if (gridBagConstraints.gridheight == 0 && gridBagConstraints.gridwidth == 0) {
/* 1127 */             i4 = i5 = -1;
/*      */           }
/*      */           
/* 1130 */           if (gridBagConstraints.gridheight == 0 && i4 < 0) {
/* 1131 */             i5 = n + i2;
/*      */           
/*      */           }
/* 1134 */           else if (gridBagConstraints.gridwidth == 0 && i5 < 0) {
/* 1135 */             i4 = i1 + i3;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1142 */       if (this.columnWidths != null && i < this.columnWidths.length)
/* 1143 */         i = this.columnWidths.length; 
/* 1144 */       if (this.rowHeights != null && j < this.rowHeights.length) {
/* 1145 */         j = this.rowHeights.length;
/*      */       }
/* 1147 */       GridBagLayoutInfo gridBagLayoutInfo = new GridBagLayoutInfo(i, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1158 */       i4 = i5 = -1;
/*      */       
/* 1160 */       Arrays.fill(arrayOfInt1, 0);
/* 1161 */       Arrays.fill(arrayOfInt2, 0);
/*      */       
/* 1163 */       int[] arrayOfInt3 = null;
/* 1164 */       int[] arrayOfInt4 = null;
/* 1165 */       short[] arrayOfShort = null;
/*      */       
/* 1167 */       if (bool) {
/* 1168 */         gridBagLayoutInfo.maxAscent = arrayOfInt3 = new int[j];
/* 1169 */         gridBagLayoutInfo.maxDescent = arrayOfInt4 = new int[j];
/* 1170 */         gridBagLayoutInfo.baselineType = arrayOfShort = new short[j];
/* 1171 */         gridBagLayoutInfo.hasBaseline = true;
/*      */       } 
/*      */ 
/*      */       
/* 1175 */       for (b = 0; b < arrayOfComponent.length; b++) {
/* 1176 */         Component component = arrayOfComponent[b];
/* 1177 */         if (component.isVisible()) {
/*      */           
/* 1179 */           GridBagConstraints gridBagConstraints = lookupConstraints(component);
/*      */           
/* 1181 */           n = gridBagConstraints.gridx;
/* 1182 */           i1 = gridBagConstraints.gridy;
/* 1183 */           i2 = gridBagConstraints.gridwidth;
/* 1184 */           i3 = gridBagConstraints.gridheight;
/*      */ 
/*      */           
/* 1187 */           if (n < 0 && i1 < 0) {
/* 1188 */             if (i4 >= 0) {
/* 1189 */               i1 = i4;
/* 1190 */             } else if (i5 >= 0) {
/* 1191 */               n = i5;
/*      */             } else {
/* 1193 */               i1 = 0;
/*      */             } 
/*      */           }
/* 1196 */           if (n < 0) {
/* 1197 */             if (i3 <= 0) {
/* 1198 */               i3 += gridBagLayoutInfo.height - i1;
/* 1199 */               if (i3 < 1) {
/* 1200 */                 i3 = 1;
/*      */               }
/*      */             } 
/* 1203 */             int i13 = 0;
/* 1204 */             for (int i12 = i1; i12 < i1 + i3; i12++) {
/* 1205 */               i13 = Math.max(i13, arrayOfInt1[i12]);
/*      */             }
/* 1207 */             n = i13 - n - 1;
/* 1208 */             if (n < 0) {
/* 1209 */               n = 0;
/*      */             }
/* 1211 */           } else if (i1 < 0) {
/* 1212 */             if (i2 <= 0) {
/* 1213 */               i2 += gridBagLayoutInfo.width - n;
/* 1214 */               if (i2 < 1) {
/* 1215 */                 i2 = 1;
/*      */               }
/*      */             } 
/* 1218 */             int i13 = 0;
/* 1219 */             for (int i12 = n; i12 < n + i2; i12++) {
/* 1220 */               i13 = Math.max(i13, arrayOfInt2[i12]);
/*      */             }
/*      */             
/* 1223 */             i1 = i13 - i1 - 1;
/* 1224 */             if (i1 < 0) {
/* 1225 */               i1 = 0;
/*      */             }
/*      */           } 
/* 1228 */           if (i2 <= 0) {
/* 1229 */             i2 += gridBagLayoutInfo.width - n;
/* 1230 */             if (i2 < 1) {
/* 1231 */               i2 = 1;
/*      */             }
/*      */           } 
/* 1234 */           if (i3 <= 0) {
/* 1235 */             i3 += gridBagLayoutInfo.height - i1;
/* 1236 */             if (i3 < 1) {
/* 1237 */               i3 = 1;
/*      */             }
/*      */           } 
/* 1240 */           int i9 = n + i2;
/* 1241 */           int i10 = i1 + i3;
/*      */           int i8;
/* 1243 */           for (i8 = n; i8 < n + i2; ) { arrayOfInt2[i8] = i10; i8++; }
/* 1244 */            for (i8 = i1; i8 < i1 + i3; ) { arrayOfInt1[i8] = i9; i8++; }
/*      */ 
/*      */           
/* 1247 */           if (gridBagConstraints.gridheight == 0 && gridBagConstraints.gridwidth == 0)
/* 1248 */             i4 = i5 = -1; 
/* 1249 */           if (gridBagConstraints.gridheight == 0 && i4 < 0) {
/* 1250 */             i5 = n + i2;
/* 1251 */           } else if (gridBagConstraints.gridwidth == 0 && i5 < 0) {
/* 1252 */             i4 = i1 + i3;
/*      */           } 
/*      */           
/* 1255 */           gridBagConstraints.tempX = n;
/* 1256 */           gridBagConstraints.tempY = i1;
/* 1257 */           gridBagConstraints.tempWidth = i2;
/* 1258 */           gridBagConstraints.tempHeight = i3;
/*      */           
/* 1260 */           int i11 = gridBagConstraints.anchor;
/* 1261 */           if (bool) {
/* 1262 */             int i12; switch (i11) {
/*      */               case 256:
/*      */               case 512:
/*      */               case 768:
/* 1266 */                 if (gridBagConstraints.ascent >= 0) {
/* 1267 */                   if (i3 == 1) {
/* 1268 */                     arrayOfInt3[i1] = 
/* 1269 */                       Math.max(arrayOfInt3[i1], gridBagConstraints.ascent);
/*      */                     
/* 1271 */                     arrayOfInt4[i1] = 
/* 1272 */                       Math.max(arrayOfInt4[i1], gridBagConstraints.descent);
/*      */ 
/*      */                   
/*      */                   }
/* 1276 */                   else if (gridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/*      */ 
/*      */                     
/* 1279 */                     arrayOfInt4[i1 + i3 - 1] = 
/* 1280 */                       Math.max(arrayOfInt4[i1 + i3 - 1], gridBagConstraints.descent);
/*      */                   
/*      */                   }
/*      */                   else {
/*      */                     
/* 1285 */                     arrayOfInt3[i1] = Math.max(arrayOfInt3[i1], gridBagConstraints.ascent);
/*      */                   } 
/*      */ 
/*      */                   
/* 1289 */                   if (gridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/*      */                     
/* 1291 */                     arrayOfShort[i1 + i3 - 1] = 
/*      */                       
/* 1293 */                       (short)(arrayOfShort[i1 + i3 - 1] | 1 << gridBagConstraints.baselineResizeBehavior.ordinal());
/*      */                     break;
/*      */                   } 
/* 1296 */                   arrayOfShort[i1] = 
/* 1297 */                     (short)(arrayOfShort[i1] | 1 << gridBagConstraints.baselineResizeBehavior.ordinal());
/*      */                 } 
/*      */                 break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case 1024:
/*      */               case 1280:
/*      */               case 1536:
/* 1308 */                 i12 = gridBagConstraints.minHeight + gridBagConstraints.insets.top + gridBagConstraints.ipady;
/*      */ 
/*      */                 
/* 1311 */                 arrayOfInt3[i1] = Math.max(arrayOfInt3[i1], i12);
/*      */                 
/* 1313 */                 arrayOfInt4[i1] = Math.max(arrayOfInt4[i1], gridBagConstraints.insets.bottom);
/*      */                 break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case 1792:
/*      */               case 2048:
/*      */               case 2304:
/* 1323 */                 i12 = gridBagConstraints.minHeight + gridBagConstraints.insets.bottom + gridBagConstraints.ipady;
/*      */                 
/* 1325 */                 arrayOfInt4[i1] = Math.max(arrayOfInt4[i1], i12);
/*      */                 
/* 1327 */                 arrayOfInt3[i1] = Math.max(arrayOfInt3[i1], gridBagConstraints.insets.top);
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/* 1334 */       gridBagLayoutInfo.weightX = new double[i7];
/* 1335 */       gridBagLayoutInfo.weightY = new double[i6];
/* 1336 */       gridBagLayoutInfo.minWidth = new int[i7];
/* 1337 */       gridBagLayoutInfo.minHeight = new int[i6];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       if (this.columnWidths != null)
/* 1344 */         System.arraycopy(this.columnWidths, 0, gridBagLayoutInfo.minWidth, 0, this.columnWidths.length); 
/* 1345 */       if (this.rowHeights != null)
/* 1346 */         System.arraycopy(this.rowHeights, 0, gridBagLayoutInfo.minHeight, 0, this.rowHeights.length); 
/* 1347 */       if (this.columnWeights != null)
/* 1348 */         System.arraycopy(this.columnWeights, 0, gridBagLayoutInfo.weightX, 0, Math.min(gridBagLayoutInfo.weightX.length, this.columnWeights.length)); 
/* 1349 */       if (this.rowWeights != null) {
/* 1350 */         System.arraycopy(this.rowWeights, 0, gridBagLayoutInfo.weightY, 0, Math.min(gridBagLayoutInfo.weightY.length, this.rowWeights.length));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1358 */       int m = Integer.MAX_VALUE;
/*      */       
/* 1360 */       int k = 1;
/* 1361 */       for (; k != Integer.MAX_VALUE; 
/* 1362 */         k = m, m = Integer.MAX_VALUE) {
/* 1363 */         for (b = 0; b < arrayOfComponent.length; b++) {
/* 1364 */           Component component = arrayOfComponent[b];
/* 1365 */           if (component.isVisible()) {
/*      */             
/* 1367 */             GridBagConstraints gridBagConstraints = lookupConstraints(component);
/*      */             
/* 1369 */             if (gridBagConstraints.tempWidth == k) {
/* 1370 */               int i9 = gridBagConstraints.tempX + gridBagConstraints.tempWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1379 */               double d = gridBagConstraints.weightx; int i8;
/* 1380 */               for (i8 = gridBagConstraints.tempX; i8 < i9; i8++)
/* 1381 */                 d -= gridBagLayoutInfo.weightX[i8]; 
/* 1382 */               if (d > 0.0D) {
/* 1383 */                 double d1 = 0.0D;
/* 1384 */                 for (i8 = gridBagConstraints.tempX; i8 < i9; i8++)
/* 1385 */                   d1 += gridBagLayoutInfo.weightX[i8]; 
/* 1386 */                 for (i8 = gridBagConstraints.tempX; d1 > 0.0D && i8 < i9; i8++) {
/* 1387 */                   double d2 = gridBagLayoutInfo.weightX[i8];
/* 1388 */                   double d3 = d2 * d / d1;
/* 1389 */                   gridBagLayoutInfo.weightX[i8] = gridBagLayoutInfo.weightX[i8] + d3;
/* 1390 */                   d -= d3;
/* 1391 */                   d1 -= d2;
/*      */                 } 
/*      */                 
/* 1394 */                 gridBagLayoutInfo.weightX[i9 - 1] = gridBagLayoutInfo.weightX[i9 - 1] + d;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1405 */               int i10 = gridBagConstraints.minWidth + gridBagConstraints.ipadx + gridBagConstraints.insets.left + gridBagConstraints.insets.right;
/*      */ 
/*      */ 
/*      */               
/* 1409 */               for (i8 = gridBagConstraints.tempX; i8 < i9; i8++)
/* 1410 */                 i10 -= gridBagLayoutInfo.minWidth[i8]; 
/* 1411 */               if (i10 > 0) {
/* 1412 */                 double d1 = 0.0D;
/* 1413 */                 for (i8 = gridBagConstraints.tempX; i8 < i9; i8++)
/* 1414 */                   d1 += gridBagLayoutInfo.weightX[i8]; 
/* 1415 */                 for (i8 = gridBagConstraints.tempX; d1 > 0.0D && i8 < i9; i8++) {
/* 1416 */                   double d2 = gridBagLayoutInfo.weightX[i8];
/* 1417 */                   int i11 = (int)(d2 * i10 / d1);
/* 1418 */                   gridBagLayoutInfo.minWidth[i8] = gridBagLayoutInfo.minWidth[i8] + i11;
/* 1419 */                   i10 -= i11;
/* 1420 */                   d1 -= d2;
/*      */                 } 
/*      */                 
/* 1423 */                 gridBagLayoutInfo.minWidth[i9 - 1] = gridBagLayoutInfo.minWidth[i9 - 1] + i10;
/*      */               }
/*      */             
/* 1426 */             } else if (gridBagConstraints.tempWidth > k && gridBagConstraints.tempWidth < m) {
/* 1427 */               m = gridBagConstraints.tempWidth;
/*      */             } 
/*      */             
/* 1430 */             if (gridBagConstraints.tempHeight == k)
/* 1431 */             { int i9 = gridBagConstraints.tempY + gridBagConstraints.tempHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1440 */               double d = gridBagConstraints.weighty; int i8;
/* 1441 */               for (i8 = gridBagConstraints.tempY; i8 < i9; i8++)
/* 1442 */                 d -= gridBagLayoutInfo.weightY[i8]; 
/* 1443 */               if (d > 0.0D) {
/* 1444 */                 double d1 = 0.0D;
/* 1445 */                 for (i8 = gridBagConstraints.tempY; i8 < i9; i8++)
/* 1446 */                   d1 += gridBagLayoutInfo.weightY[i8]; 
/* 1447 */                 for (i8 = gridBagConstraints.tempY; d1 > 0.0D && i8 < i9; i8++) {
/* 1448 */                   double d2 = gridBagLayoutInfo.weightY[i8];
/* 1449 */                   double d3 = d2 * d / d1;
/* 1450 */                   gridBagLayoutInfo.weightY[i8] = gridBagLayoutInfo.weightY[i8] + d3;
/* 1451 */                   d -= d3;
/* 1452 */                   d1 -= d2;
/*      */                 } 
/*      */                 
/* 1455 */                 gridBagLayoutInfo.weightY[i9 - 1] = gridBagLayoutInfo.weightY[i9 - 1] + d;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1466 */               int i10 = -1;
/* 1467 */               if (bool) {
/* 1468 */                 switch (gridBagConstraints.anchor) {
/*      */                   case 256:
/*      */                   case 512:
/*      */                   case 768:
/* 1472 */                     if (gridBagConstraints.ascent >= 0) {
/* 1473 */                       if (gridBagConstraints.tempHeight == 1) {
/* 1474 */                         i10 = arrayOfInt3[gridBagConstraints.tempY] + arrayOfInt4[gridBagConstraints.tempY];
/*      */                         
/*      */                         break;
/*      */                       } 
/* 1478 */                       if (gridBagConstraints.baselineResizeBehavior != Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/*      */ 
/*      */                         
/* 1481 */                         i10 = arrayOfInt3[gridBagConstraints.tempY] + gridBagConstraints.descent;
/*      */                         
/*      */                         break;
/*      */                       } 
/*      */                       
/* 1486 */                       i10 = gridBagConstraints.ascent + arrayOfInt4[gridBagConstraints.tempY + gridBagConstraints.tempHeight - 1];
/*      */                     } 
/*      */                     break;
/*      */ 
/*      */ 
/*      */                   
/*      */                   case 1024:
/*      */                   case 1280:
/*      */                   case 1536:
/* 1495 */                     i10 = gridBagConstraints.insets.top + gridBagConstraints.minHeight + gridBagConstraints.ipady + arrayOfInt4[gridBagConstraints.tempY];
/*      */                     break;
/*      */ 
/*      */ 
/*      */                   
/*      */                   case 1792:
/*      */                   case 2048:
/*      */                   case 2304:
/* 1503 */                     i10 = arrayOfInt3[gridBagConstraints.tempY] + gridBagConstraints.minHeight + gridBagConstraints.insets.bottom + gridBagConstraints.ipady;
/*      */                     break;
/*      */                 } 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/* 1510 */               if (i10 == -1) {
/* 1511 */                 i10 = gridBagConstraints.minHeight + gridBagConstraints.ipady + gridBagConstraints.insets.top + gridBagConstraints.insets.bottom;
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1516 */               for (i8 = gridBagConstraints.tempY; i8 < i9; i8++)
/* 1517 */                 i10 -= gridBagLayoutInfo.minHeight[i8]; 
/* 1518 */               if (i10 > 0) {
/* 1519 */                 double d1 = 0.0D;
/* 1520 */                 for (i8 = gridBagConstraints.tempY; i8 < i9; i8++)
/* 1521 */                   d1 += gridBagLayoutInfo.weightY[i8]; 
/* 1522 */                 for (i8 = gridBagConstraints.tempY; d1 > 0.0D && i8 < i9; i8++) {
/* 1523 */                   double d2 = gridBagLayoutInfo.weightY[i8];
/* 1524 */                   int i11 = (int)(d2 * i10 / d1);
/* 1525 */                   gridBagLayoutInfo.minHeight[i8] = gridBagLayoutInfo.minHeight[i8] + i11;
/* 1526 */                   i10 -= i11;
/* 1527 */                   d1 -= d2;
/*      */                 } 
/*      */                 
/* 1530 */                 gridBagLayoutInfo.minHeight[i9 - 1] = gridBagLayoutInfo.minHeight[i9 - 1] + i10;
/*      */               }
/*      */                }
/* 1533 */             else if (gridBagConstraints.tempHeight > k && gridBagConstraints.tempHeight < m)
/*      */             
/* 1535 */             { m = gridBagConstraints.tempHeight; } 
/*      */           } 
/*      */         } 
/* 1538 */       }  return gridBagLayoutInfo;
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
/*      */   private boolean calculateBaseline(Component paramComponent, GridBagConstraints paramGridBagConstraints, Dimension paramDimension) {
/* 1552 */     int i = paramGridBagConstraints.anchor;
/* 1553 */     if (i == 256 || i == 512 || i == 768) {
/*      */ 
/*      */ 
/*      */       
/* 1557 */       int j = paramDimension.width + paramGridBagConstraints.ipadx;
/* 1558 */       int k = paramDimension.height + paramGridBagConstraints.ipady;
/* 1559 */       paramGridBagConstraints.ascent = paramComponent.getBaseline(j, k);
/* 1560 */       if (paramGridBagConstraints.ascent >= 0) {
/*      */         
/* 1562 */         int m = paramGridBagConstraints.ascent;
/*      */         
/* 1564 */         paramGridBagConstraints.descent = k - paramGridBagConstraints.ascent + paramGridBagConstraints.insets.bottom;
/*      */         
/* 1566 */         paramGridBagConstraints.ascent += paramGridBagConstraints.insets.top;
/* 1567 */         paramGridBagConstraints
/* 1568 */           .baselineResizeBehavior = paramComponent.getBaselineResizeBehavior();
/* 1569 */         paramGridBagConstraints.centerPadding = 0;
/* 1570 */         if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CENTER_OFFSET) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1577 */           int n = paramComponent.getBaseline(j, k + 1);
/* 1578 */           paramGridBagConstraints.centerOffset = m - k / 2;
/* 1579 */           if (k % 2 == 0) {
/* 1580 */             if (m != n) {
/* 1581 */               paramGridBagConstraints.centerPadding = 1;
/*      */             }
/*      */           }
/* 1584 */           else if (m == n) {
/* 1585 */             paramGridBagConstraints.centerOffset--;
/* 1586 */             paramGridBagConstraints.centerPadding = 1;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1590 */       return true;
/*      */     } 
/*      */     
/* 1593 */     paramGridBagConstraints.ascent = -1;
/* 1594 */     return false;
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
/*      */   protected void adjustForGravity(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle) {
/* 1610 */     AdjustForGravity(paramGridBagConstraints, paramRectangle);
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
/*      */   protected void AdjustForGravity(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle) {
/* 1625 */     int k = paramRectangle.y;
/* 1626 */     int m = paramRectangle.height;
/*      */     
/* 1628 */     if (!this.rightToLeft) {
/* 1629 */       paramRectangle.x += paramGridBagConstraints.insets.left;
/*      */     } else {
/* 1631 */       paramRectangle.x -= paramRectangle.width - paramGridBagConstraints.insets.right;
/*      */     } 
/* 1633 */     paramRectangle.width -= paramGridBagConstraints.insets.left + paramGridBagConstraints.insets.right;
/* 1634 */     paramRectangle.y += paramGridBagConstraints.insets.top;
/* 1635 */     paramRectangle.height -= paramGridBagConstraints.insets.top + paramGridBagConstraints.insets.bottom;
/*      */     
/* 1637 */     int i = 0;
/* 1638 */     if (paramGridBagConstraints.fill != 2 && paramGridBagConstraints.fill != 1 && paramRectangle.width > paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx) {
/*      */ 
/*      */       
/* 1641 */       i = paramRectangle.width - paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx;
/* 1642 */       paramRectangle.width = paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx;
/*      */     } 
/*      */     
/* 1645 */     int j = 0;
/* 1646 */     if (paramGridBagConstraints.fill != 3 && paramGridBagConstraints.fill != 1 && paramRectangle.height > paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady) {
/*      */ 
/*      */       
/* 1649 */       j = paramRectangle.height - paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady;
/* 1650 */       paramRectangle.height = paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady;
/*      */     } 
/*      */     
/* 1653 */     switch (paramGridBagConstraints.anchor) {
/*      */       case 256:
/* 1655 */         paramRectangle.x += i / 2;
/* 1656 */         alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 512:
/* 1659 */         if (this.rightToLeft) {
/* 1660 */           paramRectangle.x += i;
/*      */         }
/* 1662 */         alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 768:
/* 1665 */         if (!this.rightToLeft) {
/* 1666 */           paramRectangle.x += i;
/*      */         }
/* 1668 */         alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 1024:
/* 1671 */         paramRectangle.x += i / 2;
/* 1672 */         alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 1280:
/* 1675 */         if (this.rightToLeft) {
/* 1676 */           paramRectangle.x += i;
/*      */         }
/* 1678 */         alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 1536:
/* 1681 */         if (!this.rightToLeft) {
/* 1682 */           paramRectangle.x += i;
/*      */         }
/* 1684 */         alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 1792:
/* 1687 */         paramRectangle.x += i / 2;
/* 1688 */         alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 2048:
/* 1691 */         if (this.rightToLeft) {
/* 1692 */           paramRectangle.x += i;
/*      */         }
/* 1694 */         alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 2304:
/* 1697 */         if (!this.rightToLeft) {
/* 1698 */           paramRectangle.x += i;
/*      */         }
/* 1700 */         alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/*      */       
/*      */       case 10:
/* 1703 */         paramRectangle.x += i / 2;
/* 1704 */         paramRectangle.y += j / 2;
/*      */       
/*      */       case 11:
/*      */       case 19:
/* 1708 */         paramRectangle.x += i / 2;
/*      */       
/*      */       case 12:
/* 1711 */         paramRectangle.x += i;
/*      */       
/*      */       case 13:
/* 1714 */         paramRectangle.x += i;
/* 1715 */         paramRectangle.y += j / 2;
/*      */       
/*      */       case 14:
/* 1718 */         paramRectangle.x += i;
/* 1719 */         paramRectangle.y += j;
/*      */       
/*      */       case 15:
/*      */       case 20:
/* 1723 */         paramRectangle.x += i / 2;
/* 1724 */         paramRectangle.y += j;
/*      */       
/*      */       case 16:
/* 1727 */         paramRectangle.y += j;
/*      */       
/*      */       case 17:
/* 1730 */         paramRectangle.y += j / 2;
/*      */       
/*      */       case 18:
/*      */         return;
/*      */       case 21:
/* 1735 */         if (this.rightToLeft) {
/* 1736 */           paramRectangle.x += i;
/*      */         }
/* 1738 */         paramRectangle.y += j / 2;
/*      */       
/*      */       case 22:
/* 1741 */         if (!this.rightToLeft) {
/* 1742 */           paramRectangle.x += i;
/*      */         }
/* 1744 */         paramRectangle.y += j / 2;
/*      */       
/*      */       case 23:
/* 1747 */         if (this.rightToLeft) {
/* 1748 */           paramRectangle.x += i;
/*      */         }
/*      */       
/*      */       case 24:
/* 1752 */         if (!this.rightToLeft) {
/* 1753 */           paramRectangle.x += i;
/*      */         }
/*      */       
/*      */       case 25:
/* 1757 */         if (this.rightToLeft) {
/* 1758 */           paramRectangle.x += i;
/*      */         }
/* 1760 */         paramRectangle.y += j;
/*      */       
/*      */       case 26:
/* 1763 */         if (!this.rightToLeft) {
/* 1764 */           paramRectangle.x += i;
/*      */         }
/* 1766 */         paramRectangle.y += j;
/*      */     } 
/*      */     
/* 1769 */     throw new IllegalArgumentException("illegal anchor value");
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
/*      */   private void alignOnBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 1784 */     if (paramGridBagConstraints.ascent >= 0) {
/* 1785 */       if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1793 */         int i = paramInt1 + paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY + paramGridBagConstraints.tempHeight - 1] + paramGridBagConstraints.descent - paramGridBagConstraints.insets.bottom;
/*      */ 
/*      */         
/* 1796 */         if (!paramGridBagConstraints.isVerticallyResizable())
/*      */         {
/*      */           
/* 1799 */           paramRectangle.y = i - paramGridBagConstraints.minHeight;
/* 1800 */           paramRectangle.height = paramGridBagConstraints.minHeight;
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*      */           
/* 1806 */           paramRectangle.height = i - paramInt1 - paramGridBagConstraints.insets.top;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1813 */         int i, j = paramGridBagConstraints.ascent;
/* 1814 */         if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY)) {
/*      */ 
/*      */           
/* 1817 */           i = paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY];
/*      */         }
/*      */         else {
/*      */           
/* 1821 */           i = this.layoutInfo.maxAscent[paramGridBagConstraints.tempY];
/*      */         } 
/* 1823 */         if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.OTHER) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1828 */           boolean bool = false;
/* 1829 */           j = this.componentAdjusting.getBaseline(paramRectangle.width, paramRectangle.height);
/* 1830 */           if (j >= 0)
/*      */           {
/*      */ 
/*      */             
/* 1834 */             j += paramGridBagConstraints.insets.top;
/*      */           }
/* 1836 */           if (j >= 0 && j <= i)
/*      */           {
/*      */             
/* 1839 */             if (i + paramRectangle.height - j - paramGridBagConstraints.insets.top <= paramInt2 - paramGridBagConstraints.insets.bottom) {
/*      */ 
/*      */               
/* 1842 */               bool = true;
/*      */             }
/* 1844 */             else if (paramGridBagConstraints.isVerticallyResizable()) {
/*      */ 
/*      */               
/* 1847 */               int k = this.componentAdjusting.getBaseline(paramRectangle.width, paramInt2 - paramGridBagConstraints.insets.bottom - i + j);
/*      */ 
/*      */               
/* 1850 */               if (k >= 0) {
/* 1851 */                 k += paramGridBagConstraints.insets.top;
/*      */               }
/* 1853 */               if (k >= 0 && k <= j) {
/*      */                 
/* 1855 */                 paramRectangle.height = paramInt2 - paramGridBagConstraints.insets.bottom - i + j;
/*      */                 
/* 1857 */                 j = k;
/* 1858 */                 bool = true;
/*      */               } 
/*      */             } 
/*      */           }
/* 1862 */           if (!bool) {
/*      */             
/* 1864 */             j = paramGridBagConstraints.ascent;
/* 1865 */             paramRectangle.width = paramGridBagConstraints.minWidth;
/* 1866 */             paramRectangle.height = paramGridBagConstraints.minHeight;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1872 */         paramRectangle.y = paramInt1 + i - j + paramGridBagConstraints.insets.top;
/* 1873 */         if (paramGridBagConstraints.isVerticallyResizable()) {
/* 1874 */           int k; int m; int n; switch (paramGridBagConstraints.baselineResizeBehavior) {
/*      */             case CONSTANT_ASCENT:
/* 1876 */               paramRectangle.height = Math.max(paramGridBagConstraints.minHeight, paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.insets.bottom);
/*      */               break;
/*      */ 
/*      */             
/*      */             case CENTER_OFFSET:
/* 1881 */               k = paramRectangle.y - paramInt1 - paramGridBagConstraints.insets.top;
/* 1882 */               m = paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.minHeight - paramGridBagConstraints.insets.bottom;
/*      */               
/* 1884 */               n = Math.min(k, m);
/* 1885 */               n += n;
/* 1886 */               if (n > 0 && (paramGridBagConstraints.minHeight + paramGridBagConstraints.centerPadding + n) / 2 + paramGridBagConstraints.centerOffset != i)
/*      */               {
/*      */ 
/*      */                 
/* 1890 */                 n--;
/*      */               }
/* 1892 */               paramRectangle.height = paramGridBagConstraints.minHeight + n;
/* 1893 */               paramRectangle.y = paramInt1 + i - (paramRectangle.height + paramGridBagConstraints.centerPadding) / 2 - paramGridBagConstraints.centerOffset;
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1908 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void alignAboveBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 1919 */     if (this.layoutInfo.hasBaseline(paramGridBagConstraints.tempY)) {
/*      */       int i;
/* 1921 */       if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY)) {
/*      */         
/* 1923 */         i = paramInt1 + paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY];
/*      */       }
/*      */       else {
/*      */         
/* 1927 */         i = paramInt1 + this.layoutInfo.maxAscent[paramGridBagConstraints.tempY];
/*      */       } 
/* 1929 */       if (paramGridBagConstraints.isVerticallyResizable()) {
/*      */ 
/*      */         
/* 1932 */         paramRectangle.y = paramInt1 + paramGridBagConstraints.insets.top;
/* 1933 */         paramRectangle.height = i - paramRectangle.y;
/*      */       }
/*      */       else {
/*      */         
/* 1937 */         paramRectangle.height = paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady;
/* 1938 */         paramRectangle.y = i - paramRectangle.height;
/*      */       } 
/*      */     } else {
/*      */       
/* 1942 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void alignBelowBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 1951 */     if (this.layoutInfo.hasBaseline(paramGridBagConstraints.tempY)) {
/* 1952 */       if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY)) {
/*      */         
/* 1954 */         paramRectangle.y = paramInt1 + paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY];
/*      */       }
/*      */       else {
/*      */         
/* 1958 */         paramRectangle.y = paramInt1 + this.layoutInfo.maxAscent[paramGridBagConstraints.tempY];
/*      */       } 
/* 1960 */       if (paramGridBagConstraints.isVerticallyResizable()) {
/* 1961 */         paramRectangle.height = paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.insets.bottom;
/*      */       }
/*      */     } else {
/*      */       
/* 1965 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void centerVertically(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt) {
/* 1971 */     if (!paramGridBagConstraints.isVerticallyResizable()) {
/* 1972 */       paramRectangle.y += Math.max(0, (paramInt - paramGridBagConstraints.insets.top - paramGridBagConstraints.insets.bottom - paramGridBagConstraints.minHeight - paramGridBagConstraints.ipady) / 2);
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
/*      */   protected Dimension getMinSize(Container paramContainer, GridBagLayoutInfo paramGridBagLayoutInfo) {
/* 1991 */     return GetMinSize(paramContainer, paramGridBagLayoutInfo);
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
/*      */   protected Dimension GetMinSize(Container paramContainer, GridBagLayoutInfo paramGridBagLayoutInfo) {
/* 2003 */     Dimension dimension = new Dimension();
/*      */     
/* 2005 */     Insets insets = paramContainer.getInsets();
/*      */     
/* 2007 */     int i = 0; byte b;
/* 2008 */     for (b = 0; b < paramGridBagLayoutInfo.width; b++)
/* 2009 */       i += paramGridBagLayoutInfo.minWidth[b]; 
/* 2010 */     dimension.width = i + insets.left + insets.right;
/*      */     
/* 2012 */     i = 0;
/* 2013 */     for (b = 0; b < paramGridBagLayoutInfo.height; b++)
/* 2014 */       i += paramGridBagLayoutInfo.minHeight[b]; 
/* 2015 */     dimension.height = i + insets.top + insets.bottom;
/*      */     
/* 2017 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient boolean rightToLeft = false;
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = 8838754796412211005L;
/*      */ 
/*      */ 
/*      */   
/*      */   protected void arrangeGrid(Container paramContainer) {
/* 2031 */     ArrangeGrid(paramContainer);
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
/*      */   protected void ArrangeGrid(Container paramContainer) {
/* 2046 */     Insets insets = paramContainer.getInsets();
/* 2047 */     Component[] arrayOfComponent = paramContainer.getComponents();
/*      */     
/* 2049 */     Rectangle rectangle = new Rectangle();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2054 */     this.rightToLeft = !paramContainer.getComponentOrientation().isLeftToRight();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2060 */     if (arrayOfComponent.length == 0 && (this.columnWidths == null || this.columnWidths.length == 0) && (this.rowHeights == null || this.rowHeights.length == 0)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2071 */     GridBagLayoutInfo gridBagLayoutInfo = getLayoutInfo(paramContainer, 2);
/* 2072 */     Dimension dimension = getMinSize(paramContainer, gridBagLayoutInfo);
/*      */     
/* 2074 */     if (paramContainer.width < dimension.width || paramContainer.height < dimension.height) {
/* 2075 */       gridBagLayoutInfo = getLayoutInfo(paramContainer, 1);
/* 2076 */       dimension = getMinSize(paramContainer, gridBagLayoutInfo);
/*      */     } 
/*      */     
/* 2079 */     this.layoutInfo = gridBagLayoutInfo;
/* 2080 */     rectangle.width = dimension.width;
/* 2081 */     rectangle.height = dimension.height;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2103 */     int i = paramContainer.width - rectangle.width;
/* 2104 */     if (i != 0) {
/* 2105 */       double d = 0.0D; byte b1;
/* 2106 */       for (b1 = 0; b1 < gridBagLayoutInfo.width; b1++)
/* 2107 */         d += gridBagLayoutInfo.weightX[b1]; 
/* 2108 */       if (d > 0.0D) {
/* 2109 */         for (b1 = 0; b1 < gridBagLayoutInfo.width; b1++) {
/* 2110 */           int k = (int)(i * gridBagLayoutInfo.weightX[b1] / d);
/* 2111 */           gridBagLayoutInfo.minWidth[b1] = gridBagLayoutInfo.minWidth[b1] + k;
/* 2112 */           rectangle.width += k;
/* 2113 */           if (gridBagLayoutInfo.minWidth[b1] < 0) {
/* 2114 */             rectangle.width -= gridBagLayoutInfo.minWidth[b1];
/* 2115 */             gridBagLayoutInfo.minWidth[b1] = 0;
/*      */           } 
/*      */         } 
/*      */       }
/* 2119 */       i = paramContainer.width - rectangle.width;
/*      */     }
/*      */     else {
/*      */       
/* 2123 */       i = 0;
/*      */     } 
/*      */     
/* 2126 */     int j = paramContainer.height - rectangle.height;
/* 2127 */     if (j != 0) {
/* 2128 */       double d = 0.0D; byte b1;
/* 2129 */       for (b1 = 0; b1 < gridBagLayoutInfo.height; b1++)
/* 2130 */         d += gridBagLayoutInfo.weightY[b1]; 
/* 2131 */       if (d > 0.0D) {
/* 2132 */         for (b1 = 0; b1 < gridBagLayoutInfo.height; b1++) {
/* 2133 */           int k = (int)(j * gridBagLayoutInfo.weightY[b1] / d);
/* 2134 */           gridBagLayoutInfo.minHeight[b1] = gridBagLayoutInfo.minHeight[b1] + k;
/* 2135 */           rectangle.height += k;
/* 2136 */           if (gridBagLayoutInfo.minHeight[b1] < 0) {
/* 2137 */             rectangle.height -= gridBagLayoutInfo.minHeight[b1];
/* 2138 */             gridBagLayoutInfo.minHeight[b1] = 0;
/*      */           } 
/*      */         } 
/*      */       }
/* 2142 */       j = paramContainer.height - rectangle.height;
/*      */     }
/*      */     else {
/*      */       
/* 2146 */       j = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2161 */     gridBagLayoutInfo.startx = i / 2 + insets.left;
/* 2162 */     gridBagLayoutInfo.starty = j / 2 + insets.top;
/*      */     
/* 2164 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 2165 */       Component component = arrayOfComponent[b];
/* 2166 */       if (component.isVisible()) {
/*      */ 
/*      */         
/* 2169 */         GridBagConstraints gridBagConstraints = lookupConstraints(component);
/*      */         
/* 2171 */         if (!this.rightToLeft) {
/* 2172 */           rectangle.x = gridBagLayoutInfo.startx;
/* 2173 */           for (byte b1 = 0; b1 < gridBagConstraints.tempX; b1++)
/* 2174 */             rectangle.x += gridBagLayoutInfo.minWidth[b1]; 
/*      */         } else {
/* 2176 */           rectangle.x = paramContainer.width - i / 2 + insets.right;
/* 2177 */           for (byte b1 = 0; b1 < gridBagConstraints.tempX; b1++) {
/* 2178 */             rectangle.x -= gridBagLayoutInfo.minWidth[b1];
/*      */           }
/*      */         } 
/* 2181 */         rectangle.y = gridBagLayoutInfo.starty; int k;
/* 2182 */         for (k = 0; k < gridBagConstraints.tempY; k++) {
/* 2183 */           rectangle.y += gridBagLayoutInfo.minHeight[k];
/*      */         }
/* 2185 */         rectangle.width = 0;
/* 2186 */         k = gridBagConstraints.tempX;
/* 2187 */         for (; k < gridBagConstraints.tempX + gridBagConstraints.tempWidth; 
/* 2188 */           k++) {
/* 2189 */           rectangle.width += gridBagLayoutInfo.minWidth[k];
/*      */         }
/*      */         
/* 2192 */         rectangle.height = 0;
/* 2193 */         k = gridBagConstraints.tempY;
/* 2194 */         for (; k < gridBagConstraints.tempY + gridBagConstraints.tempHeight; 
/* 2195 */           k++) {
/* 2196 */           rectangle.height += gridBagLayoutInfo.minHeight[k];
/*      */         }
/*      */         
/* 2199 */         this.componentAdjusting = component;
/* 2200 */         adjustForGravity(gridBagConstraints, rectangle);
/*      */ 
/*      */ 
/*      */         
/* 2204 */         if (rectangle.x < 0) {
/* 2205 */           rectangle.width += rectangle.x;
/* 2206 */           rectangle.x = 0;
/*      */         } 
/*      */         
/* 2209 */         if (rectangle.y < 0) {
/* 2210 */           rectangle.height += rectangle.y;
/* 2211 */           rectangle.y = 0;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2220 */         if (rectangle.width <= 0 || rectangle.height <= 0) {
/* 2221 */           component.setBounds(0, 0, 0, 0);
/*      */         
/*      */         }
/* 2224 */         else if (component.x != rectangle.x || component.y != rectangle.y || component.width != rectangle.width || component.height != rectangle.height) {
/*      */           
/* 2226 */           component.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GridBagLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */