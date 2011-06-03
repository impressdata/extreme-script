package org.extreme.script;


public class CurrentValueNameSpace extends AbstractNameSpace {
	private Object currentValue;
	
	public CurrentValueNameSpace(Object currentValue) {
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
}
