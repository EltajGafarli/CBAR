// src/components/Navigation.js
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navigation = ({ setIsAuthenticated }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
    navigate('/login');
  };

  return (
    <nav>
      <ul>
        <li><Link to="/register">Register</Link></li>
        <li><Link to="/login">Login</Link></li>
        <li><Link to="/reset">Reset Password</Link></li>
        <li><Link to="/reset_request">Request Password Reset</Link></li>
        <li><Link to="/profile">Profile</Link></li>
        <li><Link to="/verification">Verification</Link></li>
        <li><Link to="/dashboard">Dashboard</Link></li>
        <li><button onClick={handleLogout}>Logout</button></li>
      </ul>
    </nav>
  );
};

export default Navigation;
