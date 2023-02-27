package org.cap.account.test;

 

import static org.junit.jupiter.api.Assertions.*;

 

import org.cap.dao.AccountDao;
import org.cap.dto.Account;
import org.cap.dto.Address;
import org.cap.dto.Customer;
import org.cap.exception.InsufficientBalanceException;
import org.cap.exception.InvalidInitialAmountException;
import org.cap.service.AcccountService;
import org.cap.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

 

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TestAccountService {

 

    @Mock
    private AccountDao accountDao;
    
    @InjectMocks
    private AccountServiceImpl acccountService;
    private Customer customer; 
    
    @BeforeEach
    public void setup() {
        customer=new Customer();
        customer.setCustName("Tom");
        Address address=new Address();
        address.setAddressLine("North Avvenue, Pune");
        customer.setCustAddress(address);
        
    //    acccountService=new AccountServiceImpl(accountDao);
    }
    
    
    
    @Test
    public void test_findAccount_By_AccountNo() {
        //dummy account
                Account account=new Account();
                account.setAccountNo(12345);
                account.setAmount(3000);
                account.setCustomer(customer);
        
                
        //Mockito Declaration
                Mockito.when(accountDao.findAccountById(12345)).thenReturn(account);
                
                
            Account account2= acccountService.findAccountById(12345);
        
            
            Mockito.verify(accountDao).findAccountById(12345);
            assertNotNull(account2);
            assertEquals(account2.getAccountNo(), account.getAccountNo());
                
    }
    
    
    
    
 
    
    @Test
    public void test_addAccount_with_valid_customerdetails() throws InvalidInitialAmountException {
        
        //dummy account
        Account account=new Account();
        account.setAmount(3000);
        account.setCustomer(customer);
        
        //Mockito Declaration
        Mockito.when(accountDao.createAccount(account)).thenReturn(true);
        
        //actual business logic call
        Account account2=acccountService.addAccount(customer, 3000);
        
        //Mockito.verify(accountDao).createAccount(account);
        
        assertEquals(account2.getAmount(), account.getAmount());
        
    }
    
    
    @Test
    public void test_deposit(){
        
        //dummy account
        Account account=new Account();
        account.setAccountNo(001);
        account.setAmount(3000);
        account.setCustomer(customer);
        
        //Mockito Declaration
        Mockito.when(accountDao.findAccountById(001)).thenReturn(account);
        
        //actual business logic call
        Account account2=acccountService.deposit(001, 2000);
        
        assertEquals(account2.getAmount(), account.getAmount());
        //System.out.println(account2);
    }
        
        @Test
        public void test_withdraw()throws InsufficientBalanceException{

        	//dummy account
        	Account account=new Account();
        	account.setAccountNo(001);
        	account.setAmount(5000);
        	account.setCustomer(customer);

        	//Mockito Declaration
        	Mockito.when(accountDao.findAccountById(001)).thenReturn(account);

        	//actual business logic call
        	Account account2=acccountService.withdraw(001, 4000);

        	assertEquals(account2.getAmount(), account.getAmount());
        	//System.out.println(account2);
    }
}
 