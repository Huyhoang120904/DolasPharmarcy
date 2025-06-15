import React, { createContext, useState, useContext, useEffect } from "react";
import { UserService } from "../api-services/UserService";
import { AuthService } from "../api-services/AuthService";

// Create the auth context
const AuthContext = createContext();

// Custom hook to use the auth context
export const useAuth = () => useContext(AuthContext);

// Auth provider component
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Login function
  const login = async (username, password) => {
    try {
      setLoading(true);
      setError(null);
      const authResponse = await AuthService.login(username, password);
      // Store token in localStorage
      localStorage.setItem("token", authResponse.result.token);

      const useReponse = await UserService.getMyInfo();
      setUser(useReponse.result);
      setIsAuthenticated(true);
      return { success: true };
    } catch (error) {
      console.log(error);
      setError(error.message || "Authentication failed");
      return { success: false, error: error.message };
    } finally {
      setLoading(false);
    }
  };

  //Register function
  const register = async (userData) => {
    try {
      setLoading(true);
      setError(null);

      const tokenResponse = await AuthService.register(userData);

      console.log(tokenResponse);

      // Store token in localStorage
      localStorage.setItem("token", tokenResponse.result.token);

      const userResponse = await UserService.getMyInfo();

      // Store user data in localStorage
      localStorage.setItem("user", JSON.stringify(userResponse.result));

      setUser(userResponse.result);
      setIsAuthenticated(true);

      return { success: true };
    } catch (error) {
      setError(error.message || "Authentication failed");
      return { success: false, error: error.message };
    } finally {
      setLoading(false);
    }
  };

  // Validate token function
  const validateToken = async () => {
    const token = localStorage.getItem("token");
    if (!token) return false;

    try {
      const response = await AuthService.introspectToken();

      return response.result.valid;
    } catch (error) {
      console.error("Token validation error:", error);
      return false;
    }
  };

  // Logout function
  const logout = async () => {
    await AuthService.logout();
    setUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem("token");
  };

  // Check for existing session on component mount
  useEffect(() => {
    const fetchUser = async () => {
      const storedToken = localStorage.getItem("token");
      if (storedToken) {
        try {
          setLoading(true);
          const validation = await validateToken();

          if (!validation) {
            throw new Error("");
          } else {
            const useReponse = await UserService.getMyInfo();
            setUser(useReponse.result);
            setIsAuthenticated(true);
          }
        } catch (e) {
          console.log(e);
        } finally {
          setLoading(false);
        }
      }
    };
    fetchUser();
  }, []);

  const value = {
    user,
    isAuthenticated,
    loading,
    error,
    login,
    logout,
    register,
    validateToken,
  };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
