package com.cognixia.jump.intermediatejava.assignments.phonenumbervalidator;

import java.util.Scanner;

/*
 * REQUIREMENTS:
 * 	- Tell user if the entered phone number is valid (WITHOUT REGEX)
 * 	- Assume 10 digits long
 * 	- Does not contain 911 in any part
 * 	- Check for correctly formatted parenthesis & dashes
 * 
 * VALID INPUT:
 * 	- 0123456789
 * 	- (012) 345-6789
 * 	- 012-345-6789
 * 
 * INVALID INPUT:
 * 	- 000911000
 * 	- (111) 911-2084
 */

public class PhoneNumberValidator {
	
	private String invalidSequence;
	
	public PhoneNumberValidator() {
		this.invalidSequence = "911";
	}
	
	public PhoneNumberValidator(String invalidSeq) {
		this.invalidSequence = invalidSeq;
	}
	
	public String getInvalidSequence() {
		return invalidSequence;
	}

	public void setInvalidSequence(String invalidSequence) {
		this.invalidSequence = invalidSequence;
	}
	
	public static boolean checkValidNumber(String input, String invalidSeq) {
		boolean validInput = true;
		
		// Remove all whitespace from the input 
		input = input.replaceAll("\\s", "");
		
		// Find the length of the string after removing all whitespace
		int inputLength = input.length();
		
		switch(inputLength) {
			// Strictly number input
			case 10:
				// Check to make sure it is all numbers only
				try {
					// Don't need to save the value anywhere
					Integer.parseInt(input);
					
					// check for invalid sequence
					validInput = !(checkInvalidSequence(input, invalidSeq));
				} catch (NumberFormatException e) {
					validInput = false;
				}
				break;
			
			// xxx-xxx-xxxx
			case 12:
				String[] numParts = input.split("-");
				
				// If more than 3 phone parts
				if (numParts.length != 3) {
					validInput = false;
				}
				else {
				
					// concatenate the parts back together
					String numOnlyInput = "";
					for (String numPart : numParts) {
						numOnlyInput.concat(numPart);
					}
					
					// check if contains input contains the invalid substring
					validInput = !(checkInvalidSequence(numOnlyInput, invalidSeq));
				}
				
				break;
			
			// (xxx)xxx-xxxx
			case 13:
				
				// if not correctly formatted
				if (!input.startsWith("(") || input.charAt(4) != ')' || input.charAt(8) != '-' ) {
					validInput = false;
				}
				else {
					String numOnlyInput = input.substring(1,4).concat(input.substring(5,8)).concat(input.substring(9));
					validInput = !(checkInvalidSequence(numOnlyInput, invalidSeq));
				}
				break;
				
			// Not any of the valid lengths
			default:
				validInput = false;
				break;
		}
		
		
		return validInput;
	}
	
	
	// Helper function created to check for an invalid substring
	// ** Function created in case we want to change the sequence we're looking for
	public static boolean checkInvalidSequence(String input, String seq) {
		return input.contains(seq);
	}
	
	public static boolean isDone(Scanner sc) {
		boolean done = false;
		boolean validInput = false;
		String input;
		
		// while input is not valid
		while (!validInput) {
			System.out.print("Would you like to check another number? (y/n): ");
			
			// convert the input string to all lower case
			input = sc.next().toLowerCase();
			sc.nextLine();
			
			switch(input) {
				case "y":
				case "yes":
					validInput = true;
					done = true;
					break;
				case "n":
				case "no":
					validInput = true;
					done = false;
					break;
				default:
					validInput = false;
					System.out.println("Not a valid input; try again!");
					break;
			}
		}
		
		return done;
	}
	
	public static void main(String[] args) {
		PhoneNumberValidator pnv = new PhoneNumberValidator("911");
		
		Scanner sc = new Scanner(System.in);
		
		boolean done = false;
		String input;
		
		while (!done) {
			boolean validInput = false;
			
			while (!validInput) {
				System.out.print("Enter a valid phone number: ");
				input = sc.nextLine();
				
				if (PhoneNumberValidator.checkValidNumber(input, pnv.getInvalidSequence())) {
					validInput = true;
				}
				else {
					validInput = false;
					System.out.println("Your input was invalid. Please try again.");
				}
			}
			
			System.out.println("Your number is valid!");
			
			// check if user is done
			done = isDone(sc);
		}
		
		sc.close();
	}
}
