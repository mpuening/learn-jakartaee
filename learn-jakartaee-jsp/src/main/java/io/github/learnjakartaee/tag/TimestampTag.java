package io.github.learnjakartaee.tag;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class TimestampTag extends TagSupport {

	private static final long serialVersionUID = 137289750607215002L;

	private static final Logger logger = Logger.getLogger(TimestampTag.class.getName());

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(Calendar.getInstance().getTime());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception writing timestamp", e);
		}
		return SKIP_BODY;
	}
}