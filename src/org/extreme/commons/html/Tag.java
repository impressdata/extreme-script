package org.extreme.commons.html;

import java.io.PrintWriter;

import org.extreme.commons.util.BaseCoreUtils;
import org.extreme.commons.util.CodeUtils;


/*
 * ����дhtml��~~
 */
public class Tag extends Html {
	private String tagName;
	private java.util.List classes; // css.class д��class="c1 c2 c3"
	private java.util.Map styles; // css.style д��style="color:red;font-size:12;"
	private java.util.Map attributes; // tag����������
	
	private java.util.List subHtmlList; // tag��ǩ����ӱ�ǩ
	private java.util.List siblingHtmlList; // �뵱ǰTag��ǩƽ����Html��Ԫ
	
	/*
	 * ��tagNameΪnullʱ,ֻ��subHtmlList������
	 */
	public Tag() {
		this(null);
	}
	public Tag(String tagName) {
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	
	public Tag cls(String cls) {
		if(classes == null) {
			classes = new java.util.ArrayList();
		}
		
		classes.add(cls);
		
		return this;
	}
	
	public Tag css(String name, String val) {
		if(styles == null) {
			styles = new java.util.HashMap();
		}
		
		styles.put(name, val);
		
		return this;
	}
	
	public Tag css(java.util.Map cssMap) {
		if(styles == null) {
			styles = new java.util.HashMap();
		}
		
		styles.putAll(cssMap);
		
		return this;
	}
	
	public boolean hasPositionCSS() {
		if (this.styles == null) return false;
		return this.styles.containsKey("position");
	}
	
	public Tag attr(String name, String val) {
		if(attributes == null) {
			attributes = new java.util.HashMap();
		}
		
		attributes.put(name, val);
		
		return this;
	}
	
	public Tag sub(Html html) {
		if (subHtmlList == null) {
			subHtmlList = new java.util.ArrayList();
		}
		
		subHtmlList.add(html);
		
		return this;
	}
	
	public Tag sub(String html) {
		return sub(new TextHtml(html));
	}
	
	public Tag sibling(Html html) {
		if (siblingHtmlList == null) {
			siblingHtmlList = new java.util.ArrayList();
		}
		
		siblingHtmlList.add(html);
		
		return this;
	}
	
	/*
	 * ��PrintWriterдhtml��ȥ
	 */
	public void writeHtml(PrintWriter writer) {
		// tagNameΪnull��ʱ��ֻ��subHtml
		if(tagName == null) {
			for(int i = 0, len = subHtmlList == null ? 0 : subHtmlList.size(); i < len; i++) {
				((Html)subHtmlList.get(i)).writeHtml(writer);
			}
			return;
		}
		
		writer.write(new StringBuffer().append('<').append(tagName).toString());
		
		writer.write(classBuffer().toString());
		
		writer.write(styleBuffer().toString());
		
		writer.write(attributeBuffer().toString());

		/*
		 * :��ie��д<col></col>,��Ȼ����Ϊ��������ǩ,<colgroup><col></col></colgroup>,$("colgroup").children().length == 2
		 */
		if (tagName.equalsIgnoreCase("COL") && (subHtmlList == null || subHtmlList.size() == 0)) {
			writer.write(" />");
			return;
		}
		
		writer.write(">");
		
		for(int i = 0, len = subHtmlList == null ? 0 : subHtmlList.size(); i < len; i++) {
			((Html)subHtmlList.get(i)).writeHtml(writer);
		}
		
		writer.write(new StringBuffer().append("</").append(tagName).append('>').toString());
		
		// :дsiblingHtmlList
		if (this.siblingHtmlList != null) {
			for (int i = 0, len = this.siblingHtmlList.size(); i < len; i++) {
				((Html)siblingHtmlList.get(i)).writeHtml(writer);
			}
		}
	}
	
	private StringBuffer classBuffer() {
		StringBuffer sb = new StringBuffer();
		
		if(this.classes != null && this.classes.size() > 0) {
			sb.append(" class=\"");
			int size = this.classes.size();
			for (int i = 0; i < size - 1; i ++) {
				sb.append(this.classes.get(i));
				sb.append(' ');
			}
			sb.append(this.classes.get(size - 1));
			sb.append('\"');
		}
		
		return sb;
	}
	
	private StringBuffer styleBuffer() {
		StringBuffer sb = new StringBuffer();
		
		if(styles != null) {
			StringBuffer styleBuf = new StringBuffer();
			
			java.util.Iterator enIt = this.styles.entrySet().iterator();
			while(enIt.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry)enIt.next();
				styleBuf.append(entry.getKey()).append(':').append(entry.getValue()).append(';');
			}
			
			if(styleBuf.length() > 0) {
				sb.append(" style=\"").append(styleBuf).append('"');
			}
		}
		
		return sb;
	}
	
	private StringBuffer attributeBuffer() {
		StringBuffer sb = new StringBuffer();
		
		if(attributes != null) {
			java.util.Iterator enIt = this.attributes.entrySet().iterator();
			while(enIt.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry)enIt.next();//:����ط�ֻ��Ҫ�����žͿ�����
				sb.append(' ').append(entry.getKey()).append("=\"").append(CodeUtils.attributeHtmlEncode(entry.getValue().toString())).append('"');
			}
		}
		
		return sb;
	}
	
	public String toString() {
		return toHtml();
	}
	
	public Object clone() throws CloneNotSupportedException {
		Tag tag = (Tag)super.clone();
		
		tag.attributes = BaseCoreUtils.cloneMap(this.attributes);
		tag.styles = BaseCoreUtils.cloneMap(this.styles);
		
		return tag;
	}
}
