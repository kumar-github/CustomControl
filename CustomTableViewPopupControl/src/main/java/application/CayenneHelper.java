package application;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;

public class CayenneHelper
{
	private static ServerRuntime cayenneServerRuntime;

	private static void initialize()
	{
		try
		{
			cayenneServerRuntime = ServerRuntimeBuilder.builder().addConfig("cayenne-ExchangeMonitor.xml").build();
			cayenneServerRuntime.newContext();
		}
		catch(final Throwable exception)
		{
			throw new RuntimeException(exception);
		}
		finally
		{
		}
	}

	public static void initializeCayenneServerRuntime()
	{
		initialize();
	}

	public static ServerRuntime getCayenneServerRuntime()
	{
		return cayenneServerRuntime;
	}
}