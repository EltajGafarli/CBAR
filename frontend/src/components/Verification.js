// src/components/Verification.js
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Verification.css';

const Verification = () => {
  const [formData, setFormData] = useState({
    email: '',
    verificationCode: '',
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8088/api/auth/verification/verify', formData);
      alert('Verification successful. Please login.');
      navigate('/login');
    } catch (error) {
      setError('Verification failed');
    }
  };

  return (
    <div className="verification-form">
      <h2>Verify Your Account</h2>
      <form onSubmit={handleSubmit}>
        <input type="email" name="email" placeholder="Email" onChange={handleChange} />
        <input type="text" name="verificationCode" placeholder="Verification Code" onChange={handleChange} />
        <button type="submit">Verify</button>
      </form>
      {error && <div className="error">{error}</div>}
    </div>
  );
};

export default Verification;
