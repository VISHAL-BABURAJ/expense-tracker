package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseRequestDTO;
import com.example.expense_tracker.dto.ExpenseResponseDTO;
import com.example.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "CRUD operations for Expense management")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    // Constructor injection
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // -------------------------------------------------------
    // GET /expenses  –  List all expenses
    // -------------------------------------------------------
    @GetMapping
    @Operation(summary = "Get all expenses", description = "Returns a list of all recorded expenses")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    })
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // -------------------------------------------------------
    // GET /expenses/{id}  –  Get a single expense
    // -------------------------------------------------------
    @GetMapping("/{id}")
    @Operation(summary = "Get expense by ID", description = "Returns a single expense matching the given ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Expense found"),
        @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(
            @Parameter(description = "ID of the expense to retrieve", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    // -------------------------------------------------------
    // POST /expenses  –  Create a new expense
    // -------------------------------------------------------
    @PostMapping
    @Operation(summary = "Create a new expense", description = "Creates and persists a new expense record")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Expense created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error in request body")
    })
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {

        ExpenseResponseDTO created = expenseService.createExpense(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // -------------------------------------------------------
    // PUT /expenses/{id}  –  Update an existing expense
    // -------------------------------------------------------
    @PutMapping("/{id}")
    @Operation(summary = "Update an expense", description = "Fully updates an existing expense by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error in request body"),
        @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @Parameter(description = "ID of the expense to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {

        return ResponseEntity.ok(expenseService.updateExpense(id, requestDTO));
    }

    // -------------------------------------------------------
    // DELETE /expenses/{id}  –  Delete an expense
    // -------------------------------------------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an expense", description = "Deletes the expense record with the given ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<Void> deleteExpense(
            @Parameter(description = "ID of the expense to delete", required = true)
            @PathVariable Long id) {

        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
