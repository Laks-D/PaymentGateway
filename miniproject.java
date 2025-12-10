import java.util.Random;
import java.util.Scanner;

class UserProfile {
    String name;
    String email;
    String phone;
    String location;

    public UserProfile(String name, String email, String phone, String location) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

    public void displayProfile() {
        System.out.println("\n Billing Information:");
        System.out.println("Customer: " + name);
        System.out.println("Contact: " + phone + " | " + email);
        System.out.println("Location: " + location);
    }
}


interface PaymentProcessor {
    void pay(double amount);
}


class CardPayment implements PaymentProcessor {
    String cardNum;
    String expDate;
    int cvv;
    Scanner input = new Scanner(System.in);

    
    public CardPayment(String cardNum, String expDate, int cvv) {
        this.cardNum = cardNum;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("\n[Gateway] Processing Card ending with " + cardNum.substring(cardNum.length() - 4));
      
        if (verifyOTP()) {
            System.out.println("[Success] Payment of Rs." + amount + " completed via Credit/Debit Card.");
        } else {
            System.out.println("[Failed] Incorrect OTP. Transaction Cancelled.");
        }
    }

   
    private boolean verifyOTP() {
      
        Random rand = new Random();
        int generatedOTP = 1000 + rand.nextInt(9000);
        
        System.out.println("- OTP sent to registered mobile: " + generatedOTP);
        System.out.print("- Enter OTP to verify: ");
        int userOTP = input.nextInt();
        
        return userOTP == generatedOTP;
    }
}

class UPIPayment implements PaymentProcessor {
    String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("\n[Gateway] Request sent to UPI App: " + upiId);
        System.out.println("[Success] Payment of Rs." + amount + " completed via UPI.");
    }
}


class WalletPayment implements PaymentProcessor {
    String walletId;

    public WalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("\n[Gateway] Deducting from Wallet ID: " + walletId);
        System.out.println("[Success] Payment of Rs." + amount + " completed via Wallet.");
    }
}


public class PaymentGatewaySystem {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("** Secure Payment Gateway Setup **");
        
        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email ID: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print("Enter Location (City): ");
        String loc = sc.nextLine();

        UserProfile user = new UserProfile(name, email, phone, loc);
        user.displayProfile();

        System.out.print("Enter Amount to Pay: ");
        double amount = sc.nextDouble();

        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Credit/Debit Card");
        System.out.println("2. UPI");
        System.out.println("3. Wallet");
        System.out.print("Choice: ");
        int choice = sc.nextInt();

        PaymentProcessor processor = null; 

        switch (choice) {
            case 1:
                System.out.print("\nEnter Card Number (16 digits): ");
                String cNum = sc.next();
                
                System.out.print("Enter Expiration Date (MM/YY): ");
                String exp = sc.next();
                
                System.out.print("Enter CVV (3 digits): ");
                int cvv = sc.nextInt();
                
                processor = new CardPayment(cNum, exp, cvv);
                break;

            case 2:
                System.out.print("\nEnter UPI ID (e.g., name@okaxis): ");
                String uid = sc.next();
                processor = new UPIPayment(uid);
                break;

            case 3:
                System.out.print("\nEnter Wallet ID / Phone Linked: ");
                String wid = sc.next();
                processor = new WalletPayment(wid);
                break;

            default:
                System.out.println("Invalid Selection.");
                System.exit(0);
        }

       
        if (processor != null) {
            processor.pay(amount);
        }

        System.out.println("\n** Transaction Session Closed **");
        
    }
}
