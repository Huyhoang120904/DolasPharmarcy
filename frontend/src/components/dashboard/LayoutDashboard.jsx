import React from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";
import "./style.css";
import Header from "./Header";

const DashboardLayout = () => {
  return (
    <div className="bg-neutral-100 h-screen w-screen overflow-hidden flex flex-row">
      <Sidebar />
      <div className="flex flex-col flex-1">
        <Header />
        <div className="flex-1 p-4 min-h-0 overflow-auto">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default DashboardLayout;
