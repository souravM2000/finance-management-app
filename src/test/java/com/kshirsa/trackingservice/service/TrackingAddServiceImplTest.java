package com.kshirsa.trackingservice.service;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddLoanRepayment;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.*;
import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import com.kshirsa.trackingservice.repository.*;
import com.kshirsa.trackingservice.service.impl.TrackingAddServiceImpl;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingAddServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private TransactionRepo transactionRepo;
    @Mock
    private LoanDetailsRepo loanDetailsRepo;
    @Mock
    private LoanRepaymentRepo loanRepaymentRepo;
    @Mock
    private AsyncService asyncService;
    @InjectMocks
    private TrackingAddServiceImpl trackingAddService;

    @Test
    void testAddCategory() {
        AddCategory addCategory = new AddCategory("categoryName", "description", TransactionType.EXPENSE);
        Category category = new Category(addCategory);

        when(userDetailsService.getUser()).thenReturn("userId");
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        Category result = trackingAddService.addCategory(addCategory);

        assertEquals(category, result);
        verify(userDetailsService, times(1)).getUser();
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    void testAddHashTag() throws CustomException {
        String transactionId = "transactionId";
        String hashTag = "newHashTag";
        Transactions transaction = new Transactions();
        transaction.setTransactionId(transactionId);
        transaction.setTags(new HashSet<>());

        when(transactionRepo.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(userDetailsService.getUser()).thenReturn("userId");
        doNothing().when(asyncService).updateHashTags(anyString());

        String result = trackingAddService.addHashTag(transactionId, hashTag);

        assertEquals(hashTag, result);
        assertTrue(transaction.getTags().contains(hashTag));
        verify(transactionRepo, times(1)).findById(transactionId);
        verify(transactionRepo, times(1)).save(transaction);
        verify(asyncService, times(1)).updateHashTags(anyString());
    }

    @Test
    void testAddTransaction() throws CustomException {
        AddTransaction transactionDto = new AddTransaction(100.0, PaymentMode.CASH, "description", TransactionType.EXPENSE, LocalDateTime.now(),"categoryId", false,new HashSet<>(), null);
        Category category = new Category();
        category.setTransactionType(TransactionType.EXPENSE);
        Transactions transaction = new Transactions();
        transaction.setTags(new HashSet<>());
        transaction.setTransactionType(TransactionType.EXPENSE);

        when(categoryRepo.findById(transactionDto.getCategoryId())).thenReturn(Optional.of(category));
        when(userDetailsService.getUser()).thenReturn("userId");
        when(transactionRepo.save(any(Transactions.class))).thenReturn(transaction);

        Transactions result = trackingAddService.addTransaction(transactionDto);

        assertEquals(transaction, result);
        verify(categoryRepo, times(1)).findById(transactionDto.getCategoryId());
        verify(userDetailsService, times(1)).getUser();
        verify(transactionRepo, times(1)).save(any(Transactions.class));
    }

    @Test
    void testAddTransaction_InvalidCategory() {
        AddTransaction transactionDto = new AddTransaction(100.0, PaymentMode.CASH, "description", TransactionType.LOAN, LocalDateTime.now(),"categoryId", false,new HashSet<>(), null);

        when(categoryRepo.findById(transactionDto.getCategoryId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> trackingAddService.addTransaction(transactionDto));

        assertEquals(ErrorCode.INVALID_CATEGORY.name(), exception.getMessage());
        verify(categoryRepo, times(1)).findById(transactionDto.getCategoryId());
    }

    @Test
    void testAddTransaction_WrongTransactionType() {
        AddTransaction transactionDto = new AddTransaction(100.0, PaymentMode.CASH, "description", TransactionType.LOAN, LocalDateTime.now(),"categoryId", false,new HashSet<>(), null);
        Category category = new Category();
        category.setTransactionType(TransactionType.INCOME);

        when(categoryRepo.findById(transactionDto.getCategoryId())).thenReturn(Optional.of(category));

        CustomException exception = assertThrows(CustomException.class, () -> trackingAddService.addTransaction(transactionDto));

        assertEquals(ErrorCode.WRONG_TRANSACTION_TYPE.name(), exception.getMessage());
        verify(categoryRepo, times(1)).findById(transactionDto.getCategoryId());
    }

    @Test
    void testAddTransaction_WithTags() throws CustomException {
        AddTransaction transactionDto = new AddTransaction(100.0, PaymentMode.CASH, "description", TransactionType.EXPENSE, LocalDateTime.now(),"categoryId", false,new HashSet<>(), null);
        transactionDto.getTags().add("tag1");
        Category category = new Category();
        category.setTransactionType(TransactionType.EXPENSE);
        Transactions transaction = new Transactions();
        transaction.setTags(new HashSet<>());
        transaction.setTransactionType(TransactionType.EXPENSE);

        when(categoryRepo.findById(transactionDto.getCategoryId())).thenReturn(Optional.of(category));
        when(userDetailsService.getUser()).thenReturn("userId");
        when(transactionRepo.save(any(Transactions.class))).thenReturn(transaction);

        Transactions result = trackingAddService.addTransaction(transactionDto);

        assertEquals(transaction, result);
        verify(categoryRepo, times(1)).findById(transactionDto.getCategoryId());
        verify(userDetailsService, times(1)).getUser();
        verify(transactionRepo, times(1)).save(any(Transactions.class));
    }

    @Test
    void testAddTransaction_LoanDetailsRequired() {
        AddTransaction transactionDto = new AddTransaction(100.0, PaymentMode.CASH, "description", TransactionType.LOAN, LocalDateTime.now(),"categoryId", false,new HashSet<>(), null);
        Category category = new Category();
        category.setTransactionType(TransactionType.LOAN);

        Transactions transaction = new Transactions();
        transaction.setTags(new HashSet<>());
        transaction.setTransactionType(TransactionType.LOAN);

        when(transactionRepo.save(any(Transactions.class))).thenReturn(transaction);
        when(categoryRepo.findById(transactionDto.getCategoryId())).thenReturn(Optional.of(category));

        CustomException exception = assertThrows(CustomException.class, () -> trackingAddService.addTransaction(transactionDto));

        assertEquals(ErrorCode.LOAN_DETAILS_REQUIRED.name(), exception.getMessage());
        verify(categoryRepo, times(1)).findById(transactionDto.getCategoryId());
    }

    @Test
    void testAddLoanRepayment() throws CustomException {
        AddLoanRepayment loanRepaymentDto = new AddLoanRepayment("transactionId", LocalDateTime.now(), 100.0, "note");
        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setPaidAmount(50.0);
        Transactions transaction = new Transactions();
        transaction.setAmount(150.0);
        loanDetails.setTransaction(transaction);

        when(loanDetailsRepo.findByTransaction_TransactionId(loanRepaymentDto.transactionId())).thenReturn(Optional.of(loanDetails));
        when(loanRepaymentRepo.save(any(LoanRepayment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanDetailsRepo.save(any(LoanDetails.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LoanRepayment result = trackingAddService.addLoanRepayment(loanRepaymentDto);

        assertNotNull(result);
        assertEquals(loanRepaymentDto.amount(), result.getAmount());
        assertEquals(loanRepaymentDto.note(), result.getNote());
        assertEquals(loanRepaymentDto.paymentDate(), result.getPaymentDate());
        assertEquals(loanDetails, result.getLoanDetails());
        assertEquals(150.0, loanDetails.getPaidAmount());
        assertEquals(0.0, loanDetails.getOutstandingAmount());
        assertTrue(loanDetails.getIsSettled());

        verify(loanDetailsRepo, times(1)).findByTransaction_TransactionId(loanRepaymentDto.transactionId());
        verify(loanRepaymentRepo, times(1)).save(any(LoanRepayment.class));
        verify(loanDetailsRepo, times(1)).save(any(LoanDetails.class));
    }
}