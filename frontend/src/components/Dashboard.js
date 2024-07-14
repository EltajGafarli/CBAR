// src/components/Dashboard.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import CurrencyConverter from './CurrencyConverter';
import CurrencyList from './CurrencyList';

const Dashboard = () => {
  const navigate = useNavigate();

  const goToProfile = () => {
    navigate('/profile');
  };

  return (
    <div>
      <button onClick={goToProfile} style={{ position: 'absolute', top: 10, right: 10 }}>Profile</button>
      <CurrencyConverter />
      <CurrencyList />
    </div>
  );
};

export default Dashboard;
