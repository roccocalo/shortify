import React, { useState } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import UrlShortener from './components/UrlShortener';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [token, setToken] = useState('');
  const [username, setUsername] = useState('');
  const [currentView, setCurrentView] = useState('login'); // login, register, or shortener

  const handleLogin = (userData) => {
    setIsLoggedIn(true);
    setToken(userData.token);
    setUsername(userData.username);
    setCurrentView('shortener');
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setToken('');
    setUsername('');
    setCurrentView('login');
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>URL Shortener</h1>
        {isLoggedIn && (
          <div className="user-info">
            <span>Logged in as: {username}</span>
            <button className="logout-button" onClick={handleLogout}>Logout</button>
          </div>
        )}
      </header>
      <main>
        {!isLoggedIn && currentView === 'login' && (
          <div>
            <Login onLogin={handleLogin} />
            <p>
              Don't have an account?{' '}
              <button onClick={() => setCurrentView('register')}>Register</button>
            </p>
          </div>
        )}
        
        {!isLoggedIn && currentView === 'register' && (
          <div>
            <Register onRegisterSuccess={() => setCurrentView('login')} />
            <p>
              Already have an account?{' '}
              <button onClick={() => setCurrentView('login')}>Login</button>
            </p>
          </div>
        )}
        
        {isLoggedIn && currentView === 'shortener' && (
          <UrlShortener token={token} />
        )}
      </main>
    </div>
  );
}

export default App;