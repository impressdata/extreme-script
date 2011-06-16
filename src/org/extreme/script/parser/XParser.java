// $ANTLR 2.7.7 (2006-11-01): "x.g" -> "XParser.java"$
package org.extreme.script.parser;
import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class XParser extends antlr.LLkParser       implements XParserTokenTypes
 {

protected XParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public XParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected XParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public XParser(TokenStream lexer) {
  this(lexer,1);
}

public XParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final String  parse() throws RecognitionException, TokenStreamException {
		String exp;
		
		String con;
		
		con=conditionOr();
		match(Token.EOF_TYPE);
		exp = con;
		return exp;
	}
	
	protected final String  conditionOr() throws RecognitionException, TokenStreamException {
		String conOr;
		
		
				java.util.List list = new java.util.ArrayList();
				String and;
			
		
		and=conditionAnd();
		list.add(and);
		{
		_loop4:
		do {
			if ((LA(1)==LOR)) {
				match(LOR);
				and=conditionAnd();
				list.add(and);
			}
			else {
				break _loop4;
			}
			
		} while (true);
		}
		
				if (list.size() == 1) {
					conOr = (String)list.get(0);
				} else {
					conOr = ParseUtils.orString(list);
				}
			
		return conOr;
	}
	
	protected final String  conditionAnd() throws RecognitionException, TokenStreamException {
		String conAnd;
		
		
				java.util.List list = new java.util.ArrayList();
				String rel;
			
		
		rel=relation();
		list.add(rel);
		{
		_loop7:
		do {
			if ((LA(1)==LAND)) {
				match(LAND);
				rel=relation();
				list.add(rel);
			}
			else {
				break _loop7;
			}
			
		} while (true);
		}
		
				if (list.size() == 1) {
					conAnd = (String)list.get(0);
				} else {
					conAnd = ParseUtils.andString(list);
				}
			
		return conAnd;
	}
	
	protected final String  relation() throws RecognitionException, TokenStreamException {
		String rel;
		
		Token  a = null;
		Token  a2 = null;
		Token  b = null;
		Token  c = null;
		Token  d = null;
		Token  e = null;
		Token  f = null;
		Token  g = null;
		
				String left = null;
				String op = null;
				String right = null;
			
		
		left=add();
		{
		switch ( LA(1)) {
		case EQUAL:
		case EQUAL2:
		case NOT_EQUAL:
		case NOT_EQUAL2:
		case GE:
		case GT:
		case LE:
		case LT:
		{
			{
			switch ( LA(1)) {
			case EQUAL:
			{
				a = LT(1);
				match(EQUAL);
				op = "=";
				break;
			}
			case EQUAL2:
			{
				a2 = LT(1);
				match(EQUAL2);
				op = a2.getText();
				break;
			}
			case NOT_EQUAL:
			{
				b = LT(1);
				match(NOT_EQUAL);
				op = b.getText();
				break;
			}
			case NOT_EQUAL2:
			{
				c = LT(1);
				match(NOT_EQUAL2);
				op = c.getText();
				break;
			}
			case GE:
			{
				d = LT(1);
				match(GE);
				op = d.getText();
				break;
			}
			case GT:
			{
				e = LT(1);
				match(GT);
				op = e.getText();
				break;
			}
			case LE:
			{
				f = LT(1);
				match(LE);
				op = f.getText();
				break;
			}
			case LT:
			{
				g = LT(1);
				match(LT);
				op = g.getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			right=add();
			break;
		}
		case EOF:
		case LOR:
		case LAND:
		case COMMA:
		case RPAREN:
		case RBRACK:
		case RCURLY:
		case DCOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
				if (op == null) {
					rel = left;
				} else {
					rel = ParseUtils.relationString(left, op, right);
				}
			
		return rel;
	}
	
	protected final String  add() throws RecognitionException, TokenStreamException {
		String add;
		
		Token  p = null;
		Token  m = null;
		
				java.util.List multi_list = new java.util.ArrayList();
				java.util.List op_list = new java.util.ArrayList();
				String multi;
			
		
		multi=multi();
		multi_list.add(multi);
		{
		_loop14:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					p = LT(1);
					match(PLUS);
					op_list.add(p.getText());
					break;
				}
				case MINUS:
				{
					m = LT(1);
					match(MINUS);
					op_list.add(m.getText());
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				multi=multi();
				multi_list.add(multi);
			}
			else {
				break _loop14;
			}
			
		} while (true);
		}
		
				if (multi_list.size() == 1) {
					add = multi; // (String)multi_list.get(0);
				} else {
					add = ParseUtils.addString(multi_list, op_list);
				}
			
		return add;
	}
	
	protected final String  multi() throws RecognitionException, TokenStreamException {
		String multi;
		
		Token  m = null;
		Token  d = null;
		Token  mo = null;
		
				java.util.List power_list = new java.util.ArrayList();
				java.util.List op_list = new java.util.ArrayList();
				String pow;
			
		
		pow=power();
		power_list.add(pow);
		{
		_loop18:
		do {
			if (((LA(1) >= STAR && LA(1) <= MOD))) {
				{
				switch ( LA(1)) {
				case STAR:
				{
					m = LT(1);
					match(STAR);
					op_list.add(m.getText());
					break;
				}
				case DIV:
				{
					d = LT(1);
					match(DIV);
					op_list.add(d.getText());
					break;
				}
				case MOD:
				{
					mo = LT(1);
					match(MOD);
					op_list.add(mo.getText());
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				pow=power();
				power_list.add(pow);
			}
			else {
				break _loop18;
			}
			
		} while (true);
		}
		
				if (power_list.size() == 1) {
					multi = pow;// power_list.get(0);
				} else {
					multi = ParseUtils.multiString(power_list, op_list);
				}
			
		return multi;
	}
	
	protected final String  power() throws RecognitionException, TokenStreamException {
		String power;
		
		
				java.util.List list = new java.util.ArrayList();
				String unary;
			
		
		unary=unary();
		list.add(unary);
		{
		_loop21:
		do {
			if ((LA(1)==POWER)) {
				match(POWER);
				unary=unary();
				list.add(unary);
			}
			else {
				break _loop21;
			}
			
		} while (true);
		}
		
				if (list.size() == 1) {
					power = unary; // power_list.get(0);
				} else {
					power = ParseUtils.powerString(list);
				}
			
		return power;
	}
	
	protected final String  unary() throws RecognitionException, TokenStreamException {
		String unary;
		
		Token  e = null;
		Token  p = null;
		Token  m = null;
		String atom; String op = null;
		
		{
		switch ( LA(1)) {
		case LNOT:
		{
			e = LT(1);
			match(LNOT);
			op = e.getText();
			break;
		}
		case PLUS:
		{
			p = LT(1);
			match(PLUS);
			op = p.getText();
			break;
		}
		case MINUS:
		{
			m = LT(1);
			match(MINUS);
			op = m.getText();
			break;
		}
		case FLOT_NUM:
		case IDENT:
		case LPAREN:
		case LBRACK:
		case INT_NUM:
		case STRING_LITERAL_DSQ:
		case STRING_LITERAL_SSQ:
		case CR_ADRESS:
		case SHARP:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case IDENT:
		{
			atom=ident_prefix();
			break;
		}
		case STRING_LITERAL_DSQ:
		{
			atom=str_literal();
			break;
		}
		case STRING_LITERAL_SSQ:
		{
			atom=sl_or_sil();
			break;
		}
		case FLOT_NUM:
		case INT_NUM:
		{
			atom=num_literal();
			break;
		}
		case CR_ADRESS:
		{
			atom=columnrow_address();
			break;
		}
		case SHARP:
		{
			atom=column_index();
			break;
		}
		case LPAREN:
		{
			atom=closure();
			break;
		}
		case LBRACK:
		{
			atom=array();
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
				if (op == null) {
					unary = atom;
				} else {
					unary = ParseUtils.unary(op, atom);
				}
			
		return unary;
	}
	
	protected final String  ident_prefix() throws RecognitionException, TokenStreamException {
		String atom;
		
		Token  id = null;
		// some variables to test this node
				// abc | fn(2,3) | ds1.fn(1,2)
				String f_name = null; // first name		
			
		
		id = LT(1);
		match(IDENT);
		f_name = id.getText();
		{
		switch ( LA(1)) {
		case DOT:
		case LPAREN:
		{
			atom=ident_fn(f_name);
			break;
		}
		case EOF:
		case LOR:
		case LAND:
		case EQUAL:
		case EQUAL2:
		case NOT_EQUAL:
		case NOT_EQUAL2:
		case GE:
		case GT:
		case LE:
		case LT:
		case PLUS:
		case MINUS:
		case STAR:
		case DIV:
		case MOD:
		case POWER:
		case COMMA:
		case RPAREN:
		case COLON:
		case LBRACK:
		case RBRACK:
		case LCURLY:
		case RCURLY:
		case DCOLON:
		{
			atom=ident_cr(f_name);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return atom;
	}
	
	protected final String  str_literal() throws RecognitionException, TokenStreamException {
		String sl;
		
		Token  s = null;
		
		s = LT(1);
		match(STRING_LITERAL_DSQ);
		
				String str = s.getText();
				sl = org.extreme.commons.util.Utils.quote(str.substring(1, str.length() - 1));// "new StringLiteral()";
			
		return sl;
	}
	
	protected final String  sl_or_sil() throws RecognitionException, TokenStreamException {
		String atom;
		
		Token  s = null;
		
				String text = null;
			
		
		s = LT(1);
		match(STRING_LITERAL_SSQ);
		
				text = s.getText();
				text = text.substring(1, text.length() - 1);
			
		{
		atom=sil_ssq(text);
		}
		return atom;
	}
	
	protected final String  num_literal() throws RecognitionException, TokenStreamException {
		String nl;
		
		Token  i = null;
		Token  f = null;
		
		switch ( LA(1)) {
		case INT_NUM:
		{
			i = LT(1);
			match(INT_NUM);
			nl = ParseUtils.newInstance("NumberLiteral", org.extreme.commons.util.Utils.quote(i.getText()));
			break;
		}
		case FLOT_NUM:
		{
			f = LT(1);
			match(FLOT_NUM);
			nl = ParseUtils.newInstance("NumberLiteral", org.extreme.commons.util.Utils.quote(f.getText()));
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return nl;
	}
	
	protected final String  columnrow_address() throws RecognitionException, TokenStreamException {
		String ads;
		
		Token  i = null;
		
		i = LT(1);
		match(CR_ADRESS);
		
				ads = ParseUtils.newInstance("CRAddress", ParseUtils.cr(i.getText().substring(1)));
			
		return ads;
	}
	
	protected final String  column_index() throws RecognitionException, TokenStreamException {
		String idx;
		
		Token  i = null;
		
		match(SHARP);
		i = LT(1);
		match(INT_NUM);
		
				idx = ParseUtils.newInstance("AtomColumnIndex", i.getText()/* int*/);	
			
		return idx;
	}
	
	protected final String  closure() throws RecognitionException, TokenStreamException {
		String close;
		
		String ex;
		
		match(LPAREN);
		ex=conditionOr();
		match(RPAREN);
		close = "(" + ex + ")";
		return close;
	}
	
	protected final String  array() throws RecognitionException, TokenStreamException {
		String ae;
		
		java.util.List list = new java.util.ArrayList();String ex;
		
		match(LBRACK);
		{
		switch ( LA(1)) {
		case FLOT_NUM:
		case PLUS:
		case MINUS:
		case LNOT:
		case IDENT:
		case LPAREN:
		case LBRACK:
		case INT_NUM:
		case STRING_LITERAL_DSQ:
		case STRING_LITERAL_SSQ:
		case CR_ADRESS:
		case SHARP:
		{
			ex=conditionOr();
			list.add(ex);
			{
			_loop63:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					list.add(null);
					{
					switch ( LA(1)) {
					case FLOT_NUM:
					case PLUS:
					case MINUS:
					case LNOT:
					case IDENT:
					case LPAREN:
					case LBRACK:
					case INT_NUM:
					case STRING_LITERAL_DSQ:
					case STRING_LITERAL_SSQ:
					case CR_ADRESS:
					case SHARP:
					{
						ex=conditionOr();
						list.set(list.size() - 1, ex);
						break;
					}
					case COMMA:
					case RBRACK:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					break _loop63;
				}
				
			} while (true);
			}
			break;
		}
		case RBRACK:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RBRACK);
		ae = ParseUtils.newInstance("ArrayExpression", ParseUtils.toTypeArrayString((String[])list.toArray(new String[list.size()]), "String"));
		return ae;
	}
	
	protected final String  ident_fn(
		java.lang.String f_name
	) throws RecognitionException, TokenStreamException {
		String atom;
		
		Token  id2 = null;
		
				String s_name = null; // second name
				java.util.List args = null; 
				String arg;
			
		
		{
		{
		switch ( LA(1)) {
		case DOT:
		{
			match(DOT);
			id2 = LT(1);
			match(IDENT);
			s_name = id2.getText();
			break;
		}
		case LPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(LPAREN);
		args = new java.util.ArrayList();
		{
		switch ( LA(1)) {
		case FLOT_NUM:
		case PLUS:
		case MINUS:
		case LNOT:
		case IDENT:
		case LPAREN:
		case LBRACK:
		case INT_NUM:
		case STRING_LITERAL_DSQ:
		case STRING_LITERAL_SSQ:
		case CR_ADRESS:
		case SHARP:
		{
			arg=conditionOr();
			args.add(arg);
			{
			_loop33:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					args.add(null);
					{
					switch ( LA(1)) {
					case FLOT_NUM:
					case PLUS:
					case MINUS:
					case LNOT:
					case IDENT:
					case LPAREN:
					case LBRACK:
					case INT_NUM:
					case STRING_LITERAL_DSQ:
					case STRING_LITERAL_SSQ:
					case CR_ADRESS:
					case SHARP:
					{
						arg=conditionOr();
						args.set(args.size() - 1, arg);
						break;
					}
					case COMMA:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					break _loop33;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		// according to the variables above do different stuff
				String[] ex_array = (String[])args.toArray(new String[args.size()]);
				if(s_name != null) {
					atom = ParseUtils.newInstance("DatasetFunctionCall", 
						new String[] {org.extreme.commons.util.Utils.quote(f_name), org.extreme.commons.util.Utils.quote(s_name), ParseUtils.toObjectArrayString(ex_array)});
				} else {
					if (MacroUtils.isMacro(f_name)) {
						atom = MacroUtils.toMacro(f_name, ex_array);
					} else {
						atom = ParseUtils.newInstance("FunctionCall", new String[] {org.extreme.commons.util.Utils.quote(f_name), ParseUtils.toObjectArrayString(ex_array)});
					}
				}
			
		}
		return atom;
	}
	
	protected final String  ident_cr(
		java.lang.String f_name
	) throws RecognitionException, TokenStreamException {
		String atom;
		
		Token  i2 = null;
		
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
			
		
		from=columnrow_literal(targetColumnRow);
		{
		switch ( LA(1)) {
		case COLON:
		{
			match(COLON);
			i2 = LT(1);
			match(IDENT);
			targetColumnRow = org.extreme.commons.ColumnRow.valueOf(i2.getText());
			to=columnrow_literal(targetColumnRow);
			break;
		}
		case EOF:
		case LOR:
		case LAND:
		case EQUAL:
		case EQUAL2:
		case NOT_EQUAL:
		case NOT_EQUAL2:
		case GE:
		case GT:
		case LE:
		case LT:
		case PLUS:
		case MINUS:
		case STAR:
		case DIV:
		case MOD:
		case POWER:
		case COMMA:
		case RPAREN:
		case RBRACK:
		case RCURLY:
		case DCOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		atom = ParseUtils.newInstance("ColumnRowRange", new String[] {from, to});
		return atom;
	}
	
	protected final String  columnrow_literal(
		org.extreme.commons.ColumnRow targetColumnRow
	) throws RecognitionException, TokenStreamException {
		String crl;
		
		
				String left = null;
				String up = null;
				String ex = null;
				
				String start = null;
				String end = null;
			
		
		{
		switch ( LA(1)) {
		case LBRACK:
		{
			match(LBRACK);
			{
			switch ( LA(1)) {
			case IDENT:
			case ALLL:
			case ALLL2:
			{
				left=columnrow_location();
				if(left == "ColumnRowLocation.ALL") up = "ColumnRowLocation.ALL";
				break;
			}
			case SEMI:
			case RBRACK:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				up = null;
				{
				switch ( LA(1)) {
				case IDENT:
				case ALLL:
				case ALLL2:
				{
					up=columnrow_location();
					break;
				}
				case RBRACK:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case RBRACK:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RBRACK);
			break;
		}
		case EOF:
		case LOR:
		case LAND:
		case EQUAL:
		case EQUAL2:
		case NOT_EQUAL:
		case NOT_EQUAL2:
		case GE:
		case GT:
		case LE:
		case LT:
		case PLUS:
		case MINUS:
		case STAR:
		case DIV:
		case MOD:
		case POWER:
		case COMMA:
		case RPAREN:
		case COLON:
		case RBRACK:
		case LCURLY:
		case RCURLY:
		case DCOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LCURLY:
		{
			match(LCURLY);
			{
			switch ( LA(1)) {
			case FLOT_NUM:
			case PLUS:
			case MINUS:
			case LNOT:
			case IDENT:
			case LPAREN:
			case LBRACK:
			case INT_NUM:
			case STRING_LITERAL_DSQ:
			case STRING_LITERAL_SSQ:
			case CR_ADRESS:
			case SHARP:
			{
				ex=conditionOr();
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RCURLY);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				match(LBRACK);
				{
				start=conditionOr();
				}
				{
				switch ( LA(1)) {
				case DCOLON:
				{
					match(DCOLON);
					end=conditionOr();
					break;
				}
				case RBRACK:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RBRACK);
				break;
			}
			case EOF:
			case LOR:
			case LAND:
			case EQUAL:
			case EQUAL2:
			case NOT_EQUAL:
			case NOT_EQUAL2:
			case GE:
			case GT:
			case LE:
			case LT:
			case PLUS:
			case MINUS:
			case STAR:
			case DIV:
			case MOD:
			case POWER:
			case COMMA:
			case RPAREN:
			case COLON:
			case RBRACK:
			case RCURLY:
			case DCOLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			break;
		}
		case EOF:
		case LOR:
		case LAND:
		case EQUAL:
		case EQUAL2:
		case NOT_EQUAL:
		case NOT_EQUAL2:
		case GE:
		case GT:
		case LE:
		case LT:
		case PLUS:
		case MINUS:
		case STAR:
		case DIV:
		case MOD:
		case POWER:
		case COMMA:
		case RPAREN:
		case COLON:
		case RBRACK:
		case RCURLY:
		case DCOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
				crl = ParseUtils.newInstance("ColumnRowLiteral", new String[] {ParseUtils.cr(targetColumnRow), left, up, ex, start, end});
			
		return crl;
	}
	
	protected final String  columnrow_location() throws RecognitionException, TokenStreamException {
		String loc = null;
		
		
				java.util.List dim_list = new java.util.ArrayList();
				String dim;
			
		
		switch ( LA(1)) {
		case ALLL:
		case ALLL2:
		{
			{
			switch ( LA(1)) {
			case ALLL:
			{
				match(ALLL);
				break;
			}
			case ALLL2:
			{
				match(ALLL2);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			loc = "ColumnRowLocation.ALL";
			break;
		}
		case IDENT:
		{
			dim=location_dim();
			dim_list.add(dim);
			{
			_loop49:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					dim=location_dim();
					dim_list.add(dim);
				}
				else {
					break _loop49;
				}
				
			} while (true);
			}
			
					if(loc == null) {
						loc = ParseUtils.newInstance("ColumnRowLocation", ParseUtils.toTypeArrayString((String[])dim_list.toArray(new String[dim_list.size()]), "String"));
					}
				
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return loc;
	}
	
	protected final String  location_dim() throws RecognitionException, TokenStreamException {
		String dim;
		
		Token  c = null;
		Token  i = null;
		
		
				org.extreme.commons.ColumnRow columnrow = null;
				String op = "LocationDim.ABSOLUTE";
				int index = 0;
				boolean absolute = false;
			
		c = LT(1);
		match(IDENT);
		columnrow = org.extreme.commons.ColumnRow.valueOf(c.getText());
		{
		switch ( LA(1)) {
		case COLON:
		{
			match(COLON);
			{
			switch ( LA(1)) {
			case LNOT:
			{
				match(LNOT);
				absolute = true;
				break;
			}
			case PLUS:
			case MINUS:
			case INT_NUM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				op = "LocationDim.PLUS";
				break;
			}
			case MINUS:
			{
				match(MINUS);
				op = "LocationDim.MINUS";
				break;
			}
			case INT_NUM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			i = LT(1);
			match(INT_NUM);
			index = Integer.parseInt(i.getText());
			break;
		}
		case COMMA:
		case SEMI:
		case RBRACK:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
				if(absolute) {
					if(op == "LocationDim.MINUS") {
						index = -index;
					}
					op = "LocationDim.ABSOLUTE";
				}
				dim = ParseUtils.newInstance("LocationDim", new String[] {ParseUtils.cr(columnrow), op, ParseUtils.Int(index)});
			
		return dim;
	}
	
	protected final String  sil_ssq(
		java.lang.String text
	) throws RecognitionException, TokenStreamException {
		String sl;
		
		
				sl = org.extreme.commons.util.Utils.quote(text);//"new StringLiteral(text)";
			
		
		return sl;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"DOT",
		"FLOT_NUM",
		"LOR",
		"LAND",
		"EQUAL",
		"EQUAL2",
		"NOT_EQUAL",
		"NOT_EQUAL2",
		"GE",
		"GT",
		"LE",
		"LT",
		"PLUS",
		"MINUS",
		"STAR",
		"DIV",
		"MOD",
		"POWER",
		"LNOT",
		"IDENT",
		"LPAREN",
		"COMMA",
		"RPAREN",
		"COLON",
		"LBRACK",
		"SEMI",
		"RBRACK",
		"LCURLY",
		"RCURLY",
		"DCOLON",
		"ALLL",
		"ALLL2",
		"INT_NUM",
		"STRING_LITERAL_DSQ",
		"STRING_LITERAL_SSQ",
		"CR_ADRESS",
		"SHARP",
		"QUESTION",
		"BOR",
		"BAND",
		"AT",
		"WAVE",
		"Char",
		"ESC",
		"Exponent",
		"LETTER",
		"DIGIT",
		"XDIGIT",
		"WS"
	};
	
	
	}
