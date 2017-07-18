import java.util.*;

public class Exercise2 {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Exercise2 exercise = new Exercise2();
        int option;
          
        // Populate Grid / Instantiate Grid class
        Grid grid = null;
        
        if(args.length > 0) {
            grid = new MapGrid(args[0]);
        } else {
            grid = new MapGrid();
        }
        
        if(grid.getFileName() == null) {
            grid = new MapGrid(3, 3, null);
        }
        
        System.out.println("\n\n" + grid.getFileName() + " loaded...");
        
		do {
            System.out.println("\n\n=============================");
            System.out.println("1. Print");
            System.out.println("2. Edit");
            System.out.println("3. Add");
            System.out.println("4. Sort");
            System.out.println("5. Search");
            System.out.println("6. Reset");
            System.out.println("7. Exit");
            System.out.print("\nEnter next operation : ");
            
            option = exercise.acceptValidInt(1, 7);
            
            switch(option) {
                case 1: // print grid
                    grid.print();                    
                    break;
                case 2: // edit grid
                    // Get : edit type (key, val, both)
                    System.out.println("\n\nWhat will you edit?");
                    System.out.println("\t[1] Key");
                    System.out.println("\t[2] Value");
                    System.out.println("\t[3] Both");
                    System.out.print("\tEnter option : ");
                    int answer = exercise.acceptValidInt(1, 3);
                    String val;
                    String newVal;
                    
                    switch(answer) {
                        case 1:
                            System.out.print("\n\nEnter key to edit : ");
                            val = scanner.nextLine();
                            
                            System.out.print("Enter new key : ");
                            newVal = scanner.nextLine();
                            
                            grid.edit(answer, val, newVal);
                            break;
                        case 2:
                            System.out.print("\n\nEnter value to edit : ");
                            val = scanner.nextLine();
                            
                            System.out.print("Enter new value : ");
                            newVal = scanner.nextLine();
                            
                            grid.edit(answer, val, newVal);
                            break;
                        case 3:
                            System.out.print("\n\nEnter key to edit : ");
                            String key = scanner.nextLine();
                            
                            System.out.print("Enter new key : ");
                            String newKey = scanner.nextLine();
                            
                            System.out.print("\n\nEnter value to edit : ");
                            val = scanner.nextLine();
                            
                            System.out.print("Enter new value : ");
                            newVal = scanner.nextLine();
                            
                            grid.edit(key, newKey, val, newVal);
                            break;
                        default:
                            break;
                    }                    
                    //TODO: Update file;
                    
                    break;
                case 3: // add on grid
                    System.out.print("\nEnter no. of entries : ");
                    int cols = exercise.acceptValidInt(1);
                    String[] keys = new String[cols];
                    String[] values = new String[cols];
                    
                    for(int ctr = 0; ctr < cols; ctr++) {
                        System.out.print("\nEnter key : ");
                        keys[ctr] = scanner.nextLine();

                        System.out.print("Enter value : ");
                        values[ctr] = scanner.nextLine();
                    }

                    grid.addUpdate(cols, keys, values);   
                    break;
                case 4: // sort grid
                    System.out.print("\nEnter no. of columns : ");
                    cols = exercise.acceptValidInt(1);
                    
                    grid.sort(cols);
                    break;
                case 5: // search grid - OK
                    System.out.print("\n\nSearch substring : ");
                    String searchKey = scanner.nextLine();
                    
                    grid.search(searchKey);
                    break;
                case 6: // reset grid - OK
                    System.out.print("\n\nEnter no. of columns : ");
                    int col = exercise.acceptValidInt(1);
                    
                    System.out.print("\nEnter no. of rows : ");
                    int rows = exercise.acceptValidInt(1);
                    
                    System.out.println("FILE NAME : " + grid.getFileName());
                    String file = grid.getFileName();
                    grid = new MapGrid(col, rows, file);
                    
                    break;
                default: // edit grid
                    break;
            }
            
        }while(option != 7);

	}
    
    private int acceptValidInt(int min) {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		int input = 0;
		
		while(!validInput) {
			try{
				input = scanner.nextInt();
				
				if(input < min) {
					System.out.print("\nInvalid input, try again : ");
					validInput = false;
				} else {
					validInput = true;
				}
			}catch(InputMismatchException ime) {
				System.out.print("\nInvalid input, try again : ");
				scanner = new Scanner(System.in);
			}
		}
		
		return input;
	}
    
    private int acceptValidInt(int min, int max) {
		Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		int input = 0;
		
		while(!validInput) {
			try{
				input = scanner.nextInt();
				
				if(input < min || input > max) {
					System.out.print("\nInvalid input, try again : ");
					validInput = false;
				} else {
					validInput = true;
				}
			}catch(InputMismatchException ime) {
				System.out.print("\nInvalid input, try again : ");
				scanner = new Scanner(System.in);
			}
		}
		
		return input;
	}
}