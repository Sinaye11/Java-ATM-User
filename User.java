import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

   //First name of the user 
   private String firstName;
   //Last name of the user
   private String lastName;
    //The ID number  of the user/ unique identifier.
   private String uuid;
    //The MD5 hash of the user's pin number.
   private byte pinHash[];
    //The list of accounts for the user
   private ArrayList<Account> accounts;

   /**
    * Creating a new user
    * @param firstName  user's first name
    * @param lastName   user's last name
    * @param pin        user's account pin number
    * @param theBank    Bank object that user is a customer of
    */

   public User(String firstName, String lastName, String pin, Bank theBank){
    //Setting the users first name and last name
    this.firstName = firstName;
    this.lastName = lastName;

    //Saving the pin's MD5 hash, rather than the original value, for security purposes
      MessageDigest md = MessageDigest.getInstance("MD5");

    try {
         MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());
    } 
        catch (NoSuchAlgorithmException e) {
         System.err.println("error, caught NoSuchAlgorithmException");
         e.printStackTrace();
          System.exit(1);
    
    //Getting a new unique universal ID for user
    this.uuid = theBank.getNewUserUUID();

    //Create empty lists of accounts
    this.accounts = new ArrayList<Account>();

    //Print log message
    System.out.printf("New user %s, %s with ID %s created.\n", lastName,firstName, this.uuid);
   }
}
   /**
    * Adding an account for the user
    * @param anAcct the account being added to
    */
   public void addAccount(Account anAcct){

    this.accounts.add(anAcct);
   }
   /**
    * Returns the user's UUID
    * @return the uuid
    */
   public String getUUID(){

    return this.uuid;
   }
   /**
    * Checks if a certain pin matches the true User pin
    * @param aPin the pin to check
    * @return      Whether pin is valid  or not
    */
   public boolean validatePin(String aPin){

    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return MessageDigest.isEqual(md.digest(aPin.getBytes()),this. pinHash);
    } catch (NoSuchAlgorithmException e) {
        System.err.println("error, caught NoSuchAlgorithmException");
        e.printStackTrace();
        System.exit(1);
    }
    return false;
   }
   /**
    * Returns the users first name
    * @return the user's first name
    */
   public String getFirstName(){

    return this.firstName;
   }

   /***
    * Prints the summary for the accounts of the user
    */
   public void printAccountsSummary(){

    System.out.printf("\n\n%s's account summary\n", this.firstName);
    for (int a = 0; a < this.accounts.size(); a++){
        System.out.printf("  %d) %s\n", a+1,this.accounts.get(a).getSummaryLine());
    }
    System.out.println();
   }
   /**
    * Getting the number of accounts of the user
    * @return number of the accounts
    */
   public int numAccounts() {

    return this.accounts.size();
   }
   /**
    * Printing the transactional history for a certain account
    * @param acctIdx index of the account to use
    */
   public void printAcctTransHistory(int acctIdx){
    this.accounts.get(acctIdx).printTransHistory();
   }
   /**
    * Getting the account balance for a certain account
    * @param acctIdx    index of the account used
    * @return       balance of the account
    */
   public double getAcctBalance(int acctIdx){
    return this.accounts.get(acctIdx).getBalance();
   }

/**
 * Getting UUID of a certain account
 * @param acctIdx index of the account to use
 * @return      UUID of the account
 */
   public String getAcctUUID(int acctIdx){

    return this.accounts.get(acctIdx).getUUID();
   }
   /**
    * Adding a transaction to a certain account
    * @param acctIdx    index of the account
    * @param amount     amount of the transaction
    * @param memo       memo of the transaction
    */
   public void addAcctTransaction(int acctIdx, double amount, String memo){
    
    this.accounts.get(acctIdx).addTransaction(amount,memo);
   }


}
