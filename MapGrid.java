import java.util.*;
import java.util.regex.*;
import java.io.*; 

public class MapGrid implements Grid, Constants {
    //private MultivaluedHashMap gridMap;
    private Map<String, List<String>> gridMap;
    private String fileName;
    
    MapGrid() {
        this.fileName = GRID_FILE;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            readFile(reader);

        } catch(IOException ie) {
            this.fileName = null;
        }
    }
    
    MapGrid(String fileName) {
        this.fileName = fileName;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {;
            readFile(reader);                                                     
        } catch(IOException ioe) {
            // load default file
            try(BufferedReader reader = new BufferedReader(new FileReader(GRID_FILE))) {
                readFile(reader);
                this.fileName = GRID_FILE;
                
            } catch(IOException ie) {
                this.fileName = null;
            }
        }
    }
    
    MapGrid(int width, int height, String fileName) {
        this.fileName = (fileName == null)?GRID_DEFAULT:fileName;
        
        //Generate/Reset grid
        int size = width*height;
        this.gridMap = new HashMap<String, List<String>>(size);
        String newLine = "";
        
        for(int ctr = 1; ctr <= size; ctr++) {
            String key = getRandomString();
            String val = getRandomString();
            List<String> listVal;
            
            if(gridMap.containsKey(key)) {
                listVal = gridMap.get(key);
            } else {
                listVal = new ArrayList<String>();
            }
            
            listVal.add(val);
            gridMap.put(key, listVal);
            
            newLine += "(" + key + "," + val +") ";
                
            if(ctr % width == 0) {
                newLine += "\n";
                //write here
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {
                    writer.write(newLine);
                    writer.flush();
                    
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        
        new File(TEMP_FILE).renameTo(new File(this.fileName));
    }
    
    @Override
	public void print() {
		//To Do : print as grid 
        try(BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String currentLine;
            readFile(reader);

        } catch(IOException ie) {
            gridMap = null;
        }  
        
	}
	
    @Override
	public void edit(int option, String val, String newVal) {
        switch(option) {
            case 1:
                //edit key
                if(gridMap.containsKey(val)) {                    
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE));
                        BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
                        String currentLine; 
                        
                        while((currentLine = reader.readLine()) != null) {
                            Matcher match = Pattern.compile("\\((.*?)\\)").matcher(currentLine);
                            String newLine = "";
                            
                            // Get values inside parenthesis
                            while(match.find()) {
                                String entry = match.group(1);          

                                // Split values, get key, and value.
                                String[] keyVal = entry.split(",", 2);
                                if(keyVal.length >= 2) {
                                    String key = keyVal[0]; 
                                    String value = keyVal[1];

                                    if(key.equals(val)) {
                                        key = newVal;
                                    }
                                    
                                    newLine += "("+ key + "," + value +") ";
                                 }
                            }
                            writer.write(newLine + "\n");
                            writer.flush();
                        }
                        reader.close();
                        new File(TEMP_FILE).renameTo(new File(this.fileName));
                        
                        //Update Map
                        gridMap.put(newVal, gridMap.get(val));
                        gridMap.remove(val);
                        System.out.println("Successfully edited key " + val + " to " + newVal);
                        
                    } catch(IOException ie) {
                        gridMap = null;
                    }                 
                    
                } else {
                    System.out.println("Key does not exist.");
                }
                
                break;
            case 2:
                //edit val
                boolean found = false;
                
                // Update file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE));
                    BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
                    String currentLine; 

                    while((currentLine = reader.readLine()) != null) {
                        Matcher match = Pattern.compile("\\((.*?)\\)").matcher(currentLine);
                        String newLine = "";

                        // Get values inside parenthesis
                        while(match.find()) {
                            String entry = match.group(1);          

                            // Split values, get key, and value.
                            String[] keyVal = entry.split(",", 2);
                            if(keyVal.length >= 2) {
                                String key = keyVal[0]; 
                                String value = keyVal[1];

                                if(value.equals(val)) {
                                    value = newVal;
                                }

                                newLine += "("+ key + "," + value +") ";
                             }
                        }
                        writer.write(newLine + "\n");
                        writer.flush();
                    }
                    reader.close();
                    new File(TEMP_FILE).renameTo(new File(this.fileName));
                    
                    //Update map
                    for(Map.Entry<String, List<String>> entry : gridMap.entrySet()) {
                        List<String> mapList = entry.getValue();

                        if(mapList.contains(val)) {
                            int index = mapList.indexOf(val);
                            mapList.set(index, newVal);
                            found = true;
                        }    
                    }
                    
                    // Display result
                    if(!found) {
                        System.out.println("Value does not exist.");
                    }else {
                        System.out.println("Successfully edited value(s) " + val + " to " + newVal);
                    }
                    
                } catch(IOException ie) {
                    gridMap = null;
                } 
                
                break;
        }
	}
    
    @Override
	public void edit(String key, String newKey, String val, String newVal) {
        System.out.println("Editing key - " + key + "...");
        edit(1, key, newKey);
        
        System.out.println("Editing value - " + key + "...");
        edit(2, val, newVal);
	}
	
    @Override
	public void addUpdate(int cols, String[] keys, String[] vals) {
        String line = "";
        
        for(int ctr = 0; ctr < cols; ctr++) {
            if(gridMap.containsKey(keys[ctr])) {
                List<String> values = gridMap.get(keys[ctr]);
                values.add(vals[ctr]);
            } else {
                List<String> values = new ArrayList<String>();
                values.add(vals[ctr]);
                gridMap.put(keys[ctr], values);
            }
            
            line += "(" + keys[ctr] + "," + vals[ctr] + ")";
        }
        
        line += "\n";
        updateFile(line, true);
        //System.out.println("Successfully added (" + key + " , " + value + ")");
	}
    
    @Override
	public void add(String key, String value) {
        if(gridMap.containsKey(key)) {
            List<String> values = gridMap.get(key);
            values.add(value);
        } else {
            List<String> values = new ArrayList<String>();
            values.add(value);
            gridMap.put(key, values);
        }
	}
	
    @Override
	public void sort(int col) {
        int ctr = 0;
        String line = "";
        
        // SORT map/keys
        gridMap = new TreeMap<String, List<String>>(gridMap);
        
        // SORT values
        for(Map.Entry<String, List<String>> entry : gridMap.entrySet()) {
            List<String> mapList = entry.getValue();
            Collections.sort(mapList);
            
            for(String val : mapList) {
                //get key, get val
                if(ctr > 0 && ctr % col == 0) {
                    line += "\n";
                }
                
                line += "(" + entry.getKey() + "," + val + ") ";
                ctr++;
            }
        } 
        
        System.out.println("SORTED : \n" + line);
        line += "\n";
        updateFile(line, false);
	}
	
    @Override
	public void search(String sub) {
        boolean found = false;
        int keyInstance;
        int valInstance;
        String key;
        String val;
        
        if(sub.isEmpty()) {
            System.out.println("Invalid! : Empty string");
            return;
        }
        
        for(Map.Entry<String, List<String>> entry : gridMap.entrySet()) {
            keyInstance = 0;
            valInstance = 0;
            
            key = String.valueOf(entry.getKey());
            List<String> values = entry.getValue();
            
            // Get instance count
            if(key.contains(sub)) {
                keyInstance = getValInstance(key, sub);
            }
            
            for(String value : values) {
                if(value.contains(sub)) {
                    valInstance = getValInstance(value, sub);
                }
                
                if(keyInstance > 0 || valInstance > 0) {
                    System.out.println("\n(" + entry.getKey() + "," + value + ")");

                    if(keyInstance > 0) {
                        System.out.println("\t" + keyInstance + " instance(s) in key");
                    }

                    if(valInstance > 0) {
                        System.out.println("\t" + valInstance + " instance(s) in value");
                    }

                    found = true;;
                }
            }
        }        
        
        if (!found) {
            System.out.println("\n'" + sub + "' not found in grid...");
        }
	}
    
    
    private int getValInstance(String base, String sub) {
		int index = base.indexOf(sub);
		int count = 0;
		
		while(index != -1) {
			index = base.indexOf(sub, index);

			if(index != -1) {
				count++;
				index++;
			}
		}
		
		return count;
	}
    
    private String getRandomString() {
		Random rand = new Random();
		int random = 0;
		String randString = "";
		for(int ctr = 0; ctr < 5; ctr++) {
			random = rand.nextInt(74) + 48;
			randString += (char)random;
		}
		
		return randString;
	}
    
    private void readFile(BufferedReader reader) throws IOException {
        gridMap = new HashMap<String, List<String>>();
        String currentLine; 
        
        while((currentLine = reader.readLine()) != null) {
            Matcher match = Pattern.compile("\\((.*?)\\)").matcher(currentLine);
            System.out.print("\n");
            
            // Get values inside parenthesis
            while(match.find()) {
                String entry = match.group(1);          
                
                // Split values, and add in grid.
                String[] keyVal = entry.split(",", 2);
                if(keyVal.length >= 2) {
                    String key = keyVal[0];
                    String val = keyVal[1];
                    
                    this.add(key,val);    
                    System.out.print("("+ key + "," + val +") ");
                 }
            }
        }
    }
        
    private void updateFile(String content, boolean append){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append))) {
			writer.write(content); 
            
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getFileName() {
        return this.fileName;
    }
}