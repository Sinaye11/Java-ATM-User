import java.util.ArrayList;

public class Account {

    //The name of the account
    private String name;
    //The account ID number
    private String uuid;
    //User details that owns the specific account.
    private User holder;
    //List of transactions for this acount.
    private ArrayList<Transaction> transactions;
    /**
     * //Creating a new Account
     * @param name  name of the account
     * @param holder user object that contains this account
     * @param theBank bank that issues the account
     */

    public Account(String name, User holder, Bank theBank){

        //Setting the account name and account holder
        this.name =name;
        this.holder =holder;

        //Getting the new account UID
        this.uuid = theBank.getNewAccountUUID();

        //Init transactions
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Getting the account ID
     * @return the uuid
     */
    public String getUUID() {

        return this.uuid;
    }
    /**
     * Getting teh summary for the account
     * @return the string summary
     */
    public String getSummaryLine(){

        //Getting the account balance
        double balance =this.getBalance();

        //Formatting the summary line, dependent if the  balance is negative
        if (balance >= 0) {
            return String.format("%s : R%.02f: %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : R(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Getting balance of this account by adding the amounts of the transactions
     * @return balance value
     */
    public double getBalance() {

        double balance =0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }
    /**
     *  Printing the transactional history of the account
     */

    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--){ //Starting with our last index
            System.out.println(this.transactions.get(t).getSummaryLine());
        } 
            System.out.println();
    }
    /**
     * Adding new transaction in this account
     * @param amount    amount transacted
     * @param memo      transition memo
     */

    public void addTransaction (double amount, String memo){
        //Creating a new transaction object and adding it to out list
        Transaction newTrans = new Transaction(amount, memo,this);
        this.transactions.add(newTrans);
    }
}