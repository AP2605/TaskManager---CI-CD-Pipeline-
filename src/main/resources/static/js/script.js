const API_URL = "http://localhost:8081/tasks";

let editingId = null;

// Load tasks when page loads
window.onload = function () {
    loadTasks();
};

// ----------------------
// Add or Update Task
// ----------------------
async function addTask() {

    const task = {

        taskName: document.getElementById("taskName").value,

        description: document.getElementById("description").value,

        priority: document.getElementById("priority").value,

        status: document.getElementById("status").value,

        dueDate: document.getElementById("dueDate").value

    };

    let response;

    if (editingId == null) {

        // ADD TASK

        response = await fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(task)

        });

    } else {

        // UPDATE TASK

        response = await fetch(API_URL + "/" + editingId, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(task)

        });

        editingId = null;

        document.getElementById("addButton").innerHTML = "Add Task";

    }

    alert(await response.text());

    clearForm();

    loadTasks();

}

// ----------------------
// Load Tasks
// ----------------------
async function loadTasks() {

    const response = await fetch(API_URL);

    const tasks = await response.json();

    let rows = "";

    tasks.forEach(task => {

        rows += `
        <tr>

            <td>${task.id}</td>

            <td>${task.taskName}</td>

            <td>${task.description}</td>

            <td>
                <span class="badge bg-danger">
                    ${task.priority}
                </span>
            </td>

            <td>
                <span class="badge bg-warning">
                    ${task.status}
                </span>
            </td>

            <td>${task.dueDate}</td>

            <td>

                <button
                    class="btn btn-primary btn-sm"
                    onclick="editTask(${task.id})">

                    Edit

                </button>

                <button
                    class="btn btn-danger btn-sm"
                    onclick="deleteTask(${task.id})">

                    Delete

                </button>

            </td>

        </tr>
        `;

    });

    document.getElementById("taskTable").innerHTML = rows;

}

// ----------------------
// Edit Task
// ----------------------
async function editTask(id) {

    const response = await fetch(API_URL);

    const tasks = await response.json();

    const task = tasks.find(t => t.id === id);

    if (!task) return;

    document.getElementById("taskName").value = task.taskName;

    document.getElementById("description").value = task.description;

    document.getElementById("priority").value = task.priority;

    document.getElementById("status").value = task.status;

    document.getElementById("dueDate").value = task.dueDate;

    editingId = id;

    document.getElementById("addButton").innerHTML = "Update Task";

}

// ----------------------
// Delete Task
// ----------------------
async function deleteTask(id) {

    if (!confirm("Delete this task?")) {

        return;

    }

    await fetch(API_URL + "/" + id, {

        method: "DELETE"

    });

    loadTasks();

}

// ----------------------
// Clear Form
// ----------------------
function clearForm() {

    document.getElementById("taskName").value = "";

    document.getElementById("description").value = "";

    document.getElementById("priority").selectedIndex = 0;

    document.getElementById("status").selectedIndex = 0;

    document.getElementById("dueDate").value = "";

}