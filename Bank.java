import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;
    /**
     * Creates a new bank object with empty lists of user and accounts
     * @param name  the name of the bank
     */
    public Bank(String name){

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Creating a new universal unique ID for a user
     * @return the uuid
     */

    public String getNewUserUUID() {

        //inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //Will loop until we  get a unique ID
        do{
            //Generating the number
            uuid = " ";
            for (int c = 0; c < len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //Checking to make sure if it's unique
            nonUnique =false;
            for (User u : this.users){
                if(uuid.compareTo(u.getUUID()) ==0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;
    }

    /**
     * 
     *Creating a new universal unique ID for a user
     * @return the uuid
     */
    public String getNewAccountUUID() {

     //inits
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //Will loop until we  get a unique ID
         do{
            //Generating the number
            uuid = " ";
            for (int c = 0; c < len; c++){
             uuid += ((Integer)rng.nextInt(10)).toString();
         }
        //Checking to make sure if it's unique
        nonUnique =false;
        for (Account a : this.accounts){
            if(uuid.compareTo(a.getUUID()) ==0){
             nonUnique = true;
             break;
         }
     }
 }while (nonUnique);
     return uuid;
 }

    /**
     *  Adding an account
     * @param anAcct account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    /**
     * Creating a new user of the bank
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param pin       user's pin
     * @return          new user object
     */

    public User addUser(String firstName, String lastName, String pin){

        //Creating new user object and adding it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //Creating a savings account for the user, then add to the user and the bank account lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }
    /**
     * Getting user object linked with a certain userID and pin, checks if they are valid
     * @param userID UUID of user to log in
     * @param pin   pin of the user
     * @return      User object, if login is successful or null or if its not
     */
    
    public User userLogin(String userID, String pin){

        //Searching through the list of users
        for (User u : this.users){

            //Checks if user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        //If we can't find the user or we have an incorrect pin
        return null;
    }
    /**
     * Getting the name of the bank
     * @return The name of the bank
     */
    public String getName() {
        return this.name;
    }
}
