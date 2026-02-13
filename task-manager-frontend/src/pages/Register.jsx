import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    if (!user) {
      navigate("/");
    }
  }, [user, navigate]);

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/");
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Dashboard</h2>
      <p>Welcome, {user?.username}</p>

      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Dashboard;
