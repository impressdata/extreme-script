package org.extreme.script;


public class CurrentValueScope implements Scope {
	private Object currentValue;
	
	public CurrentValueScope(Object currentValue) {
		this.currentValue = currentValue;
	}

	public Object getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Object currentValue) {
		this.currentValue = currentValue;
	}

	public Object getVariable(Object var, Calculator calculator) {
		if(ScriptConstants.CURRENT.equals(var)) {
			return this.currentValue;
		}
		
		return null;
	}

	public Function getMethod(Object var, Calculator calculator) {
		// TODO Auto-generated method stub
		return null;
	}
}
