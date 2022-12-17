/*
The program is a cash register which presents a menu for the user where the user can choose between 1: Add articles, 2: Remove article, 3: Show articles, 4: Sell, 5: Order history, 6: Sort order history and 7: end. Each menu choice calls for a speicifc method which executes exactly what the menu shows. Whenever user inputs 0 an error message will be printed.

1. Show menu to user repeatedly, get input as choice of which action to take. 
   Print error if input is 0 or letters, then get input again.
2. If case 1:
  3. Get amount of articles to add from user.
  4. Add amount of articles to a table (articlenumber, amount, price), extend table if needed.
5. If case 2:
  6. Get articlenumber to be removed from user, delete specified article from table.
7. If case 3:
  8. Print all values that is not 0 in the table.
9. If case 4:
  10. Get articlenumber and amount to sell from user.
  11. Decrease amount of articles as many as specified, if amount to sell > amount of articles, 
      print error. If Articles left is 0, delete article.
  12. Save date/time for selling in a separate array.
  13. At the same index, save articlenumber, amount sold and sum of sales in a new table.
14. If case 5:
  15. Print date/time array followed by related sales from table of sales.
16. If case 6:
  17. Sort sales table together with date/time array with articlenumbers in an ascending order.
  18. Print sorted array and table.
19. If case 7:
  20. End program.
  
@author
Liza Danielsson, lizdan-6.
*/



import java.util.Scanner;
import java.util.Date;

public class Main {
  private static Scanner userInput = new Scanner(System.in);  //Global scanner.

    /*
    Main method, from where other methods will be invoked.
    */
  public static void main(String[] args) 
  {
    int actionToTake = 0;
    
    int[][] articles = new int[10][3];
    int articleNumber = 1000;
    int noOfArticles = 0;

    int[][] sales = new int[1000][3];
    Date[] salesDate = new Date[1000];

      //Repeat menu until user chooses to quit program (when pressing 7).
    do
    {
      //Invoke method "menu", save value returned.
    actionToTake = menu();
      //switch case for each number/menuchoice.
    switch (actionToTake) 
    {
        //When choice of 1, get amount of articles from user and invoke method "insertArticles".
      case 1:
        System.out.printf("\nAntal artiklar att addera: ");
        noOfArticles = input();
        articles = insertArticles(articles, articleNumber, noOfArticles);
        System.out.printf("...\n" + noOfArticles + (" artiklar adderade.\n"));
        break;
        //When choice of 2, invoke method "removeArticle".
      case 2:
        removeArticle(articles);
        break;
        //When choice of 3, invoke method "printArticles".
      case 3:
        printArticles(articles);
        break;
        //When choice of 4, invoke method "sellArticles".
      case 4:
        sellArticle(sales, salesDate, articles);
        break;
        //When choice of 5, invoke method "printSales".
      case 5:
        printSales(sales, salesDate);
        break;
        //When choice of 6, invoke method "sortedTable".
      case 6:
        sortedTable(sales, salesDate);
        break;
        //When choice of 7, end program.
      case 7:
          System.out.printf("...\nAvslutar programmet.");
          System.exit(0);
    }
    }while(true);
  }


    /*
      Method. Prints out menu and returns users choice of action.
    */
  public static int menu() 
  {
    int menuChoice = 0;
    System.out.printf("\nMENY\n¨¨¨¨\n");
    System.out.printf("1. Lägg in artiklar\n2. Ta bort artikel\n3. Visa artiklar" + 
                      "\n4. Försäljning\n5. Orderhistorik\n6. Sortera orderhistoriktabell" + 
                      "\n7. Avsluta\nDitt val: ");
    menuChoice = input();

    return menuChoice;
  }





  /*
    Method. Gets input from global scanner, checks if integer, returns integer if so (not zero tho), otherwise
    prints an error.
  */
  public static int input() 
  {
    int inputToReturn = 0;

      //Repeat while true (until "break").
    do 
    {
        //Continue if input is an integer, if so save value in variable.
      if (userInput.hasNextInt()) 
      {
        inputToReturn = userInput.nextInt();
          //Continue if value of input is 0, if so print error. Else, break loop.
        if (inputToReturn == 0)
        {
          System.out.printf("0 är ej möjligt. Försök igen...");
        }
        else
        {
          break;
        } 
      } 
        //Continue if input is other than integer, if so print error and get input again.
      else if (!userInput.hasNextInt()) 
      {
        System.out.printf("\nFelaktig inmatning, försök igen...\n");
        userInput.next();
      }
    } while (true);

    return inputToReturn;
  }


  

    /*
      Method. Checks if table of articles has room for amount decided by user. Adds wanted amount of articles
      either in original table or a copy with extra locations needed. Returns table.
    */
  public static int[][] insertArticles (int[][]articles, int articleNumber, int noOfArticles) 
  {
    int firstEmptyLocation = 0;
    int amountAdded = 0;
    int amountOfOneArticle = 0;
    int priceForArticle = 0;
    final int LOWER_BOUND_AMOUNT = 1;
    final int UPPER_BOUND_AMOUNT = 10;
    final int UPPER_BOUND_PRICE = 1000;
    final int LOWER_BOUND_PRICE = 100;
    

      //Invoke method that checks if there is enough space left in table. Save the returned table in "articles".
    articles = checkFull(articles, noOfArticles);
    
      //Loop through articles to check first empty location.
    for(int i = 0; i < articles.length; i++)
    {
        //If value on row i, column 0 is 0, continue.
      if (articles[i][0] == 0)
      {
          //first empty space is i, save in variable.
        firstEmptyLocation = i;
          
          //if first value isn't 0 (if the table is not empty), continue.
        if (!(articles[0][0] == 0))
        {
            //Save the articlenumber from the article placed before the first empty space.
          articleNumber = articles[i-1][0];
            //Add 1 to articlenumber to not use the same articlenumber twice.
          articleNumber += 1;
        }
        break;
      }
        
      
    }

      //Loop trough articles from frist empty location.
    for (int j = firstEmptyLocation; j < articles.length; j ++)
    {
        //Randomize values for amount of one article to add, and price for one article.
      amountOfOneArticle = LOWER_BOUND_AMOUNT + (int)(Math.random() * ((UPPER_BOUND_AMOUNT - LOWER_BOUND_AMOUNT) + 1));
      priceForArticle = LOWER_BOUND_PRICE + (int)(Math.random() * ((UPPER_BOUND_PRICE - LOWER_BOUND_PRICE) + 1));
        
      //Add articlenumber, amount and price for each empty row j.
      if (articles[j][0] == 0)
      {
        articles[j][0] = articleNumber;
        articles[j][1] = amountOfOneArticle;
        articles[j][2] = priceForArticle;
          //Add 1 for each adding.
        amountAdded += 1;
      }
        //Increase articlenumber after each adding.
      articleNumber += 1;
        //If number added is the same as amount to add, break the loop.
      if (amountAdded == noOfArticles)
      {
        break;
      }
    }
        
    return articles;
  } 
     
    
    
 
      /*
        Method. Checks if articles is full, returns it if not, extend it in a copy and returns that if yes.
      */
    public static int[][] checkFull(int[][]articles, int noOfArticles) 
    {
      int locationsLeft = 0;
      int amountToExtend = 0;
      int[][] temporaryArray;

        //Loops through articles.
      for(int i = 0; i < articles.length; i++)
      { 
          //if value on row i, column 0 is 0, add 1 to "locationsleft".
        if (articles[i][0] == 0)
        {
          locationsLeft += 1;
        }
      }

        //If the amount left is same or more than amount to add, return original table.
      if (locationsLeft >= noOfArticles)
      {
        return articles;
      }
        //Else,check amount to add and make a copy with original array length+amount to add, return the copy.
      else 
      {
        amountToExtend = noOfArticles - locationsLeft;
        temporaryArray = new int[articles.length+amountToExtend][3];
        System.arraycopy(articles, 0, temporaryArray, 0, (articles.length));
        return temporaryArray;
      }
    } 




    /*
      Method. Asks user which article to remove, then removes that one from the table.
    */
  public static void removeArticle (int[][]articles) 
  {
    int articleToRemove;

    System.out.printf("\nArtikel att ta bort: ");
    articleToRemove = input();

      //Loops throguh each row of table articles.
    for (int i = 0; i < articles.length; i++)
    {
        //If articlenumber in row i is the same as article wished to remove, 
        //set all columns of row i to 0, then break loop.
      if (articles[i][0] == articleToRemove)
      {
        articles[i][0] = 0;
        articles[i][1] = 0;
        articles[i][2] = 0;
        break;
      }  
    }
    System.out.printf("...\nArtikel " + articleToRemove + " är borttagen.\n");
  }




    /*
      Method. Prints all values in articles. Prints each row in one line and each column seperated with tab.
    */
  public static void printArticles (int[][]articles) 
  {
    System.out.printf("\nart.\tst.\tpris\n");
    for (int i = 0; i < articles.length; i++)
    {
      for (int j = 0; j < articles[i].length; j++)
      {
          //Print as long as value is not 0.
        if(!(articles[i][0] == 0))
        {
          System.out.printf(articles[i][j]+("\t"));
        }
      }
      System.out.printf("\n");
    }
  }   





    /*
      Method. Gets articlenumber from user and amount to sell, checks if amount of articles is more or the same        as amount to sale, if so, removes wanted amount from specified article,
      multiplies price for one article with amount to sale and saves article, amount and sum in a new table.
    */
  public static void sellArticle(int[][]sales, Date[] salesDate, int[][]articles) 
  {
    int articleNumber = 0;
    int amountToSell = 0;
    int emptyIndex = 0;
    int sumOfSales = 0;

      //Get articlenumber from user.
    System.out.printf("\nFörsäljning av artikelnummer: ");
    articleNumber = input();

      //Get amount to sell from user.
    System.out.printf("Antal: ");
    amountToSell = input();

      //Loops trough sales to find first empty index, saves index in variable.
    for (int i = 0; i < sales.length; i++)
    {
      if (sales[i][0] == 0)
      {
        emptyIndex = i;
        break;
      }
    }

      //Loops trough articles to find article to sell.
    for (int i = 0; i < articles.length; i++)
    {  
        //If articlenumber is same as the one to sale, continue.
      if(articles[i][0] == articleNumber)
      {
          //If amount of specified article is the same or more than amount to sell, continue.
        if(articles[i][1] >= amountToSell)
        {
            //Remove amount to sell from amount of articles. 
            //Save current date/time to new table "salesDate", to same index as in sales.
          articles[i][1] -= amountToSell;
          salesDate[emptyIndex] = new Date();

            //Add articlenumber (column 0) in first empty row in new table "sales".
          sales[emptyIndex][0] = articles[i][0];
            //Add amount sold (column 1) to first empty row in sales.
          sales[emptyIndex][1] = amountToSell;

            //Calculate sum from sale, add to column 2 (sum).
          sumOfSales = articles[i][2] * amountToSell;
          sales[emptyIndex][2] = sumOfSales;

          System.out.printf("\n...\n" + amountToSell + "st. av artikel " + articleNumber + " sålt.\n");

            //If amount reaches 0 after selling, remove article.
          if(articles[i][1] == 0)
          {
            articles[i][0] = 0;
            articles[i][1] = 0;
            articles[i][2] = 0;
          }
          break;
        }
          //Else (not the same or more amount left than to sell) print error.
        else
        {
          System.out.printf("\nOBS! För få artiklar i lager. \nLagerstatus artikel " +
                         articleNumber + ": " + articles[i][1] + " st.\n");
        }
      }
    }
  } 

  
    /*
      Method. Print table "salesDate" followed by table "sales" as long as value is not 0.
    */
  public static void printSales(int[][]sales, Date[] salesDate) 
  {
    System.out.printf("\ndatum\t\t\t\t\tart.\t\tst.\t\tsum.\n");
    for (int i = 0; i < sales.length; i++)
    {
      if (!(sales[i][0] == 0))
      {
        System.out.printf("%1$tF %1$tT", salesDate[i]);
        for (int j = 0; j < sales[i].length; j++)
        {
          System.out.printf("\t\t" + sales[i][j]);
        } 
        System.out.printf("\n");
      } 
    }
  } 


  
    /*
      Method. Swaps places of articles in table sales (including all columns) while the one before is bigger           than the one after. At each swap the dates in table salesDate also swaps places.
      Alos prints the new order (both sorted tables).
    */
  public static void sortedTable(int[][]sales, Date[] salesDate) 
  {
    boolean sorted;

      //Loop while sorted is false, set to true in begging.
    do 
    {
      sorted = true;
      for(int i = 0; i < (sales.length-1); i++)
      {
          //If first articlenumber is bigger than the one after, continue.
        if(sales[i][0] > sales[i+1][0])
        {
            //invoke method "swap" to change places in each row for row i.
          sales = swap(sales, i, 0);
          sales = swap(sales, i, 1);
          sales = swap(sales, i, 2);

            //Swap places on dates at same row i.
          Date temp = salesDate[i];
          salesDate[i] = salesDate[i+1];
          salesDate[i+1] = temp;

            //Change sorted to false to make another loop.
          sorted = false;
        }
      } 
        //If no value was bigger than the one after, sorted will stay true and loop will end.
    } while(!(sorted));

      //Print salesDate followed by all values in sales that is connected to the specifik salesDate.
    System.out.printf("\nDatum\t\t\t\t\tart.\t\tst.\t\tsum.\n");
    for (int i = 0; i < sales.length; i++)
    {
      if (!(sales[i][0] == 0))
      {
        System.out.printf("%1$tF %1$tT", salesDate[i]);
        for (int j = 0; j < sales[i].length; j++)
        {
          System.out.printf("\t\t" + sales[i][j]);
        } 
        System.out.printf("\n");
      } 
    }
}


    /*
      Method. Swaps places in table "sales" according to index and row. Returns sales with the swaped values.
    */
  public static int[][] swap(int[][] sales, int index, int row)
  {
    int tempVar = 0;
  
    tempVar = sales[index][row];
    sales[index][row] = sales[index+1][row];
    sales[index+1][row] = tempVar;

    return sales;
  }
} //end of main class.
