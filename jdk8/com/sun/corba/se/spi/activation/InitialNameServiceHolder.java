package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/InitialNameServiceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u151/9699/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Tuesday, September 5, 2017 7:35:43 PM PDT
*/

public final class InitialNameServiceHolder implements org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.spi.activation.InitialNameService value = null;

  public InitialNameServiceHolder ()
  {
  }

  public InitialNameServiceHolder (com.sun.corba.se.spi.activation.InitialNameService initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.spi.activation.InitialNameServiceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.spi.activation.InitialNameServiceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.spi.activation.InitialNameServiceHelper.type ();
  }

}
