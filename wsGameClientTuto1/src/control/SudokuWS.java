package control;

import java.net.URL;

import javax.xml.namespace.QName;

import model.Sudoku;

import org.apache.axis.client.Call;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.utils.JavaUtils;

public class SudokuWS {
	private Call call;

	public SudokuWS() {
		try {
			call = new Call(new URL(
					"http://localhost:8080/wsGame/services/Game"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean remoteCheckValid(Sudoku sudo){ 
		boolean result = false;
        try{
            //prepare the operation
            OperationDesc oper = new OperationDesc();
            //first parameter
            ParameterDesc param = new ParameterDesc(new QName("http://webservices", "sudo"), ParameterDesc.IN, 
                    new QName("http://www.w3.org/2001/XMLSchema", "Sudoku"), Sudoku.class, false, false);
            oper.addParameter(param);
            //return parameter
            oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
            oper.setReturnClass(boolean.class);
            oper.setReturnQName(new QName("http://webservices", "checkValidReturn"));
 
            //create Call object
            call.setOperation(oper);
            call.setOperationName(new QName("http://webservices", "checkValid"));
 
            //invoke the call
            Object resp = call.invoke(new Object[] {sudo});
 
            //extract the result
            if (!(resp instanceof java.rmi.RemoteException) ){
                try {// convert by java.lang
                    result = (boolean) resp;
                } catch (Exception e) {//convert by axis
                    result = ((boolean)JavaUtils.convert(resp, boolean.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	
	public boolean remoteCheckWin(Sudoku sudo){ 
		boolean result = false;
        try{
            //prepare the operation
            OperationDesc oper = new OperationDesc();
            //first parameter
            ParameterDesc param = new ParameterDesc(new QName("http://webservices", "sudo"), ParameterDesc.IN, 
                    new QName("http://www.w3.org/2001/XMLSchema", "Sudoku"), Sudoku.class, false, false);
            oper.addParameter(param);
            //return parameter
            oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
            oper.setReturnClass(boolean.class);
            oper.setReturnQName(new QName("http://webservices", "checkWinReturn"));
 
            //create Call object
            call.setOperation(oper);
            call.setOperationName(new QName("http://webservices", "checkWin"));
 
            //invoke the call
            Object resp = call.invoke(new Object[] {sudo});
 
            //extract the result
            if (!(resp instanceof java.rmi.RemoteException) ){
                try {// convert by java.lang
                    result = (boolean) resp;
                } catch (Exception e) {//convert by axis
                    result = ((boolean)JavaUtils.convert(resp, boolean.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
