package org.extreme.script.parser;


public class ColumnRowLocation {
	public final static ColumnRowLocation ALL = new ColumnRowLocation(null);
	
	private LocationDim[] dims;
	
	public ColumnRowLocation(LocationDim[] dims) {
		this.dims = dims;
	}
	
	public LocationDim[] getDims() {
		return dims;
	}

	public String toString() {
		if(this == ALL) {
			return "!0";
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < dims.length; i++) {
			if(i > 0) {
				sb.append(',');
			}
			sb.append(dims[i]);
		}
		
		return sb.toString();
	}
	
	public void changeColumnRow(int rowIndex, int rowChanged, int columnIndex, int colChanged){
		if (this != ALL){
			for(int i = 0; i < dims.length; i++) {
				dims[i].changeColumnRow(rowIndex, rowChanged, columnIndex, colChanged);
		}
		}
	}
}
