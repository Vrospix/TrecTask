import { useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";


function Dashboard() {
    const navigate = useNavigate();
    const [user] = useState(() =>
      JSON.parse(localStorage.getItem("user"))
    );

    const [tasks, setTasks] = useState([]);
    const [newTask, setNewTask] = useState("");

    const fetchTasks = async () => {
        try {
          const res = await API.get(`/tasks/users/${user.id}`);
          setTasks(res.data.content);
        } catch (err) {
          console.error(err);
        }
    };

    useEffect(() => {
        if (!user) {
            navigate("/");
            return;
        }

        const load = async () => {
            await fetchTasks();
        };

        load();
    }, []);

    const handleAddTask = async () => {
        if (!newTask.trim()) return;

        try {
            const res = await API.post(
                `/tasks/users/${user.id}`,
                {
                    title: newTask,
                    description: "",
                    dueDate: null
                }
            );

            setNewTask("");

            fetchTasks();

        } catch (err) {
          console.error(err);
        }
    };

    const handleToggleStatus = async (taskId) => {
        try {
            await API.patch(`/tasks/${taskId}/toggle`);
            fetchTasks(); // refresh after toggle
        } catch (err) {
            console.error(err);
        }
    };

     const handleLogout = () => {
         localStorage.removeItem("user");
         navigate("/");
     };

     return (
         <div style={{ padding: "2rem" }}>
             <h2>Dashboard</h2>
             <p>Welcome, {user?.username}</p>

             <button onClick={handleLogout}>Logout</button>

             <hr />

             <h3>Your Tasks</h3>
             <ul>
                 {tasks.map((task) => (
                     <li key={task.id}>
                       {task.title}

                       <span
                         onClick={() => handleToggleStatus(task.id)}
                         style={{ cursor: "pointer", marginLeft: "10px" }}
                       >
                         {task.status === "DONE" ? "✅" : "⬜"}
                       </span>
                     </li>

                 ))}
             </ul>

             <h4>Add New Task</h4>
             <input
               value={newTask}
               onChange={(e) => setNewTask(e.target.value)}
               placeholder="New task"
             />
             <button onClick={handleAddTask}>Add</button>
         </div>
     );
 }

export default Dashboard;
