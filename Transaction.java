import java.util.Date;

public class Transaction {
   //The amount of this transaction.
   private double amount;
   //The time and date of this transaction.
   private Date timestamp;
   // A memo for this transaction.
   private String memo;
   //The account in which the transaction was performed.
   private Account inAccount;

   /**
    * Creating a new transaction
    * @param amount amount being transacted
    * @param inAccount account the transaction belong to
   */
   public Transaction(double amount, Account inAccount){

    this.amount = amount;
    this.inAccount = inAccount;
    this.timestamp = new Date();
    this.memo = " ";
   }
  /**
    * Creating a new transaction
    * @param amount amount being transacted
    * @param inAccount account the transaction belong to
   */  

   public Transaction(double amount, String memo, Account inAccount){

    //Calling the two-arg constructor first
    this(amount, inAccount);

    //Setting the memo
    this.memo = memo;
   }
   
   /**
    * Getting the amount of the transaction
    * @return the amount
    */
   public double getAmount() {
    return this.amount;
   }
   /*
    * Getting a string to summarizing the transaction
   */

   public String getSummaryLine(){
    
    if (this.amount >= 0){
        return String.format("%s : R%.02f : %s", this.timestamp.toString(),
            this.amount, this.memo);
    } else {
        return String.format("%s : R(%.02f) : %s", this.timestamp.toString(),
            -this.amount, this.memo);
    }
   }
}
