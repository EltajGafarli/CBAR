// src/components/ResetPassword.js
import React, { useState } from 'react';
import axios from 'axios';
import './ResetPassword.css';

const ResetPassword = () => {
  const [formData, setFormData] = useState({
    resetToken: '',
    newPassword: '',
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8088/api/auth/reset', formData);
      alert('Password reset successful');
    } catch (error) {
      setError('Password reset failed');
    }
  };

  return (
    <div className="reset-password-form">
      <h2>Reset Password</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="resetToken" placeholder="Reset Token" onChange={handleChange} />
        <input type="password" name="newPassword" placeholder="New Password" onChange={handleChange} />
        <button type="submit">Reset Password</button>
      </form>
      {error && <div className="error">{error}</div>}
    </div>
  );
};

export default ResetPassword;
