// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package java.rmi.activation;

public final class ActivationGroup_Stub
    extends java.rmi.server.RemoteStub
    implements java.rmi.activation.ActivationInstantiator, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_newInstance_0;
    
    static {
	try {
	    $method_newInstance_0 = java.rmi.activation.ActivationInstantiator.class.getMethod("newInstance", new java.lang.Class[] {java.rmi.activation.ActivationID.class, java.rmi.activation.ActivationDesc.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public ActivationGroup_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of newInstance(ActivationID, ActivationDesc)
    public java.rmi.MarshalledObject newInstance(java.rmi.activation.ActivationID $param_ActivationID_1, java.rmi.activation.ActivationDesc $param_ActivationDesc_2)
	throws java.rmi.RemoteException, java.rmi.activation.ActivationException
    {
	try {
	    Object $result = ref.invoke(this, $method_newInstance_0, new java.lang.Object[] {$param_ActivationID_1, $param_ActivationDesc_2}, -5274445189091581345L);
	    return ((java.rmi.MarshalledObject) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.rmi.activation.ActivationException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
