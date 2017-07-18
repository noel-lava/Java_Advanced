public interface Grid {
	
	void print();
	
	void edit(int option, String val, String newVal);
    
    void edit(String key, String newKey, String val, String newVal);
    
	void add(String key, String value);
	
	void addUpdate(int cols, String[] key, String[] value);
	
	void sort(int col);
	
	void search(String sub);

    String getFileName();
}