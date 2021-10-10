/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncodingMap
/*     */ {
/*     */   protected static final Map<String, String> fIANA2JavaMap;
/*     */   protected static final Map<String, String> fJava2IANAMap;
/*     */   
/*     */   static {
/* 492 */     Map<String, String> aIANA2JavaMap = new HashMap<>();
/* 493 */     Map<String, String> aJava2IANAMap = new HashMap<>();
/*     */ 
/*     */     
/* 496 */     aIANA2JavaMap.put("BIG5", "Big5");
/* 497 */     aIANA2JavaMap.put("CSBIG5", "Big5");
/* 498 */     aIANA2JavaMap.put("CP037", "CP037");
/* 499 */     aIANA2JavaMap.put("IBM037", "CP037");
/* 500 */     aIANA2JavaMap.put("CSIBM037", "CP037");
/* 501 */     aIANA2JavaMap.put("EBCDIC-CP-US", "CP037");
/* 502 */     aIANA2JavaMap.put("EBCDIC-CP-CA", "CP037");
/* 503 */     aIANA2JavaMap.put("EBCDIC-CP-NL", "CP037");
/* 504 */     aIANA2JavaMap.put("EBCDIC-CP-WT", "CP037");
/* 505 */     aIANA2JavaMap.put("IBM273", "CP273");
/* 506 */     aIANA2JavaMap.put("CP273", "CP273");
/* 507 */     aIANA2JavaMap.put("CSIBM273", "CP273");
/* 508 */     aIANA2JavaMap.put("IBM277", "CP277");
/* 509 */     aIANA2JavaMap.put("CP277", "CP277");
/* 510 */     aIANA2JavaMap.put("CSIBM277", "CP277");
/* 511 */     aIANA2JavaMap.put("EBCDIC-CP-DK", "CP277");
/* 512 */     aIANA2JavaMap.put("EBCDIC-CP-NO", "CP277");
/* 513 */     aIANA2JavaMap.put("IBM278", "CP278");
/* 514 */     aIANA2JavaMap.put("CP278", "CP278");
/* 515 */     aIANA2JavaMap.put("CSIBM278", "CP278");
/* 516 */     aIANA2JavaMap.put("EBCDIC-CP-FI", "CP278");
/* 517 */     aIANA2JavaMap.put("EBCDIC-CP-SE", "CP278");
/* 518 */     aIANA2JavaMap.put("IBM280", "CP280");
/* 519 */     aIANA2JavaMap.put("CP280", "CP280");
/* 520 */     aIANA2JavaMap.put("CSIBM280", "CP280");
/* 521 */     aIANA2JavaMap.put("EBCDIC-CP-IT", "CP280");
/* 522 */     aIANA2JavaMap.put("IBM284", "CP284");
/* 523 */     aIANA2JavaMap.put("CP284", "CP284");
/* 524 */     aIANA2JavaMap.put("CSIBM284", "CP284");
/* 525 */     aIANA2JavaMap.put("EBCDIC-CP-ES", "CP284");
/* 526 */     aIANA2JavaMap.put("EBCDIC-CP-GB", "CP285");
/* 527 */     aIANA2JavaMap.put("IBM285", "CP285");
/* 528 */     aIANA2JavaMap.put("CP285", "CP285");
/* 529 */     aIANA2JavaMap.put("CSIBM285", "CP285");
/* 530 */     aIANA2JavaMap.put("EBCDIC-JP-KANA", "CP290");
/* 531 */     aIANA2JavaMap.put("IBM290", "CP290");
/* 532 */     aIANA2JavaMap.put("CP290", "CP290");
/* 533 */     aIANA2JavaMap.put("CSIBM290", "CP290");
/* 534 */     aIANA2JavaMap.put("EBCDIC-CP-FR", "CP297");
/* 535 */     aIANA2JavaMap.put("IBM297", "CP297");
/* 536 */     aIANA2JavaMap.put("CP297", "CP297");
/* 537 */     aIANA2JavaMap.put("CSIBM297", "CP297");
/* 538 */     aIANA2JavaMap.put("EBCDIC-CP-AR1", "CP420");
/* 539 */     aIANA2JavaMap.put("IBM420", "CP420");
/* 540 */     aIANA2JavaMap.put("CP420", "CP420");
/* 541 */     aIANA2JavaMap.put("CSIBM420", "CP420");
/* 542 */     aIANA2JavaMap.put("EBCDIC-CP-HE", "CP424");
/* 543 */     aIANA2JavaMap.put("IBM424", "CP424");
/* 544 */     aIANA2JavaMap.put("CP424", "CP424");
/* 545 */     aIANA2JavaMap.put("CSIBM424", "CP424");
/* 546 */     aIANA2JavaMap.put("IBM437", "CP437");
/* 547 */     aIANA2JavaMap.put("437", "CP437");
/* 548 */     aIANA2JavaMap.put("CP437", "CP437");
/* 549 */     aIANA2JavaMap.put("CSPC8CODEPAGE437", "CP437");
/* 550 */     aIANA2JavaMap.put("EBCDIC-CP-CH", "CP500");
/* 551 */     aIANA2JavaMap.put("IBM500", "CP500");
/* 552 */     aIANA2JavaMap.put("CP500", "CP500");
/* 553 */     aIANA2JavaMap.put("CSIBM500", "CP500");
/* 554 */     aIANA2JavaMap.put("EBCDIC-CP-CH", "CP500");
/* 555 */     aIANA2JavaMap.put("EBCDIC-CP-BE", "CP500");
/* 556 */     aIANA2JavaMap.put("IBM775", "CP775");
/* 557 */     aIANA2JavaMap.put("CP775", "CP775");
/* 558 */     aIANA2JavaMap.put("CSPC775BALTIC", "CP775");
/* 559 */     aIANA2JavaMap.put("IBM850", "CP850");
/* 560 */     aIANA2JavaMap.put("850", "CP850");
/* 561 */     aIANA2JavaMap.put("CP850", "CP850");
/* 562 */     aIANA2JavaMap.put("CSPC850MULTILINGUAL", "CP850");
/* 563 */     aIANA2JavaMap.put("IBM852", "CP852");
/* 564 */     aIANA2JavaMap.put("852", "CP852");
/* 565 */     aIANA2JavaMap.put("CP852", "CP852");
/* 566 */     aIANA2JavaMap.put("CSPCP852", "CP852");
/* 567 */     aIANA2JavaMap.put("IBM855", "CP855");
/* 568 */     aIANA2JavaMap.put("855", "CP855");
/* 569 */     aIANA2JavaMap.put("CP855", "CP855");
/* 570 */     aIANA2JavaMap.put("CSIBM855", "CP855");
/* 571 */     aIANA2JavaMap.put("IBM857", "CP857");
/* 572 */     aIANA2JavaMap.put("857", "CP857");
/* 573 */     aIANA2JavaMap.put("CP857", "CP857");
/* 574 */     aIANA2JavaMap.put("CSIBM857", "CP857");
/* 575 */     aIANA2JavaMap.put("IBM00858", "CP858");
/* 576 */     aIANA2JavaMap.put("CP00858", "CP858");
/* 577 */     aIANA2JavaMap.put("CCSID00858", "CP858");
/* 578 */     aIANA2JavaMap.put("IBM860", "CP860");
/* 579 */     aIANA2JavaMap.put("860", "CP860");
/* 580 */     aIANA2JavaMap.put("CP860", "CP860");
/* 581 */     aIANA2JavaMap.put("CSIBM860", "CP860");
/* 582 */     aIANA2JavaMap.put("IBM861", "CP861");
/* 583 */     aIANA2JavaMap.put("861", "CP861");
/* 584 */     aIANA2JavaMap.put("CP861", "CP861");
/* 585 */     aIANA2JavaMap.put("CP-IS", "CP861");
/* 586 */     aIANA2JavaMap.put("CSIBM861", "CP861");
/* 587 */     aIANA2JavaMap.put("IBM862", "CP862");
/* 588 */     aIANA2JavaMap.put("862", "CP862");
/* 589 */     aIANA2JavaMap.put("CP862", "CP862");
/* 590 */     aIANA2JavaMap.put("CSPC862LATINHEBREW", "CP862");
/* 591 */     aIANA2JavaMap.put("IBM863", "CP863");
/* 592 */     aIANA2JavaMap.put("863", "CP863");
/* 593 */     aIANA2JavaMap.put("CP863", "CP863");
/* 594 */     aIANA2JavaMap.put("CSIBM863", "CP863");
/* 595 */     aIANA2JavaMap.put("IBM864", "CP864");
/* 596 */     aIANA2JavaMap.put("CP864", "CP864");
/* 597 */     aIANA2JavaMap.put("CSIBM864", "CP864");
/* 598 */     aIANA2JavaMap.put("IBM865", "CP865");
/* 599 */     aIANA2JavaMap.put("865", "CP865");
/* 600 */     aIANA2JavaMap.put("CP865", "CP865");
/* 601 */     aIANA2JavaMap.put("CSIBM865", "CP865");
/* 602 */     aIANA2JavaMap.put("IBM866", "CP866");
/* 603 */     aIANA2JavaMap.put("866", "CP866");
/* 604 */     aIANA2JavaMap.put("CP866", "CP866");
/* 605 */     aIANA2JavaMap.put("CSIBM866", "CP866");
/* 606 */     aIANA2JavaMap.put("IBM868", "CP868");
/* 607 */     aIANA2JavaMap.put("CP868", "CP868");
/* 608 */     aIANA2JavaMap.put("CSIBM868", "CP868");
/* 609 */     aIANA2JavaMap.put("CP-AR", "CP868");
/* 610 */     aIANA2JavaMap.put("IBM869", "CP869");
/* 611 */     aIANA2JavaMap.put("CP869", "CP869");
/* 612 */     aIANA2JavaMap.put("CSIBM869", "CP869");
/* 613 */     aIANA2JavaMap.put("CP-GR", "CP869");
/* 614 */     aIANA2JavaMap.put("IBM870", "CP870");
/* 615 */     aIANA2JavaMap.put("CP870", "CP870");
/* 616 */     aIANA2JavaMap.put("CSIBM870", "CP870");
/* 617 */     aIANA2JavaMap.put("EBCDIC-CP-ROECE", "CP870");
/* 618 */     aIANA2JavaMap.put("EBCDIC-CP-YU", "CP870");
/* 619 */     aIANA2JavaMap.put("IBM871", "CP871");
/* 620 */     aIANA2JavaMap.put("CP871", "CP871");
/* 621 */     aIANA2JavaMap.put("CSIBM871", "CP871");
/* 622 */     aIANA2JavaMap.put("EBCDIC-CP-IS", "CP871");
/* 623 */     aIANA2JavaMap.put("IBM918", "CP918");
/* 624 */     aIANA2JavaMap.put("CP918", "CP918");
/* 625 */     aIANA2JavaMap.put("CSIBM918", "CP918");
/* 626 */     aIANA2JavaMap.put("EBCDIC-CP-AR2", "CP918");
/* 627 */     aIANA2JavaMap.put("IBM00924", "CP924");
/* 628 */     aIANA2JavaMap.put("CP00924", "CP924");
/* 629 */     aIANA2JavaMap.put("CCSID00924", "CP924");
/*     */     
/* 631 */     aIANA2JavaMap.put("EBCDIC-LATIN9--EURO", "CP924");
/* 632 */     aIANA2JavaMap.put("IBM1026", "CP1026");
/* 633 */     aIANA2JavaMap.put("CP1026", "CP1026");
/* 634 */     aIANA2JavaMap.put("CSIBM1026", "CP1026");
/* 635 */     aIANA2JavaMap.put("IBM01140", "Cp1140");
/* 636 */     aIANA2JavaMap.put("CP01140", "Cp1140");
/* 637 */     aIANA2JavaMap.put("CCSID01140", "Cp1140");
/* 638 */     aIANA2JavaMap.put("IBM01141", "Cp1141");
/* 639 */     aIANA2JavaMap.put("CP01141", "Cp1141");
/* 640 */     aIANA2JavaMap.put("CCSID01141", "Cp1141");
/* 641 */     aIANA2JavaMap.put("IBM01142", "Cp1142");
/* 642 */     aIANA2JavaMap.put("CP01142", "Cp1142");
/* 643 */     aIANA2JavaMap.put("CCSID01142", "Cp1142");
/* 644 */     aIANA2JavaMap.put("IBM01143", "Cp1143");
/* 645 */     aIANA2JavaMap.put("CP01143", "Cp1143");
/* 646 */     aIANA2JavaMap.put("CCSID01143", "Cp1143");
/* 647 */     aIANA2JavaMap.put("IBM01144", "Cp1144");
/* 648 */     aIANA2JavaMap.put("CP01144", "Cp1144");
/* 649 */     aIANA2JavaMap.put("CCSID01144", "Cp1144");
/* 650 */     aIANA2JavaMap.put("IBM01145", "Cp1145");
/* 651 */     aIANA2JavaMap.put("CP01145", "Cp1145");
/* 652 */     aIANA2JavaMap.put("CCSID01145", "Cp1145");
/* 653 */     aIANA2JavaMap.put("IBM01146", "Cp1146");
/* 654 */     aIANA2JavaMap.put("CP01146", "Cp1146");
/* 655 */     aIANA2JavaMap.put("CCSID01146", "Cp1146");
/* 656 */     aIANA2JavaMap.put("IBM01147", "Cp1147");
/* 657 */     aIANA2JavaMap.put("CP01147", "Cp1147");
/* 658 */     aIANA2JavaMap.put("CCSID01147", "Cp1147");
/* 659 */     aIANA2JavaMap.put("IBM01148", "Cp1148");
/* 660 */     aIANA2JavaMap.put("CP01148", "Cp1148");
/* 661 */     aIANA2JavaMap.put("CCSID01148", "Cp1148");
/* 662 */     aIANA2JavaMap.put("IBM01149", "Cp1149");
/* 663 */     aIANA2JavaMap.put("CP01149", "Cp1149");
/* 664 */     aIANA2JavaMap.put("CCSID01149", "Cp1149");
/* 665 */     aIANA2JavaMap.put("EUC-JP", "EUCJIS");
/* 666 */     aIANA2JavaMap.put("CSEUCPKDFMTJAPANESE", "EUCJIS");
/* 667 */     aIANA2JavaMap.put("EXTENDED_UNIX_CODE_PACKED_FORMAT_FOR_JAPANESE", "EUCJIS");
/* 668 */     aIANA2JavaMap.put("EUC-KR", "KSC5601");
/* 669 */     aIANA2JavaMap.put("CSEUCKR", "KSC5601");
/* 670 */     aIANA2JavaMap.put("KS_C_5601-1987", "KS_C_5601-1987");
/* 671 */     aIANA2JavaMap.put("ISO-IR-149", "KS_C_5601-1987");
/* 672 */     aIANA2JavaMap.put("KS_C_5601-1989", "KS_C_5601-1987");
/* 673 */     aIANA2JavaMap.put("KSC_5601", "KS_C_5601-1987");
/* 674 */     aIANA2JavaMap.put("KOREAN", "KS_C_5601-1987");
/* 675 */     aIANA2JavaMap.put("CSKSC56011987", "KS_C_5601-1987");
/* 676 */     aIANA2JavaMap.put("GB2312", "GB2312");
/* 677 */     aIANA2JavaMap.put("CSGB2312", "GB2312");
/* 678 */     aIANA2JavaMap.put("ISO-2022-JP", "JIS");
/* 679 */     aIANA2JavaMap.put("CSISO2022JP", "JIS");
/* 680 */     aIANA2JavaMap.put("ISO-2022-KR", "ISO2022KR");
/* 681 */     aIANA2JavaMap.put("CSISO2022KR", "ISO2022KR");
/* 682 */     aIANA2JavaMap.put("ISO-2022-CN", "ISO2022CN");
/*     */     
/* 684 */     aIANA2JavaMap.put("X0201", "JIS0201");
/* 685 */     aIANA2JavaMap.put("CSISO13JISC6220JP", "JIS0201");
/* 686 */     aIANA2JavaMap.put("X0208", "JIS0208");
/* 687 */     aIANA2JavaMap.put("ISO-IR-87", "JIS0208");
/* 688 */     aIANA2JavaMap.put("X0208dbiJIS_X0208-1983", "JIS0208");
/* 689 */     aIANA2JavaMap.put("CSISO87JISX0208", "JIS0208");
/* 690 */     aIANA2JavaMap.put("X0212", "JIS0212");
/* 691 */     aIANA2JavaMap.put("ISO-IR-159", "JIS0212");
/* 692 */     aIANA2JavaMap.put("CSISO159JISX02121990", "JIS0212");
/* 693 */     aIANA2JavaMap.put("GB18030", "GB18030");
/* 694 */     aIANA2JavaMap.put("GBK", "GBK");
/* 695 */     aIANA2JavaMap.put("CP936", "GBK");
/* 696 */     aIANA2JavaMap.put("MS936", "GBK");
/* 697 */     aIANA2JavaMap.put("WINDOWS-936", "GBK");
/* 698 */     aIANA2JavaMap.put("SHIFT_JIS", "SJIS");
/* 699 */     aIANA2JavaMap.put("CSSHIFTJIS", "SJIS");
/* 700 */     aIANA2JavaMap.put("MS_KANJI", "SJIS");
/* 701 */     aIANA2JavaMap.put("WINDOWS-31J", "MS932");
/* 702 */     aIANA2JavaMap.put("CSWINDOWS31J", "MS932");
/*     */ 
/*     */     
/* 705 */     aIANA2JavaMap.put("WINDOWS-1250", "Cp1250");
/* 706 */     aIANA2JavaMap.put("WINDOWS-1251", "Cp1251");
/* 707 */     aIANA2JavaMap.put("WINDOWS-1252", "Cp1252");
/* 708 */     aIANA2JavaMap.put("WINDOWS-1253", "Cp1253");
/* 709 */     aIANA2JavaMap.put("WINDOWS-1254", "Cp1254");
/* 710 */     aIANA2JavaMap.put("WINDOWS-1255", "Cp1255");
/* 711 */     aIANA2JavaMap.put("WINDOWS-1256", "Cp1256");
/* 712 */     aIANA2JavaMap.put("WINDOWS-1257", "Cp1257");
/* 713 */     aIANA2JavaMap.put("WINDOWS-1258", "Cp1258");
/* 714 */     aIANA2JavaMap.put("TIS-620", "TIS620");
/*     */     
/* 716 */     aIANA2JavaMap.put("ISO-8859-1", "ISO8859_1");
/* 717 */     aIANA2JavaMap.put("ISO-IR-100", "ISO8859_1");
/* 718 */     aIANA2JavaMap.put("ISO_8859-1", "ISO8859_1");
/* 719 */     aIANA2JavaMap.put("LATIN1", "ISO8859_1");
/* 720 */     aIANA2JavaMap.put("CSISOLATIN1", "ISO8859_1");
/* 721 */     aIANA2JavaMap.put("L1", "ISO8859_1");
/* 722 */     aIANA2JavaMap.put("IBM819", "ISO8859_1");
/* 723 */     aIANA2JavaMap.put("CP819", "ISO8859_1");
/*     */     
/* 725 */     aIANA2JavaMap.put("ISO-8859-2", "ISO8859_2");
/* 726 */     aIANA2JavaMap.put("ISO-IR-101", "ISO8859_2");
/* 727 */     aIANA2JavaMap.put("ISO_8859-2", "ISO8859_2");
/* 728 */     aIANA2JavaMap.put("LATIN2", "ISO8859_2");
/* 729 */     aIANA2JavaMap.put("CSISOLATIN2", "ISO8859_2");
/* 730 */     aIANA2JavaMap.put("L2", "ISO8859_2");
/*     */     
/* 732 */     aIANA2JavaMap.put("ISO-8859-3", "ISO8859_3");
/* 733 */     aIANA2JavaMap.put("ISO-IR-109", "ISO8859_3");
/* 734 */     aIANA2JavaMap.put("ISO_8859-3", "ISO8859_3");
/* 735 */     aIANA2JavaMap.put("LATIN3", "ISO8859_3");
/* 736 */     aIANA2JavaMap.put("CSISOLATIN3", "ISO8859_3");
/* 737 */     aIANA2JavaMap.put("L3", "ISO8859_3");
/*     */     
/* 739 */     aIANA2JavaMap.put("ISO-8859-4", "ISO8859_4");
/* 740 */     aIANA2JavaMap.put("ISO-IR-110", "ISO8859_4");
/* 741 */     aIANA2JavaMap.put("ISO_8859-4", "ISO8859_4");
/* 742 */     aIANA2JavaMap.put("LATIN4", "ISO8859_4");
/* 743 */     aIANA2JavaMap.put("CSISOLATIN4", "ISO8859_4");
/* 744 */     aIANA2JavaMap.put("L4", "ISO8859_4");
/*     */     
/* 746 */     aIANA2JavaMap.put("ISO-8859-5", "ISO8859_5");
/* 747 */     aIANA2JavaMap.put("ISO-IR-144", "ISO8859_5");
/* 748 */     aIANA2JavaMap.put("ISO_8859-5", "ISO8859_5");
/* 749 */     aIANA2JavaMap.put("CYRILLIC", "ISO8859_5");
/* 750 */     aIANA2JavaMap.put("CSISOLATINCYRILLIC", "ISO8859_5");
/*     */     
/* 752 */     aIANA2JavaMap.put("ISO-8859-6", "ISO8859_6");
/* 753 */     aIANA2JavaMap.put("ISO-IR-127", "ISO8859_6");
/* 754 */     aIANA2JavaMap.put("ISO_8859-6", "ISO8859_6");
/* 755 */     aIANA2JavaMap.put("ECMA-114", "ISO8859_6");
/* 756 */     aIANA2JavaMap.put("ASMO-708", "ISO8859_6");
/* 757 */     aIANA2JavaMap.put("ARABIC", "ISO8859_6");
/* 758 */     aIANA2JavaMap.put("CSISOLATINARABIC", "ISO8859_6");
/*     */     
/* 760 */     aIANA2JavaMap.put("ISO-8859-7", "ISO8859_7");
/* 761 */     aIANA2JavaMap.put("ISO-IR-126", "ISO8859_7");
/* 762 */     aIANA2JavaMap.put("ISO_8859-7", "ISO8859_7");
/* 763 */     aIANA2JavaMap.put("ELOT_928", "ISO8859_7");
/* 764 */     aIANA2JavaMap.put("ECMA-118", "ISO8859_7");
/* 765 */     aIANA2JavaMap.put("GREEK", "ISO8859_7");
/* 766 */     aIANA2JavaMap.put("CSISOLATINGREEK", "ISO8859_7");
/* 767 */     aIANA2JavaMap.put("GREEK8", "ISO8859_7");
/*     */     
/* 769 */     aIANA2JavaMap.put("ISO-8859-8", "ISO8859_8");
/* 770 */     aIANA2JavaMap.put("ISO-8859-8-I", "ISO8859_8");
/* 771 */     aIANA2JavaMap.put("ISO-IR-138", "ISO8859_8");
/* 772 */     aIANA2JavaMap.put("ISO_8859-8", "ISO8859_8");
/* 773 */     aIANA2JavaMap.put("HEBREW", "ISO8859_8");
/* 774 */     aIANA2JavaMap.put("CSISOLATINHEBREW", "ISO8859_8");
/*     */     
/* 776 */     aIANA2JavaMap.put("ISO-8859-9", "ISO8859_9");
/* 777 */     aIANA2JavaMap.put("ISO-IR-148", "ISO8859_9");
/* 778 */     aIANA2JavaMap.put("ISO_8859-9", "ISO8859_9");
/* 779 */     aIANA2JavaMap.put("LATIN5", "ISO8859_9");
/* 780 */     aIANA2JavaMap.put("CSISOLATIN5", "ISO8859_9");
/* 781 */     aIANA2JavaMap.put("L5", "ISO8859_9");
/*     */     
/* 783 */     aIANA2JavaMap.put("ISO-8859-13", "ISO8859_13");
/*     */     
/* 785 */     aIANA2JavaMap.put("ISO-8859-15", "ISO8859_15_FDIS");
/* 786 */     aIANA2JavaMap.put("ISO_8859-15", "ISO8859_15_FDIS");
/* 787 */     aIANA2JavaMap.put("LATIN-9", "ISO8859_15_FDIS");
/*     */     
/* 789 */     aIANA2JavaMap.put("KOI8-R", "KOI8_R");
/* 790 */     aIANA2JavaMap.put("CSKOI8R", "KOI8_R");
/* 791 */     aIANA2JavaMap.put("US-ASCII", "ASCII");
/* 792 */     aIANA2JavaMap.put("ISO-IR-6", "ASCII");
/* 793 */     aIANA2JavaMap.put("ANSI_X3.4-1968", "ASCII");
/* 794 */     aIANA2JavaMap.put("ANSI_X3.4-1986", "ASCII");
/* 795 */     aIANA2JavaMap.put("ISO_646.IRV:1991", "ASCII");
/* 796 */     aIANA2JavaMap.put("ASCII", "ASCII");
/* 797 */     aIANA2JavaMap.put("CSASCII", "ASCII");
/* 798 */     aIANA2JavaMap.put("ISO646-US", "ASCII");
/* 799 */     aIANA2JavaMap.put("US", "ASCII");
/* 800 */     aIANA2JavaMap.put("IBM367", "ASCII");
/* 801 */     aIANA2JavaMap.put("CP367", "ASCII");
/* 802 */     aIANA2JavaMap.put("UTF-8", "UTF8");
/* 803 */     aIANA2JavaMap.put("UTF-16", "UTF-16");
/* 804 */     aIANA2JavaMap.put("UTF-16BE", "UnicodeBig");
/* 805 */     aIANA2JavaMap.put("UTF-16LE", "UnicodeLittle");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 810 */     aIANA2JavaMap.put("IBM-1047", "Cp1047");
/* 811 */     aIANA2JavaMap.put("IBM1047", "Cp1047");
/* 812 */     aIANA2JavaMap.put("CP1047", "Cp1047");
/*     */ 
/*     */ 
/*     */     
/* 816 */     aIANA2JavaMap.put("IBM-37", "CP037");
/* 817 */     aIANA2JavaMap.put("IBM-273", "CP273");
/* 818 */     aIANA2JavaMap.put("IBM-277", "CP277");
/* 819 */     aIANA2JavaMap.put("IBM-278", "CP278");
/* 820 */     aIANA2JavaMap.put("IBM-280", "CP280");
/* 821 */     aIANA2JavaMap.put("IBM-284", "CP284");
/* 822 */     aIANA2JavaMap.put("IBM-285", "CP285");
/* 823 */     aIANA2JavaMap.put("IBM-290", "CP290");
/* 824 */     aIANA2JavaMap.put("IBM-297", "CP297");
/* 825 */     aIANA2JavaMap.put("IBM-420", "CP420");
/* 826 */     aIANA2JavaMap.put("IBM-424", "CP424");
/* 827 */     aIANA2JavaMap.put("IBM-437", "CP437");
/* 828 */     aIANA2JavaMap.put("IBM-500", "CP500");
/* 829 */     aIANA2JavaMap.put("IBM-775", "CP775");
/* 830 */     aIANA2JavaMap.put("IBM-850", "CP850");
/* 831 */     aIANA2JavaMap.put("IBM-852", "CP852");
/* 832 */     aIANA2JavaMap.put("IBM-855", "CP855");
/* 833 */     aIANA2JavaMap.put("IBM-857", "CP857");
/* 834 */     aIANA2JavaMap.put("IBM-858", "CP858");
/* 835 */     aIANA2JavaMap.put("IBM-860", "CP860");
/* 836 */     aIANA2JavaMap.put("IBM-861", "CP861");
/* 837 */     aIANA2JavaMap.put("IBM-862", "CP862");
/* 838 */     aIANA2JavaMap.put("IBM-863", "CP863");
/* 839 */     aIANA2JavaMap.put("IBM-864", "CP864");
/* 840 */     aIANA2JavaMap.put("IBM-865", "CP865");
/* 841 */     aIANA2JavaMap.put("IBM-866", "CP866");
/* 842 */     aIANA2JavaMap.put("IBM-868", "CP868");
/* 843 */     aIANA2JavaMap.put("IBM-869", "CP869");
/* 844 */     aIANA2JavaMap.put("IBM-870", "CP870");
/* 845 */     aIANA2JavaMap.put("IBM-871", "CP871");
/* 846 */     aIANA2JavaMap.put("IBM-918", "CP918");
/* 847 */     aIANA2JavaMap.put("IBM-924", "CP924");
/* 848 */     aIANA2JavaMap.put("IBM-1026", "CP1026");
/* 849 */     aIANA2JavaMap.put("IBM-1140", "Cp1140");
/* 850 */     aIANA2JavaMap.put("IBM-1141", "Cp1141");
/* 851 */     aIANA2JavaMap.put("IBM-1142", "Cp1142");
/* 852 */     aIANA2JavaMap.put("IBM-1143", "Cp1143");
/* 853 */     aIANA2JavaMap.put("IBM-1144", "Cp1144");
/* 854 */     aIANA2JavaMap.put("IBM-1145", "Cp1145");
/* 855 */     aIANA2JavaMap.put("IBM-1146", "Cp1146");
/* 856 */     aIANA2JavaMap.put("IBM-1147", "Cp1147");
/* 857 */     aIANA2JavaMap.put("IBM-1148", "Cp1148");
/* 858 */     aIANA2JavaMap.put("IBM-1149", "Cp1149");
/* 859 */     aIANA2JavaMap.put("IBM-819", "ISO8859_1");
/* 860 */     aIANA2JavaMap.put("IBM-367", "ASCII");
/*     */     
/* 862 */     fIANA2JavaMap = Collections.unmodifiableMap(aIANA2JavaMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 869 */     aJava2IANAMap.put("ISO8859_1", "ISO-8859-1");
/* 870 */     aJava2IANAMap.put("ISO8859_2", "ISO-8859-2");
/* 871 */     aJava2IANAMap.put("ISO8859_3", "ISO-8859-3");
/* 872 */     aJava2IANAMap.put("ISO8859_4", "ISO-8859-4");
/* 873 */     aJava2IANAMap.put("ISO8859_5", "ISO-8859-5");
/* 874 */     aJava2IANAMap.put("ISO8859_6", "ISO-8859-6");
/* 875 */     aJava2IANAMap.put("ISO8859_7", "ISO-8859-7");
/* 876 */     aJava2IANAMap.put("ISO8859_8", "ISO-8859-8");
/* 877 */     aJava2IANAMap.put("ISO8859_9", "ISO-8859-9");
/* 878 */     aJava2IANAMap.put("ISO8859_13", "ISO-8859-13");
/* 879 */     aJava2IANAMap.put("ISO8859_15", "ISO-8859-15");
/* 880 */     aJava2IANAMap.put("ISO8859_15_FDIS", "ISO-8859-15");
/* 881 */     aJava2IANAMap.put("Big5", "BIG5");
/* 882 */     aJava2IANAMap.put("CP037", "EBCDIC-CP-US");
/* 883 */     aJava2IANAMap.put("CP273", "IBM273");
/* 884 */     aJava2IANAMap.put("CP277", "EBCDIC-CP-DK");
/* 885 */     aJava2IANAMap.put("CP278", "EBCDIC-CP-FI");
/* 886 */     aJava2IANAMap.put("CP280", "EBCDIC-CP-IT");
/* 887 */     aJava2IANAMap.put("CP284", "EBCDIC-CP-ES");
/* 888 */     aJava2IANAMap.put("CP285", "EBCDIC-CP-GB");
/* 889 */     aJava2IANAMap.put("CP290", "EBCDIC-JP-KANA");
/* 890 */     aJava2IANAMap.put("CP297", "EBCDIC-CP-FR");
/* 891 */     aJava2IANAMap.put("CP420", "EBCDIC-CP-AR1");
/* 892 */     aJava2IANAMap.put("CP424", "EBCDIC-CP-HE");
/* 893 */     aJava2IANAMap.put("CP437", "IBM437");
/* 894 */     aJava2IANAMap.put("CP500", "EBCDIC-CP-CH");
/* 895 */     aJava2IANAMap.put("CP775", "IBM775");
/* 896 */     aJava2IANAMap.put("CP850", "IBM850");
/* 897 */     aJava2IANAMap.put("CP852", "IBM852");
/* 898 */     aJava2IANAMap.put("CP855", "IBM855");
/* 899 */     aJava2IANAMap.put("CP857", "IBM857");
/* 900 */     aJava2IANAMap.put("CP858", "IBM00858");
/* 901 */     aJava2IANAMap.put("CP860", "IBM860");
/* 902 */     aJava2IANAMap.put("CP861", "IBM861");
/* 903 */     aJava2IANAMap.put("CP862", "IBM862");
/* 904 */     aJava2IANAMap.put("CP863", "IBM863");
/* 905 */     aJava2IANAMap.put("CP864", "IBM864");
/* 906 */     aJava2IANAMap.put("CP865", "IBM865");
/* 907 */     aJava2IANAMap.put("CP866", "IBM866");
/* 908 */     aJava2IANAMap.put("CP868", "IBM868");
/* 909 */     aJava2IANAMap.put("CP869", "IBM869");
/* 910 */     aJava2IANAMap.put("CP870", "EBCDIC-CP-ROECE");
/* 911 */     aJava2IANAMap.put("CP871", "EBCDIC-CP-IS");
/* 912 */     aJava2IANAMap.put("CP918", "EBCDIC-CP-AR2");
/* 913 */     aJava2IANAMap.put("CP924", "IBM00924");
/* 914 */     aJava2IANAMap.put("CP1026", "IBM1026");
/* 915 */     aJava2IANAMap.put("CP1140", "IBM01140");
/* 916 */     aJava2IANAMap.put("CP1141", "IBM01141");
/* 917 */     aJava2IANAMap.put("CP1142", "IBM01142");
/* 918 */     aJava2IANAMap.put("CP1143", "IBM01143");
/* 919 */     aJava2IANAMap.put("CP1144", "IBM01144");
/* 920 */     aJava2IANAMap.put("CP1145", "IBM01145");
/* 921 */     aJava2IANAMap.put("CP1146", "IBM01146");
/* 922 */     aJava2IANAMap.put("CP1147", "IBM01147");
/* 923 */     aJava2IANAMap.put("CP1148", "IBM01148");
/* 924 */     aJava2IANAMap.put("CP1149", "IBM01149");
/* 925 */     aJava2IANAMap.put("EUCJIS", "EUC-JP");
/* 926 */     aJava2IANAMap.put("KS_C_5601-1987", "KS_C_5601-1987");
/* 927 */     aJava2IANAMap.put("GB2312", "GB2312");
/* 928 */     aJava2IANAMap.put("ISO2022KR", "ISO-2022-KR");
/* 929 */     aJava2IANAMap.put("ISO2022CN", "ISO-2022-CN");
/* 930 */     aJava2IANAMap.put("JIS", "ISO-2022-JP");
/* 931 */     aJava2IANAMap.put("KOI8_R", "KOI8-R");
/* 932 */     aJava2IANAMap.put("KSC5601", "EUC-KR");
/* 933 */     aJava2IANAMap.put("GB18030", "GB18030");
/* 934 */     aJava2IANAMap.put("GBK", "GBK");
/* 935 */     aJava2IANAMap.put("SJIS", "SHIFT_JIS");
/* 936 */     aJava2IANAMap.put("MS932", "WINDOWS-31J");
/* 937 */     aJava2IANAMap.put("UTF8", "UTF-8");
/* 938 */     aJava2IANAMap.put("Unicode", "UTF-16");
/* 939 */     aJava2IANAMap.put("UnicodeBig", "UTF-16BE");
/* 940 */     aJava2IANAMap.put("UnicodeLittle", "UTF-16LE");
/* 941 */     aJava2IANAMap.put("JIS0201", "X0201");
/* 942 */     aJava2IANAMap.put("JIS0208", "X0208");
/* 943 */     aJava2IANAMap.put("JIS0212", "ISO-IR-159");
/*     */ 
/*     */     
/* 946 */     aJava2IANAMap.put("CP1047", "IBM1047");
/*     */     
/* 948 */     fJava2IANAMap = Collections.unmodifiableMap(aIANA2JavaMap);
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
/*     */   public static String getIANA2JavaMapping(String ianaEncoding) {
/* 968 */     return fIANA2JavaMap.get(ianaEncoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJava2IANAMapping(String javaEncoding) {
/* 977 */     return fJava2IANAMap.get(javaEncoding);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/EncodingMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */