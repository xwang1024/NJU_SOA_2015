package soa.group4.SAXProcess;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler
{

	@Override
	public void error(SAXParseException exception) throws SAXException
	{
		System.out.println(exception.getMessage());
		
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException
	{
		System.out.println(exception.getMessage());		
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException
	{
		System.out.println(exception.getMessage());
	}

}
