/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import sun.awt.image.MultiResolutionToolkitImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaTracker
/*     */   implements Serializable
/*     */ {
/*     */   Component target;
/*     */   MediaEntry head;
/*     */   private static final long serialVersionUID = -483174189758638095L;
/*     */   public static final int LOADING = 1;
/*     */   public static final int ABORTED = 2;
/*     */   public static final int ERRORED = 4;
/*     */   public static final int COMPLETE = 8;
/*     */   static final int DONE = 14;
/*     */   
/*     */   public MediaTracker(Component paramComponent) {
/* 201 */     this.target = paramComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addImage(Image paramImage, int paramInt) {
/* 212 */     addImage(paramImage, paramInt, -1, -1);
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
/*     */   public synchronized void addImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3) {
/* 226 */     addImageImpl(paramImage, paramInt1, paramInt2, paramInt3);
/* 227 */     Image image = getResolutionVariant(paramImage);
/* 228 */     if (image != null) {
/* 229 */       addImageImpl(image, paramInt1, (paramInt2 == -1) ? -1 : (2 * paramInt2), (paramInt3 == -1) ? -1 : (2 * paramInt3));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addImageImpl(Image paramImage, int paramInt1, int paramInt2, int paramInt3) {
/* 236 */     this.head = MediaEntry.insert(this.head, new ImageMediaEntry(this, paramImage, paramInt1, paramInt2, paramInt3));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkAll() {
/* 291 */     return checkAll(false, true);
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
/*     */   public boolean checkAll(boolean paramBoolean) {
/* 317 */     return checkAll(paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized boolean checkAll(boolean paramBoolean1, boolean paramBoolean2) {
/* 321 */     MediaEntry mediaEntry = this.head;
/* 322 */     boolean bool = true;
/* 323 */     while (mediaEntry != null) {
/* 324 */       if ((mediaEntry.getStatus(paramBoolean1, paramBoolean2) & 0xE) == 0) {
/* 325 */         bool = false;
/*     */       }
/* 327 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 329 */     return bool;
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
/*     */   public synchronized boolean isErrorAny() {
/* 341 */     MediaEntry mediaEntry = this.head;
/* 342 */     while (mediaEntry != null) {
/* 343 */       if ((mediaEntry.getStatus(false, true) & 0x4) != 0) {
/* 344 */         return true;
/*     */       }
/* 346 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 348 */     return false;
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
/*     */   public synchronized Object[] getErrorsAny() {
/* 361 */     MediaEntry mediaEntry = this.head;
/* 362 */     byte b = 0;
/* 363 */     while (mediaEntry != null) {
/* 364 */       if ((mediaEntry.getStatus(false, true) & 0x4) != 0) {
/* 365 */         b++;
/*     */       }
/* 367 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 369 */     if (b == 0) {
/* 370 */       return null;
/*     */     }
/* 372 */     Object[] arrayOfObject = new Object[b];
/* 373 */     mediaEntry = this.head;
/* 374 */     b = 0;
/* 375 */     while (mediaEntry != null) {
/* 376 */       if ((mediaEntry.getStatus(false, false) & 0x4) != 0) {
/* 377 */         arrayOfObject[b++] = mediaEntry.getMedia();
/*     */       }
/* 379 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 381 */     return arrayOfObject;
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
/*     */   public void waitForAll() throws InterruptedException {
/* 401 */     waitForAll(0L);
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
/*     */   public synchronized boolean waitForAll(long paramLong) throws InterruptedException {
/* 428 */     long l = System.currentTimeMillis() + paramLong;
/* 429 */     boolean bool = true; while (true) {
/*     */       long l1;
/* 431 */       int i = statusAll(bool, bool);
/* 432 */       if ((i & 0x1) == 0) {
/* 433 */         return (i == 8);
/*     */       }
/* 435 */       bool = false;
/*     */       
/* 437 */       if (paramLong == 0L) {
/* 438 */         l1 = 0L;
/*     */       } else {
/* 440 */         l1 = l - System.currentTimeMillis();
/* 441 */         if (l1 <= 0L) {
/* 442 */           return false;
/*     */         }
/*     */       } 
/* 445 */       wait(l1);
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
/*     */   public int statusAll(boolean paramBoolean) {
/* 473 */     return statusAll(paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized int statusAll(boolean paramBoolean1, boolean paramBoolean2) {
/* 477 */     MediaEntry mediaEntry = this.head;
/* 478 */     int i = 0;
/* 479 */     while (mediaEntry != null) {
/* 480 */       i |= mediaEntry.getStatus(paramBoolean1, paramBoolean2);
/* 481 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 483 */     return i;
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
/*     */   public boolean checkID(int paramInt) {
/* 507 */     return checkID(paramInt, false, true);
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
/*     */   public boolean checkID(int paramInt, boolean paramBoolean) {
/* 534 */     return checkID(paramInt, paramBoolean, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized boolean checkID(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 539 */     MediaEntry mediaEntry = this.head;
/* 540 */     boolean bool = true;
/* 541 */     while (mediaEntry != null) {
/* 542 */       if (mediaEntry.getID() == paramInt && (mediaEntry
/* 543 */         .getStatus(paramBoolean1, paramBoolean2) & 0xE) == 0)
/*     */       {
/* 545 */         bool = false;
/*     */       }
/* 547 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 549 */     return bool;
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
/*     */   public synchronized boolean isErrorID(int paramInt) {
/* 563 */     MediaEntry mediaEntry = this.head;
/* 564 */     while (mediaEntry != null) {
/* 565 */       if (mediaEntry.getID() == paramInt && (mediaEntry
/* 566 */         .getStatus(false, true) & 0x4) != 0)
/*     */       {
/* 568 */         return true;
/*     */       }
/* 570 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 572 */     return false;
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
/*     */   public synchronized Object[] getErrorsID(int paramInt) {
/* 588 */     MediaEntry mediaEntry = this.head;
/* 589 */     byte b = 0;
/* 590 */     while (mediaEntry != null) {
/* 591 */       if (mediaEntry.getID() == paramInt && (mediaEntry
/* 592 */         .getStatus(false, true) & 0x4) != 0)
/*     */       {
/* 594 */         b++;
/*     */       }
/* 596 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 598 */     if (b == 0) {
/* 599 */       return null;
/*     */     }
/* 601 */     Object[] arrayOfObject = new Object[b];
/* 602 */     mediaEntry = this.head;
/* 603 */     b = 0;
/* 604 */     while (mediaEntry != null) {
/* 605 */       if (mediaEntry.getID() == paramInt && (mediaEntry
/* 606 */         .getStatus(false, false) & 0x4) != 0)
/*     */       {
/* 608 */         arrayOfObject[b++] = mediaEntry.getMedia();
/*     */       }
/* 610 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 612 */     return arrayOfObject;
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
/*     */   public void waitForID(int paramInt) throws InterruptedException {
/* 632 */     waitForID(paramInt, 0L);
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
/*     */   public synchronized boolean waitForID(int paramInt, long paramLong) throws InterruptedException {
/* 660 */     long l = System.currentTimeMillis() + paramLong;
/* 661 */     boolean bool = true; while (true) {
/*     */       long l1;
/* 663 */       int i = statusID(paramInt, bool, bool);
/* 664 */       if ((i & 0x1) == 0) {
/* 665 */         return (i == 8);
/*     */       }
/* 667 */       bool = false;
/*     */       
/* 669 */       if (paramLong == 0L) {
/* 670 */         l1 = 0L;
/*     */       } else {
/* 672 */         l1 = l - System.currentTimeMillis();
/* 673 */         if (l1 <= 0L) {
/* 674 */           return false;
/*     */         }
/*     */       } 
/* 677 */       wait(l1);
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
/*     */   public int statusID(int paramInt, boolean paramBoolean) {
/* 707 */     return statusID(paramInt, paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized int statusID(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 711 */     MediaEntry mediaEntry = this.head;
/* 712 */     int i = 0;
/* 713 */     while (mediaEntry != null) {
/* 714 */       if (mediaEntry.getID() == paramInt) {
/* 715 */         i |= mediaEntry.getStatus(paramBoolean1, paramBoolean2);
/*     */       }
/* 717 */       mediaEntry = mediaEntry.next;
/*     */     } 
/* 719 */     return i;
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
/*     */   public synchronized void removeImage(Image paramImage) {
/* 732 */     removeImageImpl(paramImage);
/* 733 */     Image image = getResolutionVariant(paramImage);
/* 734 */     if (image != null) {
/* 735 */       removeImageImpl(image);
/*     */     }
/* 737 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage) {
/* 741 */     MediaEntry mediaEntry1 = this.head;
/* 742 */     MediaEntry mediaEntry2 = null;
/* 743 */     while (mediaEntry1 != null) {
/* 744 */       MediaEntry mediaEntry = mediaEntry1.next;
/* 745 */       if (mediaEntry1.getMedia() == paramImage) {
/* 746 */         if (mediaEntry2 == null) {
/* 747 */           this.head = mediaEntry;
/*     */         } else {
/* 749 */           mediaEntry2.next = mediaEntry;
/*     */         } 
/* 751 */         mediaEntry1.cancel();
/*     */       } else {
/* 753 */         mediaEntry2 = mediaEntry1;
/*     */       } 
/* 755 */       mediaEntry1 = mediaEntry;
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
/*     */   public synchronized void removeImage(Image paramImage, int paramInt) {
/* 771 */     removeImageImpl(paramImage, paramInt);
/* 772 */     Image image = getResolutionVariant(paramImage);
/* 773 */     if (image != null) {
/* 774 */       removeImageImpl(image, paramInt);
/*     */     }
/* 776 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage, int paramInt) {
/* 780 */     MediaEntry mediaEntry1 = this.head;
/* 781 */     MediaEntry mediaEntry2 = null;
/* 782 */     while (mediaEntry1 != null) {
/* 783 */       MediaEntry mediaEntry = mediaEntry1.next;
/* 784 */       if (mediaEntry1.getID() == paramInt && mediaEntry1.getMedia() == paramImage) {
/* 785 */         if (mediaEntry2 == null) {
/* 786 */           this.head = mediaEntry;
/*     */         } else {
/* 788 */           mediaEntry2.next = mediaEntry;
/*     */         } 
/* 790 */         mediaEntry1.cancel();
/*     */       } else {
/* 792 */         mediaEntry2 = mediaEntry1;
/*     */       } 
/* 794 */       mediaEntry1 = mediaEntry;
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
/*     */   public synchronized void removeImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3) {
/* 812 */     removeImageImpl(paramImage, paramInt1, paramInt2, paramInt3);
/* 813 */     Image image = getResolutionVariant(paramImage);
/* 814 */     if (image != null) {
/* 815 */       removeImageImpl(image, paramInt1, (paramInt2 == -1) ? -1 : (2 * paramInt2), (paramInt3 == -1) ? -1 : (2 * paramInt3));
/*     */     }
/*     */ 
/*     */     
/* 819 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage, int paramInt1, int paramInt2, int paramInt3) {
/* 823 */     MediaEntry mediaEntry1 = this.head;
/* 824 */     MediaEntry mediaEntry2 = null;
/* 825 */     while (mediaEntry1 != null) {
/* 826 */       MediaEntry mediaEntry = mediaEntry1.next;
/* 827 */       if (mediaEntry1.getID() == paramInt1 && mediaEntry1 instanceof ImageMediaEntry && ((ImageMediaEntry)mediaEntry1)
/* 828 */         .matches(paramImage, paramInt2, paramInt3)) {
/*     */         
/* 830 */         if (mediaEntry2 == null) {
/* 831 */           this.head = mediaEntry;
/*     */         } else {
/* 833 */           mediaEntry2.next = mediaEntry;
/*     */         } 
/* 835 */         mediaEntry1.cancel();
/*     */       } else {
/* 837 */         mediaEntry2 = mediaEntry1;
/*     */       } 
/* 839 */       mediaEntry1 = mediaEntry;
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void setDone() {
/* 844 */     notifyAll();
/*     */   }
/*     */   
/*     */   private static Image getResolutionVariant(Image paramImage) {
/* 848 */     if (paramImage instanceof MultiResolutionToolkitImage) {
/* 849 */       return ((MultiResolutionToolkitImage)paramImage).getResolutionVariant();
/*     */     }
/* 851 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MediaTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */