// src/components/ResetRequest.js
import React from 'react';
import axios from 'axios';
import './ResetRequest.css';

const ResetRequest = () => {
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get('http://localhost:8088/api/auth/reset_request', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      alert('Reset token generated');
    } catch (error) {
      alert('Reset request failed');
    }
  };

  return (
    <div className="reset-request-form">
      <h2>Request Password Reset</h2>
      <form onSubmit={handleSubmit}>
        <button type="submit">Request Password Reset</button>
      </form>
    </div>
  );
};

export default ResetRequest;
