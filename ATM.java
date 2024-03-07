import java.util.Scanner;

public class ATM{
    public static void main(String[] args) {

        //Init Scanner
        Scanner sc = new Scanner(System.in);

        //Init Bank
        Bank theBank = new Bank(" V Bank");

        //Adding a user, that will create a savings account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //Adding a checking account for the user
        Account newAccount = new Account("Checking ", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            //Staying in the login prompt until there's a successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //Staying in the main menu until the user quits
            ATM.printUserMenu(curUser, sc);
            
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //Inits
        String userID;
        String pin;
        User authUser;

        //Ask the user ID/pin until the correct one reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID:");
            userID =sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

        //Attempt to get the user object corresponding to the ID and the pin combo
        authUser = theBank.userLogin(userID, pin);
        if (authUser == null) {
            System.out.println("Incorrect user ID/pin combination. Please try again.");
        }


        } while(authUser == null);//Loop will continue until login is successful

        return authUser;
    }
    
    public static void printUserMenu(User theUser,Scanner sc) {
        //Show the summary of the user's account
        theUser.printAccountsSummary();

        //Init
        int choice;

        //User menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
             theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdrawal");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice <1 || choice >5){
                System.out.println("Invalid choice. Please choose 1-5");
            }    
    }  while (choice < 1 || choice > 5);
    //Choice of customer
    switch (choice) {

        case 1:
            ATM.showTransHistory(theUser, sc);
            break;
        case 2:
            ATM.withdrawalFunds(theUser, sc);
            break; 
        case 3:
            ATM.depositFunds(theUser, sc);
            break;
        case 4:
            ATM.transferFunds(theUser, sc);
            break;
        case 5:
         //Bring up the other previous inputs
            sc.nextLine();
            break;
    }
        //Show the menu again provided the user doesn't want to quit
        if (choice !=5){
            ATM.printUserMenu(theUser, sc); //Use of recursion 
        }
  }
  /**
   *  Showing the transactional history for the account
   * @param theUser logged-in user object
   * @param sc      scanner object used for user input
   */
  public static void showTransHistory(User theUser, Scanner sc){

    int theAcct;

    //Getting the account to look at the transactional history
    do {
        System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", theUser.numAccounts());
        theAcct = sc.nextInt() -1;

        if(theAcct < 0 || theAcct >= theUser.numAccounts()){
            System.out.println("Invalid account. Please try again.");
        }
       }while (theAcct < 0 || theAcct >= theUser.numAccounts());

       //Printing the transactional history
       theUser.printAcctTransHistory(theAcct);

  }
  //We need to get which funds is the user transferring from  and which funds are we transferring to and the amount
  public static void transferFunds(User theUser, Scanner sc){ 

    int fromAcct;
    int toAcct;
    double amount;
    double acctBal;

    // Attaining the account to transfer from
    do {
        System.out.printf("Enter the number (1-%d) of the account\n to transfer from: ", theUser.numAccounts());
        fromAcct =sc.nextInt() -1;
        if (fromAcct < 0 || fromAcct >=theUser.numAccounts()){
            System.out.println("Invalid account. Please try again");
        }
    } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
    acctBal = theUser.getAcctBalance(fromAcct); // getting the account balance

      // Attaining the account to transfer to

 do {
    System.out.printf("Enter the number (1-%d) of the account\n to transfer to: ", theUser.numAccounts());
    toAcct =sc.nextInt() -1;
    if (toAcct < 0 || toAcct >= theUser.numAccounts()){
        System.out.println("Invalid account. Please try again");
    }
    } while (toAcct < 0 || toAcct >= theUser.numAccounts());

    //Getting the amount to be transferred, the highest that can be transferred is the account balance
    do {
        System.out.printf("Enter the amount to transfer (max R%.02f): R", acctBal); 
            amount = sc.nextDouble();
        if (amount < 0) {
            System.out.println("Amount has to be greater than zero");
        } else if (amount > acctBal) {
            System.out.printf("Amount must not be greater than\n balance of R%.02f.\n", acctBal);
        }
    } while (amount < 0 || amount > acctBal);

    //Doing the transfer
    theUser.addAcctTransaction(fromAcct, -1 * amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct))); // Subtracting money from the account
    
    theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct))); // Adding money to the account
  }
   /**
    * Processing funds withdrawn from an account
    * @param theUser    a logged-in user object
    * @param sc         scanner object for user input
    */
    public static void withdrawalFunds(User theUser, Scanner sc){

     //Initializing 
      int fromAcct;
      double amount;
      double acctBal;
      String memo;

      do{
        System.out.printf("Enter the number (1-%d) of the account\n to withdraw from: ", theUser.numAccounts());
        fromAcct = sc.nextInt() - 1;
        if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
            System.out.println("Invalid account. Please try again.");
        }
      } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
      acctBal = theUser.getAcctBalance(fromAcct);

      do{
        System.out.printf("Enter the amount to withdraw (max R%.02f): R",acctBal);
        amount = sc.nextDouble();
        if (amount < 0){
            System.out.println("Amount must be greater than zero");
        } else if(amount > acctBal){
            System.out.printf("Amount must not be greater than\n balance of R%.02f.\n", acctBal);
        }
    } while (amount < 0 || amount > acctBal);

        //Bring up the other previous inputs
        sc.nextLine();

        //Getting the memo
        System.out.print("Enter a memo");
        memo = sc.nextLine();

        //Withdrawal
        theUser.addAcctTransaction(fromAcct, -1* amount, memo);
      }
      /**
       * A fund to deposit an account
       * @param theUser
       * @param sc
       */
      public static void depositFunds(User theUser, Scanner sc){

           //Initializing
          int toAcct;
          double amount;
          double acctBal;
           String memo;

      do{
          System.out.printf("Enter the number (1-%d) of the account\n to deposit in: ", theUser.numAccounts());
          toAcct = sc.nextInt() - 1;
          if(toAcct < 0 || toAcct >= theUser.numAccounts()){
              System.out.println("Invalid account. Please try again.");
        }
      } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

      do{
        System.out.printf("Enter the amount to transfer (max R%.02f): R",acctBal);
        amount = sc.nextDouble();
        if (amount < 0){
            System.out.println("Amount must be greater than zero");
        } 
    } while (amount < 0 );

        //Bring up the other previous inputs
        sc.nextLine();

        //Getting the memo
        System.out.print("Enter a memo");
        memo = sc.nextLine();

        //Withdrawal
        theUser.addAcctTransaction(toAcct,amount, memo); 
      }
 
}