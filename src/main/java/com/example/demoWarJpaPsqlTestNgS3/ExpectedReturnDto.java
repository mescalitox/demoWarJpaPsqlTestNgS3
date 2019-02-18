/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.io.Serializable;

/**
 * @author jaubert
 *
 */
public class ExpectedReturnDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8127759895938958236L;

	private String status;

	// private String infoStatus;

	private double executionTime;

	private T returnObject;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the returnObject
	 */
	public T getReturn() {
		return returnObject;
	}

	/**
	 * @param returnObject
	 *            the returnObject to set
	 */
	public void setReturn(T returnObject) {
		this.returnObject = returnObject;
	}

	/**
	 * @return the executionTime
	 */
	public double getExecutionTime() {
		return executionTime;
	}

	/**
	 * @param executionTime
	 *            the executionTime to set
	 */
	public void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
	}

}
