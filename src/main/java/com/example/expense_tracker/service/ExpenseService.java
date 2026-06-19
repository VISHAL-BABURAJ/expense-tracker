package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.ExpenseRequestDTO;
import com.example.expense_tracker.dto.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponseDTO> getAllExpenses();

    ExpenseResponseDTO getExpenseById(Long id);

    ExpenseResponseDTO createExpense(ExpenseRequestDTO requestDTO);

    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO requestDTO);

    void deleteExpense(Long id);
}
