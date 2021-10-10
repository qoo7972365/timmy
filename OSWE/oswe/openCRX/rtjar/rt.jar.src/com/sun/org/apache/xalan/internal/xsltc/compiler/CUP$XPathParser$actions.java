/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.java_cup.internal.runtime.Symbol;
/*      */ import com.sun.java_cup.internal.runtime.lr_parser;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CUP$XPathParser$actions
/*      */ {
/*      */   private final XPathParser parser;
/*      */   
/*      */   CUP$XPathParser$actions(XPathParser parser) {
/* 1117 */     this.parser = parser; } public final Symbol CUP$XPathParser$do_action(int CUP$XPathParser$act_num, lr_parser CUP$XPathParser$parser, Stack CUP$XPathParser$stack, int CUP$XPathParser$top) throws Exception { Symbol CUP$XPathParser$result; QName qName2; Object object2; Expression expression3; QName qName1; Vector<Expression> vector4; Vector<Expression> vector3; Expression expression2; Integer integer2; Expression expression1; Vector<Expression> vector2; Vector<Expression> vector1; Integer integer1; Object object1; StepPattern stepPattern2; RelativePathPattern relativePathPattern1; StepPattern stepPattern1; IdKeyPattern idKeyPattern1; Pattern pattern1; SyntaxTreeNode syntaxTreeNode1; Object RESULT; int qnameleft; int i15; int i14; int i13; int i12; int vnameleft; int i11; int argleft; int fnameleft; int varNameleft; int fcleft; int numleft; int stringleft; int i10; int vrleft; int primaryleft; int anleft; int abbrevleft; int i9; int ntestleft; int i8; int aalpleft; int i7; int arlpleft; int i6; int stepleft; int alpleft; int rlpleft; int fexpleft; int lpleft; int peleft; int i5; int i4; int ueleft; int i3; int meleft; int i2; int i1; int eeleft; int releft; int n; int m; int oeleft; int aeleft; int exleft; int eleft; int pleft; int qnleft; int k; int axisleft; int pipleft; int ntleft; int spleft; int j; int l1left; int lleft; int i; int ikpleft; int rppleft; int lppleft; int exprleft; int patternleft; int start_valleft; int qnameright; int i35; int i34; int i33; int i32; int vnameright; int i31; int argright; int fnameright; int varNameright; int fcright; int numright; int stringright; int i30; int vrright; int primaryright; int anright; int abbrevright; int i29; int ntestright; int i28; int aalpright; int i27; int arlpright; int i26; int stepright; int alpright; int rlpright; int fexpright; int lpright; int peright; int i25; int i24; int ueright; int i23; int meright; int i22; int i21; int eeright; int reright; int i20; int i19; int oeright; int aeright; int exright; int eright; int pright; int qnright; int i18; int axisright; int pipright; int ntright; int spright; int i17; int l1right; int lright; int i16; int ikpright; int rppright; int lppright; int exprright; int patternright; int start_valright; String qname; QName qName4; String str2; Object object4; Expression expression15; QName vname; QName qName3; Expression arg; QName fname; QName varName; Expression fc; Double double_; Long num; String string; Expression expression14; Expression vr; Expression primary; Integer an; Expression abbrev; Integer integer3; Object ntest; Expression expression13; Expression aalp; Expression expression12; Expression arlp; Expression expression11; Expression step; Expression alp; Expression rlp; Expression fexp; Expression lp; Expression pe; Expression expression10; Expression expression9; Expression ue; Expression expression8; Expression me; Expression expression7; Expression expression6; Expression ee; Expression re; Expression expression5; Expression expression4; Expression oe; Expression ae; Expression ex; Expression e; Expression p; QName qn; Object object3; Integer axis; StepPattern pip; Object nt; StepPattern sp; String str1; String l1; String l; RelativePathPattern relativePathPattern2; IdKeyPattern ikp; RelativePathPattern rpp; Pattern lpp; Expression expr; Pattern pattern; SyntaxTreeNode start_val; QName name; int i53; Vector<Expression> vector5; int arglleft; SyntaxTreeNode node; long value; String namespace; int i52; int i51; int i50; int nodeType; int i49; int i48; int restleft; int i47; int i46; int i45; int i44; int i43; int i42; int i41; Vector<Expression> temp; int i40; int i39; int ppleft; int i38; int l2left; int i37; int i36; Expression exp; int arglright; int index; int i70; int i69; int i68; Step step1; int i67; int i66; int restright; int i65; int i64; int i63; int i62; int i61; int i60; int i59; int i58; int i57; int ppright; int i56; int l2right; int i55; int i54; Vector<Predicate> predicates; Vector<Expression> vector9; Vector argl; Vector vector8; Object object6; Vector vector7; Expression expression23; Expression expression22; Expression rest; Expression expression21; Expression expression20; Expression expression19; Expression expression18; Expression expression17; Expression expression16; Vector<Expression> vector6; StepPattern stepPattern3; Object object5; Vector pp; RelativePathPattern relativePathPattern4; String l2; RelativePathPattern relativePathPattern3;
/*      */     Pattern pattern2;
/*      */     int i73;
/*      */     Step right;
/*      */     int i72;
/*      */     int i71;
/*      */     int i76;
/*      */     int i75;
/*      */     Step step2;
/*      */     int i74;
/*      */     Vector vector11;
/*      */     int type;
/*      */     FilterParentPath fpp;
/*      */     Vector vector10;
/*      */     Vector vector12;
/* 1132 */     switch (CUP$XPathParser$act_num) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 140:
/* 1137 */         qName2 = null;
/* 1138 */         qName2 = this.parser.getQNameIgnoreDefaultNs("id");
/* 1139 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1141 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 139:
/* 1146 */         qName2 = null;
/* 1147 */         qName2 = this.parser.getQNameIgnoreDefaultNs("self");
/* 1148 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1150 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 138:
/* 1155 */         qName2 = null;
/* 1156 */         qName2 = this.parser.getQNameIgnoreDefaultNs("preceding-sibling");
/* 1157 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1159 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 137:
/* 1164 */         qName2 = null;
/* 1165 */         qName2 = this.parser.getQNameIgnoreDefaultNs("preceding");
/* 1166 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1168 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 136:
/* 1173 */         qName2 = null;
/* 1174 */         qName2 = this.parser.getQNameIgnoreDefaultNs("parent");
/* 1175 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1177 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 135:
/* 1182 */         qName2 = null;
/* 1183 */         qName2 = this.parser.getQNameIgnoreDefaultNs("namespace");
/* 1184 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1186 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 134:
/* 1191 */         qName2 = null;
/* 1192 */         qName2 = this.parser.getQNameIgnoreDefaultNs("following-sibling");
/* 1193 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1195 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 133:
/* 1200 */         qName2 = null;
/* 1201 */         qName2 = this.parser.getQNameIgnoreDefaultNs("following");
/* 1202 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1204 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 132:
/* 1209 */         qName2 = null;
/* 1210 */         qName2 = this.parser.getQNameIgnoreDefaultNs("decendant-or-self");
/* 1211 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1213 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 131:
/* 1218 */         qName2 = null;
/* 1219 */         qName2 = this.parser.getQNameIgnoreDefaultNs("decendant");
/* 1220 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1222 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 130:
/* 1227 */         qName2 = null;
/* 1228 */         qName2 = this.parser.getQNameIgnoreDefaultNs("child");
/* 1229 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1231 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 129:
/* 1236 */         qName2 = null;
/* 1237 */         qName2 = this.parser.getQNameIgnoreDefaultNs("attribute");
/* 1238 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1240 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 128:
/* 1245 */         qName2 = null;
/* 1246 */         qName2 = this.parser.getQNameIgnoreDefaultNs("ancestor-or-self");
/* 1247 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1249 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 127:
/* 1254 */         qName2 = null;
/* 1255 */         qName2 = this.parser.getQNameIgnoreDefaultNs("child");
/* 1256 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1258 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 126:
/* 1263 */         qName2 = null;
/* 1264 */         qName2 = this.parser.getQNameIgnoreDefaultNs("key");
/* 1265 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1267 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 125:
/* 1272 */         qName2 = null;
/* 1273 */         qName2 = this.parser.getQNameIgnoreDefaultNs("mod");
/* 1274 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1276 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 124:
/* 1281 */         qName2 = null;
/* 1282 */         qName2 = this.parser.getQNameIgnoreDefaultNs("div");
/* 1283 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1285 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 123:
/* 1290 */         qName2 = null;
/* 1291 */         qnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1292 */         qnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1293 */         qname = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1294 */         qName2 = this.parser.getQNameIgnoreDefaultNs(qname);
/* 1295 */         CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName2);
/*      */         
/* 1297 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 122:
/* 1302 */         object2 = null;
/* 1303 */         i15 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1304 */         i35 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1305 */         qName4 = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1306 */         object2 = qName4;
/* 1307 */         CUP$XPathParser$result = new Symbol(26, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1309 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 121:
/* 1314 */         object2 = null;
/* 1315 */         object2 = null;
/* 1316 */         CUP$XPathParser$result = new Symbol(26, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1318 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 120:
/* 1323 */         object2 = null;
/* 1324 */         object2 = new Integer(7);
/* 1325 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1327 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 119:
/* 1332 */         object2 = null;
/* 1333 */         i14 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1334 */         i34 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1335 */         str2 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*      */         
/* 1337 */         name = this.parser.getQNameIgnoreDefaultNs("name");
/* 1338 */         exp = new EqualityExpr(0, new NameCall(name), new LiteralExpr(str2));
/*      */ 
/*      */         
/* 1341 */         predicates = new Vector();
/* 1342 */         predicates.addElement(new Predicate(exp));
/* 1343 */         object2 = new Step(3, 7, predicates);
/*      */         
/* 1345 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1347 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 118:
/* 1352 */         object2 = null;
/* 1353 */         object2 = new Integer(8);
/* 1354 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1356 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 117:
/* 1361 */         object2 = null;
/* 1362 */         object2 = new Integer(3);
/* 1363 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1365 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 116:
/* 1370 */         object2 = null;
/* 1371 */         object2 = new Integer(-1);
/* 1372 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1374 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 115:
/* 1379 */         object2 = null;
/* 1380 */         i13 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1381 */         i33 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1382 */         object4 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1383 */         object2 = object4;
/* 1384 */         CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object2);
/*      */         
/* 1386 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 114:
/* 1391 */         expression3 = null;
/* 1392 */         i12 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1393 */         i32 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1394 */         expression15 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1395 */         expression3 = expression15;
/* 1396 */         CUP$XPathParser$result = new Symbol(3, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression3);
/*      */         
/* 1398 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 113:
/* 1403 */         qName1 = null;
/* 1404 */         vnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1405 */         vnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1406 */         vname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1408 */         qName1 = vname;
/*      */         
/* 1410 */         CUP$XPathParser$result = new Symbol(39, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName1);
/*      */         
/* 1412 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 112:
/* 1417 */         qName1 = null;
/* 1418 */         i11 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1419 */         i31 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1420 */         qName3 = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1422 */         qName1 = qName3;
/*      */         
/* 1424 */         CUP$XPathParser$result = new Symbol(38, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, qName1);
/*      */         
/* 1426 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 111:
/* 1431 */         vector4 = null;
/* 1432 */         argleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1433 */         argright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1434 */         arg = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1435 */         i53 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1436 */         arglright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1437 */         vector9 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1438 */         vector9.insertElementAt(arg, 0); vector4 = vector9;
/* 1439 */         CUP$XPathParser$result = new Symbol(36, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, vector4);
/*      */         
/* 1441 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 110:
/* 1446 */         vector3 = null;
/* 1447 */         argleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1448 */         argright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1449 */         arg = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1451 */         vector5 = new Vector();
/* 1452 */         vector5.addElement(arg);
/* 1453 */         vector3 = vector5;
/*      */         
/* 1455 */         CUP$XPathParser$result = new Symbol(36, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, vector3);
/*      */         
/* 1457 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 109:
/* 1462 */         expression2 = null;
/* 1463 */         fnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left;
/* 1464 */         fnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).right;
/* 1465 */         fname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).value;
/* 1466 */         arglleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1467 */         arglright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1468 */         argl = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*      */         
/* 1470 */         if (fname == this.parser.getQNameIgnoreDefaultNs("concat")) {
/* 1471 */           expression2 = new ConcatCall(fname, argl);
/*      */         }
/* 1473 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("number")) {
/* 1474 */           expression2 = new NumberCall(fname, argl);
/*      */         }
/* 1476 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("document")) {
/* 1477 */           this.parser.setMultiDocument(true);
/* 1478 */           expression2 = new DocumentCall(fname, argl);
/*      */         }
/* 1480 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("string")) {
/* 1481 */           expression2 = new StringCall(fname, argl);
/*      */         }
/* 1483 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("boolean")) {
/* 1484 */           expression2 = new BooleanCall(fname, argl);
/*      */         }
/* 1486 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("name")) {
/* 1487 */           expression2 = new NameCall(fname, argl);
/*      */         }
/* 1489 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("generate-id")) {
/* 1490 */           expression2 = new GenerateIdCall(fname, argl);
/*      */         }
/* 1492 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("not")) {
/* 1493 */           expression2 = new NotCall(fname, argl);
/*      */         }
/* 1495 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("format-number")) {
/* 1496 */           expression2 = new FormatNumberCall(fname, argl);
/*      */         }
/* 1498 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("unparsed-entity-uri")) {
/* 1499 */           expression2 = new UnparsedEntityUriCall(fname, argl);
/*      */         }
/* 1501 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("key")) {
/* 1502 */           expression2 = new KeyCall(fname, argl);
/*      */         }
/* 1504 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("id")) {
/* 1505 */           expression2 = new KeyCall(fname, argl);
/* 1506 */           this.parser.setHasIdCall(true);
/*      */         }
/* 1508 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("ceiling")) {
/* 1509 */           expression2 = new CeilingCall(fname, argl);
/*      */         }
/* 1511 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("round")) {
/* 1512 */           expression2 = new RoundCall(fname, argl);
/*      */         }
/* 1514 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("floor")) {
/* 1515 */           expression2 = new FloorCall(fname, argl);
/*      */         }
/* 1517 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("contains")) {
/* 1518 */           expression2 = new ContainsCall(fname, argl);
/*      */         }
/* 1520 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("string-length")) {
/* 1521 */           expression2 = new StringLengthCall(fname, argl);
/*      */         }
/* 1523 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("starts-with")) {
/* 1524 */           expression2 = new StartsWithCall(fname, argl);
/*      */         }
/* 1526 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("function-available")) {
/* 1527 */           expression2 = new FunctionAvailableCall(fname, argl);
/*      */         }
/* 1529 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("element-available")) {
/* 1530 */           expression2 = new ElementAvailableCall(fname, argl);
/*      */         }
/* 1532 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("local-name")) {
/* 1533 */           expression2 = new LocalNameCall(fname, argl);
/*      */         }
/* 1535 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("lang")) {
/* 1536 */           expression2 = new LangCall(fname, argl);
/*      */         }
/* 1538 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("namespace-uri")) {
/* 1539 */           expression2 = new NamespaceUriCall(fname, argl);
/*      */         }
/* 1541 */         else if (fname == this.parser.getQName("http://xml.apache.org/xalan/xsltc", "xsltc", "cast")) {
/* 1542 */           expression2 = new CastCall(fname, argl);
/*      */         
/*      */         }
/* 1545 */         else if (fname.getLocalPart().equals("nodeset") || fname.getLocalPart().equals("node-set")) {
/* 1546 */           this.parser.setCallsNodeset(true);
/* 1547 */           expression2 = new FunctionCall(fname, argl);
/*      */         } else {
/*      */           
/* 1550 */           expression2 = new FunctionCall(fname, argl);
/*      */         } 
/*      */         
/* 1553 */         CUP$XPathParser$result = new Symbol(16, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1555 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 108:
/* 1560 */         expression2 = null;
/* 1561 */         fnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1562 */         fnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1563 */         fname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*      */ 
/*      */         
/* 1566 */         if (fname == this.parser.getQNameIgnoreDefaultNs("current")) {
/* 1567 */           expression2 = new CurrentCall(fname);
/*      */         }
/* 1569 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("number")) {
/* 1570 */           expression2 = new NumberCall(fname, XPathParser.EmptyArgs);
/*      */         }
/* 1572 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("string")) {
/* 1573 */           expression2 = new StringCall(fname, XPathParser.EmptyArgs);
/*      */         }
/* 1575 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("concat")) {
/* 1576 */           expression2 = new ConcatCall(fname, XPathParser.EmptyArgs);
/*      */         }
/* 1578 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("true")) {
/* 1579 */           expression2 = new BooleanExpr(true);
/*      */         }
/* 1581 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("false")) {
/* 1582 */           expression2 = new BooleanExpr(false);
/*      */         }
/* 1584 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("name")) {
/* 1585 */           expression2 = new NameCall(fname);
/*      */         }
/* 1587 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("generate-id")) {
/* 1588 */           expression2 = new GenerateIdCall(fname, XPathParser.EmptyArgs);
/*      */         }
/* 1590 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("string-length")) {
/* 1591 */           expression2 = new StringLengthCall(fname, XPathParser.EmptyArgs);
/*      */         }
/* 1593 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("position")) {
/* 1594 */           expression2 = new PositionCall(fname);
/*      */         }
/* 1596 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("last")) {
/* 1597 */           expression2 = new LastCall(fname);
/*      */         }
/* 1599 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("local-name")) {
/* 1600 */           expression2 = new LocalNameCall(fname);
/*      */         }
/* 1602 */         else if (fname == this.parser.getQNameIgnoreDefaultNs("namespace-uri")) {
/* 1603 */           expression2 = new NamespaceUriCall(fname);
/*      */         } else {
/*      */           
/* 1606 */           expression2 = new FunctionCall(fname, XPathParser.EmptyArgs);
/*      */         } 
/*      */         
/* 1609 */         CUP$XPathParser$result = new Symbol(16, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1611 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 107:
/* 1616 */         expression2 = null;
/* 1617 */         varNameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1618 */         varNameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1619 */         varName = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */ 
/*      */ 
/*      */         
/* 1623 */         node = this.parser.lookupName(varName);
/*      */         
/* 1625 */         if (node != null) {
/* 1626 */           if (node instanceof Variable) {
/* 1627 */             expression2 = new VariableRef((Variable)node);
/*      */           }
/* 1629 */           else if (node instanceof Param) {
/* 1630 */             expression2 = new ParameterRef((Param)node);
/*      */           } else {
/*      */             
/* 1633 */             expression2 = new UnresolvedRef(varName);
/*      */           } 
/*      */         }
/*      */         
/* 1637 */         if (node == null) {
/* 1638 */           expression2 = new UnresolvedRef(varName);
/*      */         }
/*      */         
/* 1641 */         CUP$XPathParser$result = new Symbol(15, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1643 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 106:
/* 1648 */         expression2 = null;
/* 1649 */         fcleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1650 */         fcright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1651 */         fc = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1652 */         expression2 = fc;
/* 1653 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1655 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 105:
/* 1660 */         expression2 = null;
/* 1661 */         numleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1662 */         numright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1663 */         double_ = (Double)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1664 */         expression2 = new RealExpr(double_.doubleValue());
/* 1665 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1667 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 104:
/* 1672 */         expression2 = null;
/* 1673 */         numleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1674 */         numright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1675 */         num = (Long)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1677 */         value = num.longValue();
/* 1678 */         if (value < -2147483648L || value > 2147483647L) {
/* 1679 */           expression2 = new RealExpr(value);
/*      */         
/*      */         }
/* 1682 */         else if (num.doubleValue() == 0.0D) {
/* 1683 */           expression2 = new RealExpr(num.doubleValue());
/* 1684 */         } else if (num.intValue() == 0) {
/* 1685 */           expression2 = new IntExpr(num.intValue());
/* 1686 */         } else if (num.doubleValue() == 0.0D) {
/* 1687 */           expression2 = new RealExpr(num.doubleValue());
/*      */         } else {
/* 1689 */           expression2 = new IntExpr(num.intValue());
/*      */         } 
/*      */         
/* 1692 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1694 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 103:
/* 1699 */         expression2 = null;
/* 1700 */         stringleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1701 */         stringright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1702 */         string = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1710 */         namespace = null;
/* 1711 */         index = string.lastIndexOf(':');
/*      */         
/* 1713 */         if (index > 0) {
/* 1714 */           String prefix = string.substring(0, index);
/* 1715 */           namespace = this.parser._symbolTable.lookupNamespace(prefix);
/*      */         } 
/* 1717 */         expression2 = (namespace == null) ? new LiteralExpr(string) : new LiteralExpr(string, namespace);
/*      */ 
/*      */         
/* 1720 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1722 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 102:
/* 1727 */         expression2 = null;
/* 1728 */         i10 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1729 */         i30 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1730 */         expression14 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1731 */         expression2 = expression14;
/* 1732 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1734 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 101:
/* 1739 */         expression2 = null;
/* 1740 */         vrleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1741 */         vrright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1742 */         vr = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1743 */         expression2 = vr;
/* 1744 */         CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1746 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 100:
/* 1751 */         expression2 = null;
/* 1752 */         primaryleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1753 */         primaryright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1754 */         primary = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1755 */         i52 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1756 */         i70 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1757 */         vector8 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1758 */         expression2 = new FilterExpr(primary, vector8);
/* 1759 */         CUP$XPathParser$result = new Symbol(6, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1761 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 99:
/* 1766 */         expression2 = null;
/* 1767 */         primaryleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1768 */         primaryright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1769 */         primary = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1770 */         expression2 = primary;
/* 1771 */         CUP$XPathParser$result = new Symbol(6, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1773 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 98:
/* 1778 */         expression2 = null;
/* 1779 */         expression2 = new Step(10, -1, null);
/* 1780 */         CUP$XPathParser$result = new Symbol(20, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1782 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 97:
/* 1787 */         expression2 = null;
/* 1788 */         expression2 = new Step(13, -1, null);
/* 1789 */         CUP$XPathParser$result = new Symbol(20, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression2);
/*      */         
/* 1791 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 96:
/* 1796 */         integer2 = null;
/* 1797 */         integer2 = new Integer(13);
/* 1798 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1800 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 95:
/* 1805 */         integer2 = null;
/* 1806 */         integer2 = new Integer(12);
/* 1807 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1809 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 94:
/* 1814 */         integer2 = null;
/* 1815 */         integer2 = new Integer(11);
/* 1816 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1818 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 93:
/* 1823 */         integer2 = null;
/* 1824 */         integer2 = new Integer(10);
/* 1825 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1827 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 92:
/* 1832 */         integer2 = null;
/* 1833 */         integer2 = new Integer(9);
/* 1834 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1836 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 91:
/* 1841 */         integer2 = null;
/* 1842 */         integer2 = new Integer(7);
/* 1843 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1845 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 90:
/* 1850 */         integer2 = null;
/* 1851 */         integer2 = new Integer(6);
/* 1852 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1854 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 89:
/* 1859 */         integer2 = null;
/* 1860 */         integer2 = new Integer(5);
/* 1861 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1863 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 88:
/* 1868 */         integer2 = null;
/* 1869 */         integer2 = new Integer(4);
/* 1870 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1872 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 87:
/* 1877 */         integer2 = null;
/* 1878 */         integer2 = new Integer(3);
/* 1879 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1881 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 86:
/* 1886 */         integer2 = null;
/* 1887 */         integer2 = new Integer(2);
/* 1888 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1890 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 85:
/* 1895 */         integer2 = null;
/* 1896 */         integer2 = new Integer(1);
/* 1897 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1899 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 84:
/* 1904 */         integer2 = null;
/* 1905 */         integer2 = new Integer(0);
/* 1906 */         CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1908 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 83:
/* 1913 */         integer2 = null;
/* 1914 */         integer2 = new Integer(2);
/* 1915 */         CUP$XPathParser$result = new Symbol(41, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1917 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 82:
/* 1922 */         integer2 = null;
/* 1923 */         anleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1924 */         anright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1925 */         an = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1926 */         integer2 = an;
/* 1927 */         CUP$XPathParser$result = new Symbol(41, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer2);
/*      */         
/* 1929 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 81:
/* 1934 */         expression1 = null;
/* 1935 */         abbrevleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1936 */         abbrevright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1937 */         abbrev = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1938 */         expression1 = abbrev;
/* 1939 */         CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 1941 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 80:
/* 1946 */         expression1 = null;
/* 1947 */         i9 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1948 */         i29 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1949 */         integer3 = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1950 */         i51 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1951 */         i69 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1952 */         object6 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1954 */         expression1 = new Step(integer3.intValue(), this.parser.findNodeType(integer3.intValue(), object6), null);
/*      */ 
/*      */         
/* 1957 */         CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 1959 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 79:
/* 1964 */         expression1 = null;
/* 1965 */         i9 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1966 */         i29 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1967 */         integer3 = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1968 */         i51 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1969 */         i69 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1970 */         object6 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1971 */         i73 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1972 */         i76 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1973 */         vector11 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1975 */         expression1 = new Step(integer3.intValue(), this.parser.findNodeType(integer3.intValue(), object6), vector11);
/*      */ 
/*      */         
/* 1978 */         CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 1980 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 78:
/* 1985 */         expression1 = null;
/* 1986 */         ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1987 */         ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1988 */         ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1989 */         i50 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1990 */         i68 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1991 */         vector7 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 1993 */         if (ntest instanceof Step) {
/* 1994 */           Step step3 = (Step)ntest;
/* 1995 */           step3.addPredicates(vector7);
/* 1996 */           expression1 = (Step)ntest;
/*      */         }
/*      */         else {
/*      */           
/* 2000 */           expression1 = new Step(3, this.parser.findNodeType(3, ntest), vector7);
/*      */         } 
/*      */         
/* 2003 */         CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2005 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 77:
/* 2010 */         expression1 = null;
/* 2011 */         ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2012 */         ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2013 */         ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2015 */         if (ntest instanceof Step) {
/* 2016 */           expression1 = (Step)ntest;
/*      */         }
/*      */         else {
/*      */           
/* 2020 */           expression1 = new Step(3, this.parser.findNodeType(3, ntest), null);
/*      */         } 
/*      */ 
/*      */         
/* 2024 */         CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2026 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 76:
/* 2031 */         expression1 = null;
/* 2032 */         i8 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2033 */         i28 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2034 */         expression13 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2040 */         nodeType = -1;
/* 2041 */         if (expression13 instanceof Step && this.parser
/* 2042 */           .isElementAxis(((Step)expression13).getAxis()))
/*      */         {
/* 2044 */           nodeType = 1;
/*      */         }
/* 2046 */         step1 = new Step(5, nodeType, null);
/* 2047 */         expression1 = new AbsoluteLocationPath(this.parser.insertStep(step1, (RelativeLocationPath)expression13));
/*      */ 
/*      */         
/* 2050 */         CUP$XPathParser$result = new Symbol(24, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2052 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 75:
/* 2057 */         expression1 = null;
/* 2058 */         i8 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2059 */         i28 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2060 */         expression13 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2061 */         i49 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2062 */         i67 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2063 */         expression23 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2065 */         right = (Step)expression23;
/* 2066 */         i75 = right.getAxis();
/* 2067 */         type = right.getNodeType();
/* 2068 */         vector12 = right.getPredicates();
/*      */         
/* 2070 */         if (i75 == 3 && type != 2) {
/*      */           
/* 2072 */           if (vector12 == null) {
/* 2073 */             right.setAxis(4);
/* 2074 */             if (expression13 instanceof Step && ((Step)expression13).isAbbreviatedDot()) {
/* 2075 */               expression1 = right;
/*      */             }
/*      */             else {
/*      */               
/* 2079 */               RelativeLocationPath left = (RelativeLocationPath)expression13;
/* 2080 */               expression1 = new ParentLocationPath(left, right);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 2085 */           else if (expression13 instanceof Step && ((Step)expression13).isAbbreviatedDot()) {
/* 2086 */             Step left = new Step(5, 1, null);
/*      */             
/* 2088 */             expression1 = new ParentLocationPath(left, right);
/*      */           }
/*      */           else {
/*      */             
/* 2092 */             RelativeLocationPath left = (RelativeLocationPath)expression13;
/* 2093 */             Step mid = new Step(5, 1, null);
/*      */             
/* 2095 */             ParentLocationPath ppl = new ParentLocationPath(mid, right);
/* 2096 */             expression1 = new ParentLocationPath(left, ppl);
/*      */           }
/*      */         
/*      */         }
/* 2100 */         else if (i75 == 2 || type == 2) {
/*      */           
/* 2102 */           RelativeLocationPath left = (RelativeLocationPath)expression13;
/* 2103 */           Step middle = new Step(5, 1, null);
/*      */           
/* 2105 */           ParentLocationPath ppl = new ParentLocationPath(middle, right);
/* 2106 */           expression1 = new ParentLocationPath(left, ppl);
/*      */         }
/*      */         else {
/*      */           
/* 2110 */           RelativeLocationPath left = (RelativeLocationPath)expression13;
/* 2111 */           Step middle = new Step(5, -1, null);
/*      */           
/* 2113 */           ParentLocationPath ppl = new ParentLocationPath(middle, right);
/* 2114 */           expression1 = new ParentLocationPath(left, ppl);
/*      */         } 
/*      */         
/* 2117 */         CUP$XPathParser$result = new Symbol(22, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2119 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 74:
/* 2124 */         expression1 = null;
/* 2125 */         aalpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2126 */         aalpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2127 */         aalp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2128 */         expression1 = aalp;
/* 2129 */         CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2131 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 73:
/* 2136 */         expression1 = null;
/* 2137 */         i7 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2138 */         i27 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2139 */         expression12 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2140 */         expression1 = new AbsoluteLocationPath(expression12);
/* 2141 */         CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2143 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 72:
/* 2148 */         expression1 = null;
/* 2149 */         expression1 = new AbsoluteLocationPath();
/* 2150 */         CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2152 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 71:
/* 2157 */         expression1 = null;
/* 2158 */         arlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2159 */         arlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2160 */         arlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2161 */         expression1 = arlp;
/* 2162 */         CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2164 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 70:
/* 2169 */         expression1 = null;
/* 2170 */         i6 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2171 */         i26 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2172 */         expression11 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2173 */         i49 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2174 */         i67 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2175 */         expression23 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2177 */         if (expression11 instanceof Step && ((Step)expression11).isAbbreviatedDot()) {
/* 2178 */           expression1 = expression23;
/*      */         }
/* 2180 */         else if (((Step)expression23).isAbbreviatedDot()) {
/* 2181 */           expression1 = expression11;
/*      */         } else {
/*      */           
/* 2184 */           expression1 = new ParentLocationPath((RelativeLocationPath)expression11, expression23);
/*      */         } 
/*      */ 
/*      */         
/* 2188 */         CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2190 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 69:
/* 2195 */         expression1 = null;
/* 2196 */         stepleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2197 */         stepright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2198 */         step = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2199 */         expression1 = step;
/* 2200 */         CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2202 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 68:
/* 2207 */         expression1 = null;
/* 2208 */         alpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2209 */         alpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2210 */         alp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2211 */         expression1 = alp;
/* 2212 */         CUP$XPathParser$result = new Symbol(4, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2214 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 67:
/* 2219 */         expression1 = null;
/* 2220 */         rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2221 */         rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2222 */         rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2223 */         expression1 = rlp;
/* 2224 */         CUP$XPathParser$result = new Symbol(4, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2226 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 66:
/* 2231 */         expression1 = null;
/* 2232 */         fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2233 */         fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2234 */         fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2235 */         i48 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2236 */         i66 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2237 */         expression22 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2243 */         i72 = -1;
/* 2244 */         if (expression22 instanceof Step && this.parser
/* 2245 */           .isElementAxis(((Step)expression22).getAxis()))
/*      */         {
/* 2247 */           i72 = 1;
/*      */         }
/* 2249 */         step2 = new Step(5, i72, null);
/* 2250 */         fpp = new FilterParentPath(fexp, step2);
/* 2251 */         fpp = new FilterParentPath(fpp, expression22);
/* 2252 */         if (!(fexp instanceof KeyCall)) {
/* 2253 */           fpp.setDescendantAxis();
/*      */         }
/* 2255 */         expression1 = fpp;
/*      */         
/* 2257 */         CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2259 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 65:
/* 2264 */         expression1 = null;
/* 2265 */         fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2266 */         fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2267 */         fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2268 */         i48 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2269 */         i66 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2270 */         expression22 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2271 */         expression1 = new FilterParentPath(fexp, expression22);
/* 2272 */         CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2274 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 64:
/* 2279 */         expression1 = null;
/* 2280 */         fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2281 */         fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2282 */         fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2283 */         expression1 = fexp;
/* 2284 */         CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2286 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 63:
/* 2291 */         expression1 = null;
/* 2292 */         lpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2293 */         lpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2294 */         lp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2295 */         expression1 = lp;
/* 2296 */         CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2298 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 62:
/* 2303 */         expression1 = null;
/* 2304 */         peleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2305 */         peright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2306 */         pe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2307 */         restleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2308 */         restright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2309 */         rest = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2310 */         expression1 = new UnionPathExpr(pe, rest);
/* 2311 */         CUP$XPathParser$result = new Symbol(18, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2313 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 61:
/* 2318 */         expression1 = null;
/* 2319 */         peleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2320 */         peright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2321 */         pe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2322 */         expression1 = pe;
/* 2323 */         CUP$XPathParser$result = new Symbol(18, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2325 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 60:
/* 2330 */         expression1 = null;
/* 2331 */         i5 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2332 */         i25 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2333 */         expression10 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2334 */         expression1 = new UnaryOpExpr(expression10);
/* 2335 */         CUP$XPathParser$result = new Symbol(14, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2337 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 59:
/* 2342 */         expression1 = null;
/* 2343 */         i5 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2344 */         i25 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2345 */         expression10 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2346 */         expression1 = expression10;
/* 2347 */         CUP$XPathParser$result = new Symbol(14, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2349 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 58:
/* 2354 */         expression1 = null;
/* 2355 */         i4 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2356 */         i24 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2357 */         expression9 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2358 */         i47 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2359 */         i65 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2360 */         expression21 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2361 */         expression1 = new BinOpExpr(4, expression9, expression21);
/* 2362 */         CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2364 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 57:
/* 2369 */         expression1 = null;
/* 2370 */         i4 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2371 */         i24 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2372 */         expression9 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2373 */         i47 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2374 */         i65 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2375 */         expression21 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2376 */         expression1 = new BinOpExpr(3, expression9, expression21);
/* 2377 */         CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2379 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 56:
/* 2384 */         expression1 = null;
/* 2385 */         i4 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2386 */         i24 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2387 */         expression9 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2388 */         i47 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2389 */         i65 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2390 */         expression21 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2391 */         expression1 = new BinOpExpr(2, expression9, expression21);
/* 2392 */         CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2394 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 55:
/* 2399 */         expression1 = null;
/* 2400 */         ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2401 */         ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2402 */         ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2403 */         expression1 = ue;
/* 2404 */         CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2406 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 54:
/* 2411 */         expression1 = null;
/* 2412 */         i3 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2413 */         i23 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2414 */         expression8 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2415 */         i46 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2416 */         i64 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2417 */         expression20 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2418 */         expression1 = new BinOpExpr(1, expression8, expression20);
/* 2419 */         CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2421 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 53:
/* 2426 */         expression1 = null;
/* 2427 */         i3 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2428 */         i23 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2429 */         expression8 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2430 */         i46 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2431 */         i64 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2432 */         expression20 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2433 */         expression1 = new BinOpExpr(0, expression8, expression20);
/* 2434 */         CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2436 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 52:
/* 2441 */         expression1 = null;
/* 2442 */         meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2443 */         meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2444 */         me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2445 */         expression1 = me;
/* 2446 */         CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2448 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 51:
/* 2453 */         expression1 = null;
/* 2454 */         i2 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2455 */         i22 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2456 */         expression7 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2457 */         i45 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2458 */         i63 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2459 */         expression19 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2460 */         expression1 = new RelationalExpr(4, expression7, expression19);
/* 2461 */         CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2463 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 50:
/* 2468 */         expression1 = null;
/* 2469 */         i2 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2470 */         i22 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2471 */         expression7 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2472 */         i45 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2473 */         i63 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2474 */         expression19 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2475 */         expression1 = new RelationalExpr(5, expression7, expression19);
/* 2476 */         CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2478 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 49:
/* 2483 */         expression1 = null;
/* 2484 */         i2 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2485 */         i22 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2486 */         expression7 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2487 */         i45 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2488 */         i63 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2489 */         expression19 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2490 */         expression1 = new RelationalExpr(2, expression7, expression19);
/* 2491 */         CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2493 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 48:
/* 2498 */         expression1 = null;
/* 2499 */         i2 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2500 */         i22 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2501 */         expression7 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2502 */         i45 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2503 */         i63 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2504 */         expression19 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2505 */         expression1 = new RelationalExpr(3, expression7, expression19);
/* 2506 */         CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2508 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 47:
/* 2513 */         expression1 = null;
/* 2514 */         i1 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2515 */         i21 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2516 */         expression6 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2517 */         expression1 = expression6;
/* 2518 */         CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2520 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 46:
/* 2525 */         expression1 = null;
/* 2526 */         eeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2527 */         eeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2528 */         ee = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2529 */         i44 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2530 */         i62 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2531 */         expression18 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2532 */         expression1 = new EqualityExpr(1, ee, expression18);
/* 2533 */         CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2535 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 45:
/* 2540 */         expression1 = null;
/* 2541 */         eeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2542 */         eeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2543 */         ee = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2544 */         i44 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2545 */         i62 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2546 */         expression18 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2547 */         expression1 = new EqualityExpr(0, ee, expression18);
/* 2548 */         CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2550 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 44:
/* 2555 */         expression1 = null;
/* 2556 */         releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2557 */         reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2558 */         re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2559 */         expression1 = re;
/* 2560 */         CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2562 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 43:
/* 2567 */         expression1 = null;
/* 2568 */         n = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2569 */         i20 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2570 */         expression5 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2571 */         i43 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2572 */         i61 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2573 */         expression17 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2574 */         expression1 = new LogicalExpr(1, expression5, expression17);
/* 2575 */         CUP$XPathParser$result = new Symbol(9, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2577 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 42:
/* 2582 */         expression1 = null;
/* 2583 */         m = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2584 */         i19 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2585 */         expression4 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2586 */         expression1 = expression4;
/* 2587 */         CUP$XPathParser$result = new Symbol(9, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2589 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 41:
/* 2594 */         expression1 = null;
/* 2595 */         oeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2596 */         oeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2597 */         oe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2598 */         i42 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2599 */         i60 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2600 */         expression16 = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2601 */         expression1 = new LogicalExpr(0, oe, expression16);
/* 2602 */         CUP$XPathParser$result = new Symbol(8, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2604 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 40:
/* 2609 */         expression1 = null;
/* 2610 */         aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2611 */         aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2612 */         ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2613 */         expression1 = ae;
/* 2614 */         CUP$XPathParser$result = new Symbol(8, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2616 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 39:
/* 2621 */         expression1 = null;
/* 2622 */         exleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2623 */         exright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2624 */         ex = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2625 */         expression1 = ex;
/* 2626 */         CUP$XPathParser$result = new Symbol(2, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2628 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 38:
/* 2633 */         expression1 = null;
/* 2634 */         eleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2635 */         eright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2636 */         e = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*      */         
/* 2638 */         expression1 = new Predicate(e);
/*      */         
/* 2640 */         CUP$XPathParser$result = new Symbol(5, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, expression1);
/*      */         
/* 2642 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 37:
/* 2647 */         vector2 = null;
/* 2648 */         pleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2649 */         pright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2650 */         p = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2651 */         i41 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2652 */         i59 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2653 */         vector6 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2654 */         vector6.insertElementAt(p, 0); vector2 = vector6;
/* 2655 */         CUP$XPathParser$result = new Symbol(35, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, vector2);
/*      */         
/* 2657 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 36:
/* 2662 */         vector1 = null;
/* 2663 */         pleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2664 */         pright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2665 */         p = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2667 */         temp = new Vector();
/* 2668 */         temp.addElement(p);
/* 2669 */         vector1 = temp;
/*      */         
/* 2671 */         CUP$XPathParser$result = new Symbol(35, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, vector1);
/*      */         
/* 2673 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 35:
/* 2678 */         integer1 = null;
/* 2679 */         integer1 = new Integer(2);
/* 2680 */         CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer1);
/*      */         
/* 2682 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 34:
/* 2687 */         integer1 = null;
/* 2688 */         integer1 = new Integer(3);
/* 2689 */         CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer1);
/*      */         
/* 2691 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 33:
/* 2696 */         integer1 = null;
/* 2697 */         integer1 = new Integer(2);
/* 2698 */         CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, integer1);
/*      */         
/* 2700 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 32:
/* 2705 */         object1 = null;
/* 2706 */         qnleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2707 */         qnright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2708 */         qn = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2709 */         object1 = qn;
/* 2710 */         CUP$XPathParser$result = new Symbol(34, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2712 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 31:
/* 2717 */         object1 = null;
/* 2718 */         object1 = null;
/* 2719 */         CUP$XPathParser$result = new Symbol(34, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2721 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 30:
/* 2726 */         object1 = null;
/* 2727 */         object1 = new Integer(7);
/* 2728 */         CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2730 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 29:
/* 2735 */         object1 = null;
/* 2736 */         object1 = new Integer(8);
/* 2737 */         CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2739 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 28:
/* 2744 */         object1 = null;
/* 2745 */         object1 = new Integer(3);
/* 2746 */         CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2748 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 27:
/* 2753 */         object1 = null;
/* 2754 */         object1 = new Integer(-1);
/* 2755 */         CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2757 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 26:
/* 2762 */         object1 = null;
/* 2763 */         k = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2764 */         i18 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2765 */         object3 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2766 */         object1 = object3;
/* 2767 */         CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, object1);
/*      */         
/* 2769 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 25:
/* 2774 */         stepPattern2 = null;
/* 2775 */         axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2776 */         axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2777 */         axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2778 */         i40 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2779 */         i58 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2780 */         stepPattern3 = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2781 */         i71 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2782 */         i74 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2783 */         vector10 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */ 
/*      */         
/* 2786 */         stepPattern2 = stepPattern3.setPredicates(vector10);
/*      */         
/* 2788 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2790 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 24:
/* 2795 */         stepPattern2 = null;
/* 2796 */         axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2797 */         axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2798 */         axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2799 */         i40 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2800 */         i58 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2801 */         stepPattern3 = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2803 */         stepPattern2 = stepPattern3;
/*      */         
/* 2805 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2807 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 23:
/* 2812 */         stepPattern2 = null;
/* 2813 */         axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2814 */         axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2815 */         axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2816 */         i39 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2817 */         i57 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2818 */         object5 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2819 */         i71 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2820 */         i74 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2821 */         vector10 = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2823 */         stepPattern2 = this.parser.createStepPattern(axis.intValue(), object5, vector10);
/*      */         
/* 2825 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2827 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 22:
/* 2832 */         stepPattern2 = null;
/* 2833 */         axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2834 */         axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2835 */         axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2836 */         i39 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2837 */         i57 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2838 */         object5 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2840 */         stepPattern2 = this.parser.createStepPattern(axis.intValue(), object5, null);
/*      */         
/* 2842 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2844 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 21:
/* 2849 */         stepPattern2 = null;
/* 2850 */         pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2851 */         pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2852 */         pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2853 */         ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2854 */         ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2855 */         pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2856 */         stepPattern2 = pip.setPredicates(pp);
/* 2857 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2859 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 20:
/* 2864 */         stepPattern2 = null;
/* 2865 */         pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2866 */         pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2867 */         pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2868 */         stepPattern2 = pip;
/* 2869 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2871 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 19:
/* 2876 */         stepPattern2 = null;
/* 2877 */         ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2878 */         ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2879 */         nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2880 */         ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2881 */         ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2882 */         pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2884 */         stepPattern2 = this.parser.createStepPattern(3, nt, pp);
/*      */         
/* 2886 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2888 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 18:
/* 2893 */         stepPattern2 = null;
/* 2894 */         ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2895 */         ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2896 */         nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*      */         
/* 2898 */         stepPattern2 = this.parser.createStepPattern(3, nt, null);
/*      */         
/* 2900 */         CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern2);
/*      */         
/* 2902 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 17:
/* 2907 */         relativePathPattern1 = null;
/* 2908 */         spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2909 */         spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2910 */         sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2911 */         i38 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2912 */         i56 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2913 */         relativePathPattern4 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2914 */         relativePathPattern1 = new AncestorPattern(sp, relativePathPattern4);
/* 2915 */         CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, relativePathPattern1);
/*      */         
/* 2917 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 16:
/* 2922 */         relativePathPattern1 = null;
/* 2923 */         spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 2924 */         spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 2925 */         sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 2926 */         i38 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2927 */         i56 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2928 */         relativePathPattern4 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2929 */         relativePathPattern1 = new ParentPattern(sp, relativePathPattern4);
/* 2930 */         CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, relativePathPattern1);
/*      */         
/* 2932 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 15:
/* 2937 */         relativePathPattern1 = null;
/* 2938 */         spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2939 */         spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2940 */         sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2941 */         relativePathPattern1 = sp;
/* 2942 */         CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, relativePathPattern1);
/*      */         
/* 2944 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 14:
/* 2949 */         stepPattern1 = null;
/* 2950 */         j = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2951 */         i17 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2952 */         str1 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2953 */         stepPattern1 = new ProcessingInstructionPattern(str1);
/* 2954 */         CUP$XPathParser$result = new Symbol(30, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, stepPattern1);
/*      */         
/* 2956 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 13:
/* 2961 */         idKeyPattern1 = null;
/* 2962 */         l1left = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left;
/* 2963 */         l1right = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).right;
/* 2964 */         l1 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).value;
/* 2965 */         l2left = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2966 */         l2right = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2967 */         l2 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2968 */         idKeyPattern1 = new KeyPattern(l1, l2);
/* 2969 */         CUP$XPathParser$result = new Symbol(27, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 5)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, idKeyPattern1);
/*      */         
/* 2971 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/* 2976 */         idKeyPattern1 = null;
/* 2977 */         lleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 2978 */         lright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 2979 */         l = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 2980 */         idKeyPattern1 = new IdPattern(l);
/* 2981 */         this.parser.setHasIdCall(true);
/*      */         
/* 2983 */         CUP$XPathParser$result = new Symbol(27, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, idKeyPattern1);
/*      */         
/* 2985 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 2990 */         pattern1 = null;
/* 2991 */         i = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 2992 */         i16 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 2993 */         relativePathPattern2 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 2994 */         pattern1 = relativePathPattern2;
/* 2995 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 2997 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 3002 */         pattern1 = null;
/* 3003 */         i = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3004 */         i16 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3005 */         relativePathPattern2 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3006 */         pattern1 = new AncestorPattern(relativePathPattern2);
/* 3007 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3009 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 3014 */         pattern1 = null;
/* 3015 */         ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 3016 */         ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 3017 */         ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 3018 */         i37 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3019 */         i55 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3020 */         relativePathPattern3 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3021 */         pattern1 = new AncestorPattern(ikp, relativePathPattern3);
/* 3022 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3024 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 3029 */         pattern1 = null;
/* 3030 */         ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 3031 */         ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 3032 */         ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 3033 */         i37 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3034 */         i55 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3035 */         relativePathPattern3 = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3036 */         pattern1 = new ParentPattern(ikp, relativePathPattern3);
/* 3037 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3039 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 3044 */         pattern1 = null;
/* 3045 */         ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3046 */         ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3047 */         ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3048 */         pattern1 = ikp;
/* 3049 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3051 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 3056 */         pattern1 = null;
/* 3057 */         rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3058 */         rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3059 */         rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3060 */         pattern1 = new AbsolutePathPattern(rpp);
/* 3061 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3063 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 3068 */         pattern1 = null;
/* 3069 */         pattern1 = new AbsolutePathPattern(null);
/* 3070 */         CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3072 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 3077 */         pattern1 = null;
/* 3078 */         lppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 3079 */         lppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 3080 */         lpp = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 3081 */         i36 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3082 */         i54 = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3083 */         pattern2 = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3084 */         pattern1 = new AlternativePattern(lpp, pattern2);
/* 3085 */         CUP$XPathParser$result = new Symbol(28, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3087 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 3092 */         pattern1 = null;
/* 3093 */         lppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3094 */         lppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3095 */         lpp = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3096 */         pattern1 = lpp;
/* 3097 */         CUP$XPathParser$result = new Symbol(28, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, pattern1);
/*      */         
/* 3099 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 3104 */         syntaxTreeNode1 = null;
/* 3105 */         exprleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3106 */         exprright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3107 */         expr = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3108 */         syntaxTreeNode1 = expr;
/* 3109 */         CUP$XPathParser$result = new Symbol(1, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, syntaxTreeNode1);
/*      */         
/* 3111 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 3116 */         syntaxTreeNode1 = null;
/* 3117 */         patternleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 3118 */         patternright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 3119 */         pattern = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 3120 */         syntaxTreeNode1 = pattern;
/* 3121 */         CUP$XPathParser$result = new Symbol(1, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, syntaxTreeNode1);
/*      */         
/* 3123 */         return CUP$XPathParser$result;
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/* 3128 */         RESULT = null;
/* 3129 */         start_valleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 3130 */         start_valright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 3131 */         start_val = (SyntaxTreeNode)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 3132 */         RESULT = start_val;
/* 3133 */         CUP$XPathParser$result = new Symbol(0, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*      */ 
/*      */         
/* 3136 */         CUP$XPathParser$parser.done_parsing();
/* 3137 */         return CUP$XPathParser$result;
/*      */     } 
/*      */ 
/*      */     
/* 3141 */     throw new Exception("Invalid action number found in internal parse table"); }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/CUP$XPathParser$actions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */