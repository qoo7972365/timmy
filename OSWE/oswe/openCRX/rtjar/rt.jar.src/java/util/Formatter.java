/*      */ package java.util;
/*      */ 
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.Flushable;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.MathContext;
/*      */ import java.math.RoundingMode;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.NumberFormat;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.Instant;
/*      */ import java.time.ZoneId;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalQueries;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.misc.FormattedFloatingDecimal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Formatter
/*      */   implements Closeable, Flushable
/*      */ {
/*      */   private Appendable a;
/*      */   private final Locale l;
/*      */   private IOException lastException;
/*      */   private final char zero;
/*      */   private static double scaleUp;
/*      */   private static final int MAX_FD_CHARS = 30;
/*      */   private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
/*      */   
/*      */   private static Charset toCharset(String paramString) throws UnsupportedEncodingException {
/* 1872 */     Objects.requireNonNull(paramString, "charsetName");
/*      */     try {
/* 1874 */       return Charset.forName(paramString);
/* 1875 */     } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException illegalCharsetNameException) {
/*      */       
/* 1877 */       throw new UnsupportedEncodingException(paramString);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final Appendable nonNullAppendable(Appendable paramAppendable) {
/* 1882 */     if (paramAppendable == null) {
/* 1883 */       return new StringBuilder();
/*      */     }
/* 1885 */     return paramAppendable;
/*      */   }
/*      */ 
/*      */   
/*      */   private Formatter(Locale paramLocale, Appendable paramAppendable) {
/* 1890 */     this.a = paramAppendable;
/* 1891 */     this.l = paramLocale;
/* 1892 */     this.zero = getZero(paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Formatter(Charset paramCharset, Locale paramLocale, File paramFile) throws FileNotFoundException {
/* 1898 */     this(paramLocale, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile), paramCharset)));
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
/*      */   public Formatter() {
/* 1914 */     this(Locale.getDefault(Locale.Category.FORMAT), new StringBuilder());
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
/*      */   public Formatter(Appendable paramAppendable) {
/* 1930 */     this(Locale.getDefault(Locale.Category.FORMAT), nonNullAppendable(paramAppendable));
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
/*      */   public Formatter(Locale paramLocale) {
/* 1947 */     this(paramLocale, new StringBuilder());
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
/*      */   public Formatter(Appendable paramAppendable, Locale paramLocale) {
/* 1963 */     this(paramLocale, nonNullAppendable(paramAppendable));
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
/*      */   public Formatter(String paramString) throws FileNotFoundException {
/* 1996 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramString))));
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
/*      */   public Formatter(String paramString1, String paramString2) throws FileNotFoundException, UnsupportedEncodingException {
/* 2035 */     this(paramString1, paramString2, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(String paramString1, String paramString2, Locale paramLocale) throws FileNotFoundException, UnsupportedEncodingException {
/* 2074 */     this(toCharset(paramString2), paramLocale, new File(paramString1));
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
/*      */   public Formatter(File paramFile) throws FileNotFoundException {
/* 2107 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile))));
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
/*      */   public Formatter(File paramFile, String paramString) throws FileNotFoundException, UnsupportedEncodingException {
/* 2146 */     this(paramFile, paramString, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(File paramFile, String paramString, Locale paramLocale) throws FileNotFoundException, UnsupportedEncodingException {
/* 2185 */     this(toCharset(paramString), paramLocale, paramFile);
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
/*      */   public Formatter(PrintStream paramPrintStream) {
/* 2204 */     this(Locale.getDefault(Locale.Category.FORMAT), 
/* 2205 */         Objects.<Appendable>requireNonNull(paramPrintStream));
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
/*      */   public Formatter(OutputStream paramOutputStream) {
/* 2225 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(paramOutputStream)));
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
/*      */   public Formatter(OutputStream paramOutputStream, String paramString) throws UnsupportedEncodingException {
/* 2252 */     this(paramOutputStream, paramString, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(OutputStream paramOutputStream, String paramString, Locale paramLocale) throws UnsupportedEncodingException {
/* 2278 */     this(paramLocale, new BufferedWriter(new OutputStreamWriter(paramOutputStream, paramString)));
/*      */   }
/*      */   
/*      */   private static char getZero(Locale paramLocale) {
/* 2282 */     if (paramLocale != null && !paramLocale.equals(Locale.US)) {
/* 2283 */       DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 2284 */       return decimalFormatSymbols.getZeroDigit();
/*      */     } 
/* 2286 */     return '0';
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
/*      */   public Locale locale() {
/* 2304 */     ensureOpen();
/* 2305 */     return this.l;
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
/*      */   public Appendable out() {
/* 2318 */     ensureOpen();
/* 2319 */     return this.a;
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
/*      */   public String toString() {
/* 2354 */     ensureOpen();
/* 2355 */     return this.a.toString();
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
/*      */   public void flush() {
/* 2370 */     ensureOpen();
/* 2371 */     if (this.a instanceof Flushable) {
/*      */       try {
/* 2373 */         ((Flushable)this.a).flush();
/* 2374 */       } catch (IOException iOException) {
/* 2375 */         this.lastException = iOException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/* 2393 */     if (this.a == null)
/*      */       return; 
/*      */     try {
/* 2396 */       if (this.a instanceof Closeable)
/* 2397 */         ((Closeable)this.a).close(); 
/* 2398 */     } catch (IOException iOException) {
/* 2399 */       this.lastException = iOException;
/*      */     } finally {
/* 2401 */       this.a = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ensureOpen() {
/* 2406 */     if (this.a == null) {
/* 2407 */       throw new FormatterClosedException();
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
/*      */   public IOException ioException() {
/* 2421 */     return this.lastException;
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
/*      */   public Formatter format(String paramString, Object... paramVarArgs) {
/* 2455 */     return format(this.l, paramString, paramVarArgs);
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
/*      */   public Formatter format(Locale paramLocale, String paramString, Object... paramVarArgs) {
/* 2494 */     ensureOpen();
/*      */ 
/*      */     
/* 2497 */     int i = -1;
/*      */     
/* 2499 */     byte b = -1;
/*      */     
/* 2501 */     FormatString[] arrayOfFormatString = parse(paramString);
/* 2502 */     for (byte b1 = 0; b1 < arrayOfFormatString.length; b1++) {
/* 2503 */       FormatString formatString = arrayOfFormatString[b1];
/* 2504 */       int j = formatString.index();
/*      */       try {
/* 2506 */         switch (j) {
/*      */           case -2:
/* 2508 */             formatString.print(null, paramLocale);
/*      */             break;
/*      */           case -1:
/* 2511 */             if (i < 0 || (paramVarArgs != null && i > paramVarArgs.length - 1))
/* 2512 */               throw new MissingFormatArgumentException(formatString.toString()); 
/* 2513 */             formatString.print((paramVarArgs == null) ? null : paramVarArgs[i], paramLocale);
/*      */             break;
/*      */           
/*      */           case 0:
/* 2517 */             i = ++b;
/* 2518 */             if (paramVarArgs != null && b > paramVarArgs.length - 1)
/* 2519 */               throw new MissingFormatArgumentException(formatString.toString()); 
/* 2520 */             formatString.print((paramVarArgs == null) ? null : paramVarArgs[b], paramLocale);
/*      */             break;
/*      */           default:
/* 2523 */             i = j - 1;
/* 2524 */             if (paramVarArgs != null && i > paramVarArgs.length - 1)
/* 2525 */               throw new MissingFormatArgumentException(formatString.toString()); 
/* 2526 */             formatString.print((paramVarArgs == null) ? null : paramVarArgs[i], paramLocale);
/*      */             break;
/*      */         } 
/* 2529 */       } catch (IOException iOException) {
/* 2530 */         this.lastException = iOException;
/*      */       } 
/*      */     } 
/* 2533 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2540 */   private static Pattern fsPattern = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FormatString[] parse(String paramString) {
/* 2546 */     ArrayList<FixedString> arrayList = new ArrayList();
/* 2547 */     Matcher matcher = fsPattern.matcher(paramString);
/* 2548 */     for (int i = 0, j = paramString.length(); i < j; ) {
/* 2549 */       if (matcher.find(i)) {
/*      */ 
/*      */ 
/*      */         
/* 2553 */         if (matcher.start() != i) {
/*      */           
/* 2555 */           checkText(paramString, i, matcher.start());
/*      */           
/* 2557 */           arrayList.add(new FixedString(paramString.substring(i, matcher.start())));
/*      */         } 
/*      */         
/* 2560 */         arrayList.add(new FormatSpecifier(matcher));
/* 2561 */         i = matcher.end();
/*      */         
/*      */         continue;
/*      */       } 
/* 2565 */       checkText(paramString, i, j);
/*      */       
/* 2567 */       arrayList.add(new FixedString(paramString.substring(i)));
/*      */     } 
/*      */ 
/*      */     
/* 2571 */     return arrayList.<FormatString>toArray(new FormatString[arrayList.size()]);
/*      */   }
/*      */   
/*      */   private static void checkText(String paramString, int paramInt1, int paramInt2) {
/* 2575 */     for (int i = paramInt1; i < paramInt2; i++) {
/*      */       
/* 2577 */       if (paramString.charAt(i) == '%') {
/* 2578 */         boolean bool = (i == paramInt2 - 1) ? true : paramString.charAt(i + 1);
/* 2579 */         throw new UnknownFormatConversionException(String.valueOf(bool));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static interface FormatString {
/*      */     int index();
/*      */     void print(Object param1Object, Locale param1Locale) throws IOException;
/*      */     
/*      */     String toString(); }
/*      */   
/*      */   private class FixedString implements FormatString {
/*      */     private String s;
/*      */     
/* 2592 */     FixedString(String param1String) { this.s = param1String; } public int index() {
/* 2593 */       return -2;
/*      */     }
/* 2595 */     public void print(Object param1Object, Locale param1Locale) throws IOException { Formatter.this.a.append(this.s); } public String toString() {
/* 2596 */       return this.s;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum BigDecimalLayoutForm
/*      */   {
/* 2606 */     SCIENTIFIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2611 */     DECIMAL_FLOAT;
/*      */   }
/*      */   
/*      */   private class FormatSpecifier implements FormatString {
/* 2615 */     private int index = -1;
/* 2616 */     private Formatter.Flags f = Formatter.Flags.NONE;
/*      */     private int width;
/*      */     private int precision;
/*      */     private boolean dt = false;
/*      */     private char c;
/*      */     
/*      */     private int index(String param1String) {
/* 2623 */       if (param1String != null) {
/*      */         try {
/* 2625 */           this.index = Integer.parseInt(param1String.substring(0, param1String.length() - 1));
/* 2626 */         } catch (NumberFormatException numberFormatException) {
/*      */           assert false;
/*      */         } 
/*      */       } else {
/* 2630 */         this.index = 0;
/*      */       } 
/* 2632 */       return this.index;
/*      */     }
/*      */     
/*      */     public int index() {
/* 2636 */       return this.index;
/*      */     }
/*      */     
/*      */     private Formatter.Flags flags(String param1String) {
/* 2640 */       this.f = Formatter.Flags.parse(param1String);
/* 2641 */       if (this.f.contains(Formatter.Flags.PREVIOUS))
/* 2642 */         this.index = -1; 
/* 2643 */       return this.f;
/*      */     }
/*      */     
/*      */     Formatter.Flags flags() {
/* 2647 */       return this.f;
/*      */     }
/*      */     
/*      */     private int width(String param1String) {
/* 2651 */       this.width = -1;
/* 2652 */       if (param1String != null) {
/*      */         try {
/* 2654 */           this.width = Integer.parseInt(param1String);
/* 2655 */           if (this.width < 0)
/* 2656 */             throw new IllegalFormatWidthException(this.width); 
/* 2657 */         } catch (NumberFormatException numberFormatException) {
/*      */           assert false;
/*      */         } 
/*      */       }
/* 2661 */       return this.width;
/*      */     }
/*      */     
/*      */     int width() {
/* 2665 */       return this.width;
/*      */     }
/*      */     
/*      */     private int precision(String param1String) {
/* 2669 */       this.precision = -1;
/* 2670 */       if (param1String != null) {
/*      */         
/*      */         try {
/* 2673 */           this.precision = Integer.parseInt(param1String.substring(1));
/* 2674 */           if (this.precision < 0)
/* 2675 */             throw new IllegalFormatPrecisionException(this.precision); 
/* 2676 */         } catch (NumberFormatException numberFormatException) {
/*      */           assert false;
/*      */         } 
/*      */       }
/* 2680 */       return this.precision;
/*      */     }
/*      */     
/*      */     int precision() {
/* 2684 */       return this.precision;
/*      */     }
/*      */     
/*      */     private char conversion(String param1String) {
/* 2688 */       this.c = param1String.charAt(0);
/* 2689 */       if (!this.dt) {
/* 2690 */         if (!Formatter.Conversion.isValid(this.c))
/* 2691 */           throw new UnknownFormatConversionException(String.valueOf(this.c)); 
/* 2692 */         if (Character.isUpperCase(this.c))
/* 2693 */           this.f.add(Formatter.Flags.UPPERCASE); 
/* 2694 */         this.c = Character.toLowerCase(this.c);
/* 2695 */         if (Formatter.Conversion.isText(this.c))
/* 2696 */           this.index = -2; 
/*      */       } 
/* 2698 */       return this.c;
/*      */     }
/*      */     
/*      */     private char conversion() {
/* 2702 */       return this.c;
/*      */     }
/*      */     
/*      */     FormatSpecifier(Matcher param1Matcher) {
/* 2706 */       byte b = 1;
/*      */       
/* 2708 */       index(param1Matcher.group(b++));
/* 2709 */       flags(param1Matcher.group(b++));
/* 2710 */       width(param1Matcher.group(b++));
/* 2711 */       precision(param1Matcher.group(b++));
/*      */       
/* 2713 */       String str = param1Matcher.group(b++);
/* 2714 */       if (str != null) {
/* 2715 */         this.dt = true;
/* 2716 */         if (str.equals("T")) {
/* 2717 */           this.f.add(Formatter.Flags.UPPERCASE);
/*      */         }
/*      */       } 
/* 2720 */       conversion(param1Matcher.group(b));
/*      */       
/* 2722 */       if (this.dt) {
/* 2723 */         checkDateTime();
/* 2724 */       } else if (Formatter.Conversion.isGeneral(this.c)) {
/* 2725 */         checkGeneral();
/* 2726 */       } else if (Formatter.Conversion.isCharacter(this.c)) {
/* 2727 */         checkCharacter();
/* 2728 */       } else if (Formatter.Conversion.isInteger(this.c)) {
/* 2729 */         checkInteger();
/* 2730 */       } else if (Formatter.Conversion.isFloat(this.c)) {
/* 2731 */         checkFloat();
/* 2732 */       } else if (Formatter.Conversion.isText(this.c)) {
/* 2733 */         checkText();
/*      */       } else {
/* 2735 */         throw new UnknownFormatConversionException(String.valueOf(this.c));
/*      */       } 
/*      */     }
/*      */     public void print(Object param1Object, Locale param1Locale) throws IOException {
/* 2739 */       if (this.dt) {
/* 2740 */         printDateTime(param1Object, param1Locale);
/*      */         return;
/*      */       } 
/* 2743 */       switch (this.c) {
/*      */         case 'd':
/*      */         case 'o':
/*      */         case 'x':
/* 2747 */           printInteger(param1Object, param1Locale);
/*      */           return;
/*      */         case 'a':
/*      */         case 'e':
/*      */         case 'f':
/*      */         case 'g':
/* 2753 */           printFloat(param1Object, param1Locale);
/*      */           return;
/*      */         case 'C':
/*      */         case 'c':
/* 2757 */           printCharacter(param1Object);
/*      */           return;
/*      */         case 'b':
/* 2760 */           printBoolean(param1Object);
/*      */           return;
/*      */         case 's':
/* 2763 */           printString(param1Object, param1Locale);
/*      */           return;
/*      */         case 'h':
/* 2766 */           printHashCode(param1Object);
/*      */           return;
/*      */         case 'n':
/* 2769 */           Formatter.this.a.append(System.lineSeparator());
/*      */           return;
/*      */         case '%':
/* 2772 */           Formatter.this.a.append('%');
/*      */           return;
/*      */       } 
/*      */       assert false;
/*      */     }
/*      */ 
/*      */     
/*      */     private void printInteger(Object param1Object, Locale param1Locale) throws IOException {
/* 2780 */       if (param1Object == null) {
/* 2781 */         print("null");
/* 2782 */       } else if (param1Object instanceof Byte) {
/* 2783 */         print(((Byte)param1Object).byteValue(), param1Locale);
/* 2784 */       } else if (param1Object instanceof Short) {
/* 2785 */         print(((Short)param1Object).shortValue(), param1Locale);
/* 2786 */       } else if (param1Object instanceof Integer) {
/* 2787 */         print(((Integer)param1Object).intValue(), param1Locale);
/* 2788 */       } else if (param1Object instanceof Long) {
/* 2789 */         print(((Long)param1Object).longValue(), param1Locale);
/* 2790 */       } else if (param1Object instanceof BigInteger) {
/* 2791 */         print((BigInteger)param1Object, param1Locale);
/*      */       } else {
/* 2793 */         failConversion(this.c, param1Object);
/*      */       } 
/*      */     }
/*      */     private void printFloat(Object param1Object, Locale param1Locale) throws IOException {
/* 2797 */       if (param1Object == null) {
/* 2798 */         print("null");
/* 2799 */       } else if (param1Object instanceof Float) {
/* 2800 */         print(((Float)param1Object).floatValue(), param1Locale);
/* 2801 */       } else if (param1Object instanceof Double) {
/* 2802 */         print(((Double)param1Object).doubleValue(), param1Locale);
/* 2803 */       } else if (param1Object instanceof BigDecimal) {
/* 2804 */         print((BigDecimal)param1Object, param1Locale);
/*      */       } else {
/* 2806 */         failConversion(this.c, param1Object);
/*      */       } 
/*      */     }
/*      */     private void printDateTime(Object param1Object, Locale param1Locale) throws IOException {
/* 2810 */       if (param1Object == null) {
/* 2811 */         print("null");
/*      */         return;
/*      */       } 
/* 2814 */       Calendar calendar = null;
/*      */ 
/*      */ 
/*      */       
/* 2818 */       if (param1Object instanceof Long)
/*      */       
/*      */       { 
/* 2821 */         calendar = Calendar.getInstance((param1Locale == null) ? Locale.US : param1Locale);
/* 2822 */         calendar.setTimeInMillis(((Long)param1Object).longValue()); }
/* 2823 */       else if (param1Object instanceof Date)
/*      */       
/*      */       { 
/* 2826 */         calendar = Calendar.getInstance((param1Locale == null) ? Locale.US : param1Locale);
/* 2827 */         calendar.setTime((Date)param1Object); }
/* 2828 */       else if (param1Object instanceof Calendar)
/* 2829 */       { calendar = (Calendar)((Calendar)param1Object).clone();
/* 2830 */         calendar.setLenient(true); }
/* 2831 */       else { if (param1Object instanceof TemporalAccessor) {
/* 2832 */           print((TemporalAccessor)param1Object, this.c, param1Locale);
/*      */           return;
/*      */         } 
/* 2835 */         failConversion(this.c, param1Object); }
/*      */ 
/*      */ 
/*      */       
/* 2839 */       print(calendar, this.c, param1Locale);
/*      */     }
/*      */     
/*      */     private void printCharacter(Object param1Object) throws IOException {
/* 2843 */       if (param1Object == null) {
/* 2844 */         print("null");
/*      */         return;
/*      */       } 
/* 2847 */       String str = null;
/* 2848 */       if (param1Object instanceof Character)
/* 2849 */       { str = ((Character)param1Object).toString(); }
/* 2850 */       else if (param1Object instanceof Byte)
/* 2851 */       { byte b = ((Byte)param1Object).byteValue();
/* 2852 */         if (Character.isValidCodePoint(b))
/* 2853 */         { str = new String(Character.toChars(b)); }
/*      */         else
/* 2855 */         { throw new IllegalFormatCodePointException(b); }  }
/* 2856 */       else if (param1Object instanceof Short)
/* 2857 */       { short s = ((Short)param1Object).shortValue();
/* 2858 */         if (Character.isValidCodePoint(s))
/* 2859 */         { str = new String(Character.toChars(s)); }
/*      */         else
/* 2861 */         { throw new IllegalFormatCodePointException(s); }  }
/* 2862 */       else if (param1Object instanceof Integer)
/* 2863 */       { int i = ((Integer)param1Object).intValue();
/* 2864 */         if (Character.isValidCodePoint(i)) {
/* 2865 */           str = new String(Character.toChars(i));
/*      */         } else {
/* 2867 */           throw new IllegalFormatCodePointException(i);
/*      */         }  }
/* 2869 */       else { failConversion(this.c, param1Object); }
/*      */       
/* 2871 */       print(str);
/*      */     }
/*      */     
/*      */     private void printString(Object param1Object, Locale param1Locale) throws IOException {
/* 2875 */       if (param1Object instanceof Formattable) {
/* 2876 */         Formatter formatter = Formatter.this;
/* 2877 */         if (formatter.locale() != param1Locale)
/* 2878 */           formatter = new Formatter(formatter.out(), param1Locale); 
/* 2879 */         ((Formattable)param1Object).formatTo(formatter, this.f.valueOf(), this.width, this.precision);
/*      */       } else {
/* 2881 */         if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 2882 */           failMismatch(Formatter.Flags.ALTERNATE, 's'); 
/* 2883 */         if (param1Object == null) {
/* 2884 */           print("null");
/*      */         } else {
/* 2886 */           print(param1Object.toString());
/*      */         } 
/*      */       } 
/*      */     }
/*      */     private void printBoolean(Object param1Object) throws IOException {
/*      */       String str;
/* 2892 */       if (param1Object != null) {
/*      */ 
/*      */         
/* 2895 */         str = (param1Object instanceof Boolean) ? ((Boolean)param1Object).toString() : Boolean.toString(true);
/*      */       } else {
/* 2897 */         str = Boolean.toString(false);
/* 2898 */       }  print(str);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void printHashCode(Object param1Object) throws IOException {
/* 2904 */       String str = (param1Object == null) ? "null" : Integer.toHexString(param1Object.hashCode());
/* 2905 */       print(str);
/*      */     }
/*      */     
/*      */     private void print(String param1String) throws IOException {
/* 2909 */       if (this.precision != -1 && this.precision < param1String.length())
/* 2910 */         param1String = param1String.substring(0, this.precision); 
/* 2911 */       if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 2912 */         param1String = param1String.toUpperCase(); 
/* 2913 */       Formatter.this.a.append(justify(param1String));
/*      */     }
/*      */     
/*      */     private String justify(String param1String) {
/* 2917 */       if (this.width == -1)
/* 2918 */         return param1String; 
/* 2919 */       StringBuilder stringBuilder = new StringBuilder();
/* 2920 */       boolean bool = this.f.contains(Formatter.Flags.LEFT_JUSTIFY);
/* 2921 */       int i = this.width - param1String.length();
/* 2922 */       if (!bool)
/* 2923 */         for (byte b = 0; b < i; ) { stringBuilder.append(' '); b++; }
/* 2924 */           stringBuilder.append(param1String);
/* 2925 */       if (bool)
/* 2926 */         for (byte b = 0; b < i; ) { stringBuilder.append(' '); b++; }
/* 2927 */           return stringBuilder.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2931 */       StringBuilder stringBuilder = new StringBuilder("%");
/*      */       
/* 2933 */       Formatter.Flags flags = this.f.dup().remove(Formatter.Flags.UPPERCASE);
/* 2934 */       stringBuilder.append(flags.toString());
/* 2935 */       if (this.index > 0)
/* 2936 */         stringBuilder.append(this.index).append('$'); 
/* 2937 */       if (this.width != -1)
/* 2938 */         stringBuilder.append(this.width); 
/* 2939 */       if (this.precision != -1)
/* 2940 */         stringBuilder.append('.').append(this.precision); 
/* 2941 */       if (this.dt)
/* 2942 */         stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? 84 : 116); 
/* 2943 */       stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? 
/* 2944 */           Character.toUpperCase(this.c) : this.c);
/* 2945 */       return stringBuilder.toString();
/*      */     }
/*      */     
/*      */     private void checkGeneral() {
/* 2949 */       if ((this.c == 'b' || this.c == 'h') && this.f
/* 2950 */         .contains(Formatter.Flags.ALTERNATE)) {
/* 2951 */         failMismatch(Formatter.Flags.ALTERNATE, this.c);
/*      */       }
/* 2953 */       if (this.width == -1 && this.f.contains(Formatter.Flags.LEFT_JUSTIFY))
/* 2954 */         throw new MissingFormatWidthException(toString()); 
/* 2955 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */     }
/*      */ 
/*      */     
/*      */     private void checkDateTime() {
/* 2960 */       if (this.precision != -1)
/* 2961 */         throw new IllegalFormatPrecisionException(this.precision); 
/* 2962 */       if (!Formatter.DateTime.isValid(this.c))
/* 2963 */         throw new UnknownFormatConversionException("t" + this.c); 
/* 2964 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE, Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */ 
/*      */       
/* 2967 */       if (this.width == -1 && this.f.contains(Formatter.Flags.LEFT_JUSTIFY))
/* 2968 */         throw new MissingFormatWidthException(toString()); 
/*      */     }
/*      */     
/*      */     private void checkCharacter() {
/* 2972 */       if (this.precision != -1)
/* 2973 */         throw new IllegalFormatPrecisionException(this.precision); 
/* 2974 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE, Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */ 
/*      */       
/* 2977 */       if (this.width == -1 && this.f.contains(Formatter.Flags.LEFT_JUSTIFY))
/* 2978 */         throw new MissingFormatWidthException(toString()); 
/*      */     }
/*      */     
/*      */     private void checkInteger() {
/* 2982 */       checkNumeric();
/* 2983 */       if (this.precision != -1) {
/* 2984 */         throw new IllegalFormatPrecisionException(this.precision);
/*      */       }
/* 2986 */       if (this.c == 'd') {
/* 2987 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE });
/* 2988 */       } else if (this.c == 'o') {
/* 2989 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/*      */       } else {
/* 2991 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/*      */       } 
/*      */     }
/*      */     private void checkBadFlags(Formatter.Flags... param1VarArgs) {
/* 2995 */       for (byte b = 0; b < param1VarArgs.length; b++) {
/* 2996 */         if (this.f.contains(param1VarArgs[b]))
/* 2997 */           failMismatch(param1VarArgs[b], this.c); 
/*      */       } 
/*      */     }
/*      */     private void checkFloat() {
/* 3001 */       checkNumeric();
/* 3002 */       if (this.c != 'f')
/* 3003 */         if (this.c == 'a') {
/* 3004 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.GROUP });
/* 3005 */         } else if (this.c == 'e') {
/* 3006 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/* 3007 */         } else if (this.c == 'g') {
/* 3008 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE });
/*      */         }  
/*      */     }
/*      */     
/*      */     private void checkNumeric() {
/* 3013 */       if (this.width != -1 && this.width < 0) {
/* 3014 */         throw new IllegalFormatWidthException(this.width);
/*      */       }
/* 3016 */       if (this.precision != -1 && this.precision < 0) {
/* 3017 */         throw new IllegalFormatPrecisionException(this.precision);
/*      */       }
/*      */       
/* 3020 */       if (this.width == -1 && (this.f
/* 3021 */         .contains(Formatter.Flags.LEFT_JUSTIFY) || this.f.contains(Formatter.Flags.ZERO_PAD))) {
/* 3022 */         throw new MissingFormatWidthException(toString());
/*      */       }
/*      */       
/* 3025 */       if ((this.f.contains(Formatter.Flags.PLUS) && this.f.contains(Formatter.Flags.LEADING_SPACE)) || (this.f
/* 3026 */         .contains(Formatter.Flags.LEFT_JUSTIFY) && this.f.contains(Formatter.Flags.ZERO_PAD)))
/* 3027 */         throw new IllegalFormatFlagsException(this.f.toString()); 
/*      */     }
/*      */     
/*      */     private void checkText() {
/* 3031 */       if (this.precision != -1)
/* 3032 */         throw new IllegalFormatPrecisionException(this.precision); 
/* 3033 */       switch (this.c) {
/*      */         case '%':
/* 3035 */           if (this.f.valueOf() != Formatter.Flags.LEFT_JUSTIFY.valueOf() && this.f
/* 3036 */             .valueOf() != Formatter.Flags.NONE.valueOf()) {
/* 3037 */             throw new IllegalFormatFlagsException(this.f.toString());
/*      */           }
/* 3039 */           if (this.width == -1 && this.f.contains(Formatter.Flags.LEFT_JUSTIFY))
/* 3040 */             throw new MissingFormatWidthException(toString()); 
/*      */           return;
/*      */         case 'n':
/* 3043 */           if (this.width != -1)
/* 3044 */             throw new IllegalFormatWidthException(this.width); 
/* 3045 */           if (this.f.valueOf() != Formatter.Flags.NONE.valueOf()) {
/* 3046 */             throw new IllegalFormatFlagsException(this.f.toString());
/*      */           }
/*      */           return;
/*      */       } 
/*      */       assert false;
/*      */     }
/*      */     
/*      */     private void print(byte param1Byte, Locale param1Locale) throws IOException {
/* 3054 */       long l = param1Byte;
/* 3055 */       if (param1Byte < 0 && (this.c == 'o' || this.c == 'x')) {
/*      */ 
/*      */         
/* 3058 */         l += 256L;
/* 3059 */         assert l >= 0L : l;
/*      */       } 
/* 3061 */       print(l, param1Locale);
/*      */     }
/*      */     
/*      */     private void print(short param1Short, Locale param1Locale) throws IOException {
/* 3065 */       long l = param1Short;
/* 3066 */       if (param1Short < 0 && (this.c == 'o' || this.c == 'x')) {
/*      */ 
/*      */         
/* 3069 */         l += 65536L;
/* 3070 */         assert l >= 0L : l;
/*      */       } 
/* 3072 */       print(l, param1Locale);
/*      */     }
/*      */     
/*      */     private void print(int param1Int, Locale param1Locale) throws IOException {
/* 3076 */       long l = param1Int;
/* 3077 */       if (param1Int < 0 && (this.c == 'o' || this.c == 'x')) {
/*      */ 
/*      */         
/* 3080 */         l += 4294967296L;
/* 3081 */         assert l >= 0L : l;
/*      */       } 
/* 3083 */       print(l, param1Locale);
/*      */     }
/*      */ 
/*      */     
/*      */     private void print(long param1Long, Locale param1Locale) throws IOException {
/* 3088 */       StringBuilder stringBuilder = new StringBuilder();
/*      */       
/* 3090 */       if (this.c == 'd') {
/* 3091 */         char[] arrayOfChar; boolean bool = (param1Long < 0L) ? true : false;
/*      */         
/* 3093 */         if (param1Long < 0L) {
/* 3094 */           arrayOfChar = Long.toString(param1Long, 10).substring(1).toCharArray();
/*      */         } else {
/* 3096 */           arrayOfChar = Long.toString(param1Long, 10).toCharArray();
/*      */         } 
/*      */         
/* 3099 */         leadingSign(stringBuilder, bool);
/*      */ 
/*      */         
/* 3102 */         localizedMagnitude(stringBuilder, arrayOfChar, this.f, adjustWidth(this.width, this.f, bool), param1Locale);
/*      */ 
/*      */         
/* 3105 */         trailingSign(stringBuilder, bool);
/* 3106 */       } else if (this.c == 'o') {
/* 3107 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.LEADING_SPACE, Formatter.Flags.PLUS });
/*      */         
/* 3109 */         String str = Long.toOctalString(param1Long);
/*      */ 
/*      */         
/* 3112 */         int i = this.f.contains(Formatter.Flags.ALTERNATE) ? (str.length() + 1) : str.length();
/*      */ 
/*      */         
/* 3115 */         if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 3116 */           stringBuilder.append('0'); 
/* 3117 */         if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3118 */           for (byte b = 0; b < this.width - i; ) { stringBuilder.append('0'); b++; }
/* 3119 */             stringBuilder.append(str);
/* 3120 */       } else if (this.c == 'x') {
/* 3121 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.LEADING_SPACE, Formatter.Flags.PLUS });
/*      */         
/* 3123 */         String str = Long.toHexString(param1Long);
/*      */ 
/*      */         
/* 3126 */         int i = this.f.contains(Formatter.Flags.ALTERNATE) ? (str.length() + 2) : str.length();
/*      */ 
/*      */         
/* 3129 */         if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 3130 */           stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "0X" : "0x"); 
/* 3131 */         if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3132 */           for (byte b = 0; b < this.width - i; ) { stringBuilder.append('0'); b++; }
/* 3133 */             if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 3134 */           str = str.toUpperCase(); 
/* 3135 */         stringBuilder.append(str);
/*      */       } 
/*      */ 
/*      */       
/* 3139 */       Formatter.this.a.append(justify(stringBuilder.toString()));
/*      */     }
/*      */ 
/*      */     
/*      */     private StringBuilder leadingSign(StringBuilder param1StringBuilder, boolean param1Boolean) {
/* 3144 */       if (!param1Boolean) {
/* 3145 */         if (this.f.contains(Formatter.Flags.PLUS)) {
/* 3146 */           param1StringBuilder.append('+');
/* 3147 */         } else if (this.f.contains(Formatter.Flags.LEADING_SPACE)) {
/* 3148 */           param1StringBuilder.append(' ');
/*      */         }
/*      */       
/* 3151 */       } else if (this.f.contains(Formatter.Flags.PARENTHESES)) {
/* 3152 */         param1StringBuilder.append('(');
/*      */       } else {
/* 3154 */         param1StringBuilder.append('-');
/*      */       } 
/* 3156 */       return param1StringBuilder;
/*      */     }
/*      */ 
/*      */     
/*      */     private StringBuilder trailingSign(StringBuilder param1StringBuilder, boolean param1Boolean) {
/* 3161 */       if (param1Boolean && this.f.contains(Formatter.Flags.PARENTHESES))
/* 3162 */         param1StringBuilder.append(')'); 
/* 3163 */       return param1StringBuilder;
/*      */     }
/*      */     
/*      */     private void print(BigInteger param1BigInteger, Locale param1Locale) throws IOException {
/* 3167 */       StringBuilder stringBuilder = new StringBuilder();
/* 3168 */       boolean bool = (param1BigInteger.signum() == -1) ? true : false;
/* 3169 */       BigInteger bigInteger = param1BigInteger.abs();
/*      */ 
/*      */       
/* 3172 */       leadingSign(stringBuilder, bool);
/*      */ 
/*      */       
/* 3175 */       if (this.c == 'd') {
/* 3176 */         char[] arrayOfChar = bigInteger.toString().toCharArray();
/* 3177 */         localizedMagnitude(stringBuilder, arrayOfChar, this.f, adjustWidth(this.width, this.f, bool), param1Locale);
/* 3178 */       } else if (this.c == 'o') {
/* 3179 */         String str = bigInteger.toString(8);
/*      */         
/* 3181 */         int i = str.length() + stringBuilder.length();
/* 3182 */         if (bool && this.f.contains(Formatter.Flags.PARENTHESES)) {
/* 3183 */           i++;
/*      */         }
/*      */         
/* 3186 */         if (this.f.contains(Formatter.Flags.ALTERNATE)) {
/* 3187 */           i++;
/* 3188 */           stringBuilder.append('0');
/*      */         } 
/* 3190 */         if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3191 */           for (byte b = 0; b < this.width - i; b++) {
/* 3192 */             stringBuilder.append('0');
/*      */           } 
/* 3194 */         stringBuilder.append(str);
/* 3195 */       } else if (this.c == 'x') {
/* 3196 */         String str = bigInteger.toString(16);
/*      */         
/* 3198 */         int i = str.length() + stringBuilder.length();
/* 3199 */         if (bool && this.f.contains(Formatter.Flags.PARENTHESES)) {
/* 3200 */           i++;
/*      */         }
/*      */         
/* 3203 */         if (this.f.contains(Formatter.Flags.ALTERNATE)) {
/* 3204 */           i += 2;
/* 3205 */           stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "0X" : "0x");
/*      */         } 
/* 3207 */         if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3208 */           for (byte b = 0; b < this.width - i; b++)
/* 3209 */             stringBuilder.append('0');  
/* 3210 */         if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 3211 */           str = str.toUpperCase(); 
/* 3212 */         stringBuilder.append(str);
/*      */       } 
/*      */ 
/*      */       
/* 3216 */       trailingSign(stringBuilder, (param1BigInteger.signum() == -1));
/*      */ 
/*      */       
/* 3219 */       Formatter.this.a.append(justify(stringBuilder.toString()));
/*      */     }
/*      */     
/*      */     private void print(float param1Float, Locale param1Locale) throws IOException {
/* 3223 */       print(param1Float, param1Locale);
/*      */     }
/*      */     
/*      */     private void print(double param1Double, Locale param1Locale) throws IOException {
/* 3227 */       StringBuilder stringBuilder = new StringBuilder();
/* 3228 */       boolean bool = (Double.compare(param1Double, 0.0D) == -1) ? true : false;
/*      */       
/* 3230 */       if (!Double.isNaN(param1Double)) {
/* 3231 */         double d = Math.abs(param1Double);
/*      */ 
/*      */         
/* 3234 */         leadingSign(stringBuilder, bool);
/*      */ 
/*      */         
/* 3237 */         if (!Double.isInfinite(d)) {
/* 3238 */           print(stringBuilder, d, param1Locale, this.f, this.c, this.precision, bool);
/*      */         } else {
/* 3240 */           stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "INFINITY" : "Infinity");
/*      */         } 
/*      */ 
/*      */         
/* 3244 */         trailingSign(stringBuilder, bool);
/*      */       } else {
/* 3246 */         stringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "NAN" : "NaN");
/*      */       } 
/*      */ 
/*      */       
/* 3250 */       Formatter.this.a.append(justify(stringBuilder.toString()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void print(StringBuilder param1StringBuilder, double param1Double, Locale param1Locale, Formatter.Flags param1Flags, char param1Char, int param1Int, boolean param1Boolean) throws IOException {
/* 3258 */       if (param1Char == 'e') {
/*      */ 
/*      */         
/* 3261 */         boolean bool = (param1Int == -1) ? true : param1Int;
/*      */ 
/*      */         
/* 3264 */         FormattedFloatingDecimal formattedFloatingDecimal = FormattedFloatingDecimal.valueOf(param1Double, bool, FormattedFloatingDecimal.Form.SCIENTIFIC);
/*      */ 
/*      */         
/* 3267 */         char[] arrayOfChar1 = addZeros(formattedFloatingDecimal.getMantissa(), bool);
/*      */ 
/*      */ 
/*      */         
/* 3271 */         if (param1Flags.contains(Formatter.Flags.ALTERNATE) && !bool) {
/* 3272 */           arrayOfChar1 = addDot(arrayOfChar1);
/*      */         }
/* 3274 */         (new char[3])[0] = '+'; (new char[3])[1] = '0'; (new char[3])[2] = '0';
/* 3275 */         char[] arrayOfChar2 = (param1Double == 0.0D) ? new char[3] : formattedFloatingDecimal.getExponent();
/*      */         
/* 3277 */         int i = this.width;
/* 3278 */         if (this.width != -1)
/* 3279 */           i = adjustWidth(this.width - arrayOfChar2.length - 1, param1Flags, param1Boolean); 
/* 3280 */         localizedMagnitude(param1StringBuilder, arrayOfChar1, param1Flags, i, param1Locale);
/*      */         
/* 3282 */         param1StringBuilder.append(param1Flags.contains(Formatter.Flags.UPPERCASE) ? 69 : 101);
/*      */         
/* 3284 */         Formatter.Flags flags = param1Flags.dup().remove(Formatter.Flags.GROUP);
/* 3285 */         char c = arrayOfChar2[0];
/* 3286 */         assert c == '+' || c == '-';
/* 3287 */         param1StringBuilder.append(c);
/*      */         
/* 3289 */         char[] arrayOfChar3 = new char[arrayOfChar2.length - 1];
/* 3290 */         System.arraycopy(arrayOfChar2, 1, arrayOfChar3, 0, arrayOfChar2.length - 1);
/* 3291 */         param1StringBuilder.append(localizedMagnitude((StringBuilder)null, arrayOfChar3, flags, -1, param1Locale));
/* 3292 */       } else if (param1Char == 'f') {
/*      */ 
/*      */         
/* 3295 */         boolean bool = (param1Int == -1) ? true : param1Int;
/*      */ 
/*      */         
/* 3298 */         FormattedFloatingDecimal formattedFloatingDecimal = FormattedFloatingDecimal.valueOf(param1Double, bool, FormattedFloatingDecimal.Form.DECIMAL_FLOAT);
/*      */ 
/*      */         
/* 3301 */         char[] arrayOfChar = addZeros(formattedFloatingDecimal.getMantissa(), bool);
/*      */ 
/*      */ 
/*      */         
/* 3305 */         if (param1Flags.contains(Formatter.Flags.ALTERNATE) && !bool) {
/* 3306 */           arrayOfChar = addDot(arrayOfChar);
/*      */         }
/* 3308 */         int i = this.width;
/* 3309 */         if (this.width != -1)
/* 3310 */           i = adjustWidth(this.width, param1Flags, param1Boolean); 
/* 3311 */         localizedMagnitude(param1StringBuilder, arrayOfChar, param1Flags, i, param1Locale);
/* 3312 */       } else if (param1Char == 'g') {
/* 3313 */         char[] arrayOfChar1; int j, i = param1Int;
/* 3314 */         if (param1Int == -1) {
/* 3315 */           i = 6;
/* 3316 */         } else if (param1Int == 0) {
/* 3317 */           i = 1;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3322 */         if (param1Double == 0.0D) {
/* 3323 */           arrayOfChar1 = null;
/* 3324 */           arrayOfChar2 = new char[] { '0' };
/* 3325 */           j = 0;
/*      */         } else {
/*      */           
/* 3328 */           FormattedFloatingDecimal formattedFloatingDecimal = FormattedFloatingDecimal.valueOf(param1Double, i, FormattedFloatingDecimal.Form.GENERAL);
/*      */           
/* 3330 */           arrayOfChar1 = formattedFloatingDecimal.getExponent();
/* 3331 */           arrayOfChar2 = formattedFloatingDecimal.getMantissa();
/* 3332 */           j = formattedFloatingDecimal.getExponentRounded();
/*      */         } 
/*      */         
/* 3335 */         if (arrayOfChar1 != null) {
/* 3336 */           i--;
/*      */         } else {
/* 3338 */           i -= j + 1;
/*      */         } 
/*      */         
/* 3341 */         char[] arrayOfChar2 = addZeros(arrayOfChar2, i);
/*      */ 
/*      */         
/* 3344 */         if (param1Flags.contains(Formatter.Flags.ALTERNATE) && i == 0) {
/* 3345 */           arrayOfChar2 = addDot(arrayOfChar2);
/*      */         }
/* 3347 */         int k = this.width;
/* 3348 */         if (this.width != -1)
/* 3349 */           if (arrayOfChar1 != null) {
/* 3350 */             k = adjustWidth(this.width - arrayOfChar1.length - 1, param1Flags, param1Boolean);
/*      */           } else {
/* 3352 */             k = adjustWidth(this.width, param1Flags, param1Boolean);
/*      */           }  
/* 3354 */         localizedMagnitude(param1StringBuilder, arrayOfChar2, param1Flags, k, param1Locale);
/*      */         
/* 3356 */         if (arrayOfChar1 != null) {
/* 3357 */           param1StringBuilder.append(param1Flags.contains(Formatter.Flags.UPPERCASE) ? 69 : 101);
/*      */           
/* 3359 */           Formatter.Flags flags = param1Flags.dup().remove(Formatter.Flags.GROUP);
/* 3360 */           char c = arrayOfChar1[0];
/* 3361 */           assert c == '+' || c == '-';
/* 3362 */           param1StringBuilder.append(c);
/*      */           
/* 3364 */           char[] arrayOfChar = new char[arrayOfChar1.length - 1];
/* 3365 */           System.arraycopy(arrayOfChar1, 1, arrayOfChar, 0, arrayOfChar1.length - 1);
/* 3366 */           param1StringBuilder.append(localizedMagnitude((StringBuilder)null, arrayOfChar, flags, -1, param1Locale));
/*      */         } 
/* 3368 */       } else if (param1Char == 'a') {
/* 3369 */         int i = param1Int;
/* 3370 */         if (param1Int == -1) {
/*      */           
/* 3372 */           i = 0;
/* 3373 */         } else if (param1Int == 0) {
/* 3374 */           i = 1;
/*      */         } 
/* 3376 */         String str = hexDouble(param1Double, i);
/*      */ 
/*      */         
/* 3379 */         boolean bool = param1Flags.contains(Formatter.Flags.UPPERCASE);
/* 3380 */         param1StringBuilder.append(bool ? "0X" : "0x");
/*      */         
/* 3382 */         if (param1Flags.contains(Formatter.Flags.ZERO_PAD))
/* 3383 */           for (byte b = 0; b < this.width - str.length() - 2; b++) {
/* 3384 */             param1StringBuilder.append('0');
/*      */           } 
/* 3386 */         int j = str.indexOf('p');
/* 3387 */         char[] arrayOfChar = str.substring(0, j).toCharArray();
/* 3388 */         if (bool) {
/* 3389 */           String str1 = new String(arrayOfChar);
/*      */           
/* 3391 */           str1 = str1.toUpperCase(Locale.US);
/* 3392 */           arrayOfChar = str1.toCharArray();
/*      */         } 
/* 3394 */         param1StringBuilder.append((i != 0) ? addZeros(arrayOfChar, i) : arrayOfChar);
/* 3395 */         param1StringBuilder.append(bool ? 80 : 112);
/* 3396 */         param1StringBuilder.append(str.substring(j + 1));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private char[] addZeros(char[] param1ArrayOfchar, int param1Int) {
/*      */       byte b;
/* 3405 */       for (b = 0; b < param1ArrayOfchar.length && 
/* 3406 */         param1ArrayOfchar[b] != '.'; b++);
/*      */ 
/*      */       
/* 3409 */       boolean bool = false;
/* 3410 */       if (b == param1ArrayOfchar.length) {
/* 3411 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/* 3415 */       int i = param1ArrayOfchar.length - b - (bool ? 0 : 1);
/* 3416 */       assert i <= param1Int;
/* 3417 */       if (i == param1Int) {
/* 3418 */         return param1ArrayOfchar;
/*      */       }
/*      */       
/* 3421 */       char[] arrayOfChar = new char[param1ArrayOfchar.length + param1Int - i + (bool ? 1 : 0)];
/*      */       
/* 3423 */       System.arraycopy(param1ArrayOfchar, 0, arrayOfChar, 0, param1ArrayOfchar.length);
/*      */ 
/*      */       
/* 3426 */       int j = param1ArrayOfchar.length;
/* 3427 */       if (bool) {
/* 3428 */         arrayOfChar[param1ArrayOfchar.length] = '.';
/* 3429 */         j++;
/*      */       } 
/*      */ 
/*      */       
/* 3433 */       for (int k = j; k < arrayOfChar.length; k++) {
/* 3434 */         arrayOfChar[k] = '0';
/*      */       }
/* 3436 */       return arrayOfChar;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private String hexDouble(double param1Double, int param1Int) {
/* 3442 */       if (!Double.isFinite(param1Double) || param1Double == 0.0D || param1Int == 0 || param1Int >= 13)
/*      */       {
/* 3444 */         return Double.toHexString(param1Double).substring(2);
/*      */       }
/* 3446 */       assert param1Int >= 1 && param1Int <= 12;
/*      */       
/* 3448 */       int i = Math.getExponent(param1Double);
/* 3449 */       boolean bool1 = (i == -1023) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3454 */       if (bool1) {
/* 3455 */         Formatter.scaleUp = Math.scalb(1.0D, 54);
/* 3456 */         param1Double *= Formatter.scaleUp;
/*      */ 
/*      */         
/* 3459 */         i = Math.getExponent(param1Double);
/* 3460 */         assert i >= -1022 && i <= 1023 : i;
/*      */       } 
/*      */ 
/*      */       
/* 3464 */       int j = 1 + param1Int * 4;
/* 3465 */       int k = 53 - j;
/*      */       
/* 3467 */       assert k >= 1 && k < 53;
/*      */       
/* 3469 */       long l1 = Double.doubleToLongBits(param1Double);
/*      */       
/* 3471 */       long l2 = (l1 & Long.MAX_VALUE) >> k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3476 */       long l3 = l1 & (-1L << k ^ 0xFFFFFFFFFFFFFFFFL);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3483 */       boolean bool2 = ((l2 & 0x1L) == 0L) ? true : false;
/* 3484 */       boolean bool3 = ((1L << k - 1 & l3) != 0L) ? true : false;
/*      */       
/* 3486 */       boolean bool4 = (k > 1 && ((1L << k - 1 ^ 0xFFFFFFFFFFFFFFFFL) & l3) != 0L) ? true : false;
/*      */       
/* 3488 */       if ((bool2 && bool3 && bool4) || (!bool2 && bool3)) {
/* 3489 */         l2++;
/*      */       }
/*      */       
/* 3492 */       long l4 = l1 & Long.MIN_VALUE;
/* 3493 */       l2 = l4 | l2 << k;
/* 3494 */       double d = Double.longBitsToDouble(l2);
/*      */       
/* 3496 */       if (Double.isInfinite(d))
/*      */       {
/* 3498 */         return "1.0p1024";
/*      */       }
/* 3500 */       String str1 = Double.toHexString(d).substring(2);
/* 3501 */       if (!bool1) {
/* 3502 */         return str1;
/*      */       }
/*      */       
/* 3505 */       int m = str1.indexOf('p');
/* 3506 */       if (m == -1) {
/*      */         assert false;
/*      */         
/* 3509 */         return null;
/*      */       } 
/*      */       
/* 3512 */       String str2 = str1.substring(m + 1);
/* 3513 */       int n = Integer.parseInt(str2) - 54;
/* 3514 */       return str1.substring(0, m) + "p" + 
/* 3515 */         Integer.toString(n);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void print(BigDecimal param1BigDecimal, Locale param1Locale) throws IOException {
/* 3523 */       if (this.c == 'a')
/* 3524 */         failConversion(this.c, param1BigDecimal); 
/* 3525 */       StringBuilder stringBuilder = new StringBuilder();
/* 3526 */       boolean bool = (param1BigDecimal.signum() == -1) ? true : false;
/* 3527 */       BigDecimal bigDecimal = param1BigDecimal.abs();
/*      */       
/* 3529 */       leadingSign(stringBuilder, bool);
/*      */ 
/*      */       
/* 3532 */       print(stringBuilder, bigDecimal, param1Locale, this.f, this.c, this.precision, bool);
/*      */ 
/*      */       
/* 3535 */       trailingSign(stringBuilder, bool);
/*      */ 
/*      */       
/* 3538 */       Formatter.this.a.append(justify(stringBuilder.toString()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void print(StringBuilder param1StringBuilder, BigDecimal param1BigDecimal, Locale param1Locale, Formatter.Flags param1Flags, char param1Char, int param1Int, boolean param1Boolean) throws IOException {
/* 3546 */       if (param1Char == 'e') {
/*      */         int m;
/* 3548 */         byte b = (param1Int == -1) ? 6 : param1Int;
/* 3549 */         int i = param1BigDecimal.scale();
/* 3550 */         int j = param1BigDecimal.precision();
/* 3551 */         int k = 0;
/*      */ 
/*      */         
/* 3554 */         if (b > j - 1) {
/* 3555 */           m = j;
/* 3556 */           k = b - j - 1;
/*      */         } else {
/* 3558 */           m = b + 1;
/*      */         } 
/*      */         
/* 3561 */         MathContext mathContext = new MathContext(m);
/*      */         
/* 3563 */         BigDecimal bigDecimal = new BigDecimal(param1BigDecimal.unscaledValue(), i, mathContext);
/*      */ 
/*      */         
/* 3566 */         BigDecimalLayout bigDecimalLayout = new BigDecimalLayout(bigDecimal.unscaledValue(), bigDecimal.scale(), Formatter.BigDecimalLayoutForm.SCIENTIFIC);
/*      */ 
/*      */         
/* 3569 */         char[] arrayOfChar1 = bigDecimalLayout.mantissa();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3576 */         if ((j == 1 || !bigDecimalLayout.hasDot()) && (k > 0 || param1Flags
/* 3577 */           .contains(Formatter.Flags.ALTERNATE))) {
/* 3578 */           arrayOfChar1 = addDot(arrayOfChar1);
/*      */         }
/*      */ 
/*      */         
/* 3582 */         arrayOfChar1 = trailingZeros(arrayOfChar1, k);
/*      */         
/* 3584 */         char[] arrayOfChar2 = bigDecimalLayout.exponent();
/* 3585 */         int n = this.width;
/* 3586 */         if (this.width != -1)
/* 3587 */           n = adjustWidth(this.width - arrayOfChar2.length - 1, param1Flags, param1Boolean); 
/* 3588 */         localizedMagnitude(param1StringBuilder, arrayOfChar1, param1Flags, n, param1Locale);
/*      */         
/* 3590 */         param1StringBuilder.append(param1Flags.contains(Formatter.Flags.UPPERCASE) ? 69 : 101);
/*      */         
/* 3592 */         Formatter.Flags flags = param1Flags.dup().remove(Formatter.Flags.GROUP);
/* 3593 */         char c = arrayOfChar2[0];
/* 3594 */         assert c == '+' || c == '-';
/* 3595 */         param1StringBuilder.append(arrayOfChar2[0]);
/*      */         
/* 3597 */         char[] arrayOfChar3 = new char[arrayOfChar2.length - 1];
/* 3598 */         System.arraycopy(arrayOfChar2, 1, arrayOfChar3, 0, arrayOfChar2.length - 1);
/* 3599 */         param1StringBuilder.append(localizedMagnitude((StringBuilder)null, arrayOfChar3, flags, -1, param1Locale));
/* 3600 */       } else if (param1Char == 'f') {
/*      */         
/* 3602 */         byte b = (param1Int == -1) ? 6 : param1Int;
/* 3603 */         int i = param1BigDecimal.scale();
/*      */         
/* 3605 */         if (i > b) {
/*      */           
/* 3607 */           int j = param1BigDecimal.precision();
/* 3608 */           if (j <= i) {
/*      */             
/* 3610 */             param1BigDecimal = param1BigDecimal.setScale(b, RoundingMode.HALF_UP);
/*      */           } else {
/* 3612 */             j -= i - b;
/* 3613 */             param1BigDecimal = new BigDecimal(param1BigDecimal.unscaledValue(), i, new MathContext(j));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3620 */         BigDecimalLayout bigDecimalLayout = new BigDecimalLayout(param1BigDecimal.unscaledValue(), param1BigDecimal.scale(), Formatter.BigDecimalLayoutForm.DECIMAL_FLOAT);
/*      */ 
/*      */         
/* 3623 */         char[] arrayOfChar = bigDecimalLayout.mantissa();
/* 3624 */         boolean bool = (bigDecimalLayout.scale() < b) ? (b - bigDecimalLayout.scale()) : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3631 */         if (bigDecimalLayout.scale() == 0 && (param1Flags.contains(Formatter.Flags.ALTERNATE) || bool)) {
/* 3632 */           arrayOfChar = addDot(bigDecimalLayout.mantissa());
/*      */         }
/*      */ 
/*      */         
/* 3636 */         arrayOfChar = trailingZeros(arrayOfChar, bool);
/*      */         
/* 3638 */         localizedMagnitude(param1StringBuilder, arrayOfChar, param1Flags, adjustWidth(this.width, param1Flags, param1Boolean), param1Locale);
/* 3639 */       } else if (param1Char == 'g') {
/* 3640 */         int i = param1Int;
/* 3641 */         if (param1Int == -1) {
/* 3642 */           i = 6;
/* 3643 */         } else if (param1Int == 0) {
/* 3644 */           i = 1;
/*      */         } 
/* 3646 */         BigDecimal bigDecimal1 = BigDecimal.valueOf(1L, 4);
/* 3647 */         BigDecimal bigDecimal2 = BigDecimal.valueOf(1L, -i);
/* 3648 */         if (param1BigDecimal.equals(BigDecimal.ZERO) || (param1BigDecimal
/* 3649 */           .compareTo(bigDecimal1) != -1 && param1BigDecimal
/* 3650 */           .compareTo(bigDecimal2) == -1)) {
/*      */ 
/*      */           
/* 3653 */           int j = -param1BigDecimal.scale() + param1BigDecimal.unscaledValue().toString().length() - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3665 */           i = i - j - 1;
/*      */           
/* 3667 */           print(param1StringBuilder, param1BigDecimal, param1Locale, param1Flags, 'f', i, param1Boolean);
/*      */         } else {
/*      */           
/* 3670 */           print(param1StringBuilder, param1BigDecimal, param1Locale, param1Flags, 'e', i - 1, param1Boolean);
/*      */         } 
/* 3672 */       } else if (param1Char == 'a') {
/*      */         assert false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private class BigDecimalLayout
/*      */     {
/*      */       private StringBuilder mant;
/*      */       private StringBuilder exp;
/*      */       private boolean dot = false;
/*      */       private int scale;
/*      */       
/*      */       public BigDecimalLayout(BigInteger param2BigInteger, int param2Int, Formatter.BigDecimalLayoutForm param2BigDecimalLayoutForm) {
/* 3686 */         layout(param2BigInteger, param2Int, param2BigDecimalLayoutForm);
/*      */       }
/*      */       
/*      */       public boolean hasDot() {
/* 3690 */         return this.dot;
/*      */       }
/*      */       
/*      */       public int scale() {
/* 3694 */         return this.scale;
/*      */       }
/*      */ 
/*      */       
/*      */       public char[] layoutChars() {
/* 3699 */         StringBuilder stringBuilder = new StringBuilder(this.mant);
/* 3700 */         if (this.exp != null) {
/* 3701 */           stringBuilder.append('E');
/* 3702 */           stringBuilder.append(this.exp);
/*      */         } 
/* 3704 */         return toCharArray(stringBuilder);
/*      */       }
/*      */       
/*      */       public char[] mantissa() {
/* 3708 */         return toCharArray(this.mant);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public char[] exponent() {
/* 3714 */         return toCharArray(this.exp);
/*      */       }
/*      */       
/*      */       private char[] toCharArray(StringBuilder param2StringBuilder) {
/* 3718 */         if (param2StringBuilder == null)
/* 3719 */           return null; 
/* 3720 */         char[] arrayOfChar = new char[param2StringBuilder.length()];
/* 3721 */         param2StringBuilder.getChars(0, arrayOfChar.length, arrayOfChar, 0);
/* 3722 */         return arrayOfChar;
/*      */       }
/*      */       
/*      */       private void layout(BigInteger param2BigInteger, int param2Int, Formatter.BigDecimalLayoutForm param2BigDecimalLayoutForm) {
/* 3726 */         char[] arrayOfChar = param2BigInteger.toString().toCharArray();
/* 3727 */         this.scale = param2Int;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3734 */         this.mant = new StringBuilder(arrayOfChar.length + 14);
/*      */         
/* 3736 */         if (param2Int == 0) {
/* 3737 */           int i = arrayOfChar.length;
/* 3738 */           if (i > 1) {
/* 3739 */             this.mant.append(arrayOfChar[0]);
/* 3740 */             if (param2BigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.SCIENTIFIC)
/* 3741 */             { this.mant.append('.');
/* 3742 */               this.dot = true;
/* 3743 */               this.mant.append(arrayOfChar, 1, i - 1);
/* 3744 */               this.exp = new StringBuilder("+");
/* 3745 */               if (i < 10) {
/* 3746 */                 this.exp.append("0").append(i - 1);
/*      */               } else {
/* 3748 */                 this.exp.append(i - 1);
/*      */               }  }
/* 3750 */             else { this.mant.append(arrayOfChar, 1, i - 1); }
/*      */           
/*      */           } else {
/* 3753 */             this.mant.append(arrayOfChar);
/* 3754 */             if (param2BigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.SCIENTIFIC)
/* 3755 */               this.exp = new StringBuilder("+00"); 
/*      */           } 
/*      */           return;
/*      */         } 
/* 3759 */         long l = -(param2Int) + (arrayOfChar.length - 1);
/* 3760 */         if (param2BigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.DECIMAL_FLOAT) {
/*      */           
/* 3762 */           int i = param2Int - arrayOfChar.length;
/* 3763 */           if (i >= 0) {
/*      */             
/* 3765 */             this.mant.append("0.");
/* 3766 */             this.dot = true;
/* 3767 */             while (i > 0) { this.mant.append('0'); i--; }
/* 3768 */              this.mant.append(arrayOfChar);
/*      */           }
/* 3770 */           else if (-i < arrayOfChar.length) {
/*      */             
/* 3772 */             this.mant.append(arrayOfChar, 0, -i);
/* 3773 */             this.mant.append('.');
/* 3774 */             this.dot = true;
/* 3775 */             this.mant.append(arrayOfChar, -i, param2Int);
/*      */           } else {
/*      */             
/* 3778 */             this.mant.append(arrayOfChar, 0, arrayOfChar.length);
/* 3779 */             for (byte b = 0; b < -param2Int; b++)
/* 3780 */               this.mant.append('0'); 
/* 3781 */             this.scale = 0;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 3786 */           this.mant.append(arrayOfChar[0]);
/* 3787 */           if (arrayOfChar.length > 1) {
/* 3788 */             this.mant.append('.');
/* 3789 */             this.dot = true;
/* 3790 */             this.mant.append(arrayOfChar, 1, arrayOfChar.length - 1);
/*      */           } 
/* 3792 */           this.exp = new StringBuilder();
/* 3793 */           if (l != 0L) {
/* 3794 */             long l1 = Math.abs(l);
/*      */             
/* 3796 */             this.exp.append((l < 0L) ? 45 : 43);
/* 3797 */             if (l1 < 10L)
/* 3798 */               this.exp.append('0'); 
/* 3799 */             this.exp.append(l1);
/*      */           } else {
/* 3801 */             this.exp.append("+00");
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     private int adjustWidth(int param1Int, Formatter.Flags param1Flags, boolean param1Boolean) {
/* 3808 */       int i = param1Int;
/* 3809 */       if (i != -1 && param1Boolean && param1Flags.contains(Formatter.Flags.PARENTHESES))
/* 3810 */         i--; 
/* 3811 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     private char[] addDot(char[] param1ArrayOfchar) {
/* 3816 */       char[] arrayOfChar = param1ArrayOfchar;
/* 3817 */       arrayOfChar = new char[param1ArrayOfchar.length + 1];
/* 3818 */       System.arraycopy(param1ArrayOfchar, 0, arrayOfChar, 0, param1ArrayOfchar.length);
/* 3819 */       arrayOfChar[arrayOfChar.length - 1] = '.';
/* 3820 */       return arrayOfChar;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private char[] trailingZeros(char[] param1ArrayOfchar, int param1Int) {
/* 3826 */       char[] arrayOfChar = param1ArrayOfchar;
/* 3827 */       if (param1Int > 0) {
/* 3828 */         arrayOfChar = new char[param1ArrayOfchar.length + param1Int];
/* 3829 */         System.arraycopy(param1ArrayOfchar, 0, arrayOfChar, 0, param1ArrayOfchar.length);
/* 3830 */         for (int i = param1ArrayOfchar.length; i < arrayOfChar.length; i++)
/* 3831 */           arrayOfChar[i] = '0'; 
/*      */       } 
/* 3833 */       return arrayOfChar;
/*      */     }
/*      */ 
/*      */     
/*      */     private void print(Calendar param1Calendar, char param1Char, Locale param1Locale) throws IOException {
/* 3838 */       StringBuilder stringBuilder = new StringBuilder();
/* 3839 */       print(stringBuilder, param1Calendar, param1Char, param1Locale);
/*      */ 
/*      */       
/* 3842 */       String str = justify(stringBuilder.toString());
/* 3843 */       if (this.f.contains(Formatter.Flags.UPPERCASE)) {
/* 3844 */         str = str.toUpperCase();
/*      */       }
/* 3846 */       Formatter.this.a.append(str); } private Appendable print(StringBuilder param1StringBuilder, Calendar param1Calendar, char param1Char, Locale param1Locale) throws IOException { int k; long l2; String[] arrayOfString; long l1; int j; TimeZone timeZone; int i; Formatter.Flags flags3; String str; Formatter.Flags flags2; boolean bool; Locale locale; byte b; Formatter.Flags flags1; StringBuilder stringBuilder;
/*      */       Formatter.Flags flags5;
/*      */       int m;
/*      */       DateFormatSymbols dateFormatSymbols;
/*      */       Formatter.Flags flags4;
/*      */       int n;
/*      */       Formatter.Flags flags6;
/* 3853 */       if (param1StringBuilder == null)
/* 3854 */         param1StringBuilder = new StringBuilder(); 
/* 3855 */       switch (param1Char)
/*      */       { case 'H':
/*      */         case 'I':
/*      */         case 'k':
/*      */         case 'l':
/* 3860 */           k = param1Calendar.get(11);
/* 3861 */           if (param1Char == 'I' || param1Char == 'l')
/* 3862 */             k = (k == 0 || k == 12) ? 12 : (k % 12); 
/* 3863 */           flags3 = (param1Char == 'H' || param1Char == 'I') ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE;
/*      */ 
/*      */ 
/*      */           
/* 3867 */           param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 2, param1Locale));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4055 */           return param1StringBuilder;case 'M': k = param1Calendar.get(12); flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 2, param1Locale)); return param1StringBuilder;case 'N': k = param1Calendar.get(14) * 1000000; flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 9, param1Locale)); return param1StringBuilder;case 'L': k = param1Calendar.get(14); flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 3, param1Locale)); return param1StringBuilder;case 'Q': l2 = param1Calendar.getTimeInMillis(); flags5 = Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, l2, flags5, this.width, param1Locale)); return param1StringBuilder;case 'p': arrayOfString = new String[] { "AM", "PM" }; if (param1Locale != null && param1Locale != Locale.US) { DateFormatSymbols dateFormatSymbols1 = DateFormatSymbols.getInstance(param1Locale); arrayOfString = dateFormatSymbols1.getAmPmStrings(); }  str = arrayOfString[param1Calendar.get(9)]; param1StringBuilder.append(str.toLowerCase((param1Locale != null) ? param1Locale : Locale.US)); return param1StringBuilder;case 's': l1 = param1Calendar.getTimeInMillis() / 1000L; flags5 = Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, l1, flags5, this.width, param1Locale)); return param1StringBuilder;case 'S': j = param1Calendar.get(13); flags2 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, j, flags2, 2, param1Locale)); return param1StringBuilder;case 'z': j = param1Calendar.get(15) + param1Calendar.get(16); bool = (j < 0) ? true : false; param1StringBuilder.append(bool ? 45 : 43); if (bool) j = -j;  m = j / 60000; n = m / 60 * 100 + m % 60; flags6 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, n, flags6, 4, param1Locale)); return param1StringBuilder;case 'Z': timeZone = param1Calendar.getTimeZone(); param1StringBuilder.append(timeZone.getDisplayName((param1Calendar.get(16) != 0), 0, (param1Locale == null) ? Locale.US : param1Locale)); return param1StringBuilder;case 'A': case 'a': i = param1Calendar.get(7); locale = (param1Locale == null) ? Locale.US : param1Locale; dateFormatSymbols = DateFormatSymbols.getInstance(locale); if (param1Char == 'A') { param1StringBuilder.append(dateFormatSymbols.getWeekdays()[i]); } else { param1StringBuilder.append(dateFormatSymbols.getShortWeekdays()[i]); }  return param1StringBuilder;case 'B': case 'b': case 'h': i = param1Calendar.get(2); locale = (param1Locale == null) ? Locale.US : param1Locale; dateFormatSymbols = DateFormatSymbols.getInstance(locale); if (param1Char == 'B') { param1StringBuilder.append(dateFormatSymbols.getMonths()[i]); } else { param1StringBuilder.append(dateFormatSymbols.getShortMonths()[i]); }  return param1StringBuilder;case 'C': case 'Y': case 'y': i = param1Calendar.get(1); b = 2; switch (param1Char) { case 'C': i /= 100; break;case 'y': i %= 100; break;case 'Y': b = 4; break; }  flags4 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags4, b, param1Locale)); return param1StringBuilder;case 'd': case 'e': i = param1Calendar.get(5); flags1 = (param1Char == 'd') ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 2, param1Locale)); return param1StringBuilder;case 'j': i = param1Calendar.get(6); flags1 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 3, param1Locale)); return param1StringBuilder;case 'm': i = param1Calendar.get(2) + 1; flags1 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 2, param1Locale)); return param1StringBuilder;case 'R': case 'T': i = 58; print(param1StringBuilder, param1Calendar, 'H', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'M', param1Locale); if (param1Char == 'T') { param1StringBuilder.append(i); print(param1StringBuilder, param1Calendar, 'S', param1Locale); }  return param1StringBuilder;case 'r': i = 58; print(param1StringBuilder, param1Calendar, 'I', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'M', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'S', param1Locale).append(' '); stringBuilder = new StringBuilder(); print(stringBuilder, param1Calendar, 'p', param1Locale); param1StringBuilder.append(stringBuilder.toString().toUpperCase((param1Locale != null) ? param1Locale : Locale.US)); return param1StringBuilder;case 'c': i = 32; print(param1StringBuilder, param1Calendar, 'a', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'b', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'd', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'T', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'Z', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'Y', param1Locale); return param1StringBuilder;case 'D': i = 47; print(param1StringBuilder, param1Calendar, 'm', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'd', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'y', param1Locale); return param1StringBuilder;case 'F': i = 45; print(param1StringBuilder, param1Calendar, 'Y', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'm', param1Locale).append(i); print(param1StringBuilder, param1Calendar, 'd', param1Locale); return param1StringBuilder; }  assert false; return param1StringBuilder; }
/*      */ 
/*      */     
/*      */     private void print(TemporalAccessor param1TemporalAccessor, char param1Char, Locale param1Locale) throws IOException {
/* 4059 */       StringBuilder stringBuilder = new StringBuilder();
/* 4060 */       print(stringBuilder, param1TemporalAccessor, param1Char, param1Locale);
/*      */       
/* 4062 */       String str = justify(stringBuilder.toString());
/* 4063 */       if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 4064 */         str = str.toUpperCase(); 
/* 4065 */       Formatter.this.a.append(str);
/*      */     }
/*      */ 
/*      */     
/*      */     private Appendable print(StringBuilder param1StringBuilder, TemporalAccessor param1TemporalAccessor, char param1Char, Locale param1Locale) throws IOException {
/* 4070 */       if (param1StringBuilder == null)
/* 4071 */         param1StringBuilder = new StringBuilder();  
/*      */       try { int k; long l2; String[] arrayOfString; long l1; int j; ZoneId zoneId; int i; Formatter.Flags flags3; String str; Formatter.Flags flags2; boolean bool; Locale locale; byte b; Formatter.Flags flags1; StringBuilder stringBuilder; Formatter.Flags flags5; int m; DateFormatSymbols dateFormatSymbols; Formatter.Flags flags4; int n; Formatter.Flags flags6;
/* 4073 */         switch (param1Char)
/*      */         { case 'H':
/* 4075 */             k = param1TemporalAccessor.get(ChronoField.HOUR_OF_DAY);
/* 4076 */             param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, Formatter.Flags.ZERO_PAD, 2, param1Locale));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4291 */             return param1StringBuilder;case 'k': k = param1TemporalAccessor.get(ChronoField.HOUR_OF_DAY); param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, Formatter.Flags.NONE, 2, param1Locale)); return param1StringBuilder;case 'I': k = param1TemporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM); param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, Formatter.Flags.ZERO_PAD, 2, param1Locale)); return param1StringBuilder;case 'l': k = param1TemporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM); param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, Formatter.Flags.NONE, 2, param1Locale)); return param1StringBuilder;case 'M': k = param1TemporalAccessor.get(ChronoField.MINUTE_OF_HOUR); flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 2, param1Locale)); return param1StringBuilder;case 'N': k = param1TemporalAccessor.get(ChronoField.MILLI_OF_SECOND) * 1000000; flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 9, param1Locale)); return param1StringBuilder;case 'L': k = param1TemporalAccessor.get(ChronoField.MILLI_OF_SECOND); flags3 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, k, flags3, 3, param1Locale)); return param1StringBuilder;case 'Q': l2 = param1TemporalAccessor.getLong(ChronoField.INSTANT_SECONDS) * 1000L + param1TemporalAccessor.getLong(ChronoField.MILLI_OF_SECOND); flags5 = Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, l2, flags5, this.width, param1Locale)); return param1StringBuilder;case 'p': arrayOfString = new String[] { "AM", "PM" }; if (param1Locale != null && param1Locale != Locale.US) { DateFormatSymbols dateFormatSymbols1 = DateFormatSymbols.getInstance(param1Locale); arrayOfString = dateFormatSymbols1.getAmPmStrings(); }  str = arrayOfString[param1TemporalAccessor.get(ChronoField.AMPM_OF_DAY)]; param1StringBuilder.append(str.toLowerCase((param1Locale != null) ? param1Locale : Locale.US)); return param1StringBuilder;case 's': l1 = param1TemporalAccessor.getLong(ChronoField.INSTANT_SECONDS); flags5 = Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, l1, flags5, this.width, param1Locale)); return param1StringBuilder;case 'S': j = param1TemporalAccessor.get(ChronoField.SECOND_OF_MINUTE); flags2 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, j, flags2, 2, param1Locale)); return param1StringBuilder;case 'z': j = param1TemporalAccessor.get(ChronoField.OFFSET_SECONDS); bool = (j < 0) ? true : false; param1StringBuilder.append(bool ? 45 : 43); if (bool) j = -j;  m = j / 60; n = m / 60 * 100 + m % 60; flags6 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, n, flags6, 4, param1Locale)); return param1StringBuilder;case 'Z': zoneId = param1TemporalAccessor.<ZoneId>query(TemporalQueries.zone()); if (zoneId == null) throw new IllegalFormatConversionException(param1Char, param1TemporalAccessor.getClass());  if (!(zoneId instanceof java.time.ZoneOffset) && param1TemporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) { Instant instant = Instant.from(param1TemporalAccessor); param1StringBuilder.append(TimeZone.getTimeZone(zoneId.getId()).getDisplayName(zoneId.getRules().isDaylightSavings(instant), 0, (param1Locale == null) ? Locale.US : param1Locale)); } else { param1StringBuilder.append(zoneId.getId()); }  return param1StringBuilder;case 'A': case 'a': i = param1TemporalAccessor.get(ChronoField.DAY_OF_WEEK) % 7 + 1; locale = (param1Locale == null) ? Locale.US : param1Locale; dateFormatSymbols = DateFormatSymbols.getInstance(locale); if (param1Char == 'A') { param1StringBuilder.append(dateFormatSymbols.getWeekdays()[i]); } else { param1StringBuilder.append(dateFormatSymbols.getShortWeekdays()[i]); }  return param1StringBuilder;case 'B': case 'b': case 'h': i = param1TemporalAccessor.get(ChronoField.MONTH_OF_YEAR) - 1; locale = (param1Locale == null) ? Locale.US : param1Locale; dateFormatSymbols = DateFormatSymbols.getInstance(locale); if (param1Char == 'B') { param1StringBuilder.append(dateFormatSymbols.getMonths()[i]); } else { param1StringBuilder.append(dateFormatSymbols.getShortMonths()[i]); }  return param1StringBuilder;case 'C': case 'Y': case 'y': i = param1TemporalAccessor.get(ChronoField.YEAR_OF_ERA); b = 2; switch (param1Char) { case 'C': i /= 100; break;case 'y': i %= 100; break;case 'Y': b = 4; break; }  flags4 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags4, b, param1Locale)); return param1StringBuilder;case 'd': case 'e': i = param1TemporalAccessor.get(ChronoField.DAY_OF_MONTH); flags1 = (param1Char == 'd') ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 2, param1Locale)); return param1StringBuilder;case 'j': i = param1TemporalAccessor.get(ChronoField.DAY_OF_YEAR); flags1 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 3, param1Locale)); return param1StringBuilder;case 'm': i = param1TemporalAccessor.get(ChronoField.MONTH_OF_YEAR); flags1 = Formatter.Flags.ZERO_PAD; param1StringBuilder.append(localizedMagnitude((StringBuilder)null, i, flags1, 2, param1Locale)); return param1StringBuilder;case 'R': case 'T': i = 58; print(param1StringBuilder, param1TemporalAccessor, 'H', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'M', param1Locale); if (param1Char == 'T') { param1StringBuilder.append(i); print(param1StringBuilder, param1TemporalAccessor, 'S', param1Locale); }  return param1StringBuilder;case 'r': i = 58; print(param1StringBuilder, param1TemporalAccessor, 'I', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'M', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'S', param1Locale).append(' '); stringBuilder = new StringBuilder(); print(stringBuilder, param1TemporalAccessor, 'p', param1Locale); param1StringBuilder.append(stringBuilder.toString().toUpperCase((param1Locale != null) ? param1Locale : Locale.US)); return param1StringBuilder;case 'c': i = 32; print(param1StringBuilder, param1TemporalAccessor, 'a', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'b', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'd', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'T', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'Z', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'Y', param1Locale); return param1StringBuilder;case 'D': i = 47; print(param1StringBuilder, param1TemporalAccessor, 'm', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'd', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'y', param1Locale); return param1StringBuilder;case 'F': i = 45; print(param1StringBuilder, param1TemporalAccessor, 'Y', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'm', param1Locale).append(i); print(param1StringBuilder, param1TemporalAccessor, 'd', param1Locale); return param1StringBuilder; }  assert false; } catch (DateTimeException dateTimeException) { throw new IllegalFormatConversionException(param1Char, param1TemporalAccessor.getClass()); }  return param1StringBuilder;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void failMismatch(Formatter.Flags param1Flags, char param1Char) {
/* 4297 */       String str = param1Flags.toString();
/* 4298 */       throw new FormatFlagsConversionMismatchException(str, param1Char);
/*      */     }
/*      */     
/*      */     private void failConversion(char param1Char, Object param1Object) {
/* 4302 */       throw new IllegalFormatConversionException(param1Char, param1Object.getClass());
/*      */     }
/*      */     
/*      */     private char getZero(Locale param1Locale) {
/* 4306 */       if (param1Locale != null && !param1Locale.equals(Formatter.this.locale())) {
/* 4307 */         DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(param1Locale);
/* 4308 */         return decimalFormatSymbols.getZeroDigit();
/*      */       } 
/* 4310 */       return Formatter.this.zero;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private StringBuilder localizedMagnitude(StringBuilder param1StringBuilder, long param1Long, Formatter.Flags param1Flags, int param1Int, Locale param1Locale) {
/* 4317 */       char[] arrayOfChar = Long.toString(param1Long, 10).toCharArray();
/* 4318 */       return localizedMagnitude(param1StringBuilder, arrayOfChar, param1Flags, param1Int, param1Locale);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private StringBuilder localizedMagnitude(StringBuilder param1StringBuilder, char[] param1ArrayOfchar, Formatter.Flags param1Flags, int param1Int, Locale param1Locale) {
/* 4325 */       if (param1StringBuilder == null)
/* 4326 */         param1StringBuilder = new StringBuilder(); 
/* 4327 */       int i = param1StringBuilder.length();
/*      */       
/* 4329 */       char c = getZero(param1Locale);
/*      */ 
/*      */       
/* 4332 */       char c1 = Character.MIN_VALUE;
/* 4333 */       int j = -1;
/* 4334 */       char c2 = Character.MIN_VALUE;
/*      */       
/* 4336 */       int k = param1ArrayOfchar.length;
/* 4337 */       int m = k; byte b;
/* 4338 */       for (b = 0; b < k; b++) {
/* 4339 */         if (param1ArrayOfchar[b] == '.') {
/* 4340 */           m = b;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 4345 */       if (m < k) {
/* 4346 */         if (param1Locale == null || param1Locale.equals(Locale.US)) {
/* 4347 */           c2 = '.';
/*      */         } else {
/* 4349 */           DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(param1Locale);
/* 4350 */           c2 = decimalFormatSymbols.getDecimalSeparator();
/*      */         } 
/*      */       }
/*      */       
/* 4354 */       if (param1Flags.contains(Formatter.Flags.GROUP)) {
/* 4355 */         if (param1Locale == null || param1Locale.equals(Locale.US)) {
/* 4356 */           c1 = ',';
/* 4357 */           j = 3;
/*      */         } else {
/* 4359 */           DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(param1Locale);
/* 4360 */           c1 = decimalFormatSymbols.getGroupingSeparator();
/* 4361 */           DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getIntegerInstance(param1Locale);
/* 4362 */           j = decimalFormat.getGroupingSize();
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 4367 */       for (b = 0; b < k; b++) {
/* 4368 */         if (b == m) {
/* 4369 */           param1StringBuilder.append(c2);
/*      */           
/* 4371 */           c1 = Character.MIN_VALUE;
/*      */         }
/*      */         else {
/*      */           
/* 4375 */           char c3 = param1ArrayOfchar[b];
/* 4376 */           param1StringBuilder.append((char)(c3 - 48 + c));
/* 4377 */           if (c1 != '\000' && b != m - 1 && (m - b) % j == 1) {
/* 4378 */             param1StringBuilder.append(c1);
/*      */           }
/*      */         } 
/*      */       } 
/* 4382 */       k = param1StringBuilder.length();
/* 4383 */       if (param1Int != -1 && param1Flags.contains(Formatter.Flags.ZERO_PAD))
/* 4384 */         for (b = 0; b < param1Int - k; b++) {
/* 4385 */           param1StringBuilder.insert(i, c);
/*      */         } 
/* 4387 */       return param1StringBuilder;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Flags
/*      */   {
/*      */     private int flags;
/* 4394 */     static final Flags NONE = new Flags(0);
/*      */ 
/*      */     
/* 4397 */     static final Flags LEFT_JUSTIFY = new Flags(1);
/* 4398 */     static final Flags UPPERCASE = new Flags(2);
/* 4399 */     static final Flags ALTERNATE = new Flags(4);
/*      */ 
/*      */     
/* 4402 */     static final Flags PLUS = new Flags(8);
/* 4403 */     static final Flags LEADING_SPACE = new Flags(16);
/* 4404 */     static final Flags ZERO_PAD = new Flags(32);
/* 4405 */     static final Flags GROUP = new Flags(64);
/* 4406 */     static final Flags PARENTHESES = new Flags(128);
/*      */ 
/*      */     
/* 4409 */     static final Flags PREVIOUS = new Flags(256);
/*      */     
/*      */     private Flags(int param1Int) {
/* 4412 */       this.flags = param1Int;
/*      */     }
/*      */     
/*      */     public int valueOf() {
/* 4416 */       return this.flags;
/*      */     }
/*      */     
/*      */     public boolean contains(Flags param1Flags) {
/* 4420 */       return ((this.flags & param1Flags.valueOf()) == param1Flags.valueOf());
/*      */     }
/*      */     
/*      */     public Flags dup() {
/* 4424 */       return new Flags(this.flags);
/*      */     }
/*      */     
/*      */     private Flags add(Flags param1Flags) {
/* 4428 */       this.flags |= param1Flags.valueOf();
/* 4429 */       return this;
/*      */     }
/*      */     
/*      */     public Flags remove(Flags param1Flags) {
/* 4433 */       this.flags &= param1Flags.valueOf() ^ 0xFFFFFFFF;
/* 4434 */       return this;
/*      */     }
/*      */     
/*      */     public static Flags parse(String param1String) {
/* 4438 */       char[] arrayOfChar = param1String.toCharArray();
/* 4439 */       Flags flags = new Flags(0);
/* 4440 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/* 4441 */         Flags flags1 = parse(arrayOfChar[b]);
/* 4442 */         if (flags.contains(flags1))
/* 4443 */           throw new DuplicateFormatFlagsException(flags1.toString()); 
/* 4444 */         flags.add(flags1);
/*      */       } 
/* 4446 */       return flags;
/*      */     }
/*      */ 
/*      */     
/*      */     private static Flags parse(char param1Char) {
/* 4451 */       switch (param1Char) { case '-':
/* 4452 */           return LEFT_JUSTIFY;
/* 4453 */         case '#': return ALTERNATE;
/* 4454 */         case '+': return PLUS;
/* 4455 */         case ' ': return LEADING_SPACE;
/* 4456 */         case '0': return ZERO_PAD;
/* 4457 */         case ',': return GROUP;
/* 4458 */         case '(': return PARENTHESES;
/* 4459 */         case '<': return PREVIOUS; }
/*      */       
/* 4461 */       throw new UnknownFormatFlagsException(String.valueOf(param1Char));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static String toString(Flags param1Flags) {
/* 4467 */       return param1Flags.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 4471 */       StringBuilder stringBuilder = new StringBuilder();
/* 4472 */       if (contains(LEFT_JUSTIFY)) stringBuilder.append('-'); 
/* 4473 */       if (contains(UPPERCASE)) stringBuilder.append('^'); 
/* 4474 */       if (contains(ALTERNATE)) stringBuilder.append('#'); 
/* 4475 */       if (contains(PLUS)) stringBuilder.append('+'); 
/* 4476 */       if (contains(LEADING_SPACE)) stringBuilder.append(' '); 
/* 4477 */       if (contains(ZERO_PAD)) stringBuilder.append('0'); 
/* 4478 */       if (contains(GROUP)) stringBuilder.append(','); 
/* 4479 */       if (contains(PARENTHESES)) stringBuilder.append('('); 
/* 4480 */       if (contains(PREVIOUS)) stringBuilder.append('<'); 
/* 4481 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Conversion
/*      */   {
/*      */     static final char DECIMAL_INTEGER = 'd';
/*      */     
/*      */     static final char OCTAL_INTEGER = 'o';
/*      */     
/*      */     static final char HEXADECIMAL_INTEGER = 'x';
/*      */     
/*      */     static final char HEXADECIMAL_INTEGER_UPPER = 'X';
/*      */     
/*      */     static final char SCIENTIFIC = 'e';
/*      */     
/*      */     static final char SCIENTIFIC_UPPER = 'E';
/*      */     
/*      */     static final char GENERAL = 'g';
/*      */     
/*      */     static final char GENERAL_UPPER = 'G';
/*      */     
/*      */     static final char DECIMAL_FLOAT = 'f';
/*      */     
/*      */     static final char HEXADECIMAL_FLOAT = 'a';
/*      */     
/*      */     static final char HEXADECIMAL_FLOAT_UPPER = 'A';
/*      */     
/*      */     static final char CHARACTER = 'c';
/*      */     
/*      */     static final char CHARACTER_UPPER = 'C';
/*      */     
/*      */     static final char DATE_TIME = 't';
/*      */     
/*      */     static final char DATE_TIME_UPPER = 'T';
/*      */     
/*      */     static final char BOOLEAN = 'b';
/*      */     static final char BOOLEAN_UPPER = 'B';
/*      */     static final char STRING = 's';
/*      */     static final char STRING_UPPER = 'S';
/*      */     static final char HASHCODE = 'h';
/*      */     static final char HASHCODE_UPPER = 'H';
/*      */     static final char LINE_SEPARATOR = 'n';
/*      */     static final char PERCENT_SIGN = '%';
/*      */     
/*      */     static boolean isValid(char param1Char) {
/* 4528 */       return (isGeneral(param1Char) || isInteger(param1Char) || isFloat(param1Char) || isText(param1Char) || param1Char == 't' || 
/* 4529 */         isCharacter(param1Char));
/*      */     }
/*      */ 
/*      */     
/*      */     static boolean isGeneral(char param1Char) {
/* 4534 */       switch (param1Char) {
/*      */         case 'B':
/*      */         case 'H':
/*      */         case 'S':
/*      */         case 'b':
/*      */         case 'h':
/*      */         case 's':
/* 4541 */           return true;
/*      */       } 
/* 4543 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isCharacter(char param1Char) {
/* 4549 */       switch (param1Char) {
/*      */         case 'C':
/*      */         case 'c':
/* 4552 */           return true;
/*      */       } 
/* 4554 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isInteger(char param1Char) {
/* 4560 */       switch (param1Char) {
/*      */         case 'X':
/*      */         case 'd':
/*      */         case 'o':
/*      */         case 'x':
/* 4565 */           return true;
/*      */       } 
/* 4567 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isFloat(char param1Char) {
/* 4573 */       switch (param1Char) {
/*      */         case 'A':
/*      */         case 'E':
/*      */         case 'G':
/*      */         case 'a':
/*      */         case 'e':
/*      */         case 'f':
/*      */         case 'g':
/* 4581 */           return true;
/*      */       } 
/* 4583 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isText(char param1Char) {
/* 4589 */       switch (param1Char) {
/*      */         case '%':
/*      */         case 'n':
/* 4592 */           return true;
/*      */       } 
/* 4594 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DateTime
/*      */   {
/*      */     static final char HOUR_OF_DAY_0 = 'H';
/*      */     
/*      */     static final char HOUR_0 = 'I';
/*      */     
/*      */     static final char HOUR_OF_DAY = 'k';
/*      */     
/*      */     static final char HOUR = 'l';
/*      */     
/*      */     static final char MINUTE = 'M';
/*      */     
/*      */     static final char NANOSECOND = 'N';
/*      */     
/*      */     static final char MILLISECOND = 'L';
/*      */     
/*      */     static final char MILLISECOND_SINCE_EPOCH = 'Q';
/*      */     
/*      */     static final char AM_PM = 'p';
/*      */     
/*      */     static final char SECONDS_SINCE_EPOCH = 's';
/*      */     
/*      */     static final char SECOND = 'S';
/*      */     
/*      */     static final char TIME = 'T';
/*      */     
/*      */     static final char ZONE_NUMERIC = 'z';
/*      */     
/*      */     static final char ZONE = 'Z';
/*      */     static final char NAME_OF_DAY_ABBREV = 'a';
/*      */     static final char NAME_OF_DAY = 'A';
/*      */     static final char NAME_OF_MONTH_ABBREV = 'b';
/*      */     static final char NAME_OF_MONTH = 'B';
/*      */     static final char CENTURY = 'C';
/*      */     static final char DAY_OF_MONTH_0 = 'd';
/*      */     static final char DAY_OF_MONTH = 'e';
/*      */     static final char NAME_OF_MONTH_ABBREV_X = 'h';
/*      */     static final char DAY_OF_YEAR = 'j';
/*      */     static final char MONTH = 'm';
/*      */     static final char YEAR_2 = 'y';
/*      */     static final char YEAR_4 = 'Y';
/*      */     static final char TIME_12_HOUR = 'r';
/*      */     static final char TIME_24_HOUR = 'R';
/*      */     static final char DATE_TIME = 'c';
/*      */     static final char DATE = 'D';
/*      */     static final char ISO_STANDARD_DATE = 'F';
/*      */     
/*      */     static boolean isValid(char param1Char) {
/* 4647 */       switch (param1Char) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 'A':
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'D':
/*      */         case 'F':
/*      */         case 'H':
/*      */         case 'I':
/*      */         case 'L':
/*      */         case 'M':
/*      */         case 'N':
/*      */         case 'Q':
/*      */         case 'R':
/*      */         case 'S':
/*      */         case 'T':
/*      */         case 'Y':
/*      */         case 'Z':
/*      */         case 'a':
/*      */         case 'b':
/*      */         case 'c':
/*      */         case 'd':
/*      */         case 'e':
/*      */         case 'h':
/*      */         case 'j':
/*      */         case 'k':
/*      */         case 'l':
/*      */         case 'm':
/*      */         case 'p':
/*      */         case 'r':
/*      */         case 's':
/*      */         case 'y':
/*      */         case 'z':
/* 4692 */           return true;
/*      */       } 
/* 4694 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Formatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */