package repository;

public interface ConnectionHandler 
{
	String openOrRepairConnection();
	
	void setConnectionStatus(ConnectionError status);
	
	ConnectionError getConnectionStatus();
}