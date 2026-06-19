let editingId = null;
function addExpense() {

    let category = document.getElementById("category").value;
    let amount = document.getElementById("amount").value;
    let date = document.getElementById("date").value;
    let description = document.getElementById("description").value;

    if(category === "" || amount === "") {
        alert("Please fill all fields");
        return;
    }

    const expenseData = {
        category: category,
        amount: parseFloat(amount),
        date: date,
        description: description
    };
    if(editingId !== null) {

    fetch(`http://localhost:9090/expenses/${editingId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(expenseData)
    })
    .then(() => {
        location.reload();
    })
    .catch(error => console.error(error));

    return;
}

    fetch("http://localhost:9090/expenses", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(expenseData)
    })
    .then(response => response.json())
    .then(data => {

        let table = document.getElementById("expenseTable");

        let row = table.insertRow();

        row.insertCell(0).innerHTML = data.id;
        row.insertCell(1).innerHTML = data.category;
        row.insertCell(2).innerHTML = data.amount;
        row.insertCell(3).innerHTML = data.date;
        row.insertCell(4).innerHTML = data.description;

        row.insertCell(5).innerHTML =
        `<button onclick="editExpense(this)">Edit</button>
         <button onclick="deleteExpense(this)">Delete</button>`;

        updateTotal();
        updateCategoryTotals();
        updateTransactionCount();

        document.getElementById("category").value = "";
        document.getElementById("amount").value = "";
        document.getElementById("date").value = "";
        document.getElementById("description").value = "";
    })
    .catch(error => console.error(error));
}

function updateTotal()
{
    let table =
        document.getElementById("expenseTable");

    let total = 0;

    for(let i = 1; i < table.rows.length; i++)
    {
        let amount =
            parseFloat(table.rows[i].cells[2].innerHTML);

        if(!isNaN(amount))
        {
            total += amount;
        }
    }

    document.getElementById("totalExpense").innerHTML =
        "₹" + total;
}


function deleteExpense(button) {

    let row = button.parentNode.parentNode;

    let expenseId = row.cells[0].innerHTML;

    fetch(`http://localhost:9090/expenses/${expenseId}`, {
        method: "DELETE"
    })
    .then(() => {
        row.remove();

        updateTotal();
        updateCategoryTotals();
        updateTransactionCount();
    })
    .catch(error => console.error(error));
}
function searchExpense()
{
    let input =
        document.getElementById("search");

    let filter =
        input.value.toUpperCase();

    let table =
        document.getElementById("expenseTable");

    let tr =
        table.getElementsByTagName("tr");

    for(let i = 1; i < tr.length; i++)
    {
        let td =
            tr[i].getElementsByTagName("td")[1];

        if(td)
        {
            let txt =
                td.textContent || td.innerText;

            if(txt.toUpperCase().indexOf(filter) > -1)
            {
                tr[i].style.display = "";
            }
            else
            {
                tr[i].style.display = "none";
            }
        }
    }
}

function setActive(item)
{
    let items =
        document.querySelectorAll(".sidebar li");

    items.forEach(li =>
        li.classList.remove("active"));

    item.classList.add("active");
    
}

function editExpense(button) {

    let row = button.parentNode.parentNode;

    editingId = row.cells[0].innerHTML;

    document.getElementById("category").value = row.cells[1].innerHTML;
    document.getElementById("amount").value = row.cells[2].innerHTML;
    document.getElementById("date").value = row.cells[3].innerHTML;
    document.getElementById("description").value = row.cells[4].innerHTML;

    document.getElementById("saveButton").innerHTML = "Update Expense";
}
function updateCategoryTotals()
{
    let table =
        document.getElementById("expenseTable");

    let food = 0;
    let shopping = 0;
    let transport = 0;

    for(let i = 1; i < table.rows.length; i++)
    {
        let category =
            table.rows[i].cells[1].innerHTML
            .toLowerCase();

        let amount =
            parseFloat(
                table.rows[i].cells[2].innerHTML
            );

        if(category === "food")
        {
            food += amount;
        }
        else if(category === "shopping")
        {
            shopping += amount;
        }
        else if(category === "transport")
        {
            transport += amount;
        }
    }

    document.getElementById("foodTotal").innerHTML =
        "₹" + food;

    document.getElementById("shoppingTotal").innerHTML =
        "₹" + shopping;

    document.getElementById("transportTotal").innerHTML =
        "₹" + transport;
}
function exportCSV()
{
    let table =
        document.getElementById("expenseTable");

    let csv = [];

    for(let i = 0; i < table.rows.length; i++)
    {
        let row = [];

        for(let j = 0; j < table.rows[i].cells.length; j++)
        {
            row.push(
                table.rows[i].cells[j].innerText
            );
        }

        csv.push(row.join(","));
    }

    let csvFile =
        new Blob(
            [csv.join("\n")],
            {type:"text/csv"}
        );

    let downloadLink =
        document.createElement("a");

    downloadLink.download =
        "expenses.csv";

    downloadLink.href =
        window.URL.createObjectURL(csvFile);

    downloadLink.click();
}
function updateTransactionCount() {
    let table = document.getElementById("expenseTable");

    let count = table.rows.length - 1;

    document.getElementById("transactionCount").innerHTML = count;
}

updateTotal();
updateCategoryTotals();
updateTransactionCount();
// loadExpenses();
fetch("http://localhost:9090/expenses")
.then(response => response.json())
.then(data => {

    let table = document.getElementById("expenseTable");

    data.forEach(expense => {

    let row = table.insertRow();

          row.insertCell(0).innerHTML = expense.id;
          row.insertCell(1).innerHTML = expense.category;
          row.insertCell(2).innerHTML = expense.amount;
          row.insertCell(3).innerHTML = expense.date;
          row.insertCell(4).innerHTML = expense.description;

          row.insertCell(5).innerHTML = `
              <button onclick="editExpense(this)">Edit</button>
              <button onclick="deleteExpense(this)">Delete</button>
          `;
      });

      updateTotal();
      updateCategoryTotals();
      updateTransactionCount();
  })
  .catch(error => console.error(error));