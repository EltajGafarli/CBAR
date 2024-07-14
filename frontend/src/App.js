// src/App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import axios from 'axios';
import Register from './components/Register';
import Login from './components/Login';
import ResetPassword from './components/ResetPassword';
import ResetRequest from './components/ResetRequest';
import UserProfile from './components/UserProfile';
import Verification from './components/Verification';
import Dashboard from './components/Dashboard';
import PrivateRoute from './components/PrivateRoute';

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      setIsAuthenticated(true);
    }
  }, []);

  return (
    <Router>
      <div className="app-container">
        <main>
          <Routes>
            <Route path="/" element={<Navigate to="/login" />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login setIsAuthenticated={setIsAuthenticated} />} />
            <Route path="/reset" element={<ResetPassword />} />
            <Route path="/reset_request" element={<ResetRequest />} />
            <Route path="/verification" element={<Verification />} />
            <Route element={<PrivateRoute isAuthenticated={isAuthenticated} />}>
              <Route path="/profile" element={<UserProfile setIsAuthenticated={setIsAuthenticated} />} />
              <Route path="/dashboard" element={<Dashboard />} />
            </Route>
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
