/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLServerForFlash
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward getXML(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  79 */     PrintWriter out = response.getWriter();
/*     */     try
/*     */     {
/*  82 */       String width = request.getParameter("chartWidth");
/*  83 */       String height = request.getParameter("chartHeight");
/*  84 */       String value = request.getParameter("value");
/*  85 */       String upperLimit = request.getParameter("upperLimit");
/*  86 */       String units = request.getParameter("units");
/*  87 */       String metricname = request.getParameter("metricname");
/*  88 */       String isSemiDial = request.getParameter("isSemiDial");
/*  89 */       if (units == null)
/*     */       {
/*  91 */         units = "";
/*     */       }
/*  93 */       if (metricname == null)
/*     */       {
/*  95 */         metricname = "";
/*     */       }
/*     */       
/*  98 */       Properties chartProps = new Properties();
/*  99 */       if (isSemiDial != null)
/*     */       {
/* 101 */         chartProps.setProperty("isSemiDial", String.valueOf(isSemiDial));
/*     */       }
/*     */       else
/*     */       {
/* 105 */         chartProps.setProperty("isSemiDial", "false");
/*     */       }
/* 107 */       chartProps.setProperty("chartWidth", width);
/* 108 */       chartProps.setProperty("chartHeight", height);
/* 109 */       chartProps.setProperty("value", value);
/* 110 */       chartProps.setProperty("upperLimit", upperLimit);
/* 111 */       chartProps.setProperty("units", units);
/* 112 */       chartProps.setProperty("metricname", metricname);
/* 113 */       Document doc = getXMLDocumentForDial(chartProps);
/* 114 */       Source source = new DOMSource(doc);
/* 115 */       Result result = new StreamResult(out);
/* 116 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 117 */       transformer.setOutputProperty("method", "xml");
/* 118 */       transformer.setOutputProperty("encoding", "ISO-8859-1");
/* 119 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "20");
/* 120 */       transformer.setOutputProperty("indent", "yes");
/* 121 */       transformer.transform(source, result);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 125 */       ex.printStackTrace();
/*     */     }
/* 127 */     out.flush();
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   private void checkandSetDefaultPropsForDialGraph(Properties chartProps)
/*     */   {
/* 133 */     int chartHeight = 220;
/* 134 */     int chartWidth = 220;
/* 135 */     int value = 0;
/* 136 */     int upperLimit = 100;
/* 137 */     int criticalRange = 80;
/* 138 */     String units = "";
/* 139 */     String metricname = "";
/* 140 */     String tooltip = "";
/* 141 */     if ((chartProps.getProperty("chartHeight") != null) && (chartProps.getProperty("chartWidth") != null))
/*     */     {
/* 143 */       chartHeight = Integer.parseInt(chartProps.getProperty("chartHeight"));
/* 144 */       chartWidth = Integer.parseInt(chartProps.getProperty("chartWidth"));
/* 145 */       value = Integer.parseInt(chartProps.getProperty("value"));
/* 146 */       upperLimit = Integer.parseInt(chartProps.getProperty("upperLimit"));
/* 147 */       units = chartProps.getProperty("units");
/* 148 */       metricname = chartProps.getProperty("metricname");
/* 149 */       tooltip = "   " + metricname + " : " + value + " " + units;
/* 150 */       criticalRange = 80 * upperLimit / 100;
/* 151 */       chartProps.setProperty("color1_maxValue", String.valueOf(criticalRange));
/* 152 */       chartProps.setProperty("color2_minValue", String.valueOf(criticalRange));
/* 153 */       chartProps.setProperty("color2_maxValue", String.valueOf(upperLimit));
/* 154 */       chartProps.setProperty("upperLimit", String.valueOf(upperLimit));
/* 155 */       chartProps.setProperty("dial1_value", String.valueOf(value));
/* 156 */       chartProps.setProperty("dial1_toolText", "  " + String.valueOf(tooltip) + "  ");
/* 157 */       chartProps.setProperty("gaugeOriginX", String.valueOf(chartWidth / 2));
/* 158 */       chartProps.setProperty("gaugeOriginY", String.valueOf(chartHeight / 2));
/* 159 */       chartProps.setProperty("annotationGroup_xPos", String.valueOf(chartWidth / 2));
/* 160 */       chartProps.setProperty("annotationGroup_yPos", String.valueOf(chartHeight / 2));
/* 161 */       chartProps.setProperty("annotation1_radius", String.valueOf(chartWidth * 0.44D));
/* 162 */       chartProps.setProperty("annotation2_radius", String.valueOf(chartWidth * 0.3D));
/* 163 */       chartProps.setProperty("annotation3_radius", String.valueOf(chartWidth * 0.35D));
/* 164 */       chartProps.setProperty("pivotRadius", String.valueOf(chartWidth * 0.06D));
/* 165 */       chartProps.setProperty("gaugeOuterRadius", String.valueOf(chartWidth * 0.35D));
/* 166 */       chartProps.setProperty("gaugeInnerRadius", String.valueOf(chartWidth * 0.3D));
/*     */     }
/*     */     
/* 169 */     checkAndSetProperty(chartProps, "bgColor", "FFFFFF");
/* 170 */     checkAndSetProperty(chartProps, "upperLimit", "100");
/* 171 */     checkAndSetProperty(chartProps, "lowerLimit", "0");
/* 172 */     checkAndSetProperty(chartProps, "showLimits", "1");
/* 173 */     checkAndSetProperty(chartProps, "baseFontColor", "666666");
/* 174 */     checkAndSetProperty(chartProps, "majorTMNumber", "9");
/* 175 */     checkAndSetProperty(chartProps, "majorTMColor", "666666");
/* 176 */     checkAndSetProperty(chartProps, "majorTMHeight", "8");
/* 177 */     checkAndSetProperty(chartProps, "minorTMNumber", "4");
/* 178 */     checkAndSetProperty(chartProps, "minorTMColor", "666666");
/* 179 */     checkAndSetProperty(chartProps, "minorTMHeight", "3");
/* 180 */     checkAndSetProperty(chartProps, "pivotRadius", "20");
/* 181 */     checkAndSetProperty(chartProps, "showGaugeBorder", "0");
/* 182 */     checkAndSetProperty(chartProps, "gaugeOuterRadius", "80");
/* 183 */     checkAndSetProperty(chartProps, "gaugeInnerRadius", "70");
/* 184 */     checkAndSetProperty(chartProps, "gaugeOriginX", "110");
/* 185 */     checkAndSetProperty(chartProps, "gaugeOriginY", "110");
/* 186 */     checkAndSetProperty(chartProps, "gaugeScaleAngle", "320");
/* 187 */     checkAndSetProperty(chartProps, "displayValueDistance", "10");
/* 188 */     checkAndSetProperty(chartProps, "placeValuesInside", "1");
/* 189 */     checkAndSetProperty(chartProps, "gaugeFillMix", "");
/* 190 */     checkAndSetProperty(chartProps, "pivotFillMix", "{F0EFEA}, {BEBCB0}");
/* 191 */     checkAndSetProperty(chartProps, "pivotBorderColor", "BEBCB0");
/* 192 */     checkAndSetProperty(chartProps, "pivotfillRatio", "80,20");
/* 193 */     checkAndSetProperty(chartProps, "showShadow", "0");
/* 194 */     checkAndSetProperty(chartProps, "showBorder", "0");
/*     */     
/*     */ 
/* 197 */     checkAndSetProperty(chartProps, "dial1_value", "0");
/* 198 */     checkAndSetProperty(chartProps, "dial1_bordercolor", "FFFFFF");
/* 199 */     checkAndSetProperty(chartProps, "dial1_bgColor", "bebcb0, f0efea, bebcb0");
/* 200 */     checkAndSetProperty(chartProps, "dial1_borderAlpha", "0");
/* 201 */     checkAndSetProperty(chartProps, "dial1_baseWidth", "10");
/* 202 */     checkAndSetProperty(chartProps, "dial1_topWidth", "3");
/*     */     
/* 204 */     checkAndSetProperty(chartProps, "annotationGroup_xPos", "110");
/* 205 */     checkAndSetProperty(chartProps, "annotationGroup_yPos", "110");
/* 206 */     checkAndSetProperty(chartProps, "annotationGroup_fillRatio", "10,125,254");
/* 207 */     checkAndSetProperty(chartProps, "annotationGroup_fillPattern", "radial");
/*     */     
/*     */ 
/*     */ 
/* 211 */     checkAndSetProperty(chartProps, "annotation1_type", "circle");
/* 212 */     checkAndSetProperty(chartProps, "annotation1_xPos", "0");
/* 213 */     checkAndSetProperty(chartProps, "annotation1_yPos", "0");
/* 214 */     checkAndSetProperty(chartProps, "annotation1_radius", "97");
/* 215 */     checkAndSetProperty(chartProps, "annotation1_borderColor", "bebcb0");
/* 216 */     checkAndSetProperty(chartProps, "annotation1_fillAsGradient", "1");
/* 217 */     checkAndSetProperty(chartProps, "annotation1_fillColor", "f0efea, bebcb0");
/* 218 */     checkAndSetProperty(chartProps, "annotation1_fillRatio", "85,15");
/* 219 */     checkAndSetProperty(chartProps, "annotation2_type", "circle");
/* 220 */     checkAndSetProperty(chartProps, "annotation2_xPos", "0");
/* 221 */     checkAndSetProperty(chartProps, "annotation2_yPos", "0");
/* 222 */     checkAndSetProperty(chartProps, "annotation2_radius", "80");
/* 223 */     checkAndSetProperty(chartProps, "annotation2_fillColor", "bebcb0, f0efea");
/* 224 */     checkAndSetProperty(chartProps, "annotation2_fillRatio", "85,15");
/* 225 */     checkAndSetProperty(chartProps, "annotation3_type", "circle");
/* 226 */     checkAndSetProperty(chartProps, "annotation3_xPos", "0");
/* 227 */     checkAndSetProperty(chartProps, "annotation3_yPos", "0");
/* 228 */     checkAndSetProperty(chartProps, "annotation3_radius", "80");
/* 229 */     checkAndSetProperty(chartProps, "annotation3_color", "FFFFFF");
/* 230 */     checkAndSetProperty(chartProps, "annotation3_borderColor", "f0efea");
/*     */     
/* 232 */     checkAndSetProperty(chartProps, "color1_minValue", "0");
/* 233 */     checkAndSetProperty(chartProps, "color1_maxValue", "80");
/* 234 */     checkAndSetProperty(chartProps, "color1_code", "00FF00");
/* 235 */     checkAndSetProperty(chartProps, "color1_alpha", "0");
/*     */     
/*     */ 
/* 238 */     checkAndSetProperty(chartProps, "color2_minValue", "80");
/* 239 */     checkAndSetProperty(chartProps, "color2_maxValue", "100");
/* 240 */     checkAndSetProperty(chartProps, "color2_code", "FF0000");
/* 241 */     checkAndSetProperty(chartProps, "color2_alpha", "50");
/* 242 */     checkAndSetProperty(chartProps, "color2_name", "critical");
/*     */   }
/*     */   
/*     */   private void checkAndSetProperty(Properties chartProps, String key, String defaultValue) {
/* 246 */     if (chartProps.getProperty(key) == null)
/*     */     {
/* 248 */       chartProps.setProperty(key, defaultValue);
/*     */     }
/*     */   }
/*     */   
/*     */   private Document getXMLDocumentForDial(Properties chartProps) {
/* 253 */     if ((chartProps.get("isSemiDial") != null) && ("true".equals(chartProps.get("isSemiDial"))))
/*     */     {
/* 255 */       checkandSetDefaultPropsForSemiDialGraph(chartProps);
/*     */     }
/*     */     else
/*     */     {
/* 259 */       checkandSetDefaultPropsForDialGraph(chartProps);
/*     */     }
/*     */     
/* 262 */     Document doc = null;
/*     */     try
/*     */     {
/* 265 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 266 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 267 */       doc = docBuilder.newDocument();
/* 268 */       Element rootNode = doc.createElement("Chart");
/* 269 */       Element dials = doc.createElement("dials");
/* 270 */       Element dial = doc.createElement("dial");
/* 271 */       Element annotations = doc.createElement("annotations");
/* 272 */       Element annotationGroup = doc.createElement("annotationGroup");
/* 273 */       Element annotation1 = doc.createElement("annotation");
/* 274 */       Element annotation2 = doc.createElement("annotation");
/* 275 */       Element annotation3 = doc.createElement("annotation");
/* 276 */       Element colorRange = doc.createElement("colorRange");
/* 277 */       Element color1 = doc.createElement("color");
/* 278 */       Element color2 = doc.createElement("color");
/* 279 */       for (Enumeration keys = chartProps.keys(); keys.hasMoreElements();) {
/* 280 */         String key = (String)keys.nextElement();
/* 281 */         int index = 0;
/* 282 */         if (key.indexOf("annotationGroup_") != -1)
/*     */         {
/* 284 */           annotationGroup.setAttribute(key.substring(16, key.length()), chartProps.getProperty(key));
/*     */         }
/* 286 */         else if (key.indexOf("annotation1_") != -1)
/*     */         {
/* 288 */           annotation1.setAttribute(key.substring(12, key.length()), chartProps.getProperty(key));
/*     */         }
/* 290 */         else if (key.indexOf("annotation2_") != -1)
/*     */         {
/* 292 */           annotation2.setAttribute(key.substring(12, key.length()), chartProps.getProperty(key));
/*     */         }
/* 294 */         else if (key.indexOf("annotation3_") != -1)
/*     */         {
/* 296 */           annotation3.setAttribute(key.substring(12, key.length()), chartProps.getProperty(key));
/*     */         }
/* 298 */         else if (key.indexOf("color1_") != -1)
/*     */         {
/* 300 */           color1.setAttribute(key.substring(7, key.length()), chartProps.getProperty(key));
/*     */         }
/* 302 */         else if (key.indexOf("color2_") != -1)
/*     */         {
/* 304 */           color2.setAttribute(key.substring(7, key.length()), chartProps.getProperty(key));
/*     */         }
/* 306 */         else if (key.indexOf("dial1_") != -1)
/*     */         {
/* 308 */           dial.setAttribute(key.substring(6, key.length()), chartProps.getProperty(key));
/*     */         }
/*     */         else
/*     */         {
/* 312 */           rootNode.setAttribute(key, chartProps.getProperty(key));
/*     */         }
/*     */       }
/*     */       
/* 316 */       rootNode.appendChild(dials);
/* 317 */       dials.appendChild(dial);
/* 318 */       rootNode.appendChild(annotations);
/* 319 */       annotations.appendChild(annotationGroup);
/* 320 */       annotationGroup.appendChild(annotation1);
/* 321 */       annotationGroup.appendChild(annotation2);
/* 322 */       annotationGroup.appendChild(annotation3);
/* 323 */       colorRange.appendChild(color1);
/* 324 */       colorRange.appendChild(color2);
/* 325 */       rootNode.appendChild(colorRange);
/* 326 */       doc.appendChild(rootNode);
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 330 */       exc.printStackTrace();
/*     */     }
/* 332 */     return doc;
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
/*     */   private void checkandSetDefaultPropsForSemiDialGraph(Properties chartProps)
/*     */   {
/* 345 */     chartProps.setProperty("palette", "1");
/* 346 */     chartProps.setProperty("gaugeStartAngle", "180");
/* 347 */     chartProps.setProperty("gaugeEndAngle", "0");
/* 348 */     chartProps.setProperty("gaugeInnerRadius", "60%");
/* 349 */     chartProps.setProperty("gaugeFillMix", "{light-10},{light-20},{dark-5},{color},{light-30},{light-20},{dark-10}");
/* 350 */     chartProps.setProperty("decimals", "1");
/* 351 */     chartProps.setProperty("color1_minValue", "0");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 356 */     chartProps.setProperty("dial1_baseWidth", "6");
/* 357 */     chartProps.setProperty("dial1_topWidth", "1");
/* 358 */     chartProps.setProperty("dial1_showValue", "0");
/* 359 */     chartProps.setProperty("dial1_rearExtension", "0");
/* 360 */     chartProps.setProperty("dial1_valueY", "200");
/*     */     
/* 362 */     int chartHeight = 220;
/* 363 */     int chartWidth = 220;
/* 364 */     int value = 0;
/* 365 */     int upperLimit = 100;
/* 366 */     int criticalRange = 80;
/* 367 */     String units = "";
/* 368 */     String metricname = "";
/* 369 */     String tooltip = "";
/* 370 */     if ((chartProps.getProperty("chartHeight") != null) && (chartProps.getProperty("chartWidth") != null))
/*     */     {
/* 372 */       chartHeight = Integer.parseInt(chartProps.getProperty("chartHeight"));
/* 373 */       chartWidth = Integer.parseInt(chartProps.getProperty("chartWidth"));
/* 374 */       if ((chartProps.getProperty("value") != null) && (!"null".equals(chartProps.getProperty("value"))))
/*     */       {
/* 376 */         value = Integer.parseInt(chartProps.getProperty("value"));
/*     */       }
/*     */       else
/*     */       {
/* 380 */         value = 0;
/*     */       }
/* 382 */       if ((chartProps.getProperty("upperLimit") != null) && (!"null".equals(chartProps.getProperty("value"))))
/*     */       {
/* 384 */         upperLimit = Integer.parseInt(chartProps.getProperty("upperLimit"));
/*     */       }
/*     */       else
/*     */       {
/* 388 */         upperLimit = 1024;
/*     */       }
/* 390 */       units = chartProps.getProperty("units");
/* 391 */       metricname = chartProps.getProperty("metricname");
/* 392 */       tooltip = "   " + metricname + " : " + value + " " + units;
/* 393 */       criticalRange = 80 * upperLimit / 100;
/* 394 */       chartProps.setProperty("color1_maxValue", String.valueOf(criticalRange));
/* 395 */       chartProps.setProperty("color2_minValue", String.valueOf(criticalRange));
/* 396 */       chartProps.setProperty("color2_maxValue", String.valueOf(upperLimit));
/* 397 */       chartProps.setProperty("upperLimit", String.valueOf(upperLimit));
/* 398 */       chartProps.setProperty("dial1_value", String.valueOf(value));
/* 399 */       chartProps.setProperty("dial1_toolText", "  " + String.valueOf(tooltip) + "  ");
/* 400 */       chartProps.setProperty("gaugeOriginX", String.valueOf(chartWidth / 2));
/* 401 */       chartProps.setProperty("gaugeOriginY", String.valueOf(chartHeight * 0.9D));
/* 402 */       chartProps.setProperty("annotationGroup_xPos", String.valueOf(chartWidth / 2));
/* 403 */       chartProps.setProperty("annotationGroup_yPos", String.valueOf(chartHeight / 2));
/* 404 */       chartProps.setProperty("annotation1_radius", String.valueOf(chartWidth * 0.4D));
/* 405 */       chartProps.setProperty("annotation2_radius", String.valueOf(chartWidth * 0.3D));
/* 406 */       chartProps.setProperty("annotation3_radius", String.valueOf(chartWidth * 0.35D));
/* 407 */       chartProps.setProperty("pivotRadius", String.valueOf(chartWidth * 0.02D));
/* 408 */       chartProps.setProperty("gaugeOuterRadius", String.valueOf(chartWidth * 0.35D));
/* 409 */       chartProps.setProperty("gaugeInnerRadius", String.valueOf(chartWidth * 0.15D));
/*     */     }
/*     */     
/* 412 */     checkAndSetProperty(chartProps, "bgColor", "FFFFFF");
/* 413 */     checkAndSetProperty(chartProps, "bgAlpha", "0");
/* 414 */     checkAndSetProperty(chartProps, "upperLimit", "100");
/* 415 */     checkAndSetProperty(chartProps, "lowerLimit", "0");
/* 416 */     checkAndSetProperty(chartProps, "showLimits", "1");
/* 417 */     checkAndSetProperty(chartProps, "baseFontColor", "666666");
/* 418 */     checkAndSetProperty(chartProps, "majorTMNumber", "7");
/* 419 */     checkAndSetProperty(chartProps, "majorTMColor", "666666");
/* 420 */     checkAndSetProperty(chartProps, "majorTMHeight", "8");
/*     */     
/* 422 */     checkAndSetProperty(chartProps, "minorTMColor", "666666");
/* 423 */     checkAndSetProperty(chartProps, "minorTMHeight", "3");
/* 424 */     checkAndSetProperty(chartProps, "pivotRadius", "20");
/* 425 */     checkAndSetProperty(chartProps, "showGaugeBorder", "0");
/* 426 */     checkAndSetProperty(chartProps, "displayValueDistance", "20");
/* 427 */     checkAndSetProperty(chartProps, "pivotFillMix", "{F0EFEA}, {BEBCB0}");
/* 428 */     checkAndSetProperty(chartProps, "pivotBorderColor", "BEBCB0");
/* 429 */     checkAndSetProperty(chartProps, "pivotfillRatio", "80,20");
/* 430 */     checkAndSetProperty(chartProps, "showBorder", "0");
/* 431 */     checkAndSetProperty(chartProps, "color1_code", "669900");
/* 432 */     checkAndSetProperty(chartProps, "color2_code", "FF6666");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\XMLServerForFlash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */