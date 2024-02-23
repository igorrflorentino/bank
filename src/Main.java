import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



public class Main {
	public static void main(String[] args) {
		Person person1 = new Person("ïgor", "000");
		Bank bank1 = new Bank("bradesco");
		List<Bank.MarketableAccountTypes> typesToBeAdd = new ArrayList<>();
		typesToBeAdd.add(Bank.MarketableAccountTypes.CHECKING);
		typesToBeAdd.add(Bank.MarketableAccountTypes.BUSINESS);
		bank1.addSupportedAccountType(typesToBeAdd);
		bank1.createProspect("guto", "897");
		bank1.createClient("joao", "098", Bank.MarketableAccountTypes.BUSINESS);
		System.out.println(bank1.clients.get(0).accountsOwnedObject.get(0).balance);
		bank1.clients.get(0).accountsOwnedObject.get(0).changeBalance(-200);
		System.out.println(bank1.clients.get(0).accountsOwnedObject.get(0).getFullBalance());
	}
}
	

class Person{
	public String name;
	public String CPF;
	
	protected Person(String name, String CPF){
		System.out.println("inicializing new person");
		this.name = name;
		this.CPF = CPF;
		System.out.println("person created");
	}
}

class Client extends Person{
	public List<Account> accountsOwnedObject = new ArrayList<>(); 
	public Client(String name, String CPF, Account account){
		super(name, CPF);
		System.out.println("inicializing new client from zero");
		accountsOwnedObject.add(account);
		System.out.println("client created");
	}
	
	private Client(Person person, Account account){
		super(person.name, person.CPF);
		System.out.println("inicializing new client from person");
		accountsOwnedObject.add(account);
		System.out.println("client created");
	}
	
	public Client personToClient(Person person, Account account) {
		System.out.println("making registration");
		return new Client(person, account);
	}
	
	public String getName() {
		System.out.println("getting cliente name");
		return super.name;
	}
	
	
	
}

class Bank{
	public String name;
	public List<MarketableAccountTypes> supportedAccountTypes;
	public List<Account> accounts;
	public List<Client> clients;
	
	private static int count = 0;
	public static final List<Bank> banks = Collections.synchronizedList(new ArrayList<>());
	static enum MarketableAccountTypes{
		SAVINGS, CHECKING, BUSINESS
	}
 	
	Bank(String name){
		System.out.println("inicializing new bank");
		this.name = name;	
		this.supportedAccountTypes = new ArrayList<>();
		this.accounts = new ArrayList<>();
		this.clients = new ArrayList<>();
		banks.add(this);
		this.count++;
		System.out.println("bank created");
	}
	
	public static boolean exists() {
		System.out.println("verifing if bank exists");
		return count > 0;
    }
	
	public String getName() {
		System.out.println("getting bank name");
		return this.name;
	}
	
	public void addSupportedAccountType (List<Bank.MarketableAccountTypes> accountTypes) {
		System.out.println("adding account type");
		for(Bank.MarketableAccountTypes item : accountTypes) {
			supportedAccountTypes.add(item);
		}
		System.out.println("adding successed");
	}
		
	
	public Person createProspect(String name, String CPF) {
		System.out.println("creating prospect");
		Person person = new Person(name, CPF);
		return person;
	}
	
	public Client createClient(String name, String CPF, MarketableAccountTypes accountType) {
		System.out.println("creating client");
		Account newCreatedAccount = createAccount(accountType);	
		if (newCreatedAccount != null) {
			Client client = new Client(name, CPF, newCreatedAccount);
			this.clients.add(client);
			newCreatedAccount.owner = client.getName();
			return client;	
		}
		else {
			System.out.println("Fail creating new account");
			return null;
		}
		
	}
	
	private Account createAccount(MarketableAccountTypes accountType) {	
		System.out.println("creating account");
		if (supportedAccountTypes.contains(accountType)) {
			Account account = new Account();
			this.accounts.add(account);
			account.bankName = this.name;
			account.type = accountType.name();
			return account;
		}
		else {
			System.out.println("Account type " + accountType + " is not allowed.");
			return null;
		}
	}
}

class Account{
	double balance;
	String currency;
	String bankName;
	String type;
	UUID ID;
	String owner;

	public Account() {
		System.out.println("inicializing new account");
		this.balance = 200;
		this.currency = "U$";
		this.ID = UUID.randomUUID();
	}
	
	public void changeBalance(double difference) {
		System.out.println("Modifing balance");
		if (difference > 0) {
			balance += difference;
			System.out.println("balance increased with success");
		}
		else if (difference + balance >= 0) {
			balance += difference;
			System.out.println("balance decreased with success");
		}
		else {
			System.out.println("Something went wrong");
		}
	}
	public String getFullBalance() {
		return currency+balance;
	}
}
