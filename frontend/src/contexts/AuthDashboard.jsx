import React, { use } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function ProtectedRoute({ adminOnly = false }) {
  const { isAuthenticated, user } = useAuth();

  if (!isAuthenticated || !user) {
    return <Navigate to={"/login"} replace />;
  }

  const isAdmin = user.roles.find(
    (role) => role.rolename.toLowerCase() === "admin"
  )
    ? true
    : false;

  if (adminOnly && !isAdmin) {
    alert("Chỉ admin mới có quyền truy cập khu vực này");
    return <Navigate to="/dashboard" replace />;
  }

  return <Outlet />;
}
