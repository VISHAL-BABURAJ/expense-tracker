package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.ExpenseRequestDTO;
import com.example.expense_tracker.dto.ExpenseResponseDTO;
import com.example.expense_tracker.entity.Expense;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    // Constructor injection
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // -------------------------------------------------------
    // GET all
    // -------------------------------------------------------
    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // GET by id
    // -------------------------------------------------------
    @Override
    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = findOrThrow(id);
        return toResponseDTO(expense);
    }

    // -------------------------------------------------------
    // POST create
    // -------------------------------------------------------
    @Override
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO requestDTO) {
        Expense expense = toEntity(requestDTO);
        Expense saved = expenseRepository.save(expense);
        return toResponseDTO(saved);
    }

    // -------------------------------------------------------
    // PUT update
    // -------------------------------------------------------
    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO requestDTO) {
        Expense existing = findOrThrow(id);
        existing.setCategory(requestDTO.getCategory());
        existing.setAmount(requestDTO.getAmount());
        existing.setDate(requestDTO.getDate());
        existing.setDescription(requestDTO.getDescription());
        Expense updated = expenseRepository.save(existing);
        return toResponseDTO(updated);
    }

    // -------------------------------------------------------
    // DELETE
    // -------------------------------------------------------
    @Override
    public void deleteExpense(Long id) {
        Expense existing = findOrThrow(id);
        expenseRepository.delete(existing);
    }

    // -------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------
    private Expense findOrThrow(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }

    private ExpenseResponseDTO toResponseDTO(Expense expense) {
    return new ExpenseResponseDTO(
            expense.getId(),
            expense.getCategory(),
            expense.getAmount(),
            expense.getDate(),
            expense.getDescription()
    );
}

    private Expense toEntity(ExpenseRequestDTO dto) {
        Expense expense = new Expense();
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setDescription(dto.getDescription());
        return expense;
    }
}
