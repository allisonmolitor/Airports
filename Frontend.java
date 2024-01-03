import java.util.Scanner;

public class Frontend 
{

	private Backend backend;
	private Scanner scanner;
    boolean exit = true;


	public Frontend(Backend backend, Scanner scanner)
	{
		this.backend = backend;
		this.scanner = scanner;
		startMainLoop();
	}

	
	public void startMainLoop()
	{
		while (exit)
		{
	        System.out.println("Welcome to the Main Menu! Please pick an option by entering the corresponding number.");
			System.out.println("1. Input a File");
			System.out.println("2. Show Statistics");
			System.out.println("3. Find Shortest Route");
			System.out.println("4. Exit");

			int choice = scanner.nextInt();
			scanner.nextLine(); 

			switch (choice)
			{
			case 1:
				System.out.println("Enter file path: ");
				String filePath = scanner.nextLine();
				loadFile(filePath);
				break;
			case 2:
				System.out.println(showStatistics());
				break;
			case 3:
				System.out.println("Enter start airport: ");
				String startAirport = scanner.nextLine();
				System.out.println("Enter end airport: ");
				String endAirport = scanner.nextLine();
				System.out.println(findShortestRoute(startAirport, endAirport));
				break;
			case 4:
				exitApp();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	
	public void loadFile(String filePath)
	{
		backend.readData(filePath);
	}

	
	public String showStatistics()
	{
		return backend.getDataStatistics();
	}

	
	public String findShortestRoute(String startAirport, String endAirport)
	{
		return backend.getShortestRoute(startAirport, endAirport);
	}

	
	public void exitApp()
	{
		System.out.println("Exiting the application. Goodbye!");
	    exit = false;
	    scanner.close();
	}
}