
header {package org.extreme.script.parser;}

class XParser extends Parser;
options {
	buildAST = false;
	defaultErrorHandler = false;
}

tokens {
	// to avoid warning in this editor-antlr studio
	DOT;
	FLOT_NUM;
}

parse returns [String exp]
	{String con;}
	:	con = conditionOr
		EOF
	{exp = con;}
	;

protected conditionOr returns [String conOr]
	{
		java.util.List list = new java.util.ArrayList();
		String and;
	}
	:	and = conditionAnd {list.add(and);}
		(LOR and = conditionAnd {list.add(and);})*
	{
		if (list.size() == 1) {
			conOr = (String)list.get(0);
		} else {
			conOr = ParseUtils.orString(list);
		}
	}
	;

protected conditionAnd returns [String conAnd]
	{
		java.util.List list = new java.util.ArrayList();
		String rel;
	}
	:	rel = relation {list.add(rel);}
		(LAND rel = relation {list.add(rel);})*
	{
		if (list.size() == 1) {
			conAnd = (String)list.get(0);
		} else {
			conAnd = ParseUtils.andString(list);
		}
	}
	;

protected relation returns [String rel]
	{
		String left = null;
		String op = null;
		String right = null;
	}
	:	left = add (
	 (a:EQUAL {op = "=";}
	|	a2 : EQUAL2 {op = a2.getText();}
	|	b : NOT_EQUAL {op = b.getText();}
	|	c : NOT_EQUAL2 {op = c.getText();}
	|	d : GE {op = d.getText();}
	|	e : GT {op = e.getText();}
	|	f : LE {op = f.getText();}
	|	g : LT {op = g.getText();}) 
	right = add )?
	{
		if (op == null) {
			rel = left;
		} else {
			rel = ParseUtils.relationString(left, op, right);
		}
	}
	;

protected add returns [String add]
	{
		java.util.List multi_list = new java.util.ArrayList();
		java.util.List op_list = new java.util.ArrayList();
		String multi;
	}
	:	multi = multi {multi_list.add(multi);}
		(
			(p:PLUS {op_list.add(p.getText());}
			|m:MINUS {op_list.add(m.getText());}
			)
		
		multi = multi {multi_list.add(multi);})*
	{
		if (multi_list.size() == 1) {
			add = multi; // (String)multi_list.get(0);
		} else {
			add = ParseUtils.addString(multi_list, op_list);
		}
	}
	;

protected multi returns [String multi]
	{
		java.util.List power_list = new java.util.ArrayList();
		java.util.List op_list = new java.util.ArrayList();
		String pow;
	}
	:	pow = power {power_list.add(pow);}
		(
			(m:STAR {op_list.add(m.getText());}
			|d:DIV {op_list.add(d.getText());}
			|mo:MOD {op_list.add(mo.getText());}
			)
		
		pow = power {power_list.add(pow);})*
	{
		if (power_list.size() == 1) {
			multi = pow;// power_list.get(0);
		} else {
			multi = ParseUtils.multiString(power_list, op_list);
		}
	}
	;

protected power returns [String power]
	{
		java.util.List list = new java.util.ArrayList();
		String unary;
	}
	:	unary = unary {list.add(unary);}
		(
		POWER
		
		unary = unary{list.add(unary);})*
	{
		if (list.size() == 1) {
			power = unary; // power_list.get(0);
		} else {
			power = ParseUtils.powerString(list);
		}
	}
	;

protected unary returns [String unary]
	{String atom; String op = null;}
	:	(e:LNOT {op = e.getText();}
	|	p:PLUS {op = p.getText();}
	|	m:MINUS {op = m.getText();})?
		(atom = ident_prefix
	|	atom = str_literal
	|   atom = sl_or_sil
	|	atom = num_literal
	|	atom = columnrow_address
	|	atom = column_index
	|   atom = layer_index
	|	atom = closure
	|	atom = array
	|   atom = page_cr)
	{
		if (op == null) {
			unary = atom;
		} else {
			unary = ParseUtils.unary(op, atom);
		}
	}
	;

// should return DatasetFunctionCall also
protected ident_prefix returns [String atom] 
	{// some variables to test this node
		// abc | fn(2,3) | ds1.fn(1,2)
		String f_name = null; // first name		
	}
	:
	id : IDENT {f_name = id.getText();} 
	(
	atom = ident_fn[f_name]
	|
	atom = ident_sheet[f_name]
	|
	atom = ident_block[f_name]
	|
	atom = ident_cr[f_name]
	)
	;

// functions:fn(arg, arg) or ds1.fn(arg, arg)
protected ident_fn [java.lang.String f_name] returns [String atom]
	{
		String s_name = null; // second name
		java.util.List args = null; 
		String arg;
	}
	:
	(
	(DOT id2:IDENT {s_name = id2.getText();})?
	LPAREN {args = new java.util.ArrayList();}
	(arg = conditionOr {args.add(arg);} (COMMA {args.add(null);} (arg = conditionOr {args.set(args.size() - 1, arg);})?)*)?
	RPAREN
	{// according to the variables above do different stuff
		String[] ex_array = (String[])args.toArray(new String[args.size()]);
		if(s_name != null) {
			atom = ParseUtils.newInstance("DatasetFunctionCall", 
				new String[] {org.extreme.commons.util.Utils.quote(f_name), org.extreme.commons.util.Utils.quote(s_name), ParseUtils.toObjectArrayString(ex_array)});
		} else {
			if (MicroUtils.isMicro(f_name)) {
				atom = MicroUtils.toMicro(f_name, ex_array);
			} else {
				atom = ParseUtils.newInstance("FunctionCall", new String[] {org.extreme.commons.util.Utils.quote(f_name), ParseUtils.toObjectArrayString(ex_array)});
			}
		}
	}
	)
	;

// sheet1!a1:b2
protected ident_sheet [java.lang.String sheet_name] returns [String atom]
	{
		String cr;
		String x_name = null; // first name
	}
	:
	LNOT
	id : IDENT {x_name = id.getText();} 
	cr = ident_cr[x_name]
	{
		atom = ParseUtils.newInstance("SheetIntervalLiteral", new String[] {sheet_name, ParseUtils.cr(cr)});
	}
	;

// block1~a1
protected ident_block [java.lang.String block_name] returns [String atom]
	{
		String cr;
		String x_name = null; // first name
	}
	:
	WAVE
	id : IDENT {x_name = id.getText();} 
	cr = ident_cr[x_name]
	{
		atom = ParseUtils.newInstance("BlockIntervalLiteral", new String[] {block_name, ParseUtils.cr(cr)});
		//ParseUtils.fnString("new BlockIntervalLiteral(block_name, cr)");
	}
	;
	
// ambiguity or ColumnRow related
protected ident_cr [java.lang.String f_name] returns [String atom]
	{
		if(!f_name.matches("^[a-zA-Z]+[1-9]\\d*$")) {
			return ParseUtils.newInstance("Ambiguity", new String[] {org.extreme.commons.util.Utils.quote(f_name)});
		}
		org.extreme.commons.ColumnRow targetColumnRow = org.extreme.commons.ColumnRow.valueOf(f_name);
		// alex: some string like "MOTHERLENGTH1" -> {column:-714362997, row:0}
		if (targetColumnRow.getColumn() < 0 || targetColumnRow.getRow() < 0) {
			return ParseUtils.newInstance("Ambiguity", new String[] {org.extreme.commons.util.Utils.quote(f_name)});
		}
		String from = null;
		String to = null;;
	}
	:
	from = columnrow_literal[targetColumnRow] 
	(COLON i2:IDENT {targetColumnRow = org.extreme.commons.ColumnRow.valueOf(i2.getText());} to = columnrow_literal[targetColumnRow])?
	{atom = ParseUtils.newInstance("ColumnRowRange", new String[] {from, to});}
	;

/*
 * 
 */
protected columnrow_literal [org.extreme.commons.ColumnRow targetColumnRow] returns [String crl]
	{
		String left = null;
		String up = null;
		String ex = null;
		
		String start = null;
		String end = null;
	}
	:
	(LBRACK (left = columnrow_location {if(left == "ColumnRowLocation.ALL") up = "ColumnRowLocation.ALL";})? (SEMI {up = null;} (up = columnrow_location)?)? RBRACK)?
	(LCURLY (ex = conditionOr)? RCURLY 
		(LBRACK (start = conditionOr) (DCOLON end = conditionOr)? RBRACK)?
	)?
	{
		crl = ParseUtils.newInstance("ColumnRowLiteral", new String[] {ParseUtils.cr(targetColumnRow), left, up, ex, start, end});
	}
	;
	
/*
 * !0    ||     a1:1;b2:-1
 */
protected columnrow_location returns [String loc = null]
	{
		java.util.List dim_list = new java.util.ArrayList();
		String dim;
	}
	:	
		(ALLL | ALLL2) {loc = "ColumnRowLocation.ALL";}
	|	dim = location_dim {dim_list.add(dim);} (COMMA dim = location_dim {dim_list.add(dim);})*
	{
		if(loc == null) {
			loc = ParseUtils.newInstance("ColumnRowLocation", ParseUtils.toTypeArrayString((String[])dim_list.toArray(new String[dim_list.size()]), "String"));
		}
	}
	;
	
/*
 * a1:!-1
 */
protected location_dim returns [String dim]
	:
	{
		org.extreme.commons.ColumnRow columnrow = null;
		String op = "LocationDim.ABSOLUTE";
		int index = 0;
		boolean absolute = false;
	}
	c:IDENT {columnrow = org.extreme.commons.ColumnRow.valueOf(c.getText());}
	(COLON (LNOT {absolute = true;})? (PLUS {op = "LocationDim.PLUS";} | MINUS {op = "LocationDim.MINUS";})? 
	i:INT_NUM {index = Integer.parseInt(i.getText());})?
	{
		if(absolute) {
			if(op == "LocationDim.MINUS") {
				index = -index;
			}
			op = "LocationDim.ABSOLUTE";
		}
		dim = ParseUtils.newInstance("LocationDim", new String[] {ParseUtils.cr(columnrow), op, ParseUtils.Int(index)});
	}
	;

/*
 * "abc"
 */
protected str_literal returns [String sl]
	:	s:STRING_LITERAL_DSQ
	{
		String str = s.getText();
		sl = org.extreme.commons.util.Utils.quote(str.substring(1, str.length() - 1));// "new StringLiteral()";
	}
	;
	
/*
 * 'abc' / 'abc'!a1:b1
 */
protected sl_or_sil returns [String atom]
	{
		String text = null;
	}
	:	s:STRING_LITERAL_SSQ
	{
		text = s.getText();
		text = text.substring(1, text.length() - 1);
	}
	(
		atom = ident_sheet[text]
		|
		atom = sil_ssq[text]
	)
	;
	
protected sil_ssq [java.lang.String text] returns [String sl]
	{
		sl = org.extreme.commons.util.Utils.quote(text);//"new StringLiteral(text)";
	}
	:
	;

/*
 * 123
 */
protected num_literal returns [String nl]
	:	i:INT_NUM {nl = ParseUtils.newInstance("NumberLiteral", org.extreme.commons.util.Utils.quote(i.getText()));}
	|	f:FLOT_NUM {nl = ParseUtils.newInstance("NumberLiteral", org.extreme.commons.util.Utils.quote(f.getText()));}
	;

/*
 * [1,2,3]
 */
protected array returns [String ae]
	{java.util.List list = new java.util.ArrayList();String ex;}
	:	LBRACK (ex = conditionOr {list.add(ex);} 
	(COMMA {list.add(null);} (ex = conditionOr {list.set(list.size() - 1, ex);})?)*)?
	RBRACK
	{ae = ParseUtils.newInstance("ArrayExpression", ParseUtils.toTypeArrayString((String[])list.toArray(new String[list.size()]), "String"));}
	;

/*
 * (...)
 */
protected closure returns [String close]
	{String ex;}
	:	LPAREN ex = conditionOr RPAREN
	{close = "(" + ex + ")";}
	;
	
/*
 * {A1} | {A1:B2}
 */
protected page_cr returns [String cr]
	{
		String atom;
		String f_name = null;
	}
	:	LCURLY
	id : IDENT {f_name = id.getText();} 
	atom = ident_cr[f_name]
	RCURLY
	{
		cr = ParseUtils.newInstance("ColumnRowRangeInPage", org.extreme.commons.util.Utils.quote(atom));
	}
	;

/*
 * &a1
 */
protected columnrow_address returns [String ads]
	:	i:CR_ADRESS
	{
		ads = ParseUtils.newInstance("CRAddress", ParseUtils.cr(i.getText().substring(1)));
	}
	;

/*
 * #1
 */
protected column_index returns [String idx]
	:	SHARP i:INT_NUM
	{
		idx = ParseUtils.newInstance("AtomColumnIndex", i.getText()/* int*/);	
	}
	;

/*
 * @1
 */
protected layer_index returns [String idx]
	:	AT i:INT_NUM
	{
		idx = ParseUtils.newInstance("AtomLayerIndex", i.getText()/* int*/);
	}
	;

class XLexer extends Lexer;

options {
	k = 2;
	charVocabulary = '\u0003'..'\uFFFE';//support all unicode
}


// OPERATORS
QUESTION	:	'?'	;
LPAREN		:	'('	;
RPAREN		:	')'	;
LBRACK		:	'['	;
RBRACK		:	']'	;
LCURLY		:	'{'	;
RCURLY		:	'}'	;
COLON		:	':'	;
DCOLON		:	"::"	;
COMMA		:	','	;
//DOT			:	'.'	;
EQUAL		:	"=="	;
EQUAL2		:	'='		;
LNOT		:	'!'	;
NOT_EQUAL	:	"!="	;
NOT_EQUAL2	:	"<>"	;
DIV		:	'/'	;
PLUS		:	'+'	;
MINUS		:	'-'	;
STAR		:	'*'	;
MOD		:	'%'	;
POWER		:	'^'	;
GE		:	">="	;
GT		:	'>'	;
LE		:	"<="	;
LT		:	'<'	;
BOR		:	'|'	;
BAND		:	'&'	;
LOR		:	"||"	;
LAND		:	"&&"	;
SEMI		:	';'	;
SHARP		:	'#'	;
AT          :   '@';
WAVE  	: 	'~';

ALLL		:	'!' '0' ;
ALLL2		:	'`' '0'	;

IDENT 
    :   Char (Char|(DIGIT))* 
    ;

CR_ADRESS
	:	BAND(LETTER)+(DIGIT)+
	;

INT_NUM
	:	(DIGIT)+ ('.' (DIGIT)*{_ttype = FLOT_NUM;})? (Exponent{_ttype = FLOT_NUM;})? {
			//System.out.println("number like 1.2e+12");
		}
	|	'.' {_ttype = DOT;}
		((DIGIT)+ (Exponent)?{_ttype = FLOT_NUM;})? {
			//System.out.println("number like .3e-2");
		}
	;


STRING_LITERAL_DSQ
	:	'"' (ESC|~'"')* '"'
	;
	
STRING_LITERAL_SSQ
	:	'\'' (ESC | ~'\'')* '\''
	;

/**
 * 'a'..'z', '_', 'A'..'Z' and others without number
 */
protected Char
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

protected ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ XDIGIT XDIGIT XDIGIT XDIGIT
		|	'0'..'3'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
			)?
		)
	;

protected Exponent 
	: ('e'|'E') ('+'|'-')? (DIGIT)+ 
	;

protected LETTER
	:	'A' .. 'Z'
	|	'a' .. 'z'
	;

protected DIGIT
	:	'0' .. '9'
	;

protected XDIGIT 
	:	'0' .. '9'
	|	'a' .. 'f'
	|	'A' .. 'F'
	;

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;