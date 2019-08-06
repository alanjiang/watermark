package com.hanniu.json;

public class BaseResultMessage<T> extends BaseMessage {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	
	
}
