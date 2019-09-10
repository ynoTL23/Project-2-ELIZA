package ElizaProject;

public interface TextFileIO {

	// Create empty file in dir
	public void createFile(String fileName);
	
	// Create file with preloaded content in dir
	public void createFileWithContent(String fileName, String text);
	
	// Append text to end of file
	public void appendToFile(String fileName, String text);
	
	// Read content from file
	public String readFile(String fileName);
	
}
