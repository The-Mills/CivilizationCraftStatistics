package repository;

public interface RepositoryHandler 
{	
	String openOrRepairRepository();
	
	void setRepositoryStatus(RepositoryError status);
	
	RepositoryError getRepositoryStatus();
}