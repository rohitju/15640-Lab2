package ds.rmi.util;

import java.io.Serializable;

public class RMIMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	Command cmd; // bind,lookup,list,rebind,unbind
	Object payload;
	
	public RMIMessage(Command c, Object p) {
		cmd = c;
		payload = p;
	}
	
	public Object getPayload(){
		return payload;
	}
	
	public Command getCommand(){
		return cmd;
	}
}
