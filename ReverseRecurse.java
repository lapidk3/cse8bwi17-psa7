/** 
 * Name: Kenneth Lapid David, cs8bwans
 * Date: March 7, 2017
 * 
 * 
 * This file asks the user for a specified size to create an int[].
 * then goes on to ask them what elements the array will contain.
 * After that the array will be reversed using recursive methods,
 * and then the values of the array will be printed out
 * */
import java.util.Scanner;

/*
 * This class contains methods that will allow
 * a user to recursively reverse elements in
 * an int array
 * */
public class ReverseRecurse{

  /*
   * This method will ask the user for a maximum number of integers expected,
   * create an array of integers of that size, read at most that many integers
   * from the keyboard using a Scanner object (ignore extra input beyond the 
   * size of the array), and return the initialized array
   *
   * @Return int[]: The array which is of the size indicated by the user
   *                and contains values indicated by the user
   * */ 
  public int[] initArray(){
    int size = 0;
    
    // Ask user for size of array
    System.out.println(PSA7Strings.MAX_NUM);
    
    // Use a scanner to read input values
    Scanner input = new Scanner(System.in);
    // Check to see if an int was entered
    if(input.hasNextInt()){
      size = input.nextInt();
      // Check to see if int entered is > 0
         while(size <=0){
           System.out.println(PSA7Strings.TOO_SMALL);
           size = input.nextInt();
         }
      
    }
    else if(!input.hasNext() || !input.hasNextInt()) System.exit(1);
   
    
    // Create the int array with specified size
    int[] initArray = new int[size];
    
    // Ask user for values to be entered into Array
    System.out.printf(PSA7Strings.ENTER_INTS, size);
    
    // Keeps track of how many integers is inputed into the array
    int count = 0;
    // Loop through the array
    for(int x = 0; x < initArray.length; x++){
      if(input.hasNextInt()){
        // Add the integer into the array
        initArray[x] = input.nextInt();
        // Increase the count
        count++;
      }
      else if(!input.hasNext() || !input.hasNextInt()){
      // Check to see if count > 0 
      if(count > 0){
      // Make a new array
      int[] finalArray = new int[count];
      // Copy the non-empty contents of the old array into the new one
      System.arraycopy(initArray,0,finalArray,0,count);
      return finalArray;
      }
    }
    }
    
   return initArray;
  }
  
  /*
   *  This method prints out all the numbers in an
   *  int array
   *
   *  @Param: array: Array that you'd like to print the 
   *                 values of
   * */
  public void printArray(int[] array){
    // Loop through the entire array
    int count = 0;
    for(int x = 0; x < array.length; x++){
      // Check to see if the value is empty
       if(array[x] == 0 ) count++;
      // If array is empty, state so
       if(count == array.length) System.out.println(PSA7Strings.EMPTY);
       // Print out each int value in the array
       if (array[x] != 0) System.out.print(array[x] + " " );
    }
  }
  
    /*
   *  This method reverses the elements in an array by
   *  modifing the passed array 
   *
   * @Param: originalArray: Array that you'd like to reverse
   *                         the elements of
   * @Param: low: Beginning element of the array
   * @Param high: Ending element of the array
   * */
  public void reverse(int[] originalArray, int low, int high){
    if(originalArray == null) return;
    // Base case: Check if the low index is greater than the high index
    if(low > high) return;
    // Save the value at the low index
    int lowValue = originalArray[low];
    // Swap the values of the low and high indices
    originalArray[low] = originalArray[high];
    originalArray[high] = lowValue;

    // Recursive call, with low index incremented by one, high index decremented
    reverse(originalArray, low + 1, high - 1);
  }

    /*
   *  This method reverses the elements in an array by
   *  not modifing the passed array and instead creating 
   * a new array
   *
   * @Param: originalArray: Array that you'd like to reverse
   *                         the elements of
   * @Return: int[]: New Array that is reversed
   * */
  public int[] reverse(int[] originalArray){
    // Check to see if the array is null
    if(originalArray == null) return null;
    // Declare a new array 
    if(originalArray.length == 1){
      return originalArray;
    }
    else{
    int[] reversed = new int[originalArray.length];
   
    
    // Reverse the first and last elements of the passed
    // array into the new array
    reversed[0] = originalArray[reversed.length-1];
    reversed[reversed.length-1] = originalArray[0];
    
    if(originalArray.length == 2) return reversed;
    
    // Get the middle array
    int[] middle = new int[originalArray.length-2];
    System.arraycopy(originalArray,1,middle,0,originalArray.length-2);
    
    // Recursive call
    middle = reverse(middle);
    
    // Copy the middle elements back into the "reversed" array
    System.arraycopy(middle,0,reversed,1,reversed.length-2);
    return reversed;
    }
  }
}
