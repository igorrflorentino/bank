import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class beta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String response;
		String[] responses = new String[10];
		String[] responses2 = new String[10];
		List<Bank.MarketableAccountTypes> selectedTypes = new ArrayList<>();
		String selectedBank;
		Scanner input = new Scanner(System.in);
		Bank bank = null;
		Client client = null;
		
		System.out.println("Starting program");
		System.out.println("Create new bank? ['y'=yes/any=no]");
		response = input.next();
		
		do {	
			if (response.contentEquals("y")) {
				System.out.print("What is the bank name?" );
				response = input.next();
				System.out.println("Creating a bank");
				bank = new Bank(response);
				System.out.println("Bank created with success: "+bank.getName());
				System.out.println("What services this bank can provide?");
				System.out.println("Options: ");
				for (Bank.MarketableAccountTypes type : Bank.MarketableAccountTypes.values()) {
					System.out.println(type);
				}

		        while (selectedTypes.isEmpty()) {
		            System.out.print("Please select one or more services (separated by spaces): ");
		            responses = input.nextLine().toUpperCase().split("\\s+");

		            for (String item : responses) {
		                try {
		                    Bank.MarketableAccountTypes type = Bank.MarketableAccountTypes.valueOf(item);
		                    selectedTypes.add(type);
		                } catch (IllegalArgumentException e) {
		                    System.out.println(input + " is not a valid service.");
		                }
		            }

		            if (selectedTypes.isEmpty()) {
		                System.out.println("Please select at least one valid service.");
		            }
		        }

		        System.out.println("You selected: " + selectedTypes);
		        bank.addSupportedAccountType(selectedTypes);
				System.out.println("Create another bank? ['y'=yes/any=no]");
				response = input.next();
			}
			else {
				System.out.println("jumping to client");
			}
		}while(response.contentEquals("y"));
		
		if (Bank.exists()) {
			System.out.println("Please select the bank you want to add an client using index value");
			System.out.println("Options availables");
			for (Bank item : Bank.banks) {
				System.out.println(item.getName());
			}
			selectedBank = input.next();
			System.out.println("Bank selected: "+Bank.banks.get(Integer.parseInt(selectedBank)).name);
			System.out.println("Create new client? ['y'=yes/any=no]");
			response = input.next();
			do{
				if (response.contentEquals("y")) {
					System.out.print("What is the client name? ");
					responses2[0] = input.next();
					System.out.print("What is the client CPF? ");
					responses2[1] = input.next();
					System.out.println("What is going to be their account type? use index");
					System.out.println("Options available:");
					for (Bank.MarketableAccountTypes item : Bank.banks.get(Integer.parseInt(selectedBank)).supportedAccountTypes) {
						System.out.println(item);
					}
					responses2[2] = input.next();
					System.out.println("fscsdc: "+responses2[2].toUpperCase());
					System.out.println("Creating a new client");
					client = bank.createClient(responses2[0], responses2[1], Bank.MarketableAccountTypes.BUSINESS);
					System.out.println("Client created with success: "+client.getName());
					System.out.println("Create another client?");
					response = input.next();
				}

			}while(response.contentEquals("y"));
		}
		else {
			System.out.println("Ops..");
			System.out.println("You refused to created any Bank");
			System.out.println("No bank no fun");
			System.out.println("bye-bye");
		}
		System.out.println("Finilizing");
		System.out.println("Final data base:");
		for (Bank item : Bank.banks) {
			System.out.println(item);
			System.out.println(item.clients);
			System.out.println(item.accounts);
		}

	}

}
