java.util.Map variableMap = new java.util.HashMap();

Object[] args = new Object[] {${args}};
for (int i = 0; i < args.length - 1; i += 2) {
	if (args[i] instanceof Node) {
		String variableName = ((Node)args[i]).exString(c);
		if (VariablePattern.matcher(variableName).find()) {
			variableMap.put(variableName, c.eval(args[i + 1]));
        }
    }
}

ParameterMapNameSpace nameSpace = ParameterMapNameSpace.create(variableMap);
c.pushNameSpace(nameSpace);

Object o = ${expr};

if (o instanceof Node) {
	o = c.eval((Node)o);
}
c.removeNameSpace(nameSpace);
return o;