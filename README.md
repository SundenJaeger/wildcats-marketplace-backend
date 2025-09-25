# wildcats-marketplace-backend

# Authentication API

A simple Spring Boot API for student authentication with login and registration endpoints.

## Base URL
```
http://localhost:8080
```

## Endpoints

### 1. Register Student
Creates a new student account.

**POST** `/auth/register`

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123",
  "email": "john@example.com",
  "fullname": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "fullname": "John Doe"
}
```

### 2. Login Student
Authenticates a student using username/email and password.

**POST** `/auth/login`

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```
*Note: You can use either username or email in the `username` field*

**Response (200 OK):**
```json
{
  "username": "john_doe",
  "email": "john@example.com"
}
```

## Error Responses

The API returns appropriate HTTP status codes with error messages:

- **400 Bad Request** - Invalid request format
- **401 Unauthorized** - Invalid credentials
- **403 Forbidden** - Account is inactive
- **404 Not Found** - Student not found

**Error Response Format:**
```json
{
  "message": "Error description"
}
```

## React.js Examples

### API Service (api.js)
```javascript
const API_BASE_URL = 'http://localhost:8080';

export const authAPI = {
  register: async (userData) => {
    const response = await fetch(`${API_BASE_URL}/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message);
    }
    
    return response.json();
  },

  login: async (credentials) => {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message);
    }
    
    return response.json();
  }
};
```

### Register Component
```javascript
import React, { useState } from 'react';
import { authAPI } from './api';

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
    fullname: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await authAPI.register(formData);
      console.log('Registration successful:', response);
      // Handle success (redirect, show success message, etc.)
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="username"
        placeholder="Username"
        value={formData.username}
        onChange={handleChange}
        required
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="fullname"
        placeholder="Full Name"
        value={formData.fullname}
        onChange={handleChange}
        required
      />
      <input
        type="password"
        name="password"
        placeholder="Password"
        value={formData.password}
        onChange={handleChange}
        required
      />
      
      {error && <div style={{color: 'red'}}>{error}</div>}
      
      <button type="submit" disabled={loading}>
        {loading ? 'Registering...' : 'Register'}
      </button>
    </form>
  );
};
```

### Login Component
```javascript
import React, { useState } from 'react';
import { authAPI } from './api';

const LoginForm = () => {
  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await authAPI.login(credentials);
      console.log('Login successful:', response);
      // Store user data, redirect, etc.
      localStorage.setItem('user', JSON.stringify(response));
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="username"
        placeholder="Username or Email"
        value={credentials.username}
        onChange={handleChange}
        required
      />
      <input
        type="password"
        name="password"
        placeholder="Password"
        value={credentials.password}
        onChange={handleChange}
        required
      />
      
      {error && <div style={{color: 'red'}}>{error}</div>}
      
      <button type="submit" disabled={loading}>
        {loading ? 'Logging in...' : 'Login'}
      </button>
    </form>
  );
};
```

## Notes

- Passwords are stored in plain text (consider implementing password hashing for production)
- No authentication tokens are returned (consider implementing JWT for session management)
- All endpoints accept and return JSON data