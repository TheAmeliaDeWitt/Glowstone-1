package net.glowstone.util.mojangson.ex;

public class MojangsonParseException extends Exception
{
	private ParseExceptionReason reason;

	public MojangsonParseException( String message, ParseExceptionReason reason )
	{
		super( message );
		this.reason = reason;
	}

	@Override
	public String getMessage()
	{
		return reason.getMessage() + ": " + super.getMessage();
	}

	public ParseExceptionReason getReason()
	{
		return reason;
	}

	public enum ParseExceptionReason
	{
		INVALID_FORMAT_NUM( "Given value is not numerical" ),
		UNEXPECTED_SYMBOL( "Unexpected symbol in Mojangson string" ),
		INCOMPATIBLE_TYPE( "List does not support given tag type." );

		private final String message;

		ParseExceptionReason( String message )
		{
			this.message = message;
		}

		public String getMessage()
		{
			return message;
		}
	}
}
